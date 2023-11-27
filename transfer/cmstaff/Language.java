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
@Table(name = "language")
public class Language {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "language_id", nullable = false, unique = true)
	private int languageId;

	@Column(name = "lang_name")
	private String languageName;

	@Enumerated(EnumType.STRING)
	@Column(name = "lang_status")
	private StatusEnum languageStatus = StatusEnum.Active;

	public enum StatusEnum {
		Active, Inactive
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public StatusEnum getLanguageStatus() {
		return languageStatus;
	}

	public void setLanguageStatus(StatusEnum languageStatus) {
		this.languageStatus = languageStatus;
	}

	public Language() {
		super();
	}

	@Override
	public String toString() {
		return "Language [language_id=" + languageId + ", languageName=" + languageName + ", languageStatus="
				+ languageStatus + "]";
	}

}
