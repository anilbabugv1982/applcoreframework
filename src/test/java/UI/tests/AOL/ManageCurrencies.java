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

public class ManageCurrencies extends GetDriverInstance {

	private String id;
	private WebDriver currencyDriver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ISOUtil isoUtils = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		try {
			currencyDriver =  getDriverInstanceObject();
			appLogin = new ApplicationLogin(currencyDriver);
			taskFlowsNavigation = new NavigationTaskFlows(currencyDriver);
			isoUtils = new ISOUtil(currencyDriver);
			js = (JavascriptExecutor) currencyDriver;
			docPage = new ManageDocumentSequence(currencyDriver);
			id = CommonUtils.uniqueId();
			appLogin.login(user, passWord, currencyDriver);
			CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", currencyDriver);
			CommonUtils.hold(4);
			
			if(ApplicationLogin.newsFeedEnabled) {
				taskFlowsNavigation.navigateToTask(appLogin.getGloablePageTemplateInstance().setupandmaintenance, currencyDriver);
			}else {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", currencyDriver);				
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Currencies", currencyDriver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(currencyDriver.findElement(By.xpath("//h1[contains(text(),'Manage Currencies')]")),"Click", "", currencyDriver);
			CommonUtils.hold(3);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageCurrencies class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Create a Currency")
	public void testcase01() throws Exception {
		isoUtils.createCurrency("DP" + id, currencyDriver);
	}

	@Test(description = "Update a Currency", dependsOnMethods = { "testcase01" })
	public void testcase02() throws Exception {
		isoUtils.updateCurrency("DP" + id, "UP" + id, currencyDriver);
	}

	@Test(description = "Delete a Currency", dependsOnMethods = { "testcase02" })
	public void testcase03() throws Exception {
		isoUtils.deleteCurrency("UP" + id, currencyDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		currencyDriver.quit();
	}
}
