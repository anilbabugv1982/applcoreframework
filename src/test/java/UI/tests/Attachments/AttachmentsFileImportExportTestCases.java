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

public class AttachmentsFileImportExportTestCases extends GetDriverInstance {

	private WebDriver attachmentinstanceDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private FileImportAndExportUtils fileImportExportUIinstance;
	
//**********************************************Test Cases**************************************************
	@Parameters({ "user", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {

		attachmentinstanceDriver = getDriverInstanceObject();
		attachmentinstanceDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		aLoginInstance = new ApplicationLogin(attachmentinstanceDriver);
		ntFInstance = new NavigationTaskFlows(attachmentinstanceDriver);
		nMEInstance = new NavigatorMenuElements(attachmentinstanceDriver);
		fileImportExportUIinstance = new FileImportAndExportUtils(attachmentinstanceDriver);
		aLoginInstance.login(username, password, attachmentinstanceDriver);
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
