/**
 * 
 */
package UI.tests.UnifiedSandbox;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.*;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ReportGenerator;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;


/**
 * @author klalam
 *
 */


public class UserInterfaceText extends GetDriverInstance{
	
	public static String SingularSearch = null;
	public static String SingularReplace = null;
	public static String PluralSearch = "";
	public static String PluralReplace = "";
	public static String SandboxName = null;
	public static String RefreshableSandboxname = null;
	static WebElement sbNameCheck = null;
	
	WebDriver uITInstance;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private SandboxBannerUI sbBannerUIInstance;
	private SandboxDetailsUI sbDetailsUIInstance;
	private SandboxUI sbUiInstance;
	private ApplicationTextUI atUIInstance;
	private SearchAndReplace sarInstance;
	private CreateSandboxUI cSBInstance;
	private GlobalPageTemplate gptInstance;
	private ManageMessagesWrapperClass mMsgWCInstance;
	
	
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
		sarInstance = new SearchAndReplace(uITInstance);
		atUIInstance = new ApplicationTextUI(uITInstance);
		mMsgWCInstance = new ManageMessagesWrapperClass(uITInstance);
		
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

	@Test (description = "Create Sandbox", priority = 96)
	public void createSandbox() {
		String sandboxName;
		try {
			System.out.println("In CreateSandbox()");
			SandboxName = cSBInstance.CreateSandbox("UITSandbox", "UserInterfaceText-SITE Context", "User Interface Text", "","YES",uITInstance);
			RefreshableSandboxname = cSBInstance.CreateSandbox("UITSandbox_Refresh", "UserInterfaceText-SITE Context", "User Interface Text", "Activate","YES",uITInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,uITInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", RefreshableSandboxname,uITInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
		} catch (Exception CSE) {
			System.out.println("Exception in CreateSandbox()");
			Assert.fail();
			CSE.printStackTrace();
		}
	}//End of CreateSandbox()
	
	
	public boolean searchAndReplaceWithinActiveSandbox() {
		boolean SearchandReplaceStatus = false;
		System.out.println("In SearchAndReplaceWithinActiveSandbox()");
		try {
			CommonUtils.hold(5);
			//CommonUtils.navigateToTask(NavigatorMenuElements.UserInterfaceText);
			sbBannerUIInstance.navigatetoToolPage("User Interface Text",uITInstance);
			CommonUtils.explicitWait(atUIInstance.SearchAndReplace_Enabled, "Visible", "",uITInstance);
			CommonUtils.hold(5);
			sarInstance.PreformSearchAndReplace(SingularSearch, SingularReplace, PluralSearch, PluralReplace, "Enterprise_Scheduler_Text",uITInstance);
			SearchandReplaceStatus = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		return SearchandReplaceStatus;
	}//End of UserInterfaceTextSearchAndReplace()
	
	
	public boolean userInterfaceTextPreviewMode() {
		boolean previewModeCheckStatus = false;
		String PreviewMode;
		System.out.println("In UserInterfaceTextPreviewMode()");
		try {
			//CommonUtils.NavigationMenuItem("Configuration", "User Interface Text");
			ntFInstance.navigateToTask(nMEInstance.UserInterfaceText,uITInstance);
			PreviewMode = sbBannerUIInstance.SetSandboxPreviewMode(uITInstance);
			Assert.assertEquals("Sandbox Mode: Preview",PreviewMode);
			Assert.assertEquals("You must be in a properly configured Sandbox to customize text (edit mode, global customization, and required repositories)", atUIInstance.UITPreviewMode.getText());
			
			previewModeCheckStatus = true;
		} catch (Exception UITPM) {
			System.out.println("Exception in UserInterfaceTextPreviewMode()");
			UITPM.printStackTrace();
			Assert.fail();
		}
		return previewModeCheckStatus;
	}//End of UserInterfaceTextPreviewMode()
	
	public boolean exitFromSandboxSession(String ExitSandboxName) {
		boolean ExistSandboxSessionStatus = false;
		System.out.println("In ExitFromSandboxSession()");
	return sbBannerUIInstance.ExitSandboxSession(ExitSandboxName,uITInstance);
	}//End of ExitSandboxSession()
	
	@Test (description = "method will perform sandbox changes within refreshable sandbox session", dependsOnMethods="createSandbox",  priority = 97)
	public void performRefreshableSandboxChanges() {
		SingularSearch = "Social"; SingularReplace = "Social_ReplaceSB";
		System.out.println("PerformSandboxChanges Begin");
		Assert.assertTrue(searchAndReplaceWithinActiveSandbox(), "Search and Replace Succeeded");
	//	Assert.assertTrue(ExitFromSandboxSession(RefreshableSandboxname), "Exit Sandbox Session");
		System.out.println("PerformSandboxChanges End");
	}
	
	@Test (description = "Activating  publishable sandbox ", dependsOnMethods="createSandbox", priority = 98)
	public void activatePublishableSandbox() {
		CommonUtils.hold(5);
		sbBannerUIInstance.ExitSandboxSession(RefreshableSandboxname,uITInstance);
		CommonUtils.hold(5);
		sbUiInstance.EnterSandboxSession(SandboxName,uITInstance);
		try {
			CommonUtils.explicitWait(sbBannerUIInstance.sandboxBannerName(SandboxName,uITInstance), "Visible", "",uITInstance);
		} catch (Exception e) {
			System.out.println("Exception in ActivateRefreshableSandbox()");
			e.printStackTrace();
			Assert.fail("Sandbox Not Activated");
		}
		
		Assert.assertTrue(sbBannerUIInstance.sandboxBannerName(SandboxName,uITInstance).isDisplayed(), "Refreshable Sandbox activated");
	}//End of ActivateRefreshableSandbox()
	
	@Test (description = "method will perform sandbox changes within publishable sandbox session", dependsOnMethods= {"createSandbox","activatePublishableSandbox"}, priority = 99)
	public void performPublishableSandboxChanges() {
		SingularSearch = "error"; SingularReplace = "Error_UIT"; PluralSearch = "Social"; PluralReplace = "SocialSB";
		System.out.println("PerformRefreshableSandboxChanges Begin");
		Assert.assertTrue(searchAndReplaceWithinActiveSandbox(), "Search and Replace Succeeded");
		System.out.println("PerformRefreshableSandboxChanges End");
	}//End of PerformRefreshableSandboxChanges()
	
	@Test (description = "Sandbox Publish", dependsOnMethods= {"createSandbox","performPublishableSandboxChanges"}, priority = 100)
	public void sandboxPublish() {
		String SandboxPublishStatus;
			
		sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,uITInstance); //Initiate SandboxPublish from banner
		
			CommonUtils.hold(5);
			
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.hold(5);
			SandboxPublishStatus = "";
	
	
	}//End Of SandboxPublish()
	
	@Test (description = "method will perfrom sandbox conflict refresh on UserInterfaceText tool", dependsOnMethods= {"performRefreshableSandboxChanges","sandboxPublish"}, priority = 101)
	public void sandboxRefresh() {
		boolean sandboxRefresh = true;
		try {
			CommonUtils.hold(5);
			sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname,"Accept",uITInstance);
			
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed(), "Navigated to Sandbox UI from Sandbox Details UI");
			//Assert.assertFalse(SandboxBannerUI.SandboxBanner.isSelected(), "Sandbox exited from active session hence sandbox banner doesn't Visible");
			sandboxRefresh = sbDetailsUIInstance.VerifySandboxRefresh(RefreshableSandboxname,uITInstance);
			if(!sandboxRefresh) {
				if(sbDetailsUIInstance.SandboxActionFailed.isDisplayed()) {
					System.out.println("Sandbox Refresh failed With an Error. Kindly refer the logs for an error");
					Assert.fail();
				}
			}
				
			Assert.assertTrue(sandboxRefresh, "Sandbox Successfully Refreshed");
			
		}catch(Exception SRE) {
			System.out.println("Exception in SandboxRefresh()");
			SRE.printStackTrace();
			Assert.fail();
		}
	}//End Of SandboxRefresh()
	
	public boolean verifySandboxContents() {
		boolean VerfiyStatus = false;
			
		try {
			sbUiInstance.HomePageIcon.click();
			CommonUtils.explicitWait(sbUiInstance.HomePageIcon, "Visible", "",uITInstance);
			String eleStatus;
			eleStatus = ntFInstance.verifyMenuItem(PluralReplace,true,uITInstance);
			if(eleStatus.equalsIgnoreCase("NotFound")) {
				System.out.println("Menu Item not replaced");
			}else {
				Assert.assertEquals(eleStatus, PluralReplace);
			}
				
			//Assert.assertEquals(CommonUtils.VerifyMenuItem("Configuration", PluralReplace).getText(), PluralReplace);
			Assert.assertTrue(mMsgWCInstance.verifyStatus("ACA_AUX_IO_ERROR", SingularReplace, "", "",uITInstance)); //Updated Message Verification
			VerfiyStatus = true;
			
		} catch (Exception VSCE) {
			System.out.println("Exception in VerifySandboxContents()");
			VSCE.printStackTrace();
			Assert.fail();
		}
		
		return VerfiyStatus;
	}//End of VerifySandboxContents()

    @Test (description = "Reverting the strings published to mainline", priority = 102)
	public void revertingPublishableChanges() {
		SingularSearch = "Error_UIT"; SingularReplace = "error"; PluralSearch = "SocialSB"; PluralReplace = "Social";
		System.out.println("Navigating to Sandboxes UI Begins");
		try {
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
			SandboxName = cSBInstance.CreateSandbox("UITSandbox_Revert", "UserInterfaceText-SITE Context", "User Interface Text", "Activate","YES",uITInstance);
			System.out.println("PerformSandboxChanges Begin");
			Assert.assertTrue(searchAndReplaceWithinActiveSandbox(), "Search and Replace Succeeded");
		//	Assert.assertTrue(ExitFromSandboxSession(RefreshableSandboxname), "Exit Sandbox Session");
			System.out.println("PerformSandboxChanges End");
			
			sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,uITInstance); //Initiate SandboxPublish from banner
			CommonUtils.hold(5);
				
			sbUiInstance.AvailableSandboxesLabel.click();
				CommonUtils.hold(5);
		}catch(Exception rPCE) {
			System.out.println("Exception in revertingPublishableChanges() ");
			rPCE.printStackTrace();
			Assert.fail();
		}
	}//End Of revertingPublishableChanges()

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
	}//End Of Logout
}//End of UnifiedSandboxUI class
