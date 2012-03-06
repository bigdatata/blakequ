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
CREATE DATABASE /*!32312 IF NOT EXISTS*/`stations` /*!40100 DEFAULT CHARACTER SET utf8 */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `computer` */

insert  into `computer`(`id`,`ip`,`state`,`os`,`station_id`) values (1,'192.168.3.30',1,'unix33,win-7,pentium--4400',95),(2,'192.168.1.49',1,'windows33,win-7,pentium--4400',96);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `computer_log` */

insert  into `computer_log`(`id`,`computer_id`,`mem_rate`,`cup_rate`,`curr_time`) values (1,NULL,1,32,'2011-09-09 00:00:00'),(3,NULL,5,4,'2011-09-09 00:30:00'),(4,NULL,5,4,'2001-09-09 00:00:00'),(5,NULL,5,4,'2011-09-10 00:00:00'),(6,1,45.4877,12,'2012-03-01 22:29:49'),(7,1,45.4877,12,'2012-03-01 22:38:16'),(8,1,45.4877,12,'2012-03-01 22:44:00'),(9,1,45.4877,12,'2012-03-01 22:46:52'),(10,1,45.4877,12,'2012-03-01 23:01:29'),(11,1,45.4877,12,'2012-03-01 23:01:42'),(12,1,45.4877,12,'2012-03-01 23:12:26'),(13,2,45.4877,12,'2012-03-02 10:53:38'),(14,2,45.4877,12,'2012-03-02 10:56:49'),(15,2,45.4877,12,'2012-03-02 11:06:05'),(16,2,45.4877,12,'2012-03-02 11:08:40'),(17,1,45.4877,12,'2012-03-02 11:10:31'),(18,1,45.4877,12,'2012-03-02 11:22:55'),(19,2,45.4877,12,'2012-03-02 11:23:40'),(20,2,45.4877,12,'2012-03-02 11:24:49'),(21,1,45.4877,12,'2012-03-02 11:25:05'),(22,1,45.4877,12,'2012-03-02 11:28:52'),(23,2,45.4877,12,'2012-03-02 11:29:01'),(24,2,45.4877,12,'2012-03-02 11:38:30'),(25,1,45.4877,12,'2012-03-02 11:38:46'),(26,1,45.4877,12,'2012-03-02 11:43:34'),(27,1,45.4877,12,'2012-03-02 13:48:32'),(28,2,45.4877,12,'2012-03-02 13:48:59'),(29,2,45.4877,12,'2012-03-02 13:53:40'),(30,1,45.4877,12,'2012-03-02 13:53:54'),(31,1,45.4877,12,'2012-03-02 13:59:25'),(32,2,45.4877,12,'2012-03-02 13:59:42'),(33,1,45.4877,12,'2012-03-02 14:00:39'),(34,2,45.4877,12,'2012-03-02 14:10:15'),(35,1,45.4877,12,'2012-03-02 14:12:26'),(36,2,45.4877,12,'2012-03-02 14:28:05'),(37,1,45.4877,12,'2012-03-02 14:28:14'),(38,1,45.4877,12,'2012-03-02 14:29:09');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `port` */

insert  into `port`(`id`,`router_id`,`get_time`,`ifIndex`,`ifDescr`,`ifOperStatus`,`ifInOctets`,`locIfInCRC`,`ipRouteDest`,`portIP`,`locIfInBitsSec`,`locIfOutBitsSec`,`ifOutOctets`) values (9,6,'2012-03-02 14:28:06',1,'FastEthernet0',1,40732982,0,'192.168.1.0','192.168.1.49',20000,44000,87578678),(10,6,'2012-03-02 14:28:06',2,'Serial0',20,0,0,'','',0,0,0),(11,6,'2012-03-02 14:28:06',3,'Serial1',22,0,0,'','',0,0,0),(12,6,'2012-03-02 14:28:06',4,'Null0',21,0,0,'','',0,0,0),(13,5,'2012-03-02 14:29:09',1,'FastEthernet0',11,40732982,0,'192.168.1.0','192.168.3.30',20000,44000,87578678),(14,5,'2012-03-02 14:29:09',2,'Serial0',11,1,0,'','',0,0,0),(15,5,'2012-03-02 14:29:09',3,'Serial1',22,1,0,'','',0,0,0),(16,5,'2012-03-02 14:29:09',4,'Null0',22,0,0,'','',0,0,0);

/*Table structure for table `route` */

DROP TABLE IF EXISTS `route`;

