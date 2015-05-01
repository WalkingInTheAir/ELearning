package com.elearning.util.upload;

import org.apache.poi.ss.usermodel.Cell;


public class UploadExcelUtil {
	/**
	 * 根据获取cell 值
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String value = "";
		if (null != cell) {
			int cellType = cell.getCellType();
			switch (cellType) {
			case Cell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				value = String.valueOf(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				;
			case Cell.CELL_TYPE_ERROR:
				;
			case Cell.CELL_TYPE_FORMULA:
				;
			default:
				value = "";
				break;
			}
		}
		return value;
	}
}
