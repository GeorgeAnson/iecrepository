package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.NoticeTypeDao;
import com.osms.entity.NoticeType;
import com.osms.utils.Packager;

@Repository("noticeTypeDao")
public class NoticeTypeDaoImpl extends JDBCBase implements NoticeTypeDao {

	@Override
	public NoticeType getNoticeTypeByNoticeTypeId(int noticeTypeId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		NoticeType noticeType=null;
		String sql="SELECT * FROM NoticeType WHERE noticeTypeStatus=1 AND noticeTypeId="+noticeTypeId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				noticeType=Packager.PackagerNoticeType(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return noticeType;
	}

	@Override
	public List<NoticeType> getAllNoticeType() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<NoticeType> noticeTypes=new ArrayList<NoticeType>();
		String sql="SELECT * FROM NoticeType WHERE noticeTypeStatus=1";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				noticeTypes.add(Packager.PackagerNoticeType(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return noticeTypes;
	}

	@Override
	public void save(NoticeType noticeType) {
		String sql="INSERT INTO NoticeType VALUES(?,?,?)";
		Object[] parma={
			noticeType.getcName(),
			noticeType.geteName(),
			noticeType.getStatus()
		};
		saveOrUpdateOrDelete(sql, parma);
	}

	@Override
	public void update(NoticeType noticeType) {
		StringBuilder sql= new StringBuilder("UPDATE NoticeType SET noticeTypeeName=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		if(noticeType.geteName()!=null)
		{
			parma.add(noticeType.geteName());
		}
		
		if(noticeType.getcName()!=null&&!"".equals(noticeType.getcName()))
		{
			sql.append(", noticeTypecName=?");
			parma.add(noticeType.getcName());
		}
		if(noticeType.getStatus()!=0)
		{
			sql.append(", noticeTypeStatus=?");
			parma.add(noticeType.getStatus());
		}
		sql.append(" WHERE noticeTypeId=?");
		parma.add(noticeType.getNoticeTypeId());
		saveOrUpdateOrDelete(sql.toString(), parma.toArray());
	}

	@Override
	public void delete(NoticeType noticeType, String type) {
		StringBuilder sql=new StringBuilder("DELETE FROM NoticeType WHERE 1=1");
		ArrayList<Object> parma=new ArrayList<Object>();
		try
		{
			if(noticeType.getNoticeTypeId()!=0)
			{
				sql.append(type+"noticeTypeId=?");
				parma.add(noticeType.getNoticeTypeId());
			}
			
			if(noticeType.getcName()!=null&&!"".equals(noticeType.getcName()))
			{
				sql.append(type+"noticeTypecName=?");
				parma.add(noticeType.getcName());
			}
			if(noticeType.geteName()!=null&&!"".equals(noticeType.geteName()))
			{
				sql.append(type+"noticeTypeeName=?");
				parma.add(noticeType.geteName());
			}
			if(noticeType.getStatus()!=0)
			{
				sql.append(type+"noticeTypeStatus=?");
				parma.add(noticeType.getStatus());
			}
			saveOrUpdateOrDelete(sql.toString(), parma.toArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
