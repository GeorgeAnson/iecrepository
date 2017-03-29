package com.osms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.GuaranteeDao;
import com.osms.dao.VisaDao;
import com.osms.dao.VisaOnUserDao;
import com.osms.entity.Guarantee;
import com.osms.entity.Visa;
import com.osms.entity.VisaOnUser;
import com.osms.service.VisaOnUserService;

@Service("visaOnUserService")
public class VisaOnUserServiceImpl implements VisaOnUserService {

	@Autowired
	VisaOnUserDao visaOnUserDao;
	
	@Autowired
	VisaDao visaDao;
	
	@Autowired
	GuaranteeDao guaranteeDao;
	
	@Override
	public VisaOnUser getVisaOnUserByUserId(int userId) {
		// TODO Auto-generated method stub
		VisaOnUser visaOnUser=visaOnUserDao.getVisaOnUserByUserId(userId);
		if(visaOnUser!=null)
		{
			if(visaOnUser.getVisaId()!=0)
			{
				Visa visa=visaDao.getVisaByvisaId(visaOnUser.getVisaId());
				visaOnUser.setVisa(visa);
			}
			
			Visa visa=visaDao.getVisaByvisaId(visaOnUser.getVisaId());
			if(visa!=null&&visa.getGuaranteeId()!=0)
			{
				List<Guarantee> guarantees=guaranteeDao.getAllGuarantee();
				for(Guarantee g:guarantees)
				{
					if(visa.getGuaranteeId()==g.getGuaranteeId())
					{
						visa.setGuarantee(g);
					}
				}
			}
			
			visaOnUser.setVisa(visa);
		}
		return visaOnUser;
	}
}
