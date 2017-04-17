package com.osms.dao;

import java.sql.Connection;
import java.util.List;

import com.osms.entity.Users;

public interface UserDao {

	/**
	 * get a user object by userId
	 * @param userId
	 * 		user id
	 * @return
	 * 		Users
	 */
	public Users getUserByUserId(int userId);
	
	
	/**
	 * transaction commit
	 * save a new user object 
	 * @param user
	 * @param conn
	 * @return
	 */
	public int save(Users user, Connection conn);
	
	/**
	 * update a user's object information
	 * only those information in user's table
	 * @param user
	 */
	public void update(Users user);


	/**
	 * get a user by condition
	 * @param condition
	 * 		phone in China or email
	 * @param status
	 * @return
	 */
	public Users getUserByCondition(String condition, int status);


	/**
	 * get users by userTypeId 
	 * @param parseInt
	 * @return
	 */
	public List<Users> getUserByUserTypeId(int userTypeId);


	/**
	 * update user
	 * @param user
	 * @param conn
	 */
	public void update(Users user, Connection conn);
	
}
