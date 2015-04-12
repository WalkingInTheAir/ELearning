package com.elearning.domain;

public class Teacher extends User {
	private Department dept; // 部门
	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

}
