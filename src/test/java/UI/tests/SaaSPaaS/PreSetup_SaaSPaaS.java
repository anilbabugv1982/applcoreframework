package UI.tests.SaaSPaaS;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
import pageDefinitions.UI.oracle.applcore.qa.Audit.AuditReferencePage;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS.IDCSValidation;
import pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS.ManageAdminProfileValues;
import pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS.ManageSetupContent;
import pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS.ManageUserRoleMigration;
import pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS.SaaSPaaSCommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS.SaaSPaaS_ScheduleProcess;
import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;
import pageDefinitions.UI.oracle.applcore.qa.ds.CreateUserRoleMap;

public class PreSetup_SaaSPaaS extends GetDriverInstance {
	
	WebDriver SaasPaasDrvInstance;
	JavascriptExecutor js;
	//String id;
	//String env="";
	private String clientID = null;
	private String clientSecret = null;
	
	private ApplicationLogin aLoginInstance=null;
	private DFFUtils dffUtilsInstance=null;
	private NavigationTaskFlows ntFInstance=null;
	private NavigatorMenuElements nMEInstance=null;
	private GlobalPageTemplate glbpageInstance=null;
	private CreateUserRoleMap createUserRoleMapInstance=null;
	private SaaSPaaS_ScheduleProcess saasPaaS_ScheduleProcessInstance=null;
	private ManageUserRoleMigration manageUserRoleMigrationInstance=null;
	private IDCSValidation idcsValidationInstance=null;
	private ManageAdminProfileValues manageAdminProfileValuesInstance=null;
	//private SetupAndMaintenance sAMInstance_nTF=null;
	//private CommonUtilsDefs commonUtilsDefs=null;
	private ManageSetupContent manageSetupContent=null;
	



//*****************************************************************************************************************************************************************	

