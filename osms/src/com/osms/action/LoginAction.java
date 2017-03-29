package com.osms.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osms.dao.UserDao;
import com.osms.dao.UserTypeDao;
import com.osms.entity.UserType;
import com.osms.entity.Users;
import com.osms.globle.Constants;
import com.osms.bean.LoginBean;
import com.osms.utils.Utils;


@Component
public class LoginAction extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	UserDao userDao;
	
	@Autowired
	UserTypeDao userTypeDao;
	
	String OK_URL=null;
	String ERROR_URL=null;
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		OK_URL=super.getServletContext().getInitParameter("index_url");
		ERROR_URL=super.getServletConfig().getInitParameter("error_url");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//coding
		String condition=request.getParameter("email").trim();
		String password=request.getParameter("password").trim();
		
		//if contain null
		if(condition==null||"".equals(condition)||password==null||"".equals(password))
		{
			request.setAttribute(Constants.ERROR, "both username and password are not allowed null");
			request.getRequestDispatcher("/").forward(request, response);
			return;
		}
		//check user identity
		Users user = userDao.getUserByCondition(condition, 1);
		System.out.println(user);
		if(user==null||(user!=null && !Utils.toMD5(password).equals(user.getPassword())))
		{
			request.setAttribute(Constants.ERROR, "username or password error!");
			request.getRequestDispatcher("/").forward(request, response);
			return;
		}
		
		//login success
		forward(request, response, user);
	}
	
	//login success
	private void forward(HttpServletRequest request, HttpServletResponse response, Users user)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveUserInfo(request, user);
		request.getRequestDispatcher(OK_URL).forward(request, response);
	}

	//save user information
	private void saveUserInfo(HttpServletRequest request, Users user) {
		// TODO Auto-generated method stub
//		request.getSession().invalidate();
		HttpSession session =request.getSession();
		LoginBean loginBean=new LoginBean();
		
		loginBean.setUuid(user.getUserId());
		loginBean.setLoginName(user.getFullName());
		UserType userType=userTypeDao.getUserTypeByUserTypeId(user.getUserTypeId());
		user.setUserType(userType);
		loginBean.setcName(userType.getcName());
		loginBean.seteName(userType.geteName());
		session.setAttribute("loginBean", loginBean);
		
		session.setAttribute(Constants.USER, user);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
