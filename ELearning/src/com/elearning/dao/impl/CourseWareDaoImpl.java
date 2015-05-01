package com.elearning.dao.impl;

import java.sql.ResultSet;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;
import com.elearning.dao.ICourseWareDao;
import com.elearning.domain.CourseWare;

public class CourseWareDaoImpl extends ABaseDao implements ICourseWareDao {
	private IResultSetConverter<CourseWare> conv = new IResultSetConverter<CourseWare>() {
		@Override
		public CourseWare conver(ResultSet rs) throws Exception {
			CourseWare cw = new CourseWare();
			cw.setId(rs.getInt("COURSEWARE_ID"));
			cw.setName(rs.getString("COURSEWARE_NAME"));
			cw.setOrigName(rs.getString("COURSEWARE_ORIGNAME"));
			cw.setTime(rs.getString("COURSEWARE_TIME"));
			cw.setCourseId(rs.getInt("F_COURSE_ID"));
			cw.setTeacherId(rs.getInt("F_TEACHER_ID"));
			return cw;
		}
	};
	@Override
	public int add(CourseWare cw) throws Exception {
		return super.add("INSERT INTO TB_COURSEWARE VALUES(NULL,?,?,?,?,?)",
				new Object[] { cw.getName(), cw.getOrigName(), cw.getTime(), cw.getTeacherId(), cw.getCourseId()});
	}

	@Override
	public PageContent<CourseWare> query(PageInfo page, int courseId) throws Exception {
		return DBManager.queryByPagination("SELECT * FROM TB_COURSEWARE WHERE F_COURSE_ID = ?",
				new Object[]{courseId}, this.conv, page);
	}

	@Override
	public boolean delete(int cwId) throws Exception {
		return super
				.delete("DELETE FROM TB_COURSEWARE WHERE COURSEWARE_ID = ?", new Object[]{cwId}) > 0;
	}

	@Override
	public CourseWare getCourseWareById(int id) throws Exception {
		
		return super.queryToBean("SELECT * FROM TB_COURSEWARE WHERE COURSEWARE_ID = ?", new Object[]{id}, this.conv);
	}

}
