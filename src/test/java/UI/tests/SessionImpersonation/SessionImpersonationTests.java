package UI.tests.SessionImpersonation;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.SessionImpersonation.SessionImpersonationPage;



public class SessionImpersonationTests extends GetDriverInstance{

	static String uniqueID;

	private WebDriver SessionImpersonationTestsInstance;
	private NavigationTaskFlows taskFlowsNavigation = null;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private SessionImpersonationPage SessionImpersonationPageInstance;
	

	/*
	 * public SessionImpersonationTests() throws WebDriverException {
	 * 
	 * }
	 */
	
	@Parameters({"user","pwd"})
	@BeforeMethod(alwaysRun = true)
	public void setUp(String loginname, String loginpwd,@Optional() Method method) throws Exception {
		SessionImpersonationTestsInstance =  getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(SessionImpersonationTestsInstance);
		ntFInstance = new NavigationTaskFlows(SessionImpersonationTestsInstance);
		nMEInstance = new NavigatorMenuElements(SessionImpersonationTestsInstance);
		SessionImpersonationPageInstance = new SessionImpersonationPage(SessionImpersonationTestsInstance);
		if (!((method.getName().equals("addProxyForUser3")) | (method.getName().equals("addProxyForUser4")) | (method.getName().equals("addProxyForUser5")) | (method.getName().equals("ImpersoniseUser5AsUser4")) | (method.getName().equals("ImpersoniseUser3AsUser2")) | (method.getName().equals("ImpersoniseUser2AsUser1")) | (method.getName().equals("ImpersoniseUser4AsUser3")))) {
			uniqueID = CommonUtils.uniqueId();
			System.out.println("Setup()....");
			Log.info("Setup()....");
			Thread.sleep(2);
			aLoginInstance.login(loginname, loginpwd, SessionImpersonationTestsInstance);
			CommonUtils.hold(2);
			System.out.println("Log In successful...");
			Log.info("Log In successful...");
			
		}
	}
	

	//Single level Impersonation

