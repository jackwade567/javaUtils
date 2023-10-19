--
-- PostgreSQL database dump
--

-- Dumped from database version 13.11 (Debian 13.11-0+deb11u1)
-- Dumped by pg_dump version 15.3

-- Started on 2023-10-19 16:17:59

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

--
-- TOC entry 3140 (class 1262 OID 52843)
-- Name: ccn; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE ccn WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'C';


ALTER DATABASE ccn OWNER TO postgres;

\connect ccn

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

--
-- TOC entry 6 (class 2615 OID 52844)
-- Name: ccn_staff; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA ccn_staff;


ALTER SCHEMA ccn_staff OWNER TO postgres;

--
-- TOC entry 224 (class 1255 OID 53048)
-- Name: staff_delete_trigger(); Type: FUNCTION; Schema: ccn_staff; Owner: postgres
--

CREATE FUNCTION ccn_staff.staff_delete_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO ccn_staff.staff_history(staff_id, racf, first_name, last_name, team_id, role_id, manager_id, LOB, active_status, change_type)
    VALUES (OLD.staff_id, OLD.racf, OLD.first_name, OLD.last_name, OLD.team_id, OLD.role_id, OLD.manager_id, OLD.LOB, OLD.active_status, 'DELETE');
    RETURN OLD;
END;
$$;


ALTER FUNCTION ccn_staff.staff_delete_trigger() OWNER TO postgres;

--
-- TOC entry 222 (class 1255 OID 53044)
-- Name: staff_insert_trigger(); Type: FUNCTION; Schema: ccn_staff; Owner: postgres
--

CREATE FUNCTION ccn_staff.staff_insert_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO ccn_staff.staff_history(staff_id, racf, first_name, last_name, team_id, role_id, manager_id, LOB, active_status, change_type)
    VALUES (NEW.staff_id, NEW.racf, NEW.first_name, NEW.last_name, NEW.team_id, NEW.role_id, NEW.manager_id, NEW.LOB, NEW.active_status, 'INSERT');
    RETURN NEW;
END;
$$;


ALTER FUNCTION ccn_staff.staff_insert_trigger() OWNER TO postgres;

--
-- TOC entry 227 (class 1255 OID 53054)
-- Name: staff_language_delete_trigger(); Type: FUNCTION; Schema: ccn_staff; Owner: postgres
--

CREATE FUNCTION ccn_staff.staff_language_delete_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO ccn_staff.staff_language_history(staff_id, language_id, active_status, change_type)
    VALUES (OLD.staff_id, OLD.language_id, OLD.active_status, 'DELETE');
    RETURN OLD;
END;
$$;


ALTER FUNCTION ccn_staff.staff_language_delete_trigger() OWNER TO postgres;

--
-- TOC entry 225 (class 1255 OID 53050)
-- Name: staff_language_insert_trigger(); Type: FUNCTION; Schema: ccn_staff; Owner: postgres
--

CREATE FUNCTION ccn_staff.staff_language_insert_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO ccn_staff.staff_language_history(staff_id, language_id, active_status, change_type)
    VALUES (NEW.staff_id, NEW.language_id, NEW.active_status, 'INSERT');
    RETURN NEW;
END;
$$;


ALTER FUNCTION ccn_staff.staff_language_insert_trigger() OWNER TO postgres;

--
-- TOC entry 226 (class 1255 OID 53052)
-- Name: staff_language_update_trigger(); Type: FUNCTION; Schema: ccn_staff; Owner: postgres
--

CREATE FUNCTION ccn_staff.staff_language_update_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO ccn_staff.staff_language_history(staff_id, language_id, active_status, change_type)
    VALUES (OLD.staff_id, OLD.language_id, OLD.active_status, 'UPDATE');
    RETURN NEW;
END;
$$;


ALTER FUNCTION ccn_staff.staff_language_update_trigger() OWNER TO postgres;

--
-- TOC entry 223 (class 1255 OID 53046)
-- Name: staff_update_trigger(); Type: FUNCTION; Schema: ccn_staff; Owner: postgres
--

CREATE FUNCTION ccn_staff.staff_update_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    INSERT INTO ccn_staff.staff_history(staff_id, racf, first_name, last_name, team_id, role_id, manager_id, LOB, active_status, change_type)
    VALUES (OLD.staff_id, OLD.racf, OLD.first_name, OLD.last_name, OLD.team_id, OLD.role_id, OLD.manager_id, OLD.LOB, OLD.active_status, 'UPDATE');
    RETURN NEW;
