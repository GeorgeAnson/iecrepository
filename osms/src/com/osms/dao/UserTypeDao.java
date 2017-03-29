package com.osms.dao;

import java.util.List;

import com.osms.entity.UserType;

public interface UserTypeDao {

	/**
	 * get a userTYpe by userTypeId
	 * @param typeId
	 * @return
	 */
	public UserType getUserTypeByUserTypeId(int userTypeId);
	
	/**
	 * get all UserType objects
	 * @return
	 */
	public List<UserType> getAllUserType();
	
	/**
	 * save a UserType object
	 * @param userType
	 */
	public void save(UserType userType);
	
	/**
	 * update a userType object
	 * @param userType
	 */
	public void update(UserType userType);
	
	/**
	 * delete operation
	 * @param userType
	 * 		userType object
	 * @param type
	 * 		key words operation:AND operation, OR operation
	 */
	public void delete(UserType userType, String type);
}
