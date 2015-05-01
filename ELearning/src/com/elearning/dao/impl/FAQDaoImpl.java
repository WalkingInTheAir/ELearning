package com.elearning.dao.impl;

import java.sql.ResultSet;
import java.util.List;


import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;
import com.elearning.dao.IFAQDao;
import com.elearning.domain.Course;
import com.elearning.domain.FAQ;

public class FAQDaoImpl extends ABaseDao implements IFAQDao {

	private IResultSetConverter<FAQ> conv = new IResultSetConverter<FAQ>() {
		@Override
		public FAQ conver(ResultSet rs) throws Exception {
			FAQ faq = new FAQ();
			faq.setId(rs.getInt("FAQ_ID"));
			faq.setName(rs.getString("FAQ_NAME"));
			faq.setAnswer(rs.getString("FAQ_ANSWER"));
			Course c = new Course();
			c.setId(rs.getInt("F_COURSE_ID"));
			faq.setCourse(c);
			return faq;
		}
	};
	
	@Override
	public int addFAQ(FAQ faq) throws Exception {
		return super.add("INSERT INTO TB_FAQ VALUES(NULL,?,?,?)",
				new Object[]{faq.getName(), faq.getAnswer(), faq.getCourse().getId()});
	}

	@Override
	public PageContent<FAQ> queryFAQs(PageInfo page, int courseId) throws Exception {
		return DBManager.queryByPagination("SELECT * FROM TB_FAQ WHERE F_COURSE_ID = ? ",
				new Object[]{courseId}, this.conv, page);
	}

	@Override
	public boolean deleteFAQ(int faqId) throws Exception {
		return super.delete("DELETE FROM TB_FAQ WHERE FAQ_ID = ?",
				new Object[] { faqId }) > 0;
	}

	@Override
	public boolean updateFAQ(FAQ faq) throws Exception {
		return super.update("UPDATE TB_FAQ SET FAQ_NAME=?, FAQ_ANSWER = ? WHERE FAQ_ID = ? ",
				new Object[] { faq.getName(), faq.getAnswer(), faq.getId()}) > 0;
	}

	@Override
	public List<FAQ> queryFAQs(String conditions, Object[] params) throws Exception {
		return null;
	}

}
