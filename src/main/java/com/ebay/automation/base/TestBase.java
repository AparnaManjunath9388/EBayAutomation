package com.ebay.automation.base;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.ebay.automation.utils.ConfigFileManager;
import com.ebay.automation.utils.Constants;
import com.ebay.automation.utils.EventHandler;
import com.ebay.automation.utils.ExcelDataProvider;
import com.relevantcodes.extentreports.LogStatus;
import com.ebay.automation.utils.Reporting;

public class TestBase {
	
	private ConfigFileManager ConfigReader;
    private WebDriver driver;
    private Hashtable<String, String> TestParams;
    private Reporting Reporting;

    
    public TestBase() throws Exception {
    	ConfigReader = new ConfigFileManager();
    }
    
    @BeforeSuite
    public void beforeSuite() throws Exception {
    	
    	Reporting Reporting = new Reporting();
    	Reporting.initializeReporting();

    }
    
    @BeforeTest
    @Parameters({"ExcelDataRow", "Browser", "Platform", "Version"})
    public void beforeTest(@Optional("")String ExcelDataRow, @Optional("")String Browser, @Optional("")String platform, @Optional("")String Version, @Optional("")String Description) throws Exception {
    	
    	String url = ConfigReader.getHubURL();
		
		Platform desiredPlatform = getPlatform(platform);
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setBrowserName(Browser);
		cap.setPlatform(desiredPlatform);
		cap.setVersion(Version);
		
		if (Browser.toLowerCase().equals("chrome")){
			
			ChromeOptions options = new ChromeOptions();
			options.merge(cap);
			
			driver = new RemoteWebDriver(new URL(url), options);
		}
		else if (Browser.toLowerCase().equals("firefox")) {

			FirefoxOptions options = new FirefoxOptions();
			options.addPreference("network.proxy.type", 0);
			options.merge(cap);
			
			driver = new RemoteWebDriver(new URL(url), options);
		}
		else {
			throw new Exception("Browser not identified");
		}
		
			
		EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
		EventHandler eventHandler = new EventHandler(Reporting);
		eventDriver.register(eventHandler);
		driver = eventDriver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Constants.PAGELOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICITWAIT_TIMEOUT, TimeUnit.SECONDS);		//not recommended since it makes the selenium wait for each and every element to wait before throwing error
		
		driver.navigate().to(ConfigReader.getAppURL());
		
		//read required data
		ExcelDataProvider DataProvider = new ExcelDataProvider();
		Hashtable<String, String> params = DataProvider.get(Description, ExcelDataRow);
		if (!params.isEmpty())
			TestParams.putAll(params);
		
		//start test
		Reporting.startTest(Description);
	}

    @AfterTest
    public void afterTest() {
		try {
			driver.manage().deleteAllCookies();
			driver.quit();
			
			Reporting.endTest();
			Reporting.flushReport();
			
			TestParams.clear();
		} catch (Exception ex) {
			Reporting.StepDescription = "Exception occured: ";
			Reporting.Actual = ex.getCause().toString() + " - " + ex.getMessage();
			Reporting.status = LogStatus.ERROR;
			Reporting.WriteIntoReport();
		}
    }
    
	@AfterSuite
	public void AfterSuite() {
		Reporting.flushAndCloseReport();
	}
	
	@BeforeMethod
	public void beforeEachMethod(Method method) {
		Reporting.StepDescription = "";
		Reporting.Actual = "";
		Reporting.status = LogStatus.FAIL;
		Reporting.ScreenshotPath = "";
		
		Reporting.WriteIntoLogFile("Started test: " + method.getName(), LogStatus.INFO);
	}
	
	@AfterMethod
	public void AfterEachMethod(Method method) throws Exception {
		
		try {
			if (Reporting.status == LogStatus.FAIL || Reporting.status == LogStatus.ERROR) {
				Reporting.Actual += ". Screenshot: ";
				Reporting.ScreenshotPath = takeScreenshot();
			
			}
			Reporting.WriteIntoReport();	
		} catch (Exception e) {
			Reporting.StepDescription = "Exception occured: ";
			Reporting.Actual = e.toString();
			Reporting.status = LogStatus.ERROR;
			Reporting.WriteIntoReport();
		}

		Reporting.WriteIntoLogFile("Completed Method: "+ method.getName(), LogStatus.INFO);
	}

    public WebDriver getDriver() {
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
    	}
    	
    	return platformval;
    }
	
	public Reporting getReport() {
		return Reporting;
	}
	
	public Hashtable<String, String> getTestParams() {
		return TestParams;
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
			throw new Exception("Exception from method CommonMethods.takeScreenshot: " + e.getMessage());
		}	
	}

}
