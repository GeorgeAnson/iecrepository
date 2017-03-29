package com.osms.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osms.dao.UserDao;
import com.osms.entity.Users;
import com.osms.globle.Constants;
import com.osms.utils.Utils;

@Component
public class PwdAction extends HttpServlet{

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
		if(Constants.INIT.toLowerCase().equals(type.toLowerCase()))
		{
			request.getRequestDispatcher("WEB-INF/views/pwd.jsp").forward(request, response);
		}
		//update password
		if(Constants.UPDATE.toLowerCase().equals(type.toLowerCase()))
		{
			updatePwd(request, response);
		}
		
		
	}
	
	private void updatePwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id=request.getParameter("id").trim();
		String oldPwd=request.getParameter("oldPwd").trim();
		String newPwd=request.getParameter("newPwd").trim();
		//id the user is offline
		if(id==null || "".equals(id))
		{
			request.getSession().setAttribute(Constants.ERROR,"you are offline, please login first.");
			request.getRequestDispatcher("/WEB-INF/views/pwd.jsp").forward(request, response);
			return;
		}
		//if the new password is the same as old one
		if(newPwd.equals(oldPwd))
		{
			request.getSession().setAttribute(Constants.ERROR,"new passwrod is the same to old password.");
			request.getRequestDispatcher("/WEB-INF/views/pwd.jsp").forward(request, response);
			return;
		}
		//get user information and set new password
		Users user=userDao.getUserByUserId(Integer.parseInt(id));
		System.out.println(user);
		user.setPassword(Utils.toMD5(newPwd));
		userDao.update(user);
		request.getSession().setAttribute(Constants.ERROR, "");
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
