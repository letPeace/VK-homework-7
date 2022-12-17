CREATE SCHEMA IF NOT EXISTS core;

CREATE TABLE IF NOT EXISTS core.organization
(
    name VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS core.product
(
    name              VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY,
    organization_name VARCHAR(100) NOT NULL REFERENCES core.organization (name) ON UPDATE CASCADE ON DELETE CASCADE,
    quantity          INTEGER      NOT NULL CHECK(quantity >= 0)
);
