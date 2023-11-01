package com.bcbsfl.ccn.cmparticipant.dto;

public class LanguageDto {
	private int language_id;
	private String languageName;
	private String languageStatus;
	public enum languageStatus {
		Active,Inactive
	}
    private String lob;
	
	public enum lob {
		Medicare,Commercial
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

	public String getLanguageStatus() {
		return languageStatus;
	}

	public void setLanguageStatus(String languageStatus) {
		this.languageStatus = languageStatus;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}
	
	
}
