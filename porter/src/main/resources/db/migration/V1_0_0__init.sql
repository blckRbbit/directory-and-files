CREATE SCHEMA IF NOT EXISTS dev;

CREATE TABLE IF NOT EXISTS dev.directories
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    date_time VARCHAR(30) NOT NULL,
    path_to VARCHAR(800) NOT NULL,
    file_count INT DEFAULT 0,
    dir_count INT DEFAULT 0,
    size VARCHAR(60)
);

CREATE TABLE IF NOT EXISTS dev.files
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    size VARCHAR(60),
    path_to VARCHAR(800) NOT NULL,
    directory_id BIGSERIAL REFERENCES dev.directories(id)
);