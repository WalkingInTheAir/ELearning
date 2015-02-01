package com.elearning.upload.util;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class UploadExcelUtil {
	
	public static void main(String[] args) throws InvalidFormatException, IOException{
		Workbook wb = WorkbookFactory.create(new File("src/软件1131.xls"));
		int numOfSheets = wb.getNumberOfSheets();
		
		for(int i=0; i<numOfSheets; i++){
			
			Sheet sheet = wb.getSheetAt(i);
			int numOfCols_ = sheet.getFirstRowNum();
			int numOfCols = sheet.getLastRowNum() + 1;
			for(int j=0; j<numOfCols; j++){
				Row row = sheet.getRow(j);
				if(row == null){
					continue;
				}
				int numOfCells = row.getLastCellNum();
				for(int k=0; k<numOfCells; k++){
					Cell cell = row.getCell(k);
					if(null == cell){
						continue;
					}
					int cellType = cell.getCellType();
					String value = "";
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
					System.out.print(value + " ");
				}
				System.out.println();
			}

		}
	}
}
