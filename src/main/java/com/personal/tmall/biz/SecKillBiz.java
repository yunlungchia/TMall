package com.personal.tmall.biz;

import com.personal.tmall.entity.OrderInfo;
import com.personal.tmall.entity.SecKillOrders;
import com.personal.tmall.entity.User;
import com.personal.tmall.redis.OrderKey;
import com.personal.tmall.redis.RedisService;
import com.personal.tmall.redis.SecKillKey;
import com.personal.tmall.service.GoodsService;
import com.personal.tmall.service.OrdersService;
import com.personal.tmall.util.MD5Util;
import com.personal.tmall.util.UUIDUtil;
import com.personal.tmall.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Service
public class SecKillBiz {
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    RedisService redisService;

    private static final char[] OPS = new char[]{'+', '-', '*'};

    @Transactional
    public OrderInfo secKill(User user, GoodsVo goodsVo) {
        // 减库存
        int reduceStockRes = goodsService.reduceStock(goodsVo);

        // 生成订单，同时生成秒杀订单
        if (reduceStockRes > 0) {
            return ordersService.insertOrder(user, goodsVo);
        } else {
            if (goodsVo != null) {
                setGoodsOver(goodsVo.getId());
            }
        }
        return null;
    }

    public long getSecKillResult(Long userId, long goodsId) {

        // 查询秒杀订单
        SecKillOrders order = ordersService.queryOrderByUserIdAndGoodsId(userId, goodsId);

        // 三种情况
        if (order != null) {
            return order.getOrdersId();
        } else {
            Boolean isOver = getGoodsOver(goodsId);
            // 秒杀失败
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        // true是失败
        redisService.set(OrderKey.getSecKillFailKey, "" + goodsId, true);
    }

    private Boolean getGoodsOver(Long goodsId) {
        return redisService.exists(OrderKey.getSecKillFailKey, "" + goodsId);

    }

    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        ordersService.deleteOrders();
    }

    public String createPath(User user, long goodsId) {
        String path = MD5Util.md5(UUIDUtil.uuid() + "123");
        redisService.set(SecKillKey.getSecKillPath, "" + user.getId() + "_" + goodsId, path);
        return path;
    }

    public Boolean checkPath(User user, Long goodsId, String path) {
        String oldPath = redisService.get(SecKillKey.getSecKillPath, "" + user.getId() + "_" + goodsId, String.class);
        return path.equals(oldPath);
    }

    /**
     * 生成验证码
     *
     * @param user
     * @param goodsId
     * @return
     */
    public BufferedImage createVerifyCode(User user, long goodsId) {
        if (goodsId <= 0) {
            return null;
        }

        int width = 140;
        int height = 32;
        // create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        // 把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(SecKillKey.getSecKillVerifyCode, user.getId() + "," + goodsId, rnd);
        // 输出图片
        return image;
    }

    public boolean checkVerifyCode(User user, long goodsId, int verifyCode) {
        if (goodsId <= 0) {
            return false;
        }
        Integer codeOld = redisService.get(SecKillKey.getSecKillVerifyCode, user.getId() + "," + goodsId, Integer.class);
        if (codeOld == null || (codeOld - verifyCode) != 0) {
            return false;
        }
        redisService.delete(SecKillKey.getSecKillVerifyCode, user.getId() + "," + goodsId);
        return true;
    }

    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            // 1*5+2
            return (Integer) engine.eval(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * + - *
     *
     * @param rdm
     * @return
     */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        int num4 = rdm.nextInt(10);
        char op1 = OPS[rdm.nextInt(3)];
        char op2 = OPS[rdm.nextInt(3)];
        char op3 = OPS[rdm.nextInt(3)];
        return "" + num1 + op1 + num2 + op2 + num3 + op3 + num4;
    }
}
