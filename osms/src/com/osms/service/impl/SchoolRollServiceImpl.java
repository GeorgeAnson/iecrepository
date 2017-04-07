package com.osms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.RollStatusTypeDao;
import com.osms.dao.SchoolRollDao;
import com.osms.dao.StudentTypeDao;
import com.osms.dao.StudyPeriodDao;
import com.osms.entity.RollStatusType;
import com.osms.entity.SchoolRoll;
import com.osms.entity.StudentType;
import com.osms.entity.StudyPeriod;
import com.osms.service.SchoolRollService;

@Service("schoolRollService")
public class SchoolRollServiceImpl implements SchoolRollService {

	@Autowired
	SchoolRollDao schoolRollDao;
	
	@Autowired
	RollStatusTypeDao rollStatusTypeDao;
	@Autowired
	StudyPeriodDao studyPeriodDao;
	
	@Autowired
	StudentTypeDao studentTypeDao;
	
	@Override
	public SchoolRoll getRollStatusTypeByUserId(int userId) {
		// TODO Auto-generated method stub		
		SchoolRoll schoolRoll=schoolRollDao.getSchoolRollByUserId(userId);
		RollStatusType rollStatusType=null;
		if(schoolRoll!=null)
		{
			if(schoolRoll.getRollStatusTypeId()!=0)
			{
				rollStatusType=rollStatusTypeDao.getStatusByStatusId(schoolRoll.getRollStatusTypeId());
				schoolRoll.setRollStatusType(rollStatusType);
			}
			List<StudentType> studentTypes=studentTypeDao.getAllStudentType();
			for(StudentType s:studentTypes)
			{
				if(schoolRoll.getStudentTypeId()==s.getStudentTypeId())
				{
					schoolRoll.setStudentTypeId(s.getStudentTypeId());
				}
			}
		}
		return schoolRoll;
	}

	@Override
	public SchoolRoll getSchoolRollByUserId(int userId) {
		// TODO Auto-generated method stub
		//get schoolRoll object by userId
		SchoolRoll schoolRoll=schoolRollDao.getSchoolRollByUserId(userId);
		//get studyPeriod by syudyPeriodId
		if(schoolRoll!=null&&schoolRoll.getStudyPeriodId()!=0)
		{
			StudyPeriod studyPeriod=studyPeriodDao.getStudyPeriodByStudyPeriodId(schoolRoll.getStudyPeriodId());
			schoolRoll.setStudyPeriod(studyPeriod);
		}
		//get rollStatusPeriod
		if(schoolRoll!=null&&schoolRoll.getRollStatusTypeId()!=0)
		{
			RollStatusType rollStatusType=rollStatusTypeDao.getStatusByStatusId(schoolRoll.getRollStatusTypeId());
			schoolRoll.setRollStatusType(rollStatusType);
		}
		if(schoolRoll!=null&&schoolRoll.getStudentTypeId()!=0)
		{
			StudentType studentType=studentTypeDao.getStudentTypeByTypeId(schoolRoll.getStudentTypeId());
			schoolRoll.setStudentType(studentType);
		}
		return schoolRoll;
	}

}
