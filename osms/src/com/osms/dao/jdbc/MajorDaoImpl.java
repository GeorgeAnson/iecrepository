package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.MajorDao;
import com.osms.entity.Major;
import com.osms.utils.Packager;

@Repository("majorDao")
public class MajorDaoImpl extends JDBCBase implements MajorDao {

	@Override
	public Major getMajorByMajorId(int majorId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Major major=null;
		String sql="SELECT * FROM Major WHERE majorStatus =1 AND majorId="+majorId;
		try
		{
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				major=Packager.PackagerMajor(rs);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return major;
	}

	@Override
	public List<Major> getAllMajor() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		ArrayList<Major> majors=new ArrayList<Major>();
		String sql="SELECT * FROM Major WHERE majorStatus=1";
		try
		{
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				majors.add(Packager.PackagerMajor(rs));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return majors;
	}

	@Override
	public int save(Major major, Connection conn) {
		String sql="INSERT INTO Major VALUES(?,?,?)";
		Object[] parma={
				major.getcName(),
				major.geteName(),
				major.getStatus()
		};
		int majorId=save(sql, parma, conn);
		return majorId;
	}

	@Override
	public void update(Major major) {
		StringBuilder sql=new StringBuilder("UPDATE Major SET majoreName=?");
		ArrayList<Object> paramMajor=new ArrayList<Object>();
		paramMajor.add(major.geteName());
		if(major.getcName()!=null&&!"".equals(major.getcName()))
		{
			sql.append(", majorcName=?");
			paramMajor.add(major.getcName());
		}
		if(major.getStatus()!=0)
		{
			sql.append(", majorStatus=?");
			paramMajor.add(major.getStatus());
		}
		sql.append(" WHERE majorId=?");
		paramMajor.add(major.getMajorId());
		Object[] parma=paramMajor.toArray();
		saveOrUpdateOrDelete(sql.toString(), parma);
	}


	@Override
	public void delete(Major major, String type) {
		StringBuilder sql=new StringBuilder("DELETE FROM Major WHERE 1=1");
		ArrayList<Object> parma=new ArrayList<Object>();
		try
		{
			if(major.getMajorId()!=0)
			{
				sql.append(type+"majorId=?");
				parma.add(major.getMajorId());
			}
			
			if(major.getcName()!=null&&!"".equals(major.getcName()))
			{
				sql.append(type+"majorcName=?");
				parma.add(major.getcName());
			}
			if(major.geteName()!=null&&!"".equals(major.geteName()))
			{
				sql.append(type+"majoreName=?");
				parma.add(major.geteName());
			}
			if(major.getStatus()!=0)
			{
				sql.append(type+"majorStatus=?");
				parma.add(major.getStatus());
			}
			saveOrUpdateOrDelete(sql.toString(), parma.toArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void update(Major major, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE Major SET majorStatus=?");
		ArrayList<Object> paramMajor=new ArrayList<Object>();
		paramMajor.add(major.getStatus());
		
		sql.append(" WHERE majorId=?");
		paramMajor.add(major.getMajorId());
		Object[] parma=paramMajor.toArray();
		update(sql.toString(), parma, conn);
	}

	@Override
	public int getMajorByConditions(Major major) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		int majorId=0;
		StringBuilder sql=new StringBuilder("SELECT majorId FROM Major WHERE majorcName=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(major.getcName());
		if(major.geteName()!=null)
		{
			sql.append(" AND majoreName=?");
			parma.add(major.geteName());
		}
		if(major.getStatus()!=0)
		{
			sql.append(" AND majorStatus=?");
			parma.add(major.getStatus());
		}
		try {
			ps=conn.prepareStatement(sql.toString());
			rs=query(ps, parma.toArray());
			if(rs.next())
			{
				majorId=rs.getInt("majorId");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return majorId;
	}
	
	
}
