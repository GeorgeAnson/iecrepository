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
import com.osms.dao.UserDao;
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
	UserDao userDao;
	
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
		System.out.println("first: "+filenames);
		if(images!=null)
		{
			//save picture
			String parentPath=request.getSession().getServletContext().getRealPath("\\Passports");
			filenames=Utils.savePic(images, parentPath);
			System.out.println(filenames);
		}
		Users user=new Users();
		for(Entry<String, String> entry:parmas.entrySet())
		{
			if(entry.getKey().equals("userId"))
			{
				user.setUserId(Integer.parseInt(entry.getValue()));
			}
		}
		user=userService.getUser(user.getUserId(), Constants.STUDENT);
		//match form's parmas
		matchParmas(parmas, user, filenames);
		//save a student
		userService.updateStudent(user);
		//request
		response.sendRedirect(request.getContextPath()+"/studentInfo.html?type=init&id="+user.getUserId());
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
		response.setCharacterEncoding("UTF-8");
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
			System.out.println("查询的用户ID"+userId);
			Users user=userService.getUser(userId, Constants.STUDENT);
			System.out.println(user);
			request.getSession().setAttribute("student", user);
			request.getRequestDispatcher("/WEB-INF/views/studentInfo.jsp").forward(request, response);
		}
	}

	
	private void matchParmas(Map<String, String> parmas, Users user, List<String> filenames) {
		// TODO Auto-generated method stub
		List<PassportOnUser> passportOnUsers=new ArrayList<PassportOnUser>();
		//if all those is null	    
		for(String filename:filenames)
		{
			passportOnUsers.add(new PassportOnUser(0, user.getUserId(), user.getPassportOnUsers().get(0).getPassportId(), filename, 1, null, null));
		}
		for(Entry<String, String> entry:parmas.entrySet())
		{
			if(entry.getKey().equals("fullName")&&entry.getValue()!=null&&!entry.getValue().equals(user.getFullName()))
			{
				user.setFullName(entry.getValue());
			}
			if(entry.getKey().equals("email")&&entry.getValue()!=null&&!entry.getValue().equals(user.getEmail()))
			{
				user.setEmail(entry.getValue());
			}
			if(entry.getKey().equals("gender")&&entry.getValue()!=null&&Integer.parseInt(entry.getValue())!=0&&!entry.getValue().equals(user.getGender()))
			{
				user.setGender(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("adjectivePhone")&&entry.getValue()!=null&&!entry.getValue().equals(user.getPhone()))
			{
				user.setPhone(entry.getValue());
			}
			if(entry.getKey().equals("studentType")&&entry.getValue()!=null&&!entry.getValue().equals(user.getSchoolRoll().getStudentTypeId()))////////////////
			{
				user.getSchoolRoll().setStudentTypeId(Integer.parseInt(entry.getValue()));
			}
			
			if(entry.getKey().equals("educationTypeId")&&entry.getValue()!=null&&Integer.parseInt(entry.getValue())!=0&&!entry.getValue().equals(user.getEducationOnUser().getEducationTypeId()))
			{
				user.getEducationOnUser().setEducationTypeId(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("nationalityId")&&entry.getValue()!=null&&Integer.parseInt(entry.getValue())!=0&&!entry.getValue().equals(user.getIdentityOnUser().getNationalityId()))
			{
				user.getIdentityOnUser().setNationalityId(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("birthday")&&entry.getValue()!=null&&!entry.getValue().equals(user.getIdentityOnUser().getBirthday()))
			{
				user.getIdentityOnUser().setBirthday(Utils.stringToDate(entry.getValue()));
			}
			if(entry.getKey().equals("birthplace")&&entry.getValue()!=null&&!entry.getValue().equals(user.getIdentityOnUser().getBirthplace()))
			{
				user.getIdentityOnUser().setBirthplace(entry.getValue());
			}
			if(entry.getKey().equals("homeAddress")&&entry.getValue()!=null&&!entry.getValue().equals(user.getIdentityOnUser().getHomeAddress()))
			{
				user.getIdentityOnUser().setHomeAddress(entry.getValue());
			}
			if(entry.getKey().equals("overseasPhone")&&entry.getValue()!=null&&!entry.getValue().equals(user.getIdentityOnUser().getPhone()))
			{
				user.getIdentityOnUser().setPhone(entry.getValue());
			}
			if(entry.getKey().equals("isMarried")&&entry.getValue()!=null&&Integer.parseInt(entry.getValue())!=0&&!entry.getValue().equals(user.getIdentityOnUser().getIsMarried()))
			{
				user.getIdentityOnUser().setIsMarried(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("registerDeadLine")&&entry.getValue()!=null&&!entry.getValue().equals(user.getVisaOnUser().getVisa().getRegisterDeadLine()))
			{
				if(entry.getValue().length()!=0)
				{
					user.getVisaOnUser().getVisa().setRegisterDeadLine(Utils.stringToDate(entry.getValue()));
				}
			}
			if(entry.getKey().equals("intermediaryName")&&entry.getValue()!=null&&!entry.getValue().equals(user.getVisaOnUser().getVisa().getIntermediaryName()))
			{
				user.getVisaOnUser().getVisa().setIntermediaryName(entry.getValue());
			}
			if(entry.getKey().equals("intermediaryPhone")&&entry.getValue()!=null&&!entry.getValue().equals(user.getVisaOnUser().getVisa().getIntermediaryPhone()))
			{
				user.getVisaOnUser().getVisa().setIntermediaryPhone(entry.getValue());
			}
			if(entry.getKey().equals("guaranteeId")&&entry.getValue()!=null&&Integer.parseInt(entry.getValue())!=0&&!entry.getValue().equals(user.getVisaOnUser().getVisa().getGuaranteeId()))
			{
				user.getVisaOnUser().getVisa().setGuaranteeId(Integer.parseInt(entry.getValue()));
			}
//			if(entry.getKey().equals("visaDueDate")&&entry.getValue()!=null&&!entry.getValue().equals(user.getVisaOnUser().getVisa().getVisaDueDate()))
//			{
//				if(entry.getValue().length()!=0)
//				{
//					user.getVisaOnUser().getVisa().setVisaDueDate(Utils.stringToDate(entry.getValue()));
//				}
//			}
			if(entry.getKey().equals("academyId")&&entry.getValue()!=null&&Integer.parseInt(entry.getValue())!=0&&!entry.getValue().equals(user.getAmcOnUsers().get(0).getAcademyId()))
			{
				if(entry.getValue().length()!=0)
				{
					user.getAmcOnUsers().get(0).setAcademyId(Integer.parseInt(entry.getValue()));
				}
			}
			if(entry.getKey().equals("majorId")&&entry.getValue()!=null&&Integer.parseInt(entry.getValue())!=0&&!entry.getValue().equals(user.getAmcOnUsers().get(0).getMajorId()))
			{
				if(entry.getValue().length()!=0)
				{
					user.getAmcOnUsers().get(0).setMajorId(Integer.parseInt(entry.getValue()));
				}
			}
			if(entry.getKey().equals("classId")&&entry.getValue()!=null&&Integer.parseInt(entry.getValue())!=0&&!entry.getValue().equals(user.getAmcOnUsers().get(0).getClassId()))
			{
				if(entry.getValue().length()!=0)
				{
					user.getAmcOnUsers().get(0).setClassId(Integer.parseInt(entry.getValue()));
				}
			}
			if(entry.getKey().equals("startDate")&&entry.getValue()!=null&&!entry.getValue().equals(user.getSchoolRoll().getStudyPeriod().getStartDate()))
			{
				if(entry.getValue().length()!=0)
				{
					user.getSchoolRoll().getStudyPeriod().setStartDate(Utils.stringToDate(entry.getValue()));
				}
			}
			if(entry.getKey().equals("endDate")&&entry.getValue()!=null&&!entry.getValue().equals(user.getSchoolRoll().getStudyPeriod().getEndDate()))
			{
				if(entry.getValue().length()!=0)
				{
					user.getSchoolRoll().getStudyPeriod().setEndDate(Utils.stringToDate(entry.getValue()));
				}
			}
			if(entry.getKey().equals("rollStatusTypeId")&&entry.getValue()!=null&&Integer.parseInt(entry.getValue())!=0&&!entry.getValue().equals(user.getSchoolRoll().getRollStatusTypeId()))
			{
				user.getSchoolRoll().setRollStatusTypeId(Integer.parseInt(entry.getValue()));
			}
			if(entry.getKey().equals("cardNumber")&&entry.getValue()!=null&&!entry.getValue().equals(user.getSchoolRoll().getCardNumber()))
			{
				user.getSchoolRoll().setCardNumber(entry.getValue());
			}
			if(entry.getKey().equals("studentNumber")&&entry.getValue()!=null&&!entry.getValue().equals(user.getSchoolRoll().getStudentNumber()))
			{
				user.getSchoolRoll().setStudentNumber(entry.getValue());
			}
			if(entry.getKey().equals("dormitoryNumber")&&entry.getValue()!=null&&!entry.getValue().equals(user.getSchoolRoll().getDormitoryNumber()))
			{
				user.getSchoolRoll().setDormitoryNumber(entry.getValue());
			}
			if(entry.getKey().equals("passportNumber")&&entry.getValue()!=null&&!entry.getValue().equals(user.getPassportOnUsers().get(0).getPassport().getPassportNumber()))
			{
				for(PassportOnUser passportOnUser:user.getPassportOnUsers())
				{
					passportOnUser.getPassport().setPassportNumber(entry.getValue());
				}
			}
		}
		//save passportOnUsers
		user.setPassportOnUsers(passportOnUsers);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
