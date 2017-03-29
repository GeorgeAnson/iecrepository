package com.osms.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * identity on user table
 * @author Administrator
 *
 */
public class IdentityOnUser implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id=0;//id
	private int userId=0;//user id
	private int nationalityId=0;//nationality id
	private Date birthday=null;//birthday
	private String birthplace=null;//birthplace
	private String homeAddress=null;//home address
	private String phone=null;//Oversea phone number
	private int isMarried=0;//is married
	private int status=0;//current information status
	
	private Nationality nationality=null;
	
	private EducationOnUser educationOnUser=null;
	
	public IdentityOnUser() {
		// TODO Auto-generated constructor stub
	}

	public IdentityOnUser(int id, int userId, int nationalityId, Date birthday, String birthplace, String homeAddress,
			String phone, int isMarried, int status) {
		this.id = id;
		this.userId = userId;
		this.nationalityId = nationalityId;
		this.birthday = birthday;
		this.birthplace = birthplace;
		this.homeAddress = homeAddress;
		this.phone = phone;
		this.isMarried = isMarried;
		this.status = status;
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

	public int getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(int nationalityId) {
		this.nationalityId = nationalityId;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(int isMarried) {
		this.isMarried = isMarried;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Nationality getNationality() {
		return nationality;
	}

	public void setNationality(Nationality nationality) {
		this.nationality = nationality;
	}

	
	
	public EducationOnUser getEducationOnUser() {
		return educationOnUser;
	}

	public void setEducationOnUser(EducationOnUser educationOnUser) {
		this.educationOnUser = educationOnUser;
	}

	@Override
	public String toString() {
		return "IdentityOnUser [id=" + id + ", userId=" + userId + ", nationalityId=" + nationalityId + ", birthday="
				+ birthday + ", birthplace=" + birthplace + ", homeAddress=" + homeAddress + ", phone=" + phone
				+ ", isMarried=" + isMarried + ", status=" + status + ", nationality=" + nationality
				+ ", educationOnUser=" + educationOnUser + "]";
	}
	
	
	
}
