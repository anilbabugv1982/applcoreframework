package UI.tests.Audit;

import java.text.DecimalFormat;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import UtilClasses.UI.SetupAndMaintenance;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.AttachCatUtils;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ProfilesUtils;
import pageDefinitions.UI.oracle.applcore.qa.Audit.AuditReferencePage;
import pageDefinitions.UI.oracle.applcore.qa.Audit.AuditReportESSJob;
import pageDefinitions.UI.oracle.applcore.qa.Audit.CommonAuditSetup;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Dff.ManagePositions;
import pageDefinitions.UI.oracle.applcore.qa.Eff.RuntimeValidation;
import pageDefinitions.UI.oracle.applcore.qa.FsmExportImport.FSMExpImpWrapper;
import pageDefinitions.UI.oracle.applcore.qa.GlobalSearch.gs;
import pageDefinitions.UI.oracle.applcore.qa.Kff.AuditManageKFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Kff.ManageKFFSetup;
import pageDefinitions.UI.oracle.applcore.qa.ds.CreateUserRoleMap;


public class Audit extends GetDriverInstance  {
	String pid;
	private String id;
	private String ipName;
	public static String timestamp = "";
	
	WebDriver AuditDrvInstance;
	private ApplicationLogin aLoginInstance=null;
	private NavigationTaskFlows ntFInstance=null;
	private NavigatorMenuElements nMEInstance=null;
	private GlobalPageTemplate glbpageInstance=null;
	private CommonUtils CUtilInstance=null;
	private CommonAuditSetup AuditSetupinstance=null;
	private ProfilesUtils ProfilsInstance=null;
	private AttachCatUtils AttachCatUtilInstance;
	private AuditManageKFFUtils AuditManageKFFInstance=null;
	private RuntimeValidation RuntimeValidationInstance=null;
	private DFFUtils dffUtilsInstance=null;
	private CreateUserRoleMap createUserRoleMapInstance=null;
	private FSMExpImpWrapper fsmUtil = null;
	private AuditReportESSJob auditReportESSJObInstance = null;
	private ManagePositions managePositionsInstance = null;
	

//*****************************************************************************************************************************************************************	
	
	@Parameters({ "user", "pwd" })
	@BeforeClass(alwaysRun = true)
    public void startUP(String username, String password) throws Exception {
		id = CommonUtils.uniqueId();
		AuditDrvInstance =  getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(AuditDrvInstance);
		ntFInstance = new NavigationTaskFlows(AuditDrvInstance);
		nMEInstance = new NavigatorMenuElements(AuditDrvInstance);
		glbpageInstance=new GlobalPageTemplate(AuditDrvInstance);
		AuditSetupinstance=new CommonAuditSetup(AuditDrvInstance);
		ProfilsInstance=new ProfilesUtils(AuditDrvInstance);
		AttachCatUtilInstance=new AttachCatUtils(AuditDrvInstance);
		AuditManageKFFInstance=new AuditManageKFFUtils(AuditDrvInstance);
		RuntimeValidationInstance=new RuntimeValidation(AuditDrvInstance);
		dffUtilsInstance=new DFFUtils(AuditDrvInstance);
		createUserRoleMapInstance=new CreateUserRoleMap(AuditDrvInstance);
		fsmUtil = new FSMExpImpWrapper(AuditDrvInstance);
		auditReportESSJObInstance = new AuditReportESSJob(AuditDrvInstance);
		managePositionsInstance = new ManagePositions(AuditDrvInstance);
    aLoginInstance.login(username, password, AuditDrvInstance);       
	Log.info("Logging into the Application");
	CommonUtils.hold(10);
    }

	
	
//*****************************************************************************************************************************************************************	
	@Test(priority=1,description="This testcase is used for setting up Audit Policy for Oracle Middleware Extensions for Applications product",enabled=true)
	public void testcase01() throws Exception {
		
		ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, AuditDrvInstance);
		Log.info("Clicked on Setup and Maintenance");
		AuditSetupinstance.applcoreauditsetup(AuditDrvInstance);
		AuditSetupinstance.AuditingVO(AuditDrvInstance);
		