END;
$$;


ALTER FUNCTION ccn_staff.staff_update_trigger() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 208 (class 1259 OID 52883)
-- Name: language; Type: TABLE; Schema: ccn_staff; Owner: postgres
--

CREATE TABLE ccn_staff.language (
    language_id integer NOT NULL,
    lang_name character varying(255) NOT NULL,
    lang_status public.status_enum NOT NULL
);


ALTER TABLE ccn_staff.language OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 52881)
-- Name: language_language_id_seq; Type: SEQUENCE; Schema: ccn_staff; Owner: postgres
--

CREATE SEQUENCE ccn_staff.language_language_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ccn_staff.language_language_id_seq OWNER TO postgres;

--
-- TOC entry 3141 (class 0 OID 0)
-- Dependencies: 207
-- Name: language_language_id_seq; Type: SEQUENCE OWNED BY; Schema: ccn_staff; Owner: postgres
--

ALTER SEQUENCE ccn_staff.language_language_id_seq OWNED BY ccn_staff.language.language_id;


--
-- TOC entry 206 (class 1259 OID 52875)
-- Name: manager; Type: TABLE; Schema: ccn_staff; Owner: postgres
--

CREATE TABLE ccn_staff.manager (
    manager_id integer NOT NULL,
    manager_status public.status_enum NOT NULL,
    lob public.lob_enum NOT NULL,
    manager_firstname character varying(255),
    manager_lastname character varying(255)
);


ALTER TABLE ccn_staff.manager OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 52873)
-- Name: manager_manager_id_seq; Type: SEQUENCE; Schema: ccn_staff; Owner: postgres
--

CREATE SEQUENCE ccn_staff.manager_manager_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ccn_staff.manager_manager_id_seq OWNER TO postgres;

--
-- TOC entry 3142 (class 0 OID 0)
-- Dependencies: 205
-- Name: manager_manager_id_seq; Type: SEQUENCE OWNED BY; Schema: ccn_staff; Owner: postgres
--

ALTER SEQUENCE ccn_staff.manager_manager_id_seq OWNED BY ccn_staff.manager.manager_id;


--
-- TOC entry 217 (class 1259 OID 52994)
-- Name: member; Type: TABLE; Schema: ccn_staff; Owner: postgres
--

CREATE TABLE ccn_staff.member (
    member_id integer NOT NULL,
    cipid character varying(255) NOT NULL,
    first_name character varying(255),
    last_name character varying(255),
    active_status public.status_enum DEFAULT 'Active'::public.status_enum NOT NULL
);


ALTER TABLE ccn_staff.member OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 53006)
-- Name: member_language; Type: TABLE; Schema: ccn_staff; Owner: postgres
--

CREATE TABLE ccn_staff.member_language (
    member_id integer NOT NULL,
    language_id integer NOT NULL,
    active_status public.status_enum DEFAULT 'Active'::public.status_enum NOT NULL,
    updated_by_staff_id integer
);


ALTER TABLE ccn_staff.member_language OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 53029)
-- Name: member_language_history; Type: TABLE; Schema: ccn_staff; Owner: postgres
--

CREATE TABLE ccn_staff.member_language_history (
    history_id integer NOT NULL,
    member_id integer NOT NULL,
    language_id integer NOT NULL,
    updated_by_staff_id integer,
    active_status public.status_enum,
    change_type character varying(20) NOT NULL,
    change_timestamp timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE ccn_staff.member_language_history OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 53027)
-- Name: member_language_history_history_id_seq; Type: SEQUENCE; Schema: ccn_staff; Owner: postgres
--

CREATE SEQUENCE ccn_staff.member_language_history_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ccn_staff.member_language_history_history_id_seq OWNER TO postgres;

--
-- TOC entry 3143 (class 0 OID 0)
-- Dependencies: 219
-- Name: member_language_history_history_id_seq; Type: SEQUENCE OWNED BY; Schema: ccn_staff; Owner: postgres
--

ALTER SEQUENCE ccn_staff.member_language_history_history_id_seq OWNED BY ccn_staff.member_language_history.history_id;


--
-- TOC entry 216 (class 1259 OID 52992)
-- Name: member_member_id_seq; Type: SEQUENCE; Schema: ccn_staff; Owner: postgres
--

