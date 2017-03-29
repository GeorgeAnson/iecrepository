package com.osms.entity;

import java.io.Serializable;
/**
 * academy config table
 * @author Administrator
 *
 */
public class Academy implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int academyId=0;//ID
	private String cName=null;//Chinese name
	private String eName=null;//English name
	private int status=0;//current information status
	
	public Academy()
	{
		
	}

	public Academy(int academyId, String cName, String eName, int status) {
		this.academyId = academyId;
		this.cName = cName;
		this.eName = eName;
		this.status = status;
	}

	public int getAcademyId() {
		return academyId;
	}

	public void setAcademyId(int academyId) {
		this.academyId = academyId;
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
		return "Academy [academyId=" + academyId + ", cName=" + cName + ", eName=" + eName + ", status=" + status + "]";
	}
}
