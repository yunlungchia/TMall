package com.personal.tmall.vo;

import com.personal.tmall.entity.OrderInfo;
import lombok.Builder;
import lombok.Data;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Data
@Builder
public class OrderDetailVo {
    private GoodsVo goodsVo;
    private OrderInfo orderInfo;
}
