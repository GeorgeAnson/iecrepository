package com.osms.entity;

import java.io.Serializable;

/**
 * user type table
 * @author Administrator
 *
 */

public class UserType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int userTypeId=0;//user type id
	private String cName=null;//Chinese name
	private String eName=null;//English name
	private int status=0;//current information status
	
	public UserType()
	{
		
	}

	public UserType(int userTypeId, String cName, String eName, int status) {
		this.userTypeId = userTypeId;
		this.cName = cName;
		this.eName = eName;
		this.status = status;
	}

	public int getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserType [userTypeId=" + userTypeId + ", cName=" + cName + ", eName=" + eName + ", status=" + status
				+ "]";
	}
	
}
