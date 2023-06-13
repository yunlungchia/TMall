package com.personal.tmall.mapper;

import com.personal.tmall.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper {
    int deleteByPrimaryKey(Long id);

    int deleteAll();

    int insert(OrderInfo record);

    OrderInfo selectByPrimaryKey(Long id);

    OrderInfo selectByUserIdAndGoodsId(Long userId, Long goodsId);

    List<OrderInfo> selectAll();

    int updateByPrimaryKey(OrderInfo record);
}