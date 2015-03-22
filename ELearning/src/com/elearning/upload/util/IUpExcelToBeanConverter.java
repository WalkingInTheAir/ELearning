package com.elearning.upload.util;

import java.util.List;

public interface IUpExcelToBeanConverter<T> {
	public List<T> parser(List<List<String>> values);
}
