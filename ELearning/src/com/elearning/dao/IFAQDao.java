package com.elearning.dao;

import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.elearning.domain.FAQ;

public interface IFAQDao {
	public int addFAQ(FAQ faq) throws Exception;
	public PageContent<FAQ> queryFAQs(PageInfo page, int courseId) throws Exception;
	public boolean deleteFAQ(int faqId) throws Exception;
	public boolean updateFAQ(FAQ faq) throws Exception;
	public List<FAQ> queryFAQs(String conditions, Object[] params) throws Exception;
}
