package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchaseOrderPage {

	public PurchaseOrderPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}


	@FindBy(xpath = "//img[contains(@id,'_PrcPoPurchaseOrdersWorkarea_itemNode__FndTasksList::icon')]")
	public static WebElement tasksPanelDrawerIcon;

	@FindBy(xpath = "//a[contains(@id,'RAtl5')]")
	public static WebElement createOrder;

	@FindBy(xpath = "//div[contains(@id,'pw1::_ttxt') and text()='Create Order']")
	public static WebElement createOrderPopupWindowtitle;
	
	@FindBy(xpath = "//button[contains(@id,'commandButton1')]")
	public static WebElement createOrderCreateButton;
	
	/*
	@FindBy(xpath = "//input[contains(@id,'_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:Supplier::content')]")
	public static WebElement createOrderSupplierInputText;

	@FindBy(xpath = "//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:Supplier::lovIconId']")
	public static WebElement createOrderSupplierSearchIcon;

	@FindBy(xpath = "//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:Supplier::lovDialogId::_ttxt']")
	public static WebElement supplierSearchDialog;
	//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:Supplier::_afrLovInternalQueryId:value00::content']
	@FindBy(xpath = "//td[2]/table/tbody/tr/td/span/input")
	public static WebElement supplierSearchInputText;

	@FindBy(xpath = "//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:Supplier::_afrLovInternalQueryId::search']")
	public static WebElement supplierSearchButton;

	@FindBy(xpath = "//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:Supplier::_afrLovInternalQueryId:criteria']")
	public static WebElement supplierSearchResultTableVIEW;

	@FindBy(xpath = "//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:Supplier_afrLovInternalTableId::db']/table/tbody/tr/td")
	public static WebElement supplierSearchResultTableRow;

	@FindBy(xpath = "//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:Supplier::lovDialogId::ok']")
	public static WebElement supplierSearchOKButton;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:SupplierSite::content\"]")
	public static WebElement createOrderSupplierSiteInputText;

	@FindBy(xpath = "//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:SupplierSite::lovIconId']")
	public static WebElement createOrderSupplierSiteDropDownIcon;

	@FindBy(xpath = "//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:SupplierSite::dropdownPopup::popupsearch']")
	public static WebElement createOrderSupplierSiteDropDownSearch;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:SupplierSite::lovDialogId::_ttxt\"]")
	public static WebElement supplierSiteSearchSelectDialogTitle;

	@FindBy(xpath = "//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:SupplierSite::_afrLovInternalQueryId:value00::content']")
	public static WebElement supplierSiteSearchSelectInputText;

	@FindBy(xpath = "//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:SupplierSite::_afrLovInternalQueryId::search']")
	public static WebElement supplierSiteSearchButton;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:SupplierSite_afrLovInternalTableId:0:_afrColChild0\"]")
	public static WebElement supplierSiteSearchResultTableRow;

	@FindBy(xpath = "//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:_FOTRaT:0:dynam1:0:SupplierSite::lovDialogId::ok']")
	public static WebElement supplierSiteSearchOK;
	
	
	

	@FindBy(xpath = "//textarea[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:inputText5::content']")
	public static WebElement purchaseOrderDescription;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:lineDetailItemId::disAcr\"]")
	public static WebElement purchaseOrderLinesTab;

	@FindBy(xpath = "//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:_vw']/div/table/tbody/tr/td[3]/div")
	public static WebElement purchaseOrderLinesView;

	@FindBy(xpath = "//tr[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:_clmns']/td[2]")
	public static WebElement purchaseOrderLinesViewColumns;

	@FindBy(xpath = "//tr[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:_shwAll']/td[2]")
	public static WebElement purchaseOrderLinesViewColumnsShowAll;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:create::icon\"]")
	public static WebElement purchaseOrderLinesAddIcon;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:Item::content\"]")
	public static WebElement purchaseOrderLinesItemInputText;

	@FindBy(xpath = "//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:Item::lovIconId']")
	public static WebElement purchaseOrderLinesItemSearchIcon;

	@FindBy(xpath = "//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:Item::_afrLovInternalQueryId:value00::content']")
	public static WebElement linesItemSearchSelectItemInputText;

	@FindBy(xpath = "//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:Item::_afrLovInternalQueryId::search']")
	public static WebElement linesItemSearchSelectItemSearchButton;

	@FindBy(xpath = "//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:Item::_afrLovInternalQueryId:criteria']")
	public static WebElement linesItemSearchSelectItemVIEW;

	@FindBy(xpath = "//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:Item_afrLovInternalTableId::db']/table/tbody/tr/td")
	public static WebElement linesItemSearchSelectItemSearchResultTableRow;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:Item::lovDialogId::ok\"]")
	public static WebElement linesItemSearchSelectItemOK;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:s5:Quantity::content\"]")
	public static WebElement purchaseOrderLinesItemQuantity;

	@FindBy(xpath = "//*[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:UnitPrice::content']")
	public static WebElement purchaseOrderLinesItemPrice;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:NeedByDate::content\"]")
	public static WebElement purchaseOrderNeedByDate;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:PromisedDate::content\"]")
	public static WebElement purchaseOrderPromisedDate;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:A2:icladd\"]")
	public static WebElement purchaseOrderLinesAttachmentAddIcon;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:A2:popApplicationsTable:_ATp:popAttachmentTable:0:popDatatypeCodeChoiceListIDNew::content\"]")
	public static WebElement purchaseOrderLinesAttachmentType;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:A2:popApplicationsTable:_ATp:popAttachmentTable:0:socPopCategory::content\"]")
	public static WebElement purchaseOrderLinesAttachmentsCategoryLov;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:A2:popApplicationsTable:_ATp:popAttachmentTable:0:itPopFileText::content\"]")
	public static WebElement purchaseOrderLinesAttachmentTextTypeDetails;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:A2:popApplicationsTable:_ATp:popAttachmentTable:0:popTitleInputText::content\"]")
	public static WebElement purchaseOrderLinesAttachmentTitle;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:A2:popApplicationsTable:_ATp:popAttachmentTable:0:popDescriptionInputText::content\"]")
	public static WebElement purchaseOrderLinesAttachmentDescription;

	@FindBy(xpath = "//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:AT1:_ATp:Lines:0:A2:dc_cb1']")
	public static WebElement poLinesAttachmentOKbutton;

	@FindBy(xpath = "//*[contains(@class,'inputFile_content')]")
	public static WebElement purchaseOrderLinesAttachmentBrowse;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:APsv2\"]")
	public static WebElement purchaseOrderSave;

	@FindBy(xpath = "//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_PurchaseOrders:0:MAt2:0:AP1:SPsb2\"]")
	public static WebElement purchaseOrderSubmit;

	@FindBy(xpath = "//button[@id='_FOd1::msgDlg::cancel']")
	public static WebElement confirmationPopupOK;
	
	*/
	
	@FindBy(xpath = "//a[contains(@id,'showDetailItem6::disAcr')]")
	public static WebElement notesAndAttachmentsTab;
	
	@FindBy(xpath = "//img[contains(@id,'AP1:A1:clLAdds::icon')]")
	public static WebElement manageAttachments;
	
	@FindBy(xpath = "//div[contains(@id,'pglAAdlg')]")
	public static WebElement attachmentDiagBox;
	
	@FindBy(xpath="//select[contains(@name,'popDatatypeCodeChoiceListIDNew')]")
	public static WebElement attachmentType;
	
	@FindBy(xpath="//select[contains(@id,'socPopCategory::content')]")
	public static WebElement attachmentCategory;
	
	@FindBy(xpath = "//input[contains(@id,'ifPopup::content')]")
	public static WebElement attachmentBrowse;
	
	@FindBy(xpath="//input[contains(@id,'popTitleInputText::content')]")
	public static WebElement attachmentTitle;
	
	@FindBy(xpath="//input[contains(@id,'popDescriptionInputText::content')]")
	public static WebElement attachmentDesc;
	
	@FindBy(xpath="//button[contains(@id,'dc_cb1')]")
	public static WebElement attachmentOKButton;
	
	
	@FindBy(xpath="//textarea[contains(@id,'itPopFileText::content')]")
	public static WebElement textarea;
	
	@FindBy(xpath="//span[contains(@id,'lastAttachedTitle')]")
	public static WebElement lastAttachedText;
	
	@FindBy(xpath="//span[contains(@id,'otReviewText')]")
	public static WebElement ReviewText;
	
	@FindBy(xpath="//select[contains(@id,'popDatatypeCodeChoiceListIDNew::content')]")
	public static WebElement typeSelect;
	
	@FindBy(xpath="//input[contains(@id,'atapnru::content')]")
	public static WebElement urlField;
	
	@FindBy(xpath="//a[contains(@id,'lastAttachedURL')]")
	public static WebElement lastAttachedURL;
	
	@FindBy(xpath="//a[contains(@id,'attachmentMoreLink')]")
	public static WebElement moreAttachmentLink;
	
	@FindBy(xpath = "//img[contains(@id,'popApplicationsTable:_ATp:delete::icon')]")
	public static WebElement attachmentDeleteIcon;
	
	@FindBy(xpath = "//button[contains(@id,'confirm')]")
	public  static WebElement warningYesBtn;
	
	@FindBy(xpath = "//td[contains(@id,'ifPopup::dlgCont::contentContainer')]/descendant::input[contains(@id,'ifPopup::content')]")
	public  static WebElement UpdateBrowse;
	
	@FindBy(xpath = "//button[contains(@id,'ifPopup::dlgCont::ok')]")
	public  static WebElement updateFileOkBtn;
	
	@FindBy(xpath = "//a[contains(@aria-describedby,'APsv2_afrdescBy')]")
	public  static WebElement purchaseOrderSaveBtn;
	
	

}