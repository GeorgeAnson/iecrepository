package com.osms.dao;

import java.util.List;

import com.osms.entity.Notice;

public interface NoticeDao {

	/**
	 * get notice object by noticeId
	 * @param noticeId
	 * @return
	 */
	public Notice getNoticeByNoticeId(int noticeId);
	
	/**
	 * get all notice
	 * @return
	 */
	public List<Notice> getAllNotice();
	
	/**
	 * save a notice object
	 * @param notice
	 */
	public void save(Notice notice);
	
	/**
	 * update a notice object
	 * @param notice
	 */
	public void update(Notice notice);
	
}
