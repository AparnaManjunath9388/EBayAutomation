package com.ebay.automation.pages;

import org.openqa.selenium.WebDriver;

import com.ebay.automation.base.PageBase;

public class LoginPage extends PageBase {
	
	public LoginPage(WebDriver driver) throws Exception {
		super(driver);
	}
	
	/************************************************************
	 * getPageTitle
	 * @return String page title
	 */
	public String getPageTitle() {
		String title = driver.getTitle();
		return title;
	}
}
