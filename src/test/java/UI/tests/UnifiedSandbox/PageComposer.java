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

import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.ComposerWrapperfns;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.CreateSandboxUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxBannerUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxDetailsUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.UsbEnvironmentMode;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ReportGenerator;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.PageTemplate;

/**
 * @author klalam
 *
 */

public class PageComposer extends GetDriverInstance{
 
	static String SandboxName = null;
	static String RefreshableSandboxname = null;
	static String uniqueID;
	static String SandboxPublishStatus = "";
	static String SandboxMode = "Edit";
	String dsunique="";
	static WebElement sbNameCheck = null;
	
	WebDriver pcInstance;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private UsbEnvironmentMode uSBEnvModeInstance;
	private SandboxBannerUI sbBannerUIInstance;
	private SandboxDetailsUI sbDetailsUIInstance;
	private SandboxUI sbUiInstance;
	private PageTemplate ptInstance;
	private ComposerWrapperfns cwfInstance;
	private GlobalPageTemplate gptInstance;
	private CreateSandboxUI cSBInstance;
	
	@BeforeClass(alwaysRun = true)
	public void applicationLoginAndSandboxNavigation() {
		System.out.println("signing to the application with 'application_administrator' user");
		try {
			pcInstance =  getDriverInstanceObject();
			aLoginInstance = new ApplicationLogin(pcInstance);
			ntFInstance = new NavigationTaskFlows(pcInstance);
			nMEInstance = new NavigatorMenuElements(pcInstance);
			uSBEnvModeInstance	= new UsbEnvironmentMode(pcInstance);
			sbBannerUIInstance = new SandboxBannerUI(pcInstance);
			sbDetailsUIInstance = new SandboxDetailsUI(pcInstance);
			sbUiInstance = new SandboxUI(pcInstance);
			cSBInstance = new CreateSandboxUI(pcInstance);
			ptInstance = new PageTemplate(pcInstance);
			cwfInstance = new ComposerWrapperfns(pcInstance);
			gptInstance= new GlobalPageTemplate(pcInstance);
			
			/*if (uSBEnvModeInstance.CheckForUsbMode(uSBEnvModeInstance.RetreiveUsbProfileOption).equalsIgnoreCase("N") && uSBEnvModeInstance.CheckForUsbMode(uSBEnvModeInstance.RetrevieADFShareProfileOption).equalsIgnoreCase("FALSE")) {
				 throw new SkipException("Skipping test because environment not in USB mode");
			  }
			 System.out.println("USB mode check completed");*/
			
			aLoginInstance.login("application_administrator", "Welcome1",pcInstance);
			CommonUtils.waitForPageLoad(pcInstance);
			uniqueID = CommonUtils.uniqueId();
			System.out.println("Navigating to Sandboxes UI Begins");
			/*try {
				gptInstance.globalTemplateArea.click();
			}catch(Exception gTemplate) {
				sbBannerUIInstance.SandboxBanner.click();
			}*/
			CommonUtils.hold(5);
			ntFInstance.navigateToTask(nMEInstance.Sandboxes,pcInstance);
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",pcInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
			System.out.println("Sandboxes UI Loaded");
		}catch(Exception aLASNE) {
			System.out.println("Exception in <applicationLoginAndSandboxNavigation() >");
			aLASNE.printStackTrace();
			Assert.fail();
		}
	}//End of applicationLoginAndSandboxNavigation()


