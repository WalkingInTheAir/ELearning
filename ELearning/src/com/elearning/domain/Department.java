package com.elearning.domain;

/**
 * 院系域模型
 * @author Administrator
 */
public class Department {
	private Long id; 		// 编号
	private String name; 	// 院系名称

	public Department() {
		super();
	}

	public Department(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
