
CREATE TABLE IF NOT EXISTS public.person
(
    id serial NOT NULL ,
    first_name character(10) NOT NULL,
    last_name character(10) NOT NULL,
    address character(20) NOT NULL,
    gender character(6) NOT NULL,
    PRIMARY KEY (id)
);
