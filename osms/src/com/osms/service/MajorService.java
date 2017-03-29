package com.osms.service;


import com.osms.entity.AMCOnUser;

public interface MajorService {

	/**
	 * save a major object
	 * @param amc
	 */
	public void saveMajor(AMCOnUser amc);

	/**
	 * update a major object
	 * @param amcOnUser
	 */
	public void updateMajor(AMCOnUser amcOnUser);

	/**
	 * check
	 * @param cName
	 * @param eName
	 * @param status
	 * @return
	 */
	public int check(String cName, String eName, int status);
	
}
