package com.bcbsfl.ccn.cmparticipant.repositories;

import java.util.List;
import java.util.Optional;

import org.hibernate.type.TrueFalseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bcbsfl.ccn.cmparticipant.dto.CustomParticipantDto;
import com.bcbsfl.ccn.cmparticipant.dto.TwoFieldDto;
import com.bcbsfl.ccn.cmparticipant.entites.Participant;

import javax.persistence.NamedNativeQuery;
import javax.persistence.Tuple;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

	@Query("select e from Participant e where e.racf = ?1")
	List<Participant> findbyRacf(String racf);

	@Query("select lob from Participant e where e.racf = ?1")
	String getLob(String racf);

//	@Query(value = "select t1.cm_participant_id,t1.racf,t1.lob,t1.firstName,t1.lastName,t2.teamName,t3.roleName,t4.manager_firstname,t4.manager_lastname,STRING_AGG(t5.languageName ||  '--' || t5.language_id , '<-->') from Participant t1 join Team t2 on t1.team_id = t2.team_id join Role t3 on t1.role_id = t3.role_id join Manager t4 on t1.manager_id = t4.manager_id left join PartipantLanguage p1 on p1.cm_participant_id = t1.cm_participant_id left join Language t5 on t5.language_id = p1.language_id WHERE (t2.teamName = ?1 OR ?1 IS NULL) AND (t3.roleName = ?2 OR ?2 IS NULL) AND (CONCAT(t4.manager_firstname, ' ', t4.manager_lastname) = ?3 OR ?3 IS NULL) AND (t5.languageName = ?4 OR ?4 IS NULL) group by t1.cm_participant_id ,t2.teamName,t3.roleName,t4.manager_firstname,t4.manager_lastname ORDER BY CASE WHEN ?5 IS NULL OR ?5 = '' THEN t1.cm_participant_id END ASC, CASE WHEN ?5 IS NOT NULL AND ?5 <> '' AND ?6 = 'DESC' THEN t1.cm_participant_id END DESC  LIMIT ?7 Offset ?8 ")
//	List<Object[]> getCustomParticipantDto(String teamNameFilter, String roleNameFilter, String managerNameFilter,
//			String languageFilter, String sortColumn, String sortDirection, Integer pageSize, Integer pageNumber);
	
	
	@Query(value = "select t1.cm_participant_id,t1.racf,t1.lob,t1.first_name,t1.last_name,t2.team_name,t3.role_name,t4.manager_firstname,t4.manager_lastname,STRING_AGG(t5.lang_name || '--' || t5.language_id , '<-->') from cm_participant t1 join Team t2 on t1.team_id = t2.team_id join Role t3 on t1.role_id = t3.role_id join Manager t4 on t1.manager_id = t4.manager_id left join cm_participant_language p1 on p1.cm_participant_id = t1.cm_participant_id left join Language t5 on t5.language_id = p1.language_id WHERE (t2.team_name = ?1 OR ?1 IS NULL) AND (t3.role_name = ?2 OR ?2 IS NULL) AND (CONCAT(t4.manager_firstname, ' ', t4.manager_lastname) = ?3 OR ?3 IS NULL) AND (t5.lang_name = ?4 OR ?4 IS NULL) group by t1.cm_participant_id ,t2.team_name,t3.role_name,t4.manager_firstname,t4.manager_lastname ORDER BY CASE WHEN ?5 IS NULL OR ?5 = '' THEN t1.cm_participant_id END ASC, CASE WHEN ?5 IS NOT NULL AND ?5 <> '' AND ?6 = 'DESC' THEN t1.cm_participant_id END DESC LIMIT ?7 Offset ?8",nativeQuery = true)
	List<Object[]> getCustomParticipantDto(String teamNameFilter, String roleNameFilter, String managerNameFilter,
			String languageFilter, String sortColumn, String sortDirection, Integer pageSize, Integer pageNumber);

}
