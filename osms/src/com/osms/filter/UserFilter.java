package com.osms.filter;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.osms.entity.Users;
import com.osms.globle.Constants;


/**
 * check user login status
 */
public class UserFilter implements Filter {

	// ok url
	private String pass = null;
	// bad url
	private String error_url = null;
	// save the users' status string
	private String userConstants = null;
	/**
	 * to get parma from config file
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		pass = config.getInitParameter("pass");
		error_url = config.getInitParameter("error_url");
		userConstants = config.getInitParameter("userConstants");
		System.out.println("init ; "+error_url+"  "+userConstants);
	}

	/*
	 * check action
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		System.out.println(req.getRequestURI()+"; the request uri");

		//check whether the url is belong to passed urls
		if (isCheckAble(req)) {
			chain.doFilter(req, res);
			return;
		}

		//check whether this people is online
		if (checkUser(req) && isAvailable(req)) {
			chain.doFilter(req, res);
			return;
		}

		//if the error_url is null
		if (error_url == null) {
			chain.doFilter(req, res);
			return;
		}
		res.sendRedirect(req.getContextPath()+error_url);
	}

	/**
	 * check whether the url is belong to passed urls
	 */
	private boolean isCheckAble(HttpServletRequest request) {
		if (pass == null)
			return false;

		String url = request.getRequestURI();
		String[] permitParams = pass.split("[;]");

		for (String permit : permitParams) {
			if (isURLPassable(request.getContextPath() + permit, url, request)){
				return true;
			}
		}
		return false;
	}

	/**
	 * check whether the url should be passed
	 *
	 * @param permit
	 *
	 * @param url
	 *
	 * @return is pass
	 */
	private boolean isURLPassable(String permit, String url, HttpServletRequest request) {
		try {

			String reg = "";
			if (permit.equals(request.getContextPath() + "/"))
				reg = "^" + permit + "$";
			else
				reg = "^" + permit + "$";
			Pattern p = Pattern.compile(reg);
			return p.matcher(url).matches();
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isAvailable(HttpServletRequest req) throws ServletException, IOException {
		ServletContext context = req.getServletContext();
		@SuppressWarnings("unchecked")
		Map<String, String> userMap = (Map<String, String>) context.getAttribute(Constants.ONLINE_USERS);

		HttpSession session = req.getSession();
		Users user = (Users) session.getAttribute(Constants.USER);
		String userid = user.getUserId()+"";

		if(userMap.get(userid) != null && userMap.get(userid) != session.getId()){
			session.setAttribute(Constants.ERROR, "your account is login at other place, you are offline");
			System.out.println("ivalide login, to logout");
			session.removeAttribute(Constants.USER);
			return false;
		}
		return true;
	}


	/**
	 * check whether this people is online
	 *
	 * @param request
	 *            request
	 * @return
	 * 		is online
	 */
	private boolean checkUser(HttpServletRequest request) {
		Object o = request.getSession().getAttribute(userConstants);
		return o == null ? false : true;
	}


	@Override
	public void destroy() {
	}
}
