package com.osms.service;

import com.osms.entity.IdentityOnUser;

public interface IdentityOnUserService {

	/**
	 * get identityOnUser by userId
	 * @param userId
	 * @return
	 */
	public IdentityOnUser getIdentityOnUserByUserId(int userId);
}
