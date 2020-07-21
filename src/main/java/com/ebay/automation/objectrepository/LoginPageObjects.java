package com.ebay.automation.objectrepository;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPageObjects {
	
	public static class ProductSearch {
		public static ProductSearch getProductSearch(WebDriver driver) {
			return PageFactory.initElements(driver, ProductSearch.class);
		}
		
		@FindBy(xpath="//input[@type='text'][@class='gh-tb ui-autocomplete-input']")
		public WebElement txt_searchBox;
		
		@FindBy(xpath="//input[@id='gh-btn']")
		public WebElement btn_searchButton;
		
		@FindBy(xpath="//ul[@class='srp-results srp-grid clearfix']")
		public List<WebElement> lst_listOfSearchResults;
		
	}
	
	public static class RegisterNewUser {
		public static RegisterNewUser getRegisterNewUser(WebDriver driver) {
			return PageFactory.initElements(driver, RegisterNewUser.class);
		}
		
		@FindBy(linkText="register")
		public WebElement link_register;
		
		@FindBy(name="firstname")
		public WebElement txt_firstName;
		
		@FindBy(name="lastname")
		public WebElement txt_lastName;
		
		@FindBy(name="email")
		public WebElement txt_email;
		
		@FindBy(name="PASSWORD")
		public WebElement txt_password;
		
		@FindBy(xpath="//button[@id='ppaFormSbtBtn']")
		public WebElement btn_createNewAcct;
		
	}
	
	public static class Login {
		
		public static Login getLogin(WebDriver driver) {
			return PageFactory.initElements(driver, Login.class);
		}
		
		@FindBy(linkText="Sign in")
		public WebElement lnk_signIn;
		
		@FindBy(id="userid")
		public WebElement txt_emailId;
		
		@FindBy(id="signin-continue-btn")
		public WebElement btn_continue;
		
		@FindBy(id="pass")
		public WebElement txt_password;
		
		@FindBy(id="sgnBt")
		public WebElement btn_signIn;
		
	}

}
