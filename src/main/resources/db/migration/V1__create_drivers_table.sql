CREATE TABLE drivers (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    license_number VARCHAR(50) NOT NULL UNIQUE,
    license_class VARCHAR(20) NOT NULL,
    phone_number VARCHAR(20) NOT NULL
);