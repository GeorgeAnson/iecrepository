package com.osms.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.PassportDao;
import com.osms.dao.PassportOnUserDao;
import com.osms.dao.jdbc.JDBCUtil;
import com.osms.entity.Passport;
import com.osms.entity.PassportOnUser;
import com.osms.service.PassportOnUserService;

@Service("passportOnUserService")
public class PassportOnUserServiceImpl implements PassportOnUserService {
	
	@Autowired
	PassportOnUserDao passportOnUserDao;
	
	@Autowired
	PassportDao passportDao;
	
	@Override
	public List<Passport> getPassport(int userId) {
		// TODO Auto-generated method stub
		List<Integer> passportIds=passportOnUserDao.getPassportIds(userId);
		List<Passport> passports=new ArrayList<Passport>();
		if(!passportIds.isEmpty())
		{
			for(int passportId:passportIds)
			{
				if(passportId!=0)
				{
					Passport passport=passportDao.getPassportByPassportId(passportId);
					passports.add(passport);
				}
			}
		}
		return passports;
	}

	@Override
	public List<PassportOnUser> getPassportOnUserByUserId(int userId) {
		// TODO Auto-generated method stub
		List<PassportOnUser> passportOnUsers=passportOnUserDao.getPassportOnUserByUserId(userId);
		if(passportOnUsers!=null)
		{
			for(PassportOnUser p:passportOnUsers)
			{
				Passport passport=passportDao.getPassportByPassportId(p.getPassportId());
				p.setPassport(passport);
			}
		}
		return passportOnUsers;
	}

	@Override
	public void save(List<PassportOnUser> passportOnUsers) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			for(PassportOnUser passportOnUser:passportOnUsers)
			{
				passportOnUserDao.save(passportOnUser, conn);
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(conn);
		}	
	}
}
