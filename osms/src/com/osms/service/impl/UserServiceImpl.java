package com.osms.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.AMCOnUserDao;
import com.osms.dao.ApartmentRollDao;
import com.osms.dao.EducationOnUserDao;
import com.osms.dao.FundingOnUserDao;
import com.osms.dao.IdentityOnUserDao;
import com.osms.dao.PassportDao;
import com.osms.dao.PassportOnUserDao;
import com.osms.dao.ProfessionalTypeDao;
import com.osms.dao.RollStatusTypeDao;
import com.osms.dao.SchoolRollDao;
import com.osms.dao.StudyPeriodDao;
import com.osms.dao.UserDao;
import com.osms.dao.VisaDao;
import com.osms.dao.VisaOnUserDao;
import com.osms.dao.jdbc.JDBCUtil;
import com.osms.entity.AMCOnUser;
import com.osms.entity.ApartmentRoll;
import com.osms.entity.EducationOnUser;
import com.osms.entity.IdentityOnUser;
import com.osms.entity.PassportOnUser;
import com.osms.entity.Payment;
import com.osms.entity.SchoolRoll;
import com.osms.entity.Users;
import com.osms.entity.VisaOnUser;
import com.osms.globle.Constants;
import com.osms.service.AMCService;
import com.osms.service.ApartmentRollService;
import com.osms.service.EducationOnUserService;
import com.osms.service.IdentityOnUserService;
import com.osms.service.PassportOnUserService;
import com.osms.service.PaymentService;
import com.osms.service.SchoolRollService;
import com.osms.service.UserService;
import com.osms.service.VisaOnUserService;
import com.osms.utils.Utils;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	
	@Autowired
	FundingOnUserDao fundingOnUserDao;
	
	@Autowired
	EducationOnUserDao educationOnUserDao;
	
	@Autowired
	IdentityOnUserDao identityOnUserDao;
	
	@Autowired
	VisaOnUserDao visaOnUserDao;
	
	@Autowired
	AMCOnUserDao amcOnUserDao;
	
	@Autowired
	SchoolRollDao schoolRollDao;
	
	@Autowired
	RollStatusTypeDao rollStatusTypeDao;
	
	@Autowired
	PassportOnUserDao passportOnUserDao;
	
	@Autowired
	ApartmentRollService apartmentRollService;
	
	@Autowired
	ApartmentRollDao apartmentRollDao;
	
	@Autowired
	PassportDao passportDao;
	
	@Autowired
	StudyPeriodDao studyPeriodDao;
	
	@Autowired
	PassportOnUserService passportOnUserService;
	
	@Autowired
	VisaDao visaDao;
	@Autowired
	SchoolRollService schoolRollService;
	@Autowired
	IdentityOnUserService identityOnUserService;
	@Autowired
	VisaOnUserService visaOnUserService;
	@Autowired
	AMCService amcService;
	@Autowired
	EducationOnUserService educationOnUserService;
	@Autowired
	PaymentService paymentService;
	/**
	 * the passport and visa should be saved before save a user
	 * at VisaOnUser table ,we need visaId
	 * at PassportOnUser table, we need passportId
	 * before save a PassportOnUser, we need to save passport picture, and get it's saving path 
	 */
	@Override
	public void saveStudent(Users user) {
		int id=0;
		AMCOnUser amcOnUser=null;
		if(user.getSchoolRoll().getRollStatusTypeId()!=2)
		{
			amcOnUser=user.getAmcOnUsers().get(0);
			id=amcOnUserDao.getAMCOnUserByConditions(amcOnUser);
		}
		
		Connection conn=JDBCUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			//save a student
			int userId=userDao.save(user, conn);
			//set userId to other object
			
			if(user.getSchoolRoll().getStudentTypeId()==1)
			{
				//VisaOnUser
				user.getVisaOnUser().setUserId(userId);
				//save visa
				int visaId=visaDao.save(user.getVisaOnUser().getVisa(), conn);
				user.getVisaOnUser().setVisaId(visaId);
				//save
				visaOnUserDao.save(user.getVisaOnUser(), conn);
				if(user.getSchoolRoll().getRollStatusTypeId()!=2)
				{
					//for overSea student that entranced
					//PassportOnUser
					for(PassportOnUser p:user.getPassportOnUsers())
					{
						//save passport
						int passportId=passportDao.save(p.getPassport(), conn);
						p.setUserId(userId);
						p.setPassportId(passportId);
						passportOnUserDao.save(p, conn);
					}
				}
			}
			//IdentityOnUser
			user.getIdentityOnUser().setUserId(userId);
			identityOnUserDao.save(user.getIdentityOnUser(), conn);
			//educationOnUser
			user.getEducationOnUser().setUserId(userId);
			//save
			educationOnUserDao.save(user.getEducationOnUser(), conn);
			
			//AMCOnUser,as a student, only has a AMCOnUser object, but to teacher, maybe many
			for(AMCOnUser a:user.getAmcOnUsers())
			{
				a.setUserId(userId);
				if(id==0)
				{
					amcOnUserDao.save(a, conn);
				}
			}
			//SchoolRoll
			user.getSchoolRoll().setUserId(userId);
			if(user.getSchoolRoll().getRollStatusTypeId()!=2)
			{
				//save studyPeriod
				int studyPeriodId=studyPeriodDao.save(user.getSchoolRoll().getStudyPeriod(), conn);
				user.getSchoolRoll().setStudyPeriodId(studyPeriodId);
			}
			//save schoolRoll
			schoolRollDao.save(user.getSchoolRoll(), conn);
			//transaction commit
			conn.commit();
			if(user.getSchoolRoll().getRollStatusTypeId()!=2)
			{
				if(id!=0)
				{
					amcOnUserDao.update(amcOnUser);
				}
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(conn);
		}
	}

	@Override
	public void saveTeacher(Users user) {
		Connection conn=JDBCUtil.getConnection();
		try {
			conn.setAutoCommit(false);			
			//save a teacher
			int userId=userDao.save(user, conn);
			//set userId to other tables
			//ApartmentOnUser
			user.getApartmentRoll().setUserId(userId);
			//save
			apartmentRollDao.save(user.getApartmentRoll(), conn);
			//AMCOnUser
			for(AMCOnUser a:user.getAmcOnUsers())
			{
				a.setUserId(userId);
				//save directly, doing search only from student
				amcOnUserDao.save(a, conn);
			}
			//transaction commit
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Users getUser(int userId, String userType) {
		// TODO Auto-generated method stub
		//get user
		Users user=userDao.getUserByUserId(userId);
		if(user!=null&&user.getUserId()!=0)
		{
			user.setPassword(null);
			user.setRegisterDate(null);
		}
		
		if(user!=null&&user.getUserId()!=0&&userType.equals(Constants.STUDENT))
		{
			//get schoolRoll
			SchoolRoll schoolRoll=schoolRollService.getSchoolRollByUserId(userId);
			user.setSchoolRoll(schoolRoll);
			//get educationOnUser
			EducationOnUser educationOnUser=educationOnUserService.getEducationOnUserByUserId(userId);
			user.setEducationOnUser(educationOnUser);
			//get identity
			IdentityOnUser identityOnUser=identityOnUserService.getIdentityOnUserByUserId(userId);
			user.setIdentityOnUser(identityOnUser);
			//get passport
			List<PassportOnUser> passportOnUsers=passportOnUserService.getPassportOnUserByUserId(userId);
			user.setPassportOnUsers(passportOnUsers);
			//get visa
			VisaOnUser visaOnUser=visaOnUserService.getVisaOnUserByUserId(userId);
			user.setVisaOnUser(visaOnUser);
			//get amcOnUser, for student, he only have a amcOnUser whose status is 1
			List<AMCOnUser> amcOnUsers=amcService.getAMCByUserId(userId);
			user.setAmcOnUsers(amcOnUsers);
			//if userId is 1, it's root, can look through payment information
			if(user.getUserId()==1)
			{
				List<Payment> payments=paymentService.getPaymentByUserId(userId);
				user.setPayments(payments);
			}
		}else
		{
			//for teacher and administrator and root
			if(user!=null&&user.getUserId()!=0)
			{
				//get apartmentRoll 
				ApartmentRoll apartmentRoll=apartmentRollService.getApartmentRollByUserId(userId);
				user.setApartmentRoll(apartmentRoll);
				//get amcOnUsers
				List<AMCOnUser> amcOnUsers=amcOnUserDao.getAMCOnUserByUserId(userId);
				user.setAmcOnUsers(amcOnUsers);
			}
		}
		return user;
	}

	@Override
	public void updateStudent(Users user) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			//update a student
			userDao.update(user, conn);
			//visaOnUser
			if(user.getVisaOnUser()!=null)
			{
				if(user.getVisaOnUser().getVisa()!=null)
				{
					visaDao.update(user.getVisaOnUser().getVisa(), conn);
				}
				visaOnUserDao.update(user.getVisaOnUser(), conn);
			}
			
			if(user.getSchoolRoll()!=null)
			{
				schoolRollDao.update(user.getSchoolRoll(), conn);
			}
			
			if(user.getPassportOnUsers()!=null)
			{
				for(PassportOnUser passportOnUser:user.getPassportOnUsers())
				{
					if(passportOnUser.getPassport()!=null)
					{
						passportDao.update(passportOnUser.getPassport(), conn);
					}
					// do not update the information, we should save all the history data information
					passportOnUserDao.save(passportOnUser, conn);
				}
			}
			if(user.getIdentityOnUser()!=null)
			{
				identityOnUserDao.update(user.getIdentityOnUser(), conn);
			}
			if(user.getEducationOnUser()!=null)
			{
				educationOnUserDao.upadte(user.getEducationOnUser(), conn);
			}
			if(user.getAmcOnUsers()!=null)
			{
				for(AMCOnUser amcOnUser:user.getAmcOnUsers())
				{
					if(amcOnUser!=null)
					{
						amcOnUserDao.update(amcOnUser, conn);
					}
				}
			}
			//transaction commit
			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(conn);
		}
	}

	@Override
	public int checkEmail(String email) {
		// TODO Auto-generated method stub
		if(!Utils.isEmail(email))
		{
			return 1;
		}else if(userDao.getUserByCondition(email, 1)!=null)
		{
			return -1;
		}
		return 0;
	}

	@Override
	public int checkPhone(String phone) {
		// TODO Auto-generated method stub
		if(!Utils.isPhone(phone))
		{
			return 1;
		}else if(userDao.getUserByCondition(phone, 1)!=null)
		{
			return -1;
		}
		return 0;
	}

	@Override
	public int checkCard(String cardNumber) {
		// TODO Auto-generated method stub
		if(cardNumber.length()!=11)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public void updateTeacher(Users teacher) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			//update a student
			userDao.update(teacher, conn);
			//amcOnUsers
			if(teacher.getAmcOnUsers()!=null)
			{
				for(AMCOnUser amc:teacher.getAmcOnUsers())
				{
					System.out.println("amc : "+amc);
					if(amc!=null&&amc.getId()!=0)
					{
						amcOnUserDao.update(amc, conn);//id not null
					}else
					{
						amcOnUserDao.save(amc, conn);//userId not null
					}
				}
			}
			//apartmentRoll
			if(teacher.getApartmentRoll()!=null)
			{
				apartmentRollDao.update(teacher.getApartmentRoll(), conn);
			}
			//transaction commit
			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(conn);
		}
	}

	@Override
	public void updateParmas(Users user, Users old) {
		// TODO Auto-generated method stub
		if(!user.getFullName().equals(old.getFullName())&&user.getFullName()!=null)
		{
			old.setFullName(user.getFullName());
		}
		if(!user.getSchoolRoll().getStudentNumber().equals(old.getSchoolRoll().getStudentNumber())&&user.getSchoolRoll()!=null&&user.getSchoolRoll().getStudentNumber()!=null)
		{
			old.getSchoolRoll().setStudentNumber(user.getSchoolRoll().getStudentNumber());
		}
		if(!user.getEmail().equals(old.getEmail())&&user.getEmail()!=null)
		{
			old.setEmail(user.getEmail());
		}
		if(user.getGender()!=old.getGender()&&user.getGender()!=0)
		{
			old.setGender(user.getGender());
		}
		
	}
}
