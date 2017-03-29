package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.NationalityDao;
import com.osms.entity.Nationality;
import com.osms.utils.Packager;

@Repository("nationalityDao")
public class NationalityDaoImpl extends JDBCBase implements NationalityDao {

	@Override
	public Nationality getNationalityByNationalityId(int nationalityId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Nationality nationality=null;
		String sql="SELECT * FROM Nationality WHERE nationalityStatus=1 AND nationalityId="+nationalityId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				nationality=Packager.PackagerNationality(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		
		return nationality;
	}


	@Override
	public List<Nationality> getAllNationality() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Nationality> nationalities=new ArrayList<Nationality>();
		String sql= "SELECT * FROM Nationality WHERE nationalityStatus=1";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				nationalities.add(Packager.PackagerNationality(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return nationalities;
	}

	@Override
	public void save(Nationality nationality) {
		String sql="INSERT INTO Nationality VALUES(?,?,?)";
		Object[] param={
			nationality.getcName(),
			nationality.geteName(),
			nationality.getStatus()
		};
		saveOrUpdateOrDelete(sql, param);
	}

	@Override
	public void update(Nationality nationality) {
		StringBuilder sql=new StringBuilder("UPDATE Nationality SET nationalityeName=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		if(nationality.geteName()!=null)
		{
			parma.add(nationality.geteName());
		}
		
		if(nationality.getcName()!=null)
		{
			sql.append(", nationalitycName=?");
			parma.add(nationality.getcName());
		}
		if(nationality.getStatus()!=0)
		{
			sql.append(", nationalityStatus=?");
			parma.add(nationality.getStatus());
		}
		sql.append(" WHERE nationalityId=?");
		parma.add(nationality.getNationalityId());
		saveOrUpdateOrDelete(sql.toString(), parma.toArray());
	}

	@Override
	public void delete(Nationality nationality, String type) {
		StringBuilder sql=new StringBuilder("DELETE FROM Nationality WHERE 1=1 ");
		ArrayList<Object> parma=new ArrayList<Object>();
		try
		{
			if(nationality.getNationalityId()!=0)
			{
				sql.append(type+"nationalityId=?");
				parma.add(nationality.getNationalityId());
			}
			
			if(nationality.getcName()!=null&&!"".equals(nationality.getcName()))
			{
				sql.append(type+"nationalitycName=?");
				parma.add(nationality.getcName());
			}
			
			if(nationality.geteName()!=null&&!"".equals(nationality.geteName()))
			{
				sql.append(type+"nationalityeName=?");
				parma.add(nationality.geteName());
			}
			if(nationality.getStatus()!=0)
			{
				sql.append(type+"nationalityStatus=?");
				parma.add(nationality.getStatus());
			}
			saveOrUpdateOrDelete(sql.toString(), parma.toArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
