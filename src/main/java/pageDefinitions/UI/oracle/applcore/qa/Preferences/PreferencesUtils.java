package pageDefinitions.UI.oracle.applcore.qa.Preferences;

import java.text.SimpleDateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageDocumentSequence;
import pageDefinitions.UI.oracle.applcore.qa.Preferences.PreferencesPage;
import org.openqa.selenium.JavascriptExecutor;


public class PreferencesUtils extends PreferencesPage {
    
	private WebDriver driver=null;
	private  boolean defaultStatus;
	private  String[] dateValue;
	private  String[] dateFormate;
	private JavascriptExecutor js=null;
	private ManageDocumentSequence gTemplate=null;
	private NavigationTaskFlows navToAOL=null;
	private ApplicationLogin login=null;
	private GlobalPageTemplate gsNav=null;
	private ManageDocumentSequence docPage = null;
	
	private  String adminnewTerritory = "Switzerland";
	private  String adminnewTimeZone = "(UTC-07:00) Denver - Mountain Time (MT)";
	private String adminnewNumberFormat = "-1'234.567";
	private String adminnewTimeFormat = "HH:mm";
	private String adminnewDateFormat = "dd.MM.yy";
	private String adminnewCurrency = "Swiss Franc";
	
	public PreferencesUtils(WebDriver prefUtilsDriver) {
		 super(prefUtilsDriver);
		 js=(JavascriptExecutor)prefUtilsDriver;
		 gTemplate= new ManageDocumentSequence(prefUtilsDriver);
		 navToAOL= new NavigationTaskFlows(prefUtilsDriver);
		 login= new ApplicationLogin(prefUtilsDriver);
		 gsNav= new GlobalPageTemplate(prefUtilsDriver);
		 docPage= new ManageDocumentSequence(prefUtilsDriver);
		 
	}

	public void navigatetoPreferences(WebDriver navigationDriver) throws Exception {
		try{
			CommonUtils.hold(5);
			CommonUtils.explicitWait(userLink, "Click", "",navigationDriver);
//			navigationDriver.findElement(By.xpath("//*[contains(@id,'pt1:_UIScmil1u::icon')]")).click();
			userLink.click();
			CommonUtils.explicitWait(setPreferenceLink, "Click", "",navigationDriver);
			CommonUtils.hold(2);
			setPreferenceLink.click();
			CommonUtils.hold(3);
		}catch(Exception navigationException){
			System.out.println("Error while navigating to preference page : "+navigationException);
			navigationException.printStackTrace();
		}

	}

	public void setRegionalPreference(String territory, String timeZone, WebDriver setRegionalPreference) throws Exception {
		navigatetoPreferences(setRegionalPreference);
		CommonUtils.explicitWait(regionalLink, "Click", "",setRegionalPreference);
		CommonUtils.hold(5);
		regionalLink.click();
		CommonUtils.explicitWait(regionalPageTitle, "Click", "",setRegionalPreference);
		CommonUtils.explicitWait(territoryDropDown, "Click", "",setRegionalPreference);
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(territoryDropDown, territory);
		CommonUtils.hold(3);
		CommonUtils.selectDropDownElement(timezoneDropDown, timeZone);
		CommonUtils.hold(3);
		saveBtn.click();
		CommonUtils.hold(4);
		CommonUtils.explicitWait(setRegionalPreference.findElement(By.xpath("//span[contains(text(),'Last Saved')]")),"Visible", "",setRegionalPreference);
		CommonUtils.hold(5);
		// dateValue=driver.findElement(By.xpath("//span[contains(@id,'MAnt2:1:AP1:ot1')]")).getText();
	}

