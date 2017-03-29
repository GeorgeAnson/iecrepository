package com.osms.dao;

import java.sql.Connection;
import java.util.List;

import com.osms.entity.CClass;

public interface ClassDao {

	/**
	 * get a CClass object by cclassId
	 * @param cclassId
	 * @return
	 */
	public CClass getClassByclassId(int cclassId);
	
	
	/**
	 * get all CClass objects' information
	 * @return
	 */
	public List<CClass> getAllClass();
	
	
	/**
	 * update a CClass's information
	 * @param cclass
	 */
	public void update(CClass cclass);
	
	
	/**
	 * save a CClass object
	 * @param cclass
	 * @param conn
	 * @return
	 * 		cclassId
	 */
	public int save(CClass cclass, Connection conn);
	
	
	/**
	 *delete operator
	 * @param cclass
	 * 		  CCLass object
	 * @param type
	 * 		key word operation:AND operation, OR operation
	 */
	public void delete(CClass cclass, String type);


	/**
	 * transaction update
	 * @param cclass
	 * @param conn
	 */
	public void update(CClass cclass, Connection conn);


	/**
	 * check
	 * @param cclass
	 * @return
	 */
	public int getClassByConditions(CClass cclass);
}
