package com.osms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.AMCOnUserDao;
import com.osms.dao.GuaranteeDao;
import com.osms.dao.NationalityDao;
import com.osms.dao.NoticeTypeDao;
import com.osms.dao.PaymentTypeDao;
import com.osms.dao.RollStatusTypeDao;
import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.entity.CClass;
import com.osms.entity.Guarantee;
import com.osms.entity.Major;
import com.osms.entity.Nationality;
import com.osms.entity.NoticeType;
import com.osms.entity.PaymentType;
import com.osms.entity.RollStatusType;
import com.osms.service.AcademyService;
import com.osms.service.ClassService;
import com.osms.service.ConfigService;
import com.osms.service.MajorService;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	AcademyService academyService;
	
	@Autowired
	MajorService majorService;
	
	@Autowired
	ClassService classService;
	
	@Autowired
	NationalityDao nationalityDao;
	
	@Autowired
	PaymentTypeDao paymentTypeDao;
	
	@Autowired
	RollStatusTypeDao rollStatusTypeDao;
	
	@Autowired
	NoticeTypeDao noticeTypeDao;
	
	@Autowired
	AMCOnUserDao amcOnUserDao;
	
	@Autowired
	GuaranteeDao guaranteeDao;
	
	@Override
	public String saveAMCOnUser(String obj, String cName, String eName, int academyId, int majorId) {
		// TODO Auto-generated method stub
		AMCOnUser amcOnUser=new AMCOnUser();
		String ERROR=null;
		switch(obj.toLowerCase())
		{
		case "academy":
			//check if exist, if yes, get the id value
			int aId=academyService.check(cName,eName,1);
			if(aId!=0)
			{
				ERROR="[ "+cName+" ] , 该学院已经存在!";
			}else
			{
				Academy academy=new Academy(0, cName, eName, 1);
				amcOnUser.setAcademy(academy);
				academyService.saveAcademy(amcOnUser);
			}
			break;
		case "major":
			int mId=majorService.check(cName,eName,1);
			if(mId!=0)
			{
				ERROR="[ "+cName+" ] , 该专业已经存在!";
			}else
			{
				Major major=new Major(0, cName, eName, 1);
				amcOnUser.setMajor(major);
				amcOnUser.setAcademyId(academyId);
				majorService.saveMajor(amcOnUser);
			}
			break;
		case "cclass":
			int cId=classService.check(cName,eName,1);
			if(cId!=0)
			{
				ERROR="[ "+cName+" ] , 该班级已经存在!";
			}else
			{
				amcOnUser.setCclass(new CClass(0, cName, eName, 1));
				amcOnUser.setAcademyId(academyId);
				amcOnUser.setMajorId(majorId);
				classService.saveClass(amcOnUser);
			}
			break;
		default:
			//...
			break;
		}
		return ERROR;
	}

	@Override
	public void saveConfiguration(String obj, String cName, String eName) {
		// TODO Auto-generated method stub
		switch (obj.toLowerCase()) {
		case "nationality":
			nationalityDao.save(new Nationality(0, cName, eName, 1));
			break;
		case "paymenttype":
			paymentTypeDao.save(new PaymentType(0, cName, eName, 1));
			break;
		case "rollstatustype":
			rollStatusTypeDao.save(new RollStatusType(0, cName, eName, 1));
			break;
		case "noticetype":
			noticeTypeDao.save(new NoticeType(0, cName, eName, 1));
			break;
		default:
			//...
			break;
		}
	}

	@Override
	public void deleteAMCOnUser(String obj, String eName, int id) {
		// TODO Auto-generated method stub
		AMCOnUser amcOnUser=new AMCOnUser();
		switch(obj.toLowerCase())
		{
		case "academy":
			amcOnUser.setAcademy(new Academy(id, null, eName, -1));
			amcOnUser.setAcademyId(id);
			amcOnUser.setStatus(-1);
			academyService.updateAcademy(amcOnUser);
			break;
		case "major":
			amcOnUser.setMajor(new Major(id, null, eName, -1));
			amcOnUser.setMajorId(id);
			majorService.updateMajor(amcOnUser);
			break;
		case "cclass":
			amcOnUser.setCclass(new CClass(id, null, eName, -1));
			amcOnUser.setClassId(id);
			classService.updateClass(amcOnUser);
			break;
		default:
			//...
			break;
		}
	}

	@Override
	public void deleteConfiguration(String obj, String eName, int id) {
		// TODO Auto-generated method stub
		switch (obj.toLowerCase()) {
		case "nationality":
			nationalityDao.update(new Nationality(id, null, eName, -1));
			break;
		case "paymenttype":
			paymentTypeDao.update(new PaymentType(id, null, eName, -1));
			break;
		case "rollstatustype":
			rollStatusTypeDao.update(new RollStatusType(id, null, eName, -1));
			break;
		case "noticetype":
			noticeTypeDao.update(new NoticeType(id, null, eName, -1));
			break;
		case "guarantee":
			guaranteeDao.update(new Guarantee(id, null, eName, null, -1));
			break;
		default:
			//...
			break;
		}
	}
}
