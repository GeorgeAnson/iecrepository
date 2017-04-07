package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.StudentTypeDao;
import com.osms.entity.StudentType;
import com.osms.utils.Packager;

@Repository("studentTypeDao")
public class StudentTypeDaoImpl extends JDBCBase implements StudentTypeDao {

	@Override
	public List<StudentType> getAllStudentType() {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<StudentType> studentTypes=new ArrayList<StudentType>();
		String sql="SELECT * FROM StudentType WHERE studentTypeStatus=1";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				studentTypes.add(Packager.PackagerStudentType(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return studentTypes;
	}

	@Override
	public StudentType getStudentTypeByTypeId(int studentTypeId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		StudentType studentType=null;
		String sql="SELECT * FROM StudentType WHERE studentTypeStatus=1 AND studentTypeId="+studentTypeId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				studentType=Packager.PackagerStudentType(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return studentType;
	}

}
