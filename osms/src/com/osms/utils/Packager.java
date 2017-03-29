package com.osms.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.entity.ApartmentRoll;
import com.osms.entity.CClass;
import com.osms.entity.EducationOnUser;
import com.osms.entity.EducationType;
import com.osms.entity.FundingOnUser;
import com.osms.entity.IdentityOnUser;
import com.osms.entity.FundingType;
import com.osms.entity.Guarantee;
import com.osms.entity.Major;
import com.osms.entity.Nationality;
import com.osms.entity.Notice;
import com.osms.entity.NoticeType;
import com.osms.entity.Passport;
import com.osms.entity.PassportOnUser;
import com.osms.entity.Payment;
import com.osms.entity.PaymentType;
import com.osms.entity.ProfessionalTitleType;
import com.osms.entity.SchoolRoll;
import com.osms.entity.StudentType;
import com.osms.entity.RollStatusType;
import com.osms.entity.StudyPeriod;
import com.osms.entity.UserType;
import com.osms.entity.Users;
import com.osms.entity.Visa;
import com.osms.entity.VisaOnUser;

public class Packager {

	
	
	/**
	 * pack user type data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static UserType PackagerUserType(ResultSet rs) throws SQLException
	{
		UserType userType=new UserType();
		userType.setUserTypeId(rs.getInt("userTypeId"));
		userType.setcName(rs.getString("userTypecName"));
		userType.seteName(rs.getString("userTypeeName"));
		userType.setStatus(rs.getInt("userTypeStatus"));
		return userType;
	}
	
	
	/**
	 * pack Users table data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Users PackagerUser(ResultSet rs) throws SQLException
	{
		Users user=new Users();
		user.setUserId(rs.getInt("userId"));
		user.setFullName(rs.getString("fullName"));
		user.setGender(rs.getInt("gender"));
		user.setUserTypeId(rs.getInt("userType"));
		user.setPhone(rs.getString("userPhone"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setRegisterDate(rs.getDate("registerDate"));
		user.setStatus(rs.getInt("userStatus"));
		return user;
	}
	
	
	/**
	 * pack nationality data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Nationality PackagerNationality(ResultSet rs) throws SQLException
	{
		Nationality nationality=new Nationality();
		nationality.setNationalityId(rs.getInt("nationalityId"));
		nationality.setcName(rs.getString("nationalitycName"));
		nationality.seteName(rs.getString("nationalityeName"));
		nationality.setStatus(rs.getInt("nationalityStatus"));
		return nationality;
	}
	
	
	/**
	 * pack identityOnUser data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static IdentityOnUser PackagerIdentityOnUser(ResultSet rs) throws SQLException
	{
		IdentityOnUser identityOnUser=new IdentityOnUser();
		identityOnUser.setId(rs.getInt("identityOnUserId"));
		identityOnUser.setUserId(rs.getInt("identityOnUserUser"));
		identityOnUser.setNationalityId(rs.getInt("nationality"));
		identityOnUser.setBirthday(rs.getDate("birthday"));
		identityOnUser.setBirthplace(rs.getString("birthplace"));
		identityOnUser.setHomeAddress(rs.getString("homeAddress"));
		identityOnUser.setPhone(rs.getString("homePhone"));
		identityOnUser.setIsMarried(rs.getInt("isMarried"));
		identityOnUser.setStatus(rs.getInt("identityOnUserStatus"));
		return identityOnUser;
	}
	
	
	
	/**
	 * pack education type data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static EducationType PackagerEducationType(ResultSet rs) throws SQLException
	{
		EducationType educationType=new EducationType();
		educationType.setEducationTypeId(rs.getInt("educationTypeId"));
		educationType.setcName(rs.getString("educationTypecName"));
		educationType.seteName(rs.getString("educationTypeeName"));
		educationType.setStatus(rs.getInt("educationTypeStatus"));
		return educationType;
	}
	
	
	

	
	
	/**
	 * pack EducationOnUser data
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public static EducationOnUser PackagerEducationOnUser(ResultSet rs) throws SQLException
	{
		EducationOnUser educationOnUser=new EducationOnUser();
		educationOnUser.setId(rs.getInt("educationOnUserId"));
		educationOnUser.setUserId(rs.getInt("educationOnUserUser"));
		educationOnUser.setEducationTypeId(rs.getInt("educationType"));
		educationOnUser.setIsOversea(rs.getInt("isOversea"));
		educationOnUser.setStatus(rs.getInt("educationOnUserStatus"));
		return educationOnUser;
	}
	
	
	
	/**
	 * pack fundingType data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static FundingType PackagerFundingType(ResultSet rs) throws SQLException
	{
		FundingType fundingType=new FundingType();
		fundingType.setFundingTypeId(rs.getInt("fundingTypeId"));
		fundingType.setcName(rs.getString("fundingTypecName"));
		fundingType.seteName(rs.getString("fundingTypeeName"));
		fundingType.setStatus(rs.getInt("fundingTypeStatus"));
		return fundingType;
	}
	
	
	/**
	 * pack FundingOnUser data
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public static FundingOnUser PackagerFundingOnUser(ResultSet rs) throws SQLException
	{
		FundingOnUser fundingOnUser=new FundingOnUser();
		fundingOnUser.setId(rs.getInt("fundingOnUserId"));
		fundingOnUser.setUserId(rs.getInt("fundingOnUserUser"));
		fundingOnUser.setFundingTypeId(rs.getInt("fundingType"));
		fundingOnUser.setStatus(rs.getInt("fundingOnUserStatus"));
		return fundingOnUser;
	}
	
	
	/**
	 * pack notice type data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static NoticeType PackagerNoticeType(ResultSet rs) throws SQLException
	{
		NoticeType noticeType=new NoticeType();
		noticeType.setNoticeTypeId(rs.getInt("noticeTypeId"));
		noticeType.setcName(rs.getString("noticeTypecName"));
		noticeType.seteName(rs.getString("noticeTypeeName"));
		noticeType.setStatus(rs.getInt("noticeTypeStatus"));
		return noticeType;
	}
	
	

	/**
	 * pack notice data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Notice PackagerNotice(ResultSet rs) throws SQLException
	{
		Notice notice=new Notice();
		notice.setId(rs.getInt("noticeId"));
		notice.setUserId(rs.getInt("noticeUser"));
		notice.setNoticeTypeId(rs.getInt("noticeType"));
		notice.setTitle(rs.getString("title"));
		notice.setContent(rs.getString("ccontent"));
		notice.setWriter(rs.getString("writer"));		
		notice.setDownLoadPath(rs.getString("downloadPath"));
		notice.setPublishDate(rs.getDate("publishDate"));
		notice.setStatus(rs.getInt("noticeStatus"));
		return notice;
	}
	
	/**
	 * pack guarantee data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Guarantee PackagerGuarantee(ResultSet rs) throws SQLException
	{
		Guarantee guarantee=new Guarantee();
		guarantee.setGuaranteeId(rs.getInt("guaranteeId"));
		guarantee.setGuaranteecName(rs.getString("guaranteecName"));
		guarantee.setGuaranteeeName(rs.getString("guaranteeeName"));
		guarantee.setGuaranteePhone(rs.getString("guaranteePhone"));
		guarantee.setGuaranteeStatus(rs.getInt("guaranteeStatus"));
		return guarantee;
	}
	
	
	/**
	 * pack visa data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Visa PackagerVisa(ResultSet rs) throws SQLException
	{
		Visa visa=new Visa();
		visa.setVisaId(rs.getInt("visaId"));
		visa.setGuaranteeId(rs.getInt("guarantee"));
		visa.setRegisterDeadLine(rs.getDate("registerDeadLine"));
		visa.setIntermediaryName(rs.getString("intermediaryName"));
		visa.setIntermediaryPhone(rs.getString("intermediaryPhone"));
		visa.setVisaDueDate(rs.getDate("visaDueDate"));
		visa.setStatus(rs.getInt("visaStatus"));
		return visa;
	}
	
	
	/**
	 * pack VisaOnUser data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static VisaOnUser PackagerVisaOnUser(ResultSet rs) throws SQLException
	{
		VisaOnUser visaOnUser=new VisaOnUser();
		visaOnUser.setId(rs.getInt("visaOnUserId"));
		visaOnUser.setUserId(rs.getInt("visaOnUserUser"));
		visaOnUser.setVisaId(rs.getInt("visa"));
		visaOnUser.setVisaOnUserSchoolYear(rs.getString("visaOnUserSchoolYear"));
		visaOnUser.setVisaOnUserTheSemester(rs.getInt("visaOnUserTheSemester"));
		visaOnUser.setStatus(rs.getInt("visaOnUserStatus"));
		return visaOnUser;
	}
	
	
	
	/**
	 * pack ProfessionalTitleType data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static ProfessionalTitleType PackagerProfessionalTitleType(ResultSet rs) throws SQLException
	{
		ProfessionalTitleType professionalTitleType=new ProfessionalTitleType();
		professionalTitleType.setProfessionalTitleTypeId(rs.getInt("professionalTitleTypeId"));
		professionalTitleType.setcName(rs.getString("professionalTitleTypecName"));
		professionalTitleType.seteName(rs.getString("professionalTitleTypeeName"));
		professionalTitleType.setStatus(rs.getInt("professionalTitleTypeStatus"));
		return professionalTitleType;
	}
	
	
	/**
	 * pack ApartmentRoll data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static ApartmentRoll PackagerApartmentRoll(ResultSet rs) throws SQLException
	{
		ApartmentRoll apartmentRoll=new ApartmentRoll();
		apartmentRoll.setId(rs.getInt("apartmentRollId"));
		apartmentRoll.setUserId(rs.getInt("apartmentRollUser"));
		apartmentRoll.setProfessionalTitleTypeId(rs.getInt("professionalTitleType"));
		apartmentRoll.setCardNumber(rs.getString("tcardNumber"));
		apartmentRoll.setStatus(rs.getInt("apartmentRollStatus"));
		return apartmentRoll;
	}
	
	
	
	
	/**
	 * pack Academy data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Academy PackagerAcademy(ResultSet rs) throws SQLException
	{
		Academy academy=new Academy();
		academy.setAcademyId(rs.getInt("academyId"));
		academy.setcName(rs.getString("academycName"));
		academy.seteName(rs.getString("academyeName"));
		academy.setStatus(rs.getInt("academyStatus"));
		return academy;
	}
	
	
	
	/**
	 * pack major data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Major PackagerMajor(ResultSet rs) throws SQLException
	{
		Major major=new Major();
		major.setMajorId(rs.getInt("majorId"));
		major.setcName(rs.getString("majorcName"));
		major.seteName(rs.getString("majoreName"));
		major.setStatus(rs.getInt("majorStatus"));
		return major;
	}
	
	/**
	 * pack class date
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static CClass PackagerClass(ResultSet rs) throws SQLException
	{
		CClass cclass=new CClass();
		cclass.setClassId(rs.getInt("cclassId"));
		cclass.setcName(rs.getString("cclasscName"));
		cclass.seteName(rs.getString("cclasseName"));
		cclass.setStatus(rs.getInt("cclassStatus"));
		return cclass;
	}
	
	
	/**
	 * pack AMCOnUser data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static AMCOnUser PackagerAMCOnUser(ResultSet rs) throws SQLException
	{
		AMCOnUser amcOnUser=new AMCOnUser();
		amcOnUser.setId(rs.getInt("amcOnUserId"));
		amcOnUser.setUserId(rs.getInt("amcOnUserUser"));
		amcOnUser.setAcademyId(rs.getInt("academy"));
		amcOnUser.setMajorId(rs.getInt("major"));
		amcOnUser.setClassId(rs.getInt("cclass"));
		amcOnUser.setStatus(rs.getInt("amcOnUserStatus"));
		return amcOnUser;
	}
	
	/**
	 * pack studyPeriod data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static StudyPeriod PackagerStudyPeriod(ResultSet rs) throws SQLException
	{
		StudyPeriod studyPeriod=new StudyPeriod();
		studyPeriod.setStudyPeriodId(rs.getInt("studyPeriodId"));
		studyPeriod.setStartDate(rs.getDate("startDate"));
		studyPeriod.setEndDate(rs.getDate("endDate"));
		studyPeriod.setStatus(rs.getInt("studyPeriodStatus"));
		return studyPeriod;
	}
	
	/**
	 * pack RollStatusType data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static RollStatusType PackagerStatus(ResultSet rs) throws SQLException
	{
		RollStatusType rollStatusType=new RollStatusType();
		rollStatusType.setRollStatusTypeId(rs.getInt("rollStatusTypeId"));
		rollStatusType.setcName(rs.getString("rollStatusTypecName"));
		rollStatusType.seteName(rs.getString("rollStatusTypeeName"));
		rollStatusType.setStatus(rs.getInt("rollStatusTypeStatus"));
		return rollStatusType;
	}
	
	/**
	 * Packager StudentType
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static StudentType PackagerStudentType(ResultSet rs) throws SQLException
	{
		StudentType studentType=new StudentType();
		studentType.setStudentTypeId(rs.getInt("studentTypeId"));
		studentType.setStudentTypecName(rs.getString("studentTypecName"));
		studentType.setStudentTypeeName(rs.getString("studentTypeeName"));
		studentType.setStudentTypeStatus(rs.getInt("studentTypeStatus"));
		return studentType;
	}
	
	/**
	 * pack schoolRoll data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static SchoolRoll PackagerSchoolRoll(ResultSet rs) throws SQLException
	{
		SchoolRoll schoolRoll=new SchoolRoll();
		schoolRoll.setId(rs.getInt("schoolRollId"));
		schoolRoll.setUserId(rs.getInt("schoolRollUser"));
		schoolRoll.setStudyPeriodId(rs.getInt("studyPeriod"));
		schoolRoll.setRollStatusTypeId(rs.getInt("rollStatusType"));
		schoolRoll.setStudentTypeId(rs.getInt("studentType"));
		schoolRoll.setPlace(rs.getString("place"));
		schoolRoll.setCardNumber(rs.getString("scardNumber"));
		schoolRoll.setStudentNumber(rs.getString("studentNumber"));
		schoolRoll.setDormitoryNumber(rs.getString("dormitoryNumber"));
		schoolRoll.setComeDate(rs.getDate("comeDate"));
		schoolRoll.setLeaveDate(rs.getDate("leaveDate"));
		schoolRoll.setStatus(rs.getInt("schoolRollStatus"));
		return schoolRoll;
	}
	
	
	/**
	 * pack passport data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Passport PackagerPassport(ResultSet rs) throws SQLException
	{
		Passport passport=new Passport();
		passport.setPassportId(rs.getInt("passportId"));
		passport.setPassportNumber(rs.getString("passportNumber"));
		passport.setStatus(rs.getInt("passportStatus"));
		return passport;
	}

	
	
	/**
	 * pack PassportOnUser data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static PassportOnUser PackagerPassportOnUser(ResultSet rs) throws SQLException 
	{
		PassportOnUser passportOnUser=new PassportOnUser();
		passportOnUser.setId(rs.getInt("passportOnUserId"));
		passportOnUser.setUserId(rs.getInt("passportOnUserUser"));
		passportOnUser.setPassportId(rs.getInt("passport"));
		passportOnUser.setPassportPagePath(rs.getString("passportPagePath"));
		passportOnUser.setStatus(rs.getInt("passportOnUserStatus"));
		return passportOnUser;
	}
	
	
	
	/**
	 * pack payment type data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static PaymentType PackagerPaymentType(ResultSet rs) throws SQLException
	{
		PaymentType paymentType=new PaymentType();
		paymentType.setPaymentTypeId(rs.getInt("paymentTypeId"));
		paymentType.setcName(rs.getString("paymentTypecName"));
		paymentType.seteName(rs.getString("paymentTypeeName"));
		paymentType.setStatus(rs.getInt("paymentTypeStatus"));
		return paymentType;
	}
	
	
	
	/**
	 * pack payment data
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static Payment PackagerPayment(ResultSet rs) throws SQLException
	{
		Payment payment =new Payment();
		payment.setId(rs.getInt("paymentId"));
		payment.setUserId(rs.getInt("paymentUser"));
		payment.setPaymentTypeId(rs.getInt("paymentType"));
		payment.setSchoolYear(rs.getString("schoolYear"));
		payment.setTheSemester(rs.getInt("theSemester"));
		payment.setTotalMoney(rs.getDouble("totalMoney"));
		payment.setMoney(rs.getDouble("money"));
		payment.setPaymentOprUser(rs.getInt("paymentOprUser"));
		payment.setPayDate(rs.getDate("payDate"));
		payment.setDescrible(rs.getString("describle"));
		payment.setStatus(rs.getInt("paymentStatus"));
		return payment;
	}
}
