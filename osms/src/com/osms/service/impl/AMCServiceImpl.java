package com.osms.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.AMCOnUserDao;
import com.osms.dao.AcademyDao;
import com.osms.dao.ClassDao;
import com.osms.dao.MajorDao;
import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.entity.CClass;
import com.osms.entity.Major;
import com.osms.bean.AcademyMajorBean;
import com.osms.service.AMCService;

@Service("amcService")
public class AMCServiceImpl implements AMCService {

	@Autowired
	AMCOnUserDao amcOnUserDao;
	
	@Autowired
	AcademyDao academyDao;
	
	@Autowired
	MajorDao majorDao;
	
	@Autowired
	ClassDao classDao;
	
	
	@Override
	public void matchAllAMC(Map<Integer, Academy> academyMap, Map<Integer, AcademyMajorBean> majorMap,
			Map<Integer,AMCOnUser> classMap) {
		//get amcOnUser objects by status
		List<AMCOnUser> amcs=amcOnUserDao.getAMCOnUser(1);
		
		for(AMCOnUser amc:amcs)
		{
			//match AMCOnUser objects
			amc.setAcademy(academyDao.getAcademyByAcademyId(amc.getAcademyId()));
			amc.setMajor(majorDao.getMajorByMajorId(amc.getMajorId()));
			amc.setCclass(classDao.getClassByclassId(amc.getClassId()));
			//match academy objects
			academyMap.put(amc.getAcademyId(), amc.getAcademy());
			//match major objects
			if(amc.getAcademy()!=null&&amc.getMajor()!=null)
			{
				AcademyMajorBean academyMajorBean=new AcademyMajorBean(
						amc.getAcademy().getcName(),
						amc.getAcademy().geteName(),
						amc.getMajor().getcName(),
						amc.getMajor().geteName());
				majorMap.put(amc.getMajorId(), academyMajorBean);
			}
			//match AMCOnUser objects
			if(amc.getAcademy()!=null&&amc.getMajor()!=null&&amc.getCclass()!=null)
			{
				classMap.put(amc.getClassId(), amc);
			}
			
		}
	}


	@Override
	public List<AMCOnUser> getAMCByUserId(int userId) {
		// TODO Auto-generated method stub
		List<AMCOnUser> amcOnUsers=amcOnUserDao.getAMCOnUserByUserId(userId);
		for(AMCOnUser amcOnUser:amcOnUsers)
		{
			if(amcOnUser!=null)
			{
				if(amcOnUser.getAcademyId()!=0)
				{
					Academy academy=academyDao.getAcademyByAcademyId(amcOnUser.getAcademyId());
					amcOnUser.setAcademy(academy);
				}
				if(amcOnUser.getMajorId()!=0)
				{
					Major major=majorDao.getMajorByMajorId(amcOnUser.getMajorId());
					amcOnUser.setMajor(major);
				}
				if(amcOnUser.getClassId()!=0)
				{
					CClass cclass=classDao.getClassByclassId(amcOnUser.getClassId());
					amcOnUser.setCclass(cclass);
				}
			}
		}
		
		return amcOnUsers;
	}

}
