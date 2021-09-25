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

 Date: 25/09/2021 13:29:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for commoditytypes
-- ----------------------------
DROP TABLE IF EXISTS `commoditytypes`;
CREATE TABLE `commoditytypes`  (
  `typeid` int(0) NOT NULL,
  `commoditypename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`typeid`) USING BTREE,
  UNIQUE INDEX `commoditypename`(`commoditypename`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commoditytypes
-- ----------------------------
INSERT INTO `commoditytypes` VALUES (1001, '数码');
INSERT INTO `commoditytypes` VALUES (1002, '服装');
INSERT INTO `commoditytypes` VALUES (1003, '洗护');

SET FOREIGN_KEY_CHECKS = 1;
