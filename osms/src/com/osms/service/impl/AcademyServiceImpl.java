package com.osms.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.AMCOnUserDao;
import com.osms.dao.AcademyDao;
import com.osms.dao.ClassDao;
import com.osms.dao.MajorDao;
import com.osms.dao.jdbc.JDBCUtil;
import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.entity.CClass;
import com.osms.entity.Major;
import com.osms.globle.Constants;
import com.osms.service.AcademyService;

@Service("academyService")
public class AcademyServiceImpl implements AcademyService {

	@Autowired
	AcademyDao academyDao;
	
	@Autowired
	AMCOnUserDao amcOnUserDao;
	
	@Autowired
	MajorDao majorDao;
	
	@Autowired
	ClassDao classDao;
	
	@Override
	public void saveAcademy(AMCOnUser amcOnUser) {
		int academyId=amcOnUser.getAcademy().getAcademyId();
		Connection conn=JDBCUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			//save a academy object
			academyId=academyDao.save(amcOnUser.getAcademy(), conn);
			amcOnUser.setAcademyId(academyId);
			amcOnUser.setStatus(1);
			//save a amcOnUser object
			amcOnUserDao.save(amcOnUser, conn);
			
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
	public void updateAcademy(AMCOnUser amcOnUser) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		
		try {
			conn.setAutoCommit(false);
			//update a academy object
			academyDao.update(amcOnUser.getAcademy(), conn);
			List<Major> majors=amcOnUserDao.getMajors(amcOnUser.getAcademyId());
			for(Major m:majors)
			{
				m.setStatus(-1);
				majorDao.update(m);
				List<CClass> cclasses=amcOnUserDao.getCClasses(m.getMajorId());
				for(CClass c:cclasses)
				{
					c.setStatus(-1);
					classDao.update(c);
				}
			}
			//update a amcOnUser object
			amcOnUserDao.update(amcOnUser, conn, Constants.AND);
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
		Academy academy=new Academy(0, cName, eName, status);
		int academyId=academyDao.getAcademyByConditions(academy);
		return academyId;
	}

}
