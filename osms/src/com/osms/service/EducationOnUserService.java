package com.osms.service;

import com.osms.entity.EducationOnUser;

public interface EducationOnUserService {

	/**
	 * get educationOnUser by userId
	 * @param userId
	 * @return
	 */
	public EducationOnUser getEducationOnUserByUserId(int userId);
}
