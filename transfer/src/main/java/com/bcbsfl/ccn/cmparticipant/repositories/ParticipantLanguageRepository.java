package com.bcbsfl.ccn.cmparticipant.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bcbsfl.ccn.cmparticipant.entites.PartipantLanguage;

import javax.transaction.Transactional;

public interface ParticipantLanguageRepository extends JpaRepository<PartipantLanguage, Integer>{
	
	@Query("Select t from PartipantLanguage t where t.cm_participant_id = ?1 and t.language_id = ?2")
	public PartipantLanguage findByLanguage_Id(long cm_participant_id, int languageId);
	
	@Query("select language_id from PartipantLanguage t where t.cm_participant_id = ?1")
	public List<Integer> getLanguageIds(long cm_participant_id);
	
	@Modifying
	@Transactional
	@Query("Delete from PartipantLanguage t where t.cm_participant_id = ?1 and t.language_id in ?2")
    void deleteByLanguageList(long cm_participant_id, List<Integer> languagesId);
}
