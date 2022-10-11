package UI.tests.SaaSPaaS;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
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
import pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS.SaaSPaaS_ScheduleProcess;
import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;
import pageDefinitions.UI.oracle.applcore.qa.ds.CreateUserRoleMap;

public class SaaSPaaS extends GetDriverInstance {
	
	WebDriver SaasPaasDrvInstance;
	JavascriptExecutor js;
	String id;
	String globalUserName=null;
	String existingUserName="APP_IMPL_CONSULTANT";
	String globalRole=null;
	String env="BP";
	String processname = "User identity synchronization from this SaaS instance to the PaaS Identity Store";
	
	String existingRole1 = "Sales Analyst";	//"Sales and Operations Planner"
	String existingRole2 = "Application Diagnostics Viewer";
	String existingRole3 = "Application Developer";
	String existingRole4 = "Sales Administrator";	//Sales Manager // Don't use Application Developer as by default it has almost all the generic users.
	String existingRole5 = "Advanced Transaction Controls Analyst";
	
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

	@Parameters({ "hcmUser", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {
		
		id = CommonUtils.uniqueId();
		
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
		
		aLoginInstance.login(username, password, SaasPaasDrvInstance);
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
	}

//***************************************************************************************************************************************************************
	
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
	}
	
//****************************************************************************************************************************************************
	
	public String createUser() throws Exception {
		id = CommonUtils.uniqueId();
		String userLastName = "Name_"+id;
		
		/*ntFInstance.navigateToTask(glbpageInstance.newPerson, SaasPaasDrvInstance);
		Log.info("Clicked on New Person.");
		CommonUtils.waitForPageLoad(SaasPaasDrvInstance);
		CommonUtils.hold(10);*/
		dffUtilsInstance.navigateToHireAnEmployee(userLastName, "GBI_GHR_3TSTMA","" , "", SaasPaasDrvInstance);
		Log.info(userLastName+" User created.");
		glbpageInstance.homeIcon.click();
		CommonUtils.waitForPageLoad(SaasPaasDrvInstance);
		CommonUtils.hold(5);
		
		return userLastName;
	}

//*********************************************************************************************************************************************************************
	
	public void navigateToSecurityConsole(WebDriver driver) throws Exception {
		ntFInstance.navigateToTask(glbpageInstance.securityConsole, SaasPaasDrvInstance);
		Log.info("Clicked on Security Console.");
		//CommonUtils.waitForPageLoad(SaasPaasDrvInstance);
		//CommonUtils.hold(10);
		CommonUtils.waitForInvisibilityOfElement(glbpageInstance.securityConsole, driver, 15);
		createUserRoleMapInstance.manageSecurityConsoleRolesWarningBox(SaasPaasDrvInstance);
	}

//*********************************************************************************************************************************************************************************
	
	public void launchURL(WebDriver driver) throws Exception {
		try {
			driver.get(GetDriverInstance.EnvironmentName);
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(5);
		} catch (Exception e) {
			System.out.println("Exception in launchURL() ");
			e.printStackTrace();
		}
	}
	

//*****************************************************************************************************************************************************************	
																										
	@Parameters({ "IDCSuser", "IDCSpwd" })
	@Test(priority=13,description="This testcase is used for migrate the new user to IDCS, Also validate the Federation status.",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "UserProfile_PreSetup"})
	public void testcase01(String IDCSUser, String IDCSPassword) throws Exception {
		
		String userLastName;
		try {
			userLastName = createUser(); 
		} catch (Exception e){
			System.out.println("Again creating the user.");
			userLastName = createUser();
		}
		globalUserName = userLastName;
		System.out.println("Global User Name is: "+globalUserName);
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		String email = userLastName+"@oracle.com";
		createUserRoleMapInstance.editUserEmail(userLastName, email, SaasPaasDrvInstance);
		Log.info("User email has been edited.");
		//CommonUtils.hold(5);
		
		//Manage User Identity
		navigateToAOLTask("Manage User Identity Synchronization to PaaS Identity Store");
		manageUserRoleMigrationInstance.validateUserInUserYetToSyncTab(userLastName, SaasPaasDrvInstance);	
		Log.info("User is validated in User Yet to Synchronize tab.");
		
		//ESS Job
		//SaasPaasDrvInstance.get(GetDriverInstance.EnvironmentName.replaceAll("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_scheduled_processes_fuse_plus"));
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 600, 10, false, SaasPaasDrvInstance);
		Log.info("ESS Job is completed successfully.");
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyUserEmailInIDCS(userLastName, email, SaasPaasDrvInstance);
		Log.info("User Email verified in IDCS.");
		idcsValidationInstance.verifyFederated(userLastName, "true", SaasPaasDrvInstance);
		Log.info("Federated status verified in IDCS.");
		
	}
	
	
