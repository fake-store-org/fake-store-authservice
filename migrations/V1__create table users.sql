CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
        role VARCHAR(50) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE,
        hashed_pw VARCHAR(255) NOT NULL,
    
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    co VARCHAR(255),
    street_name VARCHAR(255),
    street_name2 VARCHAR(255),
    postal_code VARCHAR(10),
    city VARCHAR(255),
    country VARCHAR(255),
    
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);