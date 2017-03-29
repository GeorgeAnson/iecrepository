package com.osms.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osms.bean.AcademyMajorBean;
import com.osms.dao.GuaranteeDao;
import com.osms.dao.NationalityDao;
import com.osms.dao.NoticeTypeDao;
import com.osms.dao.PaymentTypeDao;
import com.osms.dao.RollStatusTypeDao;
import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.entity.Guarantee;
import com.osms.entity.Nationality;
import com.osms.entity.NoticeType;
import com.osms.entity.PaymentType;
import com.osms.entity.RollStatusType;
import com.osms.entity.Users;
import com.osms.globle.Constants;
import com.osms.service.AMCService;
import com.osms.service.ConfigService;


@Component
public class ConfigAction extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	ConfigService configService;
	
	@Autowired
	AMCService amcService;
	
	@Autowired
	NationalityDao nationalityDao;
	
	@Autowired
	PaymentTypeDao paymentTypeDao;
	
	@Autowired
	RollStatusTypeDao rollStatusTypeDao;
	
	@Autowired
	NoticeTypeDao noticeTypeDao;
	
	@Autowired
	GuaranteeDao guaranteeDao;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Users user=(Users) request.getSession().getAttribute(Constants.USER);
		if(user==null)
		{
		  response.sendRedirect(request.getContextPath()+"/login.jsp");	
		}
		String type=request.getParameter("type").trim();
		request.getSession().setAttribute(Constants.ERROR, "");
		//instance configuration page
		if(Constants.INIT.toLowerCase().equals(type.toLowerCase()))
		{
			Map<Integer, Academy> academyMap=new HashMap<Integer, Academy>();
			Map<Integer, AcademyMajorBean> majorMap=new HashMap<Integer, AcademyMajorBean>();
			Map<Integer, AMCOnUser> classMap=new HashMap<Integer, AMCOnUser>();
			amcService.matchAllAMC(academyMap, majorMap, classMap);
			request.getSession().setAttribute("academyMap", academyMap);
			request.getSession().setAttribute("majorMap", majorMap);
			request.getSession().setAttribute("classMap", classMap);
			
			List<Nationality> nationalities=nationalityDao.getAllNationality();
			request.getSession().setAttribute("nationalities", nationalities);

			List<PaymentType >paymentTypes=paymentTypeDao.getAllPaymentType();
			request.getSession().setAttribute("paymentTypes", paymentTypes);

			List<RollStatusType> rollStatusTypes=rollStatusTypeDao.getAllStatus();
			request.getSession().setAttribute("rollStatusTypes", rollStatusTypes);

			List<NoticeType> noticeTypes=noticeTypeDao.getAllNoticeType();
			request.getSession().setAttribute("noticeTypes", noticeTypes);
			
			List<Guarantee> guarantees=guaranteeDao.getAllGuarantee();
			request.getSession().setAttribute("guarantees", guarantees);
			request.getRequestDispatcher("/WEB-INF/views/admin/config.jsp").forward(request, response);
		}
		
		//add configuration information
		if(Constants.ADD.toLowerCase().equals(type.toLowerCase()))
		{
			addCondiguration(request, response);
		}
		//delete
		if(Constants.DELETE.toLowerCase().equals(type.toLowerCase()))
		{
			delConfiguration(request, response);
		}
	}
	


	/**
	 * delete configuration operation
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void delConfiguration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String obj=request.getParameter("obj").trim();
		int id=Integer.parseInt(request.getParameter("id").trim());
		String eName=request.getParameter("eName").trim();
		// amc
		if(obj.toLowerCase().equals(Constants.ACADEMY)||obj.toLowerCase().equals(Constants.MAJOR)||obj.toLowerCase().equals(Constants.CCLASS))
		{
			configService.deleteAMCOnUser(obj, eName, id);
		}else
		{
			//else
			configService.deleteConfiguration(obj, eName, id);
		}
		//instance
		initConfig(request, obj);
		request.getRequestDispatcher("/WEB-INF/views/admin/config.jsp").forward(request, response);
	}




	/**
	 * add configuration
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void addCondiguration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String obj=request.getParameter("obj").trim();
		String cName=request.getParameter("cName").trim();
		String eName=request.getParameter("eName").trim();
		// judge Chinese name
		if(cName==null)
		{
			request.getSession().setAttribute(Constants.ERROR, "中文名不能为空");
			request.getRequestDispatcher("/WEB-INF/views/admin/config.jsp").forward(request, response);
			return;
		}
		// amc
		if(obj.toLowerCase().equals(Constants.ACADEMY)||obj.toLowerCase().equals(Constants.MAJOR)||obj.toLowerCase().equals(Constants.CCLASS))
		{
			int academyId=Integer.parseInt(request.getParameter("academyId").trim());
			int majorId=Integer.parseInt(request.getParameter("majorId").trim());
			System.out.println(academyId+" "+majorId+" "+obj);
			String ERROR=null;
			ERROR=configService.saveAMCOnUser(obj, cName, eName, academyId, majorId);
			if (ERROR!=null)
			{
				request.getSession().setAttribute(Constants.ERROR, ERROR);
			}
		}else if(obj.toLowerCase().equals(Constants.GUARANTEE))
		{
			//guarantee
			String guaranteePhone=request.getParameter("gPhone").trim();
			Guarantee guarantee=new Guarantee(0, cName, eName, guaranteePhone, 1);
			guaranteeDao.save(guarantee);
		}else
		{
			//else
			configService.saveConfiguration(obj, cName, eName);
		}
		//instance
		initConfig(request, obj);
		request.getRequestDispatcher("/WEB-INF/views/admin/config.jsp").forward(request, response);
	}


	/**
	 * instance part configurations
	 * @param request
	 * @param obj
	 */
	private void initConfig(HttpServletRequest request, String obj) {
		// TODO Auto-generated method stub
		if(obj.toLowerCase().equals(Constants.ACADEMY)||obj.toLowerCase().equals(Constants.MAJOR)||obj.toLowerCase().equals(Constants.CCLASS))
		{
			Map<Integer, Academy> academyMap=new HashMap<Integer, Academy>();
			Map<Integer, AcademyMajorBean> majorMap=new HashMap<Integer, AcademyMajorBean>();
			Map<Integer, AMCOnUser> classMap=new HashMap<Integer, AMCOnUser>();
			amcService.matchAllAMC(academyMap, majorMap, classMap);
			request.getSession().setAttribute("academyMap", academyMap);
			request.getSession().setAttribute("majorMap", majorMap);
			request.getSession().setAttribute("classMap", classMap);
		}else
		{
			switch (obj.toLowerCase()) {
			case "nationality":
				List<Nationality> nationalities=nationalityDao.getAllNationality();
				request.getSession().setAttribute("nationalities", nationalities);
				break;
			case "paymenttype":
				List<PaymentType >paymentTypes=paymentTypeDao.getAllPaymentType();
				request.getSession().setAttribute("paymentTypes", paymentTypes);
				break;
			case "rollstatustype":
				List<RollStatusType> rollStatusTypes=rollStatusTypeDao.getAllStatus();
				request.getSession().setAttribute("rollStatusTypes", rollStatusTypes);
				break;
			case "noticetype":
				List<NoticeType> noticeTypes=noticeTypeDao.getAllNoticeType();
				request.getSession().setAttribute("noticeTypes", noticeTypes);
				break;
			case "guarantee":
				List<Guarantee> guarantees=guaranteeDao.getAllGuarantee();
				request.getSession().setAttribute("guarantees", guarantees);
				break;
			default:
				//....
				break;
			}
		}
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
