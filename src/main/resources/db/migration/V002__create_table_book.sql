CREATE TABLE IF NOT EXISTS `book` (
    `id` BINARY(16) PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `customer_id` BINARY(16) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
)  ENGINE=INNODB;