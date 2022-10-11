package UI.tests.FSMExportImport;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.LookupsWrapper;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageDocumentSequence;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageMessagesUtils;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ReferenceDataSetUtil;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.FsmExportImport.FSMExpImpWrapper;

public class FsmExportImport1 extends GetDriverInstance {

	private String id;
	private String ipName;
	private WebDriver fsm1Driver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;
	private LookupsWrapper lookupsUtil = null;
	private ManageMessagesUtils messagesUtil = null;
	private ReferenceDataSetUtil referenceUtil = null;
	private FSMExpImpWrapper fsmUtil = null;

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String password) throws Exception {

		id = CommonUtils.uniqueId();
		try {
			fsm1Driver = getDriverInstanceObject();
			appLogin = new ApplicationLogin(fsm1Driver);
			taskFlowsNavigation = new NavigationTaskFlows(fsm1Driver);
			lookupsUtil = new LookupsWrapper(fsm1Driver);
			messagesUtil = new ManageMessagesUtils(fsm1Driver);
			referenceUtil = new ReferenceDataSetUtil(fsm1Driver);
			fsmUtil = new FSMExpImpWrapper(fsm1Driver);
			js = (JavascriptExecutor) fsm1Driver;
			docPage = new ManageDocumentSequence(fsm1Driver);
			id = CommonUtils.uniqueId();
			appLogin.login(user, password, fsm1Driver);
			//CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", fsm1Driver);
			CommonUtils.hold(5);
		} catch (Exception fsmDriverException) {
			System.out.println("Error while initializing objects in FsmExportImport1 class," + fsmDriverException);
			fsmDriverException.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@BeforeMethod
	public void navigateToHomePage() throws Exception {
			fsm1Driver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome","FuseTaskListManagerTop"));
			CommonUtils.hold(3);
	}

	public void navigateToAll(String taskFlowName) throws Exception {
		navigateToHomePage();
		taskFlowsNavigation.navigateToAOLTaskFlows(taskFlowName, fsm1Driver);
		CommonUtils.hold(4);
	}

	@Test(description = "FSMExportImportCommonLookup")
	public void testcase01() throws Exception {
		ipName = "CMN_LOOKUP" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Common Lookups", fsm1Driver);
		CommonUtils.hold(4);
		lookupsUtil.createLookupType("STD_" + id, "M_" + id, "D_" + id, "standard", fsm1Driver);
		lookupsUtil.createLookupCode("STD_" + id, "CODE_" + id, "CM_" + id, "CD_" + id, "std", fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Common Lookups", fsm1Driver);
		fsmUtil.createConfigurationProject(ipName, fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
		navigateToAll("Manage Common Lookups");
		// navigateToHomePage();
		// CommonUtils.hold(5);
		// taskFlowsNavigation.navigateToAOLTaskFlows("Manage Common
		// Lookups",fsm1Driver);
		lookupsUtil.deleteLookupType("STD_" + id, fsm1Driver);
		CommonUtils.hold(5);
		// fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName, fsm1Driver);
		fsmUtil.importSetupData(ipName, fsm1Driver);
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
		// navigateToHomePage();
		CommonUtils.hold(5);
		// taskFlowsNavigation.navigateToAOLTaskFlows("Manage Common
		// Lookups",fsm1Driver);
		navigateToAll("Manage Common Lookups");
		lookupsUtil.verifyStatus("STD_" + id, "CODE_" + id, "M_" + id, "D_" + id, fsm1Driver);

	}

	@Test(description = "FSMExportImportStandardLookup")
	public void testcase02() throws Exception {
		ipName = "STD_LOOKUP" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Standard Lookups", fsm1Driver);
		lookupsUtil.createLookupType("STD_" + id, "M_" + id, "D_" + id, "standard", fsm1Driver);
		lookupsUtil.createLookupCode("STD_" + id, "CODE_" + id, "CM_" + id, "CD_" + id, "std", fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Standard Lookups", fsm1Driver);
		fsmUtil.createConfigurationProject(ipName, fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
		// CommonUtils.hold(5);
		// CommonUtils.navigateToAOLTaskFlows("Manage Standard Lookups");
		navigateToAll("Manage Standard Lookups");
		lookupsUtil.deleteLookupType("STD_" + id, fsm1Driver);
		CommonUtils.hold(5);
		// fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName, fsm1Driver);
		fsmUtil.importSetupData(ipName, fsm1Driver);
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
		// CommonUtils.hold(5);
		// CommonUtils.navigateToAOLTaskFlows("Manage Standard Lookups");
		navigateToAll("Manage Standard Lookups");
		lookupsUtil.verifyStatus("STD_" + id, "CODE_" + id, "M_" + id, "D_" + id, fsm1Driver);

	}

	@Test(description = "FSMExportImportSetEnableddLookup")
	public void testcase03() throws Exception {
		ipName = "STE_LOOKUP" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Set Enabled Lookups", fsm1Driver);
		lookupsUtil.createLookupType("STD_" + id, "M_" + id, "D_" + id, "set", fsm1Driver);
		lookupsUtil.createLookupCode("STD_" + id, "CODE_" + id, "CM_" + id, "CD_" + id, "set", fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Set Enabled Lookups", fsm1Driver);
		fsmUtil.createConfigurationProject(ipName, fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
		// CommonUtils.hold(5);
		// CommonUtils.navigateToAOLTaskFlows("Manage Set Enabled Lookups");
		navigateToAll("Manage Set Enabled Lookups");
		lookupsUtil.deleteLookupType("STD_" + id, fsm1Driver);
		CommonUtils.hold(5);
		// fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName, fsm1Driver);
		fsmUtil.importSetupData(ipName, fsm1Driver);
		navigateToAll("Manage Set Enabled Lookups");
		lookupsUtil.verifyStatus("STD_" + id, "CODE_" + id, "M_" + id, "D_" + id, fsm1Driver);

	}

	@Test(description = "FSMExportImportReferenceDataSets")
	public void testcase04() throws Exception {
		ipName = "REFDATASETS" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Reference Data Sets", fsm1Driver);
		referenceUtil.createReferenceDataSet("SET_" + id, fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Reference Data Sets", fsm1Driver);
		fsmUtil.createConfigurationProject(ipName, fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
		// CommonUtils.hold(5);
		// CommonUtils.navigateToAOLTaskFlows("Manage Reference Data Sets");
		navigateToAll("Manage Reference Data Sets");
		referenceUtil.updateReferenceDataSet("SET_" + id, "Update", fsm1Driver);
		CommonUtils.hold(5);
		// fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName, fsm1Driver);
		fsmUtil.importSetupData(ipName, fsm1Driver);
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
		// CommonUtils.hold(5);
		// CommonUtils.navigateToAOLTaskFlows("Manage Reference Data Sets");
		navigateToAll("Manage Reference Data Sets");
		referenceUtil.searchReferenceDataSet("SET_" + id, fsm1Driver);
		Assert.assertTrue(
				fsm1Driver.findElement(By.xpath("//input[contains(@id,'it3::content') and @value='SET_" + id + "']"))
						.isDisplayed());

	}

	@Test(description = "FSMExportImportReferenceDataSetsAssignments")
	public void testcase05() throws Exception {
		ipName = "REFDATASSIGNMENTS" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Set Assignments for Set Determinant Type", fsm1Driver);
		CommonUtils.hold(4);
		try {
			System.out.println("in try");
			referenceUtil.deleteAssignments("84Panzanelli SET", fsm1Driver);
		} catch (Exception e) {
			System.out.println("No earlier Set Assignment present.");
		}
		referenceUtil.createSetAssignments("Contract Types", "US locations", "84Panzanelli SET", fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Set Assignments for Set Determinant Type", fsm1Driver);
		fsmUtil.createConfigurationProject(ipName, fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
		// CommonUtils.hold(5);
		// CommonUtils.navigateToAOLTaskFlows("Manage Set Assignments for Set
		// Determinant Type");
		navigateToAll("Manage Set Assignments for Set Determinant Type");
		referenceUtil.deleteAssignments("84Panzanelli SET", fsm1Driver);
		CommonUtils.hold(5);
		// fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName, fsm1Driver);
		fsmUtil.importSetupData(ipName, fsm1Driver);
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
		// CommonUtils.hold(5);
		// CommonUtils.navigateToAOLTaskFlows("Manage Set Assignments for Set
		// Determinant Type");
		navigateToAll("Manage Set Assignments for Set Determinant Type");
		referenceUtil.searchAssignments("84Panzanelli SET", fsm1Driver);
		referenceUtil.deleteAssignments("84Panzanelli SET", fsm1Driver);
	}

	@Test(description = "FSMExportImportMessages")
	public void testcase06() throws Exception {
		ipName = "MSG_" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Messages", fsm1Driver);
		messagesUtil.createMessage("MSG" + id, "MSG_TEXT" + id, fsm1Driver);
		messagesUtil.createMessageToken("MSG" + id, "MSG_TOKEN" + id, "TOKEN_DESC" + id, fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Messages", fsm1Driver);
		fsmUtil.createConfigurationProject(ipName, fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
		// CommonUtils.hold(5);
		// CommonUtils.navigateToAOLTaskFlows("Manage Messages");
		navigateToAll("Manage Messages");
		messagesUtil.deleteMessage("MSG" + id, fsm1Driver);
		CommonUtils.hold(5);
		// fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName, fsm1Driver);
		fsmUtil.importSetupData(ipName, fsm1Driver);
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
		// CommonUtils.hold(5);
		// CommonUtils.navigateToAOLTaskFlows("Manage Messages");
		navigateToAll("Manage Messages");
		messagesUtil.verifyStatus("MSG" + id, "", "", "", fsm1Driver);

	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		fsm1Driver.quit();
	}

}
