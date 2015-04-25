package com.elearning.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.jdbc.converter.ConverterFactory;
import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;
import com.core.regex.util.RegexUtil;
import com.elearning.dao.ICourseDao;
import com.elearning.domain.Clas;
import com.elearning.domain.Course;
import com.elearning.domain.CourseType;
import com.elearning.domain.Department;
import com.elearning.domain.Major;
import com.elearning.domain.Teacher;

public class CourseDaoImpl extends ABaseDao implements ICourseDao {

	@Override
	public int addCourse(Course c, List<Integer> clsIds) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String insertSql = "INSERT INTO TB_COURSE (COURSE_NAME, F_CT_ID, F_RELATIVE_ID) VALUES(?, ?, ?)";
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setObject(1, c.getName());
			pstmt.setObject(2, c.getCt().getId());
			pstmt.setObject(3,
					RegexUtil.isNumStr(c.getRelativeId())
							? Integer.parseInt(c.getRelativeId())
							: null);
			int rows = pstmt.executeUpdate();
			if (rows < 1) {
				throw new Exception();
			}
			rs = pstmt.getGeneratedKeys();
			int genKey = -1;
			while (rs.next()) {
				genKey = rs.getInt(1);
			}
			if (genKey == -1) {
				throw new Exception();
			}
			pstmt.close();
			String batchSql = "INSERT INTO TB_TEACHER_COURSE_CLASS VALUES(NULL, NULL, ?, ?)";
			pstmt = conn.prepareStatement(batchSql);
			if(null != clsIds){
				for(int i=0, len=clsIds.size(); i<len; i++){
					int clsId = clsIds.get(i);
					pstmt.setInt(1, genKey);
					pstmt.setInt(2, clsId);
					pstmt.addBatch();
					if((i+1)%30 == 0){			//防止内存溢出
						pstmt.executeBatch();
						pstmt.clearBatch();
					}
				}
			}else{
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			System.out.println("******SQL LOG****** add Course Exception----ROLLBACK");
			conn.rollback();
			throw e;
		}finally{
			DBManager.close(conn, pstmt, rs);
		}
		
