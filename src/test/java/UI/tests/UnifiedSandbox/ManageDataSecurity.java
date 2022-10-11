package UI.tests.UnifiedSandbox;

import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.*;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import UtilClasses.UI.ReportGenerator;

/**
 * @author klalam
 *
 */

public class ManageDataSecurity extends GetDriverInstance{
 
	static String SandboxName = null;
	static String RefreshableSandboxname = null;
	static String uniqueID;
	static String SandboxPublishStatus = "";
	static String SandboxMode = "Edit";
	static WebElement sbNameCheck = null;
	
	WebDriver manageDsInstance;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private UsbEnvironmentMode uSBEnvModeInstance;
	private SandboxBannerUI sbBannerUIInstance;
	private SandboxDetailsUI sbDetailsUIInstance;
	private SandboxUI sbUiInstance;
	private DataSecurityWrapperClass dsWCInstance;
	private CreateSandboxUI cSBInstance;
	private GlobalPageTemplate gPTInstance;
	
	@BeforeClass(alwaysRun = true)
	public void applicationLoginAndSandboxNavigation() {
		System.out.println("signing to the application with 'it_security_manager' user");
		try {
			
			manageDsInstance =  getDriverInstanceObject();
			aLoginInstance = new ApplicationLogin(manageDsInstance);
			ntFInstance = new NavigationTaskFlows(manageDsInstance);
			nMEInstance = new NavigatorMenuElements(manageDsInstance);
			uSBEnvModeInstance	= new UsbEnvironmentMode(manageDsInstance);
			sbBannerUIInstance = new SandboxBannerUI(manageDsInstance);
			sbDetailsUIInstance = new SandboxDetailsUI(manageDsInstance);
			sbUiInstance = new SandboxUI(manageDsInstance);
			gPTInstance =new GlobalPageTemplate(manageDsInstance);
			cSBInstance = new CreateSandboxUI(manageDsInstance);
			dsWCInstance = new DataSecurityWrapperClass(manageDsInstance);
			
			aLoginInstance.login("it_security_manager", "Welcome1",manageDsInstance);
			CommonUtils.waitForPageLoad(manageDsInstance);
			uniqueID = CommonUtils.uniqueId();
			CommonUtils.hold(5);
			System.out.println("Navigating to Sandboxes UI Begins");
			ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageDsInstance);
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageDsInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
			System.out.println("Sandboxes UI Loaded");
		} catch (Exception aLASNE) {
			aLASNE.printStackTrace();
			Assert.fail();
		}
	}//End of applicationLoginAndSandboxNavigation()

	@Test (description = "method will create sandbox with DataSecurity repository ", priority = 38)
	public void createSandbox() {
		try {
			SandboxName = cSBInstance.CreateSandbox("DSSandbox", "Sandbox with DataSecurity repository", "Data Security", "Activate","YES",manageDsInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,manageDsInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,manageDsInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
			} catch (Exception CSE) {
			System.out.println("Exception in CreateSandbox()");
			Assert.fail();
			CSE.printStackTrace();
		}
	}//End of createSandbox()

	@Test (description = "method will launch Data Security taskflow", dependsOnMethods = "createSandbox", priority = 39)
	public void launchDataSecuritytf() {
		try {
			
			dsWCInstance.navigateToDStaskFlow(manageDsInstance);
			CommonUtils.explicitWait(dsWCInstance.objNameSearchField, "Visible", "",manageDsInstance);
			
			try {
				if(sbBannerUIInstance.SandboxMode.getText().contains("Edit"))
					Assert.assertEquals(dsWCInstance.DSEditModeMsg.getText(), "All customizations in this page are being carried out in the current Sandbox.");
				if(sbBannerUIInstance.SandboxMode.getText().contains("Preview"))
					Assert.assertEquals(dsWCInstance.DSPreviewModeMsg.getText(), "Task Data Security is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
				
			}catch (Exception NoSuchElementException) {
				Assert.assertTrue(true);
			}
		
		} catch (Exception LLTE) {
			System.out.println("Exception while launching DataSecurity taskflow");
			LLTE.printStackTrace();
			Assert.fail();
		}
	}//End of launchDataSecuritytf()
	


	@Test(description = "method will create DataSecurityObject within an active sandbox session", dependsOnMethods ="launchDataSecuritytf", priority = 40)
	public void createDataSecurityObject() {
		try {
			dsWCInstance.createDataBaseObject("QAAutomationTest"+uniqueID,manageDsInstance);
			System.out.println("Database object created");
			dsWCInstance.createCondition("QAAutomationTest"+uniqueID, "QAAutomationCOND_"+uniqueID,manageDsInstance);
			System.out.println("Database condition created");
			dsWCInstance.createAcion("QAAutomationTest"+uniqueID, "QAAutomation"+uniqueID,manageDsInstance);
			System.out.println("Database Action created");
			dsWCInstance.createDataBasePolicy("QAAutomationTest"+uniqueID, "QAAutomationPOL_"+uniqueID, "Application*Developer", "crm", "Application Developer","All Values", "","",manageDsInstance);
			System.out.println("Database Policy created");
			CommonUtils.hold(5);
			dsWCInstance.createDataBasePolicy("FND_OBJECTS", "QAAutomationTest"+uniqueID, "ORA_EGP_PRODUCT_MANAGER_JOB", "fscm", "Product Manager", "Multiple Values","For Business Object Application Object specfied by the Grant parameter PARAMETER1 for a specific grant for Table FND_OBJECTS", "FND_OBJECTS", manageDsInstance);
		}catch(Exception CLTE) {
			System.out.println("Exception in createDataSecurityObject()");
			CLTE.printStackTrace();
			Assert.fail();
		}
		
	}//End of createDataSecurityObject()
	
	
	@Test(description = "method will Verify sandbox changes written to mainline or not before sandbox publish", dependsOnMethods ="createDataSecurityObject", priority = 41)
	public void verifySandboxChanges() {
		SandboxMode = "";
		String databaseObjName = "QAAutomationTest"+uniqueID;
		try {
			Assert.assertTrue(sbBannerUIInstance.ExitSandboxSession(SandboxName,manageDsInstance));
			launchDataSecuritytf();
			try {
				//Assert.assertTrue(DataSecurityWrapperClass.searchDSObject("QAAutomationTest"+uniqueID));
				CommonUtils.hold(5);
				CommonUtils.explicitWait(dsWCInstance.objNameSearchField, "Visible", "",manageDsInstance);
				dsWCInstance.objNameSearchField.clear();
				dsWCInstance.objNameSearchField.sendKeys(databaseObjName);
				dsWCInstance.searchBtn.click();
				CommonUtils.hold(4);
				manageDsInstance.findElement(By.xpath("//span[contains(text(),'" + databaseObjName + "')]")).isDisplayed();
				System.out.println("Sandbox changes written to mainline");
				Assert.fail();
			}catch(Exception e) {
				System.out.println("Sandbox changes doesn't written to mainline");
				Assert.assertTrue(true);
			}
			
	}catch(Exception VSC) {
			System.out.println("Exception in VerifySandboxChanges()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//VerifySandboxChanges

	@Test (description = "method will publish sandbox", dependsOnMethods= {"createSandbox","createDataSecurityObject"},priority = 42)
	public void sandboxPublish() {
				
			System.out.println("Initiating <"+SandboxName+"> publish");
			sbDetailsUIInstance.SandboxPublish(SandboxName,manageDsInstance);
		
			CommonUtils.hold(5);
			
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.hold(5);
			SandboxPublishStatus = "";
	}//End Of SandboxPublish()
	
	@Test(description = "method will Verify sandbox changes written to mainline after sandbox publish", dependsOnMethods="sandboxPublish", priority = 43)
	public void verifyManilineChangesAfterPublish() {
		SandboxMode = "";
		try {
			launchDataSecuritytf();
			Assert.assertTrue(dsWCInstance.searchDSObject("QAAutomationTest"+uniqueID,manageDsInstance));
		}catch(Exception VSC) {
			System.out.println("Exception in VerifySandboxChanges()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//VerifyManilineChangesAfterPublish
	
	@Test(description = "method will create sandboxes with datasecurity repository to perform conflict refresh",priority = 44)
	public void refreshableSandboxesCreate() {
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gPTInstance.accessFromRecentItems("Manage Sandboxes",manageDsInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageDsInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageDsInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);	
			System.out.println("Initiating publishable sandbox creation");
			SandboxName = cSBInstance.CreateSandbox("DataSecurity", "Sandbox with DataSecurity repository", "Data Security", "","YES",manageDsInstance);
			System.out.println("Sandbox "+SandboxName+" Successfully Created");
			System.out.println("Initiating Refreshable sandbox creation");
			RefreshableSandboxname = cSBInstance.CreateSandbox("DataSecurity_Refresh", "Refreshable Sandbox with DataSecurity repository", "Data Security", "Activate","YES",manageDsInstance);
			System.out.println("Sandbox "+RefreshableSandboxname+" Successfully Created");
			sbNameCheck = sbUiInstance.getSandboxNameElement(RefreshableSandboxname,manageDsInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", RefreshableSandboxname,manageDsInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
		}catch(Exception rSCE) {
			System.out.println("Exception in refreshableSandboxesCreate() ");
			rSCE.printStackTrace();
			Assert.fail();
		}
	}//End Of refreshableSandboxesCreate()
	
	@Test(description = "method will perform sandbox conflict updates on datasecurity objects in refreshable sandbox",dependsOnMethods="refreshableSandboxesCreate",priority = 45)
	public void refreshableSandboxUpdates() {
		try {
			System.out.println("Initating datasecurity changes within refreshable sandbox "+RefreshableSandboxname);
			System.out.println("Loading DStf");  
			launchDataSecuritytf();
			
			System.out.println("updating Policy");
		
			dsWCInstance.updateObject("QAAutomationTest"+uniqueID, "policy", "QAAutomationPOL_"+uniqueID, "ConflictedSandboxRefreshUpdate",manageDsInstance);
			System.out.println("deleting Condition");
			dsWCInstance.deleteCondition("QAAutomationTest"+uniqueID, "QAAutomationCOND_"+uniqueID,manageDsInstance); 
			//DataSecurityWrapper.updateObjectPolicyparameter("FND_OBJECTS", "QAAutomationTest"+uniqueID, "ORA_EGP_PRODUCT_MANAGER_JOB", "FND_DF_FLEXFIELDS_B");
			System.out.println("Exit sandbox");
			sbBannerUIInstance.ExitSandboxSession(RefreshableSandboxname,manageDsInstance);
			System.out.println("Refreshable sandbox < "+RefreshableSandboxname+" > exited from its active session");
			CommonUtils.hold(3);
		}catch(Exception rSUE) {
			System.out.println("Exception in refreshableSandboxUpdates()");
			rSUE.printStackTrace();
			Assert.fail();
		}
	}//End Of refreshableSandboxUpdates()
	
	@Test(description = "method will perform sandbox conflict updates on data security objects in publishable sandbox",dependsOnMethods="refreshableSandboxesCreate",priority = 46)
	public void publishableSandboxUpdates() {
		try {
			System.out.println("Enter into publishable sandbox");
			System.out.println("Actiavting publishing sandbox "+SandboxName);
			sbUiInstance.EnterSandboxSession(SandboxName,manageDsInstance);
			System.out.println("Publishing sandbox < "+SandboxName+" > activated");
			System.out.println("Enterred into publishable sandbox");
			//Assert.assertTrue(SandboxUI.SandboxName(SandboxName).isDisplayed());
			System.out.println("Loading DStf");
			System.out.println("Navigating to DataSecurity taskflow");
			launchDataSecuritytf();
			System.out.println("Navigated to DataSecurity taskflow");
			System.out.println("updating DS in publishable SANDBOX");
			
			dsWCInstance.updateObject("QAAutomationTest"+uniqueID, "policy", "QAAutomationPOL_"+uniqueID, "ConflictRefreshPublishablesandbox",manageDsInstance);
			dsWCInstance.updateObject("QAAutomationTest"+uniqueID, "condition", "QAAutomationCOND_"+uniqueID, "ConflictRefreshPublishedUpdate",manageDsInstance); 
			//DataSecurityWrapper.updateObjectPolicyparameter("FND_OBJECTS", "QAAutomationTest"+uniqueID, "ORA_EGP_PRODUCT_MANAGER_JOB", "FND_APPL_TAXONOMY");
		
			System.out.println("Iniitiate publish from banner ");
			System.out.println("Initating sandbox <"+SandboxName+"> publish from banner");
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,manageDsInstance);
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageDsInstance);
			System.out.println("Sandbox <"+SandboxName+"> successfully published to mainline");
		}catch(Exception pSUE) {
			System.out.println("Exception in publishableSandboxUpdates()");
			pSUE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishableSandboxUpdates()
	
	@Test(description = "method will perform conflict sandbox refresh on dataSecurity fields", dependsOnMethods= {"refreshableSandboxUpdates","publishableSandboxUpdates"},priority = 47)
	public void conflictSandboxRefresh() {
		SandboxMode = "Edit";
		try {
			System.out.println("Initating sandbox < "+RefreshableSandboxname+" > refresh");
			System.out.println("Initiate SB conflict refresh");
			sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname, "Accept",manageDsInstance);
			System.out.println("Initiate SB conflict refresh done and verifying");
			CommonUtils.hold(5);
			sbDetailsUIInstance.VerifySandboxRefresh(RefreshableSandboxname,manageDsInstance);
			
		}catch(Exception nCSRE) {
			System.out.println("Exception in ConflictSandboxRefresh()");
			Assert.fail();
			nCSRE.printStackTrace();
		}
	}//End of ConflictSandboxRefresh()
	
	@Test(description = "method will check for Accepted datasecurity Conflicted changes", dependsOnMethods="conflictSandboxRefresh", priority = 48)
	public void verifyConflictedChanges() {
		SandboxMode = "";
		try {
			System.out.println("verifyConflictedChanges() - loading DStf");
			
			sbUiInstance.EnterSandboxSession(RefreshableSandboxname,manageDsInstance);
			
			launchDataSecuritytf();
			
			System.out.println("verifyConflictedChanges() - Verifying status");
			dsWCInstance.verifyStatus("QAAutomationTest"+uniqueID, "policy", "ConflictRefreshPublishablesandbox", "update",manageDsInstance);
									
			System.out.println("verifyConflictedChanges() - Verified");
		}catch(Exception vCCE) {
			System.out.println("Exception in verifyConflictedChanges()");
			vCCE.printStackTrace();
			Assert.fail();
		}
	}//End of verifyConflictedChanges()

	@Test(description = "method will check for DS preview mode",priority = 49)
	public void vefiryDSPreviewMode() {
		try {
			try {
				System.out.println("Checking for sandbox activation");
				CommonUtils.explicitWait(sbBannerUIInstance.SandboxBanner, "Visible", "",manageDsInstance);
				System.out.println("Sandbox is activarted");
			}catch(Exception vMPE) {
				System.out.println("Sandbox is not activated. Activating the sandbox");
				System.out.println("Entering into Sandbox session");
				sbUiInstance.EnterSandboxSession(RefreshableSandboxname,manageDsInstance);
				System.out.println("Sandbox is activated");
				System.out.println("Sandbox is activated.");
			}
			System.out.println("Flipping activated sandbox mode to 'PREVIEW' ");
			sbBannerUIInstance.SetSandboxPreviewMode(manageDsInstance);
			System.out.println("Sandbox is in 'PREVIEW' mode ");
			CommonUtils.hold(3);
			System.out.println("Navigating to DataSecurity taskflow ");
			launchDataSecuritytf();
			System.out.println("In DataSecurity page ");
			Assert.assertEquals(dsWCInstance.DSPreviewModeMsg.getText(), "Task Data Security is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
			Assert.assertTrue(dsWCInstance.previewModeNewBtn.isDisplayed());
			System.out.println("DataSecurity 'PREVIEW' mode verified");
		
		}catch(Exception vLPME) {
			System.out.println("Exception in vefiryDSPreviewMode()");
			vLPME.printStackTrace();
			Assert.fail();
		}
	}//End of vefiryDSPreviewMode()
	

	@AfterClass(alwaysRun = true)
	public void Logout() {
		try {
			aLoginInstance.logout(manageDsInstance);

		} catch (Exception ex) {
			aLoginInstance.launchURL(manageDsInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(manageDsInstance);
		}finally {
			try {
				System.out.println("Opened db connection has been closed");
				manageDsInstance.quit();
				System.out.println("driver instance has been closed and quit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing Object Instance");
			}
		}
	}//End of Logout()

}
