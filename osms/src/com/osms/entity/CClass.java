package com.osms.entity;

import java.io.Serializable;
/**
 * class table
 * @author Administrator
 *
 */
public class CClass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int classId=0;//class ID
	private String cName=null;//Chinese name
	private String eName=null;//English name
	private int status=0;//current information status
	
	public CClass() {
		// TODO Auto-generated constructor stub
	}

	public CClass(int classId, String cName, String eName, int status) {
		this.classId = classId;
		this.cName = cName;
		this.eName = eName;
		this.status = status;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
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
		return "CClass [classId=" + classId + ", cName=" + cName + ", eName=" + eName + ", status=" + status + "]";
	}
	
	
}

