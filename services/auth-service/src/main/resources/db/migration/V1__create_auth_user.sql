CREATE TABLE auth_user (
    id BIGINT PRIMARY KEY,
    email TEXT NOT NULL,
    role TEXT NOT NULL
);