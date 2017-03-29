package com.osms.entity;

import java.io.Serializable;

/**
 * passport table
 * @author Administrator
 *
 */
public class Passport implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int passportId=0;//passport id
	private String passportNumber=null;//passport number
	private int status=0;//current information status
	
	
	public Passport()
	{
		
	}


	public Passport(int passportId, String passportNumber, int status) {
		this.passportId = passportId;
		this.passportNumber = passportNumber;
		this.status = status;
	}


	public int getPassportId() {
		return passportId;
	}


	public void setPassportId(int passportId) {
		this.passportId = passportId;
	}


	public String getPassportNumber() {
		return passportNumber;
	}


	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Passport [passportId=" + passportId + ", passportNumber=" + passportNumber + ", status=" + status + "]";
	}
	
	
}
