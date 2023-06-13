-- ----------------------------
-- Table structure for `seckill_goods`
-- ----------------------------
DROP TABLE IF EXISTS `seckill_goods`;
CREATE TABLE `seckill_goods` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品表主键',
    `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
    `seckill_price` decimal(10,2) DEFAULT NULL COMMENT '秒杀价',
    `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
    `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
    `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill_goods
-- ----------------------------
INSERT INTO `seckill_goods` VALUES ('1', '1', '0.03', '9', '2019-11-13 10:56:00', '2019-11-30 11:48:00');
INSERT INTO `seckill_goods` VALUES ('2', '2', '0.03', '10', '2019-10-26 09:42:00', '2019-10-28 10:42:00');