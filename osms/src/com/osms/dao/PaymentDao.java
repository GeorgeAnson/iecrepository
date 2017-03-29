package com.osms.dao;

import java.sql.Connection;
import java.util.List;

import com.osms.entity.Payment;

public interface PaymentDao {


	/**
	 * get a payment object by paymentId
	 * @param paymentId
	 * @return
	 */
	public Payment getPaymentByPaymentId(int paymentId);
	
	/**
	 * get all payment objects
	 * @return
	 */
	public List<Payment> getAllPayment();
	
	/**
	 * save a payment object
	 * @param payment
	 */
	public void save(Payment payment);
	
	/**
	 * update a payment object
	 * @param payment
	 */
	public void update(Payment payment);

	/**
	 * get payment by userId
	 * @param userId
	 * @return
	 */
	public List<Payment> getPaymentByUserId(int userId);

	/**
	 * get payments by conditions
	 * the results order by paymentUserId ASC
	 * @param payment
	 * @return
	 */
	public List<Payment> getPaymentByConditions(Payment payment);

	/**
	 * save for bat
	 * @param p
	 * @param conn
	 */
	public void save(Payment p, Connection conn);
	
}
