package com.osms.dao;

import java.util.List;

import com.osms.entity.ProfessionalTitleType;

public interface ProfessionalTypeDao {

	/**
	 * get professionalTitleType by id
	 * @param professionalTitleTypeId
	 * @return
	 */
	public ProfessionalTitleType getProfessionalTypeBytypeId(int professionalTitleTypeId);

	/**
	 * get all professionalTitleTypes
	 * @return
	 */
	public List<ProfessionalTitleType> getAllProfessionalType();
}
