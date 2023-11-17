package com.bcbsfl.ccn.cmparticipant.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bcbsfl.ccn.cmparticipant.dto.ManagerDto;
import com.bcbsfl.ccn.cmparticipant.dto.ParticipantDetailsDto;
import com.bcbsfl.ccn.cmparticipant.dto.RoleDto;
import com.bcbsfl.ccn.cmparticipant.dto.TeamDto;
import com.bcbsfl.ccn.cmparticipant.exception.ApiErrorResponse;
import com.bcbsfl.ccn.cmparticipant.exception.ServiceErrorException;
import com.bcbsfl.ccn.cmparticipant.repositories.ParticipantRepository;

@Service
public class ParticipantDetails {
	private static final Logger logger = LogManager.getLogger(ParticipantDetails.class);
	
	@Autowired
	private ParticipantRepository participantRepository;

	public List<ParticipantDetailsDto> getCustomParticipantDto(String racfName, Integer pageSize, Integer pageNumber) {
		logger.info("Entering --> cmstaff/participant endpoint service getCustomParticipantDto()");
		List<Object[]> objects = participantRepository.getParticipantDetails(racfName, pageSize, pageNumber);
		if (!objects.isEmpty()) {
			return mapDto(objects);
		}
		logger.info("cmstaff/participant endpoint service getCustomParticipantDto() --> No data Available");
		throw new ServiceErrorException(new ApiErrorResponse("409", "No data Available"), HttpStatus.NOT_FOUND);
	}

	private List<ParticipantDetailsDto> mapDto(List<Object[]> objects) {

		List<ParticipantDetailsDto> customParticipantDtos = new ArrayList<>();
		objects.forEach(data -> {
			ParticipantDetailsDto participantDetailsDto = new ParticipantDetailsDto();
			participantDetailsDto.setCmParticipantId(ParticipantServiceImpl.castToBigInteger(data, 0));
			participantDetailsDto.setRacf(ParticipantServiceImpl.castToString(data, 1));
			participantDetailsDto.setLob(ParticipantServiceImpl.castToString(data, 2));
			participantDetailsDto.setFirstName(ParticipantServiceImpl.castToString(data, 3));
			participantDetailsDto.setLastName(ParticipantServiceImpl.castToString(data, 4));
			participantDetailsDto.setAdRole(ParticipantServiceImpl.castToString(data, 5));
			participantDetailsDto.setAddedByRacf(ParticipantServiceImpl.castToString(data, 6));
			participantDetailsDto.setUpdatedByRacf(ParticipantServiceImpl.castToString(data, 7));
			participantDetailsDto
					.setCreatedAt(ParticipantServiceImpl.castToString(data, 8));
			participantDetailsDto
					.setUpdatedAt(ParticipantServiceImpl.castToString(data, 9));
			participantDetailsDto.setTeam(castTeam(data[10].toString()));
			participantDetailsDto.setRole(castRole(data[11].toString()));
			participantDetailsDto.setManager(castManager(data[12].toString()));
			if ((String) data[13] != null) {
				participantDetailsDto
						.setLanguages(ParticipantServiceImpl.extractLanguages(data[13].toString()));
			} else {
				participantDetailsDto.setLanguages(new ArrayList<>());
			}
			customParticipantDtos.add(participantDetailsDto);
		});
		return customParticipantDtos;
	}
	private TeamDto castTeam(String input) {
		if(input != null) {
			TeamDto dto = new TeamDto();
			String [] split = input.split(",");
			int id = Integer.parseInt(split[0]);
			dto.setId(id);
			dto.setTeamName(split[1]);
			return dto;
		}
		return null;
	}
	private RoleDto castRole(String input) {
		if(input != null) {
			RoleDto dto = new RoleDto();
			String [] split = input.split(",");
			int id = Integer.parseInt(split[0]);
			dto.setId(id);
			dto.setRoleName(split[1]);
			return dto;
		}
		return null;
	}
	private ManagerDto castManager(String input) {
		if(input != null) {
			ManagerDto dto = new ManagerDto();
			String [] split = input.split(",");
			int id = Integer.parseInt(split[2]);
			dto.setId(id);
			dto.setManagerFirstName(split[0]);
			dto.setManagerLastName(split[1]);
			return dto;
		}
		return null;
	}
}
