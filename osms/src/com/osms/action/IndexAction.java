package com.osms.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osms.dao.IntroduceDao;
import com.osms.entity.Introduce;
import com.osms.entity.Users;
import com.osms.globle.Constants;

@Component
public class IndexAction extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IntroduceDao introduceDao;

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
			initPage(request, response);
		}
	}
	
	private void initPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Introduce introduce=introduceDao.getIntroduceByIntroduceId(1);
		if(introduce!=null)
		{
			request.getSession().setAttribute(Constants.INTRODUCE, introduce.getIntroduceContent());
		}
		request.getRequestDispatcher("WEB-INF/views/index.jsp").forward(request, response);	
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
