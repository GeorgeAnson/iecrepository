package com.osms.action;

import java.io.IOException;
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
import com.osms.bean.IdentityForm;
import com.osms.bean.SearchForm;
import com.osms.dao.SearchByPagesDao;
import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.entity.IdentityOnUser;
import com.osms.entity.Passport;
import com.osms.entity.SchoolRoll;
import com.osms.entity.Users;
import com.osms.globle.Constants;
import com.osms.service.AMCService;
import com.osms.service.IdentityOnUserService;
import com.osms.service.PassportOnUserService;
import com.osms.service.SchoolRollService;
import com.osms.utils.ControllerUtil;

@Component
public class StudentMgrAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	SearchByPagesDao searchByPagesDao;
	
	@Autowired
	AMCService amcService;
	
	@Autowired
	PassportOnUserService passportOnUserService;
	
	@Autowired
	SchoolRollService schoolRollService;
	
	@Autowired
	IdentityOnUserService identityOnUserService;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Users user=(Users) request.getSession().getAttribute(Constants.USER);
		if(user==null)
		{
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		
		String type=request.getParameter("type").trim();
		if(Constants.INIT.toLowerCase().equals(type.toLowerCase()))
		{
			//instance page
			InitPage(request, response);
		}
		
		if(Constants.SEARCH.toLowerCase().equals(type.toLowerCase()))
		{
			//search students by requested
			searchStudents(request, response);
		}
		
		if(Constants.FIND_IDENTITY.toLowerCase().equals(type.toLowerCase()))
		{
			String id=request.getParameter("id").trim();
			IdentityOnUser identityOnUser=identityOnUserService.getIdentityOnUserByUserId(Integer.parseInt(id));
			System.out.println(identityOnUser);
			IdentityForm identityForm=new IdentityForm();
			identityForm.setBirthday(identityOnUser.getBirthday().toString());
			identityForm.setBirthPlace(identityOnUser.getBirthplace());
			identityForm.setHomeAddress(identityOnUser.getHomeAddress());
			identityForm.setMarry(identityOnUser.getIsMarried()==2?"Î´»é":"ÒÑ»é");
			identityForm.setNational(identityOnUser.getNationality().getcName());
			identityForm.setPhone(identityOnUser.getPhone());
			identityOnUser=null;
			response.setCharacterEncoding("UTF-8");
			ControllerUtil.out(response, identityForm);
		}
	}
	
	/**
	 * instance page
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void InitPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Map<Integer, Academy> academyMap=new HashMap<Integer, Academy>();
		Map<Integer, AcademyMajorBean> majorMap=new HashMap<Integer, AcademyMajorBean>();
		Map<Integer, AMCOnUser> classMap=new HashMap<Integer, AMCOnUser>();
		amcService.matchAllAMC(academyMap, majorMap, classMap);
		request.getSession().setAttribute("academyMap", academyMap);
		request.getSession().setAttribute("majorMap", majorMap);
		request.getSession().setAttribute("classMap", classMap);
		request.getRequestDispatcher("/WEB-INF/views/admin/studentMgr.jsp").forward(request, response);
	}

	/**
	 * search users
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void searchStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		//match user information
		for(Users u:users)
		{
			List<Passport> passports=passportOnUserService.getPassport(u.getUserId());
			u.setPassports(passports);
			SchoolRoll schoolRoll=schoolRollService.getRollStatusTypeByUserId(u.getUserId());
			u.setSchoolRoll(schoolRoll);
			System.out.println(u);
		}
		
		request.getSession().setAttribute("searchForm", searchForm);
		request.getSession().setAttribute("students", users);
		request.getRequestDispatcher("/WEB-INF/views/admin/studentMgr.jsp").forward(request, response);
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
		System.out.println(currentPage);
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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
