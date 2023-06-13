package com.personal.tmall.service.impl;

import com.personal.tmall.entity.Goods;
import com.personal.tmall.entity.SecKillGoods;
import com.personal.tmall.mapper.GoodsMapper;
import com.personal.tmall.mapper.SecKillGoodsMapper;
import com.personal.tmall.service.GoodsService;
import com.personal.tmall.vo.GoodsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    SecKillGoodsMapper secKillGoodsMapper;

    @Override
    public GoodsVo getGoodsByGoodsId(Long goodsId) {
        GoodsVo goodsVo = new GoodsVo();
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        SecKillGoods secKillGoods = secKillGoodsMapper.selectByGoodsId(goodsId);
        BeanUtils.copyProperties(goods, goodsVo);
        BeanUtils.copyProperties(secKillGoods, goodsVo, "id");
        return goodsVo;
    }

    @Override
    public List<GoodsVo> queryGoodsList() {
        List<GoodsVo> goodsVoList = new ArrayList<>();
        goodsMapper.selectAll().forEach(goods -> {
            GoodsVo goodsVo = new GoodsVo();
            SecKillGoods secKillGoods = secKillGoodsMapper.selectByGoodsId(goods.getId());
            if (secKillGoods != null) {
                BeanUtils.copyProperties(goods, goodsVo);
                BeanUtils.copyProperties(secKillGoods, goodsVo, "id");
                goodsVoList.add(goodsVo);
            }
        });
        return goodsVoList;
    }

    @Override
    public int reduceStock(GoodsVo goodsVo) {
        return secKillGoodsMapper.updateByReduceStockCount(goodsVo.getGoodsId());
    }

    @Override
    public void resetStock(List<GoodsVo> goodsVoList){
        goodsVoList.forEach(goodsVo -> {
            secKillGoodsMapper.updateByResetStockCount(goodsVo.getStockCount(), goodsVo.getId());
        });
    }

}
