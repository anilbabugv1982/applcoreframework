package UI.tests.AboutPage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.AboutPage.AboutPage;

public class AboutPageTest extends GetDriverInstance {

	private WebDriver aboutPageInstanceDriver;
	private ApplicationLogin appLoginInstance;
	private NavigationTaskFlows navTaskFlowInstance;
	private NavigatorMenuElements navMenuEleInstance;
	private GlobalPageTemplate globalPgTempInstance;
	private CommonUtils commonUtilInstance;
	private AboutPage aboutPgInstance;

	private String AdfVersion = "JDEVADF_PT.FAREL13-BP38-2010_GENERIC_200720.2255.7449";
	private String AtgVersion = "ATGPF_11.1.1.9.1_GENERIC_200721.1250.REL13BP38";
	private String DbVersion = "TRUE (REL13BP38)";

	// ***********************************************************Login Application*********************************************************************************
	@Parameters({ "user", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {

		aboutPageInstanceDriver = getDriverInstanceObject();
		aboutPageInstanceDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		appLoginInstance = new ApplicationLogin(aboutPageInstanceDriver);
		globalPgTempInstance = new GlobalPageTemplate(aboutPageInstanceDriver);
		aboutPgInstance = new AboutPage(aboutPageInstanceDriver);
		appLoginInstance.login("app_impl_consultant", "Welcome1", aboutPageInstanceDriver);
		Log.info("Logging into the Application");
		CommonUtils.waitForJStoLoad(aboutPageInstanceDriver);
	}

	@Test(priority = 1)
	public void aboutPageValidations() throws Exception {

		aboutPageInstanceDriver.findElement(By.xpath(".//*[@id='pt1:_UIScmil1u::icon']")).click();
		CommonUtils.hold(5);
		Log.info("Click on dropdown icon next to User initials");
		aboutPageInstanceDriver.findElement(By.xpath(".//a[text()='About This Application']")).click();
		CommonUtils.hold(10);
		Log.info("Click on About this Application link");
		System.out.println("About this Application popup is displayed");
		WebElement adf = aboutPageInstanceDriver.findElement(By.xpath(".//tr/td/label[text()='Oracle Application Development Framework']/../../td/span[@id='pt1:_UISatpr4:0:i1:0:ott22']"));
		JavascriptExecutor js = (JavascriptExecutor)aboutPageInstanceDriver;
		String ADF_Patch = (String) js.executeScript("return arguments[0].text;", adf);
		WebElement atg = aboutPageInstanceDriver.findElement(By.xpath(".//tr/td/label[text()='Oracle Middleware Extensions for Applications']/../../td/span[@id='pt1:_UISatpr4:0:i1:1:ott22']"));
		JavascriptExecutor je = (JavascriptExecutor)aboutPageInstanceDriver;
		String ATG_Patch = (String) js.executeScript("return arguments[0].text;", atg);
		WebElement db = aboutPageInstanceDriver.findElement(By.xpath(".//tr/td/label[text()='Database Compatibility']/../../td/span[@id='pt1:_UISatpr4:0:i1:1:ott23']"));
		JavascriptExecutor ja = (JavascriptExecutor)aboutPageInstanceDriver;
		String DB_Patch = (String) js.executeScript("return arguments[0].text;", db);

		if(AdfVersion.equals(ADF_Patch)){
			System.out.println("The ADF version is displayed correctly.");
		} 
		else {
			System.out.println("The ADF version is not displayed correctly.");
		}

		if(AtgVersion.equals(ATG_Patch)){
			System.out.println("The ATG version is displayed correctly.");
		} 
		else {
			System.out.println("The ATG version is not displayed correctly.");
		}

		if(DbVersion.equals(DB_Patch)){
			System.out.println("The DB version is displayed correctly.");
		} 
		else {
			System.out.println("The DB version is not displayed correctly.");
		}

	}	

	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {

		CommonUtils.hold(2);
		appLoginInstance.logout(aboutPageInstanceDriver);
		CommonUtils.hold(2);
		aboutPageInstanceDriver.quit();
	}

}
