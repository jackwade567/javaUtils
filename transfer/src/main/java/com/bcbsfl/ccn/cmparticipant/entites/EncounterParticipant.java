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
@Table(name = "encounter_participant")
public class EncounterParticipant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "encounter_participant_id")
	private long encounter_participant_id;

	@Column(name = "cipid", nullable = false, unique = true)
	private String cipId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	public enum Status {
		Active, Inactive
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "active_status", nullable = false)
	private Status activeStatus = Status.Active;

	public String getCipId() {
		return cipId;
	}

	public void setCipId(String cipId) {
		this.cipId = cipId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Status getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Status activeStatus) {
		this.activeStatus = activeStatus;
	}

	public long getEncounter_participant_id() {
		return encounter_participant_id;
	}

	public void setEncounter_participant_id(long encounter_participant_id) {
		this.encounter_participant_id = encounter_participant_id;
	}

	@Override
	public String toString() {
		return "EncounterParticipant [encounter_participant_id=" + encounter_participant_id + ", cipId=" + cipId
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", activeStatus=" + activeStatus + "]";
	}

}
