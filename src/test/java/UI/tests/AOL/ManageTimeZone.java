package UI.tests.AOL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ISOUtil;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageDocumentSequence;

public class ManageTimeZone extends GetDriverInstance {
	private String id;
	private WebDriver timeDriver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ISOUtil isoUtils = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		try {
			timeDriver = getDriverInstanceObject();
			appLogin = new ApplicationLogin(timeDriver);
			taskFlowsNavigation = new NavigationTaskFlows(timeDriver);
			isoUtils = new ISOUtil(timeDriver);
			js = (JavascriptExecutor) timeDriver;
			docPage = new ManageDocumentSequence(timeDriver);
			id = CommonUtils.uniqueId();
			appLogin.login(user, passWord, timeDriver);
			CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", timeDriver);
			CommonUtils.hold(2);
			
			if(ApplicationLogin.newsFeedEnabled) {
				taskFlowsNavigation.navigateToTask(appLogin.getGloablePageTemplateInstance().setupandmaintenance, timeDriver);
			}else {
				CommonUtils.hold(3);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", timeDriver);
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Time Zones", timeDriver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(timeDriver.findElement(By.xpath("//h1[contains(.,'Manage Timezones')]")), "Click", "",	timeDriver);
			CommonUtils.hold(4);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageTimeZone class:  " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Test(description = "Create Time Zone")
	public void testcase01() throws Exception {
		isoUtils.createTimeZone("TZ" + id, timeDriver);
	}

	@Test(dependsOnMethods = { "testcase01" }, description = "Update Time Zone")
	public void testcase02() throws Exception {
		isoUtils.updateTimeZone("TZ" + id, "UPDATE", timeDriver);
	}
	@Test(dependsOnMethods = { "testcase02" }, description = "Delete time zone")
	public void testcase03() throws Exception {
		isoUtils.deleteTimeZone("UPDATE", timeDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {
		timeDriver.quit();
	}
}