-- Creating table: RACE
CREATE TABLE ccn_staff.RACE (
    race_id SERIAL PRIMARY KEY,
    race_name VARCHAR(100),
    codeset VARCHAR(50) DEFAULT 'CCN_SYSTEM',
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP,
    UNIQUE (race_name, codeset)
);

-- Creating table: ETHNICITY
CREATE TABLE ccn_staff.ETHNICITY (
    ethnicity_id SERIAL PRIMARY KEY,
    ethnicity_name VARCHAR(100),
    codeset VARCHAR(50) DEFAULT 'CCN_SYSTEM',
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP,
    UNIQUE (ethnicity_name, codeset)
);


-- Creating table: cm_participant_race
CREATE TABLE ccn_staff.cm_participant_race (
    cm_participant_race_id BIGSERIAL PRIMARY KEY,
    cm_participant_id BIGINT REFERENCES ccn_staff.cm_participant(cm_participant_id),
    race_id INT REFERENCES ccn_staff.RACE(race_id),
    active_status status_enum,
	transaction_id uuid,
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP
);

-- Creating table: cm_participant_ethnicity
CREATE TABLE ccn_staff.cm_participant_ethnicity (
    cm_participant_ethnicity_id BIGSERIAL PRIMARY KEY,
    cm_participant_id BIGINT REFERENCES ccn_staff.cm_participant(cm_participant_id),
    ethnicity_id INT REFERENCES ccn_staff.ETHNICITY(ethnicity_id),
    active_status status_enum,
	transaction_id uuid,
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP
);

-- Creating table: cm_participant_race_history
CREATE TABLE ccn_staff.cm_participant_race_history (
    cm_participant_race_history_id BIGSERIAL PRIMARY KEY,
    cm_participant_race_id BIGINT,
    cm_participant_id BIGINT,
    race_id INT,
    active_status status_enum,
	transaction_id uuid,
	change_type VARCHAR(10),
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP
);


-- Creating table: cm_participant_ethnicity_history
CREATE TABLE ccn_staff.cm_participant_ethnicity_history (
    cm_participant_ethnicity_history_id BIGSERIAL PRIMARY KEY,
    cm_participant_ethnicity_id BIGINT,
    cm_participant_id BIGINT,
    ethnicity_id INT,
    active_status status_enum,
	transaction_id uuid,
	change_type VARCHAR(10),
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP
);

-- Creating table: encounter_participant_ethnicity
CREATE TABLE ccn_staff.encounter_participant_ethnicity (
    encounter_participant_ethnicity_id BIGSERIAL PRIMARY KEY,
    encounter_participant_id BIGINT REFERENCES ccn_staff.encounter_participant(encounter_participant_id),
    ethnicity_id INT REFERENCES ccn_staff.ETHNICITY(ethnicity_id),
    updated_by_cm_participant_id BIGINT REFERENCES ccn_staff.cm_participant(cm_participant_id),
	transaction_id uuid,
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP
);

-- Creating table: encounter_participant_race
CREATE TABLE ccn_staff.encounter_participant_race (
    encounter_participant_race_id BIGSERIAL PRIMARY KEY,
    encounter_participant_id BIGINT REFERENCES ccn_staff.encounter_participant(encounter_participant_id),
    race_id INT REFERENCES ccn_staff.RACE(race_id),
    updated_by_cm_participant_id BIGINT REFERENCES ccn_staff.cm_participant(cm_participant_id),
	transaction_id uuid,
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP
);

-- Creating history table for encounter_participant_ethnicity
CREATE TABLE ccn_staff.encounter_participant_ethnicity_history (
    encounter_participant_ethnicity_history_id BIGSERIAL PRIMARY KEY,
    encounter_participant_ethnicity_id BIGINT NOT NULL,
    encounter_participant_id BIGINT,
    ethnicity_id INT,
    updated_by_cm_participant_id BIGINT,
    transaction_id UUID,
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP,
    change_type VARCHAR(10)
);

-- Creating history table for encounter_participant_race
CREATE TABLE ccn_staff.encounter_participant_race_history (
    encounter_participant_race_history_id BIGSERIAL PRIMARY KEY,
    encounter_participant_race_id BIGINT NOT NULL,
    encounter_participant_id BIGINT,
    race_id INT,
    updated_by_cm_participant_id BIGINT,
    transaction_id UUID,
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP,
    change_type VARCHAR(10)
);

