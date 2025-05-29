-- changeset artur:1-init-schema
CREATE TABLE telegram_users
(
    id            BIGSERIAL PRIMARY KEY,
    telegram_id   BIGINT NOT NULL UNIQUE,
    username      VARCHAR(255),
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    language_code VARCHAR(255),
    is_bot        BOOLEAN,
    created_at    TIMESTAMP,
    state         VARCHAR(255)
);

CREATE UNIQUE INDEX idx_telegram_id ON telegram_users (telegram_id);

CREATE TABLE update_history
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    content    TEXT,
    created_at TIMESTAMP,
    CONSTRAINT fk_update_history_user FOREIGN KEY (user_id) REFERENCES telegram_users (id)
);

CREATE INDEX idx_user_id ON update_history (user_id);