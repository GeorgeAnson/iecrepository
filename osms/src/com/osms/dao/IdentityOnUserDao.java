package com.osms.dao;

import java.sql.Connection;

import com.osms.entity.IdentityOnUser;

public interface IdentityOnUserDao {

	/**
	 * save a IdentityOnUser object
	 * @param identityOnUser
	 * @param conn
	 * @return
	 */
	public int save(IdentityOnUser identityOnUser, Connection conn);

	/**
	 * get identityOnUser by userId
	 * @return
	 */
	public IdentityOnUser getIdentityOnUserByUserId(int userId);

	/**
	 * update identityOnUser
	 * @param identityOnUser
	 * @param conn
	 */
	public void update(IdentityOnUser identityOnUser, Connection conn);

}
