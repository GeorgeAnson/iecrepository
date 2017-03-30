package com.osms.service;

import java.util.List;

import com.osms.entity.Users;

public interface UserService {

	/**
	 * save a student object
	 * @param users
	 * 		student object
	 */
	public void saveStudent(Users user);
	
	
	/**
	 * save a teacher object
	 * @param amc
	 * @param schoolRoll
	 * @param user
	 */
	public void saveTeacher(Users user);


	/**
	 * get user's full information
	 * @param userId
	 * @return
	 */
	public Users getUser(int userId, String userType);


	/**
	 * update user
	 * @param user
	 */
	public void updateStudent(Users user);


	/**
	 * check email
	 * @param value
	 * @return
	 */
	public int checkEmail(String value);


	/**
	 * check phone
	 * @param value
	 * @return
	 */
	public int checkPhone(String value);


    /**
     * check
     * @param value
     * @return
     */
	public int checkCard(String value);
	
}
