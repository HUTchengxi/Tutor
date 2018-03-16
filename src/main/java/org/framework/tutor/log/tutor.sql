/*
Navicat MySQL Data Transfer

Source Server         : cxmysql
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : tutor

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-03-16 22:46:47
*/

CREATE DATABASE TUTOR;
USE TUTOR;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `course_chapter`
-- ----------------------------
DROP TABLE IF EXISTS `course_chapter`;
CREATE TABLE `course_chapter` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `cid` int(11) NOT NULL COMMENT '课程id',
  `ord` int(11) NOT NULL COMMENT '课程章节号',
  `title` varchar(50) NOT NULL COMMENT '课程章节标题',
  `descript` varchar(500) NOT NULL COMMENT '课程章节概要',
  PRIMARY KEY (`id`,`cid`),
  KEY `cid` (`cid`),
  CONSTRAINT `course_chapter_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `course_main` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_chapter
-- ----------------------------
INSERT INTO `course_chapter` VALUES ('1', '3', '1', '目录1', '是多少啊嗲是的吧骚i到死电脑i俺倒是你都啊是电脑上电脑sand爬山的哪怕是的阿斯顿那时的哪怕是的');
INSERT INTO `course_chapter` VALUES ('2', '3', '2', '目录2', '是多少啊嗲是的吧骚i到死电脑i俺倒是你都啊是电脑上电脑sand爬山的哪怕是的阿斯顿那时的哪怕是的');
INSERT INTO `course_chapter` VALUES ('3', '3', '3', '目录3', '是多少啊嗲是的吧骚i到死电脑i俺倒是你都啊是电脑上电脑sand爬山的哪怕是的阿斯顿那时的哪怕是的');
INSERT INTO `course_chapter` VALUES ('4', '3', '4', '目录4', '是多少啊嗲是的吧骚i到死电脑i俺倒是你都啊是电脑上电脑sand爬山的哪怕是的阿斯顿那时的哪怕是的');
INSERT INTO `course_chapter` VALUES ('5', '3', '5', '目录5', '是多少啊嗲是的吧骚i到死电脑i俺倒是你都啊是电脑上电脑sand爬山的哪怕是的阿斯顿那时的哪怕是的');

-- ----------------------------
-- Table structure for `course_collect`
-- ----------------------------
DROP TABLE IF EXISTS `course_collect`;
CREATE TABLE `course_collect` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `cid` int(11) NOT NULL COMMENT '收藏的课程的id',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `coltime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '收藏的时间',
  `descript` varchar(50) DEFAULT NULL COMMENT '收藏笔记',
  PRIMARY KEY (`cid`,`username`),
  KEY `id` (`id`),
  CONSTRAINT `course_collect_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `course_main` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_collect
-- ----------------------------

-- ----------------------------
-- Table structure for `course_command`
-- ----------------------------
DROP TABLE IF EXISTS `course_command`;
CREATE TABLE `course_command` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `cid` int(11) NOT NULL COMMENT '课程id',
  `username` varchar(20) NOT NULL COMMENT '评论用户',
  `info` varchar(500) NOT NULL COMMENT '评论内容',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `score` int(11) DEFAULT '5' COMMENT '评价星级，默认为5，1-5',
  `god` int(11) DEFAULT '0' COMMENT '1为家教指定神评',
  PRIMARY KEY (`cid`,`username`,`id`),
  KEY `id` (`id`),
  KEY `username` (`username`),
  CONSTRAINT `course_command_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `course_main` (`id`),
  CONSTRAINT `course_command_ibfk_2` FOREIGN KEY (`username`) REFERENCES `user_main` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_command
-- ----------------------------
INSERT INTO `course_command` VALUES ('5', '2', 'chengxi', '说点什么...1', '2018-02-26 11:37:34', '5', '0');

-- ----------------------------
-- Table structure for `course_log`
-- ----------------------------
DROP TABLE IF EXISTS `course_log`;
CREATE TABLE `course_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `cid` int(11) DEFAULT NULL COMMENT '对应的课程id',
  `username` varchar(20) NOT NULL,
  `ctype` varchar(20) NOT NULL COMMENT '课程类型',
  `cname` varchar(50) NOT NULL COMMENT '课程名称',
  `logtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '浏览时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_log