CREATE SEQUENCE ccn_staff.member_member_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ccn_staff.member_member_id_seq OWNER TO postgres;

--
-- TOC entry 3144 (class 0 OID 0)
-- Dependencies: 216
-- Name: member_member_id_seq; Type: SEQUENCE OWNED BY; Schema: ccn_staff; Owner: postgres
--

ALTER SEQUENCE ccn_staff.member_member_id_seq OWNED BY ccn_staff.member.member_id;


--
-- TOC entry 204 (class 1259 OID 52867)
-- Name: role; Type: TABLE; Schema: ccn_staff; Owner: postgres
--

CREATE TABLE ccn_staff.role (
    role_id integer NOT NULL,
    role_name character varying(255) NOT NULL,
    role_status public.status_enum NOT NULL,
    lob public.lob_enum NOT NULL
);


ALTER TABLE ccn_staff.role OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 52865)
-- Name: role_role_id_seq; Type: SEQUENCE; Schema: ccn_staff; Owner: postgres
--

CREATE SEQUENCE ccn_staff.role_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ccn_staff.role_role_id_seq OWNER TO postgres;

--
-- TOC entry 3145 (class 0 OID 0)
-- Dependencies: 203
-- Name: role_role_id_seq; Type: SEQUENCE OWNED BY; Schema: ccn_staff; Owner: postgres
--

ALTER SEQUENCE ccn_staff.role_role_id_seq OWNED BY ccn_staff.role.role_id;


--
-- TOC entry 210 (class 1259 OID 52930)
-- Name: staff; Type: TABLE; Schema: ccn_staff; Owner: postgres
--

CREATE TABLE ccn_staff.staff (
    staff_id integer NOT NULL,
    racf character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    team_id integer,
    role_id integer,
    manager_id integer,
    lob public.lob_enum NOT NULL,
    active_status public.status_enum DEFAULT 'Active'::public.status_enum NOT NULL
);


ALTER TABLE ccn_staff.staff OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 52973)
-- Name: staff_history; Type: TABLE; Schema: ccn_staff; Owner: postgres
--

CREATE TABLE ccn_staff.staff_history (
    history_id integer NOT NULL,
    staff_id integer NOT NULL,
    racf character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    team_id integer,
    role_id integer,
    manager_id integer,
    lob public.lob_enum,
    active_status public.status_enum,
    change_type character varying(20) NOT NULL,
    change_timestamp timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE ccn_staff.staff_history OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 52971)
-- Name: staff_history_history_id_seq; Type: SEQUENCE; Schema: ccn_staff; Owner: postgres
--

CREATE SEQUENCE ccn_staff.staff_history_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ccn_staff.staff_history_history_id_seq OWNER TO postgres;

--
-- TOC entry 3146 (class 0 OID 0)
-- Dependencies: 212
-- Name: staff_history_history_id_seq; Type: SEQUENCE OWNED BY; Schema: ccn_staff; Owner: postgres
--

ALTER SEQUENCE ccn_staff.staff_history_history_id_seq OWNED BY ccn_staff.staff_history.history_id;


--
-- TOC entry 211 (class 1259 OID 52955)
-- Name: staff_language; Type: TABLE; Schema: ccn_staff; Owner: postgres
--

CREATE TABLE ccn_staff.staff_language (
    staff_id integer NOT NULL,
    language_id integer NOT NULL,
    active_status public.status_enum DEFAULT 'Active'::public.status_enum NOT NULL,
    staff_language_id integer NOT NULL
);


ALTER TABLE ccn_staff.staff_language OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 52985)
-- Name: staff_language_history; Type: TABLE; Schema: ccn_staff; Owner: postgres
--

CREATE TABLE ccn_staff.staff_language_history (
    history_id integer NOT NULL,
    staff_id integer NOT NULL,
    language_id integer NOT NULL,
    active_status public.status_enum,
    change_type character varying(20) NOT NULL,
    change_timestamp timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE ccn_staff.staff_language_history OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 52983)
-- Name: staff_language_history_history_id_seq; Type: SEQUENCE; Schema: ccn_staff; Owner: postgres
--

CREATE SEQUENCE ccn_staff.staff_language_history_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ccn_staff.staff_language_history_history_id_seq OWNER TO postgres;

