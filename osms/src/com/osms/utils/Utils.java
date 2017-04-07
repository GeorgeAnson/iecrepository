package com.osms.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



/**
 * ��̬��
 * �����࣬Ϊ�������ṩ����
 * @author Administrator
 *
 */
public class Utils {

	
	/**
	 * ��������Ƿ���������ʽ
	 * @param content �������ַ���
	 * @return
	 */
	public static boolean isEmail(String email)
	{
		String rule = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern pattern=Pattern.compile(rule);
		Matcher matcher=pattern.matcher(email);
		return matcher.matches();
	}
	
	
	
	
	/**
	 * ����ֻ���ʽ�Ƿ���ȷ
	 * @param phone �������ַ���
	 * @return
	 */
	public static boolean isPhone(String phone)
	{
		String rule="(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}";
		Pattern pattern=Pattern.compile(rule);
		Matcher matcher=pattern.matcher(phone);
		return matcher.matches();
	}
	
	
	/**
	 * ���ַ�������MD5����
	 */
	public static String toMD5(String secur)
	{
		String result=null;
		try 
		{
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.update(secur.getBytes());
			result=new BigInteger(1, md.digest()).toString(32);
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * ��ʱ���ַ���ת����sql��Date����
	 * 
	 * @param ymd
	 * 			yyyy-MM-dd
	 * @return
	 * 			sql��Date����
	 */
	public static final Date stringToDate(String ymd)
	{
		Date sqlDate=null;
		try 
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date;
			date = sdf.parse(ymd);
			sqlDate=new Date(date.getTime());
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return sqlDate;
	}
	
	/**
	 * change String to exact date
	 * @param ymdhms
	 * 		yyyy-dd-MM HH:mm:SS
	 * @return
	 *  sql Date
	 */
	public static final Date stringToExactDate(String ymdhms)
	{
		Date sqlDate=null;
		try 
		{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
			java.util.Date date;
			date = sdf.parse(ymdhms);
			sqlDate=new Date(date.getTime());
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return sqlDate;
	}
	
	
	/**
	 * ����������
	 * @return
	 * 		String
	 */
	public static String createRandomName()
	{
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
		return dateFormat.format(new java.util.Date())+(int)(Math.random()*89+10);
	}
	/**
	 * create random path
	 * @return
	 */
	public static String createRandomPath()
	{
		DateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(new java.util.Date());
	}
	
	
	/**
	 * analysis form
	 * @param request
	 * @param parmas
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<byte[]> analysisForm(HttpServletRequest request, Map<String, String> parmas) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items = null;
		List<byte[]> images = new ArrayList<byte[]>();
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} // ����request����
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (item.isFormField()) { // ����Ǳ��� �����Ƿ��ļ��ϴ�Ԫ��
				try {
					String name=item.getFieldName().trim();
					String value=item.getString("utf-8").trim();
					parmas.put(name, value.trim());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				String fieldName = item.getFieldName().trim(); // �ļ�����name���Ե�ֵ
				try {
					BufferedImage bi = ImageIO.read(item.getInputStream());
					if(bi!=null)
					{
						parmas.put(fieldName, item.getString("utf-8").trim());
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(bi, "png", baos);
						images.add(baos.toByteArray());
						baos.close();
						System.out.println("�����ϴ������ļ���" + baos.toByteArray());
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return images;
	}
	
	
	/**
	 * save passport image
	 * @param images
	 * @param request
	 * @return
	 */
	public static List<String> savePic(List<byte[]> images, String parentPath) {
		// TODO Auto-generated method stub
		List<String> filenames=new ArrayList<String>();
		Lock lock = new ReentrantLock();
		for(byte[] image:images)
		{
			lock.lock();
			try
			{
				//Globle.uploadPics(userId, image);
				String filename=Utils.createRandomName()+".png";
				String path=parentPath+"\\"+filename;
				Utils.getFile(image, path);
				filenames.add(filename);
			} finally 
			{
				lock.unlock();
			}
		}
		return filenames;
	}
	
	/**
	 * ����request���� ��ȡ�ϴ���ͼƬ�ļ�
	 */
	@SuppressWarnings("unchecked")
	public static byte[] analysisForm(HttpServletRequest request) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items = null;
		byte[] image = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} // ����request����
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (item.isFormField()) { // ����Ǳ��� �����Ƿ��ļ��ϴ�Ԫ��
				continue;
			} else {
				String fieldName = item.getFieldName(); // �ļ�����name���Ե�ֵ
				if ("passportPage1".equals(fieldName)||"passportPage2".equals(fieldName)||"passportPage3".equals(fieldName)) {
					try {
						BufferedImage bi = ImageIO.read(item.getInputStream());
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(bi, "png", baos);
						image = baos.toByteArray();
						baos.close();
						System.out.println("�����ϴ������ļ���" + image);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		return image;
	}
	
	/**
	 * ����byte���飬�����ļ�
	 * @param path 
	 */
	public static void getFile(byte[] bfile, String filePath) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists()&&dir.isDirectory()) {// �ж��ļ�Ŀ¼�Ƿ����
				dir.mkdirs();
			}
			System.out.println(filePath);
			file = new File(filePath);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}


	/**
	 * format year
	 * @param birthday
	 * @return
	 */
	public static String formatYear(java.util.Date date) {
		// TODO Auto-generated method stub
		return new SimpleDateFormat("yyyy").format(date);
	}



	/**
	 * format month
	 * @param date
	 * @return
	 */
	public static String formatMonth(java.util.Date date) {
		// TODO Auto-generated method stub
		return new SimpleDateFormat("MM").format(date);
	}



	/**
	 * format day
	 * @param date
	 * @return
	 */
	public static String formatDay(java.util.Date date) {
		// TODO Auto-generated method stub
		return new SimpleDateFormat("dd").format(date);
	}
	
}
