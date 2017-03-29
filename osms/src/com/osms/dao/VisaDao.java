package com.osms.dao;


import java.sql.Connection;

import com.osms.entity.Visa;

public interface VisaDao {

	/**
	 * save a visa object
	 * @param visa
	 * @param conn 
	 * @return
	 * 	id
	 */
	public int save(Visa visa, Connection conn);

	/**
	 * get visa by visaId
	 * @param visaId
	 * @return
	 */
	public Visa getVisaByvisaId(int visaId);

	/**
	 * update visa
	 * @param visa
	 * @param conn
	 */
	public void update(Visa visa, Connection conn);
}
