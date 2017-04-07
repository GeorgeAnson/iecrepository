package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.AMCOnUserDao;
import com.osms.entity.AMCOnUser;
import com.osms.entity.CClass;
import com.osms.entity.Major;
import com.osms.utils.Packager;

@Repository("amcOnUserDao")
public class AMCOnUserDaoImpl extends JDBCBase implements AMCOnUserDao {

	@Override
	public int save(AMCOnUser amcOnUser, Connection conn) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO AMCOnUser VALUES(?,?,?,?,?)";
		ArrayList<Object> parma=new ArrayList<Object>();
		if(amcOnUser.getUserId()!=0)
		{
			parma.add(amcOnUser.getUserId());
		}else
		{
			parma.add(null);
		}
		if(amcOnUser.getAcademyId()!=0)
		{
			parma.add(amcOnUser.getAcademyId());
		}else
		{
			parma.add(null);
		}
		if(amcOnUser.getMajorId()!=0)
		{
			parma.add(amcOnUser.getMajorId());
		}else
		{
			parma.add(null);
		}
		if(amcOnUser.getClassId()!=0)
		{
			parma.add(amcOnUser.getClassId());
		}else
		{
			parma.add(null);
		}
		parma.add(amcOnUser.getStatus());
		int id=save(sql, parma.toArray(), conn);
		return id;
	}

	@Override
	public int getAMCOnUserByConditions(AMCOnUser amcOnUser, String type) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		StringBuilder sql=new StringBuilder("SELECT amcOnUserId FROM AMCOnUser WHERE 1=1");
		ArrayList<Object> parma=new ArrayList<Object>();
		int id=0;
		if(amcOnUser.getAcademyId()!=0)
		{
			sql.append(type+"academy=?");
			parma.add(amcOnUser.getAcademyId());
			if(amcOnUser.getMajorId()!=0)
			{
				sql.append(type+"major=?");
				parma.add(amcOnUser.getMajorId());
				if(amcOnUser.getClassId()!=0)
				{
					sql.append(type+"cclass=?");
					parma.add(amcOnUser.getClassId());
				}else
				{
					sql.append(type+"cclass IS NULL");
				}
			}else
			{
				sql.append(type+"major IS NULL"+type+"cclass IS NULL");
			}
		}
		System.out.println(sql.toString());//////////
		try {
			ps=conn.prepareStatement(sql.toString());
			rs=query(ps, parma.toArray());
			if(rs.next())
			{
				id=rs.getInt("amcOnUserId");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return id;
	}

	@Override
	public void update(AMCOnUser amcOnUser) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE AMCOnUser SET amcOnUserStatus=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(amcOnUser.getStatus());
		if(amcOnUser.getAcademyId()!=0)
		{
			sql.append(", academy=?");
			parma.add(amcOnUser.getAcademyId());
		}
		if(amcOnUser.getMajorId()!=0)
		{
			sql.append(", major=?");
			parma.add(amcOnUser.getMajorId());
		}
		if(amcOnUser.getClassId()!=0)
		{
			sql.append(", cclass=?");
			parma.add(amcOnUser.getClassId());
		}
		if(amcOnUser.getUserId()!=0)
		{
			sql.append(", amcOnUserUser=?");
			parma.add(amcOnUser.getUserId());
		}
		sql.append(" WHERE amcOnUserId=?");
		parma.add(amcOnUser.getId());
		saveOrUpdateOrDelete(sql.toString(),parma.toArray());
	}

	@Override
	public List<AMCOnUser> getAMCOnUser(int status) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<AMCOnUser> amcOnUsers=new ArrayList<AMCOnUser>();
		String sql="SELECT * FROM AMCOnUser WHERE amcOnUserStatus="+status;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				amcOnUsers.add(Packager.PackagerAMCOnUser(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return amcOnUsers;
	}

	@Override
	public void update(AMCOnUser amcOnUser, Connection conn, String type) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE AMCOnUser SET amcOnUserStatus=? WHERE 1=1");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(amcOnUser.getStatus());
		
		if(amcOnUser.getAcademyId()!=0)
		{
			sql.append(type+"academy=?");
			parma.add(amcOnUser.getAcademyId());
		}
		if(amcOnUser.getMajorId()!=0)
		{
			sql.append(type+"major=?");
			parma.add(amcOnUser.getMajorId());
		}
		if(amcOnUser.getClassId()!=0)
		{
			sql.append(type+"cclass=?");
			parma.add(amcOnUser.getClassId());
		}
		if(amcOnUser.getUserId()!=0)
		{
			sql.append(type+"amcOnUserUser=?");
			parma.add(amcOnUser.getUserId());
		}
		
		saveOrUpdateOrDelete(sql.toString(),parma.toArray());
	}


	@Override
	public List<CClass> getCClasses(int majorId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<CClass> cclasses=new ArrayList<CClass>();
		String sql="SELECT cc.cclassId, cc.cclasscName, cc.cclasseName, cc.cclassStatus FROM AMCOnUser as amc, CClass as cc WHERE amc.cclass=cc.cclassId AND amc.major="+majorId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				cclasses.add(Packager.PackagerClass(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return cclasses;
	}

	@Override
	public List<Major> getMajors(int academyId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Major> majors=new ArrayList<Major>();
		String sql="SELECT m.majorId, m.majorcName, m.majoreName, m.majorStatus FROM AMCOnUser as amc, Major as m WHERE amc.major=m.majorId AND amc.academy="+academyId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				majors.add(Packager.PackagerMajor(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return majors;
	}

	@Override
	public int getAMCOnUserByConditions(AMCOnUser amcOnUser) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		StringBuilder sql=new StringBuilder("SELECT amcOnUserId FROM AMCOnUser WHERE amcOnUserUser=? AND academy=? AND major=? AND cclass=? AND amcOnUserStatus=1");
		//StringBuilder sql=new StringBuilder("SELECT amcOnUserId FROM AMCOnUser WHERE amcOnUserStatus=1");
		ArrayList<Object> parma=new ArrayList<Object>();
		int id=0;
		parma.add(amcOnUser.getUserId());
		parma.add(amcOnUser.getAcademyId());
		parma.add(amcOnUser.getMajorId());
		parma.add(amcOnUser.getClassId());
		
		try {
			ps=conn.prepareStatement(sql.toString());
			rs=query(ps, parma.toArray());
			if(rs.next())
			{
				id=rs.getInt("amcOnUserId");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return id;
	}

	@Override
	public List<AMCOnUser> getAMCOnUserByUserId(int userId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<AMCOnUser> amcOnUsers=new ArrayList<AMCOnUser>();
		String sql="SELECT * FROM AMCOnUser WHERE amcOnUserStatus=1 AND amcOnUserUser="+userId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				amcOnUsers.add(Packager.PackagerAMCOnUser(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return amcOnUsers;
	}

	@Override
	public List<Integer> getuserIdsByamc(int academyId, int majorId, int cclassId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Integer> userIds=new ArrayList<Integer>();
		String sql="SELECT DISTINCT amcOnUserUser FROM AMCOnUser WHERE amcOnUserStatus=1 AND amcOnUserUser IS NOT NULL"
				+ " AND academy="+academyId+" AND major="+majorId+" AND cclass="+cclassId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				userIds.add(rs.getInt("amcOnUserUser"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return userIds;
	}

	@Override
	public void update(AMCOnUser amcOnUser, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE AMCOnUser SET amcOnUserStatus=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(amcOnUser.getStatus());
		if(amcOnUser.getAcademyId()!=0)
		{
			sql.append(", academy=?");
			parma.add(amcOnUser.getAcademyId());
		}
		if(amcOnUser.getMajorId()!=0)
		{
			sql.append(", major=?");
			parma.add(amcOnUser.getMajorId());
		}
		if(amcOnUser.getClassId()!=0)
		{
			sql.append(", cclass=?");
			parma.add(amcOnUser.getClassId());
		}
		if(amcOnUser.getUserId()!=0)
		{
			sql.append(", amcOnUserUser=?");
			parma.add(amcOnUser.getUserId());
		}
		sql.append(" WHERE amcOnUserId=?");
		parma.add(amcOnUser.getId());
		update(sql.toString(), parma.toArray(), conn);
	}

	@Override
	public List<Integer> getUserIdsByConditions(AMCOnUser amc) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		//StringBuilder sql=new StringBuilder("SELECT amcOnUserId FROM AMCOnUser WHERE amcOnUserUser=? AND academy=? AND major=? AND cclass=? AND amcOnUserStatus=1");
		StringBuilder sql=new StringBuilder("SELECT amcOnUserUser FROM AMCOnUser WHERE amcOnUserStatus=1 AND amcOnUserUser IS NOT NULL");
		ArrayList<Object> parma=new ArrayList<Object>();
		List<Integer> userIds=new ArrayList<>();
		if(amc.getAcademyId()!=0)
		{
			sql.append(" AND academy=?");
			parma.add(amc.getAcademyId());
		}
		if(amc.getMajorId()!=0)
		{
			sql.append(" AND major=?");
			parma.add(amc.getMajorId());
		}
		if(amc.getClassId()!=0)
		{
			sql.append(" AND cclass=?");
			parma.add(amc.getClassId());
		}
		System.out.println(sql.toString());
		try {
			ps=conn.prepareStatement(sql.toString());
			rs=query(ps, parma.toArray());
			while(rs.next())
			{
				userIds.add(rs.getInt("amcOnUserUser"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return userIds;
	}
}
