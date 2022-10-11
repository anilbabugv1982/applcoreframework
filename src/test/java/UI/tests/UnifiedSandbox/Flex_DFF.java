package UI.tests.UnifiedSandbox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Dff.ManaageValueSetsUtil;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.CreateSandboxUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.FlexUtils;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.LookupsWrapperClass;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxBannerUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxDetailsUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.UsbEnvironmentMode;

public class Flex_DFF extends GetDriverInstance{
	
	static String SandboxName = null;
	static String RefreshableSandboxname = null;
	static String uniqueID;
	static String SandboxPublishStatus = "";
	static String SandboxMode = "Edit";
	static WebElement sbNameCheck = null;
	
	WebDriver flexDFFInstance = null;
	private ApplicationLogin aLoginInstance=null;
	private NavigatorMenuElements nMEInstance=null;
	private UsbEnvironmentMode uSBEnvModeInstance=null;
	private SandboxBannerUI sbBannerUIInstance=null;
	private SandboxDetailsUI sbDetailsUIInstance=null;
	private SandboxUI sbUiInstance=null;
	private CreateSandboxUI cSBInstance=null;
	private FlexUtils flexUtilInstance=null;
	private ManaageValueSetsUtil vsUtil=null;
	private DFFUtils dffUtilInstance=null;
	
	
	@BeforeClass(alwaysRun = true)
	public void applicationLoginAndSandboxNavigation() {
	
	try {
		flexDFFInstance =  getDriverInstanceObject();
		if(flexDFFInstance != null) {
			aLoginInstance = new ApplicationLogin(flexDFFInstance);
			nMEInstance = new NavigatorMenuElements(flexDFFInstance);
			uSBEnvModeInstance	= new UsbEnvironmentMode(flexDFFInstance);
			sbBannerUIInstance = new SandboxBannerUI(flexDFFInstance);
			sbDetailsUIInstance = new SandboxDetailsUI(flexDFFInstance);
			sbUiInstance = new SandboxUI(flexDFFInstance);
			cSBInstance = new CreateSandboxUI(flexDFFInstance);
			flexUtilInstance = new FlexUtils(flexDFFInstance);
			dffUtilInstance= new DFFUtils(flexDFFInstance);
	
		}else {
			System.out.println("FlexDff Instance is NULL");
			return;
		}
		aLoginInstance.login("app_impl_consultant", "Welcome1",flexDFFInstance);
		
		CommonUtils.waitForPageLoad(flexDFFInstance);
		System.out.println("FuseWelcome Page");
			uniqueID = CommonUtils.uniqueId();
			CommonUtils.hold(5);
		/*System.out.println("Navigating to Sandboxes UI Begins");
		fuInstance.navigateToTask(nMEInstance.Sandboxes,flexDFFInstance);
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",flexDFFInstance);
		Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
		System.out.println("Sandboxes UI Loaded");*/
		} catch (Exception aLASNE) {
			aLASNE.printStackTrace();
			Assert.fail();
		}
	}//End of ApplicationLogin()
	
/*	@Test (description = "method will create sandbox with Flexfields repository ", priority = 1)
	public void createSandbox() {
		try {
				System.out.println("Initiating Sandbox creation");
			SandboxName = cSBInstance.CreateSandbox("FlexPSB", "Sandbox with Flexfields repository", "Flexfields", "Activate","YES",flexDFFInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,flexDFFInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,flexDFFInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
				System.out.println("Sandbox < "+SandboxName+" > successfully created");
			Assert.assertTrue(sbBannerUIInstance.SandboxBanner.isDisplayed());
				System.out.println("Sandbox < "+SandboxName+" > successfully activated");
			} catch (Exception CSE) {
			System.out.println("Exception in CreateSandbox()");
			CSE.printStackTrace();
			Assert.fail();
		}
	}//End of CreateSandbox()
	*/

/*	@Test (description = "method will launch  DFF taskflow ", dependsOnMethods = "createSandbox", priority = 2)
	public void launchDfftf() {
		try {
			flexUtilInstance.navigateFlexPage("Manage Descriptive Flexfields", flexDFFInstance);
			Assert.assertEquals(flexUtilInstance.flexPageEditModeMsg.getText(), "All configurations on this page are done in the current sandbox.");
		}catch(Exception ldfe) {
			System.out.println("Exception while navigating to DFF tf");
			ldfe.printStackTrace();
			Assert.fail();
		}
	}//End of launchDfftf()
	
	@Test (description = "method will create GlobalSegment for Flexfield within publishable sandbox session", dependsOnMethods = "launchDfftf", priority = 3)
	public void createFlexSegment() {
		try {
			String arges[] = { "dffFlexCode", "FND_VS_VALUES_B", "segmentType", "global", "dffName", "SBPC_" + uniqueID,
					"dffCode", "DFFFC_CODE" + uniqueID, "dffAPIName", "DFFFCAPI" + uniqueID, "dataType", "Character", "tableColumnValueIndex", "1", "valueSetCode",
					"1 Character", "promtName", "DFFFC_" + uniqueID, "displayType", "Text Box", "createContext", "No",
					"contextCodeNameSearch", "DPK_CCODE", "contextDisplayName", "DPK_NAME", "contextCodeName", "DPK_CCODE",
					"contextAPIName", "DPKCAPI"
			};
			
			dffUtilInstance.createSegment(flexDFFInstance, arges);
			CommonUtils.hold(5);
		}catch(Exception cgse) {
			System.out.println("Exception in createGlobalSegments() "+cgse.getMessage());
			cgse.printStackTrace();
			Assert.fail();
		}
	}//End of createGlobalSegments()
	
	
	@Test (description = "method to perform sandbox publish without edited Flexfield deplyment into sandbox session", dependsOnMethods = "createFlexSegment", priority = 4)
	public void publishWOdeploymentIntoSandbox() {
		try {
			
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName, flexDFFInstance);
			System.out.println(SandboxDetailsUI.flexNotDeployedWithinSandboxMsg);
		}catch(Exception pWDISE) {
			System.out.println("Exception in publishWOdeploymentIntoSandbox() "+pWDISE.getMessage());
			pWDISE.printStackTrace();
			Assert.fail();
		}
	}*/
	
/*	@Test (description = "method to perform sandbox publish without edited Flexfield deplyment into sandbox session", priority = 5)
	//@Test (description = "method will deploy Flexfield into sandbox session", dependsOnMethods = "createFlexSegment", priority = 5)
	public void deployFlexIntoSandbox() {
		try {
			flexUtilInstance.navigateFlexPage("Manage Descriptive Flexfields", flexDFFInstance);
			Assert.assertEquals(flexUtilInstance.flexPageEditModeMsg.getText(), "All configurations on this page are done in the current sandbox.");
			dffUtilInstance.searchDFFCode("FND_VS_VALUES_B", flexDFFInstance);
			CommonUtils.waitforElementtoClick(5,dffUtilInstance.edithBtn,flexDFFInstance);
			
			Assert.assertEquals(flexUtilInstance.deployFlexIntoSandbox("FND_VS_VALUES_B", flexDFFInstance), "Confirmation");
			
			System.out.println("Flex Sandbox deployment Status : "+flexUtilInstance.getFlexDeploymentStatus("FND_VS_VALUES_B", flexDFFInstance));
			
			Assert.assertEquals("Deployed", flexUtilInstance.getFlexDeploymentStatus("FND_VS_VALUES_B", flexDFFInstance));
			
		}catch(Exception dFISE) {
			System.out.println("Exception in deplyFlexIntoSandbox : "+dFISE.getMessage());
			dFISE.printStackTrace();
			Assert.fail();
		}
		
	}
	
	@Test (description = "method to publish created/updated flexfield artifacts to Mainline", dependsOnMethods = "deployFlexIntoSandbox", priority = 6)
	public void publishFlexfieldArtifacts() {
		try {
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName, flexDFFInstance);
		}catch(Exception pFAE) {
			System.out.println("Exception in publishFlexfieldArtifacts() "+pFAE.getMessage());
			pFAE.printStackTrace();
			Assert.fail();
		}
	}*/
	
	@Test (description = "method to edit flexfield parameters", priority = 6)
	public void updateFlexAttributes() {
		try {
			String updatableParams[] = { "flexFieldCode", "FND_VS_VALUES_B", "segmentType", "global", "contextCode", "Global Data Elements", "segmentCode", "43CS_SRC",
										"glbDisplayType", "Text Area", "glbDisplayTypeSize", "10", "glbDisplayTypeHeight", "5"
										};
			
			flexUtilInstance.navigateFlexPage("Manage Descriptive Flexfields", flexDFFInstance);
			flexUtilInstance.updateSegment(flexDFFInstance, updatableParams);
		}catch(Exception uFAE) {
			System.out.println("Exception in updateFlexAttributes() "+uFAE.getMessage());
			uFAE.printStackTrace();
			Assert.fail();
		}
	}
	
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(flexDFFInstance);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(flexDFFInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(flexDFFInstance);
		}finally {
			try {
				System.out.println("Opened db connection has been closed");
				flexDFFInstance.quit();
				System.out.println("driver instance has been closed and quit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing Object Instance ");
			}
		}
	}//End Of Logout()

}
