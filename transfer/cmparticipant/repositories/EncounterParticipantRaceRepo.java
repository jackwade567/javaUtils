package com.bcbsfl.ccn.cmparticipant.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipantRace;

public interface EncounterParticipantRaceRepo extends JpaRepository<EncounterParticipantRace, Integer>{

//	@Query("select r from EncounterParticipantRace r where r.encounter_participant_id = ?1")
//	EncounterParticipantRace findByEncounterParticipantId(long encounterParticipantId);
	
	Optional<EncounterParticipantRace> findByEncounterParticipantId(long encounterParticipantId);
	
}
