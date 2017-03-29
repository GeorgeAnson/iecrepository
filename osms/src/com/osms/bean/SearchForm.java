package com.osms.bean;

import java.io.Serializable;
import java.util.Map;

public class SearchForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int academyId=0;
	private int majorId=0;
	private int cclassId=0;
	private int page=0;
	private int pages=0;
	private String schoolYear=null;
	private int thsSemester=0;
	private double totalNeeds=0;
	private double totalPaid=0;
	private Map<Integer, Double> moneyNeedsFortypes;
	private int userId=0;
	
	public SearchForm() {
		// TODO Auto-generated constructor stub
	}

	public int getAcademyId() {
		return academyId;
	}

	public void setAcademyId(int academyId) {
		this.academyId = academyId;
	}

	public int getMajorId() {
		return majorId;
	}

	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}

	public int getCclassId() {
		return cclassId;
	}

	public void setCclassId(int cclassId) {
		this.cclassId = cclassId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public int getThsSemester() {
		return thsSemester;
	}

	public void setThsSemester(int thsSemester) {
		this.thsSemester = thsSemester;
	}
	

	public double getTotalNeeds() {
		return totalNeeds;
	}

	public void setTotalNeeds(double totalNeeds) {
		this.totalNeeds = totalNeeds;
	}

	public double getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}

	public Map<Integer, Double> getMoneyNeedsFortypes() {
		return moneyNeedsFortypes;
	}

	public void setMoneyNeedsFortypes(Map<Integer, Double> moneyNeedsFortypes) {
		this.moneyNeedsFortypes = moneyNeedsFortypes;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