-- ----------------------------
INSERT INTO `course_log` VALUES ('1', '1', 'chengxi', '小学数学', '一元一次方程从入门到精通', '2018-02-05 23:57:22');

-- ----------------------------
-- Table structure for `course_main`
-- ----------------------------
DROP TABLE IF EXISTS `course_main`;
CREATE TABLE `course_main` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `username` varchar(20) NOT NULL COMMENT '家教老师名',
  `name` varchar(50) NOT NULL COMMENT '课程名称',
  `imgsrc` varchar(50) NOT NULL COMMENT '课程封面图片',
  `stype` int(11) DEFAULT '4' COMMENT '主类别：1小学，2初中，3高中，4其他兴趣',
  `ctype` varchar(20) NOT NULL COMMENT '类别名称',
  `jcount` int(11) NOT NULL COMMENT '课程总人数',
  `hcount` int(11) NOT NULL COMMENT '课程点击量',
  `ccount` int(11) NOT NULL COMMENT '课程评论量',
  `descript` varchar(500) NOT NULL COMMENT '课程描述信息',
  `price` decimal(6,1) NOT NULL COMMENT '课程报名费用',
  `ptime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '课程发布时间',
  `stime` datetime NOT NULL COMMENT '课程开课时间',
  `total` int(11) NOT NULL COMMENT '课程总天数',
  PRIMARY KEY (`username`,`id`),
  KEY `id` (`id`),
  CONSTRAINT `course_main_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_main` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_main
-- ----------------------------
INSERT INTO `course_main` VALUES ('1', 'chengxi', '小学数学从入门到精通', '/images/user/course/math.png', '1', '数学', '20', '100', '20', '小学数学，虽然简单，但是确实入门的一个砍，相信自己，你能行，我带你飞', '99.0', '2018-02-09 00:56:59', '2018-02-14 15:30:00', '90');
INSERT INTO `course_main` VALUES ('2', 'chengxi', '小学语文作文入门', '/images/user/course/chinese.jpg', '1', '语文', '10', '90', '40', '哦大家哦都怕惊动菩萨的骄傲拍的骄傲频道啊骄傲PDA金沙岛岛上都评价啊大姐啊收到就送牌的撒娇盘点十大', '199.0', '2018-02-09 14:15:49', '2018-03-01 10:30:00', '88');
INSERT INTO `course_main` VALUES ('3', 'chengxi', '小学英语入门', '/images/user/course/timg.jpg', '1', '英语', '11', '40', '30', '当年是电脑死大赛都是你打死都i拿到年送到', '99.0', '2018-02-09 15:30:39', '2018-02-14 08:30:00', '79');
INSERT INTO `course_main` VALUES ('4', 'chengxi', '小学生物入门', '/images/user/course/math.png', '1', '生物', '10', '120', '90', '基数大宋帝啊是滴哦三第哦啊书都i阿斯顿哦俺都i阿斯顿那是暗示都i撒旦你', '129.0', '2018-02-09 15:31:51', '2018-02-10 10:30:00', '86');
INSERT INTO `course_main` VALUES ('5', 'chengxi', '小学画画入门', '/images/user/course/math.png', '1', '画画', '20', '110', '40', '哦i打睡觉哦大数据哦都帕金斯都是多久哦啊速度加啊四的啊送的评价啊山坡多久啊送到', '59.0', '2018-02-09 15:32:47', '2019-03-01 10:00:00', '83');
INSERT INTO `course_main` VALUES ('6', 'chengxi', '小学地理入门', '/images/user/course/math.png', '1', '地理', '40', '130', '80', '的还动手嗲和第送达后i啊晒哦多少ida', '69.0', '2018-02-09 15:33:45', '2018-02-19 10:00:00', '82');
INSERT INTO `course_main` VALUES ('7', 'chengxi', '小学计算机精通', '/images/user/course/math.png', '1', '数学', '33', '100', '79', '萨尼哦你哦i按上述的啊你说的', '99.0', '2018-03-09 17:11:08', '2018-04-01 10:30:00', '100');
INSERT INTO `course_main` VALUES ('8', 'chengxi', '奶哦都施耐德', '/images/user/course/math.png', '1', '数学', '29', '29', '29', '啊ions发是年底哦昂是年底', '19129.0', '2018-03-10 14:24:56', '2018-04-01 01:00:00', '12');
INSERT INTO `course_main` VALUES ('9', 'chengxi', '队内赛哦打你哦', '/images/user/course/math.png', '1', '数学', '19', '18', '28', '电脑i当年', '49.0', '2018-03-10 14:25:36', '2018-02-03 08:10:00', '19');
INSERT INTO `course_main` VALUES ('10', 'chengxi', '乃是电脑', '/images/user/course/math.png', '1', '数学', '28', '49', '18', '弄爱上你到', '28.0', '2018-03-10 14:26:17', '2018-05-01 10:00:00', '39');

-- ----------------------------
-- Table structure for `course_order`
-- ----------------------------
DROP TABLE IF EXISTS `course_order`;
CREATE TABLE `course_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `cid` int(11) NOT NULL COMMENT '课程id',
  `username` varchar(20) NOT NULL COMMENT '用户',
  `state` int(11) NOT NULL COMMENT '订单状态，0购物车，1已订购，2未支付，3已失效',
  `otime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '订单更新时间',
  PRIMARY KEY (`cid`,`username`),
  KEY `id` (`id`),
  KEY `username` (`username`),
  CONSTRAINT `course_order_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_main` (`username`),
  CONSTRAINT `course_order_ibfk_2` FOREIGN KEY (`cid`) REFERENCES `course_main` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_order
-- ----------------------------
INSERT INTO `course_order` VALUES ('3', '2', 'chengxi', '0', '2018-03-09 16:09:22');

-- ----------------------------
-- Table structure for `course_treply`
-- ----------------------------
DROP TABLE IF EXISTS `course_treply`;
CREATE TABLE `course_treply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `cid` int(11) NOT NULL COMMENT '课程id',
  `cmid` int(11) NOT NULL COMMENT '对应的评价id',
  `tname` varchar(20) NOT NULL COMMENT '家教老师用户名',
  `info` varchar(500) NOT NULL COMMENT '回复内容',
  PRIMARY KEY (`cid`,`tname`,`cmid`),
  KEY `id` (`id`),
  KEY `tname` (`tname`),
  KEY `cmid` (`cmid`),
  CONSTRAINT `course_treply_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `course_main` (`id`),
  CONSTRAINT `course_treply_ibfk_2` FOREIGN KEY (`tname`) REFERENCES `user_main` (`username`),
  CONSTRAINT `course_treply_ibfk_3` FOREIGN KEY (`cmid`) REFERENCES `course_command` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_treply
-- ----------------------------

-- ----------------------------
-- Table structure for `user_log`
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `logtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '登录的时间',
  `logcity` varchar(100) NOT NULL COMMENT '登录的城市',
  `logip` varchar(15) NOT NULL COMMENT '登录的ip地址',
  `logsys` varchar(10) NOT NULL COMMENT '电脑的操作系统',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_log
