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

public class ManageProfileCategories extends GetDriverInstance{
 
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
			
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Profile Categories", pCateDriver);
			CommonUtils.hold(8);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageProfileCategories class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Create Profile Category")
	public void testcase01() throws Exception {
		prfCateUtil.createProfileCategory("QA" + pid,pCateDriver);
	}

	@Test(dependsOnMethods = "testcase01", description = "Search profile category")
	public void testcase02() throws Exception {
		prfCateUtil.verifyStatus1("QA" + pid, "",pCateDriver);
	}

	@Test(description = "Update profile category", dependsOnMethods = "testcase02")
	public void testcase03() throws Exception {
		prfCateUtil.updateCategory("QA" + pid, "Update" + pid,pCateDriver);
		prfCateUtil.verifyStatus1("QA" + pid, "Update" + pid,pCateDriver);
	}

	@Test(description = "Add profile category option", dependsOnMethods = {"testcase03"})
	public void testcase04() throws Exception {
		prfCateUtil.addProfileOption("QA" + pid, "1", "Enable Artifacts Preloading",pCateDriver); // QA1901100954
		prfCateUtil.verifyStatus1("QA" + pid, "Enable Artifacts Preloading",pCateDriver);

	}

	@Test(description = "Delete profile category option", dependsOnMethods = "testcase04")
	public void testcase05() throws Exception {
		prfCateUtil.deleteProfileOptions("QA" + pid, "Enable Artifacts Preloading",pCateDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		pCateDriver.quit();
	}
}
