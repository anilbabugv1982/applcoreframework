/*package oracle.applcore.qa.tests;

import java.sql.SQLException;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import oracle.applcore.qa.UnifiedSandbox.HomePage;
import oracle.applcore.qa.UnifiedSandbox.LookupsPage;
import oracle.applcore.qa.UnifiedSandbox.LookupsWrapperClass;
import oracle.applcore.qa.UnifiedSandbox.ManageDataSecurityPages;
import oracle.applcore.qa.UnifiedSandbox.ManageMessagesPages;
import oracle.applcore.qa.UnifiedSandbox.ManageMessagesWrapperClass;
import oracle.applcore.qa.UnifiedSandbox.StructurePages;
import oracle.applcore.qa.UnifiedSandbox.StructureTestMethods;
import oracle.applcore.qa.UnifiedSandbox.UsbEnvironmentMode;
import oracle.applcore.qa.UnifiedSandbox.AppComposerMethods;
import oracle.applcore.qa.UnifiedSandbox.AppcomposerPage;
import oracle.applcore.qa.UnifiedSandbox.ApplicationTextUI;
import oracle.applcore.qa.UnifiedSandbox.CreateSandboxUI;
import oracle.applcore.qa.UnifiedSandbox.SandboxBannerUI;
import oracle.applcore.qa.UnifiedSandbox.SandboxDetailsUI;
import oracle.applcore.qa.UnifiedSandbox.SandboxUI;
import oracle.applcore.qa.UnifiedSandbox.SearchAndReplace;
import oracle.applcore.qa.common.CommonUtils;
import oracle.applcore.qa.common.GlobalPageTemplate;
import oracle.applcore.qa.common.NavigatorMenuElements;
import oracle.applcore.qa.common.SetupAndMaintenance;
import oracle.applcore.qa.ds.DataSecurityWrapper;
import oracle.applcore.qa.setup.ReportGenerator;


*//**
 * @author vivevenk
 *
 *//*

public class UnifiedSandbox_AllToolsSanity {
  
	static String SandboxName = null;
	static String RefreshableSandboxname = null;
	
	static String uniqueID;
	static String SandboxPublishStatus = "";
	static String SandboxMode = "Edit";
	
	 static String SingularSearch = null;
	 static String SingularReplace = null;
	 static String PluralSearch = "";
	 static String PluralReplace = "";
	
	@BeforeClass
	protected void checkEnvironment() throws SQLException {
	 try {
		if (UsbEnvironmentMode.CheckForUsbMode(UsbEnvironmentMode.RetreiveUsbProfileOption).equalsIgnoreCase("N") && UsbEnvironmentMode.CheckForUsbMode(UsbEnvironmentMode.RetrevieADFShareProfileOption).equalsIgnoreCase("FALSE")) {
		 throw new SkipException("Skipping test because environment not in USB mode");
	  }}
	 catch(Exception e) {
		 
	 }
	}

	@Test (description = "Login to Application", priority = 951)
	public void applicationLogin() throws Exception {
		
		CommonUtils.login("app_impl_consultant", "Welcome1");
		CommonUtils.waitForPageLoad();
		//CommonUtils.PageRefresh();
		//CommonUtils.waitForPageLoad();
		CommonUtils.GetPageURL();
		Assert.assertTrue(CommonUtils.GetPageURL().contains("FuseWelcome"));
		System.out.println("FuseWelcome Page");
		uniqueID = CommonUtils.uniqueId();
	}//End of ApplicationLogin()

	@Test (description = "method will navigate to sandboxui from HomePage after successful login", priority = 952)
	public void sandboxUINavigation() {
		
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				GlobalPageTemplate.globalTemplateArea.click();
			}catch(Exception gTemplate) {
				SandboxBannerUI.SandboxBanner.click();
			}
			
			CommonUtils.hold(2);
			CommonUtils.navigateToTask(NavigatorMenuElements.Sandboxes);
			CommonUtils.explicitWait(SandboxUI.InitiateSandboxCreate, "Click", "");
			Assert.assertTrue(SandboxUI.InitiateSandboxCreate.isDisplayed());
		} catch (Exception SUNE) {
			System.out.println("Exception in SandboxUINavigation()");
			SUNE.printStackTrace();
			Assert.fail();
		}
		
	}//End of SandboxUINavigation()
	
	@Test (description = "method will create sandbox with multi tools", dependsOnMethods = "SandboxUINavigation", priority = 953)
	public void createSandboxes() {
		try {
			SandboxName = CreateSandboxUI.CreateSandbox("AllToolsSanity", "Sandbox with MultiTools", "Lookups,Structure,Data Security,Messages,User Interface Text,Application Composer", "Activate");
			CommonUtils.explicitWait(SandboxUI.SandboxName(SandboxName), "TextPresent", SandboxName);
			Assert.assertTrue(SandboxUI.SandboxName(SandboxName).isDisplayed());
			RefreshableSandboxname = CreateSandboxUI.CreateSandbox("AllToolsSanityRefresh", "Sandbox with MultiTools", "Lookups,Structure,Data Security,Messages,User Interface Text, Application Composer", "");
			CommonUtils.explicitWait(SandboxUI.SandboxName(RefreshableSandboxname), "TextPresent", RefreshableSandboxname);
			Assert.assertTrue(SandboxUI.SandboxName(RefreshableSandboxname).isDisplayed());
			} catch (Exception CSE) {
			System.out.println("Exception in CreateSandbox()");
			Assert.fail();
			CSE.printStackTrace();
		}
	}//End of CreateSandbox()
	
	
	@Test (description = "method will create sandbox with multi tools", dependsOnMethods = "CreateSandboxes", priority = 954)
	public void ToolsCustomization() {
		try {
			System.out.println("Lookups Customization ...");
			launchStandardLookups();
			updateLookupTypeAndCode();
		}
		catch(Exception LKUP) {
			System.out.println("All Tools Customization Exception ");
			LKUP.printStackTrace();
			ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(LKUP));
			Assert.fail();
		}
		
		try {
				System.out.println("Messages Customization ...");
				launchManageMessagestf();
				updateMessageAndMessageToken();
			}
			catch(Exception MSG) {
				System.out.println("All Tools Customization Exception");
				MSG.printStackTrace();
				ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(MSG));
				Assert.fail();
			}
			
		try {
			System.out.println("Data Security Customization ...");
			launchDataSecuritytf();
			createDataSecurityObject();
		}
		catch(Exception DS) {
			System.out.println("All Tools Customization Exception");
			DS.printStackTrace();
			ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(DS));
			Assert.fail();
		}
		
		
		try {
			System.out.println("Structure Customization ...");
			launchStructuretf();
			createStructureMenuItems();
		}
		catch(Exception ST) {
			System.out.println("All Tools Customization Exception");
			ST.printStackTrace();
			ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(ST));
			Assert.fail();
		}
		try {
			System.out.println("User Interface Text Customization ...");
			
			UserInterfaceTextCustomizations("Social","Social_USB");
			
		}
		catch(Exception uti) {
			System.out.println("All Tools Customization Exception");
			uti.printStackTrace();
			ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(uti));
			Assert.fail();
		}
		try {
			appComposerCustomizations();
		}
		catch(Exception AppC) {
			System.out.println("All Tools Customization Exception");
			AppC.printStackTrace();
			ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(AppC));
			Assert.fail();
		}
		
	}
	
	
	@Test (description = "method will create sandbox with multi tools", dependsOnMethods = "CreateSandboxes", priority = 955)
	public void refreshSandboxCustomization() {
		try {
			System.out.println("Activate Refreshable Sandbox ...");
			CommonUtils.navigateToTask(NavigatorMenuElements.Sandboxes);
			CommonUtils.explicitWait(SandboxUI.InitiateSandboxCreate, "Click", "");
			Assert.assertTrue(SandboxUI.InitiateSandboxCreate.isDisplayed());
			System.out.println("Sandboxes UI Loaded");			
			CommonUtils.hold(5);
			SandboxUI.EnterSandboxSession(RefreshableSandboxname);
			
		}
		catch(Exception REF) {
			System.out.println("Exception while activating Sandbox ");
			REF.printStackTrace();
		}
		try {
			System.out.println("Lookups Customization ...");
			launchStandardLookups();
			updateLookupTypeAndCode();
		}
		catch(Exception LKUP) {
			System.out.println("All Tools Customization Exception ");
			LKUP.printStackTrace();
		}
		
		try {
				System.out.println("Messages Customization ...");
				launchManageMessagestf();
				updateMessageAndMessageToken();
			}
			catch(Exception MSG) {
				System.out.println("All Tools Customization Exception");
				MSG.printStackTrace();
			}
			
		try {
			System.out.println("Data Security Customization ...");
			launchDataSecuritytf();
			createDataSecurityObject();
		}
		catch(Exception DS) {
			System.out.println("All Tools Customization Exception");
			DS.printStackTrace();
		}
		
		
		try {
			System.out.println("Structure Customization ...");
			launchStructuretf();
			createStructureMenuItems();
		}
		catch(Exception ST) {
			System.out.println("All Tools Customization Exception");
			ST.printStackTrace();
		}
		try {
			System.out.println("User Interface Text Customization ...");
			
			UserInterfaceTextCustomizations("Social","Social_RefUSB");
			
		}
		catch(Exception uti) {
			System.out.println("All Tools Customization Exception");
			uti.printStackTrace();
		}
		try {
			appComposerCustomizations();
		}
		catch(Exception AppC) {
			System.out.println("All Tools Customization Exception");
			AppC.printStackTrace();
			ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(AppC));
			Assert.fail();
		}
	}

	@Test (description = "method will publish sandbox", priority = 958)
	public void SandboxPublish() {
		System.out.println("Initiating <"+SandboxName+"> publish");
		SandboxDetailsUI.SandboxPublish(SandboxName);		
	    SandboxUI.AvailableSandboxesLabel.click();
		CommonUtils.hold(5);
		SandboxPublishStatus = "";
	}//End Of SandboxPublish()
	
	
	@Test(description = "method will perform runtime verification after publish", priority = 956)
    public void verifyCustomization() {
	try {
	verifyLookups();
	verifyMessages();
	verifyDataSecurity();
	verifyStructure();
	}
	catch(Exception cu) {
		System.out.println("Exception in verifying");
		cu.printStackTrace();
		
	}
	}
	
	
	@Test (description = "method will create sandbox with multi tools", dependsOnMethods = "CreateSandboxes", priority = 957)
    public void RefreshSandbox() {
		try {
		System.out.println("Initating sandbox < "+RefreshableSandboxname+" > refresh");
		SandboxDetailsUI.SandboxRefresh(RefreshableSandboxname, "Accept");
	    }catch(Exception nCSRE) {
		System.out.println("Exception in conflictSandboxRefresh()");
		ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(nCSRE));
		Assert.fail();
	    }
		
	}
	
	@Test(description="Logout from application", priority = 958)
	public void Logout() {
		try {
			CommonUtils.logout();

		} catch (Exception ex) {
			CommonUtils.launchURL();
			CommonUtils.hold(2);
			CommonUtils.logout();
		}
	} 
	
	//Methods to support the Sanity testing for Lookups, Messages, Data Security and Structure
	
	public void launchStandardLookups() {
		try {
				System.out.println("navigating to <Setup and Maintenance> initiated");
				GlobalPageTemplate.userDropDownIcon.click();
				CommonUtils.explicitWait(GlobalPageTemplate.administration_SetupAndMaintenance,"Visible","");
				GlobalPageTemplate.administration_SetupAndMaintenance.click();
			CommonUtils.navigateToAOLTaskFlows("Manage Standard Lookups");
			CommonUtils.explicitWait(LookupsPage.lkpTypeAddBtn, "Click", "");
				System.out.println("<Manage Standard Lookups> taskflow loaded successfully");
			try {
				if(SandboxMode.equalsIgnoreCase("Edit")) {
					Assert.assertEquals(LookupsPage.LookupsEditModeMsg.getText(), "All customizations in this page are being carried out in the current Sandbox.");
						System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <EDIT> mode");
				}else if(SandboxMode.equalsIgnoreCase("Preview")) {
					Assert.assertEquals(LookupsPage.LookupsPreviewModeMsg.getText(), "Lookups task is in a read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to perform customizations.");
						System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <PREVIEW> mode");
				}
					
			}catch (Exception NoSuchElementException) {
					System.out.println("<Manage Messages> taskflow loaded outside sandbox session");
				Assert.assertTrue(true);
			}
		} catch (Exception LLTE) {
			System.out.println("Exception while launching Lookup taskflow");
			ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(LLTE));
			Assert.fail();
		}
	}//End of LaunchLookuptf()
	
	public void updateLookupTypeAndCode() {
		try {
				
				System.out.println("Updating LookupCode <Y> for lookuptype <FND_PAGE_COMPOSER_SOURCE_VIEW>");
			LookupsWrapperClass.updateLookupCode("FND_PAGE_COMPOSER_SOURCE_VIEW", "Y", "YesNonConflictRefresh", "LookupCode update for NonConflictRefresh through selenium automation script", "standard");
				System.out.println("LookupCode <Y> updated successfully for lookuptype <FND_PAGE_COMPOSER_SOURCE_VIEW>");
			
		}catch(Exception CLTE) {
			System.out.println("Exception in createLookupCode()");
			
		}
		
	}//End of updateLookupTypeAndCode()
	
	
	
	public void launchManageMessagestf() {
		try {
			System.out.println("navigating to <Setup and Maintenance> initiated");		
			GlobalPageTemplate.userDropDownIcon.click();
			CommonUtils.explicitWait(GlobalPageTemplate.administration_SetupAndMaintenance,"Visible","");
			GlobalPageTemplate.administration_SetupAndMaintenance.click();
			CommonUtils.navigateToAOLTaskFlows("Manage Messages");
			CommonUtils.explicitWait(ManageMessagesPages.createBtn, "Click", "");
				System.out.println("<Manage Messages> taskflow loaded successfully");
			try {
			if(SandboxBannerUI.SandboxMode.getText().contains("Edit")) {
				Assert.assertEquals(ManageMessagesPages.MessagesEditModeMsg.getText(), "All customizations in this page are being carried out in the current Sandbox.");
					System.out.println("<Manage Messages> taskflow loaded in sandbox <EDIT> mode");
			}			
			else if(SandboxBannerUI.SandboxMode.getText().contains("Preview")){
				Assert.assertEquals(ManageMessagesPages.MessagesPreviewModeMsg.getText(), "Task Messages is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
					System.out.println("<Manage Messages> taskflow loaded in sandbox <PREVIEW> mode");
			}
				
			}catch (Exception NoSuchElementException) {
				System.out.println("<Manage Messages> taskflow loaded outside sandbox session");
				Assert.assertTrue(true);
			}
		} catch (Exception LLTE) {
			System.out.println("Exception while launching Manage Messages taskflow");
			ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(LLTE));
			Assert.fail();
		}
	}//End of ManageMessagestf()
	

	public void updateMessageAndMessageToken() {
		try {
			
				System.out.println("Intiating <FND_DUPLICATE_LOOKUP_CODE> message update");
			ManageMessagesWrapperClass.updateMessage("FND_DUPLICATE_LOOKUP_CODE", "Oracle Middleware Extensions for Applications", "Lookup Type {LKTYPE} already has code {LKCODE} updated within nonConflict refresh Sandboxsession"+uniqueID);
				System.out.println("updated <FND_DUPLICATE_LOOKUP_CODE> message ");
				System.out.println("Intiating <FND_DUPLICATE_LKCODE_MEANING> message update");
			ManageMessagesWrapperClass.updateMessage("FND_DUPLICATE_LKCODE_MEANING", "Oracle Middleware Extensions for Applications", "Lookup Type {LKTYPE} already has lookup code meaning {LKMEANING} updated within nonConflict refresh Sandboxsession"+uniqueID);
				System.out.println("updated <FND_DUPLICATE_LKCODE_MEANING> message");
				
		}catch(Exception CLTE) {
			System.out.println("Exception in UpdateMessageAndMessageToken()");
			ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(CLTE));
			
			Assert.fail();
		}
		
	}//End of UpdateMessageAndMessageToken()
	
	
	public void launchDataSecuritytf() {
		try {
			
			DataSecurityWrapper.navigateToDStaskFlow();
			CommonUtils.explicitWait(ManageDataSecurityPages.objNameSearchField, "Visible", "");
			
			try {
				if(SandboxBannerUI.SandboxMode.getText().contains("Edit"))
					Assert.assertEquals(ManageDataSecurityPages.DSEditModeMsg.getText(), "All customizations in this page are being carried out in the current Sandbox.");
				if(SandboxBannerUI.SandboxMode.getText().contains("Preview"))
					Assert.assertEquals(ManageDataSecurityPages.DSPreviewModeMsg.getText(), "Task Data Security is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
				
			}catch (Exception NoSuchElementException) {
				Assert.assertTrue(true);
			}
		
		} catch (Exception LLTE) {
			System.out.println("Exception while launching DataSecurity taskflow");
			LLTE.printStackTrace();
			Assert.fail();
		}
	}//End of launchDataSecuritytf()
	
	public void createDataSecurityObject() {
		try {
			DataSecurityWrapper.createDataBaseObject("QAAutomationTest"+uniqueID);
			System.out.println("Database object created");
			DataSecurityWrapper.createCondition("QAAutomationTest"+uniqueID, "QAAutomationCOND_"+uniqueID);
			System.out.println("Database condition created");
			DataSecurityWrapper.createAcion("QAAutomationTest"+uniqueID, "QAAutomation"+uniqueID);
			System.out.println("Database Action created");
			DataSecurityWrapper.createDataBasePolicy("QAAutomationTest"+uniqueID, "QAAutomationPOL_"+uniqueID);
			System.out.println("Database Policy created");
			CommonUtils.hold(5);
			//DataSecurityWrapper.createDataBaseObjectPolicy("FND_OBJECTS", "QAAutomationTest"+uniqueID, "ORA_EGP_PRODUCT_MANAGER_JOB", "FSCM", "Multiple Values","For Business Object Application Object specfied by the Grant parameter PARAMETER1 for a specific grant for Table FND_OBJECTS", "FND_OBJECTS");
		}catch(Exception CLTE) {
			System.out.println("Exception in createDataSecurityObject()");
			CLTE.printStackTrace();
			Assert.fail();
		}
		
	}//End of createDataSecurityObject()
	
	
	public void launchStructuretf() {
		try {
			GlobalPageTemplate.homeIcon.click();
			CommonUtils.explicitWait(GlobalPageTemplate.fuseWelcomeSpringBoard, "Visible", "");
			CommonUtils.navigateToTask(NavigatorMenuElements.Structure);
			//StructurePages.structureIcon.click();
			CommonUtils.explicitWait(StructurePages.createButton, "Click", "");
			try {
				if(SandboxBannerUI.SandboxMode.getText().contains("Edit"))
					Assert.assertTrue(StructurePages.createButton.isDisplayed());
				else if(SandboxBannerUI.SandboxMode.getText().contains("Preview"))
					Assert.assertTrue(StructurePages.navigationConfiguration.isDisplayed());
			}catch (Exception NoSuchElementException) {
			Assert.assertTrue(true);
			}
		} catch (Exception LLTE) {
			System.out.println("Exception in Strucutre taskflow launch method");
			LLTE.printStackTrace();
			Assert.fail();
		}
	}//End of Structure()
	
	public void createStructureMenuItems() {
		try {
			
			StructureTestMethods.createCategory();
			
			StructureTestMethods.createPageEntryStaticURL();
			
			StructureTestMethods.createPageEntryDynamicURL();
			CommonUtils.hold(5);
		}catch(Exception CLTE) {
			System.out.println("Exception in createPageIntegrationWizardPages()");
			CLTE.printStackTrace();
			Assert.fail();
		}
		
	}//End of createPages()
	
	public void UserInterfaceTextCustomizations(String SingularSearch, String SingularReplace ) {
		//SingularSearch = "Social"; SingularReplace = "Social_ReplaceSB";
		System.out.println("PerformSandboxChanges Begin");
		Assert.assertTrue(SearchAndReplaceWithinActiveSandbox(SingularSearch, SingularReplace), "Search and Replace Succeeded");	
		System.out.println("PerformSandboxChanges End");
	}//End of UserInterfaceTextSearchAndReplace()
	
	
	public boolean SearchAndReplaceWithinActiveSandbox(String SingularSearch, String SingularReplace) {
		boolean SearchandReplaceStatus = false;
		System.out.println("In SearchAndReplaceWithinActiveSandbox()");
		try {
			CommonUtils.hold(5);
			CommonUtils.navigateToTask(NavigatorMenuElements.UserInterfaceText);
			CommonUtils.explicitWait(ApplicationTextUI.SearchAndReplace_Enabled, "Visible", "");
			SearchAndReplace.PreformSearchAndReplace(SingularSearch, SingularReplace, PluralSearch, PluralReplace, "Enterprise_Scheduler_Text");	
			SearchandReplaceStatus = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		return SearchandReplaceStatus;
	}//End of UserInterfaceTextSearchAndReplace()
	
	public void verifyLookups() {
		try {
			System.out.println("Navigate to Manage Adminstration ProfileValues tf");
				System.out.println("Navigate to 'Setup and Maintenance' initiated");
				CommonUtils.navigateToTask(NavigatorMenuElements.SetupAndMaintenance);
				System.out.println("Intiating runtime verification of updatedlookup for <FND_PAGE_COMPOSER_SOURCE_VIEW> profile option");
			CommonUtils.navigateToAOLTaskFlows("Manage Administrator Profile Values");
			CommonUtils.explicitWait(LookupsPage.ProfiletfSaveandClose, "Click", "");
			System.out.println("Lookup runtime verification begin");
			Assert.assertEquals(LookupsPage.GetUpdatedPOValue("FND_PAGE_COMPOSER_SOURCE_VIEW", "YesNonConflictRefresh"), "YesNonConflictRefresh");
			System.out.println("Lookup runtime verification done");
				System.out.println("Runtime verification of updatedlookup for <FND_PAGE_COMPOSER_SOURCE_VIEW> profile option done");
		}catch(Exception rVE) {
				ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(rVE));
			Assert.fail();
		}
	}

	
	public void verifyMessages() {
		try {
				System.out.println("Navigate to 'Setup and Maintenance' initiated");
				CommonUtils.navigateToTask(NavigatorMenuElements.SetupAndMaintenance);
				System.out.println("Intiating runtime verification of updatedmessage for duplicate lookupcode <FND_DUPLICATE_LOOKUP_CODE>");
				System.out.println("Navigate to Manage StandardLookups tf");
			CommonUtils.navigateToAOLTaskFlows("Manage Standard Lookups");
			CommonUtils.explicitWait(LookupsPage.lkpTypeAddBtn, "Click", "");
			
			System.out.println("Verifying updatedmessage for duplicate lookupcode <FND_DUPLICATE_LOOKUP_CODE>");
				System.out.println("Verifying updatedmessage for duplicate lookupcode <FND_DUPLICATE_LOOKUP_CODE>");
			LookupsWrapperClass.createLookupCode("FNDCPQCR_REGION", "D", "NewLookupCode"+uniqueID, "Lookup Code created using Selenium automation"+uniqueID, "standard");
			ManageMessagesPages.WaitforErrorEleLoad();
			Assert.assertTrue(ManageMessagesPages.VerifyDuplicateLookupCode("FNDCPQCR_REGION", "D", "Lookup Type {LKTYPE} already has code {LKCODE} updated within nonConflict refresh Sandboxsession"+uniqueID).isDisplayed());
			ManageMessagesPages.ErroedPopupOk.click();
			
			CommonUtils.hold(3);
			LookupsPage.ExitTf.click();
				System.out.println("runtime verification of updatedmessage for duplicate lookupcode <FND_DUPLICATE_LOOKUP_CODE> done");
			CommonUtils.explicitWait(SetupAndMaintenance.searchField, "Visible", "");
			
				System.out.println("Intiating runtime verification of updatedmessage for duplicate lookupcode  meaning <FND_DUPLICATE_LKCODE_MEANING>");
			CommonUtils.navigateToAOLTfSearchPage("Manage Standard Lookups");
			CommonUtils.explicitWait(LookupsPage.lkpTypeAddBtn, "Click", "");
			System.out.println("Verifying updatedmessage for duplicate lookupcode meaning <FND_DUPLICATE_LKCODE_MEANING>");
			LookupsWrapperClass.createLookupCode("FNDCPAMPM", "QALookupCode", "AM", "Lookup Code created using Selenium automation", "standard");
			ManageMessagesPages.WaitforErrorEleLoad();
			Assert.assertTrue(ManageMessagesPages.VerifyDuplicateLookupCodeMeaning("FNDCPAMPM", "AM", "Lookup Type {LKTYPE} already has lookup code meaning {LKMEANING} updated within nonConflict refresh Sandboxsession"+uniqueID).isDisplayed());
			ManageMessagesPages.ErroedPopupOk.click();
			
			CommonUtils.hold(3);
			LookupsPage.ExitTf.click();
				System.out.println("Runtime verification of updatedmessage for duplicate lookupcode  meaning <FND_DUPLICATE_LKCODE_MEANING> done");
		}catch(Exception rVE) {
			System.out.println("Exception in runtimeVerification()");
			//rVE.printStackTrace();
			ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(rVE));
			Assert.fail();
		}
	}//End of runtimeVerification()
	
	public void verifyDataSecurity() {
		SandboxMode = "";
		try {
			//DataSecurityWrapperClass.navigateToDStaskFlow();
			launchDataSecuritytf();
			Assert.assertTrue(DataSecurityWrapper.searchDSObject("QAAutomationTest"+uniqueID));
		}catch(Exception VSC) {
			System.out.println("Exception in VerifySandboxChanges()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//verifyDataSecurity
	
	public void verifyStructure() {
		try {
				
				HomePage.homeIcon.click();
				CommonUtils.waitForPageLoad();
				System.out.println("Verifying the pages in HomePage");
				
				HomePage.GetFuseIcon(StructureTestMethods.categoryName+uniqueID).click();
				CommonUtils.hold(5);
				Assert.assertTrue(HomePage.GetFuseIcon(StructureTestMethods.pageEntryStaticN+uniqueID).isDisplayed());
				
				//StructureTestMethods.verifyStaticPage();
				
		}catch(Exception VSC) {
			System.out.println("Exception in verifyManilineChangesAfterPublish()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//verifyStructure
	
	
	public void appComposerCustomizations() {
		System.out.println("Customizing Application Composer ........");
		try {

			SandboxBannerUI.navigatetoToolPage("Application Composer");
			CommonUtils.waitForPageLoad();
			CommonUtils.explicitWait(AppcomposerPage.appComposerPageTitle, "TextPresent", "Application Composer");
			CommonUtils.explicitWait(AppcomposerPage.customObjects, "Click", "");			
			AppComposerMethods.createCustomObject("CustObjQA"+uniqueID);
		}catch(Exception CLTE) {
			System.out.println("Exception in AppcomposerCustomizations()");
			CLTE.printStackTrace();
			Assert.fail();
		}
		
	}//End of AppcomposerCustomizations()
	
	
	public void verifyAppComposer() {
		System.out.println("Verify Mainline Changes ........");
		try {
			sandboxUINavigation();
			createSandboxes();
			SandboxBannerUI.navigatetoToolPage("Application Composer");
			CommonUtils.waitForPageLoad();
			CommonUtils.explicitWait(AppcomposerPage.appComposerPageTitle, "TextPresent", "Application Composer");
			CommonUtils.explicitWait(AppcomposerPage.customObjects, "Click", "");
			AppComposerMethods.verifyCustomObject("CustObjQA"+uniqueID);
			
		}catch(Exception VSC) {
			System.out.println("Exception in verifyMainlineChangesAfterPublish()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
		
		
	}//verifyMainlineChangesAfterPublish
	

}//End of All Tools Sanity class
*/