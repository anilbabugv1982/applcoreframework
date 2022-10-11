package pageDefinitions.UI.oracle.applcore.qa.Attachments;

//Author

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

/**
 * @author mridula.g.bhat@oracle.com
 *
 */


public class OpportunitiesPage {
	private WebDriver driver;

	public OpportunitiesPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
//*******************************************************************************************************************************	

	@FindBy(xpath="//button[contains(text(),'Create Opportunity')]")
	public static WebElement createOpportunityButton;

	@FindBy(xpath="//input[contains(@id,'pt1:it1')]")
	public static WebElement NameTextField;

	//button[text()='Save and Continue']
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:cb3')]")
	public static WebElement SaveAndContinueButton;

	@FindBy(xpath="//img[contains(@id,'apptAttach:clLAddsF::icon')]")
	public static WebElement manageAttachmentsButton;

	@FindBy(xpath = "//input[contains(@class,'inputFile_content')]")
	public static WebElement browse;
	
	@FindBy(xpath="//button[contains(@id,'apptAttach:fdc_cb1')]")
	public static WebElement okButton;
	
	@FindBy(xpath = "//button[contains(@id,'fifPopup::upBtn')]")
	public static WebElement updateFileBtn;
	
	@FindBy(xpath = "//div[contains(@id,'apptAttach:body')]")
	public static WebElement attachmentDiagBox;
	
	@FindBy(xpath = "//td[@class='af_inputFile_label']/following::input[contains(@id,'fifPopup::content')]")
	public static WebElement updateBrowse;
	
	@FindBy(xpath = "//button[contains(@id,'fifPopup::dlgCont::ok')]")
	public static WebElement UpdatePopupOkBtn;

	//@FindBy(xpath="//*[@id='_FOpt1:_FOr1:0:_FOSrMOO_OPPTYMGMTOPPORTUNITIES_CRM_CARD:0:_FOTsr1:0:pt1:r3:1:pt1:r1:0:apptAttach:fpopAttachmentTable:1:fifPopup::content']")
	//public static WebElement browseButton;

	@FindBy(xpath="//button/span[contains(text(),'S')]")
	public static WebElement saveAndCloseOpp;

	

	@FindBy(xpath="//a[contains(@id,'fpopDatatypeCodeChoiceListIDNew::drop')]")
	public static WebElement typeSelect1;
	
	//li[contains(text(),'URL')] <-- not working
	@FindBy(xpath = "//div[@class='AFPopupMenuContent']/descendant::li[contains(text(),'URL')]")
	public static WebElement typeSelectURL;

	
	//@FindBy(xpath="//a[@id='_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:pt1:r1:0:pt1:r1:0:apptAttach:fpopAttachmentTable:1:fpopDatatypeCodeChoiceListIDNew::drop']")
    //public static WebElement filetype;
	
	@FindBy(xpath="//input[contains(@id,'fatapnru::content')]")
	public static WebElement URLTextField;
	
	@FindBy(xpath="//button[contains(@id,'object-subtitle:cb11')]")
	public static WebElement OppSaveIcon;
	
	//@FindBy(xpath="//li[contains(@id,'FONSr2:0:_FOTsr1:0:pt1:r3:1:pt1:r1:0:apptAttach:fpopAttachmentTable:0:fpopDatatypeCodeChoiceListIDNew::sp')]")
	//public static WebElement UrlSelec;
	
	//input[@value='File']
	@FindBy(xpath="//a[contains(@id,'fpopDatatypeCodeChoiceListIDNew::drop')]")
	public static WebElement AttachDrpDown;
	
	@FindBy(xpath="//button[contains(@id,'fifPopup::dlgCont::ok')]")
	public static WebElement updateFile_OkButton;
	
	@FindBy(xpath="//a[contains(@id,'apptAttach:attachmentMoreLink')]")
	public static WebElement moreAttachmentLink;
	
	@FindBy(xpath="//img[contains(@id,'fatdDel::icon')]")
	public static WebElement deleteAttachment;
	

}

//********************************************************************************************************************************
