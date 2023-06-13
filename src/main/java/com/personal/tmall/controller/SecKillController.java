package com.personal.tmall.controller;

import com.personal.tmall.annotation.AccessLimit;
import com.personal.tmall.biz.SecKillBiz;
import com.personal.tmall.entity.SecKillOrders;
import com.personal.tmall.entity.User;
import com.personal.tmall.mq.MQProducer;
import com.personal.tmall.mq.UserAndGoodsMsg;
import com.personal.tmall.redis.AccessKey;
import com.personal.tmall.redis.GoodsKey;
import com.personal.tmall.redis.OrderKey;
import com.personal.tmall.redis.RedisService;
import com.personal.tmall.result.CodeMsg;
import com.personal.tmall.result.Result;
import com.personal.tmall.service.GoodsService;
import com.personal.tmall.service.OrdersService;
import com.personal.tmall.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Slf4j
@Controller
@RequestMapping("/seckill")
public class SecKillController implements InitializingBean {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    RedisService redisService;

    @Autowired
    SecKillBiz secKillBiz;

    @Autowired
    MQProducer mqProducer;

    private Map<Long, Boolean> localOverMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        // 项目启动把商品的库存全部放到了redis里面
        List<GoodsVo> goodsList = goodsService.queryGoodsList();
        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getGoodsStock, "" + goods.getId(), goods.getStockCount());
            //说明 没有结束
            localOverMap.put(goods.getId(), false);
        }
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.queryGoodsList();
        for (GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getGoodsStock, "" + goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getOrderKey);
        redisService.delete(OrderKey.getSecKillFailKey);
        secKillBiz.reset(goodsList);
        return Result.success(true);
    }

    @RequestMapping(value = "/{path}/do_seckill", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> secKill(User user, Long goodsId, Model model, @PathVariable("path") String path) {
        if (user == null) {
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        // 验证路径是否正确
        Boolean check = secKillBiz.checkPath(user, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        // 内存标记商品是否卖完，减少redis访问
        if (localOverMap.get(goodsId)) {
            return Result.error(CodeMsg.SECKILL_STOCKISNULL);
        }

        // 缓存里预减库存，库存是否小于0, 返回错误信息，如果不小于继续后面操作
        Long stock = redisService.decr(GoodsKey.getGoodsStock, "" + goodsId);
        if (stock <= 0) {
            // true代表结束
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.SECKILL_STOCKISNULL);
        }

        // 判断该商品是否已经秒杀过，查询表
        SecKillOrders order = ordersService.queryOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.SECKILL_ISREPEATED);
        }
        // 封装秒杀用户信息和秒杀商品id
        UserAndGoodsMsg msg = UserAndGoodsMsg.builder()
                .user(user)
                .goodsId(goodsId).build();

        // 准备入队，使用driect队列方式
        mqProducer.produceTopicMsg(msg);
        // 通过消息队列完成 减库存和生成订单和秒杀订单
        return Result.success(0);
    }

    /**
     * 查询秒杀结果
     *
     * @param user    user
     * @param model   model
     * @param goodsId goodsId
     * @return orderId:秒杀结果订单  -1:秒杀失败  0:秒杀排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> result(User user, Model model, @RequestParam(value = "goodsId") long goodsId) {
        // 如果没有登录跳转到登录页面
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        model.addAttribute("user", user);
        long result = secKillBiz.getSecKillResult(user.getId(), goodsId);
        return Result.success(result);
    }

    /**
     * 获取秒杀地址
     *
     * @param user
     * @param model
     * @param goodsId
     * @param verifyCode
     * @param request
     * @return
     */
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getPath(User user, Model model, @RequestParam(value = "goodsId") long goodsId
            , @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode, HttpServletRequest request) {

        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 接口限流 比如实现50秒内访问5次限制
        String uri = request.getRequestURI();
        // 缓存中获取 这个访问地址上次访问的次数
        String key = uri + "_" + user.getId();
        Integer accessCount = redisService.get(AccessKey.getAccessKey, key, Integer.class);
        if (accessCount == null) {
            redisService.set(AccessKey.getAccessKey, key, 1);
        } else if (accessCount <= 5) {
            redisService.incr(AccessKey.getAccessKey, key);
        } else {
            return Result.error(CodeMsg.ACCESS_LIMIT_REACHED);
        }

        // 检查验证码
        boolean checkRes = secKillBiz.checkVerifyCode(user, goodsId, verifyCode);
        if (!checkRes) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        String path = secKillBiz.createPath(user, goodsId);
        return Result.success(path);
    }


    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getSecKillVerifyCode(HttpServletResponse response, User user,
                                               @RequestParam(name = "goodsId") String goodsId) {
        if (user == null) {
            log.error("getSecKillVerifyCode User is null");
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        try {
            if (goodsId.isEmpty()) {
                throw new RuntimeException("Bad goodsId");
            }
            BufferedImage image = secKillBiz.createVerifyCode(user, Long.parseLong(goodsId));
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.SECKILL_FAIL);
        }
    }
}
