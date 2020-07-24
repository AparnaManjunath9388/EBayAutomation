package com.ebay.automation.reporting;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
	
	private ExtentManager ExtentManager;
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	static Map<Integer, ExtentReports> extentReportsMap = new HashMap<Integer, ExtentReports>();
	//static ExtentReports extent = ExtentManager.getInstance();
	
	public ExtentTestManager() {
		ExtentManager = new ExtentManager();
	}
	
	public synchronized void createReport(String suiteName) {
		ExtentReports extent = ExtentManager.getInstance(suiteName);
		extentReportsMap.put((int) (long) (Thread.currentThread().getId()), extent);
	}

	public synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public synchronized void endTest() {
		extentReportsMap.get((int) (long) (Thread.currentThread().getId())).flush();
	}

	public synchronized ExtentTest startTest(String testName) {
		ExtentTest test = extentReportsMap.get((int) (long) (Thread.currentThread().getId())).createTest(testName);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}
}
