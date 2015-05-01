package com.elearning.service.impl;

import java.util.List;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.elearning.dao.IFAQDao;
import com.elearning.dao.impl.FAQDaoImpl;
import com.elearning.domain.FAQ;
import com.elearning.service.IFAQService;

public class FAQServiceImpl implements IFAQService {
	private IFAQDao dao = new FAQDaoImpl();
	@Override
	public ResultMessage addFAQ(FAQ faq) {
		ResultMessage result = null;
		try {
			result = dao.addFAQ(faq) > 0 
					? ResultMessageFactory.getSuccessResult("恭喜您，操作成功")
					: ResultMessageFactory.getErrorResult("网络异常，请稍后再试");
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultMessageFactory.getErrorResult("网络异常，请稍后再试");
		}
		return result;
	}

	@Override
	public PageContent<FAQ> showFAQs(PageInfo page, int courseId) throws Exception {
		return dao.queryFAQs(page, courseId);
	}

	@Override
	public ResultMessage modifyFAQ(FAQ faq) {
		ResultMessage rs = null;
		boolean isSuccess = false;
		try {
			isSuccess = dao.updateFAQ(faq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isSuccess) {
			rs = ResultMessageFactory.getSuccessResult("修改成功");
		} else {
			rs = ResultMessageFactory.getErrorResult("系统错误，请联系我们");
		}
		return rs;
	}

	@Override
	public ResultMessage deleteFAQ(int faqId) throws Exception {
		ResultMessage rs = null;
		if(dao.deleteFAQ(faqId)){
			rs = ResultMessageFactory.getSuccessResult("删除成功！");
		}else{
			rs = ResultMessageFactory.getErrorResult("系统错误，请稍后再试");
		}
		return rs;
	}

	@Override
	public List<FAQ> getFAQs(String condition, Object[] params) throws Exception {
		return null;
	}
}
