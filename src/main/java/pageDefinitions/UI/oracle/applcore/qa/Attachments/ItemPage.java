package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ItemPage {

	public ItemPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//img[contains(@id,'a1:clLAdds::icon')]")
	public static WebElement manageAttachments;
	
	@FindBy(xpath = "//div[contains(@id,'pglAAdlg')]")
	public static WebElement attachmentDiagBox;
	
	@FindBy(xpath="//select[contains(@name,'popDatatypeCodeChoiceListIDNew')]")
	public static WebElement attachmentType;
	
	//@FindBy(xpath="//select[contains(@id,'socPopCategory::content')]")
	//public static WebElement attachmentCategory;
	
	@FindBy(xpath = "//input[contains(@id,'ifPopup::content')]")
	public static WebElement attachmentBrowse;
	
	@FindBy(xpath="//input[contains(@id,'popTitleInputText::content')]")
	public static WebElement attachmentTitle;
	
	@FindBy(xpath="//input[contains(@id,'popDescriptionInputText::content')]")
	public static WebElement attachmentDesc;
	
	@FindBy(xpath="//button[contains(@id,'dc_cb1')]")
	public static WebElement attachmentOKButton;
	
	
	//@FindBy(xpath="//textarea[contains(@id,'itPopFileText::content')]")
	//public static WebElement textarea;
	
	//@FindBy(xpath="//span[contains(@id,'lastAttachedTitle')]")
	//public static WebElement lastAttachedText;
	
	//@FindBy(xpath="//span[contains(@id,'otReviewText')]")
	//public static WebElement ReviewText;
	
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
	
	@FindBy(xpath = "//button[contains(@id,'a1:confirm')]")
	public  static WebElement warningYesBtn;
	
	@FindBy(xpath = "//td[contains(@id,'ifPopup::dlgCont::contentContainer')]/descendant::input[contains(@id,'ifPopup::content')]")
	public  static WebElement UpdateBrowse;
	
	@FindBy(xpath = "//button[contains(@id,'ifPopup::dlgCont::ok')]")
	public  static WebElement updateFileOkBtn;
	
	@FindBy(xpath = "//a[contains(@aria-describedby,'csavebtn_afrdescBy')]")
	public  static WebElement itemSaveBtn;
	
//***************************************************************************************************************************************************************
	
	@FindBy(xpath = "//a[contains(@id,'pt1:ap1:sattch::disAcr')]")
	public  static WebElement attachmentsTab;
	
	@FindBy(xpath = "//img[contains(@id,'ctbTable::icon')]")
	public  static WebElement tableViewIcon;
	
	@FindBy(xpath = "//img[contains(@id,'ctbList::icon')]")
	public  static WebElement listViewIcon;
	
	@FindBy(xpath = "//img[contains(@id,'ctbGrid::icon')]")
	public  static WebElement gridViewIcon;
	
	@FindBy(xpath = "//img[contains(@id,'avgAdd::icon')]")
	public  static WebElement itemPanelAddAttachments;
	
	//@FindBy(xpath = "//button[contains(@id,'addAttach2:cbConfirm')]")
	//public static WebElement okButton;

	@FindBy(xpath = "//input[contains(@id,'dciAdd:sfadd:ifAddFile::if')]")
	public static WebElement ItemPanelbrowse;

	@FindBy(xpath = "//select[contains(@id,'socAddType::content')]")
	public static WebElement ItemPaneltypeSelect;

	//@FindBy(xpath = "//li[contains(text(),'URL')]")
	//public static WebElement typeSelectURL;

	@FindBy(xpath = "//input[contains(@id,'itAddLink::content')]")
	public static WebElement ItemPanelURLFieldLink;
	
	@FindBy(xpath = "//input[contains(@id,'itAddTitle::content')]")
	public static WebElement ItemPanelTitleField;
	
	@FindBy(xpath = "//button[contains(text(),'Add')]")
	public static WebElement ItemPanelUrlAdd;
	
	@FindBy(xpath="//textarea[contains(@id,'rteAddText::content')]")
	public static WebElement ItemPanelTextarea;

	//@FindBy(xpath = "//button[contains(@id,'pt1:object-subtitle:cb5')]")
	//public static WebElement saveandcloseSR;
	
	@FindBy(xpath = "//input[contains(@id,'dciDtl:sfdtl:ifUpd::content')]")
	public static WebElement ItemPanelUpdateBrowse;
	
	@FindBy(xpath = "//input[contains(@id,'dciDtl:sfdtl:itTitle::content')]")
	public static WebElement ItemPanelAttachmentTitle;
	
	@FindBy(xpath = "//textarea[contains(@id,'dciDtl:sfdtl:itDesc::content')]")
	public static WebElement ItemPanelAttachmentDescription;
	
	@FindBy(xpath = "//button[contains(@id,'dciDtl:sfdtl:cbDtlApp')]")
	public static WebElement ItemPanelUpdateDetailsBtn;
	
	@FindBy(xpath = "//button[contains(@id,'dciDtl:sfdtl:ifUpd::upBtn')]")
	public static WebElement ItemPanelUpdateFileBtn;
	
	@FindBy(xpath = "//input[contains(@id,'dciDtl:sfdtl:otUrl::content')]")
	public static WebElement ItemPanelUpdateURLField;
	
	@FindBy(xpath = "//textarea[contains(@id,'rteText::content')]")
	public static WebElement ItemPanelUpdateTextField;
	
	@FindBy(xpath = "//button[contains(@id,'cnlBtn')]")
	public static WebElement uploadCancelBtn;
	
	@FindBy(xpath = "//span[@class='af_progressIndicator_determinate-filled-icon-style']")
	public static WebElement fileUploadProcessBar;
	
	@FindBy(xpath = "//button[contains(@id,'av1:confirm')]")
	public  static WebElement ItemPanelWarningYesBtn;
	
	@FindBy(xpath = "//th[contains(@id,'dciTable:tblAttach:colTitle')]")
	public  static WebElement titleColumnHeader;

}
