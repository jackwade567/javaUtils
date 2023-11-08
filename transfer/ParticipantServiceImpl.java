package com.bcbsfl.ccn.cmparticipant.serviceimpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bcbsfl.ccn.cmparticipant.dto.CustomParticipantDto;
import com.bcbsfl.ccn.cmparticipant.dto.ParticipantDto;
import com.bcbsfl.ccn.cmparticipant.dto.ParticipantSearchCriteria;
import com.bcbsfl.ccn.cmparticipant.dto.Lang;
import com.bcbsfl.ccn.cmparticipant.entites.Ethnicity;
import com.bcbsfl.ccn.cmparticipant.entites.Language;
import com.bcbsfl.ccn.cmparticipant.entites.Manager;
import com.bcbsfl.ccn.cmparticipant.entites.Participant;
import com.bcbsfl.ccn.cmparticipant.entites.Participant.lob_enum;
import com.bcbsfl.ccn.cmparticipant.entites.Participant.status_enum;
import com.bcbsfl.ccn.cmparticipant.entites.PartipantLanguage;
import com.bcbsfl.ccn.cmparticipant.entites.Race;
import com.bcbsfl.ccn.cmparticipant.entites.Role;
import com.bcbsfl.ccn.cmparticipant.entites.Team;
import com.bcbsfl.ccn.cmparticipant.exception.ApiErrorResponse;
import com.bcbsfl.ccn.cmparticipant.exception.ServiceErrorException;
import com.bcbsfl.ccn.cmparticipant.repositories.EthnicityRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.LanguageRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.ManagerRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.ParticipantLanguageRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.ParticipantRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.RaceRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.RoleRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.TeamRepository;
import com.bcbsfl.ccn.cmparticipant.services.ParticipantService;

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
	
	@Autowired
	private EthnicityRepository ethnicityRepository;
	
	@Autowired
	private RaceRepository raceRepository;

	@Override
	public Map<String, Object> getAllData() {

		List<Language> languageData = languageRepository.findAll();
		List<Manager> managerData = managerRepository.findAll();
		List<Role> roleData = roleRepository.findAll();
		List<Team> teamData = teamRepository.findAll();

		if ( languageData.isEmpty()) {
		    throw new ServiceErrorException(new ApiErrorResponse("409", "languageData doesn't exist"), HttpStatus.CONFLICT);
		}
		if ( managerData.isEmpty()) {
		    throw new ServiceErrorException(new ApiErrorResponse("409", "managerData doesn't exist"), HttpStatus.CONFLICT);
		}
		if ( roleData.isEmpty()) {
		    throw new ServiceErrorException(new ApiErrorResponse("409", "roleData doesn't exist"), HttpStatus.CONFLICT);
		}
		if (teamData.isEmpty()) {
		    throw new ServiceErrorException(new ApiErrorResponse("409", "teamData doesn't exist"), HttpStatus.CONFLICT);
		}
		
		List<Ethnicity> ethnicities = ethnicityRepository.findAll();
		if (ethnicities.isEmpty()) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "ethnicity doesn't exist"),
					HttpStatus.CONFLICT);
		}
		List<Race> races = raceRepository.findAll();
		if (races.isEmpty()) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "race doesn't exist"), HttpStatus.CONFLICT);
		}

		Map<String, Object> combinedData = new HashMap<>();
		combinedData.put("languages", languageData);
		combinedData.put("manager", managerData);
		combinedData.put("roles", roleData);
		combinedData.put("teams", teamData);
		combinedData.put("ethnicities", ethnicities);
		combinedData.put("races", races);
		return combinedData;

	}

	@Override
	@Transactional
	public Participant addParticipant(ParticipantDto participantDto) {
		if (uniqueConstraintsCheck(participantDto)) {
			if (languageNameAndIdvalidation(participantDto)) {
				if (inputLanguageNameAndIdValidation(participantDto)) {
					Participant partcipant = new Participant();
					partcipant.setFirstName(participantDto.getFirstName());
					partcipant.setLastName(participantDto.getLastName());
					partcipant.setRacf(participantDto.getRacf());
					partcipant.setLob(lob_enum.valueOf(participantDto.getLob()));
					if (participantDto.getActiveStatus().equals("Inactive")
							|| participantDto.getActiveStatus().equals("Active")) {
						partcipant.setActiveStatus(status_enum.valueOf(participantDto.getActiveStatus()));
					} else {
						throw new ServiceErrorException(
								new ApiErrorResponse("409", "activeStatus should be Active or Inactive"),
								HttpStatus.CONFLICT);
					}
					if (setTeamId(participantDto.getTeamId())) {
						partcipant.setTeamId(participantDto.getTeamId());
					} else {
						throw new ServiceErrorException(
								new ApiErrorResponse("404", "with the team_Id, team entity is not available"),
								HttpStatus.NOT_FOUND);
					}
					if (setRoleId(participantDto.getRoleId())) {
						partcipant.setRoleId(participantDto.getRoleId());
					} else {
						throw new ServiceErrorException(
								new ApiErrorResponse("404", "with the role_Id, role entity is not available"),
								HttpStatus.NOT_FOUND);
					}
					if (setManagerId(participantDto.getManagerId())) {
						partcipant.setManagerId(participantDto.getManagerId());
					} else {
						throw new ServiceErrorException(
								new ApiErrorResponse("404", "with the Manager_Id, Manager entity is not available"),
								HttpStatus.NOT_FOUND);
					}
					if (participantDto.getLanguagesId().contains(0)) {
						throw new ServiceErrorException(new ApiErrorResponse("404",
								"Language id should not be 0, Please enter valid languageId, participant is not saved "),
								HttpStatus.NOT_FOUND);
					}
					Participant savedParticipant = participantRepository.save(partcipant);
					Long participantId = savedParticipant.getCmParticipantId();
					List<PartipantLanguage> partipantLanguages = addlanguages(participantId, participantDto.getLanguagesId());
					return partcipant;
				} else {
					throw new ServiceErrorException(
							new ApiErrorResponse("409", "invalid Input languageIds and languagename"),
							HttpStatus.CONFLICT);
				}
			} else {
				throw new ServiceErrorException(
						new ApiErrorResponse("409", "Both languageId's and language name are empty"),
						HttpStatus.CONFLICT);
			}

		} else {
			throw new ServiceErrorException(new ApiErrorResponse("409", "racf id with lob is already exist"),
					HttpStatus.CONFLICT);
		}

	}

	public List<CustomParticipantDto> getCustomParticipantDto(String teamNameFilter, String roleNameFilter,
			String managerNameFilter, String languageFilter, String sortColumn, String sortDirection, Integer pageSize,
			Integer pageNumber, Integer participantId, Integer teamId, Integer roleId, Integer managerId,
			Integer languageId) {
		List<Object[]> objects = participantRepository.getCustomParticipantDto(teamNameFilter, roleNameFilter,
				managerNameFilter, languageFilter, sortColumn, sortDirection, pageSize, pageNumber, participantId,
				teamId, roleId, managerId, languageId);
		if (!objects.isEmpty()) {
			return mapDto(objects);
		}

		throw new ServiceErrorException(new ApiErrorResponse("409", "No data Available"), HttpStatus.NOT_FOUND);
	}

	private List<CustomParticipantDto> mapDto(List<Object[]> objects) {
		List<CustomParticipantDto> customParticipantDtos = new ArrayList<>();
		objects.forEach(data -> {
			CustomParticipantDto customParticipantDto = new CustomParticipantDto();
			customParticipantDto.setCmParticipantId(castToBigInteger(data,0));
			customParticipantDto.setRacf(castToString(data,1));
			customParticipantDto.setLob(castToString(data,2));
			customParticipantDto.setFirstName(castToString(data,3));
			customParticipantDto.setLastName(castToString(data,4));
			customParticipantDto.setTeamName(castToString(data,5));
			customParticipantDto.setRoleName(castToString(data,6));
			customParticipantDto.setManagerFirstName(castToString(data,7));
			customParticipantDto.setManagerLastName(castToString(data,8));
			if ((String) data[9] != null) {
				customParticipantDto.setLanguages(extractLanguages(data[9].toString()));
			} else {
				customParticipantDto.setLanguages(new ArrayList<>());
			}
			customParticipantDtos.add(customParticipantDto);
		});
		return customParticipantDtos;
	}

	public static String castToString(Object[] data, int i) {
		if((String) data[i] != null ) {
			return (String) data[i];
		}
		return null;
	}
	public static BigInteger castToBigInteger(Object[] data, int i) {
		if((BigInteger) data[i] != null ) {
			return (BigInteger) data[i];
		}
		return null;
	}
	public static List<Lang> extractLanguages(String input) {
		List<Lang> languageList = new ArrayList<>();
		if (input != null) {
			String[] split = input.split("<-->");
			for (String string : split) {
				Pattern pattern = Pattern.compile("(.*?)--(\\d+)");
				Matcher matcher = pattern.matcher(string);
				while (matcher.find()) {
					String languageName = matcher.group(1).trim();
					int id = Integer.parseInt(matcher.group(2));
					languageList.add(new Lang(id, languageName));
				}
			}

			return languageList;
		}
		return languageList;
	}

	private boolean uniqueConstraintsCheck(ParticipantDto participantDto) {
		if (participantDto.getRacf() != null && participantDto.getRacf().length() > 0) {
			if (participantDto.getLob() != null && (participantDto.getLob().equals("Commercial"))
					|| participantDto.getLob().equals("Medicare")) {

				return !isMatchLob(participantDto.getRacf(), participantDto.getLob());
				
			} else {
				throw new ServiceErrorException(new ApiErrorResponse("409", "lob should be Commercial or Medicare"),
						HttpStatus.CONFLICT);
			}

		} else {
			throw new ServiceErrorException(new ApiErrorResponse("409", "racf id is  null"), HttpStatus.CONFLICT);
		}

	}

	private boolean setTeamId(int teamId) {
	    return teamRepository.findById(teamId).isPresent();
	}

	private boolean setRoleId(int roleId) {
	    return roleRepository.findById(roleId).isPresent();
	}
	

	private boolean setManagerId(int managerId) {
	    return managerRepository.findById(managerId).isPresent();
	}

	private boolean findLanguageId(int languageId) {
	    return languageRepository.findById(languageId).isPresent();
	}


	private boolean isMatchLob(String racf, String lobInput) {
		List<Participant> participants = participantRepository.findbyRacf(racf);
		if (participants != null && !participants.isEmpty()) {
			if (participants.stream().anyMatch(lob -> lob.getLob().equals(lob_enum.valueOf(lobInput)))) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}

	private boolean languageNameAndIdvalidation(ParticipantDto participantDto) {
		if ((participantDto.getLanguage() == null || participantDto.getLanguage().length() == 0)
				&& (participantDto.getLanguagesId() == null || participantDto.getLanguagesId().isEmpty())) {
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "Both languageId's and language name are empty"), HttpStatus.CONFLICT);
		}
		return true;
	}

	private boolean inputLanguageNameAndIdValidation(ParticipantDto participantDto) {
		if (participantDto.getLanguagesId() != null && !participantDto.getLanguagesId().isEmpty()) {
			List<Integer> dblanguageList = languageRepository.getlanguagesId(participantDto.getLanguagesId());
			if (!dblanguageList.isEmpty()) {
				List<Integer> unAvailableIds = participantDto.getLanguagesId().stream()
						.filter(id -> !dblanguageList.contains(id)).collect(Collectors.toList());
				if (!unAvailableIds.isEmpty()) {
					throw new ServiceErrorException(
							new ApiErrorResponse("409", "the followingId's are invalid" + unAvailableIds),
							HttpStatus.CONFLICT);
				}
			} else {
				throw new ServiceErrorException(
						new ApiErrorResponse("409", "the followingId's are invalid" + participantDto.getLanguagesId()),
						HttpStatus.CONFLICT);
			}

		}
		if (participantDto.getLanguage() != null && !"".equals(participantDto.getLanguage())) {
			if (stringValidation(participantDto.getLanguage())) {
				Integer languageId = languageRepository.getLanguageNameByID(participantDto.getLanguage());
				if (languageId != null) {
					if((participantDto.getLanguagesId() != null && !participantDto.getLanguagesId().isEmpty())) {
						participantDto.getLanguagesId().add(languageId);
					} else {
						List<Integer> addLanguage =  new ArrayList<>();
						addLanguage.add(languageId);
						participantDto.setLanguagesId(addLanguage);
					}
				} else {
					Language language = new Language();
					language.setLanguageName(participantDto.getLanguage());
					language.setLanguageStatus(language.getLanguageStatus());
					Language savedlanguage = languageRepository.save(language);
					if((participantDto.getLanguagesId() != null && !participantDto.getLanguagesId().isEmpty())) {
						participantDto.getLanguagesId().add(savedlanguage.getLanguageId());
					} else {
						List<Integer> addLanguage =  new ArrayList<>();
						addLanguage.add(savedlanguage.getLanguageId());
						participantDto.setLanguagesId(addLanguage);
					}
				}
			} else {
				throw new ServiceErrorException(
						new ApiErrorResponse("409", "languagename doesnot contain any numbers and Special Character :"
								+ participantDto.getLanguage()),
						HttpStatus.CONFLICT);
			}

		}
		if (!stringValidationInput(participantDto.getFirstName())) {
			throw new ServiceErrorException(new ApiErrorResponse("409",
					"Firstname should not contain any numbers and Special Character :" + participantDto.getFirstName()),
					HttpStatus.CONFLICT);
		}
		if (!stringValidationInput(participantDto.getLastName())) {
			throw new ServiceErrorException(new ApiErrorResponse("409",
					"LastName should not contain any numbers and Special Character :" + participantDto.getLastName()),
					HttpStatus.CONFLICT);
		}
		return true;
	}

	public static boolean stringValidation(String language) {
		Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
		Matcher matcher = pattern.matcher(language);
		return matcher.matches();
	}

	public static boolean stringValidationInput(String input) {
		Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	@Override
	public Participant updateParticipant(Long cmParticipantId, ParticipantDto participantDto) {
		Optional<Participant> optional = participantRepository.findById(cmParticipantId);
		if (optional.isPresent()) {
			Participant partcipant = optional.get();
			if (uniqueConstraintsCheck(participantDto)) {
				if(inputLanguageNameAndIdValidation(participantDto)) {
					return updateParticipantEntity(cmParticipantId, participantDto, partcipant);
				}
			} else if (!uniqueConstraintsCheck(participantDto)
					&& (partcipant.getLob().equals(lob_enum.valueOf(participantDto.getLob()))
							&& partcipant.getRacf().equals(participantDto.getRacf()))) {
				if(inputLanguageNameAndIdValidation(participantDto)) {
					return updateParticipantEntity(cmParticipantId, participantDto, partcipant);
				}
			} else {
				throw new ServiceErrorException(new ApiErrorResponse("409", "racf id with lob is already exist"),
						HttpStatus.CONFLICT);
			}

		}
		throw new ServiceErrorException(
				new ApiErrorResponse("404", "with the cm_participant_id, partcipant entity is not available"),
				HttpStatus.NOT_FOUND);
	}

	@Transactional
	private Participant updateParticipantEntity(Long cmParticipantId, ParticipantDto participantDto,
			Participant partcipant) {
		partcipant.setCmParticipantId(cmParticipantId);
		partcipant.setRacf(participantDto.getRacf());
		partcipant.setFirstName(participantDto.getFirstName());
		partcipant.setLastName(participantDto.getLastName());
		partcipant.setLob(lob_enum.valueOf(participantDto.getLob()));
		if (participantDto.getActiveStatus().equals("Inactive") || participantDto.getActiveStatus().equals("Active")) {
			partcipant.setActiveStatus(status_enum.valueOf(participantDto.getActiveStatus()));
		} else {
			throw new ServiceErrorException(new ApiErrorResponse("409", "activeStatus should be Active or Inactive"),
					HttpStatus.CONFLICT);
		}
		if (setTeamId(participantDto.getTeamId())) {
			partcipant.setTeamId(participantDto.getTeamId());

		} else {

			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the team_Id, team entity is not available"),
					HttpStatus.NOT_FOUND);
		}

		if (setRoleId(participantDto.getRoleId())) {
			partcipant.setRoleId(participantDto.getRoleId());

		} else {
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the role_Id, role entity is not available"),
					HttpStatus.NOT_FOUND);
		}

		if (setManagerId(participantDto.getManagerId())) {
			partcipant.setManagerId(participantDto.getManagerId());

		} else {
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the Manager_Id, Manager entity is not available"),
					HttpStatus.NOT_FOUND);
		}
		if (participantDto.getLanguagesId()!= null && participantDto.getLanguagesId().contains(0)) {
			throw new ServiceErrorException(
					new ApiErrorResponse("404",
							"Language id should not be 0, Please enter valid languageId, participant is not saved "),
					HttpStatus.NOT_FOUND);
		}
		List<PartipantLanguage> languages = updateLanguages(cmParticipantId, participantDto.getLanguagesId());
		participantRepository.save(partcipant);
		return participantRepository.getById(cmParticipantId);
	}

	@Override
	public void deleteParticipant(Long cmParticipantId) {
		participantRepository.deleteById(cmParticipantId);
	}

	public List<PartipantLanguage> addlanguages(Long cmParticipantId, List<Integer> languagesId) {
		List<PartipantLanguage> participantLanguages = new ArrayList<>();
		Optional<Participant> optional = participantRepository.findById(cmParticipantId);
		if (optional.isPresent() && languagesId != null) {
			List<Integer> languagesdis = languagesId.stream().distinct().collect(Collectors.toList());
			for (Integer languageId : languagesdis) {
				if (findLanguageId(languageId)) {
						PartipantLanguage participantLanguage = new PartipantLanguage();
						participantLanguage.setCmParticipantId(cmParticipantId);
						participantLanguage.setLanguageId(languageId);
						participantLanguage.setUuid(participantLanguage.getUuid());
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

	public List<PartipantLanguage> updateLanguages(Long cmParticipantId, List<Integer> languagesId) {
		List<PartipantLanguage> participantLanguages = new ArrayList<>();
		Optional<Participant> optional = participantRepository.findById(cmParticipantId);
		if (optional.isPresent()) {
			if (languagesId != null && !languagesId.isEmpty()) {
				List<Integer> languagesdis = languagesId.stream().distinct().collect(Collectors.toList());
				for (Integer languageId : languagesdis) {
					if (findLanguageId(languageId)) {
						PartipantLanguage languages = participantLanguageRepository.findByLanguage_Id(cmParticipantId,
								languageId);
						if (languages == null) {
							PartipantLanguage participantLanguage = new PartipantLanguage();
							participantLanguage.setCmParticipantId(cmParticipantId);
							participantLanguage.setLanguageId(languageId);
							participantLanguage.setActiveStatus(participantLanguage.getActiveStatus());
							participantLanguage.setUuid(participantLanguage.getUuid());
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
				List<Integer> dbLanguagesId = participantLanguageRepository.getLanguageIds(cmParticipantId);

				deleteOldlanguagesById(dbLanguagesId, languagesId, cmParticipantId);
			}

			return participantLanguages;
		}

		throw new ServiceErrorException(
				new ApiErrorResponse("404",
						"partcipant entity is not available and update failed on participant_language entity"),
				HttpStatus.NOT_FOUND);
	}

	private void deleteOldlanguagesById(List<Integer> dbLanguagesId, List<Integer> inputLanguagesId,
			long cmParticipantId) {

		List<Integer> deleteIds = null;
		if (dbLanguagesId != null && !dbLanguagesId.isEmpty() && inputLanguagesId != null
				&& !inputLanguagesId.isEmpty()) {
			deleteIds = dbLanguagesId.stream().filter(e -> !inputLanguagesId.contains(e)).collect(Collectors.toList());
		}
		if (deleteIds != null) {
			System.out.println(deleteIds);
			participantLanguageRepository.deleteByLanguageList(cmParticipantId, deleteIds);
		}
	}

	public List<CustomParticipantDto> getCustomParticipantDto(ParticipantSearchCriteria participantSearchCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
