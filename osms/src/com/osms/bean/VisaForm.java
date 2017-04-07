package com.osms.bean;

import java.io.Serializable;
import java.util.List;

import com.osms.entity.PassportOnUser;
import com.osms.entity.Payment;
import com.osms.entity.Users;
import com.osms.entity.VisaOnUser;

public class VisaForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Users user=null;
	
	private Payment payment=null;
	
	private VisaOnUser visaOnUser=null;
	
	private List<PassportOnUser> passportOnUsers=null;
	
	public VisaForm() {
		// TODO Auto-generated constructor stub
	}


	public Users getUser() {
		return user;
	}


	public void setUser(Users user) {
		this.user = user;
	}


	public Payment getPayment() {
		return payment;
	}


	public void setPayment(Payment payment) {
		this.payment = payment;
	}


	public VisaOnUser getVisaOnUser() {
		return visaOnUser;
	}


	public void setVisaOnUser(VisaOnUser visaOnUser) {
		this.visaOnUser = visaOnUser;
	}


	public List<PassportOnUser> getPassportOnUsers() {
		return passportOnUsers;
	}


	public void setPassportOnUsers(List<PassportOnUser> passportOnUsers) {
		this.passportOnUsers = passportOnUsers;
	}


	@Override
	public String toString() {
		return "VisaForm [user=" + user + ", payment=" + payment + ", visaOnUser=" + visaOnUser + ", passportOnUsers="
				+ passportOnUsers + "]";
	}

	
}
