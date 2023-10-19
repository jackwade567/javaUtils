
-- team
CREATE TABLE dlcaret.cm_team (
    team_id SERIAL PRIMARY KEY,
    team_name VARCHAR(255) NOT NULL,
    team_status status_enum NOT NULL,
    LOB lob_enum NOT NULL
);

-- role
CREATE TABLE dlcaret.cm_role (
    role_id SERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL,
    role_status status_enum NOT NULL,
    LOB lob_enum NOT NULL
);

-- manager
CREATE TABLE dlcaret.cm_manager (
    manager_id SERIAL PRIMARY KEY,
    manager_firstname VARCHAR(255) NOT NULL,
	manager_lastname VARCHAR(255) NOT NULL,
    manager_status status_enum NOT NULL,
    LOB lob_enum NOT NULL
);

-- language
CREATE TABLE dlcaret.cm_language (
    language_id SERIAL PRIMARY KEY,
    lang_name VARCHAR(255) NOT NULL,
    lang_status status_enum NOT NULL
);


CREATE TABLE dlcaret.cm_staff (
    staff_id BIGINT PRIMARY KEY,
    racf VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    team_id INT REFERENCES cm_team(team_id),
    role_id INT REFERENCES cm_role(role_id),
    manager_id INT REFERENCES cm_manager(manager_id),
    LOB lob_enum NOT NULL,
    active_status status_enum NOT NULL DEFAULT 'Active'
);

CREATE TABLE dlcaret.cm_staff_language (
	staff_language_id BIGINT PRIMARY KEY,
    staff_id BIGINT REFERENCES cm_staff(staff_id),
    language_id INT REFERENCES cm_language(language_id),
    active_status status_enum NOT NULL DEFAULT 'Active',
    UNIQUE KEY (staff_id, language_id)
);

