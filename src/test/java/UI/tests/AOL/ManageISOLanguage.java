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

public class ManageISOLanguage extends GetDriverInstance {

	private String id;
	private WebDriver isoDriver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ISOUtil isoUtils = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		try {
			isoDriver = getDriverInstanceObject();
			appLogin = new ApplicationLogin(isoDriver);
			taskFlowsNavigation = new NavigationTaskFlows(isoDriver);
			isoUtils = new ISOUtil(isoDriver);
			js = (JavascriptExecutor) isoDriver;
			docPage = new ManageDocumentSequence(isoDriver);
			id = CommonUtils.uniqueId();
			appLogin.login(user, passWord, isoDriver);
			CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", isoDriver);
			CommonUtils.hold(2);
			
			if(ApplicationLogin.newsFeedEnabled) {
				taskFlowsNavigation.navigateToTask(appLogin.getGloablePageTemplateInstance().setupandmaintenance, isoDriver);
			}else {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.hold(3);
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", isoDriver);
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}

			taskFlowsNavigation.navigateToAOLTaskFlows("Manage ISO Languages", isoDriver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(isoDriver.findElement(By.xpath("//h1[contains(text(),'Manage ISO Languages')]")),"Click", "", isoDriver);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageISOLanguage class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Create new ISO Language")
	public void testcase01() throws Exception {
		try {
			isoUtils.createISO("ISO" + id, isoDriver);
		} catch (Exception ex) {
			System.out.println("Exception while creating ISO " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Test(dependsOnMethods = { "testcase01" }, description = "Update ISO Language")
	public void testcase02() throws Exception {

		try {
			isoUtils.updateISO("ISO" + id, "UPDATE", isoDriver);
		} catch (Exception ex) {
			System.out.println("Exception while updating ISO " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Test(dependsOnMethods = { "testcase01" }, description = "Delete a ISO language")
	public void testcase03() throws Exception {

		try {
			isoUtils.deleteISO("UPDATE", isoDriver);
		} catch (Exception ex) {
			System.out.println("Exception while deleting ISO " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		isoDriver.quit();
	}
}
