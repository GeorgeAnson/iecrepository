package com.osms.dao;

import java.sql.Connection;
import java.util.List;

import com.osms.entity.PassportOnUser;

public interface PassportOnUserDao {

	/**
	 * save a passportOnUser object
	 * @param passportOnUser
	 * @param conn
	 * @return
	 * 	id
	 */
	public int save(PassportOnUser passportOnUser, Connection conn);

	/**
	 * get passportId by userId
	 * @param userId
	 * @return
	 */
	public List<Integer> getPassportIds(int userId);

	/**
	 * get passport pic path
	 * @param userId
	 * @return
	 */
	public List<PassportOnUser> getPassportOnUserByUserId(int userId);

	/**
	 * update passportOnUser
	 * @param passportOnUser
	 * @param conn
	 */
	public void update(PassportOnUser passportOnUser, Connection conn);
}
