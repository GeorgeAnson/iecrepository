package com.osms.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osms.bean.AcademyMajorBean;
import com.osms.bean.SearchForm;
import com.osms.dao.SearchByPagesDao;
import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.entity.ApartmentRoll;
import com.osms.entity.Users;
import com.osms.globle.Constants;
import com.osms.service.AMCService;
import com.osms.service.ApartmentRollService;
import com.osms.service.UserService;
import com.osms.utils.Utils;

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
	SearchByPagesDao searchByPagesDao;
	
	@Autowired
	ApartmentRollService apartmentRollService;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		//clear status==-1, if exits
		checkAndClearInfoForStatus(users);
		//match roll informations
		for (Users u : users) 
		{
			ApartmentRoll apartmentRoll=apartmentRollService.getApartmentRollByUserId(u.getUserId());
			u.setApartmentRoll(apartmentRoll);
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
		
		parma.put("userType", Constants.STUDENT);
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
		//save
		userService.saveTeacher(user);
	    request.getRequestDispatcher("/WEB-INF/views/admin/teacherMgr.jsp").forward(request, response);
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
				academyId==null||"".equals(academyId)||
				majorId==null||"".equals(majorId))
		{
			request.getSession().setAttribute(Constants.ERROR, "填选学院专业和班级");
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
		request.getRequestDispatcher("/WEB-INF/views/admin/teacherMgr.jsp").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
