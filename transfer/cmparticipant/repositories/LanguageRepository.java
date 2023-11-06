package com.bcbsfl.ccn.cmparticipant.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bcbsfl.ccn.cmparticipant.entites.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer>{

	@Query("select languageId from Language l where l.languageId in ?1")
	List<Integer> getlanguagesId(List<Integer> languagesId);
	
	@Query("select languageId from Language l where l.languageName = ?1")
	Integer getLanguageNameByID(String languageName);
}
