package com.bcbsfl.ccn.cmparticipant.ServiceImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bcbsfl.ccn.cmparticipant.Services.ParticipantService;
import com.bcbsfl.ccn.cmparticipant.dto.CustomParticipantDto;
import com.bcbsfl.ccn.cmparticipant.dto.ParticipantDto;
import com.bcbsfl.ccn.cmparticipant.dto.TwoFieldDto;
import com.bcbsfl.ccn.cmparticipant.entites.Language;
import com.bcbsfl.ccn.cmparticipant.entites.Manager;
import com.bcbsfl.ccn.cmparticipant.entites.Participant;
import com.bcbsfl.ccn.cmparticipant.entites.PartipantLanguage;
import com.bcbsfl.ccn.cmparticipant.entites.Role;
import com.bcbsfl.ccn.cmparticipant.entites.Team;
import com.bcbsfl.ccn.cmparticipant.entites.Participant.lob_enum;
import com.bcbsfl.ccn.cmparticipant.entites.Participant.status_enum;
import com.bcbsfl.ccn.cmparticipant.exception.ApiErrorResponse;
import com.bcbsfl.ccn.cmparticipant.exception.ServiceErrorException;
import com.bcbsfl.ccn.cmparticipant.repositories.LanguageRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.ManagerRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.ParticipantLanguageRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.ParticipantRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.RoleRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.TeamRepository;

@Service
public class ParticipantServiceImpl implements ParticipantService {
	@Autowired
	private ParticipantRepository participantRepository;
	@Autowired
	private ParticipantLanguageRepository participantLanguageRepository;
	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Override
	public Map<String, Object> getAllData() {

		List<Language> languageData = languageRepository.findAll();
		List<Manager> managerData = managerRepository.findAll();
		List<Role> roleData = roleRepository.findAll();
		List<Team> teamData = teamRepository.findAll();

		Map<String, Object> combinedData = new HashMap<>();
		combinedData.put("languages", languageData);
		combinedData.put("manager", managerData);
		combinedData.put("roles", roleData);
		combinedData.put("teams", teamData);

		return combinedData;

	}

	@Override
	public Participant addParticipant(ParticipantDto participantDto) {
		if (uniqueConstraintsCheck(participantDto)) {
			Participant partcipant = new Participant();
			partcipant.setFirstName(participantDto.getFirstName());
			partcipant.setLastName(participantDto.getLastName());
			partcipant.setRacf(participantDto.getRacf());
			partcipant.setActiveStatus(status_enum.valueOf(participantDto.getActiveStatus()));
			partcipant.setLob(participantDto.getLob());
			if (setTeamId(participantDto.getTeamId())) {
				partcipant.setTeam_id(participantDto.getTeamId());
			} else {
				throw new ServiceErrorException(
						new ApiErrorResponse("404", "with the team_Id, team entity is not available"),
						HttpStatus.NOT_FOUND);
			}
			if (setRoleId(participantDto.getRoleId())) {
				partcipant.setRole_id(participantDto.getRoleId());
			} else {
				throw new ServiceErrorException(
						new ApiErrorResponse("404", "with the role_Id, role entity is not available"),
						HttpStatus.NOT_FOUND);
			}
			if (setManagerId(participantDto.getManagerId())) {
				partcipant.setManager_id(participantDto.getManagerId());
			} else {
				throw new ServiceErrorException(
						new ApiErrorResponse("404", "with the Manager_Id, Manager entity is not available"),
						HttpStatus.NOT_FOUND);
			}

			participantRepository.save(partcipant);

			return partcipant;
		} else {
			throw new ServiceErrorException(new ApiErrorResponse("409", "racf id with lob is already exist"),
					HttpStatus.CONFLICT);
		}

	}
	
	public List<CustomParticipantDto> getCustomParticipantDto(String teamNameFilter, String roleNameFilter,String managerNameFilter, String languageFilter, String sortColumn, String sortDirection,Integer pageSize, Integer pageNumber) {
		List<Object[]> objects = participantRepository.getCustomParticipantDto(teamNameFilter,roleNameFilter,managerNameFilter,languageFilter,sortColumn,sortDirection,pageSize,pageNumber);
		if(objects.size() > 0) {
			List<CustomParticipantDto> customParticipantDtos = mapDto(objects);
			return customParticipantDtos;
		}
		
		throw new ServiceErrorException(new ApiErrorResponse("409", "No data Available"),
				HttpStatus.NOT_FOUND);
	}

