package com.bcbsfl.ccn.cmparticipant.serviceimpl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bcbsfl.ccn.cmparticipant.dto.CustomParticipantDto;
import com.bcbsfl.ccn.cmparticipant.dto.ParticipantDto;
import com.bcbsfl.ccn.cmparticipant.dto.ParticipantSearchCriteria;
import com.bcbsfl.ccn.cmparticipant.dto.Lang;
import com.bcbsfl.ccn.cmparticipant.entites.Language;
import com.bcbsfl.ccn.cmparticipant.entites.Participant;
import com.bcbsfl.ccn.cmparticipant.entites.Participant.lob_enum;
import com.bcbsfl.ccn.cmparticipant.entites.Participant.status_enum;
import com.bcbsfl.ccn.cmparticipant.entites.PartipantLanguage;
import com.bcbsfl.ccn.cmparticipant.exception.ApiErrorResponse;
import com.bcbsfl.ccn.cmparticipant.exception.ServiceErrorException;
import com.bcbsfl.ccn.cmparticipant.repositories.LanguageRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.ManagerRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.ParticipantLanguageRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.ParticipantRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.RoleRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.TeamRepository;
import com.bcbsfl.ccn.cmparticipant.services.ParticipantService;

@Service
public class ParticipantServiceImpl implements ParticipantService {
	private static final Logger logger = LogManager.getLogger(ParticipantServiceImpl.class);
	
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
	@Transactional
	public Participant addParticipant(ParticipantDto participantDto) {
		logger.info("Entering --> cmstaff/participant endpoint service addParticipant()");
		if (!uniqueConstraintsCheck(participantDto)) {
			logger.info("cmstaff/participant endpoint service addParticipant() --> racf id with lob is already exist");
			throw new ServiceErrorException(new ApiErrorResponse("409", "racf id with lob is already exist"),
					HttpStatus.CONFLICT);
		}
		if (!languageNameAndIdvalidation(participantDto)) {
			logger.info("cmstaff/participant endpoint service addParticipant() --> Both languageId's and language name are empty");
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "Both languageId's and language name are empty"),
					HttpStatus.CONFLICT);
		}
		if (!inputLanguageNameAndIdValidation(participantDto)) {
			logger.info("cmstaff/participant endpoint service addParticipant() --> invalid Input languageIds and languagename");
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "invalid Input languageIds and languagename"),
					HttpStatus.CONFLICT);
		}
		if (!participantDto.getActiveStatus().equals("Inactive")
				&& !participantDto.getActiveStatus().equals("Active")) {
			logger.info("cmstaff/participant endpoint service addParticipant() --> activeStatus should be Active or Inactive");
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "activeStatus should be Active or Inactive"),
					HttpStatus.CONFLICT);
		}
		if (!setTeamId(participantDto.getTeamId())) {
			logger.info("cmstaff/participant endpoint service addParticipant() --> with the team_Id, "+participantDto.getTeamId()+" team entity is not available");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the team_Id, team entity is not available"),
					HttpStatus.NOT_FOUND);
		}
		if (!setRoleId(participantDto.getRoleId())) {
			logger.info("cmstaff/participant endpoint service addParticipant() --> with the role_Id, "+participantDto.getRoleId()+" role entity is not available");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the role_Id, role entity is not available"),
					HttpStatus.NOT_FOUND);
		}
		if (!setManagerId(participantDto.getManagerId())) {
			logger.info("cmstaff/participant endpoint service addParticipant() --. with the Manager_Id, "+participantDto.getManagerId()+" Manager entity is not available");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the Manager_Id, Manager entity is not available"),
					HttpStatus.NOT_FOUND);
		}
		if (participantDto.getLanguagesId().contains(0)) {
			logger.info("cmstaff/participant endpoint service addParticipant() --> Language id should not be 0, Please enter valid languageId");
			throw new ServiceErrorException(new ApiErrorResponse("404",
					"Language id should not be 0, Please enter valid languageId "),
					HttpStatus.NOT_FOUND);
		}
		Participant partcipant = new Participant();
		partcipant.setFirstName(participantDto.getFirstName());
		partcipant.setLastName(participantDto.getLastName());
		partcipant.setRacf(participantDto.getRacf());
		partcipant.setLob(lob_enum.valueOf(participantDto.getLob()));
		partcipant.setCreatedDateTime(LocalDateTime.now());
		partcipant.setUpdatedDateTime(LocalDateTime.now());
		partcipant.setActiveStatus(status_enum.valueOf(participantDto.getActiveStatus()));
		partcipant.setTeamId(participantDto.getTeamId());
		partcipant.setRoleId(participantDto.getRoleId());
		partcipant.setManagerId(participantDto.getManagerId());
		Participant savedParticipant = participantRepository.save(partcipant);
		Long participantId = savedParticipant.getCmParticipantId();
		List<PartipantLanguage> partipantLanguages = addlanguages(participantId, participantDto.getLanguagesId());
		return partcipant;
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
		logger.info("cmstaff/participant endpoint service() --> No data Available");
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
		if (participantDto.getRacf() == null || participantDto.getRacf().length() == 0) {
			logger.info("cmstaff/participant endpoint service uniqueConstraintsCheck() --> racf id is  null");
			throw new ServiceErrorException(new ApiErrorResponse("409", "racf id is  null"), HttpStatus.CONFLICT);
		}
		if (participantDto.getLob() != null && (participantDto.getLob().equals("Commercial"))
				|| participantDto.getLob().equals("Medicare")) {
			return !isMatchLob(participantDto.getRacf(), participantDto.getLob());
		} else {
			logger.info("cmstaff/participant endpoint service uniqueConstraintsCheck() --> lob should be Commercial or Medicare");
			throw new ServiceErrorException(new ApiErrorResponse("409", "lob should be Commercial or Medicare"),
					HttpStatus.CONFLICT);
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
			logger.info("cmstaff/participant endpoint service languageNameAndIdvalidation() --> Both languageId's and language name are empty");
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "Both languageId's and language name are empty"), HttpStatus.CONFLICT);
		}
		return true;
	}

	private boolean inputLanguageNameAndIdValidation(ParticipantDto participantDto) {
		if (participantDto.getLanguagesId() != null && !participantDto.getLanguagesId().isEmpty()) {
			List<Integer> dblanguageList = languageRepository.getlanguagesId(participantDto.getLanguagesId());
			if (dblanguageList.isEmpty()) {
				logger.info("cmstaff/participant endpoint service inputLanguageNameAndIdValidation() --> the followingId's are invalid" + participantDto.getLanguagesId());
				throw new ServiceErrorException(
						new ApiErrorResponse("409", "the followingId's are invalid" + participantDto.getLanguagesId()),
						HttpStatus.CONFLICT);
			}
			List<Integer> unAvailableIds = participantDto.getLanguagesId().stream()
					.filter(id -> !dblanguageList.contains(id)).collect(Collectors.toList());
			if (!unAvailableIds.isEmpty()) {
				logger.info("cmstaff/participant endpoint service inputLanguageNameAndIdValidation() --> the followingId's are invalid" + unAvailableIds);
				throw new ServiceErrorException(
						new ApiErrorResponse("409", "the followingId's are invalid" + unAvailableIds),
						HttpStatus.CONFLICT);
			}
		}
		if (participantDto.getLanguage() != null && !"".equals(participantDto.getLanguage())) {
			if (!stringValidation(participantDto.getLanguage())) {
				logger.info("cmstaff/participant endpoint service inputLanguageNameAndIdValidation() -->languagename should not contain any numbers and Special Character :" + participantDto.getLanguage());
				throw new ServiceErrorException(
						new ApiErrorResponse("409", "languagename doesnot contain any numbers and Special Character :"
								+ participantDto.getLanguage()),
						HttpStatus.CONFLICT);
			}
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
		}
		if (!stringValidationInput(participantDto.getFirstName())) {
			logger.info("cmstaff/participant endpoint service inputLanguageNameAndIdValidation() -->FirstName should not contain any numbers and Special Character :" + participantDto.getFirstName());
			throw new ServiceErrorException(new ApiErrorResponse("409",
					"Firstname should not contain any numbers and Special Character :" + participantDto.getFirstName()),
					HttpStatus.CONFLICT);
		}
		if (!stringValidationInput(participantDto.getLastName())) {
			logger.info("cmstaff/participant endpoint service inputLanguageNameAndIdValidation() -->LastName should not contain any numbers and Special Character :" + participantDto.getLastName());
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
		logger.info("Entering --> cmstaff/participant endpoint service updateParticipant()");
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
				logger.info("cmstaff/participant endpoint service updateParticipant() --> racf id with lob is already exist");
				throw new ServiceErrorException(new ApiErrorResponse("409", "racf id with lob is already exist"),
						HttpStatus.CONFLICT);
			}

		}
		logger.info("cmstaff/participant endpoint service updateParticipant() --> No Content cmParticipantId" + cmParticipantId);
		throw new ServiceErrorException(
				new ApiErrorResponse("204", "No Content"),
				HttpStatus.NO_CONTENT);
	}

	@Transactional
	private Participant updateParticipantEntity(Long cmParticipantId, ParticipantDto participantDto,
			Participant partcipant) {
		if (!participantDto.getActiveStatus().equals("Inactive") && !participantDto.getActiveStatus().equals("Active")) {
			logger.info("cmstaff/participant endpoint service updateParticipantEntity() --> activeStatus should be Active or Inactive");
			throw new ServiceErrorException(new ApiErrorResponse("409", "activeStatus should be Active or Inactive"),
					HttpStatus.CONFLICT);
		}
		if (!setTeamId(participantDto.getTeamId())) {
			logger.info("cmstaff/participant endpoint service updateParticipantEntity() --> with the team_Id, "+participantDto.getTeamId()+" team entity is not available");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the team_Id, team entity is not available"),
					HttpStatus.NOT_FOUND);
		}
		if (!setRoleId(participantDto.getRoleId())) {
			logger.info("cmstaff/participant endpoint service updateParticipantEntity() --> with the role_Id, "+participantDto.getRoleId()+" role entity is not available");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the role_Id, role entity is not available"),
					HttpStatus.NOT_FOUND);
		}
		if (!setManagerId(participantDto.getManagerId())) {
			logger.info("cmstaff/participant endpoint service updateParticipantEntity() --> with the Manager_Id, "+participantDto.getManagerId()+" Manager entity is not available");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "with the Manager_Id, Manager entity is not available"),
					HttpStatus.NOT_FOUND);
		}
		if (participantDto.getLanguagesId()!= null && participantDto.getLanguagesId().contains(0)) {
			logger.info("cmstaff/participant endpoint service updateParticipantEntity() --> Language id should not be 0, Please enter valid languageId");
			throw new ServiceErrorException(
					new ApiErrorResponse("404",
							"Language id should not be 0, Please enter valid languageId"),
					HttpStatus.NOT_FOUND);
		}
		partcipant.setCmParticipantId(cmParticipantId);
		partcipant.setRacf(participantDto.getRacf());
		partcipant.setFirstName(participantDto.getFirstName());
		partcipant.setLastName(participantDto.getLastName());
		partcipant.setUpdatedDateTime(LocalDateTime.now());
		partcipant.setLob(lob_enum.valueOf(participantDto.getLob()));
		partcipant.setActiveStatus(partcipant.getActiveStatus());
		partcipant.setTeamId(participantDto.getTeamId());
		partcipant.setRoleId(participantDto.getRoleId());
		partcipant.setManagerId(participantDto.getManagerId());
		List<PartipantLanguage> languages = updateLanguages(cmParticipantId, participantDto.getLanguagesId());
		participantRepository.save(partcipant);
		return participantRepository.getById(cmParticipantId);
	}

	@Override
	public void deleteParticipant(Long cmParticipantId) {
		Optional<Participant> optionalParticipant = participantRepository.findById(cmParticipantId);
		if(!optionalParticipant.isPresent()) {
			throw new ServiceErrorException(
					new ApiErrorResponse("404",
							"Resource Not Found with cmParticipantId "+ cmParticipantId),
					HttpStatus.NOT_FOUND);
		}
		Participant participant = optionalParticipant.get();
		if(participant.getActiveStatus().equals(status_enum.valueOf("Active"))) {
			participant.setActiveStatus(status_enum.Inactive);
			participant.setUpdatedDateTime(LocalDateTime.now());
			participantRepository.save(participant);
		} else {
			throw new ServiceErrorException(
					new ApiErrorResponse("409",
							"No changes on Inactive cmParticipant with id "+ cmParticipantId),
					HttpStatus.CONFLICT);
		}
	}

	public List<PartipantLanguage> addlanguages(Long cmParticipantId, List<Integer> languagesId) {
		List<PartipantLanguage> participantLanguages = new ArrayList<>();
		Optional<Participant> optional = participantRepository.findById(cmParticipantId);
		if (optional.isPresent() && languagesId != null) {
			List<Integer> languagesdis = languagesId.stream().distinct().collect(Collectors.toList());
			for (Integer languageId : languagesdis) {
				if (!findLanguageId(languageId)) {
					logger.info("cmstaff/participant endpoint service addLanguages() --> Language _id "+ languageId + " is not found");
					throw new ServiceErrorException(
							new ApiErrorResponse("404", "Language _id " + languageId + " is not found"),
							HttpStatus.NOT_FOUND);
				}
				PartipantLanguage participantLanguage = new PartipantLanguage();
				participantLanguage.setCmParticipantId(cmParticipantId);
				participantLanguage.setLanguageId(languageId);
				participantLanguage.setUuid(participantLanguage.getUuid());
				participantLanguages.add(participantLanguage);
			}
			participantLanguageRepository.saveAll(participantLanguages);
			return participantLanguages;
		}
		logger.info("cmstaff/participant endpoint service addlanguages() --> participant_language update fail");
		throw new ServiceErrorException(
				new ApiErrorResponse("404", "participant_language update fail"),
				HttpStatus.NOT_FOUND);
	}

	public List<PartipantLanguage> updateLanguages(Long cmParticipantId, List<Integer> languagesId) {
		List<PartipantLanguage> participantLanguages = new ArrayList<>();
		Optional<Participant> optional = participantRepository.findById(cmParticipantId);
		if (optional.isPresent()) {
			if (languagesId != null && !languagesId.isEmpty()) {
				List<Integer> languagesdis = languagesId.stream().distinct().collect(Collectors.toList());
				for (Integer languageId : languagesdis) {
					if (!findLanguageId(languageId)) {
						logger.info("cmstaff/participant endpoint service updateLanguages() --> Language _id "+ languageId + " is not found");
						throw new ServiceErrorException(
								new ApiErrorResponse("404", "Language _id " + languageId + " is not found"),
								HttpStatus.NOT_FOUND);
					}
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
				}
				participantLanguageRepository.saveAll(participantLanguages);
				List<Integer> dbLanguagesId = participantLanguageRepository.getLanguageIds(cmParticipantId);
				deleteOldlanguagesById(dbLanguagesId, languagesId, cmParticipantId);
			}
			return participantLanguages;
		}
		logger.info("cmstaff/participant endpoint service updateLanguages() --> partcipant entity is not available and update failed on participant_language entity");
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
