package com.bcbsfl.ccn.cmparticipant.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "team")
public class Team {
	@Id
	@Column(name = "team_id")
	private int team_id;

	@Column(name = "team_name")
	private String teamName;

	@Column(name = "team_status")
	private String teamStatus;

	public enum teamStatus {
		Active, Inactive
	}

	@Column(name = "lob")
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

	public Team() {

	}

	public Team(int team_id, String teamName, String teamStatus, String lob) {
		super();
		this.team_id = team_id;
		this.teamName = teamName;
		this.teamStatus = teamStatus;
		this.lob = lob;
	}

	@Override
	public String toString() {
		return "Team [team_id=" + team_id + ", teamName=" + teamName + ", teamStatus=" + teamStatus + ", lob=" + lob
				+ "]";
	}

}
