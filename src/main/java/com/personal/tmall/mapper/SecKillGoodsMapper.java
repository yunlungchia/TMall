package com.personal.tmall.mapper;

import com.personal.tmall.entity.SecKillGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SecKillGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecKillGoods record);

    SecKillGoods selectByPrimaryKey(Long id);

    SecKillGoods selectByGoodsId(Long goodsId);

    List<SecKillGoods> selectAll();

    int updateByPrimaryKey(SecKillGoods record);

    int updateByReduceStockCount(Long goodsId);

    int updateByResetStockCount(Integer stockCount, Long goodsId);
}