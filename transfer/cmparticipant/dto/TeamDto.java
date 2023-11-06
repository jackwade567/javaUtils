package com.bcbsfl.ccn.cmparticipant.dto;

public class TeamDto {
	private int teamId;
	private String teamName;
	private String teamStatus;

	public enum teamStatus {
		Active, Inactive
	}

	private String lob;

	public enum lob {
		Medicare, Commercial
	}


	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamStatus() {
		return teamStatus;
	}

	public void setTeamStatus(String teamStatus) {
		this.teamStatus = teamStatus;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

}