	@Parameters({ "faadminUser", "faadminPwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {
		
		SaasPaasDrvInstance =  getDriverInstanceObject();
		js=(JavascriptExecutor)SaasPaasDrvInstance;
		aLoginInstance = new ApplicationLogin(SaasPaasDrvInstance);
		dffUtilsInstance = new DFFUtils(SaasPaasDrvInstance);
		ntFInstance = new NavigationTaskFlows(SaasPaasDrvInstance);
		nMEInstance = new NavigatorMenuElements(SaasPaasDrvInstance);
		glbpageInstance=new GlobalPageTemplate(SaasPaasDrvInstance);
		createUserRoleMapInstance=new CreateUserRoleMap(SaasPaasDrvInstance);
		saasPaaS_ScheduleProcessInstance = new SaaSPaaS_ScheduleProcess(SaasPaasDrvInstance);
		manageUserRoleMigrationInstance = new ManageUserRoleMigration(SaasPaasDrvInstance);
		idcsValidationInstance = new IDCSValidation(SaasPaasDrvInstance);
		manageAdminProfileValuesInstance = new ManageAdminProfileValues(SaasPaasDrvInstance);
		//sAMInstance_nTF = new SetupAndMaintenance(SaasPaasDrvInstance);
		//commonUtilsDefs = new CommonUtilsDefs(SaasPaasDrvInstance);
		manageSetupContent = new ManageSetupContent(SaasPaasDrvInstance);
		
		//aLoginInstance.login(username, password, SaasPaasDrvInstance);
		Log.info("Logging into the Application");
		//CommonUtils.hold(10);
	}
	
//**************************************************************************************************************************************************************	
	
	public void navigateToSetupAndMaintenancePage() throws Exception {
		SaasPaasDrvInstance.get(GetDriverInstance.EnvironmentName.replace("FuseWelcome","FuseTaskListManagerTop"));
		Log.info("Navigated to Setup and Maintenance page.");
		CommonUtils.hold(3);
	}
	
//*****************************************************************************************************************************************************************
	
	public void navigateToAOLTask(String AOLTaskName) throws Exception {
		navigateToSetupAndMaintenancePage();
		ntFInstance.navigateToAOLTaskFlows(AOLTaskName, SaasPaasDrvInstance);
		Log.info("Clicked on "+AOLTaskName);
		CommonUtils.waitForPageLoad(SaasPaasDrvInstance);
		SaaSPaaSCommonUtils.waitTillUILoad(30, SaasPaasDrvInstance);
	}

//**************************************************************************************************************************************************************************
	
	@Parameters({"IDCSuser", "IDCSpwd" })
	//@Test(priority=1,description="sample Pre-setup case with all groups.",enabled=true, groups = {"IDCS_PreSetup", "UserProfile_PreSetup", "RoleProfile_PreSetup", "AllProfile_PreSetup"})
	public void sampleCase(String IDCSUser, String IDCSPassword) throws Exception {
		
		System.out.println("Pre-setup cases.");
		SaasPaasDrvInstance.quit();
	}
	

//*****************************************************************************************************************************************************************	
	@Parameters({"IDCSuser", "IDCSpwd" })
	@Test(priority=1,description="This testcase is used to reset the IDCS password.",enabled=true)
	public void resetIDCSPassword(String IDCSUser, String IDCSPassword) throws Exception {
		
		idcsValidationInstance.resetIDCSPassword(IDCSUser, GetDriverInstance.idcsTempPwd, IDCSPassword, SaasPaasDrvInstance);
		

	}			
	
//*****************************************************************************************************************************************************************	
	@Parameters({"IDCSuser", "IDCSpwd" })
	@Test(priority=2,description="This testcase is used to create ATGQAClient application in IDCS.",enabled=true, groups = {"IDCS_PreSetup"})
	public void createATGQAClientApp(String IDCSUser, String IDCSPassword) throws Exception {
		String applicationName = "ATGQAClient";
		
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		String[] clientCredentials = idcsValidationInstance.createATGQAClientApplication(applicationName, SaasPaasDrvInstance);
		
		clientID = clientCredentials[0];
		clientSecret = clientCredentials[1];
		
		//System.out.println("Id: "+clientID);
		//System.out.println("Pwd: "+clientSecret);
		
		if(clientID == null || clientSecret == null) {
			Assert.assertTrue(false, "Client Credentials are null.");
		}

	}			
	
//*****************************************************************************************************************************************************************	
	@Parameters({"faadminUser", "faadminPwd" })
	@Test(priority=3,description="This testcase is used to integrate IDCS application in FA.",enabled=true, groups = {"IDCS_PreSetup"})
	public void integrateIDCSinFA(String username, String password) throws Exception {
		
		aLoginInstance.login(username, password, SaasPaasDrvInstance);
		String applicationName = "IDCS_REST_ENDPOINTAPP";
		
		String fullURL = GetDriverInstance.idcsURL.replace("/ui/v1/adminconsole", "/admin/v1");
		
		navigateToSetupAndMaintenancePage();
		manageSetupContent.navigateToManageSetupContentDefinition("Manage Integration of Additional Applications", SaasPaasDrvInstance);
		Log.info("Clicked on Manage Integration of Additional Applications");
		manageSetupContent.integrateIDCSApplication(applicationName, fullURL, clientID, clientSecret, SaasPaasDrvInstance);
		

	}		
			
//*****************************************************************************************************************************************************************	
	//@Test(priority=4,description="This testcase is used to integrate SIM application in FA.",enabled=true, groups = {"IDCS_PreSetup"})
	public void integrateSIMinFA() throws Exception {
		
		navigateToSetupAndMaintenancePage();
		manageSetupContent.navigateToManageSetupContentDefinition("Manage Integration of Additional Applications", SaasPaasDrvInstance);
		Log.info("Clicked on Manage Integration of Additional Applications");
		manageSetupContent.integrateSIMApplication("SIM_REST_ENDPOINTAPP", SaasPaasDrvInstance);

	}		
 
//*****************************************************************************************************************************************************************
	
	@Test(priority=5,description="This testcase is used to set Sync Target value as IDCS.",enabled=true, groups = {"IDCS_PreSetup"})
	public void setSyncTarget() throws Exception {
		String adminProfile = "FND_USER_IDENTITY_SYNC_TARGET";
		String profileLevel = "Site";
		String profileValue = "IDCS";
		
		SaasPaasDrvInstance.get(GetDriverInstance.EnvironmentName); // If integrateIDCSinFA() method fails, hence navigating to env page again
		navigateToAOLTask("Manage Administrator Profile Values");
		manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);

	}
	
//*****************************************************************************************************************************************************************
	
