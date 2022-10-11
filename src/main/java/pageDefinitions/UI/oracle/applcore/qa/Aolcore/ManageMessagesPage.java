package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ManageMessagesPage {

	public ManageMessagesPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//a[text()='Setup and Maintenance']")
	public WebElement setupAndMaintananceElement;	
	
	@FindBy(linkText="Manage Messages")
	public WebElement taskFlowLinkText;
	
	@FindBy(xpath="//h1[contains(text(),'Manage Messages')]")
	public WebElement pageHeading;
	
	@FindBy(xpath="//h3[text()='Create Message']")
	public WebElement createMsgPageHeading;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:qryId2:value00::content')]")
	public WebElement msgSearchField;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:APsv')]")
	public WebElement saveBtn;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:qryId2::search')]")
	public WebElement searchBtn;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:qryId2::reset')]")
	public WebElement resetBtn;
	
	@FindBy(xpath="//img[@title='New']")
	public WebElement createBtn;
	
	@FindBy(xpath="//img[@title='Edit']")
	public WebElement editBtn;
	
	@FindBy(xpath="//img[@title='Delete']")
	public WebElement deleteBtn;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:APscl')]")
	public WebElement saveandCloseBtn;
	
	@FindBy(xpath = "//label[text() = 'Message Name']/ancestor::tr[1]/descendant::input[@type = 'text']")
	public  WebElement MessageName;
	
	//@FindBy(xpath = "//label[text() = 'Application']/ancestor::tr[1]/descendant::input[@type = 'text']")
	@FindBy(xpath = "//label[text() = 'Application']/ancestor::tr[1]/descendant::select")
	public  WebElement MessageApplication;
	
	@FindBy(xpath = "//label[text() = 'Application']/ancestor::tr[1]/descendant::a")
	public  WebElement MessageApplication_Ddwon;
	
	@FindBy(xpath = "//label[text() = 'Module']/ancestor::tr[1]/descendant::input[@type = 'text']")
	public  WebElement MessageModule;
	
	@FindBy(xpath = "//label[text() = 'Short Text']/ancestor::tr[1]/descendant::textarea")
	public  WebElement MessageShortText;
	
	@FindBy(xpath = "//label[text() = 'User Details']/ancestor::tr[1]/descendant::textarea")
	public  WebElement MessageUserDetails;
	
	@FindBy(xpath = "//label[text() = 'Administrator Details']/ancestor::tr[1]/descendant::textarea")
	public  WebElement MessageAdminDetails;

	@FindBy(xpath = "//label[text() = 'Cause']/ancestor::tr[1]/descendant::textarea")
	public  WebElement MessageCause;

	@FindBy(xpath = "//label[text() = 'User Action']/ancestor::tr[1]/descendant::textarea")
	public  WebElement MessageUserAction;

	@FindBy(xpath = "//label[text() = 'Administrator Action']/ancestor::tr[1]/descendant::textarea")
	public  WebElement MessageAdminAction;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:APc')]")
	public  WebElement cancelButton;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:sbc1::content')]")
	public  WebElement _loaderCheckBox;
	
	/*
	 * Message Token WebElements
	 * 
	 */
	
	@FindBy(xpath = "//label[text() = 'Token Name']/ancestor::td[1]/descendant::input[@type = 'text']")
	public  WebElement MessageTokenName;
	
	//@FindBy(xpath = "//label[text()='Data Type']/ancestor::td[1]/descendant::a")
	@FindBy(xpath = "//label[text()='Data Type']/preceding-sibling::select")
	public  WebElement MessageTokenDataType;
	
	@FindBy(xpath = "//label[text() = 'Description']/ancestor::td[1]/descendant::input[@type = 'text']")
	public  WebElement MessageTokenDescription;
	
	@FindBy(xpath = "//td[text() = 'All customizations in this page are being carried out in the current Sandbox.']")
	public  WebElement MessagesEditModeMsg;
	
	@FindBy(xpath = "//td[text() = 'Task Messages is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.']")
	public  WebElement MessagesPreviewModeMsg;
	
	@FindBy(xpath = "//td[text() = 'Task Messages is in read-only mode because this tool is not activated in this sandbox. Activate this tool to make changes.']")
	public  WebElement MsgToolNotActivatedMsg;
	
	@FindBy(xpath = "//button[text() = 'Save' and @disabled]")
	public  WebElement PreviewModeSaveTransanction;
	
	@FindBy(xpath = "//button[text() = 'ave and Close' and @disabled]")
	public  WebElement PreviewModeSaveandClosetransaction;
	
	@FindBy(xpath = "//div[text() = 'Error']/ancestor::div[1]/descendant::button[text() = 'OK']")
	public  WebElement ErroedPopupOk;
	
	@FindBy(xpath = "//div[text() = 'Error']")
	public  WebElement ErroredPopup;
	
	@FindBy(xpath = "//td[contains(text() , 'Error')]")
	public  WebElement ChldErroredTip;
	
	@FindBy(xpath = "//span[contains(text() , 'The lookup type already exists')]")
	public  WebElement DuplicateLookupType;
	
	@FindBy(xpath = "//span[contains(text() , 'Another lookup type already has the same meaning')]")
	public  WebElement DuplicateLookupTypeMeaning;
	
}
