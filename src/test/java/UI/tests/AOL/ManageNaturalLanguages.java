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

public class ManageNaturalLanguages  extends GetDriverInstance{
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
			isoDriver =  getDriverInstanceObject();
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
				CommonUtils.hold(3);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", isoDriver);
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Natural Languages", isoDriver);
			CommonUtils.hold(4);
			CommonUtils.explicitWait(isoDriver.findElement(By.xpath("//h1[contains(.,'Manage Natural Languages')]")),"Click", "",isoDriver);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageNaturalLanguages class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Create Natural Language")
	public void testcase01() throws Exception {
		isoUtils.createNaturalLanguage("NL" + id,isoDriver);
	}

	@Test(dependsOnMethods = { "testcase01" }, description = "Update Natural Language")
	public void testcase02() throws Exception {
		isoUtils.updateNaturalLanguage("NL" + id, "UPDATE",isoDriver);
	}

	@Test(dependsOnMethods = { "testcase02" }, description = "Delete Natural Language")
	public void testcase03() throws Exception {
		isoUtils.deleteNaturalLanguage("UPDATE",isoDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		isoDriver.quit();
	}
}
