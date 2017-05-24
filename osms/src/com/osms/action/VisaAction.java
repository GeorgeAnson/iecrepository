package com.osms.action;

import java.io.IOException;
import java.util.ArrayList;
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
import com.osms.bean.VisaForm;
import com.osms.dao.AMCOnUserDao;
import com.osms.dao.SearchByPagesDao;
import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.entity.PassportOnUser;
import com.osms.entity.Payment;
import com.osms.entity.Users;
import com.osms.entity.VisaOnUser;
import com.osms.globle.Constants;
import com.osms.service.AMCService;
import com.osms.service.PassportOnUserService;
import com.osms.service.PaymentService;
import com.osms.service.VisaOnUserService;

@Component
public class VisaAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	AMCService amcService;
	
	@Autowired
	SearchByPagesDao searchByPagesDao;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	VisaOnUserService visaOnUserService;
	
	@Autowired
	PassportOnUserService passportOnUserService;
	
	@Autowired
	AMCOnUserDao amcOnUserDao;

	Users usr=null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type=request.getParameter("type").trim();
		usr=(Users) request.getSession().getAttribute(Constants.USER);
		if(usr==null)
		{
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
		if(Constants.INIT.toLowerCase().equals(type.toLowerCase()))
		{
			initPage(request, response);
		}
		if(Constants.SEARCH.toLowerCase().equals(type.toLowerCase()))
		{
			searchDueVisa(request, response);
		}
	}
	
	/**
	 * instance page
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void initPage(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(usr.getUserTypeId()==4)
		{
			List<Payment> payments=paymentService.getPaymentByUserId(usr.getUserId());
			List<VisaForm> visas=new ArrayList<VisaForm>();
			for(Payment p:payments)
			{
				VisaForm visaForm=new VisaForm();
				visaForm.setPayment(p);
				visaForm.setUser(p.getUser());
				VisaOnUser visaOnUser=visaOnUserService.getVisaOnUserByUserId(usr.getUserId());
				visaForm.setVisaOnUser(visaOnUser);
				List<PassportOnUser> passportOnUsers=passportOnUserService.getPassportOnUserByUserId(usr.getUserId());
				visaForm.setPassportOnUsers(passportOnUsers);

				visas.add(visaForm);
				visaForm.getUser().setEmail(null);
			}
			request.getSession().setAttribute("visaForms", visas);
			request.getRequestDispatcher("/WEB-INF/views/visa.jsp").forward(request, response);
		}else
		{
			Map<Integer, Academy> academyMap=new HashMap<Integer, Academy>();
			Map<Integer, AcademyMajorBean> majorMap=new HashMap<Integer, AcademyMajorBean>();
			Map<Integer, AMCOnUser> classMap=new HashMap<Integer, AMCOnUser>();
			amcService.matchAllAMC(academyMap, majorMap, classMap);
			request.getSession().setAttribute("academyMap", academyMap);
			request.getSession().setAttribute("majorMap", majorMap);
			request.getSession().setAttribute("classMap", classMap);
			
			request.getRequestDispatcher("/WEB-INF/views/visa.jsp").forward(request, response);
		}
	}

	/**
	 * get the due visas after 30 days after
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void searchDueVisa(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		IdentityHashMap<Object, Object> parma=new IdentityHashMap<Object, Object>();
		SearchForm searchForm=new SearchForm();
		//get parma
		getParma(request, response, parma, searchForm);
		//set tables
		Map<String, String> table = new IdentityHashMap<String, String>();
		table.put("Users", "userId");
		table.put("AMCOnUser", "amcOnUserUser");
		//get total data numbers,there contains N people, so there are N payments
		int count = searchByPagesDao.getCount(parma, table);
		int limit=Integer.parseInt(Constants.LIMIT_PER_PAGE_DATA);
		//get page and pages
		getSearchPage(request, limit, count, searchForm);
		//get user by amcOnUser
		List<Payment> payments=paymentService.searchByPaymentOnamc(parma, limit, searchForm.getPage(), count);
		List<VisaForm> visas=new ArrayList<VisaForm>();
		for(Payment p:payments)
		{
			VisaForm visaForm=new VisaForm();
			visaForm.setPayment(p);
			visaForm.setUser(p.getUser());
			VisaOnUser visaOnUser=visaOnUserService.getVisaOnUserByUserId(p.getUser().getUserId());
			visaForm.setVisaOnUser(visaOnUser);
			List<PassportOnUser> passportOnUsers=passportOnUserService.getPassportOnUserByUserId(p.getUser().getUserId());
			visaForm.setPassportOnUsers(passportOnUsers);
			System.out.println(passportOnUsers);

			visas.add(visaForm);
			System.out.println(visaForm);
		}
		request.getSession().setAttribute("visaForms", visas);
		request.getRequestDispatcher("/WEB-INF/views/visa.jsp").forward(request, response);
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
		int currentPage=Integer.parseInt(page)+1;
		if(currentPage<=0||currentPage>pages)
		{
			currentPage=Integer.parseInt(Constants.SEARCH_INIT_NUM);
		}
		searchForm.setPage(currentPage);
		System.out.println(searchForm.getAcademyId()+"  "+searchForm.getMajorId()+"  "+searchForm.getCclassId());
		System.out.println(count+"  "+count/(limit*1.0));
	}
	
	
	/**
	 * get parma from request
	 * @param request
	 * @param response
	 * @param parma
	 * @param searchForm
	 * @throws ServletException
	 * @throws IOException
	 */
	private void getParma(HttpServletRequest request, HttpServletResponse response,
			IdentityHashMap<Object, Object> parma, SearchForm searchForm) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(usr.getUserTypeId()!=4)
		{
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
		}else
		{
			parma.put("userType", Constants.STUDENT);
			AMCOnUser amcOnUser=amcOnUserDao.getAMCOnUserByUserId(usr.getUserId()).get(0);
			parma.put("academy", amcOnUser.getAcademyId());
			searchForm.setAcademyId(amcOnUser.getAcademyId());
			parma.put("major", amcOnUser.getMajorId());
			searchForm.setMajorId(amcOnUser.getMajorId());
			parma.put("cclass", amcOnUser.getClassId());
			searchForm.setCclassId(amcOnUser.getClassId());
			
		}
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
