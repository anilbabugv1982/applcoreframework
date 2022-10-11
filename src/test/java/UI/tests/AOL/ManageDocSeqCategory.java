package UI.tests.AOL;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.DocSeqUtil;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageDocumentSequence;

import org.openqa.selenium.JavascriptExecutor;

public class ManageDocSeqCategory  extends GetDriverInstance {
	String dsunique = "";
	private WebDriver docTestDriver=null;
	private ApplicationLogin appLogin=null;
	private NavigationTaskFlows taskFlowsNavigation=null;
	private DocSeqUtil docUtils=null;
	private JavascriptExecutor js=null;
	private ManageDocumentSequence docPage=null;
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void startUP(String username, String password) throws Exception {

		try {
			docTestDriver = getDriverInstanceObject();
			appLogin = new ApplicationLogin(docTestDriver);
			taskFlowsNavigation = new NavigationTaskFlows(docTestDriver);
			docUtils = new DocSeqUtil(docTestDriver);
			js=(JavascriptExecutor)docTestDriver;
			docPage=new ManageDocumentSequence(docTestDriver);
			appLogin.login(username, "Welcome1",docTestDriver);
			dsunique = CommonUtils.uniqueId();
			CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", docTestDriver);
			CommonUtils.hold(2);
			
			if(ApplicationLogin.newsFeedEnabled) {
				taskFlowsNavigation.navigateToTask(appLogin.getGloablePageTemplateInstance().setupandmaintenance, docTestDriver);
			}else {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", docTestDriver);
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Document Sequence Categories",docTestDriver);
			CommonUtils.hold(8);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageDocSeqCategory class:  " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Test(description = "Create new DocSeqCategory")
	public void testcase01() throws Exception {
		docUtils.createDocSeqCategory("DOCCATE" + dsunique,docTestDriver);
		docUtils.verifystatus2("DOCCATE" + dsunique,docTestDriver);
	}

	@Test(description = "Search DocSeqCategory", dependsOnMethods = { "testcase01" })
	public void testcase02() throws Exception {
		docUtils.searchDocCategory("DOCCATE" + dsunique,docTestDriver);
		docUtils.verifystatus2("DOCCATE" + dsunique,docTestDriver);
	}

	@Test(description = "Update DocSeqCategory", dependsOnMethods = { "testcase01" })
	public void testcase03() throws Exception {
		docUtils.updateDocSeqCategory("DOCCATE" + dsunique, "DOCCATE" + dsunique + "Update",docTestDriver);
		docUtils.verifystatus2("DOCCATE" + dsunique + "Update",docTestDriver);
	}

	@Test(description = "Delete DocSeqCategory", dependsOnMethods = { "testcase01" })
	public void testcase04() throws Exception {
		docUtils.deleteDocSeqCategory("DOCCATE" + dsunique + "Update",docTestDriver);

	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
       docTestDriver.quit();
	}

}
