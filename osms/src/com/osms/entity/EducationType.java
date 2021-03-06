package com.osms.entity;

import java.io.Serializable;

/**
 * education type table
 * @author Administrator
 *
 */
public class EducationType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int educationTypeId=0;//education type id
	private String cName=null;//Chinese name
	private String eName=null;//English name
	private int status=0;//current information status
	
	public EducationType()
	{
		
	}

	public EducationType(int educationTypeId, String cName, String eName, int status) {
		this.educationTypeId = educationTypeId;
		this.cName = cName;
		this.eName = eName;
		this.status = status;
	}

	public int getEducationTypeId() {
		return educationTypeId;
	}

	public void setEducationTypeId(int educationTypeId) {
		this.educationTypeId = educationTypeId;
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
		return "EducationType [educationTypeId=" + educationTypeId + ", cName=" + cName + ", eName=" + eName
				+ ", status=" + status + "]";
	}

	
}
