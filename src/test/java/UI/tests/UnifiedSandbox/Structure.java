package UI.tests.UnifiedSandbox;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.*;
import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.*;


/**
 * @author klalam
 *
 */

public class Structure extends GetDriverInstance{
  
	static String SandboxName = null;
	static String RefreshableSandboxname = null;
	public static String uniqueID;
	static String SandboxPublishStatus = "";
	static String SandboxMode = "Edit";
	static WebElement sbNameCheck = null;
	
	WebDriver structureInstance;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private UsbEnvironmentMode uSBEnvModeInstance;
	private SandboxBannerUI sbBannerUIInstance;
	private SandboxDetailsUI sbDetailsUIInstance;
	private SandboxUI sbUiInstance;
	private CreateSandboxUI cSBInstance;
	private GlobalPageTemplate gptInstance;
	private StructureTestMethods stmInstance;
		
	
	@BeforeClass(alwaysRun = true)
	public void applicationLoginAndSandboxNavigation() throws Exception {
		System.out.println("signing to the application with 'cchanel' user");
		try {
			structureInstance =  getDriverInstanceObject();
			aLoginInstance = new ApplicationLogin(structureInstance);
			ntFInstance = new NavigationTaskFlows(structureInstance);
			nMEInstance = new NavigatorMenuElements(structureInstance);
			uSBEnvModeInstance	= new UsbEnvironmentMode(structureInstance);
			sbBannerUIInstance = new SandboxBannerUI(structureInstance);
			sbDetailsUIInstance = new SandboxDetailsUI(structureInstance);
			sbUiInstance = new SandboxUI(structureInstance);
			cSBInstance = new CreateSandboxUI(structureInstance);
			gptInstance = new GlobalPageTemplate(structureInstance);
			stmInstance = new StructureTestMethods(structureInstance);
			
			/*if (uSBEnvModeInstance.CheckForUsbMode(uSBEnvModeInstance.RetreiveUsbProfileOption).equalsIgnoreCase("N") && uSBEnvModeInstance.CheckForUsbMode(uSBEnvModeInstance.RetrevieADFShareProfileOption).equalsIgnoreCase("FALSE")) {
				 throw new SkipException("Skipping test because environment not in USB mode");
			  }
			 System.out.println("USB mode check completed");*/
			
			aLoginInstance.login("cchanel", "Welcome1", structureInstance);
			CommonUtils.waitForPageLoad(structureInstance);
			uniqueID = CommonUtils.uniqueId();
			System.out.println("Navigating to Sandboxes UI Begins");
			/*try {
				gptInstance.globalTemplateArea.click();
			}catch(Exception gTemplate) {
				sbBannerUIInstance.SandboxBanner.click();
			}*/
			CommonUtils.hold(5);
			ntFInstance.navigateToTask(nMEInstance.Sandboxes,structureInstance);
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",structureInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
			System.out.println("Sandboxes UI Loaded");
		}catch(Exception aLASNE) {
			System.out.println("Exception in <applicationLoginAndSandboxNavigation() >");
			aLASNE.printStackTrace();
			//ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(aLASNE));
			Assert.fail();
		}
	}//End of applicationLoginAndSandboxNavigation()

