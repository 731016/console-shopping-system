/*
 Navicat Premium Data Transfer

 Source Server         : mysql_root
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 25/09/2021 13:32:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for userrole
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole`  (
  `roleid` int(0) NOT NULL,
  `rolename` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `menulist` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`roleid`) USING BTREE,
  UNIQUE INDEX `rolename`(`rolename`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userrole
-- ----------------------------
INSERT INTO `userrole` VALUES (0, '超级管理员', '商品管理|订单管理|用户管理|商品列表|购物|个人信息管理');
INSERT INTO `userrole` VALUES (1, '商家', '商品管理|订单管理|商品列表|个人信息管理');
INSERT INTO `userrole` VALUES (2, '买家', '商品列表|查看购物车|个人信息管理');

SET FOREIGN_KEY_CHECKS = 1;
