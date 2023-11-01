package com.bcbsfl.ccn.cmparticipant.dto;

public class TwoFieldDto {

	private int id;
	private String languageName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public TwoFieldDto(int id, String languageName) {
		this.id = id;
		this.languageName = languageName;
	}

	public TwoFieldDto() {
		super();
	}

}
