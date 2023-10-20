-- Altering team table
ALTER TABLE team
ADD COLUMN system_id VARCHAR(30),
ADD COLUMN create_user_id VARCHAR(30),
ADD COLUMN created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_update_user_id VARCHAR(30),
ADD COLUMN last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Altering role table
ALTER TABLE role
ADD COLUMN system_id VARCHAR(30),
ADD COLUMN create_user_id VARCHAR(30),
ADD COLUMN created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_update_user_id VARCHAR(30),
ADD COLUMN last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Altering language table
ALTER TABLE language
ADD COLUMN system_id VARCHAR(30),
ADD COLUMN create_user_id VARCHAR(30),
ADD COLUMN created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_update_user_id VARCHAR(30),
ADD COLUMN last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Altering manager table
ALTER TABLE manager
ADD COLUMN system_id VARCHAR(30),
ADD COLUMN create_user_id VARCHAR(30),
ADD COLUMN created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_update_user_id VARCHAR(30),
ADD COLUMN last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Altering staff table
ALTER TABLE staff
ADD COLUMN system_id VARCHAR(30),
ADD COLUMN create_user_id VARCHAR(30),
ADD COLUMN created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_update_user_id VARCHAR(30),
ADD COLUMN last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Altering staff_language table
ALTER TABLE staff_language
ADD COLUMN system_id VARCHAR(30),
ADD COLUMN create_user_id VARCHAR(30),
ADD COLUMN created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_update_user_id VARCHAR(30),
ADD COLUMN last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Altering member table
ALTER TABLE member
ADD COLUMN system_id VARCHAR(30),
ADD COLUMN create_user_id VARCHAR(30),
ADD COLUMN created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_update_user_id VARCHAR(30),
ADD COLUMN last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Altering member_language table
ALTER TABLE member_language
ADD COLUMN system_id VARCHAR(30),
ADD COLUMN create_user_id VARCHAR(30),
ADD COLUMN created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_update_user_id VARCHAR(30),
ADD COLUMN last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;


-- Altering staff_history table
ALTER TABLE staff_history
DROP COLUMN change_time,
ADD COLUMN system_id VARCHAR(30),
ADD COLUMN create_user_id VARCHAR(30),
ADD COLUMN created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_update_user_id VARCHAR(30),
ADD COLUMN last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Altering staff_language_history table
ALTER TABLE staff_language_history
DROP COLUMN change_time,
ADD COLUMN system_id VARCHAR(30),
ADD COLUMN create_user_id VARCHAR(30),
ADD COLUMN created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_update_user_id VARCHAR(30),
ADD COLUMN last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

-- Altering member_language_history table
ALTER TABLE member_language_history
DROP COLUMN change_time,
ADD COLUMN system_id VARCHAR(30),
ADD COLUMN create_user_id VARCHAR(30),
ADD COLUMN created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN last_update_user_id VARCHAR(30),
ADD COLUMN last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP;


-- Trigger function for staff_history
CREATE OR REPLACE FUNCTION ccn_staff.staff_history_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'DELETE') THEN
        INSERT INTO ccn_staff.staff_history(staff_id, racf, first_name, last_name, team_id, role_id, manager_id, lob, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (OLD.staff_id, OLD.racf, OLD.first_name, OLD.last_name, OLD.team_id, OLD.role_id, OLD.manager_id, OLD.lob, OLD.active_status, OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, OLD.last_update_date_time, 'DELETE');
        RETURN OLD;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO ccn_staff.staff_history(staff_id, racf, first_name, last_name, team_id, role_id, manager_id, lob, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (NEW.staff_id, NEW.racf, NEW.first_name, NEW.last_name, NEW.team_id, NEW.role_id, NEW.manager_id, NEW.lob, NEW.active_status, NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, NEW.last_update_date_time, 'UPDATE');
        RETURN NEW;
    ELSIF (TG_OP = 'INSERT') THEN
        INSERT INTO ccn_staff.staff_history(staff_id, racf, first_name, last_name, team_id, role_id, manager_id, lob, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (NEW.staff_id, NEW.racf, NEW.first_name, NEW.last_name, NEW.team_id, NEW.role_id, NEW.manager_id, NEW.lob, NEW.active_status, NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, NEW.last_update_date_time, 'INSERT');
        RETURN NEW;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER staff_history_trigger
AFTER INSERT OR UPDATE OR DELETE
ON ccn_staff.staff
FOR EACH ROW
EXECUTE FUNCTION ccn_staff.staff_history_trigger_function();

--Trigger for staff_language_history
CREATE OR REPLACE FUNCTION ccn_staff.staff_language_history_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'DELETE') THEN
        INSERT INTO ccn_staff.staff_language_history(staff_language_id, staff_id, language_id, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (OLD.staff_language_id, OLD.staff_id, OLD.language_id, OLD.active_status, OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, OLD.last_update_date_time, 'DELETE');
        RETURN OLD;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO ccn_staff.staff_language_history(staff_language_id, staff_id, language_id, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (NEW.staff_language_id, NEW.staff_id, NEW.language_id, NEW.active_status, NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, NEW.last_update_date_time, 'UPDATE');
        RETURN NEW;
    ELSIF (TG_OP = 'INSERT') THEN
        INSERT INTO ccn_staff.staff_language_history(staff_language_id, staff_id, language_id, active_status, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (NEW.staff_language_id, NEW.staff_id, NEW.language_id, NEW.active_status, NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, NEW.last_update_date_time, 'INSERT');
        RETURN NEW;
    END IF;
    RETURN NULL; -- This should never be reached
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER staff_language_history_trigger
AFTER INSERT OR UPDATE OR DELETE
ON ccn_staff.staff_language
FOR EACH ROW
EXECUTE FUNCTION ccn_staff.staff_language_history_trigger_function();

ALTER TABLE ccn_staff.staff_language_history
ADD COLUMN staff_language_id BIGINT;

--Trigger for member_language_history

-- Trigger function for member_language_history
CREATE OR REPLACE FUNCTION ccn_staff.member_language_history_trigger_function()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'DELETE') THEN
        INSERT INTO ccn_staff.member_language_history(member_language_id, member_id, language_id, updated_by_staff_id, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (OLD.member_language_id, OLD.member_id, OLD.language_id, OLD.updated_by_staff_id, OLD.system_id, OLD.create_user_id, OLD.created_date_time, OLD.last_update_user_id, OLD.last_update_date_time, 'DELETE');
        RETURN OLD;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO ccn_staff.member_language_history(member_language_id, member_id, language_id, updated_by_staff_id, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (NEW.member_language_id, NEW.member_id, NEW.language_id, NEW.updated_by_staff_id, NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, NEW.last_update_date_time, 'UPDATE');
        RETURN NEW;
    ELSIF (TG_OP = 'INSERT') THEN
        INSERT INTO ccn_staff.member_language_history(member_language_id, member_id, language_id, updated_by_staff_id, system_id, create_user_id, created_date_time, last_update_user_id, last_update_date_time, change_type)
        VALUES (NEW.member_language_id, NEW.member_id, NEW.language_id, NEW.updated_by_staff_id, NEW.system_id, NEW.create_user_id, NEW.created_date_time, NEW.last_update_user_id, NEW.last_update_date_time, 'INSERT');
        RETURN NEW;
    END IF;
    RETURN NULL; -- This should never be reached
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER member_language_history_trigger
AFTER INSERT OR UPDATE OR DELETE
ON ccn_staff.member_language
FOR EACH ROW
EXECUTE FUNCTION ccn_staff.member_language_history_trigger_function();
