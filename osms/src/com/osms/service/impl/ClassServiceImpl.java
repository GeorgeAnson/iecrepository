package com.osms.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.AMCOnUserDao;
import com.osms.dao.ClassDao;
import com.osms.dao.jdbc.JDBCUtil;
import com.osms.entity.AMCOnUser;
import com.osms.entity.CClass;
import com.osms.globle.Constants;
import com.osms.service.ClassService;

@Service("classService")
public class ClassServiceImpl implements ClassService {

	@Autowired
	ClassDao classDao;
	
	@Autowired
	AMCOnUserDao amcOnUserDao;
	
	@Override
	public void saveClass(AMCOnUser amcOnUser) {
		//get id
		int id=amcOnUserDao.getAMCOnUserByConditions(amcOnUser, Constants.AND);
		System.out.println(id);
		Connection conn=JDBCUtil.getConnection();
		try
		{
			conn.setAutoCommit(false);
			//save a class object
			int classId=classDao.save(amcOnUser.getCclass(), conn);
			//save classId
			amcOnUser.setClassId(classId);
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
			//update AMC information
			if(id!=0)
			{
				amcOnUserDao.update(amcOnUser);
			}
		} catch (SQLException e)
		{
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(conn);
		}
		
	}

	@Override
	public void updateClass(AMCOnUser amcOnUser) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		
		try {
			conn.setAutoCommit(false);
			//update a academy object
			classDao.update(amcOnUser.getCclass(), conn);
			//update a amcOnUser object
			//amcOnUserDao.update(amcOnUser, conn, Constants.AND);
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
		CClass cclass=new CClass(0, cName, eName, status);
		int cclassId=classDao.getClassByConditions(cclass);
		return cclassId;
	}

}
