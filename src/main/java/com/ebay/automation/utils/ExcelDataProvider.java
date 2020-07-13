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
	
	public Hashtable<String, String> get(String SheetName, String scenarioName) throws Exception{
		
		Hashtable<String, String> DataCollection = new Hashtable<String, String>();
		try {
			
			Workbook WB = WorkbookFactory.create(new FileInputStream(DatafilePath));
			Sheet sheet = WB.getSheet(SheetName);
			
			int rowCount = sheet.getPhysicalNumberOfRows();
			String[] rowsToSearch = {"ALL", scenarioName};
			
			for (String RowIdentity : rowsToSearch) {
				for (int i = 1;i< rowCount;i++) {
					if (sheet.getRow(i).getCell(0, Row.CREATE_NULL_AS_BLANK).toString().equalsIgnoreCase(RowIdentity)){
						
						int colCount = sheet.getRow(i).getPhysicalNumberOfCells();
						for(int j=1;j<colCount;j++) {
							String data = sheet.getRow(i).getCell(j, Row.CREATE_NULL_AS_BLANK).toString().replace(".0", "");
							if (data != "") {
								DataCollection.put(data.split(";")[0], data.split(";")[1]);
							} else {
								break;
							}
							
						}
					}
				}
			}
			
		} catch(Exception e) {
			throw new Exception("Exception while reading data from excel for scenario " + scenarioName + ": " + e.getMessage());
		}
		return DataCollection;
	}

}
