package com.osms.service;

import com.osms.entity.AMCOnUser;

public interface AcademyService {

	/**
	 * save a academy object
	 * @param amcOnUser
	 */
	public void saveAcademy(AMCOnUser amcOnUser);

	/**
	 * update academy with AMCOnUser
	 * @param amcOnUser
	 */
	public void updateAcademy(AMCOnUser amcOnUser);

	/**
	 * check
	 * @param cName
	 * @param eName
	 * @param i
	 * @return
	 */
	public int check(String cName, String eName, int i);


}
