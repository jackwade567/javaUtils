package com.bcbsfl.ccn.cmparticipant.dto;

public class ParticipantSearchCriteria {
    private String teamNameFilter;
    private String roleNameFilter;
    private String managerNameFilter;
    private String languageFilter;
    private String sortColumn;
    private String sortDirection;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer participantId;
    private Integer teamId;
    private Integer roleId;
    private Integer managerId;
    private Integer languageId;
	public String getTeamNameFilter() {
		return teamNameFilter;
	}
	public void setTeamNameFilter(String teamNameFilter) {
		this.teamNameFilter = teamNameFilter;
	}
	public String getRoleNameFilter() {
		return roleNameFilter;
	}
	public void setRoleNameFilter(String roleNameFilter) {
		this.roleNameFilter = roleNameFilter;
	}
	public String getManagerNameFilter() {
		return managerNameFilter;
	}
	public void setManagerNameFilter(String managerNameFilter) {
		this.managerNameFilter = managerNameFilter;
	}
	public String getLanguageFilter() {
		return languageFilter;
	}
	public void setLanguageFilter(String languageFilter) {
		this.languageFilter = languageFilter;
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getParticipantId() {
		return participantId;
	}
	public void setParticipantId(Integer participantId) {
		this.participantId = participantId;
	}
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getManagerId() {
		return managerId;
	}
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
	public Integer getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

    
    
    
}
