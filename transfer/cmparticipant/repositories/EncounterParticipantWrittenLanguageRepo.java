package com.bcbsfl.ccn.cmparticipant.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipantWrittenLanguage;

public interface EncounterParticipantWrittenLanguageRepo extends JpaRepository<EncounterParticipantWrittenLanguage, Long>{

	Optional<List<EncounterParticipantWrittenLanguage>> findByEncounterParticipantId(long encounterParticipantId);
}
