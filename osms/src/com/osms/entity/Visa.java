package com.osms.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * visa table
 * @author Administrator
 *
 */
public class Visa implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int visaId=0;//visa id
	private int guaranteeId=0;
	private Date registerDeadLine=null;//register deadline
	private String intermediaryName=null;//intermediary name
	private String intermediaryPhone=null;//intermediary phone number
//	private Date approvalDate=null;//approval date
//	private Date visaDueDate=null;//visa due date
	private int status=0;//current information status
	
	
	private Guarantee guarantee=null;
	
	public Visa()
	{
		
	}

	public Visa(int visaId, int guaranteeId, Date registerDeadLine, String intermediaryName, String intermediaryPhone,
			int status, Guarantee guarantee) {
		super();
		this.visaId = visaId;
		this.guaranteeId = guaranteeId;
		this.registerDeadLine = registerDeadLine;
		this.intermediaryName = intermediaryName;
		this.intermediaryPhone = intermediaryPhone;
		this.status = status;
		this.guarantee = guarantee;
	}

	public int getVisaId() {
		return visaId;
	}

	public void setVisaId(int visaId) {
		this.visaId = visaId;
	}

	public int getGuaranteeId() {
		return guaranteeId;
	}

	public void setGuaranteeId(int guaranteeId) {
		this.guaranteeId = guaranteeId;
	}

	public Date getRegisterDeadLine() {
		return registerDeadLine;
	}

	public void setRegisterDeadLine(Date registerDeadLine) {
		this.registerDeadLine = registerDeadLine;
	}

	public String getIntermediaryName() {
		return intermediaryName;
	}

	public void setIntermediaryName(String intermediaryName) {
		this.intermediaryName = intermediaryName;
	}

	public String getIntermediaryPhone() {
		return intermediaryPhone;
	}

	public void setIntermediaryPhone(String intermediaryPhone) {
		this.intermediaryPhone = intermediaryPhone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Guarantee getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(Guarantee guarantee) {
		this.guarantee = guarantee;
	}

	@Override
	public String toString() {
		return "Visa [visaId=" + visaId + ", guaranteeId=" + guaranteeId + ", registerDeadLine=" + registerDeadLine
				+ ", intermediaryName=" + intermediaryName + ", intermediaryPhone=" + intermediaryPhone + ", status="
				+ status + ", guarantee=" + guarantee + "]";
	}

}
