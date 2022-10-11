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

public class AttachmentsFSMExportImportTestCases extends GetDriverInstance {

	private WebDriver attachmentinstanceDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private FSMExpImpWrapper fsmUtil = null;
	private FSMExportImportAttachmentUtils fsmAttachmentInstance;
	
	private String TextUpdatedTitle=null;
	private List<String> taskFlowName;
	private String ipName;
	String fsmTask1;
	String fsmTask2;
	
//**********************************************Test Cases**************************************************
	@Parameters({ "user", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {

		attachmentinstanceDriver = getDriverInstanceObject();
		attachmentinstanceDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		aLoginInstance = new ApplicationLogin(attachmentinstanceDriver);
		ntFInstance = new NavigationTaskFlows(attachmentinstanceDriver);
		fsmUtil = new FSMExpImpWrapper(attachmentinstanceDriver);
		fsmAttachmentInstance = new FSMExportImportAttachmentUtils(attachmentinstanceDriver);
		aLoginInstance.login(username, password, attachmentinstanceDriver);
		Log.info("Logging into the Application");
		// CommonUtils.waitForJStoLoad(attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(100, attachmentinstanceDriver);
		CommonUtils.hold(10);
		
	}
//***************************************************************************************************************************************************************	
//FSM Attachment Export Import
//***************************************************************************************************************************************************************		
	@Test(priority = 126, description = "Attachments Export Import-Create Implementation Project", enabled = true)
	public void attachmentFSMExpImpCreateImpProj() throws Exception {
		ipName = "Attachment" + CommonUtils.uniqueId();
		fsmTask1 = "Manage Attachment Categories";
		fsmTask2 = "Manage Attachment Entities";
		
		
		//aLoginInstance.logout(attachmentinstanceDriver);
		//Log.info("log out of the application");
		//aLoginInstance.login("app_impl_consultant", "Welcome1", attachmentinstanceDriver);
		//Log.info("Logging into the Application");
		
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
