package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.UserTypeDao;
import com.osms.entity.UserType;
import com.osms.utils.Packager;

@Repository("userTypeDao")
public class UserTypeDaoImpl extends JDBCBase implements UserTypeDao {

	@Override
	public UserType getUserTypeByUserTypeId(int userTypeId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		UserType userType=null;
		String sql="SELECT * FROM UserType WHERE userTypeStatus=1 AND userTypeId="+userTypeId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				userType=Packager.PackagerUserType(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return userType;
	}

	@Override
	public List<UserType> getAllUserType() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<UserType> userTypes=new ArrayList<UserType>();
		String sql="SELECT * FROM UserType WHERE userTypeStatus=1";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				userTypes.add(Packager.PackagerUserType(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return userTypes;
	}

	@Override
	public void save(UserType userType) {
		String sql="INSERT INTO UserType VALUES(?,?,?)";
		Object[] parma={
			userType.getcName(),
			userType.geteName(),
			userType.getStatus()
		};
		saveOrUpdateOrDelete(sql, parma);
	}

	@Override
	public void update(UserType userType) {
		StringBuilder sql=new StringBuilder("UPDATE UserType SET userTypeeName=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(userType.geteName());
		if(userType.getcName()!=null&&!"".equals(userType.getcName()))
		{
			sql.append(", userTypecName=?");
			parma.add(userType.getcName());
		}
		if(userType.getStatus()!=0)
		{
			sql.append(", userTypeStatus=?");
			parma.add(userType.getStatus());
		}
		sql.append(" WHERE userTypeId=?");
		parma.add(userType.getUserTypeId());
		saveOrUpdateOrDelete(sql.toString(), parma.toArray());
	}

	@Override
	public void delete(UserType userType, String type) {
		StringBuilder sql=new StringBuilder("DELETE FROM UserType WHERE 1=1");
		ArrayList<Object> parma=new ArrayList<Object>();
		try
		{
			if(userType.getUserTypeId()!=0)
			{
				sql.append(type+"UserTypeId=?");
				parma.add(userType.getUserTypeId());
			}
			
			if(userType.getcName()!=null&&!"".equals(userType.getcName()))
			{
				sql.append(type+"userTypecName=?");
				parma.add(userType.getcName());
			}
			if(userType.geteName()!=null&&!"".equals(userType.geteName()))
			{
				sql.append(type+"userTypeeName=?");
				parma.add(userType.geteName());
			}
			if(userType.getStatus()!=0)
			{
				sql.append(type+"userTypeStatus=?");
				parma.add(userType.getStatus());
			}
			saveOrUpdateOrDelete(sql.toString(), parma.toArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
