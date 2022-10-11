package UI.tests.UnifiedSandbox;

import java.sql.SQLException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.*;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ReportGenerator;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import UtilClasses.UI.SetupAndMaintenance;

/**
 * @author klalam
 *
 */

public class PageIntegrationWizard extends GetDriverInstance {
	
	static String SandboxName = null;
	static String RefreshableSandboxname = null;
	static String uniqueID;
	static String SandboxPublishStatus = "";
	static String SandboxMode = "Edit";
	static WebElement sbNameCheck = null;
	
	WebDriver piwInstance;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private SandboxBannerUI sbBannerUIInstance;
	private SandboxDetailsUI sbDetailsUIInstance;
	private SandboxUI sbUiInstance;
	private CreateSandboxUI cSBInstance;
	private GlobalPageTemplate gptInstance;
	private PageIntegrationWizardTestPages pIWTPInstance;
	private NewAndExistingPages nAEPInstance;
	private CreatePage cpInstance;
	private PageIntegrationWizardPagesRoles piwPageRolesInstance;
	
	@BeforeClass(alwaysRun = true)
	public void applicationLoginAndSandboxNavigation() {
		try {
			piwInstance =  getDriverInstanceObject();
			aLoginInstance = new ApplicationLogin(piwInstance);
			ntFInstance = new NavigationTaskFlows(piwInstance);
			nMEInstance = new NavigatorMenuElements(piwInstance);
			sbBannerUIInstance = new SandboxBannerUI(piwInstance);
			sbDetailsUIInstance = new SandboxDetailsUI(piwInstance);
			sbUiInstance = new SandboxUI(piwInstance);
			cSBInstance = new CreateSandboxUI(piwInstance);
			gptInstance = new GlobalPageTemplate(piwInstance);
			pIWTPInstance = new PageIntegrationWizardTestPages(piwInstance);
			nAEPInstance = new NewAndExistingPages(piwInstance);
			cpInstance = new CreatePage(piwInstance);
			piwPageRolesInstance = new PageIntegrationWizardPagesRoles(piwInstance);
			
			/*
			 * Check and Create custom roles in the environment to assign newly created page through PIW tool		
			 */
			
			aLoginInstance.login("it_security_manager", "Welcome1",piwInstance);
			CommonUtils.waitForPageLoad(piwInstance);
			CommonUtils.hold(5);
			
			System.out.println("SecurityConsole Menu From FuseWelcome Page");
		
			ntFInstance.navigateToTask(nMEInstance.SecurityConsole,piwInstance);
			CommonUtils.waitForPageLoad(piwInstance);
			CommonUtils.explicitWait(piwInstance.findElement(By.xpath("//div[contains(@id,'ASE_ADMIN_WORKAREA::text')]")), "Visible", "",piwInstance);
			
			CommonUtils.hold(5);
			
			System.out.println("Roles Verificaiton and Creation");
			if(!(piwPageRolesInstance.isRoleExisted("FND_EXTENSIONS_TEST_ROLE_USB_PIW_FSCM_AIC",piwInstance))) {
				System.out.println("Role <FND_EXTENSIONS_TEST_ROLE_USB_PIW_FSCM_AIC> doesn't Existed, createing role");
					piwPageRolesInstance.createRole("FND_EXTENSIONS_TEST_ROLE_USB_PIW_FSCM_AIC", "APP_IMPL_CONSULTANT",piwInstance);
				CommonUtils.hold(5);
				System.out.println("Role <FND_EXTENSIONS_TEST_ROLE_USB_PIW_FSCM_AIC> created");
			}else
				System.out.println("Role <FND_EXTENSIONS_TEST_ROLE_USB_PIW_FSCM_AIC> is Existed");
			CommonUtils.hold(5);
			
			if(!(piwPageRolesInstance.isRoleExisted("FND_EXTENSIONS_TEST_ROLE_USB_PIW_HCM",piwInstance))) {
				System.out.println("Role <FND_EXTENSIONS_TEST_ROLE_USB_PIW_HCM> doesn't Existed, createing role");
					piwPageRolesInstance.createRole("FND_EXTENSIONS_TEST_ROLE_USB_PIW_HCM", "APP_IMPL_CONSULTANT",piwInstance);
					System.out.println("Role <FND_EXTENSIONS_TEST_ROLE_USB_PIW_HCM> created");
			}else
				System.out.println("Role <FND_EXTENSIONS_TEST_ROLE_USB_PIW_HCM> is Existed");
			CommonUtils.hold(5);
			System.out.println("Logging out from IT_SECURITY_MANAGER user session");
			
			try {
				aLoginInstance.logout(piwInstance);

			} catch (Exception ex) {
				aLoginInstance.launchURL(piwInstance);
				CommonUtils.hold(2);
				aLoginInstance.logout(piwInstance);
			}
			
			System.out.println("Loggin to APP_IMPL_CONSULTANT user session");
			
			aLoginInstance.login("app_impl_consultant", "Welcome1",piwInstance);
			CommonUtils.waitForPageLoad(piwInstance);
			CommonUtils.GetPageURL(piwInstance);
			Assert.assertTrue(CommonUtils.GetPageURL(piwInstance).contains("FuseWelcome"));
			uniqueID = CommonUtils.uniqueId();
			System.out.println("Navigating to Sandboxes UI Begins");
			CommonUtils.hold(3);
			ntFInstance.navigateToTask(nMEInstance.Sandboxes,piwInstance);
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",piwInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
			System.out.println("Sandboxes UI Loaded");
		}catch(Exception aLASNE) {
			System.out.println("Exception in <applicationLoginAndSandboxNavigation() >");
			aLASNE.printStackTrace();
		//	ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(aLASNE));
			Assert.fail();
		}
	}//End of applicationLoginAndSandboxNavigation()

