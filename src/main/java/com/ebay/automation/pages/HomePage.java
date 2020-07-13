package com.ebay.automation.pages;
import org.openqa.selenium.WebDriver;

import com.ebay.automation.base.PageBase;

public class HomePage extends PageBase {

	public HomePage(WebDriver driver) throws Exception {
		super(driver);
	}

	public String getPageTitle() throws Exception {
		
		String title = driver.getTitle();
		return title;
	}
	
	
	/**************************************************************
	 * 
	 * @param ExpectedUserName
	 * @return boolean value indicating if the Username is being displayed or not
	 * @throws Exception 
	 */
	public boolean verifyLoggedInUser(String ExpectedUserName) throws Exception{
		
		String property = ORparser.getObjectProperty("home.loggedInUser").replace("username", ExpectedUserName);
		return CommonMethods.getElement(property.split(";")[0], property.split(";")[1]).isDisplayed();
		
	}	
}