	@Test (description = "method will create sandbox with Structure repository ", priority = 50)
	public void createSandbox() {
		try {
			SandboxName = cSBInstance.CreateSandbox("Structure", "Sandbox with Structure repository", "Structure,Page Integration", "Activate","YES",structureInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,structureInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,structureInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
			} catch (Exception CSE) {
			System.out.println("Exception in CreateSandbox()");
			Assert.fail();
			CSE.printStackTrace();
		}
	}//End of CreateSandbox()

	@Test (description = "method will launch Strucutre taskflow", dependsOnMethods="createSandbox",  priority = 51)
	public void launchStructuretf() {
		try {
			CommonUtils.hold(5);
			ntFInstance.navigateToTask(nMEInstance.Structure,structureInstance);
			CommonUtils.explicitWait(stmInstance.createButton, "Click", "",structureInstance);
			try {
				if(sbBannerUIInstance.SandboxMode.getText().contains("Edit"))
					Assert.assertTrue(stmInstance.createButton.isDisplayed());
				else if(sbBannerUIInstance.SandboxMode.getText().contains("Preview"))
					Assert.assertTrue(stmInstance.navigationConfiguration.isDisplayed());
			}catch (Exception NoSuchElementException) {
			Assert.assertTrue(true);
			}
		} catch (Exception LLTE) {
			System.out.println("Exception in Strucutre taskflow launch method");
			LLTE.printStackTrace();
			Assert.fail();
		}
	}//End of launchStructuretf()

	@Test(description = "method will create pages within an active sandbox session", dependsOnMethods="launchStructuretf", priority = 52)
	public void createStructureMenuItems() {
		try {
			
			stmInstance.createCategory("category"+uniqueID,structureInstance);
			
			stmInstance.createPageEntryStaticURL("staticPg"+uniqueID,structureInstance);
			
			stmInstance.createPageEntryDynamicURL("DynaminPg"+uniqueID,structureInstance);
			CommonUtils.hold(5);
		}catch(Exception CLTE) {
			System.out.println("Exception in createPageIntegrationWizardPages()");
			CLTE.printStackTrace();
			Assert.fail();
		}
		
	}//End of createStructureMenuItems()
	
	
	@Test(description = "method will Verify sandbox changes written to mainline or not before sandbox publish", dependsOnMethods="createStructureMenuItems",  priority = 53)
	public void verifySandboxChanges() throws Exception {
			try {
				CommonUtils.hold(5);
				Assert.assertTrue(sbBannerUIInstance.ExitSandboxSession(SandboxName,structureInstance));
				String eleStatus;
				CommonUtils.hold(3);
				gptInstance.userDropDownIcon.click();
				CommonUtils.explicitWait(gptInstance.administration_SetupAndMaintenance,"Visible","",structureInstance);
				gptInstance.administration_SetupAndMaintenance.click();
				CommonUtils.explicitWait(ntFInstance.panelDrawer, "Click", "",structureInstance);
				eleStatus = ntFInstance.verifyMenuItem("staticPg"+uniqueID,false,structureInstance);
				if(eleStatus.equalsIgnoreCase("Found")) {
					System.out.println("Sandbox changes are written into mainline without sandbox publish");
					Assert.fail();
				}else {
					System.out.println("Sandbox changes not written into mainline");
					Assert.assertTrue(true);
				}
			}catch(Exception vSCE) {
				vSCE.printStackTrace();
			}
	
	}//VerifySandboxChanges

	@Test (description = "method will publish sandbox", dependsOnMethods= {"createSandbox","createStructureMenuItems"},priority = 54)
	public void sandboxPublish() {
		try {
			sbDetailsUIInstance.SandboxPublish(SandboxName,structureInstance);
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",structureInstance);
			CommonUtils.hold(5);
			SandboxPublishStatus = "";
		}catch(Exception sPE) {
			System.out.println("Exception in sandboxPublish()");
			sPE.printStackTrace();
			Assert.fail();
		}
	}//End Of SandboxPublish()
	
	@Test(description = "method will verify published changes",dependsOnMethods="sandboxPublish",priority = 55)
	public void verifyManilineChangesAfterPublish() {
		try {
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
				sbUiInstance.refreshToLatestConfiguration(structureInstance);
			}catch(Exception rlce) {
				System.out.println("Exception while advancing to latest configuration");
				rlce.printStackTrace();
				Assert.fail();
			}
				
			System.out.println("Advanced to latest MDS label");	
			
			CommonUtils.hold(5);*/
			gptInstance.userDropDownIcon.click();
			CommonUtils.explicitWait(gptInstance.administration_SetupAndMaintenance,"Visible","",structureInstance);
			gptInstance.administration_SetupAndMaintenance.click();
			CommonUtils.explicitWait(ntFInstance.panelDrawer, "Click", "",structureInstance);
			CommonUtils.hold(5);
				String eleStatus;
				
				eleStatus  = ntFInstance.verifyMenuItem("staticPg"+uniqueID,false,structureInstance);
				CommonUtils.hold(5);
				if(eleStatus.equalsIgnoreCase("Found")) {
					System.out.println("Sandbox changes are written into mainline post sandbox publish");
				}else {
					System.out.println("Sandbox changes are not written into mainline post sandbox publish");
					Assert.fail();
				}
		}catch(Exception VSC) {
			System.out.println("Exception in verifyManilineChangesAfterPublish()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//VerifyManilineChangesAfterPublish

	@Test(description = "method will create sandboxes for non conflict refresh on strtute tool",priority = 56)
	public void createRefreshableSandboxes() {
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gptInstance.accessFromRecentItems("Manage Sandboxes",structureInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,structureInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",structureInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);	
			
			RefreshableSandboxname = cSBInstance.CreateSandbox("Structure_Refresh", "Refreshable Sandbox with Structure repository", "Structure,Page Integration", "","YES",structureInstance);
			SandboxName = cSBInstance.CreateSandbox("Structure", "Sandbox with Structure repository", "Structure,Page Integration", "Activate","YES",structureInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,structureInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,structureInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
			CommonUtils.hold(5);
		}catch(Exception cRSE) {
			System.out.println("Exception in createRefreshableSandboxes()");
			cRSE.printStackTrace();
			Assert.fail();
		}
	}//End Of createRefreshableSandboxes()
	
	@Test(description = "method will perform changes on strtute tool within publishable sandbox session", dependsOnMethods="createRefreshableSandboxes", priority = 57)
	public void publishableSandboxChanges() {
		try {
			System.out.println("Before launchstructuretf()");
			System.out.println("Structure taskflow navigation initated");
			sbBannerUIInstance.navigatetoToolPage("Structure",structureInstance);
			System.out.println("After launchstructuretf()");
			System.out.println("Structure taskflow has been loaded");
			CommonUtils.hold(10);
			stmInstance.createPageEntryUnderExistingCategory("staticPg"+uniqueID,structureInstance);
			CommonUtils.hold(5);
			CommonUtils.explicitWait(stmInstance.createButton, "Click", "",structureInstance);
			CommonUtils.hold(5);
		}catch(Exception pSCE) {
			System.out.println("Exception in publishableSandboxChanges()");
			pSCE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishableSandboxChanges()
	
	@Test(description = "method will  publish sandbox changes to mailine", dependsOnMethods="publishableSandboxChanges", priority = 58)
	public void publishRefreshableSandboxChanges() {
		try {
			System.out.println("Initiating publish from sandbox banner");
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,structureInstance);
			
			sbUiInstance.AvailableSandboxesLabel.click();
			
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",structureInstance);
			
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
				sbUiInstance.refreshToLatestConfiguration(structureInstance);
			}catch(Exception rlce) {
				System.out.println("Exception while advancing to latest configuration");
				rlce.printStackTrace();
				Assert.fail();
			}
				
			System.out.println("Advanced to latest MDS label");	
			*/
			System.out.println("Sandbox changes are written to mainline succesfully");
		}catch(Exception pRSCE) {
			System.out.println("Exception in publishRefreshableSandboxChanges()");
			pRSCE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishRefreshableSandboxChanges()
	
	@Test(description = "method will perform non conflict sandbox refresh on Structure tool",dependsOnMethods= {"createRefreshableSandboxes","publishRefreshableSandboxChanges"},priority = 59)
	public void nonConflictSandboxRefresh() {
		try {
			System.out.println("Initiating refresh");
			System.out.println("Initiating non conflict refresh");
			sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname, "Accept",structureInstance);
			System.out.println("Refresh done and verifying");
			sbDetailsUIInstance.VerifySandboxRefresh(RefreshableSandboxname,structureInstance);
			System.out.println("Refresh verified");
			CommonUtils.hold(5);
		}catch(Exception nCSRE) {
			System.out.println("Exception in nonConflictSandboxRefresh()");
			Assert.fail();
			nCSRE.printStackTrace();
		}
	}//End of nonConflictSandboxRefresh()

	@Test(description = "method will create sandboxes to perform conflict refresh on Structure tool",priority = 60)
	public void createConflictRefreshableSandboxes() {
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gptInstance.accessFromRecentItems("Manage Sandboxes",structureInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,structureInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",structureInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);	
			SandboxName = cSBInstance.CreateSandbox("Structure", "Sandbox with Structure repository", "Structure,Page Integration", "","YES",structureInstance);
			RefreshableSandboxname = cSBInstance.CreateSandbox("Structure_Refresh", "Refreshable Sandbox with Structure repository", "Structure,Page Integration", "Activate","YES",structureInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,structureInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", RefreshableSandboxname,structureInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
		
			CommonUtils.hold(5);
			sbBannerUIInstance.navigatetoToolPage("Structure",structureInstance);
			CommonUtils.explicitWait(stmInstance.createButton, "Click", "",structureInstance);
			CommonUtils.hold(5);
			stmInstance.scrollTopOfStructureMenu(structureInstance);
			CommonUtils.hold(3);
			gptInstance.userDropDownIcon.click();
			CommonUtils.explicitWait(gptInstance.administration_SetupAndMaintenance,"Visible","",structureInstance);
			gptInstance.administration_SetupAndMaintenance.click();
			CommonUtils.explicitWait(ntFInstance.panelDrawer, "Click", "",structureInstance);
			CommonUtils.hold(5);
		}catch(Exception cCRSE) {
			System.out.println("Exception in createConflictRefreshableSandboxes() ");
			cCRSE.printStackTrace();
			Assert.fail();
		}
	}//End Of createConflictRefreshableSandboxes()
	
	@Test(description = "method will  perform conflicted structure changes in  refreshabel Sandbox", dependsOnMethods="createConflictRefreshableSandboxes", priority = 61)
	public void refreshableConflictedChanges() {
		try {
			sbBannerUIInstance.navigatetoToolPage("Structure",structureInstance);
			CommonUtils.explicitWait(stmInstance.createButton, "Click", "",structureInstance);
			CommonUtils.hold(5);
			/*stmInstance.scrollTopOfStructureMenu(structureInstance);
			CommonUtils.hold(3);*/
			stmInstance.modifyExistingCategoryPage("Me","Time",stmInstance.timeMenu2ndImg,stmInstance.timeMenu1stImg,structureInstance);
			CommonUtils.hold(5);
			CommonUtils.explicitWait(stmInstance.createButton, "Click", "",structureInstance);
			CommonUtils.hold(10);
			/*try {
				gptInstance.globalTemplateArea.click();
			}catch(Exception gTemplate) {
				sbBannerUIInstance.SandboxBanner.click();
			}*/
			sbBannerUIInstance.ExitSandboxSession(RefreshableSandboxname,structureInstance);
			CommonUtils.hold(5);
		}catch(Exception pCCE) {
			System.out.println("Exception in pulishableConflictedChanges()");
			pCCE.printStackTrace();
			Assert.fail();
		}
	}//End Of refreshableConflictedChanges()
	
	@Test(description = "method will  perform conflicted structure changes in  refreshabel Sandbox", dependsOnMethods="createConflictRefreshableSandboxes", priority = 62)
	public void publishableConflictedChanges() {
		try {
			System.out.println("Enter into publishable sandbox");
			sbUiInstance.EnterSandboxSession(SandboxName,structureInstance);
			System.out.println("Enterred into publishable sandbox");
			Assert.assertTrue(sbUiInstance.getSandboxNameElement(SandboxName,structureInstance).isDisplayed());
			CommonUtils.hold(5);
			
			sbBannerUIInstance.navigatetoToolPage("Structure",structureInstance);
			CommonUtils.explicitWait(stmInstance.createButton, "Click", "",structureInstance);
			CommonUtils.hold(5);
			
			stmInstance.modifyExistingCategoryPage("Me","Time",stmInstance.timeMenu1stImg,stmInstance.timeMenu2ndImg,structureInstance);
			CommonUtils.hold(5);
			CommonUtils.explicitWait(stmInstance.createButton, "Click", "",structureInstance);
			CommonUtils.hold(5);
		}catch(Exception pCCE) {
			System.out.println("Exception in publishableConflictedChanges()");
			pCCE.printStackTrace();
			Assert.fail();
		}
	}//End Of  publishableConflictedChanges()
	
	
	@Test(description = "method will perform conflict sandbox refresh on Structure Pages",dependsOnMethods= {"createConflictRefreshableSandboxes","refreshableConflictedChanges","publishableConflictedChanges"},priority = 63)
	public void conflictSandboxRefresh() {
		
		try {
			CommonUtils.hold(5);
			/*try {
				gptInstance.globalTemplateArea.click();
			}catch(Exception gTemplate) {
				sbBannerUIInstance.SandboxBanner.click();
			}*/
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,structureInstance);
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",structureInstance);
			
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
				sbUiInstance.refreshToLatestConfiguration(structureInstance);
			}catch(Exception rlce) {
				System.out.println("Exception while advancing to latest configuration");
				rlce.printStackTrace();
				Assert.fail();
			}
				
			System.out.println("Advanced to latest MDS label");	*/
			
			System.out.println("Initiate SB conflict refresh");
			sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname, "Accept",structureInstance);
			System.out.println("Initiate SB conflict refresh done and verifying");
			sbDetailsUIInstance.VerifySandboxRefresh(RefreshableSandboxname,structureInstance);
			CommonUtils.hold(5);
		}catch(Exception nCSRE) {
			System.out.println("Exception in conflictSandboxRefresh()");
			Assert.fail();
			nCSRE.printStackTrace();
		}
		
	}//End of nonConflictSandboxRefresh()

	@Test(description = "method will check for sandbox preview mode for structure tool",dependsOnMethods= "createConflictRefreshableSandboxes",priority = 64)
	public void strcuturePreviewMode() {
		try {
				CommonUtils.hold(5);	
			try {
				System.out.println("Checking for sandbox activation");
				CommonUtils.explicitWait(sbBannerUIInstance.SandboxBanner, "Visible", "",structureInstance);
				System.out.println("Sandbox is activated");
			}catch(Exception vMPE) {
				System.out.println("Sandbox is not activated. Activating the sandbox");
				System.out.println("Entering into Sandbox session");
				sbUiInstance.EnterSandboxSession(RefreshableSandboxname,structureInstance);
				System.out.println("Sandbox is activated.");
			}
			System.out.println("Flipping activated sandbox mode to 'PREVIEW' ");
			sbBannerUIInstance.SetSandboxPreviewMode(structureInstance);
			System.out.println("Sandbox is in 'PREVIEW' mode ");
			CommonUtils.hold(3);
			System.out.println("Navigating to Structure taskflow ");
			sbBannerUIInstance.navigatetoToolPage("Structure",structureInstance);
			CommonUtils.explicitWait(stmInstance.previewTextElement, "Visible", "",structureInstance);
			Assert.assertTrue(stmInstance.previewTextElement.isDisplayed());
			
		}catch(Exception sPME) {
			System.out.println("Exception in strcuturePreviewMode()");
			sPME.printStackTrace();
			Assert.fail();
		}
	}//End Of strcuturePreviewMode()

	@AfterClass(alwaysRun = true)
	public void Logout() {
		try {
			aLoginInstance.logout(structureInstance);

		} catch (Exception ex) {
			aLoginInstance.launchURL(structureInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(structureInstance);
		}finally {
			try {
				//uSBEnvModeInstance.getDbResource().dbConnectionClose();
				System.out.println("Opened db connection has been closed");
				structureInstance.quit();
				System.out.println("driver instance has been closed and quit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing object instance ");
			}
		}

	}//End Of Logout()

}//End of Structure class
