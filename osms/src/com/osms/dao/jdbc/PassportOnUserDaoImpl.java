package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.PassportOnUserDao;
import com.osms.entity.PassportOnUser;
import com.osms.utils.Packager;

@Repository("passportOnUserDao")
public class PassportOnUserDaoImpl extends JDBCBase implements PassportOnUserDao {

	@Override
	public int save(PassportOnUser passportOnUser, Connection conn) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO PassportOnUser VALUES(?,?,?,?)";
		Object[] parma={
			passportOnUser.getUserId(),
			passportOnUser.getPassportId(),
			passportOnUser.getPassportPagePath(),
			passportOnUser.getStatus()
		};
		int id=save(sql, parma, conn);
		return id;
	}

	@Override
	public List<Integer> getPassportIds(int userId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Integer> passportIds=new ArrayList<Integer>();
		String sql="SELECT DISTINCT passport FROM PassportOnUser WHERE passportOnUserStatus=1 AND passportOnUserUser="+userId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				passportIds.add(rs.getInt("passport"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return passportIds;
	}

	@Override
	public List<PassportOnUser> getPassportOnUserByUserId(int userId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<PassportOnUser> passportOnUsers=new ArrayList<PassportOnUser>();
		String sql="SELECT DISTINCT * FROM PassportOnUser WHERE passportOnUserStatus=1 AND passportOnUserUser="+userId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				passportOnUsers.add(Packager.PackagerPassportOnUser(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return passportOnUsers;
	}

	@Override
	public void update(PassportOnUser passportOnUser, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE PassportOnUser SET passportOnUserStatus=?");
		List<Object> parmas=new ArrayList<Object>();
		if(passportOnUser.getUserId()!=0)
		{
			sql.append(", passportOnUserUser=?");
			parmas.add(passportOnUser.getUserId());
		}
		if(passportOnUser.getPassportId()!=0)
		{
			sql.append(", passport=?");
			parmas.add(passportOnUser.getPassportId());
		}
		if(passportOnUser.getPassportPagePath()!=null)
		{
			sql.append(", passportPagePath=?");
			parmas.add(passportOnUser.getPassportPagePath());
		}
		sql.append(", passportOnUserId=?");
		parmas.add(passportOnUser.getId());
		update(sql.toString(), parmas.toArray(), conn);
	}

}
