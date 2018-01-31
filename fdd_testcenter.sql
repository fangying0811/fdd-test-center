/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.5.48 : Database - fdd_testcenter
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fdd_testcenter` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `fdd_testcenter`;

/*Table structure for table `t_automation_project` */

DROP TABLE IF EXISTS `t_automation_project`;

CREATE TABLE `t_automation_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(200) DEFAULT NULL COMMENT '项目名',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `pm` varchar(100) DEFAULT NULL COMMENT '责任人',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `team_id` bigint(20) DEFAULT NULL COMMENT '小组id',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='自动化测试项目表';

/*Table structure for table `t_department` */

DROP TABLE IF EXISTS `t_department`;

CREATE TABLE `t_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '部门名',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8 COMMENT='部门表';

/*Table structure for table `t_dingdingmanage` */

DROP TABLE IF EXISTS `t_dingdingmanage`;

CREATE TABLE `t_dingdingmanage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chat_id` varchar(255) NOT NULL,
  `group_name` varchar(100) DEFAULT NULL,
  `user_mobile` varchar(20) DEFAULT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_jenkins_build_result` */

DROP TABLE IF EXISTS `t_jenkins_build_result`;

CREATE TABLE `t_jenkins_build_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `team_id` bigint(20) DEFAULT NULL COMMENT '小组id',
  `project_name` varchar(200) DEFAULT NULL COMMENT '项目',
  `build_status` int(11) DEFAULT NULL COMMENT '1:SUCCESS,2:FAILURE,3:UNSTABLE',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `t_menu` */

DROP TABLE IF EXISTS `t_menu`;

CREATE TABLE `t_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(500) DEFAULT NULL COMMENT '菜单名',
  `url` varchar(500) DEFAULT NULL COMMENT '菜单访问URL',
  `priority` int(11) DEFAULT NULL COMMENT '优先级，数字越小优先级越高',
  `status` int(11) DEFAULT NULL COMMENT '态状，0:启用,1:禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COMMENT='菜单表';

/*Table structure for table `t_online_issue` */

DROP TABLE IF EXISTS `t_online_issue`;

CREATE TABLE `t_online_issue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '录入人员ID',
  `department_id` bigint(20) DEFAULT NULL COMMENT '录入人员所属部门',
  `team_id` bigint(20) DEFAULT NULL COMMENT '录入人员所属小组',
  `project_id` bigint(20) DEFAULT NULL COMMENT '所属项目ID',
  `issue_id` bigint(20) DEFAULT NULL COMMENT '关联redmine bug ID',
  `reason` varchar(2048) DEFAULT NULL COMMENT '问题原因',
  `solution` varchar(2048) DEFAULT NULL COMMENT '解决方案',
  `improvement` varchar(2048) DEFAULT NULL COMMENT '改进措施',
  `process` int(11) DEFAULT NULL COMMENT '分析进度，1：未分析，2：已分析',
  `resolve_status` int(11) DEFAULT NULL COMMENT '解决状态，1：未解决，2：已解决，3：不解决',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '录入时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8 COMMENT='线上bug表';

/*Table structure for table `t_outstanding_issue` */

DROP TABLE IF EXISTS `t_outstanding_issue`;

CREATE TABLE `t_outstanding_issue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '录入人员ID',
  `department_id` bigint(20) DEFAULT NULL COMMENT '录入人员所属部门',
  `team_id` bigint(20) DEFAULT NULL COMMENT '录入人员所属小组',
  `project_id` bigint(20) DEFAULT NULL COMMENT '所属项目ID',
  `issue_id` bigint(20) DEFAULT NULL COMMENT '关联redmine bug ID',
  `resolve_status` int(11) DEFAULT NULL COMMENT '解决状态，1：未解决，2：已解决，3：不解决',
  `remark` varchar(4096) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '录入时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8 COMMENT='留遗bug表';

/*Table structure for table `t_project_info` */

DROP TABLE IF EXISTS `t_project_info`;

