package UI.tests.UnifiedSandbox;

import java.sql.SQLException;


import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import UtilClasses.UI.SetupAndMaintenance;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.*;
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

public class ManageLookups extends GetDriverInstance {
  
	static String SandboxName = null;
	static String RefreshableSandboxname = null;
	static String uniqueID;
	static String SandboxPublishStatus = "";
	static String SandboxMode = "Edit";
	static WebElement sbNameCheck = null;
	
	WebDriver manageLookupsInstance = null;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private UsbEnvironmentMode uSBEnvModeInstance;
	private SandboxBannerUI sbBannerUIInstance;
	private SandboxDetailsUI sbDetailsUIInstance;
	private SandboxUI sbUiInstance;
	private LookupsWrapperClass lWCInstance;
	private CreateSandboxUI cSBInstance;
	
	//For RedWood Changes Testing
	private SetupAndMaintenance samtf;
	
	
	@BeforeClass(alwaysRun = true)
	public void applicationLoginAndSandboxNavigation() {
	
	try {
		manageLookupsInstance =  getDriverInstanceObject();
		if(manageLookupsInstance != null) {
			aLoginInstance = new ApplicationLogin(manageLookupsInstance);
			ntFInstance = new NavigationTaskFlows(manageLookupsInstance);
			nMEInstance = new NavigatorMenuElements(manageLookupsInstance);
			uSBEnvModeInstance	= new UsbEnvironmentMode(manageLookupsInstance);
			sbBannerUIInstance = new SandboxBannerUI(manageLookupsInstance);
			sbDetailsUIInstance = new SandboxDetailsUI(manageLookupsInstance);
			sbUiInstance = new SandboxUI(manageLookupsInstance);
			lWCInstance = new LookupsWrapperClass(manageLookupsInstance);
			cSBInstance = new CreateSandboxUI(manageLookupsInstance);
	
		}else
			System.out.println("Lookups Instance is NULL");
		
		
	//	System.out.println("signing to the application with 'app_impl_consultant' user");
		
			/*if (uSBEnvModeInstance.CheckForUsbMode(uSBEnvModeInstance.RetreiveUsbProfileOption).equalsIgnoreCase("N") && uSBEnvModeInstance.CheckForUsbMode(uSBEnvModeInstance.RetrevieADFShareProfileOption).equalsIgnoreCase("FALSE")) {
				 throw new SkipException("Skipping test because environment not in USB mode");
			  }*/
		
			 System.out.println("USB mode check completed");
			aLoginInstance.login("app_impl_consultant", "Welcome1",manageLookupsInstance);
		
		CommonUtils.waitForPageLoad(manageLookupsInstance);
		/*CommonUtils.PageRefresh(manageLookupsInstance);
		CommonUtils.GetPageURL(manageLookupsInstance);
		Assert.assertTrue(CommonUtils.GetPageURL(manageLookupsInstance).contains("FuseWelcome"));*/
		System.out.println("FuseWelcome Page");
			uniqueID = CommonUtils.uniqueId();
			CommonUtils.hold(5);
		System.out.println("Navigating to Sandboxes UI Begins");
			ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageLookupsInstance);
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageLookupsInstance);
		Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
		System.out.println("Sandboxes UI Loaded");
		} catch (Exception aLASNE) {
			aLASNE.printStackTrace();
			Assert.fail();
		}
	}//End of ApplicationLogin()

	@Test (description = "method will create sandbox with Lookup repository ", priority = 1)
	public void createSandbox() {
		try {
			
				System.out.println("Initiating Sandbox creation");
			SandboxName = cSBInstance.CreateSandbox("LookupSandbox", "Sandbox with lookup repository", "Lookups", "Activate","YES",manageLookupsInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,manageLookupsInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", SandboxName,manageLookupsInstance);
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
	
	@Test (description = "method will launch Manage Standard Lookups taskflow", dependsOnMethods = "createSandbox", priority = 2)
	public void launchStandardLookuptf() {
		try {
				System.out.println("navigating to <Setup and Maintenance> initiated");
				aLoginInstance.getGloablePageTemplateInstance().userDropDownIcon.click();
				CommonUtils.explicitWait(aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance,"Visible","",manageLookupsInstance);
				aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance.click();
				ntFInstance.navigateToAOLTaskFlows("Manage Standard Lookups",manageLookupsInstance);
			CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Click", "",manageLookupsInstance);
				System.out.println("<Manage Standard Lookups> taskflow loaded successfully");
			try {
				if(SandboxMode.equalsIgnoreCase("Edit")) {
					Assert.assertEquals(lWCInstance.LookupsEditModeMsg.getText(), "All configurations on this page are done in the current sandbox.");
						System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <EDIT> mode");
				}else if(SandboxMode.equalsIgnoreCase("Preview")) {
					Assert.assertEquals(lWCInstance.LookupsPreviewModeMsg.getText(), "Task Lookups is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
						System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <PREVIEW> mode");
				}
					
			}catch (Exception NoSuchElementException) {
					System.out.println("<Manage Messages> taskflow loaded outside sandbox session");
				Assert.assertTrue(true);
			}
		} catch (Exception LLTE) {
			System.out.println("Exception while launching Lookup taskflow");
			LLTE.printStackTrace();
			Assert.fail();
		}
	}//End of LaunchLookuptf()
	
	

	@Test(description = "method will create LookupType and LookupCode within an active sandbox session", dependsOnMethods = "launchStandardLookuptf", priority = 3)
	public void createLookupTypeAndCode() {
		try {
				System.out.println("Intiating <QALOOKUPTYPE "+uniqueID +" > Lookup Creation");
					lWCInstance.createLookupType("QALOOKUPTYPE"+uniqueID, "NewLookupType"+uniqueID, "Lookup Type created using Selenium automation"+uniqueID, "standard",manageLookupsInstance);
					lWCInstance.WaitAfterCreateLookupType("Lookup Type created using Selenium automation"+uniqueID,manageLookupsInstance);
				System.out.println("LookupType <QALOOKUPTYPE "+uniqueID +" > created succeessfully");
				System.out.println("Intiating <QALOOKUPCODE "+uniqueID +" > LookupCode Creation for <FNDCPQCR_REGION> LookupType");
					lWCInstance.createLookupCode("QALOOKUPTYPE"+uniqueID, "QA_LC_1"+uniqueID, "NewLookupCode_1_"+uniqueID, "Lookup Code created using Selenium automation"+uniqueID, "standard", manageLookupsInstance);
				System.out.println("LookupCode <QA_LC_1"+uniqueID +" > created successfully for <QALOOKUPTYPE"+uniqueID +" > LookupType");
				System.out.println("Intiating <QA_LC_2 "+uniqueID +" > LookupCode Creation for <QALOOKUPTYPE"+uniqueID +" > LookupType");
					lWCInstance.createLookupCode("QALOOKUPTYPE"+uniqueID, "QA_LC_2"+uniqueID, "NewLookupCode_2_"+uniqueID, "Lookup Code created using Selenium automation"+uniqueID, "standard", manageLookupsInstance);
				System.out.println("LookupCode <QA_LC_2"+uniqueID +" > created successfully for <QALOOKUPTYPE"+uniqueID +" > LookupType");
					lWCInstance.createLookupCode("FNDCPQCR_REGION", "QALOOKUPCODE"+uniqueID, "NewLookupCode"+uniqueID, "Lookup Code created using Selenium automation"+uniqueID, "standard",manageLookupsInstance);
				System.out.println("LookupCode <QALOOKUPCODE "+uniqueID +" > created successfully for <FNDCPQCR_REGION> LookupType");
		}catch(Exception CLTE) {
			System.out.println("Exception in createLookupType()");
			CLTE.printStackTrace();
			Assert.fail();
		}
		
	}//End of createLookupTypeAndCode()
	
	
	@Test(description = "method will Update LookupType and LookupCode within an active sandbox session", dependsOnMethods = "launchStandardLookuptf", priority = 4)
	public void updateLookupTypeAndCode() {
		try {
				System.out.println("Updating LookupType <FND_TREE_FLATTEN_REFRESH_TYPE>");
					lWCInstance.updateLookupType("FND_TREE_FLATTEN_REFRESH_TYPE", "QALookupTypeUpdate"+uniqueID, "LookupType update through selenium automation script"+uniqueID,manageLookupsInstance);
				System.out.println("LookupType <FND_TREE_FLATTEN_REFRESH_TYPE> updated successfully");
				System.out.println("Updating LookupCode <REVIEW> for lookuptype <ACN_IDEA_STATUS>");
					lWCInstance.updateLookupCode("ACN_IDEA_STATUS", "REVIEW", "QALookupCodeUpdate"+uniqueID, "LookupCode update through selenium automation script"+uniqueID, "standard",manageLookupsInstance);
				System.out.println("LookupCode <REVIEW> updated successfully for lookuptype <ACN_IDEA_STATUS>");
				System.out.println("Updating LookupType <FND_TREE_LABEL_TYPE>");
					lWCInstance.updateLookupType("FND_TREE_LABEL_TYPE", "QALookupTypeUpdateNonConflictRefresh"+uniqueID, "LookupType update for NonConflictRefresh through selenium automation script"+uniqueID,manageLookupsInstance);
				System.out.println("LookupType <FND_TREE_LABEL_TYPE> updated successfully");
				System.out.println("Updating LookupCode <Y> for lookuptype <FND_PAGE_COMPOSER_SOURCE_VIEW>");
					lWCInstance.updateLookupCode("FND_PAGE_COMPOSER_SOURCE_VIEW", "Y", "YesNonConflictRefresh", "LookupCode update for NonConflictRefresh through selenium automation script", "standard",manageLookupsInstance);
				System.out.println("LookupCode <Y> updated successfully for lookuptype <FND_PAGE_COMPOSER_SOURCE_VIEW>");
			
		}catch(Exception CLTE) {
			System.out.println("Exception in createLookupCode()");
			CLTE.printStackTrace();
			Assert.fail();
		}
		
	}//End of updateLookupTypeAndCode()
	

	@Test(description = "method will Verify sandbox changes written to mainline or not before sandbox publish", dependsOnMethods = {"createLookupTypeAndCode","updateLookupTypeAndCode"},  priority = 5)
	public void VerifySandboxChanges() {
		SandboxMode = "";
		try {
				System.out.println("Initiating "+SandboxName+" sandbox exit");
			Assert.assertTrue(sbBannerUIInstance.ExitSandboxSession(SandboxName,manageLookupsInstance));
			launchStandardLookuptf();
			try {
					System.out.println("Verifying for <QALOOKUPTYPE "+uniqueID+" > lookuptype written to mainline");
					lWCInstance.searchLookupType("QALOOKUPTYPE"+uniqueID, "");
				CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Visible", "",manageLookupsInstance);
				//Assert.assertTrue(LookupsWrapperClass.verifyStatus("QALOOKUPTYPE"+uniqueID, "", "NewLookupType"+uniqueID, "Lookup Type created using Selenium automation"+uniqueID)); //Created LookupType Verification
				Assert.assertTrue(manageLookupsInstance.findElement(By.xpath("//input[@value= 'NewLookupType'"+uniqueID+"']")).isDisplayed()
						&& manageLookupsInstance.findElement(By.xpath("//input[@value='Lookup Type created using Selenium automation'"+uniqueID+"']")).isDisplayed());
			}catch(Exception e) {
					System.out.println(" <QALOOKUPTYPE "+uniqueID+" > lookuptype doesn't written to mainline");
				Assert.assertTrue(true);
			}
			try {
					System.out.println("Verifying for <QALOOKUPCODE "+uniqueID+" > lookupcode written to mainline for lookuptype <FNDCPQCR_REGION> ");
					lWCInstance.searchLookupType("FNDCPQCR_REGION", "QALOOKUPCODE"+uniqueID);
				CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Visible", "",manageLookupsInstance);
				//Assert.assertTrue(LookupsWrapperClass.verifyStatus("FNDCPQCR_REGION", "QALOOKUPCODE"+uniqueID, "NewLookupCode"+uniqueID, "Lookup Code created using Selenium automation"+uniqueID)); //Created LookupCode Verification
				Assert.assertTrue(manageLookupsInstance.findElement(By.xpath("//input[@value= 'NewLookupCode'"+uniqueID+"']")).isDisplayed()
						&& manageLookupsInstance.findElement(By.xpath("//input[@value='Lookup Code created using Selenium automation'"+uniqueID+"']")).isDisplayed());
			}catch(Exception e1) {
				System.out.println(" <QALOOKUPCODE "+uniqueID+" > lookupcode doesn't written to mainline for lookuptype <FNDCPQCR_REGION> ");
				Assert.assertTrue(true);
			}
			try {
					System.out.println("Verifying update for <FND_TREE_FLATTEN_REFRESH_TYPE> lookuptype written to mainline");
					lWCInstance.searchLookupType("FND_TREE_FLATTEN_REFRESH_TYPE", "");
				CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Visible", "",manageLookupsInstance);
				//Assert.assertTrue(LookupsWrapperClass.verifyStatus("FND_TREE_FLATTEN_REFRESH_TYPE", "", "QALookupTypeUpdate"+uniqueID, "LookupType update through selenium automation script"+uniqueID)); //UpdatedLookupType Verification
				Assert.assertTrue(manageLookupsInstance.findElement(By.xpath("//input[@value= 'QALookupTypeUpdate'"+uniqueID+"']")).isDisplayed()
						&& manageLookupsInstance.findElement(By.xpath("//input[@value='LookupType update through selenium automation script'"+uniqueID+"']")).isDisplayed());
			}catch(Exception e2) {
					System.out.println("Update for <FND_TREE_FLATTEN_REFRESH_TYPE> lookuptype doesn't written to mainline");
				Assert.assertTrue(true);
			}
			try {
					System.out.println("Verifying update for <REVIEW> lookupcode written to mainline for lookuptype <ACN_IDEA_STATUS> ");
					lWCInstance.searchLookupType("ACN_IDEA_STATUS", "REVIEW");
				CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Visible", "",manageLookupsInstance);
				Assert.assertTrue(manageLookupsInstance.findElement(By.xpath("//input[@value= 'QALookupCodeUpdate'"+uniqueID+"']")).isDisplayed()
						&& manageLookupsInstance.findElement(By.xpath("//input[@value='LookupCode update through selenium automation script'"+uniqueID+"']")).isDisplayed());
			}catch(Exception e3) {
					System.out.println("Update for <REVIEW> lookupcode doesn't written to mainline for lookuptype <ACN_IDEA_STATUS> ");
				Assert.assertTrue(true);
			}
			
	}catch(Exception VSC) {
			System.out.println("Exception in VerifySandboxChanges()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//VerifySandboxChanges

	@Test (description = "method will publish sandbox", dependsOnMethods = {"createSandbox","createLookupTypeAndCode","updateLookupTypeAndCode"}, priority = 6)
	public void sandboxPublish() {
			try {
				System.out.println("Initiating <"+SandboxName+"> publish");
				sbDetailsUIInstance.SandboxPublish(SandboxName,manageLookupsInstance);
				sbUiInstance.AvailableSandboxesLabel.click();
				CommonUtils.hold(5);
				SandboxPublishStatus = "";
			}catch(Exception sPE) {
				System.out.println("Exception in sandboxPublish()");
				sPE.printStackTrace();
				Assert.fail();
			}
	}//End Of  sandboxPublish()
	
	@Test(description = "method will Verify sandbox changes written to mainline after sandbox publish", dependsOnMethods="sandboxPublish", priority = 7)
	public void verifyManilineChangesAfterPublish() {
		SandboxMode = "";
		try {
			launchStandardLookuptf();
				System.out.println("Verifying for <QALOOKUPTYPE "+uniqueID+" > lookuptype written to mainline");
			Assert.assertTrue(lWCInstance.verifyStatus("QALOOKUPTYPE"+uniqueID, "", "NewLookupType"+uniqueID, "Lookup Type created using Selenium automation"+uniqueID,manageLookupsInstance)); //Created LookupType Mainline Verification
				System.out.println(" <QALOOKUPTYPE "+uniqueID+" > lookuptype written to mainline");
				System.out.println("Verifying for <QALOOKUPCODE "+uniqueID+" > lookupcode written to mainline for lookuptype <FNDCPQCR_REGION> ");
			Assert.assertTrue(lWCInstance.verifyStatus("FNDCPQCR_REGION", "QALOOKUPCODE"+uniqueID, "NewLookupCode"+uniqueID, "Lookup Code created using Selenium automation"+uniqueID,manageLookupsInstance)); //Created LookupCode Mainline Verification
				System.out.println(" <QALOOKUPCODE "+uniqueID+" > lookupcode written to mainline for lookuptype <FNDCPQCR_REGION> ");
				System.out.println("Verifying for <FND_TREE_FLATTEN_REFRESH_TYPE> lookuptype update written to mainline");
			Assert.assertTrue(lWCInstance.verifyStatus("FND_TREE_FLATTEN_REFRESH_TYPE", "", "QALookupTypeUpdate"+uniqueID, "LookupType update through selenium automation script"+uniqueID,manageLookupsInstance)); //UpdatedLookupType Mainline Verification
				System.out.println(" <FND_TREE_FLATTEN_REFRESH_TYPE> lookuptype update written to mainline");
				System.out.println("Verifying for <QALOOKUPCODE "+uniqueID+" > lookupcode update written to mainline for lookuptype <FNDCPQCR_REGION> ");
			Assert.assertTrue(lWCInstance.verifyStatus("ACN_IDEA_STATUS", "REVIEW", "QALookupCodeUpdate"+uniqueID, "LookupCode update through selenium automation script"+uniqueID,manageLookupsInstance)); //updatedLookupCode Mainline Verification
				System.out.println(" <QALOOKUPCODE "+uniqueID+" > lookupcode update written to mainline for lookuptype <FNDCPQCR_REGION> ");
		}catch(Exception VSC) {
			System.out.println("Exception in VerifySandboxChanges()");
			VSC.printStackTrace();
			Assert.fail();
		}
		
	}//VerifyManilineChangesAfterPublish

	@Test(description = "mtehod will perform runtime verification after publish", dependsOnMethods= {"updateLookupTypeAndCode","sandboxPublish"},priority = 8)
	public void runtimeVerification() {
		try {
			System.out.println("Navigate to Manage Adminstration ProfileValues tf");
				System.out.println("Navigate to 'Setup and Maintenance' initiated");
					ntFInstance.navigateToTask(nMEInstance.SetupAndMaintenance,manageLookupsInstance);
				System.out.println("Intiating runtime verification of updatedlookup for <FND_PAGE_COMPOSER_SOURCE_VIEW> profile option");
				ntFInstance.navigateToAOLTaskFlows("Manage Administrator Profile Values",manageLookupsInstance);
			CommonUtils.explicitWait(lWCInstance.ProfiletfSaveandClose, "Click", "",manageLookupsInstance);
			System.out.println("Lookup runtime verification begin");
			Assert.assertEquals(lWCInstance.GetUpdatedPOValue("FND_PAGE_COMPOSER_SOURCE_VIEW", "YesNonConflictRefresh",manageLookupsInstance), "YesNonConflictRefresh");
			System.out.println("Lookup runtime verification done");
				System.out.println("Runtime verification of updatedlookup for <FND_PAGE_COMPOSER_SOURCE_VIEW> profile option done");
		}catch(Exception rVE) {
			System.out.println("Exception in runtimeVerification() ");
			rVE.printStackTrace();
			Assert.fail();
		}
	}

	@Test(description = "method will create sandboxes with messages repository to perform conflict refresh",priority = 9)
	public void refreshableSandboxCreate() {
		try {
				System.out.println("Navigating to Sandboxes UI Begins");
			try {
				aLoginInstance.getGloablePageTemplateInstance().accessFromRecentItems("Manage Sandboxes",manageLookupsInstance).click();
			}catch(Exception eI) {
				System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageLookupsInstance);
			}
						
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageLookupsInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
				
				System.out.println("Initiating publishable sandbox creation");
			SandboxName = cSBInstance.CreateSandbox("LookupSandbox", "Sandbox with lookup repository", "Lookups", "","YES",manageLookupsInstance);
				System.out.println("Sandbox < "+SandboxName+" > Successfully Created");
				System.out.println("Initiating Refreshable sandbox creation");
			RefreshableSandboxname = cSBInstance.CreateSandbox("LookupSandbox_Refresh", "Refreshable Sandbox with lookup repository", "Lookups", "Activate","YES",manageLookupsInstance);
				System.out.println("Sandbox < "+RefreshableSandboxname+" > Successfully Created");
			sbNameCheck = sbUiInstance.getSandboxNameElement(SandboxName,manageLookupsInstance);
			CommonUtils.explicitWait(sbNameCheck, "TextPresent", RefreshableSandboxname,manageLookupsInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
		}catch(Exception rSCE) {
			System.out.println("Exception in refreshableSandboxCreate() ");
			rSCE.printStackTrace();
		}
	}
	
	@Test(description = "method will perform sandbox conflict updates on lookup fields in refreshable sandbox",dependsOnMethods="refreshableSandboxCreate",priority = 10)
	public void refreshableSandboxUpdates() {
		try{
			
			System.out.println("Initating Lookups changes within refreshable sandbox "+RefreshableSandboxname);
			System.out.println("Loading Lookuptf");
		
			aLoginInstance.getGloablePageTemplateInstance().userDropDownIcon.click();
			CommonUtils.explicitWait(aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance,"Visible","",manageLookupsInstance);
			aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance.click();
			ntFInstance.navigateToAOLTaskFlows("Manage Standard Lookups",manageLookupsInstance);
		CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Click", "",manageLookupsInstance);
			System.out.println("<Manage Standard Lookups> taskflow loaded successfully");
		try {
			if(SandboxMode.equalsIgnoreCase("Edit")) {
				Assert.assertEquals(lWCInstance.LookupsEditModeMsg.getText(), "All configurations on this page are done in the current sandbox.");
					System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <EDIT> mode");
			}else if(SandboxMode.equalsIgnoreCase("Preview")) {
				Assert.assertEquals(lWCInstance.LookupsPreviewModeMsg.getText(), "Task Lookups is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
					System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <PREVIEW> mode");
			}
				
		}catch (Exception NoSuchElementException) {
				System.out.println("<Manage Lookups> taskflow loaded outside sandbox session");
			Assert.assertTrue(true);
		}
			System.out.println("updating Lookup");
				System.out.println("Initating update on <Y> LookupCode of <FND_PAGE_COMPOSER_SOURCE_VIEW> lookuptype within refreshable sandbox "+RefreshableSandboxname);
				lWCInstance.updateLookupCode("FND_PAGE_COMPOSER_SOURCE_VIEW", "Y", "YesConflictRefresh"+uniqueID, "LookupCode update for ConflictRefresh sandbox through selenium automation script", "standard",manageLookupsInstance);
				System.out.println("Updated <Y> LookupCode of <FND_PAGE_COMPOSER_SOURCE_VIEW> lookuptype within refreshable sandbox "+RefreshableSandboxname);
			CommonUtils.hold(3);
			System.out.println("Exit sandbox");
				System.out.println("Initiating sandbox exit for refreshable sandbox "+RefreshableSandboxname);
				sbBannerUIInstance.ExitSandboxSession(RefreshableSandboxname,manageLookupsInstance);
			System.out.println("Refreshable sandbox < "+RefreshableSandboxname+" > exited from its active session");
			
		}catch(Exception pRSUE) {
			pRSUE.printStackTrace();
			System.out.println("Exception in performingRefreshableSandboxUpates() ");
			Assert.fail();
		}
	}
	
	@Test(description = "method will perform sandbox updates on lookup fields in publishable sandbox",dependsOnMethods="refreshableSandboxCreate", priority = 11)
	public void publishableSandboxUpdates() {
		try {
			System.out.println("Actiavting publishing sandbox "+SandboxName);
			System.out.println("Enter into publishable sandbox");
			sbUiInstance.EnterSandboxSession(SandboxName,manageLookupsInstance);
				System.out.println("Publishing sandbox < "+SandboxName+" > activated");
			System.out.println("Enterred into publishable sandbox");
			System.out.println("Loading Lookuptf");
			aLoginInstance.getGloablePageTemplateInstance().userDropDownIcon.click();
			CommonUtils.explicitWait(aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance,"Visible","",manageLookupsInstance);
			aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance.click();
			ntFInstance.navigateToAOLTaskFlows("Manage Standard Lookups",manageLookupsInstance);
			CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Click", "",manageLookupsInstance);
			System.out.println("<Manage Standard Lookups> taskflow loaded successfully");
		try {
				if(SandboxMode.equalsIgnoreCase("Edit")) {
					Assert.assertEquals(lWCInstance.LookupsEditModeMsg.getText(), "All configurations on this page are done in the current sandbox.");
						System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <EDIT> mode");
				}else if(SandboxMode.equalsIgnoreCase("Preview")) {
					Assert.assertEquals(lWCInstance.LookupsPreviewModeMsg.getText(), "Task Lookups is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
						System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <PREVIEW> mode");
				}
			}catch (Exception NoSuchElementException) {
					System.out.println("<Manage Messages> taskflow loaded outside sandbox session");
				Assert.assertTrue(true);
			}
			System.out.println("updating Lookup in publishable SANDBOX");
				System.out.println("Initating update on <Y> LookupCode of <FND_PAGE_COMPOSER_SOURCE_VIEW> lookuptype within publishable sandbox "+SandboxName);
				lWCInstance.updateLookupCode("FND_PAGE_COMPOSER_SOURCE_VIEW", "Y", "Yes"+uniqueID, "LookupCode update for punlished sandbox for CRefresh case through selenium automation script", "standard",manageLookupsInstance);
				System.out.println("Updated <Y> LookupCode of <FND_PAGE_COMPOSER_SOURCE_VIEW> lookuptype within publishable sandbox "+SandboxName);
				CommonUtils.hold(3);
				System.out.println("Deleting QA_LC_1"+uniqueID +"LookupCode of QALOOKUPTYPE"+uniqueID+" lookuptype within publishable sandbox "+SandboxName);
				lWCInstance.deleteLookupCode("QALOOKUPTYPE"+uniqueID, "QA_LC_1"+uniqueID, manageLookupsInstance);
				System.out.println("Deleted QA_LC_1"+uniqueID +"LookupCode of QALOOKUPTYPE"+uniqueID+" lookuptype within publishable sandbox "+SandboxName);
				System.out.println("Iniitiate publish from banner ");
				System.out.println("Initating sandbox <"+SandboxName+"> publish from banner");
				CommonUtils.hold(5);
				sbDetailsUIInstance.InitiateSandboxPublishFromBanner(SandboxName,manageLookupsInstance);
				System.out.println("Sandbox < "+SandboxName+" > changes published to mainline");
				sbUiInstance.AvailableSandboxesLabel.click();
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageLookupsInstance);
		}catch(Exception pPSUE) {
			pPSUE.printStackTrace();
			System.out.println("Exception in performingPublishableSandboxUpdates()");
			Assert.fail();
		}
	}
	
	
	
	@Test(description = "method will perform conflict sandbox refresh on lookup fields",dependsOnMethods= {"refreshableSandboxUpdates","publishableSandboxUpdates"},priority = 12)
	public void conflictSandboxRefresh() {
		SandboxMode = "Edit";
		try {
				System.out.println("Initating sandbox < "+RefreshableSandboxname+" > refresh");
				sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname, "Accept",manageLookupsInstance);
				CommonUtils.hold(10);
		}catch(Exception nCSRE) {
			System.out.println("Exception in conflictSandboxRefresh()");
			nCSRE.printStackTrace();
			Assert.fail();
		}
	}//End of conflictSandboxRefresh()
	
	@Test(description = "method will check for Accepted Lookup Conflicted changes", dependsOnMethods="conflictSandboxRefresh", priority = 13)
	public void verifyConflictedChanges() {
		SandboxMode = "";
		try {
			System.out.println("Navigate to Manage Adminstration ProfileValues tf");
			aLoginInstance.getGloablePageTemplateInstance().userDropDownIcon.click();
			CommonUtils.explicitWait(aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance,"Visible","",manageLookupsInstance);
			aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance.click();
			ntFInstance.navigateToAOLTaskFlows("Manage Administrator Profile Values",manageLookupsInstance);
			CommonUtils.explicitWait(lWCInstance.ProfiletfSaveandClose, "Click", "",manageLookupsInstance);
			System.out.println("Lookup runtime verification begin");
			Assert.assertEquals(lWCInstance.GetUpdatedPOValue("FND_PAGE_COMPOSER_SOURCE_VIEW", "Yes"+uniqueID,manageLookupsInstance), "Yes"+uniqueID);
			System.out.println("Lookup runtime verification done after conflict refresh");
			CommonUtils.hold(5);
		}catch(Exception vCCE) {
			System.out.println("Exception in verifyConflictedChanges()");
			vCCE.printStackTrace();
			Assert.fail();
		}
	}//End of verifyConflictedChanges()
	
	@Test(description = "method will check for Accepted Lookup Conflicted changes within conflict refreshed sandbox",dependsOnMethods="conflictSandboxRefresh",priority = 13)
	public void verifyConflictedChangesWithinRefSb() {
		SandboxMode = "";
		try {
	
			System.out.println("Navigating to Sandboxes UI Begins");
	
			try {
				aLoginInstance.getGloablePageTemplateInstance().accessFromRecentItems("Manage Sandboxes",manageLookupsInstance).click();
				}catch(Exception eI) {
					System.out.println("In verifyConflictedChangesWithinRefSb() <-> Exeption while accessing sandbox ui from Favorite and RecentItems");
					eI.printStackTrace();
					ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageLookupsInstance);
			}
			
			//CommonUtils.navigateToTask(NavigatorMenuElements.Sandboxes);
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageLookupsInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(5);
			sbUiInstance.EnterSandboxSession(RefreshableSandboxname,manageLookupsInstance);
			System.out.println("Navigate to Manage Adminstration ProfileValues tf");
			aLoginInstance.getGloablePageTemplateInstance().userDropDownIcon.click();
			CommonUtils.explicitWait(aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance,"Visible","",manageLookupsInstance);
			aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance.click();
			ntFInstance.navigateToAOLTaskFlows("Manage Administrator Profile Values",manageLookupsInstance);
			CommonUtils.explicitWait(lWCInstance.ProfiletfSaveandClose, "Click", "",manageLookupsInstance);
			System.out.println("Lookup runtime verification begin");
			Assert.assertEquals(lWCInstance.GetUpdatedPOValue("FND_PAGE_COMPOSER_SOURCE_VIEW", "Yes"+uniqueID,manageLookupsInstance), "Yes"+uniqueID);
			System.out.println("Lookup runtime verification done after conflict refresh");
			CommonUtils.hold(3);
			
		}catch(Exception vCCE) {
			System.out.println("Exception in verifyConflictedChanges()");
			vCCE.printStackTrace();
			Assert.fail();
		}
	}//End of verifyConflictedChanges()
	
	@Test(description = "method will update lookup objects within refreshable sandbox for mainline conflict refresh", priority = 14)
	public void mainlineRefreshLookupUpdates() {
		try {
			try {
				aLoginInstance.getGloablePageTemplateInstance().accessFromRecentItems("Manage Sandboxes",manageLookupsInstance).click();
				}catch(Exception eI) {
					System.out.println("In mainlineRefreshLookupUpdates() <-> Exeption while accessing sandbox ui from Favorite and RecentItems");
					eI.printStackTrace();
					ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageLookupsInstance);
			}
		
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageLookupsInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
			RefreshableSandboxname = cSBInstance.CreateSandbox("LookupSandbox_Refresh", "Refreshable Sandbox with lookup repository to refresh with mainline changes", "Lookups", "Activate","YES",manageLookupsInstance);
			sbNameCheck = sbUiInstance.getSandboxNameElement(RefreshableSandboxname,manageLookupsInstance);
			Assert.assertTrue(sbNameCheck.isDisplayed());
			
			System.out.println("Loading Lookuptf");
			aLoginInstance.getGloablePageTemplateInstance().userDropDownIcon.click();
			CommonUtils.explicitWait(aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance,"Visible","",manageLookupsInstance);
			aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance.click();
			ntFInstance.navigateToAOLTaskFlows("Manage Standard Lookups",manageLookupsInstance);
		CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Click", "",manageLookupsInstance);
			System.out.println("<Manage Standard Lookups> taskflow loaded successfully");
		try {
			if(SandboxMode.equalsIgnoreCase("Edit")) {
				Assert.assertEquals(lWCInstance.LookupsEditModeMsg.getText(), "All configurations on this page are done in the current sandbox.");
					System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <EDIT> mode");
			}else if(SandboxMode.equalsIgnoreCase("Preview")) {
				Assert.assertEquals(lWCInstance.LookupsPreviewModeMsg.getText(), "Task Lookups is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
					System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <PREVIEW> mode");
			}
				
		}catch (Exception NoSuchElementException) {
				System.out.println("<Manage Lookups> taskflow loaded outside sandbox session");
			Assert.assertTrue(true);
		}
			
			System.out.println("updating Lookup within sandbox session");
			lWCInstance.updateLookupCode("FND_TREE_STATUS_TYPE", "ACTIVE", "ACTIVEMainLineUpdate"+uniqueID, "LookupCode update on mainline through selenium automation script", "standard",manageLookupsInstance);
			
			CommonUtils.hold(2);
			System.out.println("In Refreshable SB exitfromSB initiated");
			sbBannerUIInstance.ExitSandboxSession(RefreshableSandboxname,manageLookupsInstance);
			System.out.println("In Refreshable SB exitfromSB done");
			CommonUtils.hold(5);
		}catch(Exception mLUE) {
			System.out.println("Exception in mainlineRefreshLookupUpdates()");
			mLUE.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(description = "method will perform conflicts with mainline changes", priority = 15)
	public void mainlineConflictchanges() {
		try {
			System.out.println("Lookups Mainline Update Begins");
			System.out.println("Loading Lookuptf");
			aLoginInstance.getGloablePageTemplateInstance().userDropDownIcon.click();
			CommonUtils.explicitWait(aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance,"Visible","",manageLookupsInstance);
			aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance.click();
			ntFInstance.navigateToAOLTaskFlows("Manage Standard Lookups",manageLookupsInstance);
		CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Click", "",manageLookupsInstance);
			System.out.println("<Manage Standard Lookups> taskflow loaded successfully");
		try {
			if(SandboxMode.equalsIgnoreCase("Edit")) {
				Assert.assertEquals(lWCInstance.LookupsEditModeMsg.getText(), "All configurations on this page are done in the current sandbox.");
					System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <EDIT> mode");
			}else if(SandboxMode.equalsIgnoreCase("Preview")) {
				Assert.assertEquals(lWCInstance.LookupsPreviewModeMsg.getText(), "Task Lookups is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
					System.out.println("<Manage Standard Lookups> taskflow loaded in sandbox <PREVIEW> mode");
			}
				
		}catch (Exception NoSuchElementException) {
				System.out.println("<Manage Messages> taskflow loaded outside sandbox session");
			Assert.assertTrue(true);
		}
		System.out.println("updating Lookup  in mainline");
		lWCInstance.updateLookupCode("FND_TREE_STATUS_TYPE", "ACTIVE", "ACTIVERefSandboxUpdate"+uniqueID, "LookupCode update on refreshable sandbox through selenium automation script", "standard",manageLookupsInstance);
			System.out.println("Lookups Mainline Update END");
			CommonUtils.hold(2);
		}catch(Exception mCCE) {
			System.out.println("Exception in mainlineConflictchanges()");
			mCCE.printStackTrace();
			Assert.fail();
		}
	}//End of mainlineConflictchanges()	
			
	@Test(description = "method will perform conflict refresh with mainline changes", dependsOnMethods= {"mainlineRefreshLookupUpdates","mainlineConflictchanges"},priority = 16)		
	public void mailineConflictRefresh() {
		try {
			System.out.println("Navigating to Sandboxes UI Begins");
			try {
				aLoginInstance.getGloablePageTemplateInstance().accessFromRecentItems("Manage Sandboxes",manageLookupsInstance).click();
				}catch(Exception eI) {
					System.out.println("In mailineConflictRefresh() <-> Exeption while accessing sandbox ui from Favorite and RecentItems");
					eI.printStackTrace();
					ntFInstance.navigateToTask(nMEInstance.Sandboxes,manageLookupsInstance);
			}
			CommonUtils.explicitWait(sbUiInstance.InitiateSandboxCreate, "Click", "",manageLookupsInstance);
			Assert.assertTrue(sbUiInstance.InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
				sbDetailsUIInstance.SandboxRefresh(RefreshableSandboxname, "Accept",manageLookupsInstance);
			CommonUtils.hold(3);
			sbDetailsUIInstance.VerifySandboxRefresh(RefreshableSandboxname,manageLookupsInstance);
		}catch(Exception mCRE) {
			mCRE.printStackTrace();
			System.out.println("Exception in mailineConflictRefresh()");
			Assert.fail();
		}
	}

	@Test(description = "method will verify conflicted changes with mainline during sandbox refresh", dependsOnMethods="mailineConflictRefresh", priority = 17)
	public void verifyMainlineConflictChanges() {
		try {
			sbUiInstance.EnterSandboxSession(RefreshableSandboxname,manageLookupsInstance);
			System.out.println("Navigate to Manage Tree Structures tf");
			aLoginInstance.getGloablePageTemplateInstance().userDropDownIcon.click();
			CommonUtils.explicitWait(aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance,"Visible","",manageLookupsInstance);
			aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance.click();
			ntFInstance.navigateToAOLTaskFlows("Manage Tree Structures",manageLookupsInstance);
			
			CommonUtils.explicitWait(lWCInstance.ManageTreeStructure_Create, "Click", "",manageLookupsInstance);
			
			Assert.assertEquals(lWCInstance.GetUpdatedTreeStructureFieldValue("Status", "ACTIVEMainLineUpdate"+uniqueID,manageLookupsInstance), "ACTIVEMainLineUpdate"+uniqueID);
			lWCInstance.ManageTreeStructure_Done.click();
			CommonUtils.hold(10);
		}catch(Exception vMCCE) {
			System.out.println("Exception in verifyMainlineConflictChanges()");
			vMCCE.printStackTrace();
		}
	}//End  of verifyMainlineConflictChanges()
		
	@Test(description = "method will check for Lookups preview mode",priority = 18)
	public void VerifyLookupPreviewMode() {
		try {
			CommonUtils.hold(3);
			sbBannerUIInstance.homeIcon.click();
			CommonUtils.waitForPageLoad(manageLookupsInstance);
			CommonUtils.hold(3);
			sbUiInstance.EnterSandboxSession(RefreshableSandboxname,manageLookupsInstance);
			CommonUtils.hold(5);
			sbBannerUIInstance.userDropDownIcon.click();
			CommonUtils.explicitWait(sbBannerUIInstance.administration_SetupAndMaintenance,"Visible","",manageLookupsInstance);
			CommonUtils.hold(2);
			//aLoginInstance.getGloablePageTemplateInstance().administration_SetupAndMaintenance.click();
			sbBannerUIInstance.administration_SetupAndMaintenance.click();
			CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer, "Click", "",manageLookupsInstance);
			sbBannerUIInstance.SetSandboxPreviewMode(manageLookupsInstance);
			CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer, "Click", "",manageLookupsInstance);
			ntFInstance.navigateToAOLTaskFlows("Manage Standard Lookups",manageLookupsInstance);
			CommonUtils.explicitWait(lWCInstance.lkpTypeAddBtn, "Click", "",manageLookupsInstance);
			Assert.assertEquals(lWCInstance.LookupsPreviewModeMsg.getText(), "Task Lookups is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.");
			Assert.assertTrue(lWCInstance.PreviewModeSaveTransanction.isDisplayed(), "Lookups SAVE transaction is disabled in preview mode");
			Assert.assertTrue(lWCInstance.PreviewModeSaveandClosetransaction.isDisplayed(), "Lookups SaveAndClose transaction is disabled in preview mode");
		}catch(Exception vLPME) {
			System.out.println("Exception in VefiryLookupPreviewMode()");
			vLPME.printStackTrace();
			Assert.fail();
		}
	}//End of VefiryLookupPreviewMode()

	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(manageLookupsInstance);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(manageLookupsInstance);
			CommonUtils.hold(2);
			aLoginInstance.logout(manageLookupsInstance);
		}finally {
			try {
				System.out.println("Opened db connection has been closed");
				manageLookupsInstance.quit();
				System.out.println("driver instance has been closed and quit");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing Object Instance ");
			}
		}
	}//End Of Logout()
}//End of ManageLookups
