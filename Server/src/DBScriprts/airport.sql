CREATE SCHEMA IF NOT EXISTS `airport`;
USE `airport`;

CREATE TABLE IF NOT EXISTS `users` (
  `idUsers` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `isBlocked` VARCHAR(45) NOT NULL DEFAULT 'no',
   PRIMARY KEY(`idUsers`)
  );

CREATE TABLE IF NOT EXISTS `admin` (
  `idadmin` INT(11) NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
   PRIMARY KEY(`idadmin`),
    FOREIGN KEY (`idadmin`)
    REFERENCES `users`(`idUsers`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
  );

CREATE TABLE IF NOT EXISTS `maindata` (
  `idmd` INT(11) NOT NULL AUTO_INCREMENT,
  `airport` VARCHAR(45) NOT NULL,
  `date` VARCHAR(45) NOT NULL,
  `outtime` VARCHAR(45) NOT NULL,
  `intime` VARCHAR(45) NOT NULL,
   PRIMARY KEY(`idmd`)
  );

CREATE TABLE IF NOT EXISTS `flight` (
  `idF` INT(11) NOT NULL AUTO_INCREMENT,
  `price` INT(11) NOT NULL,
  `idMD` INT(11) NOT NULL,
  `seatsAmount` INT(11) NULL DEFAULT NULL,
   PRIMARY KEY(`idF`),
    FOREIGN KEY(`idMD`)
    REFERENCES `maindata`(`idmd`)
  );

CREATE TABLE IF NOT EXISTS `userdata` (
  `idUD` INT(11) NOT NULL AUTO_INCREMENT,
  `idu` INT(11) NOT NULL,
  `laggage` VARCHAR(45) NULL DEFAULT NULL,
  `age` VARCHAR(45) NULL DEFAULT NULL,
  `idf` INT(11) NULL DEFAULT NULL,
   PRIMARY KEY(`idUD`),
    FOREIGN KEY(`idf`)
    REFERENCES `flight`(`idF`),
    FOREIGN KEY(`idu`)
    REFERENCES `users`(`idUsers`)
  );

CREATE TABLE IF NOT EXISTS `agesales` (
  `idud` INT(11) NOT NULL,
  `sale` INT(11) NOT NULL DEFAULT 0,
    FOREIGN KEY(`idud`)
    REFERENCES `userdata`(`idUD`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
  );

CREATE TABLE IF NOT EXISTS `client` (
  `idclient` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `sex` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  PRIMARY KEY(`idclient`),
    FOREIGN KEY(`idclient`)
    REFERENCES `users`(`idUsers`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
  );

CREATE TABLE IF NOT EXISTS `laggage` (
  `iduserdata` INT(11) NOT NULL,
  `lprice` INT(11) NOT NULL,
    FOREIGN KEY(`iduserdata`)
    REFERENCES `userdata`(`idUD`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
  );

CREATE TABLE IF NOT EXISTS `ticket` (
  `idticket` INT(11) NOT NULL AUTO_INCREMENT,
  `idus` INT(11) NOT NULL,
  `generalPrice` INT(11) NOT NULL,
  PRIMARY KEY(`idticket`),
    FOREIGN KEY(`idus`)
    REFERENCES `users` (`idUsers`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
  );

CREATE TABLE IF NOT EXISTS `vacancy` (
  `id_vacancy` INT(11) NOT NULL AUTO_INCREMENT,
  `surname` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `post` VARCHAR(45) NOT NULL,
  `salary` INT(11) NOT NULL,
   PRIMARY KEY(`id_vacancy`)
  );

CREATE TABLE IF NOT EXISTS `airport` (
  `id_airport` INT(11) NOT NULL AUTO_INCREMENT,
  `name_airport` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `place` INT(11) NOT NULL,
   PRIMARY KEY(`id_airport`)
  );

CREATE TABLE IF NOT EXISTS `faq` (
  `id_faq` INT(11) NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(45) NOT NULL,
  `answer` VARCHAR(45) NOT NULL,
   PRIMARY KEY(`id_faq`)
  );

CREATE TABLE IF NOT EXISTS `stuff` (
  `id_stuff` INT(11) NOT NULL AUTO_INCREMENT,
  `stuff_object` VARCHAR(45) NOT NULL,
  `punishment` VARCHAR(45) NOT NULL,
   PRIMARY KEY(`id_stuff`)
  );






