package com.ebay.automation.pagetests;

import java.util.Hashtable;
//import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ebay.automation.base.TestBase;
import com.ebay.automation.pages.LoginPage;
import com.ebay.automation.utils.ExcelDataProvider;
import com.ebay.automation.pages.HomePage;

public class HomePageTest extends TestBase {
	
	private HomePage HomePage;
	//private WebDriver driver;
	private Hashtable<String, String> TestParams = null;

    @BeforeClass(alwaysRun=true)
    public void setUp() throws Exception {
    	//driver = getDriver();

    	try {
			//read required data
			ExcelDataProvider DataProvider = new ExcelDataProvider();
			Hashtable<String, String> params = DataProvider.get("HomePageTest");
			if (!params.isEmpty()) {
				TestParams = new Hashtable<String, String>();
				this.TestParams.putAll(params);
			}
    	} catch(Exception e) {
    		throw new Exception("Exception from HomePageTest.setUp: " + e.getMessage());
    	}
    	
    }
    
    
    @Test(groups = {"RegressionTest"}, description="Opens Purchase History of the logged in user")
    public void openPurchaseHistory() throws Exception {
    	try {
    		LoginPage LoginPage = openSite();
    		HomePage = LoginPage.login(TestParams.get("EmailId"), TestParams.get("Password"));
    		Assert.assertTrue(HomePage.openPurchaseHistory(), "Unable to open purchase History");
    	} catch(Exception e) {
    		throw new Exception("Exception from HomePageTest.openPurchaseHistory: " + e.getMessage());
    	}
    }
	

}
