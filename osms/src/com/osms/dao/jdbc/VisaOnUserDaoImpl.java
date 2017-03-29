package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.VisaOnUserDao;
import com.osms.entity.VisaOnUser;
import com.osms.utils.Packager;

@Repository("visaOnUserDao")
public class VisaOnUserDaoImpl extends JDBCBase implements VisaOnUserDao {

	@Override
	public int save(VisaOnUser visaOnUser, Connection conn) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO VisaOnUser VALUES(?,?,?,?,?)";
		Object[] parma={
			visaOnUser.getUserId(),
			visaOnUser.getVisaId(),
			visaOnUser.getVisaOnUserSchoolYear(),
			visaOnUser.getVisaOnUserTheSemester(),
			visaOnUser.getStatus()
		};
		int id=save(sql, parma, conn);
		return id;
	}

	@Override
	public VisaOnUser getVisaOnUserByUserId(int userId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		VisaOnUser visaOnUser=null;
		String sql="SELECT * FROM VisaOnUser WHERE visaOnUserStatus=1 AND visaOnUserUser="+userId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				visaOnUser=Packager.PackagerVisaOnUser(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return visaOnUser;
	}

	@Override
	public void update(VisaOnUser visaOnUser, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE VisaOnUser SET visa=?");
		List<Object> parmas=new ArrayList<Object>();
		if(visaOnUser.getVisaId()!=0)
		{
			parmas.add(visaOnUser.getVisaId());
		}else
		{
			parmas.add(null);
		}
		
		if(visaOnUser.getUserId()!=0)
		{
			sql.append(", visaOnUserUser=?");
			parmas.add(visaOnUser.getUserId());
		}
		if(visaOnUser.getVisaOnUserSchoolYear()!=null)
		{
			sql.append(", visaOnUserSchoolYear=?");
			parmas.add(visaOnUser.getVisaOnUserSchoolYear());
		}
		
		if(visaOnUser.getVisaOnUserTheSemester()!=0)
		{
			sql.append(", visaOnUserTheSemester=?");
			parmas.add(visaOnUser.getVisaOnUserTheSemester());
		}
		
		if(visaOnUser.getStatus()!=0)
		{
			sql.append(", visaOnUserStatus=?");
			parmas.add(visaOnUser.getStatus());
		}
		sql.append(" WHERE visaOnUserId=?");
		parmas.add(visaOnUser.getId());
		update(sql.toString(), parmas.toArray(), conn);
	}

}
