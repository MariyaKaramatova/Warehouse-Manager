-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema warehouse
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema warehouse
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `warehouse` DEFAULT CHARACTER SET latin1 ;
USE `warehouse` ;

-- -----------------------------------------------------
-- Table `warehouse`.`commodities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warehouse`.`commodities` (
  `commodities_id` INT(11) NOT NULL AUTO_INCREMENT,
  `volume` INT(11) NOT NULL,
  `weight` INT(11) NOT NULL,
  `price` INT(11) NOT NULL,
  PRIMARY KEY (`commodities_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `warehouse`.`lots`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warehouse`.`lots` (
  `lots_id` INT(11) NOT NULL AUTO_INCREMENT,
  `volume` INT(11) NOT NULL,
  `load_capacity` INT(11) NOT NULL,
  PRIMARY KEY (`lots_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `warehouse`.`movings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warehouse`.`movings` (
  `movings_id` INT(11) NOT NULL AUTO_INCREMENT,
  `date` DATETIME NOT NULL,
  `commodity_id` INT(11) NOT NULL,
  `quantity` INT(11) NOT NULL,
  `type` ENUM('move_in', 'move_out') NOT NULL,
  PRIMARY KEY (`movings_id`),
  INDEX `fk_move_commodity_id_idx` (`commodity_id` ASC),
  CONSTRAINT `fk_move_commodity_id`
    FOREIGN KEY (`commodity_id`)
    REFERENCES `warehouse`.`commodities` (`commodities_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `warehouse`.`supply`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `warehouse`.`supply` (
  `supply_id` INT(11) NOT NULL AUTO_INCREMENT,
  `commodity_id` INT(11) NOT NULL,
  `quantity` INT(11) NOT NULL,
  `lot_id` INT(11) NOT NULL,
  PRIMARY KEY (`supply_id`),
  INDEX `fk_lot_id_idx` (`lot_id` ASC),
  INDEX `fk_commodity_id_idx` (`commodity_id` ASC),
  CONSTRAINT `fk_commodity_id`
    FOREIGN KEY (`commodity_id`)
    REFERENCES `warehouse`.`commodities` (`commodities_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_lot_id`
    FOREIGN KEY (`lot_id`)
    REFERENCES `warehouse`.`lots` (`lots_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
