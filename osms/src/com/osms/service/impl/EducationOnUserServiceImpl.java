package com.osms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.EducationOnUserDao;
import com.osms.dao.EducationTypeDao;
import com.osms.entity.EducationOnUser;
import com.osms.entity.EducationType;
import com.osms.service.EducationOnUserService;

@Service("educationOnUserService")
public class EducationOnUserServiceImpl implements EducationOnUserService {

	@Autowired
	EducationOnUserDao educationOnUserDao;
	@Autowired
	EducationTypeDao educationTypeDao;
	
	@Override
	public EducationOnUser getEducationOnUserByUserId(int userId) {
		// TODO Auto-generated method stub
		EducationOnUser educationOnUser=educationOnUserDao.getEducationOnUserByUserId(userId);
		if(educationOnUser!=null&&educationOnUser.getEducationTypeId()!=0)
		{
			EducationType educationType=educationTypeDao.getEducationTypeByEducationTypeId(educationOnUser.getEducationTypeId());
			educationOnUser.setEducationType(educationType);
		}
		return educationOnUser;
	}

}
