CREATE TABLE user_credentials(
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    email VARCHAR(300) UNIQUE NOT NULL,
    nickname VARCHAR(300) NOT NULL,
    hash_password VARCHAR(300) NOT NULL,
    account_status BOOLEAN NOT NULL DEFAULT FALSE,
    password_date TIMESTAMP NOT NULL
);

CREATE TABLE service_data(
    user_id UUID PRIMARY KEY UNIQUE NOT NULL,
    key INT NOT NULL,
    date TIMESTAMP NOT NULL
);