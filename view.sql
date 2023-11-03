CREATE OR REPLACE VIEW dlcaret.cm_staff_lang_view
 AS
 SELECT t1.cm_participant_id,
    t1.racf,
    t1.lob,
    t1.first_name,
    t1.last_name,
    t2.team_name AS team,
    t3.role_name AS role,
    t4.manager_firstname AS manager_first_name,
    t4.manager_lastname AS manager_last_name,
    t1.create_user_id,
    t1.created_date_time,
    t5.lang_name AS language
   FROM dlcaret.cm_participant t1
     LEFT JOIN dlcaret.cm_participant_language s1 ON t1.cm_participant_id = s1.cm_participant_id
     LEFT JOIN dlcaret.language t5 ON t5.language_id = s1.language_id
     LEFT JOIN dlcaret.team t2 ON t1.team_id = t2.team_id
     LEFT JOIN dlcaret.role t3 ON t1.role_id = t3.role_id
     LEFT JOIN dlcaret.manager t4 ON t1.manager_id = t4.manager_id
  WHERE t1.active_status = 'Active'::status_enum AND t1.lob <> 'Medicare'::lob_enum;

SELECT T1.FIRST_NAME AS "Member_First_Name",
	T1.LAST_NAME AS "Member_Last_Name",
	T1.CIPID AS "Member_CIP_ID",
	T1.encounter_participant_party_id AS "Menber_Party_Id",
	T4.RACF AS "Created_By_RACF",
	T2.CREATED_DATE_TIME AS "Created_Date_Time",
	T3.LANG_NAME AS "Spoken_Language"
FROM CCN_STAFF.ENCOUNTER_PARTICIPANT T1
LEFT JOIN CCN_STAFF.ENCOUNTER_PARTICIPANT_LANGUAGE T2 ON T1.ENCOUNTER_PARTICIPANT_ID = T2.ENCOUNTER_PARTICIPANT_ID
LEFT JOIN CCN_STAFF.LANGUAGE T3 ON T3.LANGUAGE_ID = T2.LANGUAGE_ID
Left JOIN CCN_STAFF.CM_PARTICIPANT T4 ON T4.CM_PARTICIPANT_ID = T2.UPDATED_BY_CM_PARTICIPANT_ID
WHERE T1.ACTIVE_STATUS = 'Active'::STATUS_ENUM;