--
-- TOC entry 3147 (class 0 OID 0)
-- Dependencies: 214
-- Name: staff_language_history_history_id_seq; Type: SEQUENCE OWNED BY; Schema: ccn_staff; Owner: postgres
--

ALTER SEQUENCE ccn_staff.staff_language_history_history_id_seq OWNED BY ccn_staff.staff_language_history.history_id;


--
-- TOC entry 221 (class 1259 OID 53056)
-- Name: staff_language_staff_language_id_seq; Type: SEQUENCE; Schema: ccn_staff; Owner: postgres
--

CREATE SEQUENCE ccn_staff.staff_language_staff_language_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ccn_staff.staff_language_staff_language_id_seq OWNER TO postgres;

--
-- TOC entry 3148 (class 0 OID 0)
-- Dependencies: 221
-- Name: staff_language_staff_language_id_seq; Type: SEQUENCE OWNED BY; Schema: ccn_staff; Owner: postgres
--

ALTER SEQUENCE ccn_staff.staff_language_staff_language_id_seq OWNED BY ccn_staff.staff_language.staff_language_id;


--
-- TOC entry 209 (class 1259 OID 52928)
-- Name: staff_staff_id_seq; Type: SEQUENCE; Schema: ccn_staff; Owner: postgres
--

CREATE SEQUENCE ccn_staff.staff_staff_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ccn_staff.staff_staff_id_seq OWNER TO postgres;

--
-- TOC entry 3149 (class 0 OID 0)
-- Dependencies: 209
-- Name: staff_staff_id_seq; Type: SEQUENCE OWNED BY; Schema: ccn_staff; Owner: postgres
--

ALTER SEQUENCE ccn_staff.staff_staff_id_seq OWNED BY ccn_staff.staff.staff_id;


--
-- TOC entry 202 (class 1259 OID 52859)
-- Name: team; Type: TABLE; Schema: ccn_staff; Owner: postgres
--

CREATE TABLE ccn_staff.team (
    team_id integer NOT NULL,
    team_name character varying(255) NOT NULL,
    team_status public.status_enum NOT NULL,
    lob public.lob_enum NOT NULL
);


ALTER TABLE ccn_staff.team OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 52857)
-- Name: team_team_id_seq; Type: SEQUENCE; Schema: ccn_staff; Owner: postgres
--

CREATE SEQUENCE ccn_staff.team_team_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ccn_staff.team_team_id_seq OWNER TO postgres;

--
-- TOC entry 3150 (class 0 OID 0)
-- Dependencies: 201
-- Name: team_team_id_seq; Type: SEQUENCE OWNED BY; Schema: ccn_staff; Owner: postgres
--

ALTER SEQUENCE ccn_staff.team_team_id_seq OWNED BY ccn_staff.team.team_id;


--
-- TOC entry 2929 (class 2604 OID 52886)
-- Name: language language_id; Type: DEFAULT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.language ALTER COLUMN language_id SET DEFAULT nextval('ccn_staff.language_language_id_seq'::regclass);


--
-- TOC entry 2928 (class 2604 OID 52878)
-- Name: manager manager_id; Type: DEFAULT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.manager ALTER COLUMN manager_id SET DEFAULT nextval('ccn_staff.manager_manager_id_seq'::regclass);


--
-- TOC entry 2938 (class 2604 OID 52997)
-- Name: member member_id; Type: DEFAULT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.member ALTER COLUMN member_id SET DEFAULT nextval('ccn_staff.member_member_id_seq'::regclass);


--
-- TOC entry 2941 (class 2604 OID 53032)
-- Name: member_language_history history_id; Type: DEFAULT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.member_language_history ALTER COLUMN history_id SET DEFAULT nextval('ccn_staff.member_language_history_history_id_seq'::regclass);


--
-- TOC entry 2927 (class 2604 OID 52870)
-- Name: role role_id; Type: DEFAULT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.role ALTER COLUMN role_id SET DEFAULT nextval('ccn_staff.role_role_id_seq'::regclass);


--
-- TOC entry 2930 (class 2604 OID 52933)
-- Name: staff staff_id; Type: DEFAULT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff ALTER COLUMN staff_id SET DEFAULT nextval('ccn_staff.staff_staff_id_seq'::regclass);


