package com.osms.service;


public interface ConfigService {

	/**
	 * add academy, or major or cclass with amcOnUser
	 * @param obj
	 * @param cName
	 * @param eName
	 */
	public String saveAMCOnUser(String obj, String cName, String eName, int academyId, int majorId);
	
	/**
	 * add other configuration
	 * @param obj
	 * @param cName
	 * @param eName
	 */
	public void saveConfiguration(String obj, String cName, String eName);
	
	/**
	 * delete academy or major or cclass with amcOnUser
	 * the way to delete is to set status=-1
	 * @param obj
	 * @param id
	 */
	public void deleteAMCOnUser(String obj, String eName, int id);
	
	/**
	 * delete configurations
	 * the way to delete configurations is to set status=-1
	 * @param obj
	 * @param id
	 */
	public void deleteConfiguration(String obj, String eName,  int id);
}
