package com.osms.entity;

import java.io.Serializable;

/**
 * visa on user table
 * @author Administrator
 *
 */
public class VisaOnUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id=0;//visa on user id
	private int userId=0;//user id
	private int visaId=0;//visa id
	private int status=0;//current information status
	private String visaOnUserSchoolYear=null;
	private int visaOnUserTheSemester=0;
	private Users user=null;//user object
	private Visa visa=null;//visa object
	
	public VisaOnUser() {
		// TODO Auto-generated constructor stub
	}

	public VisaOnUser(int id, int userId, int visaId, int status, String visaOnUserSchoolYear,
			int visaOnUserTheSemester, Users user, Visa visa) {
		this.id = id;
		this.userId = userId;
		this.visaId = visaId;
		this.status = status;
		this.visaOnUserSchoolYear = visaOnUserSchoolYear;
		this.visaOnUserTheSemester = visaOnUserTheSemester;
		this.user = user;
		this.visa = visa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getVisaId() {
		return visaId;
	}

	public void setVisaId(int visaId) {
		this.visaId = visaId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVisaOnUserSchoolYear() {
		return visaOnUserSchoolYear;
	}

	public void setVisaOnUserSchoolYear(String visaOnUserSchoolYear) {
		this.visaOnUserSchoolYear = visaOnUserSchoolYear;
	}

	public int getVisaOnUserTheSemester() {
		return visaOnUserTheSemester;
	}

	public void setVisaOnUserTheSemester(int visaOnUserTheSemester) {
		this.visaOnUserTheSemester = visaOnUserTheSemester;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Visa getVisa() {
		return visa;
	}

	public void setVisa(Visa visa) {
		this.visa = visa;
	}

	@Override
	public String toString() {
		return "VisaOnUser [id=" + id + ", userId=" + userId + ", visaId=" + visaId + ", status=" + status
				+ ", visaOnUserSchoolYear=" + visaOnUserSchoolYear + ", visaOnUserTheSemester=" + visaOnUserTheSemester
				+ ", user=" + user + ", visa=" + visa + "]";
	}

	
}

