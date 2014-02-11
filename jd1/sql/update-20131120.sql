/*
SQLyog Community Edition- MySQL GUI v6.15 RC2
MySQL - 5.5.28-log : Database - sp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `sp`;

USE `sp`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` int(11) NOT NULL COMMENT '排序（升序）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COMMENT='字典表';

/*Data for the table `sys_dict` */

insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (1,'正常','0','del_flag','删除标记',10,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (2,'删除','1','del_flag','删除标记',20,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (3,'显示','1','show_hide','显示/隐藏',10,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (4,'隐藏','0','show_hide','显示/隐藏',20,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (5,'是','1','yes_no','是/否',10,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (6,'否','0','yes_no','是/否',20,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (12,'默认主题','default','theme','主题方案',10,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (13,'天蓝主题','cerulean','theme','主题方案',20,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (14,'橙色主题','readable','theme','主题方案',30,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (15,'红色主题','united','theme','主题方案',40,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (16,'Flat主题','flat','theme','主题方案',60,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (21,'公司','1','sys_office_type','机构类型',60,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (22,'部门','2','sys_office_type','机构类型',70,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (23,'一级','1','sys_office_grade','机构等级',10,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (24,'二级','2','sys_office_grade','机构等级',20,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (25,'三级','3','sys_office_grade','机构等级',30,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (26,'四级','4','sys_office_grade','机构等级',40,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (27,'所有数据','1','sys_data_scope','数据范围',10,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (28,'所在公司及以下数据','2','sys_data_scope','数据范围',20,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (29,'所在公司数据','3','sys_data_scope','数据范围',30,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (30,'所在部门及以下数据','4','sys_data_scope','数据范围',40,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (31,'所在部门数据','5','sys_data_scope','数据范围',50,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (32,'仅本人数据','8','sys_data_scope','数据范围',90,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (33,'按明细设置','9','sys_data_scope','数据范围',100,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'1');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (34,'系统管理','1','sys_user_type','用户类型',10,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (35,'部门经理','2','sys_user_type','用户类型',20,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (36,'普通用户','3','sys_user_type','用户类型',30,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (62,'接入日志','1','sys_log_type','日志类型',30,1,'2013-06-03 00:00:00',1,'2013-06-03 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (63,'异常日志','2','sys_log_type','日志类型',40,1,'2013-06-03 00:00:00',1,'2013-06-03 00:00:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (64,'HTTP','1','sys_protocol','协议类型',1,2,'2013-07-10 14:43:44',2,'2013-07-10 14:43:44',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (65,'WS','2','sys_protocol','协议类型',2,2,'2013-07-10 14:44:07',2,'2013-07-10 14:44:07',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (66,'1','1','1','1',1,2,'2013-07-18 18:19:04',2,'2013-07-18 18:19:04',NULL,'1');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (67,'12','31','12','3123',31,2,'2013-07-18 18:48:42',2,'2013-07-18 18:48:42',NULL,'1');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (68,'test','11111111111111111111111111111111111111111111111111','22222222222222111111111122222222222221111111111122','212121',12121212,2,'2013-07-18 20:23:30',2,'2013-07-18 20:23:30',NULL,'1');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (69,'102 银行转账','102','bussiness_type','银行转账',100,2,'2013-08-02 16:46:08',2,'2013-08-05 15:58:08',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (70,'103 信用卡还款','103','bussiness_type','信用卡还款',101,2,'2013-08-02 16:46:33',2,'2013-08-05 15:58:01',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (71,'201 手机充值','201','bussiness_type','手机充值',200,2,'2013-08-02 16:46:48',2,'2013-08-05 15:58:36',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (72,'203 缴纳电费','203','bussiness_type','缴纳电费',201,2,'2013-08-02 16:47:04',2,'2013-08-05 15:58:42',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (73,'301 Q币直充','301','bussiness_type','Q币直充',300,2,'2013-08-02 16:47:25',2,'2013-08-05 15:58:21',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (74,'302 游戏卡密','302','bussiness_type','游戏卡密',301,2,'2013-08-02 16:47:39',2,'2013-08-05 15:58:30',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (75,'303 游戏直充','303','bussiness_type','游戏直充',301,2,'2013-08-02 16:47:51',2,'2013-08-05 15:58:47',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (76,'999 通用应用','999','bussiness_type','通用应用',900,2,'2013-08-02 16:48:05',2,'2013-08-05 15:58:59',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (77,'402 机票业务','402','bussiness_type','机票业务',400,2,'2013-08-05 10:27:38',2,'2013-08-05 15:58:54',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (78,'180 擎动银联查询','180','bussiness_type','擎动银联查询',102,2,'2013-08-05 15:55:35',2,'2013-08-05 15:57:56',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (79,'181 擎动银联交易','181','bussiness_type','擎动银联交易',103,2,'2013-08-05 15:56:13',2,'2013-08-05 15:57:51',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (80,'191 手机无卡支付','191','bussiness_type','手机无卡支付',104,2,'2013-08-12 14:15:47',2,'2013-08-12 14:16:20',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (81,'182 前台支付','182','bussiness_type','前台支付',109,2,'2013-08-12 15:23:47',2,'2013-08-12 15:23:47',NULL,'1');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (82,'189 擎动发货确认','189','bussiness_type','擎动银联交易',189,2,'2013-09-10 09:14:06',2,'2013-09-10 09:14:06',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (83,'按比例分红','0','distri_type','分润设置',0,1,'2013-10-15 14:32:46',1,'2013-10-15 14:34:17',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (84,'按盈利分段分红','1','distri_type','分润设置',1,1,'2013-10-15 14:33:17',1,'2013-10-15 14:35:00',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (85,'预订','0','order_status','订单状态',0,2,'2013-11-13 11:02:31',2,'2013-11-13 11:04:28',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (86,'完成','1','order_status','订单状态',1,2,'2013-11-13 11:03:04',2,'2013-11-13 11:04:25',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (87,'失败','2','order_status','订单状态',2,2,'2013-11-13 11:04:02',2,'2013-11-13 11:04:21',NULL,'0');
insert  into `sys_dict`(`id`,`label`,`value`,`type`,`description`,`sort`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (88,'处理中','3','order_status','订单状态',3,2,'2013-11-13 14:25:34',2,'2013-11-13 14:25:34',NULL,'0');

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型（1：接入日志；2：异常日志）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(5) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`),
  KEY `sys_log_request_uri` (`request_uri`),
  KEY `sys_log_type` (`type`),
  KEY `sys_log_create_date` (`create_date`)
) ENGINE=InnoDB AUTO_INCREMENT=652 DEFAULT CHARSET=utf8;

/*Data for the table `sys_log` */

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(255) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '菜单名称',
  `href` varchar(255) DEFAULT NULL COMMENT '链接',
  `target` varchar(20) DEFAULT NULL COMMENT '目标（mainFrame、 _blank、_self、_parent、_top）',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `sort` int(11) NOT NULL COMMENT '排序（升序）',
  `is_show` char(1) NOT NULL COMMENT '是否在菜单中显示（1：显示；0：不显示）',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id` (`parent_id`),
  KEY `sys_menu_parent_ids` (`parent_ids`),
  KEY `sys_menu_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 COMMENT='菜单表';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (1,0,'0,','顶级菜单',NULL,NULL,NULL,0,'1',NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (2,1,'0,1,','系统设置',NULL,NULL,NULL,900,'1',NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (3,2,'0,1,2,','系统设置',NULL,NULL,NULL,980,'1',NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (4,3,'0,1,2,3,','菜单管理','/sys/menu/',NULL,'list-alt',30,'1',NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (5,4,'0,1,2,3,4,','查看',NULL,NULL,NULL,30,'0','sys:menu:view',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (6,4,'0,1,2,3,4,','修改',NULL,NULL,NULL,30,'0','sys:menu:edit',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (7,3,'0,1,2,3,','角色管理','/sys/role/',NULL,'lock',50,'1',NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (8,7,'0,1,2,3,7,','查看',NULL,NULL,NULL,30,'0','sys:role:view',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (9,7,'0,1,2,3,7,','修改',NULL,NULL,NULL,30,'0','sys:role:edit',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (10,3,'0,1,2,3,','字典管理','/sys/dict/',NULL,'th-list',60,'1',NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (11,10,'0,1,2,3,10,','查看',NULL,NULL,NULL,30,'0','sys:dict:view',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (12,10,'0,1,2,3,10,','修改',NULL,NULL,NULL,30,'0','sys:dict:edit',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (13,2,'0,1,2,','机构用户',NULL,NULL,NULL,970,'1',NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (17,13,'0,1,2,13,','机构管理','/sys/office/',NULL,'th-large',40,'1',NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (18,17,'0,1,2,13,17,','查看',NULL,NULL,NULL,30,'0','sys:office:view',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (19,17,'0,1,2,13,17,','修改',NULL,NULL,NULL,30,'0','sys:office:edit',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (20,13,'0,1,2,13,','用户管理','/sys/user/',NULL,'user',30,'1',NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (21,20,'0,1,2,13,20,','查看',NULL,NULL,NULL,30,'0','sys:user:view',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (22,20,'0,1,2,13,20,','修改',NULL,NULL,NULL,30,'0','sys:user:edit',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (67,2,'0,1,2,','日志查询',NULL,NULL,NULL,985,'1',NULL,1,'2013-06-03 00:00:00',1,'2013-06-03 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (68,67,'0,1,2,67,','日志查询','/sys/log',NULL,'pencil',30,'1','sys:log:view',1,'2013-06-03 00:00:00',1,'2013-06-03 00:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (69,2,'0,1,2,','服务管理','','','',30,'1','',2,'2013-07-10 11:52:13',2,'2013-07-10 11:52:13',NULL,'1');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (70,1,'0,1,','服务管理','','','',800,'1','',2,'2013-07-10 11:53:15',2,'2013-07-10 15:09:40',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (71,75,'0,1,70,75,','消费者','/bussiness/consumer','','th-large',30,'1','',1,'2013-07-10 12:45:16',2,'2013-07-11 10:14:32',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (72,75,'0,1,70,75,','提供者','/bussiness/provider','','user',40,'1','',1,'2013-07-10 12:45:45',1,'2013-07-10 13:48:07',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (73,75,'0,1,70,75,','服务','/bussiness/services','','inbox',50,'1','',1,'2013-07-10 12:46:12',1,'2013-07-10 13:48:07',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (74,75,'0,1,70,75,','本地接口','/bussiness/localservices','','file',60,'1','',1,'2013-07-10 12:46:40',1,'2013-07-10 13:48:07',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (75,70,'0,1,70,','服务管理','','','film',960,'1','',1,'2013-07-10 12:47:20',1,'2013-07-10 13:48:07',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (76,71,'0,1,70,75,71,','修改','','','',30,'0','bussiness:consumer:edit',1,'2013-07-10 13:44:03',1,'2013-07-10 13:44:03',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (77,72,'0,1,70,75,72,','修改','','','',30,'0','bussiness:provider:edit',1,'2013-07-10 13:44:48',2,'2013-07-10 13:49:19',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (78,73,'0,1,70,75,73,','修改','','','',30,'0','bussiness:services:edit',1,'2013-07-10 13:45:10',1,'2013-07-10 13:45:10',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (79,74,'0,1,70,75,74,','修改','','','',30,'0','bussiness:localservices:edit',1,'2013-07-10 13:45:29',1,'2013-07-10 13:45:29',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (80,74,'0,1,70,75,74,','查看','','','',30,'0','bussiness:localservices:view',1,'2013-07-10 13:45:41',1,'2013-07-10 13:47:23',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (81,73,'0,1,70,75,73,','查看','','','',30,'0','bussiness:services:view',1,'2013-07-10 13:45:58',1,'2013-07-10 13:47:08',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (82,72,'0,1,70,75,72,','查看','','','',30,'0','bussiness:provider:view',1,'2013-07-10 13:46:10',1,'2013-07-10 13:46:53',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (83,71,'0,1,70,75,71,','查看','','','',30,'0','bussiness:consumer:view',1,'2013-07-10 13:46:19',1,'2013-07-10 13:46:37',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (84,75,'0,1,70,75,','服务记录','/bussiness/serviceRecord/','','cog',70,'1','bussiness:serviceRecord:view',2,'2013-07-16 09:24:59',2,'2013-07-16 10:43:54',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (85,84,'0,1,70,75,84,','查看','','','',30,'0','bussiness:serviceRecord:edit',1,'2013-07-16 09:26:54',1,'2013-07-16 09:26:54',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (86,1,'0,1,','12','321','123','',30,'0','123',2,'2013-07-18 18:18:31',2,'2013-07-18 18:18:31',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (87,1,'0,1,','5','1','3','',30,'0','31',2,'2013-07-18 18:48:13',2,'2013-07-18 18:48:13',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (88,1,'0,1,','51','23','31','',30,'0','3123',2,'2013-07-18 18:48:24',2,'2013-07-18 18:48:24',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (89,70,'0,1,70,','234','42','234','',30,'0','432',2,'2013-07-18 18:49:16',2,'2013-07-18 18:49:16',NULL,'1');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (90,75,'0,1,70,75,','订单查询','/bussiness/orderRecord','','search',80,'1','',1,'2013-08-13 14:33:20',1,'2013-11-11 09:40:05',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (91,90,'0,1,70,75,90,','查询','','','',30,'0','bussiness:orderRecord:view',1,'2013-08-13 14:33:33',1,'2013-08-13 14:33:33',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (92,71,'0,1,70,75,71,','查看秘钥','','','',30,'0','bussiness:consumer:viewkey',2,'2013-10-31 10:15:28',2,'2013-10-31 10:15:28',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (93,89,'0,1,70,89,','分润管理','','','',30,'1','',2,'2013-11-11 09:22:16',2,'2013-11-11 09:22:16',NULL,'1');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (94,75,'0,1,70,75,','分润设置','/operation/profitSet','','inbox',90,'1','',1,'2013-11-11 09:23:10',1,'2013-11-11 09:39:34',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (95,94,'0,1,70,75,94,','查询','','','',30,'0','operation:profitSet:view',1,'2013-11-11 09:24:47',1,'2013-11-11 09:39:43',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (96,94,'0,1,70,75,94,','保存','','','',30,'0','operation:profitSet:edit',1,'2013-11-11 09:24:58',1,'2013-11-11 09:39:53',NULL,'0');

/*Table structure for table `sys_office` */

DROP TABLE IF EXISTS `sys_office`;

CREATE TABLE `sys_office` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` bigint(20) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(255) NOT NULL COMMENT '所有父级编号',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `name` varchar(100) NOT NULL COMMENT '机构名称',
  `type` char(1) NOT NULL COMMENT '机构类型（1：公司；2：部门；3：小组）',
  `grade` char(1) NOT NULL COMMENT '机构等级（1：一级；2：二级；3：三级；4：四级）',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) DEFAULT NULL COMMENT '传真',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`),
  KEY `sys_office_parent_ids` (`parent_ids`),
  KEY `sys_office_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='部门表';

/*Data for the table `sys_office` */

insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (1,0,'0,','100000','北京市总公司','1','1',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (2,1,'0,1,','100001','公司领导','2','3','1','1','1','','','',1,'2013-05-27 00:00:00',2,'2013-07-18 18:17:45','','0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (3,1,'0,1,','100002','人力部','2','1','321','12','321','1','2','3',1,'2013-05-27 00:00:00',2,'2013-07-18 18:17:56','1','0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (4,1,'0,1,','100003','市场部','2','1',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (5,1,'0,1,','100004','技术部','2','1','','','','','','',1,'2013-05-27 00:00:00',2,'2013-07-10 15:26:12','','0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (6,1,'0,1,','100005','研发部','2','1',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (7,1,'0,1,','200000','山东省分公司','1','2','','','','','','',1,'2013-05-27 00:00:00',2,'2013-07-16 09:43:28','','0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (8,7,'0,1,7,','200001','公司领导','2','2',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (9,7,'0,1,7,','200002','综合部','2','2',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (10,7,'0,1,7,','200003','市场部','2','2',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (11,7,'0,1,7,','200004','技术部','2','2',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (12,7,'0,1,7,','201000','济南市分公司','1','3',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (13,12,'0,1,7,12,','201001','公司领导','2','3',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (14,12,'0,1,7,12,','201002','综合部','2','3',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (15,12,'0,1,7,12,','201003','市场部','2','3',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (16,12,'0,1,7,12,','201004','技术部','2','3',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (17,12,'0,1,7,12,','201010','济南市历城区分公司','1','4',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (18,17,'0,1,7,12,17,','201011','公司领导','2','4',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (19,17,'0,1,7,12,17,','201012','综合部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (20,17,'0,1,7,12,17,','201013','市场部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (21,17,'0,1,7,12,17,','201014','技术部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (22,12,'0,1,7,12,','201020','济南市历下区分公司','1','4',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (23,22,'0,1,7,12,22,','201021','公司领导','2','4',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (24,22,'0,1,7,12,22,','201022','综合部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (25,22,'0,1,7,12,22,','201023','市场部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (26,22,'0,1,7,12,22,','201024','技术部','2','4',NULL,NULL,NULL,NULL,NULL,NULL,1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (27,1,'0,1,','','4','1','1','','','','','','',2,'2013-07-09 13:47:05',2,'2013-07-09 13:47:05','','1');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (28,5,'0,1,5,','1','1','1','1','1','1','1','1','1','1',2,'2013-07-18 18:18:10',2,'2013-07-18 18:18:10','1','0');
insert  into `sys_office`(`id`,`parent_id`,`parent_ids`,`code`,`name`,`type`,`grade`,`address`,`zip_code`,`master`,`phone`,`fax`,`email`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (29,1,'0,1,','123','41','1','1','31','123','41','123','31','12',2,'2013-07-18 18:47:58',2,'2013-07-18 18:47:58','4123','0');

/*Table structure for table `sys_profit_region` */

DROP TABLE IF EXISTS `sys_profit_region`;

CREATE TABLE `sys_profit_region` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `profit_set_id` bigint(20) NOT NULL COMMENT '分润设置表id',
  `money` double(10,2) NOT NULL COMMENT '利润金额',
  `startregion` varchar(100) DEFAULT NULL COMMENT '利润区间',
  `endregion` varchar(100) DEFAULT NULL COMMENT '利润区间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8 COMMENT='利润区间信息表';

/*Data for the table `sys_profit_region` */

/*Table structure for table `sys_profit_set` */

DROP TABLE IF EXISTS `sys_profit_set`;

CREATE TABLE `sys_profit_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id号',
  `business_type` varchar(100) NOT NULL COMMENT '业务类型',
  `linkman` varchar(20) DEFAULT NULL COMMENT '联系人',
  `tel` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) DEFAULT NULL COMMENT '联系地址',
  `distri_type` bigint(20) NOT NULL COMMENT '分润方式(数据字典)',
  `rate` double(6,2) DEFAULT NULL COMMENT '分红比例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

/*Data for the table `sys_profit_set` */

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `office_id` bigint(20) DEFAULT NULL COMMENT '归属机构',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据范围（0：所有数据；1：所在公司及以下数据；2：所在公司数据；3：所在部门及以下数据；4：所在部门数据；8：仅本人数据；9：按明细设置）',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`office_id`,`name`,`data_scope`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (1,1,'系统管理员','1',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_role`(`id`,`office_id`,`name`,`data_scope`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (2,1,'公司管理员','2',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_role`(`id`,`office_id`,`name`,`data_scope`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (3,1,'本公司管理员','3',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_role`(`id`,`office_id`,`name`,`data_scope`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (4,1,'部门管理员','4',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_role`(`id`,`office_id`,`name`,`data_scope`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (5,1,'本部门管理员','5',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_role`(`id`,`office_id`,`name`,`data_scope`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (6,1,'普通用户','8',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');
insert  into `sys_role`(`id`,`office_id`,`name`,`data_scope`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (7,7,'山东省管理员','9',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00',NULL,'0');

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色编号',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,1);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,2);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,3);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,4);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,5);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,6);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,7);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,8);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,9);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,10);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,11);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,12);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,13);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,17);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,18);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,19);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,20);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,21);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,22);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,67);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,68);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,70);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,71);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,72);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,73);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,74);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,75);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,76);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,77);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,78);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,79);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,80);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,81);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,82);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,83);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,84);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,85);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,86);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,87);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,88);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,90);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,91);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,92);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,94);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,95);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,96);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,1);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,2);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,3);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,4);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,5);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,6);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,7);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,8);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,9);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,10);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,11);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,12);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,13);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,14);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,15);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,16);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,17);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,18);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,19);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,20);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,21);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,22);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,23);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,24);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,25);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,26);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,27);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,28);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,29);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,30);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,31);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,32);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,33);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,34);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,35);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,36);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,37);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,38);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,39);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,40);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,41);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,42);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,43);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,44);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,45);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,46);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,47);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,48);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,49);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,50);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,51);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,52);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,53);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,54);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,55);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,56);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,57);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,58);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,59);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,60);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,61);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,62);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,63);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,64);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,65);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,66);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,67);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (2,68);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,1);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,2);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,3);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,4);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,5);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,6);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,7);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,8);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,9);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,10);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,11);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,12);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,13);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,17);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,18);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,19);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,20);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,21);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,22);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,67);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (3,68);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,1);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,2);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,3);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,4);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,5);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,6);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,7);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,8);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,9);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,10);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,11);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,12);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,13);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,14);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,15);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,16);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,17);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,18);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,19);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,20);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,21);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,22);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,23);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,24);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,25);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,26);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,27);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,28);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,29);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,30);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,31);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,32);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,33);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,34);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,35);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,36);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,37);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,38);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,39);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,40);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,41);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,42);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,43);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,44);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,45);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,46);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,47);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,48);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,49);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,50);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,51);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,52);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,53);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,54);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,55);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,56);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,57);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,58);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,59);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,60);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,61);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,62);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,63);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,64);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,65);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,66);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,67);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (4,68);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,1);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,2);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,3);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,4);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,5);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,6);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,7);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,8);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,9);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,10);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,11);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,12);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,13);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,17);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,18);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,19);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,20);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,21);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,22);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,67);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (5,68);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,1);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,2);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,13);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,14);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,15);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,16);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,17);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,18);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,19);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,20);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,21);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (6,22);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,1);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,2);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,3);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,4);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,5);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,6);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,7);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,8);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,9);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,10);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,11);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,12);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,13);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,14);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,15);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,16);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,17);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,18);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,19);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,20);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,21);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,22);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,23);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,24);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,25);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,26);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,27);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,28);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,29);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,30);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,31);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,32);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,33);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,34);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,35);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,36);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,37);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,38);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,39);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,40);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,41);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,42);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,43);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,44);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,45);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,46);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,47);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,48);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,49);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,50);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,51);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,52);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,53);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,54);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,55);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,56);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,57);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,58);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,59);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,60);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,61);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,62);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,63);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,64);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,65);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,66);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,67);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (7,68);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,1);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,70);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,71);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,72);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,73);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,74);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,75);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,76);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,77);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,78);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,79);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,80);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,81);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,82);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,83);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,84);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (8,85);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,1);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,70);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,71);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,72);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,73);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,74);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,75);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,76);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,77);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,78);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,79);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,80);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,81);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,82);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,83);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,84);
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (9,85);

/*Table structure for table `sys_role_office` */

DROP TABLE IF EXISTS `sys_role_office`;

CREATE TABLE `sys_role_office` (
  `role_id` bigint(20) NOT NULL COMMENT '角色编号',
  `office_id` bigint(20) NOT NULL COMMENT '机构编号',
  PRIMARY KEY (`role_id`,`office_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-机构';

/*Data for the table `sys_role_office` */

insert  into `sys_role_office`(`role_id`,`office_id`) values (7,7);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,8);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,9);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,10);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,11);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,12);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,13);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,14);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,15);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,16);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,17);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,18);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,19);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,20);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,21);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,22);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,23);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,24);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,25);
insert  into `sys_role_office`(`role_id`,`office_id`) values (7,26);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `company_id` bigint(20) NOT NULL COMMENT '归属公司',
  `office_id` bigint(20) NOT NULL COMMENT '归属部门',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `no` varchar(100) DEFAULT NULL COMMENT '工号',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `user_type` char(1) DEFAULT NULL COMMENT '用户类型',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`company_id`,`office_id`,`login_name`,`password`,`no`,`name`,`email`,`phone`,`mobile`,`user_type`,`login_ip`,`login_date`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (1,1,1,'superadmin','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','0001','Thinkgem','thinkgem@163.com','8675','8675',NULL,'10.8.15.118','2013-11-11 09:45:06',1,'2013-05-27 00:00:00',1,'2013-05-27 00:00:00','最高管理员','0');
insert  into `sys_user`(`id`,`company_id`,`office_id`,`login_name`,`password`,`no`,`name`,`email`,`phone`,`mobile`,`user_type`,`login_ip`,`login_date`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (2,1,1,'admin','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','0002','管理员','thinkgem@163.com','8675','8675','','10.8.15.121','2013-11-20 15:16:05',1,'2013-05-27 00:00:00',2,'2013-07-10 15:22:57','管理员','0');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `role_id` bigint(20) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values (1,1);
insert  into `sys_user_role`(`user_id`,`role_id`) values (2,1);
insert  into `sys_user_role`(`user_id`,`role_id`) values (3,2);
insert  into `sys_user_role`(`user_id`,`role_id`) values (4,3);
insert  into `sys_user_role`(`user_id`,`role_id`) values (5,4);
insert  into `sys_user_role`(`user_id`,`role_id`) values (6,5);
insert  into `sys_user_role`(`user_id`,`role_id`) values (7,1);
insert  into `sys_user_role`(`user_id`,`role_id`) values (7,2);
insert  into `sys_user_role`(`user_id`,`role_id`) values (7,7);
insert  into `sys_user_role`(`user_id`,`role_id`) values (8,2);
insert  into `sys_user_role`(`user_id`,`role_id`) values (9,1);
insert  into `sys_user_role`(`user_id`,`role_id`) values (10,2);
insert  into `sys_user_role`(`user_id`,`role_id`) values (11,3);
insert  into `sys_user_role`(`user_id`,`role_id`) values (12,4);
insert  into `sys_user_role`(`user_id`,`role_id`) values (13,5);
insert  into `sys_user_role`(`user_id`,`role_id`) values (14,6);
insert  into `sys_user_role`(`user_id`,`role_id`) values (15,6);
insert  into `sys_user_role`(`user_id`,`role_id`) values (16,6);
insert  into `sys_user_role`(`user_id`,`role_id`) values (17,6);
insert  into `sys_user_role`(`user_id`,`role_id`) values (17,8);

/*Table structure for table `tab_consumer` */

DROP TABLE IF EXISTS `tab_consumer`;

CREATE TABLE `tab_consumer` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `code` varchar(200) DEFAULT NULL COMMENT '使用方编号',
  `name` varchar(200) DEFAULT NULL COMMENT '使用方名称',
  `ip` varchar(100) DEFAULT NULL COMMENT 'IP',
  `description` varchar(1000) DEFAULT NULL COMMENT '使用方描述',
  `isused` varchar(10) DEFAULT NULL COMMENT '启用',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  `notifyurl` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Data for the table `tab_consumer` */

/*Table structure for table `tab_localservices` */

DROP TABLE IF EXISTS `tab_localservices`;

CREATE TABLE `tab_localservices` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `code` varchar(200) DEFAULT NULL COMMENT '接口编号',
  `name` varchar(200) DEFAULT NULL COMMENT '接口名称',
  `protocol` varchar(30) DEFAULT NULL COMMENT '协议类型',
  `port` varchar(15) DEFAULT NULL COMMENT '端口',
  `uri` varchar(300) DEFAULT NULL COMMENT 'URI',
  `isused` varchar(10) DEFAULT NULL COMMENT '是否启用',
  `description` varchar(1000) DEFAULT NULL COMMENT '接口描述',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='本地服务程序';

/*Data for the table `tab_localservices` */

/*Table structure for table `tab_order_record` */

DROP TABLE IF EXISTS `tab_order_record`;

CREATE TABLE `tab_order_record` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `call_back_url` varchar(500) NOT NULL COMMENT '回调地址',
  `order_id` varchar(100) NOT NULL COMMENT '订单号',
  `pay_order_id` varchar(100) DEFAULT NULL COMMENT '支付订单号',
  `amount` double DEFAULT NULL COMMENT '支付金额',
  `consumer` varchar(500) NOT NULL COMMENT '顾客IP',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(3000) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  `productid` varchar(100) DEFAULT NULL COMMENT '产品ID',
  `nums` bigint(30) DEFAULT NULL COMMENT '商品数量',
  `updateDate` datetime DEFAULT NULL COMMENT '处理时间',
  `status` varchar(100) DEFAULT NULL COMMENT '状态',
  `productname` varchar(500) DEFAULT NULL COMMENT '商品名称',
  `provider` int(15) DEFAULT NULL COMMENT '供应者ID',
  `dispaly` varchar(500) DEFAULT NULL,
  `do_service` bigint(30) DEFAULT NULL COMMENT '服务编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='记录表';

/*Data for the table `tab_order_record` */

/*Table structure for table `tab_provider` */

DROP TABLE IF EXISTS `tab_provider`;

CREATE TABLE `tab_provider` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `code` varchar(200) DEFAULT NULL COMMENT '接入方编号',
  `name` varchar(200) DEFAULT NULL COMMENT '接入方名称',
  `description` varchar(1000) DEFAULT NULL COMMENT '接入方描述',
  `protocol` varchar(30) DEFAULT NULL COMMENT '通讯协议',
  `ip` varchar(100) DEFAULT NULL COMMENT 'IP',
  `port` varchar(10) DEFAULT NULL COMMENT '通讯端口',
  `isused` varchar(10) DEFAULT NULL COMMENT '启用',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='服务提供方';

/*Data for the table `tab_provider` */

insert  into `tab_provider`(`id`,`code`,`name`,`description`,`protocol`,`ip`,`port`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (3,'QD001','擎动自营商城接口','擎动自营商城接口','1','59.175.217.22','8081','1',2,'2013-07-15 10:22:24',2,'2013-07-31 16:47:25',NULL,'0');
insert  into `tab_provider`(`id`,`code`,`name`,`description`,`protocol`,`ip`,`port`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (4,'T002','本地测试','本地测试','1','10.8.11.164','8080','1',2,'2013-07-15 11:26:22',2,'2013-07-15 13:57:14',NULL,'0');
insert  into `tab_provider`(`id`,`code`,`name`,`description`,`protocol`,`ip`,`port`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (8,'QD002','擎动银联在线接口','','1','59.175.217.22','8081','1',2,'2013-08-05 15:51:06',2,'2013-08-12 11:50:45',NULL,'0');
insert  into `tab_provider`(`id`,`code`,`name`,`description`,`protocol`,`ip`,`port`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (9,'kamennet','卡门网云接口','','1','api.kamennet.com','80','1',2,'2013-10-24 14:04:48',2,'2013-10-24 14:04:48',NULL,'0');
insert  into `tab_provider`(`id`,`code`,`name`,`description`,`protocol`,`ip`,`port`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (10,'LJ001','珞迦航空机票','','1','open.elufei.com','80','1',2,'2013-11-04 16:41:48',2,'2013-11-04 16:41:48',NULL,'0');

/*Table structure for table `tab_service_consumer` */

DROP TABLE IF EXISTS `tab_service_consumer`;

CREATE TABLE `tab_service_consumer` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `service_id` int(15) DEFAULT NULL,
  `consumer_id` int(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1056 DEFAULT CHARSET=utf8 COMMENT='使用者权限';

/*Data for the table `tab_service_consumer` */

/*Table structure for table `tab_service_record` */

DROP TABLE IF EXISTS `tab_service_record`;

CREATE TABLE `tab_service_record` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `username` varchar(200) DEFAULT NULL COMMENT '访问主机',
  `ip` varchar(200) DEFAULT NULL COMMENT '访问IP',
  `re_data` varchar(5000) DEFAULT NULL COMMENT '请求数据包',
  `trade_money` double DEFAULT NULL COMMENT '交易金额',
  `do_data` varchar(7000) DEFAULT NULL COMMENT '返回数据包',
  `status` varchar(3) DEFAULT NULL COMMENT '状态',
  `re_service` varchar(30) DEFAULT NULL COMMENT '本地服务',
  `do_service` int(15) DEFAULT NULL COMMENT '远程服务',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38909 DEFAULT CHARSET=utf8 COMMENT='服务记录';

/*Data for the table `tab_service_record` */

insert  into `tab_service_record`(`id`,`username`,`ip`,`re_data`,`trade_money`,`do_data`,`status`,`re_service`,`do_service`,`create_date`,`remarks`,`del_flag`) values (38906,NULL,'10.8.11.159','{\"ACTION_NAME\": \"SELECT_PRODUCT\",\"ACTION_USER\": \"WPF\",\"APP_ID\":\"303\",\"ACTION_INFO\":\"5351504957545553535150495754555336B76DE78F1463AC7F295BA76748DC11\"}',0,NULL,NULL,NULL,NULL,'2013-11-20 15:57:29','','0');
insert  into `tab_service_record`(`id`,`username`,`ip`,`re_data`,`trade_money`,`do_data`,`status`,`re_service`,`do_service`,`create_date`,`remarks`,`del_flag`) values (38907,NULL,'10.8.11.159','{\"ACTION_NAME\": \"SELECT_PRODUCT\",\"ACTION_USER\": \"WPF\",\"APP_ID\":\"303\",\"ACTION_INFO\":\"494954495448535248575257575751538A6769DB09209669D1134A8EAC5BE6C5\"}',0,NULL,NULL,NULL,NULL,'2013-11-20 15:58:24','','0');
insert  into `tab_service_record`(`id`,`username`,`ip`,`re_data`,`trade_money`,`do_data`,`status`,`re_service`,`do_service`,`create_date`,`remarks`,`del_flag`) values (38908,NULL,'10.8.11.159','{\"ACTION_NAME\": \"SELECT_PRODUCT\",\"ACTION_USER\": \"WPF\",\"APP_ID\":\"303\",\"ACTION_INFO\":\"56565152484851515656515248485151F46869DCDC3A7A802FA733346C5672C8\"}',0,NULL,NULL,NULL,NULL,'2013-11-20 15:58:48','','0');

/*Table structure for table `tab_services` */

DROP TABLE IF EXISTS `tab_services`;

CREATE TABLE `tab_services` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `code` varchar(200) DEFAULT NULL COMMENT '服务编号',
  `bussiness_type` varchar(15) DEFAULT NULL COMMENT '业务类型',
  `name` varchar(200) DEFAULT NULL COMMENT '服务名称',
  `provider_id` int(15) DEFAULT NULL COMMENT '接入方ID',
  `description` varchar(1000) DEFAULT NULL COMMENT '服务描述',
  `uri` varchar(300) DEFAULT NULL COMMENT '服务URI',
  `business_handler` varchar(1000) DEFAULT NULL COMMENT '本地对应服务',
  `isused` varchar(10) DEFAULT NULL COMMENT '启用',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='服务';

/*Data for the table `tab_services` */

insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (4,'201','201','手机充值',3,'7.3.1  手机充值商品列表接口','/mall/paymentServer','httpQD201Handler','1',2,'2013-07-15 10:26:17',2,'2013-10-24 16:13:28',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (7,'301','301','Q币充值',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:22:13',2,'2013-08-05 10:22:41',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (8,'999','999','订单查询',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:25:16',2,'2013-08-05 10:25:40',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (9,'402','402','机票业务',3,'/mall/paymentServer  \r\nhttpQD201Handler','/mall/paymentServer','httpQD401Handler','1',2,'2013-08-05 10:28:38',2,'2013-09-26 10:57:34',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (10,'302','302','游戏卡密',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:29:06',2,'2013-08-05 10:31:46',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (11,'403','303','游戏充值',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:29:33',2,'2013-08-05 10:32:18',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (12,'203','203','缴纳电费',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:30:00',2,'2013-08-05 10:32:22',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (13,'103','103','信用卡还款',3,'1','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:30:29',2,'2013-08-05 10:32:27',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (14,'102','102','银行卡转账',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:30:53',2,'2013-08-05 10:32:31',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (15,'180','180','擎动银联查询',8,'查询','/mpi/payServlet/queryServlet','httpQD181Handler','1',2,'2013-08-05 15:53:57',2,'2013-08-12 16:38:51',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (16,'181','181','擎动银联交易',8,'消费、撤销交易、退货','/mpi/payServlet','httpQD181Handler','1',2,'2013-08-05 15:54:59',2,'2013-08-12 16:38:46',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (17,'191','191','支付',8,'','/mpi/nocard','httpQD201Handler','1',2,'2013-08-12 14:17:50',2,'2013-08-12 14:17:58',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (18,'189','189','发货确认',8,'擎动待开发接口','-','httpQD189Handler','1',2,'2013-09-10 09:15:24',2,'2013-09-10 09:17:54',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (19,'201','201','手机充值',9,'','-','httpKaMen201Handler','1',2,'2013-10-28 11:20:59',2,'2013-10-28 11:20:59',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (20,'301','301','Q币充值',9,'','-','httpKaMen301Handler','1',2,'2013-10-28 17:08:13',2,'2013-10-29 15:15:37',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (21,'302','302','游戏卡密',9,'','-','httpKaMen302Handler','1',2,'2013-10-29 15:14:23',2,'2013-10-29 15:16:01',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (22,'303','303','游戏直充',9,'','-','httpKaMen303Handler','1',2,'2013-10-29 15:14:33',2,'2013-10-29 15:15:54',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (23,'999','999','通用应用',9,'','-','httpKaMen999Handler','1',2,'2013-10-29 17:08:25',2,'2013-10-29 17:08:47',NULL,'0');
insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (24,'402','402','机票业务',10,'','-','luoJiaHttpHandler','1',2,'2013-11-04 16:42:11',2,'2013-11-14 10:29:25',NULL,'0');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