//*****************************************************************************************************************************************************************
	
	@Parameters({ "IDCSuser", "IDCSpwd" })
	@Test(priority=14,description="This test case is used for update the User then migrate to IDCS",enabled=true, dependsOnMethods = {"testcase01"}, dependsOnGroups = {"IDCS_PreSetup", "UserProfile_PreSetup"})
	public void testcase02(String IDCSUser, String IDCSPassword) throws Exception {
		id = CommonUtils.uniqueId();
		//String userName = "APP_IMPL_CONSULTANT";
		launchURL(SaasPaasDrvInstance);
		
		String userName = globalUserName;
		if(userName.equals(null)) {
			Assert.assertTrue(false, "User is not created. User Name is null.");
			
		}
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		String email = userName+"_"+id+"@oracle.com";
		createUserRoleMapInstance.editUserEmail(userName, email, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		
		//Manage User Identity
		navigateToAOLTask("Manage User Identity Synchronization to PaaS Identity Store");
		manageUserRoleMigrationInstance.validateUserInUserYetToSyncTab(userName, SaasPaasDrvInstance);
		manageUserRoleMigrationInstance.validateUsersUpdatedEmailInUserYetToSyncTab(userName, email, SaasPaasDrvInstance);
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 600, 10, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyUserEmailInIDCS(userName, email, SaasPaasDrvInstance);
		Log.info("Email verified in IDCS.");
		
	}
	
		
//*****************************************************************************************************************************************************************
	
	@Parameters({ "IDCSuser", "IDCSpwd" })
	@Test(priority=15,description="This test case is used for deactivate the User then migrate to IDCS",enabled=true, dependsOnMethods = {"testcase01"}, dependsOnGroups = {"IDCS_PreSetup", "UserProfile_PreSetup"})
	public void testcase03(String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);
		//String userName = "APP_IMPL_CONSULTANT";
		launchURL(SaasPaasDrvInstance);
		
		String userName = globalUserName;
		if(userName.equals(null)) {
			Assert.assertTrue(false, "User is not created. User Name is null.");
			
		}
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		createUserRoleMapInstance.DeactivateFAUser(userName, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		
		//Manage User Identity
		navigateToAOLTask("Manage User Identity Synchronization to PaaS Identity Store");
		manageUserRoleMigrationInstance.validateUserInUserYetToSyncTab(userName, SaasPaasDrvInstance);
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 600, 10, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyUserIsDeactivated(userName, SaasPaasDrvInstance);
		Log.info("User Deactivation verified in IDCS.");
		
	}
	

//*****************************************************************************************************************************************************************
	
	@Parameters({ "IDCSuser", "IDCSpwd" })
	@Test(priority=16,description="This test case is used for activate the User then migrate to IDCS",enabled=true, dependsOnMethods = {"testcase03"}, dependsOnGroups = {"IDCS_PreSetup", "UserProfile_PreSetup"})
	public void testcase04(String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);
		//String userName = "APP_IMPL_CONSULTANT";
		launchURL(SaasPaasDrvInstance);
		
		String userName = globalUserName;
		if(userName.equals(null)) {
			Assert.assertTrue(false, "User is not created. User Name is null.");
			
		}
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		createUserRoleMapInstance.ActivateFAUser(userName, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		
		//Manage User Identity
		navigateToAOLTask("Manage User Identity Synchronization to PaaS Identity Store");
		manageUserRoleMigrationInstance.validateUserInUserYetToSyncTab(userName, SaasPaasDrvInstance);
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 600, 10, false, SaasPaasDrvInstance);
		
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyUserIsActivated(userName, SaasPaasDrvInstance);
		Log.info("User Activation verified in IDCS.");
		
		//SaasPaasDrvInstance.close();
		
	}

