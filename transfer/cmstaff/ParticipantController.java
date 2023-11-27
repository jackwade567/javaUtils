package com.bcbsfl.ccn.cmparticipant.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcbsfl.ccn.cmparticipant.dto.CustomParticipantDto;
import com.bcbsfl.ccn.cmparticipant.dto.ParticipantDetailsDto;
import com.bcbsfl.ccn.cmparticipant.dto.ParticipantDto;
import com.bcbsfl.ccn.cmparticipant.entites.Participant;
import com.bcbsfl.ccn.cmparticipant.exception.ApiErrorResponse;
import com.bcbsfl.ccn.cmparticipant.exception.ServiceErrorException;
import com.bcbsfl.ccn.cmparticipant.serviceimpl.ParticipantDetails;
import com.bcbsfl.ccn.cmparticipant.serviceimpl.ParticipantServiceImpl;

@RestController
@RequestMapping("mmp/api/v1")
public class ParticipantController {
	private static final Logger logger = LogManager.getLogger(ParticipantController.class);
	
	@Autowired
	private ParticipantServiceImpl participantServiceImpl;

	@Autowired
	private ParticipantDetails participantDetails;

	@PostMapping("cmstaff/participant")
	public ResponseEntity<Participant> addParticipant(@Valid @RequestBody ParticipantDto participantDto) {
		logger.info("Entering --> cmstaff/participant endpoint");
		if (participantDto != null) {
			Participant participant = participantServiceImpl.addParticipant(participantDto);
			return new ResponseEntity<>(participant, HttpStatus.CREATED);
		}
		logger.info("Entering --> cmstaff/participant endpoint --> StaffRequest is null");
		throw new ServiceErrorException(new ApiErrorResponse("404", "StaffRequest is null"), HttpStatus.BAD_REQUEST);
	}

	@PutMapping("cmstaff/participant/{participantid}")
	public ResponseEntity<Participant> updateParticipant(@PathVariable Long participantid,
			@Valid @RequestBody ParticipantDto participantDto) {
		logger.info("Entering --> cmstaff/participant endpoint");
		if (participantid != null && participantDto != null) {
			Participant updatedPartcipant = participantServiceImpl.updateParticipant(participantid, participantDto);
			return new ResponseEntity<>(updatedPartcipant, HttpStatus.OK);
		}
		logger.info("Entering --> cmstaff/participant endpoint --> StaffRequest is null");
		throw new ServiceErrorException(new ApiErrorResponse("404", "StaffRequest is null"), HttpStatus.BAD_REQUEST);

	}

	@GetMapping("cmstaff/fetchallparticipants")
	public ResponseEntity<List<CustomParticipantDto>> fetchAllData(
			@RequestParam(required = false) String teamNameFilter,
			@RequestParam(required = false) String roleNameFilter,
			@RequestParam(required = false) String managerNameFilter,
			@RequestParam(required = false) String languageFilter, @RequestParam(required = false) String sortColumn,
			@RequestParam(required = false) String sortDirection, @RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer participantId,
			@RequestParam(required = false) Integer teamId, @RequestParam(required = false) Integer roleId,
			@RequestParam(required = false) Integer managerId, @RequestParam(required = false) Integer languageId) {
		logger.info("Entering --> cmstaff/participant endpoint");
		List<CustomParticipantDto> customParticipantDto = participantServiceImpl.getCustomParticipantDto(teamNameFilter,
				roleNameFilter, managerNameFilter, languageFilter, sortColumn, sortDirection, pageSize, pageNumber,
				participantId, teamId, roleId, managerId, languageId);
		return new ResponseEntity<>(customParticipantDto, HttpStatus.OK);

	}

	@GetMapping("cmstaff/participant")
	public ResponseEntity<List<ParticipantDetailsDto>> getParticipantData(
			@RequestParam(required = false) String racf, @RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false) Integer pageNumber) {
		logger.info("Entering --> cmstaff/participant endpoint");
		List<ParticipantDetailsDto> customParticipantDto = null;
		if(racf != null && racf.length() > 0) {
		 customParticipantDto = participantDetails.getCustomParticipantDto(racf,
					null, null);
		} else {
		 customParticipantDto = participantDetails.getCustomParticipantDto(racf,
				 pageSize, pageNumber);
		}
		return new ResponseEntity<>(customParticipantDto, HttpStatus.OK);
	}

	@DeleteMapping("cmstaff/participant/{cmParticipantId}")
	public ResponseEntity<String> deleteParticipant(@PathVariable Long cmParticipantId) {
		if(cmParticipantId != null) {
			participantServiceImpl.deleteParticipant(cmParticipantId);
			return new ResponseEntity<>("cmparticipantId "+cmParticipantId +" set it as Inactive",HttpStatus.OK);
		}
		throw new ServiceErrorException(new ApiErrorResponse("404", "cmParticipantId is null or empty"), HttpStatus.BAD_REQUEST);
	}

}
