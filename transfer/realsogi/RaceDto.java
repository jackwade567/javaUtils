package com.bcbsfl.ccn.realsogi.dto;

import java.time.LocalDateTime;

public class RaceDto {

	private Integer id;
	private String value;
	private String source;
	private String createdByRacf;
	private LocalDateTime createdDateTime;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCreatedByRacf() {
		return createdByRacf;
	}

	public void setCreatedByRacf(String createdByRacf) {
		this.createdByRacf = createdByRacf;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RaceDto [id=" + id + ", value=" + value + ", source=" + source + ", createdByRacf=" + createdByRacf
				+ ", createdDateTime=" + createdDateTime + "]";
	}

}
