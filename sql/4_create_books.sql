-- Создание таблицы books
CREATE TABLE IF NOT EXISTS book (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(100) NOT NULL,
    isbn VARCHAR(20),
    publication_year INTEGER,
    genre VARCHAR(50),
    price DECIMAL(10,2),
    pages_count INTEGER
);