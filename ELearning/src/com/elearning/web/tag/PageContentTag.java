package com.elearning.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import net.sf.json.JSONObject;

/**
 * 动态模板内容标签 使用一套模板页面，动态生成模板内容
 * 
 * @author Administrator
 *
 */
public class PageContentTag extends BaseTag {
	private static final long serialVersionUID = 8169655960425738465L;
	private String defaultPage = "index.jsp";

	@Override
	public int doStartTag() throws JspException {
		try {
			String page = (String) super.req.getAttribute("include");		// 从Attribute中获取请求的页面或URL
			int menuIndex = -1; 											// 当前激活的模板菜单项
			int itemIndex = -1; 											// 当前激活的模板菜单子项
			int tabIndex = 0; 												// 当前激活的内容区tab编号
			JSONObject msgJson = (JSONObject) super.req.getAttribute("msg");// 错误/成功提示信息
			if (null == page || "".equals(page.trim())
					|| "#".equals(page.trim())) {
				page = defaultPage;
			} else {
				String strMenuIndex = (String) super.req
						.getAttribute("menuIndex");
				String strItemIndex = (String) super.req
						.getAttribute("itemIndex");
				String strTabIndex = (String) super.req
						.getAttribute("tabIndex");
				String regex = "[0-9]+";
				if (null != strMenuIndex) {
					menuIndex = strMenuIndex.matches(regex) ? Integer
							.parseInt(strMenuIndex) : -1;
				}
				if (null != strItemIndex) {
					itemIndex = strItemIndex.matches(regex) ? Integer
							.parseInt(strItemIndex) : -1;
				}
				if (null != strTabIndex) {
					tabIndex = strTabIndex.matches(regex) ? Integer
							.parseInt(strTabIndex) : 0;
				}
			}
			StringBuffer script = new StringBuffer();
			// 生成javascript变量，供前台判断调用
			script.append("<script type='text/javascript'>\n")
					.append("var menuIndex = " + menuIndex).append(";\n")
					.append("var itemIndex = " + itemIndex).append(";\n")
					.append("var tabIndex = " + tabIndex).append(";\n")
					.append("var includePage = '" + page + "'").append(";\n")
					.append("var msg = " + msgJson + "").append(";\n")
					.append("</script>");
			// 动态包含内容
			super.pageContext.include(page);
			super.res.getWriter().write(script.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Tag.EVAL_PAGE;
	}

}
