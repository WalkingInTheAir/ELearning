/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : e-learning

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2015-07-06 21:57:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin`;
CREATE TABLE `tb_admin` (
  `admin_id` int(11) NOT NULL auto_increment,
  `admin_name` varchar(11) default NULL,
  `admin_number` varchar(11) default NULL,
  `admin_password` varchar(11) default NULL,
  PRIMARY KEY  (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_admin
-- ----------------------------
INSERT INTO `tb_admin` VALUES ('1', 'ADMIN1', 'A10001', '123456');

-- ----------------------------
-- Table structure for tb_class
-- ----------------------------
DROP TABLE IF EXISTS `tb_class`;
CREATE TABLE `tb_class` (
  `cls_id` int(11) NOT NULL auto_increment COMMENT '班级编号',
  `cls_name` varchar(20) default NULL COMMENT '班级名称',
  `f_major_id` int(11) default NULL COMMENT '专业编号',
  PRIMARY KEY  (`cls_id`),
  KEY `f_major_id` (`f_major_id`),
  CONSTRAINT `f_major_id` FOREIGN KEY (`f_major_id`) REFERENCES `tb_major` (`major_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_class
-- ----------------------------
INSERT INTO `tb_class` VALUES ('1', '软件1211', '1');
INSERT INTO `tb_class` VALUES ('2', '软件1221', '1');
INSERT INTO `tb_class` VALUES ('3', '软件1231', '1');
INSERT INTO `tb_class` VALUES ('4', '电商1211', '2');
INSERT INTO `tb_class` VALUES ('5', '电商1221', '2');
INSERT INTO `tb_class` VALUES ('6', '电商1231', '2');
INSERT INTO `tb_class` VALUES ('7', '计媒1211', '4');
INSERT INTO `tb_class` VALUES ('8', '土木1211', '37');
INSERT INTO `tb_class` VALUES ('9', '土木1221', '37');

