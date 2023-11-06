package com.bcbsfl.ccn.cmparticipant.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipantEthnicity;

public interface EncounterParticipantEthnicityRepo extends JpaRepository<EncounterParticipantEthnicity, Integer> {

	Optional<EncounterParticipantEthnicity> findByEncounterParticipantId(long encounterParticipantId);
}
