package com.core.path.util;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

	public static void setRequestAttr(HttpServletRequest req, String attrName,
			Object value) {
		req.setAttribute(attrName, value);
	}
	
	public static void setRequestAttr(HttpServletRequest req, Map<String, Object> attrs){
		if(attrs == null || attrs.isEmpty())
			return;
		Iterator<String> it = attrs.keySet().iterator(); 
		while(it.hasNext()){
			String attrName = it.next();
			req.setAttribute(attrName, attrs.get(attrName));
		}
	}
	
	
	public static void clearRequestAttr(HttpServletRequest req, String[] attrNames){
		if(null == attrNames || attrNames.length == 0)
			return;
		
		for(String attrName : attrNames){
			req.removeAttribute(attrName);
		}
	}
	
	public static void copyRequestParam2Attr(HttpServletRequest req, String paramName){
		String paramValue = req.getParameter(paramName);
		req.setAttribute(paramName, paramValue);
	}
	public static void copyRequestParam2Attr(HttpServletRequest req, String[] paramNames){
		if(null == paramNames || paramNames.length == 0)
			return;
		for(String paramName : paramNames){
			copyRequestParam2Attr(req, paramName);
		}
	}
}
