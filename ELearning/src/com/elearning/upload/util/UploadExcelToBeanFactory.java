package com.elearning.upload.util;

import java.util.ArrayList;
import java.util.List;

import com.elearning.domain.Department;

public class UploadExcelToBeanFactory {

	public static List<Department> conToDept(List<List<String>> values){
		
		return new IUpExcelToBeanConverter<Department>(){
			@Override
			public List<Department> parser(List<List<String>> values) {
				List<Department> depts = new ArrayList<Department>();
				for (List<String> value : values) {
					Department dept = new Department(value.get(0));
					depts.add(dept);
				}
				return depts;
			}
		}.parser(values);
	}
}
