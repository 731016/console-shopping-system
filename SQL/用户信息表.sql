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

 Date: 25/09/2021 13:31:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for userinformation
-- ----------------------------
DROP TABLE IF EXISTS `userinformation`;
CREATE TABLE `userinformation`  (
  `user` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uname` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `roleid` int(0) NOT NULL,
  `status` int(0) NOT NULL,
  PRIMARY KEY (`user`) USING BTREE,
  UNIQUE INDEX `uname`(`uname`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinformation
-- ----------------------------
INSERT INTO `userinformation` VALUES ('admin1', '123456', '苹果', 1, 1);
INSERT INTO `userinformation` VALUES ('admin2', '123456', '鸿星尔克', 1, 1);
INSERT INTO `userinformation` VALUES ('admin3', '123456', '妮维雅', 1, 1);
INSERT INTO `userinformation` VALUES ('bi', '123', '哔哩', 2, 1);
INSERT INTO `userinformation` VALUES ('buyer1', '123', '张三', 2, 1);
INSERT INTO `userinformation` VALUES ('buyer2', '123', '李四', 2, 1);
INSERT INTO `userinformation` VALUES ('buyer3', '123', '王五', 2, 1);
INSERT INTO `userinformation` VALUES ('root', 'root', '马化腾', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
