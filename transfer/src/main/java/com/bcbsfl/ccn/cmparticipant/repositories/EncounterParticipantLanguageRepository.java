package com.bcbsfl.ccn.cmparticipant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipantLanguage;

import javax.transaction.Transactional;

public interface EncounterParticipantLanguageRepository extends JpaRepository<EncounterParticipantLanguage, Integer>{

	@Query("Select t from EncounterParticipantLanguage t where t.encounter_participant_id = ?1 and t.language_id = ?2")
	EncounterParticipantLanguage findByLanguage_Id(long encounter_participant_id, Integer languageId);

	@Query("select language_id from EncounterParticipantLanguage t where t.encounter_participant_id = ?1")
	List<Integer> getLanguageIds(long encounter_participant_id);

	@Modifying
	@Transactional
	@Query("Delete from EncounterParticipantLanguage t where t.encounter_participant_id = ?1 and t.language_id in ?2")
	void deleteByLanguageList(long encounter_participant_id, List<Integer> deleteIds);
	
	@Query("select cm_participant_id from EncounterParticipantLanguage t where t.encounter_participant_id = ?1")
	List<Long> getUpdatedByStaffId(long encounter_participant_id);
	
	@Modifying
	@Transactional
	@Query("Delete from EncounterParticipantLanguage t where t.encounter_participant_id = ?1 and t.cm_participant_id in ?2")
	void deleteByMemberandStaffId(long encounter_participant_id, List<Long> cm_participant_id);

}
