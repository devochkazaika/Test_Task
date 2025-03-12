--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3 (Debian 13.3-1.pgdg100+1)
-- Dumped by pg_dump version 13.3 (Debian 13.3-1.pgdg100+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: bookrent; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.bookrent (
    id uuid NOT NULL,
    rentdate timestamp(6) without time zone,
    rentstatus character varying(255),
    returndate timestamp(6) without time zone,
    book_id bigint,
    user_id uuid,
    CONSTRAINT bookrent_rentstatus_check CHECK (((rentstatus)::text = ANY ((ARRAY['OPENED'::character varying, 'CLOSED'::character varying])::text[])))
);


ALTER TABLE public.bookrent OWNER TO "user";

--
-- Name: books; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.books (
    id bigint NOT NULL,
    author character varying(255),
    booktheme character varying(255),
    count integer,
    name character varying(255),
    publicationdate date,
    CONSTRAINT books_booktheme_check CHECK (((booktheme)::text = ANY ((ARRAY['CLASSIC'::character varying, 'FANTASTIC'::character varying, 'EPIC'::character varying, 'DRAMA'::character varying, 'DETECTIVE'::character varying])::text[]))),
    CONSTRAINT books_count_check CHECK ((count >= 0))
);


ALTER TABLE public.books OWNER TO "user";

--
-- Name: books_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.books_id_seq OWNER TO "user";

--
-- Name: books_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.books_id_seq OWNED BY public.books.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    age smallint,
    email character varying(255),
    firstname character varying(255),
    lastname character varying(255)
);


ALTER TABLE public.users OWNER TO "user";

--
-- Name: books id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.books ALTER COLUMN id SET DEFAULT nextval('public.books_id_seq'::regclass);


--
-- Data for Name: bookrent; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.bookrent (id, rentdate, rentstatus, returndate, book_id, user_id) FROM stdin;
d9352a03-932a-4629-ac31-800cbedd2220	2025-03-11 10:15:30	OPENED	\N	3	0de44d89-a233-499e-af67-9d8bd26edcdb
5c303d5e-708d-4129-b48c-804201c63a81	2025-03-11 10:15:30	OPENED	\N	3	07661f18-df13-4016-a8d9-75cd03fc9510
02735aec-3f86-4433-b69a-7388592ecde5	2025-03-11 10:15:30	CLOSED	2025-03-12 23:25:22.164439	3	0de44d89-a233-499e-af67-9d8bd26edcdb
94d08a62-f24f-4d09-a501-34b36255f819	2025-03-11 10:15:30	OPENED	\N	1	0de44d89-a233-499e-af67-9d8bd26edcdb
\.


--
-- Data for Name: books; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.books (id, author, booktheme, count, name, publicationdate) FROM stdin;
2	Достоевский	DETECTIVE	12	Преступление и наказание	1870-03-11
3	Тургеньев	DRAMA	28	Отцы и дети	1842-03-11
1	Толстой	CLASSIC	0	Война и мир	1895-03-11
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: user
--

COPY public.users (id, age, email, firstname, lastname) FROM stdin;
0de44d89-a233-499e-af67-9d8bd26edcdb	44	martunov@gmail.com	Илья	Мартынов
f4464485-802b-41cd-9eb1-aa3e7cff69a1	23	ivanova@gmail.com	Марина	Иванова
07661f18-df13-4016-a8d9-75cd03fc9510	34	petrov@gmail.com	Петр	Петров
0ace6437-ea65-4db0-97e9-fddd8aec4392	34	dep@gmail.com	Джонни	Дэп
\.


--
-- Name: books_id_seq; Type: SEQUENCE SET; Schema: public; Owner: user
--

SELECT pg_catalog.setval('public.books_id_seq', 3, true);


--
-- Name: bookrent bookrent_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.bookrent
    ADD CONSTRAINT bookrent_pkey PRIMARY KEY (id);


--
-- Name: books books_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: bookrent fkm0jtw04ir8xns6i36a6taxkiw; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.bookrent
    ADD CONSTRAINT fkm0jtw04ir8xns6i36a6taxkiw FOREIGN KEY (book_id) REFERENCES public.books(id);


--
-- Name: bookrent fkmjd44rx5r59q98emodkcrwx23; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.bookrent
    ADD CONSTRAINT fkmjd44rx5r59q98emodkcrwx23 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

