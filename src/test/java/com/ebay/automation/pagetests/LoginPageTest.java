package com.ebay.automation.pagetests;

import java.util.Hashtable;
//import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ebay.automation.base.TestBase;
import com.ebay.automation.pages.HomePage;
import com.ebay.automation.pages.LoginPage;
import com.ebay.automation.utils.ExcelDataProvider;

public class LoginPageTest extends TestBase {

	private LoginPage LoginPage;
	//private WebDriver driver;
	private Hashtable<String, String> TestParams = null;
	
    @BeforeClass(alwaysRun=true)
    public void setUp() throws Exception {
    	//driver = getDriver();
    	try {
			//read required data
			ExcelDataProvider DataProvider = new ExcelDataProvider();
			Hashtable<String, String> params = DataProvider.get("LoginPageTest");
			if (!params.isEmpty()) {
				TestParams = new Hashtable<String, String>();
				this.TestParams.putAll(params);
			}
    	} catch(Exception e) {
    		Assert.fail("Exception from LoginPageTest.setUp: caused by -> " + e.getCause() + ", message -> " + e.getMessage(), e);
    		throw new Exception("Exception from LoginPageTest.setUp: " + e.getMessage());
    	}
    	
    }
	
	@Test(groups = {"SmokeTest"})
	public void assertLoginPageTitle() throws Exception {
		
		try {
			
			LoginPage = openSite();
			String expected = TestParams.get("ExpectedPageTitle");
			Assert.assertTrue(LoginPage.getPageTitle().equalsIgnoreCase(expected), "Page title not as expected");
		} catch(Exception e) {
			
			Assert.fail("Exception from LoginPageTest.assertLoginPageTitle: caused by -> " + e.getCause() + ", message -> " + e.getMessage(), e);
			throw new Exception("Exception from LoginPageTest.assertLoginPageTitle: " + e.getMessage());
		}
		
	}
	
	@Test(groups = {"RegressionTest"})
	public void searchForProduct() throws Exception {
		
		try {
			
			String ProductToSearch = TestParams.get("ProductToSearch");
			String MinimumExpectedCount = TestParams.get("ExpectedResultsCount");

			LoginPage = openSite();
			LoginPage.searchProduct(ProductToSearch);
			Assert.assertTrue(LoginPage.verifySearchResults(MinimumExpectedCount), "Search results count not as expected");		
		} catch(Exception e) {
			
			Assert.fail("Exception from LoginPageTest.searchForProduct: caused by -> " + e.getCause() + ", message -> " + e.getMessage(), e);
			throw new Exception("Exception from LoginPageTest.searchForProduct: " + e.getMessage());
		}

	}
	
	@Test(groups = {"RegressionTest"})
	public void signUp() throws Exception {
		
		try {
			String FirstName = TestParams.get("FirstName");
			String LastName = TestParams.get("LastName");
			String RegisterEmailId = TestParams.get("RegisterEmailId");
			String RegisterPwd = TestParams.get("RegisterPassword");

			LoginPage = openSite();
			
			HomePage HomePage = LoginPage.registerNewUser(FirstName, LastName, RegisterEmailId, RegisterPwd);
			Assert.assertTrue(HomePage.verifyLoggedInUser(FirstName), "New account registration failed");
		} catch(Exception e) {
			
			Assert.fail("Exception from LoginPageTest.signUp: caused by -> " + e.getCause() + ", message -> " + e.getMessage(), e);
			throw new Exception("Exception from LoginPageTest.signUp: " + e.getMessage());
		}
		
	}
	
	@Test(groups = {"RegressionTest"})
	public void login() throws Exception {
		
		try {
			String EmailId = TestParams.get("EmailId");
			String Password = TestParams.get("Password");
			String expectedUsername = TestParams.get("ExpectedUsername");

			LoginPage = openSite();
			HomePage HomePage = LoginPage.login(EmailId, Password);
			Assert.assertTrue(HomePage.verifyLoggedInUser(expectedUsername), "Username not as expected");
		} catch(Exception e) {
			
			Assert.fail("Exception from LoginPageTest.login: caused by -> " + e.getCause() + ", message -> " + e.getMessage(), e);
			throw new Exception("Exception from LoginPageTest.login: " + e.getMessage());
		}
		

	}
	
	

}
