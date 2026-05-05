CREATE TABLE IF NOT EXISTS `clients` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(80) NOT NULL,
    `email` VARCHAR(80) NOT NULL,
    `phone` VARCHAR(30),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `users` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(80) NOT NULL,
    `password` VARCHAR(80) NOT NULL,
    `role` VARCHAR(80) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `collaborator` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(80) NOT NULL,
    `user_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE IF NOT EXISTS `driver` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(80) NOT NULL,
    `phone` VARCHAR(30) NOT NULL,
    `cpf` VARCHAR(11) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `vehicle` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `vehicle_type` VARCHAR(80) NOT NULL,
    `plate` VARCHAR(80) NOT NULL,
    `plate2` VARCHAR(80),
    `plate3` VARCHAR(80),
    `plate4` VARCHAR(80),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `orders` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `order_number` VARCHAR(80) NOT NULL,
    `product` VARCHAR(80) NOT NULL,
    `packaging` VARCHAR(80) NOT NULL,
    `recipient` VARCHAR(80) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `shipment`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `date` DATE NOT NULL,
    `driver_id` BIGINT NOT NULL,
    `vehicle_id` BIGINT NOT NULL,
    `order_id` BIGINT NOT NULL,
    `collaborator_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`driver_id`) REFERENCES `driver` (`id`),
    FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
    FOREIGN KEY (`collaborator_id`) REFERENCES `collaborator` (`id`)
);

CREATE TABLE IF NOT EXISTS `freight` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `info_basic` VARCHAR(80) NOT NULL,
    `quantity` DECIMAL(10,2) NOT NULL,
    `freight_value` DECIMAL(10,2) NOT NULL,
    `info_additional` VARCHAR(80) NOT NULL,
    `client_id` BIGINT NOT NULL,
    `shipment_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`),
    FOREIGN KEY (`shipment_id`) REFERENCES `shipment` (`id`)
);