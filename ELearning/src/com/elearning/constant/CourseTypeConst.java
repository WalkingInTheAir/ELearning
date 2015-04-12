package com.elearning.constant;

public enum CourseTypeConst {
	TYPE1(1, "公共课"), 
	TYPE2(2, "公共课-院系级"),
	TYPE3(3, "专业课"),
	TYPE4(4, "专业课-班级级");
	
	private int key;
	private String desc;
	private CourseTypeConst(int key, String desc) {
		this.key = key;
		this.desc = desc;
	}
	
	public int getTypeKey(){
		return this.key;
	}
	
	public String getTypeDesc(){
		return this.desc;
	}
}
