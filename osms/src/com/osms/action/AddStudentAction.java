package com.osms.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osms.bean.AcademyMajorBean;
import com.osms.dao.EducationTypeDao;
import com.osms.dao.GuaranteeDao;
import com.osms.dao.NationalityDao;
import com.osms.dao.RollStatusTypeDao;
import com.osms.dao.StudentTypeDao;
import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.entity.EducationOnUser;
import com.osms.entity.EducationType;
import com.osms.entity.FundingOnUser;
import com.osms.entity.Guarantee;
import com.osms.entity.IdentityOnUser;
import com.osms.entity.Nationality;
import com.osms.entity.Passport;
import com.osms.entity.PassportOnUser;
import com.osms.entity.RollStatusType;
import com.osms.entity.SchoolRoll;
import com.osms.entity.StudentType;
import com.osms.entity.StudyPeriod;
import com.osms.entity.Users;
import com.osms.entity.Visa;
import com.osms.entity.VisaOnUser;
import com.osms.globle.Constants;
import com.osms.service.AMCService;
import com.osms.service.UserService;
import com.osms.utils.Utils;

@Component
public class AddStudentAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	NationalityDao nationalityDao;
	
	@Autowired
	EducationTypeDao educationTypeDao;
	
	@Autowired
	RollStatusTypeDao rollStatusTypeDao;
	
	@Autowired
	AMCService amcService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	StudentTypeDao studentTypeDao;
	
	@Autowired
	GuaranteeDao guaranteeDao;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type=request.getParameter("type").trim();
		Users usr=(Users) request.getSession().getAttribute(Constants.USER);
		if(usr==null)
		{
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		if(Constants.INIT.toLowerCase().equals(type.toLowerCase()))
		{
			initPage(request, response);
		}
		//add a student
		if(Constants.ADD.toLowerCase().equals(type.toLowerCase()))
		{
			addStudent(request, response);
		}
	}
	
	/**
	 * instance page's information
	 * @param response 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void initPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Nationality> nationalities=nationalityDao.getAllNationality();
		request.getSession().setAttribute("nationalities", nationalities);
		List<EducationType> educationTypes=educationTypeDao.getAllEducationType();
		request.getSession().setAttribute("educationTypes", educationTypes);
		List<RollStatusType> rollStatusTypes=rollStatusTypeDao.getAllStatus();
		request.getSession().setAttribute("rollStatusTypes", rollStatusTypes);
		List<StudentType> studentTypes=studentTypeDao.getAllStudentType();
		request.getSession().setAttribute("studentTypes", studentTypes);
		List<Guarantee> guarantees=guaranteeDao.getAllGuarantee();
		request.getSession().setAttribute("guarantees", guarantees);

		Map<Integer, Academy> academyMap=new HashMap<Integer, Academy>();
		Map<Integer, AcademyMajorBean> majorMap=new HashMap<Integer, AcademyMajorBean>();
		Map<Integer, AMCOnUser> classMap=new HashMap<Integer, AMCOnUser>();
		amcService.matchAllAMC(academyMap, majorMap, classMap);
		request.getSession().setAttribute("academyMap", academyMap);
		request.getSession().setAttribute("majorMap", majorMap);
		request.getSession().setAttribute("classMap", classMap);
		request.getRequestDispatcher("/WEB-INF/views/admin/addStudent.jsp").forward(request, response);
	}

	/**
	 * add a student
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, String> parmas=new HashMap<String, String>();
		List<byte[]> images=Utils.analysisForm(request, parmas);
		//save picture
		String parentPath=request.getSession().getServletContext().getRealPath("\\Passports");
		List<String> filenames=Utils.savePic(images, parentPath);
		Users user=new Users();
		//match form's parmas
		matchParmas(parmas, user, filenames);
		
		System.out.println(user);
		
		//save a student
		userService.updateStudent(user);
		
		//request
		request.getRequestDispatcher("/WEB-INF/views/admin/addStudent.jsp").forward(request, response);
	}

	
	private void matchParmas(Map<String, String> parmas, Users user, List<String> filenames) {
		// TODO Auto-generated method stub
		List<FundingOnUser> fundingOnUsers=new ArrayList<FundingOnUser>();
		EducationOnUser educationOnUser=new EducationOnUser();
		
		educationOnUser.setStatus(1);
		user.setUserTypeId( Integer.parseInt(Constants.STUDENT));
		user.setPassword(Utils.toMD5("123456"));
		user.setRegisterDate(Utils.stringToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
		user.setStatus(1);
		IdentityOnUser identityOnUser=new IdentityOnUser();
		identityOnUser.setStatus(1);
		Visa visa=new Visa();
		visa.setStatus(1);
		AMCOnUser amcOnUser=new AMCOnUser();
		amcOnUser.setStatus(1);
		StudyPeriod studyPeriod=new StudyPeriod();
		studyPeriod.setStatus(1);
		SchoolRoll schoolRoll=new SchoolRoll();
		schoolRoll.setStatus(1);
		schoolRoll.setPlace(Constants.SOFTWARE_OWNER);
//		schoolRoll.setRollName(Constants.STUDENT_ROLL_NAME);
		Passport passport=new Passport();
		passport.setStatus(1);
		List<PassportOnUser> passportOnUsers=new ArrayList<PassportOnUser>();
		for(String filename:filenames)
		{
			passportOnUsers.add(new PassportOnUser(0, 0, 0, filename, 1, null, null));
		}
		for(Entry<String, String> entry:parmas.entrySet())
		{
			if(entry.getKey().equals("fullName"))
			{
				user.setFullName(entry.getValue());
			}
			if(entry.getKey().equals("email"))
			{
				user.setEmail(entry.getValue());
			}
			if(entry.getKey().equals("gender"))
			{
				user.setGender(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("adjectivePhone"))
			{
				user.setPhone(entry.getValue());
			}
//			if(entry.getKey().equals("fundingTypeId1")&&entry.getValue()!=null&&!"".equals(entry.getValue()))
//			{
//				fundingOnUsers.add(new FundingOnUser(0, 0, Integer.parseInt(entry.getValue()), 1, null, null));
//			}
//			if(entry.getKey().equals("fundingTypeId2")&&entry.getValue()!=null&&!"".equals(entry.getValue()))
//			{
//				fundingOnUsers.add(new FundingOnUser(0, 0, Integer.parseInt(entry.getValue()), 1, null, null));
//			}
//			if(entry.getKey().equals("fundingTypeId3")&&entry.getValue()!=null&&!"".equals(entry.getValue()))
//			{
//				fundingOnUsers.add(new FundingOnUser(0, 0, Integer.parseInt(entry.getValue()), 1, null, null));
//			}
			if(entry.getKey().equals("studentType"))
			{
				schoolRoll.setStudentTypeId(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("isOversea"))
			{
				educationOnUser.setIsOversea(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("educationTypeId"))
			{
				educationOnUser.setEducationTypeId(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("nationalityId"))
			{
				identityOnUser.setNationalityId(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("birthday"))
			{
				identityOnUser.setBirthday(Utils.stringToDate(entry.getValue()));
			}
			if(entry.getKey().equals("birthplace"))
			{
				identityOnUser.setBirthplace(entry.getValue());
			}
			if(entry.getKey().equals("homeAddress"))
			{
				identityOnUser.setHomeAddress(entry.getValue());
			}
			if(entry.getKey().equals("overseasPhone"))
			{
				identityOnUser.setPhone(entry.getValue());
			}
			if(entry.getKey().equals("isMarried"))
			{
				identityOnUser.setIsMarried(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("registerDeadLine"))
			{
				if(entry.getValue().length()!=0)
				{
					visa.setRegisterDeadLine(Utils.stringToDate(entry.getValue()));
				}
			}
			if(entry.getKey().equals("intermediaryName"))
			{
				visa.setIntermediaryName(entry.getValue());
			}
			if(entry.getKey().equals("intermediaryPhone"))
			{
				visa.setIntermediaryPhone(entry.getValue());
			}
			if(entry.getKey().equals("guaranteeId"))
			{
				visa.setGuaranteeId(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("visaDueDate"))
			{
				if(entry.getValue().length()!=0)
				{
					visa.setVisaDueDate(Utils.stringToDate(entry.getValue()));
				}
			}
			if(entry.getKey().equals("academyId"))
			{
				if(entry.getValue().length()!=0)
				{
					amcOnUser.setAcademyId(Integer.parseInt(entry.getValue()));
				}
			}
			if(entry.getKey().equals("majorId"))
			{
				if(entry.getValue().length()!=0)
				{
					amcOnUser.setMajorId(Integer.parseInt(entry.getValue()));
				}
			}
			if(entry.getKey().equals("classId"))
			{
				if(entry.getValue().length()!=0)
				{
					amcOnUser.setClassId(Integer.parseInt(entry.getValue()));
				}
			}
			if(entry.getKey().equals("startDate"))
			{
				if(entry.getValue().length()!=0)
				{
					studyPeriod.setStartDate(Utils.stringToDate(entry.getValue()));
					schoolRoll.setComeDate(studyPeriod.getStartDate());
				}
			}
			if(entry.getKey().equals("endDate"))
			{
				if(entry.getValue().length()!=0)
				{
					studyPeriod.setEndDate(Utils.stringToDate(entry.getValue()));
					schoolRoll.setLeaveDate(studyPeriod.getEndDate());
				}
			}
			if(entry.getKey().equals("rollStatusTypeId"))
			{
				schoolRoll.setRollStatusTypeId(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("cardNumber"))
			{
				schoolRoll.setCardNumber(entry.getValue());
			}
			if(entry.getKey().equals("studentNumber"))
			{
				schoolRoll.setStudentNumber(entry.getValue());
			}
			if(entry.getKey().equals("studentNumber"))
			{
				schoolRoll.setStudentNumber("studentNumber");
			}
			if(entry.getKey().equals("dormitoryNumber"))
			{
				schoolRoll.setDormitoryNumber(entry.getValue());
			}
			if(entry.getKey().equals("studentTypeId"))
			{
				schoolRoll.setStudentTypeId(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("passportNumber"))
			{
				passport.setPassportNumber(entry.getValue());
			}
		}
		
		VisaOnUser visaOnUser=new VisaOnUser();
		//get schoolYear and semester
		getSchoolYearAndSemester(visaOnUser);
		visaOnUser.setStatus(1);
		visaOnUser.setVisa(visa);
		
		List<AMCOnUser> amcOnUsers=new ArrayList<AMCOnUser>();
		amcOnUsers.add(amcOnUser);
		
		for(PassportOnUser ps:passportOnUsers)
		{
			ps.setPassport(passport);
		}
		schoolRoll.setStudyPeriod(studyPeriod);
		
		//save object;
		//save fudningOnUsers
		user.setFundingOnUsers(fundingOnUsers);
		//save educationOnUsers
		user.setEducationOnUser(educationOnUser);
		//save identityOnUsers
		user.setIdentityOnUser(identityOnUser);
		//save visaOnUsers
		user.setVisaOnUser(visaOnUser);
		//save amcOnUsers
		user.setAmcOnUsers(amcOnUsers);
		//save schoolRolls
		user.setSchoolRoll(schoolRoll);
		//save passportOnUsers
		user.setPassportOnUsers(passportOnUsers);
	}
	
	/**
	 * get the schoolYear and semester
	 * @param visaOnUser
	 */
	private void getSchoolYearAndSemester(VisaOnUser visaOnUser) {
		// TODO Auto-generated method stub
		String startYear=new SimpleDateFormat("yyyy").format(new Date());
		String visaOnUserSchoolYear=null;
		int visaOnUserTheSemester=0;
		int month=Integer.parseInt(new SimpleDateFormat("MM").format(new Date()));
		//get schoolYear and semester
		if((month>=7&&month<=12)||(month>=1&&month<=2))
		{
			if(month>=7&&month<=12)
			{
				visaOnUserSchoolYear=(startYear+"-"+(Integer.parseInt(startYear)+1)).toString();
			}else
			{
				visaOnUserSchoolYear=((Integer.parseInt(startYear)-1)+"-"+startYear).toString();
			}
			visaOnUserTheSemester=1;
		}else
		{
			visaOnUserSchoolYear=(startYear+"-"+(Integer.parseInt(startYear)+1)).toString();
			visaOnUserTheSemester=2;
		}
		visaOnUser.setVisaOnUserSchoolYear(visaOnUserSchoolYear);
		visaOnUser.setVisaOnUserTheSemester(visaOnUserTheSemester);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