		//glbpageInstance.homeIcon.click();
		//Log.info("Clicked on Home Icon");
		//CommonUtils.waitForPageLoad(AuditDrvInstance);
		//CommonUtils.hold(5);
	}
	
	
//*****************************************************************************************************************************************************************
	@Test(priority=2,description="This testcase is used for setting up OPSS auditing",enabled=true)
	public void testcase02() throws Exception {
		ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, AuditDrvInstance);
		Log.info("Clicked on Setup and Maintenance");
		ntFInstance.navigateToAOLTaskFlows("Manage Audit Policies", AuditDrvInstance);
		Log.info("Clicked on Manage Audit Policies");
		
		AuditSetupinstance.opssauditing(AuditDrvInstance);
	}
	
	
//*****************************************************************************************************************************************************************	
	@Test(priority=3,description="This testcase is used for setting up Audit Policy for Item Flex Data",enabled=true)
	public void testcase03() throws Exception {
		ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, AuditDrvInstance);
		Log.info("Clicked on Setup and Maintenance");
		AuditSetupinstance.itemEffFlexAuditing(AuditDrvInstance);
		
		//glbpageInstance.homeIcon.click();
		//Log.info("Clicked on Home Icon");
		//CommonUtils.waitForPageLoad(AuditDrvInstance);
		//CommonUtils.hold(5);
	}
	
	
//*****************************************************************************************************************************************************************
	@Test(priority=4,description="This testcase is used for setting up Audit Policy for Person DFF Data",enabled=true)
	public void testcase04() throws Exception {
		ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, AuditDrvInstance);
		Log.info("Clicked on Setup and Maintenance");
		AuditSetupinstance.personDffFlexAuditing(AuditDrvInstance);
		
		//glbpageInstance.homeIcon.click();
		//Log.info("Clicked on Home Icon");
		//CommonUtils.waitForPageLoad(AuditDrvInstance);
		//CommonUtils.hold(5);
	}

//*****************************************************************************************************************************************************************
	
	@Test(priority=5,description="This testcase is used for setting up Audit Policy for Position DFF Data",enabled=true)
	public void testcase05() throws Exception {
		ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, AuditDrvInstance);
		Log.info("Clicked on Setup and Maintenance");
		AuditSetupinstance.positionDffFlexAuditing(AuditDrvInstance);
		
		//glbpageInstance.homeIcon.click();
		//Log.info("Clicked on Home Icon");
		//CommonUtils.waitForPageLoad(AuditDrvInstance);
		//CommonUtils.hold(5);
	}
	
//*****************************************************************************************************************************************************************
	
	@Test(priority=6,description="This testcase is used Creating Profile Option",enabled=true)
	public void testcase06() throws Exception {
		ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, AuditDrvInstance);
		Log.info("Click on Setup and Maintenance");
		ntFInstance.navigateToAOLTaskFlows("Manage Profile Options", AuditDrvInstance);
		Log.info("Navigate to Manage Profile Option task flow");
		
		AuditReferencePage.POName = "PO" + CommonUtils.uniqueId();
		ProfilsInstance.createProfileOptions(AuditReferencePage.POName, AuditDrvInstance);
		CommonUtils.hold(5);
		
		//glbpageInstance.homeIcon.click();
		//Log.info("Clicked on Home Icon");
		//CommonUtils.waitForPageLoad(AuditDrvInstance);
		//CommonUtils.hold(5);
	}
	
	
//*****************************************************************************************************************************************************************
	@Parameters({ "audituser", "pwd" })
	@Test(priority=7,description="This testcase is used for validating Audit Report for Profile Option",enabled=true, dependsOnMethods={"testcase06"})
	public void testcase07(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);       
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.auditreports, AuditDrvInstance);
		Log.info("Clicked on Audit Reports");
		
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		AuditSetupinstance.auditreport("Oracle Middleware Extensions for Applications","Profile Option", false, AuditDrvInstance);
		Log.info("Oracle Middleware Extensions for Applications:Profile Option - Audit Report validated");
		
		AuditSetupinstance.auditReprotClickOnHomeIcon(AuditDrvInstance);
	} 	
	

