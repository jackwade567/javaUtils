package com.bcbsfl.ccn.cmparticipant.services;

import java.util.ArrayList;
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

import com.bcbsfl.ccn.cmparticipant.dto.EncounterParticipantRequestDto;
import com.bcbsfl.ccn.cmparticipant.dto.EncounterParticipantResponseDto;
import com.bcbsfl.ccn.cmparticipant.dto.EthinicityDto;
import com.bcbsfl.ccn.cmparticipant.dto.MemberSpokenLanguageDto;
import com.bcbsfl.ccn.cmparticipant.dto.MemberWrittenLanguageDto;
import com.bcbsfl.ccn.cmparticipant.dto.RaceDto;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipant;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipant.Status;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipantEthnicity;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipantLanguage;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipantRace;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipantWrittenLanguage;
import com.bcbsfl.ccn.cmparticipant.entites.Ethnicity;
import com.bcbsfl.ccn.cmparticipant.entites.Language;
import com.bcbsfl.ccn.cmparticipant.entites.Manager;
import com.bcbsfl.ccn.cmparticipant.entites.Participant;
import com.bcbsfl.ccn.cmparticipant.entites.Race;
import com.bcbsfl.ccn.cmparticipant.entites.Role;
import com.bcbsfl.ccn.cmparticipant.entites.Team;
import com.bcbsfl.ccn.cmparticipant.exception.ApiErrorResponse;
import com.bcbsfl.ccn.cmparticipant.exception.ServiceErrorException;
import com.bcbsfl.ccn.cmparticipant.repositories.EncounterParticipantEthnicityRepo;
import com.bcbsfl.ccn.cmparticipant.repositories.EncounterParticipantLanguageRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.EncounterParticipantRaceRepo;
import com.bcbsfl.ccn.cmparticipant.repositories.EncounterParticipantRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.EncounterParticipantWrittenLanguageRepo;
import com.bcbsfl.ccn.cmparticipant.repositories.EthnicityRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.LanguageRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.ParticipantRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.RaceRepository;
import com.bcbsfl.ccn.cmparticipant.serviceimpl.ParticipantServiceImpl;

@Service
public class EncounterParticipantServiceImpl implements EncounterParticipantService {

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

