package com.elearning.service;

import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.elearning.domain.FAQ;
		
public interface IFAQService {
	public ResultMessage addFAQ(FAQ faq);
	public PageContent<FAQ> showFAQs(PageInfo page, int courseId) throws Exception;
	public ResultMessage modifyFAQ(FAQ faq) throws Exception;
	public ResultMessage deleteFAQ(int faqId) throws Exception;
	public List<FAQ> getFAQs(String condition, Object[] params) throws Exception;
}
	