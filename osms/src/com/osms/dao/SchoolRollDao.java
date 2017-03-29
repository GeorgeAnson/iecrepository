package com.osms.dao;

import java.sql.Connection;

import com.osms.entity.SchoolRoll;

public interface SchoolRollDao {

	/**
	 * save a schoolRoll object
	 * @param schoolRoll
	 * @param conn
	 * @return
	 * 	id
	 */
	public int save(SchoolRoll schoolRoll, Connection conn);

	/**
	 * get rollStatusTypeId by userId
	 * @param userId
	 * @return
	 */
	public int getRollStatusTypeByUserId(int userId);

	/**
	 * get schoolRoll by userId
	 * @param userId
	 * @return
	 */
	public SchoolRoll getSchoolRollByUserId(int userId);

	/**
	 * update schoolRoll
	 * @param schoolRoll
	 * @param conn
	 */
	public void update(SchoolRoll schoolRoll, Connection conn);
}
