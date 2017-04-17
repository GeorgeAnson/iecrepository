package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.UserDao;
import com.osms.entity.Users;
import com.osms.utils.Packager;

@Repository("userDao")
public class UserDaoImpl extends JDBCBase implements UserDao{
	
	@Override
	public Users getUserByUserId(int userId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Users user=null;
		
		String sql="SELECT * FROM Users WHERE userStatus=1 AND userId="+userId;
		
		try
		{
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				user=Packager.PackagerUser(rs);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		
		return user;
	}

	
	@Override
	public int save(Users user, Connection conn) {
		String sql="INSERT INTO Users VALUES(?,?,?,?,?,?,?,?)";
		Object[] param={
			user.getFullName(),
			user.getGender(),
			user.getUserTypeId(),
			user.getPhone(),
			user.getEmail(),
			user.getPassword(),
			user.getRegisterDate(),
			user.getStatus()
		};
		//save a user's information and return the object's id
		int userId=save(sql, param, conn);
		return userId;
	}


	@Override
	public void update(Users user) {
		StringBuilder sql= new StringBuilder("UPDATE Users SET gender=?");
		ArrayList<Object> paramList=new ArrayList<Object>();
		paramList.add(user.getGender());
		
		if(user.getUserTypeId()!=0||(user.getUserType()!=null&&user.getUserType().getUserTypeId()!=0))
		{
			sql.append(" ,userType=?");
			paramList.add(user.getUserTypeId()==0?user.getUserType().getUserTypeId():user.getUserTypeId());
		}else
		{
			paramList.add(null);
		}
		//if status equals 0,it means null
		if(user.getStatus()!=0)
		{
			sql.append(", userStatus=?");
			paramList.add(user.getStatus());
		}
		
		if(user.getFullName()!=null)
		{
			sql.append(", fullName=?");
			paramList.add(user.getFullName());
		}
		
		if(user.getPhone()!=null)
		{
			sql.append(", userPhone=?");
			paramList.add(user.getPhone());
		}
		
		if(user.getEmail()!=null)
		{
			sql.append(", email=?");
			paramList.add(user.getEmail());
		}
		
		if(user.getPassword()!=null)
		{
			sql.append(", password=?");
			paramList.add(user.getPassword());
		}
		
		sql.append(" WHERE userId=?");
		paramList.add(user.getUserId());
		
		Object[] param=paramList.toArray();
		saveOrUpdateOrDelete(sql.toString(), param);
	}


	@Override
	public Users getUserByCondition(String condition, int status) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Users user=null;
		String sql="SELECT * FROM Users WHERE userStatus=? AND (userPhone=? OR email=?)";
		Object[] parma={
			status,
			condition,
			condition
		};
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps, parma);
			if(rs.next())
			{
				user=Packager.PackagerUser(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}


	@Override
	public List<Users> getUserByUserTypeId(int userTypeId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Users> users=new ArrayList<Users>();
		String sql="SELECT userId, fullName FROM Users WHERE userStatus=1 AND userType="+userTypeId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				Users user=new Users();
				user.setUserId(rs.getInt("userId"));
				user.setFullName(rs.getString("fullName"));
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return users;
	}


	@Override
	public void update(Users user, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql= new StringBuilder("UPDATE Users SET gender=?");
		ArrayList<Object> paramList=new ArrayList<Object>();
		paramList.add(user.getGender());
		
		if(user.getUserTypeId()!=0||(user.getUserType()!=null&&user.getUserType().getUserTypeId()!=0))
		{
			sql.append(" ,userType=?");
			paramList.add(user.getUserTypeId()==0?user.getUserType().getUserTypeId():user.getUserTypeId());
		}else
		{
			paramList.add(null);
		}
		//if status equals 0,it means null
		if(user.getStatus()!=0)
		{
			sql.append(", userStatus=?");
			paramList.add(user.getStatus());
		}
		
		if(user.getFullName()!=null)
		{
			sql.append(", fullName=?");
			paramList.add(user.getFullName());
		}
		
		if(user.getPhone()!=null)
		{
			sql.append(", userPhone=?");
			paramList.add(user.getPhone());
		}
		
		if(user.getEmail()!=null)
		{
			sql.append(", email=?");
			paramList.add(user.getEmail());
		}
		
		if(user.getPassword()!=null)
		{
			sql.append(", password=?");
			paramList.add(user.getPassword());
		}
		
		sql.append(" WHERE userId=?");
		paramList.add(user.getUserId());
		update(sql.toString(), paramList.toArray(), conn);
	}
}
