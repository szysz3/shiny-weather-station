--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: purge_current_conditions(); Type: FUNCTION; Schema: public; Owner: pi
--

CREATE FUNCTION public.purge_current_conditions() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    
    IF (SELECT count(id) FROM current_conditions) > 500
    THEN 
        
        DELETE FROM current_conditions
        WHERE id = (SELECT min(id) FROM current_conditions);
    END IF;
RETURN NULL;
END;
$$;


ALTER FUNCTION public.purge_current_conditions() OWNER TO pi;

--
-- Name: purge_forecast(); Type: FUNCTION; Schema: public; Owner: pi
--

CREATE FUNCTION public.purge_forecast() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    
    IF (SELECT count(id) FROM forecast) > 500
    THEN 
        
        DELETE FROM forecast
        WHERE id = (SELECT min(id) FROM forecast);
    END IF;
RETURN NULL;
END;
$$;


ALTER FUNCTION public.purge_forecast() OWNER TO pi;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: current_conditions; Type: TABLE; Schema: public; Owner: pi; Tablespace: 
--

CREATE TABLE public.current_conditions (
    id bigint NOT NULL,
    date timestamp without time zone,
    data json
);


ALTER TABLE public.current_conditions OWNER TO pi;

--
-- Name: current_conditions_id_seq; Type: SEQUENCE; Schema: public; Owner: pi
--

CREATE SEQUENCE public.current_conditions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.current_conditions_id_seq OWNER TO pi;

--
-- Name: current_conditions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pi
--

ALTER SEQUENCE public.current_conditions_id_seq OWNED BY public.current_conditions.id;


--
-- Name: forecast; Type: TABLE; Schema: public; Owner: pi; Tablespace: 
--

CREATE TABLE public.forecast (
    id bigint NOT NULL,
    date timestamp without time zone,
    data json
);


ALTER TABLE public.forecast OWNER TO pi;

--
-- Name: forecast_id_seq; Type: SEQUENCE; Schema: public; Owner: pi
--

CREATE SEQUENCE public.forecast_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.forecast_id_seq OWNER TO pi;

--
-- Name: forecast_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pi
--

ALTER SEQUENCE public.forecast_id_seq OWNED BY public.forecast.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: pi
--

ALTER TABLE ONLY public.current_conditions ALTER COLUMN id SET DEFAULT nextval('public.current_conditions_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: pi
--

ALTER TABLE ONLY public.forecast ALTER COLUMN id SET DEFAULT nextval('public.forecast_id_seq'::regclass);


--
-- Name: current_conditions_pkey; Type: CONSTRAINT; Schema: public; Owner: pi; Tablespace: 
--

ALTER TABLE ONLY public.current_conditions
    ADD CONSTRAINT current_conditions_pkey PRIMARY KEY (id);


--
-- Name: forecast_pkey; Type: CONSTRAINT; Schema: public; Owner: pi; Tablespace: 
--

ALTER TABLE ONLY public.forecast
    ADD CONSTRAINT forecast_pkey PRIMARY KEY (id);


--
-- Name: purge_current_conditions_trigger; Type: TRIGGER; Schema: public; Owner: pi
--

CREATE TRIGGER purge_current_conditions_trigger AFTER INSERT ON public.current_conditions FOR EACH ROW EXECUTE PROCEDURE public.purge_current_conditions();


--
-- Name: purge_forecast_trigger; Type: TRIGGER; Schema: public; Owner: pi
--

CREATE TRIGGER purge_forecast_trigger AFTER INSERT ON public.forecast FOR EACH ROW EXECUTE PROCEDURE public.purge_forecast();


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