	@Test (description = "method will create sandbox with PIW repository ", priority =110)
	public void createSandbox() {
		try {
			SandboxName = cSBInstance.CreateSandbox("PageIntegrationWizard", "Sandbox with lookup repository", "Page Integration", "Activate","YES",piwInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,piwInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,piwInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
			CommonUtils.hold(5);
			} catch (Exception CSE) {
			System.out.println("Exception in CreateSandbox()");
			Assert.fail();
			CSE.printStackTrace();
		}
	}//End of CreateSandbox()
	
	@Test (description = "method will launch Page Integration Wizard taskflow", dependsOnMethods="createSandbox", priority = 111)
	public void launchPageIntegrationWizardtf() {
		try {
			sbBannerUIInstance.navigatetoToolPage("Page Integration",piwInstance);
			CommonUtils.explicitWait(nAEPInstance.tab_NewPages, "Click", "",piwInstance);
			
			if(sbBannerUIInstance.SandboxMode.getText().contains("Edit"))
				Assert.assertTrue(nAEPInstance.newpage.isDisplayed());
			else if(sbBannerUIInstance.SandboxMode.getText().contains("Preview"))
				Assert.assertTrue(nAEPInstance.PageIntegrationWizard_PreviewMode.isDisplayed());
		
		} catch (Exception LLTE) {
			System.out.println("Exception in PageIntegrationWizars taskflow launch method");
			LLTE.printStackTrace();
			Assert.fail();
		}
	}//End of PageIntegrationWizardtf()
	
	

	@Test(description = "method will create pages within an active sandbox session", dependsOnMethods= {"createSandbox","launchPageIntegrationWizardtf"}, priority = 112)
	public void createPageIntegrationWizardPages() {
		try {
			
			pIWTPInstance.createPage("G1"+uniqueID, "FND_EXTENSIONS_TEST_ROLE_USB_PIW_FSCM_AIC");
			
			CommonUtils.hold(5);
			
			pIWTPInstance.createPage("G2"+uniqueID, "FND_EXTENSIONS_TEST_ROLE_USB_PIW_FSCM_AIC");
			
			CommonUtils.hold(5);
			
		//	PageIntegrationWizardTestPages.addTabToExistingPage("Sales","Opportunities","Oppr_Tb1"+uniqueID,"FND_EXTENSIONS_TEST_ROLE_USB_PIW_CRM");
			pIWTPInstance.addTabToExistingPage("My Team","Goals","gOAL_Tb1"+uniqueID,"FND_EXTENSIONS_TEST_ROLE_USB_PIW_HCM","Test_tab",piwInstance);
			
			
		}catch(Exception CLTE) {
			System.out.println("Exception in createPageIntegrationWizardPages()");
			CLTE.printStackTrace();
			Assert.fail();
		}
		
	}//End of createPages()
	
	
	@Test(description = "method will Verify sandbox changes written to mainline or not before sandbox publish", dependsOnMethods="createPageIntegrationWizardPages", priority = 113)
	public void verifySandboxChanges() throws Exception {
				String eleStatus;
			try {
				sbBannerUIInstance.ExitSandboxSession(SandboxName,piwInstance);
					    CommonUtils.hold(3);
						gptInstance.userDropDownIcon.click();
						CommonUtils.explicitWait(gptInstance.administration_SetupAndMaintenance,"Visible","",piwInstance);
						gptInstance.administration_SetupAndMaintenance.click();
						CommonUtils.explicitWait(ntFInstance.panelDrawer, "Click", "",piwInstance);
						try {
							gptInstance.globalTemplateArea.click();
						}catch(Exception gTemplate) {
							sbBannerUIInstance.SandboxBanner.click();
						}
						CommonUtils.hold(2);
						eleStatus = ntFInstance.verifyMenuItem("G1"+uniqueID,false,piwInstance);
						
						if(eleStatus.equalsIgnoreCase("Found")) {
							System.out.println("Sandbox changes are written into mainline without sandbox publish");
							Assert.fail();
						}
			}catch(Exception vSCE) {
				System.out.println("Sandbox changes not written into mainline");
				Assert.assertTrue(true);
				}
	}//VerifySandboxChanges

	@Test (description = "method will publish sandbox", dependsOnMethods= {"createSandbox","createPageIntegrationWizardPages"}, priority = 114)
	public void sandboxPublish() {
			
			CommonUtils.hold(10);
			sbDetailsUIInstance.SandboxPublish(SandboxName,piwInstance);
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.hold(5);
			SandboxPublishStatus = "";
	}//End Of SandboxPublish()

	@Test(description = "method will Verify sandbox changes written to mainline after sandbox publish", dependsOnMethods="sandboxPublish", priority = 115)
	public void verifyManilineChangesAfterPublish() {
		try {
			
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
				sbUiInstance.refreshToLatestConfiguration(piwInstance);
			}catch(Exception rlce) {
				System.out.println("Exception while advancing to latest configuration");
				rlce.printStackTrace();
				Assert.fail();
			}
				
			System.out.println("Advanced to latest MDS label");	
			
			CommonUtils.hold(5);*/
			gptInstance.userDropDownIcon.click();
			CommonUtils.explicitWait(gptInstance.administration_SetupAndMaintenance,"Visible","",piwInstance);
			gptInstance.administration_SetupAndMaintenance.click();
			CommonUtils.explicitWait(ntFInstance.panelDrawer, "Click", "",piwInstance);
			CommonUtils.hold(3);
			String eleStatus;
			eleStatus  = ntFInstance.verifyMenuItem("G1"+uniqueID,false,piwInstance);
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
	
	@Test(description = "method will create sandboxes to perfrom nonconflict refresh with PageIntegationWizard tool",priority = 116)
	public void createRefeshableSandboxes() {
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gptInstance.accessFromRecentItems("Manage Sandboxes",piwInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,piwInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",piwInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);	
			RefreshableSandboxname = cSBInstance.CreateSandbox("PIWSandbox_Refresh", "Refreshable Sandbox with PIW repository", "Page Integration", "","YES",piwInstance);
			SandboxName = cSBInstance.CreateSandbox("PIWSandbox", "Sandbox with PIW repository", "Page Integration", "Activate","YES",piwInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,piwInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,piwInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
			CommonUtils.hold(3);
		}catch(Exception cRSE) {
			System.out.println("Exception in createRefeshableSandboxes()");
			cRSE.printStackTrace();
			Assert.fail();
		}
	}//End Of createRefeshableSandboxes()
	
	@Test(description = "method will create publishable changes within sandbox session on PageIntegationWizard tool", dependsOnMethods="createRefeshableSandboxes", priority = 117)
	public void publishableSandboxChanges() {
		try {
			System.out.println("Initiating PIW Customization");
			CommonUtils.hold(5);
			sbBannerUIInstance.navigatetoToolPage("Page Integration",piwInstance);
			
			CommonUtils.explicitWait(nAEPInstance.tab_NewPages, "Click", "",piwInstance);
		
			System.out.println("PIW tf loaded");
			
			CommonUtils.hold(5); 
			pIWTPInstance.addTabToPage("G1"+uniqueID,"G2_tb_Automation_2"+uniqueID,"FND_EXTENSIONS_TEST_ROLE_USB_PIW_FSCM_AIC","Test_tab",piwInstance);
			
			CommonUtils.hold(5);
			nAEPInstance.tab_ExistinPages.click();
			CommonUtils.explicitWait(nAEPInstance.addTabToExistingPage, "Click", "", piwInstance);
			System.out.println("Added tab to Page");
			CommonUtils.hold(5);
			pIWTPInstance.updateTabinExistingPage("Goals","gOAL_Tb1"+uniqueID, "https://www.google.com/finance/converter",piwInstance);
			
			System.out.println("Updated Page");
			
			CommonUtils.hold(5);
		}catch(Exception pSCE) {
			System.out.println("Exception in publishableSandboxChanges()");
			pSCE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishableSandboxChanges()
	
	@Test(description = "method will publish PageIntegationWizard sandbox updates to mainline", dependsOnMethods= {"createRefeshableSandboxes","publishableSandboxChanges"}, priority = 118)
	public void publishSandboxUpdates() {
		try {
			System.out.println("Initiating publish from sandbox banner");
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,piwInstance);
			System.out.println("Sandbox publish initiated");
					
			sbUiInstance.AvailableSandboxesLabel.click();
			
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",piwInstance);
			CommonUtils.hold(3);

			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
				sbUiInstance.refreshToLatestConfiguration(piwInstance);
			}catch(Exception rlce) {
				System.out.println("Exception while advancing to latest configuration");
				rlce.printStackTrace();
				Assert.fail();
			}
				
			System.out.println("Advanced to latest MDS label");	*/
			
		}catch(Exception pSUE) {
			System.out.println("Exception in publishSandboxUpdates()");
			pSUE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishSandboxUpdates()
	
	@Test(description = "method will perform non conflict sandbox refresh on PageIntegationWizard Pages", dependsOnMethods= {"createRefeshableSandboxes","publishableSandboxChanges"}, priority = 119)
	public void piwSandboxRefresh() {
		try {
			System.out.println("In nonConflictSandboxRefresh()");
			System.out.println("Initiating refresh");
			sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname, "Accept",piwInstance);
			System.out.println("Refresh done and verifying");
			sbDetailsUIInstance.VerifySandboxRefresh(RefreshableSandboxname,piwInstance);
			System.out.println("refresh verified");
		}catch(Exception nCSRE) {
			System.out.println("Exception in nonConflictSandboxRefresh()");
			Assert.fail();
			nCSRE.printStackTrace();
		}
	}//End of nonConflictSandboxRefresh()

 
	@Test(description = "method will check for PageIntegrationWizard preview mode", dependsOnMethods= "createRefeshableSandboxes", priority = 120)
	public void verifyPIWPreviewMode() {
		try {
			/*System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gptInstance.accessFromRecentItems("Manage Sandboxes",piwInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,piwInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",piwInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");*/
			CommonUtils.hold(5);	
			try {
				System.out.println("Checking for sandbox activation");
				//CommonUtils.explicitWait(sbBannerUIInstance.SandboxBanner, "Visible", "",piwInstance);
				CommonUtils.explicitWait(sbBannerUIInstance.sandboxBannerName(RefreshableSandboxname, piwInstance), "Visible", "",piwInstance);
				System.out.println("Sandbox is in activated state");
			}catch(Exception vMPE) {
				System.out.println("Sandbox is not activated. Activating the sandbox");
				System.out.println("Entering into Sandbox session");
				sbUiInstance.EnterSandboxSession(RefreshableSandboxname,piwInstance);
				System.out.println("Sandbox is activated.");
			}
			System.out.println("Flipping activated sandbox mode to 'PREVIEW' ");
			sbBannerUIInstance.SetSandboxPreviewMode(piwInstance);
			System.out.println("Sandbox is in 'PREVIEW' mode ");
			CommonUtils.hold(3);
			System.out.println("Navigating to PageTemplateComposer taskflow ");
			CommonUtils.hold(5);
			sbBannerUIInstance.navigatetoToolPage("Page Integration",piwInstance);
			CommonUtils.explicitWait(cpInstance.piwPreviewModeDialog, "Visible", "",piwInstance);
			Assert.assertTrue(cpInstance.piwPreviewModeDialog.isDisplayed());
			CommonUtils.hold(3);
		}catch(Exception vLPME) {
			System.out.println("Exception in VefiryPIWPreviewMode()");
			vLPME.printStackTrace();
			Assert.fail();
		}
	}//End of verifyPIWPreviewMode()

	@AfterClass(alwaysRun = true)
	public void Logout() {
		try {
			aLoginInstance.logout(piwInstance);

		} catch (Exception ex) {
			aLoginInstance.launchURL(piwInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(piwInstance);
		}finally {
			try {
				System.out.println("Opened db connection has been closed");
				piwInstance.quit();
				System.out.println("driver instance has been closed and quit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
		}
	}//End Of Logout
	
}
