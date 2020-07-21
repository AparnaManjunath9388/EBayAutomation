package com.ebay.automation.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ebay.automation.base.PageBase;
import com.ebay.automation.objectrepository.HomePageObjects;

public class HomePage extends PageBase {

	public HomePage(WebDriver driver) throws Exception {
		super(driver);
	}

	public String getPageTitle() throws Exception {
		try {
			return driver.getTitle();
		} catch(Exception e) {
			throw new Exception("Exception occured in HomePage.getPageTitle: cause -> " + e.getCause() + ", message -> " + e.getMessage());
		}
		
	}
	
	
	/**************************************************************
	 * 
	 * @param ExpectedUserName
	 * @return boolean value indicating if the Username is being displayed or not
	 * @throws Exception 
	 */
	public boolean verifyLoggedInUser(String ExpectedUserName) throws Exception{
		
		try {
			WebElement element = HomePageObjects.UserVerification.getUserVerification(driver).lnk_loggedinUser;
			return (element.getText().equalsIgnoreCase(ExpectedUserName)) ? true : false;
		} catch(Exception e) {
			throw new Exception("Exception occured in HomePage.verifyLoggedInUser: cause -> " + e.getCause() + ", message -> " + e.getMessage());
		}
	}
	
	public boolean openPurchaseHistory() throws Exception {
		
		try {
			
			WebElement hoverLink = HomePageObjects.PurchaseHistory.getPurchaseHistory(driver).lnk_myEbay;
			WebElement linkToClick = HomePageObjects.PurchaseHistory.getPurchaseHistory(driver).lnk_purchaseHistory;
			
			if (CommonMethods.hoverMouseAndClick(hoverLink, linkToClick)) {
				Thread.sleep(1000);
				return HomePageObjects.PurchaseHistory.getPurchaseHistory(driver).we_purchaseHistoryBox.isDisplayed();
			} else
				return false;
		} catch(Exception e) {
			throw new Exception("Exception occured in HomePage.openPurchaseHistory: cause -> " + e.getCause() + ", message -> " + e.getMessage());
		}
	}
}
