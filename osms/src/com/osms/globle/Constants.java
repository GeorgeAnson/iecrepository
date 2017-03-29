package com.osms.globle;

import java.awt.Color;
import java.awt.Font;

/**
 * 常量类，用于管理常量参数
 */
public class Constants {
	/**
	 * 验证码长度
	 */
	public static final Integer IMAGE_WIDTH=90;
	/**
	 * 验证码宽度
	 */
	public static final Integer IMAGE_HEIGHT=30;
	/**
	 * 验证码字体
	 */
	public static Font[] codeFont={new Font("Times New Roman",Font.PLAIN,20),
			new Font("Times New Roman",Font.PLAIN,20),new Font("Times New Roman",Font.PLAIN,20),
			new Font("Times New Roman",Font.PLAIN,20),new Font("Times New Roman",Font.PLAIN,20)};
	/**
	 * 验证码每个字符颜色
	 */
	public static Color[] color={Color.BLACK,Color.BLUE,Color.RED,Color.DARK_GRAY,Color.GREEN};
	/**
	 * 验证码字库
	 */
	public static final String IMAGE_CHAR="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	/**
	 * session中的用户
	 */
	public static String USER = "user";
	/**
	 * 验证码
	 */
	public static String CHECK_NUMBER_NAME = "identify_code";
	/**
	 * 错误提示信息
	 */
	public static String ERROR= "error_msg";
	/**
	 * 登录页访问次数
	 */
	public static String LOGIN_PAGE = "login.jsp";
	/**
	 * 保存在session中的用户活跃时长统计
	 */
	public static String ACCESS_RECORD_IN_SESSION = "accessList";
	/**
	 * 保存在ServletContext中的用户列表
	 */
	public static String ONLINE_USERS = "com.osms.listener.OnLineListener.Map";
	/**
	 * 用户类别配置
	 */
	/**
	 * 学生类型
	 */
	public static String STUDENT="4";
	/**
	 * 教师类型
	 */
	public static String TEACHER="3";
	/**
	 * 二级管理
	 */
	public static String ADMIN="2";
	/**
	 * 最高管理
	 */
	public static String ROOT="1";
	
	/**
	 * 删除操作关键字操作类型
	 */
   	public static String AND=" AND ";
   	/**
   	 * 删除操作关键字类型
   	 */
	public static String OR=" OR ";
	/**
	 * get all users,those users exist whose status is 1
	 */
	public static final String STATUS_OK = "1";
	/**
	 * academy
	 */
	public static final String ACADEMY = "academy";
	/**
	 * major
	 */
	public static final String MAJOR = "major";
	/**
	 * cclass
	 */
	public static final String CCLASS = "cclass";
	
	public static final String SOFTWARE_OWNER = "宁波工程学院";
	
	public static final String LIMIT_PER_PAGE_DATA = "10";
	
	public static final String SEARCH_INIT_NUM = "1";
	
	public static final String INIT = "init";
	
	public static final String ADD = "add";
	
	public static final String SEARCH = "search";
	
	public static final String EXCEL = "excel";
	
	public static final String WORD = "word";
	
	public static final String DELETE = "del";
	
	public static final String EDIT = "edit";
	
	public static final String PUBLISH = "publish";
	
	public static final String UPDATE = "update";
	
	public static final String INIT_CONFIGURATION = "initConfig";
	
	public static final String GUARANTEE = "guarantee";
	
	public static final String FIND_IDENTITY = "findidentity";
	
	public static final String INIT_ADD = "initAdd";
	
	public static final String EXCELS = "excels";
	public static final String WORDS = "words";
	
}
