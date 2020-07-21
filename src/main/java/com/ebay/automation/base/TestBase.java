package com.ebay.automation.base;

import java.io.File;
import java.net.URL;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.tools.ant.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.ebay.automation.pages.LoginPage;
import com.ebay.automation.utils.ConfigFileManager;
import com.ebay.automation.utils.Constants;
import com.ebay.automation.utils.EventHandler;

public class TestBase {
	
 
	private WebDriver driver;
	private Hashtable<String, String> TestParams;
	private ConfigFileManager ConfigManager;
	
	@BeforeMethod(alwaysRun=true)
    @Parameters({"Browser", "Platform"})
    public void initialSetup(ITestContext context, String Browser, String Platform) throws Exception {
    	
    	try {
        	ConfigFileManager ConfigReader = new ConfigFileManager();
        	String url = ConfigReader.getHomeHubURL();
    		
    		Platform desiredPlatform = getPlatform(Platform);
    		DesiredCapabilities cap = new DesiredCapabilities();
    		cap.setBrowserName(Browser);
    		cap.setPlatform(desiredPlatform);
    		cap.setVersion("ANY");
    		//cap.setVersion(Version);
    		
    		try {
    		
	    		if (Browser.toLowerCase().equals("chrome")){
	    			
	    			ChromeOptions options = new ChromeOptions();
	    			options.merge(cap);
	    			driver = new RemoteWebDriver(new URL(url), options);
	    		}
	    		else if (Browser.toLowerCase().equals("firefox")) {
	
	    			FirefoxOptions options = new FirefoxOptions();
	    			options.merge(cap);
	    			driver = new RemoteWebDriver(new URL(url), options);
	    		}
	    		else {
	    			throw new Exception("Browser not identified");
	    		}
    		} catch(Exception e) {
    			try {
    				url = ConfigReader.getOfficeHubURL();
    	    		if (Browser.toLowerCase().equals("chrome")){
    	    			
    	    			ChromeOptions options = new ChromeOptions();
    	    			options.merge(cap);
    	    			driver = new RemoteWebDriver(new URL(url), options);
    	    		}
    	    		else if (Browser.toLowerCase().equals("firefox")) {
    	
    	    			FirefoxOptions options = new FirefoxOptions();
    	    			options.merge(cap);
    	    			driver = new RemoteWebDriver(new URL(url), options);
    	    		}
    	    		else {
    	    			throw new Exception("Browser not identified");
    	    		}   				
    			} catch(Exception ex) {
    				throw ex;
    			}
    			
    		}
    		
    			
    		EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
    		EventHandler eventHandler = new EventHandler();
    		eventDriver.register(eventHandler);
    		driver = eventDriver;
    		
    		context.setAttribute("WebDriver", driver);
    		driver.manage().window().maximize();
    		driver.manage().deleteAllCookies();
    		driver.manage().timeouts().pageLoadTimeout(Constants.PAGELOAD_TIMEOUT, TimeUnit.SECONDS);
    		driver.manage().timeouts().implicitlyWait(Constants.IMPLICITWAIT_TIMEOUT, TimeUnit.SECONDS);		//not recommended since it makes the selenium wait for each and every element to wait before throwing error
    		
    		
    	} catch(Exception e) {
    		throw new Exception("Exception from TestBase.initialSetup: " + e.getStackTrace());
    	}
    }
    
    

    @AfterMethod(alwaysRun=true)
    public void afterClass() throws Exception {
		try {
			driver.manage().deleteAllCookies();
			driver.quit();
		} catch (Exception ex) {
			throw new Exception("Exception from TestBase.afterClass: " + ex.getStackTrace());
		}
    }
	
    
    public Platform getPlatform(String platform) {
    	
    	Platform platformval = null;
    	
    	switch (platform.toLowerCase()){
    		
    	case "win10":
    		platformval = Platform.WIN10;
    		
    	case "mac":
    		platformval = Platform.MAC;
    		
    	case "linux":
    		platformval = Platform.LINUX;
    		
    	case "win8":
    		platformval = Platform.WIN8;
    		
    	case "windows":
    		platformval = Platform.WINDOWS;
    	}
    	
    	return platformval;
    }
	
	public String takeScreenshot()  throws Exception {
		try {
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String currentDir = System.getProperty("user.dir");
			
			FileUtils FileUtil = FileUtils.getFileUtils();
			String FileName = currentDir + "/screenshots/" + System.currentTimeMillis() + ".png";
			FileUtil.copyFile(srcFile, new File(FileName));
			 return FileName;
		} catch(Exception e) {
			throw new Exception("Exception from method TestBase.takeScreenshot: " + e.getStackTrace());
		}	
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public Hashtable<String, String> getTestParams(){
		return TestParams;
	}
	
	public LoginPage openSite() throws Exception {
		
		LoginPage LoginPage = null;
		try {
			ConfigManager = new ConfigFileManager();
			driver.get(ConfigManager.getAppURL());
			LoginPage = new LoginPage(this.driver);
		} catch(Exception e) {
			throw new Exception("Exception from TestBase.openSite: " + e.getStackTrace());
		}
		return LoginPage;
	}

}
