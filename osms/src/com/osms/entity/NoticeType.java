package com.osms.entity;

import java.io.Serializable;
/**
 * notice type table
 * @author Administrator
 *
 */
public class NoticeType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int noticeTypeId=0;//notice type id
	private String cName=null;//Chinese name
	private String eName=null;//English name
	private int status=0;//current information status
	
	public NoticeType()
	{
		
	}

	public NoticeType(int noticeTypeId, String cName, String eName, int status) {
		this.noticeTypeId = noticeTypeId;
		this.cName = cName;
		this.eName = eName;
		this.status = status;
	}

	public int getNoticeTypeId() {
		return noticeTypeId;
	}

	public void setNoticeTypeId(int noticeTypeId) {
		this.noticeTypeId = noticeTypeId;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "NoticeType [noticeTypeId=" + noticeTypeId + ", cName=" + cName + ", eName=" + eName + ", status="
				+ status + "]";
	}
	
	
}