	public void verfyRegionalPreference(String user, WebDriver verifRegioanlDriver) throws Exception {
		navigatetoPreferences(verifRegioanlDriver);
		CommonUtils.explicitWait(regionalLink, "Click", "",verifRegioanlDriver);
		CommonUtils.hold(3);
		regionalLink.click();
		CommonUtils.explicitWait(saveBtn, "Click", "",verifRegioanlDriver);
		CommonUtils.hold(3);
		saveBtn.click();
		CommonUtils.hold(4);
		CommonUtils.explicitWait(verifRegioanlDriver.findElement(By.xpath("//span[contains(text(),'Last Saved')]")),	"Visible", "",verifRegioanlDriver);
		CommonUtils.hold(3);
		dateValue = verifRegioanlDriver.findElement(By.xpath("//span[contains(@id,'AP1:ot1')]")).getText().split(" ");
		dateFormate = new Select(dateDropDown).getFirstSelectedOption().getText().split(" ");
		System.out.println("Date Formate " + dateFormate[0] + " " + dateValue[0]);
		SimpleDateFormat sd = new SimpleDateFormat(dateFormate[0]);
		sd.setLenient(false);
		try {
			sd.parse(dateValue[0]);
			// sd.parse("2017/01/01");
			System.out.println("passed");
		} catch (Exception e) {
			System.out.println("Failed");
			Assert.assertFalse(false, "Incorrect date format");
		}
	}

	public void setLanguage(String language, String level,WebDriver setLangDriver) throws Exception {
		navigatetoPreferences(setLangDriver);
		CommonUtils.explicitWait(languageLink, "Click", "",setLangDriver);
		languageLink.click();
		CommonUtils.explicitWait(languagePageTitle, "Click", "",setLangDriver);
		CommonUtils.hold(7);

		if (level.equalsIgnoreCase("current")) {
			CommonUtils.selectDropDownElement(currentDropDown, language);
			CommonUtils.hold(4);
		} else {
			CommonUtils.selectDropDownElement(defaultDropDown, language);
			CommonUtils.hold(4);
		}
		
		saveBtn.click();
		CommonUtils.hold(8);

		try {			
			CommonUtils.explicitWait(setLangDriver.findElement(By.xpath("//label[text()='기본값']")),"Visible", "",setLangDriver);
		} catch (Exception e) {
			System.out.println("Korean language title : " + false);
		}

	}

	public void selectValue(WebElement sele, String index) throws Exception {
		Select s = new Select(sele);
		s.selectByValue(index);
	}

