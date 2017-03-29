package com.osms.entity;

import java.io.Serializable;
/**
 * funding source table
 * @author Administrator
 *
 */
public class FundingType implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int fundingTypeId=0;//funding source id
	private String cName=null;//Chinese name
	private String eName=null;//English name
	private int status=0;//current information status
	
	
	public FundingType()
	{
		
	}
	
	
	public FundingType(int fundingTypeId, String cName, String eName, int status) {
		this.fundingTypeId = fundingTypeId;
		this.cName = cName;
		this.eName = eName;
		this.status = status;
	}


	public int getFundingTypeId() {
		return fundingTypeId;
	}


	public void setFundingTypeId(int fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
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
		return "FundingType [fundingTypeId=" + fundingTypeId + ", cName=" + cName + ", eName=" + eName + ", status="
				+ status + "]";
	}
}
