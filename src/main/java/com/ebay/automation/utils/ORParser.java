package com.ebay.automation.utils;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;

public class ORParser {
	
	private Properties ObjectRep;
	private String Key;
	private String Value;
	
	public ORParser() throws Exception {
		ConfigFileManager ConfigManager = new ConfigFileManager();
		ObjectRep = new Properties();
		//ObjectRep.load(new FileInputStream(ConfigManager.getObjectRepPath()));
	}
	
	public String getObjectProperty(String ObjName) throws Exception {
		try {
			return ObjectRep.getProperty(ObjName);
		} catch(Exception e) {
			throw e;
		}
	}
		
	public By getBy(String ObjName) throws Exception {
		
		String prop = ObjectRep.getProperty(ObjName);
		
		Key = prop.split(";")[0];
		Value = prop.split(";")[1];
		
		switch(Key.toLowerCase()) {
		 
		case "name":
			return By.name(Value);
			
		case "id":
			return By.id(Value);
			
		case "linktext":
			return By.linkText(Value);
			
		case "partiallinktext":
			return By.partialLinkText(Value);
	
		case "tagname":
			return By.tagName(Value);
			
		case "cssselector":
			return By.cssSelector(Value);
		
		case "css":
			return By.cssSelector(Value);
			
		case "xpath":
			return By.xpath(Value);
			
		case "classname":
			return By.className(Value);
			
		default:
			throw new Exception("Exception from ORParser.getBy: Unknown locator type '" + Key + "'");

		}
	}

}
