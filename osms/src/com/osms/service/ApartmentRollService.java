package com.osms.service;

import com.osms.entity.ApartmentRoll;

public interface ApartmentRollService {

	/**
	 * get full apartmentRoll informations
	 * @param userId
	 * @return
	 */
	public ApartmentRoll getApartmentRollByUserId(int userId);
}