CREATE TABLE `t_project_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '项目录入人员ID',
  `project_name` varchar(200) DEFAULT NULL COMMENT '项目名称',
  `department_id` bigint(20) DEFAULT NULL COMMENT '项目所属部门ID',
  `team_id` bigint(20) DEFAULT NULL COMMENT '小组ID',
  `tester` int(11) DEFAULT '0' COMMENT '测试总人数',
  `developer` int(11) DEFAULT '0' COMMENT '开发总人数',
  `status` int(11) DEFAULT '1' COMMENT '态状，1:启用,2:禁用',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '项目录入时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '项目修改时间',
  `redmine_project_id` int(11) DEFAULT NULL COMMENT '项目对应redmine项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='项目表';

/*Table structure for table `t_project_weekly_report` */

DROP TABLE IF EXISTS `t_project_weekly_report`;

CREATE TABLE `t_project_weekly_report` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `department_id` bigint(20) DEFAULT NULL,
  `team_id` bigint(20) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `weekly_report_id` int(11) NOT NULL,
  `master` varchar(30) DEFAULT NULL,
  `dev_counts` tinyint(4) NOT NULL,
  `per_bugs` tinyint(4) DEFAULT NULL,
  `remark` varchar(2048) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `t_requirement_info` */

DROP TABLE IF EXISTS `t_requirement_info`;

CREATE TABLE `t_requirement_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '测试需求录入人员ID',
  `department_id` bigint(20) DEFAULT NULL COMMENT '测试需求录入人员所属部门',
  `team_id` bigint(20) DEFAULT NULL COMMENT '测试需求录入人员所属小组',
  `project_id` bigint(20) DEFAULT NULL COMMENT '测试需求所属项目ID',
  `version_info` varchar(100) DEFAULT NULL COMMENT '版本描述信息',
  `resource` varchar(100) DEFAULT NULL COMMENT '测试人力资源',
  `requirement_info` varchar(2048) DEFAULT NULL COMMENT '需求描述信息',
  `plan_start_time` timestamp NULL DEFAULT NULL COMMENT '计划测试开始时间，null或空表示待定',
  `version_release_time` timestamp NULL DEFAULT NULL COMMENT '计划发布时间',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '测试需求录入时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '测试需求修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=291 DEFAULT CHARSET=utf8 COMMENT='测试需求表';

/*Table structure for table `t_sonar_project` */

DROP TABLE IF EXISTS `t_sonar_project`;

CREATE TABLE `t_sonar_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `team_id` bigint(20) DEFAULT NULL COMMENT '小组id',
  `project_name` varchar(200) DEFAULT NULL COMMENT '项目名称',
  `sonar_project_name` varchar(200) DEFAULT NULL COMMENT 'sonnar项目名称',
  `component_key` varchar(200) DEFAULT NULL COMMENT 'sonnar项目查询key',
  `pm` varchar(100) DEFAULT NULL COMMENT '开发负责人',
  `status` int(11) DEFAULT NULL COMMENT '态状，1:启用,2:禁用',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='sonnar项目表';

/*Table structure for table `t_sonar_statistic` */

DROP TABLE IF EXISTS `t_sonar_statistic`;

CREATE TABLE `t_sonar_statistic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `team_id` bigint(20) DEFAULT NULL COMMENT '小组id',
  `sonar_project_id` bigint(20) DEFAULT NULL COMMENT 'sonnar项目id',
  `blocker` int(11) DEFAULT NULL,
  `critical` int(11) DEFAULT NULL,
  `major` int(11) DEFAULT NULL,
  `info` int(11) DEFAULT NULL,
  `minor` int(11) DEFAULT NULL,
  `statistic_date` timestamp NULL DEFAULT NULL COMMENT '每周五统计',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15190 DEFAULT CHARSET=utf8 COMMENT='项目sonnar数据统计表';

/*Table structure for table `t_team` */

DROP TABLE IF EXISTS `t_team`;

CREATE TABLE `t_team` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `name` varchar(100) DEFAULT NULL COMMENT '小组名称',
  `developer` int(11) DEFAULT '0' COMMENT '开发总人数',
  `tester` int(11) DEFAULT '0' COMMENT '测试总人数',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='小组表';

/*Table structure for table `t_test_report` */

DROP TABLE IF EXISTS `t_test_report`;

CREATE TABLE `t_test_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version_info` varchar(255) NOT NULL,
  `test_result` bigint(20) DEFAULT NULL COMMENT '测试结论：0:不通过，1:通过',
  `resource` varchar(200) NOT NULL COMMENT '测试人员',
  `user_id` bigint(20) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `team_id` bigint(20) DEFAULT NULL COMMENT '所属组ID',
  `project_id` bigint(20) DEFAULT NULL COMMENT '所属项目ID',
  `true_start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '实际开始时间',
  `true_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '实际结束时间',
  `summary` varchar(4096) DEFAULT NULL COMMENT '总结',
  `version_detail` varchar(4096) DEFAULT NULL COMMENT '版本功能列表',
  `issues` varchar(4096) DEFAULT NULL COMMENT 'bug ID',
  `cases` bigint(20) DEFAULT NULL,
  `bugs` bigint(20) DEFAULT NULL,
  `bug_critical` bigint(20) DEFAULT NULL,
  `dev_tag` varchar(200) DEFAULT NULL COMMENT '转测tag',
  `release_tag` varchar(200) DEFAULT NULL COMMENT '发布tag',
  `bugs_link` varchar(1024) DEFAULT NULL,
  `requirement_link` varchar(1024) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `t_test_statistic` */

