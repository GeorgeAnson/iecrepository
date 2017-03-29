package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.NoticeDao;
import com.osms.entity.Notice;
import com.osms.utils.Packager;

@Repository("noticeDao")
public class NoticeDaoImpl extends JDBCBase implements NoticeDao {

	@Override
	public Notice getNoticeByNoticeId(int noticeId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Notice notice=null;
		String sql="SELECT * FROM Notice WHERE noticeStatus=1 AND noticeId="+noticeId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				notice=Packager.PackagerNotice(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return notice;
	}

	@Override
	public List<Notice> getAllNotice() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Notice> notices=new ArrayList<Notice>();
		String sql="SELECT noticeId, noticeType, title, writer, publishDate FROM Notice WHERE noticeStatus=1 ORDER BY publishDate DESC";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				Notice notice=new Notice();
				notice.setId(rs.getInt("noticeId"));
				notice.setNoticeTypeId(rs.getInt("noticeType"));
				notice.setTitle(rs.getString("title"));
				notice.setWriter(rs.getString("writer"));
				notice.setPublishDate(rs.getDate("publishDate"));
				notices.add(notice);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return notices;
	}

	@Override
	public void save(Notice notice) {
		String sql="INSERT INTO Notice VALUES(?,?,?,?,?,?,?,?)";
		Object[] parma={
			notice.getUserId(),
			notice.getNoticeTypeId(),
			notice.getTitle(),
			notice.getContent(),
			notice.getWriter(),
			notice.getDownLoadPath(),
			notice.getPublishDate(),
			notice.getStatus()
		};
		saveOrUpdateOrDelete(sql, parma);
	}

	@Override
	public void update(Notice notice) {
		StringBuilder sql=new StringBuilder("UPDATE Notice SET noticeStatus=?");
		ArrayList<Object> parmaNotice=new ArrayList<Object>();
		
		parmaNotice.add(notice.getStatus());
		
		if(notice.getUserId()!=0)
		{
			sql.append(", noticeUser=?");
			parmaNotice.add(notice.getUserId());
		}
		
		if(notice.getNoticeTypeId()!=0)
		{
			sql.append(", noticeType=?");
			parmaNotice.add(notice.getNoticeTypeId());
		}
		
		if(notice.getTitle()!=null&&!"".equals(notice.getTitle()))
		{
			sql.append(", title=?");
			parmaNotice.add(notice.getTitle());
		}
		
		if(notice.getContent()!=null&&!"".equals(notice.getContent()))
		{
			sql.append(", ccontent=?");
			parmaNotice.add(notice.getContent());
		}
		
		if(notice.getWriter()!=null&&!"".equals(notice.getWriter()))
		{
			sql.append(", writer=?");
			parmaNotice.add(notice.getWriter());
		}
		
		if(notice.getDownLoadPath()!=null&&!"".equals(notice.getDownLoadPath()))
		{
			sql.append(", downLoadPath=?");
			parmaNotice.add(notice.getDownLoadPath());
		}
		
		if(notice.getPublishDate()!=null&&!"".equals(notice.getPublishDate()))
		{
			sql.append(", publishDate=?");
			parmaNotice.add(notice.getPublishDate());
		}
	
		sql.append(" WHERE noticeId=?");
		parmaNotice.add(notice.getId());
		Object[] parma=parmaNotice.toArray();
		saveOrUpdateOrDelete(sql.toString(), parma);
	}
}
