package com.elearning.domain;

import java.util.HashSet;
import java.util.Set;

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
	private Set<Course> courses = new HashSet<Course>();
	public Clas() {
		super();
	}
	public Clas(int classId) {
		this();
		this.id = classId;
	}

	public Clas(int classId, String className, Major major) {
		this();
		this.id = classId;
		this.name = className;
		this.major = major;
	}

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
	@Override
	public int hashCode() {
		return this.id;
	}
	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof Clas) {
			Clas c = (Clas) obj;
			return c.id == this.id && null != c.name && c.name.equals(this.name);
		}
		return false;
	}
	public Set<Course> getCourses() {
		return courses;
	}
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

}
