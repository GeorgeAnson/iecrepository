package com.osms.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
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
import com.osms.bean.SearchForm;
import com.osms.dao.ProfessionalTypeDao;
import com.osms.dao.SearchByPagesDao;
import com.osms.dao.UserDao;
import com.osms.dao.UserTypeDao;
import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.entity.ApartmentRoll;
import com.osms.entity.CClass;
import com.osms.entity.Major;
import com.osms.entity.ProfessionalTitleType;
import com.osms.entity.UserType;
import com.osms.entity.Users;
import com.osms.globle.Constants;
import com.osms.service.AMCService;
import com.osms.service.ApartmentRollService;
import com.osms.service.UserService;
import com.osms.utils.ControllerUtil;
import com.osms.utils.JSONUtil;
import com.osms.utils.Utils;

import net.sf.json.JSONObject;

@Component
public class TeacherMgrAction extends HttpServlet {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	AMCService amcService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserTypeDao userTypeDao;
	
	@Autowired
	SearchByPagesDao searchByPagesDao;
	
	@Autowired
	ApartmentRollService apartmentRollService;
	
	@Autowired
	ProfessionalTypeDao professionalTypeDao;
	
	@Autowired
	UserDao userDao;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getSession().setAttribute(Constants.ERROR, "");
		Users usr=(Users) request.getSession().getAttribute(Constants.USER);
		if(usr==null)
		{
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		String type=request.getParameter("type").trim();
		if(Constants.INIT.toLowerCase().equals(type.toLowerCase()))
		{
			initPage(request, response);
		}
		if(Constants.ADD.toLowerCase().equals(type.toLowerCase()))
		{
			addTeacher(request, response);
		}
		if(Constants.SEARCH.toLowerCase().equals(type.toLowerCase()))
		{
			searchTeachers(request, response);
		}
		if(Constants.DETAIL.toLowerCase().equals(type.toLowerCase()))
		{
			findTeacher(request, response);
		}
		if(Constants.UPDATE.toLowerCase().equals(type.toLowerCase()))
		{
			updateTeacher(request, response);
		}
		if(Constants.DELETE.toLowerCase().equals(type.toLowerCase()))
		{
			String id=request.getParameter("id").trim();
			Users teacher=userDao.getUserByUserId(Integer.parseInt(id));
			teacher.setStatus(-1);
			userDao.update(teacher);
			JSONObject json=new JSONObject();
			json.element("success", true);
			response.setCharacterEncoding("UTF-8");
			ControllerUtil.out(response, json);
		}
	}
	
