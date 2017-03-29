package com.osms.dao;

import java.util.List;

import com.osms.entity.PaymentType;

public interface PaymentTypeDao {

	/**
	 * get a paymentType object by paymentTypeId
	 * @param paymentTypeId
	 * @return
	 */
	public PaymentType getPaymentTypeByPaymentTypeId(int paymentTypeId);
	
	/**
	 * get all paymentType objects
	 * @return
	 */
	public List<PaymentType> getAllPaymentType();
	
	/**
	 * save a paymentType object
	 * @param paymentType
	 */
	public void save(PaymentType paymentType);
	
	/**
	 * update a paymentType object
	 * @param paymentType
	 */
	public void update(PaymentType paymentType);
	
	/**
	 * delete operation
	 * @param paymentType
	 * 		paymentType object
	 * @param type
	 * 	    key words operation: AND operation, OR operation
	 */
	public void delete(PaymentType paymentType, String type);
}
