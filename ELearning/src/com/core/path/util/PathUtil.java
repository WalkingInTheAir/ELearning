package com.core.path.util;

import javax.servlet.http.HttpServletRequest;
/**
 * 路径相关操作工具类
 * @author Administrator
 *
 */
public class PathUtil {
	
	/**
	 * 获取web项目根目录
	 * @param req
	 * @return
	 */
	public static String getBasePath(HttpServletRequest req){
		
		StringBuffer buff = new StringBuffer();
		buff.append(req.getScheme())
			.append("://")
			.append(req.getServerName())
			.append(":")
			.append(req.getServerPort())
			.append(req.getContextPath())
			.append("/");
		return buff.toString();
	}
}
