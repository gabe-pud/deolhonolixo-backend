CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),

    verification_code VARCHAR(255),
    verified BOOLEAN DEFAULT FALSE,
    verification_expiry TIMESTAMP WITH TIME ZONE,

    password_reset_code VARCHAR(255),
    password_reset_expiry TIMESTAMP WITH TIME ZONE
);