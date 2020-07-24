package com.ebay.automation.base;

import java.util.Hashtable;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.ebay.automation.pages.LoginPage;
import com.ebay.automation.utils.ConfigFileManager;
import com.ebay.automation.utils.WebDriverFactory;

public class TestBase {
	
 
	private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	private Hashtable<String, String> TestParams;
	private ConfigFileManager ConfigManager;
	
	@BeforeMethod(alwaysRun=true)
    @Parameters({"Browser", "Platform"})
    public void initialSetup(ITestContext context, @Optional("") String Browser, @Optional("") String Platform) throws Exception {
    	
    	try {  		
    		driver.set(WebDriverFactory.getInstance().createDriver(Browser, Platform));
    		context.setAttribute("WebDriver", driver.get());
    	} catch(Exception e) {
    		
    		Assert.fail("Exception from TestBase.initialSetup", e);
    		throw new Exception("Exception from TestBase.initialSetup: " + e.getStackTrace());
    	}
    }
    
    

    @AfterMethod(alwaysRun=true)
    public void afterClass() throws Exception {
		try {
			driver.get().manage().deleteAllCookies();
			driver.get().quit();
		} catch (Exception ex) {
			
			Assert.fail("Exception from TestBase.afterClass", ex);
			throw new Exception("Exception from TestBase.afterClass: " + ex.getStackTrace());
		}
    }
	
	public WebDriver getDriver() {
		return driver.get();
	}
	
	public Hashtable<String, String> getTestParams(){
		return TestParams;
	}
	
	public LoginPage openSite() throws Exception {
		
		LoginPage LoginPage = null;
		try {
			ConfigManager = new ConfigFileManager();
			driver.get().get(ConfigManager.getAppURL());
			LoginPage = new LoginPage(driver.get());
		} catch(Exception e) {
			
			Assert.fail("Exception from TestBase.openSite", e);
			throw new Exception("Exception from TestBase.openSite: " + e.getStackTrace());
		}
		return LoginPage;
	}

}
