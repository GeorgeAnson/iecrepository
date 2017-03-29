package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.RollStatusTypeDao;
import com.osms.entity.RollStatusType;
import com.osms.utils.Packager;

@Repository("rollStatusTypeDao")
public class RollStatusTypeDaoImpl extends JDBCBase implements RollStatusTypeDao {

	@Override
	public RollStatusType getStatusByStatusId(int rollStatusTypeId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		RollStatusType status=null;
		String sql="SELECT * FROM RollStatusType WHERE rollStatusTypeStatus=1 AND rollStatusTypeId="+rollStatusTypeId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				status=Packager.PackagerStatus(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return status;
	}

	@Override
	public List<RollStatusType> getAllStatus() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<RollStatusType> status=new ArrayList<RollStatusType>();
		String sql="SELECT * FROM RollStatusType WHERE rollStatusTypeStatus=1";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				status.add(Packager.PackagerStatus(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		
		return status;
	}

	@Override
	public void save(RollStatusType rollStatusType) {
		String sql="INSERT INTO RollStatusType VALUES(?,?,?)";
		Object[] parma={
			rollStatusType.getcName(),
			rollStatusType.geteName(),
			rollStatusType.getStatus()
		};
		saveOrUpdateOrDelete(sql, parma);
	}

	@Override
	public void update(RollStatusType rollStatusType) {
		StringBuilder sql= new StringBuilder("UPDATE RollStatusType SET rollStatusTypeeName=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(rollStatusType.geteName());
		
		if(rollStatusType.getcName()!=null&&!"".equals(rollStatusType.getcName()))
		{
			sql.append(", rollStatusTypecName=?");
			parma.add(rollStatusType.getcName());
		}
		if(rollStatusType.getStatus()!=0)
		{
			sql.append(", rollStatusTypeStatus=?");
			parma.add(rollStatusType.getStatus());
		}
		sql.append(" WHERE rollStatusTypeId=?");
		parma.add(rollStatusType.getRollStatusTypeId());
		saveOrUpdateOrDelete(sql.toString(), parma.toArray());
	}

	@Override
	public void delete(RollStatusType rollStatusType, String type) {
		StringBuilder sql=new StringBuilder("DELETE FROM RollStatusType WHERE 1=1 ");
		ArrayList<Object> parma=new ArrayList<Object>();
		try
		{
			if(rollStatusType.getRollStatusTypeId()!=0)
			{
				sql.append(type+"rollStatusTypeId=?");
				parma.add(rollStatusType.getRollStatusTypeId());
			}
			
			if(rollStatusType.getcName()!=null&&!"".equals(rollStatusType.getcName()))
			{
				sql.append(type+"rollStatusTypecName=?");
				parma.add(rollStatusType.getcName());
			}
			if(rollStatusType.geteName()!=null&&!"".equals(rollStatusType.geteName()))
			{
				sql.append(type+"rollStatusTypeeName=?");
				parma.add(rollStatusType.geteName());
			}
			if(rollStatusType.getStatus()!=0)
			{
				sql.append(type+"rollStatusTypeStatus=?");
				parma.add(rollStatusType.getStatus());
			}
			
			saveOrUpdateOrDelete(sql.toString(), parma.toArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
}
