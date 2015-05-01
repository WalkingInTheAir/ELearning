package com.core.util.path;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
/**
 * 路径相关操作工具类
 * @author Administrator
 *
 */
public class PathUtil {
	private static Map<String, String> common = new HashMap<String, String>();
	static {
		Properties pro = new Properties();
		try {
			// 读取并解析数据库配置文件
			pro.load(PathUtil.class.getResourceAsStream("/common.properties"));
			@SuppressWarnings("unchecked")
			Enumeration<String> names=(Enumeration<String>) pro.propertyNames();
			while(names.hasMoreElements()){
			    String key = names.nextElement();
			    common.put(key, pro.getProperty(key));
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
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
	
	
	public static String getUploadPath(UploadPathKey key){
		String path = "";
		if("courseware".equals(key.value())){
			path = common.get("location.upload." + key.value());
		}
		return path;
	}
	
	public enum UploadPathKey {
		COURSEWARE("courseware");
		private String key;
		UploadPathKey(String key) {
			this.key = key;
		}

		public String value() {
			return this.key;
		}
	}
}
