package com.osms.dao;

import java.util.List;

import com.osms.entity.NoticeType;

public interface NoticeTypeDao {

	/**
	 * get noticeType object by noticeTypeId
	 * @param noticeTypeId
	 * @return
	 */
	public NoticeType getNoticeTypeByNoticeTypeId(int noticeTypeId);
	
	/**
	 * get all noticeType objects
	 * @return
	 */
	public List<NoticeType> getAllNoticeType();
	
	/**
	 * save a noticeType object
	 * @param noticeType
	 */
	public void save(NoticeType noticeType);
	
	/**
	 * update a noticeType object
	 * @param noticeType
	 */
	public void update(NoticeType noticeType);
	
	/**
	 * delete operation
	 * @param noticeType
	 * 		noticeType object
	 * @param type
	 * 		key words operation: AND operation, OR operation
	 */
	public void delete(NoticeType noticeType, String type);
}
