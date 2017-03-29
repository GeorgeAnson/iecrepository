package com.osms.bean;

import java.io.Serializable;

/**
 * bean配置，处理config页面处理话专业管理数据加载
 * @author Administrator
 *
 */
public class AcademyMajorBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String academyChineseName=null;//学院中文名称
	private String academyEnglishName=null;//学院英文名称
	private String majorChineseName=null;//专业中文名称
	private String majorEnglishName=null;//专业英文名称
	
	public AcademyMajorBean()
	{
		
	}

	public AcademyMajorBean(String academyChineseName, String academyEnglishName, String majorChineseName,
			String majorEnglishName) {
		this.academyChineseName = academyChineseName;
		this.academyEnglishName = academyEnglishName;
		this.majorChineseName = majorChineseName;
		this.majorEnglishName = majorEnglishName;
	}

	public String getAcademyChineseName() {
		return academyChineseName;
	}

	public void setAcademyChineseName(String academyChineseName) {
		this.academyChineseName = academyChineseName;
	}

	public String getAcademyEnglishName() {
		return academyEnglishName;
	}

	public void setAcademyEnglishName(String academyEnglishName) {
		this.academyEnglishName = academyEnglishName;
	}

	public String getMajorChineseName() {
		return majorChineseName;
	}

	public void setMajorChineseName(String majorChineseName) {
		this.majorChineseName = majorChineseName;
	}

	public String getMajorEnglishName() {
		return majorEnglishName;
	}

	public void setMajorEnglishName(String majorEnglishName) {
		this.majorEnglishName = majorEnglishName;
	}

	@Override
	public String toString() {
		return "AcademyMajorBean [academyChineseName=" + academyChineseName + ", academyEnglishName="
				+ academyEnglishName + ", majorChineseName=" + majorChineseName + ", majorEnglishName="
				+ majorEnglishName + "]";
	}
	
}
