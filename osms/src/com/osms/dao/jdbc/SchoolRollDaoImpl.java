package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.SchoolRollDao;
import com.osms.entity.SchoolRoll;
import com.osms.utils.Packager;

@Repository("schoolRollDao")
public class SchoolRollDaoImpl extends JDBCBase implements SchoolRollDao {

	@Override
	public int save(SchoolRoll schoolRoll, Connection conn) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO SchoolRoll VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		ArrayList<Object> parmas=new ArrayList<Object>();
		parmas.add(schoolRoll.getUserId());
		
		if(schoolRoll.getStudyPeriodId()!=0)
		{
			parmas.add(schoolRoll.getStudyPeriodId());
		}else
		{
			parmas.add(null);
		}
		
		if(schoolRoll.getRollStatusTypeId()!=0)
		{
			parmas.add(schoolRoll.getRollStatusTypeId());
		}else
		{
			parmas.add(null);
		}
		
		if(schoolRoll.getStudentTypeId()!=0)
		{
			parmas.add(schoolRoll.getStudentTypeId());
		}else
		{
			parmas.add(null);
		}
		
		Object[] parma={
				schoolRoll.getPlace(),
				schoolRoll.getCardNumber(),
				schoolRoll.getStudentNumber(),
				schoolRoll.getDormitoryNumber(),
				schoolRoll.getComeDate(),
				schoolRoll.getLeaveDate(),
				schoolRoll.getStatus()
			};
		
		for(Object o:parma)
		{
			parmas.add(o);
		}
		
		int id=save(sql, parmas.toArray(), conn);
		return id;
	}

	@Override
	public int getRollStatusTypeByUserId(int userId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		int rollStatusTypeId=0;
		String sql="SELECT rollStatusType FROM SchoolRoll WHERE schoolRollStatus=1 AND schoolRollUser="+userId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				rollStatusTypeId=rs.getInt("rollStatusType");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return rollStatusTypeId;
	}

	@Override
	public SchoolRoll getSchoolRollByUserId(int userId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		SchoolRoll schoolRoll=null;
		String sql="SELECT * FROM SchoolRoll WHERE schoolRollStatus=1 AND schoolRollUser="+userId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				schoolRoll=Packager.PackagerSchoolRoll(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return schoolRoll;
	}

	@Override
	public void update(SchoolRoll schoolRoll, Connection conn) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("UPDATE SchoolRoll SET schoolRollStatus=?");
		List<Object> parmas=new ArrayList<Object>();
		
		if(schoolRoll.getStatus()!=0)
		{
			parmas.add(schoolRoll.getStatus());
		}else
		{
			parmas.add(1);
		}
		
		if(schoolRoll.getUserId()!=0)
		{
			sql.append(", schoolRollUser=?");
			parmas.add(schoolRoll.getUserId());
		}
		if(schoolRoll.getStudyPeriodId()!=0)
		{
			sql.append(", studyPeriod=?");
			parmas.add(schoolRoll.getStudentTypeId());
		}
		
		if(schoolRoll.getRollStatusTypeId()!=0)
		{
			sql.append(", rollStatusType=?");
			parmas.add(schoolRoll.getRollStatusTypeId());
		}
		if(schoolRoll.getStudentTypeId()!=0)
		{
			sql.append(", studentType=?");
			parmas.add(schoolRoll.getStudentTypeId());
		}
		if(schoolRoll.getPlace()!=null)
		{
			sql.append(", place=?");
			parmas.add(schoolRoll.getPlace());
		}
		if(schoolRoll.getCardNumber()!=null)
		{
			sql.append(", scardNumber=?");
			parmas.add(schoolRoll.getCardNumber());
		}
		if(schoolRoll.getStudentNumber()!=null)
		{
			sql.append(", studentNumber=?");
			parmas.add(schoolRoll.getStudentNumber());
		}
		if(schoolRoll.getDormitoryNumber()!=null)
		{
			sql.append(", dormitoryNumber=?");
			parmas.add(schoolRoll.getDormitoryNumber());
		}
		if(schoolRoll.getComeDate()!=null)
		{
			sql.append(", comeDate=?");
			parmas.add(schoolRoll.getComeDate());
		}
		if(schoolRoll.getLeaveDate()!=null)
		{
			sql.append(", leaveDate=?");
			parmas.add(schoolRoll.getLeaveDate());
		}
		
		sql.append(" WHERE schoolRollId=?");
		parmas.add(schoolRoll.getId());
		update(sql.toString(), parmas.toArray(), conn);
	}

}
