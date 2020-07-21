package com.ebay.automation.utils;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.tools.ant.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonMethods {

	private WebDriver driver;
	private WebDriverWait wait;
	private ORParser ORparser;
	
   public CommonMethods(WebDriver driver) throws Exception {
		this.driver = driver;
		wait = new WebDriverWait(driver, Constants.EXPLICITWAIT_TIMEOUT);
		ORparser = new ORParser();
	}

   public WebElement getElement(String objName) throws Exception {
    	return driver.findElement(ORparser.getBy(objName));
    }
    
    public List<WebElement> getElements(String objName) throws Exception {
    	return driver.findElements(ORparser.getBy(objName));
    }
    
	public WebElement getElement(String property, String Value) throws Exception {
		
		
		switch(property.toLowerCase()) {
		 
		case "name":
			return driver.findElement(By.name(Value));
			
		case "id":
			return driver.findElement(By.id(Value));
			
		case "linktext":
			return driver.findElement(By.linkText(Value));
			
		case "partiallinktext":
			return driver.findElement(By.partialLinkText(Value));
	
		case "tagname":
			return driver.findElement(By.tagName(Value));
			
		case "cssselector":
			return driver.findElement(By.cssSelector(Value));
		
		case "classname":
			return driver.findElement(By.cssSelector(Value));
			
		case "xpath":
			return driver.findElement(By.xpath(Value));
		default:
			throw new Exception("Exception from ORParser.getBy: Unknown locator type '" + property + "'");

		}	
	}
	
	
	public List<WebElement> getElements(String property, String Value) throws Exception {
		
		
		switch(property.toLowerCase()) {
		 
		case "name":
			return driver.findElements(By.name(Value));
			
		case "id":
			return driver.findElements(By.id(Value));
			
		case "linktext":
			return driver.findElements(By.linkText(Value));
			
		case "partiallinktext":
			return driver.findElements(By.partialLinkText(Value));
	
		case "tagname":
			return driver.findElements(By.tagName(Value));
			
		case "cssselector":
			return driver.findElements(By.cssSelector(Value));
		
		case "classname":
			return driver.findElements(By.cssSelector(Value));
			
		case "xpath":
			return driver.findElements(By.xpath(Value));
		default:
			throw new Exception("Exception from ORParser.getBy: Unknown locator type '" + property + "'");

		}	
	}
	
	public HashMap<String, String> getHrefOfAllLinksAndImageLinks() throws Exception {
		
		HashMap<String, String> FinalList = new HashMap<String, String>();
		try {
			List<WebElement> AllFrames = driver.findElements(By.tagName("frame"));
			
			if (AllFrames.isEmpty()) {
				List<WebElement> ListOfLinks = driver.findElements(By.tagName("a"));
				ListOfLinks.addAll(driver.findElements(By.tagName("img")));		
				String href = "";
				for (WebElement element : ListOfLinks) {
					href = element.getAttribute("href");
					if (href!= null && !href.startsWith("javascript:")) {
						FinalList.put(element.getText(), href);
					}
				}
			} else {
				
				String href = "";
				for(int i=0;i<AllFrames.size();i++) {
					switchToFrame(AllFrames.get(i).getAttribute("name"), "name");
					List<WebElement> ListOfLinks = driver.findElements(By.tagName("a"));
					ListOfLinks.addAll(driver.findElements(By.tagName("img")));
					for (WebElement element : ListOfLinks) {
						href = element.getAttribute("href");
						if (href!= null && !href.startsWith("javascript:")) {
							FinalList.put(element.getText(), href);
						}
					}
				switchToFrame("default", "");
				}
			}
		} catch (Exception e) {
			throw new Exception("Exception from method CommonMethods.getAllLinksAndImageLinks: " + e.getMessage() );
		}
		
		return FinalList;
	}
	
	public List<WebElement> getAllLinks() {
		
		return driver.findElements(By.tagName("a"));
		
	}
	
	public String find_Which_Of_These_Links_Not_Displayed(String[] listOfLinksToVerify) {
		
		ArrayList<WebElement> ListOfLinkElements = (ArrayList<WebElement>) getAllLinks();
		ArrayList<String> ListOfElementsText = new ArrayList<String>();
		
		for (WebElement e : ListOfLinkElements) {
			ListOfElementsText.add(e.getText());		
		}
		
		String LinksNotPresent = "";
		for (String linkText : listOfLinksToVerify) {
			if (!ListOfElementsText.contains(linkText)) {
				LinksNotPresent+=linkText + ",";
			}
		}
		
		if (!LinksNotPresent.isEmpty()) {
			LinksNotPresent = LinksNotPresent.substring(0, LinksNotPresent.length()-1);		
		}
		
		return LinksNotPresent;
	}
	
	/************************************************************
	 * getPageTitle
	 * @return String page title
	 */
	public String getPageTitle() {
		return driver.getTitle();
	}
	
	/************************************************************
	 * clickOnWebElement
	 * @param String objName
	 * @return String page title
	 * @throws Exception 
	 */
	public boolean clickOnWebElement(String objName) throws Exception {
		
		try {
			By by = ORparser.getBy(objName);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			WebElement element = driver.findElement(by);
			if (element.isDisplayed() && element.isEnabled()) {
				element.click();
				return true;
			} else {
				return false;
			}
		} catch(Exception e) {
			
			try {
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				WebElement element = getElement(objName);
				executor.executeScript("arguments[0].click();", element);
				return true;
			} catch(Exception ex) {
				throw new Exception("Exception from method CommonMethods.clickOnWebElement: " + ex.getMessage());
			}
		}
	}
	
	/**************************************************************
	 * navigate_To_Page_And_GetTitle
	 * @param String LinkName
	 * @param String ExpectedTitle
	 * @return boolean value
	 * @throws Exception 
	 */
	public void navigate_To_Page_And_GetTitle(String LinkName, String ExpectedTitle) throws Exception {
		
		this.clickOnWebElement(LinkName);
		wait.until(ExpectedConditions.titleContains(ExpectedTitle));
		this.getPageTitle();
	}
	
	/**************************************************************
	 * setText
	 * @param ObjName
	 * @param ValueToSet
	 * @return void
	 * @throws Exception 
	 */
	public boolean setText(String ObjName, String ValueToSet) throws Exception {
		try {
			WebElement element = getElement(ObjName);
			element.sendKeys(Keys.CONTROL + "a");
			element.sendKeys(Keys.DELETE);
			element.sendKeys(ValueToSet);
			return element.getAttribute("value").equals(ValueToSet);
			//wait.until(ExpectedConditions.attributeContains(by, "value", ValueToSet));
		} catch(Exception e) {
			throw new Exception("Exception from method CommonMethods.setText: " + e.getMessage());
		}
	}
	
	/**************************************************************
	 * selectFromDropdown
	 * @param ObjName
	 * @param ValueToSelect
	 * @return void
	 * @throws Exception 
	 */
	@SuppressWarnings("unlikely-arg-type")
	public boolean selectFromDropdown(String ObjName, String ValueToSelect) throws Exception {
		
		try {
			Select SearchTarget = new Select(getElement(ObjName));
			SearchTarget.selectByVisibleText(ValueToSelect);
			return true;
			//SearchTarget.selectByValue(ValueToSelect);
		} catch(Exception e) {
			throw new Exception("Exception from method CommonMethods.selectFromDropdown: " + e.getMessage());
		}
	}

	/**************************************************************
	 * switchToFrame
	 * @param String FrameNameOrId
	 * @param String By (name/id/WebElement)
	 * @return void
	 * @throws Exception 
	 */
	public void switchToFrame(String FrameNameOrId, String By) throws Exception {
		try {
			switch (By.toLowerCase()) {
				
			case "name":
				driver.switchTo().frame(FrameNameOrId);
				break;
				
			case "webelement":
				WebElement frame  =  getElement(FrameNameOrId);
				driver.switchTo().frame(frame);
				break;
				
			case "id":
				driver.switchTo().frame(Integer.parseInt(FrameNameOrId));
				break;		
				
			default:
				driver.switchTo().defaultContent();
				break;
			}
		} catch(Exception e) {
			throw new Exception("Exception from method CommonMethods.switchToFrame: " + e.getMessage());
		}
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
	

	public static Date getTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar.getTime();
	}
	
	public String getURLResponse(URL url) throws Exception {
		
		 String response = "";
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 try
		 {
		     connection.connect();
		     response = connection.getResponseMessage();         
		     connection.disconnect();
		     return response;
		 }catch(Exception e) {
			 throw new Exception("Exception from method CommonMethods.getURLResponse: "+ e.toString());
		 }  
	}
	
	/**************************************************************
	 * getAllBrokenLinks
	 * @return List of links that are broken in the page
	 */
	public ArrayList<String> getAllBrokenLinks() throws Exception{
		
		HashMap<String, String> ListOfLinks = getHrefOfAllLinksAndImageLinks();
		
		//List<String> ListOfBrokenLinkText = null;
		ArrayList<String> ListOfBrokenLinkText = new ArrayList<String>();
		
		try {
			String response;
			if (!ListOfLinks.isEmpty()) {
				for(String key : ListOfLinks.keySet()) {
					response = getURLResponse(new URL(ListOfLinks.get(key)));
					
					if (!response.equalsIgnoreCase("ok")) {
						ListOfBrokenLinkText.add(key);
					}
				}
			}
			
			return ListOfBrokenLinkText;
		}catch(Exception e) {
			throw new Exception("Exception from method CommonMethods.getAllBrokenLinks: " + e.toString() + " | " + e.getMessage());
		}
	}
	
	
	/****************************************************************
	 * getElementText
	 * @param String ObjectName
	 * @return String which is the text value of the object
	 */
	public String getElementText(String ObjName) throws Exception {
		
		try {
			return getElement(ObjName).getText();
		} catch(Exception e) {
			throw new Exception("Exception from method CommonMethods.getElementText: " + e.toString());
		}
		
	}
	
	public boolean CompareText(String BaseText, String CompareText, String TypeOfComparison) throws Exception {
		
		switch(TypeOfComparison.toLowerCase()) {
		
		case "equals":
			return BaseText.equals(CompareText);
			
		case "equalsignorecase":
			return BaseText.equalsIgnoreCase(CompareText);
			
		case "contains":
			return BaseText.contains(CompareText);
			
		case "matches":
			return BaseText.matches(CompareText);
			
		case "startswith":
			return BaseText.startsWith(CompareText);
			
		case "endswith":
			return BaseText.endsWith(CompareText);
			
		case "isempty":
			return BaseText.isEmpty();
			
		default:
			throw new Exception("From method CommonMethods.CompareText: Could not recognize the Type of comparison");
			
		}
	}
	
	
	public void scrollVerticalBarBy(long pixelsOfYAxis, String direction) throws Exception {
		
		try {
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			
			if (pixelsOfYAxis != 0) {
				String pixels = "";
				if (direction.equalsIgnoreCase("up") && pixelsOfYAxis > 0) {
					pixels = "-" + pixelsOfYAxis;
				} else {
					pixels = "" + pixelsOfYAxis;
				}	
			
				String cmd = "window.scrollBy(0," + pixels + ")";
				jse.executeScript(cmd);
			} else {
				jse.executeScript("window.scrollTo(0,0)");
			}
		} catch(Exception e) {
			throw new Exception("Exception from method CommonMethods.scrollVerticalBarBy: " + e.getMessage());
		}
	}
	
	/*public long getVerticalScrollBarLength() throws Exception {
		
		try {
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			
			long pageHieght = (long) jse.executeScript("function returnHightestNode() {" +
					"var pageHeight = 0;" +
					"pageHeight = findHighestNode(document.documentElement.childNodes);" +
					"return pageHeight;}" +
					"function findHighestNode(nodesList) {" +
					"var pageHeight = 0;" +
			        "for (var i = nodesList.length - 1; i >= 0; i--) {" +
			            "if (nodesList[i].scrollHeight && nodesList[i].clientHeight) {" +
			                "var elHeight = Math.max(nodesList[i].scrollHeight, nodesList[i].clientHeight);" +
			                "pageHeight = Math.max(elHeight, pageHeight);" +
			           " }" +
			            "if (nodesList[i].childNodes.length) findHighestNode(nodesList[i].childNodes);" +
			      "  } return pageHeight;" +
			  "}" + "returnHightestNode();");
			return pageHieght;
			
			
		} catch (Exception e) {
			throw new Exception("From method CommonMethods.getVerticalScrollBarLength: " + e.getMessage());
		}
	} */
	
	public long getVerticalScrollBarLength() throws Exception {
	
		try {
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			
			long pageHeight = (long) jse.executeScript("var body = document.body," +
			    "html = document.documentElement;" +
			    "return Math.max( body.scrollHeight, body.offsetHeight, " +
			          "html.clientHeight, html.scrollHeight, html.offsetHeight );");
			
			return pageHeight;
		} catch(Exception e) {
			throw new Exception("Exception from method CommonMethods.getVerticalScrollBarLength: " + e.getMessage());
			
		}
	}

		
		
		    
	
	public void getListOfLinksNotDisplayed(LinkedList<String> ListOfLinks) throws Exception {
		try {
			
			String[] arrayOfLinks = ListOfLinks.toArray(new String[ListOfLinks.size()]);
			for (String link : arrayOfLinks) {
				
				WebElement WebLink = driver.findElement(By.linkText(link));
				if (WebLink.isDisplayed()) {
					ListOfLinks.remove(link);
				}
			}
		} catch (Exception e) {
			throw new Exception("Exception from method CommonMethods.getListOfLinksNotDisplayed: " + e.getMessage());
		}
		
	}
	
	public boolean elementExists(String objName) throws Exception {
		
		boolean isDisplayed = false;
		try {
			isDisplayed = getElement(objName).isDisplayed();
			return isDisplayed;
		} catch(Exception e) {
			throw new Exception("Exception from method CommonMethods.elementExists: " + e.getMessage());
		}
	}
	
	public boolean clickOnElementBasedOnText(String ElementsObjName, String TextToMatch, String ComparisonType) throws Exception {
		
		boolean ElementFound = false;
		try {
			List<WebElement> AllElements = getElements("ElementsObjName");
			
			for (WebElement e: AllElements) {
				if (CompareText(e.getAttribute("value"), TextToMatch, ComparisonType)) {
					e.click();
					ElementFound = true;
					break;		
				}
			}
			return ElementFound;
		} catch(Exception e) {
			throw new Exception("Exception from CommonMethods.clickOnElementBasedOnText: " + e.getMessage());
		}
	}
	
	//@SuppressWarnings("finally")
	public boolean FigureOutInputTypeAndSetValue(String objName, String ValueToSet) throws Exception {
		
		boolean valueSet = false;
		try {
			By by = ORparser.getBy(objName);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			
			
			WebElement obj = driver.findElement(by);
			String objType = obj.getAttribute("type");
			
			if (objType == "text") {
				obj.sendKeys(ValueToSet);
				valueSet = true;
			} else if (objType == "radio") {
				obj.click();
				valueSet = true;
			}
		} catch(Exception e) {
			try {
				selectFromDropdown(objName, ValueToSet);
				valueSet = true;
			} catch(Exception ex) {
				throw ex;
			}	
		}
		
		return valueSet;
	}
	
	public boolean uploadFile(String objName, String filePath) throws Exception {
		
		boolean uploaded = false;
		try {
			if (clickOnWebElement(objName)) {
				
				//create Robot class
				Robot robot = new Robot();
				robot.setAutoDelay(2000);
				
				//select string which is the file path. Put it in clipboard which simulates Ctrl+C action
				StringSelection selectedString = new StringSelection(filePath);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selectedString, null);
				
				robot.setAutoDelay(1000);
				
				//simulation of Ctrl+V
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_V);
				
				//simulation of clicking on "Upload" button
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				uploaded = true;
			}
			
		} catch (Exception e) {
			throw new Exception("Exception from uploadFile method: " + e.getMessage());
		}
		
		return uploaded;
	}
	
	public boolean setDate(String objName, String CalBoxObject, String Date) throws Exception {
		
		boolean DateSet = false;
		try {
			if (clickOnWebElement(objName)) {
				wait.until(ExpectedConditions.presenceOfElementLocated(ORparser.getBy(CalBoxObject)));
				DateFormat formatter = new SimpleDateFormat("d-MMM-yyyy");
				Date date = formatter.parse(Date);

				String CalTitle = "/table/thead/tr[1]/td[@class='title']";
				String CalBoxProp = ORparser.getObjectProperty(CalBoxObject) + CalTitle;
				WebElement title = getElement(CalBoxProp.split(";")[0], CalBoxProp.split(";")[1]);
				String titleText = title.getText();
				WebElement Btn;
				long year = Integer.parseInt(Date.split("-")[2]);
				
				//move to required Year	
				if (year < Integer.parseInt(Year.now().toString())) {

					String PrevYearBtn = "/table/thead/tr[@class='headrow']/td[1]/div";
					String BtnProp = ORparser.getObjectProperty(CalBoxObject) + PrevYearBtn;
					Btn = getElement(BtnProp.split(";")[0], BtnProp.split(";")[1]);
					while (!CompareText(titleText, year + "", "contains")) {
						Btn.click();
						Thread.sleep(1000);
						titleText = title.getText();
					}
				}
					
				//move to required month
				@SuppressWarnings("deprecation")
				String month = Month.of(date.getMonth()+1).toString().toLowerCase();
				
				if (date.getMonth()+1 < (getTime().getMonth()+1)) {
					String prevMonthBtn = "/table/thead/tr[@class='headrow']/td[2]/div";
					String LeftBtnProp = ORparser.getObjectProperty(CalBoxObject) + prevMonthBtn;
					Btn = getElement(LeftBtnProp.split(";")[0], LeftBtnProp.split(";")[1]);
					while(!CompareText(titleText.toLowerCase(), month, "contains")) {
						Btn.click();
						Thread.sleep(1000);
						titleText = title.getText();
					}
				} else {
					String nextMonthBtn = "/table/thead/tr[@class='headrow']/td[4]/div";
					String RightBtnProp = ORparser.getObjectProperty(CalBoxObject) + nextMonthBtn;
					Btn = getElement(RightBtnProp.split(";")[0], RightBtnProp.split(";")[1]);
					while(!CompareText(titleText.toLowerCase(), month, "contains")) {
						Btn.click();
						Thread.sleep(1000);
						titleText = title.getText();
					}
				}
				
				//traverse through calendar days and click on required day
				if (CompareText(titleText, year + "", "contains") && CompareText(titleText.toLowerCase(), month, "contains")) {
				
					String daysRow = "/table/tbody/tr";
					String rowsProp = ORparser.getObjectProperty(CalBoxObject) + daysRow;
					List<WebElement> allRows = getElements(rowsProp.split(";")[0], rowsProp.split(";")[1]);
					String Day = String.valueOf(date.getDate());
				
					if (!allRows.isEmpty()) {
						for (WebElement row : allRows) {
							List<WebElement> cells = row.findElements(By.tagName("td"));
							if (!cells.isEmpty()) {
								for (WebElement cell : cells) {
									if (cell.getText().equals(Day) && !cell.getAttribute("class").equals("day wn")) {
										cell.click();
										DateSet = true;
										break;
									}
								}
							}		
							if (DateSet)
								break;
						}
					}
				}
			}
		} catch(Exception e) {
			throw new Exception("Exception from method CommonMethods.setDate: " + e.getMessage());
		}
		
		return DateSet;
	}
	
	/*public boolean hoverMouseAndClick(String mouseHoverObj, String objToClick) throws Exception {
		
		boolean clicked = false;
		try {
			
			Actions actions = new Actions(driver);
			
			WebElement ElementToHover = getElement(mouseHoverObj);
			wait.until(ExpectedConditions.elementToBeClickable(ElementToHover));
			actions.moveToElement(ElementToHover).perform();
			
			WebElement ElementToClick = getElement(objToClick);
			wait.until(ExpectedConditions.elementToBeClickable(ElementToClick));
			ElementToClick.click();
			clicked = true;
			
		} catch (Exception e) {
			throw new Exception("Exception from method CommonMethods.hoverMouseAndClick: " + e.getMessage());
		}
		
		return clicked;
	}*/
	
	public boolean hoverMouseAndClick(WebElement mouseHoverObj, WebElement objToClick) throws Exception {
		
		boolean clicked = false;
		try {
			
			Actions actions = new Actions(driver);
			wait.until(ExpectedConditions.elementToBeClickable(mouseHoverObj));
			actions.moveToElement(mouseHoverObj).perform();
			
			wait.until(ExpectedConditions.elementToBeClickable(objToClick));
			objToClick.click();
			clicked = true;
			
		} catch (Exception e) {
			throw new Exception("Exception from method CommonMethods.hoverMouseAndClick: " + e.getMessage());
		}
		
		return clicked;
	}
	
	public boolean searchInLookUpWindow(String winTitle, String value) throws Exception {
		
		boolean found = false;
		try {
			String parentHandle = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();
			
			for (String handle : handles) {
				if(!handle.equals(parentHandle)) {
					driver.switchTo().window(handle);
					driver.manage().window().maximize();
					break;
				}
			}
			
			String title = getElementText("searchWin.title");
			
			if (CompareText(title.toLowerCase(), winTitle.toLowerCase(), "contains")) {
				
				setText("searchWin.searchBox", value);
				clickOnWebElement("searchWin.searchBtn");
				
				Thread.sleep(1000);
				List<WebElement> allLinks = driver.findElements(By.linkText(value));
				
				if (!allLinks.isEmpty()) {
					allLinks.get(0).click();
					found = true;
				}else {
					driver.close();
				}
			}
			driver.switchTo().window(parentHandle);
			return found;
			
		} catch(Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			throw new Exception("Exception from method " + methodName + ": " + e.getMessage());
		}
	}
	
	public boolean rightClickAndSelect(WebElement element, String MoveDirection) throws Exception {
		
		try {
			
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Actions actions = new Actions(driver);
			

			//JavascriptExecutor executor = (JavascriptExecutor)driver;
			//executor.executeScript("window.focus();");
			if (MoveDirection.equalsIgnoreCase("down")) {
				actions.contextClick(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.RETURN).build().perform();
			} else {
				actions.contextClick(element).sendKeys(Keys.ARROW_UP).sendKeys(Keys.ARROW_UP).sendKeys(Keys.RETURN).build().perform();
			}
			return true;
		} catch(Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			throw new Exception("Exception from method " + methodName + ": " + e.getMessage());
		}
		
	}
	
	public boolean tryLocatingElement(By by) {
		
		int attempts = 0;
		boolean found = false;
		while (attempts < 2) {
			try {
				WebElement e = driver.findElement(by);
				found = true;
				break;
			} catch(Exception e) {
			}
			attempts++;
		}
		return found;
	}
	
	public void scrollElementIntoView(WebElement element) throws Exception {
		try {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", element);
		} catch(Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			throw new Exception("Exception from method " + methodName + ": " + e.getMessage());
		}
		
	}

}
