package com.osms.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.osms.bean.SearchForm;

/**
 * user table
 * @author Administrator
 *
 */
public class Users implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int userId=0;//user' sid
	private String fullName=null;//user name
	private int gender=0;//user's gender
	private int userTypeId=0;//user's id which about user's type
	private String phone=null;//user's phone number while in China
	private String email=null;//user's email
	private String password=null;//user's password
	private Date registerDate=null;//user's account create date
	private int status=0;//current information status
	
	private UserType userType=null;//user type object
	
	private EducationOnUser educationOnUser=null;//educationOnUser object
	private IdentityOnUser identityOnUser=null;//EducationOnUser object
	private List<FundingOnUser> fundingOnUsers=null;//ArrayList of FundingOnUser,not only, maybe contains some type of them
	private VisaOnUser visaOnUser=null;//VisaOnUser object
	private ApartmentRoll apartmentRoll=null;//ApartmentRoll object
	private List<AMCOnUser> amcOnUsers=null;//AMCOnUser object,not only as to teacher,although to student it is
	private SchoolRoll schoolRoll=null;//SchoolRoll object
	private List<PassportOnUser> passportOnUsers=null;//PassportOnUser object
	private List<Passport> passports=null;
	private AMCOnUser amcOnUser=null;
	private List<Payment> payments=null;
	private SearchForm searchForm=null;
	
	public Users() {
		// TODO Auto-generated constructor stub
	}

	public Users(int userId, String fullName, int gender, int userTypeId, String phone, String email, String password,
			Date registerDate, int status, UserType userType) {
		this.userId = userId;
		this.fullName = fullName;
		this.gender = gender;
		this.userTypeId = userTypeId;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.registerDate = registerDate;
		this.status = status;
		this.userType = userType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	
	public EducationOnUser getEducationOnUser() {
		return educationOnUser;
	}

	public void setEducationOnUser(EducationOnUser educationOnUser) {
		this.educationOnUser = educationOnUser;
	}

	public IdentityOnUser getIdentityOnUser() {
		return identityOnUser;
	}

	public void setIdentityOnUser(IdentityOnUser identityOnUser) {
		this.identityOnUser = identityOnUser;
	}

	public List<FundingOnUser> getFundingOnUsers() {
		return fundingOnUsers;
	}

	public void setFundingOnUsers(List<FundingOnUser> fundingOnUsers) {
		this.fundingOnUsers = fundingOnUsers;
	}

	public VisaOnUser getVisaOnUser() {
		return visaOnUser;
	}

	public void setVisaOnUser(VisaOnUser visaOnUser) {
		this.visaOnUser = visaOnUser;
	}

	public ApartmentRoll getApartmentRoll() {
		return apartmentRoll;
	}

	public void setApartmentRoll(ApartmentRoll apartmentRoll) {
		this.apartmentRoll = apartmentRoll;
	}

	public List<AMCOnUser> getAmcOnUsers() {
		return amcOnUsers;
	}

	public void setAmcOnUsers(List<AMCOnUser> amcOnUsers) {
		this.amcOnUsers = amcOnUsers;
	}

	public SchoolRoll getSchoolRoll() {
		return schoolRoll;
	}

	public void setSchoolRoll(SchoolRoll schoolRoll) {
		this.schoolRoll = schoolRoll;
	}

	

	public List<PassportOnUser> getPassportOnUsers() {
		return passportOnUsers;
	}

	public void setPassportOnUsers(List<PassportOnUser> passportOnUsers) {
		this.passportOnUsers = passportOnUsers;
	}
	

	public List<Passport> getPassports() {
		return passports;
	}

	public void setPassports(List<Passport> passports) {
		this.passports = passports;
	}

	public AMCOnUser getAmcOnUser() {
		return amcOnUser;
	}

	public void setAmcOnUser(AMCOnUser amcOnUser) {
		this.amcOnUser = amcOnUser;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public SearchForm getSearchForm() {
		return searchForm;
	}

	public void setSearchForm(SearchForm searchForm) {
		this.searchForm = searchForm;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", fullName=" + fullName + ", gender=" + gender + ", userTypeId="
				+ userTypeId + ", phone=" + phone + ", email=" + email + ", password=" + password + ", registerDate="
				+ registerDate + ", status=" + status + ", userType=" + userType + ", educationOnUser="
				+ educationOnUser + ", identityOnUser=" + identityOnUser + ", fundingOnUsers=" + fundingOnUsers
				+ ", visaOnUser=" + visaOnUser + ", apartmentRoll=" + apartmentRoll + ", amcOnUsers=" + amcOnUsers
				+ ", schoolRoll=" + schoolRoll + ", passportOnUsers=" + passportOnUsers + ", passports=" + passports
				+ ", amcOnUser=" + amcOnUser + ", payments=" + payments + ", searchForm=" + searchForm + "]";
	}
	
}
