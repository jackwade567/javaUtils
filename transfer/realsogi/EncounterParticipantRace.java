package com.bcbsfl.ccn.realsogi.entites;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 */
/**
 * 
 */
@Entity
@Table(name = "encounter_participant_race")
public class EncounterParticipantRace {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "encounter_participant_race_id", nullable = false, unique = true)
	private long encounterParticipantRaceId;

	@Column(name = "encounter_participant_id")
	private long encounterParticipantId;

	@Column(name = "race_id")
	private int raceId;

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

	public long getEncounterParticipantRaceId() {
		return encounterParticipantRaceId;
	}

	public void setEncounterParticipantRaceId(long encounterParticipantRaceId) {
		this.encounterParticipantRaceId = encounterParticipantRaceId;
	}

	public long getEncounterParticipantId() {
		return encounterParticipantId;
	}

	public void setEncounterParticipantId(long encounterParticipantId) {
		this.encounterParticipantId = encounterParticipantId;
	}

	public int getRaceId() {
		return raceId;
	}

	public void setRaceId(int raceId) {
		this.raceId = raceId;
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
		return "EncounterParticipantRace [encounterParticipantRaceId=" + encounterParticipantRaceId
				+ ", encounterParticipantId=" + encounterParticipantId + ", raceId=" + raceId
				+ ", updatedByCmParticipantId=" + updatedByCmParticipantId + ", uuid=" + uuid + ", systemId=" + systemId
				+ ", createDateTime=" + createDateTime + ", updateDateTime=" + updateDateTime + "]";
	}
}
