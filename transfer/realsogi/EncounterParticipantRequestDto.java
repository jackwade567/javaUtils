package com.bcbsfl.ccn.realsogi.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EncounterParticipantRequestDto {

	@NotBlank(message = "cipId cannot be empty")
	@NotNull(message = "cipId cannot be null")
	@JsonProperty("memberCipId")
	private String memberCipId;

	@NotBlank(message = "memberfirstName cannot be empty")
	@NotNull(message = "memberfirstName cannot be null")
	@JsonProperty("memberFirstName")
	private String memberFirstName;

	@NotBlank(message = "memberlastName cannot be empty")
	@NotNull(message = "memberlastName cannot be null")
	@JsonProperty("memberLastName")
	private String memberLastName;

	@JsonProperty("racf")
	private String racf;

	@Min(value = 1, message = "member_party_id should not be empty or null or zero or negative")
	@JsonProperty("memberPartyId")
	private long memberPartyId;

	@NotBlank(message = "activeStatus cannot be empty")
	@NotNull(message = "activeStatus cannot be null")
	@JsonProperty("activeStatus")
	private String activeStatus;

	@JsonProperty("updateByParticipantId")
	private Long updateByPartcipantId;

	@JsonProperty("raceId")
	private List<Integer> raceId = new ArrayList<>();

	@JsonProperty("ethnicityId")
	private Integer ethnicityId;

	@JsonProperty("spokenLanguage")
	private List<Integer> spokenLanguages = new ArrayList<>();

	@JsonProperty("writtenLanguage")
	private List<Integer> writtenLanguages = new ArrayList<>();

	@JsonProperty("otherRace")
	private String race;

	@JsonProperty("otherSpokenLanguage")
	private String otherSpokenLang;

	@JsonProperty("otherWrittenLanguage")
	private String otherWrittenLang;

	public String getMemberCipId() {
		return memberCipId;
	}

	public void setMemberCipId(String memberCipId) {
		this.memberCipId = memberCipId;
	}

	public String getMemberFirstName() {
		return memberFirstName;
	}

	public void setMemberFirstName(String memberFirstName) {
		this.memberFirstName = memberFirstName;
	}

	public String getMemberLastName() {
		return memberLastName;
	}

	public void setMemberLastName(String memberLastName) {
		this.memberLastName = memberLastName;
	}

	public String getRacf() {
		return racf;
	}

	public void setRacf(String racf) {
		this.racf = racf;
	}

	public long getMemberPartyId() {
		return memberPartyId;
	}

	public void setMemberPartyId(long memberPartyId) {
		this.memberPartyId = memberPartyId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Long getUpdateByPartcipantId() {
		return updateByPartcipantId;
	}

	public void setUpdateByPartcipantId(Long updateByPartcipantId) {
		this.updateByPartcipantId = updateByPartcipantId;
	}

	public List<Integer> getRaceId() {
		return raceId;
	}

	public void setRaceId(List<Integer> raceId) {
		this.raceId = raceId;
	}

	public Integer getEthnicityId() {
		return ethnicityId;
	}

	public void setEthnicityId(Integer ethnicityId) {
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

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getOtherSpokenLang() {
		return otherSpokenLang;
	}

	public void setOtherSpokenLang(String otherSpokenLang) {
		this.otherSpokenLang = otherSpokenLang;
	}

	public String getOtherWrittenLang() {
		return otherWrittenLang;
	}

	public void setOtherWrittenLang(String otherWrittenLang) {
		this.otherWrittenLang = otherWrittenLang;
	}

	@Override
	public String toString() {
		return "EncounterParticipantRequestDto [memberCipId=" + memberCipId + ", memberFirstName=" + memberFirstName
				+ ", memberLastName=" + memberLastName + ", racf=" + racf + ", memberPartyId=" + memberPartyId
				+ ", activeStatus=" + activeStatus + ", updateByPartcipantId=" + updateByPartcipantId + ", raceId="
				+ raceId + ", ethnicityId=" + ethnicityId + ", spokenLanguages=" + spokenLanguages
				+ ", writtenLanguages=" + writtenLanguages + ", race=" + race + ", otherSpokenLang=" + otherSpokenLang
				+ ", otherWrittenLang=" + otherWrittenLang + "]";
	}

}
