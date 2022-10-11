package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FSMExportImportAttachmentPage {

	public FSMExportImportAttachmentPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//span[normalize-space(text())='Manage Attachment Entities']/following::span[contains(@id,'ot16')]")
	public static WebElement addEntitiesNotes;
	
	@FindBy(xpath = "//span[normalize-space(text())='Manage Attachment Categories']/following::span[contains(@id,'ot16')]")
	public static WebElement addCategoriesNotes;
	
	@FindBy(xpath = "//div[contains(@id,'d1::_ttxt') and contains(text(),'Notes: ')]")
	public static WebElement notesPopupHeading;
	
	@FindBy(xpath = "//img[contains(@id,'a1:applicationsTable:_ATp:create::icon')]")
	public static WebElement attachmentAddIcon;
	
	@FindBy(xpath = "//select[contains(@id,'dCode::content')]")
	public static WebElement attachmentType;
	
	@FindBy(xpath = "//input[contains(@id,'desktopFile::content')]")
	public static WebElement attachmentBrowse;
	
	@FindBy(xpath = "//button[contains(@id,'desktopFile::upBtn')]")
	public static WebElement attachmentUpdateBtn;
	
	@FindBy(xpath = "//button[contains(@id,'applicationsTable1:d1::ok')]")
	public static WebElement attachmentSaveAndCloseButton;
	
	@FindBy(xpath = "//textarea[contains(@id,'itNewText::content')]")
	public static WebElement textarea;
	
	@FindBy(xpath = "//input[contains(@id,'TitleInputText::content')]")
	public static WebElement attachmentTitle;
	
	@FindBy(xpath = "//input[contains(@id,'DescriptionInputText::content')]")
	public static WebElement attachmentDescription;
	
	@FindBy(xpath = "//input[contains(@id,'itnewurl::content')]")
	public static WebElement urlField;
	
	@FindBy(xpath = "//button[contains(@id,'desktopFile::dlgCont::ok')]")
	public static WebElement updateFileOkBtn;
	
	@FindBy(xpath = "//img[contains(@id,'a1:applicationsTable:_ATp:delete::icon')]")
	public static WebElement attachmentDeleteIcon;
	
	@FindBy(xpath = "//button[contains(@id,'confirm')]")
	public static WebElement warningYesBtn;
	
	
	

}
