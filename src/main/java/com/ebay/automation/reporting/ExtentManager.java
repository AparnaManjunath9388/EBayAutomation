package com.ebay.automation.reporting;

import java.io.File;
import java.time.LocalDateTime;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
	    private String reportFileLocation = "";
	    
	 
	    /*public ExtentReports getInstance(String suiteName) {
	        createInstance(suiteName);
	        return extent;
	    }*/
	 
	    //Create an extent report instance
	    public ExtentReports getInstance(String suiteName) {
	    	
	    	ExtentReports extent;
		    String reportFileName = "Test-Automation-Report"+ "_" + suiteName + "_" + LocalDateTime.now().toString().replace(":", "_").replace(".", "_") + ".html";
		    String fileSeperator = System.getProperty("file.separator");
		    String reportFilepath = System.getProperty("user.dir") +fileSeperator+ "TestReport";
		    reportFileLocation =  reportFilepath +fileSeperator+ reportFileName;
	        String fileName = getReportPath(reportFilepath);
	       
	        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
	        htmlReporter.config().setTheme(Theme.STANDARD);
	        htmlReporter.config().setDocumentTitle(reportFileName);
	        htmlReporter.config().setEncoding("utf-8");
	        htmlReporter.config().setReportName(reportFileName);
	        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
	 
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	        //Set environment details
			extent.setSystemInfo("OS", "Windows");
			extent.setSystemInfo("AUT", "EBay");
	 
	        return extent;
	    }
	     
	    //Create the report path
	    private String getReportPath (String path) {
	    	File testDirectory = new File(path);
	        if (!testDirectory.exists()) {
	        	if (testDirectory.mkdir()) {
	                System.out.println("Directory: " + path + " is created!" );
	                return reportFileLocation;
	            } else {
	                System.out.println("Failed to create directory: " + path);
	                return System.getProperty("user.dir");
	            }
	        } else {
	            System.out.println("Directory already exists: " + path);
	        }
			return reportFileLocation;
	    }

}
