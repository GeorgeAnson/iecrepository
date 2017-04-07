package com.osms.action;

import java.io.IOException;
import java.util.ArrayList;
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
import com.osms.entity.CClass;
import com.osms.entity.EducationOnUser;
import com.osms.entity.EducationType;
import com.osms.entity.FundingOnUser;
import com.osms.entity.Guarantee;
import com.osms.entity.IdentityOnUser;
import com.osms.entity.Major;
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
import com.osms.utils.ControllerUtil;
import com.osms.utils.Utils;

import net.sf.json.JSONArray;

@Component
public class UserInfoAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	UserService userService;
	
	@Autowired
	NationalityDao nationalityDao;
	
	@Autowired
	EducationTypeDao educationTypeDao;
	
	@Autowired
	RollStatusTypeDao rollStatusTypeDao;
	
	@Autowired
	AMCService amcService;
	
	@Autowired
	GuaranteeDao guaranteeDao;

	@Autowired
	StudentTypeDao studentTypeDao;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Users usr=(Users) request.getSession().getAttribute(Constants.USER);
		if(usr==null)
		{
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
		String type=request.getParameter("type").trim();
		if(Constants.INIT.toLowerCase().equals(type.toLowerCase()))
		{
			getUserInfo(request, response);
		}
		//instance userInfo page for update, get configuration informations
		//initConfig
		if(Constants.INIT_CONFIGURATION.toLowerCase().equals(type.toLowerCase()))
		{
			initUpdate(request, response);
		}
		//update
		if(Constants.UPDATE.toLowerCase().equals(type.toLowerCase()))
		{
			updateUserInfo(request, response);
		}
	}
	
	/**
	 * update user information
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void updateUserInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String, String> parmas=new HashMap<String, String>();
		List<byte[]> images=Utils.analysisForm(request, parmas);
		List<String> filenames=null;
		if(images!=null)
		{
			//save picture
			String parentPath=request.getSession().getServletContext().getRealPath("\\Passports");
			filenames=Utils.savePic(images, parentPath);
		}
		Users user=new Users();
		//String jsonString =request.getParameter("user").trim();
		//user=(Users) JSONUtil.jsonToBean(jsonString, user.getClass());
		//match form's parmas
		matchParmas(parmas, user, filenames);
		
		System.out.println(user);
		
		//save a student
		userService.updateStudent(user);
		
		//request
		request.getRequestDispatcher("/WEB-INF/views/admin/addStudent.jsp").forward(request, response);
	}

	private void initUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Nationality> nationalities=nationalityDao.getAllNationality();
		List<EducationType> educationTypes=educationTypeDao.getAllEducationType();
		List<RollStatusType> rollStatusTypes=rollStatusTypeDao.getAllStatus();
		List<StudentType> studentTypes=studentTypeDao.getAllStudentType();
		List<Guarantee> guarantees=guaranteeDao.getAllGuarantee();
		
		Map<Integer, Academy> academyMap=new HashMap<Integer, Academy>();
		Map<Integer, AcademyMajorBean> majorMap=new HashMap<Integer, AcademyMajorBean>();
		Map<Integer, AMCOnUser> classMap=new HashMap<Integer, AMCOnUser>();
		amcService.matchAllAMC(academyMap, majorMap, classMap);
		//jsonArray
		JSONArray jsonArray=new JSONArray();
		jsonArray.add(academyMap.values());
		Map<Integer, Major> m=new HashMap<>();
		for(Entry<Integer, AcademyMajorBean> entry:majorMap.entrySet())
		{
			Major major=new Major();
			major.setMajorId(entry.getKey());
			major.setcName(entry.getValue().getMajorChineseName());
			major.seteName(entry.getValue().getMajorEnglishName());
			major.setStatus(1);
			m.put(entry.getKey(), major);
		}
		jsonArray.add(m.values());
		Map<Integer, CClass> c=new HashMap<>();
		for(Entry<Integer, AMCOnUser> entry:classMap.entrySet())
		{
			c.put(entry.getKey(), entry.getValue().getCclass());
		}
		jsonArray.add(c.values());
		jsonArray.add(nationalities);
		jsonArray.add(educationTypes);
		jsonArray.add(rollStatusTypes);
		jsonArray.add(studentTypes);
		jsonArray.add(guarantees);
		System.out.println(jsonArray);
		ControllerUtil.out(response, jsonArray.toArray());
	}

	/**
	 * get user information by userId
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id=request.getParameter("id").trim();
		if(id==null||"".equals(id))
		{
			request.getSession().setAttribute(Constants.ERROR, "用户信息获取错误，请重试");
			request.getRequestDispatcher("/WEB-INF/views/admin/studentMgr.jsp").forward(request, response);
			return;
		}else
		{
			int userId=Integer.parseInt(id);
			Users user=userService.getUser(userId, Constants.STUDENT);
			System.out.println(user);
			request.getSession().setAttribute("student", user);
			request.getRequestDispatcher("/WEB-INF/views/studentInfo.jsp").forward(request, response);
		}
	}

	
	private void matchParmas(Map<String, String> parmas, Users user, List<String> filenames) {
		// TODO Auto-generated method stub
		List<FundingOnUser> fundingOnUsers=new ArrayList<FundingOnUser>();
		EducationOnUser educationOnUser=new EducationOnUser();
		
		educationOnUser.setStatus(1);
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
		Passport passport=new Passport();
		passport.setStatus(1);
		List<PassportOnUser> passportOnUsers=new ArrayList<PassportOnUser>();
		//if all those is null
		if(filenames==null)
		{
			passportOnUsers.add(new PassportOnUser(0, 0, 0, null, 1, null, null));
		}
	    
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
//		getSchoolYearAndSemester(visaOnUser);
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
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
