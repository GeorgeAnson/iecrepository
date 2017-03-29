package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.IdentityOnUserDao;
import com.osms.entity.IdentityOnUser;
import com.osms.utils.Packager;

@Repository("identityOnUserDao")
public class IdentityOnUserDaoImpl extends JDBCBase implements IdentityOnUserDao {

	@Override
	public int save(IdentityOnUser identityOnUser, Connection conn) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO IdentityOnUser VALUES(?,?,?,?,?,?,?,?)";
		Object[] parma={
			identityOnUser.getUserId(),
			identityOnUser.getNationalityId(),
			identityOnUser.getBirthday(),
			identityOnUser.getBirthplace(),
			identityOnUser.getHomeAddress(),
			identityOnUser.getPhone(),
			identityOnUser.getIsMarried(),
			identityOnUser.getStatus()
		};
		int id=save(sql, parma, conn);
		return id;
	}

	@Override
	public IdentityOnUser getIdentityOnUserByUserId(int userId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		IdentityOnUser identityOnUser=null;
		String sql="SELECT * FROM IdentityOnUser WHERE identityOnUserStatus=1 AND identityOnUserUser="+userId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				identityOnUser=Packager.PackagerIdentityOnUser(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return identityOnUser;
	}

	@Override
	public void update(IdentityOnUser identityOnUser, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE IdentityOnUser SET identityOnUserStatus=?");
		List<Object> parmas=new ArrayList<Object>();
		if(identityOnUser.getUserId()!=0)
		{
			sql.append(", identityOnUserUser=?");
			parmas.add(identityOnUser.getUserId());
		}
		if(identityOnUser.getNationalityId()!=0)
		{
			sql.append(", nationality=?");
			parmas.add(identityOnUser.getNationalityId());
		}
		if(identityOnUser.getBirthday()!=null)
		{
			sql.append(", birthday=?");
			parmas.add(identityOnUser.getBirthday());
		}
		if(identityOnUser.getBirthplace()!=null)
		{
			sql.append(", birthplace=?");
			parmas.add(identityOnUser.getBirthplace());
		}
		if(identityOnUser.getHomeAddress()!=null)
		{
			sql.append(", homeAddress=?");
			parmas.add(identityOnUser.getHomeAddress());
		}
		if(identityOnUser.getPhone()!=null)
		{
			sql.append(", homePhone=?");
			parmas.add(identityOnUser.getPhone());
		}
		if(identityOnUser.getIsMarried()!=0)
		{
			sql.append(", isMarried=?");
			parmas.add(identityOnUser.getIsMarried());
		}
		sql.append(" WHERE identityOnUserId=?");
		parmas.add(identityOnUser.getId());
		update(sql.toString(), parmas.toArray(), conn);
	}
}
