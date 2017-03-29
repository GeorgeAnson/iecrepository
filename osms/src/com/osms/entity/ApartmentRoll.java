package com.osms.entity;

import java.io.Serializable;
/**
 * teacher's apartment roll
 * @author Administrator
 *
 */
public class ApartmentRoll implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id=0;//id
	private int userId=0;//user id
	private int professionalTitleTypeId=0;// professional title type id
	private String cardNumber=null;//student card number
	private int status=0;//current information status
	
	
	private Users user=null;
	private ProfessionalTitleType fProfessionalTitleType=null;
	
	public ApartmentRoll() {
		// TODO Auto-generated constructor stub
	}

	public ApartmentRoll(int id, int userId, int professionalTitleTypeId, String cardNumber, int status, Users user,
			ProfessionalTitleType fProfessionalTitleType) {
		this.id = id;
		this.userId = userId;
		this.professionalTitleTypeId = professionalTitleTypeId;
		this.cardNumber = cardNumber;
		this.status = status;
		this.user = user;
		this.fProfessionalTitleType = fProfessionalTitleType;
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

	public int getProfessionalTitleTypeId() {
		return professionalTitleTypeId;
	}

	public void setProfessionalTitleTypeId(int professionalTitleTypeId) {
		this.professionalTitleTypeId = professionalTitleTypeId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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

	public ProfessionalTitleType getfProfessionalTitleType() {
		return fProfessionalTitleType;
	}

	public void setfProfessionalTitleType(ProfessionalTitleType fProfessionalTitleType) {
		this.fProfessionalTitleType = fProfessionalTitleType;
	}

	@Override
	public String toString() {
		return "ApartmentRoll [id=" + id + ", userId=" + userId + ", professionalTitleTypeId=" + professionalTitleTypeId
				+ ", cardNumber=" + cardNumber + ", status=" + status + ", user=" + user + ", fProfessionalTitleType="
				+ fProfessionalTitleType + "]";
	}
	
	
}
