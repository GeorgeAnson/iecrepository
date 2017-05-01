//package com.osms.action;
//
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.imageio.ImageIO;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.osms.entity.PassportOnUser;
//import com.osms.entity.Users;
//import com.osms.globle.Constants;
//import com.osms.service.PassportOnUserService;
//import com.osms.utils.Utils;
//
//@Component
//public class PicAction extends HttpServlet{
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	@Autowired
//	PassportOnUserService passportOnUserService;
//	
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		Users user=(Users) request.getSession().getAttribute(Constants.USER);
//		if(user==null)
//		{
//			response.sendRedirect(request.getContextPath()+"/login.jsp");
//			return;
//		}
//		String type=request.getParameter("type").trim();
//		if(Constants.UPDATE.toLowerCase().equals(type.toLowerCase()))
//		{
//			savePic(request, response);
//		}
//	}
//	
//	
//	private void savePic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		
//		int userId=Integer.parseInt(request.getParameter("userId").trim());
//		int passportId=Integer.parseInt(request.getParameter("passportId").trim());
//		BufferedImage bi = ImageIO.read(request.getInputStream());
//		if(bi!=null)
//		{
////			parmas.put(fieldName, item.getString("utf-8").trim());
////			ByteArrayOutputStream baos = new ByteArrayOutputStream();
////			ImageIO.write(bi, "png", baos);
////			images.add(baos.toByteArray());
////			baos.close();
////			System.out.println("�����ϴ������ļ���" + baos.toByteArray());
//		}
//		
//		
////		Map<String, String> parmas=new HashMap<String, String>();
////		List<byte[]> images=Utils.analysisForm(request, parmas);
////		//save picture
////		String parentPath=request.getSession().getServletContext().getRealPath("\\Passports");
////		
////		List<String> fileNames=Utils.savePic(images, parentPath);
////		List<PassportOnUser> passportOnUsers=new ArrayList<>();
////		for(String fileName:fileNames)
////		{
////			System.out.println(fileName);
////			PassportOnUser passportOnUser=new PassportOnUser();
////			passportOnUser.setPassportPagePath(fileName);
////			for(Entry<String, String> entry:parmas.entrySet())
////			{
////				System.out.println(entry.getValue());
////				if(entry.getKey().toLowerCase().equals("userId".toLowerCase()))
////				{
////					passportOnUser.setUserId(Integer.parseInt(entry.getValue()));
////				}
////				if(entry.getKey().toLowerCase().equals("passportId".toLowerCase()))
////				{
////					passportOnUser.setPassportId(Integer.parseInt(entry.getValue()));
////				}
////			}
////			passportOnUser.setStatus(Integer.parseInt(Constants.STATUS_OK));
////			passportOnUsers.add(passportOnUser);
////			System.out.println(passportOnUser);
////		}
////		passportOnUserService.save(passportOnUsers);
//		//request.getRequestDispatcher("/WEB-INF/views/studentInfo.jsp").forward(request, response);
//	}
//
//
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doPost(request, response);
//	}
//	
//}
