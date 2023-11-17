package com.bcbsfl.ccn.realsogi.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcbsfl.ccn.realsogi.dto.EncounterParticipantRequestDto;
import com.bcbsfl.ccn.realsogi.dto.EncounterParticipantResponseDto;
import com.bcbsfl.ccn.realsogi.entites.EncounterParticipant;
import com.bcbsfl.ccn.realsogi.exception.ApiErrorResponse;
import com.bcbsfl.ccn.realsogi.exception.ServiceErrorException;
import com.bcbsfl.ccn.realsogi.services.EncounterParticipantServiceImpl;

@RestController
@RequestMapping("mmp/v1/api")
public class EncounterParticipantController {
	private static final Logger logger = LogManager.getLogger(EncounterParticipantController.class);
	
	@Autowired
	private EncounterParticipantServiceImpl encounterParticipantServiceImpl;
	
	@GetMapping("realsogi/REL")
	public ResponseEntity<Map<String, Object>> getAllData() {
		logger.info("Entering --> realsogi/REL endpoint");
		return ResponseEntity.ok(encounterParticipantServiceImpl.getAllData());
	}

	@PostMapping("realsogi/encounterparticipant")
	public ResponseEntity<EncounterParticipant> addEncounterParticipant(
			@Valid @RequestBody EncounterParticipantRequestDto encounterParticipantRequestDto) {
		logger.info("Entering --> realsogi/encounterparticipant endpoint");
		if (encounterParticipantRequestDto != null) {
			EncounterParticipant encounterParticipant = encounterParticipantServiceImpl
					.addEncounterParticipant(encounterParticipantRequestDto);

			return new ResponseEntity<>(encounterParticipant, HttpStatus.CREATED);

		}
		logger.info("Exiting --> realsogi/encounterparticipant endpoint - request body is null");
		throw new ServiceErrorException(new ApiErrorResponse("404", "memberRequest is null"), HttpStatus.BAD_REQUEST);
	}

	@GetMapping("realsogi/encounterparticipant/{memberCipId}")
	public ResponseEntity<EncounterParticipantResponseDto> getEncounterParticipant(
			@PathVariable(name = "memberCipId") String memberCipId) {
		logger.info("Entering --> realsogi/encounterparticipant/{memberCipId} endpoint");
		EncounterParticipantResponseDto encounterParticipantResponseDto = encounterParticipantServiceImpl
				.getEncounterParticipantResponse(memberCipId);
		return new ResponseEntity<>(encounterParticipantResponseDto, HttpStatus.OK);
	}
	

	@PutMapping("realsogi/encounterparticipant/{encounterParticipantId}")
	public ResponseEntity<EncounterParticipant> updateEncounterParticipant(@PathVariable Long encounterParticipantId,
			@RequestBody EncounterParticipantRequestDto encounterParticipantRequestDto) {
		logger.info("Entering --> realsogi/encounterparticipant/{memberCipId} endpoint");
		if (encounterParticipantId != null && encounterParticipantRequestDto != null) {
			EncounterParticipant updatedEncounterParticipant = encounterParticipantServiceImpl
					.updateEncounterParticipant(encounterParticipantId, encounterParticipantRequestDto);
			return new ResponseEntity<>(updatedEncounterParticipant, HttpStatus.OK);
		}
		logger.info("Entering --> realsogi/encounterparticipant/{encounterParticipantId} endpoint - request body is null");
		throw new ServiceErrorException(new ApiErrorResponse("404", "memberRequest is null"), HttpStatus.BAD_REQUEST);

	}
}
