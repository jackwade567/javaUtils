CREATE OR REPLACE FUNCTION ccn_staff.cm_participant_history_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'DELETE') THEN
        INSERT INTO ccn_staff.cm_participant_history(cm_participant_id, racf, first_name, last_name, team_id, role_id, manager_id, lob, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (OLD.cm_participant_id, OLD.racf, OLD.first_name, OLD.last_name, OLD.team_id, OLD.role_id, OLD.manager_id, OLD.lob, OLD.active_status, OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, OLD.last_update_date_time, 'DELETE');
        RETURN OLD;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO ccn_staff.cm_participant_history(cm_participant_id, racf, first_name, last_name, team_id, role_id, manager_id, lob, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (OLD.cm_participant_id, OLD.racf, OLD.first_name, OLD.last_name, OLD.team_id, OLD.role_id, OLD.manager_id, OLD.lob, OLD.active_status, OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, OLD.last_update_date_time, 'UPDATE');
        RETURN NEW;
    ELSIF (TG_OP = 'INSERT') THEN
        INSERT INTO ccn_staff.cm_participant_history(cm_participant_id, racf, first_name, last_name, team_id, role_id, manager_id, lob, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (NEW.cm_participant_id, NEW.racf, NEW.first_name, NEW.last_name, NEW.team_id, NEW.role_id, NEW.manager_id, NEW.lob, NEW.active_status, NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, NEW.last_update_date_time, 'INSERT');
        RETURN NEW;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cm_participant_history_trigger
AFTER INSERT OR UPDATE OR DELETE
ON ccn_staff.cm_participant
FOR EACH ROW
EXECUTE FUNCTION ccn_staff.cm_participant_history_trigger_function();


CREATE OR REPLACE FUNCTION ccn_staff.cm_participant_language_history_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'DELETE') THEN
        INSERT INTO ccn_staff.cm_participant_language_history(cm_participant_language_id, cm_participant_id, language_id, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (OLD.cm_participant_language_id, OLD.cm_participant_id, OLD.language_id, OLD.active_status, OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, OLD.last_update_date_time, 'DELETE');
        RETURN OLD;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO ccn_staff.cm_participant_language_history(cm_participant_language_id, cm_participant_id, language_id, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (OLD.cm_participant_language_id, OLD.cm_participant_id, OLD.language_id, OLD.active_status, OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, OLD.last_update_date_time, 'UPDATE');
        RETURN NEW;
    ELSIF (TG_OP = 'INSERT') THEN
        INSERT INTO ccn_staff.cm_participant_language_history(cm_participant_language_id, cm_participant_id, language_id, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (NEW.cm_participant_language_id, NEW.cm_participant_id, NEW.language_id, NEW.active_status, NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, NEW.last_update_date_time, 'INSERT');
        RETURN NEW;
    END IF;
    RETURN NULL; 
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cm_participant_language_history_trigger
AFTER INSERT OR UPDATE OR DELETE
ON ccn_staff.cm_participant_language
FOR EACH ROW
EXECUTE FUNCTION ccn_staff.cm_participant_language_history_trigger_function();


-- Trigger function for encounter_participant_language_history
CREATE OR REPLACE FUNCTION ccn_staff.encounter_participant_language_history_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'DELETE') THEN
        INSERT INTO ccn_staff.encounter_participant_language_history(encounter_participant_language_id, encounter_participant_id, language_id, updated_by_cm_participant_id, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (OLD.encounter_participant_language_id, OLD.encounter_participant_id, OLD.language_id, OLD.updated_by_cm_participant_id, OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, OLD.last_update_date_time, 'DELETE');
        RETURN OLD;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO ccn_staff.encounter_participant_language_history(encounter_participant_language_id, encounter_participant_id, language_id, updated_by_cm_participant_id, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (OLD.encounter_participant_language_id, OLD.encounter_participant_id, OLD.language_id, OLD.updated_by_cm_participant_id, OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, OLD.last_update_date_time, 'UPDATE');
        RETURN NEW;
    ELSIF (TG_OP = 'INSERT') THEN
        INSERT INTO ccn_staff.encounter_participant_language_history(encounter_participant_language_id, encounter_participant_id, language_id, updated_by_cm_participant_id, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (NEW.encounter_participant_language_id, NEW.encounter_participant_id, NEW.language_id, NEW.updated_by_cm_participant_id, NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, NEW.last_update_date_time, 'INSERT');
        RETURN NEW;
    END IF;
    RETURN NULL; -- This should never be reached
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER encounter_participant_language_history_trigger
AFTER INSERT OR UPDATE OR DELETE
ON ccn_staff.encounter_participant_language
FOR EACH ROW
EXECUTE FUNCTION ccn_staff.encounter_participant_language_history_trigger_function();

