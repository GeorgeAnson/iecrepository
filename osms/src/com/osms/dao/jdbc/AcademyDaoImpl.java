package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.AcademyDao;
import com.osms.entity.Academy;
import com.osms.utils.Packager;

@Repository("academyDao")
public class AcademyDaoImpl extends JDBCBase implements AcademyDao {

	@Override
	public Academy getAcademyByAcademyId(int academyId) {
		Connection conn=JDBCUtil.getConnection();
		String sql="SELECT * FROM Academy WHERE academyStatus=1 AND academyId="+academyId;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Academy academy=null;
		try 
		{
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				academy=Packager.PackagerAcademy(rs);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		
		return academy;
	}

	@Override
	public List<Academy> getAllAcademy() {
		Connection conn=JDBCUtil.getConnection();
		String sql="SELECT * FROM Academy WHERE academyStatus=1";
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Academy> academies = new ArrayList<Academy>();
		try 
		{
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				academies.add(Packager.PackagerAcademy(rs));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return academies;
	}

	@Override
	public int save(Academy academy, Connection conn) {
		String sql="INSERT INTO Academy VALUES(?,?,?)";
		Object[] param={
			academy.getcName(),
			academy.geteName(),
			academy.getStatus()
		};
		int academyId=save(sql, param, conn);
		return academyId;
	}

	@Override
	public void update(Academy academy) {
		StringBuilder sql=new StringBuilder("UPDATE Academy SET academyeName=?");
		ArrayList<Object> paramAcademy=new ArrayList<Object>();
		paramAcademy.add(academy.geteName());
		
		if(academy.getcName()!=null)
		{
			sql.append(", academycName=?");
			paramAcademy.add(academy.getcName());
		}
		if(academy.getStatus()!=0)
		{
			sql.append(", academyStatus=?");
			paramAcademy.add(academy.getStatus());
		}
		sql.append(" WHERE academyId=?");
		paramAcademy.add(academy.getAcademyId());
		Object[] param=paramAcademy.toArray();
		saveOrUpdateOrDelete(sql.toString(), param);
	}

	@Override
	public void delete(Academy academy, String type) {
		StringBuilder sql=new StringBuilder("DELETE FROM Academy WHERE 1=1 ");
		ArrayList<Object> parma=new ArrayList<Object>();
		try
		{
			if(academy.getAcademyId()!=0)
			{
				sql.append(type+"academyId=?");
				parma.add(academy.getAcademyId());
			}
			
			if(academy.getcName()!=null&&!"".equals(academy.getcName()))
			{
				sql.append(type+"academycName=?");
				parma.add(academy.getcName());
			}
			
			if(academy.geteName()!=null&&!"".equals(academy.geteName()))
			{
				sql.append(type+"academyeName=?");
				parma.add(academy.geteName());
			}
			
			if(academy.getStatus()!=0)
			{
				sql.append(type+"academyStatus=?");
				parma.add(academy.getStatus());
			}
			
			saveOrUpdateOrDelete(sql.toString(), parma.toArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void update(Academy academy, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE Academy SET academyStatus=?");
		ArrayList<Object> paramAcademy=new ArrayList<Object>();
		paramAcademy.add(academy.getStatus());
		
		sql.append(" WHERE academyId=?");
		paramAcademy.add(academy.getAcademyId());
		Object[] param=paramAcademy.toArray();
		update(sql.toString(), param, conn);
	}

	@Override
	public int getAcademyByConditions(Academy academy) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		int academyId=0;
		StringBuilder sql=new StringBuilder("SELECT academyId FROM Academy WHERE academycName=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(academy.getcName());
		if(academy.geteName()!=null)
		{
			sql.append(" AND academyeName=?");
			parma.add(academy.geteName());
		}
		if(academy.getStatus()!=0)
		{
			sql.append(" AND academyStatus=?");
			parma.add(academy.getStatus());
		}
		try {
			ps=conn.prepareStatement(sql.toString());
			rs=query(ps, parma.toArray());
			if(rs.next())
			{
				academyId=rs.getInt("academyId");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return academyId;
	}

	
}