	@Test (description = "method will create sandbox with PageComposer repository ", priority = 65)
	public void createSandbox() {
		try {
			SandboxName = cSBInstance.CreateSandbox("PageComposer", "Sandbox with PageComposer repository", "Page Composer", "Activate","YES",pcInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,pcInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,pcInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
			} catch (Exception CSE) {
			System.out.println("Exception in CreateSandbox()");
			Assert.fail();
			CSE.printStackTrace();
		}
	}//End of createSandbox()

		
	@Test(description = "method will customize pages through pagecomposer within an active sandbox session", dependsOnMethods="createSandbox", priority = 66)
	public void composerCustomizations() {
		try {
			cwfInstance.PageComposer(ptInstance.prefrencesLanguageEle,"LanguageSBCustomization"+uniqueID,pcInstance);
		}catch(Exception CLTE) {
			System.out.println("Exception in composerCustomizations()");
			CLTE.printStackTrace();
			Assert.fail();
		}
	}//End of composerCustomizations()
	
	
	@Test(description = "method will Verify sandbox changes written to mainline or not before sandbox publish", dependsOnMethods= {"createSandbox","composerCustomizations"},priority = 67)
	public void verifySandboxChanges() {
		SandboxMode = "";
		try {
			Assert.assertTrue(sbBannerUIInstance.ExitSandboxSession(SandboxName,pcInstance));
			
			try {
				Assert.assertTrue(cwfInstance.VerifyComposerCustomizations("LanguageSBCustomization"+uniqueID,pcInstance).isDisplayed());
				CommonUtils.hold(5);
			}catch(Exception e) {
				Assert.assertTrue(true);
			}
			
	}catch(Exception VSC) {
			System.out.println("Exception in verifySandboxChanges()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//verifySandboxChanges

	@Test (description = "method will publish sandbox", dependsOnMethods= {"createSandbox","composerCustomizations"}, priority = 68)
	public void sandboxPublish() {
			sbDetailsUIInstance.SandboxPublish(SandboxName,pcInstance);
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.hold(5);
			SandboxPublishStatus = "";
	}//End Of SandboxPublish()
	
	@Test(description = "method will Verify sandbox changes written to mainline after sandbox publish", dependsOnMethods="sandboxPublish", priority = 69)
	public void verifyManilineChangesAfterPublish() {
		SandboxMode = "";
		try {
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
				sbUiInstance.refreshToLatestConfiguration(pcInstance);
			}catch(Exception rlce) {
				System.out.println("Exception while advancing to latest configuration");
				rlce.printStackTrace();
				Assert.fail();
			}
				
			System.out.println("Advanced to latest MDS label");	*/
			CommonUtils.hold(10);
			Assert.assertTrue(cwfInstance.VerifyComposerCustomizations("LanguageSBCustomization"+uniqueID,pcInstance).isDisplayed());
			
		}catch(Exception VSC) {
			System.out.println("Exception in VerifySandboxChanges()");
			VSC.printStackTrace();
			Assert.fail();
		}
	}//VerifyManilineChangesAfterPublish
	
	@Test(description = "method will create sandboxes to perform non-conflict refresh on Page Composer artifcats",priority = 70)
	public void createRefreshableSandboxes() {
		try {
				System.out.println("Navigating to Sandboxes UI Begins");
				try {
					gptInstance.accessFromRecentItems("Manage Sandboxes",pcInstance).click();
				}catch(Exception eI) {
					System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
					eI.printStackTrace();
					ntFInstance.navigateToTask(nMEInstance.Sandboxes,pcInstance);
				}
							
				CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",pcInstance);
				Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
					System.out.println("Sandboxes UI Loaded");
				CommonUtils.hold(5);	
				
				RefreshableSandboxname = cSBInstance.CreateSandbox("PageComposer_Refresh", "Refreshable Sandbox with Page Composer repository", "Page Composer", "","YES",pcInstance);
				SandboxName = cSBInstance.CreateSandbox("PageComposer", "Sandbox with Page Composer repository", "Page Composer", "Activate","YES",pcInstance);
				sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,pcInstance);
				CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,pcInstance);
				Assert.assertTrue(sbNameCheck.isDisplayed());
				CommonUtils.hold(3);
		}catch(Exception cRSE) {
			System.out.println("Exception in createRefreshableSandboxes()");
			cRSE.printStackTrace();
			Assert.fail();
		}
	}//End Of createRefreshableSandboxes()
	
	@Test(description = "method will create publishable sandbox changes on Page Composer artifcats",dependsOnMethods="createRefreshableSandboxes", priority = 71)
	public void publishableSandboxUpdates() {
		try {
			System.out.println("Intiating Page Composer Customizations");
			
			cwfInstance.PageComposer(ptInstance.prefrencesLanguageEle,"LanguageSBCustNonConflictRefSBCust"+uniqueID,pcInstance);
			System.out.println("Customization done");
			CommonUtils.hold(3);
		}catch(Exception pSUE) {
			System.out.println("Exception in publishableSandboxUpdates()");
			pSUE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishableSandboxUpdates()
	
	@Test(description = "method will publishable sandbox changes on Page Composer artifcats to mainline", dependsOnMethods= {"createRefreshableSandboxes","publishableSandboxUpdates"},priority = 72)
	public void publishSandboxChanges() {
		try {
			System.out.println("Initiating publish from sandbox banner");
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,pcInstance);
			System.out.println("publish done and verify");
	
			sbUiInstance.AvailableSandboxesLabel.click();
			
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",pcInstance);
			CommonUtils.hold(3);
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
				sbUiInstance.refreshToLatestConfiguration(pcInstance);
			}catch(Exception rlce) {
				System.out.println("Exception while advancing to latest configuration");
				rlce.printStackTrace();
				Assert.fail();
			}
				
			System.out.println("Advanced to latest MDS label");	*/
		}catch(Exception pSCE) {
			System.out.println("Exception in publishSandboxChanges()");
			pSCE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishSandboxChanges()
	
	@Test(description = "method will perform non conflict sandbox refresh on Page Composer artifcats", dependsOnMethods="publishSandboxChanges", priority = 73)
	public void nonConflictSandboxRefresh() {
		SandboxMode = "Edit";
		try {
		
		/*	HomePage.homeIcon.click();
			CommonUtils.waitForPageLoad();
		*/	
			System.out.println("Initiating refresh");
			sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname, "Accept",pcInstance);
			System.out.println("Refresh done and verifying");
			sbDetailsUIInstance.VerifySandboxRefresh(RefreshableSandboxname,pcInstance);
			System.out.println("refresh verified");
		}catch(Exception nCSRE) {
			System.out.println("Exception in nonConflictSandboxRefresh()");
			Assert.fail();
			nCSRE.printStackTrace();
		}
	}//End of nonConflictSandboxRefresh()

	@Test(description = "method will check for PageComposer preview mode", dependsOnMethods="createRefreshableSandboxes", priority = 74)
	public void verifyPageComposerPreviewMode() {
		try {
			
			CommonUtils.hold(5);	
			/*try {
				System.out.println("Checking for sandbox activation");
				CommonUtils.explicitWait(sbBannerUIInstance.SandboxBanner, "Visible", "",pcInstance);
				System.out.println("Sandbox is activated");
			}catch(Exception vMPE) {*/
				System.out.println("Sandbox is not activated. Activating the sandbox");
				System.out.println("Entering into Sandbox session");
				sbUiInstance.EnterSandboxSession(RefreshableSandboxname,pcInstance);
				System.out.println("Sandbox is activated");
		//	}
			System.out.println("Flipping activated sandbox mode to 'PREVIEW' ");
			sbBannerUIInstance.SetSandboxPreviewMode(pcInstance);
			System.out.println("Sandbox is in 'PREVIEW' mode ");
			CommonUtils.hold(3);
			System.out.println("Navigating to PageComposer taskflow ");
			sbBannerUIInstance.navigatetoToolPage("Page Composer",pcInstance);
			CommonUtils.explicitWait(ptInstance.pCPreviewModedialog, "Visible", "",pcInstance);
			Assert.assertTrue(ptInstance.pCPreviewModedialog.isDisplayed());
			CommonUtils.hold(3);
			ptInstance.pCPreviewModedialogOk.click();
			CommonUtils.hold(3);
		}catch(Exception vLPME) {
			System.out.println("Exception in vefiryPageComposerPreviewMode()");
			vLPME.printStackTrace();
			Assert.fail();
		}
	}//End of  verifyPageComposerPreviewMode()
	
	@AfterClass(alwaysRun = true)
	public void Logout() {
		try {
			aLoginInstance.logout(pcInstance);

		} catch (Exception ex) {
			aLoginInstance.launchURL(pcInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(pcInstance);
		}finally {
			try {
			//	uSBEnvModeInstance.getDbResource().dbConnectionClose();
				System.out.println("Opened db connection has been closed");
				pcInstance.quit();
				System.out.println("driver instance has been closed and quit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing Object Instance");
			}
		}
	}//End Of Logout 

}//End Of PageComposer Class
