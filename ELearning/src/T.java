import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.core.jdbc.converter.IResultSetConverter;
import com.core.jdbc.helper.DBManager;


public class T {

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 100; i++) {
			fun();
			Thread.sleep(100);
		}
	}

	public static void fun() throws Exception {
		String dt = DBManager
				.queryToBean(
						"SELECT to_char(systimestamp, 'yyyy-mm-dd hh24:mi:ssxff') FROM dual",
						new IResultSetConverter<String>() {

							@Override
							public String conver(ResultSet rs) throws Exception {
								return rs.getString(1);
							}

						});
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = df.parse(dt);
		Calendar cn = Calendar.getInstance();
		cn.setTime(date);
		System.out.println(cn.getTimeInMillis());
	}
}
