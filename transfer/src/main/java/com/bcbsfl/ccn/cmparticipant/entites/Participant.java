package com.bcbsfl.ccn.cmparticipant.entites;

import com.bcbsfl.ccn.cmparticipant.dto.CustomParticipantDto;
import com.bcbsfl.ccn.cmparticipant.dto.TwoFieldDto;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
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
	private Long cm_participant_id;

	@Column(name = "racf", nullable = false, unique = true)
	private String racf;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "team_id")
	private int team_id;

	@Column(name = "role_id")
	private int role_id;

	@Column(name = "manager_id")
	private int manager_id;

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

	public Long getCm_participant_id() {
		return cm_participant_id;
	}

	public void setCm_participant_id(Long cm_participant_id) {
		this.cm_participant_id = cm_participant_id;
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

	public int getTeam_id() {
		return team_id;
	}

	public void setTeam_id(int team_id) {
		this.team_id = team_id;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public int getManager_id() {
		return manager_id;
	}

	public void setManager_id(int manager_id) {
		this.manager_id = manager_id;
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
		return "Participant [cm_participant_id=" + cm_participant_id + ", racf=" + racf + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", team_id=" + team_id + ", role_id=" + role_id + ", manager_id="
				+ manager_id + ", lob=" + lob + ", activeStatus=" + activeStatus + "]";
	}

}
