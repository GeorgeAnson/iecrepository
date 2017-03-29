package com.osms.dao;

import java.sql.Connection;
import java.util.List;

import com.osms.entity.AMCOnUser;
import com.osms.entity.CClass;
import com.osms.entity.Major;

public interface AMCOnUserDao {

	/**
	 * save a AMCONUser object
	 * @param amcOnUser
	 * @param conn
	 * @return
	 * 	id
	 */
	public int save(AMCOnUser amcOnUser, Connection conn);

	/**
	 * get a amcOnUser information by condition
	 * @param amcOnUser
	 * @param type
	 * @return
	 */
	public int getAMCOnUserByConditions(AMCOnUser amcOnUser, String type);

	/**
	 * update AMCOnUser object
	 * @param amcOnUser
	 */
	public void update(AMCOnUser amcOnUser);

	/**
	 * get AMCOnUser objects
	 * @param status
	 * @return
	 */
	public List<AMCOnUser> getAMCOnUser(int status);

	/**
	 * update
	 * @param amcOnUser
	 * @param conn
	 */
	public void update(AMCOnUser amcOnUser, Connection conn, String type);

	/**
	 * get amc and academy and major and class together
	 * @return
	 */
//	public List<AMCOnUser> getAMCOnUser();

	/**
	 * get classIds by majorId
	 * @param majorId
	 * @return
	 */
	public List<CClass> getCClasses(int majorId);

	/**
	 * major at a academy
	 * @param academyId
	 * @return
	 */
	public List<Major> getMajors(int academyId);

	/**
	 * get id with all
	 * @param amcOnUser
	 * @return
	 */
	public int getAMCOnUserByConditions(AMCOnUser amcOnUser);

	/**
	 * get amcOnUser by UserId
	 * @param userId
	 * @return
	 */
	public List<AMCOnUser> getAMCOnUserByUserId(int userId);

	
	/**
	 * get userIds by academyId and majorId and cclassId
	 * @param academyId
	 * @param majorId
	 * @param cclassId
	 * @return
	 */
	public List<Integer> getuserIdsByamc(int academyId, int majorId, int cclassId);

	/**
	 * update amcOnUser
	 * @param amcOnUser
	 * @param conn
	 */
	public void update(AMCOnUser amcOnUser, Connection conn);

}
