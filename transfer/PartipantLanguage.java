package com.bcbsfl.ccn.cmparticipant.entites;

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
@Table(name = "cm_participant_language")
public class PartipantLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cm_participant_language_id", nullable = false, unique = true)
	private int cmParticipantLanguageId;

	@Column(name = "transaction_id", nullable = false, unique = true)
	private UUID uuid = UUID.randomUUID();

	@Column(name = "cm_participant_id")
	private long cmParticipantId;

	@Column(name = "language_id")
	private int languageId;

	public enum ActiveStatus {
		Active, Inactive
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "active_status")
	private ActiveStatus status = ActiveStatus.Active;

	public ActiveStatus getActiveStatus() {
		return status;
	}

	public void setActiveStatus(ActiveStatus status) {
		this.status = status;
	}

	public int getCmParticipantLanguageId() {
		return cmParticipantLanguageId;
	}

	public void setCmParticipantLanguageId(int cmParticipantLanguageId) {
		this.cmParticipantLanguageId = cmParticipantLanguageId;
	}

	public long getCmParticipantId() {
		return cmParticipantId;
	}

	public void setCmParticipantId(long cmParticipantId) {
		this.cmParticipantId = cmParticipantId;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public ActiveStatus getStatus() {
		return status;
	}

	public void setStatus(ActiveStatus status) {
		this.status = status;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "PartipantLanguage [cm_participant_language_id=" + cmParticipantLanguageId + ", uuid=" + uuid
				+ ", cm_participant_id=" + cmParticipantId + ", language_id=" + languageId + ", status=" + status + "]";
	}

}