-- ----------------------------
-- Table structure for tb_course
-- ----------------------------
DROP TABLE IF EXISTS `tb_course`;
CREATE TABLE `tb_course` (
  `course_id` int(11) NOT NULL auto_increment,
  `course_name` varchar(30) default NULL,
  `f_ct_id` int(11) default NULL,
  `f_relative_id` int(11) default NULL,
  `course_desc` text,
  `course_outline` text,
  PRIMARY KEY  (`course_id`),
  KEY `course_f_ct_id` (`f_ct_id`),
  CONSTRAINT `course_f_ct_id` FOREIGN KEY (`f_ct_id`) REFERENCES `tb_coursetype` (`ct_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_course
-- ----------------------------
INSERT INTO `tb_course` VALUES ('1', '毛泽东思想概论', '1', null, '一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四一二三四', null);
INSERT INTO `tb_course` VALUES ('2', '军事理论', '1', null, null, null);
INSERT INTO `tb_course` VALUES ('3', '计算机网络基础', '2', '1', '<p>计算机网络基础是一门。。。。。</p>', '<h1 label=\"标题居中\" style=\"white-space: normal; font-size: 32px; border-bottom-color: rgb(204, 204, 204); border-bottom-width: 2px; border-bottom-style: solid; padding: 0px 4px 0px 0px; text-align: center; margin: 0px 0px 20px;\">课程大纲</h1><h1 style=\"white-space: normal;\">第一章 xxx<br/></h1><h1 style=\"white-space: normal;\">第二章 xxx<br/></h1><h1 style=\"white-space: normal;\">第三章 xxx</h1><p><br/></p>');
INSERT INTO `tb_course` VALUES ('4', 'JAVA面向对象高级编程', '3', '1', null, null);
INSERT INTO `tb_course` VALUES ('5', 'JSP与SERVLET', '4', '1', null, null);
INSERT INTO `tb_course` VALUES ('7', '操作系统', '2', '1', null, null);
INSERT INTO `tb_course` VALUES ('8', '数据结构', '3', '1', null, null);

-- ----------------------------
-- Table structure for tb_coursetype
-- ----------------------------
DROP TABLE IF EXISTS `tb_coursetype`;
CREATE TABLE `tb_coursetype` (
  `ct_id` int(11) NOT NULL auto_increment,
  `ct_name` varchar(20) default NULL,
  PRIMARY KEY  (`ct_id`),
  UNIQUE KEY `unique` (`ct_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_coursetype
-- ----------------------------
INSERT INTO `tb_coursetype` VALUES ('3', '专业课');
INSERT INTO `tb_coursetype` VALUES ('1', '公共课');
INSERT INTO `tb_coursetype` VALUES ('4', '班级专业课');
INSERT INTO `tb_coursetype` VALUES ('2', '院系公共课');

-- ----------------------------
-- Table structure for tb_coursevideo
-- ----------------------------
DROP TABLE IF EXISTS `tb_coursevideo`;
CREATE TABLE `tb_coursevideo` (
  `COURSEVIDEO_ID` int(11) NOT NULL auto_increment,
  `COURSEVIDEO_NAME` varchar(20) default NULL,
  `COURSEVIDEO_ORIGNAME` varchar(100) default NULL,
  `COURSEVIDEO_TIME` date default NULL,
  `F_TEACHER_ID` int(11) default NULL,
  `F_COURSE_ID` int(11) default NULL,
  PRIMARY KEY  (`COURSEVIDEO_ID`),
  KEY `F_VIDEO_COURSE_ID` (`F_COURSE_ID`),
  KEY `F_VIDEO_TEACHER_ID` (`F_TEACHER_ID`),
  CONSTRAINT `F_VIDEO_COURSE_ID` FOREIGN KEY (`F_COURSE_ID`) REFERENCES `tb_course` (`course_id`),
  CONSTRAINT `F_VIDEO_TEACHER_ID` FOREIGN KEY (`F_TEACHER_ID`) REFERENCES `tb_teacher` (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_coursevideo
-- ----------------------------
INSERT INTO `tb_coursevideo` VALUES ('10', '1431778629437.mp4', '第一章 你好.mp4', '2015-05-16', '1', '3');
INSERT INTO `tb_coursevideo` VALUES ('11', '1431778782802.mp4', 'Solo 中英字幕版 (Fang1169制)--音悦Tai.mp4', '2015-05-16', '1', '3');

-- ----------------------------
-- Table structure for tb_courseware
-- ----------------------------
DROP TABLE IF EXISTS `tb_courseware`;
CREATE TABLE `tb_courseware` (
  `COURSEWARE_ID` int(11) NOT NULL auto_increment,
  `COURSEWARE_NAME` varchar(20) default NULL,
  `COURSEWARE_ORIGNAME` varchar(100) default NULL,
  `COURSEWARE_TIME` datetime default NULL,
  `F_TEACHER_ID` int(11) default NULL,
  `F_COURSE_ID` int(11) default NULL,
  PRIMARY KEY  (`COURSEWARE_ID`),
  KEY `F_COURSEWARE_TEACHER_ID` (`F_TEACHER_ID`),
  KEY `F_COURSEWARE_COURSE_ID` (`F_COURSE_ID`),
  CONSTRAINT `F_COURSEWARE_COURSE_ID` FOREIGN KEY (`F_COURSE_ID`) REFERENCES `tb_course` (`course_id`),
  CONSTRAINT `F_COURSEWARE_TEACHER_ID` FOREIGN KEY (`F_TEACHER_ID`) REFERENCES `tb_teacher` (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_courseware
-- ----------------------------
INSERT INTO `tb_courseware` VALUES ('1', '1431775703636.pptx', '1430320228956.pptx', '2015-05-16 19:28:23', '1', '3');

-- ----------------------------
-- Table structure for tb_department
-- ----------------------------
DROP TABLE IF EXISTS `tb_department`;
CREATE TABLE `tb_department` (
  `dept_id` int(11) NOT NULL auto_increment,
  `dept_name` varchar(30) default NULL COMMENT '院系表',
  PRIMARY KEY  (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_department
-- ----------------------------
INSERT INTO `tb_department` VALUES ('1', '计算机与软件学院');
INSERT INTO `tb_department` VALUES ('2', '机械工程学院');
INSERT INTO `tb_department` VALUES ('3', '能源与电气工程学院');
INSERT INTO `tb_department` VALUES ('4', '经济管理学院');
INSERT INTO `tb_department` VALUES ('5', '艺术设计学院');
INSERT INTO `tb_department` VALUES ('6', '文理学院');
INSERT INTO `tb_department` VALUES ('7', '社会科学部');
INSERT INTO `tb_department` VALUES ('8', '体育部');
INSERT INTO `tb_department` VALUES ('9', '国际教育学院');
INSERT INTO `tb_department` VALUES ('10', 'dasfadsf');

-- ----------------------------
-- Table structure for tb_faq
-- ----------------------------
DROP TABLE IF EXISTS `tb_faq`;
CREATE TABLE `tb_faq` (
  `FAQ_ID` int(11) NOT NULL auto_increment,
  `FAQ_NAME` varchar(50) default NULL,
  `FAQ_ANSWER` text,
  `F_COURSE_ID` int(11) default NULL,
  PRIMARY KEY  (`FAQ_ID`),
  KEY `FAQ_F_COURSE_ID` (`F_COURSE_ID`),
  CONSTRAINT `FAQ_F_COURSE_ID` FOREIGN KEY (`F_COURSE_ID`) REFERENCES `tb_course` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_faq
-- ----------------------------
INSERT INTO `tb_faq` VALUES ('12', 'BBB', 'BBBBB', '3');
INSERT INTO `tb_faq` VALUES ('14', 'ASDFAAA', 'ASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDF', '3');
INSERT INTO `tb_faq` VALUES ('15', 'SADFASDF', 'ASDFSDAFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDF', '3');
INSERT INTO `tb_faq` VALUES ('16', 'ASDFDSAF', 'ASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDFASDFDSAFASDF', '3');

-- ----------------------------
-- Table structure for tb_major
-- ----------------------------
DROP TABLE IF EXISTS `tb_major`;
CREATE TABLE `tb_major` (
  `major_id` int(11) NOT NULL auto_increment,
  `major_name` varchar(20) default NULL,
  `f_dept_id` int(11) default NULL,
  PRIMARY KEY  (`major_id`),
  KEY `dept_id` (`f_dept_id`),
  CONSTRAINT `dept_id` FOREIGN KEY (`f_dept_id`) REFERENCES `tb_department` (`dept_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_major
-- ----------------------------
INSERT INTO `tb_major` VALUES ('1', '软件技术', '1');
INSERT INTO `tb_major` VALUES ('2', '电子商务', '1');
INSERT INTO `tb_major` VALUES ('3', '网络技术', '1');
INSERT INTO `tb_major` VALUES ('4', '多媒体', '1');
INSERT INTO `tb_major` VALUES ('37', '土木工程', '2');
INSERT INTO `tb_major` VALUES ('38', 'asfdasf', '1');
INSERT INTO `tb_major` VALUES ('39', 'asdfadsfads', '1');
INSERT INTO `tb_major` VALUES ('40', 'affas', '1');
INSERT INTO `tb_major` VALUES ('41', 'fadf', '1');

-- ----------------------------
-- Table structure for tb_student
-- ----------------------------
DROP TABLE IF EXISTS `tb_student`;
CREATE TABLE `tb_student` (
  `stu_id` int(11) NOT NULL auto_increment,
  `stu_name` varchar(255) default NULL,
  `stu_number` varchar(20) default NULL,
  `stu_password` varchar(20) default NULL,
  `f_cls_id` int(11) default NULL,
  PRIMARY KEY  (`stu_id`),
  UNIQUE KEY `u_stu_num` (`stu_number`),
  KEY `stu_f_cls_id` (`f_cls_id`),
  CONSTRAINT `stu_f_cls_id` FOREIGN KEY (`f_cls_id`) REFERENCES `tb_class` (`cls_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_student
-- ----------------------------
INSERT INTO `tb_student` VALUES ('1', '张三', 'S10001', '123456', '1');

-- ----------------------------
-- Table structure for tb_teacher
-- ----------------------------
DROP TABLE IF EXISTS `tb_teacher`;
CREATE TABLE `tb_teacher` (
  `teacher_id` int(11) NOT NULL auto_increment,
  `teacher_number` varchar(20) NOT NULL,
  `teacher_name` varchar(20) default NULL,
  `teacher_password` varchar(20) default NULL,
  `f_dept_id` int(11) default NULL,
  PRIMARY KEY  (`teacher_id`),
  UNIQUE KEY `teacher_number` (`teacher_number`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_teacher
-- ----------------------------
INSERT INTO `tb_teacher` VALUES ('1', 'T10001', '文渊', '1', '1');
INSERT INTO `tb_teacher` VALUES ('2', 'T10002', '文博', 'L10002', '1');
INSERT INTO `tb_teacher` VALUES ('3', 'T10003', '张萍萍', 'T10003', '6');
INSERT INTO `tb_teacher` VALUES ('4', 'T10004', '李小丽', 'T10004', '6');
INSERT INTO `tb_teacher` VALUES ('5', 'T10005', '文昌', 'T10005', '1');

-- ----------------------------
-- Table structure for tb_teacher_course_class
-- ----------------------------
DROP TABLE IF EXISTS `tb_teacher_course_class`;
CREATE TABLE `tb_teacher_course_class` (
  `tc_id` int(11) NOT NULL auto_increment,
  `f_teacher_id` int(11) default NULL,
  `f_course_id` int(11) default NULL,
  `f_cls_id` int(11) default NULL,
  PRIMARY KEY  (`tc_id`),
  KEY `f_teacher_id` (`f_teacher_id`),
  KEY `f_course_id` (`f_course_id`),
  KEY `f_cls_id` (`f_cls_id`),
  CONSTRAINT `f_cls_id` FOREIGN KEY (`f_cls_id`) REFERENCES `tb_class` (`cls_id`),
  CONSTRAINT `f_course_id` FOREIGN KEY (`f_course_id`) REFERENCES `tb_course` (`course_id`),
  CONSTRAINT `f_teacher_id` FOREIGN KEY (`f_teacher_id`) REFERENCES `tb_teacher` (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_teacher_course_class
-- ----------------------------
INSERT INTO `tb_teacher_course_class` VALUES ('1', '3', '1', '1');
INSERT INTO `tb_teacher_course_class` VALUES ('2', '3', '1', '2');
INSERT INTO `tb_teacher_course_class` VALUES ('3', '3', '1', '3');
INSERT INTO `tb_teacher_course_class` VALUES ('4', '3', '1', '4');
INSERT INTO `tb_teacher_course_class` VALUES ('5', '3', '1', '5');
INSERT INTO `tb_teacher_course_class` VALUES ('6', '3', '1', '6');
INSERT INTO `tb_teacher_course_class` VALUES ('7', '3', '1', '7');
INSERT INTO `tb_teacher_course_class` VALUES ('8', '4', '2', '1');
INSERT INTO `tb_teacher_course_class` VALUES ('9', '4', '2', '2');
INSERT INTO `tb_teacher_course_class` VALUES ('10', '4', '2', '3');
INSERT INTO `tb_teacher_course_class` VALUES ('11', '4', '2', '4');
INSERT INTO `tb_teacher_course_class` VALUES ('12', '4', '2', '5');
INSERT INTO `tb_teacher_course_class` VALUES ('13', '4', '2', '6');
INSERT INTO `tb_teacher_course_class` VALUES ('14', '4', '2', '7');
INSERT INTO `tb_teacher_course_class` VALUES ('15', '1', '3', '1');
INSERT INTO `tb_teacher_course_class` VALUES ('16', '2', '3', '2');
INSERT INTO `tb_teacher_course_class` VALUES ('17', '2', '3', '3');
INSERT INTO `tb_teacher_course_class` VALUES ('18', '2', '3', '4');
INSERT INTO `tb_teacher_course_class` VALUES ('19', '2', '3', '5');
INSERT INTO `tb_teacher_course_class` VALUES ('20', '2', '3', '6');
INSERT INTO `tb_teacher_course_class` VALUES ('21', '2', '3', '7');
INSERT INTO `tb_teacher_course_class` VALUES ('22', null, '4', '1');
INSERT INTO `tb_teacher_course_class` VALUES ('23', null, '4', '2');
INSERT INTO `tb_teacher_course_class` VALUES ('24', null, '4', '3');
INSERT INTO `tb_teacher_course_class` VALUES ('26', '2', '5', '1');
INSERT INTO `tb_teacher_course_class` VALUES ('27', '3', '1', '8');
INSERT INTO `tb_teacher_course_class` VALUES ('28', '4', '2', '8');
INSERT INTO `tb_teacher_course_class` VALUES ('29', '3', '1', '9');
INSERT INTO `tb_teacher_course_class` VALUES ('30', '4', '2', '9');
INSERT INTO `tb_teacher_course_class` VALUES ('31', '5', '7', '1');
INSERT INTO `tb_teacher_course_class` VALUES ('32', '5', '7', '2');
INSERT INTO `tb_teacher_course_class` VALUES ('33', '5', '7', '3');
INSERT INTO `tb_teacher_course_class` VALUES ('34', '5', '7', '4');
INSERT INTO `tb_teacher_course_class` VALUES ('35', '5', '7', '5');
INSERT INTO `tb_teacher_course_class` VALUES ('36', '5', '7', '6');
INSERT INTO `tb_teacher_course_class` VALUES ('37', '5', '7', '7');
INSERT INTO `tb_teacher_course_class` VALUES ('38', null, '8', '1');
INSERT INTO `tb_teacher_course_class` VALUES ('39', null, '8', '2');
INSERT INTO `tb_teacher_course_class` VALUES ('40', null, '8', '3');

-- ----------------------------
-- View structure for view_class
-- ----------------------------
DROP VIEW IF EXISTS `view_class`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER  VIEW `view_class` AS select `c`.`cls_id` AS `cls_id`,`c`.`cls_name` AS `cls_name`,`m`.`major_id` AS `major_id`,`m`.`major_name` AS `major_name`,`m`.`f_dept_id` AS `dept_id`,`d`.`dept_name` AS `dept_name` from ((`tb_class` `c` join `tb_major` `m` on((`c`.`f_major_id` = `m`.`major_id`))) join `tb_department` `d` on((`m`.`f_dept_id` = `d`.`dept_id`))) ;

-- ----------------------------
-- View structure for view_student
-- ----------------------------
DROP VIEW IF EXISTS `view_student`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER  VIEW `view_student` AS select `view_class`.`cls_id` AS `cls_id`,`view_class`.`cls_name` AS `cls_name`,`view_class`.`major_id` AS `major_id`,`view_class`.`major_name` AS `major_name`,`view_class`.`dept_id` AS `dept_id`,`view_class`.`dept_name` AS `dept_name`,`tb_student`.`stu_id` AS `stu_id`,`tb_student`.`stu_name` AS `stu_name`,`tb_student`.`stu_number` AS `stu_number` from (`view_class` join `tb_student`) where (`view_class`.`cls_id` = `tb_student`.`f_cls_id`) ;

-- ----------------------------
-- View structure for view_teacher
-- ----------------------------
DROP VIEW IF EXISTS `view_teacher`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER  VIEW `view_teacher` AS select `tb_teacher`.`teacher_id` AS `teacher_id`,`tb_teacher`.`teacher_number` AS `teacher_number`,`tb_teacher`.`teacher_name` AS `teacher_name`,`tb_department`.`dept_id` AS `dept_id`,`tb_department`.`dept_name` AS `dept_name` from (`tb_teacher` join `tb_department`) where (`tb_teacher`.`f_dept_id` = `tb_department`.`dept_id`) ;
