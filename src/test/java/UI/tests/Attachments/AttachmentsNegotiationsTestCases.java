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

public class AttachmentsNegotiationsTestCases extends GetDriverInstance {

	private WebDriver attachmentinstanceDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private NegotiationsUtils Negotinstance;
	
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
		Negotinstance= new NegotiationsUtils(attachmentinstanceDriver);
		aLoginInstance.login(username, password, attachmentinstanceDriver);
		Log.info("Logging into the Application");
		// CommonUtils.waitForJStoLoad(attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(100, attachmentinstanceDriver);
		CommonUtils.hold(10);
		
	}

//*************************************************************************************************************************
	//Manage Negotiations 
//*************************************************************************************************************************	
	@Test(priority = 46, description = "Managing Negotiations", enabled = true)
	public void CreateNegotiations() throws Exception {
		
		//aLoginInstance.logout(attachmentinstanceDriver);
		//Log.info("log out of CONTRACT MANAGER");
		//aLoginInstance.login("cvbuyer01", "Welcome1", attachmentinstanceDriver);
		//Log.info("Logging into the Application");
		
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
