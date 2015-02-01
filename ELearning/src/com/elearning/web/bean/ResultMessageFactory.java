package com.elearning.web.bean;

public class ResultMessageFactory {

	public static ResultMessage getSuccessResult(String message) {
		return new ResultMessage(message, ResultType.SUCCESS);
	}
	public static ResultMessage getSuccessResult() {
		return new ResultMessage(ResultType.SUCCESS);
	}

	public static ResultMessage getFailResult() {
		return new ResultMessage(ResultType.FAILURE);
	}
	public static ResultMessage getFailResult(String message) {
		return new ResultMessage(message, ResultType.FAILURE);
	}

	public static ResultMessage getErrorResult(String message) {
		return new ResultMessage(message, ResultType.ERROR);
	}
	public static ResultMessage getErrorResult() {
		return new ResultMessage(ResultType.ERROR);
	}
}
