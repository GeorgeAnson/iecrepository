package com.osms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * payment on user
 * @author Administrator
 *
 */
public class Payment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id=0;//payment on user id
	private int userId=0;//user id
	private int paymentTypeId=0;//payment type id
	private String schoolYear;//
	private int theSemester=0;//this semester
	private double totalMoney=0;//totally needs to pay
	private double money=0;//total money 
	private int paymentOprUser=0;//operate root's id
	private Date payDate=null;//paid date
	private String describle=null;
	private int status=0;//current information status
	
	/**
	 * æ€∫œ∂‘œÛ
	 */
	private Users user=null;//user object
	private Users oprUser=null;//root's object of operation
	private PaymentType paymentType=null;//payment type object
	
	
	public Payment()
	{
		
	}


	public Payment(int id, int userId, int paymentTypeId, String schoolYear, int theSemester, double totalMoney,
			double money, int paymentOprUser, Date payDate, String describle, int status) {
		this.id = id;
		this.userId = userId;
		this.paymentTypeId = paymentTypeId;
		this.schoolYear = schoolYear;
		this.theSemester = theSemester;
		this.totalMoney = totalMoney;
		this.money = money;
		this.paymentOprUser = paymentOprUser;
		this.payDate = payDate;
		this.describle = describle;
		this.status = status;
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


	public int getPaymentTypeId() {
		return paymentTypeId;
	}


	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}


	public String getSchoolYear() {
		return schoolYear;
	}


	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}


	public int getTheSemester() {
		return theSemester;
	}


	public void setTheSemester(int theSemester) {
		this.theSemester = theSemester;
	}


	public double getTotalMoney() {
		return totalMoney;
	}


	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}


	public double getMoney() {
		return money;
	}


	public void setMoney(double money) {
		this.money = money;
	}


	public int getPaymentOprUser() {
		return paymentOprUser;
	}


	public void setPaymentOprUser(int paymentOprUser) {
		this.paymentOprUser = paymentOprUser;
	}


	public Date getPayDate() {
		return payDate;
	}


	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}


	public String getDescrible() {
		return describle;
	}


	public void setDescrible(String describle) {
		this.describle = describle;
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


	public Users getOprUser() {
		return oprUser;
	}


	public void setOprUser(Users oprUser) {
		this.oprUser = oprUser;
	}


	public PaymentType getPaymentType() {
		return paymentType;
	}


	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}


	@Override
	public String toString() {
		return "Payment [id=" + id + ", userId=" + userId + ", paymentTypeId=" + paymentTypeId + ", schoolYear="
				+ schoolYear + ", theSemester=" + theSemester + ", totalMoney=" + totalMoney + ", money=" + money
				+ ", paymentOprUser=" + paymentOprUser + ", payDate=" + payDate + ", describle=" + describle
				+ ", status=" + status + ", user=" + user + ", oprUser=" + oprUser + ", paymentType=" + paymentType
				+ "]";
	}


	
}
