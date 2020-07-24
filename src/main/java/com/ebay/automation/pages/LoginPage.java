package com.ebay.automation.pages;

import java.util.List;
import com.ebay.automation.objectrepository.LoginPageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
		return driver.getTitle();
	}
	
	public void searchProduct(String Product) throws Exception {
		
		try {
			
			
			LoginPageObjects.ProductSearch.getProductSearch(driver).txt_searchBox.sendKeys(Product);
			LoginPageObjects.ProductSearch.getProductSearch(driver).btn_searchButton.click();
		} catch(Exception e) {
			throw new Exception("Exception occured in LoginPage.searchProduct: cause -> " + e.getCause() + ", message -> " + e.getMessage());
		}
		
	}
	
	public boolean verifySearchResults(String expectedCount) throws Exception {
		
		try {
			List<WebElement> searchResults = LoginPageObjects.ProductSearch.getProductSearch(driver).lst_listOfSearchResults;		
			return (searchResults.size() == Integer.valueOf(expectedCount)) ? true : false;
		} catch(Exception e) {
			throw new Exception("Exception occured in LoginPage.verifySearchResults: cause -> " + e.getCause() + ", message -> " + e.getMessage());
		}

	}
	
	public HomePage registerNewUser(String FirstName, String LastName, String EmailId, String Password) throws Exception {
		
		try {
				LoginPageObjects.RegisterNewUser.getRegisterNewUser(driver).link_register.click();
				wait.until(ExpectedConditions.visibilityOf(LoginPageObjects.RegisterNewUser.getRegisterNewUser(driver).txt_firstName));
				
				LoginPageObjects.RegisterNewUser.getRegisterNewUser(driver).txt_firstName.sendKeys(FirstName);
				Thread.sleep(1000);
				LoginPageObjects.RegisterNewUser.getRegisterNewUser(driver).txt_lastName.sendKeys(LastName);
				Thread.sleep(1000);
				LoginPageObjects.RegisterNewUser.getRegisterNewUser(driver).txt_email.sendKeys(EmailId);
				Thread.sleep(1000);
				LoginPageObjects.RegisterNewUser.getRegisterNewUser(driver).txt_password.sendKeys(Password);
				Thread.sleep(1000);
				LoginPageObjects.RegisterNewUser.getRegisterNewUser(driver).btn_createNewAcct.click();
				return new HomePage(driver);
		} catch(Exception e) {
			throw new Exception("Exception occured in LoginPage.registerNewUser: cause -> " + e.getCause() + ", message -> " + e.getMessage());
		}
	}
	
	public HomePage login(String Username, String Password) throws Exception {
		
		HomePage HomePage = null;
		try {
			
			wait.until(ExpectedConditions.visibilityOf(LoginPageObjects.Login.getLogin(driver).lnk_signIn));
			LoginPageObjects.Login.getLogin(driver).lnk_signIn.click();
			wait.until(ExpectedConditions.visibilityOf(LoginPageObjects.Login.getLogin(driver).txt_emailId));
			LoginPageObjects.Login.getLogin(driver).txt_emailId.sendKeys(Username);
			Thread.sleep(1000);
			LoginPageObjects.Login.getLogin(driver).btn_continue.click();
			wait.until(ExpectedConditions.visibilityOf(LoginPageObjects.Login.getLogin(driver).txt_password));
			LoginPageObjects.Login.getLogin(driver).txt_password.sendKeys(Password);
			Thread.sleep(1000);
			LoginPageObjects.Login.getLogin(driver).btn_signIn.click();
				
			HomePage = new HomePage(driver);
		} catch(Exception e) {
			throw new Exception("Exception occured in LoginPage.login: cause -> " + e.getCause() + ", message -> " + e.getMessage());
		}
		return HomePage;
	}
}
