package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class Negotiation {

	public Negotiation(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//*[@id=\"_FOpt1:_UISmmLink::icon\"]")
	public static WebElement navigatorFusePlusUiIcon;

	@FindBy(xpath="//*[@id=\"pt1:nv_itemNode_procurement_negotiations\"]")
	public static WebElement navigatorProcurementNegotiations;
	
	@FindBy(xpath="//img[contains(@id,'FOTsdiNegotiationWorkarea_FndTasksList')]")
	public static WebElement negotiationsTasksPanelDrawerIcon;
	
	@FindBy(xpath="//a[contains(@id,'RAtl2')]")
	public static WebElement tasksManageNegotiation;
	
	@FindBy(xpath="//a[contains(@id,'AP1:query::_afrDscl')]")
	public static WebElement searchArrowIcon;
	
	@FindBy(xpath="//input[contains(@name,'AP1:query:value10')]")
	public static WebElement negotiationSearchInputText;
	
	@FindBy(xpath="//button[contains(text(),'Search')]")
	public static WebElement negotiationSearchButton;
	
	@FindBy(xpath="//a[contains(@id,'ATp:result:0:cmdLink2')]")
	public static WebElement searchResultTableNegotiaton;
	
	@FindBy(xpath="//span[contains(@id,'ATp:result:0:outputText4')]")
	public static WebElement searchResultTableNegotiatonTitle;
	
	@FindBy(xpath="//span[contains(@id,'ATp:result:0:outputText11')]")
	public static WebElement searchResultTableNegotiatonType;	
	
	@FindBy(xpath="//img[contains(@id,'ATp:duplicate::icon')]")
	public static WebElement negotiationDuplicateIcon;
	
	@FindBy(xpath="//select[contains(@id,'NegTypeDisplayNameId::content')]")
	public static WebElement duplicateNegotiationNegotioationTypeSOC;
	
	@FindBy(xpath="//button[contains(text(),'Create')]")
	public static WebElement duplicateNegotiationCreateButton;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP3:tbb1:1:commandNavigationItem2')]")
	public static WebElement editNegotiationCoverPageNextTrainBarButton;
	
	//input[contains(@name,'sor1') and @value='1']
	@FindBy(xpath="//label[contains(@for,'sor1:_1')]")
	public static WebElement closeDateDaysAfterRadioButton;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP3:it4::content')]")
	public static WebElement closeDateDaysAfterTextField;
	
	@FindBy(xpath="//img[contains(@id,'clLAdds::icon')]")
	public static WebElement negotiationAttachmentAddIcon;
	
	@FindBy(xpath = "//input[contains(@id,'ifPopup::content')]")
	public static WebElement negotiationAttachmentBrowse;
	
	
	@FindBy(xpath="//select[contains(@name,'popDatatypeCodeChoiceListIDNew')]")
	public static WebElement negotiationAttachmentType;

	@FindBy(xpath="//select[contains(@id,'socPopCategory::content')]")
	public static WebElement negotiationAttachmentCategory;
	
	@FindBy(xpath="//input[contains(@name,'popTitleInputText')]")
	public static WebElement negotiationAttachmentTitle;
	
	@FindBy(xpath="//input[contains(@id,'popDescriptionInputText::content')]")
	public static WebElement negotiationAttachmentDesc;
	
	//@FindBy(xpath="//button[contains(@id,'pt1:AP3:a1:dc_cb1')]")
	@FindBy(xpath="//button[contains(@id,'dc_cb1')]")
	public static WebElement negotiationAttachmentOKButton;
	
	@FindBy(xpath="//tr[contains(@id,'FOSritemNode_procurement_negotiations:0:MAt3:1:pt1:AP3:responseLayoutNameId::content']")
	public static WebElement responseLayoutInputText;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt3:1:pt1:AP3:responseLayoutNameId::lovIconId")
	public static WebElement responseLayoutLOVIcon;
	
	@FindBy(xpath="//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt3:1:pt1:AP3:responseLayoutNameId::dropdownPopup::popupsearch\"]")
	public static WebElement responseLayoutDropDownSearch;
	
	@FindBy(xpath="//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt3:1:pt1:AP3:responseLayoutNameId::_afrLovInternalQueryId:value00::content\"]")
	public static WebElement responseLayoutSearchNameInputText;
	
	@FindBy(xpath="//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt3:1:pt1:AP3:responseLayoutNameId::_afrLovInternalQueryId::search\"]")
	public static WebElement responseLayoutSearchButton;
	
	@FindBy(xpath="//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt3:1:pt1:AP3:responseLayoutNameId_afrLovInternalTableId:0:_afrColChild0\"]")
	public static WebElement responseLayoutSearchResultTableRow;
	
	@FindBy(xpath="//*[@id=\"_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt3:1:pt1:AP3:responseLayoutNameId::lovDialogId::ok\"]")
	public static WebElement ResponseLayoutSearchOK;
	
	@FindBy(xpath="//input[contains(@id,'MAt3:1:pt1:AP3:inputDate2::content')]")
	public static WebElement EditNegotiationCloseDate;
	
	@FindBy(xpath="//a[contains(@id,'FONSr2:0:MAt3:1:pt1:AP3:SPsv2::popEl')]")
	public static WebElement negotiationOverviewSaveDropDown;
	
	@FindBy(xpath="//td[contains(text(),'ave and Close')]")
	public static WebElement negotiationOverviewSaveandClose;
	
	@FindBy(xpath="//button[contains(@id,'msgDlg::cancel')]")
	public static WebElement confirmationPopupOK;
	
	@FindBy(xpath="//[@id='_FOpt1:_FOr1:0:_FOSritemNode_procurement_negotiations:0:MAt3:1:pt1:AP3:responseLayoutNameId::lovDialogId::ok']")
	public static WebElement responseLayoutOK;
	
	@FindBy(xpath="//textarea[contains(@id,'itPopFileText::content')]")
	public static WebElement textarea;
	
	@FindBy(xpath="//input[contains(@id,'popTitleInputText::content')]")
	public static WebElement attachmentTitle;
	
	@FindBy(xpath="//span[contains(@id,'lastAttachedTitle')]")
	public static WebElement lastAttachedText;
	
	@FindBy(xpath="//a[contains(@id,'lastAttachedURL')]")
	public static WebElement lastAttachedURL;
	
	@FindBy(xpath="//span[contains(@id,'otReviewText')]")
	public static WebElement ReviewText;
	
	@FindBy(xpath="//select[contains(@id,'popDatatypeCodeChoiceListIDNew::content')]")
	public static WebElement typeSelect;
	
	@FindBy(xpath="//input[contains(@id,'atapnru::content')]")
	public static WebElement urlField;
	
	@FindBy(xpath="//a[contains(@id,'attachmentMoreLink')]")
	public static WebElement moreAttachmentLink;
	
	@FindBy(xpath = "//div[contains(@id,'a1:pglAAdlg')]")
	public static WebElement attachmentDiagBox;
	
	@FindBy(xpath = "//img[contains(@id,'popApplicationsTable:_ATp:delete::icon')]")
	public static WebElement NegoAttachmentDeleteIcon;
	
	@FindBy(xpath = "//button[contains(@id,'confirm')]")
	public  static WebElement warningYesBtn;
	
	@FindBy(xpath = "//td[contains(@id,'ifPopup::dlgCont::contentContainer')]/descendant::input[contains(@id,'ifPopup::content')]")
	public  static WebElement negotiationUpdateBrowse;
	
	@FindBy(xpath = "//button[contains(@id,'ifPopup::dlgCont::ok')]")
	public  static WebElement updateFileOkBtn;
	
	@FindBy(xpath = "//a[contains(@aria-describedby,'AP3:SPsv2_afrdescBy')]")
	public  static WebElement negoSaveBtn;
	
	
	
	
}
