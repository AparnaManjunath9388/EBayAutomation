package com.ebay.automation.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.aventstack.extentreports.Status;
import com.ebay.automation.reporting.ExtentTestManager;


public class EventHandler implements WebDriverEventListener {
	
	private Logger logger;
	
	public EventHandler() {
		logger = Logger.getLogger(EventHandler.class);
	}
	
	@Override
	public void afterAlertAccept(WebDriver arg0) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Alert acception completed. ");
		
	}

	@Override
	public void afterAlertDismiss(WebDriver arg0) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Alert dismission completed. ");
		
	}

	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Intended to set value: " + arg2[0] + ", value that has been set: " + arg0.getAttribute("value"));
		
	}

	@Override
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Clicked on element " + arg0.toString());
		
	}

	@Override
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Found the element using " + arg0.toString());
		
	}

	@Override
	public <X> void afterGetScreenshotAs(OutputType<X> arg0, X arg1) {
		// TODO Auto-generated method stub
		logger.info("From Thread " + Thread.currentThread().getId() + ": Screenshot taken");
		
	}

	@Override
	public void afterGetText(WebElement arg0, WebDriver arg1, String arg2) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Fetched value '" + arg2 + "' from element " + arg0.toString());
		
	}

	@Override
	public void afterNavigateBack(WebDriver arg0) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Navigated back to url " + arg0.getCurrentUrl());
		
	}

	@Override
	public void afterNavigateForward(WebDriver arg0) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Navigated forward to url " + arg0.getCurrentUrl());
		
	}

	@Override
	public void afterNavigateRefresh(WebDriver arg0) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": After Navigate Refresh url " + arg0.getCurrentUrl());
		
	}

	@Override
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Intended to navigate to url: " + arg0 + ", navigated to: " + arg1.getCurrentUrl());
		
	}

	@Override
	public void afterScript(String arg0, WebDriver arg1) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Completed the script '" + arg0 + "'");
		
	}

	@Override
	public void afterSwitchToWindow(String arg0, WebDriver arg1) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Intended to switch to window '" + arg0 + "', navigated to window '" + arg1.getWindowHandle().toString());
		
	}

	@Override
	public void beforeAlertAccept(WebDriver arg0) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Trying to accept alert. ");
		
	}

	@Override
	public void beforeAlertDismiss(WebDriver arg0) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Trying to dismiss alert. ");
		
	}

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Before value: " + arg0.getAttribute("value") + ", expected to be set to: " + arg2[0]);
		
	}

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Trying to click on element " + arg0.toString());
		
	}

	@Override
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Trying to find the element using " + arg0.toString());
		
	}

	@Override
	public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Trying to take screenshot. ");
		
	}

	@Override
	public void beforeGetText(WebElement arg0, WebDriver arg1) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Trying to get text value from element " + arg0.toString());
		
	}

	@Override
	public void beforeNavigateBack(WebDriver arg0) {
		logger.info("From Thread " + Thread.currentThread().getId() +": url before navigating back: " + arg0.getCurrentUrl());
		
	}

	@Override
	public void beforeNavigateForward(WebDriver arg0) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": url before navigating forward: " + arg0.getCurrentUrl());
		
	}

	@Override
	public void beforeNavigateRefresh(WebDriver arg0) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": url before navigate refresh: " + arg0.getCurrentUrl());
		
	}

	@Override
	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Trying to navigate to url: " + arg0);
		
	}

	@Override
	public void beforeScript(String arg0, WebDriver arg1) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Trying to start the script " + arg0);
		
	}

	@Override
	public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
		logger.info("From Thread " + Thread.currentThread().getId() + ": Trying to switch to window " + arg0);
		
	}

	@Override
	public void onException(Throwable arg0, WebDriver arg1) {
		
		ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
		logger.info("From Thread " + Thread.currentThread().getId() + ": Exception: Cause- " + arg0.getCause() + ", Message- " + arg0.getMessage());
	}
}
