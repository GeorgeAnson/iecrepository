package com.osms.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osms.dao.UserDao;
import com.osms.entity.Users;
import com.osms.globle.Constants;
import com.osms.utils.ExportToExcelUtil;

@Component
public class ExportAction extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	UserDao userDao;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type=request.getParameter("type").trim();
		if(Constants.EXCEL.toLowerCase().equals(type.toLowerCase()))
		{
			//new bean for excel
			exportExcel(request, response);
		}
		if(Constants.EXCELS.toLowerCase().equals(type.toLowerCase()))
		{
			//zip excel
			exportExcelsToZip(request, response);
		}
		if(Constants.WORD.toLowerCase().equals(type.toLowerCase()))
		{
			//new bean for word
			exportWord(request, response);
		}
		if(Constants.WORDS.toLowerCase().equals(type.toLowerCase()))
		{
			//zip word
			exportWordsToZip(request, response);
		}
	}
	
	private void exportWordsToZip(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void exportExcelsToZip(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		ExportToExcelUtil<Users> execlUtil=new ExportToExcelUtil<Users>();
		int total=request.getParameter("total")==null?10:Integer.parseInt(request.getParameter("total"));
		OutputStream out=null;
		try
		{
			out=response.getOutputStream();
			execlUtil.setResponseHeader(response, "留学生信息表");
			String[] headers={};
			String[] columns={};
			List<Users> users=userDao.getUserList(getQueryKeyWords());
			execlUtil.exportExcel(headers, columns, users, out, request, "");
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

	private List<String> getQueryKeyWords() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * export to word
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void exportWord(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * export to excel
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void exportExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
