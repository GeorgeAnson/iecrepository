package com.osms.bean;

import java.util.Date;

/**
 * bat export data to excel 
 * @author Administrator
 *
 */
public class Export {
	
	String fullName=null;
	int gender=0;
	int userTypeId=0;
	String gphone=null;
	String wphone=null;
	String email=null;
	Date registerDate=null;
	int status=0;
	String dormitoryNumber=null;
	String scradNumber=null;
	String studentNumber=null;
	Date birthday=null;
	String birthdayplace=null;
	String homeAddress=null;
	String nationality=null;
	
	public Export() {
		// TODO Auto-generated constructor stub
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getGphone() {
		return gphone;
	}

	public void setGphone(String gphone) {
		this.gphone = gphone;
	}

	public String getWphone() {
		return wphone;
	}

	public void setWphone(String wphone) {
		this.wphone = wphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDormitoryNumber() {
		return dormitoryNumber;
	}

	public void setDormitoryNumber(String dormitoryNumber) {
		this.dormitoryNumber = dormitoryNumber;
	}

	public String getScradNumber() {
		return scradNumber;
	}

	public void setScradNumber(String scradNumber) {
		this.scradNumber = scradNumber;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBirthdayplace() {
		return birthdayplace;
	}

	public void setBirthdayplace(String birthdayplace) {
		this.birthdayplace = birthdayplace;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
}