	/**
	 * update a teacher's information
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void updateTeacher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jsonTeacher=request.getParameter("teacher").trim();
		String jsonAmcOnUsers=request.getParameter("amcOnUsers").trim();
		Users teacher=(Users) JSONUtil.jsonToBean(jsonTeacher, Users.class);
		List<AMCOnUser> amcOnUsers=JSONUtil.jsonToList(jsonAmcOnUsers, AMCOnUser.class);
		for(AMCOnUser amc:amcOnUsers)
		{
			amc.setUser(null);
			amc.setAcademy(null);
			amc.setMajor(null);
			amc.setCclass(null);
		}
		teacher.setAmcOnUsers(amcOnUsers);
		System.out.println(teacher.getUserTypeId());
		System.out.println("ttttt: "+amcOnUsers);
		Users user=userService.getUser(teacher.getUserId(), Constants.TEACHER);
		
		for(AMCOnUser amc:user.getAmcOnUsers())
		{
			amc.setUser(null);
			amc.setAcademy(null);
			amc.setMajor(null);
			amc.setCclass(null);
			amc.setStatus(-1);
		}
		System.out.println("uuuuu: "+user.getAmcOnUsers());
		int status=checkInfo(request, response, teacher, user);
		
		if(status!=0)
		{
			return;
		}else
		{
			userService.updateTeacher(user);
			JSONObject json=new JSONObject();
			json.element("success", true);
			response.setCharacterEncoding("UTF-8");
			ControllerUtil.out(response, json);
		}	
	}

	/**
	 * get a teacher's information 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void findTeacher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id=request.getParameter("id").trim();
		if(id ==null||"".equals(id))
		{
			request.getSession().setAttribute(Constants.ERROR, "查询用户不存在");
			request.getRequestDispatcher("/WEB-INF/views/admin/teacherMgr.jsp").forward(request, response);
			return;
		}else
		{
			int userId=Integer.parseInt(id);
			Users teacher=userService.getUser(userId, Constants.TEACHER);
			response.setCharacterEncoding("UTF-8");
			ControllerUtil.out(response, teacher);
			return;
		}
	}

	/**
	 * search teachers
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void searchTeachers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<Object, Object> parma = new IdentityHashMap<Object, Object>();
		SearchForm searchForm=new SearchForm();
		//get search parma
		getSearchParma(request, parma, searchForm);
		//set tables
		Map<String, String> table = new IdentityHashMap<String, String>();
		table.put("Users", "userId");
		table.put("AMCOnUser", "amcOnUserUser");
		//get total data numbers
		int count = searchByPagesDao.getCount(parma, table);
		int limit=Integer.parseInt(Constants.LIMIT_PER_PAGE_DATA);
		getSearchPage(request, limit, count, searchForm);
		//get user by amcOnUser
		List<Users> users=searchByPagesDao.getUsersByAMCOnUsers(parma, limit, searchForm.getPage(), count);
		
		Map<Integer, Users> userMaps=new HashMap<>();
		for(Users user:users)
		{
			userMaps.put(user.getUserId(), user);
		}
		users.clear();
		for(Entry<Integer, Users> entry:userMaps.entrySet())
		{
			users.add(entry.getValue());
		}
		//clear status==-1, if exits
		checkAndClearInfoForStatus(users);
		//match roll informations
		for (Users u : users) 
		{
			ApartmentRoll apartmentRoll=apartmentRollService.getApartmentRollByUserId(u.getUserId());
			u.setApartmentRoll(apartmentRoll);
			List<AMCOnUser> amcOnUsers=amcService.getAMCByUserId(u.getUserId());
			UserType userType=userTypeDao.getUserTypeByUserTypeId(u.getUserTypeId());
			u.setUserType(userType);
			if(amcOnUsers.size()>1)
			{
				int aid=0,mid=0;
				for(AMCOnUser amcOnUser:amcOnUsers)
				{
					if(aid!=0&&aid==amcOnUser.getAcademy().getAcademyId())
					{
						aid=amcOnUser.getAcademy().getAcademyId();
						amcOnUser.setAcademy(null);
					}else
					{
						aid=amcOnUser.getAcademy().getAcademyId();
					}
					if(mid!=0&&mid==amcOnUser.getMajor().getMajorId())
					{
						mid=amcOnUser.getMajor().getMajorId();
						amcOnUser.setMajor(null);
					}else
					{
						mid=amcOnUser.getMajor().getMajorId();
					}
				}
			}
			u.setAmcOnUsers(amcOnUsers);
			System.out.println(u);
		}
		request.getSession().setAttribute("tSearch", searchForm);
		request.getSession().setAttribute("teachers", users);
		request.getRequestDispatcher("/WEB-INF/views/admin/teacherMgr.jsp").forward(request, response);
	}
	
	/**
	 * clear information about amcOnUser
	 * @param users
	 */
	private void checkAndClearInfoForStatus(List<Users> users) {
		// TODO Auto-generated method stub
		for(Users u:users)
		{
			if(u.getUserTypeId()==4)
			{
				users.remove(u);
			}else
			{
				if(u.getAmcOnUser()!=null)
				{
					if(u.getAmcOnUser().getStatus()==-1)
					{
						u.setAmcOnUser(null);
					}
					if(u.getAmcOnUser().getAcademy()!=null)
					{
						if(u.getAmcOnUser().getAcademy().getStatus()==-1)
						{
							u.setAmcOnUser(null);
						}
					}
					if(u.getAmcOnUser().getMajor()!=null)
					{
						if(u.getAmcOnUser().getMajor().getStatus()==-1)
						{
							u.getAmcOnUser().setMajor(null);
							u.getAmcOnUser().setCclass(null);
						}
					}
					if(u.getAmcOnUser().getCclass()!=null)
					{
						if(u.getAmcOnUser().getCclass().getStatus()==-1)
						{
							u.getAmcOnUser().setCclass(null);
						}
					}
				}
			}
		}
	}

	
	/**
	 * get current page and total pages
	 * @param request
	 * @param count
	 * @param count 
	 * @param searchForm
	 */
	private void getSearchPage(HttpServletRequest request, int limit, int count, SearchForm searchForm) {
		// TODO Auto-generated method stub
		String page=request.getParameter("page").trim();
		//if both them is null, put all of those data
		if(page==null||"".equals(page))
		{
			page=Constants.SEARCH_INIT_NUM;
		}
		//get total pages
		int pages=0;
		if(count/(limit*0.1)>count/limit)
		{
			pages=count/limit+1;
		}else
		{
			pages=count/limit;
		}
		searchForm.setPages(pages);
		//get current page
		int currentPage=Integer.parseInt(page);
		if(currentPage<=0||currentPage>pages)
		{
			currentPage=Integer.parseInt(Constants.SEARCH_INIT_NUM);
		}
		searchForm.setPage(currentPage);
		System.out.println(searchForm.getAcademyId()+"  "+searchForm.getMajorId()+"  "+searchForm.getCclassId());
		System.out.println(count+"  "+count/(limit*1.0));
	}
	
