package com.bcbsfl.ccn.cmparticipant.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncounterParticipantResponseDto {

	@JsonProperty("memberCipId")
	private String memberCipId;
	@JsonProperty("memberFirstName")
	private String memberFirstName;
	@JsonProperty("memberLastName")
	private String memberLastName;
	@JsonProperty("memberPartyId")
	private Long memberPartyId;
	@JsonProperty("race")
	private RaceDto raceDto;
	@JsonProperty("ethinicity")
	private EthinicityDto ethinicityDto;
	@JsonProperty("SpokenLanguages")
	private List<MemberSpokenLanguageDto> memberSpokenLanguageDtos = new ArrayList<>();
	@JsonProperty("WrittenLanguages")
	private List<MemberWrittenLanguageDto> memberWrittenLanguageDtos = new ArrayList<>();

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

	public Long getMemberPartyId() {
		return memberPartyId;
	}

	public void setMemberPartyId(Long memberPartyId) {
		this.memberPartyId = memberPartyId;
	}

	public RaceDto getRaceDto() {
		return raceDto;
	}

	public void setRaceDto(RaceDto raceDto) {
		this.raceDto = raceDto;
	}

	public EthinicityDto getEthinicityDto() {
		return ethinicityDto;
	}

	public void setEthinicityDto(EthinicityDto ethinicityDto) {
		this.ethinicityDto = ethinicityDto;
	}

	public List<MemberSpokenLanguageDto> getMemberSpokenLanguageDtos() {
		return memberSpokenLanguageDtos;
	}

	public void setMemberSpokenLanguageDtos(List<MemberSpokenLanguageDto> memberSpokenLanguageDtos) {
		this.memberSpokenLanguageDtos = memberSpokenLanguageDtos;
	}

	public List<MemberWrittenLanguageDto> getMemberWrittenLanguageDtos() {
		return memberWrittenLanguageDtos;
	}

	public void setMemberWrittenLanguageDtos(List<MemberWrittenLanguageDto> memberWrittenLanguageDtos) {
		this.memberWrittenLanguageDtos = memberWrittenLanguageDtos;
	}

	@Override
	public String toString() {
		return "EncounterParticipantResponseDto [memberCipId=" + memberCipId + ", memberFirstName=" + memberFirstName
				+ ", memberLastName=" + memberLastName + ", memberPartyId=" + memberPartyId + ", raceDto=" + raceDto
				+ ", ethinicityDto=" + ethinicityDto + ", memberSpokenLanguageDtos=" + memberSpokenLanguageDtos
				+ ", memberWrittenLanguageDtos=" + memberWrittenLanguageDtos + "]";
	}

}
