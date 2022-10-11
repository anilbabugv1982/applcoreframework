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
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ItemImportESSJobs;
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

public class AttachmentsManageTradingPartnerItemsTestCases extends GetDriverInstance {

	private WebDriver attachmentinstanceDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private GlobalPageTemplate glbpageInstance;
	private ContentServerUtils ContentServerInstance;
	private ManageTradingPartnerItemsUtils TradingPartnerItemsInstance;
	private ItemImportESSJobs essJobInstance;
	
	
//**********************************************Test Cases**************************************************
	@Parameters({ "PimUser", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {

		attachmentinstanceDriver = getDriverInstanceObject();
		attachmentinstanceDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		aLoginInstance = new ApplicationLogin(attachmentinstanceDriver);
		ntFInstance = new NavigationTaskFlows(attachmentinstanceDriver);
		nMEInstance = new NavigatorMenuElements(attachmentinstanceDriver);
		glbpageInstance = new GlobalPageTemplate(attachmentinstanceDriver);
		ContentServerInstance= new ContentServerUtils(attachmentinstanceDriver);
		TradingPartnerItemsInstance= new ManageTradingPartnerItemsUtils(attachmentinstanceDriver);
		essJobInstance= new ItemImportESSJobs(attachmentinstanceDriver);
		aLoginInstance.login(username, password, attachmentinstanceDriver);
		Log.info("Logging into the Application");
		// CommonUtils.waitForJStoLoad(attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(100, attachmentinstanceDriver);
		CommonUtils.hold(10);
		
	}

//***************************************************************************************************************************************************************		
// manage Trading Partner Items
//***************************************************************************************************************************************************************	
	//@Test(priority = 120, description = "Validate Manage Trading Partner Items Attachments through ESS Jobs", enabled = true)
	public void manageTradingPartnerItemsAttachment() throws Exception {
		String primaryFileName = "7-tt1504320-b.jpg";
		String title = "New Title BP47";
		String desc = "New Description BP47";
		
		String dataFileName = "EgpItemAttachmentsIntf.zip";
		String loadJobName = "Load Interface File for Import";
		String importJobName = "Item Import";
		
		//aLoginInstance.logout(attachmentinstanceDriver);
		//Log.info("log out of the application");
		//aLoginInstance.login("pimqa", "Welcome1", attachmentinstanceDriver);
		//Log.info("Logging into the Application");
		
		ContentServerInstance.navigateToContentServer(attachmentinstanceDriver);
		String content_ID = ContentServerInstance.newCheckIn("Sample Title", "Sample Desc", primaryFileName, "FAFusionImportExport", attachmentinstanceDriver);
		attachmentinstanceDriver.get(GetDriverInstance.EnvironmentName);
		
		essJobInstance.createLoadInterfaceFileForImportESSJob(loadJobName, true, dataFileName, attachmentinstanceDriver);
		essJobInstance.createLoadInterfaceFileForImportESSJob(importJobName, true, "", attachmentinstanceDriver);
		
		
		ntFInstance.navigateToTask(glbpageInstance.productInformationManagement, attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(attachmentinstanceDriver);
		Log.info("Clicked on Product Information Management.");
		AttachmentCommonUtils.waitTillLoad(40, attachmentinstanceDriver);
		TradingPartnerItemsInstance.manageTradingPartnerItems(title, desc, primaryFileName, attachmentinstanceDriver);
		
		ContentServerInstance.navigateToContentServer(attachmentinstanceDriver);
		ContentServerInstance.searchContentID(content_ID, attachmentinstanceDriver);
		ContentServerInstance.validateContentInformation(content_ID, title, desc, primaryFileName, attachmentinstanceDriver);
		attachmentinstanceDriver.get(GetDriverInstance.EnvironmentName);
		
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
