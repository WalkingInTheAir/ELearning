package com.elearning.domain;

public class CourseType {
	private int id;
	private String name;

	public CourseType(){
		super();
	}
	public CourseType(int typeId, String typeName) {
		this();
		this.id = typeId;
		this.name = typeName;
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

}
