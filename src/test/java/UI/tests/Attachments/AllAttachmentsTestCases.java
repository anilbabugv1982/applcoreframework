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

public class AllAttachmentsTestCases extends GetDriverInstance {

	private WebDriver attachmentinstanceDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private GlobalPageTemplate glbpageInstance;
	private CommonUtils CUtilInstance;
	private ServiceRequestPage srreqinstance;
	private ServiceRequestUtils SRinstance;
	private OpportunitiesUtils OPPinstance;
	private ContractUtils Contractinstance;
	private NegotiationsUtils Negotinstance;
	private FileImportAndExportUtils fileImportExportUIinstance;
	private PurchaseOrderUtils Orderinstance;
	private RuntimeValidation RuntimeValidationInstance=null;
	private ItemUtils Iteminstance;
	private HcmAttachmentsUtils Hcminstance;
	private ContentServerUtils ContentServerInstance;
	private ManageTradingPartnerItemsUtils TradingPartnerItemsInstance;
	private FSMExpImpWrapper fsmUtil = null;
	private FSMExportImportAttachmentUtils fsmAttachmentInstance;
	
	private String TextUpdatedTitle=null;
	private List<String> taskFlowName;
	private String ipName;
	String fsmTask1;
	String fsmTask2;
	
//**********************************************Test Cases**************************************************
	// @Parameters({ "user", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP() throws Exception {

		attachmentinstanceDriver = getDriverInstanceObject();
		attachmentinstanceDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		aLoginInstance = new ApplicationLogin(attachmentinstanceDriver);
		ntFInstance = new NavigationTaskFlows(attachmentinstanceDriver);
		nMEInstance = new NavigatorMenuElements(attachmentinstanceDriver);
		glbpageInstance = new GlobalPageTemplate(attachmentinstanceDriver);
		srreqinstance = new ServiceRequestPage(attachmentinstanceDriver);
		SRinstance = new ServiceRequestUtils(attachmentinstanceDriver);
		fileImportExportUIinstance = new FileImportAndExportUtils(attachmentinstanceDriver);
		OPPinstance = new OpportunitiesUtils(attachmentinstanceDriver);
		Contractinstance=new ContractUtils(attachmentinstanceDriver);
		Negotinstance= new NegotiationsUtils(attachmentinstanceDriver);
		Orderinstance= new PurchaseOrderUtils(attachmentinstanceDriver);
		RuntimeValidationInstance=new RuntimeValidation(attachmentinstanceDriver);
		Iteminstance= new ItemUtils(attachmentinstanceDriver);
		Hcminstance= new HcmAttachmentsUtils(attachmentinstanceDriver);
		ContentServerInstance= new ContentServerUtils(attachmentinstanceDriver);
		TradingPartnerItemsInstance= new ManageTradingPartnerItemsUtils(attachmentinstanceDriver);
		fsmUtil = new FSMExpImpWrapper(attachmentinstanceDriver);
		fsmAttachmentInstance = new FSMExportImportAttachmentUtils(attachmentinstanceDriver);
		aLoginInstance.login("hcm_user1", "Welcome1", attachmentinstanceDriver);
		Log.info("Logging into the Application");
		// CommonUtils.waitForJStoLoad(attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(100, attachmentinstanceDriver);
		CommonUtils.hold(10);
		
	}
	
