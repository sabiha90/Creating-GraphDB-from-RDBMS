/*
SQLyog v4.06 RC1
Host - 5.1.54-community : Database - neo4j
*********************************************************************
Server version : 5.1.54-community
*/


create database if not exists `neo4j`;

USE `neo4j`;

/*Table structure for table `marks_details` */

drop table if exists `marks_details`;

CREATE TABLE `marks_details` (
  `roll_no` varchar(10) DEFAULT NULL,
  `sub_code` varchar(10) DEFAULT NULL,
  `marks_obt` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `marks_details` */

insert into `marks_details` values ('1','s1',89),('2','s5',35),('2','s8',68),('3','s8',75);

/*Table structure for table `student` */

drop table if exists `student`;

CREATE TABLE `student` (
  `roll_no` varchar(10) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `dob` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`roll_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `student` */

insert into `student` values ('1','Ram','16-08-1999'),('2','Hari','19-05-2003'),('3','Rahul','16-08-1999');

/*Table structure for table `subject` */

drop table if exists `subject`;

CREATE TABLE `subject` (
  `sub_code` varchar(10) NOT NULL,
  `sub_name` varchar(30) DEFAULT NULL,
  `tot_marks` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`sub_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `subject` */

insert into `subject` values ('s1','Maths','100'),('s2','Hindi','50'),('s3','English','100');
