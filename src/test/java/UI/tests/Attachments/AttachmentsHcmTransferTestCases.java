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

public class AttachmentsHcmTransferTestCases extends GetDriverInstance {

	private WebDriver attachmentinstanceDriver;
	private ApplicationLogin aLoginInstance;
	private HcmAttachmentsUtils Hcminstance;
	
//**********************************************Test Cases**************************************************
	@Parameters({ "HcmUser", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {

		attachmentinstanceDriver = getDriverInstanceObject();
		attachmentinstanceDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		aLoginInstance = new ApplicationLogin(attachmentinstanceDriver);
		Hcminstance= new HcmAttachmentsUtils(attachmentinstanceDriver);
		aLoginInstance.login(username, password, attachmentinstanceDriver);
		Log.info("Logging into the Application");
		// CommonUtils.waitForJStoLoad(attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(100, attachmentinstanceDriver);
		CommonUtils.hold(10);
		
	}
	
//***************************************************************************************************************************************************************		
// ****************HCM Transfer***************
//***************************************************************************************************************************************************************	
	@Test(priority = 150, description = "Navigate to Transfer HCM Attachments section", enabled = true)
	public void TransferHcmNavigation() throws Exception {
		
		//aLoginInstance.logout(attachmentinstanceDriver);
		//Log.info("log out of PIMQA");
		//aLoginInstance.login("hcm_user1", "Welcome1", attachmentinstanceDriver);
		//Log.info("Logging into the Application");
		
		Hcminstance.setHcmAttachmentsProfileValues(attachmentinstanceDriver);
		
		Hcminstance.navigateToHCMTaskAttachments("Transfer", attachmentinstanceDriver);
		Hcminstance.clickOnContinueSectionButton(attachmentinstanceDriver);
		Hcminstance.clickOnContinueSectionButton(attachmentinstanceDriver);
		
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 151, description = "Attach File to Transfer HCM.", enabled = true,dependsOnMethods="TransferHcmNavigation")
	public void TransferHCMAttachmentsAddFile() throws Exception {
		Hcminstance.hcmFileAttach("HCMTransferDoc.docx", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 152, description = "Attach URL to Transfer HCM.", enabled = true,dependsOnMethods="TransferHcmNavigation")
	public void TransferHCMAttachmentsAddURL() throws Exception {
		Hcminstance.hcmURLAttach("https://www.google.com", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 153, description = "Edit File Attachment to Transfer HCM.", enabled = true,dependsOnMethods="TransferHCMAttachmentsAddFile")
	public void TransferHCMAttachmentsEditFile() throws Exception {
		Hcminstance.hcmEditFileAttach("HCMTransferDoc.docx", "TransferDoc", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 154, description = "Edit URL Attachment to Transfer HCM.", enabled = true,dependsOnMethods="TransferHCMAttachmentsAddURL")
	public void TransferHCMAttachmentsEditURL() throws Exception {
		Hcminstance.hcmEditURLAttach("https://www.google.com", "Google", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 155, description = "Download File Attachment to Transfer HCM.", enabled = true,dependsOnMethods="TransferHCMAttachmentsEditFile")
	public void TransferHCMAttachmentsDownloadFile() throws Exception {
		Hcminstance.hcmDownloadFileAttach("HCMTransferDoc.docx", "TransferDoc", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 156, description = "Download URL Attachment to Transfer HCM.", enabled = true,dependsOnMethods="TransferHCMAttachmentsEditURL")
	public void TransferHCMAttachmentsDownloadURL() throws Exception {
		Hcminstance.hcmDownloadURLAttach("https://www.google.com", "Google", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 157, description = "Delete File Attachment to Transfer HCM.", enabled = true,dependsOnMethods="TransferHCMAttachmentsEditFile")
	public void TransferHCMAttachmentsDeleteFile() throws Exception {
		Hcminstance.hcmDeleteFileAttach("TransferDoc", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 158, description = "Delete URL Attachment to Transfer HCM.", enabled = true,dependsOnMethods="TransferHCMAttachmentsEditURL")
	public void TransferHCMAttachmentsDeleteURL() throws Exception {
		Hcminstance.hcmDeleteFileAttach("Google", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 159, description = "Attach FileURL to Transfer HCM.", enabled = true,dependsOnMethods="TransferHcmNavigation")
	public void TransferHCMAttachmentsAddFileURL() throws Exception {
		Hcminstance.hcmURLAttach("file:/C:/Users/ashipare/Desktop/Personal/Goals.pdf", attachmentinstanceDriver);
	}

//*******************************************************************************************************************************************************************
		
	@Test(priority = 160, description = "Cancel Transfer HCM", enabled = true,dependsOnMethods="TransferHcmNavigation")
		public void CancelTransfer() throws Exception {
		try {
			Hcminstance.cancelHCM(attachmentinstanceDriver);
		} catch (Exception e) {
			CommonUtils.PageRefresh(attachmentinstanceDriver);
			Hcminstance.cancelHCM(attachmentinstanceDriver);
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
