package com.bcbsfl.ccn.cmparticipant.Services;

import java.util.Map;

import com.bcbsfl.ccn.cmparticipant.dto.ParticipantDto;
import com.bcbsfl.ccn.cmparticipant.entites.Participant;

public interface ParticipantService {

	Map<String, Object> getAllData();

	Participant addParticipant(ParticipantDto participantDto);

	Participant updateParticipant(Long cm_participant_id, ParticipantDto participantDto);

	void deleteParticipant(Long cm_participant_id);

}
