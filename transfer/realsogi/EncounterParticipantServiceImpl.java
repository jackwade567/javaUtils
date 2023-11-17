package com.bcbsfl.ccn.realsogi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.bcbsfl.ccn.realsogi.dto.EncounterParticipantRequestDto;
import com.bcbsfl.ccn.realsogi.dto.EncounterParticipantResponseDto;
import com.bcbsfl.ccn.realsogi.dto.EthnicityDto;
import com.bcbsfl.ccn.realsogi.dto.MemberSpokenLanguageDto;
import com.bcbsfl.ccn.realsogi.dto.MemberWrittenLanguageDto;
import com.bcbsfl.ccn.realsogi.dto.RaceDto;
import com.bcbsfl.ccn.realsogi.entites.EncounterParticipant;
import com.bcbsfl.ccn.realsogi.entites.EncounterParticipantEthnicity;
import com.bcbsfl.ccn.realsogi.entites.EncounterParticipantLanguage;
import com.bcbsfl.ccn.realsogi.entites.EncounterParticipantRace;
import com.bcbsfl.ccn.realsogi.entites.EncounterParticipantWrittenLanguage;
import com.bcbsfl.ccn.realsogi.entites.Ethnicity;
import com.bcbsfl.ccn.realsogi.entites.Language;
import com.bcbsfl.ccn.realsogi.entites.Manager;
import com.bcbsfl.ccn.realsogi.entites.Participant;
import com.bcbsfl.ccn.realsogi.entites.Race;
import com.bcbsfl.ccn.realsogi.entites.Role;
import com.bcbsfl.ccn.realsogi.entites.Team;
import com.bcbsfl.ccn.realsogi.exception.ApiErrorResponse;
import com.bcbsfl.ccn.realsogi.exception.ServiceErrorException;
import com.bcbsfl.ccn.realsogi.repositories.EncounterParticipantEthnicityRepo;
import com.bcbsfl.ccn.realsogi.repositories.EncounterParticipantLanguageRepository;
import com.bcbsfl.ccn.realsogi.repositories.EncounterParticipantRaceRepo;
import com.bcbsfl.ccn.realsogi.repositories.EncounterParticipantRepository;
import com.bcbsfl.ccn.realsogi.repositories.EncounterParticipantWrittenLanguageRepo;
import com.bcbsfl.ccn.realsogi.repositories.EthnicityRepository;
import com.bcbsfl.ccn.realsogi.repositories.LanguageRepository;
import com.bcbsfl.ccn.realsogi.repositories.ManagerRepository;
import com.bcbsfl.ccn.realsogi.repositories.ParticipantRepository;
import com.bcbsfl.ccn.realsogi.repositories.RaceRepository;
import com.bcbsfl.ccn.realsogi.repositories.RoleRepository;
import com.bcbsfl.ccn.realsogi.repositories.TeamRepository;

@Service
public class EncounterParticipantServiceImpl implements EncounterParticipantService {
	private static final Logger logger = LogManager.getLogger(EncounterParticipantServiceImpl.class);

	@Autowired
	private EncounterParticipantRepository encounterParticipantRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private EncounterParticipantLanguageRepository encounterParticipantLanguageRepo;

	@Autowired
	private EncounterParticipantWrittenLanguageRepo encounterParticipantWrittenLanguageRepo;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private RaceRepository raceRepository;

	@Autowired
	private EncounterParticipantRaceRepo encounterParticipantRaceRepo;

	@Autowired
	private EncounterParticipantEthnicityRepo encounterParticipantEthnicityRepo;

