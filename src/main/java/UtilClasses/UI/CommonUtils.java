package UtilClasses.UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
//import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.Logger;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.SetupAndMaintenance;
import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;

public class CommonUtils {

	public static WebElement common_element;
	private static WebDriverWait explicitWait = null;
	//public static Logger log = Logger.getLogger(CommonUtils.class);
//***********************************************************************************************************************************************
	public enum ExplicitWaitCalls {
		Visible, Click, TextPresent, ElementVisible;
	}
//***********************************************************************************************************************************************
	public static void explicitWait(WebElement Element, String EleType, String ElePresent, WebDriver driver)
			throws Exception {

		try {
			if (explicitWait == null) {
				explicitWait = new WebDriverWait(driver, 120);
			}
			switch (ExplicitWaitCalls.valueOf(EleType)) {
			case Visible:
				explicitWait.until(ExpectedConditions.visibilityOf(Element));
				break;
			case Click:
				explicitWait.until(ExpectedConditions.elementToBeClickable(Element));
				break;
			case TextPresent:
				explicitWait.until(ExpectedConditions.textToBePresentInElement(Element, ElePresent));
				break;
			case ElementVisible:
				explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Element.toString())));
				break;
			}

		} catch (Exception e) {
			System.out.println("Exception in explicitWait()");
			e.printStackTrace();
			Assert.fail();
		}

	}
//***********************************************************************************************************************************************	
	public static void waitForPageLoad(final WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			WebDriverWait wait2 = new WebDriverWait(driver, 60);
			wait2.until(pageLoadCondition);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
//***********************************************************************************************************************************************
	public static void waitForPageLoad(int seconds, final WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			WebDriverWait wait2 = new WebDriverWait(driver, seconds);
			wait2.until(pageLoadCondition);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

//***********************************************************************************************************************************************	
	public static void selectDropDownElement(WebElement location, String value) {
		Select selectElement = new Select(location);
		selectElement.selectByVisibleText(value);
	}
//***********************************************************************************************************************************************
	public static boolean isElementPresent(WebElement element) {
		boolean isPresent = false;
		try {
			element.isDisplayed();
			isPresent = true;
		} catch (Exception e) {
			System.out.println(element.toString() + " element not found");
		}
		return isPresent;
	}
//***********************************************************************************************************************************************
	public static boolean checkForElementPresence(WebElement element, WebElement scrollElement, WebDriver driver) {
		boolean isPresent = false;
		try {
			element.isDisplayed();
			isPresent = true;
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight",
					scrollElement);
			CommonUtils.hold(3);
			element.isDisplayed();
			isPresent = true;
			System.out.println(element.toString() + " element not found");
		}
		return isPresent;
	}// End Of isElementPresent()
//***********************************************************************************************************************************************
	public static void hold(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {

		}
	}

//***********************************************************************************************************************************************	
	public static String getGroupName() {
		return "Group" + RandomStringUtils.randomAlphabetic(5);
	}
//***********************************************************************************************************************************************	

	public static String uniqueId() {
		return new SimpleDateFormat("yyMMddhhmmssSSS").format(new Date());
	}
	
//******************************************************************************************************************************	
		
	public static String time() {
		return new SimpleDateFormat("mmss").format(new Date());
	}
			
//***********************************************************************************************************************************************
	public WebElement selectNavigatorItem(String element, WebDriver driver) {
		WebElement menu_item = driver.findElement(
				By.xpath("//h1[contains(text(),'Navigator')]/ancestor::div[3]/descendant::a[contains(text(),'" + element
						+ "')]"));
		return menu_item;
	}
//***********************************************************************************************************************************************
	public static String currentDate(String dateFormat) {
		Calendar c = Calendar.getInstance();
		c.get(Calendar.DAY_OF_MONTH);
		return new SimpleDateFormat(dateFormat).format(c.getTime()); // Ex:
		// "MM/dd/yyyy"
	}
//***********************************************************************************************************************************************	

	public static String futureDate(String dateFormat, int daysToAdd) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, daysToAdd);
		return new SimpleDateFormat(dateFormat).format(c.getTime()); // Ex:
		// "MM/dd/yyyy"
	}

//***********************************************************************************************************************************************	
	public static String pastDate(String dateFormat, int daysToBack) {
		Calendar c = Calendar.getInstance();
		int date = -daysToBack;
		c.add(Calendar.DAY_OF_MONTH, date);
		return new SimpleDateFormat(dateFormat).format(c.getTime()); // Ex: //
		// "MM/dd/yyyy"
	}
//***********************************************************************************************************************************************	

	public static void waitForElement(WebElement element, WebDriver driver) {
		WebDriverWait wait1 = new WebDriverWait(driver, 30L);
		wait1.pollingEvery(10, TimeUnit.SECONDS);
		wait1.until(ExpectedConditions.visibilityOf(element));
	}
//***********************************************************************************************************************************************
	public static void waitForVisibilityOfElementLocated(By expectedElement, int timeout,WebDriver driver) {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, timeout);
	        wait.until(ExpectedConditions.visibilityOfElementLocated(expectedElement));

	    } catch (Exception e) {
	        e.printStackTrace();
	        //System.out.println("print ur message here");
	    }
	}
	
	
	
	
//***********************************************************************************************************************************************
	
	//Wait for the visibility of the element for a certain amount of time
		public static void waitforElementtoVisible(long timeoutseconds, WebElement element,WebDriver driver) {

			WebDriverWait wait = new WebDriverWait(driver, timeoutseconds);
			wait.pollingEvery(20, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.visibilityOf(element));
		}
