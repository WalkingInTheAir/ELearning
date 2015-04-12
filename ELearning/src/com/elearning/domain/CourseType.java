package com.elearning.domain;

public class CourseType {
	private int id;
	private String name;

	public CourseType(){
		super();
	}
	public CourseType(int typeId, String typeName) {
		this(typeId);
		this.name = typeName;
	}
	public CourseType(int typeId){
		this();
		this.id = typeId;
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
