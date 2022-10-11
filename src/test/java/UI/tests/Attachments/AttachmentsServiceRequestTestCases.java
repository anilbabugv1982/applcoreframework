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

public class AttachmentsServiceRequestTestCases extends GetDriverInstance {

	private WebDriver attachmentinstanceDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private ServiceRequestUtils SRinstance;
	
//**********************************************Test Cases**************************************************
	@Parameters({ "user", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {

		attachmentinstanceDriver = getDriverInstanceObject();
		attachmentinstanceDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		aLoginInstance = new ApplicationLogin(attachmentinstanceDriver);
		ntFInstance = new NavigationTaskFlows(attachmentinstanceDriver);
		SRinstance = new ServiceRequestUtils(attachmentinstanceDriver);
		aLoginInstance.login(username, password, attachmentinstanceDriver);
		Log.info("Logging into the Application");
		// CommonUtils.waitForJStoLoad(attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(100, attachmentinstanceDriver);
		CommonUtils.hold(10);
		
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
