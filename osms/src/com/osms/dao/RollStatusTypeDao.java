package com.osms.dao;

import java.util.List;

import com.osms.entity.RollStatusType;

public interface RollStatusTypeDao {

	/**
	 * get rollStatusType by rollStatusTypeId
	 * @param rollStatusTypeId
	 */
	public RollStatusType getStatusByStatusId(int rollStatusTypeId);
	
	/**
	 * get all RollStatusType objects
	 * @return
	 */
	public List<RollStatusType> getAllStatus();
	
	/**
	 * save a rollStatusType object
	 * @param rollStatusType
	 */
	public void save(RollStatusType rollStatusType);
	
	/**
	 * update a RollStatusType object
	 * @param status
	 */
	public void update(RollStatusType rollStatusType);
	
	/**
	 * delete operation
	 * @param type
	 * 		key words operaion:AND operation, OR operation
	 */
	public void delete(RollStatusType rollStatusType, String type);
}
