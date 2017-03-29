package com.osms.bean;

import java.io.Serializable;

/**
 * bean���ã�����configҳ�洦��רҵ�������ݼ���
 * @author Administrator
 *
 */
public class AcademyMajorBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String academyChineseName=null;//ѧԺ��������
	private String academyEnglishName=null;//ѧԺӢ������
	private String majorChineseName=null;//רҵ��������
	private String majorEnglishName=null;//רҵӢ������
	
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
