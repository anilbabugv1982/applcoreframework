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

public class AttachmentsItemsTestCases extends GetDriverInstance {

	private WebDriver attachmentinstanceDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private GlobalPageTemplate glbpageInstance;
	private RuntimeValidation RuntimeValidationInstance=null;
	private ItemUtils Iteminstance;
	
	private String TextUpdatedTitle=null;
	
//**********************************************Test Cases**************************************************
	@Parameters({ "PimUser", "pwd" })
	@BeforeClass(alwaysRun = true)
	public void startUP(String username, String password) throws Exception {

		attachmentinstanceDriver = getDriverInstanceObject();
		attachmentinstanceDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		aLoginInstance = new ApplicationLogin(attachmentinstanceDriver);
		ntFInstance = new NavigationTaskFlows(attachmentinstanceDriver);
		glbpageInstance = new GlobalPageTemplate(attachmentinstanceDriver);
		RuntimeValidationInstance=new RuntimeValidation(attachmentinstanceDriver);
		Iteminstance= new ItemUtils(attachmentinstanceDriver);
		aLoginInstance.login(username, password, attachmentinstanceDriver);
		Log.info("Logging into the Application");
		// CommonUtils.waitForJStoLoad(attachmentinstanceDriver);
		CommonUtils.waitForPageLoad(100, attachmentinstanceDriver);
		CommonUtils.hold(10);
		
	}

//*************************************************************************************************************************************************************	
// Create Item
//***************************************************************************************************************************************************************	
	@Test(priority = 76, description = "Create Item", enabled = true)
	public void CreateItem() throws Exception {
		
		//aLoginInstance.logout(attachmentinstanceDriver);
		//Log.info("log out of cvbuyer01");
		//aLoginInstance.login("pimqa", "Welcome1", attachmentinstanceDriver);
		//Log.info("Logging into the Application");
		
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
		attachmentinstanceDriver.manage().timeouts().implicitlyWait(4,TimeUnit.SECONDS);
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
