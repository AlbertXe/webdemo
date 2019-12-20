USE `study`;
DROP TABLE IF EXISTS `cron`;
CREATE TABLE `cron`  (
  `cron_id` varchar(30) NOT NULL PRIMARY KEY,
  `cron` varchar(30) NOT NULL
);
INSERT INTO `cron` VALUES ('1', '0/5 * * * * ?');