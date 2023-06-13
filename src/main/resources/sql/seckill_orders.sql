-- ----------------------------
-- Table structure for `seckill_orders`
-- ----------------------------
DROP TABLE IF EXISTS `seckill_orders`;
CREATE TABLE `seckill_orders`
(
    `id`        bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单id',
    `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
    `orders_id` bigint(20) DEFAULT NULL COMMENT '订单id',
    `goods_id`  bigint(20) DEFAULT NULL COMMENT '商品id',
    PRIMARY KEY (`id`),
    UNIQUE KEY `seckill_orders_userId_goodsId_index` (`user_id`, `goods_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of seckill_order
-- ----------------------------
INSERT INTO `seckill_orders` (`id`, `user_id`, `orders_id`, `goods_id`)
VALUES (4, 13000000000, 4, 1);
