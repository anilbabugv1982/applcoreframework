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
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageDocumentSequence;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ReferenceDataSetUtil;

public class ManageSetAssignments extends GetDriverInstance{

	private WebDriver currencyDriver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ReferenceDataSetUtil isoUtils = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;
	

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		try {
			currencyDriver =  getDriverInstanceObject();
			appLogin = new ApplicationLogin(currencyDriver);
			taskFlowsNavigation = new NavigationTaskFlows(currencyDriver);
			isoUtils = new ReferenceDataSetUtil(currencyDriver);
			js = (JavascriptExecutor) currencyDriver;
			docPage = new ManageDocumentSequence(currencyDriver);
			appLogin.login(user, passWord, currencyDriver);
			CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", currencyDriver);
			CommonUtils.hold(2);
			
			if(ApplicationLogin.newsFeedEnabled) {
				taskFlowsNavigation.navigateToTask(appLogin.getGloablePageTemplateInstance().setupandmaintenance, currencyDriver);
			}else {
				CommonUtils.hold(3);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", currencyDriver);
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Set Assignments for Set Determinant Type", currencyDriver);	
			CommonUtils.explicitWait(currencyDriver.findElement(By.xpath("//h1[contains(text(),'Manage Set Assignments for Set Determinant Type')]")),"Click", "",currencyDriver);
			CommonUtils.hold(3);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageSetAssignments class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Create reference data set assignment")
	public void testcase01() throws Exception {
		isoUtils.createSetAssignments("Close Reasons", "Vision", "84Panzanelli SET", currencyDriver);

	}

	@Test(dependsOnMethods = { "testcase01" }, description = "Update reference Data set Assignment")
	public void testcase02() throws Exception {
		isoUtils.updateAssignments("84Panzanelli SET", "Asia Set", currencyDriver);
	}

	@Test(dependsOnMethods = { "testcase02" }, description = "Delete reference data set assignments")
	public void testcase03() throws Exception {
		isoUtils.deleteAssignments("Asia Set", currencyDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		currencyDriver.quit();
	}

}
