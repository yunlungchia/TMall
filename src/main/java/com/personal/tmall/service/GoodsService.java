package com.personal.tmall.service;

import com.personal.tmall.vo.GoodsVo;

import java.util.List;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
public interface GoodsService {
    GoodsVo getGoodsByGoodsId(Long goodsId);

    List<GoodsVo> queryGoodsList();

    int reduceStock(GoodsVo goodsVo);

    void resetStock(List<GoodsVo> goodsVoList);
}
