package com.bcbsfl.ccn.realsogi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bcbsfl.ccn.realsogi.entites.Race;

public interface RaceRepository extends JpaRepository<Race, Integer>{

	Optional<Race> findByRaceId(int raceId);
	Optional<Race> findByRaceName(String raceName);
	
	@Query("select raceId from Race r where r.raceId in ?1")
	List<Integer> getRaceId(List<Integer> raceIds);
	
	@Query("select raceId from Race r where r.raceName = ?1")
	Integer getRaceNameByID(String raceName);
}
