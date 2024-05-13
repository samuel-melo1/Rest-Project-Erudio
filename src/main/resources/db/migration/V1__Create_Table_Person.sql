
CREATE TABLE IF NOT EXISTS public.person
(
    id serial NOT NULL ,
    first_name character(60) NOT NULL,
    last_name character(50) NOT NULL,
    address character(60) NOT NULL,
    gender character(60) NOT NULL,
    PRIMARY KEY (id)
);