DROP TABLE IF EXISTS `t_test_statistic`;

CREATE TABLE `t_test_statistic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `build_id` bigint(20) DEFAULT NULL COMMENT '构建ID，对应t_test_sutie_statistic表id字段',
  `service_id` bigint(20) DEFAULT NULL COMMENT '服务id',
  `class_name` varchar(200) DEFAULT NULL COMMENT '用例所属类名',
  `case_name` varchar(200) DEFAULT NULL COMMENT '用例名称',
  `case_description` varchar(200) DEFAULT NULL COMMENT '用例描述',
  `case_message` longtext,
  `case_log` longtext COMMENT '用例日志',
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `duration_time` varchar(200) DEFAULT NULL COMMENT '执行耗时',
  `status` int(11) DEFAULT NULL COMMENT '状态：1：成功 2：失败 3:跳过',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8 COMMENT='测试集构建结果记录表';

/*Table structure for table `t_test_sutie_statistic` */

DROP TABLE IF EXISTS `t_test_sutie_statistic`;

CREATE TABLE `t_test_sutie_statistic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `service_id` bigint(20) DEFAULT NULL COMMENT '服务id',
  `department_id` bigint(20) DEFAULT NULL,
  `team_id` bigint(20) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `duration_time` varchar(100) DEFAULT NULL COMMENT '执行耗时',
  `passed` int(11) DEFAULT NULL COMMENT '通过用例数',
  `failed` int(11) DEFAULT NULL COMMENT '失败用例数',
  `skipped` int(11) DEFAULT NULL COMMENT '跳过用例数',
  `total` int(11) DEFAULT NULL COMMENT '用例总数',
  `status` int(11) DEFAULT NULL COMMENT '状态：1：成功 2：失败',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COMMENT='测试集结果记录表';

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL COMMENT '登录用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `true_name` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) DEFAULT NULL COMMENT '箱邮',
  `department_id` bigint(20) DEFAULT '0' COMMENT '所属部门ID',
  `team_id` bigint(20) DEFAULT NULL COMMENT '所属小组ID',
  `status` int(11) DEFAULT NULL COMMENT '态状，0:启用,1:禁用',
  `is_admin` int(11) DEFAULT NULL COMMENT '否是管理员，0：系统管理员，1：部门管理员，2：小组管理员，3：普通用户',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Table structure for table `t_weekly_report` */

DROP TABLE IF EXISTS `t_weekly_report`;

CREATE TABLE `t_weekly_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '周报录入人员ID',
  `department_id` bigint(20) DEFAULT NULL COMMENT '周报录入人员所属部门id',
  `team_id` bigint(20) DEFAULT NULL COMMENT '周报录入人员所属小组id',
  `project_id` bigint(20) DEFAULT NULL COMMENT '周报所属项目ID',
  `type` int(11) DEFAULT '1' COMMENT '类型：1，版本测试 2，活动测试  3，临时需求测试  4，其他',
  `version_info` varchar(100) DEFAULT NULL COMMENT '版本描述信息',
  `resource` varchar(100) DEFAULT NULL COMMENT '测试责任人',
  `case_number` int(11) DEFAULT '0' COMMENT '版本用例总数',
  `version_test_time` timestamp NULL DEFAULT NULL COMMENT '版本转测时间，null或空表示待定',
  `test_time` double(20,1) DEFAULT NULL COMMENT '版本测试时间，null或空：待定，非0：具体测试时间天数',
  `test_status` int(11) DEFAULT NULL COMMENT '测试进度：1：未开始，2：进行中，3：已完成',
  `bug_number` int(11) DEFAULT NULL COMMENT '版本bug总数',
  `bug_critical` int(11) DEFAULT NULL,
  `version_release_time` timestamp NULL DEFAULT NULL COMMENT '版本发布时间，null或空表示待定',
  `remark` varchar(2048) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '周报录入时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '周报修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=874 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='测试周报表';

/*Table structure for table `t_work_plan` */

DROP TABLE IF EXISTS `t_work_plan`;

CREATE TABLE `t_work_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `team_id` bigint(20) DEFAULT NULL COMMENT '小组ID',
  `resource` varchar(1024) DEFAULT NULL COMMENT '责任人',
  `work_info` varchar(1024) DEFAULT NULL COMMENT '任务信息简要概述',
  `work_output` varchar(4096) DEFAULT NULL COMMENT '任务详细输出内容',
  `plan_start_time` timestamp NULL DEFAULT NULL COMMENT '计划开始时间',
  `plan_done_time` timestamp NULL DEFAULT NULL COMMENT '计划完成时间',
  `status` int(11) DEFAULT '1' COMMENT '进度：1：未完成，2：按时完成，3：未按时完成',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '录入时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='专项任务计划表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
