package com.osms.dao;

import java.sql.Connection;
import java.util.List;

import com.osms.entity.Major;

public interface MajorDao {

	/**
	 * get major object by majorId
	 * @param majorId
	 * @return
	 */
	public Major getMajorByMajorId(int majorId);
	
	/**
	 * get all major objects
	 * @return
	 */
	public List<Major> getAllMajor();
	
	/**
	 * save a major object
	 */
	public int save(Major major, Connection conn);
	
	
	/**
	 * update major object information
	 * @param major
	 */
	public void update(Major major);
	
	/**
	 * delete operation
	 * @param major
	 * 		major object
	 * @param type
	 * 		key word operation:AND operation, OR operation
	 */
	public void delete(Major major, String type);

	/**
	 * transaction update
	 * @param major
	 * @param conn
	 */
	public void update(Major major, Connection conn);

	/**
	 * get majorId for check
	 * @param major
	 * @return
	 */
	public int getMajorByConditions(Major major);
}
