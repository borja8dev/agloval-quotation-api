ALTER TABLE users
    ADD COLUMN IF NOT EXISTS password     VARCHAR(255),
    ADD COLUMN IF NOT EXISTS role         VARCHAR(20) NOT NULL DEFAULT 'ROLE_CLIENT',
    ADD COLUMN IF NOT EXISTS created_at   TIMESTAMP;

CREATE TABLE IF NOT EXISTS jwt_refresh_tokens (
    id          BIGSERIAL PRIMARY KEY,
    token       TEXT        NOT NULL UNIQUE,
    user_id     BIGINT      NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    expiry_date TIMESTAMP   NOT NULL,
    revoked     BOOLEAN     NOT NULL DEFAULT FALSE
);

CREATE INDEX IF NOT EXISTS idx_jwt_refresh_tokens_user_id ON jwt_refresh_tokens(user_id);
