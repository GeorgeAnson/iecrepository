package com.osms.entity;

import java.io.Serializable;

public class StudentType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int studentTypeId=0;
	private String studentTypecName=null;
	private String studentTypeeName=null;
	private int studentTypeStatus=0;
	
	public StudentType() {
		// TODO Auto-generated constructor stub
	}

	public StudentType(int studentTypeId, String studentTypecName, String studentTypeeName, int studentTypeStatus) {
		this.studentTypeId = studentTypeId;
		this.studentTypecName = studentTypecName;
		this.studentTypeeName = studentTypeeName;
		this.studentTypeStatus = studentTypeStatus;
	}

	public int getStudentTypeId() {
		return studentTypeId;
	}

	public void setStudentTypeId(int studentTypeId) {
		this.studentTypeId = studentTypeId;
	}

	public String getStudentTypecName() {
		return studentTypecName;
	}

	public void setStudentTypecName(String studentTypecName) {
		this.studentTypecName = studentTypecName;
	}

	public String getStudentTypeeName() {
		return studentTypeeName;
	}

	public void setStudentTypeeName(String studentTypeeName) {
		this.studentTypeeName = studentTypeeName;
	}

	public int getStudentTypeStatus() {
		return studentTypeStatus;
	}

	public void setStudentTypeStatus(int studentTypeStatus) {
		this.studentTypeStatus = studentTypeStatus;
	}

	@Override
	public String toString() {
		return "StudentType [studentTypeId=" + studentTypeId + ", studentTypecName=" + studentTypecName
				+ ", studentTypeeName=" + studentTypeeName + ", studentTypeStatus=" + studentTypeStatus + "]";
	}
	
	
}
