/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : ers

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 02/07/2025 17:57:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL COMMENT 'id',
  `adusername` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员账号',
  `adpwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员密码',
  `adcreatetime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '88888888', '2025-04-16 22:29:13');
INSERT INTO `admin` VALUES (10, 'admin123', '123456', '2025-04-18 22:00:23');
INSERT INTO `admin` VALUES (831, 'admin123456', '123456', '2025-04-18 22:04:52');
INSERT INTO `admin` VALUES (943, 'adminDemo', '88888888', '2025-04-20 13:02:29');

-- ----------------------------
-- Table structure for applyrecord
-- ----------------------------
DROP TABLE IF EXISTS `applyrecord`;
CREATE TABLE `applyrecord`  (
  `anumbering` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登记记录编号由（uid+applisubtime）用户id+申请提交时间组成',
  `aname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请人姓名',
  `asex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请人性别',
  `aidnumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请人身份证号',
  `aphone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请人手机号',
  `acar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请人车牌号（选填）',
  `aentertime` datetime NOT NULL COMMENT '预约进校时间',
  `aouttime` datetime NOT NULL COMMENT '预约离校时间',
  `adesc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '进校事由',
  `astatus` int NOT NULL COMMENT '0:待审批；1:已通过；2:已拒绝；3:已取消；4:已爽约',
  `applisubtime` datetime NOT NULL COMMENT '申请提交时间',
  PRIMARY KEY (`anumbering`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of applyrecord
-- ----------------------------
INSERT INTO `applyrecord` VALUES ('1', '1', '1', '1', '1', '1', '2025-04-18 16:59:40', '2025-04-18 16:59:43', '1', 4, '2025-04-18 16:59:49');
INSERT INTO `applyrecord` VALUES ('120250419105801', '刘启', '男', '511724200000005555', '13980190000', '', '2025-04-19 17:40:00', '2025-04-19 18:57:00', '测试用的案例', 4, '2025-04-19 10:58:01');
INSERT INTO `applyrecord` VALUES ('120250419111731', '刘启', '男', '511724200000000000', '13980190000', '', '2025-04-20 13:00:00', '2025-04-26 19:17:00', '测试数据一', 4, '2025-04-19 11:17:31');
INSERT INTO `applyrecord` VALUES ('120250419111750', '刘启', '男', '511724200000000000', '13980190000', '', '2025-04-27 19:17:00', '2025-04-28 19:17:00', '测试数据二', 3, '2025-04-19 11:17:51');
INSERT INTO `applyrecord` VALUES ('120250419111813', '刘启', '男', '511724200000000000', '13980190000', '', '2025-04-28 19:18:00', '2025-04-30 19:18:00', '测试数据三', 3, '2025-04-19 11:18:13');
INSERT INTO `applyrecord` VALUES ('120250419114855', '刘启', '男', '511724200000000000', '13980190000', '', '2025-04-20 19:48:00', '2025-04-22 19:48:00', '测试待审核显示是否正常', 4, '2025-04-19 11:48:56');
INSERT INTO `applyrecord` VALUES ('120250419120204', '刘启', '男', '511724200000000000', '13980190000', '', '2025-06-19 20:01:00', '2025-06-19 20:01:00', '测试取消申请功能是否实现', 3, '2025-04-19 12:02:04');
INSERT INTO `applyrecord` VALUES ('120250419133801', '刘启', '男', '511724200000000000', '13980190000', '', '2025-04-19 21:37:00', '2025-04-21 21:37:00', '测试一下这个', 3, '2025-04-19 13:38:01');
INSERT INTO `applyrecord` VALUES ('120250419135437', '刘启', '男', '511724200000000000', '13980190000', '', '2025-04-19 21:54:00', '2025-04-20 00:54:00', '测试签到功能是否正常', 4, '2025-04-19 13:54:38');
INSERT INTO `applyrecord` VALUES ('120250419135559', '刘启', '男', '511724200000000000', '13980190000', '', '2025-04-19 08:55:00', '2025-04-20 03:55:00', '测试一下这个签到功能正常不', 3, '2025-04-19 13:55:59');
INSERT INTO `applyrecord` VALUES ('120250419141600', '刘启', '男', '511724200000000000', '13980190000', '', '2025-04-19 22:15:00', '2025-04-26 22:15:00', '测试前端开始', 3, '2025-04-19 14:16:01');
INSERT INTO `applyrecord` VALUES ('120250419193554', '刘启', '男', '511724200000000000', '13980190000', '', '2025-06-01 08:00:00', '2025-06-02 08:00:00', '测试实现选择的数值是否有时区差别', 0, '2025-04-19 19:35:54');
INSERT INTO `applyrecord` VALUES ('120250419231048', '刘启', '男', '511724200000000000', '13980190000', '', '2025-04-19 23:01:00', '2025-04-20 23:01:00', '测试测试测试', 0, '2025-04-19 23:10:49');
INSERT INTO `applyrecord` VALUES ('120250517230343', '刘启', '男', '511724200000001234', '13980190000', '', '2025-05-17 23:00:00', '2025-05-18 23:03:00', '测试当前功能', 4, '2025-05-17 23:03:43');
INSERT INTO `applyrecord` VALUES ('120250604194836', '刘启', '男', '511724200000001234', '13980190000', '', '2025-06-04 19:46:00', '2025-06-05 19:48:00', '测试签到功能是否正常运行', 4, '2025-06-04 19:48:36');
INSERT INTO `applyrecord` VALUES ('ap00120250416223727', '刘启', '男', '511724200000004100', '1391234567', '川S65655', '2025-04-20 22:39:21', '2025-04-21 22:40:05', '回学校参加毕业答辩', 4, '2025-04-16 22:37:27');
INSERT INTO `applyrecord` VALUES ('ap00220250413224059', '陈某某', '女', '511724200000000000', '13900000000', NULL, '2025-04-14 22:42:18', '2025-04-15 22:42:28', '回学校玩', 0, '2025-04-13 22:40:59');

-- ----------------------------
-- Table structure for noshow
-- ----------------------------
DROP TABLE IF EXISTS `noshow`;
CREATE TABLE `noshow`  (
  `nid` int NOT NULL AUTO_INCREMENT COMMENT '爽约表编号',
  `nname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '爽约人',
  `ndatetime` datetime NOT NULL COMMENT '爽约的时间',
  `nanumbering` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '爽约记录的编号（关联apply表anumbering）',
  PRIMARY KEY (`nid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of noshow
-- ----------------------------
INSERT INTO `noshow` VALUES (1, '陈某某', '2025-04-16 22:43:36', 'ap00220250413224059');
INSERT INTO `noshow` VALUES (2, '1', '2025-04-19 18:10:00', '1');
INSERT INTO `noshow` VALUES (3, '刘启', '2025-04-19 18:14:00', '120250419105801');
INSERT INTO `noshow` VALUES (4, '刘启', '2025-04-22 08:45:00', '120250419111731');
INSERT INTO `noshow` VALUES (5, '刘启', '2025-05-17 23:31:00', '120250517230343');
INSERT INTO `noshow` VALUES (6, '刘启', '2025-05-17 23:35:00', '120250419135437');
INSERT INTO `noshow` VALUES (7, '刘启铭', '2025-06-04 20:17:00', '120250604194836');

-- ----------------------------
-- Table structure for opentime
-- ----------------------------
DROP TABLE IF EXISTS `opentime`;
CREATE TABLE `opentime`  (
  `oid` int NOT NULL AUTO_INCREMENT COMMENT '数据编号',
  `oname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '时间名称',
  `ostartdatetime` datetime NOT NULL COMMENT '开始时间',
  `oenddatetime` datetime NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`oid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of opentime
-- ----------------------------
INSERT INTO `opentime` VALUES (1, '元旦节', '2025-01-01 00:00:00', '2025-01-03 00:00:00');
INSERT INTO `opentime` VALUES (2, '劳动节', '2025-05-01 00:00:00', '2025-05-06 00:00:00');
INSERT INTO `opentime` VALUES (3, '测试', '2025-04-14 00:11:00', '2025-04-17 01:11:00');
INSERT INTO `opentime` VALUES (4, '测试', '2025-04-28 13:04:00', '2025-04-29 14:04:00');

-- ----------------------------
-- Table structure for settings
-- ----------------------------
DROP TABLE IF EXISTS `settings`;
CREATE TABLE `settings`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `late` int NOT NULL COMMENT '迟到多少分钟算爽约',
  `noshowtimes` int NOT NULL COMMENT '爽约多少次进入黑名单',
  `limitdays` int NOT NULL COMMENT '黑名单保持多少天',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of settings
-- ----------------------------
INSERT INTO `settings` VALUES (1, 30, 4, 30);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户姓名',
  `upwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `usex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户性别',
  `uidnumber` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户身份证号',
  `uphone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户手机号',
  `upic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像路径',
  `blacklist` int NOT NULL COMMENT '0：没进黑名单；1：进黑名单',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `enblacklisttime` datetime NULL DEFAULT NULL COMMENT '进入黑名单的时间（根据条件多少天移出黑名单）',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '验证码',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '刘启', '123456789', '男', '511724200000001234', '13980190000', '/img/1111.png', 0, '2025-04-16 22:16:18', NULL, '1889');
INSERT INTO `user` VALUES (2, '陈某某', '123456', '女', '511724200000005555', '15200003321', '/img/2222.png', 1, '2025-04-15 22:17:12', '2025-04-17 18:44:34', '1111');
INSERT INTO `user` VALUES (4, '刘某某', '123456', '1', '511724200000008888', '13999999999', '/img/1111.png', 0, '2025-04-19 17:32:23', NULL, '1111');

SET FOREIGN_KEY_CHECKS = 1;
