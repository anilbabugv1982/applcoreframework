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

public class ManageIndustries extends GetDriverInstance {
	
	private String id;
	private WebDriver industryDriver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ISOUtil isoUtils = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		try {
			industryDriver =  getDriverInstanceObject();
			appLogin = new ApplicationLogin(industryDriver);
			taskFlowsNavigation = new NavigationTaskFlows(industryDriver);
			isoUtils = new ISOUtil(industryDriver);
			js = (JavascriptExecutor) industryDriver;
			docPage = new ManageDocumentSequence(industryDriver);
			id = CommonUtils.uniqueId();
			appLogin.login(user, passWord,industryDriver);
			CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", industryDriver);
			CommonUtils.hold(2);
			
			if(ApplicationLogin.newsFeedEnabled) {
				taskFlowsNavigation.navigateToTask(appLogin.getGloablePageTemplateInstance().setupandmaintenance, industryDriver);
			}else {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", industryDriver);
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Industries", industryDriver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(industryDriver.findElement(By.xpath("//h1[contains(text(),'Manage Industries')]")),"Click", "",industryDriver);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageIndustriese class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Create new industry")
	public void testcase01() throws Exception {
		isoUtils.createIndustry("NAME" + id,industryDriver);
		isoUtils.searchIndustry("NAME" + id,industryDriver);
	}

	@Test(dependsOnMethods = { "testcase01" }, description = "Create new territory")
	public void testcase02() throws Exception {
		isoUtils.createTerritory("Australia", "NAME" + id,industryDriver);

	}

	@Test(dependsOnMethods = { "testcase02" }, description = "Update newly created industry")
	public void testcase03() throws Exception {
		isoUtils.updateIndustry("NAME" + id, "UP" + id,industryDriver);
	}

	@Test(dependsOnMethods = { "testcase03" }, description = "Delete newly created territory")
	public void testcase04() throws Exception {
		isoUtils.deleteTerritory("UP" + id, "Australia",industryDriver);
	}

	@Test(dependsOnMethods = { "testcase04" }, description = "Delete newly created industry")
	public void testcase05() throws Exception {
		isoUtils.deleteIndustry("UP" + id,industryDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		industryDriver.quit();
	}
}
