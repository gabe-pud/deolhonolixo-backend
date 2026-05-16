ALTER TABLE users
    DROP COLUMN verification_code,
    DROP COLUMN verification_expiry,
    DROP COLUMN password_reset_code,
    DROP COLUMN password_reset_expiry;