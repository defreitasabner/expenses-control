ALTER TABLE expenses
    ADD COLUMN user_id BIGINT NOT NULL;

ALTER TABLE expenses
    ADD FOREIGN KEY (user_id) REFERENCES users(id);