package UI.tests.AOL;

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
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ProfilesUtils;

/**
 * @author BIRSWAIN
 *
 */

public class ManageProfileOptions extends GetDriverInstance{

	private String pid=null;
	private WebDriver pCateDriver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ProfilesUtils prfCateUtil = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;
	
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String loginname, String loginpwd) throws Exception {
		try {
			pCateDriver =  getDriverInstanceObject();
			appLogin = new ApplicationLogin(pCateDriver);
			taskFlowsNavigation = new NavigationTaskFlows(pCateDriver);
			prfCateUtil = new ProfilesUtils(pCateDriver);
			js = (JavascriptExecutor) pCateDriver;
			docPage = new ManageDocumentSequence(pCateDriver);
			pid = CommonUtils.uniqueId();
			appLogin.login(loginname, loginpwd, pCateDriver);
			CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", pCateDriver);
			CommonUtils.hold(2);
			
			if(ApplicationLogin.newsFeedEnabled) {
				taskFlowsNavigation.navigateToTask(appLogin.getGloablePageTemplateInstance().setupandmaintenance, pCateDriver);
			}else {
				CommonUtils.hold(3);
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", pCateDriver);
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Profile Options", pCateDriver);
			CommonUtils.hold(5);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageProfileOptions class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Create Operation")
	public void testcase01() throws Exception {
		prfCateUtil.createProfileOptions("PO" + pid,pCateDriver);
		// ProfileWrapperClass.verifyStatus("PO" + pid, "");
	}

	@Test(dependsOnMethods = { "testcase01" }, description = "Search profile option")
	public void testcase02() throws Exception {
		prfCateUtil.verifyStatus("PO" + pid, "",pCateDriver);

	}

	@Test(dependsOnMethods = { "testcase02" }, description = "Update profile option")
	public void testcase03() throws Exception {

		prfCateUtil.updateProfileOptions("PO" + pid, "POUPDATE" + pid);
		prfCateUtil.verifyStatus("PO" + pid, "POUPDATE" + pid,pCateDriver);

	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {
		pCateDriver.quit();
	}
}
