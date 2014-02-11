alter table  tab_goods_consumer add spreadprice double not null COMMENT '进售差价';



insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (27,1,'0,1,','我的面板',NULL,NULL,NULL,100,'1',NULL,1,'2013-05-27 08:00:00',1,'2013-05-27 08:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (28,27,'0,1,27,','个人信息',NULL,NULL,NULL,990,'1',NULL,1,'2013-05-27 08:00:00',1,'2013-05-27 08:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (29,28,'0,1,27,28,','个人信息','/sys/user/info',NULL,'user',30,'1',NULL,1,'2013-05-27 08:00:00',1,'2013-05-27 08:00:00',NULL,'0');
insert  into `sys_menu`(`id`,`parent_id`,`parent_ids`,`name`,`href`,`target`,`icon`,`sort`,`is_show`,`permission`,`create_by`,`create_date`,`update_by`,`update_date`,`remarks`,`del_flag`) values (30,28,'0,1,27,28,','修改密码','/sys/user/modifyPwd',NULL,'lock',40,'1',NULL,1,'2013-05-27 08:00:00',1,'2013-05-27 08:00:00',NULL,'0');
 

 


