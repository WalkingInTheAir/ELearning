package com.elearning.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;
import com.elearning.dao.ICourseTypeDao;
import com.elearning.domain.CourseType;

public class CourseTypeDaoImpl extends ABaseDao implements ICourseTypeDao{

	IResultSetConverter<CourseType> conv = new IResultSetConverter<CourseType>(){
		@Override
		public CourseType conver(ResultSet rs) throws Exception {
			CourseType type = new CourseType();
			type.setId(rs.getInt("CT_ID"));
			type.setName(rs.getString("CT_NAME"));
			return type;
		}
	};
	
	@Override
	public int addType(CourseType type) throws Exception {
		String sql = "INSERT INTO TB_COURSETYPE VALUES(NULL, ?)";
		return super.add(sql, new Object[] { type.getName() });
	}

	@Override
	public int deleteType(int typeId) throws Exception {
		String sql = "DELETE FROM TB_COURSETYPE WHERE CT_ID = ?";
		return super.delete(sql, new Object[] { typeId });
	}

	@Override
	public int modifyType(CourseType type) throws Exception {
		String sql = "UPDATE TB_COURSETYPE SET CT_NAME = ? WHERE CT_ID = ?";
		return super.update(sql, new Object[] { type.getName(), type.getId() });
	}

	@Override
	public List<CourseType> queryList(String conditions, Object[] params)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TB_COURSETYPE ");
		if (!StringUtils.isBlank(conditions) && null != params) {
			sb.append(" WHERE ").append(conditions);
		}
		sb.append(" ORDER BY CT_ID ASC");
		return super.queryToList(sb.toString(), params, this.conv);
	}

	@Override
	public CourseType getCourseType(String conditions, Object[] params)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TB_COURSETYPE ");
		if (!StringUtils.isBlank(conditions) && null != params) {
			sb.append(" WHERE ").append(conditions);
		}
		sb.append(" ORDER BY CT_ID ASC");
		return super.queryToBean(sb.toString(), params, this.conv);
	}

	@Override
	public PageContent<CourseType> getPage(PageInfo page) throws Exception {
		String sql = "SELECT * FROM TB_COURSETYPE ORDER BY CT_ID ASC";
		return DBManager.queryByPagination(sql, this.conv, page);
	}

}
