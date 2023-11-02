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
