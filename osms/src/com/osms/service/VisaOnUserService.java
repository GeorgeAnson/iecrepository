package com.osms.service;


import com.osms.entity.VisaOnUser;

public interface VisaOnUserService {

	/**
	 * get VisaOnUser by userId
	 * @param userId
	 * @return
	 */
	public VisaOnUser getVisaOnUserByUserId(int userId);
}