//*****************************************************************************************************************************************************************
	@Parameters({ "user", "pwd" })
	@Test(priority=8,description="This testcase is used Creating Attachment Categories and Entity",enabled=true)
	public void testcase08(String username, String password) throws Exception {
			
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, AuditDrvInstance);
		Log.info("Clicked on Setup and Maintenance");
		ntFInstance.navigateToAOLTaskFlows("Manage Attachment Entities", AuditDrvInstance);
		Log.info("Clicked on Manage Attachment Entities");
		
		AuditReferencePage.attachEntDetails = AttachCatUtilInstance.attachmententity(AuditDrvInstance, false);
		Log.info("Created Attachment Entity");
		CommonUtils.hold(5);

		
		ntFInstance.navigateToAOLTfSearchPage("Manage Attachment Categories",AuditDrvInstance);
		Log.info("Click on Manage Attachment Categories");
		AuditReferencePage.attachCatDetails = AttachCatUtilInstance.AttachCateg(AuditDrvInstance);
		Log.info("Created Attachment Category");
		CommonUtils.hold(5);
		
		//glbpageInstance.homeIcon.click();
		//Log.info("Clicked on Home Icon");
		//CommonUtils.waitForPageLoad(AuditDrvInstance);
		//CommonUtils.hold(5);
	}
	

//*****************************************************************************************************************************************************************
	@Parameters({ "audituser", "pwd" })
	@Test(priority=9,description="This testcase is used for validating Audit Report for Document Entities",enabled=true, dependsOnMethods={"testcase08"})
	public void testcase09(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);       
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.auditreports, AuditDrvInstance);
		Log.info("Clicked on Audit Reports");
		
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		AuditSetupinstance.auditreport("Oracle Middleware Extensions for Applications","Document Entities", false, AuditDrvInstance);
		Log.info("Oracle Middleware Extensions for Applications:Document Entities  - Audit Report validated");
		
		AuditSetupinstance.auditReprotClickOnHomeIcon(AuditDrvInstance);
	}	
	
			
//*****************************************************************************************************************************************************************
	@Parameters({ "audituser", "pwd" })
	@Test(priority=10,description="This testcase is used for validating Audit Report for Document Categories",enabled=true, dependsOnMethods={"testcase08"})
	public void testcase10(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);       
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.auditreports, AuditDrvInstance);
		Log.info("Clicked on Audit Reports");
		
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		AuditSetupinstance.auditreport("Oracle Middleware Extensions for Applications","Document Categories", true, AuditDrvInstance);
		Log.info("Oracle Middleware Extensions for Applications:Document Categories - Audit Report validated");
		
		AuditSetupinstance.auditReprotClickOnHomeIcon(AuditDrvInstance);
	}	
	
	
//*****************************************************************************************************************************************************************	
	@Parameters({ "user", "pwd" })
	@Test(priority=11,description="This testcase is used Creating Structure in KFF",enabled=true)
	public void testcase11(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, AuditDrvInstance);
		Log.info("Clicked on Setup and Maintenance");
		ntFInstance.navigateToAOLTaskFlows("Manage Key Flexfields", AuditDrvInstance);
		Log.info("Clicked on Manage Key Flexfields");
		
		AuditManageKFFInstance.clickManageKeyFlexfieldsSearch(AuditDrvInstance);
		Log.info("Clicked on Search Key Flexfields button.");
		
		AuditManageKFFInstance.clickMDSPFlexfieldCode(AuditDrvInstance);
		Log.info("Clicked on MDSP Key Flexfield Code row.");
		
		
		AuditManageKFFInstance.deployFlexfield(AuditDrvInstance);
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		
		AuditManageKFFInstance.clickManageStructure(AuditDrvInstance);
		Log.info("Clicked on Manage Structures.");
		
		AuditReferencePage.KFFStructureDetails = AuditManageKFFInstance.createStructure(AuditDrvInstance, ".");
		Log.info("Structure has been created.");
		
		// this is just to handle RemoteRegion error
		glbpageInstance.homeIcon.click();
		Log.info("Clicked on Home Icon");
		ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, AuditDrvInstance);
		Log.info("Clicked on Setup and Maintenance");
		ntFInstance.navigateToAOLTaskFlows("Manage Key Flexfields", AuditDrvInstance);
		Log.info("Clicked on Manage Key Flexfields");
		AuditManageKFFInstance.clickManageKeyFlexfieldsSearch(AuditDrvInstance);
		Log.info("Clicked on Search Key Flexfields button.");
		AuditManageKFFInstance.clickManageStructure(AuditDrvInstance);
		Log.info("Clicked on Manage Structures.");
		// till this to handle RemoteRegion error		
		
		AuditManageKFFInstance.validateCreatedStructure(AuditDrvInstance);
		Log.info("Structure has been validated.");
		
		//glbpageInstance.homeIcon.click();
		//Log.info("Clicked on Home Icon");
		//CommonUtils.waitForPageLoad(AuditDrvInstance);
		//CommonUtils.hold(5);
	}	
	
	