	/**
	 * get search parma from request
	 * @param request
	 * @param parma
	 * @param searchForm 
	 */
	private void getSearchParma(HttpServletRequest request, Map<Object, Object> parma, SearchForm searchForm) {
		// TODO Auto-generated method stub
		String academyId=request.getParameter("academyId").trim();
		String majorId=request.getParameter("majorId").trim();
		String cclassId=request.getParameter("cclassId").trim();
		
		parma.put("userType", Constants.TEACHER);
		if(academyId!=null&&!"".equals(academyId)&&!"0".equals(academyId))
		{
			parma.put("academy", Integer.parseInt(academyId));
			searchForm.setAcademyId(Integer.parseInt(academyId));
		}
		if(majorId!=null&&!"".equals(majorId)&&!"0".equals(majorId))
		{
			parma.put("major", Integer.parseInt(majorId));
			searchForm.setMajorId(Integer.parseInt(majorId));
		}
		if(cclassId!=null&&!"".equals(cclassId)&&!"0".equals(cclassId))
		{
			parma.put("cclass", Integer.parseInt(cclassId));
			searchForm.setCclassId(Integer.parseInt(cclassId));
		}
	}

	/**
	 * add a teacher object
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException
	 */
	private void addTeacher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//user
		Users user=new Users();
		getParmas(request, response, user);
		int status=checkAddInfo(request, response, user);
		if(status==0)
		{
			System.out.println(user);
			//save
			userService.saveTeacher(user);
		    request.getRequestDispatcher("/WEB-INF/views/admin/teacherMgr.jsp").forward(request, response);
		    return;
		}else
		{
			return;
		}
		
	}

	private int checkAddInfo(HttpServletRequest request, HttpServletResponse response, Users user) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ERROR="";
		int status=0;
		status=userService.checkEmail(user.getEmail());
		if(status!=0)
		{
			if(status==1)
			{
				ERROR="邮箱格式不正确";
				request.getSession().setAttribute(Constants.ERROR, ERROR);
			}
			if(status==-1)
			{
				ERROR="邮箱已被注册";
				request.getSession().setAttribute(Constants.ERROR, ERROR);
			}
		}
		
