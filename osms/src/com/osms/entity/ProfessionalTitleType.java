package com.osms.entity;

import java.io.Serializable;
/**
 * professional title type of teacher
 * @author Administrator
 *
 */
public class ProfessionalTitleType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int professionalTitleTypeId=0;//id
	private String cName=null;//Chinese name
	private String eName=null;//English name
	private int status=0;//current information status
	
	
	public ProfessionalTitleType()
	{
		
	}
	
	
	public ProfessionalTitleType(int professionalTitleTypeId, String cName, String eName, int status) {
		this.professionalTitleTypeId = professionalTitleTypeId;
		this.cName = cName;
		this.eName = eName;
		this.status = status;
	}


	public int getProfessionalTitleTypeId() {
		return professionalTitleTypeId;
	}


	public void setProfessionalTitleTypeId(int professionalTitleTypeId) {
		this.professionalTitleTypeId = professionalTitleTypeId;
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
		return "ProfessionalTitleType [professionalTitleTypeId=" + professionalTitleTypeId + ", cName=" + cName
				+ ", eName=" + eName + ", status=" + status + "]";
	}
	
}
