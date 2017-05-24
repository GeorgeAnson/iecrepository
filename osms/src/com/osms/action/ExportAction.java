package com.osms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osms.bean.Export;
import com.osms.dao.AMCOnUserDao;
import com.osms.dao.AcademyDao;
import com.osms.dao.UserDao;
import com.osms.entity.AMCOnUser;
import com.osms.entity.Users;
import com.osms.globle.Constants;
import com.osms.service.UserService;
import com.osms.utils.ExportToExcelUtil;
import com.osms.utils.ExportUtil;
import com.osms.utils.Utils;

@Component
public class ExportAction extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//file type-doc
	private static String WORD_TYPE=".doc";
	//file type-doc
	private static String EXCEL_TYPE=".xls";
	//file-xls
	private static String WORD="word";
	//file-xls
	private static String EXCEL="excel";
	//model name-visa-old-doc type
	private static String VISA_WORD_MODEL="visaModel.ftl";
	//visa -new -excel type
	private static String VISA_XLS_MODEL="visa.ftl";
	//filename-201table
	private static String VISA_XLS="visa.xls";
	//model name-word
	private static String DOC_MODEL="entrance.ftl";
	//filename-entrance document
	private static String ENTRANCE_DOC="entrance.doc";

	@Autowired
	UserDao userDao;
	@Autowired
	UserService userService;
	
	@Autowired
	AcademyDao academyDao;
	
	@Autowired
	AMCOnUserDao amcOnUserDao;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type=request.getParameter("type").trim();
		if(Constants.EXCEL.toLowerCase().equals(type.toLowerCase()))
		{
			//new bean,202-table->visa-new
			exportExcel(request, response);
		}
		if(Constants.EXCELS.toLowerCase().equals(type.toLowerCase()))
		{
			//zip excel学生信息
			exportExcelsToZip(request, response);
		}
		if(Constants.TABLE.toLowerCase().equals(type.toLowerCase()))
		{
			//202table->visa-old
			exportTable(request, response);
		}
		
		if(Constants.DOC.toLowerCase().equals(type.toLowerCase()))
		{
			//entrance.doc
			exportDOC(request, response);
		}
	}

	/**
	 * 导出入学通知书
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void exportDOC(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		int userId=Integer.parseInt(request.getParameter("id").trim());
		Users user=userService.getUser(userId, Constants.STUDENT);
		Map<String, Object>dataMap=new HashMap<>();
		//dataMap.put("place", Constants.SOFTWARE_OWNER);
		dataMap.put("fullName", user.getFullName());
		dataMap.put("cyear", Utils.formatYear(user.getSchoolRoll().getComeDate()));
		dataMap.put("cmonth", Utils.formatMonth(user.getSchoolRoll().getComeDate()));
		dataMap.put("lyear", Utils.formatYear(user.getSchoolRoll().getLeaveDate()));
		dataMap.put("lmonth", Utils.formatMonth(user.getSchoolRoll().getLeaveDate()));
		dataMap.put("cmajor", user.getAmcOnUsers().get(0).getMajor().getcName());
		dataMap.put("emajor", user.getAmcOnUsers().get(0).getMajor().geteName());
		dataMap.put("year", Utils.formatYear(new Date()));
		dataMap.put("month", Utils.formatMonth(new Date()));
		dataMap.put("day", Utils.formatDay(new Date()));
		
		ExportUtil exportToWordUtil=new ExportUtil();
		String path=request.getServletContext().getRealPath("/")+"WEB-INF/model";
//		String fileName=path+"/"+Utils.createRandomName()+".doc";
		File file=null;
		InputStream inputStream=null;
		ServletOutputStream servletOutputStream=null;
		try
		{
			request.setCharacterEncoding("UTF-8");
			file=exportToWordUtil.exportToWordOrExcel(dataMap, WORD, DOC_MODEL, path, WORD_TYPE);
			inputStream=new  FileInputStream(file);
			response.setContentType("application/msword");
			response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(ENTRANCE_DOC,"UTF-8"));
			servletOutputStream=response.getOutputStream();
			byte[] buffer=new byte[512];
			int read=-1;
			while((read=inputStream.read(buffer))!=-1)
			{
				servletOutputStream.write(buffer, 0, read);
			}
			servletOutputStream.flush();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			if(inputStream!=null)
			{
				inputStream.close();
			}
			if(servletOutputStream!=null)
			{
				servletOutputStream.close();
			}
			if(file!=null)
			{
				file.delete();
			}
		}
	}

	/**
	 * 批量导出学生信息
	 * @param request
	 * @param response
	 */
	private void exportExcelsToZip(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		ExportToExcelUtil<Export> execlUtil=new ExportToExcelUtil<Export>();
		OutputStream out=null;
		try
		{
			response.setCharacterEncoding("UTF-8");
			out=response.getOutputStream();
			execlUtil.setResponseHeader(response, "留学生信息表".getBytes("UTF-8"));
			String[] headers={"姓名","性别","国内手机号","国外手机号","邮箱","宿舍号","学号","学生卡号","出生日期","出生地点","家庭住址","国籍","用户类型","账号状态"};
			String[] columns={"fullName","gender","gphone","wphone","email","dormitoryNumber","scradNumber","studentNumber","birthday","birthdayplace","homeAddress","nationality","userTypeId","status"};
			List<Export> exports=new ArrayList<>();
			String academyId=request.getParameter("academyId").trim();
			String majorId=request.getParameter("majorId").trim();
			String cclassId=request.getParameter("cclassId").trim();
			if(academyId==null||"".equals(academyId)||majorId==null||"".equals(majorId)||cclassId==null||"".equals(cclassId))
			{
				academyId=majorId=cclassId="0";
			}
			AMCOnUser amc=new AMCOnUser();
			amc.setAcademyId(Integer.parseInt(academyId));
			amc.setMajorId(Integer.parseInt(majorId));
			amc.setClassId(Integer.parseInt(cclassId));
			List<Integer>userIds=amcOnUserDao.getUserIdsByConditions(amc);
			for(int userId:userIds)
			{
				Users user=userService.getUser(userId, Constants.STUDENT);
//				System.out.println(user);
				if(user!=null)
				{
					Export export=new Export();
					export.setFullName(user.getFullName());
					export.setGender(user.getGender());
					export.setUserTypeId(user.getUserTypeId());
					export.setGphone(user.getPhone());
					export.setEmail(user.getEmail());
					export.setRegisterDate(user.getRegisterDate());
					export.setStatus(user.getStatus());
					if(user.getSchoolRoll()!=null)
					{
						export.setDormitoryNumber(user.getSchoolRoll().getDormitoryNumber());
						export.setScradNumber(user.getSchoolRoll().getCardNumber());
						export.setStudentNumber(user.getSchoolRoll().getStudentNumber());
					}
					if(user.getIdentityOnUser()!=null)
					{
						export.setBirthday(user.getIdentityOnUser().getBirthday());
						export.setBirthdayplace(user.getIdentityOnUser().getBirthplace());
						export.setHomeAddress(user.getIdentityOnUser().getHomeAddress());
						export.setWphone(user.getIdentityOnUser().getPhone());
						if(user.getIdentityOnUser().getNationality()!=null)
						{
							export.setNationality(user.getIdentityOnUser().getNationality().getcName());
						}
					}
					exports.add(export);
				}
			}
			
			execlUtil.exportExcel(headers, columns, exports, out, request, "yyyy-MM-dd");
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	/**
	 * 导出签证信息-old
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void exportTable(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int userId=Integer.parseInt(request.getParameter("id").trim());
		Users user=userService.getUser(userId, Constants.STUDENT);
		Map<String, Object>dataMap=new HashMap<>();
		dataMap.put("place", Constants.SOFTWARE_OWNER);
		dataMap.put("fullName", user.getFullName());
		dataMap.put("cName", user.getIdentityOnUser().getNationality().getcName());
		dataMap.put("passportNumber", user.getPassportOnUsers().get(0).getPassport().getPassportNumber());
		dataMap.put("gender", user.getGender()==1?"男":"女");
		dataMap.put("isMarried", user.getIdentityOnUser().getIsMarried()==1?"是":"否");
		dataMap.put("byear", Utils.formatYear(user.getIdentityOnUser().getBirthday()));
		dataMap.put("bmonth", Utils.formatMonth(user.getIdentityOnUser().getBirthday()));
		dataMap.put("bdate", Utils.formatDay(user.getIdentityOnUser().getBirthday()));
		dataMap.put("birthpalce", user.getIdentityOnUser().getBirthplace());
		dataMap.put("homeAddress", user.getIdentityOnUser().getHomeAddress());
		dataMap.put("homePhone", user.getIdentityOnUser().getPhone());
		dataMap.put("educationTypecName", user.getEducationOnUser().getEducationType().getcName());
		dataMap.put("occupation", "学生");
		dataMap.put("majorcName", user.getAmcOnUsers().get(0).getMajor().getcName());
		dataMap.put("cyear", Utils.formatYear(user.getSchoolRoll().getComeDate()));
		dataMap.put("cmonth", Utils.formatMonth(user.getSchoolRoll().getComeDate()));
		dataMap.put("lyear", Utils.formatYear(user.getSchoolRoll().getLeaveDate()));
		dataMap.put("lmonth", Utils.formatMonth(user.getSchoolRoll().getLeaveDate()));
		dataMap.put("studentTypecName", user.getSchoolRoll().getStudentType().getStudentTypecName());
		dataMap.put("ryear", Utils.formatYear(user.getVisaOnUser().getVisa().getRegisterDeadLine()));
		dataMap.put("rmonth", Utils.formatMonth(user.getVisaOnUser().getVisa().getRegisterDeadLine()));
		dataMap.put("rdate", Utils.formatDay(user.getVisaOnUser().getVisa().getRegisterDeadLine()));
		dataMap.put("guaranteecName", user.getVisaOnUser().getVisa().getGuarantee().getGuaranteecName());
		dataMap.put("guaranteePhone", user.getVisaOnUser().getVisa().getGuarantee().getGuaranteePhone());

		ExportUtil exportToWordUtil=new ExportUtil();
		String path=request.getServletContext().getRealPath("/")+"WEB-INF/model";
//		String fileName=path+"/"+Utils.createRandomName()+".doc";
		File file=null;
		InputStream inputStream=null;
		ServletOutputStream servletOutputStream=null;
		try
		{
			request.setCharacterEncoding("UTF-8");
			file=exportToWordUtil.exportToWordOrExcel(dataMap, WORD, VISA_WORD_MODEL, path, WORD_TYPE);
			inputStream=new  FileInputStream(file);
			response.setContentType("application/msword");
			response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode("visa.doc","UTF-8"));
			servletOutputStream=response.getOutputStream();
			byte[] buffer=new byte[512];
			int read=-1;
			while((read=inputStream.read(buffer))!=-1)
			{
				servletOutputStream.write(buffer, 0, read);
			}
			servletOutputStream.flush();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			if(inputStream!=null)
			{
				inputStream.close();
			}
			if(servletOutputStream!=null)
			{
				servletOutputStream.close();
			}
			if(file!=null)
			{
				file.delete();
			}
		}
	}

	/**
	 * export to excel-202table->visa-new
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void exportExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int userId=Integer.parseInt(request.getParameter("id").trim());
		Users user=userService.getUser(userId, Constants.STUDENT);
		Map<String, Object>dataMap=new HashMap<>();
		dataMap.put("place", Constants.SOFTWARE_OWNER);
		dataMap.put("fullName", user.getFullName());
		dataMap.put("cName", user.getIdentityOnUser().getNationality().getcName());
		dataMap.put("passportNumber", user.getPassportOnUsers().get(0).getPassport().getPassportNumber());
		dataMap.put("gender", user.getGender()==1?"男":"女");
		dataMap.put("isMarried", user.getIdentityOnUser().getIsMarried()==1?"已婚":"未婚");
		dataMap.put("byear", Utils.formatYear(user.getIdentityOnUser().getBirthday()));
		dataMap.put("bmonth", Utils.formatMonth(user.getIdentityOnUser().getBirthday()));
		dataMap.put("bdate", Utils.formatDay(user.getIdentityOnUser().getBirthday()));
		dataMap.put("birthpalce", user.getIdentityOnUser().getBirthplace());
		dataMap.put("homeAddress", user.getIdentityOnUser().getHomeAddress());
		dataMap.put("homePhone", user.getIdentityOnUser().getPhone());
		dataMap.put("educationTypecName", user.getEducationOnUser().getEducationType().getcName());
		dataMap.put("occupation", "学生");
		dataMap.put("majorcName", user.getAmcOnUsers().get(0).getMajor().getcName());
		dataMap.put("cyear", Utils.formatYear(user.getSchoolRoll().getComeDate()));
		dataMap.put("cmonth", Utils.formatMonth(user.getSchoolRoll().getComeDate()));
		dataMap.put("lyear", Utils.formatYear(user.getSchoolRoll().getLeaveDate()));
		dataMap.put("lmonth", Utils.formatMonth(user.getSchoolRoll().getLeaveDate()));
		dataMap.put("studentTypecName", user.getSchoolRoll().getStudentType().getStudentTypecName());
		dataMap.put("ryear", Utils.formatYear(user.getVisaOnUser().getVisa().getRegisterDeadLine()));
		dataMap.put("rmonth", Utils.formatMonth(user.getVisaOnUser().getVisa().getRegisterDeadLine()));
		dataMap.put("rdate", Utils.formatDay(user.getVisaOnUser().getVisa().getRegisterDeadLine()));
		dataMap.put("guaranteecName", user.getVisaOnUser().getVisa().getGuarantee().getGuaranteecName());
		dataMap.put("guaranteePhone", user.getVisaOnUser().getVisa().getGuarantee().getGuaranteePhone());

		ExportUtil exportUtil=new ExportUtil();
		String path=request.getServletContext().getRealPath("/")+"WEB-INF/model";
//		String fileName=path+"/"+Utils.createRandomName()+".doc";
		File file=null;
		InputStream inputStream=null;
		ServletOutputStream servletOutputStream=null;
		try
		{
			request.setCharacterEncoding("UTF-8");
			file=exportUtil.exportToWordOrExcel(dataMap, EXCEL, VISA_XLS_MODEL, path, EXCEL_TYPE);
			inputStream=new  FileInputStream(file);
			response.setContentType("application/msword");
			response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(VISA_XLS,"UTF-8"));
			servletOutputStream=response.getOutputStream();
			byte[] buffer=new byte[512];
			int read=-1;
			while((read=inputStream.read(buffer))!=-1)
			{
				servletOutputStream.write(buffer, 0, read);
			}
			servletOutputStream.flush();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			if(inputStream!=null)
			{
				inputStream.close();
			}
			if(servletOutputStream!=null)
			{
				servletOutputStream.close();
			}
			if(file!=null)
			{
				file.delete();
			}
		}
	}
	
	

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
