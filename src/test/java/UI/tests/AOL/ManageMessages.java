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
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageMessagesUtils;

public class ManageMessages extends GetDriverInstance {

	private String uniqueID = null;
	private WebDriver isoDriver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ManageMessagesUtils isoUtils = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;
	

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void startUP(String username, String password) throws Exception {
		try {
			isoDriver =  getDriverInstanceObject();
			appLogin = new ApplicationLogin(isoDriver);
			taskFlowsNavigation = new NavigationTaskFlows(isoDriver);
			isoUtils = new ManageMessagesUtils(isoDriver);
			js = (JavascriptExecutor) isoDriver;
			docPage = new ManageDocumentSequence(isoDriver);
			uniqueID  = CommonUtils.uniqueId();
			appLogin.login(username, password, isoDriver);
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
			taskFlowsNavigation.navigateToAOLTaskFlows("Manage Messages", isoDriver);
			CommonUtils.hold(6);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in ManageMessages class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Create New Message")
	public void testcase01() throws Exception {
		isoUtils.createMessage("MSG" + uniqueID, "MSG_TEXT" + uniqueID,isoDriver);
		isoUtils.verifyStatus("MSG" + uniqueID, "", "", "",isoDriver);
	}

	@Test(description = "Create New MessageToken", dependsOnMethods = { "testcase01" })
	public void testcase02() throws Exception {
		isoUtils.createMessageToken("MSG" + uniqueID, "MSG_TOKEN" + uniqueID, "TOKEN_DESC" + uniqueID,isoDriver);
		isoUtils.verifyStatus("MSG" + uniqueID, "", "MSG_TOKEN" + uniqueID, "",isoDriver);
	}

	@Test(description = "Update New Message", dependsOnMethods = { "testcase02" })
	public void testcase03() throws Exception {
		isoUtils.updateMessage("MSG" + uniqueID, "MSG_TEXT_DESC" + uniqueID,isoDriver);
		isoUtils.verifyStatus("MSG" + uniqueID, "MSG_TEXT_DESC" + uniqueID, "", "",isoDriver);
	}

	@Test(description = "update New Message Token", dependsOnMethods = { "testcase03" })
	public void testcase04() throws Exception {
		isoUtils.UpdateToken("MSG" + uniqueID, "TOKEN_DESC_UPDA" + uniqueID,isoDriver);
		isoUtils.verifyStatus("MSG" + uniqueID, "", "", "TOKEN_DESC_UPDA" + uniqueID,isoDriver);
	}

	@Test(description = "delete New MsgToken", dependsOnMethods = { "testcase04" })
	public void testcase05() throws Exception {
		isoUtils.deleteToken("MSG" + uniqueID, "MSG_TOKEN" + uniqueID,isoDriver);
		isoUtils.verifyDeleteToken("MSG" + uniqueID, "MSG_TOKEN" + uniqueID,isoDriver);
	}

	@Test(description = "delete New Message", dependsOnMethods = { "testcase05" })
	public void testcase06() throws Exception {
		isoUtils.deleteMessage("MSG" + uniqueID,isoDriver);
	}

	@Test(description = "Create message token for existing message", dependsOnMethods = { "testcase06" })
	public void testcase07() throws Exception {
		isoUtils.createMessageToken("ACA_AO_REV_INVALID", "MSG_TOKEN" + uniqueID, "TOKEN_DESC" + uniqueID,isoDriver);
	}

	@Test(description = "Update an existing message", dependsOnMethods = { "testcase07" })
	public void testcase08() throws Exception {
		isoUtils.updateMessage("ACA_AO_REV_INVALID", "MSG_TEXT_DESC" + uniqueID,isoDriver);
	}

	@Test(dependsOnMethods = { "testcase08" }, description = "Update a token for existing message")
	public void testcase09() throws Exception {
		isoUtils.UpdateToken("ACA_AO_REV_INVALID", "TOKEN_DESC_UPDA" + uniqueID,isoDriver);
	}

	@Test(dependsOnMethods = { "testcase09" }, description = "Delete a token for existing message")
	public void testcase10() throws Exception {
		isoUtils.deleteToken("ACA_AO_REV_INVALID", "MSG_TOKEN" + uniqueID,isoDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		isoDriver.quit();
	}
}
