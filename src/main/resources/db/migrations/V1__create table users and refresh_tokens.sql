CREATE TABLE users
(
    user_id      UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    role         VARCHAR(50)  NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    hashed_pw    VARCHAR(255) NOT NULL,

    first_name   VARCHAR(255),
    last_name    VARCHAR(255),
    co           VARCHAR(255),
    street_name  VARCHAR(255),
    street_name2 VARCHAR(255),
    postal_code  VARCHAR(10),
    city         VARCHAR(255),
    country      VARCHAR(255),

    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE refresh_tokens
(
    token_id    UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    token       VARCHAR(255)             NOT NULL UNIQUE,
    user_id     UUID                     NOT NULL,
    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_refresh_token_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE CASCADE
);