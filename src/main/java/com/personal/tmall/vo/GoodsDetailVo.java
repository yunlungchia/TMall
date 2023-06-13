package com.personal.tmall.vo;

import com.personal.tmall.entity.User;
import lombok.Builder;
import lombok.Data;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Data
@Builder
public class GoodsDetailVo {
    private int secKillStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goodsVo;
    private User user;
}
