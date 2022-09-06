--liquibase formatted sql

--changeset elias:3
CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL DEFAULT '{noop}123',
    role       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP
);
--rollback DROP TABLE users;