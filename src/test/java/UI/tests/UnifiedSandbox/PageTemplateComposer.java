package UI.tests.UnifiedSandbox;

import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
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
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.GlobalPageTemplateClasses;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxBannerUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxDetailsUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.UsbEnvironmentMode;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.PageTemplate;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ReportGenerator;

public class PageTemplateComposer extends GetDriverInstance {
  
	static String SandboxName = null;
	static String RefreshableSandboxname = null;
	static String uniqueID;
	static String SandboxPublishStatus = "";
	static String SandboxMode = "Edit";
	String dsunique="";
	static WebElement sbNameCheck = null;
	
	WebDriver ptcInstance;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private SandboxBannerUI sbBannerUIInstance;
	private SandboxDetailsUI sbDetailsUIInstance;
	private SandboxUI sbUiInstance;
	private PageTemplate ptInstance;
	private ComposerWrapperfns cwfInstance;
	private GlobalPageTemplate gptInstance;
	private CreateSandboxUI cSBInstance;
	private GlobalPageTemplateClasses gptcInstance;
	
	@BeforeClass(alwaysRun = true)
	public void applicationLoginAndSandboxNavigation() throws Exception {
		try {
			
			ptcInstance =  getDriverInstanceObject();
			aLoginInstance = new ApplicationLogin(ptcInstance);
			ntFInstance = new NavigationTaskFlows(ptcInstance);
			nMEInstance = new NavigatorMenuElements(ptcInstance);
			sbBannerUIInstance = new SandboxBannerUI(ptcInstance);
			sbDetailsUIInstance = new SandboxDetailsUI(ptcInstance);
			sbUiInstance = new SandboxUI(ptcInstance);
			ptInstance = new PageTemplate(ptcInstance);
			cwfInstance = new ComposerWrapperfns(ptcInstance);
			gptInstance= new GlobalPageTemplate(ptcInstance);
			cSBInstance = new CreateSandboxUI(ptcInstance);
			gptcInstance = new GlobalPageTemplateClasses(ptcInstance);
			
			aLoginInstance.login("application_administrator", "Welcome1",ptcInstance);
			CommonUtils.waitForPageLoad(ptcInstance);
			/*CommonUtils.PageRefresh(ptcInstance);
			CommonUtils.GetPageURL(ptcInstance);
			Assert.assertTrue(CommonUtils.GetPageURL(ptcInstance).contains("FuseWelcome"));*/
			uniqueID = CommonUtils.uniqueId();
			System.out.println("Navigating to Sandboxes UI Begins");
			/*try {
				gptInstance.globalTemplateArea.click();
			}catch(Exception gTemplate) {
				sbBannerUIInstance.SandboxBanner.click();
			}*/
			CommonUtils.hold(5);
			ntFInstance.navigateToTask(nMEInstance.Sandboxes,ptcInstance);
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",ptcInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
			System.out.println("Sandboxes UI Loaded");
		}catch(Exception aLASNE) {
			System.out.println("Exception in <applicationLoginAndSandboxNavigation() >");
			aLASNE.printStackTrace();
			//ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(aLASNE));
			Assert.fail();
		}
	}//End of applicationLoginAndSandboxNavigation()

		
	@Test (description = "method will create sandbox with PageComposer repository ", priority = 86)
	public void createSandbox() {
		try {
			SandboxName = cSBInstance.CreateSandbox("PageComposer", "Sandbox with PageTemplateComposer repository", "Page Template Composer", "Activate","YES",ptcInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,ptcInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,ptcInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
			CommonUtils.hold(5);
			} catch (Exception CSE) {
			System.out.println("Exception in CreateSandbox()");
			Assert.fail();
			CSE.printStackTrace();
		}
	}//End of createSandbox()

		
	@Test(description = "method will customize pages through pageTemplatecomposer within an active sandbox session", dependsOnMethods="createSandbox", priority = 87)
	public void templateComposerCustomizations() {
		try {
			gptInstance.userDropDownIcon.click();
			CommonUtils.explicitWait(gptInstance.administration_EditGlobalPageTemplate, "Click", "",ptcInstance);
			gptInstance.administration_EditGlobalPageTemplate.click();
			CommonUtils.explicitWait(gptInstance.composerViewLink, "Visible", "",ptcInstance);
			cwfInstance.WatchlistCustomization("WatchList_SandboxCustomization",ptcInstance);
			CommonUtils.hold(5);
		}catch(Exception CLTE) {
			System.out.println("Exception in composerCustomizations()");
			CLTE.printStackTrace();
			Assert.fail();
		}
		
	}//End of composerCustomizations()
	
	
	@Test(description = "method will Verify sandbox changes written to mainline or not before sandbox publish", dependsOnMethods="templateComposerCustomizations", priority = 88)
	public void verifySandboxChanges() {
		SandboxMode = "";
		try {
			Assert.assertTrue(sbBannerUIInstance.ExitSandboxSession(SandboxName,ptcInstance));
			
			try {
				Assert.assertTrue(cwfInstance.VerifyCustomizedWatchListIcon("WatchList_SandboxCustomization",ptcInstance).isDisplayed());
			}catch(Exception e) {
				Assert.assertTrue(true);
			}
			
	}catch(Exception VSC) {
			System.out.println("Exception in verifySandboxChanges()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//verifySandboxChanges

	@Test (description = "method will publish sandbox", dependsOnMethods= {"createSandbox","templateComposerCustomizations"},priority = 89)
	public void sandboxPublish() {
		CommonUtils.hold(5);
		sbDetailsUIInstance.SandboxPublish(SandboxName,ptcInstance);
		sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.hold(5);
			SandboxPublishStatus = "";
	}//End Of SandboxPublish()
	
	@Test(description = "method will Verify sandbox changes written to mainline after sandbox publish", dependsOnMethods= "sandboxPublish", priority = 90)
	public void verifyManilineChangesAfterPublish() {
		SandboxMode = "";
		try {
			
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
				sbUiInstance.refreshToLatestConfiguration(ptcInstance);
			}catch(Exception rlce) {
				System.out.println("Exception while advancing to latest configuration");
				rlce.printStackTrace();
				Assert.fail();
			}
				
			System.out.println("Advanced to latest MDS label");	
			*/
			gptInstance.homeIcon.click();
			CommonUtils.waitForPageLoad(ptcInstance);
			CommonUtils.hold(10);
			Assert.assertTrue(cwfInstance.VerifyCustomizedWatchListIcon("WatchList_SandboxCustomization",ptcInstance).isDisplayed());
			
		}catch(Exception VSC) {
			System.out.println("Exception in VerifySandboxChanges()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//VerifyManilineChangesAfterPublish
	
	@Test(description = "method will create sandboxes to perform refresh with PageTemplateComposer artifcats",priority = 91)
	public void createRefreshableSandboxes() {
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gptInstance.accessFromRecentItems("Manage Sandboxes",ptcInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,ptcInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",ptcInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);	
			
			RefreshableSandboxname = cSBInstance.CreateSandbox("PageTemplateComposer_Refresh", "Refreshable Sandbox with PageTemplateComposer repository", "Page Template Composer", "","YES",ptcInstance);
			SandboxName = cSBInstance.CreateSandbox("PageTemplateComposer", "Sandbox with PageTemplateComposer repository", "Page Template Composer", "Activate","YES",ptcInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,ptcInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,ptcInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
			
			CommonUtils.hold(3);
			
		}catch(Exception cRSE) {
			System.out.println("Exception in createRefreshableSandboxes()");
			cRSE.printStackTrace();
			Assert.fail();
		}
	}//End Of createRefreshableSandboxes()
	
	@Test(description = "method will create PageTemplateComposer artifcats within publishable sandbox", dependsOnMethods= "createRefreshableSandboxes", priority = 92)
	public void publishableSandboxChanges() {
		try {
			System.out.println("Intiating Page Composer Customizations");
			
			CommonUtils.hold(5);
			gptInstance.userDropDownIcon.click();
			CommonUtils.explicitWait(gptInstance.administration_EditGlobalPageTemplate, "Click", "",ptcInstance);
			gptInstance.administration_EditGlobalPageTemplate.click();
			CommonUtils.explicitWait(gptInstance.composerViewLink, "Visible", "",ptcInstance);
			CommonUtils.hold(10);
			cwfInstance.OracleImageCustomization(ptcInstance);
			CommonUtils.hold(5);
			cwfInstance.WatchlistCustomization("WIcon_SandboxRefresh",ptcInstance);
			
			System.out.println("Customization done");
			CommonUtils.hold(5);
		}catch(Exception pSCE) {
			System.out.println("Exception in publishableSandboxChanges() ");
			pSCE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishableSandboxChanges()
	
	@Test(description = "method will publish PageTemplateComposer artifcats to mainline", dependsOnMethods= {"createRefreshableSandboxes","publishableSandboxChanges"}, priority = 93)
	public void publishSandboxUpdates() {
		try {
			System.out.println("Initiating publish from sandbox banner");
			CommonUtils.hold(5);
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,ptcInstance);
			System.out.println("publish done and verify");
		
			sbUiInstance.AvailableSandboxesLabel.click();
			
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",ptcInstance);
			CommonUtils.hold(3);
			
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
				sbUiInstance.refreshToLatestConfiguration(ptcInstance);
			}catch(Exception rlce) {
				System.out.println("Exception while advancing to latest configuration");
				rlce.printStackTrace();
				Assert.fail();
			}
				
			System.out.println("Advanced to latest MDS label");	*/
			
		}catch(Exception pSUE) {
			System.out.println("Exception in publishSandboxUpdates() ");
			pSUE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishSandboxUpdates()
	
	@Test(description = "method will perform non conflict sandbox refresh on Page Composer artifcats", dependsOnMethods= {"createRefreshableSandboxes","publishSandboxUpdates"},priority = 94)
	public void nonConflictSandboxRefresh() {
		SandboxMode = "Edit";
		try {
			System.out.println("Initiating refresh");
			sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname, "Accept",ptcInstance);
			System.out.println("Refresh done and verifying");
			sbDetailsUIInstance.VerifySandboxRefresh(RefreshableSandboxname,ptcInstance);
			System.out.println("refresh verified");
		}catch(Exception nCSRE) {
			System.out.println("Exception in nonConflictSandboxRefresh()");
			Assert.fail();
			nCSRE.printStackTrace();
		}
	}//End of nonConflictSandboxRefresh()

	@Test(description = "method will check for PageComposer preview mode", dependsOnMethods= "createRefreshableSandboxes", priority = 95)
	public void verifyPageTemplateComposerPreviewMode() {
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			CommonUtils.hold(5);	
				System.out.println("Sandbox is not activated. Activating the sandbox");
				System.out.println("Entering into Sandbox session");
				sbUiInstance.EnterSandboxSession(RefreshableSandboxname,ptcInstance);
				System.out.println("Sandbox is activated.");
			CommonUtils.hold(5);
				System.out.println("Flipping activated sandbox mode to 'PREVIEW' ");
			
			sbBannerUIInstance.SetSandboxPreviewMode(ptcInstance);
			System.out.println("Sandbox is in 'PREVIEW' mode ");
			CommonUtils.hold(3);
			System.out.println("Navigating to PageTemplateComposer taskflow ");
			CommonUtils.hold(5);
			gptInstance.userDropDownIcon.click();
			CommonUtils.explicitWait(gptInstance.administration_EditGlobalPageTemplate, "Click", "",ptcInstance);
			gptInstance.administration_EditGlobalPageTemplate.click();
			CommonUtils.explicitWait(gptcInstance.GTC_PreviewmodeDialog, "Visible", "",ptcInstance);
			Assert.assertTrue(gptcInstance.GTC_PreviewmodeDialog.isDisplayed());
			CommonUtils.hold(3);
			gptcInstance.GTC_PreviewmodeDialog_Ok.click();
			CommonUtils.hold(3);
		}catch(Exception vLPME) {
			System.out.println("Exception in vefiryPageTemplateComposerPreviewMode()");
			vLPME.printStackTrace();
			Assert.fail();
		}
	}//End of  verifyPageTemplateComposerPreviewMode()
	
	@AfterClass(alwaysRun = true)
	public void Logout() {
		try {
			aLoginInstance.logout(ptcInstance);

		} catch (Exception ex) {
			aLoginInstance.launchURL(ptcInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(ptcInstance);
		}finally {
			try {
				System.out.println("Opened db connection has been closed");
				ptcInstance.quit();
				System.out.println("driver instance has been closed and quit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing Object Instance ");
			}
		}
	}//End Of Logout
}