//		status=userService.checkCard(user.getApartmentRoll().getCardNumber());
//		
//		if(status!=0)
//		{
//			if(status==1)
//			{
//				ERROR="卡号长度应为11位";
//				request.getSession().setAttribute(Constants.ERROR, ERROR);
//			}
//		}
		status=userService.checkPhone(user.getPhone());
		
		if(status!=0)
		{
			if(status==1)
			{
				ERROR="手机号码格式不正确";
				request.getSession().setAttribute(Constants.ERROR, ERROR);
			}
			if(status==-1)
			{
				ERROR="该手机号已被注册";
				request.getSession().setAttribute(Constants.ERROR, ERROR);
			}
		}
		if(status!=0)
		{
			request.getRequestDispatcher("/WEB-INF/views/admin/teacherMgr.jsp").forward(request, response);
		}
		return status;
	}

	private int checkInfo(HttpServletRequest request, HttpServletResponse response, Users teacher, Users user) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ERROR="";
		int status=0;
		if(!teacher.getEmail().equals(user.getEmail()))
		{
			status=userService.checkEmail(teacher.getEmail());
		}
		
		if(status!=0)
		{
			if(status==1)
			{
				ERROR="邮箱格式不正确";
				request.getSession().setAttribute(Constants.ERROR, ERROR);
			}
			if(status==-1)
			{
				ERROR="邮箱已被注册";
				request.getSession().setAttribute(Constants.ERROR, ERROR);
			}
		}
		
		if(!teacher.getApartmentRoll().getCardNumber().equals(user.getApartmentRoll().getCardNumber()))
		{
			status=userService.checkCard(teacher.getApartmentRoll().getCardNumber());
		}
		
		if(status!=0)
		{
			if(status==1)
			{
				ERROR="卡号长度应为11位";
				request.getSession().setAttribute(Constants.ERROR, ERROR);
			}
		}
		if(!teacher.getPhone().equals(user.getPhone()))
		{
			status=userService.checkPhone(teacher.getPhone());
		}
		
		if(status!=0)
		{
			if(status==1)
			{
				ERROR="手机号码格式不正确";
				request.getSession().setAttribute(Constants.ERROR, ERROR);
			}
			if(status==-1)
			{
				ERROR="该手机号已被注册";
				request.getSession().setAttribute(Constants.ERROR, ERROR);
			}
		}
		if(status!=0)
		{
			JSONObject json=new JSONObject();
			json.element("success", false);
			json.element("msg", ERROR);
			response.setCharacterEncoding("UTF-8");
			ControllerUtil.out(response, json);
		}else
		{
			if(teacher.getFullName()!=null)
			{
				user.setFullName(teacher.getFullName());
			}
			if(teacher.getApartmentRoll().getCardNumber()!=null)
			{
				user.getApartmentRoll().setCardNumber(teacher.getApartmentRoll().getCardNumber());
			}
			if(teacher.getPhone()!=null)
			{
				user.setPhone(teacher.getPhone());
			}
			if(teacher.getEmail()!=null)
			{
				user.setEmail(teacher.getEmail());
			}
			if(teacher.getGender()!=0)
			{
				user.setGender(teacher.getGender());
			}
			if(teacher.getApartmentRoll().getProfessionalTitleTypeId()!=0)
			{
				user.getApartmentRoll().setProfessionalTitleTypeId(teacher.getApartmentRoll().getProfessionalTitleTypeId());
			}
			if(teacher.getUserTypeId()!=0)
			{
				user.setUserTypeId(teacher.getUserTypeId());
			}
			if(teacher.getAmcOnUsers()!=null)
			{
				for(AMCOnUser amc:teacher.getAmcOnUsers())
				{
					amc.setUserId(user.getUserId());
					amc.setStatus(Integer.parseInt(Constants.STATUS_OK));
					if(amc.getId()==0)
					{
						user.getAmcOnUsers().add(amc);
					}else
					{
						for(AMCOnUser amcOnUser:user.getAmcOnUsers())
						{
							if(amcOnUser.getId()==amc.getId())
							{
								amcOnUser.setAcademyId(amc.getAcademyId());
								amcOnUser.setMajorId(amc.getMajorId());
								amcOnUser.setClassId(amc.getClassId());
								amcOnUser.setStatus(amc.getStatus());
							}
						}
					}
				}
			}
		}
		return status;
	}

	/**
	 * get request parmas
	 * @param request
	 * @param response
	 * @param user 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void getParmas(HttpServletRequest request, HttpServletResponse response, Users user)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//fundamental informations
		String fullName=request.getParameter("fullName").trim();
		String gender=request.getParameter("gender").trim();
		String userTypeId=request.getParameter("userTypeId").trim();
		String email=request.getParameter("email").trim();
		String phone=request.getParameter("phone").trim();
		if(email==null||"".equals(email)||phone==null||"".equals(phone))
		{
			request.getSession().setAttribute(Constants.ERROR, "填写邮箱及手机信息");
			request.getRequestDispatcher("/WEB-INF/views/admin/teacherMgr.jsp").forward(request, response);
			return;
		}
		user.setFullName(fullName);
		user.setGender(Integer.parseInt(gender));
		user.setUserTypeId(Integer.parseInt(userTypeId));
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassword(Utils.toMD5("123456"));
		user.setRegisterDate(Utils.stringToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
		user.setStatus(Integer.parseInt(Constants.STATUS_OK));
		//amcOnUser
		List<AMCOnUser> amcOnUsers=new ArrayList<AMCOnUser>();
		getamcOnUsers(request, response, amcOnUsers);
		//apartmentRoll
		String tcardNumber=request.getParameter("tcardNumber").trim();
		String professionalTitleTypeId=request.getParameter("professionalTitleTypeId").trim();
		ApartmentRoll apartmentRoll=new ApartmentRoll(0, 0, Integer.parseInt(professionalTitleTypeId), 
				tcardNumber, Integer.parseInt(Constants.STATUS_OK), null, null);
		//match
		user.setAmcOnUsers(amcOnUsers);
		user.setApartmentRoll(apartmentRoll);
	}

	/**
	 * Split string of classId
	 * @param request
	 * @param response
	 * @param amcOnUsers
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void getamcOnUsers(HttpServletRequest request, HttpServletResponse response, List<AMCOnUser> amcOnUsers)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String academyId=request.getParameter("academyId").trim();
		String majorId=request.getParameter("majorId").trim();
		String cclassIdstr=request.getParameter("cclasIdstr").trim();
		if(cclassIdstr==null||"".equals(cclassIdstr)||
				academyId==null||"".equals(academyId)||"0".equals(academyId)||
				majorId==null||"".equals(majorId))
		{
			request.getSession().setAttribute(Constants.ERROR, "填选学院、专业和班级");
			request.getRequestDispatcher("/WEB-INF/views/admin/teacherMgr.jsp").forward(request, response);
			return;
		}
		String[] cclassIds=cclassIdstr.split(";");
		for(String classId:cclassIds)
		{
			amcOnUsers.add(new AMCOnUser(0, 0, Integer.parseInt(academyId), Integer.parseInt(majorId), 
					Integer.parseInt(classId), Integer.parseInt(Constants.STATUS_OK), null, null, null, null));
		}
	}

	/**
	 * instance page
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void initPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<Integer, Academy> academyMap=new HashMap<Integer, Academy>();
		Map<Integer, AcademyMajorBean> majorMap=new HashMap<Integer, AcademyMajorBean>();
		Map<Integer, AMCOnUser> classMap=new HashMap<Integer, AMCOnUser>();
		amcService.matchAllAMC(academyMap, majorMap, classMap);
		request.getSession().setAttribute("academyMap", academyMap);
		request.getSession().setAttribute("majorMap", majorMap);
		request.getSession().setAttribute("classMap", classMap);
		
		
		System.out.println(majorMap);
		
		List<ProfessionalTitleType> professionalTitleTyps=professionalTypeDao.getAllProfessionalType();
		request.getSession().setAttribute("professionalTitleTypes", professionalTitleTyps);
		
		request.getRequestDispatcher("/WEB-INF/views/admin/teacherMgr.jsp").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
