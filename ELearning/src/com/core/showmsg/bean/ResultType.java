package com.core.showmsg.bean;
/**
 * 信息的基础类型
 * @author BlackHorse
 * @version 1.0
 */
public enum ResultType {

	SUCCESS("S"), 
	INFO("I"), 
	ERROR("E"), 
	WARNING("W");

	private String type;

	private ResultType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
