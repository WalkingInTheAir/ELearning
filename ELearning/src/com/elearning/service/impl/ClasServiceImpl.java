package com.elearning.service.impl;


import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.showmsg.bean.ResultMessage;
import com.core.showmsg.bean.ResultMessageFactory;
import com.elearning.dao.IClasDao;
import com.elearning.dao.impl.ClasDaoImpl;
import com.elearning.domain.Clas;
import com.elearning.service.IClasService;

public class ClasServiceImpl implements IClasService {
	
	private IClasDao dao = new ClasDaoImpl();
	@Override
	public PageContent<Clas> showClasz(String conditions, Object[] params,
			PageInfo pageInfo) {
		try {
			return dao.getClasses(conditions, params, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public boolean isExist(int majorId, String className) {
		boolean result = false;
		try {
			result = dao.isExist(majorId, className);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public ResultMessage addClass(Clas clas){
		ResultMessage result = null;
		try {
			int rows = dao.addClas(clas);
			if(rows <= 0){
				result = ResultMessageFactory.getErrorResult("添加失败, 请稍后再试");
			}else{
				result = ResultMessageFactory.getSuccessResult("添加成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		return result;
	}
	@Override
	public ResultMessage deleteClass(int classId) {
		ResultMessage rm = null;
		try {
			int rows = dao.deleteClas(classId);
			if(rows <= 0){
				rm = ResultMessageFactory.getErrorResult("删除失败, 请稍后再试");
			}else{
				rm = ResultMessageFactory.getSuccessResult("删除成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		
		return rm;
	}
	@Override
	public ResultMessage mdfClass(Clas clas) {
		ResultMessage rm = null;
		try {
			int rows = dao.modifyClas(clas);
			if(rows > 0){
				rm = ResultMessageFactory.getSuccessResult("修改成功");
			}else{
				rm = ResultMessageFactory.getErrorResult("没有找到对应的记录");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm = ResultMessageFactory.getErrorResult("系统异常，请联系我们");
		}
		return rm;
	}

}
