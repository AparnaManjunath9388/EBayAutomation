package com.ebay.automation.utils;

import java.io.FileInputStream;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelDataProvider {
	
	private String DatafilePath;
	
	public ExcelDataProvider() throws Exception {
		
		ConfigFileManager configManager = new ConfigFileManager();
		DatafilePath = configManager.getDataFilePath();
	}
	
	public Hashtable<String, String> get(String SheetName) throws Exception{
		
		Hashtable<String, String> DataCollection = new Hashtable<String, String>();
		try {
			
			Workbook WB = WorkbookFactory.create(new FileInputStream(DatafilePath));
			Sheet sheet = WB.getSheet(SheetName);
			
			int rowCount = sheet.getPhysicalNumberOfRows();
			
			for (int i = 0; i<rowCount; i+=2) {
				int colCount = sheet.getRow(i).getPhysicalNumberOfCells();
				
				for (int j=0;j<colCount;j++) {
					String header = sheet.getRow(i).getCell(j, Row.CREATE_NULL_AS_BLANK).toString().replace(".0", "");
					String data = sheet.getRow(i+1).getCell(j, Row.CREATE_NULL_AS_BLANK).toString().replace(".0", "");
					DataCollection.put(header, data);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception("Exception while reading data from excel sheet " + SheetName + ": " + e.getStackTrace());
			
		}
		return DataCollection;
	}

}
