-- ----------------------------
-- Table structure for `order_info`
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`
(
    `id`               bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
    `user_id`          bigint(20)     DEFAULT NULL COMMENT '用户id',
    `goods_id`         bigint(20)     DEFAULT NULL COMMENT '商品id',
    `delivery_addr_id` bigint(20)     DEFAULT NULL COMMENT '收获地址id',
    `goods_name`       varchar(16)    DEFAULT NULL COMMENT '冗余过来的商品名称',
    `goods_count`      int(11)        DEFAULT NULL COMMENT '商品数量',
    `goods_price`      decimal(10, 2) DEFAULT NULL COMMENT '商品单价',
    `goods_channel`    tinyint(4)     DEFAULT NULL COMMENT '1.pc 2.android 3.ios',
    `status`           tinyint(4)     DEFAULT NULL COMMENT '订单状态：0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
    `create_date`      datetime       DEFAULT NULL COMMENT '订单创建时间',
    `pay_date`         datetime       DEFAULT NULL COMMENT '支付时间',
    `order_number`     varchar(100)   DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` (`id`, `user_id`, `goods_id`, `delivery_addr_id`, `goods_name`, `goods_count`, `goods_price`,
                          `goods_channel`, `status`, `create_date`, `pay_date`, `order_number`)
VALUES (4, 13000000000, 1, NULL, 'iphone X', 1, 250.00, 1, 0, '2023-06-13 16:32:04', NULL, NULL);

