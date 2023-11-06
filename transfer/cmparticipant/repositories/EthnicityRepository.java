package com.bcbsfl.ccn.cmparticipant.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bcbsfl.ccn.cmparticipant.entites.Ethnicity;

public interface EthnicityRepository extends JpaRepository<Ethnicity, Integer>{

	Optional<Ethnicity> findByEthnicityId(int ethnicityId);
}