//************************************************************************************************************************************************************	
	
	@Parameters({ "faadminUser", "faadminPwd", "IDCSuser", "IDCSpwd" })
	@Test(priority=17,description="This test case is used for migrate the existing User to IDCS",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "UserProfile_PreSetup"})
	public void testcase05(String user, String pwd, String IDCSUser, String IDCSPassword) throws Exception {
		
		id = CommonUtils.uniqueId();
		launchURL(SaasPaasDrvInstance);
		logOut();
		aLoginInstance.login(user, pwd, SaasPaasDrvInstance);
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		
		String userName = existingUserName;
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		String email = userName+"_"+id+"@oracle.com";
		createUserRoleMapInstance.editUserEmail(userName, email, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		
		//Manage User Identity
		navigateToAOLTask("Manage User Identity Synchronization to PaaS Identity Store");
		manageUserRoleMigrationInstance.validateUserInUserYetToSyncTab(userName, SaasPaasDrvInstance);
		manageUserRoleMigrationInstance.validateUsersUpdatedEmailInUserYetToSyncTab(userName, email, SaasPaasDrvInstance);
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 600, 10, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyUserEmailInIDCS(userName, email, SaasPaasDrvInstance);
		Log.info("Email verified in IDCS.");
		
	}
	
		
//*****************************************************************************************************************************************************************
	
	@Parameters({ "hcmUser", "pwd", "IDCSuser", "IDCSpwd" })
	@Test(priority=18,description="This testcase is used for migrate the new user to IDCS when FEDERATION is FALSE",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "UserProfile_PreSetup"})
	public void testcase06(String user, String pwd, String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		
		logOut();
		aLoginInstance.login(user, pwd, SaasPaasDrvInstance);
		Log.info("Logging into the Application");
		CommonUtils.hold(10);
		
		String adminProfile = "FND_USER_MIGRATION_FA_FEDERATION";
		String profileLevel = "Site";
		String profileValue = "FALSE";
		
		navigateToAOLTask("Manage Administrator Profile Values");
		manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);
		
		String userLastName = createUser();
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		String email = userLastName+"@oracle.com";
		createUserRoleMapInstance.editUserEmail(userLastName, email, SaasPaasDrvInstance);
		Log.info("User email has been edited.");
		CommonUtils.hold(5);
		
		//Manage User Identity
		navigateToAOLTask("Manage User Identity Synchronization to PaaS Identity Store");
		manageUserRoleMigrationInstance.validateUserInUserYetToSyncTab(userLastName, SaasPaasDrvInstance);		
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 600, 10, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyFederated(userLastName, "false", SaasPaasDrvInstance);
		Log.info("Federated status verified in IDCS.");
		
		//assigning user to globaluser for the next test cases.
		globalUserName=userLastName;
		System.out.println("New Global User is: "+globalUserName);
		
		//SaasPaasDrvInstance.close();
	}
	

//*****************************************************************************************************************************************************************
	@Test(priority=19,description="This testcase is used to set Sync Job Type value as ROLE.",enabled=true, groups = {"RoleProfile_PreSetup"})
	public void testcase07() throws Exception {
		launchURL(SaasPaasDrvInstance);
		
		String adminProfile = "FND_SYNC_JOB_TYPE";
		String profileLevel = "Site";
		String profileValue = "ROLE";
		
		navigateToAOLTask("Manage Administrator Profile Values");
		manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);

	}			
				
