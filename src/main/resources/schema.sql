-- DROP TABLE IF EXISTS WishItem;
CREATE TABLE IF NOT EXISTS WishItem (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    url VARCHAR(255),
    price NUMERIC(10, 2),
    date_created TIMESTAMP
);
