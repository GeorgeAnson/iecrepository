package com.osms.bean;

import java.io.Serializable;
import java.util.Date;

public class LoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 登录用户的id
	private String loginName=null;
	// 登录的ip地址
	private String ip=null;
	// 登录时间
	private Date loginTime=null;
	
	private int uuid=0;
	private String cName=null;
	private String eName=null;
	
	public int getUuid() {
		return uuid;
	}


	public void setUuid(int uuid) {
		this.uuid = uuid;
	}


	public String getcName() {
		return cName;
	}


	public void setcName(String cName) {
		this.cName = cName;
	}


	public String geteName() {
		return eName;
	}


	public void seteName(String eName) {
		this.eName = eName;
	}


	public String getLoginName() {
		return loginName;
	}
	
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	
	public String getIp() {
		return ip;
	}
	
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	public Date getLoginTime() {
		return loginTime;
	}
	
	
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
}
