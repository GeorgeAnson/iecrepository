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
import com.osms.utils.ControllerUtil;
import com.osms.utils.JSONUtil;
import com.osms.utils.Utils;

import net.sf.json.JSONObject;

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

	Users user=null;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getSession().setAttribute(Constants.ERROR, "");
		user=(Users) request.getSession().getAttribute(Constants.USER);
		if(user==null)
		{
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		String type=request.getParameter("type").trim();
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
		
		if(Constants.INIT_ADD.toLowerCase().equals(type.toLowerCase()))
		{
			initAdd(request, response);
		}
		
		if(Constants.DETAIL.toLowerCase().equals(type.toLowerCase()))
		{
			getDetail(request, response);
		}
	}

	private void getDetail(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int userId=Integer.parseInt(request.getParameter("id").trim());
		List<Payment> payments=paymentService.getPaymentForDifferTypeByUserId(userId);
		
		if(null!=payments&&payments.size()>0)
		{
			Iterator it=payments.iterator();
			while(it.hasNext())
			{
				Payment payment=(Payment) it.next();
				payment.setUser(userDao.getUserByUserId(payment.getUserId()));
				payment.setOprUser(userDao.getUserByUserId(payment.getPaymentOprUser()));
			}
		}
		for(Payment p:payments)
		{
			System.out.println("detail : "+p);
		}
		response.setCharacterEncoding("UTF-8");
		ControllerUtil.out(response, payments);
	}

	private void initAdd(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int userId=request.getParameter("id").trim()==null?0:Integer.parseInt(request.getParameter("id").trim());
		List<Payment> payments=paymentService.getPaymentForDifferTypeByUserId(userId);
		//List<PaymentType> paymentTypes=new ArrayList<>();
		//paymentTypes=paymentTypeDao.getAllPaymentType();
		for(Payment payment:payments)
		{
			payment.setPayDate(null);
			payment.setPaymentOprUser(-1);;
			payment.setInvalidTime(null);
			payment.setId(-1);
			payment.setStatus(-1);
			payment.setValidTime(null);
			payment.setPaymentTypeId(-1);
			payment.getPaymentType().setStatus(-1);
			//payment.setPaymentTypes(paymentTypes);
		}
		response.setCharacterEncoding("UTF-8");
		ControllerUtil.out(response, payments);
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
		if(user.getUserTypeId()!=4)
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
			
			AMCOnUser amcOnUser=amcOnUserDao.getAMCOnUserByUserId(user.getUserId()).get(0);
			parma.put("academy", amcOnUser.getAcademyId());
			searchForm.setAcademyId(amcOnUser.getAcademyId());
			parma.put("major", amcOnUser.getMajorId());
			searchForm.setMajorId(amcOnUser.getMajorId());
			parma.put("cclass", amcOnUser.getClassId());
			searchForm.setCclassId(amcOnUser.getClassId());
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
		
		if(op.toLowerCase().equals("addbills".toLowerCase()))
		{
			String validTime=request.getParameter("validTime").trim();//schoolYear
			String invalidTime=request.getParameter("invalidTime").trim();//theSemester
			String describle=request.getParameter("describe").trim();
			
			String academyId=request.getParameter("academyId").trim();
			System.out.println(academyId+"  academyId");
			String majorId=request.getParameter("majorId").trim();
			String cclassId=request.getParameter("cclassId").trim();
			//get parmas
			String paymentTypeIds=request.getParameter("paymentTypeIds").trim();
			System.out.println(paymentTypeIds);
			//check
			int status=check(request, response, academyId, majorId, cclassId, paymentTypeIds, invalidTime);
			if(status!=0)
			{
				request.getRequestDispatcher("/WEB-INF/views/admin/addBills.jsp").forward(request, response);
				return;
			}else
			{
				//get userIds
				List<Integer> userIds=amcOnUserDao.getuserIdsByamc(Integer.parseInt(academyId), Integer.parseInt(majorId), Integer.parseInt(cclassId));
				System.out.println(userIds+" ,payids "+paymentTypeIds);
				//get results
				List<Payment> payments=matchParmas(userIds, paymentTypeIds, majorId, validTime, invalidTime, describle, oprUserId);
				System.out.println(payments);
				//save
				paymentService.save(payments);
				request.getRequestDispatcher("/WEB-INF/views/admin/addBills.jsp").forward(request, response);
				return;
			}
		}else if(op.toLowerCase().equals("bills".toLowerCase()))
		{
			String jsonString =request.getParameter("payment").trim();
			Payment payment=(Payment) JSONUtil.jsonToBean(jsonString, Payment.class);
			//userId,paymentTypeId,schoolYear,theSemester,totalNeeds,money,[paymentDate],describle,status,oprUserId
			payment.setPaymentOprUser(oprUserId);
			payment.setPayDate(Utils.stringToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			payment.setStatus(1);
			System.out.println("Paymeent: "+payment);
			paymentDao.save(payment);
			JSONObject json=new JSONObject();
			json.element("success", true);
			response.setCharacterEncoding("UTF-8");
			ControllerUtil.out(response, json);
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
	private List<Payment> matchParmas(List<Integer> userIds, String paymentTypeIds, String majorId,
			String validTime, String invalidTime, String describle, int oprUserId) {
		// TODO Auto-generated method stub
		List<Payment> payments=new ArrayList<Payment>();
		String[] str=paymentTypeIds.split(";");
		for(int userId:userIds)
		{
			for(String items:str)
			{
				String[] matchs=items.split(",");
				Payment p=new Payment();
				p.setUserId(userId);
				p.setPaymentTypeId(Integer.parseInt(matchs[0]));
				p.setValidTime(Utils.stringToDate(validTime));
				p.setInvalidTime(Utils.stringToDate(invalidTime));
				p.setTotalMoney(Double.parseDouble(matchs[1]));
//				p.setMoney(Double.parseDouble(matchs[2]));
				p.setMoney(0);//初始化生成账单，无需缴费费
				p.setPayDate(Utils.stringToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
				p.setDescrible(describle);
				p.setStatus(1);
				p.setPaymentOprUser(oprUserId);
				payments.add(p);
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
	 * @param invalidTime 
	 * @param totalNeeds
	 * @param op 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private int check(HttpServletRequest request, HttpServletResponse response, 
			String academyId, String majorId, String cclassId, String paymentTypeIds, String invalidTime) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int status=0;
		if(academyId==null||"".equals(academyId)||"0".equals(academyId)
				||majorId==null||"".equals(majorId)
				||cclassId==null||"".equals(cclassId))
		{
			request.getSession().setAttribute(Constants.ERROR, "学院、专业、班级均不能留空");
			status=1;
		}
		
		String[] items=paymentTypeIds.split(";");
		for(String it:items)
		{
			String[] matchs=it.split(",");
			if(matchs[0]==null)
			{
				request.getSession().setAttribute(Constants.ERROR, "请选择缴费项目");
				status=1;
			}
			if(matchs[1]==null)
			{
				request.getSession().setAttribute(Constants.ERROR, "金额不能留空");
				status=1;
			}else
			{
				double needs=Double.parseDouble(matchs[1]);
				if(needs<0)
				{
					request.getSession().setAttribute(Constants.ERROR, "缴费金额不能为负");
					status=1;
				}
			}
		}
		return status;
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
		
		if(user.getUserTypeId()==4)
		{
			List<Payment> payments=paymentService.getPaymentForDifferTypeByUserId(user.getUserId());
			
			if(null!=payments&&payments.size()>0)
			{
				Iterator it=payments.iterator();
				while(it.hasNext())
				{
					Payment payment=(Payment) it.next();
					payment.setUser(userDao.getUserByUserId(payment.getUserId()));
				}
			}
			for(Payment p:payments)
			{
				//p.getUser().setUserId(0);//保持和管理员查询的条件相同，patment.user.userId,否则学生登陆无法查询
				p.getUser().setEmail(null);
				p.getUser().setPassword(null);
				p.getUser().setRegisterDate(null);
				p.getUser().setUserTypeId(0);
				p.getUser().setStatus(0);
				p.getUser().setUserTypeId(0);
				System.out.println("detail : "+p);
			}
			request.getSession().setAttribute("payments", payments);
			request.getRequestDispatcher("/WEB-INF/views/bills.jsp").forward(request, response);
		}else
		{
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
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
