package com.osms.entity;

import java.io.Serializable;
/**
 * roll status type table
 * @author Administrator
 *
 */
public class RollStatusType implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int rollStatusTypeId=0;//roll status type id
	private String cName=null;//Chinese name
	private String eName=null;//English name
	private int status=0;//current information status
	
	public RollStatusType()
	{
		
	}

	public RollStatusType(int rollStatusTypeId, String cName, String eName, int status) {
		this.rollStatusTypeId = rollStatusTypeId;
		this.cName = cName;
		this.eName = eName;
		this.status = status;
	}

	public int getRollStatusTypeId() {
		return rollStatusTypeId;
	}

	public void setRollStatusTypeId(int rollStatusTypeId) {
		this.rollStatusTypeId = rollStatusTypeId;
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
		return "RollStatusType [rollStatusTypeId=" + rollStatusTypeId + ", cName=" + cName + ", eName=" + eName
				+ ", status=" + status + "]";
	}

}
