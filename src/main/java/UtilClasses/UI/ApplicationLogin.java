package UtilClasses.UI;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import TestBase.UI.GetDriverInstance;
import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;



public class ApplicationLogin extends CommonUtilsDefs{

	public static boolean newsFeedEnabled = false;
	private GlobalPageTemplate gPTInstance; 
	public ApplicationLogin(WebDriver driver) {
		super(driver);
		gPTInstance = new GlobalPageTemplate(driver);
	}
	
	public void login(String loginname, String loginpwd, WebDriver driver) throws Exception {
		launchURL(driver);
		CommonUtils.hold(5);
		CommonUtils.explicitWait(username, "Click", "",driver);
		username.sendKeys(loginname);
		CommonUtils.explicitWait(password, "Click", "",driver);
		password.sendKeys(loginpwd);
		CommonUtils.explicitWait(signIn, "Click", "",driver);
		signIn.click();
		CommonUtils.waitForPageLoad(driver);
		/*
		 * 30789269 till fix is available don't use gPTInstance.verifyGlobalPageTemplate(driver);
		 *  use CommonUtils.hold()
		 */
	//	gPTInstance.verifyGlobalPageTemplate(driver);
	/*	CommonUtils.hold(10);
		
		try {
			System.out.println("Verifying for NewsFeed PO");
			if(newsFeedEnabled == false && newsFeed.isEnabled()) {
				System.out.println("NewsFeed not Enabled "+newsFeedEnabled);
					newsFeedEnabled = true;
					System.out.println("NewsFeed Enabled");
				}
			System.out.println("Verifying for NewsFeed PO ends");
		}catch(Exception iNFEE) {
			System.out.println("Exception in isNewsFeedEnabled() ");
			iNFEE.printStackTrace();
		}
		 */
	}

	
	public void launchURL(WebDriver driver) {

		try {
			driver.get(GetDriverInstance.EnvironmentName);
			CommonUtils.explicitWait(username, "Visible", "", driver);
		} catch (Exception e) {
			System.out.println("Exception in launchURL() ");
			e.printStackTrace();
		}

	}
	
	public void loginem(String loginname, String loginpwd,WebDriver driver) throws Exception {
		launchEMURL(driver);
		Thread.sleep(10000);
		username.clear();
		username.sendKeys(loginname);
		password.clear();
		password.sendKeys(loginpwd);
		signIn.click();
	}

	public void launchEMURL(WebDriver driver) {

		driver.get(GetDriverInstance.EMName);

	}
	
	 public void verifyLoginPage(WebDriver driver) {
		  WebDriverWait wait = new WebDriverWait(driver,30);
		  wait.until(ExpectedConditions.visibilityOf(username));
		  wait.until(ExpectedConditions.visibilityOf(password));
		  wait.until(ExpectedConditions.elementToBeClickable(signIn));
		  Assert.assertTrue(username.isDisplayed() && password.isDisplayed() && signIn.isDisplayed(), "Login Page Not loaded");
	  }

	 public void logout(WebDriver driver) {
		try {
				CommonUtils.hold(5);
				CommonUtils.explicitWait(userLink, "Click", "",driver);
				userLink.click();
				CommonUtils.explicitWait(logOut, "Click", "",driver);
				CommonUtils.hold(5);
				logOut.click();
				CommonUtils.hold(5);
				CommonUtils.explicitWait(confirmButton, "Click", "",driver);
				confirmButton.click();
				verifyLoginPage(driver);
			//	DriverInstance.getDriverInstance().manage().deleteAllCookies();
				CommonUtils.hold(5);
//				throw new Exception();
			} catch (Exception e) {
				try{
					System.out.println("Inside catch block to fix log out issue");
					launchURL(driver);
					CommonUtils.hold(5);
					CommonUtils.explicitWait(userLink, "Click", "",driver);
					userLink.click();
					CommonUtils.explicitWait(logOut, "Click", "",driver);
					CommonUtils.hold(5);
					logOut.click();
					CommonUtils.hold(5);
					CommonUtils.explicitWait(confirmButton, "Click", "",driver);
					confirmButton.click();
					verifyLoginPage(driver);
				//	DriverInstance.getDriverInstance().manage().deleteAllCookies();
					CommonUtils.hold(5);
				}catch (Exception e1){
					System.out.println("Inside Catch block after refreshing URL");
					e1.printStackTrace();
				}
			}
		}
	 
	 public GlobalPageTemplate getGloablePageTemplateInstance() {
		 return gPTInstance; 
	 }
	 
}
