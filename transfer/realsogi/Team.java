package com.bcbsfl.ccn.realsogi.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "team")
public class Team {
	@Id
	@Column(name = "team_id")
	private int teamId;

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

	public Team() {

	}

	public Team(int teamId, String teamName, String teamStatus, String lob) {
		super();
		this.teamId = teamId;
		this.teamName = teamName;
		this.teamStatus = teamStatus;
		this.lob = lob;
	}

	@Override
	public String toString() {
		return "Team [teamId=" + teamId + ", teamName=" + teamName + ", teamStatus=" + teamStatus + ", lob=" + lob
				+ "]";
	}

}
