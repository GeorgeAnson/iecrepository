package com.osms.service;

import java.util.List;
import java.util.Map;

import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.bean.AcademyMajorBean;

public interface AMCService {

	/**
	 * Map中键不可重复，键值可重复
	 * 装配AMC及班级，专业管理所需数据
	 * @param academyMap
	 * 		键-Academy，键值-amcId
	 * @param majorMap
	 * 		键-Academy，键值-Major
	 * @param classList
	 * 		AMC list集合
	 */
	public void matchAllAMC(Map<Integer,Academy> academyMap, Map<Integer, AcademyMajorBean> majorMap,
			Map<Integer,AMCOnUser> classMap);

	/**
	 * get amcOnUser by userId
	 * @param userId
	 * @return
	 */
	public List<AMCOnUser> getAMCByUserId(int userId);
	
}
