package com.bcbsfl.ccn.cmparticipant.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.bcbsfl.ccn.cmparticipant.entites.PartipantLanguage;
import com.bcbsfl.ccn.cmparticipant.exception.ApiErrorResponse;
import com.bcbsfl.ccn.cmparticipant.exception.ServiceErrorException;
import com.bcbsfl.ccn.cmparticipant.serviceimpl.ParticipantDetails;
import com.bcbsfl.ccn.cmparticipant.serviceimpl.ParticipantServiceImpl;

@RestController
@RequestMapping("mmp/v1/api")
public class ParticipantController {

	@Autowired
	private ParticipantServiceImpl participantServiceImpl;

	@Autowired
	private ParticipantDetails participantDetails;

	@GetMapping("CCNCONFIGS/REL")
	public ResponseEntity<Map<String, Object>> getAllData() {
		return ResponseEntity.ok(participantServiceImpl.getAllData());
	}

	@PostMapping("CCNCONFIGS/addParticipant")
	public ResponseEntity<Participant> addParticipant(@Valid @RequestBody ParticipantDto participantDto) {
		if (participantDto != null) {
			Participant participant = participantServiceImpl.addParticipant(participantDto);
			List<PartipantLanguage> languages = participantServiceImpl.addlanguages(participant.getCmParticipantId(),
					participantDto.getLanguagesId());
			// if (languages.size() == participantDto.getLanguagesId().size()) {
			return new ResponseEntity<>(participant, HttpStatus.CREATED);
			// }
//			throw new ServiceErrorException(
//					new ApiErrorResponse("404", "StaffLanguage save operation failed but participant is saved"),
//					HttpStatus.BAD_REQUEST);
		}
		throw new ServiceErrorException(new ApiErrorResponse("404", "StaffRequest is null"), HttpStatus.BAD_REQUEST);
	}

	@PutMapping("CCNCONFIGS/updateParticipant/{cm_participant_id}")
	public ResponseEntity<Participant> updateParticipant(@PathVariable Long cm_participant_id,
			@Valid @RequestBody ParticipantDto participantDto) {
		if (cm_participant_id != null && participantDto != null) {
			Participant updatedPartcipant = participantServiceImpl.updateParticipant(cm_participant_id, participantDto);
			return new ResponseEntity<>(updatedPartcipant, HttpStatus.OK);
		}
		throw new ServiceErrorException(new ApiErrorResponse("404", "StaffRequest is null"), HttpStatus.BAD_REQUEST);

	}

	@GetMapping("CCNCONFIGS/fetchAllData")
	public ResponseEntity<List<CustomParticipantDto>> fetchAllData(
			@RequestParam(required = false) String teamNameFilter,
			@RequestParam(required = false) String roleNameFilter,
			@RequestParam(required = false) String managerNameFilter,
			@RequestParam(required = false) String languageFilter, @RequestParam(required = false) String sortColumn,
			@RequestParam(required = false) String sortDirection, @RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer participantId,
			@RequestParam(required = false) Integer teamId, @RequestParam(required = false) Integer roleId,
			@RequestParam(required = false) Integer managerId, @RequestParam(required = false) Integer languageId) {
		List<CustomParticipantDto> customParticipantDto = participantServiceImpl.getCustomParticipantDto(teamNameFilter,
				roleNameFilter, managerNameFilter, languageFilter, sortColumn, sortDirection, pageSize, pageNumber,
				participantId, teamId, roleId, managerId, languageId);
		return new ResponseEntity<>(customParticipantDto, HttpStatus.OK);

	}

	@GetMapping("CCNCONFIGS/getParticipant")
	public ResponseEntity<List<ParticipantDetailsDto>> getParticipantData(
			@RequestParam(required = false) String racfName, @RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false) Integer pageNumber) {
		List<ParticipantDetailsDto> customParticipantDto = null;
		if(racfName != null && racfName.length() > 0) {
		 customParticipantDto = participantDetails.getCustomParticipantDto(racfName,
					null, null);
		} else {
		 customParticipantDto = participantDetails.getCustomParticipantDto(racfName,
				 pageSize, pageNumber);
		}
		
		return new ResponseEntity<>(customParticipantDto, HttpStatus.OK);

	}

//	@DeleteMapping("/{cm_participant_id}")
//	public ResponseEntity<Void> deleteParticipant(@PathVariable Long cm_participant_id) {
//		participantServiceImpl.deleteParticipant(cm_participant_id);
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}

}
