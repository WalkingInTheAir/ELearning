package com.elearning.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.core.jdbc.bean.PageContent;
import com.core.jdbc.bean.PageInfo;
import com.core.jdbc.converter.ConverterFactory;
import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;
import com.elearning.dao.IClasDao;
import com.elearning.domain.Clas;
import com.elearning.domain.Department;
import com.elearning.domain.Major;

public class ClasDaoImpl extends ABaseDao implements IClasDao {

	private IResultSetConverter<Clas> conv = new IResultSetConverter<Clas>() {

		@Override
		public Clas conver(ResultSet rs) throws Exception {
			int clsId = rs.getInt("CLS_ID");
			String clsName = rs.getString("CLS_NAME");
			int majorId = rs.getInt("MAJOR_ID");
			String majorName = rs.getString("MAJOR_NAME");
			int deptId = rs.getInt("DEPT_ID");
			String deptName = rs.getString("DEPT_NAME");
			Major m = new Major();
			m.setId(majorId);
			m.setName(majorName);
			m.setDept(new Department(deptId, deptName));
			Clas clas = new Clas();
			clas.setId(clsId);
			clas.setName(clsName);
			clas.setMajor(m);
			return clas;
		}

	};

	@Override
	public int addClas(Clas clas) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String insertSql = "INSERT INTO TB_CLASS VALUES(NULL, ?, ?)";
			conn = DBManager.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setObject(1, clas.getName());
			pstmt.setObject(2, clas.getMajor().getId());
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
			rs.close();
			String sql ="SELECT COURSE_ID FROM TB_COURSE WHERE (F_CT_ID = 1 AND F_RELATIVE_ID IS NULL)"
					+ " OR (F_CT_ID = 2 AND F_RELATIVE_ID = ?)"
					+ " OR (F_CT_ID = 3 AND F_RELATIVE_ID = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clas.getMajor().getDept().getId());
			pstmt.setInt(2, clas.getMajor().getId());
			List<Integer> courseIds = new ArrayList<Integer>();
			rs = pstmt.executeQuery();
			while(rs.next()){
				courseIds.add(rs.getInt(1));
			}
			pstmt.close();
			rs.close();
			String batchSql = "INSERT INTO TB_TEACHER_COURSE_CLASS VALUES(NULL, NULL, ?, ?)";
			pstmt = conn.prepareStatement(batchSql);
			if(null != courseIds){
				for(int i=0, len=courseIds.size(); i<len; i++){
					int courseId = courseIds.get(i);
					pstmt.setInt(1, courseId);
					pstmt.setInt(2, genKey);
					pstmt.addBatch();
					if((i+1)%30 == 0){			//防止内存溢出
						pstmt.executeBatch();
						pstmt.clearBatch();
					}
				}
				pstmt.executeBatch();
				conn.commit();
			}
		} catch (Exception e) {
			System.out.println("******SQL LOG****** add class Exception----ROLLBACK");
			conn.rollback();
			throw e;
		}finally{
			DBManager.close(conn, pstmt, rs);
		}
		
		return 1;
	}

	@Override
	public int deleteClas(int clasId) throws Exception {
		return super.delete("DELETE FROM TB_CLASS WHERE CLS_ID = ?",
				new Object[] { clasId });
	}

	@Override
	public int modifyClas(Clas clas) throws Exception {
		return super
				.update("UPDATE TB_CLASS SET CLS_NAME = ? WHERE CLS_ID = ?",
						new Object[] { clas.getName(), clas.getId() });
	}

	@Override
	public boolean isExist(int majorId, String className) throws Exception {
		return super
				.queryToBean(
						"SELECT COUNT(*) FROM TB_CLASS WHERE F_MAJOR_ID = ? AND CLS_NAME = ?",
						new Object[] { majorId, className },
						ConverterFactory.getConverter(Integer.class)) > 0;
	}

	@Override
	public Clas getClasById(int id) throws Exception {
		return null;
	}

	@Override
	public PageContent<Clas> getClasses(String conditions, Object[] params,
			PageInfo pageInfo) throws Exception {
		StringBuffer sb = new StringBuffer(
				"SELECT * FROM VIEW_CLASS");
		if (!StringUtils.isBlank(conditions) && null != params) {
			sb.append(" WHERE ").append(conditions);
		}
		return DBManager.queryByPagination(sb.toString(), params, this.conv,
				pageInfo);
	}

	@Override
	public List<Clas> getClassesToList(String conditions, Object[] params)
			throws Exception {
		return null;
	}

	@Override
	public List<Integer> getClsIdsToList(String conditions, Object[] params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CLS_ID FROM VIEW_CLASS ");
		if(!StringUtils.isBlank(conditions)){
			sql.append(" WHERE ").append(conditions);
		}
		sql.append(" ORDER BY CLS_ID ASC");
		return super.queryToList(sql.toString(), params, ConverterFactory.getConverter(Integer.class));
	}

}
