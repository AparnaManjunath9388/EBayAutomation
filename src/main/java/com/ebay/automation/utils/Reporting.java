package com.ebay.automation.utils;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ebay.automation.base.TestBase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reporting {
	
	private ExtentReports Report;
	private Logger logger;
	private ExtentTest Test;
	
	public String StepDescription;
	public String Actual;
	public LogStatus status;
	public String ScreenshotPath;
	
	public void initializeReporting() throws Exception {
	
		//create report
		ConfigFileManager ConfigReader = new ConfigFileManager();
		String TestRunID = ConfigReader.getAppName() + "_" + LocalDateTime.now().toString().replace(":", "_").replace(".", "_");
		String path = ConfigReader.getHtmlReportsPath() + File.separator  + "ExecutionReport_" + TestRunID + ".html";
		
		Report = new ExtentReports(path);
		logger = Logger.getLogger(TestBase.class);
		
		WriteIntoLogFile("Created HTML Report at path - " + path, LogStatus.INFO);
		
	}
	
	public ExtentTest startTest(String TestDescription) {
		Test = Report.startTest(TestDescription);
		Test.setEndedTime(getTime(System.currentTimeMillis()));
		WriteIntoLogFile("Started test: " + TestDescription, LogStatus.INFO);
		return Test;
	}
	
	
	public Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	public void WriteIntoReport() {
		
		//write into html report
		
		if (! "".equals(ScreenshotPath)) {
			Actual += Test.addScreenCapture(ScreenshotPath);
		}
		Test.log(status, StepDescription, Actual);	
		WriteIntoLogFile(Actual, status);

	}
	
	public void WriteIntoLogFile(String Message, LogStatus status) {
		
		//write into log file
		switch (status) {
		case FAIL:
		case ERROR:
			logger.error(Message);
			break;
			
		case FATAL:
			logger.fatal(Message);
			break;
			
		default:
			logger.info(Message);
		}
	}
	
	public void closeReport() {
		Report.close();
		WriteIntoLogFile("Closed HTML Report", LogStatus.INFO);
		
	}
	
	public void endTest() {
		Test.setEndedTime(getTime(System.currentTimeMillis()));
		WriteIntoLogFile("Started test", LogStatus.INFO);
	}
	
	public void flushAndCloseReport() {
		Report.flush();
		Report.close();
		WriteIntoLogFile("Flushed into HTML Report and closed", LogStatus.INFO);
	}
	
	public void flushReport() {
		Report.flush();
		WriteIntoLogFile("Flushed into HTML Report", LogStatus.INFO);
	}
}
