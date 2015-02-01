package com.elearning.web.bean;

public enum ResultType {

	SUCCESS("S"), FAILURE("F"), ERROR("E");
	
	private String type;

	private ResultType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
