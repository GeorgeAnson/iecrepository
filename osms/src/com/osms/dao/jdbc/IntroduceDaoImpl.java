package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.osms.dao.IntroduceDao;
import com.osms.entity.Introduce;
import com.osms.utils.Packager;

@Repository("introduceDao")
public class IntroduceDaoImpl extends JDBCBase implements IntroduceDao {

	@Override
	public Introduce getIntroduceByIntroduceId(int introduceId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="SELECT * FROM Introduce WHERE introduceStatus=1 AND introduceId="+introduceId;
		Introduce introduce=null;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				introduce=Packager.PackagerIntroduce(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return introduce;
	}

}
