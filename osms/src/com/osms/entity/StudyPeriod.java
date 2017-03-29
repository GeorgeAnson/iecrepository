package com.osms.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * study period table
 * @author Administrator
 *
 */
public class StudyPeriod implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int studyPeriodId=0;//study period id
	private Date startDate=null;//start date
	private Date endDate=null;//end date
	private int status=0;//current information status
	
	public StudyPeriod()
	{
		
	}

	

	public StudyPeriod(int studyPeriodId, Date startDate, Date endDate, int status) {
		this.studyPeriodId = studyPeriodId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}



	public int getStudyPeriodId() {
		return studyPeriodId;
	}



	public void setStudyPeriodId(int studyPeriodId) {
		this.studyPeriodId = studyPeriodId;
	}



	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	@Override
	public String toString() {
		return "StudyPeriod [studyPeriodId=" + studyPeriodId + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", status=" + status + "]";
	}

}
