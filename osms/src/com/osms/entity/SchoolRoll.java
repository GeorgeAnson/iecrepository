package com.osms.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * study information table
 * @author Administrator
 *
 */
public class SchoolRoll implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id=0;//id
	private int userId=0;//user id
	private int studyPeriodId=0;//study period id
	private int rollStatusTypeId=0;//roll status type id
	private int studentTypeId=0;
	private String place=null;//work or study place
	private String cardNumber=null;//student card number
	private String studentNumber=null;//student number
	private String dormitoryNumber=null;//dormitory number
	private Date comeDate=null;//come date
	private Date leaveDate=null;//leave date
	private int status=0;//current information status
	
	
	private Users user=null;//user object
	private StudyPeriod studyPeriod=null;//study period object
	private RollStatusType rollStatusType=null;//roll status type object
	private StudentType studentType=null;
	
	public SchoolRoll()
	{
		
	}

	public SchoolRoll(int id, int userId, int studyPeriodId, int rollStatusTypeId, int studentTypeId, String place,
			String cardNumber, String studentNumber, String dormitoryNumber, Date comeDate, Date leaveDate, int status,
			Users user, StudyPeriod studyPeriod, RollStatusType rollStatusType, StudentType studentType) {
		this.id = id;
		this.userId = userId;
		this.studyPeriodId = studyPeriodId;
		this.rollStatusTypeId = rollStatusTypeId;
		this.studentTypeId = studentTypeId;
		this.place = place;
		this.cardNumber = cardNumber;
		this.studentNumber = studentNumber;
		this.dormitoryNumber = dormitoryNumber;
		this.comeDate = comeDate;
		this.leaveDate = leaveDate;
		this.status = status;
		this.user = user;
		this.studyPeriod = studyPeriod;
		this.rollStatusType = rollStatusType;
		this.studentType = studentType;
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

	public int getStudyPeriodId() {
		return studyPeriodId;
	}

	public void setStudyPeriodId(int studyPeriodId) {
		this.studyPeriodId = studyPeriodId;
	}

	public int getRollStatusTypeId() {
		return rollStatusTypeId;
	}

	public void setRollStatusTypeId(int rollStatusTypeId) {
		this.rollStatusTypeId = rollStatusTypeId;
	}

	public int getStudentTypeId() {
		return studentTypeId;
	}

	public void setStudentTypeId(int studentTypeId) {
		this.studentTypeId = studentTypeId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getDormitoryNumber() {
		return dormitoryNumber;
	}

	public void setDormitoryNumber(String dormitoryNumber) {
		this.dormitoryNumber = dormitoryNumber;
	}

	public Date getComeDate() {
		return comeDate;
	}

	public void setComeDate(Date comeDate) {
		this.comeDate = comeDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
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

	public StudyPeriod getStudyPeriod() {
		return studyPeriod;
	}

	public void setStudyPeriod(StudyPeriod studyPeriod) {
		this.studyPeriod = studyPeriod;
	}

	public RollStatusType getRollStatusType() {
		return rollStatusType;
	}

	public void setRollStatusType(RollStatusType rollStatusType) {
		this.rollStatusType = rollStatusType;
	}

	public StudentType getStudentType() {
		return studentType;
	}

	public void setStudentType(StudentType studentType) {
		this.studentType = studentType;
	}

	@Override
	public String toString() {
		return "SchoolRoll [id=" + id + ", userId=" + userId + ", studyPeriodId=" + studyPeriodId
				+ ", rollStatusTypeId=" + rollStatusTypeId + ", studentTypeId=" + studentTypeId + ", place=" + place
				+ ", cardNumber=" + cardNumber + ", studentNumber=" + studentNumber + ", dormitoryNumber="
				+ dormitoryNumber + ", comeDate=" + comeDate + ", leaveDate=" + leaveDate + ", status=" + status
				+ ", user=" + user + ", studyPeriod=" + studyPeriod + ", rollStatusType=" + rollStatusType
				+ ", studentType=" + studentType + "]";
	}

}
