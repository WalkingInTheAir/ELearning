package com.elearning.domain;

public class Course {

	private int id;
	private String name;
	private String relativeId;
	private String relativeName;
	private CourseType ct;
	private Department dept;
	private Teacher teacher;
	private Clas clas;
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

	public String getRelativeId() {
		return relativeId;
	}

	public void setRelativeId(String relativeId) {
		this.relativeId = relativeId;
	}

	public String getRelativeName() {
		return relativeName;
	}

	public void setRelativeName(String relativeName) {
		this.relativeName = relativeName;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Clas getClas() {
		return clas;
	}

	public void setClas(Clas clas) {
		this.clas = clas;
	}
	
}
