package com.bcbsfl.ccn.cmparticipant.dto;

public class LanguageDto {
	private int languageId;
	private String languageName;
	private String languageStatus;
	public enum languageStatus {
		Active,Inactive
	}
    private String lob;
	
	public enum lob {
		Medicare,Commercial
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
