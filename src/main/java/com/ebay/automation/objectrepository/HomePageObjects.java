package com.ebay.automation.objectrepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageObjects {
	
	public static class UserVerification {
		
		public static UserVerification getUserVerification(WebDriver driver) {
			return PageFactory.initElements(driver, UserVerification.class);
		}
		
		@FindBy(xpath="//*[@id=\"gh-ug\"]/b[1]")
		public WebElement lnk_loggedinUser;
		
	}
	
	public static class PurchaseHistory {
		
		public static PurchaseHistory getPurchaseHistory(WebDriver driver) {
			return PageFactory.initElements(driver, PurchaseHistory.class);
		}
		
		@FindBy(xpath="//*[@id=\"gh-eb-My\"]/div/a[1]")
		public WebElement lnk_myEbay;
		
		@FindBy(partialLinkText="Purchase History")
		public WebElement lnk_purchaseHistory;
		
		@FindBy(xpath="//*[@id=\"purchase-history\"]")
		public WebElement we_purchaseHistoryBox;
		
	}

}
