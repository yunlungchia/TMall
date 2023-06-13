-- ----------------------------
-- Table structure for `goods`
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
     `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
     `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
     `goods_img` varchar(64) DEFAULT NULL COMMENT '商品图片',
     `goods_detail` longtext COMMENT '商品详细介绍',
     `goods_price` decimal(10,2) DEFAULT NULL COMMENT '商品单价',
     `goods_stock` int(11) DEFAULT NULL COMMENT '商品库存，-1表示没有限制',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', 'iphone X', 'iphone X(A1865 64G 黑色 全网通 4G手机)', '/img/iphonex.png', 'iphone X(A1865 64G 黑色 全网通 4G手机)', '8765.00', '10000');
INSERT INTO `goods` VALUES ('2', '华为Meta 10', '华为Meta 10(A1865 64G 黑色 全网通 4G手机)', '/img/meta10.png', '华为Meta 10(A1865 64G 黑色 全网通 4G手机)', '3300.00', '-1');

