package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HcmAttachmentsPage {

	public HcmAttachmentsPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[contains(@class,'flat-tabs-text') and text()='My Team']")
	public static WebElement myTeamTab;
	
	@FindBy(xpath = "//a[contains(text(),'Anthony Nvn3tierManager1')]")
	public static WebElement directReports_Anthony;
	
	@FindBy(xpath = "//div[contains(@id,'gp1Btn') and @title='Comments and Attachments']")
	public static WebElement commentsAndAttachments;
	
	@FindBy(xpath = "//div[contains(@id,'gp1Upl:UPsp1:SPsb2')]")
	public static WebElement continueToolbarButton;
	
	@FindBy(xpath = "//button[contains(@id,'Rgn:0:GPcb')]")
	public static WebElement continueButton;
	
	@FindBy(xpath = "//table[contains(@id,'dzAvsd:pglDzMsg')]")
	public static WebElement browse;
	
	@FindBy(xpath = "//a[contains(@id,'dzAvsd:cilDzMsg')]")
	public static WebElement addAttachmentLink;
	
	@FindBy(xpath = "//td[contains(text(),'Add Link')]")
	public static WebElement addLinkMenu;
	
	@FindBy(xpath = "//div[contains(@id,'dAvsdAddU::_ttxt')]")
	public static WebElement addLinkDiagBox;
	
	@FindBy(xpath = "//textarea[contains(@id,'itAvsdUrl::content')]")
	public static WebElement addLinktextField;
	
	@FindBy(xpath = "//div[contains(@id,'sfAvsd:ctbAvsdAuS')]")
	public static WebElement addLinkSaveAndClose;
	
	@FindBy(xpath = "//div[contains(@class,'FndDropZoneProgressContainer')]")
	public static WebElement fileUploadProcessBar;
	
	@FindBy(xpath = "//img[contains(@id,'pglDzProg::dzProgTbl::r0::dzCancelImg')]")
	public static WebElement uploadCancelBtn;
	
	@FindBy(xpath = "//input[contains(@id,'itIeTitle::content')]")
	public static WebElement editTitleField;
	
	@FindBy(xpath = "//textarea[contains(@id,'itIeDesc::content')]")
	public static WebElement editDescriptionField;
	
	@FindBy(xpath = "//div[contains(@id,'pseAvsdCe:PSEcb2')]")
	public static WebElement editSaveBtn;
	
	@FindBy(xpath = "//button[contains(@id,'confirm')]")
	public  static WebElement warningYesBtn;
	
	@FindBy(xpath = "//div[contains(@id,'UPsp1:SPc')]")
	public  static WebElement cancelHCMButton;
	

}