//*****************************************************************************************************************************************************************	
		
	@Parameters({ "IDCSuser", "IDCSpwd" })
	@Test(priority=20,description="This testcase is used for add existing role to newly created user and migrate to IDCS",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "RoleProfile_PreSetup"})
	public void testcase08(String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);

		launchURL(SaasPaasDrvInstance);
		String roleName = existingRole1; 	//"Sales Analyst";	//Sales and Operations Planner
		String userLastName = globalUserName;
		
		if(userLastName.equals(null)) {
			//Assert.assertTrue(false, "User is not created. User Name is null.");
			
			//Set Admin Profile to User
			String adminProfile = "FND_SYNC_JOB_TYPE";
			String profileLevel = "Site";
			String profileValue = "USER";
			navigateToAOLTask("Manage Administrator Profile Values");
			manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);		
		
			//Create User
			userLastName = createUser();
			
			//Edit user email
			navigateToSecurityConsole(SaasPaasDrvInstance);
			String email = userLastName+"@oracle.com";
			createUserRoleMapInstance.editUserEmail(userLastName, email, SaasPaasDrvInstance);
			createUserRoleMapInstance.addExistingRoleToUser(userLastName, roleName, SaasPaasDrvInstance);
			CommonUtils.hold(5);
			
			//Manage User Identity
			navigateToAOLTask("Manage User Identity Synchronization to PaaS Identity Store");
			manageUserRoleMigrationInstance.validateUserInUserYetToSyncTab(userLastName, SaasPaasDrvInstance);
			
			//ESS Job
			ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
			CommonUtils.hold(5);
			saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
			
			//Set Admin Profile to Role
			adminProfile = "FND_SYNC_JOB_TYPE";
			profileLevel = "Site";
			profileValue = "ROLE";
			navigateToAOLTask("Manage Administrator Profile Values");
			manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);
			
			globalUserName=userLastName;
			System.out.println("Newly created Global User is: "+globalUserName);
			
		}else {
			//Add role to user
			navigateToSecurityConsole(SaasPaasDrvInstance);
			createUserRoleMapInstance.addExistingRoleToUser(userLastName, roleName, SaasPaasDrvInstance);
			CommonUtils.hold(5);
		}
		
		//Manage Role Identity
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		manageUserRoleMigrationInstance.addRoleinMigrateRolesPage(roleName, SaasPaasDrvInstance);	
		
		//ESS JOb
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyUserWithRole(userLastName, roleName, SaasPaasDrvInstance);
		
		//SaasPaasDrvInstance.close();
		
	}
	
	
//*****************************************************************************************************************************************************************
	
	@Parameters({ "IDCSuser", "IDCSpwd" })
	@Test(priority=21,description="This testcase is used for create new role and migrate to IDCS",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "RoleProfile_PreSetup"})
	public void testcase09(String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		navigateToSecurityConsole(SaasPaasDrvInstance);
		String role = createUserRoleMapInstance.createRole(SaasPaasDrvInstance, "", "");
		Log.info("Role has been created.");
		CommonUtils.hold(5);
		globalRole = role;
		
		//Manage Role Identity
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		manageUserRoleMigrationInstance.addRoleinMigrateRolesPage(role, SaasPaasDrvInstance);		
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyRole(role, SaasPaasDrvInstance);
		
		//SaasPaasDrvInstance.close();
		
	}


//*****************************************************************************************************************************************************************
	
	@Parameters({ "IDCSuser", "IDCSpwd" })
	@Test(priority=22,description="This testcase is used for update the Role and migrate to IDCS",enabled=true, dependsOnMethods = {"testcase09"}, dependsOnGroups = {"IDCS_PreSetup", "RoleProfile_PreSetup"})
	public void testcase10(String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		String role = globalRole;
		System.out.println("Role is: "+role);
		if(role.equals(null)) {
			Assert.assertTrue(false, "Role is not created. Role Name is null.");
			
		}
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		String roleDesc = "RoleDesc"+id;
		createUserRoleMapInstance.updateRoleDescription(role, roleDesc, SaasPaasDrvInstance);
		Log.info("Role Description has been updated.");
		CommonUtils.hold(5);
		
		//Manage Role Identity
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		manageUserRoleMigrationInstance.addRoleinMigrateRolesPage(role, SaasPaasDrvInstance);		
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyRoleDescription(role, roleDesc, SaasPaasDrvInstance);
		Log.info("Role Description has been verified.");
		
		//SaasPaasDrvInstance.close();
		
	}
	
	
