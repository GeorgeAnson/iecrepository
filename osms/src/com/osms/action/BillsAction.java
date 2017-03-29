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
import com.osms.dao.AMCOnUserDao;
import com.osms.dao.PaymentDao;
import com.osms.dao.PaymentTypeDao;
import com.osms.dao.SearchByPagesDao;
import com.osms.dao.UserDao;
import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.entity.Payment;
import com.osms.entity.PaymentType;
import com.osms.entity.Users;
import com.osms.globle.Constants;
import com.osms.service.AMCService;
import com.osms.service.PaymentService;
import com.osms.utils.Utils;

@Component
public class BillsAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	PaymentTypeDao paymentTypeDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	PaymentDao paymentDao;
	
	@Autowired
	SearchByPagesDao searchByPagesDao;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	AMCService amcService;
	
	@Autowired
	AMCOnUserDao amcOnUserDao;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type=request.getParameter("type").trim();
		Users user=(Users) request.getSession().getAttribute(Constants.USER);
		if(user==null)
		{
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
		
		if(Constants.INIT.toLowerCase().equals(type.toLowerCase()))
		{
			initBillsPage(request, response);
		}
		
		if(Constants.ADD.toLowerCase().equals(type.toLowerCase()))
		{
			addBills(request, response, user.getUserId());
		}
		
		if(Constants.SEARCH.toLowerCase().equals(type.toLowerCase()))
		{
			SearchBills(request, response);
		}
	}

	/**
	 * search bills informations
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void SearchBills(HttpServletRequest request, HttpServletResponse response)
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
		//get schoolYear and semester
		parma.put("schoolYear", "'"+searchForm.getSchoolYear()+"'");
		parma.put("theSemester", searchForm.getThsSemester());
		//get user by amcOnUser
		List<Payment> payments=paymentService.searchByPaymentOnamc(parma, limit, searchForm.getPage(), count);
		
		for(Payment p:payments)
		{
			Users oprUser=userDao.getUserByUserId(p.getPaymentOprUser());
			p.setOprUser(oprUser);
			System.out.println(p);
		}
		request.getSession().setAttribute("payments", payments);
		request.getSession().setAttribute("searchForm", searchForm);
		request.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(request, response);
	}

	/**
	 * get search page the total pages
	 * @param request
	 * @param limit
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
		String academyId=request.getParameter("academyId").trim();
		String majorId=request.getParameter("majorId").trim();
		String cclassId=request.getParameter("cclassId").trim();
		String schoolYear=request.getParameter("schoolYear").trim();
		String theSemester=request.getParameter("theSemester").trim();
		
		if(schoolYear==null||"".equals(schoolYear)
				||theSemester==null||"".equals(theSemester))
		{
			request.getSession().setAttribute(Constants.ERROR, "请选择学年和学期");
			request.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(request, response);
			return;
		}
		parma.put("userType", Constants.STUDENT);
		if(academyId!=null&&!"".equals(academyId))
		{
			parma.put("academy", Integer.parseInt(academyId));
			searchForm.setAcademyId(Integer.parseInt(academyId));
		}
		if(majorId!=null&&!"".equals(majorId))
		{
			parma.put("major", Integer.parseInt(majorId));
			searchForm.setMajorId(Integer.parseInt(majorId));
		}
		if(cclassId!=null&&!"".equals(cclassId))
		{
			parma.put("cclass", Integer.parseInt(cclassId));
			searchForm.setCclassId(Integer.parseInt(cclassId));
		}
		if(schoolYear!=null&&!"".equals(schoolYear))
		{
			searchForm.setSchoolYear(schoolYear);
		}
		if(theSemester!=null&&!"".equals(theSemester))
		{
			searchForm.setThsSemester(Integer.parseInt(theSemester));
		}
	}

	/**
	 * add bills
	 * @param request
	 * @param response
	 * @param id 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void addBills(HttpServletRequest request, HttpServletResponse response, int oprUserId) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String op=request.getParameter("op").trim();
		String totalNeeds=request.getParameter("totalNeeds").trim();
		String money=request.getParameter("money").trim();
		String schoolYear=request.getParameter("schoolYear").trim();
		String theSemester=request.getParameter("theSemester").trim();
		String describle=request.getParameter("describe").trim();
		
		if(op.toLowerCase().equals("addbills".toLowerCase()))
		{
			String academyId=request.getParameter("academyId").trim();
			String majorId=request.getParameter("majorId").trim();
			String cclassId=request.getParameter("cclassId").trim();
			//get parmas
			String paymentTypeIds=request.getParameter("paymentTypeIds").trim();
			//check
			check(request, response, academyId, majorId, cclassId, paymentTypeIds, totalNeeds, money, theSemester);
			//get userIds
			List<Integer> userIds=amcOnUserDao.getuserIdsByamc(Integer.parseInt(academyId), Integer.parseInt(majorId), Integer.parseInt(cclassId));
			//get results
			List<Payment> payments=matchParmas(userIds, paymentTypeIds, totalNeeds, money, majorId, schoolYear, theSemester, describle, oprUserId);
			//save
			paymentService.save(payments);
			request.getRequestDispatcher("/WEB-INF/views/admin/addBills.jsp").forward(request, response);
			return;
		}else if(op.toLowerCase().equals("bills".toLowerCase()))
		{
			//getParmas(request, response);
			String condition=request.getParameter("condition").trim();
			String paymentTypeId=request.getParameter("paymentTypeId").trim();
			
			//judge whether parma is ok
			judgeParma(request, response, condition, totalNeeds, paymentTypeId, money);
			//get user object
			Users user=userDao.getUserByCondition(condition, 1);
			if(user==null)
			{
				request.getSession().setAttribute(Constants.ERROR, "用户不存在，请检查输入手机号/邮箱是否正确");
				request.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(request, response);
				return;
			}
			//save a payment object
			Payment payment=new Payment();
			payment.setUserId(user.getUserId());
			payment.setPaymentTypeId(Integer.parseInt(paymentTypeId));
			payment.setSchoolYear(schoolYear);
			payment.setTheSemester(Integer.parseInt(theSemester));
			payment.setTotalMoney(Double.parseDouble(totalNeeds));
			payment.setMoney(Double.parseDouble(money));
			payment.setPayDate(Utils.stringToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			payment.setDescrible(describle);
			payment.setStatus(1);
			payment.setPaymentOprUser(oprUserId);
			//save
			paymentDao.save(payment);
			
			request.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * get result payments
	 * @param userIds
	 * @param paymentTypeIds
	 * @param totalNeeds
	 * @param majorId
	 * @param schoolYear
	 * @param theSemester
	 * @param describle
	 * @param oprUserId 
	 * @param  
	 * @return
	 */
	private List<Payment> matchParmas(List<Integer> userIds, String paymentTypeIds, String totalNeeds, String money, String majorId,
			String schoolYear, String theSemester, String describle, int oprUserId) {
		// TODO Auto-generated method stub
		List<Payment> payments=new ArrayList<Payment>();
		String[] payTypeIds=paymentTypeIds.split(";");
		for(int userId:userIds)
		{
			for (String payTypeId:payTypeIds) 
			{
				if(payTypeId!=null&&!"".equals(payTypeId))
				{
					Payment p=new Payment();
					p.setUserId(userId);
					p.setPaymentTypeId(Integer.parseInt(payTypeId));
					p.setSchoolYear(schoolYear);
					p.setTheSemester(Integer.parseInt(theSemester));
					p.setTotalMoney(Double.parseDouble(totalNeeds));
					p.setMoney(Double.parseDouble(money));
					p.setPayDate(Utils.stringToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
					p.setDescrible(describle);
					p.setStatus(1);
					p.setPaymentOprUser(oprUserId);
					payments.add(p);
				}
			}
		}
		return payments;
	}

	/**
	 * check bat add
	 * @param request
	 * @param response
	 * @param academyId
	 * @param majorId
	 * @param cclassId
	 * @param paymentTypeIds
	 * @param totalNeeds
	 * @param money
	 * @param schoolYear
	 * @param theSemester
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void check(HttpServletRequest request, HttpServletResponse response, 
			String academyId, String majorId, String cclassId, String paymentTypeIds, String totalNeeds,
			String money, String theSemester) throws ServletException, IOException {
		// TODO Auto-generated method stub
		judgeParma(request, response, theSemester, paymentTypeIds, money, totalNeeds);
		
		if(academyId==null||"".equals(academyId)
				||majorId==null||"".equals(majorId)
				||cclassId==null||"".equals(cclassId))
		{
			request.getSession().setAttribute(Constants.ERROR, "不能留空");
			request.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(request, response);
			return;
		}
	}

	/**
	 * judge whether parma is ok
	 * @param request 
	 * @param response 
	 * @param condition
	 * @param paymentTypeId
	 * @param money
	 * @param money2 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void judgeParma(HttpServletRequest request, HttpServletResponse response,
			String condition, String paymentTypeId, String money, String totalNeeds)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(condition==null||"".equals(condition)
				||money==null||"".equals(money)
				||totalNeeds==null||"".equals(totalNeeds))
		{
			request.getSession().setAttribute(Constants.ERROR, "不能留空");
			request.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(request, response);
			return;
		}
		
		double needs=Double.parseDouble(totalNeeds);
		if(needs<0)
		{
			request.getSession().setAttribute(Constants.ERROR, "缴费金额不能为负");
			request.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(request, response);
			return;
		}
		
		double pay=Double.parseDouble(money);
		if(pay<0)
		{
			request.getSession().setAttribute(Constants.ERROR, "缴费金额不能为负");
			request.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(request, response);
			return;
		}
		
		if(needs<pay)
		{
			request.getSession().setAttribute(Constants.ERROR, "应缴金额小于实缴金额");
			request.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(request, response);
			return;
		}
	}


	/**
	 * instance bills page
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void initBillsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String op=request.getParameter("op").trim();
		Map<Integer, Academy> academyMap=new HashMap<Integer, Academy>();
		Map<Integer, AcademyMajorBean> majorMap=new HashMap<Integer, AcademyMajorBean>();
		Map<Integer, AMCOnUser> classMap=new HashMap<Integer, AMCOnUser>();
		amcService.matchAllAMC(academyMap, majorMap, classMap);
		request.getSession().setAttribute("academyMap", academyMap);
		request.getSession().setAttribute("majorMap", majorMap);
		request.getSession().setAttribute("classMap", classMap);

		List<PaymentType> paymentTypes=paymentTypeDao.getAllPaymentType();
		request.getSession().setAttribute("paymentTypes", paymentTypes);
		
		if(op.toLowerCase().equals(Constants.ADD.toLowerCase()))
		{
			request.getRequestDispatcher("/WEB-INF/views/admin/addBills.jsp").forward(request, response);
			return;
		}else if(op.toLowerCase().equals("manage".toLowerCase()))
		{
			request.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(request, response);
			return;
		}else
		{
			request.getRequestDispatcher(request.getContextPath()+"/login.jsp").forward(request, response);
			return;
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
