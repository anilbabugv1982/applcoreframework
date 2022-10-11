package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author DASKUMAR
 *
 */
public class LookupsPage {
	
	public LookupsPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	
//	@FindBy(linkText="Manage Standard Lookups")
//	@FindBy(linkText="Manage Common Lookups")
	@FindBy(linkText="Manage Set-Enabled Lookups")
	public WebElement taskFlowLink;
	
//	@FindBy(xpath="//h1[contains(text(),'Manage Standard Lookups')]")
//	@FindBy(xpath="//h1[contains(text(),'Manage Common Lookups')]")
	@FindBy(xpath="//h1[contains(text(),'Manage Set Enabled Lookups')]")
	public WebElement stdLookupTitle;
	
	@FindBy(xpath="//input[contains(@id,'value00::content')]")
	public WebElement lkpTypeSearchField;
	
	@FindBy(xpath="//button[text()='Save']")
	public WebElement saveBtn;
	
	@FindBy(xpath="//button[text()='Search']")
	public WebElement searchBtn;
	
	@FindBy(xpath="//button[text()='Reset']")
	public WebElement resetBtn;
	
	@FindBy(xpath="//img[contains(@id,':AT1:_ATp:create::icon')]")
	public WebElement lkpTypeAddBtn;
	
	@FindBy(xpath="//img[contains(@id,'AP1:AT1:_ATp:delete::icon')]")
	public WebElement lkpTypeDeleteBtn;
	
	@FindBy(xpath="//img[contains(@id,':AT2:_ATp:create::icon')]")
	public WebElement lkpCodeAddBtn;
	
	@FindBy(xpath="//img[contains(@id,'AP1:AT2:_ATp:delete::icon')]")
	public WebElement lkpCodeDeleteBtn;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:AT1:confirm')]")
	public WebElement lkpTypecondirmBtn;
	
	@FindBy(xpath="//label[text()='Lookup Type']/preceding-sibling::input")
	public WebElement lkpTypeinputField;
	
	@FindBy(xpath="//label[text()='Meaning']/preceding-sibling::input")
	public WebElement lkpTypeMeaningInput;
	
	@FindBy(xpath="//label[text()='Description']/preceding-sibling::input")
	public WebElement lkpTypeDescInput;
	
	@FindBy(xpath="//label[text()='Module']/preceding-sibling::input")
	public WebElement moduleInput;	
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:AT2:_ATp:ATt2::db')]/table/tbody/tr/td[2]/following::input")
	public WebElement lkpCodeInput;
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:AT2:_ATp:ATt2::db')]/table/tbody/tr/td[3]/following::input")
	public WebElement displaySeqInput;
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:AT2:_ATp:ATt2::db')]/table/tbody/tr/td[5]/following::input")
	public WebElement lkpCodeStartDateInput;	
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:AT2:_ATp:ATt2::db')]/table/tbody/tr/td[7]/following::input")
	public WebElement lkpCodeMeaningInput;
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:AT2:_ATp:ATt2::db')]/table/tbody/tr/td[8]/following::input")
	public WebElement lkpCodeDescInput;
	
	@FindBy(xpath="//input[contains(@id,'afr_pt1_afr_AP1_afr_AT2_afr__ATp_afr_ATt2_afr_')]")
	public WebElement qbeSearchField;
	
	@FindBy(xpath="//button[contains(@id,'pt1:AP1:AT2:confirm')]")
	public WebElement lkpCodecondirmBtn;
	
	@FindBy(xpath = "//td[text() = 'All customizations in this page are being carried out in the current Sandbox.']")
	public  WebElement LookupsEditModeMsg;
	
	@FindBy(xpath = "//td[text() = 'Task Lookups is in read-only mode because the sandbox is in preview mode. Switch the sandbox to edit mode to make changes.']")
	public  WebElement LookupsPreviewModeMsg;
	
	@FindBy(xpath = "//td[text() = 'Task Lookups is in read-only mode because this tool is not activated in this sandbox. Activate this tool to make changes.']")
	public  WebElement LookupToolNotActivatedMsg;
	
	@FindBy(xpath = "//button[text() = 'Save' and @disabled]")
	public  WebElement PreviewModeSaveTransanction;
	
	@FindBy(xpath = "//button[text() = 'ave and Close' and @disabled]")
	public  WebElement PreviewModeSaveandClosetransaction;
	
	@FindBy(xpath = "//button[text() = 'ave and Close']")
	public  WebElement ProfiletfSaveandClose;
	
	@FindBy(xpath = "//label[text() = 'Profile Option Code']/ancestor::tr[1]/descendant::input[@type = 'text']")
	public  WebElement POCode;
	
	@FindBy(xpath = "//button[contains(@id , 'search')]")
	public  WebElement POSearch;
	
	
	/*
	 * Set-Enabled Lookup Type WebElements;
	 * 
	 */
	
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:AT2:_ATp:ATt2::db')]/table/tbody/tr/td[2]/following::input")
	public WebElement ssetlkpCodeInput;
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:AT2:_ATp:ATt2::db')]/table/tbody/tr/td[4]/following::input")
	public WebElement setdisplaySeqInput;
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:AT2:_ATp:ATt2::db')]/table/tbody/tr/td[6]/following::input")
	public WebElement setlkpCodeStartDateInput;	
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:AT2:_ATp:ATt2::db')]/table/tbody/tr/td[8]/following::input")
	public WebElement setlkpCodeMeaningInput;
	
	@FindBy(xpath="//div[contains(@id,'pt1:AP1:AT2:_ATp:ATt2::db')]/table/tbody/tr/td[9]/following::input")
	public WebElement setlkpCodeDescInput;
	
	@FindBy(xpath="//select[contains(@id,'pt1:AP1:AT1:_ATp:ATt1:0:soc2::content')]")
	public WebElement setlkpGroupNameSelect;	
	
	@FindBy(xpath="//label[text()='Reference Data Set']/preceding-sibling::select")
	public WebElement setlkpDataSetSelect;
	
	@FindBy(xpath="//button[text() = 'ancel']")
	public WebElement ExitTf;
	
	/*
	 * Xpaths for "Manage Tree Structures" tf to verify runtime changes 
	 */
	
	@FindBy(xpath="//img[@title = 'Create']")
	public WebElement ManageTreeStructure_Create;
	
	@FindBy(xpath="//button[@id = 'pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:APb']")
	public WebElement ManageTreeStructure_Done;
	
	
}