//*****************************************************************************************************************************************************************
	
	@Parameters({ "IDCSuser", "IDCSpwd" })
	@Test(priority=23,description="This testcase is used for add existing User to newly created Role and migrate to IDCS",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "RoleProfile_PreSetup"})
	public void testcase11(String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		String userName = existingUserName;
		String role = globalRole;
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		createUserRoleMapInstance.addUserToRole(role, userName, SaasPaasDrvInstance);
		Log.info("User is added to the Role.");
		CommonUtils.hold(5);
		
		//Manage Role Identity
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		manageUserRoleMigrationInstance.addRoleinMigrateRolesPage(role, SaasPaasDrvInstance);		
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyRoleWithUser(userName, role, SaasPaasDrvInstance);
		
		//SaasPaasDrvInstance.close();
		
	}
	
//*****************************************************************************************************************************************************************
	
	@Parameters({ "IDCSuser", "IDCSpwd" })
	@Test(priority=24,description="This testcase is used for existing Role migration to IDCS",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "RoleProfile_PreSetup"})
	public void testcase12(String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		String role = existingRole2; 	//"Application Diagnostics Viewer";
		
		if(manageUserRoleMigrationInstance.isRolePresentInMigrateRolesPage(role, SaasPaasDrvInstance)) {
			role = "Sales Administrator";
			if(manageUserRoleMigrationInstance.isRolePresentInMigrateRolesPage(role, SaasPaasDrvInstance)) {
				role = "Benefits Administrator - Vision Operations";
				if(manageUserRoleMigrationInstance.isRolePresentInMigrateRolesPage(role, SaasPaasDrvInstance)) {
					role = "Budget Analyst";
					if(manageUserRoleMigrationInstance.isRolePresentInMigrateRolesPage(role, SaasPaasDrvInstance)) {
						role = "Quality Analyst";
					}	
				}
			}
		}
		
		System.out.println("Role is: "+role);
		manageUserRoleMigrationInstance.addRoleinMigrateRolesPage(role, SaasPaasDrvInstance);
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyRole(role, SaasPaasDrvInstance);
		
		//SaasPaasDrvInstance.close();	
		
	}
	

//*****************************************************************************************************************************************************************
	
	@Parameters({ "IDCSuser", "IDCSpwd" })
	@Test(priority=25,description="This testcase is used for delete the existing role in Manage Role Identity list and migrate to IDCS",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "RoleProfile_PreSetup"})
	public void testcase13(String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		String role = existingRole2; 	//"Application Diagnostics Viewer";
		
		//Manage Role Identity
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		manageUserRoleMigrationInstance.deleteRoleInMigrateRolesPage(role, SaasPaasDrvInstance);
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyRoleIsNotPresent(role, SaasPaasDrvInstance);
		
		//SaasPaasDrvInstance.close();
		
	}
	
	
//*****************************************************************************************************************************************************************
	
	@Parameters({ "hcmUser", "pwd" })
	@Test(priority=26,description="This testcase is used to set Sync Job Type value as ALL.",enabled=true, groups = {"AllProfile_PreSetup"})
	public void testcase14(String username, String password) throws Exception {
		
		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		
		String adminProfile = "FND_SYNC_JOB_TYPE";
		String profileLevel = "Site";
		String profileValue = "ALL";
		
		navigateToAOLTask("Manage Administrator Profile Values");
		manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);
		
		//SaasPaasDrvInstance.close();

	}			
				
//*****************************************************************************************************************************************************************	
				
	@Parameters({"IDCSuser", "IDCSpwd" })
	@Test(priority=27,description="This testcase is used for create new User and new Role and migrate to IDCS",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "AllProfile_PreSetup"})
	public void testcase15(String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);

		String userName = createUser(); 
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		String email = userName+"@oracle.com";
		createUserRoleMapInstance.editUserEmail(userName, email, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		
		//Create New Role
		String role = createUserRoleMapInstance.createRole(SaasPaasDrvInstance, "", userName);
		Log.info("Role has been created.");
		CommonUtils.hold(5);
		
		
		//Manage User Identity
		navigateToAOLTask("Manage User Identity Synchronization to PaaS Identity Store");
		manageUserRoleMigrationInstance.validateUserInUserYetToSyncTab(userName, SaasPaasDrvInstance);
		
		//Manage Role Identity
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		manageUserRoleMigrationInstance.addRoleinMigrateRolesPage(role, SaasPaasDrvInstance);
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyRoleWithUser(userName, role, SaasPaasDrvInstance);
		Log.info("Role with User verified.");
		
		//SaasPaasDrvInstance.close();
		
	}
	
	
