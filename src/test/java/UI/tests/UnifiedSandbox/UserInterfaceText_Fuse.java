package UI.tests.UnifiedSandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.ApplicationTextUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.CreateSandboxUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.ManageMessagesWrapperClass;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxBannerUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxDetailsUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SearchAndReplace;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.UsbEnvironmentMode;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.UserIntefaceTextFuseUIPageDef;


public class UserInterfaceText_Fuse extends GetDriverInstance{
	
	static String sandboxName;
	static String refreshableSandboxName;
	static String searchText;
	static String replacedText;
	static String SandboxPublishStatus = "";
	
	WebDriver uITInstance;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private SandboxBannerUI sbBannerUIInstance;
	private SandboxDetailsUI sbDetailsUIInstance;
	private SandboxUI sbUiInstance;
	//private ApplicationTextUI atUIInstance;
	//private SearchAndReplace sarInstance;
	private CreateSandboxUI cSBInstance;
	private GlobalPageTemplate gptInstance;
	//private ManageMessagesWrapperClass mMsgWCInstance;
	private UserIntefaceTextFuseUIPageDef uITDefInstance;
	
	
	@BeforeClass(alwaysRun = true)
	public void applicationLoginAndSandboxNavigation() {
	try {
		uITInstance =  getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(uITInstance);
		ntFInstance = new NavigationTaskFlows(uITInstance);
		nMEInstance = new NavigatorMenuElements(uITInstance);
		sbBannerUIInstance = new SandboxBannerUI(uITInstance);
		sbDetailsUIInstance = new SandboxDetailsUI(uITInstance);
		sbUiInstance = new SandboxUI(uITInstance);
		cSBInstance = new CreateSandboxUI(uITInstance);
		gptInstance = new GlobalPageTemplate(uITInstance);
		uITDefInstance = new UserIntefaceTextFuseUIPageDef(uITInstance);
	//	sarInstance = new SearchAndReplace(uITInstance);
	//	atUIInstance = new ApplicationTextUI(uITInstance);
	//	mMsgWCInstance = new ManageMessagesWrapperClass(uITInstance);
		
		aLoginInstance.login("application_administrator", "Welcome1",uITInstance);
		CommonUtils.waitForPageLoad(uITInstance);
		/*CommonUtils.GetPageURL(uITInstance);
		Assert.assertTrue(CommonUtils.GetPageURL(uITInstance).contains("FuseWelcome"));*/
		System.out.println("Navigating to Sandboxes UI Begins");
	/*	try {
			GlobalPageTemplate.globalTemplateArea.click();
			System.out.println("globalTemplateArea found and clicked");
		}catch(Exception gTemplate) {
			SandboxBannerUI.SandboxBanner.click();
			System.out.println("sandboxBanner found and clicked");
		}
	*/
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(nMEInstance.Sandboxes,uITInstance);
		CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",uITInstance);
		Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
		System.out.println("Sandboxes UI Loaded");
		}catch(Exception aLASNE) {
			System.out.println("Exception in <applicationLoginAndSandboxNavigation() >");
			aLASNE.printStackTrace();
			Assert.fail();
		}
	}//End of ApplicationLogin()

	@Test (description = "method will create sandbox with UserInterfaceText repository ", priority = 96)
	public void createSandbox() {
		try {
				System.out.println("Initiating Sandbox creation");
				sandboxName = cSBInstance.CreateSandbox("UitSandbox", "Sandbox with UserInterfaceText repository", "User Interface Text", "Activate", "YES",uITInstance);
			CommonUtils.explicitWait(sbUiInstance.sandboxBannerName(sandboxName,uITInstance), "TextPresent", sandboxName,uITInstance);
			CommonUtils.hold(2);
			Assert.assertTrue(sbUiInstance.sandboxBannerName(sandboxName,uITInstance).isDisplayed());
				System.out.println("Sandbox < "+sandboxName+" > successfully created");
			Assert.assertTrue(sbBannerUIInstance.SandboxBanner.isDisplayed());
				System.out.println("Sandbox < "+sandboxName+" > successfully activated");
			} catch (Exception CSE) {
			System.out.println("Exception in CreateSandbox()");
			Assert.fail();
			//CSE.printStackTrace();
		}
	}//End of CreateSandbox()
	
