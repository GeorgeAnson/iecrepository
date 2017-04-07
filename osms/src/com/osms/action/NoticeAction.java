package com.osms.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osms.dao.NoticeDao;
import com.osms.dao.NoticeTypeDao;
import com.osms.dao.UserDao;
import com.osms.entity.Notice;
import com.osms.entity.NoticeType;
import com.osms.entity.Users;
import com.osms.globle.Constants;
import com.osms.utils.Utils;


@Component
public class NoticeAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	UserDao userDao;
	
	@Autowired
	NoticeTypeDao noticeTypeDao;
	
	@Autowired
	NoticeDao noticeDao;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type=request.getParameter("type").trim();
		if(Constants.INIT.toLowerCase().equals(type.toLowerCase()))
		{
			initNoticePage(request, response);
		}
		
		if(Constants.EDIT.toLowerCase().equals(type.toLowerCase()))
		{
			initUEditorPage(request, response);
		}
		
		if(Constants.PUBLISH.toLowerCase().equals(type.toLowerCase()))
		{
			saveNotice(request, response);
		}
		
		if(Constants.SEARCH.toLowerCase().equals(type.toLowerCase()))
		{
			searchNotice(request, response);
		}
	}
	
	/**
	 * search a notice obejct
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void searchNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id=request.getParameter("id").trim();
		if(id==null||"".equals(id))
		{
			request.getSession().setAttribute(Constants.ERROR, "搜索出现错误，请重试");
			request.getRequestDispatcher("/WEB-INF/views/notice.jsp").forward(request, response);
			return;
		}
		
		Notice notice=noticeDao.getNoticeByNoticeId(Integer.parseInt(id));
		request.getSession().setAttribute("currentNotice", notice);
		request.getRequestDispatcher("/WEB-INF/views/notice.jsp").forward(request, response);
	}

	/**
	 * instance notice page
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void initNoticePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Notice> notices=new ArrayList<Notice>();
		notices=noticeDao.getAllNotice();
		
		if(notices!=null)
		{
			List<NoticeType> noticeTypes=noticeTypeDao.getAllNoticeType();
			for(Notice notice:notices)
			{
				for(NoticeType noticeType:noticeTypes)
				{
					if(notice.getNoticeTypeId()==noticeType.getNoticeTypeId())
					{
						notice.setNoticeType(noticeType);
					}
				}
			}
		}
		//new notice
		Notice notice=null;
		if(!notices.isEmpty()&&notices.size()>0)
		{
			notice=noticeDao.getNoticeByNoticeId(notices.get(0).getId());
		}
		//history notices
		request.getSession().setAttribute("currentNotice", notice);
		request.getSession().setAttribute("notices", notices);
		request.getRequestDispatcher("/WEB-INF/views/notice.jsp").forward(request, response);
	}

	/**
	 * save notice object
	 * @param request
	 * @param response 
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void saveNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String noticeTitle=request.getParameter("noticeTitle").trim();
		String userId=request.getParameter("userId").trim();
		String noticeTypeId=request.getParameter("noitceTypeId").trim();
		
		if(noticeTitle==null||"".equals(noticeTitle))
		{
			request.getSession().setAttribute(Constants.ERROR, "请填写公告标题");
			request.getRequestDispatcher("/WEB-INF/views/admin/editNotice.jsp").forward(request, response);
			return;
		}
		
		if(noticeTypeId==null||"".equals(noticeTypeId)
				||userId==null||"".equals(userId))
		{
			request.getSession().setAttribute(Constants.ERROR, "必须填写发布人以及通知类型");
			request.getRequestDispatcher("/WEB-INF/views/admin/editNotice.jsp").forward(request, response);
			return;
		}
		String ccontent=request.getParameter("ccontent").trim();
		
//		System.out.println(ccontent);
		Notice notice=new Notice();
		notice.setUserId(Integer.parseInt(userId));
		Users user=userDao.getUserByUserId(Integer.parseInt(userId));
		notice.setWriter(user.getFullName());
		notice.setTitle(noticeTitle);
		notice.setNoticeTypeId(Integer.parseInt(noticeTypeId));
		notice.setContent(ccontent);
		notice.setPublishDate(Utils.stringToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
		notice.setStatus(1);
		noticeDao.save(notice);
		response.sendRedirect(request.getContextPath()+"/notice.html?type=init");
	}

	/**
	 * instance UEditor page,get writer and notice type
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void initUEditorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Users> users=userDao.getUserByUserTypeId(Integer.parseInt(Constants.ROOT));
		List<NoticeType> noticeTypes=noticeTypeDao.getAllNoticeType();
		
		request.getSession().setAttribute("writers", users);
		request.getSession().setAttribute("noticeTypes", noticeTypes);
		request.getRequestDispatcher("/WEB-INF/views/admin/editNotice.jsp").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
