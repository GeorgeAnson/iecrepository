package com.osms.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
	private Date validTime=null;//
	private Date invalidTime=null;//this semester
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
	private List<PaymentType> paymentTypes=null;
	
	
	public Payment()
	{
		
	}


	public Payment(int id, int userId, int paymentTypeId, Date validTime, Date invalidTime, double totalMoney,
			double money, int paymentOprUser, Date payDate, String describle, int status, Users user, Users oprUser,
			PaymentType paymentType, List<PaymentType> paymentTypes) {
		super();
		this.id = id;
		this.userId = userId;
		this.paymentTypeId = paymentTypeId;
		this.validTime = validTime;
		this.invalidTime = invalidTime;
		this.totalMoney = totalMoney;
		this.money = money;
		this.paymentOprUser = paymentOprUser;
		this.payDate = payDate;
		this.describle = describle;
		this.status = status;
		this.user = user;
		this.oprUser = oprUser;
		this.paymentType = paymentType;
		this.paymentTypes = paymentTypes;
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


	public Date getValidTime() {
		return validTime;
	}


	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}


	public Date getInvalidTime() {
		return invalidTime;
	}


	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
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


	public List<PaymentType> getPaymentTypes() {
		return paymentTypes;
	}


	public void setPaymentTypes(List<PaymentType> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}


	@Override
	public String toString() {
		return "Payment [id=" + id + ", userId=" + userId + ", paymentTypeId=" + paymentTypeId + ", validTime="
				+ validTime + ", invalidTime=" + invalidTime + ", totalMoney=" + totalMoney + ", money=" + money
				+ ", paymentOprUser=" + paymentOprUser + ", payDate=" + payDate + ", describle=" + describle
				+ ", status=" + status + ", user=" + user + ", oprUser=" + oprUser + ", paymentType=" + paymentType
				+ ", paymentTypes=" + paymentTypes + "]";
	}
	
}
