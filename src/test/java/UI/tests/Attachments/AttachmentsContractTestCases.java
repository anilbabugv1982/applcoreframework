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

public class AttachmentsContractTestCases extends GetDriverInstance {

	private WebDriver attachmentinstanceDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private ContractUtils Contractinstance;
	
	
//**********************************************Test Cases**************************************************
	@Parameters({ "ContractUser", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {

		attachmentinstanceDriver = getDriverInstanceObject();
		attachmentinstanceDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		aLoginInstance = new ApplicationLogin(attachmentinstanceDriver);
		ntFInstance = new NavigationTaskFlows(attachmentinstanceDriver);
		Contractinstance=new ContractUtils(attachmentinstanceDriver);
		aLoginInstance.login(username, password, attachmentinstanceDriver);
		Log.info("Logging into the Application");
		// CommonUtils.waitForJStoLoad(attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(100, attachmentinstanceDriver);
		CommonUtils.hold(10);
		
	}

//*************************************************************************************************************************
//********************* Create Contract **************************
//*************************************************************************************************************************

	@Test(priority = 28, description = "Testcase for Creating Contract", enabled = true)
	public void CreateContract() throws Exception {
		
		//aLoginInstance.logout(attachmentinstanceDriver);
		//Log.info("log out of APPLICATION CONSULTANT");
		//aLoginInstance.login("conmgr", "Welcome1", attachmentinstanceDriver);
		//Log.info("Logging into the Application");
		
		ntFInstance.navigateToTask(ContractPage.contracts,attachmentinstanceDriver);
		Log.info("Click on the Contracts from Navigation Menu");
		//CommonUtils.hold(15);
		Contractinstance.createContract(attachmentinstanceDriver,"Items and Services Procurement");
		Assert.assertTrue(ContractPage.headerContractDoc.isDisplayed(), "Contract Header is Displayed");
		//CommonUtils.hold(10);
	}
	
//*************************************************************************************************************************
	@Test(priority = 29, description = "Adding FileType to Contract Document", enabled = true,dependsOnMethods="CreateContract")
	public void ContractDocumentAttachmentsAddFile() throws Exception {
		Contractinstance.contractDocumentFileAttach("Applying patch.txt", attachmentinstanceDriver);
	}

//*************************************************************************************************************************	
	@Test(priority = 30, description = "Updating FileType to Contract Document", enabled = true,dependsOnMethods="ContractDocumentAttachmentsAddFile")
	public void ContractDocumentAttachmentsUpdateFile() throws Exception {
		Contractinstance.updateContractDocumentFileAttachment("Applying patch.txt", "ContractUpdate.jpg", attachmentinstanceDriver);
	}

//*************************************************************************************************************************	
	@Test(priority = 31, description = "Adding URL to Contract Document", enabled = true,dependsOnMethods="CreateContract")
	public void ContractDocumentAttachmentsAddURL() throws Exception {
		Contractinstance.contractDocumentURLAttach("https://www.google.com", "Google", attachmentinstanceDriver);
	}	
	
//*************************************************************************************************************************

	
	@Test(priority = 32, description = "Download File to Contract Document", enabled = true,dependsOnMethods="ContractDocumentAttachmentsUpdateFile")
	public void ContractDocumentAttachmentsDownloadFile() throws Exception {
		Contractinstance.downloadContractDocumentFileAttachment("ContractUpdate.jpg", attachmentinstanceDriver);
	}	
	
//*************************************************************************************************************************

	@Test(priority = 33, description = "Download URL to Contract Document", enabled = true,dependsOnMethods="ContractDocumentAttachmentsAddURL")
	public void ContractDocumentAttachmentsDownloadURL() throws Exception {
		Contractinstance.downloadContractDocumentURLAttachment("https://www.google.com", attachmentinstanceDriver);
	}	
	
//*************************************************************************************************************************
	@Test(priority = 34, description = "Delete File to Contract Document", enabled = true,dependsOnMethods="ContractDocumentAttachmentsUpdateFile")
	public void ContractDocumentAttachmentsDeleteFile() throws Exception {
		Contractinstance.deleteContractDocumentAttachment("ContractUpdate.jpg", attachmentinstanceDriver);
	}	
	
//**********************************************************************************************************************************************************
	@Test(priority = 35, description = "Delete URL to Contract Document", enabled = true,dependsOnMethods="ContractDocumentAttachmentsAddURL")
	public void ContractDocumentAttachmentsDeleteURL() throws Exception {
		Contractinstance.deleteContractDocumentAttachment("https://www.google.com", attachmentinstanceDriver);
	}

//*******************************************************************************************************************************************************	
	@Test(priority = 36, description = "Adding File-URL to Contract Document", enabled = true,dependsOnMethods="CreateContract")
	public void ContractDocumentAttachmentsAddFileURL() throws Exception {
		Contractinstance.contractDocumentURLAttach("file:/C:/Users/ashipare/Desktop/Personal/Goals.pdf", "fileURL", attachmentinstanceDriver);
	}
	
//************************************************************************************************************************************************************
//Supporting
	
//*************************************************************************************************************************
	@Test(priority = 37, description = "Adding FileType to Supporting Document", enabled = true,dependsOnMethods="CreateContract")
	public void SupportingDocumentAttachmentsAddFile() throws Exception {
		Contractinstance.supportingDocumentFileAttach("Applying Support patch.txt", attachmentinstanceDriver);
	}	

//*************************************************************************************************************************	
	@Test(priority = 38, description = "Updating FileType to Contract Document", enabled = true,dependsOnMethods="SupportingDocumentAttachmentsAddFile")
	public void SupportingDocumentAttachmentsUpdateFile() throws Exception {
		Contractinstance.updateSupportingDocumentFileAttachment("Applying Support patch.txt", "SupportDocUpdate.pdf", attachmentinstanceDriver);
	}	

//*************************************************************************************************************************	
	@Test(priority = 39, description = "Adding URL to Contract Document", enabled = true,dependsOnMethods="CreateContract")
	public void SupportingDocumentAttachmentsAddURL() throws Exception {
		Contractinstance.supportingDocumentURLAttach("https://www.oracle.com", "Oracle", attachmentinstanceDriver);
	}		
		
//*************************************************************************************************************************
	@Test(priority = 40, description = "Download File to Contract Document", enabled = true,dependsOnMethods="SupportingDocumentAttachmentsUpdateFile")
	public void SupportingDocumentAttachmentsDownloadFile() throws Exception {
		Contractinstance.downloadSupportingDocumentFileAttachment("SupportDocUpdate.pdf", attachmentinstanceDriver);
	}	
			
		
//*************************************************************************************************************************
	@Test(priority = 41, description = "Download URL to Contract Document", enabled = true,dependsOnMethods="SupportingDocumentAttachmentsAddURL")
	public void SupportingDocumentAttachmentsDownloadURL() throws Exception {
		Contractinstance.downloadSupportingDocumentURLAttachment("https://www.oracle.com", attachmentinstanceDriver);
	}
			
		
//*************************************************************************************************************************
	@Test(priority = 42, description = "Delete File to Contract Document", enabled = true,dependsOnMethods="SupportingDocumentAttachmentsUpdateFile")
	public void SupportingDocumentAttachmentsDeleteFile() throws Exception {
		Contractinstance.deleteSupportingDocumentAttachment("SupportDocUpdate.pdf", attachmentinstanceDriver);
	}		
		
//*************************************************************************************************************************
	@Test(priority = 43, description = "Delete URL to Contract Document", enabled = true,dependsOnMethods="SupportingDocumentAttachmentsAddURL")
	public void SupportingDocumentAttachmentsDeleteURL() throws Exception {
		Contractinstance.deleteSupportingDocumentAttachment("https://www.oracle.com", attachmentinstanceDriver);
	}	
		
//*********************************************************************************************************************************************************		
	@Test(priority = 44, description = "Adding File-URL to Contract Document", enabled = true,dependsOnMethods="CreateContract")
	public void SupportingDocumentAttachmentsAddFileURL() throws Exception {
		Contractinstance.supportingDocumentURLAttach("file:/C:/Users/ashipare/Desktop/Personal/Hyd.pdf", "fileURL", attachmentinstanceDriver);
	}
	
//*************************************************************************************************************************
		
	@Test(priority = 45, description = "Save the Contract", enabled = true,dependsOnMethods="CreateContract")
		public void SaveContract() throws Exception {
		try {
			Contractinstance.saveContract(attachmentinstanceDriver);
		} catch (Exception e) {
			CommonUtils.PageRefresh(attachmentinstanceDriver);
			Contractinstance.saveContract(attachmentinstanceDriver);
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
