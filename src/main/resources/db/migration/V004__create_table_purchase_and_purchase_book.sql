CREATE TABLE IF NOT EXISTS `purchase` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `customer_id` INT NOT NULL,
    `nfe` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    `created_at` DATETIME NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
)  ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS `purchase_book` (
    `purchase_id` INT NOT NULL,
    `book_id` INT NOT NULL,
    FOREIGN KEY (purchase_id) REFERENCES purchase(id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    PRIMARY KEY (purchase_id, book_id)
)  ENGINE=INNODB;