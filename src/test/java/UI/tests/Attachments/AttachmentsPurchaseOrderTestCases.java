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

public class AttachmentsPurchaseOrderTestCases extends GetDriverInstance {

	private WebDriver attachmentinstanceDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private PurchaseOrderUtils Orderinstance;
	
	private String TextUpdatedTitle=null;
	
//**********************************************Test Cases**************************************************
	@Parameters({ "SalesUser", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {

		attachmentinstanceDriver = getDriverInstanceObject();
		attachmentinstanceDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		aLoginInstance = new ApplicationLogin(attachmentinstanceDriver);
		ntFInstance = new NavigationTaskFlows(attachmentinstanceDriver);
		nMEInstance = new NavigatorMenuElements(attachmentinstanceDriver);
		Orderinstance= new PurchaseOrderUtils(attachmentinstanceDriver);
		aLoginInstance.login(username, password, attachmentinstanceDriver);
		Log.info("Logging into the Application");
		// CommonUtils.waitForJStoLoad(attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(100, attachmentinstanceDriver);
		CommonUtils.hold(10);
		
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
		//attachmentinstanceDriver.manage().timeouts().implicitlyWait(4,TimeUnit.SECONDS);
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
