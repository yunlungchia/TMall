package com.personal.tmall.mq;

import com.personal.tmall.entity.User;
import lombok.Builder;
import lombok.Data;

/**
 * @author YunLung Chia
 * @date 2023/6/12
 */
@Data
@Builder
public class UserAndGoodsMsg {
    private User user;
    private Long goodsId;
}
