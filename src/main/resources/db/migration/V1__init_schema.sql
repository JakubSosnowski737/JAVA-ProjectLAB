CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL
);

CREATE TABLE roles (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users_roles (
                             user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                             role_id INTEGER NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
                             PRIMARY KEY (user_id, role_id)
);

CREATE TABLE authors (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         biography TEXT
);

CREATE TABLE categories (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE,
                            description TEXT
);

CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(200) NOT NULL,
                       isbn VARCHAR(50) NOT NULL UNIQUE,
                       description TEXT,
                       price NUMERIC(10,2)
);

CREATE TABLE authors_books (
                               author_id INTEGER NOT NULL REFERENCES authors(id) ON DELETE CASCADE,
                               book_id INTEGER NOT NULL REFERENCES books(id) ON DELETE CASCADE,
                               PRIMARY KEY (author_id, book_id)
);

CREATE TABLE books_categories (
                                  book_id INTEGER NOT NULL REFERENCES books(id) ON DELETE CASCADE,
                                  category_id INTEGER NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
                                  PRIMARY KEY (book_id, category_id)
);

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
