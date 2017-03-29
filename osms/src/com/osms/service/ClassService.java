package com.osms.service;

import com.osms.entity.AMCOnUser;

public interface ClassService {

	/**
	 * save a cclass object
	 * @param amc
	 */
	public void saveClass(AMCOnUser amc);

	/**
	 * update a cclass object
	 * @param amcOnUser
	 */
	public void updateClass(AMCOnUser amcOnUser);

	/**
	 * check
	 * @param cName
	 * @param eName
	 * @param status
	 * @return
	 */
	public int check(String cName, String eName, int status);
}
