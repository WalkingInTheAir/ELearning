package com.elearning.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 院系域模型
 * 
 * @author Administrator
 */
public class Department {
	private int id; // 编号
	private String name; // 院系名称
	private Set<Major> majors = new HashSet<Major>();
	public Department() {
		super();
	}

	public Department(String name) {
		super();
		this.name = name;
	}
	public Department(int id, String name) {
		this(id);
		this.name = name;
	}
	public Department(int id) {
		super();
		this.id = id;
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

	public Set<Major> getMajors() {
		return majors;
	}

	public void setMajors(Set<Major> majors) {
		this.majors = majors;
	}

}
