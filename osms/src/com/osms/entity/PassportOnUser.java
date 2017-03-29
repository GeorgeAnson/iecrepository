package com.osms.entity;

import java.io.Serializable;

/**
 * passport on user
 * @author Administrator
 *
 */
public class PassportOnUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id=0;//passport on user id
	private int userId=0;//user id
	private int passportId=0;//passport id
	private String passportPagePath=null;//passport page path
	private int status=0;//current information status
	
	private Users user=null;// user object
	private Passport passport=null;//passport object
	
	public PassportOnUser() {
		// TODO Auto-generated constructor stub
	}

	public PassportOnUser(int id, int userId, int passportId, String passportPagePath, int status, Users user,
			Passport passport) {
		this.id = id;
		this.userId = userId;
		this.passportId = passportId;
		this.passportPagePath = passportPagePath;
		this.status = status;
		this.user = user;
		this.passport = passport;
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

	public int getPassportId() {
		return passportId;
	}

	public void setPassportId(int passportId) {
		this.passportId = passportId;
	}

	public String getPassportPagePath() {
		return passportPagePath;
	}

	public void setPassportPagePath(String passportPagePath) {
		this.passportPagePath = passportPagePath;
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

	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	@Override
	public String toString() {
		return "PassportOnUser [id=" + id + ", userId=" + userId + ", passportId=" + passportId + ", passportPagePath="
				+ passportPagePath + ", status=" + status + ", user=" + user + ", passport=" + passport + "]";
	}
	
}
