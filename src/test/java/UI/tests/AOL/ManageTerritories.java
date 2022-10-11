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

public class ManageTerritories extends GetDriverInstance{
	private String id;
	private WebDriver territoryDriver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ISOUtil isoUtils = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		try {
			territoryDriver = getDriverInstanceObject();
			appLogin = new ApplicationLogin(territoryDriver);
			taskFlowsNavigation = new NavigationTaskFlows(territoryDriver);
			isoUtils = new ISOUtil(territoryDriver);
			js = (JavascriptExecutor) territoryDriver;
			docPage = new ManageDocumentSequence(territoryDriver);
			id = CommonUtils.uniqueId();
			appLogin.login(user, passWord,territoryDriver);
			CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", territoryDriver);
			CommonUtils.hold(2);
			
			if(ApplicationLogin.newsFeedEnabled) {
				taskFlowsNavigation.navigateToTask(appLogin.getGloablePageTemplateInstance().setupandmaintenance, territoryDriver);
			}else {
				CommonUtils.hold(3);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", territoryDriver);
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Territories", territoryDriver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(territoryDriver.findElement(By.xpath("//h1[contains(.,'Manage Territories')]")),"Click", "",territoryDriver);
			CommonUtils.hold(4);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageTerritories class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Create new territory")
	public void testcase01() throws Exception {
		isoUtils.createTerritory("TT" + id,territoryDriver);
	}

	@Test(dependsOnMethods = { "testcase01" }, description = "Update territory")
	public void testcase02() throws Exception {
		isoUtils.updateTerritory("TT" + id, "UPDATE",territoryDriver);
	}

	@Test(dependsOnMethods = { "testcase02" }, description = "Delete territory")
	public void testcase03() throws Exception {
		isoUtils.deleteTerritory("UPDATE",territoryDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		territoryDriver.quit();
	}
}
