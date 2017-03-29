package com.osms.dao;

import java.sql.Connection;

import com.osms.entity.EducationOnUser;

public interface EducationOnUserDao {

	/**
	 * save a educationOnUser object
	 * @param educationOnUser
	 * @param conn
	 * @return
	 * 	id
	 */
	public int save(EducationOnUser educationOnUser, Connection conn);

	/**
	 * get eduactionOnUser by userId
	 * @param userId
	 * @return
	 */
	public EducationOnUser getEducationOnUserByUserId(int userId);

	/**
	 * update educationOnUser
	 * @param educationOnUser
	 * @param conn
	 */
	public void upadte(EducationOnUser educationOnUser, Connection conn);
}
