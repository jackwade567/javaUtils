package com.bcbsfl.ccn.cmparticipant.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "encounter_participant_language")
public class EncounterParticipantLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "encounter_participant_language_id", nullable = false, unique = true)
	private int encounter_participant_language_id;

	@Column(name = "encounter_participant_id")
	private long encounter_participant_id;

	@Column(name = "language_id")
	private int language_id;

	public enum activeStatus {
		Active, Inactive
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "active_status")
	private activeStatus status = activeStatus.Active;

	@Column(name = "updated_by_cm_participant_id")
	private long cm_participant_id;

	public int getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
	}

	public activeStatus getStatus() {
		return status;
	}

	public void setStatus(activeStatus status) {
		this.status = status;
	}

	public int getEncounter_participant_language_id() {
		return encounter_participant_language_id;
	}

	public void setEncounter_participant_language_id(int encounter_participant_language_id) {
		this.encounter_participant_language_id = encounter_participant_language_id;
	}

	public long getEncounter_participant_id() {
		return encounter_participant_id;
	}

	public void setEncounter_participant_id(long encounter_participant_id) {
		this.encounter_participant_id = encounter_participant_id;
	}

	public long getCm_participant_id() {
		return cm_participant_id;
	}

	public void setCm_participant_id(long cm_participant_id) {
		this.cm_participant_id = cm_participant_id;
	}

	@Override
	public String toString() {
		return "EncounterParticipantLanguage [encounter_participant_language_id=" + encounter_participant_language_id
				+ ", encounter_participant_id=" + encounter_participant_id + ", language_id=" + language_id
				+ ", status=" + status + ", cm_participant_id=" + cm_participant_id + "]";
	}

}
