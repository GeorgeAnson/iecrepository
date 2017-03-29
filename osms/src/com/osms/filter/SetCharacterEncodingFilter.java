package com.osms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharacterEncodingFilter implements Filter{

	//编码格式
	protected String encoding=null;
	
	@Override
	public void destroy() {
		this.encoding=null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 判断当前请求是否单独设置编码格式
		if(request.getCharacterEncoding()==null)
		{
			//获取默认编码格式
			String encoding=this.encoding;
			if(encoding!=null)
			{
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
			}
		}
//		System.out.println("设置编码为utf-8编码格式");
		//向下一个过滤器转发
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		//获取初始化参数
		this.encoding=filterConfig.getInitParameter("encoding");
	}

}