//*****************************************************************************************************************************************************************
	
	@Parameters({"IDCSuser", "IDCSpwd" })
	@Test(priority=28,description="This testcase is used for create New User and add existing Role and migrate to IDCS",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "AllProfile_PreSetup"})
	public void testcase16(String IDCSUser, String IDCSPassword) throws Exception {

		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		String userName = createUser(); 
		String role = existingRole3; 	//"Application Developer";
		
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		String email = userName+"@oracle.com";
		createUserRoleMapInstance.editUserEmail(userName, email, SaasPaasDrvInstance);
		createUserRoleMapInstance.addExistingRoleToUser(userName, role, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		
		//Manage User Identity
		navigateToAOLTask("Manage User Identity Synchronization to PaaS Identity Store");
		manageUserRoleMigrationInstance.validateUserInUserYetToSyncTab(userName, SaasPaasDrvInstance);
		
		//Manage Role Identity
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		manageUserRoleMigrationInstance.addRoleinMigrateRolesPage(role, SaasPaasDrvInstance);
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyUserWithRole(userName, role, SaasPaasDrvInstance);
		Log.info("User with Role verified.");
		
		//SaasPaasDrvInstance.close();
		
	}
	
	
//*****************************************************************************************************************************************************************
	
	@Parameters({"IDCSuser", "IDCSpwd" })
	@Test(priority=29,description="This testcase is used for create new Role and add existing User and migrate to IDCS",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "AllProfile_PreSetup"})
	public void testcase17(String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		String userName = existingUserName; 
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		
		//Create New Role
		String role = createUserRoleMapInstance.createRole(SaasPaasDrvInstance, "", userName);
		Log.info("Role has been created.");
		CommonUtils.hold(5);
		
		//Manage Role Identity
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		manageUserRoleMigrationInstance.addRoleinMigrateRolesPage(role, SaasPaasDrvInstance);	
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyRoleWithUser(userName, role, SaasPaasDrvInstance);
		
		//SaasPaasDrvInstance.close();
	}
	

//*****************************************************************************************************************************************************************
	
	@Parameters({"IDCSuser", "IDCSpwd" })
	@Test(priority=30,description="This testcase is used for migrate existing Role and existing User association to IDCS",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "AllProfile_PreSetup"})
	public void testcase18(String IDCSUser, String IDCSPassword) throws Exception {

		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		String userName;
		String[] userList = new String[] { existingUserName, "CVBUYER01", "IT_SECURITY_MGR", "CVBUYER01_ACC", "HCM_USER10" };
		String role = existingRole4; 	//"Sales Administrator";	//Sales Manager // Don't use Application Developer as by default it has almost all the generic users.
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		
		userName = createUserRoleMapInstance.validateUserAlreadyAddedToRole(role, userList, SaasPaasDrvInstance);
		System.out.println("User going to be added: "+userName);
		
		//Validating this user is present in IDCS, Otherwise add it.
		if(userName != "") {
			idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
			try {
				idcsValidationInstance.verifyUserIsActivated(userName, SaasPaasDrvInstance);
				launchURL(SaasPaasDrvInstance);
				navigateToSecurityConsole(SaasPaasDrvInstance);
			} catch (NoSuchElementException e) {
				launchURL(SaasPaasDrvInstance);
				logOut();
				aLoginInstance.login("faadmin", "Fusionapps1", SaasPaasDrvInstance);
				Log.info("Logging into the Application");
				CommonUtils.hold(10);
				navigateToSecurityConsole(SaasPaasDrvInstance);
				String email = userName+"_"+id+"@oracle.com";
				createUserRoleMapInstance.editUserEmail(userName, email, SaasPaasDrvInstance);
				CommonUtils.hold(5);
			} catch (AssertionError e) {
				launchURL(SaasPaasDrvInstance);
				logOut();
				aLoginInstance.login("faadmin", "Fusionapps1", SaasPaasDrvInstance);
				Log.info("Logging into the Application");
				CommonUtils.hold(10);
				navigateToSecurityConsole(SaasPaasDrvInstance);
				createUserRoleMapInstance.ActivateFAUser(userName, SaasPaasDrvInstance);
				CommonUtils.hold(5);
			}
		}
		
		if(userName != "") {
			createUserRoleMapInstance.addUserToRole(role, userName, SaasPaasDrvInstance);
		}
		else {
			Assert.assertTrue(false, "All the listed users are already added to the Role.");
		}
		
		//Manage Role Identity
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		manageUserRoleMigrationInstance.addRoleinMigrateRolesPage(role, SaasPaasDrvInstance);
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyRoleWithUser(userName, role, SaasPaasDrvInstance);
		
		//SaasPaasDrvInstance.close();
	}
		
	
