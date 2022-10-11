package UI.tests.FSMExportImport;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.DocSeqUtil;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageDocumentSequence;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ProfilesUtils;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.FsmExportImport.FSMExpImpWrapper;

public class FsmExportImport2 extends GetDriverInstance {


	private String id;
	private String ipName;
	private WebDriver fsm1Driver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;
	private DocSeqUtil docUtil = null;
	private ProfilesUtils pUtils = null;
	private FSMExpImpWrapper fsmUtil = null;

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String password) throws Exception {

		id = CommonUtils.uniqueId();
		try {
			fsm1Driver = getDriverInstanceObject();
			appLogin = new ApplicationLogin(fsm1Driver);
			taskFlowsNavigation = new NavigationTaskFlows(fsm1Driver);
			docUtil = new DocSeqUtil(fsm1Driver);
			pUtils = new ProfilesUtils(fsm1Driver);
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

	
	@BeforeMethod
	public void navigateToHomePage() {
			fsm1Driver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome","FuseTaskListManagerTop"));
			CommonUtils.hold(3);
	}

	public void navigateToAll(String taskFlowName) {
		navigateToHomePage();
		taskFlowsNavigation.navigateToAOLTaskFlows(taskFlowName, fsm1Driver);
		CommonUtils.hold(4);
	}

	@Test(description = "FSMExportImportProfileOptions")
	public void testcase01() throws Exception {
		ipName = "PO" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Profile Options",fsm1Driver);
		pUtils.createProfileOptions("PO" + id,fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Profile Options",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Profile Options");
		navigateToAll("Manage Profile Options");
		pUtils.updateProfileOptions("PO" + id, "POUPDATE" + id);
		CommonUtils.hold(5);
		//fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Profile Options");
		navigateToAll("Manage Profile Options");
		pUtils.verifyStatus("PO" + id, "PO" + id,fsm1Driver);
	}

	@Test(description = "FSMExportImportProfileCategories")
	public void testcase02() throws Exception {
		ipName = "PC" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Profile Categories",fsm1Driver);
		pUtils.createProfileCategory("QA" + id,fsm1Driver);
		pUtils.addProfileOption("QA" + id, "1", "Enable Artifacts Preloading",fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Profile Categories",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Profile Categories");
		navigateToAll("Manage Profile Categories");
		pUtils.deleteProfileOptions("QA" + id, "Enable Artifacts Preloading",fsm1Driver);
		CommonUtils.hold(5);
		//fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Profile Categories");
		navigateToAll("Manage Profile Categories");
		pUtils.verifyStatus1("QA" + id, "Enable Artifacts Preloading",fsm1Driver);		
	}

	@Test(description = "FSMExportImportAdminProfile")
	public void testcase03() throws Exception {
		ipName = "AP" + id;
		navigateToAll("Manage Administrator Profile Values");
		try {
			pUtils.deleteAdminProfile("AFLOG_PLSQL_FILENAME","Site", fsm1Driver);
		} catch (Exception e){
			System.out.println("Profile Value is not displayed.");
		}
		pUtils.addProfileOptionValues("AFLOG_PLSQL_FILENAME", "Site", "", "",fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Administrator Profile Values",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Administrator Profile Values");
		navigateToAll("Manage Administrator Profile Values");
		pUtils.deleteAdminProfile("AFLOG_PLSQL_FILENAME","Site",fsm1Driver);
		//fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Administrator Profile Values");
		navigateToAll("Manage Administrator Profile Values");
		CommonUtils.hold(5);
		pUtils.verifyStatus4("AFLOG_PLSQL_FILENAME","Site",fsm1Driver);
//		pUtils.deleteAdminProfile("AFLOG_LEVEL", "IT_SECURITY_MANAGER");
	}
		
	@Test(description = "FSMExportImportDocSeqCategories")
	public void testcase04() throws Exception {
		ipName = "DOCCATE" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Document Sequence Categories",fsm1Driver);
		docUtil.createDocSeqCategory("DOCCATE" + id,fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Document Sequence Categories",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Document Sequence Categories");
		navigateToAll("Manage Document Sequence Categories");
		docUtil.deleteDocSeqCategory("DOCCATE" + id,fsm1Driver);
		CommonUtils.hold(5);
		//fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Document Sequence Categories");
		navigateToAll("Manage Document Sequence Categories");
		docUtil.verifystatus2("DOCCATE" + id,fsm1Driver);
	}
	
	
	@Test(description = "FSMExportImportDocSeq")
	public void testcase05() throws Exception {
		ipName = "DOCSEQ" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Document Sequences",fsm1Driver);
		docUtil.createDocumentSequence("DOC" + id,fsm1Driver);
		docUtil.createDocSeqAssignment("DOC" + id, "ACH",fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Document Sequences",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Document Sequences");
		navigateToAll("Manage Document Sequences");
		docUtil.deleteDocSeqAssignment("DOC" + id, "ACH",fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.searchConfigurationProject(ipName,fsm1Driver);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Document Sequences");
		navigateToAll("Manage Document Sequences");
		docUtil.verifyStatus1("DOC" + id, "ACH",fsm1Driver);
		docUtil.deleteDocSeqAssignment("DOC" + id, "ACH",fsm1Driver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
      fsm1Driver.quit();
	}

}