//*************************************************************************************************************************
//********************* File Import Export **************************	
//*************************************************************************************************************************
	
	@Test(priority = 1, description = "Testcase for Creating File Import/Export", enabled = true)
	public void CreateFileImportandExport() throws Exception {

		Log.info("Entered FileImportExport");
		//CommonUtils.PageRefresh(attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(20, attachmentinstanceDriver);
		//ntFInstance.navigateToTask(FileImportAndExportUIPage.fileImportExport, attachmentinstanceDriver);
		ntFInstance.navigateToTask(nMEInstance.FileExportAndImport, attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(attachmentinstanceDriver);
		CommonUtils.hold(10);
		
		fileImportExportUIinstance.addFileAttachment("FileImportExport.txt", attachmentinstanceDriver);
		
	}

//********************************************************************************************************************************************************************************
	
	@Test(priority = 2, description = "Testcase to validate download file link in File Import/Export", enabled = true, dependsOnMethods="CreateFileImportandExport")
	public void DownloadFileImportandExport() {
		fileImportExportUIinstance.verifyDownloadFile("FileImportExport.txt", attachmentinstanceDriver);
	}

//*************************************************************************************************************************
	
	@Test(priority = 3, description = "Testcase for Search File in File Import/Export", enabled = true, dependsOnMethods="CreateFileImportandExport")
	public void SearchFileImportandExport() throws Exception {
		fileImportExportUIinstance.searchFile("FileImportExport.txt", attachmentinstanceDriver);

	}

//*************************************************************************************************************************
	@Test(priority = 4, description = "Testcase to validate download file link in File Import/Export", enabled = true, dependsOnMethods="SearchFileImportandExport")
	public void DownloadSearchedFileImportandExport() {
		fileImportExportUIinstance.verifyDownloadFile("FileImportExport.txt", attachmentinstanceDriver);
	}

//*************************************************************************************************************************
	
	@Test(priority = 5, description = "Testcase for Search File in File Import/Export", enabled = true,dependsOnMethods="SearchFileImportandExport")
	public void DeleteFileImportandExport() throws Exception {
		fileImportExportUIinstance.deleteFile("FileImportExport.txt", attachmentinstanceDriver);

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
		Log.info("Navigate to Opourtunities page");
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
	
//*************************************************************************************************************************
//********************* Service Request **************************
//*************************************************************************************************************************
	@Test(priority = 17, description = "Create Service Request", enabled = true)
	public void createServiceRequest() throws Exception {
		
		//CommonUtils.PageRefresh(attachmentinstanceDriver);
		ntFInstance.navigateToTask(ServiceRequestPage.serviceRequests, attachmentinstanceDriver);
		Log.info("Click on Service Request Page in Navigation Pane");
		CommonUtils.waitForPageLoad(80, attachmentinstanceDriver);
		Log.info("Wait for Service Request Page to Load");
		//CommonUtils.hold(30);
		SRinstance.CreateServiceRequest(attachmentinstanceDriver);
		Log.info("Create Service Request");
		
		try {
			//CommonUtils.waitForElement(ServiceRequestPage.addAttachements, 30, 5, attachmentinstanceDriver);
			CommonUtils.explicitWait(ServiceRequestPage.addAttachements, "Click", "", attachmentinstanceDriver);
			Assert.assertTrue(ServiceRequestPage.addAttachements.isDisplayed());
			Log.info("Attachment Icon Exists");
		} catch(Exception e) {
			CommonUtils.popupok(attachmentinstanceDriver);
		}
	}

//*************************************************************************************************************************************************
	
	@Test(priority = 18, description = "Add File Attachments in Service Request", enabled = true,dependsOnMethods="createServiceRequest")
	public void ServiceRequestAttachmentsAddFile() throws Exception {
		SRinstance.addSRFileAttachment("ServiceRequest.PNG", attachmentinstanceDriver);
	}

//**************************************************************************************************************************************************
	
	@Test(priority = 19, description = "Add URL Attachments in Service Request", enabled = true,dependsOnMethods="createServiceRequest")
	public void ServiceRequestAttachmentsAddUrl() throws Exception {
		SRinstance.addSRURLAttachment("https://www.google.com/", "Google", attachmentinstanceDriver);
		
	}
	
//*********************************************************************************************************************************************************************
	
	@Test(priority = 20, description = "Update File Attachments in Service Request", enabled = true,dependsOnMethods="ServiceRequestAttachmentsAddFile")
	public void ServiceRequestAttachmentsUpdateFile() throws Exception {
		SRinstance.clickOnAttachmentFolderIcon(attachmentinstanceDriver);
		
		SRinstance.updateSRFileAttachment("ServiceRequest.PNG", "SR.zip", attachmentinstanceDriver);
	}

//*********************************************************************************************************************************************************************
	
	@Test(priority = 21, description = "Download File Attachments in Service Request", enabled = true,dependsOnMethods="ServiceRequestAttachmentsUpdateFile")
	public void ServiceRequestAttachmentsDownloadFile() throws Exception {
		SRinstance.downloadSRFileAttachment("SR.zip", attachmentinstanceDriver);
		
	}
	
//*********************************************************************************************************************************************************************	
	
	@Test(priority = 22, description = "Delete File Attachments in Service Request", enabled = true,dependsOnMethods="ServiceRequestAttachmentsUpdateFile")
	public void ServiceRequestAttachmentsDeleteFile() throws Exception {
		SRinstance.deleteSRFileAttachment("SR.zip", attachmentinstanceDriver);
		
	}

//*********************************************************************************************************************************************************************
	
	@Test(priority = 23, description = "Update URL Attachments in Service Request", enabled = true,dependsOnMethods="ServiceRequestAttachmentsAddUrl")
	public void ServiceRequestAttachmentsUpdateUrl() throws Exception {
		SRinstance.clickOnAttachmentFolderIcon(attachmentinstanceDriver);
		
		SRinstance.updateSRURLAttachment("Google", "https://www.oracle.com/", "Oracle", attachmentinstanceDriver);
		
	}

//*********************************************************************************************************************************************************************
	
	@Test(priority = 24, description = "Download URL Attachments in Service Request", enabled = true,dependsOnMethods="ServiceRequestAttachmentsUpdateUrl")
	public void ServiceRequestAttachmentsDownloadUrl() throws Exception {
		SRinstance.downloadSRURLAttachment("https://www.oracle.com/", "Oracle", attachmentinstanceDriver);
		
	}
	
//*********************************************************************************************************************************************************************
	
	@Test(priority = 25, description = "Delete URL Attachments in Service Request", enabled = true,dependsOnMethods="ServiceRequestAttachmentsUpdateUrl")
	public void ServiceRequestAttachmentsDeleteUrl() throws Exception {
		SRinstance.deleteSRURLAttachment("Oracle", attachmentinstanceDriver);
		
	}

//*************************************************************************************************************************
	
	@Test(priority = 26, description = "Add File URL Attachments in Service Request", enabled = true,dependsOnMethods="createServiceRequest")
	public void ServiceRequestAttachmentsAddFileUrl() throws Exception {
		SRinstance.addSRURLAttachment("file:/C:/Users/ashipare/Desktop/Personal/Goals.pdf", "fileURL", attachmentinstanceDriver);
		CommonUtils.explicitWait(ServiceRequestPage.saveandcloseSR, "Click", "", attachmentinstanceDriver);
		
	}

//*************************************************************************************************************************
		
	@Test(priority = 27, description = "Save the Service Request", enabled = true,dependsOnMethods="createServiceRequest")
	public void SaveServiceRequest() throws Exception {
		try {
			SRinstance.saveAndCloseServiceRequest(attachmentinstanceDriver);
		} catch (Exception e) {
			CommonUtils.PageRefresh(attachmentinstanceDriver);
			SRinstance.saveAndCloseServiceRequest(attachmentinstanceDriver);
		}	
		
	}
	
//*************************************************************************************************************************
//********************* Create Contract **************************
//*************************************************************************************************************************

	@Test(priority = 28, description = "Testcase for Creating Contract", enabled = true)
	public void CreateContract() throws Exception {
		
		aLoginInstance.logout(attachmentinstanceDriver);
		Log.info("log out of APPLICATION CONSULTANT");
		aLoginInstance.login("conmgr", "Welcome1", attachmentinstanceDriver);
		Log.info("Logging into the Application");
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
	
//*************************************************************************************************************************
	//Manage Negotiations 
//*************************************************************************************************************************	
	@Test(priority = 46, description = "Managing Negotiations", enabled = true)
	public void CreateNegotiations() throws Exception {
		
		aLoginInstance.logout(attachmentinstanceDriver);
		Log.info("log out of CONTRACT MANAGER");
		aLoginInstance.login("cvbuyer01", "Welcome1", attachmentinstanceDriver);
		Log.info("Logging into the Application");
		ntFInstance.navigateToTask(nMEInstance.navigatorProcurementNegotiations,attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(attachmentinstanceDriver);
		Log.info("Click on the Negotations from Navigation Menu");
		Negotinstance.ManageNego(attachmentinstanceDriver);
	}
	
//**************************************************************************************************************************************************************
	@Test(priority = 47, description = "Attach file to Negotiations", enabled = true,dependsOnMethods="CreateNegotiations")
	public void AddFiletoNegotiations() throws Exception {
		Negotinstance.NegotiationFileAttach("Negotiations.pdf", attachmentinstanceDriver);
	}	
	
//************************************************************************************************************************************************************
	@Test(priority = 48, description = "Attach Text to Negotiations", enabled = true,dependsOnMethods="CreateNegotiations")
	public void AddTexttoNegotiations() throws Exception {
		CommonUtils.PageRefresh(attachmentinstanceDriver);
		String textTitle = "TextTitle"+CommonUtils.uniqueId();
		Negotinstance.NegotiationTextAttach("Attachment text", textTitle, attachmentinstanceDriver);
	}
	
//*****************************************************************************************************************************************************************
	@Test(priority = 49, description = "Attach URL to Negotiations", enabled = true,dependsOnMethods="CreateNegotiations")
	public void AddURLtoNegotiations() throws Exception {
		CommonUtils.PageRefresh(attachmentinstanceDriver);
		Negotinstance.NegotiationURLAttach("https://www.google.com", "Google", attachmentinstanceDriver);
	
	}
	
//***************************************************************************************************************************************************************	
	
	@Test(priority = 50, description = "Updating File to Negotiations", enabled = true,dependsOnMethods="AddFiletoNegotiations")
	public void NegotiationsAttachmentsUpdateFile() throws Exception {
		Negotinstance.updateNegoFileAttachment("Negotiations.pdf", "NegoUpdate.zip", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************
	@Test(priority = 51, description = "Updating Text to Negotiations", enabled = true,dependsOnMethods="AddTexttoNegotiations")
	public void NegotiationsAttachmentsUpdateText() throws Exception {
		TextUpdatedTitle = "TextUpdate"+CommonUtils.uniqueId();
		Negotinstance.updateNegoTextAttachment("Attachment text", "Sample Negotiation", TextUpdatedTitle, attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 52, description = "Updating URL to Negotiations", enabled = true,dependsOnMethods="AddURLtoNegotiations")
	public void NegotiationsAttachmentsUpdateURL() throws Exception {
		Negotinstance.updateNegoURLAttachment("https://www.google.com", "https://www.oracle.com", "Oracle", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 53, description = "Download File to Negotiations", enabled = true,dependsOnMethods="NegotiationsAttachmentsUpdateFile")
	public void NegotiationsAttachmentsDownloadFile() throws Exception {
		try {
			Negotinstance.clickOnNegoSaveButton(attachmentinstanceDriver);
		} catch(ElementClickInterceptedException e) {
			Negotinstance.clickOnAttachmentOkButton(attachmentinstanceDriver);
			Negotinstance.clickOnNegoSaveButton(attachmentinstanceDriver);
		}
		Negotinstance.downloadNegoFileAttachment("NegoUpdate.zip", attachmentinstanceDriver);
	}	
	
//*****************************************************************************************************************************************************************
	@Test(priority = 54, description = "Download Text to Negotiations", enabled = true,dependsOnMethods="NegotiationsAttachmentsUpdateText")
	public void NegotiationsAttachmentsDownloadText() throws Exception {
		
		Negotinstance.downloadNegoTextAttachment("Sample Negotiation", TextUpdatedTitle, attachmentinstanceDriver);
	}	
	
//**************************************************************************************************************************************************************
	
	@Test(priority = 55, description = "Download URL to Negotiations", enabled = true,dependsOnMethods="NegotiationsAttachmentsUpdateURL")
	public void NegotiationsAttachmentsDownloadURL() throws Exception {
		Negotinstance.downloadNegoURLAttachment("https://www.oracle.com", attachmentinstanceDriver);
	}	
	
//****************************************************************************************************************************************************************
	@Test(priority = 56, description = "Delete File to Negotiations", enabled = true,dependsOnMethods="NegotiationsAttachmentsUpdateFile")
	public void NegotiationsAttachmentsDeleteFile() throws Exception {
		Negotinstance.deleteNegoAttachment("NegoUpdate.zip", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 57, description = "Delete Text to Negotiations", enabled = true,dependsOnMethods="NegotiationsAttachmentsUpdateText")
	public void NegotiationsAttachmentsDeleteText() throws Exception {
		Negotinstance.deleteNegoAttachment("Sample Negotiation", attachmentinstanceDriver);
		
	}
	
//**************************************************************************************************************************************************************
	@Test(priority = 58, description = "Delete URL to Negotiations", enabled = true,dependsOnMethods="NegotiationsAttachmentsUpdateURL")
	public void NegotiationsAttachmentsDeleteURL() throws Exception {
		Negotinstance.deleteNegoAttachment("https://www.oracle.com", attachmentinstanceDriver);
		Negotinstance.clickOnAttachmentOkButton(attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 59, description = "Adding File-URL to Negotiations", enabled = true,dependsOnMethods="CreateNegotiations")
	public void NegotiationsAttachmentsAddFileURL() throws Exception {
		try {
			Negotinstance.clickOnNegoSaveButton(attachmentinstanceDriver);
		} catch(ElementClickInterceptedException e) {
			Negotinstance.clickOnAttachmentOkButton(attachmentinstanceDriver);
			Negotinstance.clickOnNegoSaveButton(attachmentinstanceDriver);
		}
		Negotinstance.NegotiationURLAttach("file:/C:/Users/ashipare/Desktop/Personal/Goals.pdf", "fileURL", attachmentinstanceDriver);
	}
	
//*************************************************************************************************************************
		
	@Test(priority = 60, description = "Save the Negotiations", enabled = true,dependsOnMethods="CreateNegotiations")
		public void SaveNegotiations() throws Exception {
		try {
			Negotinstance.clickOnNegoSaveButton(attachmentinstanceDriver);
		} catch (Exception e) {
			CommonUtils.PageRefresh(attachmentinstanceDriver);
			Negotinstance.clickOnNegoSaveButton(attachmentinstanceDriver);
		}
		
	}

	
//*************************************************************************************************************************************************************	
// Create Purchase Order
//***************************************************************************************************************************************************************	
	@Test(priority = 61, description = "Create Purchase Order", enabled = true)
	public void CreatePurchaseOrder() throws Exception {
		
		ntFInstance.navigateToTask(nMEInstance.navigatorPurchaseOrder,attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(attachmentinstanceDriver);
		Log.info("Click on the Negotations from Navigation Menu");
		Orderinstance.createOrder(attachmentinstanceDriver);
	}
	
//************************************************************************************************************************************************************
	@Test(priority = 62, description = "Attach file to Purchase Order", enabled = true,dependsOnMethods="CreatePurchaseOrder")
	public void AddFiletoPurchaseOrder() throws Exception {
		Orderinstance.purchaseOrderFileAttach("PurchaseOrder.xls", attachmentinstanceDriver);
	}	
	
//************************************************************************************************************************************************************
	@Test(priority = 63, description = "Attach Text to Purchase Order", enabled = true,dependsOnMethods="CreatePurchaseOrder")
	public void AddTexttoPurchaseOrder() throws Exception {
		CommonUtils.PageRefresh(attachmentinstanceDriver);
		String textTitle = "TextTitle"+CommonUtils.uniqueId();
		Orderinstance.purchaseOrderTextAttach("Attachment text", textTitle, attachmentinstanceDriver);
	}
	
//*****************************************************************************************************************************************************************
	@Test(priority = 64, description = "Attach URL to Purchase Order", enabled = true,dependsOnMethods="CreatePurchaseOrder")
	public void AddURLtoPurchaseOrder() throws Exception {
		CommonUtils.PageRefresh(attachmentinstanceDriver);
		Orderinstance.purchaseOrderURLAttach("https://www.google.com", "Google", attachmentinstanceDriver);
	
	}
	
//***************************************************************************************************************************************************************	
	
	@Test(priority = 65, description = "Updating File to Purchase Order", enabled = true,dependsOnMethods="AddFiletoPurchaseOrder")
	public void PurchaseOrderAttachmentsUpdateFile() throws Exception {
		Orderinstance.updatePurchaseOrderFileAttachment("PurchaseOrder.xls", "OrderUpdate.zip", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************
	@Test(priority = 66, description = "Updating Text to Purchase Order", enabled = true,dependsOnMethods="AddTexttoPurchaseOrder")
	public void PurchaseOrderAttachmentsUpdateText() throws Exception {
		TextUpdatedTitle = "TextUpdate"+CommonUtils.uniqueId();
		Orderinstance.updatePurchaseOrderTextAttachment("Attachment text", "Sample Order", TextUpdatedTitle, attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 67, description = "Updating URL to Purchase Order", enabled = true,dependsOnMethods="AddURLtoPurchaseOrder")
	public void PurchaseOrderAttachmentsUpdateURL() throws Exception {
		Orderinstance.updatePurchaseOrderURLAttachment("https://www.google.com", "https://www.oracle.com", "Oracle", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 68, description = "Download File to Purchase Order", enabled = true,dependsOnMethods="PurchaseOrderAttachmentsUpdateFile")
	public void PurchaseOrderAttachmentsDownloadFile() throws Exception {
		try {
			Orderinstance.clickOnPurchaseOrderSaveButton(attachmentinstanceDriver);
		} catch(ElementClickInterceptedException e) {
			Orderinstance.clickOnAttachmentOkButton(attachmentinstanceDriver);
			Orderinstance.clickOnPurchaseOrderSaveButton(attachmentinstanceDriver);
		}
		Orderinstance.downloadPurchaseOrderFileAttachment("OrderUpdate.zip", attachmentinstanceDriver);
	}	
	
//*****************************************************************************************************************************************************************
	@Test(priority = 69, description = "Download Text to Purchase Order", enabled = true,dependsOnMethods="PurchaseOrderAttachmentsUpdateText")
	public void PurchaseOrderAttachmentsDownloadText() throws Exception {
		
		Orderinstance.downloadPurchaseOrderTextAttachment("Sample Order", TextUpdatedTitle, attachmentinstanceDriver);
	}	
	
//**************************************************************************************************************************************************************
	
	@Test(priority = 70, description = "Download URL to Purchase Order", enabled = true,dependsOnMethods="PurchaseOrderAttachmentsUpdateURL")
	public void PurchaseOrderAttachmentsDownloadURL() throws Exception {
		Orderinstance.downloadPurchaseOrderURLAttachment("https://www.oracle.com", attachmentinstanceDriver);
	}	
	
//****************************************************************************************************************************************************************
	@Test(priority = 71, description = "Delete File to Purchase Order", enabled = true,dependsOnMethods="PurchaseOrderAttachmentsUpdateFile")
	public void PurchaseOrderAttachmentsDeleteFile() throws Exception {
		Orderinstance.deletePurchaseOrderAttachment("OrderUpdate.zip", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 72, description = "Delete Text to Purchase Order", enabled = true,dependsOnMethods="PurchaseOrderAttachmentsUpdateText")
	public void PurchaseOrderAttachmentsDeleteText() throws Exception {
		Orderinstance.deletePurchaseOrderAttachment("Sample Order", attachmentinstanceDriver);
		
	}
	
//**************************************************************************************************************************************************************
	@Test(priority = 73, description = "Delete URL to Purchase Order", enabled = true,dependsOnMethods="PurchaseOrderAttachmentsUpdateURL")
	public void PurchaseOrderAttachmentsDeleteURL() throws Exception {
		Orderinstance.deletePurchaseOrderAttachment("https://www.oracle.com", attachmentinstanceDriver);
		Orderinstance.clickOnAttachmentOkButton(attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 74, description = "Adding File-URL to Purchase Order", enabled = true,dependsOnMethods="CreatePurchaseOrder")
	public void PurchaseOrderAttachmentsAddFileURL() throws Exception {
		try {
			Orderinstance.clickOnPurchaseOrderSaveButton(attachmentinstanceDriver);
		} catch(ElementClickInterceptedException e) {
			Orderinstance.clickOnAttachmentOkButton(attachmentinstanceDriver);
			Orderinstance.clickOnPurchaseOrderSaveButton(attachmentinstanceDriver);
		}
		Orderinstance.purchaseOrderURLAttach("file:/C:/Users/ashipare/Desktop/Personal/Goals.pdf", "fileURL", attachmentinstanceDriver);
		Orderinstance.clickOnPurchaseOrderSaveButton(attachmentinstanceDriver);
	}

//*************************************************************************************************************************
		
	@Test(priority = 75, description = "Save the Purchase Orders", enabled = true,dependsOnMethods="CreatePurchaseOrder")
		public void SavePurchaseOrders() throws Exception {
		try {
			Orderinstance.clickOnPurchaseOrderSaveButton(attachmentinstanceDriver);
		} catch (Exception e) {
			CommonUtils.PageRefresh(attachmentinstanceDriver);
			Orderinstance.clickOnPurchaseOrderSaveButton(attachmentinstanceDriver);
		}
		
	}

//*************************************************************************************************************************************************************	
// Create Item
//***************************************************************************************************************************************************************	
	@Test(priority = 76, description = "Create Item", enabled = true)
	public void CreateItem() throws Exception {
		
		aLoginInstance.logout(attachmentinstanceDriver);
		Log.info("log out of cvbuyer01");
		aLoginInstance.login("pimqa", "Welcome1", attachmentinstanceDriver);
		Log.info("Logging into the Application");
		
		ntFInstance.navigateToTask(glbpageInstance.productInformationManagement, attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(attachmentinstanceDriver);
		Log.info("Clicked on Product Information Management.");
		AttachmentCommonUtils.waitTillLoad(40, attachmentinstanceDriver);
		
		String itemName = "Item_"+CommonUtils.uniqueId();
		System.out.println("Item name to be created  is -"+itemName);
		
		Assert.assertTrue(RuntimeValidationInstance.createItem(itemName, "", "EFF_NT_SR_MR_NV_ALL_LEVEL_ALL_TYPES_2", attachmentinstanceDriver), "Item creation unsuccessful for EFF.");
		Log.info("Item has been created.");
		
	}	

//************************************************************************************************************************************************************
	@Test(priority = 77, description = "Attach file to Item", enabled = true,dependsOnMethods="CreateItem")
	public void AddFiletoItem() throws Exception {
		Iteminstance.itemFileAttach("Item.docx", attachmentinstanceDriver);
	}	
	
//*****************************************************************************************************************************************************************
	@Test(priority = 78, description = "Attach URL to Item", enabled = true,dependsOnMethods="CreateItem")
	public void AddURLtoItem() throws Exception {
		CommonUtils.PageRefresh(attachmentinstanceDriver);
		Iteminstance.itemURLAttach("https://www.google.com", "Google", attachmentinstanceDriver);
	
	}
	
//***************************************************************************************************************************************************************	
	
	@Test(priority = 79, description = "Updating File to Item", enabled = true,dependsOnMethods="AddFiletoItem")
	public void ItemAttachmentsUpdateFile() throws Exception {
		Iteminstance.updateItemFileAttachment("Item.docx", "ItemUpdate.zip", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 80, description = "Updating URL to Item", enabled = true,dependsOnMethods="AddURLtoItem")
	public void ItemAttachmentsUpdateURL() throws Exception {
		Iteminstance.updateItemURLAttachment("https://www.google.com", "https://www.oracle.com", "Oracle", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 81, description = "Download File to Item", enabled = true,dependsOnMethods="ItemAttachmentsUpdateFile")
	public void ItemAttachmentsDownloadFile() throws Exception {
		try {
			Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		} catch(ElementClickInterceptedException e) {
			Iteminstance.clickOnAttachmentOkButton(attachmentinstanceDriver);
			Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		}
		Iteminstance.downloadItemFileAttachment("ItemUpdate.zip", attachmentinstanceDriver);
	}	
	
//*****************************************************************************************************************************************************************
	@Test(priority = 82, description = "Download URL to Item", enabled = true,dependsOnMethods="ItemAttachmentsUpdateURL")
	public void ItemAttachmentsDownloadURL() throws Exception {
		Iteminstance.downloadItemURLAttachment("https://www.oracle.com", attachmentinstanceDriver);
	}	
	
//****************************************************************************************************************************************************************
	@Test(priority = 83, description = "Delete File to Item", enabled = true,dependsOnMethods="ItemAttachmentsUpdateFile")
	public void ItemAttachmentsDeleteFile() throws Exception {
		Iteminstance.deleteItemAttachment("ItemUpdate.zip", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 84, description = "Delete URL to Item", enabled = true,dependsOnMethods="ItemAttachmentsUpdateURL")
	public void ItemAttachmentsDeleteURL() throws Exception {
		Iteminstance.deleteItemAttachment("https://www.oracle.com", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 85, description = "Adding File-URL to Item", enabled = true,dependsOnMethods="CreateItem")
	public void ItemAttachmentsAddFileURL() throws Exception {
		try {
			Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		} catch(ElementClickInterceptedException e) {
			Iteminstance.clickOnAttachmentOkButton(attachmentinstanceDriver);
			Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		}
		Iteminstance.itemURLAttach("file:/C:/Users/ashipare/Desktop/Personal/Goals.pdf", "fileURLItem", attachmentinstanceDriver);
		Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
	}		
	
//***************************************************************************************************************************************************************	
//Create Item - Attachment Table View
//***************************************************************************************************************************************************************
	@Test(priority = 86, description = "Attach file to Item Panel Table View", enabled = true,dependsOnMethods="CreateItem")
	public void AddFiletoItemPanelTable() throws Exception {
		Iteminstance.clickOnAttachmentsTab(attachmentinstanceDriver);
		Iteminstance.clickOnItemPanelTableView(attachmentinstanceDriver);
		Iteminstance.itemPanelFileAttach("Table", "ItemTable.docx", attachmentinstanceDriver);
	}	
	
//************************************************************************************************************************************************************
	@Test(priority = 87, description = "Attach Text to Item Panel Table View", enabled = true,dependsOnMethods="CreateItem")
	public void AddTexttoItemPanelTable() throws Exception {
		//CommonUtils.PageRefresh(attachmentinstanceDriver);
		//String textTitle = "TextTitle"+CommonUtils.uniqueId();
		Iteminstance.itemPanelTextAttach("Table", "Item Table Text", "ItemTableText", attachmentinstanceDriver);
	}
	
//*****************************************************************************************************************************************************************
	@Test(priority = 88, description = "Attach URL to Item Panel Table View", enabled = true,dependsOnMethods="CreateItem")
	public void AddURLtoItemPanelTable() throws Exception {
		//CommonUtils.PageRefresh(attachmentinstanceDriver);
		Iteminstance.itemPanelURLAttach("Table", "https://www.google.com", "Google", attachmentinstanceDriver);
	
	}
	
//***************************************************************************************************************************************************************	
	
	@Test(priority = 89, description = "Updating File to Item Panel Table View", enabled = true,dependsOnMethods="AddFiletoItemPanelTable")
	public void ItemPanelTableAttachmentsUpdateFile() throws Exception {
		Iteminstance.updateItemPanelFileAttachment("Table", "ItemTable.docx", "ItemTableUpdate.zip", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************
	@Test(priority = 90, description = "Updating Text to Item Panel Table View", enabled = true,dependsOnMethods="AddTexttoItemPanelTable")
	public void ItemPanelTableAttachmentsUpdateText() throws Exception {
		TextUpdatedTitle = "TextItemTable"+CommonUtils.uniqueId();
		Iteminstance.updateItemPanelTextAttachment("Table", "ItemTableText", "New Item Table Text", TextUpdatedTitle, attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 91, description = "Updating URL to Item Panel Table View", enabled = true,dependsOnMethods="AddURLtoItemPanelTable")
	public void ItemPanelTableAttachmentsUpdateURL() throws Exception {
		Iteminstance.updateItemPanelURLAttachment("Table", "Google", "https://www.oracle.com", "Oracle", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 92, description = "Download File to Item Panel Table View", enabled = true,dependsOnMethods="ItemPanelTableAttachmentsUpdateFile")
	public void ItemPanelTableAttachmentsDownloadFile() throws Exception {
		Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		Iteminstance.downloadItemPanelFileAttachment("ItemTableUpdate.zip", attachmentinstanceDriver);
	}	
	
//*****************************************************************************************************************************************************************
	@Test(priority = 93, description = "Download Text to Item Panel Table View", enabled = true,dependsOnMethods="ItemPanelTableAttachmentsUpdateText")
	public void ItemPanelTableAttachmentsDownloadText() throws Exception {
		
		Iteminstance.downloadItemPanelTextAttachment(TextUpdatedTitle, attachmentinstanceDriver);
	}	
	
//**************************************************************************************************************************************************************
	
	@Test(priority = 94, description = "Download URL to Item Panel Table View", enabled = true,dependsOnMethods="ItemPanelTableAttachmentsUpdateURL")
	public void ItemPanelTableAttachmentsDownloadURL() throws Exception {
		Iteminstance.downloadItemPanelURLAttachment("https://www.oracle.com", "Oracle", attachmentinstanceDriver);
	}	
	
//****************************************************************************************************************************************************************
	@Test(priority = 95, description = "Delete File to Item Panel Table View", enabled = true,dependsOnMethods="ItemPanelTableAttachmentsUpdateFile")
	public void ItemPanelTableAttachmentsDeleteFile() throws Exception {
		Iteminstance.deleteItemPanelFileAttachment("ItemTableUpdate.zip", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 96, description = "Delete Text to Item Panel Table View", enabled = true,dependsOnMethods="ItemPanelTableAttachmentsUpdateText")
	public void ItemPanelTableAttachmentsDeleteText() throws Exception {
		Iteminstance.deleteItemPanelTextAttachment(TextUpdatedTitle, attachmentinstanceDriver);
		
	}
	
//**************************************************************************************************************************************************************
	@Test(priority = 97, description = "Delete URL to Item Panel Table View", enabled = true,dependsOnMethods="ItemPanelTableAttachmentsUpdateURL")
	public void ItemPanelTableAttachmentsDeleteURL() throws Exception {
		Iteminstance.deleteItemPanelURLAttachment("Oracle", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 98, description = "Adding File-URL to Item Panel Table View", enabled = true,dependsOnMethods="CreateItem")
	public void ItemPanelTableAttachmentsAddFileURL() throws Exception {
		Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		Iteminstance.clickOnItemPanelAddAttachments(attachmentinstanceDriver);
		Iteminstance.itemPanelURLAttach("Table", "file:/C:/Users/ashipare/Desktop/Personal/Goals.pdf", "fileURLTable", attachmentinstanceDriver);
		Iteminstance.deleteItemPanelURLAttachment("fileURLTable", attachmentinstanceDriver);
		Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
	}	
		


//***************************************************************************************************************************************************************	
//Create Item - Attachment List View
//***************************************************************************************************************************************************************
	@Test(priority = 99, description = "Attach file to Item Panel List View", enabled = true,dependsOnMethods="CreateItem")
	public void AddFiletoItemPanelList() throws Exception {
		Iteminstance.clickOnAttachmentsTab(attachmentinstanceDriver);
		Iteminstance.clickOnItemPanelListView(attachmentinstanceDriver);
		Iteminstance.itemPanelFileAttach("List", "ItemList.docx", attachmentinstanceDriver);
	}	
	
//************************************************************************************************************************************************************
	@Test(priority = 100, description = "Attach Text to Item Panel List View", enabled = true,dependsOnMethods="CreateItem")
	public void AddTexttoItemPanelList() throws Exception {
		//String textTitle = "TextTitle"+CommonUtils.uniqueId();
		Iteminstance.itemPanelTextAttach("List", "Item List Text", "ItemListText", attachmentinstanceDriver);
	}
	
//*****************************************************************************************************************************************************************
	@Test(priority = 101, description = "Attach URL to Item Panel List View", enabled = true,dependsOnMethods="CreateItem")
	public void AddURLtoItemPanelList() throws Exception {
		Iteminstance.itemPanelURLAttach("List", "https://www.edx.org", "edx", attachmentinstanceDriver);
	
	}
	
//***************************************************************************************************************************************************************	
	
	@Test(priority = 102, description = "Updating File to Item Panel List View", enabled = true,dependsOnMethods="AddFiletoItemPanelList")
	public void ItemPanelListAttachmentsUpdateFile() throws Exception {
		Iteminstance.updateItemPanelFileAttachment("List", "ItemList.docx", "ItemListUpdate.zip", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************
	@Test(priority = 103, description = "Updating Text to Item Panel List View", enabled = true,dependsOnMethods="AddTexttoItemPanelList")
	public void ItemPanelListAttachmentsUpdateText() throws Exception {
		TextUpdatedTitle = "TextItemList"+CommonUtils.uniqueId();
		Iteminstance.updateItemPanelTextAttachment("List", "ItemListText", "New Item List Text", TextUpdatedTitle, attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 104, description = "Updating URL to Item Panel List View", enabled = true,dependsOnMethods="AddURLtoItemPanelList")
	public void ItemPanelListAttachmentsUpdateURL() throws Exception {
		Iteminstance.updateItemPanelURLAttachment("List", "edx", "https://www.coursera.org", "Coursera", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 105, description = "Download File to Item Panel List View", enabled = true,dependsOnMethods="ItemPanelListAttachmentsUpdateFile")
	public void ItemPanelListAttachmentsDownloadFile() throws Exception {
		Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		Iteminstance.downloadItemPanelFileAttachment("ItemListUpdate.zip", attachmentinstanceDriver);
	}	
	
//*****************************************************************************************************************************************************************
	@Test(priority = 106, description = "Download Text to Item Panel List View", enabled = true,dependsOnMethods="ItemPanelListAttachmentsUpdateText")
	public void ItemPanelListAttachmentsDownloadText() throws Exception {
		
		Iteminstance.downloadItemPanelTextAttachment(TextUpdatedTitle, attachmentinstanceDriver);
	}	
	
//**************************************************************************************************************************************************************
	
	@Test(priority = 107, description = "Download URL to Item Panel List View", enabled = true,dependsOnMethods="ItemPanelListAttachmentsUpdateURL")
	public void ItemPanelListAttachmentsDownloadURL() throws Exception {
		Iteminstance.downloadItemPanelURLAttachment("https://www.coursera.org", "Coursera", attachmentinstanceDriver);
	}	
	
//****************************************************************************************************************************************************************
	@Test(priority = 108, description = "Delete File to Item Panel List View", enabled = true,dependsOnMethods="ItemPanelListAttachmentsUpdateFile")
	public void ItemPanelListAttachmentsDeleteFile() throws Exception {
		Iteminstance.deleteItemPanelFileAttachment("ItemListUpdate.zip", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 109, description = "Delete Text to Item Panel List View", enabled = true,dependsOnMethods="ItemPanelListAttachmentsUpdateText")
	public void ItemPanelListAttachmentsDeleteText() throws Exception {
		Iteminstance.deleteItemPanelTextAttachment(TextUpdatedTitle, attachmentinstanceDriver);
		
	}
	
//**************************************************************************************************************************************************************
	@Test(priority = 110, description = "Delete URL to Item Panel List View", enabled = true,dependsOnMethods="ItemPanelListAttachmentsUpdateURL")
	public void ItemPanelListAttachmentsDeleteURL() throws Exception {
		Iteminstance.deleteItemPanelURLAttachment("Coursera", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 111, description = "Adding File-URL to Item Panel List View", enabled = true,dependsOnMethods="CreateItem")
	public void ItemPanelListAttachmentsAddFileURL() throws Exception {
		Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		Iteminstance.clickOnItemPanelAddAttachments(attachmentinstanceDriver);
		Iteminstance.itemPanelURLAttach("List", "file:/C:/Users/ashipare/Desktop/Personal/Hyd.pdf", "fileURLList", attachmentinstanceDriver);
		Iteminstance.deleteItemPanelURLAttachment("fileURLList", attachmentinstanceDriver);
		Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
	}	
		

	
//***************************************************************************************************************************************************************	
//Create Item - Attachment Grid View
//***************************************************************************************************************************************************************
	@Test(priority = 112, description = "Attach file to Item Panel Grid View", enabled = true,dependsOnMethods="CreateItem")
	public void AddFiletoItemPanelGrid() throws Exception {
		Iteminstance.clickOnAttachmentsTab(attachmentinstanceDriver);
		Iteminstance.clickOnItemPanelGridView(attachmentinstanceDriver);
		Iteminstance.itemPanelFileAttach("Grid", "ItemGrid.docx", attachmentinstanceDriver);
	}	
	
//************************************************************************************************************************************************************
	@Test(priority = 113, description = "Attach Text to Item Panel Grid View", enabled = true,dependsOnMethods="CreateItem")
	public void AddTexttoItemPanelGrid() throws Exception {
		//CommonUtils.PageRefresh(attachmentinstanceDriver);
		//String textTitle = "TextTitle"+CommonUtils.uniqueId();
		Iteminstance.itemPanelTextAttach("Grid", "Item Grid Text", "ItemGridText", attachmentinstanceDriver);
	}
	
//*****************************************************************************************************************************************************************
	@Test(priority = 114, description = "Attach URL to Item Panel Grid View", enabled = true,dependsOnMethods="CreateItem")
	public void AddURLtoItemPanelGrid() throws Exception {
		//CommonUtils.PageRefresh(attachmentinstanceDriver);
		Iteminstance.itemPanelURLAttach("Grid", "https://www.ted.com", "Ted", attachmentinstanceDriver);
	
	}
	
//***************************************************************************************************************************************************************	
	
	@Test(priority = 115, description = "Updating File to Item Panel Grid View", enabled = true,dependsOnMethods="AddFiletoItemPanelGrid")
	public void ItemPanelGridAttachmentsUpdateFile() throws Exception {
		Iteminstance.updateItemPanelFileAttachment("Grid", "ItemGrid.docx", "ItemGridUpdate.zip", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************
	@Test(priority = 116, description = "Updating Text to Item Panel Grid View", enabled = true,dependsOnMethods="AddTexttoItemPanelGrid")
	public void ItemPanelGridAttachmentsUpdateText() throws Exception {
		TextUpdatedTitle = "TextItemGrid"+CommonUtils.uniqueId();
		Iteminstance.updateItemPanelTextAttachment("Grid", "ItemGridText", "New Item Grid Text", TextUpdatedTitle, attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 117, description = "Updating URL to Item Panel Grid View", enabled = true,dependsOnMethods="AddURLtoItemPanelGrid")
	public void ItemPanelGridAttachmentsUpdateURL() throws Exception {
		Iteminstance.updateItemPanelURLAttachment("Grid", "Ted", "https://www.wikipedia.org", "Wikipedia", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 118, description = "Download File to Item Panel Grid View", enabled = true,dependsOnMethods="ItemPanelGridAttachmentsUpdateFile")
	public void ItemPanelGridAttachmentsDownloadFile() throws Exception {
		Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		Iteminstance.downloadItemPanelFileAttachment("ItemGridUpdate.zip", attachmentinstanceDriver);
	}	
	
//*****************************************************************************************************************************************************************
	@Test(priority = 119, description = "Download Text to Item Panel Grid View", enabled = true,dependsOnMethods="ItemPanelGridAttachmentsUpdateText")
	public void ItemPanelGridAttachmentsDownloadText() throws Exception {
		
		Iteminstance.downloadItemPanelTextAttachment(TextUpdatedTitle, attachmentinstanceDriver);
	}	
	
//**************************************************************************************************************************************************************
	
	@Test(priority = 120, description = "Download URL to Item Panel Grid View", enabled = true,dependsOnMethods="ItemPanelGridAttachmentsUpdateURL")
	public void ItemPanelGridAttachmentsDownloadURL() throws Exception {
		Iteminstance.downloadItemPanelURLAttachment("https://www.wikipedia.org", "Wikipedia", attachmentinstanceDriver);
	}	
	
//****************************************************************************************************************************************************************
	@Test(priority = 121, description = "Delete File to Item Panel Grid View", enabled = true,dependsOnMethods="ItemPanelGridAttachmentsUpdateFile")
	public void ItemPanelGridAttachmentsDeleteFile() throws Exception {
		Iteminstance.deleteItemPanelFileAttachment("ItemGridUpdate.zip", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 122, description = "Delete Text to Item Panel Grid View", enabled = true,dependsOnMethods="ItemPanelGridAttachmentsUpdateText")
	public void ItemPanelGridAttachmentsDeleteText() throws Exception {
		Iteminstance.deleteItemPanelTextAttachment(TextUpdatedTitle, attachmentinstanceDriver);
		
	}
	
//**************************************************************************************************************************************************************
	@Test(priority = 123, description = "Delete URL to Item Panel Grid View", enabled = true,dependsOnMethods="ItemPanelGridAttachmentsUpdateURL")
	public void ItemPanelGridAttachmentsDeleteURL() throws Exception {
		Iteminstance.deleteItemPanelURLAttachment("Wikipedia", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 124, description = "Adding File-URL to Item Panel Grid View", enabled = true,dependsOnMethods="CreateItem")
	public void ItemPanelGridAttachmentsAddFileURL() throws Exception {
		Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		Iteminstance.clickOnItemPanelAddAttachments(attachmentinstanceDriver);
		Iteminstance.itemPanelURLAttach("Grid", "file:/C:/Users/ashipare/Desktop/Personal/Blr.pdf", "fileURLGrid", attachmentinstanceDriver);
		Iteminstance.deleteItemPanelURLAttachment("fileURLGrid", attachmentinstanceDriver);
		Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
	}	

//*************************************************************************************************************************
		
	@Test(priority = 125, description = "Save the Item", enabled = true,dependsOnMethods="CreateItem")
		public void SaveItem() throws Exception {
		try {
			Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		} catch (Exception e) {
			CommonUtils.PageRefresh(attachmentinstanceDriver);
			Iteminstance.clickOnItemSaveButton(attachmentinstanceDriver);
		}
		
	}

	
//***************************************************************************************************************************************************************		
// manage Trading Partner Items
//***************************************************************************************************************************************************************	
	//@Test(priority = 120, description = "Validate Manage Trading Partner Items Attachments through ESS Jobs", enabled = true)
	public void manageTradingPartnerItemsAttachment() throws Exception {
		
		//aLoginInstance.logout(attachmentinstanceDriver);
		//Log.info("log out of the application");
		//aLoginInstance.login("pimqa", "Welcome1", attachmentinstanceDriver);
		//Log.info("Logging into the Application");
		
		ntFInstance.navigateToTask(glbpageInstance.productInformationManagement, attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(attachmentinstanceDriver);
		Log.info("Clicked on Product Information Management.");
		AttachmentCommonUtils.waitTillLoad(40, attachmentinstanceDriver);
		
		//ContentServerInstance.navigateToContentServer(attachmentinstanceDriver);
		//String content_ID = ContentServerInstance.newCheckIn("Sample Title", "Sample Desc", "7-tt1504320-b.jpg", "FAFusionImportExport", attachmentinstanceDriver);
		//ContentServerInstance.searchContentID(content_ID, attachmentinstanceDriver);
		//ContentServerInstance.validateContentInformation(content_ID, "Sample Title", "Sample Desc", "7-tt1504320-b.jpg", attachmentinstanceDriver);
		//attachmentinstanceDriver.get(GetDriverInstance.EnvironmentName);
		
	}	

//***************************************************************************************************************************************************************	
//FSM Attachment Export Import
//***************************************************************************************************************************************************************		
	@Test(priority = 126, description = "Attachments Export Import-Create Implementation Project", enabled = true)
	public void attachmentFSMExpImpCreateImpProj() throws Exception {
		ipName = "Attachment" + CommonUtils.uniqueId();
		fsmTask1 = "Manage Attachment Categories";
		fsmTask2 = "Manage Attachment Entities";
		
		
		aLoginInstance.logout(attachmentinstanceDriver);
		Log.info("log out of the application");
		aLoginInstance.login("app_impl_consultant", "Welcome1", attachmentinstanceDriver);
		Log.info("Logging into the Application");
		
		CommonUtils.hold(2);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, attachmentinstanceDriver);
		CommonUtils.hold(10);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer, "Click", "",attachmentinstanceDriver);
		CommonUtils.hold(10);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().panelDrawer.click();
		CommonUtils.hold(8);
		CommonUtils.explicitWait(ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject,	"Click", "", attachmentinstanceDriver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().implementationProject.click();
		CommonUtils.hold(8);
		
		taskFlowName = new ArrayList<String>();
        taskFlowName.add(fsmTask1);
        taskFlowName.add(fsmTask2);
        fsmUtil.createImplementationProject(ipName, taskFlowName, "Tasks", attachmentinstanceDriver);
        CommonUtils.PageRefresh(attachmentinstanceDriver);
        fsmAttachmentInstance.navigateToFSMAttachment(ipName, fsmTask1, attachmentinstanceDriver);
        
	}	

//************************************************************************************************************************************************************
	
	@Test(priority = 127, description = "Attach file to FSM Attachments Categories", enabled = true,dependsOnMethods="attachmentFSMExpImpCreateImpProj")
	public void AddFiletoFSMAttacmentCategories() throws Exception {
		fsmAttachmentInstance.fsmFileAttach(fsmTask1, "FSMCatDoc.docx", attachmentinstanceDriver);
	}	
	
//************************************************************************************************************************************************************
	@Test(priority = 128, description = "Attach Text to Attachments Categories", enabled = true,dependsOnMethods="attachmentFSMExpImpCreateImpProj")
	public void AddTexttoFSMAttachmentsCategories() throws Exception {
		TextUpdatedTitle = "TextTitle"+CommonUtils.uniqueId();
		fsmAttachmentInstance.fsmTextAttach(fsmTask1, "Category Text", TextUpdatedTitle, attachmentinstanceDriver);
	}
	
//*****************************************************************************************************************************************************************
	@Test(priority = 129, description = "Attach URL to Attachments Categories", enabled = true,dependsOnMethods="attachmentFSMExpImpCreateImpProj")
	public void AddURLtoFSMAttachmentsCategories() throws Exception {
		fsmAttachmentInstance.fsmURLAttach(fsmTask1, "https://www.google.com", "Google", attachmentinstanceDriver);
	
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 130, description = "Adding File-URL to Attachments Categories", enabled = true,dependsOnMethods="attachmentFSMExpImpCreateImpProj")
	public void AddFileURLtoFSMAttachmentsCategories() throws Exception {
		fsmAttachmentInstance.fsmURLAttach(fsmTask1, "file:/C:/Users/ashipare/Desktop/Personal/Goals.pdf", "fileURL", attachmentinstanceDriver);
	}	
	
//***************************************************************************************************************************************************************	
	
	@Test(priority = 131, description = "Updating File to Attachments Categories", enabled = true,dependsOnMethods="AddFiletoFSMAttacmentCategories")
	public void AttachmentsCategoriesUpdateFile() throws Exception {
		fsmAttachmentInstance.updateFSMFileAttachment(fsmTask1, "FSMCatAttachments.zip", "FSMCatDocUpdate.pdf", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************
	@Test(priority = 132, description = "Updating Text Title to Attachments Categories", enabled = true,dependsOnMethods="AddTexttoFSMAttachmentsCategories")
	public void AttachmentsCategoriesUpdateText() throws Exception {
		
		fsmAttachmentInstance.updateFSMTextAttachment(fsmTask1, "Category Text", "CatText", "CatTextDescNew", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 133, description = "Updating URL to Attachments Categories", enabled = true,dependsOnMethods="AddURLtoFSMAttachmentsCategories")
	public void AttachmentsCategoriesUpdateURL() throws Exception {
		fsmAttachmentInstance.updateFSMURLAttachment(fsmTask1, "https://www.google.com", "GoogleNew", "DescGoogle", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 134, description = "Download File to Attachments Categories", enabled = true,dependsOnMethods="AttachmentsCategoriesUpdateFile")
	public void AttachmentsCategoriesDownloadFile() throws Exception {
		fsmAttachmentInstance.downloadFSMFileAttachment("FSMCatDocUpdate.pdf", attachmentinstanceDriver);
	}	
	
//*****************************************************************************************************************************************************************
	@Test(priority = 135, description = "Download Text to Attachments Categories", enabled = true,dependsOnMethods="AttachmentsCategoriesUpdateText")
	public void AttachmentsCategoriesDownloadText() throws Exception {
		fsmAttachmentInstance.downloadFSMTextAttachment("Category Text", TextUpdatedTitle, attachmentinstanceDriver);
	}	
	
//**************************************************************************************************************************************************************
	
	@Test(priority = 136, description = "Download URL to Attachments Categories", enabled = true,dependsOnMethods="AttachmentsCategoriesUpdateURL")
	public void AttachmentsCategoriesDownloadURL() throws Exception {
		fsmAttachmentInstance.downloadFSMURLAttachment("https://www.google.com", attachmentinstanceDriver);
	}	
	
//****************************************************************************************************************************************************************
	@Test(priority = 137, description = "Delete File to Attachments Categories", enabled = true,dependsOnMethods="AttachmentsCategoriesUpdateFile")
	public void AttachmentsCategoriesDeleteFile() throws Exception {
		fsmAttachmentInstance.deleteFSMAttachment("FSMCatDocUpdate.pdf", attachmentinstanceDriver);
		fsmAttachmentInstance.saveAndCloseAttachmentPopup(attachmentinstanceDriver);
	}
	

//************************************************************************************************************************************************************
	
	@Test(priority = 138, description = "Attach file to FSM Attachments Entities", enabled = true,dependsOnMethods="attachmentFSMExpImpCreateImpProj")
	public void AddFiletoFSMAttacmentEntities() throws Exception {
		fsmAttachmentInstance.clickNotes("Manage Attachment Entities", attachmentinstanceDriver);
		fsmAttachmentInstance.fsmFileAttach(fsmTask2, "FSMEntDoc.docx", attachmentinstanceDriver);
	}	
	
//************************************************************************************************************************************************************
	@Test(priority = 139, description = "Attach Text to Attachments Entities", enabled = true,dependsOnMethods="attachmentFSMExpImpCreateImpProj")
	public void AddTexttoFSMAttachmentsEntities() throws Exception {
		TextUpdatedTitle = "TextTitle"+CommonUtils.uniqueId();
		fsmAttachmentInstance.fsmTextAttach(fsmTask2, "Entity Text", TextUpdatedTitle, attachmentinstanceDriver);
	}
	
//*****************************************************************************************************************************************************************
	@Test(priority = 140, description = "Attach URL to Attachments Entities", enabled = true,dependsOnMethods="attachmentFSMExpImpCreateImpProj")
	public void AddURLtoFSMAttachmentsEntities() throws Exception {
		fsmAttachmentInstance.fsmURLAttach(fsmTask2, "https://www.google.com", "Google", attachmentinstanceDriver);
	
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 141, description = "Adding File-URL to Attachments Entities", enabled = true,dependsOnMethods="attachmentFSMExpImpCreateImpProj")
	public void AttachmentsEntitiesAddFileURL() throws Exception {
		fsmAttachmentInstance.fsmURLAttach(fsmTask2, "file:/C:/Users/ashipare/Desktop/Personal/Goals.pdf", "fileURL", attachmentinstanceDriver);
	}	
	
//***************************************************************************************************************************************************************	
	
	@Test(priority = 142, description = "Updating File to Attachments Entities", enabled = true,dependsOnMethods="AddFiletoFSMAttacmentEntities")
	public void AttachmentsEntitiesUpdateFile() throws Exception {
		fsmAttachmentInstance.updateFSMFileAttachment(fsmTask2, "FSMEntAttachments.zip", "FSMEntDocUpdate.pdf", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************
	@Test(priority = 143, description = "Updating Text Title to Attachments Entities", enabled = true,dependsOnMethods="AddTexttoFSMAttachmentsEntities")
	public void AttachmentsEntitiesUpdateText() throws Exception {
		
		fsmAttachmentInstance.updateFSMTextAttachment(fsmTask2, "Entity Text", "EntText", "EntTextDescNew", attachmentinstanceDriver);
	}

//***************************************************************************************************************************************************************	
	@Test(priority = 144, description = "Updating URL to Attachments Entities", enabled = true,dependsOnMethods="AddURLtoFSMAttachmentsEntities")
	public void AttachmentsEntitiesUpdateURL() throws Exception {
		fsmAttachmentInstance.updateFSMURLAttachment(fsmTask2, "https://www.google.com", "GoogleNew", "DescGoogle", attachmentinstanceDriver);
	}
	
//***************************************************************************************************************************************************************	
	@Test(priority = 145, description = "Download File to Attachments Entities", enabled = true,dependsOnMethods="AttachmentsEntitiesUpdateFile")
	public void AttachmentsEntitiesDownloadFile() throws Exception {
		fsmAttachmentInstance.downloadFSMFileAttachment("FSMEntDocUpdate.pdf", attachmentinstanceDriver);
	}	
	
//*****************************************************************************************************************************************************************
	@Test(priority = 146, description = "Download Text to Attachments Entities", enabled = true,dependsOnMethods="AttachmentsEntitiesUpdateText")
	public void AttachmentsEntitiesDownloadText() throws Exception {
		fsmAttachmentInstance.downloadFSMTextAttachment("Entity Text", TextUpdatedTitle, attachmentinstanceDriver);
	}	
	
//**************************************************************************************************************************************************************
	
	@Test(priority = 147, description = "Download URL to Attachments Entities", enabled = true,dependsOnMethods="AttachmentsEntitiesUpdateURL")
	public void AttachmentsEntitiesDownloadURL() throws Exception {
		fsmAttachmentInstance.downloadFSMURLAttachment("https://www.google.com", attachmentinstanceDriver);
	}	
	
//****************************************************************************************************************************************************************
	@Test(priority = 148, description = "Delete File to Attachments Entities", enabled = true,dependsOnMethods="AttachmentsEntitiesUpdateFile")
	public void AttachmentsEntitiesDeleteFile() throws Exception {
		fsmAttachmentInstance.deleteFSMAttachment("FSMEntDocUpdate.pdf", attachmentinstanceDriver);
		fsmAttachmentInstance.saveAndCloseAttachmentPopup(attachmentinstanceDriver);
		ntFInstance.getSAMInstance().getCommonUtilsDefsInstance().ipDoneBtn.click();
		CommonUtils.hold(5);
	}

//*********************************************************************************************************************************************************************************
	
	@Test(priority = 149, description = "Attachments FSM Export Import", enabled = true,dependsOnMethods="attachmentFSMExpImpCreateImpProj")
	public void attachmentFSMExportImport() throws Exception {
		
		fsmUtil.createConfigurationProject(ipName, attachmentinstanceDriver);
		fsmUtil.downloadPackage(attachmentinstanceDriver); CommonUtils.hold(5);
		fsmUtil.uploadConfigurationPackage(ipName, attachmentinstanceDriver);
		fsmUtil.importSetupData(ipName, attachmentinstanceDriver);
		CommonUtils.hold(5);
		
	}	

	
//***************************************************************************************************************************************************************		
// Transfer HCM
//***************************************************************************************************************************************************************	
	@Test(priority = 150, description = "Navigate to Transfer HCM Attachments section", enabled = true)
	public void TransferHcmNavigation() throws Exception {
		
		//aLoginInstance.logout(attachmentinstanceDriver);
		Log.info("log out of PIMQA");
		//aLoginInstance.login("hcm_user1", "Welcome1", attachmentinstanceDriver);
		Log.info("Logging into the Application");
		
		//Hcminstance.setHcmAttachmentsProfileValues(attachmentinstanceDriver);
		
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
	
	//@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {

		CommonUtils.hold(2);
		// CommonUtils.logout();
		aLoginInstance.logout(attachmentinstanceDriver);
		CommonUtils.hold(2);
		attachmentinstanceDriver.quit();
	}
//*****************************************************************************************************************************************************************	

}
