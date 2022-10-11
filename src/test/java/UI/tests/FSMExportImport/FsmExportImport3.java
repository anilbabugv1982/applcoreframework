package UI.tests.FSMExportImport;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ISOUtil;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ManageDocumentSequence;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.FsmExportImport.FSMExpImpWrapper;

public class FsmExportImport3 extends GetDriverInstance{
	
	private static String taskListURL="";
	private String id;
	private String ipName;
	private WebDriver fsm1Driver = null;
	private ApplicationLogin appLogin = null;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private JavascriptExecutor js = null;
	private ManageDocumentSequence docPage = null;
	private ISOUtil isoUtil = null;
	private FSMExpImpWrapper fsmUtil = null;
	
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String password) throws Exception {

		id = CommonUtils.uniqueId();
		try {
			fsm1Driver = getDriverInstanceObject();
			appLogin = new ApplicationLogin(fsm1Driver);
			taskFlowsNavigation = new NavigationTaskFlows(fsm1Driver);
			isoUtil = new ISOUtil(fsm1Driver);
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
	public void navigateToHomePage() throws Exception {
			fsm1Driver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome","FuseTaskListManagerTop"));
			CommonUtils.hold(3);

	}

	public void navigateToAll(String taskFlowName) throws Exception {
		navigateToHomePage();
		taskFlowsNavigation.navigateToAOLTaskFlows(taskFlowName, fsm1Driver);
		CommonUtils.hold(4);
	}
	
	@Test(description = "FSMExportImportCurrencies")
	public void testcase01() throws Exception {
		ipName = "CURR" + id;
		// CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		currencyTF();
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Currencies",fsm1Driver);
		isoUtil.createCurrency("DP" + id,fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Currencies",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		// CommonUtils.navigateToAOLTaskFlows("Manage Common Lookups");
//		currencyTF();
		navigateToAll("Manage Currencies");
		isoUtil.deleteCurrency("DP" + id,fsm1Driver);
		CommonUtils.hold(5);
		//fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		currencyTF();
		navigateToAll("Manage Currencies");
		isoUtil.verifyCurrencyStatus("DP" + id,fsm1Driver);
		isoUtil.deleteCurrency("DP" + id,fsm1Driver);

	}

	@Test(description = "FSMExportImportISOLanguage")
	public void testcase02() throws Exception {
		ipName = "ISO" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage ISO Languages",fsm1Driver);
		isoUtil.createISO("ISO" + id,fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage ISO Languages",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage ISO Languages");
		navigateToAll("Manage ISO Languages");
		isoUtil.deleteISO("ISO" + id,fsm1Driver);
		CommonUtils.hold(5);
		//fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage ISO Languages");
		navigateToAll("Manage ISO Languages");
		isoUtil.searchISO("ISO" + id,fsm1Driver);
		isoUtil.deleteISO("ISO" + id,fsm1Driver);

	}

	@Test(description = "FSMExportImportLanguages")
	public void testcase03() throws Exception {
		ipName = "LANG" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Languages",fsm1Driver);
		isoUtil.updateLanguage("Arabic", "UPDATE",fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Languages",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Languages");
		navigateToAll("Manage Languages");
		isoUtil.updateLanguage("UPDATE", "POST",fsm1Driver);
		CommonUtils.hold(5);
		//fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Languages");
		navigateToAll("Manage Languages");
		isoUtil.searchLanguage("UPDATE",fsm1Driver);
		isoUtil.updateLanguage("UPDATE", "Arabic",fsm1Driver);

	}

	@Test(description = "FSMExportImportTerritories")
	public void testcase04() throws Exception {
		ipName = "TERRI" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Territories",fsm1Driver);
		isoUtil.createTerritory("TT" + id,fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Territories",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Territories");
		navigateToAll("Manage Territories");
		isoUtil.deleteTerritory("TT" + id,fsm1Driver);
		CommonUtils.hold(5);
		//fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Territories");
		navigateToAll("Manage Territories");
		isoUtil.searchTerritory("TT" + id,fsm1Driver);
		isoUtil.deleteTerritory("TT" + id,fsm1Driver);

	}

	@Test(description = "FSMExportImportTimeZones")
	public void testcase05() throws Exception {
		ipName = "TIMEZONE" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Time Zones",fsm1Driver);
		isoUtil.createTimeZone("TZ" + id,fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Time Zones",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Time Zones");
		navigateToAll("Manage Time Zones");
		isoUtil.deleteTimeZone("TZ" + id,fsm1Driver);
		CommonUtils.hold(5);
		//fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Time Zones");
		navigateToAll("Manage Time Zones");
		isoUtil.searchTimeZone("TZ" + id,fsm1Driver);
		isoUtil.deleteTimeZone("TZ" + id,fsm1Driver);

	}

	@Test(description = "FSMExportImportIndustries")
	public void testcase06() throws Exception {
		ipName = "INDUS" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Industries",fsm1Driver);
		isoUtil.createIndustry("NAME" + id,fsm1Driver);
		isoUtil.createTerritory("Australia", "NAME" + id,fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Industries",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Industries");
		navigateToAll("Manage Industries");
		isoUtil.deleteIndustry("NAME" + id,fsm1Driver);
		CommonUtils.hold(5);
		//fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Industries");
		navigateToAll("Manage Industries");
		isoUtil.searchIndustry("NAME" + id,fsm1Driver);
		isoUtil.deleteIndustry("NAME" + id,fsm1Driver);
	}

	@Test(description = "FSMExportImportNaturalLanguage")
	public void testcase07() throws Exception {
		ipName = "NL" + id;
		taskFlowsNavigation.navigateToAOLTaskFlows("Manage Natural Languages",fsm1Driver);
		isoUtil.createNaturalLanguage("NL" + id,fsm1Driver);
		CommonUtils.hold(5);
		fsmUtil.createImplementationProject(ipName, "Manage Natural Languages",fsm1Driver);
		fsmUtil.createConfigurationProject(ipName,fsm1Driver);
		fsmUtil.downloadPackage(fsm1Driver);
		CommonUtils.hold(3);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Natural Languages");
		navigateToAll("Manage Natural Languages");
		isoUtil.deleteNaturalLanguage("NL" + id,fsm1Driver);
		CommonUtils.hold(5);
		//fsmUtil.searchConfigurationProject(ipName);
		fsmUtil.uploadConfigurationPackage(ipName,fsm1Driver);
		fsmUtil.importSetupData(ipName,fsm1Driver);
//		CommonUtils.navigateToTask(GlobalPageTemplate.setupandmaintenance);
//		CommonUtils.navigateToAOLTaskFlows("Manage Natural Languages");
		navigateToAll("Manage Natural Languages");
		isoUtil.searchNaturalLanguage("NL" + id,fsm1Driver);
		isoUtil.deleteNaturalLanguage("NL" + id,fsm1Driver);
	}

//	public void currencyTF() throws Exception {
//		SetupAndMaintenance.panelDrawer.click();
//		CommonUtils.hold(5);
//		CommonUtils.explicitWait(SetupAndMaintenance.searchLink, CommonUtils.ExplicitWaitCalls.Click.toString(), "");
//		SetupAndMaintenance.searchLink.click();
//		CommonUtils.hold(5);
//		CommonUtils.explicitWait(ISOReferenceDataPage.filterLink, CommonUtils.ExplicitWaitCalls.Click.toString(), "");
//		// System.out.println(ISOReferenceDataPage.filterLink.isDisplayed());
//		ISOReferenceDataPage.filterLink.click();
//		CommonUtils.hold(2);
//		CommonUtils.explicitWait(ISOReferenceDataPage.checkBoxAll, CommonUtils.ExplicitWaitCalls.Click.toString(), "");
//		ISOReferenceDataPage.checkBoxAll.click();
//		CommonUtils.hold(2);
//		ISOReferenceDataPage.checkBoxAll.click();
//		CommonUtils.hold(2);
//		CommonUtils.explicitWait(ISOReferenceDataPage.checkBoxTasks, CommonUtils.ExplicitWaitCalls.Click.toString(),
//				"");
//		ISOReferenceDataPage.checkBoxTasks.click();
//		CommonUtils.hold(2);
//		CommonUtils.explicitWait(ISOReferenceDataPage.filetrOKBtn, CommonUtils.ExplicitWaitCalls.Click.toString(), "");
//		ISOReferenceDataPage.filetrOKBtn.click();
//		CommonUtils.explicitWait(SetupAndMaintenance.searchField, CommonUtils.ExplicitWaitCalls.Click.toString(), "");
//		SetupAndMaintenance.searchField.clear();
//		SetupAndMaintenance.searchField.sendKeys("Manage Currencies");
//		CommonUtils.explicitWait(SetupAndMaintenance.searchButton, CommonUtils.ExplicitWaitCalls.Click.toString(), "");
//		SetupAndMaintenance.searchButton.click();
//		CommonUtils.hold(2);
//		CommonUtils.explicitWait(DriverInstance.getDriverInstance().findElement(By.linkText("Manage Currencies")),
//				CommonUtils.ExplicitWaitCalls.Click.toString(), "");
//		DriverInstance.getDriverInstance().findElement(By.linkText("Manage Currencies")).click();
//		CommonUtils.hold(5);
//		CommonUtils.explicitWait(
//				DriverInstance.getDriverInstance().findElement(By.xpath("//h1[contains(text(),'Manage Currencies')]")),
//				CommonUtils.ExplicitWaitCalls.Visible.toString(), "");
//
//	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
        fsm1Driver.quit();
	}

}
