BEGIN;


CREATE TABLE IF NOT EXISTS ccn_staff.language
(
    language_id integer NOT NULL DEFAULT nextval('ccn_staff.language_language_id_seq'::regclass),
    lang_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    lang_status status_enum NOT NULL,
    CONSTRAINT language_pkey PRIMARY KEY (language_id)
);

CREATE TABLE IF NOT EXISTS ccn_staff.staff_language
(
    staff_id integer NOT NULL,
    language_id integer NOT NULL,
    active_status status_enum NOT NULL DEFAULT 'Active'::status_enum,
    staff_language_id integer NOT NULL DEFAULT nextval('ccn_staff.staff_language_staff_language_id_seq'::regclass),
    CONSTRAINT staff_language_pkey PRIMARY KEY (staff_language_id),
    CONSTRAINT staff_language_staff_id_language_id_key UNIQUE (staff_id, language_id)
);

CREATE TABLE IF NOT EXISTS ccn_staff.staff
(
    staff_id integer NOT NULL DEFAULT nextval('ccn_staff.staff_staff_id_seq'::regclass),
    racf character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    team_id integer,
    role_id integer,
    manager_id integer,
    lob lob_enum NOT NULL,
    active_status status_enum NOT NULL DEFAULT 'Active'::status_enum,
    CONSTRAINT staff_pkey PRIMARY KEY (staff_id)
);

CREATE TABLE IF NOT EXISTS ccn_staff.manager
(
    manager_id integer NOT NULL DEFAULT nextval('ccn_staff.manager_manager_id_seq'::regclass),
    manager_status status_enum NOT NULL,
    lob lob_enum NOT NULL,
    manager_firstname character varying(255) COLLATE pg_catalog."default",
    manager_lastname character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT manager_pkey PRIMARY KEY (manager_id)
);

CREATE TABLE IF NOT EXISTS ccn_staff.role
(
    role_id integer NOT NULL DEFAULT nextval('ccn_staff.role_role_id_seq'::regclass),
    role_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    role_status status_enum NOT NULL,
    lob lob_enum NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (role_id)
);

CREATE TABLE IF NOT EXISTS ccn_staff.team
(
    team_id integer NOT NULL DEFAULT nextval('ccn_staff.team_team_id_seq'::regclass),
    team_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    team_status status_enum NOT NULL,
    lob lob_enum NOT NULL,
    CONSTRAINT team_pkey PRIMARY KEY (team_id)
);

CREATE TABLE IF NOT EXISTS ccn_staff.member_language
(
    member_id integer NOT NULL,
    language_id integer NOT NULL,
    active_status status_enum NOT NULL DEFAULT 'Active'::status_enum,
    updated_by_staff_id integer,
    CONSTRAINT member_language_pkey PRIMARY KEY (member_id, language_id)
);

CREATE TABLE IF NOT EXISTS ccn_staff.member
(
    member_id integer NOT NULL DEFAULT nextval('ccn_staff.member_member_id_seq'::regclass),
    cipid character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    active_status status_enum NOT NULL DEFAULT 'Active'::status_enum,
    CONSTRAINT member_pkey PRIMARY KEY (member_id),
    CONSTRAINT member_cipid_key UNIQUE (cipid)
);

CREATE TABLE IF NOT EXISTS ccn_staff.member_language_history
(
    history_id integer NOT NULL DEFAULT nextval('ccn_staff.member_language_history_history_id_seq'::regclass),
    member_id integer NOT NULL,
    language_id integer NOT NULL,
    updated_by_staff_id integer,
    active_status status_enum,
    change_type character varying(20) COLLATE pg_catalog."default" NOT NULL,
    change_timestamp timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT member_language_history_pkey PRIMARY KEY (history_id)
);

ALTER TABLE IF EXISTS ccn_staff.staff_language
    ADD CONSTRAINT staff_language_language_id_fkey FOREIGN KEY (language_id)
    REFERENCES ccn_staff.language (language_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS ccn_staff.staff_language
    ADD CONSTRAINT staff_language_staff_id_fkey FOREIGN KEY (staff_id)
    REFERENCES ccn_staff.staff (staff_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS ccn_staff.staff
    ADD CONSTRAINT staff_manager_id_fkey FOREIGN KEY (manager_id)
    REFERENCES ccn_staff.manager (manager_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS ccn_staff.staff
    ADD CONSTRAINT staff_role_id_fkey FOREIGN KEY (role_id)
    REFERENCES ccn_staff.role (role_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS ccn_staff.staff
    ADD CONSTRAINT staff_team_id_fkey FOREIGN KEY (team_id)
    REFERENCES ccn_staff.team (team_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS ccn_staff.member_language
    ADD CONSTRAINT member_language_language_id_fkey FOREIGN KEY (language_id)
    REFERENCES ccn_staff.language (language_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS ccn_staff.member_language
    ADD CONSTRAINT member_language_member_id_fkey FOREIGN KEY (member_id)
    REFERENCES ccn_staff.member (member_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS ccn_staff.member_language
    ADD CONSTRAINT member_language_updated_by_staff_id_fkey FOREIGN KEY (updated_by_staff_id)
    REFERENCES ccn_staff.staff (staff_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS ccn_staff.member_language_history
    ADD CONSTRAINT member_language_history_updated_by_staff_id_fkey FOREIGN KEY (updated_by_staff_id)
    REFERENCES ccn_staff.staff (staff_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;
