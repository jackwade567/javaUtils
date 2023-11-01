package com.bcbsfl.ccn.cmparticipant.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Entity
@Table(name = "cm_participant_language")
public class PartipantLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cm_participant_language_id", nullable = false, unique = true)
	private int cm_participant_language_id;

	@Column(name = "cm_participant_id")
	private long cm_participant_id;

	@Column(name = "language_id")
	private int language_id;

	public enum activeStatus {
		Active, Inactive
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "active_status")
	private activeStatus status = activeStatus.Active;

	public int getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
	}

	public activeStatus getActiveStatus() {
		return status;
	}

	public void setActiveStatus(activeStatus status) {
		this.status = status;
	}

	public int getCm_participant_language_id() {
		return cm_participant_language_id;
	}

	public void setCm_participant_language_id(int cm_participant_language_id) {
		this.cm_participant_language_id = cm_participant_language_id;
	}

	public long getCm_participant_id() {
		return cm_participant_id;
	}

	public void setCm_participant_id(long cm_participant_id) {
		this.cm_participant_id = cm_participant_id;
	}

	public activeStatus getStatus() {
		return status;
	}

	public void setStatus(activeStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PartipantLanguage [cm_participant_language_id=" + cm_participant_language_id + ", cm_participant_id="
				+ cm_participant_id + ", language_id=" + language_id + ", status=" + status + "]";
	}

}
