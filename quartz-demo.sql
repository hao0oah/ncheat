
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `task_schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `task_schedule_job`;
CREATE TABLE `task_schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `job_name` varchar(50) DEFAULT NULL,
  `job_group` varchar(50) DEFAULT NULL,
  `job_status` varchar(10) DEFAULT NULL,
  `cron_expression` varchar(30) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `bean_class` varchar(127) DEFAULT NULL,
  `method_name` varchar(50) NOT NULL,
  `spring_id` varchar(50) DEFAULT NULL,
  `is_concurrent` varchar(10) DEFAULT NULL COMMENT '1',
  `params` varchar(255),
  PRIMARY KEY (`job_id`),
  UNIQUE KEY `name_group` (`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_schedule_job
-- ----------------------------
INSERT INTO `task_schedule_job` VALUES ('15', '2016-07-29 20:57:17', '2016-07-29 20:57:57', '1001', 'TASK', '0', '0/10 * * * * ?', '定时上报1001', 'com.founder.task.Task100403', 'task1001', null,'1', '{start:20160701,end:20160705}');
INSERT INTO `task_schedule_job` VALUES ('16', '2016-07-29 20:57:17', '2016-07-29 20:57:57', '1002', 'TASK', '0', '0/10 * * * * ?', '定时上报1002', 'com.founder.task.Task100403', 'task1002', null,'1', '{last:10}');
