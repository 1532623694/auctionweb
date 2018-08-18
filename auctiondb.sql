/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 10.1.19-MariaDB : Database - autiondb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`autiondb` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `autiondb`;

/*Table structure for table `auction` */

DROP TABLE IF EXISTS `auction`;

CREATE TABLE `auction` (
  `auctionId` int(11) NOT NULL AUTO_INCREMENT,
  `auctionName` varchar(50) NOT NULL,
  `auctionStartPrice` decimal(9,2) NOT NULL,
  `auctionUpset` decimal(9,2) NOT NULL,
  `auctionStartTime` datetime NOT NULL,
  `auctionEndTime` datetime NOT NULL,
  `auctionPic` varchar(50) NOT NULL,
  `auctionPicType` varchar(20) NOT NULL,
  `auctionDesc` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`auctionId`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `auction` */

insert  into `auction`(`auctionId`,`auctionName`,`auctionStartPrice`,`auctionUpset`,`auctionStartTime`,`auctionEndTime`,`auctionPic`,`auctionPicType`,`auctionDesc`) values (2,'旧电脑','100.00','100.00','2017-04-20 00:00:00','2017-04-30 00:00:00','book_10.gif','image/gif','very good'),(3,'旧电脑3','100.00','100.00','2017-04-20 00:00:00','2017-04-30 00:00:00','book_05.gif','image/gif','very good'),(4,'旧电脑2','100.00','100.00','2017-04-20 00:00:00','2017-04-30 00:00:00','book_04.gif','image/gif','very good'),(5,'旧桌子','200.00','200.00','2017-04-20 00:00:00','2017-04-30 00:00:00','images/33232242.jpg','jpg','very good'),(7,'旧电脑5','100.00','100.00','2017-04-20 00:00:00','2017-04-30 00:00:00','book_01.gif','image/gif','very good'),(8,'旧桌子2','200.00','200.00','2017-04-20 00:00:00','2017-04-30 00:00:00','book_10.gif','image/gif','very good'),(9,'旧桌子3','200.00','200.00','2017-04-20 00:00:00','2017-04-30 00:00:00','book_09.gif','image/gif','very good'),(11,'花瓶','1000.00','1000.00','2017-04-23 00:00:00','2017-04-30 00:00:00','book_09.gif','image/gif','ssss'),(12,'二手花瓶','90.00','90.00','2017-04-17 00:00:00','2017-04-28 00:00:00','ad20.jpg','jpg','sfsfs'),(13,'旧碗','56.00','56.00','2017-04-23 00:00:00','2017-04-30 00:00:00','ad20.jpg','jpg','sss'),(14,'显示器','1222.00','1222.00','2017-04-24 00:00:00','2017-04-30 00:00:00','ad20.jpg','jpg','dsds'),(15,'旧花瓶100','500.00','500.00','2017-05-22 00:00:00','2017-05-31 00:00:00','ad20.jpg','image/jpeg','sfsf'),(16,'旧拖鞋啊啊啊','10.00','10.00','2017-06-14 00:00:00','2017-06-30 00:00:00','考试.JPG','image/jpeg','标杆'),(20,'AK74','100.00','100.00','2017-07-22 00:00:00','2017-07-31 00:00:00','book_01.gif','gif','好'),(21,'旧尿壶22222','2222.00','2222.00','2018-06-10 00:00:00','2018-07-28 00:00:00','img4.jpg','image/jpeg','很好使');

/*Table structure for table `auctionrecord` */

DROP TABLE IF EXISTS `auctionrecord`;

CREATE TABLE `auctionrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `auctionId` int(11) NOT NULL,
  `auctionTime` datetime NOT NULL,
  `auctionPrice` decimal(9,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rec_REF_user` (`userId`),
  KEY `FK_rec_REF_AUC` (`auctionId`),
  CONSTRAINT `FK_rec_REF_AUC` FOREIGN KEY (`auctionId`) REFERENCES `auction` (`auctionId`),
  CONSTRAINT `FK_rec_REF_user` FOREIGN KEY (`userId`) REFERENCES `auctionuser` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `auctionrecord` */

insert  into `auctionrecord`(`id`,`userId`,`auctionId`,`auctionTime`,`auctionPrice`) values (6,5,2,'2017-04-25 09:27:40','123.00'),(7,5,15,'2017-05-22 10:15:03','600.00'),(8,5,15,'2017-05-25 15:21:29','700.00'),(9,5,16,'2017-06-14 20:45:57','19.00'),(10,5,20,'2017-07-18 15:33:10','110.00'),(11,5,20,'2017-07-18 15:33:45','120.00'),(12,10,21,'2018-06-14 10:30:09','2223.00'),(13,10,21,'2018-06-14 10:53:34','22224.00'),(14,10,21,'2018-06-14 11:33:46','22235.00'),(15,10,21,'2018-06-15 11:14:09','666666.00'),(16,10,21,'2018-06-19 10:23:50','6666666.00');

/*Table structure for table `auctionuser` */

DROP TABLE IF EXISTS `auctionuser`;

CREATE TABLE `auctionuser` (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表',
  `userName` varchar(20) NOT NULL,
  `userPassword` varchar(20) NOT NULL,
  `userCardNo` varchar(20) DEFAULT NULL,
  `userTel` varchar(20) DEFAULT NULL,
  `userAddress` varchar(200) DEFAULT NULL,
  `userPostNumber` varchar(6) DEFAULT NULL,
  `userIsadmin` int(11) DEFAULT '0',
  `userQuestion` varchar(100) DEFAULT NULL,
  `userAnswer` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `auctionuser` */

insert  into `auctionuser`(`userId`,`userName`,`userPassword`,`userCardNo`,`userTel`,`userAddress`,`userPostNumber`,`userIsadmin`,`userQuestion`,`userAnswer`) values (1,'旺财','8888',NULL,NULL,NULL,NULL,0,NULL,NULL),(4,'Mike','00000',NULL,NULL,NULL,NULL,1,NULL,NULL),(5,'test1','999','123456789012345678','8787878','广东省广州','600600',0,'',''),(7,'张三','0000',NULL,NULL,NULL,NULL,1,NULL,NULL),(8,'李四','10000','123456789012345678',NULL,NULL,'511500',0,NULL,NULL),(9,'Jack','99999','12345646456464','99999999','gangdong','599599',0,'',''),(10,'kk','11','123456789012345','12234','sadfsaf','23443',0,'',''),(11,'asd','dddddd','123456789012345678','111','111','111',NULL,NULL,NULL),(12,'asd','dddddd','123456789012345678','111','111','111',NULL,NULL,NULL),(13,'qqq','123456','123456789123456789','12345678','111','111',0,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
