package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ISOReferenceDataPage {

	public ISOReferenceDataPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

	WebElement ele;

	@FindBy(partialLinkText = "Manage Currencies")
	public WebElement tfCurrecyLink;

	@FindBy(partialLinkText = "Manage ISO Languages")
	public WebElement tfISOLink;

	@FindBy(partialLinkText = "Manage Industries")
	public WebElement tfIndustryLink;

	@FindBy(partialLinkText = "Manage Languages")
	public WebElement tfLanguageLink;

	@FindBy(partialLinkText = "Manage Natural Languages")
	public WebElement tfNaturalLangLink;

	@FindBy(partialLinkText = "Manage Territories")
	public WebElement tfTerritoryLink;

	@FindBy(partialLinkText = "Manage Timezones")
	public WebElement tfTimeZoneLink;

	@FindBy(xpath = "//button[contains(@id,'APsv')]")
	public WebElement btnsave;

	@FindBy(xpath = "//button[contains(@id,'APscl')]")
	public WebElement btnSaveandClose;

	@FindBy(xpath = "//button[contains(@id,'search')]")
	public WebElement btnSearch;

	@FindBy(xpath = "//button[contains(@id,'reset')]")
	public WebElement btnReset;

	@FindBy(xpath = "//img[contains(@id,'create::icon')]")
	public WebElement btnCreate;

	@FindBy(xpath = "//img[contains(@id,'delete::icon')]")
	public WebElement btnDelete;

	// Manage Currencies Page WebElements

	@FindBy(xpath = "//span[text()='Tasks']")
	public WebElement filterLink;

	@FindBy(xpath = "//label[text()='All']")
	public WebElement checkBoxAll;

	@FindBy(xpath = "//label[text()='Tasks']")
	public WebElement checkBoxTasks;

	@FindBy(xpath = "//button[contains(@id,'d1::ok')]")
	public WebElement filetrOKBtn;

	@FindBy(xpath = "//input[contains(@id,'qryId1:value00::content')]")
	public WebElement crnCodeSearchField;

	@FindBy(xpath = "//input[contains(@id,'value10::content')]")
	public WebElement crnNameSearchField;

	@FindBy(xpath = "//input[contains(@id,'it7::content')]")
	public WebElement crnCodeinputField;

	@FindBy(xpath = "//input[contains(@id,'it4::content')]")
	public WebElement crnNameinputField;

	@FindBy(xpath = "//input[contains(@id,'it3::content')]")
	public WebElement crnDescinputField;

	@FindBy(xpath = "//a[contains(@id,'id1::glyph')]")
	public WebElement crnstartDateIcon;

	@FindBy(xpath = "//td[@tabindex='0']")
	public WebElement crnCurrentDate;

	// Manage ISO

	@FindBy(xpath = "//input[contains(@id,'qryId1:value00::content')]")
	public WebElement isocode3SearchInput;

	@FindBy(xpath = "//input[contains(@id,'AP1:qryId1:value20')]")
	public WebElement isonameSearchInput;

	@FindBy(xpath = "//input[contains(@id,'it2::content')]")
	public WebElement iso3codeInput;

	@FindBy(xpath = "//input[contains(@id,'it3::content')]")
	public WebElement iso2CodeInput;

	@FindBy(xpath = "//label[text()='Name']/preceding-sibling::input[contains(@id,'it1::content')]")
	public WebElement isoNameInput;

	@FindBy(xpath = "//input[contains(@id,'pt1:AP1:AT1:_ATp:ATt1:0:it4::content')]") //
	public WebElement isoDescInput;

	@FindBy(xpath = "//label[contains(@for,'sbc1::content')]")
	public WebElement isoCheckBox;

	// Industries

	@FindBy(linkText = "Manage Industries")
	public WebElement indLink;

	@FindBy(xpath = "//h1[text()='Manage Industries']")
	public WebElement indTitle;

	@FindBy(xpath = "//input[contains(@id,'AP3:qryId1:value00::content')]")
	public WebElement indNameSearchField;

	@FindBy(xpath = "//input[contains(@id,'AP3:qryId1:value10::content')]")
	public WebElement indcodeSearchField;

	@FindBy(xpath = "//input[contains(@id,'pt1:AP3:AT3:_ATp:ATt3:0:it1::content')]")
	public WebElement indcodeinput;
	
	@FindBy(xpath = "//label[text()='Industry Name']/preceding-sibling::input[contains(@id,'it2::content')]")
	public WebElement indNameInput;
	
	@FindBy(xpath = "//label[text()='Description']/preceding-sibling::input[contains(@id,'it3::content')]")
	public WebElement indDescInput;
	
	@FindBy(xpath = "//input[contains(@id,'sbc2::content') and @type='checkbox']")
	public WebElement indCodeCb1;
	
	@FindBy(xpath = "//select[contains(@id,'soc1::content')]")
	public WebElement indTerritoryDD;
	
	@FindBy(xpath = "//input[contains(@id,'sbc1::content') and @type='checkbox']")
	public WebElement indTerrCB;
	
	@FindBy(xpath = "//img[contains(@id,'pt1:AP3:AT3:_ATp:create::icon')]")
	public WebElement indaddBtn1;
	
	@FindBy(xpath = "//img[contains(@id,'pt1:AP3:AT4:_ATp:create::icon')]")
	public WebElement indaddBtn2;
	
	@FindBy(xpath = "//img[contains(@id,'pt1:AP3:AT3:_ATp:delete::icon')]")
	public WebElement inddeleteBtn1;
	
	@FindBy(xpath = "//img[contains(@id,'pt1:AP3:AT4:_ATp:delete::icon')]")
	public WebElement inddeleteBtn2;
	
 // Manage Languages
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP1:qryId1:value10::content')]")
	public WebElement langSearchName;
	
	@FindBy(xpath = "//select[contains(@name,'pt1:AP1:qryId1:value20')]")
	public WebElement langInstallSearcgDD; 
	
	@FindBy(xpath = "//input[contains(@id,'pt1:AP1:AT1:_ATp:ATt1:0:it5::content')]")
	public WebElement langNameInput;
	
// Manage Natural Lang
	
	@FindBy(xpath = "//input[contains(@id,'pt1:AP1:qryId1:value10::content')]")
	public WebElement nlNameSearchField;
	
	@FindBy(xpath = "//input[contains(@name,'pt1:AP1:AT1:_ATp:ATt1:0:it3')]")
	public WebElement nlCodeInput;
	
	@FindBy(xpath = "//label[contains(.,'Language Name')]/preceding-sibling::input[contains(@id,'it1::content')]")
//	@FindBy(xpath = "//input[contains(@id,'ATp:ATt1:1:it1::content')]")
	public WebElement nlNameInput;
	
	@FindBy(xpath = "//input[contains(@id,'pt1:AP1:AT1:_ATp:ATt1:0:it2::content')]")
	public WebElement nlDescInput;
	
	@FindBy(xpath = "//select[contains(@id,'pt1:AP1:AT1:_ATp:ATt1:0:soc1::content')]")
	public WebElement nlISOLangDD;
	
	@FindBy(xpath = "//select[contains(@id,'pt1:AP1:AT1:_ATp:ATt1:0:soc2::content')]")
	public WebElement nlISOTerritoryDD;
	
	// Manage TimeZone
	
	@FindBy(xpath = "//input[contains(@id,'qryId1:value10::content')]")
	public WebElement tznameQueryInput;
	
	@FindBy(xpath = "//input[contains(@id,'ATt2:0:it1::content')]")
	public WebElement tzCodeInput;
	
	@FindBy(xpath="//label[contains(.,'Timezone Name')]/preceding-sibling::input[contains(@id,'it2::content')]")
	public WebElement tzNameInput;
	
	// Manage Territories
	
	@FindBy(xpath = "//input[contains(@id,'AP1:qryId1:value10::content')]")
	public WebElement ttNameQueryinput;
	
	@FindBy(xpath = "//label[text()='Territory Code']/preceding-sibling::input[contains(@id,'it6::content')]")
	public WebElement ttCodeInput;
	
	@FindBy(xpath="//label[contains(.,'Territory Name')]/preceding-sibling::input[contains(@id,'it3::content')]")
	public WebElement ttNameInput;
	
	
	
	
}
