use tps;

--drop old tables if exists
DROP TABLE  IF EXISTS trader_deals;
DROP TABLE  IF EXISTS sale_deals;
DROP TABLE  IF EXISTS product;
DROP TABLE  IF EXISTS user;
DROP TABLE  IF EXISTS role;
DROP TABLE  IF EXISTS cusip_user;

CREATE TABLE IF NOT EXISTS `trader_deals`(
    `txn_i` INT UNSIGNED AUTO_INCREMENT,
    `product_id` VARCHAR(50) NOT NULL,
    `volume` INT UNSIGNED NOT NULL,
    `price` DOUBLE NOT NULL,
    `notional_principal` DOUBLE,
    `order_id` VARCHAR(50),
    `trade_sender` INT UNSIGNED,
    `trade_reciver` INT UNSIGNED,
    `timestamp` TIMESTAMP NOT NULL,
    `inter_orig_sys` VARCHAR(50),
    `inter_i` INT UNSIGNED,
    `inter_v_num` INT UNSIGNED,
    `ver` INT UNSIGNED,
    `status` VARCHAR(20) NOT NULL,
    `reject_code` INT UNSIGNED,
    `reject_reason` VARCHAR(50),
    `trade_orig_sys` VARCHAR(50),
     PRIMARY KEY (`txn_i`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sale_deals`(
    `txn_i` INT UNSIGNED AUTO_INCREMENT,
    `product_id` VARCHAR(50) NOT NULL,
    `volume` INT UNSIGNED NOT NULL,
    `price` DOUBLE NOT NULL,
    `notional_principal` DOUBLE,
    `order_id` VARCHAR(50),
    `trade_sender` INT UNSIGNED,
    `trade_reciver` INT UNSIGNED,
    `timestamp` TIMESTAMP NOT NULL,
    `inter_orig_sys` VARCHAR(50),
    `inter_i` INT UNSIGNED,
    `inter_v_num` INT UNSIGNED,
    `ver` INT UNSIGNED,
    `status` VARCHAR(20) NOT NULL,
    `reject_code` INT UNSIGNED,
    `reject_reason` VARCHAR(50),
    `trade_orig_sys` VARCHAR(50),
     PRIMARY KEY (`txn_i`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `product`(
    `id` INT UNSIGNED AUTO_INCREMENT,
    `cusip` VARCHAR(50) NOT NULL,
    `face_value` DOUBLE NOT NULL,
    `coupon` DOUBLE NOT NULL,
    `maturity_date` TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `user`(
    `id` INT UNSIGNED AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `pwd` VARCHAR(50) NOT NULL,
    `role_id` int,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `role`(
    `id` INT UNSIGNED AUTO_INCREMENT,
    `role` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `cusip_user`(
     `id` INT UNSIGNED AUTO_INCREMENT,
     `user_id` INT UNSIGNED,
     `product_id` VARCHAR(50) NOT NULL,
     PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO user(name, pwd, role_id) VALUES ('test_user', '111', 1);
INSERT INTO product(cusip, face_value, coupon,maturity_date) VALUES ('1234', 100, 1.1,'2019-10-10');