	public void launchNewSession(String user, String password) throws Exception {
		System.out.println("Launching new firefox browser session ..... ");
		driver = new FirefoxDriver();
		CommonUtils.hold(8);
		driver.get(GetDriverInstance.EnvironmentName);
		CommonUtils.explicitWait(driver.findElement(By.id("userid")), "Click", "", driver);
		CommonUtils.hold(5);
		driver.findElement(By.id("userid")).sendKeys(user);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.id("btnActive")).click();
		CommonUtils.explicitWait(driver.findElement(By.xpath("//a[contains(@id,'_UIScmil')]")), "Click", "", driver);
		CommonUtils.hold(15);
		driver.findElement(By.xpath("//a[contains(@id,'_UIScmil')]")).click();
		CommonUtils.hold(8);
	}


	public void launchNewChromeSession(String user, String password) throws Exception {
		System.out.println("Launching new Chrome browser session ..... ");
		driver = new ChromeDriver();
		CommonUtils.hold(8);
		driver.get(GetDriverInstance.EnvironmentName);
		CommonUtils.explicitWait(driver.findElement(By.id("userid")), "Click", "", driver);
		CommonUtils.hold(5);
		driver.findElement(By.id("userid")).sendKeys(user);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.id("btnActive")).click();
		CommonUtils.explicitWait(driver.findElement(By.xpath("//a[contains(@id,'_UIScmil')]")), "Click", "", driver);
		CommonUtils.hold(15);
		driver.findElement(By.xpath("//a[contains(@id,'_UIScmil')]")).click();
		CommonUtils.hold(8);
	}

	public void verifyLanguageSettings(String level, String user) throws Exception {


		if(GetDriverInstance.browserName.equalsIgnoreCase("firefox")){
			launchNewSession(user, "Welcome1");
		}else{
			launchNewChromeSession(user, "Welcome1");
		}

		CommonUtils.hold(4);
		if (level.equalsIgnoreCase("current")) {

			try {
				driver.findElement(By.partialLinkText("Set Preferences")).isDisplayed();
				defaultStatus = true;
			} catch (Exception e) {
				defaultStatus = false;
				System.out.println("Current Session language setting error : " + e.getMessage());
			} finally {
				driver.quit();
			}
			Assert.assertTrue(defaultStatus);	
			selectValue(currentDropDown, "0");
			CommonUtils.hold(4);
			saveBtn.click();
			CommonUtils.hold(10);

		} else {
			try {
				driver.findElement(By.xpath("//a[contains(text(),'가변 필드 강조 표시')]")).isDisplayed();
				defaultStatus = true;
			} catch (Exception e) {
				defaultStatus = false;
				System.out.println("Default Session language setting error : " + e.getMessage());
			} finally {
				driver.quit();
			}
			Assert.assertTrue(defaultStatus);
			System.out.println("About to set the default value");
			selectValue(defaultDropDown, "0");
			CommonUtils.hold(4);
			saveBtn.click();
			CommonUtils.hold(10);
		}
		defaultStatus = false;
	}

	public void setScreenReaderMode(WebDriver setScreenDrvier) throws Exception {
		try {
			navigatetoPreferences(setScreenDrvier);
			CommonUtils.explicitWait(accessibilityLink, "Click", "",setScreenDrvier);
			accessibilityLink.click();
			CommonUtils.explicitWait(accessibilityPageTitle, "Visible", "",setScreenDrvier);
			CommonUtils.hold(2);
			screenRdrModeBtn.click();
			CommonUtils.hold(3);
			saveBtn.click();
			CommonUtils.hold(10);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
   public void verifyScreenReaderMode(String user) throws Exception{
	   if(GetDriverInstance.browserName.equalsIgnoreCase("firefox")){
		   launchNewSession(user, "Welcome1");
	   }else{
		   launchNewChromeSession(user, "Welcome1");
	   }
	   CommonUtils.hold(4);
	   try{
		   CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(text(),'Press escape to exit this popup.')]")), "Click", "",driver);		   
		   Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Press escape to exit this popup.')]")).isDisplayed());
		   
	   }catch(Exception e){
		   System.out.println("Screen reader mode test failed "+e.getMessage());		  
	   }finally {
			driver.quit();
			defaultModeBtn.click();
			CommonUtils.hold(4);
			saveBtn.click();
			CommonUtils.hold(10);
		}
	   
   }

//	public void verifyregionalPreference(String territory, String timeZone) throws Exception {
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.hold(3);
//		CommonUtils.navigateToAOLTaskFlows("Manage Administrator Profile Values");
//	}

	public void resetPassword(String currentPassword,String newPassword,WebDriver resetPassDriver) throws Exception {
		navigatetoPreferences(resetPassDriver);
		CommonUtils.explicitWait(passwordLink, "Click", "",resetPassDriver);
		CommonUtils.hold(4);
		passwordLink.click();
		CommonUtils.explicitWait(newPassword1, "Click", "",resetPassDriver);
		CommonUtils.hold(15);
		boolean status=false;
		try{
			status=resetPassDriver.findElement(By.xpath("//input[contains(@id,'currentPwdIp::content')]")).isDisplayed();
		}catch(Exception e){
			System.out.println("No Current password field present : "+e.getMessage());			
		}
		if(status) {
			resetPassDriver.findElement(By.xpath("//input[contains(@id,'currentPwdIp::content')]")).sendKeys(currentPassword);
			CommonUtils.hold(2);
		}

		newPassword1.sendKeys(newPassword);
		confirmPassword.sendKeys(newPassword);
		JavascriptExecutor js1= (JavascriptExecutor)resetPassDriver;
		js1.executeScript("arguments[0].click();", passwordSaveBtn);
//		passwordSaveBtn.click();
		CommonUtils.explicitWait(passwordLink, "Click", "",resetPassDriver);
		CommonUtils.hold(10);
	}

	public void verifyResetPassword(String user, String password) throws Exception {
		if(GetDriverInstance.browserName.equalsIgnoreCase("firefox")){
			launchNewSession(user, "Welcome2");
		}else{
			launchNewChromeSession(user, "Welcome2");
		}
		CommonUtils.hold(4);
		try{			
			driver.findElement(By.xpath("//*[contains(@id,'pt1:_UIScmil1u::icon')]"));
			defaultStatus = true;
		} catch (Exception e) {
			defaultStatus = false;
		} finally {
			driver.quit();
		}
		Assert.assertTrue(defaultStatus);
	}

	public void setAdminPreference(String territory, String timeZone,WebDriver setAdminPrefDriver) throws Exception {
		setAdminPrefDriver.get(GetDriverInstance.EnvironmentName);
		CommonUtils.hold(5);
		
		if(ApplicationLogin.newsFeedEnabled) {
			navToAOL.navigateToTask(login.getGloablePageTemplateInstance().setupandmaintenance, setAdminPrefDriver);
		}else {
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", setAdminPrefDriver);
			docPage.setupAndMaintananceElement.click();
			CommonUtils.hold(4);
		}
		
		navToAOL.navigateToAOLTaskFlows("Set User General Preferences",setAdminPrefDriver);
		CommonUtils.hold(5);
		CommonUtils.explicitWait(setAdminPrefDriver.findElement(By.xpath("//label[text()='Territory']")),"Click", "",setAdminPrefDriver);
		CommonUtils.selectDropDownElement(adminTerritoryDD, territory);
		CommonUtils.hold(8);
		CommonUtils.selectDropDownElement(adminTimeZoneDD, timeZone);
		CommonUtils.hold(5);
		saveBtn.click();
		while (!saveBtn.isEnabled()) {
			CommonUtils.hold(2);
		}
		CommonUtils.explicitWait(setAdminPrefDriver.findElement(By.xpath("//span[contains(text(),'Last Saved')]")),	CommonUtils.ExplicitWaitCalls.Visible.toString(), "",setAdminPrefDriver);
		CommonUtils.hold(4);
	}

	public void verifyAdminPreference(String user,WebDriver verifyAdminDriver) throws Exception {
		CommonUtils.hold(5);
		userLink.click();
		CommonUtils.hold(5);
		navToAOL.logOut.click();
		CommonUtils.hold(5);
		navToAOL.confirmButton.click();
		CommonUtils.hold(5);		
		login.login(user, "Welcome1",verifyAdminDriver);
		CommonUtils.explicitWait(gsNav.navigatorButton, "Click", "",verifyAdminDriver);
		CommonUtils.hold(8);
		navigatetoPreferences(verifyAdminDriver);
		CommonUtils.explicitWait(regionalLink, "Click", "",verifyAdminDriver);
		regionalLink.click();
		CommonUtils.explicitWait(regionalPageTitle, "Click", "",verifyAdminDriver);
		CommonUtils.hold(3);

		try {
			Assert.assertTrue(getUservalues(userTerritoryDD).equals(adminnewTerritory));
			Assert.assertTrue(getUservalues(userDateFormatDD).contains(adminnewDateFormat));
			Assert.assertTrue(getUservalues(userTimeFormatDD).contains(adminnewTimeFormat));
			Assert.assertTrue(getUservalues(userNumberFormatDD).equals(adminnewNumberFormat));
			Assert.assertTrue(getUservalues(userCurrencyFormatDD).equals(adminnewCurrency));
			Assert.assertTrue(getUservalues(userTerritoryDD).equals(adminnewTerritory));
		} catch (Exception e) {
			System.out.println("Admin preference verifaction failed. "+e.getMessage());
			e.printStackTrace();
		}

	}

	public String getUservalues(WebElement selectEle) throws Exception {
		Select s = new Select(selectEle);
		System.out.println(s.getFirstSelectedOption().getText());
		return s.getFirstSelectedOption().getText();
	}
}
