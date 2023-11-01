package com.bcbsfl.ccn.cmparticipant.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bcbsfl.ccn.cmparticipant.dto.EncounterParticipantDto;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipant;
import com.bcbsfl.ccn.cmparticipant.entites.EncounterParticipantLanguage;
import com.bcbsfl.ccn.cmparticipant.entites.Language;
import com.bcbsfl.ccn.cmparticipant.entites.Participant;
import com.bcbsfl.ccn.cmparticipant.entites.PartipantLanguage;
import com.bcbsfl.ccn.cmparticipant.exception.ApiErrorResponse;
import com.bcbsfl.ccn.cmparticipant.exception.ServiceErrorException;
import com.bcbsfl.ccn.cmparticipant.repositories.EncounterParticipantLanguageRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.EncounterParticipantRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.LanguageRepository;
import com.bcbsfl.ccn.cmparticipant.repositories.ParticipantRepository;

@Service
public class EncounterParticipantServiceImpl implements EncounterParticipantService {

	@Autowired
	private EncounterParticipantRepository encounterParticipantRepository;

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private EncounterParticipantLanguageRepository encounterParticipantLanguageRepo;

	@Autowired
	private LanguageRepository languageRepository;

	@Override
	public EncounterParticipant addEncounterParticipant(EncounterParticipantDto encounterParticipantDto) {
		if (updateCmParticipantIdcheck(encounterParticipantDto)) {
			if (uniqueConstraintsCheck(encounterParticipantDto)) {
				EncounterParticipant encounterParticipant = new EncounterParticipant();
				encounterParticipant.setCipId(encounterParticipantDto.getCipId());
				encounterParticipant.setFirstName(encounterParticipantDto.getFirstName());
				encounterParticipant.setLastName(encounterParticipantDto.getLastName());
				encounterParticipant.setActiveStatus(encounterParticipantDto.getActiveStatus());
				EncounterParticipant savedEncounterParticipant = encounterParticipantRepository.save(encounterParticipant);
				Long encounterParticipantId = savedEncounterParticipant.getEncounter_participant_id();
				addlanguages(encounterParticipantId, encounterParticipantDto.getUpdateByPartcipantId(), encounterParticipantDto.getLanguagesId());
				return savedEncounterParticipant;
			} else {
				throw new ServiceErrorException(new ApiErrorResponse("409", "cipId is already exist"),
						HttpStatus.CONFLICT);
			}
		} else {
			throw new ServiceErrorException(new ApiErrorResponse("409", "Participant doesn't exist"), HttpStatus.CONFLICT);
		}

	}

	private boolean updateCmParticipantIdcheck(EncounterParticipantDto encounterParticipantDto) {
		if (encounterParticipantDto.getUpdateByPartcipantId() != 0) {
			Optional<Participant> participantOptional = participantRepository.findById(encounterParticipantDto.getUpdateByPartcipantId());
			if (participantOptional.isPresent()) {
				return true;
			} else {
				return false;
			}
		}
		throw new ServiceErrorException(new ApiErrorResponse("404", "Participant_id must not be empty!"),
				HttpStatus.NOT_FOUND);
	}

	private boolean uniqueConstraintsCheck(EncounterParticipantDto encounterParticipantDto) {
		if (encounterParticipantDto.getCipId() != null && encounterParticipantDto.getCipId().length() > 0) {
			Optional<EncounterParticipant> EncounterParticipantOptional = encounterParticipantRepository.findByCipId(encounterParticipantDto.getCipId());
			if (!EncounterParticipantOptional.isPresent()) {
				return true;
			} else {
				return false;
			}
		}
		throw new ServiceErrorException(new ApiErrorResponse("404", "cipId must not be empty!"), HttpStatus.NOT_FOUND);
	}