	@Test (description = "method will perform searchandreplace operation from UIT", dependsOnMethods = "createSandbox", priority = 97)
	public void performSearchAndReplace() {
		searchText = "Social";
		replacedText = "Social_uit";
		try {
			sbBannerUIInstance.navigatetoToolPage("User Interface Text",uITInstance);
			CommonUtils.explicitWait(uITDefInstance.findText, "Visible", "",uITInstance);
			CommonUtils.hold(2);
			uITDefInstance.setModuleNameIndexMap(uITInstance);
			uITDefInstance.stringsSearchAndReplace(searchText, replacedText, "", "",uITInstance);
			CommonUtils.hold(5);
			uITDefInstance.textReplacementHistoryTab.click();
			CommonUtils.hold(10);
			Assert.assertTrue(uITInstance.findElement(By.xpath("//span[text() = '"+replacedText+"']")).isDisplayed());
			CommonUtils.hold(5);
			uITDefInstance.searchNreplaceTab.click();
			CommonUtils.hold(10);
			
		}catch(Exception pSARE) {
			System.out.println("Exception in performSearchAndReplace()");
			pSARE.printStackTrace();
			Assert.fail();
		}
	}//End of performSearchAndReplace()
	
	@Test(description = "method will Verify sandbox changes written to mainline or not before sandbox publish", dependsOnMethods="performSearchAndReplace",  priority = 98)
	public void verifySandboxChanges() throws Exception {
		String eleStatus;
			try {
				CommonUtils.hold(5);
				Assert.assertTrue(sbBannerUIInstance.ExitSandboxSession(sandboxName,uITInstance));
				CommonUtils.hold(5);
				gptInstance.userDropDownIcon.click();
				CommonUtils.explicitWait(gptInstance.administration_SetupAndMaintenance,"Visible","",uITInstance);
				gptInstance.administration_SetupAndMaintenance.click();
				CommonUtils.waitForPageLoad(uITInstance);
				CommonUtils.explicitWait(ntFInstance.panelDrawer, "Click", "",uITInstance);
				eleStatus = ntFInstance.verifyMenuItem(replacedText,false,uITInstance);
				CommonUtils.hold(5);
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
	
	@Test (description = "method will publish sandbox", dependsOnMethods= {"createSandbox","performSearchAndReplace"},priority = 99)
	public void sandboxPublish() {
		try {
			sbDetailsUIInstance.SandboxPublish(sandboxName,uITInstance);
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.hold(10);
			SandboxPublishStatus = "";
		}catch(Exception sPE) {
			System.out.println("Exception in sandboxPublish()");
			sPE.printStackTrace();
			Assert.fail();
		}
	}//End Of SandboxPublish()
	
	@Test(description = "method will verify published changes",dependsOnMethods="sandboxPublish",priority = 100)
	public void verifyManilineChangesAfterPublish() {
		try {
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
					sbUiInstance.refreshToLatestConfiguration(uITInstance);
				}catch(Exception rtlcE) {
					System.out.println("Lable read Enalbed but 'Refresh To Last Configuration' is not enabled post publish of MDS updates");
					rtlcE.printStackTrace();
					Assert.fail();
				}
			System.out.println("Advanced to latest MDS label");
			CommonUtils.hold(5);*/
			gptInstance.userDropDownIcon.click();
			CommonUtils.explicitWait(gptInstance.administration_SetupAndMaintenance,"Visible","",uITInstance);
			gptInstance.administration_SetupAndMaintenance.click();
			CommonUtils.explicitWait(ntFInstance.panelDrawer, "Click", "",uITInstance);
			CommonUtils.hold(10);
				String eleStatus;
				
				eleStatus  = ntFInstance.verifyMenuItem(replacedText,false,uITInstance);
				CommonUtils.hold(5);
				if(eleStatus.equalsIgnoreCase("Found")) {
					System.out.println("Sandbox changes are written into mainline post sandbox publish");
				}else {
					System.out.println("Sandbox changes are not written into mainline post sandbox publish");
					Assert.fail();
				}
				CommonUtils.hold(10);
		}catch(Exception VSC) {
			System.out.println("Exception in verifyManilineChangesAfterPublish()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//VerifyManilineChangesAfterPublish
	
	@Test(description = "method will create sandboxes for non conflict refresh on UserInterfaceText tool",priority = 101)
	public void createRefreshableSandboxes() {
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gptInstance.accessFromRecentItems("Manage Sandboxes",uITInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,uITInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",uITInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);	
			
			refreshableSandboxName = cSBInstance.CreateSandbox("UIT_NonConflict_Refresh", "Refreshable Sandbox with UserInterfaceText repository", "User Interface Text", "", "YES",uITInstance);
			CommonUtils.hold(10);
			sandboxName = cSBInstance.CreateSandbox("UIT_Publish", "Sandbox with UserInterfaceText repository", "User Interface Text", "Activate", "YES",uITInstance);
			CommonUtils.explicitWait(sbUiInstance.sandboxBannerName(sandboxName,uITInstance), "TextPresent", sandboxName,uITInstance);
			Assert.assertTrue(sbUiInstance.sandboxBannerName(sandboxName,uITInstance).isDisplayed());
			CommonUtils.hold(5);
		}catch(Exception cRSE) {
			System.out.println("Exception in createRefreshableSandboxes()");
			cRSE.printStackTrace();
			Assert.fail();
		}
	}//End Of createRefreshableSandboxes()
	
	@Test(description = "method will perform changes on UserInterfaceText tool within publishable sandbox session", dependsOnMethods="createRefreshableSandboxes", priority = 102)
	public void publishableSandboxChanges() {
		searchText = "error";
		replacedText = "error_uit";
		try {
			sbBannerUIInstance.navigatetoToolPage("User Interface Text",uITInstance);
			CommonUtils.explicitWait(uITDefInstance.findText, "Visible", "",uITInstance);
			CommonUtils.hold(2);
			uITDefInstance.stringsSearchAndReplace(searchText, replacedText, "", "",uITInstance);
			CommonUtils.hold(5);
			uITDefInstance.textReplacementHistoryTab.click();
			CommonUtils.hold(10);
			Assert.assertTrue(uITInstance.findElement(By.xpath("//span[text() = '"+replacedText+"']")).isDisplayed());
			CommonUtils.hold(5);
			uITDefInstance.searchNreplaceTab.click();
			CommonUtils.hold(10);
			
		}catch(Exception pSCE) {
			System.out.println("Exception in publishableSandboxChanges()");
			pSCE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishableSandboxChanges()
	
	@Test(description = "method will  publish sandbox changes to mailine", dependsOnMethods="publishableSandboxChanges", priority = 103)
	public void publishRefreshableSandboxChanges() {
		try {
			System.out.println("Initiating publish from sandbox banner");
			
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(sandboxName,uITInstance);
			
			sbUiInstance.AvailableSandboxesLabel.click();
			
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",uITInstance);
			CommonUtils.hold(5);
			System.out.println("Sandbox changes are written to mainline succesfully");
			/*System.out.println("Advancing to latest MDS label");
				try {
					sbUiInstance.refreshToLatestConfiguration(uITInstance);
				}catch(Exception rtlcE) {
					System.out.println("Lable read Enalbed but 'Refresh To Last Configuration' is not enabled post publish of MDS updates");
					rtlcE.printStackTrace();
					Assert.fail();
				}
				System.out.println("Advanced to latest MDS label");*/
		}catch(Exception pRSCE) {
			System.out.println("Exception in publishRefreshableSandboxChanges()");
			pRSCE.printStackTrace();
			Assert.fail();
		}
	}//End Of publishRefreshableSandboxChanges()
	
	@Test(description = "method will perform non conflict sandbox refresh on UserInterfaceText tool",dependsOnMethods= {"createRefreshableSandboxes","publishRefreshableSandboxChanges"},priority = 104)
	public void nonConflictSandboxRefresh() {
		try {
			System.out.println("Initiating refresh");
			System.out.println("Initiating non conflict refresh");
			sbDetailsUIInstance.SandboxRefresh(refreshableSandboxName, "Accept",uITInstance);
			System.out.println("Refresh done and verifying");
			sbDetailsUIInstance.VerifySandboxRefresh(refreshableSandboxName,uITInstance);
			System.out.println("Refresh verified");
			CommonUtils.hold(5);
		}catch(Exception nCSRE) {
			System.out.println("Exception in nonConflictSandboxRefresh()");
			Assert.fail();
			nCSRE.printStackTrace();
		}
	}//End of nonConflictSandboxRefresh()
	
	@Test(description = "method will create sandboxes to perform conflict refresh on UserInterfaceText tool",priority = 105)
	public void createConflictRefreshableSandboxes() {
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gptInstance.accessFromRecentItems("Manage Sandboxes",uITInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,uITInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",uITInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);	
			sandboxName = cSBInstance.CreateSandbox("UIT_ConflictRefresh_publish", "Sandbox with UserInterfaceText repository", "User Interface Text", "", "YES",uITInstance);
			CommonUtils.hold(10);
			refreshableSandboxName = cSBInstance.CreateSandbox("UIT_ConflictRefresh", "Refreshable Sandbox with UserInterfaceText repository", "User Interface Text", "Activate", "YES",uITInstance);
			CommonUtils.explicitWait(sbBannerUIInstance.sandboxBannerName(refreshableSandboxName,uITInstance), "TextPresent", refreshableSandboxName,uITInstance);
			Assert.assertTrue(sbBannerUIInstance.sandboxBannerName(refreshableSandboxName,uITInstance).isDisplayed());
		
			CommonUtils.hold(5);
		}catch(Exception cCRSE) {
			System.out.println("Exception in createConflictRefreshableSandboxes() ");
			cCRSE.printStackTrace();
			gptInstance.homeIcon.click();
			try {
				//CommonUtils.explicitWait(GlobalPageTemplate.fuseWelcomeSpringBoard, "Click", "");
				CommonUtils.waitForPageLoad(uITInstance);
				CommonUtils.hold(5);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CommonUtils.hold(5);
			Assert.fail();
		}
	}//End Of createConflictRefreshableSandboxes()
	
	@Test(description = "method will  perform conflicted structure changes in  refreshabel Sandbox", dependsOnMethods="createConflictRefreshableSandboxes", priority = 106)
	public void refreshableConflictedChanges() {
		searchText = "error_uit";
		replacedText = "error_uit_refresh";
		try {
			sbBannerUIInstance.navigatetoToolPage("User Interface Text",uITInstance);
			CommonUtils.explicitWait(uITDefInstance.findText, "Visible", "",uITInstance);
			CommonUtils.hold(2);
			uITDefInstance.stringsSearchAndReplace(searchText, replacedText, "", "",uITInstance);
			CommonUtils.hold(5);
			uITDefInstance.textReplacementHistoryTab.click();
			CommonUtils.hold(10);
			Assert.assertTrue(uITInstance.findElement(By.xpath("//span[text() = '"+replacedText+"']")).isDisplayed());
			CommonUtils.hold(5);
			uITDefInstance.searchNreplaceTab.click();
			CommonUtils.hold(10);
			sbBannerUIInstance.ExitSandboxSession(refreshableSandboxName,uITInstance);
			CommonUtils.hold(5);
			System.out.println("Customizing Structure for Refreshable Sandbox --> done");
		}catch(Exception pCCE) {
			System.out.println("Exception in refreshableConflictedChanges()");
			pCCE.printStackTrace();
			Assert.fail();
		}
	}//End Of refreshableConflictedChanges()
	
	@Test(description = "method will  perform conflicted structure changes in  refreshabel Sandbox", dependsOnMethods="createConflictRefreshableSandboxes", priority = 107)
	public void publishableConflictedChanges() {
		searchText = "error_uit";
		replacedText = "error";
		try {
			System.out.println("Customizing UIT for Publishable Sandbox");
			System.out.println("Enter into publishable sandbox");
			sbUiInstance.EnterSandboxSession(sandboxName,uITInstance);
			System.out.println("Enterred into publishable sandbox");
			Assert.assertTrue(sbUiInstance.sandboxBannerName(sandboxName,uITInstance).isDisplayed());
			CommonUtils.hold(5);
			
			sbBannerUIInstance.navigatetoToolPage("User Interface Text",uITInstance);
			CommonUtils.explicitWait(uITDefInstance.findText, "Visible", "",uITInstance);
			CommonUtils.hold(2);
			uITDefInstance.stringsSearchAndReplace(searchText, replacedText, "", "",uITInstance);
			CommonUtils.hold(10);
			
			searchText = "Social_uit";
			replacedText = "Social";
			
			uITDefInstance.stringsSearchAndReplace(searchText, replacedText, "", "",uITInstance);
			CommonUtils.hold(10);
			
			System.out.println("Customizing UIT for Publishable Sandbox --> done");
		}catch(Exception pCCE) {
			System.out.println("Exception in publishableConflictedChanges()");
			pCCE.printStackTrace();
			Assert.fail();
		}
	}//End Of  publishableConflictedChanges()
	
	@Test(description = "method will perform conflict sandbox refresh on uit updates",dependsOnMethods= {"createConflictRefreshableSandboxes","refreshableConflictedChanges","publishableConflictedChanges"},priority = 108)
	public void conflictSandboxRefresh() {
		
		try {
			CommonUtils.hold(2);
			
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(sandboxName,uITInstance);
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",uITInstance);
			CommonUtils.hold(5);
			/*System.out.println("Advancing to latest MDS label");
			try {
					sbUiInstance.refreshToLatestConfiguration(uITInstance);
				}catch(Exception rtlcE) {
					System.out.println("Lable read Enalbed but 'Refresh To Last Configuration' is not enabled post publish of MDS updates");
					rtlcE.printStackTrace();
					Assert.fail();
				}
			System.out.println("Advanced to latest MDS label");*/
			System.out.println("Initiate SB conflict refresh");
			sbDetailsUIInstance.SandboxRefresh(refreshableSandboxName, "Accept",uITInstance);
			System.out.println("Initiate SB conflict refresh done and verifying");
			CommonUtils.hold(5);
			sbDetailsUIInstance.VerifySandboxRefresh(refreshableSandboxName,uITInstance);
			CommonUtils.hold(5);
		}catch(Exception nCSRE) {
			System.out.println("Exception in conflictSandboxRefresh()");
			Assert.fail();
			nCSRE.printStackTrace();
		}
		
	}//End of conflictSandboxRefresh()

	
	
	@Test(description = "method will check for sandbox preview mode for UIT tool", priority = 109)
	public void uitPreviewMode() {
		try {
			sbBannerUIInstance.checkAndExitActiveSandboxSession(uITInstance);
			System.out.println("Navigating to Sandboxes UI Begins");
			System.out.println("Entering into Sandbox session");
			sbUiInstance.EnterSandboxSession(refreshableSandboxName,uITInstance);
				System.out.println("Sandbox is activated");
						
				System.out.println("Flipping activated sandbox mode to 'PREVIEW' ");
				sbBannerUIInstance.SetSandboxPreviewMode(uITInstance);
			System.out.println("Sandbox is in 'PREVIEW' mode ");
			CommonUtils.hold(3);
			System.out.println("Navigating to Structure taskflow ");
			sbBannerUIInstance.navigatetoToolPage("User Interface Text",uITInstance);
			CommonUtils.explicitWait(uITDefInstance.uitPreviewTextElement, "Visible", "",uITInstance);
			CommonUtils.hold(3);
			Assert.assertTrue(uITDefInstance.uitPreviewTextElement.isDisplayed());
			
		}catch(Exception sPME) {
			System.out.println("Exception in uitPreviewMode()");
			sPME.printStackTrace();
			Assert.fail();
		}
	}//End Of uitPreviewMode()

	@AfterClass(alwaysRun = true)
	public void Logout() {
		try {
			aLoginInstance.logout(uITInstance);
		} catch (Exception ex) {
			aLoginInstance.launchURL(uITInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(uITInstance);
		}finally {
			try {
				System.out.println("Opened db connection has been closed");
				uITInstance.quit();
				System.out.println("driver instance has been closed and quit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing Object Instance ");
			}
		}
	}

}
