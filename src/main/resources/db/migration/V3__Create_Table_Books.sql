
CREATE TABLE books
(
    id serial PRIMARY KEY,
    author varchar ,
    launch_date date NOT NULL,
    price decimal(65,2) NOT NULL,
    title varchar
);
