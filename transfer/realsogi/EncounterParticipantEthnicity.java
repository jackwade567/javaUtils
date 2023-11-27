package com.bcbsfl.ccn.realsogi.entites;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "encounter_participant_ethnicity")
public class EncounterParticipantEthnicity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "encounter_participant_ethnicity_id", nullable = false, unique = true)
	private Long encounterParticipantEthnicityId;

	@Column(name = "encounter_participant_id")
	private Long encounterParticipantId;

	@Column(name = "ethnicity_id")
	private int ethnicityId;

	@Column(name = "updated_by_cm_participant_id")
	private Long updatedByCmParticipantId;

	@Column(name = "transaction_id", nullable = false, unique = true)
	private UUID uuid = UUID.randomUUID();

	@Column(name = "system_id")
	private String systemId;

	@Column(name = "created_date_time", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime createDateTime;

	@Column(name = "last_update_date_time", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime updateDateTime;

	public Long getEncounterParticipantEthnicityId() {
		return encounterParticipantEthnicityId;
	}

	public void setEncounterParticipantEthnicityId(Long encounterParticipantEthnicityId) {
		this.encounterParticipantEthnicityId = encounterParticipantEthnicityId;
	}

	public Long getEncounterParticipantId() {
		return encounterParticipantId;
	}

	public void setEncounterParticipantId(Long encounterParticipantId) {
		this.encounterParticipantId = encounterParticipantId;
	}

	public int getEthnicityId() {
		return ethnicityId;
	}

	public void setEthnicityId(int ethnicityId) {
		this.ethnicityId = ethnicityId;
	}

	public Long getUpdatedByCmParticipantId() {
		return updatedByCmParticipantId;
	}

	public void setUpdatedByCmParticipantId(Long updatedByCmParticipantId) {
		this.updatedByCmParticipantId = updatedByCmParticipantId;
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
		return "EncounterParticipantEthnicity [encounterParticipantEthnicityId=" + encounterParticipantEthnicityId
				+ ", encounterParticipantId=" + encounterParticipantId + ", ethnicityId=" + ethnicityId
				+ ", updatedByCmParticipantId=" + updatedByCmParticipantId + ", uuid=" + uuid + ", systemId=" + systemId
				+ ", createDateTime=" + createDateTime + ", updateDateTime=" + updateDateTime + "]";
	}
}