CREATE TABLE `route` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(20) NOT NULL,
  `station_num` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `route` */

insert  into `route`(`id`,`name`,`station_num`) values (1,'深广线',5),(2,'成都线',3),(3,'成昆线',4),(4,'成渝线',7),(5,'成绵线',3),(6,'成广线',3),(7,'成乐线',3);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `router` */

insert  into `router`(`id`,`router_ip`,`state`,`port_count`,`router_info`,`station_id`) values (4,'654',654,6574,'654',4),(5,'192.168.3.30',1,4,'linux，2.6.35',95),(6,'192.168.1.49',1,4,'linux，2.6.35',96);

/*Table structure for table `router_log` */

DROP TABLE IF EXISTS `router_log`;

CREATE TABLE `router_log` (
  `id` int(11) NOT NULL auto_increment,
  `router_id` int(11) default NULL,
  `cpu_rate` float default NULL,
  `mem_rate` float default NULL,
  `router_info` varchar(255) default NULL,
  `curr_time` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK37699D4EE7FE55E3` (`router_id`),
  CONSTRAINT `FK37699D4EE7FE55E3` FOREIGN KEY (`router_id`) REFERENCES `router` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `router_log` */

insert  into `router_log`(`id`,`router_id`,`cpu_rate`,`mem_rate`,`router_info`,`curr_time`) values (2,4,5,4,'3','2011-08-09 00:00:00'),(4,5,5,4,'3','2001-09-09 00:00:00'),(5,4,5,4,'3','2011-09-09 03:00:00'),(6,4,5,4,'3','2011-09-09 03:40:00'),(7,4,4,4,'4','2011-09-09 03:30:00'),(8,NULL,57,8.68938,'linux???2.6.35','2012-03-01 22:29:50'),(9,NULL,57,8.68938,'linux???2.6.35','2012-03-01 22:38:16'),(10,5,57,8.68938,'linux???2.6.35','2012-03-01 22:44:00'),(11,5,57,8.68938,'linux???2.6.35','2012-03-01 22:46:53'),(12,5,57,8.68938,'linux，2.6.35','2012-03-01 23:01:29'),(13,5,57,8.68938,'linux，2.6.35','2012-03-01 23:01:42'),(14,5,57,8.68938,'linux，2.6.35','2012-03-01 23:12:27'),(15,6,45,7.28338,'linux??2.6.35','2012-03-02 10:53:39'),(16,6,45,7.28338,'linux??2.6.35','2012-03-02 10:56:50'),(17,6,45,7.28338,'linux??2.6.35','2012-03-02 11:06:06'),(18,6,45,7.28338,'linux??2.6.35','2012-03-02 11:08:40'),(19,5,57,8.68938,'linux??2.6.35','2012-03-02 11:10:32'),(20,5,57,8.68938,'linux，2.6.35','2012-03-02 11:22:55'),(21,6,45,7.28338,'linux，2.6.35','2012-03-02 11:23:40'),(22,6,45,7.28338,'linux，2.6.35','2012-03-02 11:24:49'),(23,5,57,8.68938,'linux，2.6.35','2012-03-02 11:25:05'),(24,5,57,8.68938,'linux，2.6.35','2012-03-02 11:28:53'),(25,6,45,7.28338,'linux，2.6.35','2012-03-02 11:29:02'),(26,6,45,7.28338,'linux，2.6.35','2012-03-02 11:38:30'),(27,5,57,8.68938,'linux，2.6.35','2012-03-02 11:38:46'),(28,5,57,8.68938,'linux，2.6.35','2012-03-02 11:43:34'),(29,5,57,8.68938,'linux，2.6.35','2012-03-02 13:48:32'),(30,6,45,7.28338,'linux，2.6.35','2012-03-02 13:48:59'),(31,6,45,7.28338,'linux，2.6.35','2012-03-02 13:53:40'),(32,5,57,8.68938,'linux，2.6.35','2012-03-02 13:53:54'),(33,5,57,8.68938,'linux，2.6.35','2012-03-02 13:59:25'),(34,6,45,7.28338,'linux，2.6.35','2012-03-02 13:59:42'),(35,5,57,8.68938,'linux，2.6.35','2012-03-02 14:00:39'),(36,6,45,7.28338,'linux??2.6.35','2012-03-02 14:10:15'),(37,5,57,8.68938,'linux，2.6.35','2012-03-02 14:12:26'),(38,6,45,7.28338,'linux，2.6.35','2012-03-02 14:28:06'),(39,5,57,8.68938,'linux，2.6.35','2012-03-02 14:28:15'),(40,5,57,8.68938,'linux，2.6.35','2012-03-02 14:29:09');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `segment` */

insert  into `segment`(`id`,`station1_id`,`station2_id`,`route_id`,`state`) values (2,1,3,3,3),(3,3,3,3,1),(4,4,5,3,1),(5,6,7,1,0),(6,7,8,1,0),(10,1,9,2,0),(12,1,26,2,0),(13,26,9,2,0),(14,2,26,3,0),(31,81,82,6,0),(32,82,83,6,0),(34,86,87,5,0),(35,87,88,5,0),(49,93,94,7,0),(50,94,95,7,0),(51,95,96,7,0);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `station` */

insert  into `station`(`id`,`name`,`state`,`x`,`y`,`segment_num`) values (1,'TDCS021',0,'2','2',2),(2,'32',1,'2','2',2),(3,'TDCSa3',1,'3','3',2),(4,'ggg',1,'323','111',2),(5,'fTDCSdas',1,'3','2',2),(6,'ds',0,'4.0','4.0',2),(7,'1as',0,'5','5',2),(8,'asd',0,'6','4',2),(9,'fdd',0,'7','5',2),(26,'都江堰',0,'43.0','22.0',2),(29,'成站TDCS',0,'345','651',2),(30,'32',0,'120','342',0),(31,'绵阳站',0,'781','432',2),(32,'广元站',0,'342','123',2),(33,'成光站',0,'234','213',2),(34,'管里站',0,'1','43',2),(35,'43',0,'43','33',2),(36,'32111111',0,'65','12',2),(37,'76',0,'67.0','121',2),(81,'广1站',0,'213','332',2),(82,'广2站',0,'223','33',2),(83,'广州站',0,'233','324',2),(86,'成2站',0,'213','332',2),(87,'成3站',0,'223','33',2),(88,'锦州站',0,'233','324',2),(93,'TDCS成都站',0,'23','32',2),(94,'乐1站',0,'213','332',2),(95,'乐2站',0,'223','33',2),(96,'乐山站',0,'233','324',2),(97,'????',0,'0','0',0),(98,'??2?',0,'0','0',0);

/*Table structure for table `system` */

DROP TABLE IF EXISTS `system`;

CREATE TABLE `system` (
  `id` int(11) NOT NULL auto_increment,
  `config_key` varchar(255) NOT NULL,
  `config_value` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system` */

insert  into `system`(`id`,`config_key`,`config_value`) values (1,'frequency','1');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `authority` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`authority`) values (5,'ddd','432','admin'),(6,'fdafff','432','user'),(8,'admin','admin','admin'),(12,'test2','123','user'),(13,'test2222','67','user'),(14,'test1','11','admin'),(15,'ad','ad','user'),(16,'32','32','user'),(21,'dddd','fda','user'),(22,'范德萨','dd','user'),(23,'hao','123','user');

