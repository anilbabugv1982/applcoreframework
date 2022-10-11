package UI.tests.Attachments;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.AttachmentCommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ContentServerUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ContractUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.FSMExportImportAttachmentUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.FileImportAndExportUIPage;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.FileImportAndExportUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.HcmAttachmentsUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ItemUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ManageTradingPartnerItemsUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.Negotiation;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.NegotiationsUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.OpportunitiesUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.OpportunitiesPage;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.PurchaseOrderPage;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.PurchaseOrderUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ServiceRequestUtils;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ServiceRequestPage;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ContractPage;
import pageDefinitions.UI.oracle.applcore.qa.Audit.AuditReferencePage;
import pageDefinitions.UI.oracle.applcore.qa.Eff.RuntimeValidation;
import pageDefinitions.UI.oracle.applcore.qa.FsmExportImport.FSMExpImpWrapper;
import pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS.ManageAdminProfileValues;

public class AttachmentsOpportunityTestCases extends GetDriverInstance {

	private WebDriver attachmentinstanceDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private OpportunitiesUtils OPPinstance;
	
//**********************************************Test Cases**************************************************
	@Parameters({ "user", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {

		attachmentinstanceDriver = getDriverInstanceObject();
		attachmentinstanceDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		aLoginInstance = new ApplicationLogin(attachmentinstanceDriver);
		ntFInstance = new NavigationTaskFlows(attachmentinstanceDriver);
		nMEInstance = new NavigatorMenuElements(attachmentinstanceDriver);
		OPPinstance = new OpportunitiesUtils(attachmentinstanceDriver);
		aLoginInstance.login(username, password, attachmentinstanceDriver);
		Log.info("Logging into the Application");
		// CommonUtils.waitForJStoLoad(attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(100, attachmentinstanceDriver);
		CommonUtils.hold(10);
		
	}

//*************************************************************************************************************************
//********************* Opportunity **************************
//*************************************************************************************************************************
	@Test(priority = 6, description = "Testcase for Creating Opportunity", enabled = true)
	public void CreateOpportunity() throws Exception {
		
		Log.info("Navigate to Opportunities page");
		ntFInstance.navigateToTask(nMEInstance.Opportunities, attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(attachmentinstanceDriver);
		Log.info("Add Oppurtunity");
		Log.info("Navigate to Opportunities page");
		CommonUtils.waitForPageLoad(attachmentinstanceDriver);
		CommonUtils.hold(12);
		OPPinstance.CreateOpportunity(attachmentinstanceDriver);
		CommonUtils.explicitWait(OpportunitiesPage.manageAttachmentsButton, "Click", "", attachmentinstanceDriver);
		Assert.assertTrue(OpportunitiesPage.manageAttachmentsButton.isDisplayed(), "Unable to View Attachments Icon");
	}

//*************************************************************************************************************************
	
	@Test(priority = 7, description = "Testcase for Adding File to Opportunity", enabled = true,dependsOnMethods="CreateOpportunity")
	public void OpportunityAttachmentsAddFile() throws Exception {
		OPPinstance.addOppFileAttachment("Opportunities.txt", attachmentinstanceDriver);

	}

//*************************************************************************************************************************	
	
	@Test(priority = 8, description = "Testcase for Adding URL to Opportunity", enabled = true,dependsOnMethods="CreateOpportunity")
	public void OpportunitytAttachmentsAddUrl() throws Exception {
		OPPinstance.addOppURLAttachment("https://www.google.com", attachmentinstanceDriver);
	}
	
//*********************************************************************************************************************************************************************
	
	@Test(priority = 9, description = "Update File Attachments in Opportunity", enabled = true,dependsOnMethods="OpportunityAttachmentsAddFile")
	public void OpportunityAttachmentsUpdateFile() throws Exception {
		
		OPPinstance.updateOppFileAttachment("Opportunities.txt", "OppUpdate.docx", attachmentinstanceDriver);
	}
		
//*********************************************************************************************************************************************************************
	@Test(priority = 10, description = "Update URL Attachments in Opportunity", enabled = true,dependsOnMethods="OpportunitytAttachmentsAddUrl")
	public void OpportunityAttachmentsUpdateUrl() throws Exception {
		
		OPPinstance.updateOppURLAttachment("https://www.google.com", "https://www.oracle.com", attachmentinstanceDriver);
		
	}
		
//*********************************************************************************************************************************************************************
	
	@Test(priority = 11, description = "Download File Attachments in Opportunity", enabled = true,dependsOnMethods="OpportunityAttachmentsUpdateFile")
	public void OpportunityAttachmentsDownloadFile() throws Exception {
		OPPinstance.downloadOppFileAttachment("OppUpdate.docx", attachmentinstanceDriver);
		
	}
				
//*********************************************************************************************************************************************************************	
	@Test(priority = 12, description = "Download URL Attachments in Opportunity", enabled = true,dependsOnMethods="OpportunityAttachmentsUpdateUrl")
	public void OpportunityAttachmentsDownloadUrl() throws Exception {
		OPPinstance.downloadOppURLAttachment("https://www.oracle.com", attachmentinstanceDriver);
		
	}
		
		
//*********************************************************************************************************************************************************************
		
	@Test(priority = 13, description = "Delete File Attachments in Opportunity", enabled = true,dependsOnMethods="OpportunityAttachmentsUpdateFile")
	public void OpportunityAttachmentsDeleteFile() throws Exception {
		OPPinstance.deleteOppFileAttachment("OppUpdate.docx", attachmentinstanceDriver);
		
	}	
		
//*********************************************************************************************************************************************************************
		
	@Test(priority = 14, description = "Delete URL Attachments in Opportunity", enabled = true,dependsOnMethods="OpportunityAttachmentsUpdateUrl")
	public void OpportunityAttachmentsDeleteUrl() throws Exception {
		OPPinstance.deleteOppURLAttachment("https://www.oracle.com", attachmentinstanceDriver);
		
	}	

//*************************************************************************************************************************
		
	@Test(priority = 15, description = "Add File URL Attachments in Opportunity", enabled = true,dependsOnMethods="CreateOpportunity")
		public void OpportunityAttachmentsAddFileUrl() throws Exception {
			//OPPinstance.clickOnManageAttachmentsButton(attachmentinstanceDriver);
			OPPinstance.addOppURLAttachment("file:/C:/Users/ashipare/Desktop/Personal/Goals.pdf", attachmentinstanceDriver);
			CommonUtils.explicitWait(OpportunitiesPage.saveAndCloseOpp, "Click", "", attachmentinstanceDriver);
		
	}
		
//*************************************************************************************************************************
		
	@Test(priority = 16, description = "Save the Opportunity", enabled = true,dependsOnMethods="CreateOpportunity")
		public void SaveOpportunity() throws Exception {
		try {
			OPPinstance.saveAndCloseOpportunities(attachmentinstanceDriver);
		} catch (Exception e) {
			CommonUtils.PageRefresh(attachmentinstanceDriver);
			OPPinstance.saveAndCloseOpportunities(attachmentinstanceDriver);
		}
		
	}

//*****************************************************************************************************************************************************************
	
	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {

		CommonUtils.hold(2);
		// CommonUtils.logout();
		aLoginInstance.logout(attachmentinstanceDriver);
		CommonUtils.hold(2);
		attachmentinstanceDriver.quit();
	}
//*****************************************************************************************************************************************************************	

}
