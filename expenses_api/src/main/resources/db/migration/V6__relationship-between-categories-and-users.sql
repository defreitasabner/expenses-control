ALTER TABLE categories
    ADD COLUMN user_id BIGINT;

ALTER TABLE categories
    ADD FOREIGN KEY (user_id) REFERENCES users(id);