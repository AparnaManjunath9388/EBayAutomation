package com.ebay.automation.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigFileManager {
	
	private Properties prop;
	
	public ConfigFileManager() throws Exception {
		try {
			prop = new Properties();
			prop.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\ebay\\automation\\config\\config.properties"));
		} catch(Exception e) {
			throw new Exception("Exception from ConfigFileManager: " + e.getMessage());
		}
	}
	
	public String getAppURL() throws Exception {
		try {
			return prop.getProperty("AppUrl");
		} catch(Exception e) {
			throw new Exception("Exception while reading url from Config file: " + e.getMessage());
		}
	}
	
	public String getDeviceName() throws Exception {
		try {
			return prop.getProperty("deviceName");
		} catch(Exception e) {
			throw new Exception("Exception while reading deviceName from Config file: " + e.getMessage());
		}
	}
	
	public String getUdid() throws Exception {
		try {
			return prop.getProperty("udid");
		} catch(Exception e) {
			throw new Exception("Exception while reading udid from Config file: " + e.getMessage());
		}
	}
	
	public String getPlatformName() throws Exception {
		try {
			return prop.getProperty("platformName");
		} catch(Exception e) {
			throw new Exception("Exception while reading platformName from Config file: " + e.getMessage());
		}
	}
	
	public String getPlatformVersion() throws Exception {
		try {
			return prop.getProperty("platformVersion");
		} catch(Exception e) {
			throw new Exception("Exception while reading platformVersion from Config file: " + e.getMessage());
		}
	}
	
	public String getAppPackage() throws Exception {
		try {
			return prop.getProperty("appPackage");
		} catch(Exception e) {
			throw new Exception("Exception while reading appPackage from Config file: " + e.getMessage());
		}
	}
	
	public String getAppActivity() throws Exception {
		try {
			return prop.getProperty("appActivity");
		} catch(Exception e) {
			throw new Exception("Exception while reading appActivity from Config file: " + e.getMessage());
		}
	}
	
	public String getObjectRepPath() throws Exception {
		try {
			return prop.getProperty("ObjectRepPath").replace("userdir", System.getProperty("user.dir"));
		} catch(Exception e) {
			throw new Exception("Exception while reading ObjectRepPath from Config file: " + e.getMessage());
		}		
	}
	
	
	public String getDataFilePath() throws Exception {
		try {
			return prop.getProperty("DataFilePath").replace("userdir", System.getProperty("user.dir"));
		} catch(Exception e) {
			throw new Exception("Exception while reading DataFilePath from Config file: " + e.getMessage());
		}		
	}
	
	
	public String getAppName() throws Exception {
		try {
			return prop.getProperty("AppName");
		} catch(Exception e) {
			throw new Exception("Exception while reading AppName from Config file: " + e.getMessage());
		}		
	}
	
	public String getHtmlReportsPath() throws Exception {
		try {
			return prop.getProperty("HtmlReportPath").replace("userdir", System.getProperty("user.dir"));
		} catch(Exception e) {
			throw new Exception("Exception while reading HtmlReportPath from Config file: " + e.getMessage());
		}		
	}
	
	public String getEmailToList() throws Exception {
		try {
			return prop.getProperty("EmailToList");
		} catch(Exception e) {
			throw new Exception("Exception while reading EmailToList from Config file: " + e.getMessage());
		}	
	}
	
	public String getEmailCcList() throws Exception {
		try {
			return prop.getProperty("EmailCcList");
		} catch(Exception e) {
			throw new Exception("Exception while reading EmailCcList from Config file: " + e.getMessage());
		}
	}
	
	public String getEmailBccList() throws Exception {
		try {
			return prop.getProperty("EmailBccList");
		} catch(Exception e) {
			throw new Exception("Exception while reading EmailBccList from Config file: " + e.getMessage());
		}
	}	
	
	public String getHubURL() throws Exception {
		try {
			return prop.getProperty("HubURL");
		} catch(Exception e) {
			throw new Exception("Exception while reading url from Config file: " + e.getMessage());
		}
	}
}
