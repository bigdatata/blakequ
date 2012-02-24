/*
SQLyog Ultimate v9.01 
MySQL - 5.0.15-nt : Database - stations
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`stations` /*!40100 DEFAULT CHARACTER SET gbk */;

USE `stations`;

/*Table structure for table `computer` */

DROP TABLE IF EXISTS `computer`;

CREATE TABLE `computer` (
  `id` int(11) NOT NULL auto_increment,
  `ip` varchar(255) default NULL,
  `state` int(11) default NULL,
  `os` varchar(255) default NULL,
  `station_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `station_id` (`station_id`),
  KEY `FKDC497F1B16D8F991` (`station_id`),
  CONSTRAINT `FKDC497F1B16D8F991` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `computer` */

insert  into `computer`(`id`,`ip`,`state`,`os`,`station_id`) values (1,'2',2,'2',1),(2,'3',4,'5',2),(3,'5',5,'5',4);

/*Table structure for table `computer_log` */

DROP TABLE IF EXISTS `computer_log`;

CREATE TABLE `computer_log` (
  `id` int(11) NOT NULL auto_increment,
  `computer_id` int(11) default NULL,
  `mem_rate` float default NULL,
  `cup_rate` float default NULL,
  `curr_time` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK8F1B4C2014CE5E63` (`computer_id`),
  CONSTRAINT `FK8F1B4C2014CE5E63` FOREIGN KEY (`computer_id`) REFERENCES `computer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `computer_log` */

insert  into `computer_log`(`id`,`computer_id`,`mem_rate`,`cup_rate`,`curr_time`) values (1,1,1,32,'2011-09-09 00:00:00'),(3,1,5,4,'2011-09-09 00:30:00'),(4,3,5,4,'2001-09-09 00:00:00'),(5,3,5,4,'2011-09-10 00:00:00'),(6,1,5,4,'2014-09-09 00:00:00');

/*Table structure for table `port` */

DROP TABLE IF EXISTS `port`;

CREATE TABLE `port` (
  `id` int(11) NOT NULL auto_increment,
  `router_id` int(11) NOT NULL,
  `get_time` datetime default NULL COMMENT '如果异常记录时间，正常不记录',
  `ifIndex` int(11) default NULL,
  `ifDescr` varchar(32) default NULL,
  `ifOperStatus` int(11) default NULL COMMENT '0(正常)，1(异常)，2(未知)',
  `ifInOctets` int(11) default NULL COMMENT '流量',
  `locIfInCRC` int(11) default NULL,
  `ipRouteDest` varchar(64) default NULL,
  `portIP` varchar(64) default NULL,
  `locIfInBitsSec` int(11) default NULL,
  `locIfOutBitsSec` int(11) default NULL,
  `ifOutOctets` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_Reference_4` (`router_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`router_id`) REFERENCES `router` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `port` */

insert  into `port`(`id`,`router_id`,`get_time`,`ifIndex`,`ifDescr`,`ifOperStatus`,`ifInOctets`,`locIfInCRC`,`ipRouteDest`,`portIP`,`locIfInBitsSec`,`locIfOutBitsSec`,`ifOutOctets`) values (1,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,2,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `route` */

DROP TABLE IF EXISTS `route`;

CREATE TABLE `route` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(20) NOT NULL,
  `station_num` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `route` */

insert  into `route`(`id`,`name`,`station_num`) values (1,'深广线',5),(2,'成都线',3),(3,'成昆线',4),(4,'成渝线',7);

/*Table structure for table `router` */

DROP TABLE IF EXISTS `router`;

CREATE TABLE `router` (
  `id` int(11) NOT NULL auto_increment,
  `router_ip` varchar(255) NOT NULL,
  `state` int(11) default NULL,
  `port_count` int(11) default NULL,
  `router_info` varchar(255) default NULL,
  `station_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `station_id` (`station_id`),
  KEY `FKC8DB974916D8F991` (`station_id`),
  CONSTRAINT `FKC8DB974916D8F991` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `router` */

insert  into `router`(`id`,`router_ip`,`state`,`port_count`,`router_info`,`station_id`) values (1,'2',2,2,'2',1),(2,'21',1,21,'32',2),(3,'1111111',NULL,3,'44444444',3),(4,'654',654,6574,'654',4);

/*Table structure for table `router_log` */

DROP TABLE IF EXISTS `router_log`;

CREATE TABLE `router_log` (
  `id` int(11) NOT NULL auto_increment,
  `router_id` int(11) default NULL,
  `error_packet` int(11) default NULL,
  `cpu_rate` float default NULL,
  `mem_rate` float default NULL,
  `router_info` varchar(255) default NULL,
  `curr_time` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK37699D4EE7FE55E3` (`router_id`),
  CONSTRAINT `FK37699D4EE7FE55E3` FOREIGN KEY (`router_id`) REFERENCES `router` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `router_log` */

insert  into `router_log`(`id`,`router_id`,`error_packet`,`cpu_rate`,`mem_rate`,`router_info`,`curr_time`) values (2,2,6,5,4,'3','2011-08-09 00:00:00'),(4,3,6,5,4,'3','2001-09-09 00:00:00'),(5,4,6,5,4,'3','2011-09-09 03:00:00'),(6,4,6,5,4,'3','2011-09-09 03:40:00'),(7,4,4,4,4,'4','2011-09-09 03:30:00');

/*Table structure for table `segment` */

DROP TABLE IF EXISTS `segment`;

CREATE TABLE `segment` (
  `id` int(11) NOT NULL auto_increment,
  `station1_id` int(11) default NULL,
  `station2_id` int(11) default NULL,
  `route_id` int(11) NOT NULL,
  `state` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK75A49F3366AF84A8` (`station1_id`),
  KEY `FK75A49F3366AFF907` (`station2_id`),
  CONSTRAINT `FK75A49F3366AF84A8` FOREIGN KEY (`station1_id`) REFERENCES `station` (`id`),
  CONSTRAINT `FK75A49F3366AFF907` FOREIGN KEY (`station2_id`) REFERENCES `station` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `segment` */

insert  into `segment`(`id`,`station1_id`,`station2_id`,`route_id`,`state`) values (2,1,3,3,3),(3,3,3,3,1),(4,4,5,3,1),(5,6,7,1,0),(6,7,8,1,0),(10,1,9,2,0),(12,1,26,2,0),(13,26,9,2,0),(14,2,26,3,0),(15,NULL,NULL,4,0),(16,29,31,4,0),(17,29,32,4,0),(18,32,31,4,0),(19,31,33,4,0),(20,33,32,4,0),(21,32,34,4,0);

/*Table structure for table `station` */

DROP TABLE IF EXISTS `station`;

CREATE TABLE `station` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `state` int(11) default NULL,
  `x` varchar(255) default NULL,
  `y` varchar(255) default NULL,
  `segment_num` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `station` */

insert  into `station`(`id`,`name`,`state`,`x`,`y`,`segment_num`) values (1,'TDCS021',0,'2','2',2),(2,'32',1,'2','2',2),(3,'TDCSa3',1,'3','3',2),(4,'ggg',1,'3','2',2),(5,'fTDCSdas',1,'3','2',2),(6,'ds',0,'4.0','4.0',2),(7,'1as',0,'5','5',2),(8,'asd',0,'6','4',2),(9,'fdd',0,'7','5',2),(26,'都江堰',0,'43.0','22.0',2),(29,'成都站TDCS',0,'432.0','432.0',2),(30,'32',0,'0','0',0),(31,'绵阳站',0,'321.0','44.0',2),(32,'广元站',0,'432.0','44.0',2),(33,'成光站',0,'12.0','12.0',2),(34,'管里站',0,'33333.0','22222.0',2),(35,'43',0,'1.0','1.0',2),(36,'32111111',0,'321.0','32.0',2),(37,'76',0,'67.0','765.0',2);

/*Table structure for table `system` */

DROP TABLE IF EXISTS `system`;

CREATE TABLE `system` (
  `id` int(11) NOT NULL auto_increment,
  `config_key` varchar(255) NOT NULL,
  `config_value` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `system` */

insert  into `system`(`id`,`config_key`,`config_value`) values (5,'3qqq','133333333'),(8,'rate2','12'),(9,'rate3','13'),(10,'rate4','14'),(13,'key1','key'),(14,'key1','key11111'),(17,'32','多发点'),(18,'key1','key111'),(19,'32qq','多发点'),(20,'fda','key111'),(21,'rate4','144444'),(22,'3111','133333333');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `authority` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`authority`) values (5,'ddd','432','admin'),(6,'fdafff','432','user'),(8,'admin','admin','admin'),(12,'test2','123','user'),(13,'test2222','67','user'),(14,'test1','11','admin'),(15,'ad','ad','user'),(16,'32','32','user'),(21,'dddd','fda','user'),(22,'范德萨','dd','user');

/*Table structure for table `warn` */

DROP TABLE IF EXISTS `warn`;

CREATE TABLE `warn` (
  `id` int(11) NOT NULL auto_increment,
  `warncontent` varchar(255) default NULL,
  `warnstate` int(11) default NULL,
  `warntime` datetime default NULL,
  `stationId` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `warn` */

insert  into `warn`(`id`,`warncontent`,`warnstate`,`warntime`,`stationId`) values (2,'2',0,'2012-09-08 00:00:00',1),(4,NULL,2,'2009-09-08 00:00:00',3),(5,'fds',0,'3911-03-01 00:00:00',4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
