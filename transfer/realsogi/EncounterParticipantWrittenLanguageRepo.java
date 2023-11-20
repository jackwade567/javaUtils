package com.bcbsfl.ccn.realsogi.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bcbsfl.ccn.realsogi.entites.EncounterParticipantLanguage;
import com.bcbsfl.ccn.realsogi.entites.EncounterParticipantWrittenLanguage;

public interface EncounterParticipantWrittenLanguageRepo extends JpaRepository<EncounterParticipantWrittenLanguage, Long>{

	Optional<List<EncounterParticipantWrittenLanguage>> findByEncounterParticipantId(long encounterParticipantId);
	
	@Query("Select t from EncounterParticipantWrittenLanguage t where t.encounterParticipantId = ?1 and t.languageId = ?2")
	EncounterParticipantWrittenLanguage findByLanguage_Id(long encounterParticipantId, Integer languageId);

	@Query("select languageId from EncounterParticipantWrittenLanguage t where t.encounterParticipantId = ?1")
	List<Integer> getLanguageIds(long encounterParticipantId);

	@Modifying
	@Transactional
	@Query("Delete from EncounterParticipantWrittenLanguage t where t.encounterParticipantId = ?1 and t.languageId in ?2")
	void deleteByLanguageList(Long encounterParticipantId, List<Integer> deleteIds);
	
	@Query("select languageId from EncounterParticipantWrittenLanguage t where t.encounterParticipantId = ?1")
	List<Integer> getDblanguageId(Long encounterParticipantId);
	
	@Modifying
	@Transactional
	@Query("Delete from EncounterParticipantWrittenLanguage t where t.encounterParticipantId = ?1 and t.cmParticipantId in ?2")
	void deleteByMemberandStaffId(long encounterParticipantId, List<Long> cmParticipantId);
}
