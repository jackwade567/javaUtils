package com.bcbsfl.ccn.cmparticipant.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@NamedNativeQuery(name = "getCustomParticipantDto", query = "select t1.cm_participant_id as id,t1.racf as racf,t1.lob as lob,t1.firstName as fName,t1.lastName as lName,t2.teamName as teamName,t3.roleName as roleName,t4.manager_firstname as manFName,t4.manager_lastname as manLName from Participant t1 join Team t2 on t1.team_id = t2.team_id join Role t3 on t1.role_id = t3.role_id join Manager t4 on t1.manager_id = t4.manager_id where t1.cm_participant_id :cm_participant_id", resultSetMapping = "Mapping.CustomParticipantDto")
//@SqlResultSetMapping(name = "Mapping.CustomParticipantDto", classes = @ConstructorResult(targetClass = CustomParticipantDto.class, columns = { @ColumnResult(name = "id"), @ColumnResult(name = "racf"),@ColumnResult(name = "lob"),@ColumnResult(name = "fName"),@ColumnResult(name = "lName"),@ColumnResult(name = "teamName"),@ColumnResult(name = "roleName"),@ColumnResult(name = "manFName"),@ColumnResult(name = "manLName")}) )

//@NamedNativeQuery(name = "getTwoField", query = "select t1.cm_participant_id as id,t1.racf as racf from Participant t1 where t1.cm_participant_id :cm_participant_id", resultSetMapping = "Mapping.TwoFieldDto")
//@SqlResultSetMapping(name = "Mapping.TwoFieldDto", classes = @ConstructorResult(targetClass = TwoFieldDto.class, columns = { @ColumnResult(name = "id",type = long.class), @ColumnResult(name = "racf",type = String.class)}) )

@Entity
@Table(name = "cm_participant")
public class Participant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cm_participant_id", nullable = false, unique = true)
	private Long cmParticipantId;

	@Column(name = "racf", nullable = false, unique = true)
	private String racf;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "team_id")
	private int teamId;

	@Column(name = "role_id")
	private int roleId;

	@Column(name = "manager_id")
	private int managerId;

	@Enumerated(EnumType.STRING)
	@Column(name = "lob")
	private lob_enum lob;

	@Enumerated(EnumType.STRING)
	@Column(name = "active_status")
	private status_enum activeStatus;

	public enum status_enum {
		Active, Inactive

	}

	public enum lob_enum {
		Medicare, Commercial

	}



	public Long getCmParticipantId() {
		return cmParticipantId;
	}

	public void setCmParticipantId(Long cmParticipantId) {
		this.cmParticipantId = cmParticipantId;
	}

	public String getRacf() {
		return racf;
	}

	public void setRacf(String racf) {
		this.racf = racf;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public lob_enum getLob() {
		return lob;
	}

	public void setLob(lob_enum lob) {
		this.lob = lob;
	}

	public status_enum getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(status_enum activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Override
	public String toString() {
		return "Participant [cm_participant_id=" + cmParticipantId + ", racf=" + racf + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", team_id=" + teamId + ", role_id=" + roleId + ", manager_id="
				+ managerId + ", lob=" + lob + ", activeStatus=" + activeStatus + "]";
	}

}
