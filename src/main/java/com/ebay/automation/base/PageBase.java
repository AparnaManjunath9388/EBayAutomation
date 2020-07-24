package com.ebay.automation.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.ebay.automation.utils.CommonMethods;
import com.ebay.automation.utils.Constants;
import com.ebay.automation.utils.ORParser;

public class PageBase {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ORParser ORparser;
    protected CommonMethods CommonMethods;

    public PageBase(WebDriver driver) throws Exception {
        this.driver = driver;
        wait = new WebDriverWait((WebDriver) driver, Constants.EXPLICITWAIT_TIMEOUT);
        CommonMethods = new CommonMethods(driver);
        ORparser = new ORParser();
    }
    
 

}
