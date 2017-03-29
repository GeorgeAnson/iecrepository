package com.osms.service;

import java.util.List;
import java.util.Map;

import com.osms.entity.AMCOnUser;
import com.osms.entity.Academy;
import com.osms.bean.AcademyMajorBean;

public interface AMCService {

	/**
	 * Map�м������ظ�����ֵ���ظ�
	 * װ��AMC���༶��רҵ������������
	 * @param academyMap
	 * 		��-Academy����ֵ-amcId
	 * @param majorMap
	 * 		��-Academy����ֵ-Major
	 * @param classList
	 * 		AMC list����
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
