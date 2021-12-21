CREATE TABLE IF NOT EXISTS `customer_roles` (
    `customer_id` INT,
    `role` VARCHAR(50) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
)  ENGINE=INNODB;