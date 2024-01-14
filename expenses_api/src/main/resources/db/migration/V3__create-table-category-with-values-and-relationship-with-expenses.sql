CREATE TABLE categories(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,

    PRIMARY KEY(id)
);

INSERT INTO categories(name)
    VALUES
        ("Alimentação"),
        ("Saúde"),
        ("Moradia"),
        ("Transporte"),
        ("Educação"),
        ("Lazer"),
        ("Imprevisto"),
        ("Outros");

ALTER TABLE expenses
    ADD COLUMN category_id BIGINT NOT NULL;

ALTER TABLE expenses
    ADD FOREIGN KEY (category_id) REFERENCES categories(id);