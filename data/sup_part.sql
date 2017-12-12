/*
SQLyog v4.06 RC1
Host - 5.1.54-community : Database - sup_part
*********************************************************************
Server version : 5.1.54-community
*/


create database if not exists `sup_part`;

USE `sup_part`;

/*Table structure for table `part` */

drop table if exists `part`;

CREATE TABLE `part` (
  `part_no` varchar(10) NOT NULL,
  `part_name` varchar(30) DEFAULT NULL,
  `color` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`part_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `part` */

insert into `part` values ('p1','Nut','Red'),('p2','Bolt','Green'),('p3','Screw','Blue'),('p4','Bolt','Red');

/*Table structure for table `shipment` */

drop table if exists `shipment`;

CREATE TABLE `shipment` (
  `sup_no` varchar(10) DEFAULT NULL,
  `part_no` varchar(10) DEFAULT NULL,
  `date` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `shipment` */

insert into `shipment` values ('s1','p1','1/1/13'),('s1','p2','1/1/13'),('s2','p1','1/1/13'),('s2','p3','2/1/13'),('s3','p1','2/1/13'),('s3','p3','2/1/13');

/*Table structure for table `supplier` */

drop table if exists `supplier`;

CREATE TABLE `supplier` (
  `sup_no` varchar(10) NOT NULL,
  `sup_name` varchar(30) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`sup_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `supplier` */

insert into `supplier` values ('s1','Ram','Kolkata'),('s2','Hari','Mumbai'),('s3','Bob','Mumbai');
