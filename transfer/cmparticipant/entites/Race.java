package com.bcbsfl.ccn.cmparticipant.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "race")
public class Race {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "race_id", nullable = false, unique = true)
	private int raceId;

	@Column(name = "race_name")
	private String raceName;

	@Column(name = "codeset")
	private String codeset = "CCN_SYSTEM";

	public int getEthnicityId() {
		return raceId;
	}

	public int getRaceId() {
		return raceId;
	}

	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}

	public String getRaceName() {
		return raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	public String getCodeset() {
		return codeset;
	}

	public void setCodeset(String codeset) {
		this.codeset = codeset;
	}

	@Override
	public String toString() {
		return "Race [raceId=" + raceId + ", raceName=" + raceName + ", codeset=" + codeset + "]";
	}

}