//*****************************************************************************************************************************************************************	
	@Parameters({ "audituser", "pwd" })
	@Test(priority=12,description="This testcase is used for validating Audit Report for Key Flexfield",enabled=true, dependsOnMethods={"testcase11"})
	public void testcase12(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);       
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.auditreports, AuditDrvInstance);
		Log.info("Clicked on Audit Reports");
		
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		AuditSetupinstance.auditreport("Oracle Middleware Extensions for Applications","Key Flexfield", true, AuditDrvInstance);
		Log.info("Oracle Middleware Extensions for Applications:Key Flexfield - Audit Report validated");
		
		AuditSetupinstance.auditReprotClickOnHomeIcon(AuditDrvInstance);
	}
	
	
//*****************************************************************************************************************************************************************	
	@Parameters({ "OPSSuser", "pwd" })
	@Test(priority=13,description="This testcase is used for creating role and user for OPSS",enabled=true)
	public void testcase13(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);       
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.securityConsole, AuditDrvInstance);
		Log.info("Clicked on Security Console.");
		
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(20);
		createUserRoleMapInstance.manageSecurityConsoleRolesWarningBox(AuditDrvInstance);
		
		String userFirstName = "FirstName_"+CommonUtils.uniqueId();
		AuditReferencePage.enterpriseRoles = createUserRoleMapInstance.createUser(AuditDrvInstance, userFirstName);
		Log.info("User has been created.");
		
		AuditReferencePage.applicationRole = createUserRoleMapInstance.createRole(AuditDrvInstance, "Application implementation Administrator", userFirstName);
		Log.info("Role has been created.");
	}
	

//*****************************************************************************************************************************************************************
	@Parameters({ "audituser", "pwd" })
	@Test(priority=14,description="This testcase is used for validating Audit Report for Oracle Platform Security Services",enabled=true, dependsOnMethods={"testcase13"})
	public void testcase14(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);       
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.auditreports, AuditDrvInstance);
		Log.info("Clicked on Audit Reports");
		
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		AuditSetupinstance.auditreport("Oracle Platform Security Services","", false, AuditDrvInstance);
		Log.info("Oracle Platform Security Services - Audit Report validated");
		
		AuditSetupinstance.auditReprotClickOnHomeIcon(AuditDrvInstance);
	}
	

//*****************************************************************************************************************************************************************
	@Parameters({ "EFFuser", "pwd" })
	@Test(priority=15,description="This testcase is used for creating Items for EFF",enabled=true)
	public void testcase15(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);       
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.productInformationManagement, AuditDrvInstance);
		Log.info("Clicked on Product Information Management.");
		CommonUtils.waitForInvisibilityOfElement(AuditReferencePage.waitCursor, AuditDrvInstance, 30);
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		
		String itemName = "Item_"+CommonUtils.uniqueId();
		System.out.println("Item name to be created  is -"+itemName);
		CommonUtils.hold(10);
		
		Assert.assertTrue(RuntimeValidationInstance.createItem(itemName, "", "EFF_NT_SR_MR_NV_ALL_LEVEL_ALL_TYPES_2", AuditDrvInstance), "Item creation unsuccessful for EFF.");
		Log.info("Item has been created.");
		
		//RuntimeValidationInstance.pageExistsUnderItemUsage("Item","Additional Attributes");
		
		JavascriptExecutor js = (JavascriptExecutor) AuditDrvInstance;
		js.executeScript("window.scrollBy(0,250)");
		CommonUtils.explicitWait(RuntimeValidationInstance.specificationsLink, "Click", "", AuditDrvInstance);
		RuntimeValidationInstance.specificationsLink.click();
		CommonUtils.hold(5);
		
		CommonUtils.explicitWait(AuditDrvInstance.findElement(By.xpath("//a[contains(@id,'tiaLink2')]")), "Click", "", AuditDrvInstance);
		AuditDrvInstance.findElement(By.xpath("//a[contains(@id,'tiaLink2')]")).click();
		Log.info("Additional Attributes link clicked.");
		
		AuditSetupinstance.enterItemAdditionalAttributesValues(AuditDrvInstance);
		Log.info("Provided values in Additional Attributes fields.");
		
		//RuntimeValidationInstance.pageExistsUnderItemUsage("Item","IPage_SR");
		RuntimeValidationInstance.itemSaveAndClose();
		Log.info("Save and Closed - Item.");
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
	}
	
	
