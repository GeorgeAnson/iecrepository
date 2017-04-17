package com.osms.dao;

import java.sql.Connection;

import com.osms.entity.ApartmentRoll;

public interface ApartmentRollDao {

	/**
	 * save a ApartmentRoll object
	 * @param apartmentRoll
	 * @param conn
	 * @return
	 * 	id
	 */
	public int save(ApartmentRoll apartmentRoll, Connection conn);

	/**
	 * get apartmentRoll by userId
	 * @param userId
	 * @return
	 */
	public ApartmentRoll getApartmentRollByUserId(int userId);

	/**
	 * update apartmentRoll
	 * @param apartmentRoll
	 * @param conn 
	 */
	public void update(ApartmentRoll apartmentRoll, Connection conn);
}