	@Test(priority=1,description="User2 impersonating user1",enabled=true)
	@Parameters({"user1","user2"})
	public void testcase01(@Optional("Gabrielle Lee") String user1, @Optional("CHANNEL_ACCOUNT_MGR") String user2) throws Exception{		
		Log.info("User2 impersonating user1");
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(nMEInstance.SetPreferences, SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPage.proxiesLink.click();
		CommonUtils.hold(2);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPageInstance.searchUser(user2);
		aLoginInstance.logout(SessionImpersonationTestsInstance);
		aLoginInstance.login(user2, "Welcome1", SessionImpersonationTestsInstance);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPageInstance.ImpersoniseUser(user1);
	}

	//Multiple Impersonation
	@Parameters({"user1","user2", "user3"})
	@Test(priority=2,description="Check Multiple Impersonation",enabled=true)
	public void multipleImpersonation(@Optional("Gabrielle Lee") String user1, @Optional("CHANNEL_ACCOUNT_MGR") String user2, @Optional("CHANNEL_OPERATIONS_MGR") String user3) throws Exception{		
		Log.info("multipleImpersonation()....");
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(nMEInstance.SetPreferences, SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPage.proxiesLink.click();
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(2);
		Log.info("search for user2 and add proxy");
		SessionImpersonationPageInstance.searchUser(user2);
		Log.info("search for user3 and add proxy");
		SessionImpersonationPage.proxiesLink.click();
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPageInstance.searchUser(user3);
		SessionImpersonationPage.proxiesLink.click();
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		aLoginInstance.logout(SessionImpersonationTestsInstance);
		Log.info("Login with user2 and impersonate as User1");
		aLoginInstance.login("CHANNEL_ACCOUNT_MGR", "Welcome1", SessionImpersonationTestsInstance);
		Log.info("Impersonize user1");
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPageInstance.ImpersoniseUser(user1);
		aLoginInstance.logout(SessionImpersonationTestsInstance);
		Log.info("Login with user3 and impersonate as User1");
		aLoginInstance.login("CHANNEL_OPERATIONS_MGR", "Welcome1", SessionImpersonationTestsInstance);
		Log.info("Impersonize user1");
		SessionImpersonationPageInstance.ImpersoniseUser(user1);
	}

	//user deleted

	@Test(priority=3,description="Login with User1 and add proxy for User2",enabled=false)
	public void addProxyForUser_5() throws Exception{
		System.out.println("addProxyForUser_5()....");
		Log.info("addProxyForUser_5()....");
		System.out.println("Before navigation");
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		ntFInstance.navigateToTask(nMEInstance.SetPreferences, SessionImpersonationTestsInstance);
		Log.info("Clicked on SetPreferences");
		CommonUtils.hold(2);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPage.proxiesLink.click();
		Log.info("Clicked on Proxies");
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(2);
		//SessionImpersonationWrapperPage.searchUser("CHANNEL_OPERATIONS_MANAGER");
		//SessionImpersonationPage.proxies.click();
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		//String username = DriverInstance.getDriverInstance().findElement(By.xpath("//div[2]/table[@class='af_table_data-table af_table_data-table-VH-lines']/tbody/tr[1]/td[4]")).getText();
		//SessionImpersonationWrapperPage.searchUserAndDelete("CHANNEL_OPERATIONS_MANAGER");
		SessionImpersonationPageInstance.deleteUser("CHANNEL_OPERATIONS_MANAGER");
		aLoginInstance.logout(SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		aLoginInstance.login("CHANNEL_OPERATIONS_MANAGER", "Welcome1", SessionImpersonationTestsInstance);
		Log.info("Impersonize user1");
		SessionImpersonationPageInstance.ImpersoniseUser("Gabrielle Lee");
	}

	//user3 impersonating user2 and user1
	@Parameters({"user1","user2", "user3", "user_2"})
	@Test(priority=4,description="User3 to impersonize as User1 and User2",enabled=true)
	public void addProxyForUser_2And1(@Optional("Gabrielle Lee") String user1, @Optional("CHANNEL_ACCOUNT_MGR") String user2, @Optional("CHANNEL_OPERATIONS_MGR") String user3, @Optional("Channel Account Mgr") String user_2) throws Exception{
		System.out.println("addProxyForUser_2And1()....");
		Log.info("addProxyForUser_2And1()....");
		System.out.println("Before navigation");
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(nMEInstance.SetPreferences, SessionImpersonationTestsInstance);
		Log.info("Clicked on SetPreferences");
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		SessionImpersonationPage.proxiesLink.click();
		Log.info("Clicked on Proxies");
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(2);
		SessionImpersonationPageInstance.searchUser(user3);
		SessionImpersonationPage.proxiesLink.click();
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		aLoginInstance.logout(SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		aLoginInstance.login(user3, "Welcome1", SessionImpersonationTestsInstance);
		Log.info("Impersonize user2 as user1");
		SessionImpersonationPageInstance.ImpersoniseUser(user1);
		CommonUtils.hold(5);
		aLoginInstance.logout(SessionImpersonationTestsInstance);
		aLoginInstance.login(user2, "Welcome1", SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(nMEInstance.SetPreferences, SessionImpersonationTestsInstance);
		Log.info("Clicked on SetPreferences");
		CommonUtils.hold(2);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPage.proxiesLink.click();
		Log.info("Clicked on Proxies");
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(2);
		SessionImpersonationPageInstance.searchUser(user3);
		SessionImpersonationPage.proxiesLink.click();
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		aLoginInstance.logout(SessionImpersonationTestsInstance);
		CommonUtils.hold(2);
		aLoginInstance.login(user3, "Welcome1", SessionImpersonationTestsInstance);
		Log.info("Impersonize user3 as user2");
		SessionImpersonationPageInstance.ImpersoniseUser(user_2);
	}

	//Multilevel Impersonation
	@Parameters({"user2"})
	@Test(priority=5,description="Login with User1 and add proxy for User2",enabled=true)
	public void addProxyForUser2(@Optional("CHANNEL_ACCOUNT_MGR") String user2) throws Exception{
		System.out.println("addProxyForUser2()....");
		Log.info("addProxyForUser2()....");
		System.out.println("Before navigation");
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(nMEInstance.SetPreferences, SessionImpersonationTestsInstance);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		Log.info("Clicked on SetPreferences");
		CommonUtils.hold(5);
		SessionImpersonationPage.proxiesLink.click();
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		Log.info("Clicked on Proxies");
		CommonUtils.hold(2);
		SessionImpersonationPageInstance.searchUser(user2);
		SessionImpersonationPage.proxiesLink.click();
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
	}

	@Parameters({"user2", "user3"})
	@Test(priority=6,description="Login with User2 and add proxy for User3",enabled=true)
	public void addProxyForUser3(@Optional("CHANNEL_ACCOUNT_MGR") String user2, @Optional("CHANNEL_OPERATIONS_MGR") String user3) throws Exception{
		System.out.println("addProxyForUser3()....");
		aLoginInstance.login(user2, "Welcome1", SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(nMEInstance.SetPreferences, SessionImpersonationTestsInstance);
		CommonUtils.hold(2);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(2);
		SessionImpersonationPage.proxiesLink.click();
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(2);
		SessionImpersonationPageInstance.searchUser(user3);
		CommonUtils.hold(2);
	}

	@Parameters({"user3", "user4"})
	@Test(priority=7,description="Login with User3 and add proxy for User4",enabled=true)
	public void addProxyForUser4(@Optional("CHANNEL_OPERATIONS_MGR") String user3, @Optional("CHANNEL_ACCOUNT_MANAGER") String user4) throws Exception{
		System.out.println("addProxyForUser4()....");
		aLoginInstance.login(user3, "Welcome1", SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		ntFInstance.navigateToTask(nMEInstance.SetPreferences, SessionImpersonationTestsInstance);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		SessionImpersonationPage.proxiesLink.click();
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(2);
		SessionImpersonationPageInstance.searchUser(user4);
		CommonUtils.hold(2);
	}

	@Parameters({"user4", "user5"})
	@Test(priority=8,description="Login with User4 and add proxy for User5",enabled=true)
	public void addProxyForUser5(@Optional("CHANNEL_ACCOUNT_MANAGER") String user4, @Optional("CHANNEL_OPERATIONS_MANAGER") String user5) throws Exception{
		System.out.println("addProxyForUser5()....");
		aLoginInstance.login(user4, "Welcome1", SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(nMEInstance.SetPreferences, SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPage.proxiesLink.click();
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.hold(2);
		SessionImpersonationPageInstance.searchUser(user5);
		CommonUtils.hold(2);
	}

	@Parameters({"user5", "user_4"})
	@Test(priority=9, description="Impersonise User5 as User4",enabled=true, dependsOnMethods="addProxyForUser5")
	public void ImpersoniseUser5AsUser4(@Optional("CHANNEL_OPERATIONS_MANAGER") String user5, @Optional("Account_MgrENT1 CAM") String user_4) throws Exception{
		System.out.println("ImpersoniseUser5AsUser4()....");
		aLoginInstance.login(user5, "Welcome1", SessionImpersonationTestsInstance);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPageInstance.ImpersoniseUser(user_4);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		CommonUtils.explicitWait(SessionImpersonationPage.linkUser, "Click", "", SessionImpersonationTestsInstance);
		SessionImpersonationPage.linkUser.click();
	}

	@Parameters({"user3", "user_2"})
	@Test(priority=10, description="Impersonise User3 as User2",enabled=true, dependsOnMethods="addProxyForUser3")
	public void ImpersoniseUser3AsUser2(@Optional("CHANNEL_OPERATIONS_MGR") String user3, @Optional("Channel Account Mgr") String user_2) throws Exception{
		System.out.println("ImpersoniseUser3AsUser2()....");
		aLoginInstance.login(user3, "Welcome1", SessionImpersonationTestsInstance);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPageInstance.ImpersoniseUser(user_2);
		CommonUtils.explicitWait(SessionImpersonationPage.linkUser, "Click", "", SessionImpersonationTestsInstance);
		SessionImpersonationPage.linkUser.click();
	}

	@Parameters({"user2", "user1"})
	@Test(priority=11, description="Impersonise User2 as User1",enabled=true, dependsOnMethods="addProxyForUser2")
	public void ImpersoniseUser2AsUser1(@Optional("CHANNEL_ACCOUNT_MGR") String user2, @Optional("Gabrielle Lee") String user1) throws Exception{
		System.out.println("ImpersoniseUser2AsUser1()....");
		aLoginInstance.login("CHANNEL_ACCOUNT_MGR", "Welcome1", SessionImpersonationTestsInstance);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPageInstance.ImpersoniseUser("Gabrielle Lee");
		CommonUtils.explicitWait(SessionImpersonationPage.linkUser, "Click", "", SessionImpersonationTestsInstance);
		SessionImpersonationPage.linkUser.click();
	}

	@Parameters({"user4", "user_3"})
	@Test(priority=12, description="Impersonise User4 as User3",enabled=true, dependsOnMethods="addProxyForUser4")
	public void ImpersoniseUser4AsUser3(@Optional("CHANNEL_ACCOUNT_MANAGER") String user4, @Optional("Channel_OPS_Mgr_ENT2 COM") String user_3) throws Exception{
		System.out.println("ImpersoniseUser4AsUser3()....");
		aLoginInstance.login(user4, "Welcome1", SessionImpersonationTestsInstance);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		SessionImpersonationPageInstance.ImpersoniseUser(user_3);
		CommonUtils.explicitWait(SessionImpersonationPage.linkUser, "Click", "", SessionImpersonationTestsInstance);
		SessionImpersonationPage.linkUser.click();
	}

	//Same User impersonation
	@Parameters({"user"})
	@Test(priority=13,description="Login with User1 and add proxy for User1",enabled=true)
	public void addProxyForUser1(@Optional("SALES_ADMIN") String user) throws Exception{
		System.out.println("addProxyForUser1()....");
		Log.info("addProxyForUser1()....");
		System.out.println("Before navigation");
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(nMEInstance.SetPreferences, SessionImpersonationTestsInstance);
		CommonUtils.waitForPageLoad(SessionImpersonationTestsInstance);
		Log.info("Clicked on SetPreferences");
		CommonUtils.hold(2);
		SessionImpersonationPage.proxiesLink.click();
		//SessionImpersonationTestsInstanceInstance.waitForPageLoad();
		Log.info("Clicked on Proxies");
		CommonUtils.hold(2);
		SessionImpersonationPageInstance.searchUserOne(user);
	}


	@AfterMethod(alwaysRun=true)
	public void tearDown() throws Exception{
		Log.info("Logout().....");
		aLoginInstance.logout(SessionImpersonationTestsInstance);
		//driverInstanceObject.quit();
		SessionImpersonationTestsInstance.quit();
	}
}