		return 1;
	}

	@Override
	public boolean isExist(Course c) throws Exception {
		String sql = "SELECT COUNT(*) FROM TB_COURSE WHERE COURSE_NAME = ? AND F_CT_ID = ? AND (F_RELATIVE_ID = ? OR F_RELATIVE_ID IS NULL)";
		return super.queryToBean(sql,
				new Object[]{
						c.getName(),
						c.getCt().getId(),
						RegexUtil.isNumStr(c.getRelativeId())
								? Integer.parseInt(c.getRelativeId())
								: null}, ConverterFactory.getConverter(Integer.class)) > 0;
	}

	public static void main(String[] args) throws Exception {

		CourseDaoImpl c = new CourseDaoImpl();
		System.out
				.println(c
						.queryToBean(
								"SELECT COUNT(*) FROM TB_COURSE WHERE COURSE_NAME = ? AND F_CT_ID = ? AND (F_RELATIVE_ID = ? OR F_RELATIVE_ID IS NULL)",
								new Object[]{"a", 1, null},
								ConverterFactory.getConverter(Integer.class)));
	}

	@Override
	public PageContent<Course> queryCourses(String rltTabName, String preName, String conditions, Object[] params,
 PageInfo page) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COURSE_ID, COURSE_NAME, F_CT_ID, CT_NAME, F_RELATIVE_ID");
		if (null != rltTabName) {
			sb.append("," + preName + "_NAME").append(" FROM TB_COURSE C, TB_COURSETYPE T")
					.append("," + rltTabName + " RLT");
		}else{
			sb.append(" FROM TB_COURSE C, TB_COURSETYPE T");
		}
		if (!StringUtils.isBlank(conditions)) {
			sb.append(" WHERE ").append(conditions).append(" AND C.F_CT_ID = T.CT_ID");
		}else{
			sb.append(" WHERE C.F_CT_ID = T.CT_ID");
		}
		return DBManager.queryByPagination(sb.toString(), params,
				new IResultSetConverter<Course>() {
					@Override
					public Course conver(ResultSet rs) throws Exception {
						Course c = new Course();
						c.setId(rs.getInt("COURSE_ID"));
						c.setName(rs.getString("COURSE_NAME"));
						if(!StringUtils.isBlank(preName)){
							c.setRelativeId(String.valueOf(rs.getInt("F_RELATIVE_ID")));
							c.setRelativeName(rs.getString(preName + "_NAME"));
						}
						CourseType t = new CourseType();
						t.setId(rs.getInt("F_CT_ID"));
						t.setName(rs.getString("CT_NAME"));
						c.setCt(t);
						return c;
					}

				}, page);
	}

	@Override
	public int deleteCourse(int courseId) throws Exception {
		String sql = "DELETE FROM TB_COURSE WHERE COURSE_ID = ?";
		return super.delete(sql, new Object[]{courseId});
	}

	@Override
	public int updateCourse(Course c) throws Exception {
		String sql = "UPDATE TB_COURSE SET COURSE_NAME = ? WHERE COURSE_ID = ?";
		return super.update(sql, new Object[]{c.getName(), c.getId()});
	}

	@Override
	public List<Department> getCdtClasses(int courseId) throws Exception {
		String sql = "SELECT DISTINCT * FROM TB_TEACHER_COURSE_CLASS TCC, VIEW_CLASS V "
				+ "WHERE TCC.F_COURSE_ID = ? AND TCC.F_TEACHER_ID IS NULL AND TCC.F_CLS_ID = V.CLS_ID";

		List<Clas> cs = DBManager.queryToList(sql, new Object[]{courseId}, new IResultSetConverter<Clas>() {
			@Override
			public Clas conver(ResultSet rs) throws Exception {
				int deptId = rs.getInt("dept_id");
				String deptName = rs.getString("dept_name");
				int majorId = rs.getInt("major_id");
				String majorName = rs.getString("major_name");
				int clsId = rs.getInt("cls_id");
				String clsName = rs.getString("cls_name");
				Clas c = new Clas();
				c.setId(clsId);
				c.setName(clsName);
				Major m = new Major();
				m.setId(majorId);
				m.setName(majorName);
				Department dept = new Department();
				dept.setId(deptId);
				dept.setName(deptName);
				m.setDept(dept);
				c.setMajor(m);
				return c;
			}
		});
		List<Department> depts = new ArrayList<Department>();
		Map<Integer, Department> dps = new HashMap<Integer, Department>();
		Map<Integer, Major> mjs = new HashMap<Integer, Major>();
		for (int i = 0; i < cs.size(); i++) {
			Clas c = cs.get(i);
			Major m = c.getMajor();
			Department d = m.getDept();
			if (!dps.containsKey(d.getId())) {
				dps.put(d.getId(), d);
				depts.add(d);
			}
			if (!mjs.containsKey(m.getId())) {
				mjs.put(m.getId(), m);
			}
			mjs.get(m.getId()).getClasses().add(c);
			dps.get(d.getId()).getMajors().add(mjs.get(m.getId()));
		}
		return depts;
	}

	@Override
	public int updateClassCourseTeacher(int courseId, int teacherId, Set<Integer> classIds) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE TB_TEACHER_COURSE_CLASS SET F_TEACHER_ID = ? WHERE F_COURSE_ID = ? AND F_CLS_ID IN (");
		for(int clsId : classIds){
			sql.append(clsId + ",");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(")");
		return super.update(sql.toString(), new Object[]{teacherId, courseId});
	}

	@Override
	public PageContent<Course> queryClassCourses(int clsId, PageInfo p) throws Exception {
		String sql = "select cls_id, cls_name, course_id, course_name, teacher_id, teacher_number, teacher_name, dept_id, dept_name "
				+ "from tb_class c, view_teacher t, tb_teacher_course_class tc, tb_course co "
				+ "where c.cls_id = ? AND c.cls_id = tc.f_cls_id AND tc.f_teacher_id = t.teacher_id AND co.course_id = tc.f_course_id";
		return DBManager.queryByPagination(sql, new Object[]{clsId}, new IResultSetConverter<Course>(){
			@Override
			public Course conver(ResultSet rs) throws Exception {
				Course c = new Course();
				c.setId(rs.getInt("course_id"));
				c.setName(rs.getString("course_name"));
				Clas cls = new Clas();
				cls.setId(rs.getInt("cls_id"));
				cls.setName(rs.getString("cls_name"));
				c.setClas(cls);
				Teacher t = new Teacher();
				t.setId(rs.getInt("teacher_id"));
				t.setName(rs.getString("teacher_name"));
				t.setNum(rs.getString("teacher_number"));
				Department tDept = new Department();
				tDept.setId(rs.getInt("dept_id"));
				tDept.setName(rs.getString("dept_name"));
				t.setDept(tDept);
				c.setTeacher(t);
				return c;
			}}, p);
	}

	@Override
	public int updateCourseTeacher(int classId, int courseId, int teacherId) throws Exception {
		String sql = "UPDATE TB_TEACHER_COURSE_CLASS SET F_TEACHER_ID = ? WHERE F_COURSE_ID = ? AND F_CLS_ID = ?";
		return super.update(sql, new Object[]{teacherId, courseId, classId});
	}

	@Override
	public List<Course> queryCourses(String sql, Object[] params,
			IResultSetConverter<Course> converter) throws Exception {
		
		return super.queryToList(sql, params, converter);
	}

	@Override
	public Course getCourseById(int courseId) throws Exception {
		String sql = "SELECT * FROM TB_COURSE WHERE COURSE_ID = ?";
		return super.queryToBean(sql, new Object[]{courseId}, new IResultSetConverter<Course>(){

			@Override
			public Course conver(ResultSet rs) throws Exception {
				Course c = new Course();
				c.setId(rs.getInt("COURSE_ID"));
				c.setName(rs.getString("COURSE_NAME"));
				c.setDesc(rs.getString("COURSE_DESC"));
				c.setOutline(rs.getString("COURSE_OUTLINE"));
				return c;
			}
		});
	}

}
