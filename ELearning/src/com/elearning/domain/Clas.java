package com.elearning.domain;

/**
 * 班级域模型
 * 
 * @author Administrator
 *
 */
public class Clas {
	private int id; // 编号
	private String name; // 名称
	private Major major; // 专业

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

}