	public List<EncounterParticipantLanguage> addlanguages(Long encounterParticipantId, Long updateByParticipantId, List<Integer> languagesId) {
		List<EncounterParticipantLanguage> EncounterParticipantLanguages = new ArrayList<>();
		if (encounterParticipantId != null && encounterParticipantId != 0) {
			if (languagesId != null && languagesId.size() > 0) {
				for (Integer languadeId : languagesId) {
					EncounterParticipantLanguage encounterParticipantLanguage = new EncounterParticipantLanguage();
					encounterParticipantLanguage.setEncounter_participant_id(encounterParticipantId);
					encounterParticipantLanguage.setLanguage_id(languadeId);
					encounterParticipantLanguage.setCm_participant_id(updateByParticipantId);
					encounterParticipantLanguageRepo.save(encounterParticipantLanguage);
					EncounterParticipantLanguages.add(encounterParticipantLanguage);
				}
			} else {
				throw new ServiceErrorException(
						new ApiErrorResponse("404", "languagesId list is empty but member is saved"),
						HttpStatus.NOT_FOUND);
			}
		} else {
			throw new ServiceErrorException(new ApiErrorResponse("404", "EncounterParticipant entity doesn't exist " + encounterParticipantId),
					HttpStatus.NOT_FOUND);
		}
		return EncounterParticipantLanguages;
	}

	// Update Member and Member_language

	@Override
	public EncounterParticipant updateEncounterParticipant(long encounterParticipantId, EncounterParticipantDto encounterParticipantDto) {
		if (updateCmParticipantIdcheck(encounterParticipantDto)) {
			Optional<EncounterParticipant> optional = encounterParticipantRepository.findById(encounterParticipantId);
			if (optional.isPresent()) {
				EncounterParticipant encounterParticipant = optional.get();
				if (uniqueConstraintsCheck(encounterParticipantDto)) {
					encounterParticipant.setCipId(encounterParticipantDto.getCipId());
					encounterParticipant.setFirstName(encounterParticipantDto.getFirstName());
					encounterParticipant.setLastName(encounterParticipantDto.getLastName());
					encounterParticipant.setActiveStatus(encounterParticipantDto.getActiveStatus());

					List<EncounterParticipantLanguage> languages = UpdateLanguages(encounterParticipantId, encounterParticipantDto.getLanguagesId(),
							encounterParticipantDto.getUpdateByPartcipantId());
					encounterParticipantRepository.save(encounterParticipant);
					return encounterParticipantRepository.getById(encounterParticipantId);
				} else if(!uniqueConstraintsCheck(encounterParticipantDto) && (encounterParticipant.getCipId().equals(encounterParticipantDto.getCipId()))){
					encounterParticipant.setFirstName(encounterParticipantDto.getFirstName());
					encounterParticipant.setLastName(encounterParticipantDto.getLastName());
					encounterParticipant.setActiveStatus(encounterParticipantDto.getActiveStatus());
					List<EncounterParticipantLanguage> languages = UpdateLanguages(encounterParticipantId, encounterParticipantDto.getLanguagesId(),
							encounterParticipantDto.getUpdateByPartcipantId());
					encounterParticipantRepository.save(encounterParticipant);
					return encounterParticipantRepository.getById(encounterParticipantId);
					
				} else {
					throw new ServiceErrorException(new ApiErrorResponse("409", "cipId is already exist"),
							HttpStatus.CONFLICT);
				}
			} else {
				throw new ServiceErrorException(
						new ApiErrorResponse("404", "with the EncounterparticipantId, EncounterParticipant entity is not available"),
						HttpStatus.NOT_FOUND);
			}

		} else {
			throw new ServiceErrorException(
					new ApiErrorResponse("409", "Since Participant doesn't exist, member is not updated "),
					HttpStatus.CONFLICT);
		}
	}

