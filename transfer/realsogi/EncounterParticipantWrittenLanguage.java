package com.bcbsfl.ccn.realsogi.entites;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "encounter_participant_written_language")
public class EncounterParticipantWrittenLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "encounter_participant_written_language_id", nullable = false, unique = true)
	private long encounterParticipantWrittenLanguageId;

	@Column(name = "encounter_participant_id")
	private long encounterParticipantId;

	@Column(name = "language_id")
	private int languageId;

	public enum activeStatus {
		Active, Inactive
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "active_status")
	private activeStatus status = activeStatus.Active;

	@Column(name = "updated_by_cm_participant_id")
	private Long cmParticipantId;

	@Column(name = "transaction_id", nullable = false, unique = true)
	private UUID uuid = UUID.randomUUID();

	@Column(name = "system_id")
	private String systemId;

	@Column(name = "created_date_time", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime createDateTime;

	@Column(name = "last_update_date_time", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime updateDateTime;

	public long getEncounterParticipantWrittenLanguageId() {
		return encounterParticipantWrittenLanguageId;
	}

	public void setEncounterParticipantWrittenLanguageId(long encounterParticipantWrittenLanguageId) {
		this.encounterParticipantWrittenLanguageId = encounterParticipantWrittenLanguageId;
	}

	public long getEncounterParticipantId() {
		return encounterParticipantId;
	}

	public void setEncounterParticipantId(long encounterParticipantId) {
		this.encounterParticipantId = encounterParticipantId;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public Long getCmParticipantId() {
		return cmParticipantId;
	}

	public void setCmParticipantId(Long cmParticipantId) {
		this.cmParticipantId = cmParticipantId;
	}

	public activeStatus getStatus() {
		return status;
	}

	public void setStatus(activeStatus status) {
		this.status = status;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	@Override
	public String toString() {
		return "EncounterParticipantWrittenLanguage [encounterParticipantWrittenLanguageId="
				+ encounterParticipantWrittenLanguageId + ", encounterParticipantId=" + encounterParticipantId
				+ ", languageId=" + languageId + ", status=" + status + ", cmParticipantId=" + cmParticipantId
				+ ", uuid=" + uuid + ", systemId=" + systemId + ", createDateTime=" + createDateTime
				+ ", updateDateTime=" + updateDateTime + "]";
	}

}
