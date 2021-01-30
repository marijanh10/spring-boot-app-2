DROP TABLE IF EXISTS `favourite_city`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `city`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
);

CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(255) NOT NULL,
  `population` long default 0,
  `time_added` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `favourite_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_user` int(11) NOT NULL,
  `fk_city` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`fk_user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_city` FOREIGN KEY (`fk_city`) REFERENCES `city` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
