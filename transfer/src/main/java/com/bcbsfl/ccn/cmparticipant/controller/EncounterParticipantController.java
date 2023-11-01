package com.bcbsfl.ccn.cmparticipant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcbsfl.ccn.cmparticipant.Services.EncounterParticipantServiceImpl;
import com.bcbsfl.ccn.cmparticipant.dto.EncounterParticipantDto;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipant;
import com.bcbsfl.ccn.cmparticipant.exception.ApiErrorResponse;
import com.bcbsfl.ccn.cmparticipant.exception.ServiceErrorException;

@RestController
@RequestMapping("mmp/api/v1")
public class EncounterParticipantController {

	@Autowired
	private EncounterParticipantServiceImpl encounterParticipantServiceImpl;

	@PostMapping("CCNCONFIGS/addEncounterParticipant")
	public ResponseEntity<EncounterParticipant> addEncounterParticipant(
			@RequestBody EncounterParticipantDto encounterParticipantDto) {
		if (encounterParticipantDto != null) {
			EncounterParticipant encounterParticipant = encounterParticipantServiceImpl
					.addEncounterParticipant(encounterParticipantDto);

			return new ResponseEntity<>(encounterParticipant, HttpStatus.CREATED);

		}
		throw new ServiceErrorException(new ApiErrorResponse("404", "memberRequest is null"), HttpStatus.BAD_REQUEST);
	}

	@PutMapping("CCNCONFIGS/updateEncounterParticipant/{encounterParticipantId}")
	public ResponseEntity<EncounterParticipant> updateEncounterParticipant(@PathVariable Long encounterParticipantId,
			@RequestBody EncounterParticipantDto encounterParticipantDto) {
		if (encounterParticipantId != null && encounterParticipantDto != null) {
			EncounterParticipant updatedEncounterParticipant = encounterParticipantServiceImpl
					.updateEncounterParticipant(encounterParticipantId, encounterParticipantDto);
			return new ResponseEntity<>(updatedEncounterParticipant, HttpStatus.OK);
		}
		throw new ServiceErrorException(new ApiErrorResponse("404", "memberRequest is null"), HttpStatus.BAD_REQUEST);

	}
}
