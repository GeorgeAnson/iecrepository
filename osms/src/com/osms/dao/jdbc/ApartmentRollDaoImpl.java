package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.osms.dao.ApartmentRollDao;
import com.osms.entity.ApartmentRoll;
import com.osms.utils.Packager;

@Repository("apartmentRollDao")
public class ApartmentRollDaoImpl extends JDBCBase implements ApartmentRollDao {

	@Override
	public int save(ApartmentRoll apartmentRoll, Connection conn) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO ApartmentRoll VALUES(?,?,?,?)";
		Object[] parma={
			apartmentRoll.getUserId(),
			apartmentRoll.getProfessionalTitleTypeId(),
			apartmentRoll.getCardNumber(),
			apartmentRoll.getStatus()
		};
		int id=save(sql, parma, conn);
		return id;
	}

	@Override
	public ApartmentRoll getApartmentRollByUserId(int userId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		ApartmentRoll apartmentRoll=null;
		String sql="SELECT * FROM ApartmentRoll WHERE apartmentRollStatus=1 AND apartmentRollUser="+userId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				apartmentRoll=Packager.PackagerApartmentRoll(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return apartmentRoll;
	}

}
