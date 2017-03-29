package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.GuaranteeDao;
import com.osms.entity.Guarantee;
import com.osms.utils.Packager;

@Repository("guaranteeDao")
public class GuaranteeDaoImpl extends JDBCBase implements GuaranteeDao {

	@Override
	public void save(Guarantee guarantee) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO Guarantee VALUES(?,?,?,?)";
		Object[] parma={
			guarantee.getGuaranteecName(),
			guarantee.getGuaranteeeName(),
			guarantee.getGuaranteePhone(),
			guarantee.getGuaranteeStatus()
		};
		save(sql, parma);
	}

	@Override
	public List<Guarantee> getAllGuarantee() {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Guarantee> guarantees=new ArrayList<Guarantee>();
		String sql="SELECT * FROM Guarantee WHERE guaranteeStatus=1";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while (rs.next()) {
				guarantees.add(Packager.PackagerGuarantee(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return guarantees;
	}

	@Override
	public void update(Guarantee guarantee) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE Guarantee SET guaranteeeName=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(guarantee.getGuaranteeeName());
		if(guarantee.getGuaranteecName()!=null&&!"".equals(guarantee.getGuaranteecName()))
		{
			sql.append(", guaranteecName=?");
			parma.add(guarantee.getGuaranteecName());
		}
		
		if(guarantee.getGuaranteePhone()!=null&&!"".equals(guarantee.getGuaranteePhone()))
		{
			sql.append(", guaranteePhone=?");
			parma.add(guarantee.getGuaranteePhone());
		}
		if(guarantee.getGuaranteeStatus()!=0)
		{
			sql.append(", guaranteeStatus=?");
			parma.add(guarantee.getGuaranteeStatus());
		}
		sql.append(" WHERE guaranteeId=?");
		parma.add(guarantee.getGuaranteeId());
		saveOrUpdateOrDelete(sql.toString(), parma.toArray());
	}

}