-- ----------------------------
INSERT INTO `user_log` VALUES ('2', 'chengxi', '2018-02-05 00:28:22', '未知地区', '220.202.152.37', 'Windows');
INSERT INTO `user_log` VALUES ('3', 'chengxi', '2018-02-05 01:31:27', '未知地区', '220.202.152.37', 'Windows');
INSERT INTO `user_log` VALUES ('4', 'chengxi', '2018-02-05 11:08:53', '未知地区', '119.39.248.124', 'Windows');
INSERT INTO `user_log` VALUES ('5', 'chengxi', '2018-02-05 23:43:34', '未知地区', '183.214.168.196', 'Windows');
INSERT INTO `user_log` VALUES ('6', 'chengxi', '2018-02-05 23:49:36', '未知地区', '183.214.168.196', 'Windows');
INSERT INTO `user_log` VALUES ('7', 'chengxi', '2018-02-07 00:03:10', '未知地区', '183.214.169.251', 'Windows');
INSERT INTO `user_log` VALUES ('8', 'chengxi', '2018-02-07 13:45:48', '未知地区', '183.214.29.251', 'Windows');
INSERT INTO `user_log` VALUES ('9', 'CHENGXI', '2018-02-07 23:29:12', '未知地区', '183.214.169.251', 'Windows');
INSERT INTO `user_log` VALUES ('10', 'chengxi', '2018-02-08 00:47:59', '未知地区', '183.214.169.251', 'Windows');
INSERT INTO `user_log` VALUES ('11', 'chengxi', '2018-02-08 02:00:58', '未知地区', '183.214.169.251', 'Windows');
INSERT INTO `user_log` VALUES ('12', 'chengxi', '2018-02-08 09:21:23', '未知地区', '183.214.169.251', 'Windows');
INSERT INTO `user_log` VALUES ('13', 'chengxi', '2018-02-08 09:37:52', '未知地区', '183.214.169.251', 'Windows');
INSERT INTO `user_log` VALUES ('14', 'chengxi', '2018-02-08 20:03:15', '未知地区', '43.250.201.71', 'Windows');
INSERT INTO `user_log` VALUES ('15', 'chengxi', '2018-02-08 21:42:29', '未知地区', '43.250.201.71', 'Windows');
INSERT INTO `user_log` VALUES ('16', 'chengxi', '2018-02-08 22:31:52', '未知地区', '43.250.201.71', 'Windows');
INSERT INTO `user_log` VALUES ('17', 'chengxi', '2018-02-08 22:38:25', '未知地区', '43.250.201.71', 'Windows');
INSERT INTO `user_log` VALUES ('18', 'chengxi', '2018-02-11 00:17:07', '未知地区', '183.214.29.251', 'Windows');
INSERT INTO `user_log` VALUES ('19', 'chengxi', '2018-02-11 12:44:13', '未知地区', '183.214.169.251', 'Windows');
INSERT INTO `user_log` VALUES ('20', 'chengxi', '2018-02-13 13:44:35', '未知地区', '220.202.152.37', 'Windows');
INSERT INTO `user_log` VALUES ('21', 'chengxi', '2018-02-13 22:23:16', '未知地区', '119.39.248.110', 'Windows');
INSERT INTO `user_log` VALUES ('22', 'chengxi', '2018-02-14 00:13:35', '未知地区', '119.39.248.110', 'Windows');
INSERT INTO `user_log` VALUES ('23', 'chengxi', '2018-02-15 13:15:01', '未知地区', '43.250.201.1', 'Windows');
INSERT INTO `user_log` VALUES ('24', 'chengxi', '2018-02-16 12:56:06', '未知地区', '43.250.201.38', 'Windows');
INSERT INTO `user_log` VALUES ('25', 'chengxi', '2018-02-16 22:51:57', '未知地区', '220.202.152.36', 'Windows');
INSERT INTO `user_log` VALUES ('26', 'chengxi', '2018-02-16 23:20:01', '未知地区', '220.202.152.36', 'Windows');
INSERT INTO `user_log` VALUES ('27', 'chengxi', '2018-02-18 00:31:20', '未知地区', '119.39.248.61', 'Windows');
INSERT INTO `user_log` VALUES ('28', 'chengxi', '2018-02-25 18:53:39', '未知地区', '43.250.201.63', 'Windows');
INSERT INTO `user_log` VALUES ('29', 'chengxi', '2018-02-25 20:26:52', '未知地区', '43.250.201.63', 'Windows');
INSERT INTO `user_log` VALUES ('30', 'chengxi', '2018-03-04 09:46:03', '未知地区', '116.31.92.195', 'Windows');
INSERT INTO `user_log` VALUES ('31', 'chengxi', '2018-03-05 08:43:25', '未知地区', '116.31.92.195', 'Windows');
INSERT INTO `user_log` VALUES ('32', 'chengxi', '2018-03-05 15:03:23', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('33', 'chengxi', '2018-03-06 12:26:02', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('34', 'chengxi', '2018-03-06 13:36:12', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('35', 'chengxi', '2018-03-06 15:15:58', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('36', 'chengxi', '2018-03-06 18:34:06', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('37', 'chengxi', '2018-03-07 08:49:17', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('38', 'chengxi', '2018-03-07 12:07:04', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('39', 'chengxi', '2018-03-07 18:42:38', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('40', 'chengxi', '2018-03-07 19:55:50', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('41', 'chengxi', '2018-03-08 09:22:39', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('42', 'chengxi', '2018-03-08 15:12:00', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('43', 'chengxi', '2018-03-08 17:06:43', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('44', 'chengxi', '2018-03-08 19:31:11', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('45', 'chengxi', '2018-03-08 19:35:19', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('46', 'chengxi', '2018-03-08 19:36:16', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('47', 'chengxi', '2018-03-08 19:36:23', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('48', 'chengxi', '2018-03-08 19:36:31', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('49', 'chengxi', '2018-03-08 19:39:39', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('50', 'chengxi', '2018-03-08 19:42:16', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('51', 'chengxi', '2018-03-08 19:43:02', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('52', 'chengxi', '2018-03-08 19:43:47', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('53', 'chengxi', '2018-03-08 19:43:52', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('54', 'chengxi', '2018-03-08 19:44:06', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('55', 'chengxi', '2018-03-08 19:46:17', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('56', 'chengxi', '2018-03-08 19:46:29', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('57', 'chengxi', '2018-03-08 19:48:07', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('58', 'chengxi', '2018-03-08 19:48:47', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('59', 'chengxi', '2018-03-08 19:50:17', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('60', 'chengxi', '2018-03-08 19:51:55', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('61', 'chengxi', '2018-03-08 19:52:06', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('62', 'chengxi', '2018-03-09 08:59:03', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('63', 'chengxi', '2018-03-09 08:59:17', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('64', 'chengxi', '2018-03-09 08:59:55', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('65', 'chengxi', '2018-03-09 09:05:39', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('66', 'chengxi', '2018-03-09 09:08:06', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('67', 'chengxi', '2018-03-09 09:11:57', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('68', 'chengxi', '2018-03-09 09:12:38', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('69', 'chengxi', '2018-03-09 09:36:04', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('70', 'chengxi', '2018-03-09 09:37:50', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('71', 'chengxi', '2018-03-09 09:44:42', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('72', 'chengxi', '2018-03-09 09:47:09', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('73', 'chengxi', '2018-03-09 09:47:42', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('74', 'chengxi', '2018-03-09 09:49:29', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('75', 'chengxi', '2018-03-09 09:50:03', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('76', 'chengxi', '2018-03-09 09:50:22', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('77', 'chengxi', '2018-03-09 09:50:56', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('78', 'chengxi', '2018-03-09 09:51:10', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('79', 'chengxi', '2018-03-09 09:51:27', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('80', 'chengxi', '2018-03-09 09:51:49', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('81', 'chengxi', '2018-03-09 09:52:15', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('82', 'chengxi', '2018-03-09 09:52:39', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('83', 'chengxi', '2018-03-09 09:53:03', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('84', 'chengxi', '2018-03-09 09:53:11', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('85', 'chengxi', '2018-03-09 09:53:23', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('86', 'chengxi', '2018-03-09 09:53:36', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('87', 'chengxi', '2018-03-09 09:53:51', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('88', 'chengxi', '2018-03-09 09:55:29', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('89', 'chengxi', '2018-03-09 09:55:37', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('90', 'chengxi', '2018-03-09 09:56:06', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('91', 'chengxi', '2018-03-09 09:56:15', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('92', 'chengxi', '2018-03-09 09:57:18', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('93', 'chengxi', '2018-03-09 09:57:48', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('94', 'chengxi', '2018-03-09 09:58:10', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('95', 'chengxi', '2018-03-09 09:58:49', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('96', 'chengxi', '2018-03-09 15:09:35', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('97', 'chengxi', '2018-03-09 15:43:55', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('98', 'chengxi', '2018-03-10 10:07:14', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('99', 'chengxi', '2018-03-11 23:27:28', '未知地区', '113.91.86.19', 'Windows');
INSERT INTO `user_log` VALUES ('100', 'chengxi', '2018-03-12 22:15:34', '未知地区', '119.139.115.116', 'Windows');
INSERT INTO `user_log` VALUES ('101', 'chengxi', '2018-03-14 21:34:13', '未知地区', '183.16.89.93', 'Windows');
INSERT INTO `user_log` VALUES ('102', 'chengxi', '2018-03-14 22:05:55', '未知地区', '183.16.89.93', 'Windows');
INSERT INTO `user_log` VALUES ('103', 'chengxi', '2018-03-14 22:06:43', '未知地区', '183.16.89.93', 'Windows');
INSERT INTO `user_log` VALUES ('104', 'chengxi', '2018-03-15 08:55:56', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('105', 'chengxi', '2018-03-15 09:02:01', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('106', 'chengxi', '2018-03-15 09:07:37', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('107', 'chengxi', '2018-03-15 12:15:45', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('108', 'chengxi', '2018-03-15 12:30:44', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('109', 'chengxi', '2018-03-15 20:07:08', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('110', 'chengxi', '2018-03-15 21:03:36', '未知地区', '113.91.85.70', 'Windows');
INSERT INTO `user_log` VALUES ('111', 'chengxi', '2018-03-15 21:47:47', '未知地区', '113.91.85.70', 'Windows');
INSERT INTO `user_log` VALUES ('112', 'chengxi', '2018-03-15 23:01:00', '未知地区', '113.91.85.70', 'Windows');
INSERT INTO `user_log` VALUES ('113', 'chengxi', '2018-03-16 17:31:11', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('114', 'chengxi', '2018-03-16 18:39:19', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('115', 'chengxi', '2018-03-16 19:26:55', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('116', 'chengxi', '2018-03-16 19:30:34', '未知地区', '112.95.135.83', 'Windows');
INSERT INTO `user_log` VALUES ('117', 'chengxi', '2018-03-16 21:52:33', '未知地区', '113.91.86.124', 'Windows');
INSERT INTO `user_log` VALUES ('118', 'chengxi', '2018-03-16 22:26:00', '未知地区', '113.91.86.124', 'Windows');

-- ----------------------------
-- Table structure for `user_main`
-- ----------------------------
DROP TABLE IF EXISTS `user_main`;
CREATE TABLE `user_main` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识',
  `identity` int(11) DEFAULT '0' COMMENT '0普通用户，-1admin，1家教，-2咱不验证用户',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(12) NOT NULL COMMENT '密码',
  `nickname` varchar(12) NOT NULL COMMENT '昵称',
  `sex` int(11) DEFAULT '0' COMMENT '0为女，1为男',
  `age` int(11) DEFAULT '0' COMMENT '年龄',
  `telephone` char(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `info` varchar(200) DEFAULT '这位童鞋很懒，什么都没留下' COMMENT '个人描述',
  `imgsrc` varchar(50) DEFAULT 'images/default/user_face.jpg' COMMENT '用户的个人头像',
  `regtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册的时间',
  PRIMARY KEY (`username`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_main
-- ----------------------------
INSERT INTO `user_main` VALUES ('8', '-1', '11111', '111111', '勤成游客282b1c21', '0', '0', null, 'dreamyjm@163.xom', '这位童鞋很懒，什么都没留下', 'images/default/user_face.jpg', '2018-03-12 23:36:11');
INSERT INTO `user_main` VALUES ('9', '-1', '222222', '222222', '勤成游客041e51c4', '0', '0', null, '1277309556@qq.com', '这位童鞋很懒，什么都没留下', 'images/default/user_face.jpg', '2018-03-12 23:38:54');
INSERT INTO `user_main` VALUES ('1', '1', 'chengxi', 'chengxi', '成兮', '1', '21', '', 'dreamyjm@163.com', '很温柔的', '/images/default/2.jpg', '2018-02-08 22:27:36');
INSERT INTO `user_main` VALUES ('1', '0', 'yuanfen', 'yuanfen', '立命安身', '0', '10', '18274786820', null, '这位童鞋很懒，什么都没留下', 'images/default/user_face.jpg', '2018-02-26 14:18:33');

-- ----------------------------
-- Table structure for `user_message`
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `identity` int(11) DEFAULT '0' COMMENT '是否私信通知，0表示否，1表示是',
  `suser` varchar(20) NOT NULL COMMENT '发送的管理员用户名，模拟admin',
  `username` varchar(20) NOT NULL COMMENT '发送私信通知的用户名',
  `title` varchar(50) NOT NULL COMMENT '通知的标题',
  `descript` text NOT NULL COMMENT '通知的信息',
  `stime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '通知发送的时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0表示未读，1表示已读',
  `imgsrc` varchar(100) NOT NULL DEFAULT '/images/user/face/bg.png' COMMENT '发送通知的人的头像临时存放位置',
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `username` (`username`),
  CONSTRAINT `user_message_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_main` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_message
-- ----------------------------
INSERT INTO `user_message` VALUES ('1', '0', ' 王者荣耀助手', 'chengxi', '欢迎来到王者荣耀', '是迪士尼的上单你上单你圣丹尼斯但是是电脑老是看到你看圣诞快乐的岁的年龄可适当是你到时肯定能考上', '2018-03-16 17:33:07', '1', '/images/user/face/bg.png');

-- ----------------------------
-- Table structure for `user_secret`
-- ----------------------------
DROP TABLE IF EXISTS `user_secret`;
CREATE TABLE `user_secret` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `username` varchar(20) NOT NULL COMMENT '对用用户名',
  `question` varchar(20) NOT NULL COMMENT '密保问题，不超过20个字',
  `answer` varchar(20) NOT NULL COMMENT '密保答案，不超过20个字',
  `msg` varchar(20) DEFAULT '无' COMMENT '提示信息，暂时不用',
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  CONSTRAINT `user_secret_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_main` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_secret
-- ----------------------------

-- ----------------------------
-- Table structure for `user_sign`
-- ----------------------------
DROP TABLE IF EXISTS `user_sign`;
CREATE TABLE `user_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `stime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '签到时间',
  PRIMARY KEY (`id`,`username`),
  KEY `id` (`id`),
  KEY `username` (`username`),
  CONSTRAINT `user_sign_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_main` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_sign
-- ----------------------------
INSERT INTO `user_sign` VALUES ('1', 'chengxi', '2018-02-09 08:00:00');
INSERT INTO `user_sign` VALUES ('2', 'chengxi', '2018-02-18 01:13:42');
INSERT INTO `user_sign` VALUES ('4', 'chengxi', '2018-02-25 23:53:52');
INSERT INTO `user_sign` VALUES ('5', 'chengxi', '2018-02-25 23:54:36');
INSERT INTO `user_sign` VALUES ('6', 'chengxi', '2018-02-26 00:01:26');
INSERT INTO `user_sign` VALUES ('7', 'yuanfen', '2018-02-26 14:18:53');
INSERT INTO `user_sign` VALUES ('8', 'chengxi', '2018-03-04 09:49:59');
INSERT INTO `user_sign` VALUES ('9', 'chengxi', '2018-03-09 15:46:35');
INSERT INTO `user_sign` VALUES ('10', 'chengxi', '2018-03-10 10:08:06');

-- ----------------------------
-- Table structure for `user_vali`
-- ----------------------------
DROP TABLE IF EXISTS `user_vali`;
CREATE TABLE `user_vali` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `valicode` varchar(20) DEFAULT NULL COMMENT '邮箱验证码，保留一天有效期，过期注册作废',
  `regtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册的时间',
  `status` int(11) NOT NULL COMMENT '0邮箱验证，1手机号码验证',
  `resend` int(11) NOT NULL DEFAULT '1' COMMENT '用户是否屏蔽验证弹出消息，1表示不屏蔽，0表示屏蔽',
  `vstatus` int(11) NOT NULL DEFAULT '0' COMMENT '0未验证，1已失效 为空则以验证',
  PRIMARY KEY (`username`,`resend`),
  KEY `id` (`id`),
  CONSTRAINT `user_vali_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user_main` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_vali
-- ----------------------------
INSERT INTO `user_vali` VALUES ('7', '222222', '98096499ad7a4868', '2018-03-12 23:38:54', '0', '1', '0');
