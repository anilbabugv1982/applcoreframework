/*package oracle.applcore.qa.tests;

import java.util.HashMap;
import org.testng.Assert;


import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import oracle.applcore.qa.common.CommonUtils;
import oracle.applcore.qa.stringeditor.SEManageSandbox;
import oracle.applcore.qa.stringeditor.SearchAndReplace;
import oracle.applcore.qa.setup.DriverInstance;

public class SESandboxTests {
	private String sbTestName = null;
	private String stgEditorName = null;
	private HashMap<SEManageSandbox.SearchReplaceTypes, String> flow1ValuesMap = new HashMap<SEManageSandbox.SearchReplaceTypes, String>();
	private HashMap<SEManageSandbox.SearchReplaceTypes, String> flow2ValuesMap = new HashMap<SEManageSandbox.SearchReplaceTypes, String>();
	private HashMap<SEManageSandbox.SearchReplaceTypes, String> flow1ValuesMap_AC = new HashMap<SEManageSandbox.SearchReplaceTypes, String>();
	private HashMap<SEManageSandbox.SearchReplaceTypes, String> flow2ValuesMap_AC = new HashMap<SEManageSandbox.SearchReplaceTypes, String>();
	
	private HashMap<String, String> valuesMaps = new HashMap<String, String>();
	public SESandboxTests() {
		// TODO Auto-generated constructor stub
	}
	public static String UpdatableText="Testapplcore";
	public static String ExistingText="My Photo";
	
	private void commonCode() {
		SEManageSandbox.goToHomePage();
		System.out.println("Step1: Click on navigation");
		CommonUtils.hold(10);
		PageTemplateFusePlus.navigatorButton.click();
		System.out.println("Step2: Click on User Interface Text");
		CommonUtils.hold(8);
		PageTemplateFusePlus.userInterfaceText.click();
		CommonUtils.hold(12);

	}
	private void buildValueMaps() {
//     	buildTestFlowMaps();
		valuesMaps.put("Proxies1234", "Proxies");
		valuesMaps.put("Proxies", "Proxies1234");
		valuesMaps.put("About This Application", "About This Application1234");
		valuesMaps.put("About This Application1234", "About This Application");
		valuesMaps.put("Reports and Analytics", "Reports and Analytics1234");
		valuesMaps.put("Reports and Analytics1234", "Reports and Analytics");
		valuesMaps.put("Scheduled Processes", "Scheduled Processes1234");
		valuesMaps.put("Scheduled Processes1234", "Scheduled Processes");
		valuesMaps.put("An invalid descriptive", "An invalid descriptive1234");
		valuesMaps.put("An invalid descriptive1234", "An invalid descriptive");
		valuesMaps.put("Preferences", "Preferences1234");
		valuesMaps.put("Preferences1234", "Preferences");

	}  
	
	private void buildTestFlowMaps() {
		flow1ValuesMap.put(SEManageSandbox.SearchReplaceTypes.USER_INTERFACE, "Proxies");
		flow2ValuesMap.put(SEManageSandbox.SearchReplaceTypes.USER_INTERFACE, "About This Application");
		flow1ValuesMap.put(SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU, "Reports and Analytics");
		flow2ValuesMap.put(SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU, "Scheduled Processes");
		flow1ValuesMap_AC.put(SEManageSandbox.SearchReplaceTypes.MUTI_PART, "An invalid descriptive");
		flow1ValuesMap.put(SEManageSandbox.SearchReplaceTypes.ALL, "Preferences");
		
	}
	
	private void activateAndValidateChangesinOldSandbox() {
	   PageTemplateFusePlus.homeButton.click();
		SEManageSandbox.clickUserMenuAndActivateSandboxNEW(sbTestName);
		CommonUtils.hold(10);
		Assert.assertEquals(flow1ValuesMap.get(SEManageSandbox.SearchReplaceTypes.USER_INTERFACE),SEManageSandbox.getValueForUserInterfaceTextFlow1());
		System.out.println("Step1: Click on navigation");
		SEManageSandbox.goToHomePage();
		Assert.assertEquals(flow2ValuesMap.get(SEManageSandbox.SearchReplaceTypes.USER_INTERFACE),SEManageSandbox.getValueForUserInterfaceTextFlow2());
		SEManageSandbox.goToHomePage();
		Assert.assertEquals(flow1ValuesMap.get(SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU),SEManageSandbox.getValueForGlobalMenuLabelTextFlow1());
		SEManageSandbox.goToHomePage();
		Assert.assertEquals(flow2ValuesMap.get(SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU),SEManageSandbox.getValueForGlobalMenuLabelTextFlow2());
		SEManageSandbox.goToHomePage();
		Assert.assertEquals(flow1ValuesMap_AC.get(SEManageSandbox.SearchReplaceTypes.MUTI_PART),SEManageSandbox.getValueForMultiPartTextFlow1());
		SEManageSandbox.goToHomePage();
		Assert.assertEquals(flow1ValuesMap.get(SEManageSandbox.SearchReplaceTypes.ALL),SEManageSandbox.getValueForAll());
		}
	
	@Parameters({ "uname", "pwd" })
    @BeforeClass
    public void startUP(String username, String password) throws Exception {
		buildValueMaps();
		System.out.println(System.getProperty("user.dir")+"\\src\\test\\resources\\MyExtentReport.html");
    CommonUtils.login(username, password);
    CommonUtils.hold(5);
    }
   
	@Test(priority=1,description="This testcase is for activating sandbox")
	public void testcase01() throws Exception {
		sbTestName = SEManageSandbox.sandboxcreate("SBTest");               //Create Sandbox
		SEManageSandbox.ActivateSandbox(sbTestName);             //Activate Sandbox
	    SEManageSandbox.exitSandbox();
		stgEditorName = SEManageSandbox.sandboxcreate("STGEditorTest");               //Create Sandbox
		SEManageSandbox.ActivateSandbox(stgEditorName);             //Activate Sandbox
	}

	@Test(priority=2,description="This testcase is to test Search and Replace in sandbox")
	public void testcase02() throws Exception {
		String currentValue =  SEManageSandbox.getValueForUserInterfaceTextFlow1();
		flow1ValuesMap.put(SEManageSandbox.SearchReplaceTypes.USER_INTERFACE,currentValue);  
		commonCode();
		CommonUtils.hold(8);
		SEManageSandbox.SearchAndReplaceStrings(currentValue, valuesMaps.get(currentValue) , "","", SEManageSandbox.SearchReplaceTypes.USER_INTERFACE);
		PageTemplateFusePlus.homeButton.click();
		CommonUtils.hold(10);
		 String valueAfterChange = SEManageSandbox.getValueForUserInterfaceTextFlow1();
		 flow1ValuesMap_AC.put(SEManageSandbox.SearchReplaceTypes.USER_INTERFACE,valueAfterChange);
		Assert.assertEquals(valuesMaps.get(currentValue),valueAfterChange);
	}

	@Test(priority=3,description="This testcase is to test Search and Replace in sandbox")
	public void testcase03() throws Exception {
	    PageTemplateFusePlus.homeButton.click();
		String currentValue =  SEManageSandbox.getValueForUserInterfaceTextFlow2();
		flow2ValuesMap.put(SEManageSandbox.SearchReplaceTypes.USER_INTERFACE,currentValue); 
		commonCode();
		CommonUtils.hold(8);
		SEManageSandbox.SearchAndReplaceStrings(currentValue, valuesMaps.get(currentValue), "","", SEManageSandbox.SearchReplaceTypes.USER_INTERFACE);
		PageTemplateFusePlus.homeButton.click();
		CommonUtils.hold(10);
		 String valueAfterChange = SEManageSandbox.getValueForUserInterfaceTextFlow2();
		 flow2ValuesMap_AC.put(SEManageSandbox.SearchReplaceTypes.USER_INTERFACE,valueAfterChange);
		Assert.assertEquals(valuesMaps.get(currentValue), valueAfterChange);
	}
   @Test(priority=4,description="This testcase is to test Search and Replace in sandbox")
	public void testcase04() throws Exception {
		SEManageSandbox.goToHomePage();
		String currentValue =  SEManageSandbox.getValueForGlobalMenuLabelTextFlow1();
		flow1ValuesMap.put(SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU,currentValue); 
		commonCode();
		System.out.println("Step1: Click on navigation");
		CommonUtils.hold(8);
		SEManageSandbox.SearchAndReplaceStrings(currentValue, valuesMaps.get(currentValue), "","", SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU);
		PageTemplateFusePlus.homeButton.click();
		CommonUtils.hold(10);
		String valueAfterChange = SEManageSandbox.getValueForGlobalMenuLabelTextFlow1();
		flow1ValuesMap_AC.put(SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU,valueAfterChange);
		Assert.assertEquals(valuesMaps.get(currentValue), valueAfterChange);	
		}
	
	@Test(priority=5,description="This testcase is to test Search and Replace in sandbox") 
		public void testcase05() throws Exception {
		SEManageSandbox.goToHomePage();
		String currentValue =  SEManageSandbox.getValueForGlobalMenuLabelTextFlow2();
		flow2ValuesMap.put(SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU,currentValue);
		commonCode();
		System.out.println("Step1: Click on navigation");
		CommonUtils.hold(8);
		SEManageSandbox.SearchAndReplaceStrings(currentValue, valuesMaps.get(currentValue), "","", SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU);
		PageTemplateFusePlus.homeButton.click();
		CommonUtils.hold(10);
		String valueAfterChange = SEManageSandbox.getValueForGlobalMenuLabelTextFlow2();
		flow2ValuesMap_AC.put(SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU,valueAfterChange);
		Assert.assertEquals(valuesMaps.get(currentValue), valueAfterChange);
	}

	@Test(priority=6,description="This testcase is to test Search and Replace in sandbox")
	public void testcase06() throws Exception {
		commonCode();
		System.out.println("Step1: Click on navigation");
		CommonUtils.hold(8);
		SEManageSandbox.SearchAndReplaceStrings("The reference objects cannot be found in the database1234", "The reference objects cannot be found in the database", "","", SEManageSandbox.SearchReplaceTypes.MUTI_PART);
	}

	@Test(priority=7,description="This testcase is to test Search and Replace in sandbox")
	public void testcase07() throws Exception {
   SEManageSandbox.goToHomePage();
	   String currentValue =  SEManageSandbox.getValueForMultiPartTextFlow1();
//	   currentValue = currentValue.substring(0, SEManageSandbox.getIndex(currentValue, " ", 3));
		flow1ValuesMap.put(SEManageSandbox.SearchReplaceTypes.MUTI_PART,currentValue);
		commonCode();
		System.out.println("Step1: Click on navigation");
		CommonUtils.hold(8);
		SEManageSandbox.SearchAndReplaceStrings(currentValue, valuesMaps.get(currentValue), "","", SEManageSandbox.SearchReplaceTypes.MUTI_PART);
		PageTemplateFusePlus.homeButton.click();
	   CommonUtils.hold(10);
	   String valueAfterChange = SEManageSandbox.getValueForMultiPartTextFlow1();
//	   valueAfterChange = currentValue.substring(0, SEManageSandbox.getIndex(currentValue, " ", 3));
	   flow1ValuesMap_AC.put(SEManageSandbox.SearchReplaceTypes.MUTI_PART,valueAfterChange);
	   Assert.assertEquals(valuesMaps.get(currentValue), valueAfterChange);
	}
		@Test(priority=8,description="This testcase is to test Search and Replace in sandbox")
	    public void testcase08() throws Exception {
		SEManageSandbox.goToHomePage();
		String currentValue =  SEManageSandbox.getValueForAll();
		flow1ValuesMap.put(SEManageSandbox.SearchReplaceTypes.ALL,currentValue);
		commonCode();
		System.out.println("Step1: Click on navigation");
		CommonUtils.hold(8);
		SEManageSandbox.SearchAndReplaceStrings(currentValue, valuesMaps.get(currentValue), "","", SEManageSandbox.SearchReplaceTypes.ALL);
		PageTemplateFusePlus.homeButton.click();
		CommonUtils.hold(10);
		String valueAfterChange = SEManageSandbox.getValueForAll();
		flow1ValuesMap_AC.put(SEManageSandbox.SearchReplaceTypes.ALL,valueAfterChange);
		 Assert.assertEquals(valuesMaps.get(currentValue), valueAfterChange);
			}
	@Test(priority=9,description="This testcase is for activate and validate changes in old sandbox")
   public void testcase9() throws Exception {
		activateAndValidateChangesinOldSandbox();
		}
	@Test(priority=10,description="This testcase is for activate and publish current sandbox")
	public void testcase10() throws Exception {
		SEManageSandbox.goToHomePage();
	    SEManageSandbox.clickUserMenuAndActivateSandbox(stgEditorName);
		SEManageSandbox.publishsandbox();
	}
	@Test(priority=11,description="This testcase is for logout and login with diff user")
	public void testcase11() throws Exception {
		logOutCommon();
		CommonUtils.login("FUSION", "Welcome1");
	    CommonUtils.hold(5);   
	}
	
  @Test(priority=12,description="This testcase is for validate user interface changes after publish")
     public void testcase12() throws Exception {
		CommonUtils.hold(10);
		Assert.assertEquals(flow1ValuesMap_AC.get(SEManageSandbox.SearchReplaceTypes.USER_INTERFACE),SEManageSandbox.getValueForUserInterfaceTextFlow1());
	}
	@Test(priority=13,description="This testcase is for validate user interface changes after publish")
  public void testcase13() throws Exception {
		SEManageSandbox.goToHomePage();
		Assert.assertEquals(flow2ValuesMap_AC.get(SEManageSandbox.SearchReplaceTypes.USER_INTERFACE),SEManageSandbox.getValueForUserInterfaceTextFlow2());
	}
	@Test(priority=14,description="This testcase is for validate Global Menu changes after publish")
	public void testcase14() throws Exception {
		SEManageSandbox.goToHomePage();
		Assert.assertEquals(flow1ValuesMap_AC.get(SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU),SEManageSandbox.getValueForGlobalMenuLabelTextFlow1());
	}
	@Test(priority=15,description="This testcase is for validate Global Menu changes after publish")
	public void testcase15() throws Exception {
		SEManageSandbox.goToHomePage();
		Assert.assertEquals(flow1ValuesMap_AC.get(SEManageSandbox.SearchReplaceTypes.GLOBAL_MENU),SEManageSandbox.getValueForGlobalMenuLabelTextFlow2());
	}
	@Test(priority=16,description="This testcase is for validate Multipart changes after publish")
	public void testcase16() throws Exception {
		SEManageSandbox.goToHomePage();
		Assert.assertEquals(flow1ValuesMap_AC.get(SEManageSandbox.SearchReplaceTypes.MUTI_PART),SEManageSandbox.getValueForMultiPartTextFlow1());
	}
	@Test(priority=17,description="This testcase is for validate all changes after publish")
	public void testcase17() throws Exception {
		SEManageSandbox.goToHomePage();
		Assert.assertEquals(flow1ValuesMap_AC.get(SEManageSandbox.SearchReplaceTypes.ALL),SEManageSandbox.getValueForAll());
	}
	@Test(priority=18,description="This testcase is for activate and validate changes in old sandbox")
	public void testcase18() throws Exception {
		activateAndValidateChangesinOldSandbox();
	}
	@AfterClass
	public void logOut() throws InterruptedException {

		logOutCommon();

	}
	private void logOutCommon() {
		try {
			CommonUtils.logout();

		} catch (Exception ex) {
			CommonUtils.launchURL();
			CommonUtils.hold(5);
			CommonUtils.logout();
		}
	}
}









*/