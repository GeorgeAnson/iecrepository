package com.osms.dao;

import java.sql.Connection;
import java.util.List;

import com.osms.entity.StudyPeriod;

public interface StudyPeriodDao {

	/**
	 * get studyPeriod by studyPeriodId
	 * @param periodId
	 * @return
	 */
	public StudyPeriod getStudyPeriodByStudyPeriodId(int studyPeriodId);
	
	/**
	 * get all StudyPeriod obejcts
	 * @return
	 */
	public List<StudyPeriod> getAllStudyPeriod();
	
	/**
	 * save a studyPeriod object
	 * @param studyPeriod
	 * @param conn 
	 * @return
	 */
	public int save(StudyPeriod studyPeriod, Connection conn);
	
	/**
	 * update a studyPeriod object
	 * @param studyPeriod
	 */
	public void update(StudyPeriod studyPeriod);
	
	/**
	 * delete operation
	 * @param studyPeriod
	 * 		delete object
	 * @param type
	 * 		key words operation:AND operation, OR operation
	 */
	public void delete(StudyPeriod studyPeriod, String type);
}
