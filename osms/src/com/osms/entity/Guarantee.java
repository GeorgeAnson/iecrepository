package com.osms.entity;

import java.io.Serializable;

public class Guarantee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int guaranteeId=0;
	private String guaranteecName=null;
	private String guaranteeeName=null;
	private String guaranteePhone=null;
	private int guaranteeStatus=0;
	
	public Guarantee() {
		// TODO Auto-generated constructor stub
	}

	public Guarantee(int guaranteeId, String guaranteecName, String guaranteeeName, String guaranteePhone,
			int guaranteeStatus) {
		this.guaranteeId = guaranteeId;
		this.guaranteecName = guaranteecName;
		this.guaranteeeName = guaranteeeName;
		this.guaranteePhone = guaranteePhone;
		this.guaranteeStatus = guaranteeStatus;
	}

	public int getGuaranteeId() {
		return guaranteeId;
	}

	public void setGuaranteeId(int guaranteeId) {
		this.guaranteeId = guaranteeId;
	}

	public String getGuaranteecName() {
		return guaranteecName;
	}

	public void setGuaranteecName(String guaranteecName) {
		this.guaranteecName = guaranteecName;
	}

	public String getGuaranteeeName() {
		return guaranteeeName;
	}

	public void setGuaranteeeName(String guaranteeeName) {
		this.guaranteeeName = guaranteeeName;
	}

	public String getGuaranteePhone() {
		return guaranteePhone;
	}

	public void setGuaranteePhone(String guaranteePhone) {
		this.guaranteePhone = guaranteePhone;
	}

	public int getGuaranteeStatus() {
		return guaranteeStatus;
	}

	public void setGuaranteeStatus(int guaranteeStatus) {
		this.guaranteeStatus = guaranteeStatus;
	}

	@Override
	public String toString() {
		return "Guarantee [guaranteeId=" + guaranteeId + ", guaranteecName=" + guaranteecName + ", guaranteeeName="
				+ guaranteeeName + ", guaranteePhone=" + guaranteePhone + ", guaranteeStatus=" + guaranteeStatus + "]";
	}
	
	
}
