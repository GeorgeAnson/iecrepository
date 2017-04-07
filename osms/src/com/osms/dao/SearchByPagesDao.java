package com.osms.dao;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.osms.entity.Payment;
import com.osms.entity.Users;

public interface SearchByPagesDao {

	/**
	 * count data numbers
	 * @param parma
	 * @param tableName
	 * @return
	 */
	public int getCount(Map<Object, Object> parma, String tableName);
	
	/**
	 * get users by page number
	 * @param parma
	 * @param limit
	 * @param pages
	 * @param count
	 * @return
	 */
	public List<Users> getUsers(Map<Object, Object> parma,int limit, int pages, int count);
	
	
	/**
	 * count data numbers
	 * @param parma
	 * @param tableName
	 * @return
	 */
	public int getCount(Map<Object, Object> parma, Map<String, String> table);
	
	
	/**
	 * get user by amcOnUsers
	 * @param parma
	 * @param limit
	 * @param pages
	 * @param count
	 * @return
	 */
	public List<Users> getUsersByAMCOnUsers(Map<Object, Object> parma, int limit, int pages, int count);

	/**
	 * get user by amc and payment
	 * @param parma
	 * @param limit
	 * @param page
	 * @param count
	 * @return
	 */
	public List<Payment> getUsersByPaymentOnamc(IdentityHashMap<Object, Object> parma, int limit, int page, int count);

	/**
	 * get for student about visa and payment
	 * @param userId
	 * @return
	 */
	public List<Payment> getUsersByPaymentOnamc(int userId);
	
}