CREATE TABLE dlcaret.staff_history (
    history_id BIGINT PRIMARY KEY,
    staff_id BIGINT NOT NULL,
    racf VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    team_id INT,
    role_id INT,
    manager_id INT,
    LOB lob_enum,
    active_status status_enum,
    change_type VARCHAR(20) NOT NULL, -- e.g., 'INSERT', 'UPDATE', 'DELETE'
    change_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- triggers

CREATE OR REPLACE FUNCTION staff_insert_trigger() RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO cm_staff_history(staff_id, racf, first_name, last_name, team_id, role_id, manager_id, LOB, active_status, change_type)
    VALUES (NEW.staff_id, NEW.racf, NEW.first_name, NEW.last_name, NEW.team_id, NEW.role_id, NEW.manager_id, NEW.LOB, NEW.active_status, 'INSERT');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cm_staff_after_insert
AFTER INSERT ON cm_staff
FOR EACH ROW
EXECUTE FUNCTION staff_insert_trigger();

CREATE OR REPLACE FUNCTION staff_update_trigger() RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO cm_staff_history(staff_id, racf, first_name, last_name, team_id, role_id, manager_id, LOB, active_status, change_type)
    VALUES (OLD.staff_id, OLD.racf, OLD.first_name, OLD.last_name, OLD.team_id, OLD.role_id, OLD.manager_id, OLD.LOB, OLD.active_status, 'UPDATE');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cm_staff_after_update
AFTER UPDATE ON cm_staff
FOR EACH ROW
EXECUTE FUNCTION staff_update_trigger();

CREATE OR REPLACE FUNCTION staff_delete_trigger() RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO cm_staff_history(staff_id, racf, first_name, last_name, team_id, role_id, manager_id, LOB, active_status, change_type)
    VALUES (OLD.staff_id, OLD.racf, OLD.first_name, OLD.last_name, OLD.team_id, OLD.role_id, OLD.manager_id, OLD.LOB, OLD.active_status, 'DELETE');
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cm_staff_after_delete
AFTER DELETE ON cm_staff
FOR EACH ROW
EXECUTE FUNCTION staff_delete_trigger();


CREATE TABLE dlcaret.cm_staff_language_history (
    history_id BIGINT PRIMARY KEY,
    staff_language_id BIGINT,
    staff_id BIGINT,
    language_id INT,
    active_status status_enum,
    change_type VARCHAR(255) NOT NULL, -- e.g., 'INSERT', 'UPDATE', 'DELETE'
    change_date TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION before_insert_staff_language()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO cm_staff_language_history (staff_language_id, staff_id, language_id, active_status, change_type)
    VALUES (NEW.staff_language_id, NEW.staff_id, NEW.language_id, NEW.active_status, 'INSERT');

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cm_trigger_before_insert_staff_language
BEFORE INSERT ON cm_staff_language
FOR EACH ROW
EXECUTE FUNCTION before_insert_staff_language();


CREATE OR REPLACE FUNCTION before_update_staff_language()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO cm_staff_language_history (staff_language_id, staff_id, language_id, active_status, change_type)
    VALUES (OLD.staff_language_id, OLD.staff_id, OLD.language_id, OLD.active_status, 'UPDATE');

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cm_trigger_before_update_staff_language
BEFORE UPDATE ON cm_staff_language
FOR EACH ROW
EXECUTE FUNCTION before_update_staff_language();


CREATE OR REPLACE FUNCTION before_delete_staff_language()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO cm_staff_language_history (staff_language_id, staff_id, language_id, active_status, change_type)
    VALUES (OLD.staff_language_id, OLD.staff_id, OLD.language_id, OLD.active_status, 'DELETE');

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cm_trigger_before_delete_staff_language
BEFORE DELETE ON cm_staff_language
FOR EACH ROW
EXECUTE FUNCTION before_delete_staff_language();

-- triggers


CREATE TABLE dlcaret.cm_member (
    member_id BIGINT PRIMARY KEY,
    cipId VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    active_status status_enum NOT NULL DEFAULT 'Active'
);

CREATE TABLE dlcaret.cm_member_language (
    member_language_id BIGINT PRIMARY KEY,
    member_id BIGINT REFERENCES cm_member(member_id),
    language_id INT REFERENCES cm_language(language_id),
    updated_by_staff_id BIGINT REFERENCES cm_staff(staff_id),  -- the staff who made the update
    active_status status_enum NOT NULL DEFAULT 'Active',
    UNIQUE (member_id, language_id)
);

CREATE TABLE dlcaret.cm_member_language_history (
    history_id BIGINT PRIMARY KEY,
    member_language_id BIGINT,
    member_id BIGINT,
    language_id INT,
    updated_by_staff_id BIGINT, -- reflecting the column name change
    active_status status_enum,
    change_type VARCHAR(255) NOT NULL,  -- e.g., 'INSERT', 'UPDATE', 'DELETE'
    change_date TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE OR REPLACE FUNCTION before_insert_member_language()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO cm_member_language_history (member_language_id, member_id, language_id, updated_by_staff_id, active_status, change_type)
    VALUES (NEW.member_language_id, NEW.member_id, NEW.language_id, NEW.updated_by_staff_id, NEW.active_status, 'INSERT');

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cm_trigger_before_insert_member_language
BEFORE INSERT ON cm_member_language
FOR EACH ROW
EXECUTE FUNCTION before_insert_member_language();



CREATE OR REPLACE FUNCTION before_update_member_language()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO cm_member_language_history (member_language_id, member_id, language_id, updated_by_staff_id, active_status, change_type)
    VALUES (OLD.member_language_id, OLD.member_id, OLD.language_id, OLD.updated_by_staff_id, OLD.active_status, 'UPDATE');

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cm_trigger_before_update_member_language
BEFORE UPDATE ON cm_member_language
FOR EACH ROW
EXECUTE FUNCTION before_update_member_language();


CREATE OR REPLACE FUNCTION before_delete_member_language()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO cm_member_language_history (member_language_id, member_id, language_id, updated_by_staff_id, active_status, change_type)
    VALUES (OLD.member_language_id, OLD.member_id, OLD.language_id, OLD.updated_by_staff_id, OLD.active_status, 'DELETE');

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cm_trigger_before_delete_member_language
BEFORE DELETE ON cm_member_language
FOR EACH ROW
EXECUTE FUNCTION before_delete_member_language();
