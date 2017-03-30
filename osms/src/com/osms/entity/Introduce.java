package com.osms.entity;

import java.io.Serializable;
import java.util.Date;

public class Introduce implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int introduceId=0;
	private String introduceContent=null;
	private Date publishDate=null;
	private int introduceStatus=0;
	
	public Introduce() {
		// TODO Auto-generated constructor stub
	}

	public int getIntroduceId() {
		return introduceId;
	}

	public void setIntroduceId(int introduceId) {
		this.introduceId = introduceId;
	}

	public String getIntroduceContent() {
		return introduceContent;
	}

	public void setIntroduceContent(String introduceContent) {
		this.introduceContent = introduceContent;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public int getIntroduceStatus() {
		return introduceStatus;
	}

	public void setIntroduceStatus(int introduceStatus) {
		this.introduceStatus = introduceStatus;
	}

	@Override
	public String toString() {
		return "Introduce [introduceId=" + introduceId + ", introduceContent=" + introduceContent + ", publishDate="
				+ publishDate + ", introduceStatus=" + introduceStatus + "]";
	}
}