//*****************************************************************************************************************************************************************
	
	@Parameters({"IDCSuser", "IDCSpwd" })
	@Test(priority=31,description="This testcase is used for create new Role when FA_ENV_NAME is prefixed and migrate to IDCS ",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "AllProfile_PreSetup"})
	public void testcase19(String IDCSUser, String IDCSPassword) throws Exception {
	
		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		String adminProfile = "FND_USER_MIGRATION_FA_ENV_NAME";
		String profileLevel = "Site";
		String profileValue = env;
		
		navigateToAOLTask("Manage Administrator Profile Values");
		manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		
		//Create New Role
		String role = createUserRoleMapInstance.createRole(SaasPaasDrvInstance, "", "");
		Log.info("Role has been created.");
		CommonUtils.hold(5);
		
		//Manage Role Identity
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		manageUserRoleMigrationInstance.addRoleinMigrateRolesPage(role, SaasPaasDrvInstance);
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyRole(profileValue+"_"+role, SaasPaasDrvInstance);
		Log.info("Role has been verified.");
		
		//SaasPaasDrvInstance.close();
	}
	

//*****************************************************************************************************************************************************************
	
	@Parameters({"IDCSuser", "IDCSpwd" })
	@Test(priority=32,description="This testcase is used for migrate existing Role and assign existing user when FA_ENV_NAME is prefixed to IDCS.",enabled=true, dependsOnGroups = {"IDCS_PreSetup", "AllProfile_PreSetup"})
	public void testcase20(String IDCSUser, String IDCSPassword) throws Exception {
		
		//startUP(username, password);
		launchURL(SaasPaasDrvInstance);
		//String adminProfile = "FND_USER_MIGRATION_FA_ENV_NAME";
		//String profileLevel = "Site";
		String profileValue = env;
		
		//navigateToAOLTask("Manage Administrator Profile Values");
		//manageAdminProfileValuesInstance.setProfileValue(adminProfile, profileLevel, "", profileValue, SaasPaasDrvInstance);
		
		String userName = existingUserName;
		String role = existingRole5; 	//"Advanced Transaction Controls Analyst";
		
		navigateToSecurityConsole(SaasPaasDrvInstance);
		createUserRoleMapInstance.addUserToRole(role, userName, SaasPaasDrvInstance);
		CommonUtils.hold(5);

		//Manage Role Identity
		navigateToAOLTask("Migrate Enterprise Roles and Assignments to PaaS Identity Store");
		manageUserRoleMigrationInstance.addRoleinMigrateRolesPage(role, SaasPaasDrvInstance);
		
		//ESS Job
		ntFInstance.navigateToTask(glbpageInstance.scheduledProcesses, SaasPaasDrvInstance);
		CommonUtils.hold(5);
		saasPaaS_ScheduleProcessInstance.createEssJobForSaaSPaaSMigration(processname, 1200, 20, false, SaasPaasDrvInstance);
		
		//IDCS validation
		idcsValidationInstance.loginToIDCS(IDCSUser, IDCSPassword, SaasPaasDrvInstance);
		idcsValidationInstance.verifyRoleWithUser(userName, profileValue+"_"+role, SaasPaasDrvInstance);
		Log.info("Role with User has been verified.");
		
		//SaasPaasDrvInstance.close();
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
	}
//************************************************************************************************************************************	

}																								
	