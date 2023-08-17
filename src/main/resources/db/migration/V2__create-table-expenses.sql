CREATE TABLE expenses(
    id BIGINT NOT NULL AUTO_INCREMENT,
    description VARCHAR(300) NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    datetime DATETIME NOT NULL,

    PRIMARY KEY(id)
);