package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.PassportDao;
import com.osms.entity.Passport;
import com.osms.utils.Packager;

@Repository("passportDao")
public class PassportDaoImpl extends JDBCBase implements PassportDao {

	@Override
	public int save(Passport passport, Connection conn) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO Passport VALUES(?,?)";
		Object[] parma={
			passport.getPassportNumber(),
			passport.getStatus()
		};
		int passportId=save(sql, parma, conn);
		return passportId;
	}

	@Override
	public Passport getPassportByPassportId(int passportId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Passport passport=null;
		String sql="SELECT * FROM Passport WHERE passportStatus=1 AND passportId="+passportId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				passport=Packager.PackagerPassport(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return passport;
	}

	@Override
	public void update(Passport passport, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE Passport SET passportStatus=?");
		List<Object> parmas=new ArrayList<Object>();
		parmas.add(passport.getStatus());
		if(passport.getPassportNumber()!=null)
		{
			sql.append(", passportNumber=?");
			parmas.add(passport.getPassportNumber());
		}
		sql.append(", passportId=?");
		parmas.add(passport.getPassportId());
		
		update(sql.toString(), parmas.toArray(), conn);
	}
}
