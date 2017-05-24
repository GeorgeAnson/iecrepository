package com.osms.dao.jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.VisaDao;
import com.osms.entity.Visa;
import com.osms.utils.Packager;

@Repository("visaDao")
public class VisaDaoImpl extends JDBCBase implements VisaDao {

	@Override
	public int save(Visa visa, Connection conn) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO Visa VALUES(?,?,?,?,?)";
		Object[] parma={
			visa.getRegisterDeadLine(),
			visa.getIntermediaryName(),
			visa.getIntermediaryPhone(),
			visa.getGuaranteeId(),
//			visa.getVisaDueDate(),
			visa.getStatus()
		};
		int visaId=save(sql, parma, conn);
		return visaId;
	}

	@Override
	public Visa getVisaByvisaId(int visaId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Visa visa=null;
		String sql="SELECT * FROM Visa WHERE visaStatus=1 AND visaId="+visaId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				visa=Packager.PackagerVisa(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return visa;
	}

	@Override
	public void update(Visa visa, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE Visa SET guarantee=?");
		List<Object> parmas=new ArrayList<Object>();
		if(visa.getGuaranteeId()!=0)
		{
			parmas.add(visa.getGuaranteeId());
		}else
		{
			parmas.add(null);
		}
		
		if(visa.getRegisterDeadLine()!=null)
		{
			sql.append(", registerDeadLine=?");
			parmas.add(visa.getRegisterDeadLine());
		}
		if(visa.getIntermediaryName()!=null)
		{
			sql.append(", intermediaryName=?");
			parmas.add(visa.getIntermediaryName());
		}
		if(visa.getIntermediaryPhone()!=null)
		{
			sql.append(", intermediaryPhone=?");
			parmas.add(visa.getIntermediaryPhone());
		}
//		if(visa.getVisaDueDate()!=null)
//		{
//			sql.append(", visaDueDate=?");
//			parmas.add(visa.getVisaDueDate());
//		}
		if(visa.getStatus()!=0)
		{
			sql.append(", visaStatus=?");
			parmas.add(visa.getStatus());
		}
		sql.append(" WHERE visaId=?");
		parmas.add(visa.getVisaId());
		update(sql.toString(), parmas.toArray(), conn);
	}
	
}
