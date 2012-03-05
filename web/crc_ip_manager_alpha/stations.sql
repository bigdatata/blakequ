drop database if exists stations;

/*==============================================================*/
/* Database: stations*/
/*==============================================================*/
create database stations;

use stations;

/*Table structure for table `computer` */

DROP TABLE IF EXISTS `computer`;

CREATE TABLE `computer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `os` varchar(255) DEFAULT NULL,
  `station_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `station_id` (`station_id`),
  KEY `FKDC497F1B16D8F991` (`station_id`),
  CONSTRAINT `FKDC497F1B16D8F991` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `computer` */

insert  into `computer`(`id`,`ip`,`state`,`os`,`station_id`) values (1,'2',2,'2',1),(2,'3',4,'5',2),(3,'5',5,'5',4),(7,'192.168.1.29',1,'windows33,win-7,pentium--4400',38);

/*Table structure for table `computer_log` */

DROP TABLE IF EXISTS `computer_log`;

CREATE TABLE `computer_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `computer_id` int(11) DEFAULT NULL,
  `mem_rate` float DEFAULT NULL,
  `cup_rate` float DEFAULT NULL,
  `curr_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8F1B4C2014CE5E63` (`computer_id`),
  CONSTRAINT `FK8F1B4C2014CE5E63` FOREIGN KEY (`computer_id`) REFERENCES `computer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `computer_log` */

insert  into `computer_log`(`id`,`computer_id`,`mem_rate`,`cup_rate`,`curr_time`) values (1,1,1,32,'2011-09-09 00:00:00'),(3,1,5,4,'2011-09-09 00:30:00'),(4,3,5,4,'2001-09-09 00:00:00'),(5,3,5,4,'2011-09-10 00:00:00'),(6,1,5,4,'2014-09-09 00:00:00'),(7,7,45.4877,12,'2012-02-25 13:28:19'),(8,7,45.4877,12,'2012-02-25 13:35:27'),(9,7,45.4877,12,'2012-02-25 13:39:20'),(10,7,45.4877,12,'2012-02-25 13:40:56'),(11,7,45.4877,12,'2012-02-25 13:41:16'),(12,7,45.4877,12,'2012-02-25 13:45:38'),(13,7,45.4877,12,'2012-02-25 13:58:27');

/*Table structure for table `port` */

DROP TABLE IF EXISTS `port`;

CREATE TABLE `port` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `router_id` int(11) NOT NULL,
  `get_time` datetime DEFAULT NULL COMMENT '如果异常记录时间，正常不记录',
  `ifIndex` int(11) DEFAULT NULL,
  `ifDescr` varchar(32) DEFAULT NULL,
  `ifOperStatus` int(11) DEFAULT NULL COMMENT '0(正常)，1(异常)，2(未知)',
  `ifInOctets` int(11) DEFAULT NULL COMMENT '流量',
  `locIfInCRC` int(11) DEFAULT NULL,
  `ipRouteDest` varchar(64) DEFAULT NULL,
  `portIP` varchar(64) DEFAULT NULL,
  `locIfInBitsSec` int(11) DEFAULT NULL,
  `locIfOutBitsSec` int(11) DEFAULT NULL,
  `ifOutOctets` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_4` (`router_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`router_id`) REFERENCES `router` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `port` */

insert  into `port`(`id`,`router_id`,`get_time`,`ifIndex`,`ifDescr`,`ifOperStatus`,`ifInOctets`,`locIfInCRC`,`ipRouteDest`,`portIP`,`locIfInBitsSec`,`locIfOutBitsSec`,`ifOutOctets`) values (1,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,5,'2012-02-25 13:58:28',1,'FastEthernet0',1,40732982,0,'192.168.1.0','192.168.1.11',20000,44000,87578678),(5,5,'2012-02-25 13:58:28',2,'Serial0',2,0,0,'','',0,0,0),(6,5,'2012-02-25 13:58:28',3,'Serial1',2,0,0,'','',0,0,0),(7,5,'2012-02-25 13:58:28',4,'Null0',1,0,0,'','',0,0,0);

/*Table structure for table `route` */

DROP TABLE IF EXISTS `route`;

CREATE TABLE `route` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `station_num` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=gbk;

/*Data for the table `route` */

insert  into `route`(`id`,`name`,`station_num`) values (1,'深广线',5),(2,'成都线',3),(3,'成昆线',4),(4,'成渝线',7);

/*Table structure for table `router` */

DROP TABLE IF EXISTS `router`;

CREATE TABLE `router` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `router_ip` varchar(255) NOT NULL,
  `state` int(11) DEFAULT NULL,
  `port_count` int(11) DEFAULT NULL,
  `router_info` varchar(255) DEFAULT NULL,
  `station_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `station_id` (`station_id`),
  KEY `FKC8DB974916D8F991` (`station_id`),
  CONSTRAINT `FKC8DB974916D8F991` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `router` */

insert  into `router`(`id`,`router_ip`,`state`,`port_count`,`router_info`,`station_id`) values (1,'2',2,2,'2',1),(2,'21',1,21,'32',2),(3,'1111111',NULL,3,'44444444',3),(4,'654',654,6574,'654',4),(5,'192.168.1.11',1,4,'linux，2.6.35',38);

/*Table structure for table `router_log` */

DROP TABLE IF EXISTS `router_log`;

CREATE TABLE `router_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `router_id` int(11) DEFAULT NULL,
  `cpu_rate` float DEFAULT NULL,
  `mem_rate` float DEFAULT NULL,
  `router_info` varchar(255) DEFAULT NULL,
  `curr_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK37699D4EE7FE55E3` (`router_id`),
  CONSTRAINT `FK37699D4EE7FE55E3` FOREIGN KEY (`router_id`) REFERENCES `router` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `router_log` */

