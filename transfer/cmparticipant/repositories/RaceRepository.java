package com.bcbsfl.ccn.cmparticipant.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcbsfl.ccn.cmparticipant.entites.Race;

public interface RaceRepository extends JpaRepository<Race, Integer>{

	Optional<Race> findByRaceId(int raceId);
}
