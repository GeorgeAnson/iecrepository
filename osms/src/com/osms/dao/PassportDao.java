package com.osms.dao;

import java.sql.Connection;

import com.osms.entity.Passport;

public interface PassportDao {

	/**
	 * save a passport object
	 * @param passport
	 * @param conn 
	 * @return
	 * 	id
	 */
	public int save(Passport passport, Connection conn);

	/**
	 * get passport by passportId
	 * @param passportId
	 * @return
	 */
	public Passport getPassportByPassportId(int passportId);

	/**
	 * update passport
	 * @param passport
	 * @param conn
	 */
	public void update(Passport passport, Connection conn);
}
