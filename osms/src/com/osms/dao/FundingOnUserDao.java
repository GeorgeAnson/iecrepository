package com.osms.dao;

import java.sql.Connection;

import com.osms.entity.FundingOnUser;

public interface FundingOnUserDao {

	/**
	 * save a fundingOnUser object
	 * @param fundingOnUser
	 * @param conn
	 * @return
	 *  id
	 */
	public int save(FundingOnUser fundingOnUser, Connection conn);
	
}
