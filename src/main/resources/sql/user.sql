-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`              bigint(20) NOT NULL COMMENT '用户id, 手机号',
    `nickname`        varchar(255) DEFAULT NULL COMMENT '昵称'
    `password`        varchar(32)  DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
    `salt`            varchar(10)  DEFAULT NULL COMMENT '加密SALT'
    `head`            varchar(128) DEFAULT NULL COMMENT '头像',
    `register_data`   datetime     DEFAULT NULL COMMENT '注册时间'
    `last_login_data` datetime     DEFAULT NULL COMMENT '最后登录时间'
    `login_count`     int(11)      DEFAULT NULL COMMENT '登录次数'
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

# 明文登录密码: 123456
INSERT INTO `user` (`id`, `nickname`, `password`, `salt`, `head`, `register_data`, `last_login_data`, `login_count`)
VALUES
    (13000000000, 'user0', 'a69eead8138f909fe5b79e9f95971a82', '1a2b3c', NULL, '2022-05-14 16:50:42', NULL, 1),
    (13000000001, 'user1', 'a69eead8138f909fe5b79e9f95971a82', '1a2b3c', NULL, '2022-05-14 16:50:42', NULL, 1),
    (13000000002, 'user2', 'a69eead8138f909fe5b79e9f95971a82', '1a2b3c', NULL, '2022-05-14 16:50:42', NULL, 1);
