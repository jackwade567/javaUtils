package com.bcbsfl.ccn.cmparticipant.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bcbsfl.ccn.cmparticipant.entites.PartipantLanguage;

public interface ParticipantLanguageRepository extends JpaRepository<PartipantLanguage, Integer>{
	
	@Query("Select t from PartipantLanguage t where t.cmParticipantId = ?1 and t.languageId = ?2")
	public PartipantLanguage findByLanguage_Id(long cmParticipantId, int languageId);
	
	@Query("Select t from PartipantLanguage t where  t.languageId = ?1")
	public PartipantLanguage findByLanguage_Id(int languageId);
	
	@Query("select languageId from PartipantLanguage t where t.cmParticipantId = ?1")
	public List<Integer> getLanguageIds(long cmParticipantId);
	
	@Modifying
	@Transactional
	@Query("Delete from PartipantLanguage t where t.cmParticipantId = ?1 and t.languageId in ?2")
    void deleteByLanguageList(long cmParticipantId, List<Integer> languagesId);
}
