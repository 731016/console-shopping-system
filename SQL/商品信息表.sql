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

 Date: 25/09/2021 13:29:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for commodityinformation
-- ----------------------------
DROP TABLE IF EXISTS `commodityinformation`;
CREATE TABLE `commodityinformation`  (
  `commodityid` int(0) NOT NULL AUTO_INCREMENT,
  `commodityname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `typeid` int(0) NOT NULL,
  `brand` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` double(12, 2) NOT NULL,
  `productiondate` date NOT NULL,
  `expirationdate` date NOT NULL,
  PRIMARY KEY (`commodityid`) USING BTREE,
  UNIQUE INDEX `commodityname`(`commodityname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of commodityinformation
-- ----------------------------
INSERT INTO `commodityinformation` VALUES (1, 'iPhoneX', 1001, '苹果', 8900.00, '2020-09-19', '2120-09-19');
INSERT INTO `commodityinformation` VALUES (2, 'iPad2021', 1001, '苹果', 2890.00, '2021-06-21', '2111-09-19');
INSERT INTO `commodityinformation` VALUES (3, 'AirPods', 1001, '苹果', 1200.00, '2019-09-19', '2041-09-19');
INSERT INTO `commodityinformation` VALUES (4, 't恤', 1002, '鸿星尔克', 120.50, '2021-07-21', '2031-09-19');
INSERT INTO `commodityinformation` VALUES (5, '黑裤子', 1002, '鸿星尔克', 200.00, '2021-07-11', '2031-09-19');
INSERT INTO `commodityinformation` VALUES (6, '鞋子', 1002, '鸿星尔克', 230.00, '2021-07-01', '2031-09-19');
INSERT INTO `commodityinformation` VALUES (7, '洗面奶', 1001, '妮维雅', 8900.00, '2021-06-21', '2022-09-19');
INSERT INTO `commodityinformation` VALUES (8, '护发素', 1001, '妮维雅', 8900.00, '2021-06-21', '2022-09-19');
INSERT INTO `commodityinformation` VALUES (9, '眼霜', 1001, '妮维雅', 8900.00, '2021-06-21', '2022-09-19');

SET FOREIGN_KEY_CHECKS = 1;
