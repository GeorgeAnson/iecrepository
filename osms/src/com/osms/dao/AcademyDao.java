package com.osms.dao;

import java.sql.Connection;
import java.util.List;

import com.osms.entity.Academy;

public interface AcademyDao {

	/**
	 * get a academy object by academyId
	 * @param academyId
	 * 		int
	 * @return
	 * 		Academy
	 */
	public Academy getAcademyByAcademyId(int academyId);
	
	/**
	 * get all academy object
	 * @return
	 */
	public List<Academy> getAllAcademy();
	
	/**
	 * transaction commit
	 * save a academy object
	 * @param academy
	 * @return
	 * 	academyId
	 */
	public int save(Academy academy, Connection conn);
	
	/**
	 * update a academy object's information
	 * @param academy
	 */
	public void update(Academy academy);
	
	/**
	 * delete operation
	 * @param academy
	 * 		academy object
	 * @param type
	 * 		key word operation:AND operation,OR operation
	 */
	public void delete(Academy academy, String type);

	/**
	 * transaction update 
	 * @param academy
	 * @param conn
	 */
	public void update(Academy academy, Connection conn);

	/**
	 * get by conditions
	 * @param academy
	 * @return
	 */
	public int getAcademyByConditions(Academy academy);
}
