package com.osms.dao;

import java.util.List;

import com.osms.entity.Guarantee;

public interface GuaranteeDao {

	/**
	 * save guarantee
	 * @param guarantee
	 */
	public void save(Guarantee guarantee);

	/**
	 * get all guarantees
	 * @return
	 */
	public List<Guarantee> getAllGuarantee();

	/**
	 * update guarantee object
	 * @param guarantee
	 */
	public void update(Guarantee guarantee);
}