	@Override
	@Transactional
	public EncounterParticipant addEncounterParticipant(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		if (!uniqueConstraintsCheck(encounterParticipantRequestDto)) {
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
		if (!cmParticipantIdAndEthinicIdCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "cmParticipantIdAndEthinicId Check failed"),
					HttpStatus.CONFLICT);
		}
		if (!raceAndSpokenAndWrittenLangCheck(encounterParticipantRequestDto)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "raceAndSpokenAndWrittenLang Check failed"),
					HttpStatus.CONFLICT);
		}
		EncounterParticipant encounterParticipant = new EncounterParticipant();
		encounterParticipant.setCipId(encounterParticipantRequestDto.getMemberCipId());
		encounterParticipant.setFirstName(encounterParticipantRequestDto.getMemberFirstName());
		encounterParticipant.setLastName(encounterParticipantRequestDto.getMemberLastName());
		encounterParticipant.setEncounterParticipantPartyId(encounterParticipantRequestDto.getMemberPartyId());
		encounterParticipant.setActiveStatus(Status.valueOf(encounterParticipantRequestDto.getActiveStatus()));
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
		return savedEncounterParticipant;
	}

	private void addRace(Long encounterParticipantId, long updateByPartcipantId, int raceId) {
		EncounterParticipantRace encounterParticipantRace = new EncounterParticipantRace();
		encounterParticipantRace.setEncounterParticipantId(encounterParticipantId);
		encounterParticipantRace.setRaceId(raceId);
		encounterParticipantRace.setUpdatedByCmParticipantId(updateByPartcipantId);
		encounterParticipantRace.setUuid(encounterParticipantRace.getUuid());
		encounterParticipantRaceRepo.save(encounterParticipantRace);
	}

	private void addEthnicity(Long encounterParticipantId, long updateByPartcipantId, int ethnicityId) {
		EncounterParticipantEthnicity encounterParticipantEthnicity = new EncounterParticipantEthnicity();
		encounterParticipantEthnicity.setEncounterParticipantId(encounterParticipantId);
		encounterParticipantEthnicity.setEthnicityId(ethnicityId);
		encounterParticipantEthnicity.setUpdatedByCmParticipantId(updateByPartcipantId);
		encounterParticipantEthnicity.setUuid(encounterParticipantEthnicity.getUuid());
		encounterParticipantEthnicityRepo.save(encounterParticipantEthnicity);
	}

	private boolean stringValidationInput(String input) {
		Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	private boolean cmParticipantIdAndEthinicIdCheck(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		Optional<Participant> participantOptional = participantRepository
				.findById(encounterParticipantRequestDto.getUpdateByPartcipantId());
		if (!participantOptional.isPresent()) {
			throw new ServiceErrorException(new ApiErrorResponse("404", "No record found for given Participant_id!"),
					HttpStatus.NOT_FOUND);
		}
		Optional<Ethnicity> ethnicityOptional = ethnicityRepository
				.findById(encounterParticipantRequestDto.getEthnicityId());
		if (!ethnicityOptional.isPresent()) {
			throw new ServiceErrorException(new ApiErrorResponse("404", "No record found for given ethninicityId!"),
					HttpStatus.NOT_FOUND);
		}
		return true;
	}

	private boolean raceAndSpokenAndWrittenLangCheck(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		if (encounterParticipantRequestDto.getRaceId() != null && encounterParticipantRequestDto.getRaceId() <= 0) {
			throw new ServiceErrorException(new ApiErrorResponse("404", "Raceid should not be zero or negative value"),
					HttpStatus.NOT_FOUND);
		}
		if ((encounterParticipantRequestDto.getRace() == null || encounterParticipantRequestDto.getRace().isBlank())
				&& (encounterParticipantRequestDto.getRaceId() == null
						|| encounterParticipantRequestDto.getRaceId() == 0)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "Both race and raceId are empty"),
					HttpStatus.CONFLICT);
		}
		if ((encounterParticipantRequestDto.getRace() != null && !encounterParticipantRequestDto.getRace().isBlank())
				&& (encounterParticipantRequestDto.getRaceId() != null
						&& encounterParticipantRequestDto.getRaceId() != 0)) {
			throw new ServiceErrorException(new ApiErrorResponse("409", "either race or raceId required"),
					HttpStatus.CONFLICT);
		}
		if ((encounterParticipantRequestDto.getSpokenLanguages() == null
				|| encounterParticipantRequestDto.getSpokenLanguages().isEmpty())
				&& (encounterParticipantRequestDto.getOtherSpokenLang() == null
						|| encounterParticipantRequestDto.getOtherSpokenLang().isBlank())) {
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "Both otherSpokenlanguage and Spokenlanguage are empty"),
					HttpStatus.CONFLICT);
		}
		if ((encounterParticipantRequestDto.getWrittenLanguages() == null
				|| encounterParticipantRequestDto.getWrittenLanguages().isEmpty())
				&& (encounterParticipantRequestDto.getOtherWrittenLang() == null
						|| encounterParticipantRequestDto.getOtherWrittenLang().isBlank())) {
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "Both otherWrittenlanguage and Writtenlanguage are empty"),
					HttpStatus.CONFLICT);
		}
		if (encounterParticipantRequestDto.getSpokenLanguages() != null
				&& !encounterParticipantRequestDto.getSpokenLanguages().isEmpty()) {
			List<Integer> dblanguageList = languageRepository
					.getlanguagesId(encounterParticipantRequestDto.getSpokenLanguages());
			if (!dblanguageList.isEmpty()) {
				List<Integer> unAvailableIds = encounterParticipantRequestDto.getSpokenLanguages().stream()
						.filter(id -> !dblanguageList.contains(id)).collect(Collectors.toList());
				if (!unAvailableIds.isEmpty()) {
					throw new ServiceErrorException(
							new ApiErrorResponse("409", "The followingId's are invalid" + unAvailableIds),
							HttpStatus.CONFLICT);
				}
			} else {
				throw new ServiceErrorException(
						new ApiErrorResponse("409",
								"the followingId's are invalid" + encounterParticipantRequestDto.getSpokenLanguages()),
						HttpStatus.CONFLICT);
			}
		}
		if (encounterParticipantRequestDto.getWrittenLanguages() != null
				&& !encounterParticipantRequestDto.getWrittenLanguages().isEmpty()) {
			List<Integer> dblanguageList = languageRepository
					.getlanguagesId(encounterParticipantRequestDto.getWrittenLanguages());
			if (!dblanguageList.isEmpty()) {
				List<Integer> unAvailableIds = encounterParticipantRequestDto.getWrittenLanguages().stream()
						.filter(id -> !dblanguageList.contains(id)).collect(Collectors.toList());
				if (!unAvailableIds.isEmpty()) {
					throw new ServiceErrorException(
							new ApiErrorResponse("409", "the followingId's are invalid" + unAvailableIds),
							HttpStatus.CONFLICT);
				}
			} else {
				throw new ServiceErrorException(
						new ApiErrorResponse("409",
								"the followingId's are invalid" + encounterParticipantRequestDto.getWrittenLanguages()),
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
		if (encounterParticipantRequestDto.getRaceId() != null) {
			Optional<Race> raceOptional = raceRepository.findById(encounterParticipantRequestDto.getRaceId());
			if (!raceOptional.isPresent()) {
				throw new ServiceErrorException(new ApiErrorResponse("404", "No record found for given raceId!"),
						HttpStatus.NOT_FOUND);
			}
		}
		if (encounterParticipantRequestDto.getRace() != null && !encounterParticipantRequestDto.getRace().isBlank()) {
			Optional<Race> raceOptional = raceRepository.findByRaceName(encounterParticipantRequestDto.getRace());
			Integer raceId = 0;
			if (!raceOptional.isPresent()) {
				Race race = new Race();
				race.setRaceName(encounterParticipantRequestDto.getRace());
				race.setCodeset(race.getCodeset());
				Race saveRace = raceRepository.save(race);
				raceId = saveRace.getRaceId();
				encounterParticipantRequestDto.setRaceId(raceId);
			} else {
				Race race = raceOptional.get();
				encounterParticipantRequestDto.setRaceId(race.getRaceId());
			}
		}
		return true;
	}

	private boolean inputLanguagCheck(String language) {
		if (language != null && !"".equals(language)) {
			if (ParticipantServiceImpl.stringValidation(language)) {
				return true;
			} else {
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
		throw new ServiceErrorException(new ApiErrorResponse("404", "cipId must not be empty!"), HttpStatus.NOT_FOUND);
	}

	private boolean constraintsCheck(EncounterParticipantRequestDto encounterParticipantRequestDto) {
		if (encounterParticipantRequestDto.getSpokenLanguages() != null
				&& encounterParticipantRequestDto.getSpokenLanguages().contains(0)) {
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "Language id should not be 0, Please enter valid languageId "),
					HttpStatus.NOT_FOUND);
		}
		if (encounterParticipantRequestDto.getWrittenLanguages() != null
				&& encounterParticipantRequestDto.getWrittenLanguages().contains(0)) {
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "Language id should not be 0, Please enter valid languageId "),
					HttpStatus.NOT_FOUND);
		}
		if (!stringValidationInput(encounterParticipantRequestDto.getMemberFirstName())) {
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "MemberFirstname should not contain any numbers and Special Character :"
							+ encounterParticipantRequestDto.getMemberFirstName()),
					HttpStatus.CONFLICT);
		}
		if (!stringValidationInput(encounterParticipantRequestDto.getMemberLastName())) {
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
				throw new ServiceErrorException(
						new ApiErrorResponse("404", "languagesId list is empty but member is saved"),
						HttpStatus.NOT_FOUND);
			}
		} else {
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
				throw new ServiceErrorException(
						new ApiErrorResponse("404", "languagesId list is empty but member is saved"),
						HttpStatus.NOT_FOUND);
			}
		} else {
			throw new ServiceErrorException(
					new ApiErrorResponse("404", "EncounterParticipant entity doesn't exist " + encounterParticipantId),
					HttpStatus.NOT_FOUND);
		}
		return encounterParticipantLanguages;
	}

	@Override
	public EncounterParticipantResponseDto getEncounterParticipantResponse(String cipId) {
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
			throw new ServiceErrorException(
					new ApiErrorResponse("404", " with cipId, " + cipId + " Resource Not Found"), HttpStatus.NOT_FOUND);
		}
		throw new ServiceErrorException(new ApiErrorResponse("404", "cipId shouldn't empty or null exist"),
				HttpStatus.NOT_FOUND);
	}

	private void getRaceResponse(EncounterParticipantResponseDto encounterParticipantResponseDto,
			long encounterParticipantId) {
		Optional<EncounterParticipantRace> optional = encounterParticipantRaceRepo
				.findByEncounterParticipantId(encounterParticipantId);
		if (optional.isPresent()) {
			EncounterParticipantRace encounterParticipantRace = optional.get();
			Optional<Race> raceOptional = raceRepository.findById(encounterParticipantRace.getRaceId());
			if (raceOptional.isPresent()) {
				Race race = raceOptional.get();
				RaceDto raceDto = new RaceDto();
				raceDto.setValue(race.getRaceName() != null ? race.getRaceName() : null);
				raceDto.setCreatedDateTime(encounterParticipantRace.getDateTime());
				raceDto.setSource(encounterParticipantRace.getSystemId());
				String racf = participantRepository
						.findByCmParticipantId(encounterParticipantRace.getUpdatedByCmParticipantId());
				raceDto.setCreatedByRacf(racf != null ? racf : null);
				encounterParticipantResponseDto.setRaceDto(raceDto);
			}
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
				EthinicityDto ethinicityDto = new EthinicityDto();
				ethinicityDto.setValue(ethnicity.getEthnicityName());
				ethinicityDto.setCreatedDateTime(encounterParticipantEthnicity.getDateTime());
				ethinicityDto.setSource(encounterParticipantEthnicity.getSystemId());
				String racf = participantRepository
						.findByCmParticipantId(encounterParticipantEthnicity.getUpdatedByCmParticipantId());
				ethinicityDto.setCreatedByRacf(racf != null ? racf : null);
				encounterParticipantResponseDto.setEthinicityDto(ethinicityDto);
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
	/*
	 * @Override public EncounterParticipant updateEncounterParticipant(long
	 * encounterParticipantId, EncounterParticipantDto encounterParticipantDto) { if
	 * (updateCmParticipantIdcheck(encounterParticipantDto)) {
	 * Optional<EncounterParticipant> optional =
	 * encounterParticipantRepository.findById(encounterParticipantId); if
	 * (optional.isPresent()) { EncounterParticipant encounterParticipant =
	 * optional.get(); if (uniqueConstraintsCheck(encounterParticipantDto)) {
	 * encounterParticipant.setCipId(encounterParticipantDto.getCipId());
	 * encounterParticipant.setFirstName(encounterParticipantDto.getFirstName());
	 * encounterParticipant.setLastName(encounterParticipantDto.getLastName());
	 * encounterParticipant.setActiveStatus(encounterParticipantDto.getActiveStatus(
	 * ));
	 * 
	 * List<EncounterParticipantLanguage> languages =
	 * UpdateLanguages(encounterParticipantId,
	 * encounterParticipantDto.getLanguagesId(),
	 * encounterParticipantDto.getUpdateByPartcipantId());
	 * encounterParticipantRepository.save(encounterParticipant); return
	 * encounterParticipantRepository.getById(encounterParticipantId); } else if
	 * (!uniqueConstraintsCheck(encounterParticipantDto) &&
	 * (encounterParticipant.getCipId().equals(encounterParticipantDto.getCipId())))
	 * { encounterParticipant.setFirstName(encounterParticipantDto.getFirstName());
	 * encounterParticipant.setLastName(encounterParticipantDto.getLastName());
	 * encounterParticipant.setActiveStatus(encounterParticipantDto.getActiveStatus(
	 * )); List<EncounterParticipantLanguage> languages =
	 * UpdateLanguages(encounterParticipantId,
	 * encounterParticipantDto.getLanguagesId(),
	 * encounterParticipantDto.getUpdateByPartcipantId());
	 * encounterParticipantRepository.save(encounterParticipant); return
	 * encounterParticipantRepository.getById(encounterParticipantId);
	 * 
	 * } else { throw new ServiceErrorException(new ApiErrorResponse("409",
	 * "cipId is already exist"), HttpStatus.CONFLICT); } } else { throw new
	 * ServiceErrorException( new ApiErrorResponse("404",
	 * "with the EncounterparticipantId, EncounterParticipant entity is not available"
	 * ), HttpStatus.NOT_FOUND); }
	 * 
	 * } else { throw new ServiceErrorException( new ApiErrorResponse("409",
	 * "Since Participant doesn't exist, member is not updated "),
	 * HttpStatus.CONFLICT); } }
	 * 
	 * private List<EncounterParticipantLanguage> UpdateLanguages(long
	 * encounterParticipantId, List<Integer> languagesId, long cm_participant_id) {
	 * List<EncounterParticipantLanguage> ePLanguages = new ArrayList<>();
	 * List<Long> getUpdatedByParticipantId = new ArrayList<>();
	 * Optional<EncounterParticipant> optional =
	 * encounterParticipantRepository.findById(encounterParticipantId); if
	 * (optional.isPresent()) { if (languagesId != null && languagesId.size() > 0) {
	 * getUpdatedByParticipantId = encounterParticipantLanguageRepo
	 * .getUpdatedByStaffId(encounterParticipantId); for (Integer languageId :
	 * languagesId) { if (findLanguageId(languageId)) { EncounterParticipantLanguage
	 * languages = encounterParticipantLanguageRepo
	 * .findByLanguage_Id(encounterParticipantId, languageId); if (languages == null
	 * && getUpdatedByParticipantId.contains(cm_participant_id)) {
	 * EncounterParticipantLanguage encounterParticipantLanguage = new
	 * EncounterParticipantLanguage();
	 * encounterParticipantLanguage.setEncounter_participant_id(
	 * encounterParticipantId);
	 * encounterParticipantLanguage.setLanguage_id(languageId);
	 * encounterParticipantLanguage.setStatus(encounterParticipantLanguage.getStatus
	 * ()); encounterParticipantLanguage.setCm_participant_id(cm_participant_id); //
	 * encounterParticipantLanguageRepo.save(encounterParticipantLanguage);
	 * ePLanguages.add(encounterParticipantLanguage); } else if (languages == null
	 * && !getUpdatedByParticipantId.contains(cm_participant_id)) {
	 * encounterParticipantLanguageRepo.deleteByMemberandStaffId(
	 * encounterParticipantId, getUpdatedByParticipantId);
	 * EncounterParticipantLanguage encounterParticipantLanguage = new
	 * EncounterParticipantLanguage();
	 * encounterParticipantLanguage.setEncounter_participant_id(
	 * encounterParticipantId);
	 * encounterParticipantLanguage.setLanguage_id(languageId);
	 * encounterParticipantLanguage.setStatus(encounterParticipantLanguage.getStatus
	 * ()); encounterParticipantLanguage.setCm_participant_id(cm_participant_id); //
	 * encounterParticipantLanguageRepo.save(encounterParticipantLanguage);
	 * ePLanguages.add(encounterParticipantLanguage); } else if (languages != null
	 * && !getUpdatedByParticipantId.contains(cm_participant_id)) {
	 * encounterParticipantLanguageRepo.deleteByMemberandStaffId(
	 * encounterParticipantId, getUpdatedByParticipantId);
	 * EncounterParticipantLanguage encounterParticipantLanguage = new
	 * EncounterParticipantLanguage();
	 * encounterParticipantLanguage.setEncounter_participant_id(
	 * encounterParticipantId);
	 * encounterParticipantLanguage.setLanguage_id(languageId);
	 * encounterParticipantLanguage.setStatus(encounterParticipantLanguage.getStatus
	 * ()); encounterParticipantLanguage.setCm_participant_id(cm_participant_id); //
	 * encounterParticipantLanguageRepo.save(encounterParticipantLanguage);
	 * ePLanguages.add(encounterParticipantLanguage); } } else { throw new
	 * ServiceErrorException( new ApiErrorResponse("404", "Language _id " +
	 * languageId + " is not found"), HttpStatus.NOT_FOUND); }
	 * 
	 * } encounterParticipantLanguageRepo.saveAll(ePLanguages); List<Integer>
	 * dbLanguagesId =
	 * encounterParticipantLanguageRepo.getLanguageIds(encounterParticipantId);
	 * 
	 * deleteOldlanguagesById(dbLanguagesId, languagesId, encounterParticipantId); }
	 * 
	 * return ePLanguages; }
	 * 
	 * throw new ServiceErrorException( new ApiErrorResponse("404",
	 * "Participant entity is not available and update failed on Staff_language entity"
	 * ), HttpStatus.NOT_FOUND); }
	 * 
	 * private void deleteOldlanguagesById(List<Integer> dbLanguagesId,
	 * List<Integer> languagesId, long encounterParticipantId) { List<Integer>
	 * deleteIds = null; if (dbLanguagesId != null && dbLanguagesId.size() > 0 &&
	 * languagesId != null && languagesId.size() > 0) { deleteIds =
	 * dbLanguagesId.stream().filter(e ->
	 * !languagesId.contains(e)).collect(Collectors.toList()); } if (deleteIds !=
	 * null) {
	 * encounterParticipantLanguageRepo.deleteByLanguageList(encounterParticipantId,
	 * deleteIds); } }
	 * 
	 * private boolean findLanguageId(Integer languageId) { Optional<Language>
	 * language = languageRepository.findById(languageId); if (language.isPresent())
	 * { return true; } return false; }
	 */
}
