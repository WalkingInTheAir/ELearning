package com.core.showmsg.bean;

import net.sf.json.JSONObject;

/**
 * 提示信息的java bean
 * @author BlackHorse
 * @version 1.0
 */
public class ResultMessage{

	private String message;
	private ResultType type;
	private JSONObject jsonResult; 
	
	public ResultMessage(){
		super();
		jsonResult = new JSONObject();
	}
	public ResultMessage(ResultType type){
		this();
		this.type = type;
	}
	public ResultMessage(String message, ResultType type){
		this(type);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ResultType getType() {
		return type;
	}
	public void setType(ResultType type) {
		this.type = type;
	}
	
	public JSONObject toJSONObj(){
		jsonResult.put("msg", message);
		jsonResult.put("type", type.getType());
		return jsonResult;
	}

}
