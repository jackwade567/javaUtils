package com.bcbsfl.ccn.cmparticipant.services;

import java.util.Map;

import com.bcbsfl.ccn.cmparticipant.dto.EncounterParticipantRequestDto;
import com.bcbsfl.ccn.cmparticipant.dto.EncounterParticipantResponseDto;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipant;

public interface EncounterParticipantService {

	EncounterParticipant addEncounterParticipant(EncounterParticipantRequestDto encounterParticipantRequestDto);
	//EncounterParticipant updateEncounterParticipant(long encounter_participant_id, EncounterParticipantDto encounterParticipantDto);
	
	EncounterParticipantResponseDto getEncounterParticipantResponse(String cipId);
}
