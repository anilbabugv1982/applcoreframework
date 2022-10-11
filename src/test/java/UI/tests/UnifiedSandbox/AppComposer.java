/**
 * 
 */
package UI.tests.UnifiedSandbox;

import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import TestBase.UI.GetDriverInstance;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.AppComposerMethods;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.AppcomposerPage;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.CreateSandboxUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.LookupsWrapperClass;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxBannerUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxDetailsUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxUI;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import UtilClasses.UI.ReportGenerator;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.UsbEnvironmentMode;

/**
 * @author klalam
 * @author vivevenk
 *
 */

public class AppComposer extends GetDriverInstance{
	
	static String SandboxName = null;
	static String RefreshableSandboxname = null;
	static String uniqueID;
	static String SandboxPublishStatus = "";
	static String SandboxMode = "Edit";
	static Boolean OnetimeSandboxPageVisited = false;
	static WebElement sbNameCheck = null;
	
	WebDriver aComposerInstance;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private UsbEnvironmentMode uSBEnvModeInstance;
	private SandboxBannerUI sbBannerUIInstance;
	private SandboxDetailsUI sbDetailsUIInstance;
	private SandboxUI sbUiInstance;
	private CreateSandboxUI cSBInstance;
	private GlobalPageTemplate gptInstance;
	private AppComposerMethods acmInstnace;
	
	@BeforeClass(alwaysRun = true)
	public void applicationLoginAndSandboxNavigation() {
		System.out.println("signing to the application with 'app_impl_consultant' user");
		try {
			
			aComposerInstance =  getDriverInstanceObject();
			aLoginInstance = new ApplicationLogin(aComposerInstance);
			ntFInstance = new NavigationTaskFlows(aComposerInstance);
			nMEInstance = new NavigatorMenuElements(aComposerInstance);
			uSBEnvModeInstance	= new UsbEnvironmentMode(aComposerInstance);
			sbBannerUIInstance = new SandboxBannerUI(aComposerInstance);
			sbDetailsUIInstance = new SandboxDetailsUI(aComposerInstance);
			sbUiInstance = new SandboxUI(aComposerInstance);
			gptInstance= new GlobalPageTemplate(aComposerInstance);
			cSBInstance = new CreateSandboxUI(aComposerInstance);
			acmInstnace = new AppComposerMethods(aComposerInstance);
			
			aLoginInstance.login("app_impl_consultant", "Welcome1",aComposerInstance);
			CommonUtils.waitForPageLoad(aComposerInstance);
			uniqueID = CommonUtils.uniqueId();
			System.out.println("Navigating to Sandboxes UI Begins");
			
			CommonUtils.hold(3);
			ntFInstance.navigateToTask(nMEInstance.Sandboxes,aComposerInstance);
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",aComposerInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
			System.out.println("Sandboxes UI Loaded");
		}catch(Exception aLASNE) {
			System.out.println("Exception in <applicationLoginAndSandboxNavigation() >");
			aLASNE.printStackTrace();
			Assert.fail();
		}
	}//End of applicationLoginAndSandboxNavigation()
	
