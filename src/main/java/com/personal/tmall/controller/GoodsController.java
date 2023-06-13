package com.personal.tmall.controller;

import com.personal.tmall.entity.User;
import com.personal.tmall.redis.GoodsKey;
import com.personal.tmall.redis.RedisService;
import com.personal.tmall.result.Result;
import com.personal.tmall.service.GoodsService;
import com.personal.tmall.service.UserService;
import com.personal.tmall.vo.GoodsDetailVo;
import com.personal.tmall.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 商品列表页面
     *
     * @param model
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("user", user);

        // 从缓存里面获取页面数据
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        // 查库获取商品数据
        List<GoodsVo> goodsVoList = goodsService.queryGoodsList();
        model.addAttribute("goodsList", goodsVoList);

        // 渲染成HTML
        WebContext ctx = new WebContext(request, response,
                request.getServletContext(), request.getLocale(),
                model.asMap());

        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        // 将HTML写入缓存
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    /**
     * 去商品详情面,提供页面需要的参数
     *
     * @param model
     * @param user
     * @param goodsId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> goodsDetail(Model model, User user, @PathVariable("goodsId") Long goodsId, HttpServletRequest request, HttpServletResponse response) {

        // 查询商品对象
        GoodsVo goods = goodsService.getGoodsByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        // 秒杀剩余时间
        long remainSeconds;
        // 0:秒杀未开始 1:秒杀进行中 2:秒杀结束
        long secKillStatus;

        long start = goods.getStartDate().getTime();
        long end = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        if (now < start) {
            // 秒杀未开始
            secKillStatus = 0;
            remainSeconds = (start - now) / 1000;
        } else if (end < now) {
            // 秒杀结束
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            //秒杀正在进行中
            secKillStatus = 1;
            remainSeconds = 0;
        }

        GoodsDetailVo goodsDetailVo = GoodsDetailVo.builder()
                .secKillStatus((int) secKillStatus)
                .remainSeconds((int) remainSeconds)
                .goodsVo(goods)
                .user(user).build();

        return Result.success(goodsDetailVo);
    }

}
