import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;
import com.elearning.domain.Clas;
import com.elearning.domain.Department;
import com.elearning.domain.Major;


public class T {

	public static void main(String[] args) throws Exception{
		
		test();
	}
	
	
	public static void test() throws Exception{
		String sql = "select distinct * from tb_teacher_course_class tcc, view_class v where tcc.f_cls_id = v.cls_id ";
		
		List<Clas> cs = DBManager.queryToList(sql, null, new IResultSetConverter<Clas>(){
			@Override
			public Clas conver(ResultSet rs) throws Exception {
				int	deptId = rs.getInt("dept_id");
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
		
		Iterator<Integer> it = dps.keySet().iterator();
		while(it.hasNext()){
			Department d = dps.get(it.next());
			System.out.println(d.getId() + ":" + d.getName());
			Set<Major> ms = d.getMajors();
			for(Major m : ms){
				System.out.println(" major:" + m.getId() + ":" + m.getName());
				Set<Clas> css = m.getClasses();
				for(Clas c : css){
					System.out.println("  -class: " + c.getId() + ":" + c.getName());
				}
			}
		}
	}
}
