-- ----------------------------
-- Table structure for `seckill_orders`
-- ----------------------------
DROP TABLE IF EXISTS `seckill_orders`;
CREATE TABLE `seckill_orders`
(
    `id`        bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`   bigint(20) DEFAULT NULL COMMENT '用户id',
    `orders_id` bigint(20) DEFAULT NULL COMMENT '订单id',
    `goods_id`  bigint(20) DEFAULT NULL COMMENT '商品id',
    PRIMARY KEY (`id`),
    UNIQUE KEY `seckill_orders_userId_goodsId_index` (`user_id`, `goods_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 706
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of seckill_order
-- ----------------------------
INSERT INTO `seckill_orders`
VALUES ('705', '13240414892', '705', '1');