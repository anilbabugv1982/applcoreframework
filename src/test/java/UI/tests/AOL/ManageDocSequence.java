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
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.DocSeqUtil;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageDocumentSequence;

public class ManageDocSequence extends GetDriverInstance{

	int dsunique = 0;
	private WebDriver docSeqTestDriver=null;
	private ApplicationLogin appLogin=null;
	private NavigationTaskFlows taskFlowsNavigation=null;
	private DocSeqUtil docUtils=null;
	private JavascriptExecutor js=null;
	private ManageDocumentSequence docPage=null;
	

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void startUP(String username, String password) throws Exception {
		try {
			docSeqTestDriver =  getDriverInstanceObject();
			appLogin = new ApplicationLogin(docSeqTestDriver);
			taskFlowsNavigation = new NavigationTaskFlows(docSeqTestDriver);
			docUtils = new DocSeqUtil(docSeqTestDriver);
			js=(JavascriptExecutor)docSeqTestDriver;
			docPage=new ManageDocumentSequence(docSeqTestDriver);
			appLogin.login(username, "Welcome1",docSeqTestDriver);
			dsunique=(int)Math.floor(Math.random()*(999-100+1)+100);
			CommonUtils.explicitWait(taskFlowsNavigation.getGlobalPageInstance().navigatorButton, "Click", "", docSeqTestDriver);
			CommonUtils.hold(2);
			
			if(ApplicationLogin.newsFeedEnabled) {
				taskFlowsNavigation.navigateToTask(appLogin.getGloablePageTemplateInstance().setupandmaintenance, docSeqTestDriver);
			}else {
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
				CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", docSeqTestDriver);
				docPage.setupAndMaintananceElement.click();
				CommonUtils.hold(4);
			}
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Document Sequences",docSeqTestDriver);
			CommonUtils.hold(8);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageDocSequence class:  " + e.getMessage());
			e.printStackTrace();
		}		

	}

	@Test(description = "Create new DocumentSequence")
	public void testcase01() throws Exception {
		docUtils.createDocumentSequence("DOC" + dsunique,docSeqTestDriver);
		docUtils.verifyStatus1("DOC" + dsunique, "",docSeqTestDriver);

	}

	@Test(description = "Create new DocumentSequence Assignments", dependsOnMethods = { "testcase01" })
	public void testcase02() throws Exception {
		docUtils.createDocSeqAssignment("DOC" + dsunique, "ACH",docSeqTestDriver);// INVOICE-OKS
		docUtils.verifyStatus1("DOC" + dsunique, "ACH",docSeqTestDriver);
	}

	@Test(description = "Delete DocSeq Assignment", dependsOnMethods = { "testcase02" })
	public void testcase03() throws Exception {
		docUtils.deleteDocSeqAssignment("DOC" + dsunique, "ACH",docSeqTestDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		docSeqTestDriver.quit();
	}

}
