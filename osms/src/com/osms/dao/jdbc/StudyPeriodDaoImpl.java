package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.StudyPeriodDao;
import com.osms.entity.StudyPeriod;
import com.osms.utils.Packager;

@Repository("studyPeriodDao")
public class StudyPeriodDaoImpl extends JDBCBase implements StudyPeriodDao {

	@Override
	public StudyPeriod getStudyPeriodByStudyPeriodId(int studyPeriodId) {
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		StudyPeriod studyPeriod = null;
		String sql = "SELECT * FROM StudyPeriod WHERE studyPeriodId=" + studyPeriodId;
		try {
			ps = conn.prepareStatement(sql);
			rs = query(ps);
			if (rs.next()) {
				studyPeriod = Packager.PackagerStudyPeriod(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return studyPeriod;
	}

	@Override
	public List<StudyPeriod> getAllStudyPeriod() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<StudyPeriod> studyPeriods=new ArrayList<StudyPeriod>();
		String sql="SELECT * FROM StudyPeriod WHERE studyPeriodStatus=1";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				studyPeriods.add(Packager.PackagerStudyPeriod(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		
		return studyPeriods;
	}

	@Override
	public int save(StudyPeriod studyPeriod, Connection conn) {
		String sql="INSERT INTO StudyPeriod VALUES(?,?,?)";
		Object[] parma={
			studyPeriod.getStartDate(),
			studyPeriod.getEndDate(),
			studyPeriod.getStatus()
		};
		int periodId=save(sql, parma, conn);
		return periodId;
	}

	@Override
	public void update(StudyPeriod studyPeriod) {
		StringBuilder sql=new StringBuilder("UPDATE StudyPeriod SET startDate=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		if(studyPeriod.getStartDate()!=null)
		{
			parma.add(studyPeriod.getStartDate());
		}else
		{
			parma.add(null);
		}
		if(studyPeriod.getEndDate()!=null)
		{
			sql.append(", endDate=?");
			parma.add(studyPeriod.getEndDate());
		}
		if(studyPeriod.getStatus()!=0)
		{
			sql.append(", studyPeriodStatus=?");
			parma.add(studyPeriod.getStatus());
		}
		saveOrUpdateOrDelete(sql.toString(), parma.toArray());
	}

	@Override
	public void delete(StudyPeriod studyPeriod, String type) {
		StringBuilder sql=new StringBuilder("DELETE FROM StudyPeriod WHERE 1=1 ");
		ArrayList<Object> parma=new ArrayList<Object>();
		try
		{
			if(studyPeriod.getStudyPeriodId()!=0)
			{
				sql.append(type+"studyPeriodId=?");
				parma.add(studyPeriod.getStudyPeriodId());
			}
			
			if(studyPeriod.getStartDate()!=null&&!"".equals(studyPeriod.getStartDate()))
			{
				sql.append(type+"startDate=?");
				parma.add(studyPeriod.getStartDate());
			}
			
			if(studyPeriod.getEndDate()!=null&&!"".equals(studyPeriod.getEndDate()))
			{
				sql.append(type+"endDate=?");
				parma.add(studyPeriod.getEndDate());
			}
			if(studyPeriod.getStatus()!=0)
			{
				sql.append(type+"studyPeriodStatus=?");
				parma.add(studyPeriod.getStatus());
			}
			saveOrUpdateOrDelete(sql.toString(), parma.toArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
