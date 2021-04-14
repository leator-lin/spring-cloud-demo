/*
Navicat MySQL Data Transfer

Source Server         : future
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : spring-cloud-demo

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2021-04-14 16:00:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_db
-- ----------------------------
DROP TABLE IF EXISTS `t_db`;
CREATE TABLE `t_db` (
  `db_id` int(11) NOT NULL,
  `db_name` varchar(20) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `hash_value` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`db_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_db
-- ----------------------------
INSERT INTO `t_db` VALUES ('1', 'g01_db_0', '1', '0,1,2,3');
INSERT INTO `t_db` VALUES ('2', 'g01_db_1', '1', '4,5,6');
INSERT INTO `t_db` VALUES ('3', 'g01_db_2', '1', '7,8,9');
INSERT INTO `t_db` VALUES ('4', 'g02_db_0', '2', '0,1,2');
INSERT INTO `t_db` VALUES ('5', 'g02_db_1', '2', '3,4,5');
INSERT INTO `t_db` VALUES ('6', 'g02_db_2', '2', '6,7,8');

-- ----------------------------
-- Table structure for t_db_group
-- ----------------------------
DROP TABLE IF EXISTS `t_db_group`;
CREATE TABLE `t_db_group` (
  `group_id` int(11) NOT NULL,
  `group_name` varchar(20) DEFAULT NULL,
  `start_id` int(11) DEFAULT NULL,
  `end_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_db_group
-- ----------------------------
INSERT INTO `t_db_group` VALUES ('1', 'group01', '0', '4000');
INSERT INTO `t_db_group` VALUES ('2', 'group02', '4000', '8000');

-- ----------------------------
-- Table structure for t_db_table
-- ----------------------------
DROP TABLE IF EXISTS `t_db_table`;
CREATE TABLE `t_db_table` (
  `table_id` int(11) NOT NULL,
  `table_name` varchar(20) DEFAULT NULL,
  `db_id` int(11) DEFAULT NULL,
  `start_id` int(11) DEFAULT NULL,
  `end_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_db_table
-- ----------------------------
INSERT INTO `t_db_table` VALUES ('1', 'table0', '1', '0', '10000000');
INSERT INTO `t_db_table` VALUES ('2', 'table1', '1', '10000000', '20000000');
INSERT INTO `t_db_table` VALUES ('3', 'table2', '1', '20000000', '30000000');
INSERT INTO `t_db_table` VALUES ('4', 'table3', '1', '30000000', '40000000');
INSERT INTO `t_db_table` VALUES ('5', 'table4', '2', '0', '10000000');
INSERT INTO `t_db_table` VALUES ('6', 'table5', '2', '10000000', '25000000');
INSERT INTO `t_db_table` VALUES ('7', 'table6', '2', '25000000', '40000000');
INSERT INTO `t_db_table` VALUES ('8', 'table7', '3', '0', '10000000');
INSERT INTO `t_db_table` VALUES ('9', 'table8', '3', '10000000', '25000000');
INSERT INTO `t_db_table` VALUES ('10', 'table9', '3', '25000000', '40000000');
INSERT INTO `t_db_table` VALUES ('11', 'table10', '4', '40000000', '55000000');
INSERT INTO `t_db_table` VALUES ('12', 'table11', '4', '55000000', '70000000');
INSERT INTO `t_db_table` VALUES ('13', 'table12', '4', '70000000', '80000000');
INSERT INTO `t_db_table` VALUES ('14', 'table13', '5', '40000000', '55000000');
INSERT INTO `t_db_table` VALUES ('15', 'table14', '5', '55000000', '70000000');
INSERT INTO `t_db_table` VALUES ('16', 'table15', '5', '70000000', '80000000');
INSERT INTO `t_db_table` VALUES ('17', 'table16', '6', '40000000', '55000000');
INSERT INTO `t_db_table` VALUES ('18', 'table17', '6', '55000000', '70000000');
INSERT INTO `t_db_table` VALUES ('19', 'table18', '6', '70000000', '80000000');