//*****************************************************************************************************************************************************************
	@Parameters({ "audituser", "pwd" })
	@Test(priority=16,description="This testcase is used for validating Audit Report for Item EFF",enabled=true, dependsOnMethods={"testcase15"})
	public void testcase16(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);       
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.auditreports, AuditDrvInstance);
		Log.info("Clicked on Audit Reports");
		
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		AuditSetupinstance.auditreport("Product Hub","Item", false, AuditDrvInstance);
		Log.info("Product Hub:Item - Audit Report validated");
		
		AuditSetupinstance.auditReprotClickOnHomeIcon(AuditDrvInstance);
	}
	

//*****************************************************************************************************************************************************************
	@Parameters({ "DFFuser", "pwd" })
	@Test(priority=17,description="This testcase is used for creating Person for DFF",enabled=true)
	public void testcase17(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);       
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.newPerson, AuditDrvInstance);
		Log.info("Clicked on New Person.");
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		
		String uniqueID = CommonUtils.uniqueId();
		AuditReferencePage.Person_glb = "glb" + uniqueID.substring(9);
		AuditReferencePage.FBLPG = "pg" + uniqueID.substring(9);
		AuditReferencePage.empLastName = "lastName" + uniqueID;
		dffUtilsInstance.navigateToHireAnEmployee(AuditReferencePage.empLastName, "GBI_GHR_3TSTMA", AuditReferencePage.Person_glb, AuditReferencePage.FBLPG, AuditDrvInstance);
		Log.info("Hire an Employee - Successful.");
	}
	
	
//*****************************************************************************************************************************************************************
	@Parameters({ "DFFuser", "pwd" })
	@Test(priority=18,description="This testcase is used for validating Audit Report for Person DFF",enabled=true, dependsOnMethods={"testcase17"})
	public void testcase18(String username, String password) throws Exception {
		
		//logOut();
		//aLoginInstance.login(username, password, AuditDrvInstance);       
		//Log.info("Logging into the Application");
		//CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.auditreports, AuditDrvInstance);
		Log.info("Clicked on Audit Reports");
		
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		AuditSetupinstance.auditreport("Global Human Resources", "Person", false, AuditDrvInstance);
		Log.info("Global Human Resources:Person - Audit Report validated");
		
		AuditSetupinstance.auditReprotClickOnHomeIcon(AuditDrvInstance);
	}
	

//*****************************************************************************************************************************************************************
	@Parameters({ "audituser", "pwd" })
	@Test(priority=19,description="This testcase is used for validating Export to Excel functionality in Audit Report page",enabled=true, dependsOnMethods={"testcase17"})
	public void testcase19(String username, String password) throws Exception {
		
		//logOut();
		//aLoginInstance.login(username, password, AuditDrvInstance);       
		//Log.info("Logging into the Application");
		//CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.auditreports, AuditDrvInstance);
		Log.info("Clicked on Audit Reports");
		
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		AuditSetupinstance.validateExporttoExcel("Global Human Resources", "Person", true, AuditDrvInstance);
		//AuditSetupinstance.validateExporttoExcel("Product Hub", "Item", true, AuditDrvInstance);
		Log.info("Global Human Resources:Person - Export to Excel validated.");
		
		AuditSetupinstance.auditReprotClickOnHomeIcon(AuditDrvInstance);
	}
	
	
