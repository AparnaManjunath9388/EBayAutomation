package com.ebay.automation.utils;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class WebDriverFactory {
	
	private WebDriver driver = null;
	
	public static WebDriverFactory getInstance() {
		return new WebDriverFactory();
	}
	
	public WebDriver createDriver(String Browser, String Platform) throws Exception {
    	try {
        	ConfigFileManager ConfigReader = new ConfigFileManager();
        	//String url = ConfigReader.getOfficeHubURL();
        	String url = ConfigReader.getJenkinsHubURL();
        	
    		
    		Platform desiredPlatform = getPlatform(Platform);
    		DesiredCapabilities cap = new DesiredCapabilities();
    		cap.setBrowserName(Browser);
    		cap.setPlatform(desiredPlatform);
    		cap.setVersion("ANY");
    		//cap.setVersion(Version);
    		
    		//try {
    		
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
	    			throw new Exception("Specified Browser '" + Browser + "' not identified");
	    		}
    		/*} catch(Exception e) {
    			try {
    				url = ConfigReader.getHomeHubURL();
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
    			
    		}*/
    		
    			
    		EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
    		EventHandler eventHandler = new EventHandler();
    		eventDriver.register(eventHandler);
    		driver = eventDriver;
    		
    		driver.manage().window().maximize();
    		driver.manage().deleteAllCookies();
    		driver.manage().timeouts().pageLoadTimeout(Constants.PAGELOAD_TIMEOUT, TimeUnit.SECONDS);
    		driver.manage().timeouts().implicitlyWait(Constants.IMPLICITWAIT_TIMEOUT, TimeUnit.SECONDS);		//not recommended since it makes the selenium wait for each and every element to wait before throwing error
 
	
    	} catch(Exception e) {
    		throw new Exception("Exception from WebDriverFactory.CreateDriver: " + e.getStackTrace());
    	}
    	
    	return driver;
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

}
