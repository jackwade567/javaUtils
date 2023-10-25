CREATE TABLE dlcaret.cm_participant (
    cm_participant_id BIGSERIAL PRIMARY KEY,
    racf VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    team_id INT REFERENCES dlcaret.cm_team(team_id),
    role_id INT REFERENCES dlcaret.cm_role(role_id),
    manager_id INT REFERENCES dlcaret.cm_manager(manager_id),
    LOB lob_enum NOT NULL,
    active_status status_enum NOT NULL DEFAULT 'Active',
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dlcaret.cm_participant_language (
    cm_participant_language_id BIGSERIAL PRIMARY KEY,
    cm_participant_id BIGINT REFERENCES dlcaret.cm_participant(cm_participant_id),
    language_id INT REFERENCES dlcaret.cm_language(language_id),
    active_status status_enum NOT NULL DEFAULT 'Active',
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (cm_participant_id, language_id)
);

CREATE TABLE dlcaret.encounter_participant (
    encounter_participant_id BIGSERIAL PRIMARY KEY,
    cipId VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    active_status status_enum NOT NULL DEFAULT 'Active',
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dlcaret.encounter_participant_language (
    encounter_participant_language_id BIGSERIAL PRIMARY KEY,
    encounter_participant_id BIGINT REFERENCES dlcaret.encounter_participant(encounter_participant_id),
    language_id INT REFERENCES dlcaret.cm_language(language_id),
    updated_by_cm_participant_id BIGINT REFERENCES dlcaret.cm_participant(cm_participant_id), 
    active_status status_enum NOT NULL DEFAULT 'Active',
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    created_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (encounter_participant_id, language_id)
);

CREATE TABLE dlcaret.cm_participant_history (
    history_id BIGSERIAL PRIMARY KEY,
    cm_participant_id BIGINT NOT NULL,
    racf VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    team_id INT,
    role_id INT,
    manager_id INT,
    LOB lob_enum,
    active_status status_enum,
    change_type VARCHAR(20) NOT NULL,
    created_date_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dlcaret.cm_participant_language_history (
    history_id BIGSERIAL PRIMARY KEY,
    cm_participant_language_id BIGINT,
    cm_participant_id BIGINT,
    language_id INT,
    active_status status_enum,
    change_type VARCHAR(255) NOT NULL,
    created_date_time TIMESTAMP NOT NULL DEFAULT NOW(),
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dlcaret.encounter_participant_language_history (
    history_id BIGSERIAL PRIMARY KEY,
    encounter_participant_language_id BIGINT,
    encounter_participant_id BIGINT,
    language_id INT,
    updated_by_cm_participant_id BIGINT,
    active_status status_enum,
    change_type VARCHAR(255) NOT NULL,
    created_date_time TIMESTAMP NOT NULL DEFAULT NOW(),
    system_id VARCHAR(30),
    create_user_id VARCHAR(30),
    last_update_user_id VARCHAR(30),
    last_update_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