--
-- TOC entry 2934 (class 2604 OID 52976)
-- Name: staff_history history_id; Type: DEFAULT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff_history ALTER COLUMN history_id SET DEFAULT nextval('ccn_staff.staff_history_history_id_seq'::regclass);


--
-- TOC entry 2933 (class 2604 OID 53058)
-- Name: staff_language staff_language_id; Type: DEFAULT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff_language ALTER COLUMN staff_language_id SET DEFAULT nextval('ccn_staff.staff_language_staff_language_id_seq'::regclass);


--
-- TOC entry 2936 (class 2604 OID 52988)
-- Name: staff_language_history history_id; Type: DEFAULT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff_language_history ALTER COLUMN history_id SET DEFAULT nextval('ccn_staff.staff_language_history_history_id_seq'::regclass);


--
-- TOC entry 2926 (class 2604 OID 52862)
-- Name: team team_id; Type: DEFAULT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.team ALTER COLUMN team_id SET DEFAULT nextval('ccn_staff.team_team_id_seq'::regclass);


--
-- TOC entry 2950 (class 2606 OID 52888)
-- Name: language language_pkey; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.language
    ADD CONSTRAINT language_pkey PRIMARY KEY (language_id);


--
-- TOC entry 2948 (class 2606 OID 52880)
-- Name: manager manager_pkey; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.manager
    ADD CONSTRAINT manager_pkey PRIMARY KEY (manager_id);


--
-- TOC entry 2962 (class 2606 OID 53005)
-- Name: member member_cipid_key; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.member
    ADD CONSTRAINT member_cipid_key UNIQUE (cipid);


--
-- TOC entry 2968 (class 2606 OID 53035)
-- Name: member_language_history member_language_history_pkey; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.member_language_history
    ADD CONSTRAINT member_language_history_pkey PRIMARY KEY (history_id);


--
-- TOC entry 2966 (class 2606 OID 53011)
-- Name: member_language member_language_pkey; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.member_language
    ADD CONSTRAINT member_language_pkey PRIMARY KEY (member_id, language_id);


--
-- TOC entry 2964 (class 2606 OID 53003)
-- Name: member member_pkey; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.member
    ADD CONSTRAINT member_pkey PRIMARY KEY (member_id);


--
-- TOC entry 2946 (class 2606 OID 52872)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (role_id);


--
-- TOC entry 2958 (class 2606 OID 52982)
-- Name: staff_history staff_history_pkey; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff_history
    ADD CONSTRAINT staff_history_pkey PRIMARY KEY (history_id);


--
-- TOC entry 2960 (class 2606 OID 52991)
-- Name: staff_language_history staff_language_history_pkey; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff_language_history
    ADD CONSTRAINT staff_language_history_pkey PRIMARY KEY (history_id);


--
-- TOC entry 2954 (class 2606 OID 53064)
-- Name: staff_language staff_language_pkey; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff_language
    ADD CONSTRAINT staff_language_pkey PRIMARY KEY (staff_language_id);


--
-- TOC entry 2956 (class 2606 OID 53066)
-- Name: staff_language staff_language_staff_id_language_id_key; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff_language
    ADD CONSTRAINT staff_language_staff_id_language_id_key UNIQUE (staff_id, language_id);


--
-- TOC entry 2952 (class 2606 OID 52939)
-- Name: staff staff_pkey; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff
    ADD CONSTRAINT staff_pkey PRIMARY KEY (staff_id);


--
-- TOC entry 2944 (class 2606 OID 52864)
-- Name: team team_pkey; Type: CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (team_id);


--
-- TOC entry 2978 (class 2620 OID 53049)
-- Name: staff staff_after_delete; Type: TRIGGER; Schema: ccn_staff; Owner: postgres
--

CREATE TRIGGER staff_after_delete AFTER DELETE ON ccn_staff.staff FOR EACH ROW EXECUTE FUNCTION ccn_staff.staff_delete_trigger();


--
-- TOC entry 2979 (class 2620 OID 53045)
-- Name: staff staff_after_insert; Type: TRIGGER; Schema: ccn_staff; Owner: postgres
--

CREATE TRIGGER staff_after_insert AFTER INSERT ON ccn_staff.staff FOR EACH ROW EXECUTE FUNCTION ccn_staff.staff_insert_trigger();


--
-- TOC entry 2980 (class 2620 OID 53047)
-- Name: staff staff_after_update; Type: TRIGGER; Schema: ccn_staff; Owner: postgres
--