	private List<CustomParticipantDto> mapDto(List<Object[]> tuple) {
		List<CustomParticipantDto> customParticipantDtos = new ArrayList<>();
		tuple.forEach(data -> {
			CustomParticipantDto customParticipantDto = new CustomParticipantDto();
			customParticipantDto.setCm_participant_id((BigInteger) data[0]);
			customParticipantDto.setRacf((String) data[1]);
			customParticipantDto.setLob( (String) data[2]);
			customParticipantDto.setFirstName((String) data[3]);
			customParticipantDto.setLastName((String) data[4]);
			customParticipantDto.setTeam_name((String) data[5]);
			customParticipantDto.setRole_name((String) data[6]);
			customParticipantDto.setManager_FName((String) data[7]);
			customParticipantDto.setManager_LName((String) data[8]);
			if((String) data[9] != null) {
				customParticipantDto.setLanguages(extractLanguages(data[9].toString()));
			} else {
				customParticipantDto.setLanguages(new ArrayList<>());
			}
			customParticipantDtos.add(customParticipantDto);
		});
		return customParticipantDtos;
	}
	private static List<TwoFieldDto> extractLanguages(String input) {
        List<TwoFieldDto> languageList = new ArrayList<>();
        // Use a regex pattern to extract language and id
        if(input != null) {
        	String [] split = input.split("<-->");
        	for(String string : split) {
        		Pattern pattern = Pattern.compile("(.*?)--(\\d+)");
                Matcher matcher = pattern.matcher(string);
                while (matcher.find()) {
                	String languageName = matcher.group(1).trim();
                	int id = Integer.parseInt(matcher.group(2));
                    languageList.add(new TwoFieldDto(id,languageName));
                }
        	}

            return languageList;
        }
        return languageList;
    }

	private boolean uniqueConstraintsCheck(ParticipantDto participantDto) {
		if (participantDto.getRacf() != null && participantDto.getRacf().length() > 0) {
			if (participantDto.getLob() != null && (participantDto.getLob().equals(lob_enum.Commercial))
					|| participantDto.getLob().equals(lob_enum.Medicare)) {

				if (isMatchLob(participantDto.getRacf(), participantDto.getLob())) {
					return false;
				} else {
					return true;
				}
			} else {
				throw new ServiceErrorException(new ApiErrorResponse("409", "lob should be commercial or medicare"),
						HttpStatus.CONFLICT);
			}

		} else {
			throw new ServiceErrorException(new ApiErrorResponse("409", "racf id is  null"), HttpStatus.CONFLICT);
		}

	}

	private boolean setTeamId(int teamId) {
		Optional<Team> team = teamRepository.findById(teamId);
		if (team.isPresent()) {
			return true;
		}
		return false;
	}

	private boolean setRoleId(int roleId) {
		Optional<Role> role = roleRepository.findById(roleId);
		if (role.isPresent()) {
			return true;
		}
		return false;
	}

	private boolean setManagerId(int managerId) {
		Optional<Manager> manager = managerRepository.findById(managerId);
		if (manager.isPresent()) {
			return true;
		}
		return false;
	}

	private boolean findLanguageId(int languageId) {
		Optional<Language> language = languageRepository.findById(languageId);
		if (language.isPresent()) {
			return true;
		}
		return false;
	}

	private boolean isMatchLob(String racf, lob_enum lobInput) {
		List<Participant> participants = participantRepository.findbyRacf(racf);
		if (participants != null && participants.size() > 0) {
			if (participants.stream().anyMatch(lob -> lob.getLob().equals(lobInput))) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}

	@Override
	public Participant updateParticipant(Long cm_participant_id, ParticipantDto participantDto) {
		Optional<Participant> optional = participantRepository.findById(cm_participant_id);
		if (optional.isPresent()) {
			Participant partcipant = optional.get();
			if (uniqueConstraintsCheck(participantDto)) {
				return updateParticipantEntity(cm_participant_id, participantDto, partcipant);
			} else if (!uniqueConstraintsCheck(participantDto)
					&& (partcipant.getLob().equals(participantDto.getLob()) && partcipant.getRacf().equals(participantDto.getRacf()))) {
				return updateParticipantEntity(cm_participant_id, participantDto, partcipant);
			} else {
				throw new ServiceErrorException(new ApiErrorResponse("409", "racf id with lob is already exist"),
						HttpStatus.CONFLICT);
			}

		}
		throw new ServiceErrorException(new ApiErrorResponse("404", "with the cm_participant_id, partcipant entity is not available"),
				HttpStatus.NOT_FOUND);
	}

	private Participant updateParticipantEntity(Long cm_participant_id, ParticipantDto participantDto, Participant partcipant) {
		partcipant.setCm_participant_id(cm_participant_id);
		partcipant.setRacf(participantDto.getRacf());
		partcipant.setFirstName(participantDto.getFirstName());
		partcipant.setLastName(participantDto.getLastName());
		partcipant.setActiveStatus(status_enum.valueOf(participantDto.getActiveStatus()));
		partcipant.setLob(participantDto.getLob());

		if (setTeamId(participantDto.getTeamId())) {
			partcipant.setTeam_id(participantDto.getTeamId());

		} else {

			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the team_Id, team entity is not available"),
					HttpStatus.NOT_FOUND);
		}

		if (setRoleId(participantDto.getRoleId())) {
			partcipant.setRole_id(participantDto.getRoleId());

		} else {
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the role_Id, role entity is not available"),
					HttpStatus.NOT_FOUND);
		}

