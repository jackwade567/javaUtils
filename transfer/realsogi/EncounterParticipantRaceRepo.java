package com.bcbsfl.ccn.realsogi.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bcbsfl.ccn.realsogi.entites.EncounterParticipantRace;

public interface EncounterParticipantRaceRepo extends JpaRepository<EncounterParticipantRace, Integer>{

//	@Query("select r from EncounterParticipantRace r where r.encounter_participant_id = ?1")
//	EncounterParticipantRace findByEncounterParticipantId(long encounterParticipantId);
	
	Optional<List<EncounterParticipantRace>> findByEncounterParticipantId(long encounterParticipantId);
	
	@Query("select raceId from EncounterParticipantRace t where t.encounterParticipantId = ?1")
	List<Integer> getDbEncounterParticipantId(Long encounterParticipantId);
	
	@Query("Select t from EncounterParticipantRace t where t.encounterParticipantId = ?1 and t.raceId = ?2")
	EncounterParticipantRace findByRace_Id(long encounterParticipantId, Integer raceId);
	
	@Modifying
	@Transactional
	@Query("Delete from EncounterParticipantRace t where t.encounterParticipantId = ?1 and t.updatedByCmParticipantId in ?2")
	void deleteByMemberandStaffId(long encounterParticipantId, List<Long> updatedByCmParticipantId);
	
	@Query("select raceId from EncounterParticipantRace t where t.encounterParticipantId = ?1")
	List<Integer> getRaceIds(long encounterParticipantId);
	
	@Modifying
	@Transactional
	@Query("Delete from EncounterParticipantRace t where t.encounterParticipantId = ?1 and t.raceId in ?2")
	void deleteByRaceList(Long encounterParticipantId, List<Integer> deleteIds);
	
}
