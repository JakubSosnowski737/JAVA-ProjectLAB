CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE users_roles (
                             user_id INT REFERENCES users(id),
                             role_id INT REFERENCES roles(id),
                             PRIMARY KEY (user_id, role_id)
);

