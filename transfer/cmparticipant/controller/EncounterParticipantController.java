package com.bcbsfl.ccn.cmparticipant.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcbsfl.ccn.cmparticipant.dto.EncounterParticipantRequestDto;
import com.bcbsfl.ccn.cmparticipant.dto.EncounterParticipantResponseDto;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipant;
import com.bcbsfl.ccn.cmparticipant.exception.ApiErrorResponse;
import com.bcbsfl.ccn.cmparticipant.exception.ServiceErrorException;
import com.bcbsfl.ccn.cmparticipant.services.EncounterParticipantServiceImpl;

@RestController
@RequestMapping("mmp/v1/api")
public class EncounterParticipantController {

	@Autowired
	private EncounterParticipantServiceImpl encounterParticipantServiceImpl;

	@PostMapping("CCNCONFIGS/addEncounterParticipant")
	public ResponseEntity<EncounterParticipant> addEncounterParticipant(
			@Valid @RequestBody EncounterParticipantRequestDto encounterParticipantRequestDto) {
		if (encounterParticipantRequestDto != null) {
			EncounterParticipant encounterParticipant = encounterParticipantServiceImpl
					.addEncounterParticipant(encounterParticipantRequestDto);

			return new ResponseEntity<>(encounterParticipant, HttpStatus.CREATED);

		}
		throw new ServiceErrorException(new ApiErrorResponse("404", "memberRequest is null"), HttpStatus.BAD_REQUEST);
	}

	@GetMapping("CCNCONFIGS/getEncounterParticipant/{memberCipId}")
	public ResponseEntity<EncounterParticipantResponseDto> getEncounterParticipant(
			@PathVariable(name = "memberCipId") String memberCipId) {
		EncounterParticipantResponseDto encounterParticipantResponseDto = encounterParticipantServiceImpl
				.getEncounterParticipantResponse(memberCipId);
		return new ResponseEntity<>(encounterParticipantResponseDto, HttpStatus.OK);
	}

//	@PutMapping("CCNCONFIGS/updateEncounterParticipant/{encounterParticipantId}")
//	public ResponseEntity<EncounterParticipant> updateEncounterParticipant(@PathVariable Long encounterParticipantId,
//			@RequestBody EncounterParticipantDto encounterParticipantDto) {
//		if (encounterParticipantId != null && encounterParticipantDto != null) {
//			EncounterParticipant updatedEncounterParticipant = encounterParticipantServiceImpl
//					.updateEncounterParticipant(encounterParticipantId, encounterParticipantDto);
//			return new ResponseEntity<>(updatedEncounterParticipant, HttpStatus.OK);
//		}
//		throw new ServiceErrorException(new ApiErrorResponse("404", "memberRequest is null"), HttpStatus.BAD_REQUEST);
//
//	}
}
