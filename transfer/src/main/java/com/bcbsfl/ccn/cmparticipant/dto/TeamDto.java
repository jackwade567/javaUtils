package com.bcbsfl.ccn.cmparticipant.dto;

public class TeamDto {
	private int team_id;
	private String teamName;
	private String teamStatus;

	public enum teamStatus {
		Active, Inactive
	}

	private String lob;

	public enum lob {
		Medicare, Commercial
	}

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
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
