package com.elearning.domain;

import java.util.HashSet;
import java.util.Set;

public class Major {
	private int id;
	private String name;
	private Department dept;
	private Set<Clas> classes = new HashSet<Clas>();
	public Major(){
		super();
	}
	public Major(int id){
		this();
		this.id = id;
	}
	public Major(int majorId, String majorName, Department dept) {
		this(majorId);
		this.name = majorName;
		this.dept = dept;
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

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}
	public Set<Clas> getClasses() {
		return classes;
	}
	public void setClasses(Set<Clas> classes) {
		this.classes = classes;
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
		if (obj == this){
			return true;
		}
		if (obj instanceof Major) {
			Major m = (Major) obj;
			return m.id == this.id && null != m.name && m.name.equals(this.name);
		}
		return false;
	}
	
	
}
