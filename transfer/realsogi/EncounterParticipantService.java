package com.bcbsfl.ccn.realsogi.services;

import java.util.Map;

import com.bcbsfl.ccn.realsogi.dto.EncounterParticipantRequestDto;
import com.bcbsfl.ccn.realsogi.dto.EncounterParticipantResponseDto;
import com.bcbsfl.ccn.realsogi.entites.EncounterParticipant;

public interface EncounterParticipantService {

	EncounterParticipant addEncounterParticipant(EncounterParticipantRequestDto encounterParticipantRequestDto);
	
	EncounterParticipant updateEncounterParticipant(String memberCipId, EncounterParticipantRequestDto encounterParticipantRequestDto);
	
	EncounterParticipantResponseDto getEncounterParticipantResponse(String cipId);
	
	Map<String, Object> getAllData();
}