//*****************************************************************************************************************************************************************
	@Parameters({ "audituser", "pwd" })
	@Test(priority=20,description="This testcase is used for validating Export to CSV functionality in Audit Report page",enabled=true, dependsOnMethods={"testcase17"})
	public void testcase20(String username, String password) throws Exception {
		
		//logOut();
		//aLoginInstance.login(username, password, AuditDrvInstance);       
		//Log.info("Logging into the Application");
		//CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(glbpageInstance.auditreports, AuditDrvInstance);
		Log.info("Clicked on Audit Reports");
		
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		AuditSetupinstance.validateExporttoCSV("Global Human Resources", "Person", true, AuditDrvInstance);
		//AuditSetupinstance.validateExporttoCSV("Product Hub", "Item", true, AuditDrvInstance);
		Log.info("Global Human Resources:Person - Export to CSV validated.");
		
		AuditSetupinstance.auditReprotClickOnHomeIcon(AuditDrvInstance);
	} 
		

//*****************************************************************************************************************************************************************

	@Parameters({ "DFFuser", "pwd" })
	@Test(priority=21,description="This testcase is used adding Position attributes.",enabled=true, dependsOnMethods={"testcase05"})
	public void testcase21(String username, String password) throws Exception {
		
		//logOut();
		//aLoginInstance.login(username, password, AuditDrvInstance);       
		//Log.info("Logging into the Application");
		//CommonUtils.hold(10);
		
		ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance, AuditDrvInstance);
		Log.info("Click on Setup and Maintenance");
		ntFInstance.navigateToAOLTaskFlows("Manage Positions", AuditDrvInstance);
		Log.info("Navigate to Manage Positions task flow");
		
		id = CommonUtils.uniqueId();
		double amount = Double.parseDouble(id);
		//DecimalFormat formatter = new DecimalFormat("#,###");
		//AuditReferencePage.POS_GLB = formatter.format(amount);
		AuditReferencePage.POS_GLB = id;
		AuditReferencePage.att1_UK_001_Simple = "att1"+id;
		AuditReferencePage.posglobal1 = "glb" + id.substring(6);
		String positionName = "Aero-Space Maintenance Mechanic II";
		
		managePositionsInstance.editPositionAttributes(positionName, AuditReferencePage.att1_UK_001_Simple, AuditReferencePage.POS_GLB, AuditReferencePage.posglobal1, AuditDrvInstance);
		
	}

//*****************************************************************************************************************************************************************
	
	@Parameters({ "DFFuser", "pwd" })
	@Test(priority=22,description="This testcase is used for validating Audit Report for Position",enabled=true)
	public void testcase22(String username, String password) throws Exception {
		
		ntFInstance.navigateToTask(glbpageInstance.auditreports, AuditDrvInstance);
		Log.info("Clicked on Audit Reports");
		
		CommonUtils.waitForPageLoad(AuditDrvInstance);
		CommonUtils.hold(10);
		AuditSetupinstance.auditreport("Global Human Resources", "Position", false, AuditDrvInstance);
		Log.info("Global Human Resources:Position - Audit Report validated");
		
		AuditSetupinstance.auditReprotClickOnHomeIcon(AuditDrvInstance);
	} 	
	
//*****************************************************************************************************************************************************************

	@Parameters({ "user", "pwd" })
	@Test(priority=23,description="This testcase is used to generate Excel Audit Report using ESS",enabled=true, dependsOnMethods={"testcase06"})
	public void testcase23(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);       
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		String processname = "Generate Audit Report";
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, AuditDrvInstance);
		CommonUtils.hold(5);
		
		String product = "Oracle Middleware Extensions for Applications";
		String boType = "Profile Option";
		boolean includeChildObjects = true;
		String outputFileFormat = "Excel";
		
		auditReportESSJObInstance.createEssJobForAuditReport(processname, true, product, boType, includeChildObjects, outputFileFormat, AuditDrvInstance);
		Log.info("Audit Report ESS Job ran successfully.");
		
		
	} 
	
