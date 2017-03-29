package com.osms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.EducationOnUserDao;
import com.osms.dao.IdentityOnUserDao;
import com.osms.dao.NationalityDao;
import com.osms.entity.EducationOnUser;
import com.osms.entity.IdentityOnUser;
import com.osms.entity.Nationality;
import com.osms.service.IdentityOnUserService;

@Service("identityOnUserService")
public class IdentityOnUserServiceImpl implements IdentityOnUserService {

	@Autowired
	IdentityOnUserDao identityOnUserDao;
	
	@Autowired
	NationalityDao nationalityDao;
	
	@Autowired
	EducationOnUserDao educationOnUserDao;
	
	@Override
	public IdentityOnUser getIdentityOnUserByUserId(int userId) {
		// TODO Auto-generated method stub
		IdentityOnUser identityOnUser=identityOnUserDao.getIdentityOnUserByUserId(userId);
		if(identityOnUser!=null&&identityOnUser.getNationalityId()!=0)
		{
			Nationality nationality=nationalityDao.getNationalityByNationalityId(identityOnUser.getNationalityId());
			identityOnUser.setNationality(nationality);
			EducationOnUser educationOnUser=educationOnUserDao.getEducationOnUserByUserId(userId);
			identityOnUser.setEducationOnUser(educationOnUser);
		}
		return identityOnUser;
	}

}
