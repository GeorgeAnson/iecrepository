package com.osms.dao;

import java.sql.Connection;

import com.osms.entity.VisaOnUser;

public interface VisaOnUserDao {

	/**
	 * save a VisaOnUser Object
	 * @param visaOnUser
	 * @param conn
	 * @return
	 * 	id
	 */
	public int save(VisaOnUser visaOnUser, Connection conn);

	/**
	 * get visa on User by userId
	 * @param userId
	 * @return
	 */
	public VisaOnUser getVisaOnUserByUserId(int userId);

	/**
	 * update visaOnUser
	 * @param visaOnUser
	 * @param conn
	 */
	public void update(VisaOnUser visaOnUser, Connection conn);
}
