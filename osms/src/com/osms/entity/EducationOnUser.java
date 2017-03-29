package com.osms.entity;

import java.io.Serializable;

/**
 * education on user table
 * @author Administrator
 *
 */
public class EducationOnUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id=0;//education on user id
	private int userId=0;//user id
	private int educationTypeId=0;//education type id
	private int isOversea=0;//whether this education information is oversea
	private int status=0;//current information status
	
	private Users user=null;//user object
	private EducationType educationType=null;//education type id
	
	public EducationOnUser() {
		// TODO Auto-generated constructor stub
	}

	public EducationOnUser(int id, int userId, int educationTypeId, int isOversea, int status, Users user,
			EducationType educationType) {
		this.id = id;
		this.userId = userId;
		this.educationTypeId = educationTypeId;
		this.isOversea = isOversea;
		this.status = status;
		this.user = user;
		this.educationType = educationType;
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

	public int getEducationTypeId() {
		return educationTypeId;
	}

	public void setEducationTypeId(int educationTypeId) {
		this.educationTypeId = educationTypeId;
	}

	public int getIsOversea() {
		return isOversea;
	}

	public void setIsOversea(int isOversea) {
		this.isOversea = isOversea;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public EducationType getEducationType() {
		return educationType;
	}

	public void setEducationType(EducationType educationType) {
		this.educationType = educationType;
	}

	@Override
	public String toString() {
		return "EducationOnUser [id=" + id + ", userId=" + userId + ", educationTypeId=" + educationTypeId
				+ ", isOversea=" + isOversea + ", status=" + status + ", user=" + user + ", educationType="
				+ educationType + "]";
	}
	
	
}
