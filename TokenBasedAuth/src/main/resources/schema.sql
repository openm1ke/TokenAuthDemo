CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE IF NOT EXISTS app.users (
    -- PostgreSQL
    id BIGSERIAL PRIMARY KEY,
    -- H2
    -- id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);