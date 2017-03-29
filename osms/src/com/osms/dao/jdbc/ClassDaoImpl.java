package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.ClassDao;
import com.osms.entity.CClass;
import com.osms.utils.Packager;

@Repository("classDao")
public class ClassDaoImpl extends JDBCBase implements ClassDao {

	@Override
	public CClass getClassByclassId(int cclassId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		CClass cclass=null;
		String sql="SELECT * FROM CClass WHERE cclassStatus=1 AND cclassId="+cclassId;
		try
		{
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				cclass=Packager.PackagerClass(rs);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return cclass;
	}

	@Override
	public List<CClass> getAllClass() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		ArrayList<CClass> classes=new ArrayList<CClass>();
		String sql="SELECT * FROM CClass WHERE cclassStatus=1";
		try
		{
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				classes.add(Packager.PackagerClass(rs));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return classes;
	}

	@Override
	public void update(CClass cclass) {
		StringBuilder sql=new StringBuilder("UPDATE CClass SET cclasseName=?");
		ArrayList<Object> paramClass=new ArrayList<Object>();
		paramClass.add(cclass.geteName());
		if(cclass.getcName()!=null&&!"".equals(cclass.getcName()))
		{
			sql.append(", cclasscName=?");
			paramClass.add(cclass.getcName());
		}
		if(cclass.getStatus()!=0)
		{
			sql.append(", cclassStatus=?");
			paramClass.add(cclass.getStatus());
		}
		sql.append(" WHERE cclassId=?");
		paramClass.add(cclass.getClassId());
		Object[] param=paramClass.toArray();
		saveOrUpdateOrDelete(sql.toString(), param);
		
	}

	@Override
	public int save(CClass cclass, Connection conn) {
		String sql="INSERT INTO CClass VALUES(?,?,?)";
		Object[] param={
			cclass.getcName(),
			cclass.geteName(),
			cclass.getStatus()
		};
		int cclassId=save(sql, param, conn);
		return cclassId;
	}

	@Override
	public void delete(CClass cclass, String type) {
		StringBuilder sql=new StringBuilder("DELETE FROM CClass WHERE 1=1 ");
		ArrayList<Object> parma=new ArrayList<Object>();
		try
		{
			if(cclass.getClassId()!=0)
			{
				sql.append(type+"cclassId=?");
				parma.add(cclass.getClassId());
			}
			
			if(cclass.getcName()!=null&&!"".equals(cclass.getcName()))
			{
				sql.append(type+"classcName=?");
				parma.add(cclass.getcName());
			}
			if(cclass.geteName()!=null&&!"".equals(cclass.geteName()))
			{
				sql.append(type+"cclasseName=?");
				parma.add(cclass.geteName());
			}
			if(cclass.getStatus()!=0)
			{
				sql.append(type+"cclassStatus=?");
				parma.add(cclass.getStatus());
			}
			
			saveOrUpdateOrDelete(sql.toString(), parma.toArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void update(CClass cclass, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE CClass SET cclassStatus=?");
		ArrayList<Object> paramClass=new ArrayList<Object>();
		paramClass.add(cclass.getStatus());
		
		sql.append(" WHERE cclassId=?");
		paramClass.add(cclass.getClassId());
		Object[] param=paramClass.toArray();
		update(sql.toString(), param, conn);
	}

	@Override
	public int getClassByConditions(CClass cclass) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		int cclassId=0;
		StringBuilder sql=new StringBuilder("SELECT cclassId FROM CClass WHERE cclasscName=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(cclass.getcName());
		if(cclass.geteName()!=null)
		{
			sql.append(" AND cclasseName=?");
			parma.add(cclass.geteName());
		}
		if(cclass.getStatus()!=0)
		{
			sql.append(" AND cclassStatus=?");
			parma.add(cclass.getStatus());
		}
		try {
			ps=conn.prepareStatement(sql.toString());
			rs=query(ps, parma.toArray());
			if(rs.next())
			{
				cclassId=rs.getInt("cclassId");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return cclassId;
	}

	
}