//*****************************************************************************************************************************************************************
	@Parameters({ "user", "pwd" })
	@Test(priority=24,description="This testcase is used to generate CSV Audit Report using ESS",enabled=true, dependsOnMethods={"testcase06"})
	public void testcase24(String username, String password) throws Exception {
		
		//logOut();
		//aLoginInstance.login(username, password, AuditDrvInstance);       
		//Log.info("Logging into the Application");
		//CommonUtils.hold(10);
		
		String processname = "Generate Audit Report";
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, AuditDrvInstance);
		CommonUtils.hold(5);
		
		String product = "Oracle Middleware Extensions for Applications";
		String boType = "Profile Option";
		boolean includeChildObjects = true;
		String outputFileFormat = "CSV";
		
		auditReportESSJObInstance.createEssJobForAuditReport(processname, true, product, boType, includeChildObjects, outputFileFormat, AuditDrvInstance);
		Log.info("Audit Report ESS Job ran successfully.");
		
		
	} 
		
//*****************************************************************************************************************************************************************		
	
	@Parameters({ "user", "pwd" })
	@Test(priority=25,description="This testcase is used for validating KFF Audit Export/Import",enabled=true)
	public void testcase25(String username, String password) throws Exception {
		
		logOut();
		aLoginInstance.login(username, password, AuditDrvInstance);       
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		ipName = "PROJ" + id;
		
		fsmUtil.createImplementationProject(ipName, "Manage Audit Policies", AuditDrvInstance);
		fsmUtil.createConfigurationProject(ipName, AuditDrvInstance);
		fsmUtil.downloadPackage(AuditDrvInstance);
		CommonUtils.hold(5);
		AuditDrvInstance.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome","FuseTaskListManagerTop"));
		CommonUtils.hold(5);
		
		AuditSetupinstance.applcoreauditsetup(AuditDrvInstance);
		AuditSetupinstance.AuditViewObjectScroll(AuditDrvInstance, "Last");
		CommonUtils.hold(5);
		boolean flag1 = AuditSetupinstance.isSelectedAttributeViewObject(AuditDrvInstance, "Key Flexfield Labeled Segment");
		
		if(flag1) {
			AuditSetupinstance.uncheckAttributeViewObject(AuditDrvInstance, "Key Flexfield Labeled Segment");
		}
		else {
			AuditSetupinstance.selectAttributeViewObject(AuditDrvInstance, "Key Flexfield Labeled Segment");
		}
		
		((JavascriptExecutor) AuditDrvInstance).executeScript("window.scrollTo(0, -(document.body.scrollHeight))");
		System.out.println("----after Verticale scroll till top----");
		
		AuditReferencePage.SaveAndCloseBtn.click();
		CommonUtils.hold(5);
		Log.info("Save and Closed - Configure Business Object Attributes");
		
		AuditReferencePage.SaveAndCloseBtn.click();
		CommonUtils.hold(5);
		Log.info("Save and Closed - Manage Audit Policies");
		
		//CommonUtils.hold(5);
		fsmUtil.uploadConfigurationPackage(ipName, AuditDrvInstance);
		fsmUtil.importSetupData(ipName, AuditDrvInstance);
		CommonUtils.hold(5);
		
		AuditDrvInstance.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome","FuseTaskListManagerTop"));
		CommonUtils.hold(5);		
		AuditSetupinstance.applcoreauditsetup(AuditDrvInstance);
		AuditSetupinstance.AuditViewObjectScroll(AuditDrvInstance, "Last");
		boolean flag2 = AuditSetupinstance.isSelectedAttributeViewObject(AuditDrvInstance, "Key Flexfield Labeled Segment");
		
		Assert.assertEquals(flag2, flag1, "Assertion failed for Key Flexfield Labeled Segment value after Audit Import");
		Log.info("Audit Export Import verified.");		
		
		//glbpageInstance.homeIcon.click();
		//Log.info("Clicked on Home Icon");
		//CommonUtils.waitForPageLoad(AuditDrvInstance);
		//CommonUtils.hold(5);
	}
		

//*****************************************************************************************************************************************************************
		
	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {

		try {
			aLoginInstance.logout(AuditDrvInstance);

		} catch (Exception ex) {
			aLoginInstance.launchURL(AuditDrvInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(AuditDrvInstance);
		} 
		
		  //finally { AuditDrvInstance.quit(); }
		 
	}
//************************************************************************************************************************************	

}
