DROP TABLE `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(13) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `orderinfo`.`users` (`username`, `email`, `password`, `first_name`, `last_name`, `address`, `phone`) VALUES ('root', 'root@root', MD5('root'), 'root', 'root', 'root', '91');
INSERT INTO `orderinfo`.`users` (`username`, `email`, `password`, `first_name`, `last_name`, `address`, `phone`) VALUES ('gsp', 'gsp@student.dei.uc.pt', MD5('123456'), 'goncalo', 'pereira', 'coimbra', '911112937');