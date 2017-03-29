package com.osms.entity;

import java.io.Serializable;
/**
 * nationality table
 * @author Administrator
 *
 */
public class Nationality implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int nationalityId=0;//nationality id
	private String cName=null;//Chinese name
	private String eName=null;//English name
	private int status=0;//current information status
	
	public Nationality()
	{
		
	}

	public Nationality(int nationalityId, String cName, String eName, int status) {
		this.nationalityId = nationalityId;
		this.cName = cName;
		this.eName = eName;
		this.status = status;
	}

	public int getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(int nationalityId) {
		this.nationalityId = nationalityId;
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
		return "Nationality [nationalityId=" + nationalityId + ", cName=" + cName + ", eName=" + eName + ", status="
				+ status + "]";
	}
	
}
