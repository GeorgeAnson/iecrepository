package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.osms.dao.ProfessionalTypeDao;
import com.osms.entity.ProfessionalTitleType;
import com.osms.utils.Packager;

@Repository("professionalTypeDao")
public class ProfessionalTypeDaoImpl extends JDBCBase implements ProfessionalTypeDao {

	@Override
	public ProfessionalTitleType getProfessionalTypeBytypeId(int professionalTitleTypeId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		ProfessionalTitleType professionalTitleType=null;
		String sql="SELECT * FROM ProfessionalTitleType WHERE professionalTitleTypeStatus=1 AND professionalTitleTypeId="+professionalTitleTypeId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				professionalTitleType=Packager.PackagerProfessionalTitleType(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return professionalTitleType;
	}

}
