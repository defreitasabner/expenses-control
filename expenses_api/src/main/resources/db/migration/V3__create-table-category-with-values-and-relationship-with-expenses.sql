CREATE TABLE categories(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,

    PRIMARY KEY(id)
);

ALTER TABLE expenses
    ADD COLUMN category_id BIGINT NOT NULL;

ALTER TABLE expenses
    ADD FOREIGN KEY (category_id) REFERENCES categories(id);