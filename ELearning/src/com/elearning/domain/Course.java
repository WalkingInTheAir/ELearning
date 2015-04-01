package com.elearning.domain;

public class Course {

	private int id;
	private String name;

	private CourseType ct;
	private Department dept;

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

	public CourseType getCt() {
		return ct;
	}

	public void setCt(CourseType ct) {
		this.ct = ct;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

}
