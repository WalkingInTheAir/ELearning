package com.elearning.web.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultMessage{

	private String message;
	private ResultType type;
	private JSONObject jsonResult = new JSONObject(); 
	
	public ResultMessage(ResultType type){
		this.type = type;
	}
	public ResultMessage(String message, ResultType type){
		this.message = message;
		this.type = type;
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
	
	public JSONObject getJSONResult() throws JSONException{
		jsonResult.put("msg", message);
		jsonResult.put("type", type.getType());
		return jsonResult;
	}

}
