package com.osms.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.AMCOnUserDao;
import com.osms.dao.ClassDao;
import com.osms.dao.MajorDao;
import com.osms.dao.jdbc.JDBCUtil;
import com.osms.entity.AMCOnUser;
import com.osms.entity.CClass;
import com.osms.entity.Major;
import com.osms.globle.Constants;
import com.osms.service.MajorService;

@Service("majorService")
public class MajorServiceImpl implements MajorService {

	@Autowired
	MajorDao majorDao;
	
	@Autowired
	AMCOnUserDao amcOnUserDao;
	
	@Autowired
	ClassDao classDao;
	
	@Override
	public void saveMajor(AMCOnUser amcOnUser) {
		//get id
		int id=amcOnUserDao.getAMCOnUserByConditions(amcOnUser, Constants.AND);

		//save major object
		Connection conn=JDBCUtil.getConnection();
		try
		{
			conn.setAutoCommit(false);
			int majorId=majorDao.save(amcOnUser.getMajor(), conn);
			amcOnUser.setMajorId(majorId);
			amcOnUser.setStatus(1);
			if(id==0)
			{
				amcOnUserDao.save(amcOnUser, conn);
			}else
			{
				amcOnUser.setId(id);
			}
			
			//commit
			conn.commit();
			//else, anyway update the amcOnUser table's information
			if(id!=0)
			{
				amcOnUserDao.update(amcOnUser);
			}
		} catch (SQLException e)
		{
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(conn);
		}
	}

	@Override
	public void updateMajor(AMCOnUser amcOnUser) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		
		try {
			conn.setAutoCommit(false);
			//update a academy object
			majorDao.update(amcOnUser.getMajor(), conn);
			List<CClass> cclasses=amcOnUserDao.getCClasses(amcOnUser.getMajorId());
			//update those classes which belongs to the major
			for(CClass cclass:cclasses)
			{
				cclass.setStatus(-1);
				classDao.update(cclass);
			}
			//update a amcOnUser object
//			amcOnUserDao.update(amcOnUser, conn, Constants.AND);
			//transaction commit
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
		}finally
		{
			JDBCUtil.close(conn);
		}
	}

	@Override
	public int check(String cName, String eName, int status) {
		// TODO Auto-generated method stub
		Major major=new Major(0, cName, eName, status);
		int majorId=majorDao.getMajorByConditions(major);
		return majorId;
	}

}
