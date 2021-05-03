-- Table: public.customer
DROP TABLE IF EXISTS customer CASCADE;
CREATE TABLE public.customer
(
    id serial NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT customer_pkey PRIMARY KEY (id),
    CONSTRAINT uq_customer_name UNIQUE (name)
);

insert into customer(name) values
('Вова Пукин'),
('Вениамин Пух'),
('Тот Самый Кох'),
('Вася Пупкин');

-- Table: public.product
DROP TABLE IF EXISTS product CASCADE;
CREATE TABLE public.product
(
    id serial NOT NULL,
    cost integer NOT NULL,
    title character varying(255) NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT uq_product_title UNIQUE (title)
);
truncate table product;
insert into product(title, cost) values
('чай с молоком', 89),
('кашка-какашка', 100),
('молоко птичье', 80),
('пособие проктолога', 1299),
('пособие по уходу', 500),
('хрен на постном масле', 0);

-- Table: public.purchase
DROP TABLE IF EXISTS public.purchase;

CREATE TABLE public.purchase
(
    id serial NOT NULL,
    count integer NOT NULL,
    price integer NOT NULL,
    customer_id integer NOT NULL,
    product_id integer NOT NULL,
    CONSTRAINT purchase_pkey PRIMARY KEY (id),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id)
        REFERENCES public.customer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_product FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

DROP INDEX IF EXISTS public.customer_idx;
CREATE INDEX customer_idx ON public.purchase USING btree(customer_id);

DROP INDEX IF EXISTS public.product_idx;
CREATE INDEX product_idx ON public.purchase USING btree(product_id);

insert into purchase(customer_id, product_id, count, price) values
(1, 1, 1, 100),
(1, 1, 1, 90),
(1, 6, 1, 50),
(2, 1, 1, 999),
(2, 2, 5, 64),
(2, 3, 2, 1),
(3, 6, 100, 0);