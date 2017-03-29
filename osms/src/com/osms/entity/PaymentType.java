package com.osms.entity;

import java.io.Serializable;

/**
 * payment type table
 * @author Administrator
 *
 */
public class PaymentType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int paymentTypeId=0;//payment type id
	private String cName=null;//Chinese name
	private String eName=null;//English name
	private int status=0;//current information status
	
	public PaymentType()
	{
		
	}

	public PaymentType(int paymentTypeId, String cName, String eName, int status) {
		this.paymentTypeId = paymentTypeId;
		this.cName = cName;
		this.eName = eName;
		this.status = status;
	}

	public int getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
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
		return "PaymentType [paymentTypeId=" + paymentTypeId + ", cName=" + cName + ", eName=" + eName + ", status="
				+ status + "]";
	}
	
}
