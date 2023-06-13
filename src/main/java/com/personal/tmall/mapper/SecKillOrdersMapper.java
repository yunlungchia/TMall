package com.personal.tmall.mapper;

import com.personal.tmall.entity.SecKillOrders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SecKillOrdersMapper {
    int deleteByPrimaryKey(Long id);

    int deleteAll();

    int insert(SecKillOrders record);

    SecKillOrders selectByPrimaryKey(Long id);

    SecKillOrders selectByUserIdAndGoodsId(Long userId, Long goodsId);

    List<SecKillOrders> selectAll();

    int updateByPrimaryKey(SecKillOrders record);
}