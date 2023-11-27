package com.bcbsfl.ccn.realsogi.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ethnicity")
public class Ethnicity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ethnicity_id", nullable = false, unique = true)
	private int ethnicityId;

	@Column(name = "ethnicity_name")
	private String ethnicityName;

	@Column(name = "codeset")
	private String codeset = "CCN_SYSTEM";

	
	public int getEthnicityId() {
		return ethnicityId;
	}

	public void setEthnicityId(int ethnicityId) {
		this.ethnicityId = ethnicityId;
	}

	public String getEthnicityName() {
		return ethnicityName;
	}

	public void setEthnicityName(String ethnicityName) {
		this.ethnicityName = ethnicityName;
	}

	public String getCodeset() {
		return codeset;
	}

	public void setCodeset(String codeset) {
		this.codeset = codeset;
	}

	@Override
	public String toString() {
		return "Ethnicity [ethnicity_id=" + ethnicityId + ", ethnicity_name=" + ethnicityName + ", codeset=" + codeset
				+ "]";
	}
}