//***********************************************************************************************************************************************
		public static void waitforElementtoClick(long timeoutseconds, WebElement element,WebDriver driver) {

			WebDriverWait wait = new WebDriverWait(driver, timeoutseconds);
			wait.pollingEvery(20, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
//***********************************************************************************************************************************************	
	  public static void waitForElementToload(By by, WebDriver driver) {
	        try {
	             /*timeout increased due to application slowness
	             This change needs to be revoked when application is back to its old speed.*/
	            WebDriverWait wait = new WebDriverWait(driver, 30);
	           // wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	            wait.pollingEvery(10, TimeUnit.SECONDS);
	            wait.until(ExpectedConditions.presenceOfElementLocated(by));

	        } catch (Exception e) {
	        	System.out.println("Element is taking too much time to load");
	        }
	    }
	  
//*********************************************************************************************************************************************	  
	public static void PageRefresh(WebDriver driver) {
		driver.navigate().refresh();
	}
//***********************************************************************************************************************************************
	public static void popupapply(WebDriver driver) {
		WebElement popupapply = driver.findElement(By.xpath("//button[contains(@id,'pt1:pePanel:0:peApply')]"));
		popupapply.click();

	}

	public static void popupok(WebDriver driver) {
		WebElement popupok = driver.findElement(By.xpath("//button[contains(@id,'pt1:pePanel:0:peDialo::ok')]"));
		popupok.click();

	}
//***********************************************************************************************************************************************
	public static WebElement scrollToElement(WebElement element, WebDriver driver) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", new Object[] { element });

		Thread.sleep(5000L);
		driver.manage().timeouts().implicitlyWait(30L, TimeUnit.SECONDS);
		return element;
	}
//***********************************************************************************************************************************************
	public static void waitForElementToPresent(WebElement elementToPresent) {
		try {
			int count = 0;
			do {
				threadSleep(500);
				count++;
				if (count == 8)
					break;
			} while (elementToPresent.isDisplayed() == false);
		} catch (Exception e) {
			System.out.println("Exception occurred in waitForElementToPresent() , exception = " + e.getMessage());
		}
	}
//***********************************************************************************************************************************************
	public static void threadSleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			System.out.println("Exception occurred in threadSleep() , exception = " + e.getMessage());
		}

	}
	//***********************************************************************************************************************************************
	public static String GetPageURL(WebDriver driver) {
		String CurrentUrl = null;
		CurrentUrl = driver.getCurrentUrl();
		return CurrentUrl;
	}
//***********************************************************************************************************************************************
	public static JavascriptExecutor getJavaScriptExecutor(WebDriver driver)
	{
      JavascriptExecutor js = (JavascriptExecutor) driver;
      return js;
    }

//*********************************************************************************************************************     
	public static Boolean waitForJStoLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) getJavaScriptExecutor(driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};
		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return getJavaScriptExecutor(driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

//*********************************************************************************************************************
	public static Boolean waitForJStoLoad(WebDriver driver, int time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) getJavaScriptExecutor(driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};
		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return getJavaScriptExecutor(driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

//*********************************************************************************************************************
	  public static void clickWithJavaScript(WebElement ele, WebDriver driver) {
	        getJavaScriptExecutor(driver).executeScript("arguments[0].click();", ele);
	    }
//*********************************************************************************************************************
	    public static void waitForElement(WebElement webElement, int timeoutInSecs, int pollingTimeInSecs,WebDriver driver) {

	        waitExplicitly(timeoutInSecs, pollingTimeInSecs, ExpectedConditions.visibilityOf(webElement),driver);

	        return;
	    }
//*********************************************************************************************************************	   
	    public static void waitExplicitly(int timeoutInSecs, int pollingTimeInSecs,
                ExpectedCondition<WebElement> expectedCondition,WebDriver driver) {
WebDriverWait wait = new WebDriverWait(driver, timeoutInSecs);
wait.pollingEvery(pollingTimeInSecs, TimeUnit.SECONDS);
wait.until(expectedCondition);
	    }

//*********************************************************************************************************************
	
	    public static WebElement waitForElement5(WebElement webElement, int timeoutInSecs, int pollingTimeInSecs,WebDriver driver) {

	        waitExplicitly(timeoutInSecs, pollingTimeInSecs, ExpectedConditions.visibilityOf(webElement),driver);

	        return webElement;
	    }  
//********************************************************************************************************************
//***********************************************************************************************************************************************
public static void waitForInvisibilityOfElement(WebElement element, WebDriver driver,long seconds) {
        driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
    try {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.pollingEvery(2, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.invisibilityOf(element));
 
    } catch (Exception e) {
        System.out.println("");
    }
 
}


	public static XSSFSheet readDataFromExcel(File file) {
		XSSFWorkbook workbook = null;
		XSSFSheet dataSheet = null;
		try {
			FileInputStream fileIpStream = new FileInputStream(file);
			if (fileIpStream != null) {
				workbook = new XSSFWorkbook(fileIpStream);
			} else
				System.out.println("File doesn't exist");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Get sheet from Excel
		if (workbook != null) {
			System.out.println("Sheet name -> " + workbook.getSheetName(0));
			dataSheet = workbook.getSheetAt(0);
		} else {
			System.out.println("workbook is null");
			Assert.assertTrue(false, "workbook is null");
		}
		return dataSheet;
	}
 	    
	    
}





