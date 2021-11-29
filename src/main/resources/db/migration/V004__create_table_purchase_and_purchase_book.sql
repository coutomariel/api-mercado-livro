CREATE TABLE IF NOT EXISTS `purchase` (
    `id` BINARY(16) PRIMARY KEY,
    `customer_id` BINARY(16) NOT NULL,
    `nfe` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    `created_at` DATETIME NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
)  ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `purchase_book` (
    `purchase_id` BINARY(16) NOT NULL,
    `book_id` BINARY(16) NOT NULL,
    FOREIGN KEY (purchase_id) REFERENCES purchase(id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    PRIMARY KEY (purchase_id, book_id)
)  ENGINE=INNODB;