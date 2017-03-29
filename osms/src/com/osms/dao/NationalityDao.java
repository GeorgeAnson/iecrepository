package com.osms.dao;

import java.util.List;

import com.osms.entity.Nationality;

public interface NationalityDao {

	/**
	 * get nationality object by nationalityId
	 * @param nationalityId
	 * @return
	 */
	public Nationality getNationalityByNationalityId(int nationalityId);
	
	
	/**
	 * get all nationality objects
	 * @return
	 */
	public List<Nationality> getAllNationality();
	
	
	/**
	 * save a nationality object
	 * @param nationality
	 */
	public void save(Nationality nationality);
	
	
	/**
	 * update a nationality object
	 * @param nationality
	 */
	public void update(Nationality nationality);
	
	/**
	 * delete operation
	 * @param nationality
	 * 		nationality object
	 * @param type
	 * 		key word operation:AND operation, OR operation
	 */
	public void delete(Nationality nationality, String type);
}
