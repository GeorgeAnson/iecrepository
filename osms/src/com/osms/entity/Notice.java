package com.osms.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * notice table
 * @author Administrator
 *
 */
public class Notice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id=0;//id
	private int userId=0;//user id
	private int noticeTypeId=0;//notice type id
	private String title=null;//notice title
	private String content=null;//notice content
	private String writer=null;//writer
	private String downLoadPath=null;//download link
	private Date publishDate=null;//publish Date
	private int status=0;//current information status
	
	
	private Users user=null;//user object
	private NoticeType noticeType=null;//notice type object
	
	
	public Notice() {
		// TODO Auto-generated constructor stub
	}


	public Notice(int id, int userId, int noticeTypeId, String title, String content, String writer,
			String downLoadPath, Date publishDate, int status, Users user, NoticeType noticeType) {
		this.id = id;
		this.userId = userId;
		this.noticeTypeId = noticeTypeId;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.downLoadPath = downLoadPath;
		this.publishDate = publishDate;
		this.status = status;
		this.user = user;
		this.noticeType = noticeType;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getNoticeTypeId() {
		return noticeTypeId;
	}


	public void setNoticeTypeId(int noticeTypeId) {
		this.noticeTypeId = noticeTypeId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getWriter() {
		return writer;
	}


	public void setWriter(String writer) {
		this.writer = writer;
	}


	public String getDownLoadPath() {
		return downLoadPath;
	}


	public void setDownLoadPath(String downLoadPath) {
		this.downLoadPath = downLoadPath;
	}


	public Date getPublishDate() {
		return publishDate;
	}


	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public Users getUser() {
		return user;
	}


	public void setUser(Users user) {
		this.user = user;
	}


	public NoticeType getNoticeType() {
		return noticeType;
	}


	public void setNoticeType(NoticeType noticeType) {
		this.noticeType = noticeType;
	}


	@Override
	public String toString() {
		return "Notice [id=" + id + ", userId=" + userId + ", noticeTypeId=" + noticeTypeId + ", title=" + title
				+ ", content=" + content + ", writer=" + writer + ", downLoadPath=" + downLoadPath + ", publishDate="
				+ publishDate + ", status=" + status + ", user=" + user + ", noticeType=" + noticeType + "]";
	}
	
	
	
	
}
