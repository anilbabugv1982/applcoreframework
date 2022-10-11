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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.CreateSandboxUI;
import UtilClasses.UI.SetupAndMaintenance;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.LookupsPage;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.LookupsWrapperClass;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.ManageMessagesPages;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.ManageMessagesWrapperClass;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxBannerUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxDetailsUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.SandboxUI;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.UsbEnvironmentMode;
import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import UtilClasses.UI.ReportGenerator;

/**
 * @author klalam
 *
 */


public class ManageMessages extends GetDriverInstance{
  
	static String SandboxName = null;
	static String RefreshableSandboxname = null;
	static String uniqueID;
	static String SandboxPublishStatus = "";
	static String SandboxMode = "Edit";
	static WebElement sbNameCheck = null;
	
	WebDriver manageMessagesInstance;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private UsbEnvironmentMode uSBEnvModeInstance;
	private SandboxBannerUI sbBannerUIInstance;
	private SandboxDetailsUI sbDetailsUIInstance;
	private SandboxUI sbUiInstance;
	private GlobalPageTemplate gPTInstance;
	private CreateSandboxUI cSBInstance;
	private CommonUtilsDefs cUDInstance;
	private LookupsWrapperClass lWCInstance;
	private ManageMessagesWrapperClass mWCInstance;
	
	
	@BeforeClass(alwaysRun = true)
	public void applicationLoginAndSandboxNavigation() {
		
		try {
			
			manageMessagesInstance =  getDriverInstanceObject();
			aLoginInstance = new ApplicationLogin(manageMessagesInstance);
			ntFInstance = new NavigationTaskFlows(manageMessagesInstance);
			nMEInstance = new NavigatorMenuElements(manageMessagesInstance);
			uSBEnvModeInstance	= new UsbEnvironmentMode(manageMessagesInstance);
			sbBannerUIInstance = new SandboxBannerUI(manageMessagesInstance);
			sbDetailsUIInstance = new SandboxDetailsUI(manageMessagesInstance);
			sbUiInstance = new SandboxUI(manageMessagesInstance);
			gPTInstance =new GlobalPageTemplate(manageMessagesInstance);
			cSBInstance = new CreateSandboxUI(manageMessagesInstance);
			cUDInstance = new CommonUtilsDefs(manageMessagesInstance);
			lWCInstance = new LookupsWrapperClass(manageMessagesInstance);
			mWCInstance = new ManageMessagesWrapperClass(manageMessagesInstance);
			
			System.out.println("signing to the application with 'app_impl_consultant' user");
			/*
			if (uSBEnvModeInstance.CheckForUsbMode(uSBEnvModeInstance.RetreiveUsbProfileOption).equalsIgnoreCase("N") && uSBEnvModeInstance.CheckForUsbMode(uSBEnvModeInstance.RetrevieADFShareProfileOption).equalsIgnoreCase("FALSE")) {
				 throw new SkipException("Skipping test because environment not in USB mode");
			  }*/
			 System.out.println("USB mode check completed");
			
			 aLoginInstance.login("app_impl_consultant", "Welcome1",manageMessagesInstance);
		
		CommonUtils.waitForPageLoad(manageMessagesInstance);
	/*	CommonUtils.PageRefresh(manageMessagesInstance);
		CommonUtils.GetPageURL(manageMessagesInstance);
	//	CommonUtils.explicitWait(gPTInstance.fuseWelcomeSpringBoard, "Visible", "",manageMessagesInstance);
	//	Assert.assertTrue(gPTInstance.fuseWelcomeSpringBoard.isDisplayed());
		//	System.out.println("FuseWelcome Page");
*/		uniqueID = CommonUtils.uniqueId();
		CommonUtils.hold(5);
		//System.out.println("Navigating to Sandboxes UI Begins");
		ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageMessagesInstance);
		CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageMessagesInstance);
		Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
		System.out.println("Sandboxes UI Loaded");
		} catch (Exception aLASNE) {
			aLASNE.printStackTrace();
			//ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(aLASNE));
			Assert.fail();
		}
	}//End of applicationLoginAndSandboxNavigation()

	@Test (description = "method will create sandbox with Message repository ", priority = 19)
	public void createSandbox() {
		try {
				System.out.println("Initiating Sandbox creation");
			SandboxName = cSBInstance.CreateSandbox("MessagesSandbox", "Sandbox with Messages repository", "Messages", "Activate","YES",manageMessagesInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,manageMessagesInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,manageMessagesInstance);
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

	@Test (description = "method will launch Manage Messages taskflow", dependsOnMethods="createSandbox", priority = 20)
	public void launchManageMessagestf() {
		try {
			System.out.println("navigating to <Setup and Maintenance> initiated");
			
			gPTInstance.userDropDownIcon.click();
			CommonUtils.explicitWait(gPTInstance.administration_SetupAndMaintenance,"Visible","",manageMessagesInstance);
			gPTInstance.administration_SetupAndMaintenance.click();
			ntFInstance.navigateToAOLTaskFlows("Manage Messages",manageMessagesInstance);
			CommonUtils.explicitWait(mWCInstance.createBtn, "Click", "",manageMessagesInstance);
				System.out.println("<Manage Messages> taskflow loaded successfully");
			try {
			if(sbBannerUIInstance.SandboxMode.getText().contains("Edit")) {
				Assert.assertEquals(mWCInstance.MessagesEditModeMsg.getText(), "All customizations in this page are being carried out in the current Sandbox.");
					System.out.println("<Manage Messages> taskflow loaded in sandbox <EDIT> mode");
			}			
			else if(sbBannerUIInstance.SandboxMode.getText().contains("Preview")){
				Assert.assertEquals(mWCInstance.MessagesPreviewModeMsg.getText(), "Task Messages is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
					System.out.println("<Manage Messages> taskflow loaded in sandbox <PREVIEW> mode");
			}
				
			}catch (Exception NoSuchElementException) {
				System.out.println("<Manage Messages> taskflow loaded outside sandbox session");
				Assert.assertTrue(true);
			}
		} catch (Exception LLTE) {
			System.out.println("Exception while launching Manage Messages taskflow");
			LLTE.printStackTrace();
			Assert.fail();
		}
	}//End of ManageMessagestf()
	
	
		
	@Test(description = "method will create Message and Message token within an active sandbox session", dependsOnMethods="launchManageMessagestf", priority = 21)
	public void createMessageAndMessageToken() {
		try {
			System.out.println("UniqueId - "+uniqueID);
				System.out.println("Intiating <QAMESSAGE_ "+uniqueID +" > Message Creation");
				mWCInstance.createMessage("QAMESSAGE_"+uniqueID, "QA Message Created through Selenium automation script within Unified Sandbox Session"+uniqueID,manageMessagesInstance);
				System.out.println("Message <QAMESSAGE_ "+uniqueID +" > created succeessfully");
				System.out.println("Intiating <QAMESSAGE_ "+uniqueID +" > MessageToken Creation");
				mWCInstance.createMessageToken("QAMESSAGE_"+uniqueID, "QAMESSAGETOKEN_"+uniqueID, "Message Token created within active unified sandbox session"+uniqueID,manageMessagesInstance);
				System.out.println("MessageToken <QAMESSAGE_ "+uniqueID +" > created succeessfully");
			
		}catch(Exception CLTE) {
			System.out.println("Exception in CreateMessageAndMessageToken()");
			CLTE.printStackTrace();
			Assert.fail();
		}
		
	}//End of CreateMessageAndMessageToken()


	@Test(description = "method will Update Message and MessageToken within an active sandbox session", dependsOnMethods="launchManageMessagestf", priority = 22)
	public void updateMessageAndMessageToken() {
		try {
				System.out.println("Intiating <FND-EXCEP_INVALID_FLEX_CODE> message update");
				mWCInstance.updateMessage("FND-EXCEP_INVALID_FLEX_CODE", "Oracle Middleware Extensions for Applications", "QA Message Updated through Selenium automation script within Unified Sandbox Session"+uniqueID,manageMessagesInstance);
				System.out.println("updated <FND-EXCEP_INVALID_FLEX_CODE> message");
				System.out.println("Intiating <FND_TREE_BAD_PROTECTED_FLAG> message token update");
				mWCInstance.UpdateToken("FND_TREE_BAD_PROTECTED_FLAG", "Oracle Middleware Extensions for Applications", "Message Token Updated within active unified sandbox session"+uniqueID,manageMessagesInstance);
				System.out.println("updated <FND_TREE_BAD_PROTECTED_FLAG> message token");
				System.out.println("Intiating <FND_DUPLICATE_LOOKUP_CODE> message update");
				mWCInstance.updateMessage("FND_DUPLICATE_LOOKUP_CODE", "Oracle Middleware Extensions for Applications", "Lookup Type {LKTYPE} already has code {LKCODE} updated within nonConflict refresh Sandboxsession"+uniqueID,manageMessagesInstance);
				System.out.println("updated <FND_DUPLICATE_LOOKUP_CODE> message ");
				System.out.println("Intiating <FND_DUPLICATE_LKCODE_MEANING> message update");
				mWCInstance.updateMessage("FND_DUPLICATE_LKCODE_MEANING", "Oracle Middleware Extensions for Applications", "Lookup Type {LKTYPE} already has lookup code meaning {LKMEANING} updated within nonConflict refresh Sandboxsession"+uniqueID,manageMessagesInstance);
				System.out.println("updated <FND_DUPLICATE_LKCODE_MEANING> message");
				System.out.println("Intiating <FND_MSG_NAME_UNIQUEE> message token update");
				mWCInstance.UpdateToken("FND_MSG_NAME_UNIQUE", "Oracle Middleware Extensions for Applications", "Message Token Updated within nonConflictRefresh unified sandbox session"+uniqueID,manageMessagesInstance);
				System.out.println("updated <FND_MSG_NAME_UNIQUE> message token");
		}catch(Exception CLTE) {
			System.out.println("Exception in UpdateMessageAndMessageToken()");
		//	ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(CLTE));
			CLTE.printStackTrace();
			Assert.fail();
		}
		
	}//End of UpdateMessageAndMessageToken()
	
	@Test(description = "method will Verify sandbox changes written to mainline or not before sandbox publish", dependsOnMethods="createMessageAndMessageToken", priority = 23)
	public void verifySandboxChanges() {
		SandboxMode = "";
		try {
				System.out.println("Initiating "+SandboxName+" sandbox exit");
			Assert.assertTrue(sbBannerUIInstance.ExitSandboxSession(SandboxName,manageMessagesInstance));
			launchManageMessagestf();
			try {
					System.out.println("Verifying sandbox changes on mainline");
					mWCInstance.searchMessage("QAMESSAGE_"+uniqueID,manageMessagesInstance);
				CommonUtils.hold(2);
				CommonUtils.explicitWait(manageMessagesInstance.findElement(By.xpath("//span[contains(text() ,'QAMESSAGE_"+uniqueID+"')]")),"Visible", "",manageMessagesInstance);
				Assert.assertTrue(manageMessagesInstance.findElement(By.xpath("//span[contains(text() , 'QAMESSAGE_"+uniqueID+"')]")).isDisplayed()); //Created Message Verification
					System.out.println("Sandbox changes written to mainline");
				Assert.fail();
			}catch(Exception e) {
				System.out.println("QAMESSAGE_"+uniqueID+" message not found in mainline");
				System.out.println("Sandbox changes not written to mainline");
				Assert.assertTrue(true);
			}
		}catch(Exception VSC) {
			System.out.println("Exception in VerifySandboxChanges()");
		//		ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(VSC));
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//VerifySandboxChanges

	@Test (description = "method will publish sandbox", dependsOnMethods= {"createSandbox","createMessageAndMessageToken","updateMessageAndMessageToken"}, priority = 24)
	public void sandboxPublish() {
		try {
			System.out.println("Initiating <"+SandboxName+"> publish");
			sbDetailsUIInstance.SandboxPublish(SandboxName,manageMessagesInstance);
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.hold(5);
			SandboxPublishStatus = "";
		}catch(Exception sPE) {
			System.out.println("Exception in sandboxPublish()");
			sPE.printStackTrace();
			Assert.fail();
		}
	}//End Of sandboxPublish()
	
	@Test(description = "method will Verify sandbox changes written to mainline after sandbox publish", dependsOnMethods="sandboxPublish", priority = 25)
	public void verifyManilineChangesAfterPublish() {
		SandboxMode = "";
		try {
			launchManageMessagestf();
				System.out.println("Verifying published message QAMESSAGE_"+uniqueID +" on mainline");
			Assert.assertTrue(mWCInstance.verifyStatus("QAMESSAGE_"+uniqueID, "QA Message Created through Selenium automation script within Unified Sandbox Session"+uniqueID, "", "",manageMessagesInstance)); //Created Message Verification
				System.out.println("Verifying published message token QAMESSAGETOKEN_"+uniqueID +" on mainline");
			Assert.assertTrue(mWCInstance.verifyStatus("QAMESSAGE_"+uniqueID, "", "QAMESSAGETOKEN_"+uniqueID, "Message Token created within active unified sandbox session"+uniqueID,manageMessagesInstance)); //Created MessageToken Verification
			
				System.out.println("Verifying published updated message 'FND-EXCEP_INVALID_FLEX_CODE' on mainline");
			Assert.assertTrue(mWCInstance.verifyStatus("FND-EXCEP_INVALID_FLEX_CODE", "QA Message Updated through Selenium automation script within Unified Sandbox Session"+uniqueID, "", "",manageMessagesInstance)); //Updated Message Verification
				System.out.println("Verifying published updated message token for the message 'FND_TREE_BAD_PROTECTED_FLAG' on mainline");
			Assert.assertTrue(mWCInstance.verifyStatus("FND_TREE_BAD_PROTECTED_FLAG", "", "", "Message Token Updated within active unified sandbox session"+uniqueID,manageMessagesInstance)); //updated MessageToken Verification
		}catch(Exception VSC) {
			System.out.println("Exception in VerifySandboxChanges()");
			VSC.printStackTrace();
			Assert.fail();
		}
	}//VerifyManilineChangesAfterPublish
	
	@Test(description = "method with verify runtime changes published to mainline", dependsOnMethods= {"updateMessageAndMessageToken","sandboxPublish"},priority = 26)
	public void runtimeVerification() {
		try {
					System.out.println("Navigate to 'Setup and Maintenance' initiated");
					ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance,manageMessagesInstance);
				System.out.println("Intiating runtime verification of updatedmessage for duplicate lookupcode <FND_DUPLICATE_LOOKUP_CODE>");
				System.out.println("Navigate to Manage StandardLookups tf");
				ntFInstance.navigateToAOLTaskFlows("Manage Standard Lookups",manageMessagesInstance);
			CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Click", "",manageMessagesInstance);
			
			System.out.println("Verifying updatedmessage for duplicate lookupcode <FND_DUPLICATE_LOOKUP_CODE>");
				System.out.println("Verifying updatedmessage for duplicate lookupcode <FND_DUPLICATE_LOOKUP_CODE>");
				lWCInstance.createLookupCode("FNDCPQCR_REGION", "D", "NewLookupCode"+uniqueID, "Lookup Code created using Selenium automation"+uniqueID, "standard",manageMessagesInstance);
				mWCInstance.WaitforErrorEleLoad(manageMessagesInstance);
			Assert.assertTrue(mWCInstance.VerifyDuplicateLookupCode("FNDCPQCR_REGION", "D", "Lookup Type {LKTYPE} already has code {LKCODE} updated within nonConflict refresh Sandboxsession"+uniqueID,manageMessagesInstance).isDisplayed());
			mWCInstance.ErroedPopupOk.click();
			
			CommonUtils.hold(3);
			lWCInstance.ExitTf.click();
				System.out.println("runtime verification of updatedmessage for duplicate lookupcode <FND_DUPLICATE_LOOKUP_CODE> done");
			CommonUtils.explicitWait(cUDInstance.searchField, "Visible", "",manageMessagesInstance);
			
				System.out.println("Intiating runtime verification of updatedmessage for duplicate lookupcode  meaning <FND_DUPLICATE_LKCODE_MEANING>");
				ntFInstance.navigateToAOLTfSearchPage("Manage Standard Lookups",manageMessagesInstance);
			CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Click", "",manageMessagesInstance);
			System.out.println("Verifying updatedmessage for duplicate lookupcode meaning <FND_DUPLICATE_LKCODE_MEANING>");
			lWCInstance.createLookupCode("FNDCPAMPM", "QALookupCode", "AM", "Lookup Code created using Selenium automation", "standard",manageMessagesInstance);
			mWCInstance.WaitforErrorEleLoad(manageMessagesInstance);
			Assert.assertTrue(mWCInstance.VerifyDuplicateLookupCodeMeaning("FNDCPAMPM", "AM", "Lookup Type {LKTYPE} already has lookup code meaning {LKMEANING} updated within nonConflict refresh Sandboxsession"+uniqueID,manageMessagesInstance).isDisplayed());
			mWCInstance.ErroedPopupOk.click();
			
			CommonUtils.hold(3);
			lWCInstance.ExitTf.click();
				System.out.println("Runtime verification of updatedmessage for duplicate lookupcode  meaning <FND_DUPLICATE_LKCODE_MEANING> done");
		}catch(Exception rVE) {
			System.out.println("Exception in runtimeVerification()");
			rVE.printStackTrace();
			Assert.fail();
		}
	}//End of runtimeVerification()
	
	@Test(description = "method will create sandboxes with messages repository to perform conflict refresh",priority = 27)
	public void refreshableSandboxesCreate() {
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gPTInstance.accessFromRecentItems("Manage Sandboxes",manageMessagesInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageMessagesInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageMessagesInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);	
				System.out.println("Initiating publishable sandbox creation");
				SandboxName = cSBInstance.CreateSandbox("MessagesSandbox", "Sandbox with Messages repository to be published", "Messages", "","YES",manageMessagesInstance);
					System.out.println("Sandbox "+SandboxName+" Successfully Created");
					System.out.println("Initiating Refreshable sandbox creation");
				RefreshableSandboxname = cSBInstance.CreateSandbox("MessagesSandbox_Refresh", "Refreshable Sandbox with Messages repository", "Messages", "Activate","YES",manageMessagesInstance);
					System.out.println("Sandbox "+RefreshableSandboxname+" Successfully Created");
				sbNameCheck = sbUiInstance.getSandboxNameElement(RefreshableSandboxname,manageMessagesInstance);
				CommonUtils.explicitWait(sbNameCheck, "TextPresent", RefreshableSandboxname,manageMessagesInstance);
				Assert.assertTrue(sbNameCheck.isDisplayed());
		}catch(Exception rSCE) {
			System.out.println("Exception in refreshableSandboxesCreate()");
			rSCE.printStackTrace();
			Assert.fail();
		}
	}//End of refreshableSandboxesCreate()
	
	@Test(description = "method will perform sandbox conflict updates on messages in refreshable sandbox",dependsOnMethods="refreshableSandboxesCreate",priority = 28)
	public void refreshableSandboxUpdates() {
		try {
			System.out.println("Initating Messages changes within refreshable sandbox "+RefreshableSandboxname);
			launchManageMessagestf();
				System.out.println("Intiating <FND_MSG_NAME_UNIQUE> message update within refreshable sandbox session");
				mWCInstance.updateMessage("FND_MSG_NAME_UNIQUE", "Oracle Middleware Extensions for Applications", "The message name {MESSAGE_NAME} must be unique within an application Refreshable sanddbox"+uniqueID,manageMessagesInstance);
				System.out.println("Updated <FND_MSG_NAME_UNIQUE> message");
				System.out.println("Intiating <FND_MSG_TOKEN_UNIQUE> message update within refreshable sandbox session");
				mWCInstance.updateMessage("FND_MSG_TOKEN_UNIQUE", "Oracle Middleware Extensions for Applications", "A token with the name {TOKEN_NAME} already exists for this message Refreshable sandbox"+uniqueID,manageMessagesInstance);
				System.out.println("Updated <FND_MSG_TOKEN_UNIQUE> message");
				System.out.println("Intiating <QAMESSAGE_"+uniqueID+"> message update within refreshable sandbox session");
				mWCInstance.updateMessage("QAMESSAGE_"+uniqueID, "Oracle Middleware Extensions for Applications", "Created message updated within refreshable sandbox session"+uniqueID,manageMessagesInstance);
				System.out.println("Updated <QAMESSAGE_"+uniqueID+"> message");
				System.out.println("Initiating sandbox exit for refreshable sandbox "+RefreshableSandboxname);
				sbBannerUIInstance.ExitSandboxSession(RefreshableSandboxname,manageMessagesInstance);
				System.out.println("Refreshable sandbox < "+RefreshableSandboxname+" > exited from its active session");
		}catch(Exception rSUE) {
			System.out.println("Exception in refreshableSandboxUpdates()");
			rSUE.printStackTrace();
			Assert.fail();
		}
	}//End of refreshableSandboxUpdates()
	
	@Test(description = "method will perform sandbox conflict updates on messages in publishable sandbox",dependsOnMethods="refreshableSandboxesCreate",priority = 29)
	public void publishableSandboxUpdates() {
		try {
			System.out.println("Actiavting publishing sandbox "+SandboxName);
			sbUiInstance.EnterSandboxSession(SandboxName,manageMessagesInstance);
				System.out.println("Publishing sandbox < "+SandboxName+" > activated");
			launchManageMessagestf();
				System.out.println("Intiating <FND_MSG_NAME_UNIQUE> message update within publishable sandbox session");
				mWCInstance.updateMessage("FND_MSG_NAME_UNIQUE", "Oracle Middleware Extensions for Applications", "The message name {MESSAGE_NAME} must be unique within an application published conflictsanddbox"+uniqueID,manageMessagesInstance);
				System.out.println("Updated <FND_MSG_NAME_UNIQUE> message");
				System.out.println("Intiating <FND_MSG_TOKEN_UNIQUE> message update within publishable sandbox session");
				mWCInstance.updateMessage("FND_MSG_TOKEN_UNIQUE", "Oracle Middleware Extensions for Applications", "A token with the name {TOKEN_NAME} already exists for this message published conflictsanddbox"+uniqueID,manageMessagesInstance);
				System.out.println("Updated <FND_MSG_TOKEN_UNIQUE> message");
				System.out.println("Initating sandbox <"+SandboxName+"> publish from banner");
				sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,manageMessagesInstance);
			sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageMessagesInstance);
		}catch(Exception pSUE) {
			System.out.println("Exception in publishableSandboxUpdates()");
			pSUE.printStackTrace();
			Assert.fail();
		}
	}//End of publishableSandboxUpdates()

	@Test(description = "method will perform conflict sandbox refresh on messages",dependsOnMethods= {"refreshableSandboxUpdates","publishableSandboxUpdates"},priority = 30)
	public void conflictSandboxRefresh() {
		SandboxMode = "Edit";
		try {
				System.out.println("Initating sandbox < "+RefreshableSandboxname+" > refresh");
				sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname, "Accept",manageMessagesInstance);
				CommonUtils.hold(10);
		}catch(Exception nCSRE) {
			System.out.println("Exception in conflictSandboxRefresh()");
			nCSRE.printStackTrace();
			Assert.fail();
		}
	}//End of conflictSandboxRefresh()
	
	@Test(description = "method will check for messages conflicted changes outside refreshable sandbox session ",dependsOnMethods="conflictSandboxRefresh",priority = 31)
	public void verifyConflictedChanges() {
		SandboxMode = "";
		try {
			launchManageMessagestf();
				System.out.println("Verifying conflicted changes for <FND_MSG_NAME_UNIQUE> message");
				mWCInstance.createMessage("FND_MSG_NAME_UNIQUE", "QA Message Created through Selenium automation script to verify runtime functionality"+uniqueID,manageMessagesInstance);
				mWCInstance.WaitforErrorEleLoad(manageMessagesInstance);
			Assert.assertTrue(mWCInstance.VerifyDuplicateMsgName("FND_MSG_NAME_UNIQUE", "The message name {MESSAGE_NAME} must be unique within an application published conflictsanddbox"+uniqueID,manageMessagesInstance).isDisplayed());
			try {
				mWCInstance.ErroedPopupOk.click();
				CommonUtils.hold(3);
				lWCInstance.ExitTf.click();
					System.out.println("Verifying conflicted changes for <FND_MSG_NAME_UNIQUE> message");
			}catch(Exception NoSuchElementException) {
				lWCInstance.ExitTf.click();
			}
			CommonUtils.hold(5);
		}catch(Exception vCCE) {
			System.out.println("Exception in verifyConflictedChanges()");
			vCCE.printStackTrace();
			Assert.fail();
		}
	}//End of verifyConflictedChanges()
	
	@Test(description = "method will check for messages conflicted changes within conflict refreshed sandbox session",dependsOnMethods="conflictSandboxRefresh",priority = 32)
	public void verifyConflictedChangesWithinRefSB() {
		SandboxMode = "";
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			
			try {
				gPTInstance.accessFromRecentItems("Manage Sandboxes",manageMessagesInstance).click();
				}catch(Exception eI) {
					System.out.println("In verifyConflictedChangesWithinRefSb() <-> Exeption while accessing sandbox ui from Favorite and RecentItems");
					eI.printStackTrace();
					ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageMessagesInstance);
			}
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageMessagesInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);
				System.out.println("Actiavting refreshable sandbox <"+RefreshableSandboxname+">");
			sbUiInstance.EnterSandboxSession(RefreshableSandboxname,manageMessagesInstance);
				System.out.println("Actiavted refreshable sandbox <"+RefreshableSandboxname+"> session");
			launchManageMessagestf();
				System.out.println("Verifying conflicted changes for <FND_MSG_NAME_UNIQUE> message");
				mWCInstance.createMessage("FND_MSG_NAME_UNIQUE", "QA Message Created through Selenium automation script to verify runtime functionality"+uniqueID,manageMessagesInstance);
				mWCInstance.WaitforErrorEleLoad(manageMessagesInstance);
			Assert.assertTrue(mWCInstance.VerifyDuplicateMsgName("FND_MSG_NAME_UNIQUE", "The message name {MESSAGE_NAME} must be unique within an application published conflictsanddbox"+uniqueID,manageMessagesInstance).isDisplayed());
			try {
				mWCInstance.ErroedPopupOk.click();
				CommonUtils.hold(3);
				lWCInstance.ExitTf.click();
					System.out.println("Verifying conflicted changes for <FND_MSG_NAME_UNIQUE> message");
			}catch(Exception NoSuchElementException) {
				lWCInstance.ExitTf.click();
			}
		}catch(Exception vCCE) {
			System.out.println("Exception in verifyConflictedChanges()");
			Assert.fail();
		}
	}//End of verifyConflictedChanges()
	
	@Test(description = "method will update messages objects within refreshable sandbox for mainline conflict refresh", priority = 33)
	public void mainlineRefreshMessagesUpdates() {
		try {
			try {
				gPTInstance.accessFromRecentItems("Manage Sandboxes",manageMessagesInstance).click();
				}catch(Exception eI) {
					System.out.println("In mainlineRefreshLookupUpdates() <-> Exeption while accessing sandbox ui from Favorite and RecentItems");
					eI.printStackTrace();
					ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageMessagesInstance);
			}
		
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageMessagesInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
			RefreshableSandboxname = cSBInstance.CreateSandbox("Messages_MainlineRefresh", "Refreshable Sandbox with essages repository to refresh with mainline changes", "Messages", "Activate","YES",manageMessagesInstance);
			CommonUtils.explicitWait(sbBannerUIInstance.sandboxBannerName(RefreshableSandboxname,manageMessagesInstance), "Visible", "",manageMessagesInstance);
			Assert.assertEquals(sbBannerUIInstance.sandboxBannerName(RefreshableSandboxname, manageMessagesInstance).getText(), RefreshableSandboxname);
			
			launchManageMessagestf(); 
			System.out.println("Initiating updates for the message <FND_DUPLICATE_LOOKUP_TYPE> within sandbox <"+RefreshableSandboxname+"> session");
			mWCInstance.updateMessage("FND_DUPLICATE_LOOKUP_TYPE", "Oracle Middleware Extensions for Applications", "The lookup type already exists refreshable sandbox update"+uniqueID,manageMessagesInstance);
				System.out.println("Sandbox updates for the message <FND_DUPLICATE_LOOKUP_TYPE> done");
				System.out.println("Initiating updates for the message <FND_DUPLICATE_LKTYPE_MEANING> within sandbox <"+RefreshableSandboxname+"> sessio");
				mWCInstance.updateMessage("FND_DUPLICATE_LKTYPE_MEANING", "Oracle Middleware Extensions for Applications", "Another lookup type already has the same meaning refreshable sandbox update"+uniqueID,manageMessagesInstance);
				System.out.println("sandbox updates for the message <FND_DUPLICATE_LKTYPE_MEANING> done");
			CommonUtils.explicitWait(mWCInstance.createBtn, "Click", "",manageMessagesInstance);
				System.out.println("Initiating exit from <"+RefreshableSandboxname+"> sandbox session");
				sbBannerUIInstance.ExitSandboxSession(RefreshableSandboxname,manageMessagesInstance);
				System.out.println("Sandbox <"+RefreshableSandboxname+"> exited from its active session");
				CommonUtils.hold(5);
		}catch(Exception mRLUE) {
			System.out.println("Exception in mainlineRefreshLookupUpdates()");
			mRLUE.printStackTrace();
			Assert.fail();
		}
	}//End of mainlineRefreshLookupUpdates()
	
	@Test(description = "method will update conflicted objects in  mainline", priority = 34)
	public void mainlineConflictchanges() {
		try {
			System.out.println("Initiating maniline updates for messages");
			launchManageMessagestf();
				System.out.println("Initiating message <QAMESSAGE_"+uniqueID+"> delete from mainline");
				mWCInstance.deleteMessage("QAMESSAGE_"+uniqueID, "Oracle Middleware Extensions for Applications",manageMessagesInstance);
				System.out.println("Message <QAMESSAGE_"+uniqueID+"> deleted from mainline");
				System.out.println("Initiating message <FND_DUPLICATE_LOOKUP_TYPE> update from mainline");
				mWCInstance.updateMessage("FND_DUPLICATE_LOOKUP_TYPE", "Oracle Middleware Extensions for Applications", "The lookup type already exists mainline update"+uniqueID,manageMessagesInstance);
				System.out.println("Message <FND_DUPLICATE_LOOKUP_TYPE> successfully updated from mainline");
				System.out.println("Initiating message <FND_DUPLICATE_LKTYPE_MEANING> update from mainline");
				mWCInstance.updateMessage("FND_DUPLICATE_LKTYPE_MEANING", "Oracle Middleware Extensions for Applications", "Another lookup type already has the same meaning mainline update"+uniqueID,manageMessagesInstance);
				System.out.println("Message <FND_DUPLICATE_LKTYPE_MEANING> successfully updated from mainline");
				System.out.println("Maniline updates for messages are successfully completed");
			CommonUtils.hold(3);
		}catch(Exception mCCE) {
			System.out.println("Exception in mainlineConflictchanges()");
			mCCE.printStackTrace();
			Assert.fail();
		}
	}//End of mainlineConflictchanges()
	
	@Test(description = "method will perform conflict refresh with mainline changes", dependsOnMethods= {"mainlineRefreshMessagesUpdates","mainlineConflictchanges"}, priority = 35)
	public void mailineConflictRefresh() {
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				gPTInstance.accessFromRecentItems("Manage Sandboxes",manageMessagesInstance).click();
				}catch(Exception eI) {
					System.out.println("In mailineConflictRefresh() <-> Exeption while accessing sandbox ui from Favorite and RecentItems");
					eI.printStackTrace();
					ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageMessagesInstance);
			}
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageMessagesInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
				System.out.println("Initiating refresh for sandbox <"+RefreshableSandboxname+">");
				sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname, "Accept",manageMessagesInstance);
			CommonUtils.hold(3);
			sbDetailsUIInstance.VerifySandboxRefresh(RefreshableSandboxname,manageMessagesInstance);
		}catch(Exception mCce) {
			System.out.println("Exception in mainlineConflictchanges()");
		//	ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(mCce));
			mCce.printStackTrace();
			Assert.fail();
		}
	}//End of mailineConflictRefresh()
	
	@Test(description = "method will verify conflicted changes with mainline during sandbox refresh", dependsOnMethods="mailineConflictRefresh",priority = 36)
	public void verifyMainlineConflictChanges() {
		try {
				System.out.println("Verifying mainlineconflict changes begins");	
				System.out.println("Navigating to sandbox <"+RefreshableSandboxname+"> details ui");
			sbUiInstance.NavigateToSandboxDetailsUI(RefreshableSandboxname,manageMessagesInstance);
			CommonUtils.hold(3);
				System.out.println("Adding <Lookups> tool for the sandbox <"+RefreshableSandboxname+">");
				sbDetailsUIInstance.addTool("Lookups",manageMessagesInstance); //Adding Lookup Tool
				System.out.println("Activating sandbox <"+RefreshableSandboxname+">");
			CommonUtils.hold(5);
			sbDetailsUIInstance.ActivateSandbox.click(); //Activating Sandbox
			
			CommonUtils.explicitWait(sbBannerUIInstance.SandboxBanner, "Visible", "",manageMessagesInstance);
						
				System.out.println("Navigating to <Setup and Maintenance> page");
		
				gPTInstance.userDropDownIcon.click();
				CommonUtils.explicitWait(gPTInstance.administration_SetupAndMaintenance,"Visible","",manageMessagesInstance);
				gPTInstance.administration_SetupAndMaintenance.click();
				System.out.println("Navigating to <Manage Standard Lookups> taskflow");
				ntFInstance.navigateToAOLTaskFlows("Manage Standard Lookups",manageMessagesInstance);
			CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Click", "",manageMessagesInstance);

				System.out.println("Verifying updatedmessage for duplicate lookuptype <FNDCPQCR_REGION> in mainline");
				lWCInstance.createLookupType("FNDCPQCR_REGION", "QALookupTypeUpdate"+uniqueID, "LookupType created through selenium automation script"+uniqueID, "standard",manageMessagesInstance);
				mWCInstance.WaitforErrorEleLoad(manageMessagesInstance);
			Assert.assertTrue(mWCInstance.VerifyDuplicateLookupType("The lookup type already exists mainline update"+uniqueID,manageMessagesInstance).isDisplayed());
			mWCInstance.ErroedPopupOk.click();
			
			CommonUtils.hold(3);
			lWCInstance.ExitTf.click();
			CommonUtils.explicitWait(cUDInstance.searchField, "Visible", "",manageMessagesInstance);
				System.out.println("Verifying updatedmessage for duplicate lookuptype name with lookup type <FNDCPQCR_REGION> in mainline done");
				System.out.println("Navigating to <Manage Standard Lookups> taskflow");
				ntFInstance.navigateToAOLTfSearchPage("Manage Standard Lookups",manageMessagesInstance);
			CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Click", "",manageMessagesInstance);
			
				System.out.println("Verifying updatedmessage for duplicate lookuptype meaning in mainline");
			System.out.println("Verifying updatedmessage for duplicate lookuptype meaning");
			lWCInstance.createLookupType("QALOOKUPMAINLINEVERIFY"+uniqueID, "FNDCPSCHED", "LookupType created through selenium automation script"+uniqueID, "standard",manageMessagesInstance);
			mWCInstance.WaitforErrorEleLoad(manageMessagesInstance);
			Assert.assertTrue(mWCInstance.VerifyDuplicateLookupType("Another lookup type already has the same meaning mainline update"+uniqueID,manageMessagesInstance).isDisplayed());
			mWCInstance.ErroedPopupOk.click();
			
			CommonUtils.hold(3);
			lWCInstance.ExitTf.click();
				System.out.println("Verifying updatedmessage for duplicate lookuptype meaning in mainline");
		}catch(Exception vMCCE) {
			System.out.println("Exception in verifyMainlineConflictChanges()");
	//		ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(vMCCE));
			vMCCE.printStackTrace();
			Assert.fail();
		}
	}//End of verifyMainlineConflictChanges()

	@Test(description = "method will check for Messages preview mode",priority = 37)
	public void verifyMessagesPreviewMode() {
		try {
			try {
				System.out.println("Checking for sandbox activation");
				CommonUtils.explicitWait(sbBannerUIInstance.SandboxBanner, "Visible", "",manageMessagesInstance);
				System.out.println("Sandbox is activarted");
			}catch(Exception vMPE) {
				System.out.println("Sandbox is not activated. Activating the sandbox");
				System.out.println("Entering into Sandbox session");
				sbUiInstance.EnterSandboxSession(RefreshableSandboxname,manageMessagesInstance);
				System.out.println("Sandbox is activated");
				System.out.println("Sandbox is activated.");
			}
			System.out.println("Flipping activated sandbox mode to 'PREVIEW' ");
			sbBannerUIInstance.SetSandboxPreviewMode(manageMessagesInstance);
			System.out.println("Sandbox is in 'PREVIEW' mode ");
			CommonUtils.hold(5);
			System.out.println("navigating to 'Setup and Maintenance' initiated");
			ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance,manageMessagesInstance);
			CommonUtils.explicitWait(cUDInstance.panelDrawer, "Click", "",manageMessagesInstance);
			CommonUtils.hold(5);
			System.out.println("Navigating to Manage Messages taskflow");
			ntFInstance.navigateToAOLTaskFlows("Manage Messages",manageMessagesInstance);
			CommonUtils.explicitWait(mWCInstance.createBtn, "Click", "",manageMessagesInstance);
			
			System.out.println("Verifying state of messges taskflow in preview mode context");
			Assert.assertEquals(mWCInstance.MessagesPreviewModeMsg.getText(), "Task Messages is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
			System.out.println("Verifying messages updates disable in preview mode context");
			Assert.assertTrue(mWCInstance.PreviewModeSaveTransanction.isDisplayed(), "Messages SAVE transaction is disabled in preview mode");
			Assert.assertTrue(mWCInstance.PreviewModeSaveandClosetransaction.isDisplayed(), "Messages SaveAndClose transaction is disabled in preview mode");
			System.out.println("Preview mode context Verified");
		}catch(Exception vLPME) {
			System.out.println("Exception in VefiryMessagesPreviewMode()");
			vLPME.printStackTrace();
			Assert.fail();
		}
	}//End of VefiryMessagesPreviewMode()
	
	@AfterClass(alwaysRun = true)
	public void Logout() {
		System.out.println("Application logout initiated");
		try {
			aLoginInstance.logout(manageMessagesInstance);

		} catch (Exception ex) {
			aLoginInstance.launchURL(manageMessagesInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(manageMessagesInstance);
		}finally {
			try {
				System.out.println("Opened db connection has been closed");
				manageMessagesInstance.quit();
				System.out.println("driver instance has been closed and quit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing Object Instance ");
			}
		}
		System.out.println("Successfully logged out from the application");
	}

}
