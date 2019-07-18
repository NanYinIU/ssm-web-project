/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.42-log : Database - nyoa
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`nyoa` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `nyoa`;

/*Table structure for table `auth` */

DROP TABLE IF EXISTS `auth`;

CREATE TABLE `auth` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `ord` int(32) DEFAULT NULL,
  `comm` varchar(256) DEFAULT NULL,
  `create_data` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `auth` */

insert  into `auth`(`id`,`name`,`ord`,`comm`,`create_data`) values (1,'权限1',1,NULL,NULL);

/*Table structure for table `icon` */

DROP TABLE IF EXISTS `icon`;

CREATE TABLE `icon` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `icon_name` varchar(256) DEFAULT NULL,
  `icon_unicode` varchar(256) DEFAULT NULL,
  `icon_class` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `icon` */

insert  into `icon`(`id`,`icon_name`,`icon_unicode`,`icon_class`) values (1,'半星','&#xe6c9;','layui-icon-rate-half'),(3,'手机','&#xe678;','layui-icon-cellphone'),(4,'验证码','&#xe679;','layui-icon-vercode'),(7,'微博2','&#xe675;','layui-icon-login-weibo'),(8,'密码','&#xe673;','layui-icon-password'),(9,'用户名','&#xe66f;','layui-icon-username'),(10,'刷新-粗','&#xe9aa;','layui-icon-refresh-3'),(11,'授权','&#xe672;','layui-icon-auz'),(12,'左向右伸缩菜单','&#xe66b;','layui-icon-spread-left'),(13,'提示说明','&#xe702;','layui-icon-tips'),(14,'便签','&#xe66e;','layui-icon-note'),(15,'主页','&#xe68e;','layui-icon-home'),(16,'控制台','&#xe665;','layui-icon-console'),(17,'设置-空心','&#xe716;','layui-icon-set'),(18,'应用','&#xe653;','layui-icon-app'),(19,'布局','&#xe632','layui-icon-layouts');

/*Table structure for table `r_nav_category_user` */

DROP TABLE IF EXISTS `r_nav_category_user`;

CREATE TABLE `r_nav_category_user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `user_id` int(32) DEFAULT NULL,
  `nav_category_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_r_nav_category_user_001` (`user_id`),
  KEY `fk_r_nav_category_user_002` (`nav_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `r_nav_category_user` */

insert  into `r_nav_category_user`(`id`,`user_id`,`nav_category_id`) values (1,1,1);

/*Table structure for table `r_nav_user` */

DROP TABLE IF EXISTS `r_nav_user`;

CREATE TABLE `r_nav_user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `nav_id` int(32) DEFAULT NULL,
  `user_id` int(32) DEFAULT NULL,
  `category_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `r_nav_user` */

insert  into `r_nav_user`(`id`,`nav_id`,`user_id`,`category_id`) values (1,1,1,1),(2,2,1,1),(3,3,1,1);

/*Table structure for table `r_user_auth` */

DROP TABLE IF EXISTS `r_user_auth`;

CREATE TABLE `r_user_auth` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `user_id` int(32) DEFAULT NULL,
  `auth_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_r_user_auth_001` (`user_id`),
  KEY `fk_r_user_auth_002` (`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `r_user_auth` */

/*Table structure for table `r_user_role` */

DROP TABLE IF EXISTS `r_user_role`;

CREATE TABLE `r_user_role` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `user_id` int(32) DEFAULT NULL,
  `role_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_r_user_role_001` (`user_id`),
  KEY `fk_r_user_role_002` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `r_user_role` */

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `ord` int(32) DEFAULT NULL,
  `comm` varchar(256) DEFAULT NULL,
  `create_data` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`ord`,`comm`,`create_data`) values (1,'角色1',1,NULL,NULL);

/*Table structure for table `s_nav_bar` */

DROP TABLE IF EXISTS `s_nav_bar`;

CREATE TABLE `s_nav_bar` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `href` varchar(256) DEFAULT NULL,
  `parent_nav_id` int(32) DEFAULT NULL,
  `icon` varchar(256) DEFAULT NULL,
  `is_deleted` int(32) DEFAULT NULL,
  `ord` int(32) DEFAULT NULL,
  `spread` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `s_nav_bar` */

insert  into `s_nav_bar`(`id`,`name`,`href`,`parent_nav_id`,`icon`,`is_deleted`,`ord`,`spread`) values (1,'内容管理',NULL,NULL,'&#xe66e;',0,1,1),(2,'图标管理','/icon/iconManage',1,'&#xe66e;',0,2,0),(3,'navBar管理','/nav/navManage',1,'&#xe66e;',0,3,0);

/*Table structure for table `s_nav_category` */

DROP TABLE IF EXISTS `s_nav_category`;

CREATE TABLE `s_nav_category` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `comment` varchar(256) DEFAULT NULL,
  `ord` int(32) DEFAULT NULL,
  `icon` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `s_nav_category` */

insert  into `s_nav_category`(`id`,`name`,`comment`,`ord`,`icon`) values (1,'系统管理',NULL,1,'&#xe66e;');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `salt` varchar(32) DEFAULT NULL,
  `age` int(4) DEFAULT NULL,
  `sex` varchar(16) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `unitId` int(32) DEFAULT NULL,
  `status` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`password`,`salt`,`age`,`sex`,`create_time`,`unitId`,`status`) values (1,'admin','/yUpk6RV488=',NULL,24,'男','2018-12-10',NULL,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