	@Test(priority=6,description="This testcase is used to set Sync Job Type value as USER.",enabled=true, groups = {"UserProfile_PreSetup"})
	public void setSyncJobType() throws Exception {
		String adminProfile = "FND_SYNC_JOB_TYPE";
		String profileLevel = "Site";
		String profileValue = "USER";
		
		//navigateToAOLTask("Manage Administrator Profile Values");
		manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);

	}		
			
//*****************************************************************************************************************************************************************	
	
	@Test(priority=7,description="This testcase is used to set Max Selected Role size.",enabled=true, groups = {"RoleProfile_PreSetup"})
	public void setMaxRoleSize() throws Exception {
		String adminProfile = "FND_ROLE_SYNC_MAX_SELECTED_ROLES_SIZE";
		String profileLevel = "Site";
		String profileValue = "25";
		
		//navigateToAOLTask("Manage Administrator Profile Values");
		manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);

	}		
			
//*****************************************************************************************************************************************************************	
	@Test(priority=8,description="This testcase is used to set Migration Fetch Batch size.",enabled=true, groups = {"UserProfile_PreSetup"})
	public void setUserBatchSize() throws Exception {
		String adminProfile = "FND_USER_MIGRATION_FETCH_BATCH_SIZE";
		String profileLevel = "Site";
		String profileValue = "35000";
		
		//navigateToAOLTask("Manage Administrator Profile Values");
		manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);

	}		
			
//*****************************************************************************************************************************************************************	
	@Test(priority=9,description="This testcase is used to set Max Retry Attempts.",enabled=true, groups = {"UserProfile_PreSetup"})
	public void setMaxRetry() throws Exception {
		String adminProfile = "FND_USER_MIGRATION_MAX_RETRY_ATTEMPTS";
		String profileLevel = "Site";
		String profileValue = "20";
		
		//navigateToAOLTask("Manage Administrator Profile Values");
		manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);

	}		
			
//*****************************************************************************************************************************************************************	
	@Test(priority=10,description="This testcase is used to set Federation value.",enabled=true, groups = {"UserProfile_PreSetup"})
	public void setFederationValue() throws Exception {
		String adminProfile = "FND_USER_MIGRATION_FA_FEDERATION";
		String profileLevel = "Site";
		String profileValue = "TRUE";
		
		//navigateToAOLTask("Manage Administrator Profile Values");
		manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);

	}		
			
//*****************************************************************************************************************************************************************	
	@Test(priority=11,description="This testcase is used to delete FA Env Name as prefix.",enabled=true, groups = {"RoleProfile_PreSetup"})
	public void deleteEnvAsPrefix() throws Exception {
		String adminProfile = "FND_USER_MIGRATION_FA_ENV_NAME";
		String profileLevel = "Site";
		//String profileValue = "TRUE";
		
		//navigateToAOLTask("Manage Administrator Profile Values");
		manageAdminProfileValuesInstance.deleteAdminProfile(adminProfile, profileLevel, SaasPaasDrvInstance);

	}		
			
//*****************************************************************************************************************************************************************	
	@Test(priority=12,description="This testcase is used to migrate bydefault users which are yet to be synchronized.",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "UserProfile_PreSetup"})
	public void migrateExistingDefaultUsers() throws Exception {
		String processname = "User identity synchronization from this SaaS instance to the PaaS Identity Store";
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 3600, 300, false, SaasPaasDrvInstance);
		navigateToAOLTask("Manage User Identity Synchronization to PaaS Identity Store");
		manageUserRoleMigrationInstance.validateNoUserPresentInUserYetToSyncTab(SaasPaasDrvInstance);	

	}	
	

				
//*****************************************************************************************************************************************************************	

	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {

		try {
			aLoginInstance.logout(SaasPaasDrvInstance);

		} catch (Exception ex) {
			aLoginInstance.launchURL(SaasPaasDrvInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(SaasPaasDrvInstance);
		} 
		
		finally { SaasPaasDrvInstance.quit(); }
		 
	}	
//************************************************************************************************************************************	

}	
