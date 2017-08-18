DROP TABLE IF EXISTS `t_test`;

CREATE TABLE `t_test` (
  `id`   BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255),
  `age`  INT(3)              DEFAULT 0,
  PRIMARY KEY (`id`)
);

INSERT INTO
  `t_test` (`name`, `age`)
VALUES
  ('jack01', 22),
  ('jack02', 19),
  ('jack03', 22),
  ('jack04', 31),
  ('jack05', 22),
  ('jack06', 24),
  ('jack07', 23),
  ('jack08', 32),
  ('jack09', 20),
  ('jack10', 22),
  ('may01', 23),
  ('may02', 28),
  ('may03', 26),
  ('may04', 24),
  ('may05', 43),
  ('may06', 22),
  ('may07', 23),
  ('may08', 21),
  ('may09', 23),
  ('may10', 20),
  ('peter01', 25),
  ('peter02', 27),
  ('peter03', 23),
  ('peter04', 21),
  ('peter05', 24),
  ('peter06', 34),
  ('peter07', 19),
  ('peter08', 24),
  ('peter09', 33),
  ('peter10', 24),
  ('mark01', 45),
  ('mark02', 15),
  ('mark03', 26);