--liquibase formatted sql

--changeset elias:1
CREATE TABLE IF NOT EXISTS categories
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP
);
--rollback DROP TABLE categories;

--changeset elias:2
CREATE TABLE IF NOT EXISTS jokes
(
    id          SERIAL PRIMARY KEY,
    value       VARCHAR(255) NOT NULL,
    category_id INT REFERENCES categories (id),
    created_at  TIMESTAMP
);
--rollback DROP TABLE authorities;