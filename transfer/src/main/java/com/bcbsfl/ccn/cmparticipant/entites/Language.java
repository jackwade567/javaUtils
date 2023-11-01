package com.bcbsfl.ccn.cmparticipant.entites;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "language")
public class Language {
	@Id
	@Column(name = "language_id")
	private int language_id;

	@Column(name = "lang_name")
	private String languageName;

	@Enumerated(EnumType.STRING)
	@Column(name = "lang_status")
	private StatusEnum languageStatus;

	public enum StatusEnum {
		Active, Inactive
	}

	public int getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
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
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Language [language_id=" + language_id + ", languageName=" + languageName + ", languageStatus="
				+ languageStatus + "]";
	}


}
