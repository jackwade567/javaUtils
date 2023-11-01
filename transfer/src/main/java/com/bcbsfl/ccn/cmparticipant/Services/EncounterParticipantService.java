package com.bcbsfl.ccn.cmparticipant.Services;

import com.bcbsfl.ccn.cmparticipant.dto.EncounterParticipantDto;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipant;

public interface EncounterParticipantService {

	EncounterParticipant addEncounterParticipant(EncounterParticipantDto encounterParticipantDto);
	EncounterParticipant updateEncounterParticipant(long encounter_participant_id, EncounterParticipantDto encounterParticipantDto);
	
}
