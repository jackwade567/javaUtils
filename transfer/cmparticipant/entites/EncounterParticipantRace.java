package com.bcbsfl.ccn.cmparticipant.entites;

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
	private long updatedByCmParticipantId;

	@Column(name = "transaction_id", nullable = false, unique = true)
	private UUID uuid = UUID.randomUUID();

	@Column(name = "system_id")
	private String systemId;

	@Column(name = "created_date_time", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private LocalDateTime dateTime;

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

	public long getUpdatedByCmParticipantId() {
		return updatedByCmParticipantId;
	}

	public void setUpdatedByCmParticipantId(long updatedByCmParticipantId) {
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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "EncounterParticipantRace [encounterParticipantRaceId=" + encounterParticipantRaceId
				+ ", encounterParticipantId=" + encounterParticipantId + ", raceId=" + raceId
				+ ", updatedByCmParticipantId=" + updatedByCmParticipantId + ", uuid=" + uuid + ", systemId=" + systemId
				+ ", dateTime=" + dateTime + "]";
	}

}
