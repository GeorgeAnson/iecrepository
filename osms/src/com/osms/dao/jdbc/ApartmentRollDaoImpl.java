package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

	@Override
	public void update(ApartmentRoll apartmentRoll, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE ApartmentRoll SET apartmentRollStatus=?");
		ArrayList<Object> parmas=new ArrayList<Object>();
		parmas.add(apartmentRoll.getStatus()==0?1:apartmentRoll.getStatus());
		
		if(apartmentRoll.getCardNumber()!=null)
		{
			sql.append(", tcardNumber=?");
			parmas.add(apartmentRoll.getCardNumber());
		}
		if(apartmentRoll.getProfessionalTitleTypeId()!=0)
		{
			sql.append(", professionalTitleType=?");
			parmas.add(apartmentRoll.getProfessionalTitleTypeId());
		}
		
		sql.append(" WHERE apartmentRollId=?");
		parmas.add(apartmentRoll.getId());
		update(sql.toString(), parmas.toArray(), conn);
	}

}
