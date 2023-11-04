CREATE TABLE ccn_staff.encounter_participant_written_language (
    encounter_participant_written_language_id BIGSERIAL PRIMARY KEY,
    encounter_participant_id BIGINT REFERENCES ccn_staff.encounter_participant(encounter_participant_id),
    language_id INT REFERENCES ccn_staff.cm_language(language_id),
    updated_by_cm_participant_id BIGINT REFERENCES ccn_staff.cm_participant(cm_participant_id), 
    active_status status_enum NOT NULL DEFAULT 'Active',
	transaction_id uuid,
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (encounter_participant_id, language_id)
);

CREATE TABLE ccn_staff.encounter_participant_written_language_history (
    encounter_participant_written_language_history_id BIGSERIAL PRIMARY KEY,
    encounter_participant_written_language_id BIGINT,
    encounter_participant_id BIGINT,
    language_id INT,
    updated_by_cm_participant_id BIGINT,
    active_status status_enum,
    transaction_id uuid,
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP,
    change_type VARCHAR(10) 
);

CREATE OR REPLACE FUNCTION ccn_staff.encounter_participant_written_language_history_func()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO ccn_staff.encounter_participant_written_language_history(
            encounter_participant_written_language_id, encounter_participant_id, language_id, updated_by_cm_participant_id, 
            active_status, system_id, create_user_id, created_date_time, last_update_user_id, 
            last_update_date_time, transaction_id, change_type)
        VALUES (
            NEW.encounter_participant_written_language_id, NEW.encounter_participant_id, NEW.language_id, NEW.updated_by_cm_participant_id, 
            NEW.active_status, NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, 
            NEW.last_update_date_time, NEW.transaction_id, 'INSERT'
        );
        RETURN NEW;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO ccn_staff.encounter_participant_written_language_history(
            encounter_participant_written_language_id, encounter_participant_id, language_id, updated_by_cm_participant_id, 
            active_status, system_id, create_user_id, created_date_time, last_update_user_id, 
            last_update_date_time, transaction_id, change_type)
        VALUES (
            OLD.encounter_participant_written_language_id, OLD.encounter_participant_id, OLD.language_id, OLD.updated_by_cm_participant_id, 
            OLD.active_status, OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, 
            OLD.last_update_date_time, OLD.transaction_id, 'UPDATE'
        );
        RETURN NEW;
    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO ccn_staff.encounter_participant_written_language_history(
            encounter_participant_written_language_id, encounter_participant_id, language_id, updated_by_cm_participant_id, 
            active_status, system_id, create_user_id, created_date_time, last_update_user_id, 
            last_update_date_time, transaction_id, change_type)
        VALUES (
            OLD.encounter_participant_written_language_id, OLD.encounter_participant_id, OLD.language_id, OLD.updated_by_cm_participant_id, 
            OLD.active_status, OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, 
            OLD.last_update_date_time, OLD.transaction_id, 'DELETE'
        );
        RETURN OLD;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- Trigger for encounter_participant_written_language
CREATE TRIGGER encounter_participant_written_language_history
AFTER INSERT OR UPDATE OR DELETE ON ccn_staff.encounter_participant_written_language
FOR EACH ROW EXECUTE PROCEDURE ccn_staff.encounter_participant_written_language_history_func();

