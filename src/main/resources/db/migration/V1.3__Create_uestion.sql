CREATE TABLE `question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `titile` VARCHAR(50),
  `description` TEXT,
  `gmt_create` BIGINT,
  `gmt_modified` BIGINT,
  `creator` INT,
  `comment_count` INT DEFAULT 0,
  `view_count` INT DEFAULT 0,
  `link_count` INT DEFAULT 0,
  `tag` VARCHAR(256),
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;