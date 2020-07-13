package com.ebay.automation.pagetests;

import java.util.Hashtable;

import org.testng.annotations.Test;

import com.ebay.automation.base.TestBase;
import com.ebay.automation.pages.LoginPage;
import com.ebay.automation.utils.Reporting;
import com.relevantcodes.extentreports.LogStatus;

public class LoginPageTest extends TestBase {
	
	private LoginPage LoginPage;
	private Reporting Reporting;
	private Hashtable<String, String> TestParams;
	
	public LoginPageTest() throws Exception {
		LoginPage = new LoginPage(getDriver());
		Reporting = getReport();
		TestParams = getTestParams();
	}
	
	
	@Test
	public void assertLoginPageTitle() {
		
		String expected = TestParams.get("ExpectedPageTitle");
		Reporting.StepDescription = "Verify page title. Expected: " + expected;
		Reporting.status = LogStatus.FAIL;
		
		if (LoginPage.getPageTitle().equalsIgnoreCase(expected))
			Reporting.Actual = "Title as expected";
		else
			Reporting.Actual = "Title not as expected";
	}
	
	
	
	

}
