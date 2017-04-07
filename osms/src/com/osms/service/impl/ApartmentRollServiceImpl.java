package com.osms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.ApartmentRollDao;
import com.osms.dao.ProfessionalTypeDao;
import com.osms.entity.ApartmentRoll;
import com.osms.entity.ProfessionalTitleType;
import com.osms.service.ApartmentRollService;

@Service("apartmentRollService")
public class ApartmentRollServiceImpl implements ApartmentRollService {

	@Autowired
	ApartmentRollDao apartmentRollDao;
	@Autowired
	ProfessionalTypeDao professionalTypeDao;
	
	@Override
	public ApartmentRoll getApartmentRollByUserId(int userId) {
		// TODO Auto-generated method stub
		ApartmentRoll apartmentRoll=apartmentRollDao.getApartmentRollByUserId(userId);
		ProfessionalTitleType professionalTitleType=null;
		if(apartmentRoll!=null)
		{
			professionalTitleType=professionalTypeDao.getProfessionalTypeBytypeId(apartmentRoll.getProfessionalTitleTypeId());
		}
		apartmentRoll.setfProfessionalTitleType(professionalTitleType);
		return apartmentRoll;
	}

}
