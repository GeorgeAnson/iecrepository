package com.osms.service;

import com.osms.entity.SchoolRoll;

public interface SchoolRollService {

	/**
	 * get rollStatusType on SchoolRoll by userId
	 * @param userId
	 * @return
	 */
	public SchoolRoll getRollStatusTypeByUserId(int userId);

	/**
	 * get schoolRoll by userId
	 * @param userId
	 * @return
	 */
	public SchoolRoll getSchoolRollByUserId(int userId);
}
