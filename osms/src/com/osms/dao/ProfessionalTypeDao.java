package com.osms.dao;

import com.osms.entity.ProfessionalTitleType;

public interface ProfessionalTypeDao {

	/**
	 * get professionalTitleType by id
	 * @param professionalTitleTypeId
	 * @return
	 */
	public ProfessionalTitleType getProfessionalTypeBytypeId(int professionalTitleTypeId);
}
