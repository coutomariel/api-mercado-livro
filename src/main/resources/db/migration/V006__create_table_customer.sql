CREATE TABLE IF NOT EXISTS `customer_roles` (
    `customer_id` BINARY(16),
    `role` VARCHAR(50) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
)  ENGINE=INNODB;