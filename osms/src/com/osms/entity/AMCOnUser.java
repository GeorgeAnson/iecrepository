package com.osms.entity;

import java.io.Serializable;

/**
 * Academy major and class On user
 * @author Administrator
 *
 */
public class AMCOnUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id=0;//ID
	private int userId=0;//user id
	private int academyId=0;//academy id
	private int majorId=0;//major id
	private int classId=0;//class id
	private int status=0;//current information status
	
	private Users user=null;//user object
	private Academy academy=null;//academy object
	private Major major=null;//major object
	private CClass cclass=null;//CClass object
	
	public AMCOnUser() {
		// TODO Auto-generated constructor stub
	}

	public AMCOnUser(int id, int userId, int academyId, int majorId, int classId, int status, Users user,
			Academy academy, Major major, CClass cclass) {
		this.id = id;
		this.userId = userId;
		this.academyId = academyId;
		this.majorId = majorId;
		this.classId = classId;
		this.status = status;
		this.user = user;
		this.academy = academy;
		this.major = major;
		this.cclass = cclass;
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

	public int getAcademyId() {
		return academyId;
	}

	public void setAcademyId(int academyId) {
		this.academyId = academyId;
	}

	public int getMajorId() {
		return majorId;
	}

	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
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

	public Academy getAcademy() {
		return academy;
	}

	public void setAcademy(Academy academy) {
		this.academy = academy;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public CClass getCclass() {
		return cclass;
	}

	public void setCclass(CClass cclass) {
		this.cclass = cclass;
	}

	@Override
	public String toString() {
		return "AMCOnUser [id=" + id + ", userId=" + userId + ", academyId=" + academyId + ", majorId=" + majorId
				+ ", classId=" + classId + ", status=" + status + ", user=" + user + ", academy=" + academy + ", major="
				+ major + ", cclass=" + cclass + "]";
	}
	
	
}
