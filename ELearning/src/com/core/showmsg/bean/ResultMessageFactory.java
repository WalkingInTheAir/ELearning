package com.core.showmsg.bean;
/**
 * 生成提示信息java bean的工厂
 * @author BlackHorse
 * @version 1.0
 */
public class ResultMessageFactory {

	private static ResultMessage getResultMessageInstance(ResultType type) {
		return new ResultMessage(type);
	}

	private static ResultMessage getResultMessageInstance(String message,
			ResultType type) {
		return new ResultMessage(message, type);
	}

	/**
	 * SUCCESS级别
	 * @param message
	 * @return
	 */
	public static ResultMessage getSuccessResult(String message) {
		return getResultMessageInstance(message, ResultType.SUCCESS);
	}

	/**
	 * SUCCESS级别
	 * @return
	 */
	public static ResultMessage getSuccessResult() {
		return getResultMessageInstance(ResultType.SUCCESS);
	}

	/**
	 * ERROR级别
	 * @param message
	 * @return
	 */
	public static ResultMessage getErrorResult(String message) {
		return getResultMessageInstance(message, ResultType.ERROR);
	}

	/**
	 * ERROR级别
	 * @return
	 */
	public static ResultMessage getErrorResult() {
		return getResultMessageInstance(ResultType.ERROR);
	}
	
	/**
	 * INFO级别
	 * @param message
	 * @return
	 */
	public static ResultMessage getInfoResult(String message) {
		return getResultMessageInstance(message, ResultType.INFO);
	}

	/**
	 * INFO级别
	 * @return
	 */
	public static ResultMessage getInfoResult() {
		return getResultMessageInstance(ResultType.INFO);
	}
	
	/**
	 * WARNING级别
	 * @param message
	 * @return
	 */
	public static ResultMessage getWarningResult(String message) {
		return getResultMessageInstance(message, ResultType.WARNING);
	}

	/**
	 * WARNING级别
	 * @return
	 */
	public static ResultMessage getWarningResult() {
		return getResultMessageInstance(ResultType.WARNING);
	}

}