CREATE OR REPLACE FUNCTION ccn_staff.encounter_participant_ethnicity_history_func()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO ccn_staff.encounter_participant_ethnicity_history(
            encounter_participant_ethnicity_id,encounter_participant_id, ethnicity_id, updated_by_cm_participant_id, 
            system_id, create_user_id, created_date_time, last_update_user_id, 
            last_update_date_time, transaction_id, change_type)
        VALUES (
            NEW.encounter_participant_ethnicity_id,NEW.encounter_participant_id, NEW.ethnicity_id, NEW.updated_by_cm_participant_id, 
            NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, 
            NEW.last_update_date_time, NEW.transaction_id, 'INSERT'
        );
        RETURN NEW;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO ccn_staff.encounter_participant_ethnicity_history(
            encounter_participant_ethnicity_id,encounter_participant_id, ethnicity_id, updated_by_cm_participant_id, 
            system_id, create_user_id, created_date_time, last_update_user_id, 
            last_update_date_time, transaction_id, change_type)
        VALUES (
            OLD.encounter_participant_ethnicity_id, OLD.encounter_participant_id, OLD.ethnicity_id, OLD.updated_by_cm_participant_id, 
            OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, 
            OLD.last_update_date_time, OLD.transaction_id, 'UPDATE'
        );
        RETURN NEW;
    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO ccn_staff.encounter_participant_ethnicity_history(
            encounter_participant_ethnicity_id,encounter_participant_id, ethnicity_id, updated_by_cm_participant_id, 
            system_id, create_user_id, created_date_time, last_update_user_id, 
            last_update_date_time, transaction_id, change_type)
        VALUES (
            OLD.encounter_participant_ethnicity_id, OLD.encounter_participant_id, OLD.ethnicity_id, OLD.updated_by_cm_participant_id, 
            OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, 
            OLD.last_update_date_time, OLD.transaction_id, 'DELETE'
        );
        RETURN OLD;
    END IF;
END;
$$ LANGUAGE plpgsql;


-- Trigger for encounter_participant_ethnicity
CREATE TRIGGER encounter_participant_ethnicity_history
AFTER INSERT OR UPDATE OR DELETE ON ccn_staff.encounter_participant_ethnicity
FOR EACH ROW EXECUTE PROCEDURE ccn_staff.encounter_participant_ethnicity_history_func();



-- Trigger for encounter_participant_race
CREATE OR REPLACE FUNCTION ccn_staff.encounter_participant_race_history_func()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO ccn_staff.encounter_participant_race_history(
            encounter_participant_race_id, encounter_participant_id, race_id, updated_by_cm_participant_id, 
            system_id, create_user_id, created_date_time, last_update_user_id, 
            last_update_date_time, transaction_id, change_type)
        VALUES (
            NEW.encounter_participant_race_id,NEW.encounter_participant_id, NEW.race_id, NEW.updated_by_cm_participant_id, 
            NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, 
            NEW.last_update_date_time, NEW.transaction_id, 'INSERT'
        );
        RETURN NEW;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO ccn_staff.encounter_participant_race_history(
            encounter_participant_race_id,encounter_participant_id, race_id, updated_by_cm_participant_id, 
            system_id, create_user_id, created_date_time, last_update_user_id, 
            last_update_date_time, transaction_id, change_type)
        VALUES (
            OLD.encounter_participant_race_id, OLD.encounter_participant_id, OLD.race_id, OLD.updated_by_cm_participant_id, 
            OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, 
            OLD.last_update_date_time, OLD.transaction_id, 'UPDATE'
        );
        RETURN NEW;
    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO ccn_staff.encounter_participant_race_history(
            encounter_participant_race_id, encounter_participant_id, race_id, updated_by_cm_participant_id, 
            system_id, create_user_id, created_date_time, last_update_user_id, 
            last_update_date_time, transaction_id, change_type)
        VALUES (
            OLD.encounter_participant_race_id, OLD.encounter_participant_id, OLD.race_id, OLD.updated_by_cm_participant_id, 
            OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, 
            OLD.last_update_date_time, OLD.transaction_id, 'DELETE'
        );
        RETURN OLD;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER encounter_participant_race_history
AFTER INSERT OR UPDATE OR DELETE ON ccn_staff.encounter_participant_race
FOR EACH ROW EXECUTE PROCEDURE ccn_staff.encounter_participant_race_history_func();
