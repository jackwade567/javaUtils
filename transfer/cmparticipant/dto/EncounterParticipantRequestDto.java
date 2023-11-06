package com.bcbsfl.ccn.cmparticipant.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncounterParticipantRequestDto {

	@NotBlank(message = "cipId cannot be empty")
	@NotNull(message = "cipId cannot be null")
	@JsonProperty("memberCipId")
	private String memberCipId;

	@NotBlank(message = "memberfirstName cannot be empty")
	@NotNull(message = "memberfirstName cannot be null")
	@JsonProperty("memberfirstName")
	private String memberFirstName;

	@NotBlank(message = "memberlastName cannot be empty")
	@NotNull(message = "memberlastName cannot be null")
	@JsonProperty("memberlastName")
	private String memberLastName;

	@NotBlank(message = "racf cannot be empty")
	@NotNull(message = "racf cannot be null")
	@JsonProperty("racf")
	private String racf;

	@Min(value = 1,message = "member_party_id should not be empty or null or zero")
	@JsonProperty("member_party_id")
	private long memberPartyId;

	@NotBlank(message = "activeStatus cannot be empty")
	@NotNull(message = "activeStatus cannot be null")
	@JsonProperty("activeStatus")
	private String activeStatus;

	@Min(value = 1,message = "updateByPartcipantId should not be empty or null or zero")
	@JsonProperty("updateByPartcipantId")
	private long updateByPartcipantId;

	@Min(value = 1,message = "race_id should not be empty or null or zero")
	@JsonProperty("race_id")
	private int raceId;

	@Min(value = 1,message = "ethnicity_id should not be empty or null or zero")
	@JsonProperty("ethnicity_id")
	private int ethnicityId;

	@Size(min = 1, message = "At least one spoken_languages required")
	@JsonProperty("spoken_languages")
	private List<Integer> spokenLanguages = new ArrayList<>();

	@Size(min = 1, message = "At least one written_languages required")
	@JsonProperty("written_languages")
	private List<Integer> writtenLanguages = new ArrayList<>();

	public String getMemberCipId() {
		return memberCipId;
	}

	public void setMemberCipId(String memberCipId) {
		this.memberCipId = memberCipId;
	}

	public String getMemberfirstName() {
		return memberFirstName;
	}

	public void setMemberfirstName(String memberfirstName) {
		this.memberFirstName = memberfirstName;
	}

	public String getMemberlastName() {
		return memberLastName;
	}

	public void setMemberlastName(String memberlastName) {
		this.memberLastName = memberlastName;
	}

	public String getRacf() {
		return racf;
	}

	public void setRacf(String racf) {
		this.racf = racf;
	}

	
	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public long getUpdateByPartcipantId() {
		return updateByPartcipantId;
	}

	public void setUpdateByPartcipantId(long updateByPartcipantId) {
		this.updateByPartcipantId = updateByPartcipantId;
	}




	public long getMemberPartyId() {
		return memberPartyId;
	}

	public void setMemberPartyId(long memberPartyId) {
		this.memberPartyId = memberPartyId;
	}

	public int getRaceId() {
		return raceId;
	}

	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}

	public int getEthnicityId() {
		return ethnicityId;
	}

	public void setEthnicityId(int ethnicityId) {
		this.ethnicityId = ethnicityId;
	}

	public List<Integer> getSpokenLanguages() {
		return spokenLanguages;
	}

	public void setSpokenLanguages(List<Integer> spokenLanguages) {
		this.spokenLanguages = spokenLanguages;
	}

	public List<Integer> getWrittenLanguages() {
		return writtenLanguages;
	}

	public void setWrittenLanguages(List<Integer> writtenLanguages) {
		this.writtenLanguages = writtenLanguages;
	}

	@Override
	public String toString() {
		return "EncounterParticipantDto [memberCipId=" + memberCipId + ", memberfirstName=" + memberFirstName
				+ ", memberlastName=" + memberLastName + ", racf=" + racf + ", member_party_id=" + memberPartyId
				+ ", activeStatus=" + activeStatus + ", updateByPartcipantId=" + updateByPartcipantId + ", race_id="
				+ raceId + ", ethnicity_id=" + ethnicityId + ", spoken_languages=" + spokenLanguages
				+ ", written_languages=" + writtenLanguages + "]";
	}

}
