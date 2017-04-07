package com.osms.dao;

import java.util.List;

import com.osms.entity.StudentType;

public interface StudentTypeDao {

	public List<StudentType> getAllStudentType();

	/**
	 * get studentType by studentTypeId
	 * @param studentTypeId
	 * @return
	 */
	public StudentType getStudentTypeByTypeId(int studentTypeId);
}