	@Autowired
	private EthnicityRepository ethnicityRepository;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Override
	@Transactional
	public EncounterParticipant addEncounterParticipant(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		logger.info("Entering --> realsogi/encounterparticipant endpoint service addEncounterParticipant()");
		if (!uniqueConstraintsCheck(encounterParticipantRequestDto)) {
			logger.info("realsogi/encounterparticipant endpoint service addEncounterParticipant() --> cipId already exists");
			throw new ServiceErrorException(new ApiErrorResponse("409", "cipId already exists"), HttpStatus.CONFLICT);
		}
		if (!constraintsCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "ConstraintCheck failed"), HttpStatus.CONFLICT);
		}
		if (!encounterParticipantRequestDto.getActiveStatus().equals("Inactive")
				&& !encounterParticipantRequestDto.getActiveStatus().equals("Active")) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "activeStatus should be Active or Inactive"),
					HttpStatus.CONFLICT);
		}
		if (!raceAndSpokenAndWrittenlangEmptyCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("404", "Empty Field violation"), HttpStatus.NOT_FOUND);
		}
		if (encounterParticipantRequestDto.getEthnicityId() == null
				|| encounterParticipantRequestDto.getEthnicityId() <= 0) {
			logger.info(
					"realsogi/encounterparticipant endpoint service addEncounterParticipant() --> EthnicityId should not be null or zero or negative!");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "EthnicityId should not be null or zero or negative!"),
					HttpStatus.NOT_FOUND);
		}
		if (!cmParticipantId(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "cmParticipantId Check failed"),
					HttpStatus.CONFLICT);
		}
		if (!ethnicIdCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "EthnicId Check failed"), HttpStatus.CONFLICT);
		}
		if (!writtenLangCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "SpokenAndWrittenLang Check failed"),
					HttpStatus.CONFLICT);
		}
		if (!spokenLangCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "SpokenAndWrittenLang Check failed"),
					HttpStatus.CONFLICT);
		}
		if (!raceCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "Race Check failed"), HttpStatus.CONFLICT);
		}
		EncounterParticipant encounterParticipant = new EncounterParticipant();
		encounterParticipant.setCipId(encounterParticipantRequestDto.getMemberCipId());
		encounterParticipant.setFirstName(encounterParticipantRequestDto.getMemberFirstName());
		encounterParticipant.setLastName(encounterParticipantRequestDto.getMemberLastName());
		encounterParticipant.setEncounterParticipantPartyId(encounterParticipantRequestDto.getMemberPartyId());
		encounterParticipant.setActiveStatus(encounterParticipant.getActiveStatus());
		EncounterParticipant savedEncounterParticipant = encounterParticipantRepository.save(encounterParticipant);
		Long encounterParticipantId = savedEncounterParticipant.getEncounterParticipantId();
		addlanguages(encounterParticipantId, encounterParticipantRequestDto.getUpdateByPartcipantId(),
				encounterParticipantRequestDto.getSpokenLanguages());
		addWrittenLanguages(encounterParticipantId, encounterParticipantRequestDto.getUpdateByPartcipantId(),
				encounterParticipantRequestDto.getWrittenLanguages());
		addEthnicity(encounterParticipantId, encounterParticipantRequestDto.getUpdateByPartcipantId(),
				encounterParticipantRequestDto.getEthnicityId());
		addRace(encounterParticipantId, encounterParticipantRequestDto.getUpdateByPartcipantId(),
				encounterParticipantRequestDto.getRaceId());
		logger.info("Exiting --> realsogi/encounterparticipant endpoint service addEncounterParticipant()");
		return savedEncounterParticipant;
	}

	private List<EncounterParticipantRace> addRace(Long encounterParticipantId, long updateByPartcipantId,
			List<Integer> raceIds) {
		List<EncounterParticipantRace> encounterParticipantRaces = new ArrayList<>();
		if (encounterParticipantId != null && encounterParticipantId != 0) {
			if (raceIds != null && !raceIds.isEmpty()) {
				List<Integer> racesdis = raceIds.stream().distinct().collect(Collectors.toList());
				for (Integer raceId : racesdis) {
					EncounterParticipantRace encounterParticipantRace = new EncounterParticipantRace();
					encounterParticipantRace.setEncounterParticipantId(encounterParticipantId);
					encounterParticipantRace.setRaceId(raceId);
					encounterParticipantRace.setUpdatedByCmParticipantId(updateByPartcipantId);
					encounterParticipantRace.setUuid(encounterParticipantRace.getUuid());
					encounterParticipantRaceRepo.save(encounterParticipantRace);
					encounterParticipantRaces.add(encounterParticipantRace);
				}
			} else {
				logger.info("realsogi/encounterparticipant endpoint service addRace() --> raceIds list is empty");
				throw new ServiceErrorException(new ApiErrorResponse("404", "raceIds list is empty"),
						HttpStatus.NOT_FOUND);
			}
		} else {
			logger.info(
					"realsogi/encounterparticipant endpoint service addRace() --> EncounterParticipant entity doesn't exist");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "EncounterParticipant entity doesn't exist " + encounterParticipantId),
					HttpStatus.NOT_FOUND);
		}
		return encounterParticipantRaces;
	}

	private void addEthnicity(Long encounterParticipantId, long updateByPartcipantId, int ethnicityId) {
		logger.info("Entering --> realsogi/encounterparticipant endpoint service addEthnicity()");
		EncounterParticipantEthnicity encounterParticipantEthnicity = new EncounterParticipantEthnicity();
		encounterParticipantEthnicity.setEncounterParticipantId(encounterParticipantId);
		encounterParticipantEthnicity.setEthnicityId(ethnicityId);
		encounterParticipantEthnicity.setUpdatedByCmParticipantId(updateByPartcipantId);
		encounterParticipantEthnicity.setUuid(encounterParticipantEthnicity.getUuid());
		encounterParticipantEthnicityRepo.save(encounterParticipantEthnicity);
		logger.info("Exiting --> realsogi/encounterparticipant endpoint service addEthnicity()");
	}

	private boolean stringValidationInput(String input) {
		Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	private boolean cmParticipantId(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		Optional<Participant> participantOptional = participantRepository
				.findById(encounterParticipantRequestDto.getUpdateByPartcipantId());
		if (!participantOptional.isPresent()) {
			logger.info(
					"realsogi/encounterparticipant endpoint service cmParticipantId() --> No record found for given Participant_id!");
			throw new ServiceErrorException(new ApiErrorResponse("404", "No record found for given Participant_id!"),
					HttpStatus.NOT_FOUND);
		}
		return true;
	}

	private boolean ethnicIdCheck(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		if (encounterParticipantRequestDto.getEthnicityId() != null
				&& encounterParticipantRequestDto.getEthnicityId() > 0) {
			Optional<Ethnicity> ethnicityOptional = ethnicityRepository
					.findById(encounterParticipantRequestDto.getEthnicityId());
			if (!ethnicityOptional.isPresent()) {
				logger.info(
						"realsogi/encounterparticipant endpoint service ethnicIdCheck() --> No record found for given ethninicityId!");
				throw new ServiceErrorException(new ApiErrorResponse("404", "No record found for given ethninicityId!"),
						HttpStatus.NOT_FOUND);
			}
		}
		return true;
	}

	private boolean raceAndSpokenAndWrittenlangEmptyCheck(
			EncounterParticipantRequestDto encounterParticipantRequestDto) {
		if ((encounterParticipantRequestDto.getRace() == null || encounterParticipantRequestDto.getRace().isBlank())
				&& (encounterParticipantRequestDto.getRaceId() == null
						|| encounterParticipantRequestDto.getRaceId().isEmpty())) {
			logger.info(
					"realsogi/encounterparticipant endpoint service raceAndSpokenAndWrittenlangEmptyCheck() --> Both otheRace and raceId are empty");
			throw new ServiceErrorException(new ApiErrorResponse("409", "Both otheRace and raceId are empty"),
					HttpStatus.CONFLICT);
		}
		if ((encounterParticipantRequestDto.getSpokenLanguages() == null
				|| encounterParticipantRequestDto.getSpokenLanguages().isEmpty())
				&& (encounterParticipantRequestDto.getOtherSpokenLang() == null
						|| encounterParticipantRequestDto.getOtherSpokenLang().isBlank())) {
			logger.info(
					"realsogi/encounterparticipant endpoint service raceAndSpokenAndWrittenlangEmptyCheck() --> Both otherSpokenlanguage and Spokenlanguage are empty");
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "Both otherSpokenlanguage and Spokenlanguage are empty"),
					HttpStatus.CONFLICT);
		}
		if ((encounterParticipantRequestDto.getWrittenLanguages() == null
				|| encounterParticipantRequestDto.getWrittenLanguages().isEmpty())
				&& (encounterParticipantRequestDto.getOtherWrittenLang() == null
						|| encounterParticipantRequestDto.getOtherWrittenLang().isBlank())) {
			logger.info(
					"realsogi/encounterparticipant endpoint service raceAndSpokenAndWrittenlangEmptyCheck() --> Both otherWrittenlanguage and Writtenlanguage are empty");
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "Both otherWrittenlanguage and Writtenlanguage are empty"),
					HttpStatus.CONFLICT);
		}
		return true;
	}

	private boolean raceCheck(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		if (encounterParticipantRequestDto.getRaceId() != null
				&& !encounterParticipantRequestDto.getRaceId().isEmpty()) {
			List<Integer> dbRaceList = raceRepository.getRaceId(encounterParticipantRequestDto.getRaceId());
			if (!dbRaceList.isEmpty()) {
				List<Integer> unAvailableIds = encounterParticipantRequestDto.getRaceId().stream()
						.filter(id -> !dbRaceList.contains(id)).collect(Collectors.toList());
				if (!unAvailableIds.isEmpty()) {
					logger.info("realsogi/encounterparticipant endpoint service raceCheck() --> The following race Id's are invalid "+ unAvailableIds);
					throw new ServiceErrorException(
							new ApiErrorResponse("409", "The following race Id's are invalid" + unAvailableIds),
							HttpStatus.CONFLICT);
				}
			} else {
				logger.info("Entering --> realsogi/encounterparticipant endpoint service raceCheck() --> The following race Id's are invalid "+ encounterParticipantRequestDto.getRaceId());
				throw new ServiceErrorException(
						new ApiErrorResponse("409",
								"The following race Id's are invalid" + encounterParticipantRequestDto.getRaceId()),
						HttpStatus.CONFLICT);
			}
		}
		if (inputLanguagCheck(encounterParticipantRequestDto.getRace())) {
			Integer raceId = raceRepository.getRaceNameByID(encounterParticipantRequestDto.getRace());
			if (raceId != null) {
				if ((encounterParticipantRequestDto.getRaceId() != null
						&& !encounterParticipantRequestDto.getRaceId().isEmpty())) {
					encounterParticipantRequestDto.getRaceId().add(raceId);
				} else {
					List<Integer> addRace = new ArrayList<>();
					addRace.add(raceId);
					encounterParticipantRequestDto.setRaceId(addRace);
				}
			} else {
				Race race = new Race();
				race.setRaceName(encounterParticipantRequestDto.getRace());
				Race savedRace = raceRepository.save(race);
				if ((encounterParticipantRequestDto.getRaceId() != null
						&& !encounterParticipantRequestDto.getRaceId().isEmpty())) {
					encounterParticipantRequestDto.getRaceId().add(savedRace.getRaceId());
				} else {
					List<Integer> addRace = new ArrayList<>();
					addRace.add(savedRace.getRaceId());
					encounterParticipantRequestDto.setRaceId(addRace);
				}
			}
		}
		return true;
	}

	private boolean spokenLangCheck(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		if (encounterParticipantRequestDto.getSpokenLanguages() != null
				&& !encounterParticipantRequestDto.getSpokenLanguages().isEmpty()) {
			List<Integer> dblanguageList = languageRepository
					.getlanguagesId(encounterParticipantRequestDto.getSpokenLanguages());
			if (!dblanguageList.isEmpty()) {
				List<Integer> unAvailableIds = encounterParticipantRequestDto.getSpokenLanguages().stream()
						.filter(id -> !dblanguageList.contains(id)).collect(Collectors.toList());
				if (!unAvailableIds.isEmpty()) {
					logger.info("realsogi/encounterparticipant endpoint service spokenLangCheck() --> The following spokenLanguage Id's are invalid " + unAvailableIds);
					throw new ServiceErrorException(
							new ApiErrorResponse("409",
									"The following spokenLanguage Id's are invalid" + unAvailableIds),
							HttpStatus.CONFLICT);
				}
			} else {
				logger.info("realsogi/encounterparticipant endpoint service spokenLangCheck() --> The following spokenLanguage Id's are invalid " + encounterParticipantRequestDto.getSpokenLanguages());
				throw new ServiceErrorException(
						new ApiErrorResponse("409", "The following spokenLanguage Id's are invalid"
								+ encounterParticipantRequestDto.getSpokenLanguages()),
						HttpStatus.CONFLICT);
			}
		}
		if (inputLanguagCheck(encounterParticipantRequestDto.getOtherSpokenLang())) {
			Integer languageId = languageRepository
					.getLanguageNameByID(encounterParticipantRequestDto.getOtherSpokenLang());
			if (languageId != null) {
				if ((encounterParticipantRequestDto.getSpokenLanguages() != null
						&& !encounterParticipantRequestDto.getSpokenLanguages().isEmpty())) {
					encounterParticipantRequestDto.getSpokenLanguages().add(languageId);
				} else {
					List<Integer> addLanguage = new ArrayList<>();
					addLanguage.add(languageId);
					encounterParticipantRequestDto.setSpokenLanguages(addLanguage);
				}
			} else {
				Language language = new Language();
				language.setLanguageName(encounterParticipantRequestDto.getOtherSpokenLang());
				language.setLanguageStatus(language.getLanguageStatus());
				Language savedlanguage = languageRepository.save(language);
				if ((encounterParticipantRequestDto.getSpokenLanguages() != null
						&& !encounterParticipantRequestDto.getSpokenLanguages().isEmpty())) {
					encounterParticipantRequestDto.getSpokenLanguages().add(savedlanguage.getLanguageId());
				} else {
					List<Integer> addLanguage = new ArrayList<>();
					addLanguage.add(savedlanguage.getLanguageId());
					encounterParticipantRequestDto.setSpokenLanguages(addLanguage);
				}
			}
		}
		return true;
	}

	private boolean writtenLangCheck(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		if (encounterParticipantRequestDto.getWrittenLanguages() != null
				&& !encounterParticipantRequestDto.getWrittenLanguages().isEmpty()) {
			List<Integer> dblanguageList = languageRepository
					.getlanguagesId(encounterParticipantRequestDto.getWrittenLanguages());
			if (!dblanguageList.isEmpty()) {
				List<Integer> unAvailableIds = encounterParticipantRequestDto.getWrittenLanguages().stream()
						.filter(id -> !dblanguageList.contains(id)).collect(Collectors.toList());
				if (!unAvailableIds.isEmpty()) {
					logger.info("realsogi/encounterparticipant endpoint service writtenLangCheck() --> The following writtenLanguage Id's are invalid "+unAvailableIds);
					throw new ServiceErrorException(
							new ApiErrorResponse("409",
									"The following writtenLanguage Id's are invalid" + unAvailableIds),
							HttpStatus.CONFLICT);
				}
			} else {
				logger.info("realsogi/encounterparticipant endpoint service writtenLangCheck() --> The following writtenLanguage Id's are invalid "+encounterParticipantRequestDto.getWrittenLanguages());
				throw new ServiceErrorException(
						new ApiErrorResponse("409", "The following writtenLanguage Id's are invalid"
								+ encounterParticipantRequestDto.getWrittenLanguages()),
						HttpStatus.CONFLICT);
			}
		}
		if (inputLanguagCheck(encounterParticipantRequestDto.getOtherWrittenLang())) {
			Integer languageId = languageRepository
					.getLanguageNameByID(encounterParticipantRequestDto.getOtherWrittenLang());
			if (languageId != null) {
				if ((encounterParticipantRequestDto.getWrittenLanguages() != null
						&& !encounterParticipantRequestDto.getWrittenLanguages().isEmpty())) {
					encounterParticipantRequestDto.getWrittenLanguages().add(languageId);
				} else {
					List<Integer> addLanguage = new ArrayList<>();
					addLanguage.add(languageId);
					encounterParticipantRequestDto.setWrittenLanguages(addLanguage);
				}
			} else {
				Language language = new Language();
				language.setLanguageName(encounterParticipantRequestDto.getOtherWrittenLang());
				language.setLanguageStatus(language.getLanguageStatus());
				Language savedlanguage = languageRepository.save(language);
				if ((encounterParticipantRequestDto.getWrittenLanguages() != null
						&& !encounterParticipantRequestDto.getWrittenLanguages().isEmpty())) {
					encounterParticipantRequestDto.getWrittenLanguages().add(savedlanguage.getLanguageId());
				} else {
					List<Integer> addLanguage = new ArrayList<>();
					addLanguage.add(savedlanguage.getLanguageId());
					encounterParticipantRequestDto.setWrittenLanguages(addLanguage);
				}
			}
		}
		return true;
	}

	public static boolean stringValidation(String language) {
		Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
		Matcher matcher = pattern.matcher(language);
		return matcher.matches();
	}

	private boolean inputLanguagCheck(String language) {
		if (language != null && !"".equals(language)) {
			if (stringValidation(language)) {
				return true;
			} else {
				logger.info("realsogi/encounterparticipant endpoint service inputLanguagCheck() --> languagename should not contain any numbers and Special Character : "+language);
				throw new ServiceErrorException(
						new ApiErrorResponse("409",
								"languagename should not contain any numbers and Special Character :" + language),
						HttpStatus.CONFLICT);
			}
		}
		return false;
	}

	private boolean uniqueConstraintsCheck(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		if (encounterParticipantRequestDto.getMemberCipId() != null
				&& encounterParticipantRequestDto.getMemberCipId().length() > 0) {
			Optional<EncounterParticipant> encounterParticipantOptional = encounterParticipantRepository
					.findByCipId(encounterParticipantRequestDto.getMemberCipId());
			return !encounterParticipantOptional.isPresent();
		}
		logger.info("realsogi/encounterparticipant endpoint service uniqueConstraintsCheck() --> cipId must not be empty!");
		throw new ServiceErrorException(new ApiErrorResponse("404", "cipId must not be empty!"), HttpStatus.NOT_FOUND);
	}

	private boolean constraintsCheck(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		if (encounterParticipantRequestDto.getSpokenLanguages() != null
				&& encounterParticipantRequestDto.getSpokenLanguages().contains(0)) {
			logger.info("realsogi/encounterparticipant endpoint service constraintsCheck() --> spokenLanguage id should not be 0, Please enter valid languageId");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "spokenLanguage id should not be 0, Please enter valid languageId "),
					HttpStatus.NOT_FOUND);
		}
		if (encounterParticipantRequestDto.getWrittenLanguages() != null
				&& encounterParticipantRequestDto.getWrittenLanguages().contains(0)) {
			logger.info("realsogi/encounterparticipant endpoint service constraintsCheck() --> writtenLanguage id should not be 0, Please enter valid languageId");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "writtenLanguage id should not be 0, Please enter valid languageId "),
					HttpStatus.NOT_FOUND);
		}
		if (encounterParticipantRequestDto.getRaceId() != null
				&& encounterParticipantRequestDto.getRaceId().contains(0)) {
			logger.info("realsogi/encounterparticipant endpoint service constraintsCheck() --. raceId should not be 0, Please enter valid languageId");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "raceId should not be 0, Please enter valid languageId "),
					HttpStatus.NOT_FOUND);
		}
		if (!stringValidationInput(encounterParticipantRequestDto.getMemberFirstName())) {
			logger.info("realsogi/encounterparticipant endpoint service constraintsCheck() -->MemberFirstname should not contain any numbers and Special Character : "+ encounterParticipantRequestDto.getMemberFirstName());
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "MemberFirstname should not contain any numbers and Special Character :"
							+ encounterParticipantRequestDto.getMemberFirstName()),
					HttpStatus.CONFLICT);
		}
		if (!stringValidationInput(encounterParticipantRequestDto.getMemberLastName())) {
			logger.info("realsogi/encounterparticipant endpoint service constraintsCheck() -->MemberlastName should not contain any numbers and Special Character : "+ encounterParticipantRequestDto.getMemberLastName());
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "MemberlastName should not contain any numbers and Special Character :"
							+ encounterParticipantRequestDto.getMemberLastName()),
					HttpStatus.CONFLICT);
		}
		return true;
	}

	public List<EncounterParticipantLanguage> addlanguages(Long encounterParticipantId, Long updateByParticipantId,
			List<Integer> spokenlanguagesId) {
		List<EncounterParticipantLanguage> encounterParticipantLanguages = new ArrayList<>();
		if (encounterParticipantId != null && encounterParticipantId != 0) {
			if (spokenlanguagesId != null && !spokenlanguagesId.isEmpty()) {
				List<Integer> languagesdis = spokenlanguagesId.stream().distinct().collect(Collectors.toList());
				for (Integer languadeId : languagesdis) {
					EncounterParticipantLanguage encounterParticipantLanguage = new EncounterParticipantLanguage();
					encounterParticipantLanguage.setEncounterParticipantId(encounterParticipantId);
					encounterParticipantLanguage.setLanguageId(languadeId);
					encounterParticipantLanguage.setCmParticipantId(updateByParticipantId);
					encounterParticipantLanguage.setUuid(encounterParticipantLanguage.getUuid());
					// encounterParticipantLanguage.setDateTime(LocalDateTime.now());
					encounterParticipantLanguageRepo.save(encounterParticipantLanguage);
					encounterParticipantLanguages.add(encounterParticipantLanguage);
				}
			} else {
				logger.info("realsogi/encounterparticipant endpoint service addlanguages() --> languagesId list is empty");
				throw new ServiceErrorException(
						new ApiErrorResponse("404", "languagesId list is empty but member is saved"),
						HttpStatus.NOT_FOUND);
			}
		} else {
			logger.info("realsogi/encounterparticipant endpoint service addlanguages() --> EncounterParticipant entity doesn't exist");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "EncounterParticipant entity doesn't exist " + encounterParticipantId),
					HttpStatus.NOT_FOUND);
		}
		return encounterParticipantLanguages;
	}

	public List<EncounterParticipantWrittenLanguage> addWrittenLanguages(Long encounterParticipantId,
			Long updateByParticipantId, List<Integer> writtenlanguagesId) {
		List<EncounterParticipantWrittenLanguage> encounterParticipantLanguages = new ArrayList<>();
		if (encounterParticipantId != null && encounterParticipantId != 0) {
			if (writtenlanguagesId != null && !writtenlanguagesId.isEmpty()) {
				List<Integer> languagesdis = writtenlanguagesId.stream().distinct().collect(Collectors.toList());
				for (Integer languadeId : languagesdis) {
					EncounterParticipantWrittenLanguage encounterParticipantLanguage = new EncounterParticipantWrittenLanguage();
					encounterParticipantLanguage.setEncounterParticipantId(encounterParticipantId);
					encounterParticipantLanguage.setLanguageId(languadeId);
					encounterParticipantLanguage.setCmParticipantId(updateByParticipantId);
					encounterParticipantLanguage.setUuid(encounterParticipantLanguage.getUuid());
					encounterParticipantWrittenLanguageRepo.save(encounterParticipantLanguage);
					encounterParticipantLanguages.add(encounterParticipantLanguage);
				}
			} else {
				logger.info("realsogi/encounterparticipant endpoint service addWrittenLanguages() --. languagesId list is empty");
				throw new ServiceErrorException(
						new ApiErrorResponse("404", "languagesId list is empty but member is saved"),
						HttpStatus.NOT_FOUND);
			}
		} else {
			logger.info("realsogi/encounterparticipant endpoint service addWrittenLanguages() --> EncounterParticipant entity doesn't exist");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "EncounterParticipant entity doesn't exist " + encounterParticipantId),
					HttpStatus.NOT_FOUND);
		}
		return encounterParticipantLanguages;
	}

	@Override
	public EncounterParticipantResponseDto getEncounterParticipantResponse(String cipId) {
		logger.info("Entering --> realsogi/encounterparticipant endpoint service getEncounterParticipantResponse()");
		if (cipId != null && cipId.length() > 0) {
			EncounterParticipantResponseDto encounterParticipantResponseDto = new EncounterParticipantResponseDto();
			Optional<EncounterParticipant> optionalEncPart = encounterParticipantRepository.findByCipId(cipId);
			if (optionalEncPart.isPresent()) {
				EncounterParticipant encounterParticipant = optionalEncPart.get();
				encounterParticipantResponseDto.setMemberCipId(
						encounterParticipant.getCipId() != null ? encounterParticipant.getCipId() : null);
				encounterParticipantResponseDto.setMemberFirstName(
						encounterParticipant.getFirstName() != null ? encounterParticipant.getFirstName() : null);
				encounterParticipantResponseDto.setMemberLastName(
						encounterParticipant.getLastName() != null ? encounterParticipant.getLastName() : null);
				encounterParticipantResponseDto
						.setMemberPartyId(encounterParticipant.getEncounterParticipantPartyId() != null
								? encounterParticipant.getEncounterParticipantPartyId()
								: null);
				getRaceResponse(encounterParticipantResponseDto, encounterParticipant.getEncounterParticipantId());
				getEhnicityResponse(encounterParticipantResponseDto, encounterParticipant.getEncounterParticipantId());
				getSpokenlanguageResponse(encounterParticipantResponseDto,
						encounterParticipant.getEncounterParticipantId());
				getWrittenlanguageResponse(encounterParticipantResponseDto,
						encounterParticipant.getEncounterParticipantId());
				return encounterParticipantResponseDto;
			}
			logger.info("realsogi/encounterparticipant endpoint service getEncounterParticipantResponse() -->  with cipId, " + cipId +  " Resource Not Found");
			throw new ServiceErrorException(
					new ApiErrorResponse("404", " with cipId, " + cipId + " Resource Not Found"), HttpStatus.NOT_FOUND);
		}
		logger.info("realsogi/encounterparticipant endpoint service getEncounterParticipantResponse() --> cipId shouldn't be empty or null");
		throw new ServiceErrorException(new ApiErrorResponse("404", "cipId shouldn't be empty or null"),
				HttpStatus.NOT_FOUND);
	}

	private void getRaceResponse(EncounterParticipantResponseDto encounterParticipantResponseDto,
			long encounterParticipantId) {
		Optional<List<EncounterParticipantRace>> optionalRace = encounterParticipantRaceRepo
				.findByEncounterParticipantId(encounterParticipantId);
		if (optionalRace.isPresent()) {
			List<EncounterParticipantRace> races = optionalRace.get();
			List<RaceDto> raceDtos = new ArrayList<>();
			for (EncounterParticipantRace race : races) {
				Optional<Race> raceOptional = raceRepository.findById(race.getRaceId());
				if (raceOptional.isPresent()) {
					Race raceObj = raceOptional.get();
					RaceDto raceDto = new RaceDto();
					raceDto.setId(raceObj.getRaceId());
					raceDto.setValue(raceObj.getRaceName());
					raceDto.setCreatedDateTime(race.getDateTime());
					raceDto.setSource(race.getSystemId());
					String racf = participantRepository.findByCmParticipantId(race.getUpdatedByCmParticipantId());
					raceDto.setCreatedByRacf(racf != null ? racf : null);
					raceDtos.add(raceDto);
				}
			}
			encounterParticipantResponseDto.setRaceDto(raceDtos);
		}
	}

	private void getEhnicityResponse(EncounterParticipantResponseDto encounterParticipantResponseDto,
			long encounterParticipantId) {
		Optional<EncounterParticipantEthnicity> optional = encounterParticipantEthnicityRepo
				.findByEncounterParticipantId(encounterParticipantId);
		if (optional.isPresent()) {
			EncounterParticipantEthnicity encounterParticipantEthnicity = optional.get();
			Optional<Ethnicity> ethnicityOptional = ethnicityRepository
					.findById(encounterParticipantEthnicity.getEthnicityId());
			if (ethnicityOptional.isPresent()) {
				Ethnicity ethnicity = ethnicityOptional.get();
				EthnicityDto ethnicityDto = new EthnicityDto();
				ethnicityDto.setId(ethnicity.getEthnicityId());
				ethnicityDto.setValue(ethnicity.getEthnicityName());
				ethnicityDto.setCreatedDateTime(encounterParticipantEthnicity.getDateTime());
				ethnicityDto.setSource(encounterParticipantEthnicity.getSystemId());
				String racf = participantRepository
						.findByCmParticipantId(encounterParticipantEthnicity.getUpdatedByCmParticipantId());
				ethnicityDto.setCreatedByRacf(racf != null ? racf : null);
				encounterParticipantResponseDto.setEthnicityDto(ethnicityDto);
			}
		}
	}

	private void getSpokenlanguageResponse(EncounterParticipantResponseDto encounterParticipantResponseDto,
			long encounterParticipantId) {
		Optional<List<EncounterParticipantLanguage>> optionalSpoLa = encounterParticipantLanguageRepo
				.findByEncounterParticipantId(encounterParticipantId);
		if (optionalSpoLa.isPresent()) {
			List<EncounterParticipantLanguage> languages = optionalSpoLa.get();
			List<MemberSpokenLanguageDto> memberSpokenLanguageDtos = new ArrayList<>();
			for (EncounterParticipantLanguage language : languages) {
				Optional<Language> languageOptional = languageRepository.findById(language.getLanguageId());
				if (languageOptional.isPresent()) {
					Language languageSpoken = languageOptional.get();
					MemberSpokenLanguageDto memberSpokenLanguageDto = new MemberSpokenLanguageDto();
					memberSpokenLanguageDto.setId(languageSpoken.getLanguageId());
					memberSpokenLanguageDto.setValue(languageSpoken.getLanguageName());
					memberSpokenLanguageDto.setCreatedDateTime(language.getDateTime());
					memberSpokenLanguageDto.setSource(language.getSystemId());
					String racf = participantRepository.findByCmParticipantId(language.getCmParticipantId());
					memberSpokenLanguageDto.setCreatedByRacf(racf != null ? racf : null);
					memberSpokenLanguageDtos.add(memberSpokenLanguageDto);
				}
			}
			encounterParticipantResponseDto.setMemberSpokenLanguageDtos(memberSpokenLanguageDtos);
		}
	}

	private void getWrittenlanguageResponse(EncounterParticipantResponseDto encounterParticipantResponseDto,
			long encounterParticipantId) {
		Optional<List<EncounterParticipantWrittenLanguage>> optionalwriLa = encounterParticipantWrittenLanguageRepo
				.findByEncounterParticipantId(encounterParticipantId);
		if (optionalwriLa.isPresent()) {
			List<EncounterParticipantWrittenLanguage> languages = optionalwriLa.get();
			List<MemberWrittenLanguageDto> memberWrittenLanguageDtos = new ArrayList<>();
			for (EncounterParticipantWrittenLanguage language : languages) {
				Optional<Language> languageOptional = languageRepository.findById(language.getLanguageId());
				if (languageOptional.isPresent()) {
					Language languageSpoken = languageOptional.get();
					MemberWrittenLanguageDto memberWrittenLanguageDto = new MemberWrittenLanguageDto();
					memberWrittenLanguageDto.setId(languageSpoken.getLanguageId());
					memberWrittenLanguageDto.setValue(languageSpoken.getLanguageName());
					memberWrittenLanguageDto.setCreatedDateTime(language.getDateTime());
					memberWrittenLanguageDto.setSource(language.getSystemId());
					String racf = participantRepository.findByCmParticipantId(language.getCmParticipantId());
					memberWrittenLanguageDto.setCreatedByRacf(racf != null ? racf : null);
					memberWrittenLanguageDtos.add(memberWrittenLanguageDto);
				}
			}
			encounterParticipantResponseDto.setMemberWrittenLanguageDtos(memberWrittenLanguageDtos);
		}
	}

	// Update Member and Member_language
	@Override
	@Transactional
	public EncounterParticipant updateEncounterParticipant(long encounter_participant_id,
			EncounterParticipantRequestDto encounterParticipantRequestDto) {
		logger.info("Entering --> realsogi/encounterparticipant endpoint service updateEncounterParticipant()");
		Optional<EncounterParticipant> encounterOptional = encounterParticipantRepository
				.findById(encounter_participant_id);
		if (!encounterOptional.isPresent()) {
			logger.info("realsogi/encounterparticipant endpoint service updateEncounterParticipant() --> No Content");
			throw new ServiceErrorException(new ApiErrorResponse("204", "No Content"), HttpStatus.NO_CONTENT);
		}
		EncounterParticipant encounterParticipant = encounterOptional.get();
		if (!encounterParticipant.getCipId().equals(encounterParticipantRequestDto.getMemberCipId())) {
			logger.info("realsogi/encounterparticipant endpoint service updateEncounterParticipant() --> No Content available for provided memberCipId "+ encounterParticipantRequestDto.getMemberCipId());
			throw new ServiceErrorException(new ApiErrorResponse("409",
					"No Content available for provided memberCipId " + encounterParticipantRequestDto.getMemberCipId()),
					HttpStatus.CONFLICT);
		}
		if (!constraintsCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "ConstraintCheck failed"), HttpStatus.CONFLICT);
		}
		if (!encounterParticipantRequestDto.getActiveStatus().equals("Inactive")
				&& !encounterParticipantRequestDto.getActiveStatus().equals("Active")) {
			logger.info("realsogi/encounterparticipant endpoint service updateEncounterParticipant() --> activeStatus should be Active or Inactive");
			throw new ServiceErrorException(new ApiErrorResponse("409", "activeStatus should be Active or Inactive"),
					HttpStatus.CONFLICT);
		}
		if (!cmParticipantId(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "cmParticipantId Check failed"),
					HttpStatus.CONFLICT);
		}
		if (encounterParticipantRequestDto.getEthnicityId() != null
				&& encounterParticipantRequestDto.getEthnicityId() <= 0) {
			logger.info("realsogi/encounterparticipant endpoint service updateEncounterParticipant() --> ethnicity Id can not be zero or negative");
			throw new ServiceErrorException(new ApiErrorResponse("409", "ethnicity Id can not be zero or negative"),
					HttpStatus.CONFLICT);
		}
		if (!raceCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "Race Check failed"), HttpStatus.CONFLICT);
		}
		if (!ethnicIdCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "EthnicId Check failed"), HttpStatus.CONFLICT);
		}
		if (!writtenLangCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "SpokenAndWrittenLang Check failed"),
					HttpStatus.CONFLICT);
		}
		if (!spokenLangCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "SpokenAndWrittenLang Check failed"),
					HttpStatus.CONFLICT);
		}
		encounterParticipant.setFirstName(encounterParticipantRequestDto.getMemberFirstName());
		encounterParticipant.setLastName(encounterParticipantRequestDto.getMemberLastName());
		encounterParticipant.setEncounterParticipantPartyId(encounterParticipantRequestDto.getMemberPartyId());
		encounterParticipant.setActiveStatus(encounterParticipant.getActiveStatus());
		updateEthnicity(encounterParticipantRequestDto, encounter_participant_id);
		updateRace(encounterParticipantRequestDto, encounter_participant_id);
		updateSpokenLanguages(encounterParticipantRequestDto, encounter_participant_id);
		updateWrittenLanguages(encounterParticipantRequestDto, encounter_participant_id);
		logger.info("Exiting --> realsogi/encounterparticipant endpoint service updateEncounterParticipant()");
		return encounterParticipant;
	}

	private void updateEthnicity(EncounterParticipantRequestDto encounterParticipantRequestDto,
			Long encounter_participant_id) {
		if (encounterParticipantRequestDto.getEthnicityId() == null
				|| encounterParticipantRequestDto.getEthnicityId() == 0) {
			return;
		}
		Optional<EncounterParticipantEthnicity> encounPartiEtni = encounterParticipantEthnicityRepo
				.findByEncounterParticipantId(encounter_participant_id);
		if (encounPartiEtni.isPresent()) {
			EncounterParticipantEthnicity encounterParticipantEthnicity = encounPartiEtni.get();
			encounterParticipantEthnicity.setEthnicityId(encounterParticipantRequestDto.getEthnicityId());
			encounterParticipantEthnicity
					.setUpdatedByCmParticipantId(encounterParticipantRequestDto.getUpdateByPartcipantId());
			encounterParticipantEthnicityRepo.save(encounterParticipantEthnicity);
		}
	}

	private void updateRace(EncounterParticipantRequestDto encounterParticipantRequestDto,
			Long encounter_participant_id) {
		if ((encounterParticipantRequestDto.getRace() == null || encounterParticipantRequestDto.getRace().isBlank())
				&& (encounterParticipantRequestDto.getRaceId() == null
						|| encounterParticipantRequestDto.getRaceId().isEmpty())) {
			return;
		}
		List<Long> getUpdatedByParticipantId = encounterParticipantRaceRepo
				.getUpdatedByStaffId(encounter_participant_id);
		List<EncounterParticipantRace> ePRaces = new ArrayList<>();
		List<Integer> raceIds = encounterParticipantRequestDto.getRaceId().stream().distinct()
				.collect(Collectors.toList());
		for (Integer raceId : raceIds) {
			EncounterParticipantRace races = encounterParticipantRaceRepo.findByRace_Id(encounter_participant_id,
					raceId);
			if (races == null
					&& getUpdatedByParticipantId.contains(encounterParticipantRequestDto.getUpdateByPartcipantId())) {
				EncounterParticipantRace encounterParticipantRace = new EncounterParticipantRace();
				encounterParticipantRace.setEncounterParticipantId(encounter_participant_id);
				encounterParticipantRace.setRaceId(raceId);
				encounterParticipantRace
						.setUpdatedByCmParticipantId(encounterParticipantRequestDto.getUpdateByPartcipantId());
				encounterParticipantRace.setUuid(encounterParticipantRace.getUuid());
				// encounterParticipantRaceRepo.save(encounterParticipantRace);
				ePRaces.add(encounterParticipantRace);
			} else if (races == null
					&& !getUpdatedByParticipantId.contains(encounterParticipantRequestDto.getUpdateByPartcipantId())) {
				encounterParticipantRaceRepo.deleteByMemberandStaffId(encounter_participant_id,
						getUpdatedByParticipantId);
				EncounterParticipantRace encounterParticipantRace = new EncounterParticipantRace();
				encounterParticipantRace.setEncounterParticipantId(encounter_participant_id);
				encounterParticipantRace.setRaceId(raceId);
				encounterParticipantRace
						.setUpdatedByCmParticipantId(encounterParticipantRequestDto.getUpdateByPartcipantId());
				encounterParticipantRace.setUuid(encounterParticipantRace.getUuid());
				// encounterParticipantRaceRepo.save(encounterParticipantRace);
				ePRaces.add(encounterParticipantRace);
			} else if (races != null
					&& !getUpdatedByParticipantId.contains(encounterParticipantRequestDto.getUpdateByPartcipantId())) {
				encounterParticipantRaceRepo.deleteByMemberandStaffId(encounter_participant_id,
						getUpdatedByParticipantId);
				EncounterParticipantRace encounterParticipantRace = new EncounterParticipantRace();
				encounterParticipantRace.setEncounterParticipantId(encounter_participant_id);
				encounterParticipantRace.setRaceId(raceId);
				encounterParticipantRace
						.setUpdatedByCmParticipantId(encounterParticipantRequestDto.getUpdateByPartcipantId());
				encounterParticipantRace.setUuid(encounterParticipantRace.getUuid());
				// encounterParticipantRaceRepo.save(encounterParticipantRace);
				ePRaces.add(encounterParticipantRace);
			}
		}
		encounterParticipantRaceRepo.saveAll(ePRaces);
		List<Integer> dbRaceId = encounterParticipantRaceRepo.getRaceIds(encounter_participant_id);

		deleteOldRacesById(dbRaceId, raceIds, encounter_participant_id);
	}

	private void updateSpokenLanguages(EncounterParticipantRequestDto encounterParticipantRequestDto,
			Long encounter_participant_id) {
		if ((encounterParticipantRequestDto.getSpokenLanguages() == null
				|| encounterParticipantRequestDto.getSpokenLanguages().isEmpty())
				&& (encounterParticipantRequestDto.getOtherSpokenLang() == null
						|| encounterParticipantRequestDto.getOtherSpokenLang().isBlank())) {

			return;
		}
		List<Long> getUpdatedByParticipantId = encounterParticipantLanguageRepo
				.getUpdatedByStaffId(encounter_participant_id);
		List<EncounterParticipantLanguage> ePLanguages = new ArrayList<>();
		List<Integer> languagesIds = encounterParticipantRequestDto.getSpokenLanguages().stream().distinct()
				.collect(Collectors.toList());
		for (Integer languageId : languagesIds) {
			EncounterParticipantLanguage languages = encounterParticipantLanguageRepo
					.findByLanguage_Id(encounter_participant_id, languageId);
			if (languages == null
					&& getUpdatedByParticipantId.contains(encounterParticipantRequestDto.getUpdateByPartcipantId())) {
				EncounterParticipantLanguage encounterParticipantLanguage = new EncounterParticipantLanguage();
				encounterParticipantLanguage.setEncounterParticipantId(encounter_participant_id);
				encounterParticipantLanguage.setLanguageId(languageId);
				encounterParticipantLanguage
						.setCmParticipantId(encounterParticipantRequestDto.getUpdateByPartcipantId());
				encounterParticipantLanguage.setUuid(encounterParticipantLanguage.getUuid());
				encounterParticipantLanguageRepo.save(encounterParticipantLanguage);
				ePLanguages.add(encounterParticipantLanguage);
			} else if (languages == null
					&& !getUpdatedByParticipantId.contains(encounterParticipantRequestDto.getUpdateByPartcipantId())) {
				encounterParticipantLanguageRepo.deleteByMemberandStaffId(encounter_participant_id,
						getUpdatedByParticipantId);
				EncounterParticipantLanguage encounterParticipantLanguage = new EncounterParticipantLanguage();
				encounterParticipantLanguage.setEncounterParticipantId(encounter_participant_id);
				encounterParticipantLanguage.setLanguageId(languageId);
				encounterParticipantLanguage
						.setCmParticipantId(encounterParticipantRequestDto.getUpdateByPartcipantId());
				encounterParticipantLanguage.setUuid(encounterParticipantLanguage.getUuid());
				encounterParticipantLanguageRepo.save(encounterParticipantLanguage);
				ePLanguages.add(encounterParticipantLanguage);
			} else if (languages != null
					&& !getUpdatedByParticipantId.contains(encounterParticipantRequestDto.getUpdateByPartcipantId())) {
				encounterParticipantLanguageRepo.deleteByMemberandStaffId(encounter_participant_id,
						getUpdatedByParticipantId);
				EncounterParticipantLanguage encounterParticipantLanguage = new EncounterParticipantLanguage();
				encounterParticipantLanguage.setEncounterParticipantId(encounter_participant_id);
				encounterParticipantLanguage.setLanguageId(languageId);
				encounterParticipantLanguage
						.setCmParticipantId(encounterParticipantRequestDto.getUpdateByPartcipantId());
				encounterParticipantLanguage.setUuid(encounterParticipantLanguage.getUuid());
				encounterParticipantLanguageRepo.save(encounterParticipantLanguage);
				ePLanguages.add(encounterParticipantLanguage);
			}
		}
		encounterParticipantLanguageRepo.saveAll(ePLanguages);
		List<Integer> dbLanguagesId = encounterParticipantLanguageRepo.getLanguageIds(encounter_participant_id);

		deleteOldlanguagesById(dbLanguagesId, languagesIds, encounter_participant_id);
	}

	private void updateWrittenLanguages(EncounterParticipantRequestDto encounterParticipantRequestDto,
			Long encounter_participant_id) {
		if ((encounterParticipantRequestDto.getWrittenLanguages() == null
				|| encounterParticipantRequestDto.getWrittenLanguages().isEmpty())
				&& (encounterParticipantRequestDto.getOtherWrittenLang() == null
						|| encounterParticipantRequestDto.getOtherWrittenLang().isBlank())) {

			return;
		}
		List<Long> getUpdatedByParticipantId = encounterParticipantWrittenLanguageRepo
				.getUpdatedByStaffId(encounter_participant_id);
		List<EncounterParticipantWrittenLanguage> ePLanguages = new ArrayList<>();
		List<Integer> languagesIds = encounterParticipantRequestDto.getWrittenLanguages().stream().distinct()
				.collect(Collectors.toList());
		for (Integer languageId : languagesIds) {
			EncounterParticipantWrittenLanguage languages = encounterParticipantWrittenLanguageRepo
					.findByLanguage_Id(encounter_participant_id, languageId);
			if (languages == null
					&& getUpdatedByParticipantId.contains(encounterParticipantRequestDto.getUpdateByPartcipantId())) {
				EncounterParticipantWrittenLanguage encounterParticipantLanguage = new EncounterParticipantWrittenLanguage();
				encounterParticipantLanguage.setEncounterParticipantId(encounter_participant_id);
				encounterParticipantLanguage.setLanguageId(languageId);
				encounterParticipantLanguage
						.setCmParticipantId(encounterParticipantRequestDto.getUpdateByPartcipantId());
				encounterParticipantLanguage.setUuid(encounterParticipantLanguage.getUuid());
				encounterParticipantWrittenLanguageRepo.save(encounterParticipantLanguage);
				ePLanguages.add(encounterParticipantLanguage);
			} else if (languages == null
					&& !getUpdatedByParticipantId.contains(encounterParticipantRequestDto.getUpdateByPartcipantId())) {
				encounterParticipantWrittenLanguageRepo.deleteByMemberandStaffId(encounter_participant_id,
						getUpdatedByParticipantId);
				EncounterParticipantWrittenLanguage encounterParticipantLanguage = new EncounterParticipantWrittenLanguage();
				encounterParticipantLanguage.setEncounterParticipantId(encounter_participant_id);
				encounterParticipantLanguage.setLanguageId(languageId);
				encounterParticipantLanguage
						.setCmParticipantId(encounterParticipantRequestDto.getUpdateByPartcipantId());
				encounterParticipantLanguage.setUuid(encounterParticipantLanguage.getUuid());
				encounterParticipantWrittenLanguageRepo.save(encounterParticipantLanguage);
				ePLanguages.add(encounterParticipantLanguage);
			} else if (languages != null
					&& !getUpdatedByParticipantId.contains(encounterParticipantRequestDto.getUpdateByPartcipantId())) {
				encounterParticipantWrittenLanguageRepo.deleteByMemberandStaffId(encounter_participant_id,
						getUpdatedByParticipantId);
				EncounterParticipantWrittenLanguage encounterParticipantLanguage = new EncounterParticipantWrittenLanguage();
				encounterParticipantLanguage.setEncounterParticipantId(encounter_participant_id);
				encounterParticipantLanguage.setLanguageId(languageId);
				encounterParticipantLanguage
						.setCmParticipantId(encounterParticipantRequestDto.getUpdateByPartcipantId());
				encounterParticipantLanguage.setUuid(encounterParticipantLanguage.getUuid());
				encounterParticipantWrittenLanguageRepo.save(encounterParticipantLanguage);
				ePLanguages.add(encounterParticipantLanguage);
			}
		}
		encounterParticipantWrittenLanguageRepo.saveAll(ePLanguages);
		List<Integer> dbLanguagesId = encounterParticipantWrittenLanguageRepo.getLanguageIds(encounter_participant_id);

		deleteOldWrittenlanguagesById(dbLanguagesId, languagesIds, encounter_participant_id);
	}

	private void deleteOldlanguagesById(List<Integer> dbLanguagesId, List<Integer> languagesId,
			long encounterParticipantId) {
		List<Integer> deleteIds = null;
		if (dbLanguagesId != null && dbLanguagesId.size() > 0 && languagesId != null && languagesId.size() > 0) {
			deleteIds = dbLanguagesId.stream().filter(e -> !languagesId.contains(e)).collect(Collectors.toList());
		}
		if (deleteIds != null) {
			encounterParticipantLanguageRepo.deleteByLanguageList(encounterParticipantId, deleteIds);
		}
	}

	private void deleteOldWrittenlanguagesById(List<Integer> dbLanguagesId, List<Integer> languagesId,
			long encounterParticipantId) {
		List<Integer> deleteIds = null;
		if (dbLanguagesId != null && dbLanguagesId.size() > 0 && languagesId != null && languagesId.size() > 0) {
			deleteIds = dbLanguagesId.stream().filter(e -> !languagesId.contains(e)).collect(Collectors.toList());
		}
		if (deleteIds != null) {
			encounterParticipantWrittenLanguageRepo.deleteByLanguageList(encounterParticipantId, deleteIds);
		}
	}

	private void deleteOldRacesById(List<Integer> dbRaceIds, List<Integer> racesId, long encounterParticipantId) {
		List<Integer> deleteIds = null;
		if (dbRaceIds != null && dbRaceIds.size() > 0 && racesId != null && racesId.size() > 0) {
			deleteIds = dbRaceIds.stream().filter(e -> !racesId.contains(e)).collect(Collectors.toList());
		}
		if (deleteIds != null) {
			encounterParticipantRaceRepo.deleteByRaceList(encounterParticipantId, deleteIds);
		}
	}

	@Override
	public Map<String, Object> getAllData() {
		List<Language> languageData = languageRepository.findAll();
		List<Manager> managerData = managerRepository.findAll();
		List<Role> roleData = roleRepository.findAll();
		List<Team> teamData = teamRepository.findAll();

		if (languageData.isEmpty()) {
			logger.info("realsogi/REL endpoint service getAllData() --> languageData doesn't exist");
			throw new ServiceErrorException(new ApiErrorResponse("409", "languageData doesn't exist"),
					HttpStatus.CONFLICT);
		}
		if (managerData.isEmpty()) {
			logger.info("realsogi/REL endpoint service getAllData() --> managerData doesn't exist");
			throw new ServiceErrorException(new ApiErrorResponse("409", "managerData doesn't exist"),
					HttpStatus.CONFLICT);
		}
		if (roleData.isEmpty()) {
			logger.info("realsogi/REL endpoint service getAllData() --> roleData doesn't exist");
			throw new ServiceErrorException(new ApiErrorResponse("409", "roleData doesn't exist"), HttpStatus.CONFLICT);
		}
		if (teamData.isEmpty()) {
			logger.info("realsogi/REL endpoint service getAllData() --> teamData doesn't exist");
			throw new ServiceErrorException(new ApiErrorResponse("409", "teamData doesn't exist"), HttpStatus.CONFLICT);
		}

		List<Ethnicity> ethnicities = ethnicityRepository.findAll();
		if (ethnicities.isEmpty()) {
			logger.info("realsogi/REL endpoint service getAllData() --> ethnicity doesn't exist");
			throw new ServiceErrorException(new ApiErrorResponse("409", "ethnicity doesn't exist"),
					HttpStatus.CONFLICT);
		}
		List<Race> races = raceRepository.findAll();
		if (races.isEmpty()) {
			logger.info("realsogi/REL endpoint service getAllData() --> race doesn't exist");
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
}
