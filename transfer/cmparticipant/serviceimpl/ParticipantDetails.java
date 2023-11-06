package com.bcbsfl.ccn.cmparticipant.serviceimpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bcbsfl.ccn.cmparticipant.dto.ParticipantDetailsDto;
import com.bcbsfl.ccn.cmparticipant.exception.ApiErrorResponse;
import com.bcbsfl.ccn.cmparticipant.exception.ServiceErrorException;
import com.bcbsfl.ccn.cmparticipant.repositories.ParticipantRepository;

@Service
public class ParticipantDetails {

	@Autowired
	private ParticipantRepository participantRepository;

	public List<ParticipantDetailsDto> getCustomParticipantDto(String racfName, Integer pageSize, Integer pageNumber) {
		List<Object[]> objects = participantRepository.getParticipantDetails(racfName, pageSize, pageNumber);
		if (!objects.isEmpty()) {
			return mapDto(objects);
		}

		throw new ServiceErrorException(new ApiErrorResponse("409", "No data Available"), HttpStatus.NOT_FOUND);
	}

	private List<ParticipantDetailsDto> mapDto(List<Object[]> objects) {

		List<ParticipantDetailsDto> customParticipantDtos = new ArrayList<>();
		objects.forEach(data -> {
			ParticipantDetailsDto participantDetailsDto = new ParticipantDetailsDto();
			participantDetailsDto.setCmParticipantId(((BigInteger) data[0] != null) ? (BigInteger) data[0] : null);
			participantDetailsDto.setRacf(((String) data[1] != null) ? (String) data[1] : null);
			participantDetailsDto.setLob(((String) data[2] != null) ? (String) data[2] : null);
			participantDetailsDto.setFirstName(((String) data[3] != null) ? (String) data[3] : null);
			participantDetailsDto.setLastName(((String) data[4] != null) ? (String) data[4] : null);
			participantDetailsDto.setAdRole(((String) data[5] != null) ? (String) data[5] : null);
			participantDetailsDto.setAddedByRacf(((String) data[6] != null) ? (String) data[6] : null);
			participantDetailsDto.setUpdatedByRacf(((String) data[7] != null) ? (String) data[7] : null);
			participantDetailsDto
					.setCreatedAt((String) data[8] != null ? ((String) data[8]) : null);
			participantDetailsDto
					.setUpdatedAt(((String) data[9] != null) ? ((String) data[9]) : null);
			participantDetailsDto.setTeamName(((String) data[10] != null) ? (String) data[10] : null);
			participantDetailsDto.setRoleName(((String) data[11] != null) ? (String) data[11] : null);
			participantDetailsDto.setManagerFName(((String) data[12] != null) ? (String) data[12] : null);
			participantDetailsDto.setManagerLName(((String) data[13] != null) ? (String) data[13] : null);
			if ((String) data[14] != null) {
				participantDetailsDto
						.setLanguages(Stream.of(data[14].toString().split(",")).collect(Collectors.toList()));
			} else {
				participantDetailsDto.setLanguages(new ArrayList<>());
			}
			customParticipantDtos.add(participantDetailsDto);
		});
		return customParticipantDtos;
	}
}