	@Test (description = "method will create sandbox with AppComposer repository ", priority = 76)
	public void createSandbox() {
		System.out.println("Creating Sandbox........");
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gptInstance.accessFromRecentItems("Manage Sandboxes",aComposerInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,aComposerInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",aComposerInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);	
			SandboxName = cSBInstance.CreateSandbox("AppComposer", "Sandbox with AppComposer repository", "Application Composer,Data Security", "Activate","YEs",aComposerInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,aComposerInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,aComposerInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());			
			CommonUtils.waitForPageLoad(aComposerInstance);
			} catch (Exception CSE) {
			System.out.println("Exception in CreateSandbox()");
			Assert.fail();
			CSE.printStackTrace();
		}
	}//End of createSandbox()

		
	@Test(description = "method will customize pages through Appcomposer within an active sandbox session", priority = 77, dependsOnMethods = {"createSandbox"})
	public void AppcomposerCustomizations() {
		System.out.println("Customizing Application Composer ........");
		try {
			sbBannerUIInstance.navigatetoToolPage("Application Composer",aComposerInstance);
			CommonUtils.waitForPageLoad(aComposerInstance);
			CommonUtils.explicitWait(acmInstnace.appComposerPageTitle, "TextPresent", "Application Composer",aComposerInstance);
			CommonUtils.explicitWait(acmInstnace.customObjects, "Click", "",aComposerInstance);			
			acmInstnace.createCustomObject("CustObjQA"+uniqueID,aComposerInstance);
		}catch(Exception CLTE) {
			System.out.println("Exception in AppcomposerCustomizations()");
			CLTE.printStackTrace();
			Assert.fail();
		}
	}//End of AppcomposerCustomizations()
	
	
	@Test (description = "method will publish sandbox", priority = 78, dependsOnMethods = {"createSandbox","AppcomposerCustomizations"})
	public void sandboxPublish() {
		System.out.println("Publishing Sandbox ........");
		try {
			System.out.println("Initating sandbox <"+SandboxName+"> publish from banner");
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,aComposerInstance);
			CommonUtils.hold(5);
			SandboxPublishStatus = "";
			sbUiInstance.AvailableSandboxesLabel.click();
		    }
		    catch(Exception CLTE) {
				System.out.println("Exception in AppComposerSandboxPublish()");
				CLTE.printStackTrace();
				Assert.fail();
			}
	}//End Of SandboxPublish()
	
	@Test(description = "method will Verify sandbox changes written to mainline after sandbox publish", priority = 79,dependsOnMethods = {"sandboxPublish"} )
	public void verifyMainlineChangesAfterPublish() {
		System.out.println("Verify Mainline Changes ........");
		try {
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
				sbUiInstance.refreshToLatestConfiguration(aComposerInstance);
			}catch(Exception rlce) {
				System.out.println("Exception while advancing to latest configuration");
				rlce.printStackTrace();
				Assert.fail();
			}
				
			System.out.println("Advanced to latest MDS label");	*/
			
			//VV changes
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gptInstance.accessFromRecentItems("Manage Sandboxes",aComposerInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,aComposerInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",aComposerInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);	
			createSandbox();
			sbBannerUIInstance.navigatetoToolPage("Application Composer",aComposerInstance);
			CommonUtils.waitForPageLoad(aComposerInstance);
			CommonUtils.explicitWait(acmInstnace.customObjects, "Click", "",aComposerInstance);
			acmInstnace.verifyCustomObject("CustObjQA"+uniqueID,aComposerInstance);
			
		}catch(Exception VSC) {
			System.out.println("Exception in verifyMainlineChangesAfterPublish()");
			VSC.printStackTrace();
			Assert.fail();
		}
	}//verifyMainlineChangesAfterPublish

	@Test(description = "method will perform conflict sandbox refresh on appcomposer tool ",priority = 80)
	public void creatRefreshableSandboxes() {
		try {
				System.out.println("Navigating to Sandboxes UI Begins");
				try {
					gptInstance.accessFromRecentItems("Manage Sandboxes",aComposerInstance).click();
				}catch(Exception eI) {
					System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
					eI.printStackTrace();
					ntFInstance.navigateToTask(nMEInstance.Sandboxes,aComposerInstance);
				}
							
				CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",aComposerInstance);
				Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
					System.out.println("Sandboxes UI Loaded");
				CommonUtils.hold(5);	
				
				SandboxName = cSBInstance.CreateSandbox("AppComposer", "Sandbox with AppComposer repository", "Application Composer,Data Security", "","YES",aComposerInstance);
				sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,aComposerInstance);
				 CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,aComposerInstance);
				 Assert.assertTrue(sbNameCheck.isDisplayed());
				 RefreshableSandboxname = cSBInstance.CreateSandbox("AppComposer_Refresh", "Sandbox with AppComposer repository", "Application Composer,Data Security", "Activate","YES",aComposerInstance);
				 sbNameCheck = sbUiInstance.getSandboxNameElement(RefreshableSandboxname,aComposerInstance);
				CommonUtils.explicitWait(sbNameCheck, "TextPresent", RefreshableSandboxname,aComposerInstance);
				Assert.assertTrue(sbNameCheck.isDisplayed());
				
				CommonUtils.hold(3);
		}catch(Exception cRSE) {
			System.out.println("Exception in creatRefreshableSandboxes()");
			cRSE.printStackTrace();
			Assert.fail();
		}
	}//End Of creatRefreshableSandboxes()
	
	@Test(description = "method will create appcompoer artifacts to be refresh with mainline within refreshable sandbox session ", dependsOnMethods = "creatRefreshableSandboxes", priority = 81)
	public void refreshableSandboxUpdates() {
		try {
			gptInstance.homeIcon.click();
			CommonUtils.waitForPageLoad(aComposerInstance);
			
			System.out.println("Initiating sandbox customization");
				
			//VV changes to launch from Tools
			//CommonUtils.navigateToTask(NavigatorMenuElements.ApplicationComposer);
			sbBannerUIInstance.navigatetoToolPage("Application Composer",aComposerInstance);
			CommonUtils.waitForPageLoad(aComposerInstance);
			CommonUtils.explicitWait(acmInstnace.customObjects, "Click", "",aComposerInstance);
			//VV changes
			//CommonUtils.PageRefresh();  
			//AppComposerMethods.editcustobjfields("CustObjQA"+uniqueID, "Text", "CustObjQA_Text", "CustObjQA_textRefSandbox");
			//AppComposerMethods.addfieldsCustomObject("CustObjQA"+uniqueID, "Text", "CustObjQA_Text", "CustObjQA_LKUP");
			
			acmInstnace.addfieldsCustomObject("CustObjQA"+uniqueID, "Text", "CustObjQA_Text", "CustObjQA_LKUP",aComposerInstance);
			System.out.println("Customization's done");
			System.out.println("Exit sandbox");
			sbBannerUIInstance.ExitSandboxSession(RefreshableSandboxname,aComposerInstance);
			CommonUtils.hold(3);
		}catch(Exception rSUE) {
			System.out.println("Exception in publishableSandboxUpdates()");
			rSUE.printStackTrace();
			Assert.fail();
		}
	}//End Of refreshableSandboxUpdates()
	
	@Test(description = "method will create appcompoer artifacts to be publish  with mainline within publishable sandbox session ", dependsOnMethods = "creatRefreshableSandboxes", priority = 82)
	public void publishableSandboxUpdates() {
		try{
			System.out.println("Enter into publishable sandbox");
			sbUiInstance.EnterSandboxSession(SandboxName,aComposerInstance);
			System.out.println("Entered into publishable sandbox");
			Assert.assertTrue(sbUiInstance.getSandboxNameElement(SandboxName,aComposerInstance).isDisplayed());
			
			System.out.println("Initiating sandbox customization");
			
			sbBannerUIInstance.navigatetoToolPage("Application Composer",aComposerInstance);
			CommonUtils.waitForPageLoad(aComposerInstance);
			CommonUtils.explicitWait(acmInstnace.customObjects, "Click", "",aComposerInstance);
			
			acmInstnace.addfieldsStandardObject("Account", "Text", "CusField_StdObj","SIC",aComposerInstance);
			System.out.println("Customization's done");
			CommonUtils.hold(3);
		}catch(Exception pSUE) {
			System.out.println("Exception in publishableSandboxUpdates()");
			pSUE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishableSandboxUpdates()
	
	@Test(description = "method will publish updated appcompoer artifacts to mainline ",dependsOnMethods = "publishableSandboxUpdates", priority = 83)
	public void publishSandboxChanges() {
		try {
			System.out.println("Iniitiate publish from banner ");
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,aComposerInstance);
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",aComposerInstance);
			CommonUtils.hold(5);
			
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
				sbUiInstance.refreshToLatestConfiguration(aComposerInstance);
			}catch(Exception rlce) {
				System.out.println("Exception while advancing to latest configuration");
				rlce.printStackTrace();
				Assert.fail();
			}
				
			System.out.println("Advanced to latest MDS label");	
			*/
			
		}catch(Exception pSCE) {
			System.out.println("Exception in publishSandboxChanges()");
			pSCE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishSandboxChanges()
	
	@Test(description = "method will perform conflict sandbox refresh on appcomposer tool", dependsOnMethods = {"creatRefreshableSandboxes","refreshableSandboxUpdates","publishableSandboxUpdates"},priority = 84)
	public void autoMergeSandboxRefresh() {
		System.out.println("Sandbox Merge Refresh ........");
		SandboxMode = "Edit";
		try {
			System.out.println("Initiate SB conflict refresh");
			sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname, "Accept",aComposerInstance);
			System.out.println("Initiate SB conflict refresh done and verifying");
			sbDetailsUIInstance.VerifySandboxRefresh(RefreshableSandboxname,aComposerInstance);
			CommonUtils.hold(3);
		}catch(Exception nCSRE) {
			System.out.println("Exception in ConflictSandboxRefresh()");
			Assert.fail();
			nCSRE.printStackTrace();
		}
	}//End of AutoMergeSandboxRefresh()
	
	@Test(description = "method will check for AppComposer preview mode",dependsOnMethods="creatRefreshableSandboxes", priority = 85)
	public void verifyAppComposerPreviewMode() {
		System.out.println("Checking Preview Mode........");
		try {
			CommonUtils.hold(5);	
			try {
				System.out.println("Checking for sandbox activation");
				CommonUtils.explicitWait(sbBannerUIInstance.SandboxBanner, "Visible", "",aComposerInstance);
				System.out.println("Sandbox is activarted");
			}catch(Exception vMPE) {
				System.out.println("Sandbox is not activated. Activating the sandbox");
				System.out.println("Entering into Sandbox session");
				sbUiInstance.EnterSandboxSession(RefreshableSandboxname,aComposerInstance);
				System.out.println("Sandbox is activated");
				System.out.println("Sandbox is activated.");
			}
			System.out.println("Flipping activated sandbox mode to 'PREVIEW' ");
			sbBannerUIInstance.SetSandboxPreviewMode(aComposerInstance);
			System.out.println("Sandbox is in 'PREVIEW' mode ");
			CommonUtils.hold(3);
			System.out.println("Navigating to AppComposer taskflow ");
			sbBannerUIInstance.SetSandboxPreviewMode(aComposerInstance);
			CommonUtils.waitForPageLoad(aComposerInstance);
			Assert.assertTrue(acmInstnace.AppComposerPreviewMode.isDisplayed());
			
	}catch(Exception vLPME) {
			System.out.println("Exception in verifyAppComposerPreviewMode()");
			vLPME.printStackTrace();
			Assert.fail();
		}
	}//End of verifyAppComposerPreviewMode()
	
	@AfterClass(alwaysRun = true)
	public void Logout() {
		try {
			aLoginInstance.logout(aComposerInstance);

		} catch (Exception ex) {
			aLoginInstance.launchURL(aComposerInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(aComposerInstance);
		}finally {
			try {
				System.out.println("Opened db connection has been closed");
				aComposerInstance.quit();
				System.out.println("driver instance has been closed and quit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing object Instance ");
			}
		}
	}//End Of Logout 

}
