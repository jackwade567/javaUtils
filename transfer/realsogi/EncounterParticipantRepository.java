package com.bcbsfl.ccn.realsogi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bcbsfl.ccn.realsogi.entites.EncounterParticipant;


public interface EncounterParticipantRepository extends JpaRepository<EncounterParticipant, Long> {

	@Query("select r from EncounterParticipant r where LOWER(r.cipId) = LOWER(?1)")
	Optional<EncounterParticipant> findByCipId(String cipId);

}
