package com.osms.service;

import java.util.IdentityHashMap;
import java.util.List;

import com.osms.entity.Payment;

public interface PaymentService {

	/**
	 * get payment by userId
	 * @param userId
	 * @return
	 */
	public List<Payment> getPaymentByUserId(int userId);

	/**
	 * get payments by conditions=>userId, schoolYear, the semester
	 * the results order by paymentUserId ASC
	 * @param payment
	 * @return
	 */
	public List<Payment> getPaymentByConditions(Payment payment);

	/**
	 * save for bat
	 * @param payments
	 */
	public void save(List<Payment> payments);
	
	/**
	 * get search result by search parmas
	 * @param parma
	 * @param limit
	 * @param page
	 * @param count
	 * @return
	 */
	public List<Payment> searchByPaymentOnamc(IdentityHashMap<Object, Object> parma, int limit, int page, int count);

	/**
	 * for student, get visa and payment
	 * @param userId
	 * @return
	 */
	public List<Payment> searchByPaymentOnUserId(int userId);
}