/*Table structure for table `warn` */

DROP TABLE IF EXISTS `warn`;

CREATE TABLE `warn` (
  `id` int(11) NOT NULL auto_increment,
  `warncontent` varchar(255) default NULL,
  `warnstate` int(11) default NULL,
  `warntime` datetime default NULL,
  `stationId` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `warn` */

insert  into `warn`(`id`,`warncontent`,`warnstate`,`warntime`,`stationId`) values (7,'null 站点靖江站台账文件读取异常! 端口0:状态由UP-->DOWN 3:状态由DOWN-->UP ',1,'2012-02-28 17:07:03',81),(8,'站点天津站台账文件读取异常! 端口0:状态由DOWN-->UP 1:状态由DOWN-->UP 3:状态由DOWN-->UP ',1,'2012-02-28 17:16:41',82),(9,'站点靖江站台账文件读取异常! 端口0:状态由UP-->DOWN 3:状态由DOWN-->UP ',1,'2012-02-28 17:17:43',82),(10,'站点靖江站台账文件读取异常! 端口0:状态由UP-->DOWN 3:状态由DOWN-->UP ',1,'2012-02-28 17:19:26',83),(12,'站点???é?????台账文件读取异常! 端口0:状态由UP-->DOWN 3:状态由DOWN-->UP ',1,'2012-03-01 22:38:16',97),(13,'站点???é?????台账文件读取异常! 端口0:状态由UP-->DOWN 3:状态由DOWN-->UP ',1,'2012-03-01 22:44:01',97),(14,'站点???é?????台账文件读取异常! 端口0:状态由UP-->DOWN 3:状态由DOWN-->UP ',1,'2012-03-01 22:46:53',97),(15,'站点绵阳站台账文件读取异常! 端口0:状态由UP-->DOWN 3:状态由DOWN-->UP ',1,'2012-03-01 23:01:30',31),(16,'站点绵阳站台账文件读取异常! 端口0:状态由UP-->DOWN 3:状态由DOWN-->UP  站点绵阳站台账文件读取异常! 端口0:状态由UP-->DOWN 3:状态由DOWN-->UP ',1,'2012-03-01 23:01:42',31),(17,'站点绵阳站台账文件读取异常! 端口0:状态由UP-->DOWN 3:状态由DOWN-->UP ',1,'2012-03-01 23:12:27',31),(18,'端口0:状态由INIT-->UP(DOWN) 1:状态由DOWN-->UP 3:状态由DOWN-->UP ',1,'2012-03-02 11:08:40',97),(19,'端口0:状态由INIT-->UP(DOWN) 1:状态由DOWN-->UP 3:状态由DOWN-->UP ',1,'2012-03-02 11:23:41',96),(20,'站点:乐山站状态未知！ 站点乐山站台账文件读取异常! 端口0:状态由INIT-->UP(DOWN) 1:状态由DOWN-->UP 3:状态由DOWN-->UP ',1,'2012-03-02 11:24:49',96),(21,'站点:乐2站状态未知！ 站点乐2站台账文件读取异常!',1,'2012-03-02 11:25:05',95),(22,'站点乐2站台账文件读取异常!',1,'2012-03-02 11:28:53',95),(23,'站点乐山站台账文件读取异常! 端口0:状态由INIT-->UP(DOWN) 1:状态由DOWN-->UP 3:状态由DOWN-->UP ',1,'2012-03-02 11:29:02',96),(24,'站点乐山站台账文件读取异常! 端口0:状态由INIT-->UP(DOWN) 1:状态由DOWN-->UP 3:状态由DOWN-->UP ',1,'2012-03-02 11:38:30',96),(25,'线段id=51告警，位于站点：乐2站',1,'2012-03-02 11:38:46',0),(26,'站点乐2站台账文件读取异常!',1,'2012-03-02 11:38:46',95),(27,'线段id=51告警，位于站点：乐2站',1,'2012-03-02 11:43:34',0),(28,'站点:乐2站状态未知！ 站点乐2站台账文件读取异常!',1,'2012-03-02 11:43:34',95),(29,'站点乐2站台账文件读取异常!',1,'2012-03-02 13:48:32',95),(30,'线段id=51告警，位于站点：乐山站',1,'2012-03-02 13:48:59',0),(31,'站点乐山站台账文件读取异常! 端口0:状态由INIT-->UP(DOWN) 1:状态由DOWN-->UP 3:状态由DOWN-->UP ',1,'2012-03-02 13:48:59',96),(32,'站点乐山站台账文件读取异常! 端口0:状态由INIT-->UP(DOWN) 1:状态由DOWN-->UP 3:状态由DOWN-->UP ',1,'2012-03-02 13:53:41',96),(33,'线段id=51告警，位于站点：乐2站',1,'2012-03-02 13:53:54',0),(34,'站点乐2站台账文件读取异常!',1,'2012-03-02 13:53:54',95),(35,'站点乐2站台账文件读取异常!',1,'2012-03-02 13:59:25',95),(36,'线段id=51告警，位于站点：乐山站',1,'2012-03-02 13:59:43',0),(37,'站点乐山站台账文件读取异常! 端口0:状态由INIT-->UP(DOWN) 1:状态由DOWN-->UP 3:状态由DOWN-->UP ',1,'2012-03-02 13:59:43',96),(38,'线段id=51告警，位于站点：乐2站',1,'2012-03-02 14:12:26',0),(39,'站点:乐2站状态未知！ 站点乐2站台账文件读取异常!',1,'2012-03-02 14:12:26',95),(40,'站点乐山站台账文件读取异常! 端口0:状态由INIT-->UP(DOWN) 1:状态由DOWN-->UP 3:状态由DOWN-->UP ',1,'2012-03-02 14:28:06',96),(41,'线段id=51告警，位于站点：乐2站',1,'2012-03-02 14:28:15',0),(42,'站点乐2站台账文件读取异常!',1,'2012-03-02 14:28:15',95);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
