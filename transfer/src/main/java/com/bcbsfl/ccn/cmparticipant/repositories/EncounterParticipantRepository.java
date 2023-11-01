package com.bcbsfl.ccn.cmparticipant.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipant;


public interface EncounterParticipantRepository extends JpaRepository<EncounterParticipant, Long> {

	@Query("select r from EncounterParticipant r where r.cipId = ?1")
	Optional<EncounterParticipant> findByCipId(String cipId);
	

}