		if (setManagerId(participantDto.getManagerId())) {
			partcipant.setManager_id(participantDto.getManagerId());

		} else {
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the Manager_Id, Manager entity is not available"),
					HttpStatus.NOT_FOUND);
		}
		List<PartipantLanguage> languages = UpdateLanguages(cm_participant_id, participantDto.getLanguagesId());
		participantRepository.save(partcipant);
		return participantRepository.getById(cm_participant_id);
	}

	@Override
	public void deleteParticipant(Long cm_participant_id) {
		participantRepository.deleteById(cm_participant_id);
	}

	public List<PartipantLanguage> addlanguages(Long cm_participant_id, List<Integer> languagesId) {
		List<PartipantLanguage> participantLanguages = new ArrayList<>();
		Optional<Participant> optional = participantRepository.findById(cm_participant_id);
		if (optional.isPresent() && languagesId != null) {
			for (Integer languageId : languagesId) {
				if (findLanguageId(languageId)) {
					PartipantLanguage participantLanguage = new PartipantLanguage();
					participantLanguage.setCm_participant_id(cm_participant_id);
					participantLanguage.setLanguage_id(languageId);
					participantLanguages.add(participantLanguage);
				} else {
					throw new ServiceErrorException(
							new ApiErrorResponse("404", "Language _id " + languageId + " is not found"),
							HttpStatus.NOT_FOUND);
				}
			}
			participantLanguageRepository.saveAll(participantLanguages);
			return participantLanguages;
		}

		throw new ServiceErrorException(
				new ApiErrorResponse("404", "participant_language update fail, team entity is not available"),
				HttpStatus.NOT_FOUND);
	}

	public List<PartipantLanguage> UpdateLanguages(Long cm_participant_id, List<Integer> languagesId) {
		List<PartipantLanguage> participantLanguages = new ArrayList<>();
		Optional<Participant> optional = participantRepository.findById(cm_participant_id);
		if (optional.isPresent()) {
			if (languagesId != null && languagesId.size() > 0) {
				for (Integer languageId : languagesId) {
					if (findLanguageId(languageId)) {
						PartipantLanguage languages = participantLanguageRepository.findByLanguage_Id(cm_participant_id, languageId);
						if (languages == null) {
							PartipantLanguage participantLanguage = new PartipantLanguage();
							participantLanguage.setCm_participant_id(cm_participant_id);
							participantLanguage.setLanguage_id(languageId);
							participantLanguage.setActiveStatus(participantLanguage.getActiveStatus());
							// participantLanguageRepository.save(participantLanguage);
							participantLanguages.add(participantLanguage);
						}
					} else {
						throw new ServiceErrorException(
								new ApiErrorResponse("404", "Language _id " + languageId + " is not found"),
								HttpStatus.NOT_FOUND);
					}

				}
				participantLanguageRepository.saveAll(participantLanguages);
				List<Integer> dbLanguagesId = participantLanguageRepository.getLanguageIds(cm_participant_id);

				deleteOldlanguagesById(dbLanguagesId, languagesId, cm_participant_id);
			}

			return participantLanguages;
		}

		throw new ServiceErrorException(
				new ApiErrorResponse("404", "partcipant entity is not available and update failed on participant_language entity"),
				HttpStatus.NOT_FOUND);
	}

	private void deleteOldlanguagesById(List<Integer> dbLanguagesId, List<Integer> inputLanguagesId, long cm_participant_id) {

		List<Integer> deleteIds = null;
		if (dbLanguagesId != null && dbLanguagesId.size() > 0 && inputLanguagesId != null
				&& inputLanguagesId.size() > 0) {
			deleteIds = dbLanguagesId.stream().filter(e -> !inputLanguagesId.contains(e)).collect(Collectors.toList());
		}
		if (deleteIds != null) {
			System.out.println(deleteIds);
			participantLanguageRepository.deleteByLanguageList(cm_participant_id, deleteIds);
		}
	}

}