	private List<EncounterParticipantLanguage> UpdateLanguages(long encounterParticipantId, List<Integer> languagesId, long cm_participant_id) {
		List<EncounterParticipantLanguage> ePLanguages = new ArrayList<>();
		List<Long> getUpdatedByParticipantId = new ArrayList<>();
		Optional<EncounterParticipant> optional = encounterParticipantRepository.findById(encounterParticipantId);
		if (optional.isPresent()) {
			if (languagesId != null && languagesId.size() > 0) {
				getUpdatedByParticipantId = encounterParticipantLanguageRepo.getUpdatedByStaffId(encounterParticipantId);
				for (Integer languageId : languagesId) {
					if (findLanguageId(languageId)) {
						EncounterParticipantLanguage languages = encounterParticipantLanguageRepo.findByLanguage_Id(encounterParticipantId, languageId);
						if (languages == null && getUpdatedByParticipantId.contains(cm_participant_id)) {
							EncounterParticipantLanguage encounterParticipantLanguage = new EncounterParticipantLanguage();
							encounterParticipantLanguage.setEncounter_participant_id(encounterParticipantId);
							encounterParticipantLanguage.setLanguage_id(languageId);
							encounterParticipantLanguage.setStatus(encounterParticipantLanguage.getStatus());
							encounterParticipantLanguage.setCm_participant_id(cm_participant_id);
							// encounterParticipantLanguageRepo.save(encounterParticipantLanguage);
							ePLanguages.add(encounterParticipantLanguage);
						} else if (languages == null && !getUpdatedByParticipantId.contains(cm_participant_id)){
							encounterParticipantLanguageRepo.deleteByMemberandStaffId(encounterParticipantId, getUpdatedByParticipantId);
							EncounterParticipantLanguage encounterParticipantLanguage = new EncounterParticipantLanguage();
							encounterParticipantLanguage.setEncounter_participant_id(encounterParticipantId);
							encounterParticipantLanguage.setLanguage_id(languageId);
							encounterParticipantLanguage.setStatus(encounterParticipantLanguage.getStatus());
							encounterParticipantLanguage.setCm_participant_id(cm_participant_id);
							// encounterParticipantLanguageRepo.save(encounterParticipantLanguage);
							ePLanguages.add(encounterParticipantLanguage);
						} else if (languages != null && !getUpdatedByParticipantId.contains(cm_participant_id)){
							encounterParticipantLanguageRepo.deleteByMemberandStaffId(encounterParticipantId, getUpdatedByParticipantId);
							EncounterParticipantLanguage encounterParticipantLanguage = new EncounterParticipantLanguage();
							encounterParticipantLanguage.setEncounter_participant_id(encounterParticipantId);
							encounterParticipantLanguage.setLanguage_id(languageId);
							encounterParticipantLanguage.setStatus(encounterParticipantLanguage.getStatus());
							encounterParticipantLanguage.setCm_participant_id(cm_participant_id);
							// encounterParticipantLanguageRepo.save(encounterParticipantLanguage);
							ePLanguages.add(encounterParticipantLanguage);
						}
					} else {
						throw new ServiceErrorException(
								new ApiErrorResponse("404", "Language _id " + languageId + " is not found"),
								HttpStatus.NOT_FOUND);
					}

				}
				encounterParticipantLanguageRepo.saveAll(ePLanguages);
				List<Integer> dbLanguagesId = encounterParticipantLanguageRepo.getLanguageIds(encounterParticipantId);

				deleteOldlanguagesById(dbLanguagesId, languagesId, encounterParticipantId);
			}

			return ePLanguages;
		}

		throw new ServiceErrorException(
				new ApiErrorResponse("404", "Participant entity is not available and update failed on Staff_language entity"),
				HttpStatus.NOT_FOUND);
	}

	private void deleteOldlanguagesById(List<Integer> dbLanguagesId, List<Integer> languagesId, long encounterParticipantId) {
		List<Integer> deleteIds = null;
		if (dbLanguagesId != null && dbLanguagesId.size() > 0 && languagesId != null && languagesId.size() > 0) {
			deleteIds = dbLanguagesId.stream().filter(e -> !languagesId.contains(e)).collect(Collectors.toList());
		}
		if (deleteIds != null) {
			encounterParticipantLanguageRepo.deleteByLanguageList(encounterParticipantId, deleteIds);
		}
	}

	private boolean findLanguageId(Integer languageId) {
		Optional<Language> language = languageRepository.findById(languageId);
		if (language.isPresent()) {
			return true;
		}
		return false;
	}

}
