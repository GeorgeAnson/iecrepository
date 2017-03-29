package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.EducationOnUserDao;
import com.osms.entity.EducationOnUser;
import com.osms.utils.Packager;

@Repository("educationOnUserDao")
public class EducationOnUserDaoImpl extends JDBCBase implements EducationOnUserDao {

	@Override
	public int save(EducationOnUser educationOnUser, Connection conn) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO EducationOnUser VALUES(?,?,?,?)";
		Object[] parma={
			educationOnUser.getUserId(),
			educationOnUser.getEducationTypeId(),
			educationOnUser.getIsOversea(),
			educationOnUser.getStatus()
		};
		int id=save(sql, parma, conn);
		return id;
	}

	@Override
	public EducationOnUser getEducationOnUserByUserId(int userId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		EducationOnUser educationOnUser=null;
		String sql="SELECT * FROM EducationOnUser WHERE educationOnUserStatus=1 AND educationOnUserUser="+userId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				educationOnUser=Packager.PackagerEducationOnUser(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return educationOnUser;
	}

	@Override
	public void upadte(EducationOnUser educationOnUser, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE EducationOnUser SET educationOnUserStatus=?");
		List<Object> parmas=new ArrayList<Object>();
		parmas.add(educationOnUser.getStatus());
		
		if(educationOnUser.getUserId()!=0)
		{
			sql.append(", educationOnUserUser=?");
			parmas.add(educationOnUser.getUserId());
		}
		if(educationOnUser.getEducationTypeId()!=0)
		{
			sql.append(", educationType=?");
			parmas.add(educationOnUser.getEducationTypeId());
		}
		if(educationOnUser.getIsOversea()!=0)
		{
			sql.append(", isOversea=?");
			parmas.add(educationOnUser.getIsOversea());
		}
		sql.append(" WHERE educationOnUserId=?");
		parmas.add(educationOnUser.getId());
		update(sql.toString(), parmas.toArray(), conn);
	}

}
