package com.osms.entity;

import java.io.Serializable;

/**
 * fundingSource on user
 * @author Administrator
 *
 */
public class FundingOnUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id=0;//funding source id
	private int userId=0;//user id
	private int fundingTypeId=0;//funding source type id
	private int status=0;//current information status
	
	private Users user=null;//user object
	private FundingType fundingType=null;//funding type object
	
	public FundingOnUser() {
		// TODO Auto-generated constructor stub
	}

	public FundingOnUser(int id, int userId, int fundingTypeId, int status, Users user, FundingType fundingType) {
		this.id = id;
		this.userId = userId;
		this.fundingTypeId = fundingTypeId;
		this.status = status;
		this.user = user;
		this.fundingType = fundingType;
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

	public int getFundingTypeId() {
		return fundingTypeId;
	}

	public void setFundingTypeId(int fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
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

	public FundingType getFundingType() {
		return fundingType;
	}

	public void setFundingType(FundingType fundingType) {
		this.fundingType = fundingType;
	}

	@Override
	public String toString() {
		return "FundingOnUser [id=" + id + ", userId=" + userId + ", fundingTypeId=" + fundingTypeId + ", status="
				+ status + ", user=" + user + ", fundingType=" + fundingType + "]";
	}
	
	
	
}
