package com.osms.service;

import java.util.List;

import com.osms.entity.Passport;
import com.osms.entity.PassportOnUser;

public interface PassportOnUserService {

	/**
	 * get passport information
	 * @param userId
	 * @return
	 */
	public List<Passport> getPassport(int userId);

	/**
	 * get all passportOnUser information by userId
	 * @param userId
	 * @return
	 */
	public List<PassportOnUser> getPassportOnUserByUserId(int userId);
}
