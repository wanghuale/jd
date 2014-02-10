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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='服务';

/*Data for the table `tab_services` */

insert  into `tab_services`(`id`,`code`,`bussiness_type`,`name`,`provider_id`,`description`,`uri`,`business_handler`,`isused`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (3,'S1','201','显示消息',4,'返回客户发送的消息','/sp/https/show','httpBusinessHandler','1',2,'2013-07-10 15:52:25',2,'2013-08-02 17:01:50',NULL,'0'),(4,'201','201','手机充值',3,'7.3.1  手机充值商品列表接口\r\n取商品的列表接口。字段能够满足的业务就用此接口，不能满足的重新定义。','/mall/paymentServer','httpQD201Handler','1',2,'2013-07-15 10:26:17',2,'2013-08-05 14:51:15',NULL,'0'),(5,'43','103','12',5,'321','12','httpBusinessHandler','0',2,'2013-07-18 18:14:45',2,'2013-08-02 17:01:32',NULL,'0'),(6,'41','102','12',6,'123','31','httpBusinessHandler','0',2,'2013-07-18 18:46:51',2,'2013-08-02 17:01:22',NULL,'0'),(7,'301','301','Q币充值',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:22:13',2,'2013-08-05 10:22:41',NULL,'0'),(8,'999','999','订单查询',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:25:16',2,'2013-08-05 10:25:40',NULL,'0'),(9,'402','402','机票业务',3,'/mall/paymentServer  \r\nhttpQD201Handler','/mall/paymentServer','httpQD401Handler','1',2,'2013-08-05 10:28:38',2,'2013-09-26 10:57:34',NULL,'0'),(10,'302','302','游戏卡密',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:29:06',2,'2013-08-05 10:31:46',NULL,'0'),(11,'403','303','游戏充值',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:29:33',2,'2013-08-05 10:32:18',NULL,'0'),(12,'203','203','缴纳电费',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:30:00',2,'2013-08-05 10:32:22',NULL,'0'),(13,'103','103','信用卡还款',3,'1','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:30:29',2,'2013-08-05 10:32:27',NULL,'0'),(14,'102','102','银行卡转账',3,'','/mall/paymentServer','httpQD201Handler','1',2,'2013-08-05 10:30:53',2,'2013-08-05 10:32:31',NULL,'0'),(15,'180','180','擎动银联查询',8,'查询','/mpi/payServlet/queryServlet','httpQD181Handler','1',2,'2013-08-05 15:53:57',2,'2013-08-12 16:38:51',NULL,'0'),(16,'181','181','擎动银联交易',8,'消费、撤销交易、退货','/mpi/payServlet','httpQD181Handler','1',2,'2013-08-05 15:54:59',2,'2013-08-12 16:38:46',NULL,'0'),(17,'191','191','支付',8,'','/mpi/nocard','httpQD201Handler','1',2,'2013-08-12 14:17:50',2,'2013-08-12 14:17:58',NULL,'0'),(18,'189','189','发货确认',8,'擎动待开发接口','-','httpQD189Handler','1',2,'2013-09-10 09:15:24',2,'2013-09-10 09:17:54',NULL,'0');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
