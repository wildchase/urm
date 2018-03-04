/*
Navicat MySQL Data Transfer

Source Server         : ali.panly.me-test_busi
Source Server Version : 50639
Source Host           : ali.panly.me:3306
Source Database       : test_busi

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-03-04 15:08:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for test_order
-- ----------------------------
DROP TABLE IF EXISTS `test_order`;
CREATE TABLE `test_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_type` varchar(3) DEFAULT NULL,
  `order_area_id` varchar(3) DEFAULT NULL,
  `seller_id` bigint(20) DEFAULT NULL,
  `mall_id` bigint(20) DEFAULT NULL,
  `buyer_id` bigint(20) DEFAULT NULL,
  `order_amount` decimal(10,0) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test_order
-- ----------------------------
INSERT INTO `test_order` VALUES ('1', '1', '1', '10001', '1', '2001', '111', '2017-01-01 00:00:00');
INSERT INTO `test_order` VALUES ('2', '1', '2', '10001', '2', '2001', '1123', '2017-12-01 00:00:00');
INSERT INTO `test_order` VALUES ('3', '1', '1', '10001', '1', '2001', '321321', '2017-01-01 00:00:00');
INSERT INTO `test_order` VALUES ('5', '2', '2', '10002', '1', '2001', '77', '2017-05-05 00:00:00');
INSERT INTO `test_order` VALUES ('6', '1', '1', '10001', '1', '2001', '111', '2017-01-01 00:00:00');
INSERT INTO `test_order` VALUES ('7', '1', '2', '10001', '2', '2001', '1123', '2017-12-01 00:00:00');
INSERT INTO `test_order` VALUES ('8', '1', '1', '10001', '1', '2001', '111', '2017-01-01 00:00:00');
INSERT INTO `test_order` VALUES ('9', '1', '2', '10001', '2', '2001', '1123', '2017-12-01 00:00:00');
INSERT INTO `test_order` VALUES ('10', '2', '2', '10002', '1', '2001', '77', '2017-05-05 00:00:00');
INSERT INTO `test_order` VALUES ('11', '1', '1', '10001', '1', '2001', '111', '2017-01-01 00:00:00');
INSERT INTO `test_order` VALUES ('12', '1', '2', '10001', '2', '2001', '1123', '2017-12-01 00:00:00');
INSERT INTO `test_order` VALUES ('13', '1', '1', '10001', '1', '2001', '111', '2017-01-01 00:00:00');
INSERT INTO `test_order` VALUES ('14', '1', '2', '10001', '2', '2001', '1123', '2017-12-01 00:00:00');
INSERT INTO `test_order` VALUES ('15', '2', '2', '10002', '1', '2001', '77', '2017-05-05 00:00:00');
INSERT INTO `test_order` VALUES ('16', '1', '1', '10001', '1', '2001', '111', '2017-01-01 00:00:00');
INSERT INTO `test_order` VALUES ('17', '1', '2', '10001', '2', '2001', '1123', '2017-12-01 00:00:00');
INSERT INTO `test_order` VALUES ('18', '1', '1', '10001', '1', '2001', '111', '2017-01-01 00:00:00');
INSERT INTO `test_order` VALUES ('19', '1', '2', '10001', '2', '2001', '1123', '2017-12-01 00:00:00');
INSERT INTO `test_order` VALUES ('20', '2', '2', '10002', '1', '2001', '555', '2017-05-05 00:00:00');
