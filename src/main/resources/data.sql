INSERT INTO `user` (`email`, `password`) VALUES
('admin','$2a$10$WRrMt.oXHrhUmon.HBDf1u9FegkCsI9/nE7JM5VZK3myPN/RgFoyS'),
('user','$2a$10$WRrMt.oXHrhUmon.HBDf1u9FegkCsI9/nE7JM5VZK3myPN/RgFoyS'),
('user1','$2a$10$WRrMt.oXHrhUmon.HBDf1u9FegkCsI9/nE7JM5VZK3myPN/RgFoyS');

INSERT INTO `city` (`name`, `description`, `population`, `time_added`) VALUES
('Zagreb','Croatia Capital', 1000000, DATEADD('HOUR',-7, CURRENT_DATE())),
('Split','City in Croatia', 500000, DATEADD('HOUR',4, CURRENT_DATE())),
('Osijek','City in Croatia', 120000, DATEADD('HOUR',25, CURRENT_DATE())),
('Ilok','City in Croatia', 120000, DATEADD('HOUR',-13, CURRENT_DATE())),
('Trogir','City in Croatia', 120000, DATEADD('HOUR',-22, CURRENT_DATE())),
('Vinkovci','City in Croatia', 120000, DATEADD('HOUR',20, CURRENT_DATE())),
('Slavonski Brod','City in Croatia', 120000, DATEADD('HOUR',-14, CURRENT_DATE())),
('Slatina','City in Croatia', 120000, DATEADD('HOUR',-10, CURRENT_DATE())),
('Virovitica','City in Croatia', 120000, DATEADD('HOUR',20, CURRENT_DATE())),
('Vukovar','Croatia Capital', 10000000, DATEADD('HOUR',5, CURRENT_DATE()));

INSERT INTO `favourite_city` (`fk_user`, `fk_city`) VALUES
(1,3),
(2,3),
(3,3),
(1,4),
(2,5);

