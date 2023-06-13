-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`              bigint(20) NOT NULL COMMENT '用户id，手机号',
    `nickname`        varchar(255) DEFAULT NULL,
    `password`        varchar(32)  DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
    `salt`            varchar(10)  DEFAULT NULL,
    `head`            varchar(128) DEFAULT NULL COMMENT '头像',
    `register_data`   datetime     DEFAULT NULL,
    `last_login_data` datetime     DEFAULT NULL,
    `login_count`     int(11)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;