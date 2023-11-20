package com.bcbsfl.ccn.realsogi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bcbsfl.ccn.realsogi.entites.EncounterParticipantLanguage;

import javax.transaction.Transactional;

public interface EncounterParticipantLanguageRepository extends JpaRepository<EncounterParticipantLanguage, Integer>{

	@Query("Select t from EncounterParticipantLanguage t where t.encounterParticipantId = ?1 and t.languageId = ?2")
	EncounterParticipantLanguage findByLanguage_Id(long encounterParticipantId, Integer languageId);

	@Query("select languageId from EncounterParticipantLanguage t where t.encounterParticipantId = ?1")
	List<Integer> getLanguageIds(long encounterParticipantId);

	@Modifying
	@Transactional
	@Query("Delete from EncounterParticipantLanguage t where t.encounterParticipantId = ?1 and t.languageId in ?2")
	void deleteByLanguageList(long encounterParticipantId, List<Integer> deleteIds);
	
	@Query("select languageId from EncounterParticipantLanguage t where t.encounterParticipantId = ?1")
	List<Integer> getlanguageIdId(Long encounterParticipantId);
	
	@Modifying
	@Transactional
	@Query("Delete from EncounterParticipantLanguage t where t.encounterParticipantId = ?1 and t.cmParticipantId in ?2")
	void deleteByMemberandStaffId(long encounterParticipantId, List<Long> cmParticipantId);
	
	Optional<List<EncounterParticipantLanguage>> findByEncounterParticipantId(long encounterParticipantId);

}