insert  into `router_log`(`id`,`router_id`,`cpu_rate`,`mem_rate`,`router_info`,`curr_time`) values (2,2,5,4,'3','2011-08-09 00:00:00'),(4,3,5,4,'3','2001-09-09 00:00:00'),(5,4,5,4,'3','2011-09-09 03:00:00'),(6,4,5,4,'3','2011-09-09 03:40:00'),(7,4,4,4,'4','2011-09-09 03:30:00'),(8,5,45,7.28338,'linux，2.6.35','2012-02-25 13:45:38'),(9,5,45,7.28338,'linux，2.6.35','2012-02-25 13:58:27');

/*Table structure for table `segment` */

DROP TABLE IF EXISTS `segment`;

CREATE TABLE `segment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `station1_id` int(11) DEFAULT NULL,
  `station2_id` int(11) DEFAULT NULL,
  `route_id` int(11) NOT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK75A49F3366AF84A8` (`station1_id`),
  KEY `FK75A49F3366AFF907` (`station2_id`),
  CONSTRAINT `FK75A49F3366AF84A8` FOREIGN KEY (`station1_id`) REFERENCES `station` (`id`),
  CONSTRAINT `FK75A49F3366AFF907` FOREIGN KEY (`station2_id`) REFERENCES `station` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `segment` */

insert  into `segment`(`id`,`station1_id`,`station2_id`,`route_id`,`state`) values (2,1,3,3,3),(3,3,3,3,1),(4,4,5,3,1),(5,6,7,1,0),(6,7,8,1,0),(10,1,9,2,0),(12,1,26,2,0),(13,26,9,2,0),(14,2,26,3,0),(15,NULL,NULL,4,0),(16,29,31,4,0),(17,29,32,4,0),(18,32,31,4,0),(19,31,33,4,0),(20,33,32,4,0),(21,32,34,4,0);

/*Table structure for table `station` */

DROP TABLE IF EXISTS `station`;

CREATE TABLE `station` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `x` varchar(255) DEFAULT NULL,
  `y` varchar(255) DEFAULT NULL,
  `segment_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

/*Data for the table `station` */

insert  into `station`(`id`,`name`,`state`,`x`,`y`,`segment_num`) values (1,'TDCS021',0,'2','2',2),(2,'32',1,'2','2',2),(3,'TDCSa3',1,'3','3',2),(4,'ggg',1,'3','2',2),(5,'fTDCSdas',1,'3','2',2),(6,'ds',0,'4.0','4.0',2),(7,'1as',0,'5','5',2),(8,'asd',0,'6','4',2),(9,'fdd',0,'7','5',2),(26,'都江堰',0,'43.0','22.0',2),(29,'成都站TDCS',0,'432.0','432.0',2),(30,'32',0,'0','0',0),(31,'绵阳站',0,'321.0','44.0',2),(32,'广元站',0,'432.0','44.0',2),(33,'成光站',0,'12.0','12.0',2),(34,'管里站',0,'33333.0','22222.0',2),(35,'43',0,'1.0','1.0',2),(36,'32111111',0,'321.0','32.0',2),(37,'76',0,'67.0','765.0',2),(38,'成都站',1,'32','22',NULL);

/*Table structure for table `system` */

DROP TABLE IF EXISTS `system`;

CREATE TABLE `system` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `config_key` varchar(255) NOT NULL,
  `config_value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `system` */

insert  into `system`(`id`,`config_key`,`config_value`) values (1,'frequency','300000');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET gbk NOT NULL,
  `password` varchar(255) CHARACTER SET gbk NOT NULL,
  `authority` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`authority`) values (5,'ddd','432','admin'),(6,'fdafff','432','user'),(8,'admin','1','admin'),(12,'test2','123','user'),(13,'test2222','67','user'),(14,'test1','11','admin'),(15,'ad','ad','user'),(16,'32','32','user'),(21,'dddd','fda','user'),(22,'范德萨','dd','user'),(23,'hao','123','hao');

/*Table structure for table `warn` */

DROP TABLE IF EXISTS `warn`;

CREATE TABLE `warn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `warncontent` varchar(255) DEFAULT NULL,
  `warnstate` int(11) DEFAULT NULL,
  `warntime` datetime DEFAULT NULL,
  `stationId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `warn` */

insert  into `warn`(`id`,`warncontent`,`warnstate`,`warntime`,`stationId`) values (2,'2',0,'2012-09-08 00:00:00',1),(5,'fds',0,'3911-03-01 00:00:00',4);

/*Table structure for table `warn_segment` */

DROP TABLE IF EXISTS `warn_segment`;

CREATE TABLE `warn_segment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `warntime` datetime NOT NULL,
  `station_start` varchar(32) NOT NULL,
  `station_end` varchar(32) NOT NULL,
  `warncontent` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `warn_segment` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
