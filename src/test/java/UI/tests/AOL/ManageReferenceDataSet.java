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

public class ManageReferenceDataSet extends GetDriverInstance {

	private String id;
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
			currencyDriver = getDriverInstanceObject();
			appLogin = new ApplicationLogin(currencyDriver);
			taskFlowsNavigation = new NavigationTaskFlows(currencyDriver);
			isoUtils = new ReferenceDataSetUtil(currencyDriver);
			js = (JavascriptExecutor) currencyDriver;
			docPage = new ManageDocumentSequence(currencyDriver);
			id = CommonUtils.uniqueId();
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
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Reference Data Sets", currencyDriver);		
			CommonUtils.explicitWait(currencyDriver.findElement(By.xpath("//h1[contains(text(),'Manage Reference Data Sets')]")),"Click", "",currencyDriver);
			CommonUtils.hold(3);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageReferenceDataSet class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Create Reference DataSet")
	public void testcase01() throws Exception {
		isoUtils.createReferenceDataSet("SET_" + id,currencyDriver);
	}

	@Test(description = "Update Reference DataSet", dependsOnMethods = { "testcase01" })
	public void testcase02() throws Exception {
		isoUtils.updateReferenceDataSet("SET_" + id, "Update",currencyDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		currencyDriver.quit();
	}
}
