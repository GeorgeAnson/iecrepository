package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.EducationTypeDao;
import com.osms.entity.EducationType;
import com.osms.utils.Packager;

@Repository("educationTypeDao")
public class EducationTypeDaoImpl extends JDBCBase implements EducationTypeDao {

	@Override
	public EducationType getEducationTypeByEducationTypeId(int educationTypeId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		EducationType educationType=null;
		String sql="SELECT * FROM EducationType WHERE educationTypeStatus=1 AND educationTypeId="+educationTypeId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				educationType=Packager.PackagerEducationType(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return educationType;
	}

	@Override
	public List<EducationType> getAllEducationType() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<EducationType>educationTypes=new ArrayList<EducationType>();
		String sql="SELECT * FROM EducationType WHERE educationTypeStatus=1";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				educationTypes.add(Packager.PackagerEducationType(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return educationTypes;
	}


	@Override
	public void save(EducationType educationType) {
		String sql="INSERT INTO EducationType VALUES(?,?,?)";
		Object[] param={
			educationType.getcName(),
			educationType.geteName(),
			educationType.getStatus()
		};
		saveOrUpdateOrDelete(sql, param);
	}

	@Override
	public void update(EducationType educationType) {
		StringBuilder sql= new StringBuilder("UPDATE EducationType SET educationTypeeName=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(educationType.geteName());
		if(educationType.getcName()!=null&&!"".equals(educationType.getcName()))
		{
			sql.append(", educationTypecName=?");
			parma.add(educationType.getcName());
		}
		if(educationType.getStatus()!=0)
		{
			sql.append(", educationTypeStatus=?");
			parma.add(educationType.getStatus());
		}
		sql.append(" WHERE educationTypeId=?");
		parma.add(educationType.getEducationTypeId());
		saveOrUpdateOrDelete(sql.toString(), parma.toArray());
	}

	@Override
	public void delete(EducationType educationType, String type) {
		StringBuilder sql=new StringBuilder("DELETE FROM EducationType WHERE 1=1");
		ArrayList<Object> parma=new ArrayList<Object>();
		try
		{
			if(educationType.getEducationTypeId()!=0)
			{
				sql.append(type+"educationTypeId=?");
				parma.add(educationType.getEducationTypeId());
			}
			
			if(educationType.getcName()!=null&&!"".equals(educationType.getcName()))
			{
				sql.append(type+"educationTypecName=?");
				parma.add(educationType.getcName());
			}
			if(educationType.geteName()!=null&&!"".equals(educationType.geteName()))
			{
				sql.append(type+"educationTypeeName=?");
				parma.add(educationType.geteName());
			}
			if(educationType.getStatus()!=0)
			{
				sql.append(type+"educationTypeStatus=?");
				parma.add(educationType.getStatus());
			}
			saveOrUpdateOrDelete(sql.toString(), parma.toArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