CREATE TRIGGER staff_after_update AFTER UPDATE ON ccn_staff.staff FOR EACH ROW EXECUTE FUNCTION ccn_staff.staff_update_trigger();


--
-- TOC entry 2981 (class 2620 OID 53055)
-- Name: staff_language staff_language_after_delete; Type: TRIGGER; Schema: ccn_staff; Owner: postgres
--

CREATE TRIGGER staff_language_after_delete AFTER DELETE ON ccn_staff.staff_language FOR EACH ROW EXECUTE FUNCTION ccn_staff.staff_language_delete_trigger();


--
-- TOC entry 2982 (class 2620 OID 53051)
-- Name: staff_language staff_language_after_insert; Type: TRIGGER; Schema: ccn_staff; Owner: postgres
--

CREATE TRIGGER staff_language_after_insert AFTER INSERT ON ccn_staff.staff_language FOR EACH ROW EXECUTE FUNCTION ccn_staff.staff_language_insert_trigger();


--
-- TOC entry 2983 (class 2620 OID 53053)
-- Name: staff_language staff_language_after_update; Type: TRIGGER; Schema: ccn_staff; Owner: postgres
--

CREATE TRIGGER staff_language_after_update AFTER UPDATE ON ccn_staff.staff_language FOR EACH ROW EXECUTE FUNCTION ccn_staff.staff_language_update_trigger();


--
-- TOC entry 2977 (class 2606 OID 53036)
-- Name: member_language_history member_language_history_updated_by_staff_id_fkey; Type: FK CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.member_language_history
    ADD CONSTRAINT member_language_history_updated_by_staff_id_fkey FOREIGN KEY (updated_by_staff_id) REFERENCES ccn_staff.staff(staff_id);


--
-- TOC entry 2974 (class 2606 OID 53017)
-- Name: member_language member_language_language_id_fkey; Type: FK CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.member_language
    ADD CONSTRAINT member_language_language_id_fkey FOREIGN KEY (language_id) REFERENCES ccn_staff.language(language_id);


--
-- TOC entry 2975 (class 2606 OID 53012)
-- Name: member_language member_language_member_id_fkey; Type: FK CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.member_language
    ADD CONSTRAINT member_language_member_id_fkey FOREIGN KEY (member_id) REFERENCES ccn_staff.member(member_id);


--
-- TOC entry 2976 (class 2606 OID 53022)
-- Name: member_language member_language_updated_by_staff_id_fkey; Type: FK CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.member_language
    ADD CONSTRAINT member_language_updated_by_staff_id_fkey FOREIGN KEY (updated_by_staff_id) REFERENCES ccn_staff.staff(staff_id);


--
-- TOC entry 2972 (class 2606 OID 52966)
-- Name: staff_language staff_language_language_id_fkey; Type: FK CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff_language
    ADD CONSTRAINT staff_language_language_id_fkey FOREIGN KEY (language_id) REFERENCES ccn_staff.language(language_id);


--
-- TOC entry 2973 (class 2606 OID 52961)
-- Name: staff_language staff_language_staff_id_fkey; Type: FK CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff_language
    ADD CONSTRAINT staff_language_staff_id_fkey FOREIGN KEY (staff_id) REFERENCES ccn_staff.staff(staff_id);


--
-- TOC entry 2969 (class 2606 OID 52950)
-- Name: staff staff_manager_id_fkey; Type: FK CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff
    ADD CONSTRAINT staff_manager_id_fkey FOREIGN KEY (manager_id) REFERENCES ccn_staff.manager(manager_id);


--
-- TOC entry 2970 (class 2606 OID 52945)
-- Name: staff staff_role_id_fkey; Type: FK CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff
    ADD CONSTRAINT staff_role_id_fkey FOREIGN KEY (role_id) REFERENCES ccn_staff.role(role_id);


--
-- TOC entry 2971 (class 2606 OID 52940)
-- Name: staff staff_team_id_fkey; Type: FK CONSTRAINT; Schema: ccn_staff; Owner: postgres
--

ALTER TABLE ONLY ccn_staff.staff
    ADD CONSTRAINT staff_team_id_fkey FOREIGN KEY (team_id) REFERENCES ccn_staff.team(team_id);


-- Completed on 2023-10-19 16:18:29

--
-- PostgreSQL database dump complete
--

