package com.osms.dao;

import java.util.List;

import com.osms.entity.EducationType;

public interface EducationTypeDao {

	/**
	 * get educationType object by educationTypeId
	 * @param educationId
	 * @return
	 */
	public EducationType getEducationTypeByEducationTypeId(int educationTypeId);
	
	
	/**
	 * get all EducationType object
	 * @return
	 */
	public List<EducationType> getAllEducationType();
	
	
	/**
	 * save a EduactionType Object
	 * @param educationType
	 */
	public void save(EducationType educationType);
	
	
	/**
	 * update an educationType
	 * @param educationType
	 */
	public void update(EducationType educationType);
	
	
	/**
	 * delete operation
	 * @param educationType
	 * 		educationTYpe object
	 * @param type
	 * 		key word operation:AND operator, OR operator
	 */
	public void delete(EducationType educationType, String type);
}
