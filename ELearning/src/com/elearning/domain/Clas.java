package com.elearning.domain;
/**
 * 班级域模型
 * @author Administrator
 *
 */
public class Clas {
	private Long id;			//编号
	private String name;		//名称
	private Department dept;	//院系
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
}
