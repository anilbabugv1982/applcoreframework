package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ItemImportESSJobsPage {

	public ItemImportESSJobsPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[contains(@id,'_afrLovInternalQueryId::search')]")
	public static WebElement jobSearchPopup_SearchBtn;
	
	@FindBy(xpath = "//a[contains(@id,'jobDisplayNameId::lovIconId')]")
	public static WebElement importProcessDropdown;
	
	@FindBy(xpath = "//a[contains(@id,'jobDisplayNameId::dropdownPopup::popupsearch')]")
	public static WebElement importProcessSearchLink;
	
	@FindBy(xpath = "//input[contains(@id,'jobDisplayNameId::_afrLovInternalQueryId:value00::content')]")
	public static WebElement importProcessPopup_SearchField;
	
	@FindBy(xpath = "//button[contains(@id,'jobDisplayNameId::_afrLovInternalQueryId::search')]")
	public static WebElement importProcessPopup_SearchButton;
	
	@FindBy(xpath = "//button[contains(@id,'jobDisplayNameId::lovDialogId::ok')]")
	public static WebElement importProcessPopup_OkButton;
	
	@FindBy(xpath = "//input[contains(@id,'jobDisplayNameId::content')]")
	public static WebElement importProcessSearchField;
	
	@FindBy(xpath = "//a[contains(@id,'ucmFileId::lovIconId')]")
	public static WebElement dataFileDropdown;
	
	@FindBy(xpath = "//a[contains(@id,'cl1')]")
	public static WebElement dataFileuploadLink;
	
	@FindBy(xpath = "//input[contains(@id,'if1::content')]")
	public static WebElement dataFileChooseFile;
	
	@FindBy(xpath = "//button[contains(@id,'d1::ok')]")
	public static WebElement dataFileOkButton;
	
	@FindBy(xpath = "//input[contains(@id,'ucmFileId::content')]")
	public static WebElement dataFileSearchField;
	
	@FindBy(xpath = "//input[contains(@id,'paramDynForm_Attribute1_ATTRIBUTE1::content')]")
	public static WebElement itemImportJob_BatchIDField;
	

}
