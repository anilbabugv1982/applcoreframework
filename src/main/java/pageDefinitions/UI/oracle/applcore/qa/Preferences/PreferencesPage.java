package pageDefinitions.UI.oracle.applcore.qa.Preferences;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PreferencesPage {

	public PreferencesPage(WebDriver prefPageDriver) {
		PageFactory.initElements(prefPageDriver, this);
	} 

	@FindBy(xpath = "//a[contains(@id,'UIScmil1u')]")
	public WebElement userLink;

	@FindBy(xpath ="//a[contains(@id,'pt1:_UIScmi1') and text()='Set Preferences']")
	public WebElement setPreferenceLink;
	
	@FindBy(xpath="//h1[contains(text(),'Preferences')]")
	public WebElement preferencesPageTitle;

	@FindBy(linkText = "Regional")
	public WebElement regionalLink;

	@FindBy(xpath = "//a[contains(@id,'_FOTsr1:0:P_sp1:cl6')]")
	public WebElement languageLink;

	//@FindBy(linkText = "Accessibility")
	@FindBy(xpath="//a[contains(@id,'_sp1:cl1') and contains(text(),'Accessibility')]")
	public WebElement accessibilityLink;

	@FindBy(linkText = "Password")
	public WebElement passwordLink;

	@FindBy(xpath = "//h1[contains(text(),': Regional')]")
	public WebElement regionalPageTitle;

	@FindBy(xpath="//select[contains(@id,'AP1:soc1::content')]")
	public WebElement territoryDropDown;

	@FindBy(xpath = "//select[contains(@id,'AP1:soc3::content')]")
	public WebElement dateDropDown;

	@FindBy(xpath = "//select[contains(@id,'AP1:soc23::content')]")
	public WebElement timeDropDown;

	@FindBy(xpath = "//select[contains(@id,'AP1:soc35::content')]")
	public WebElement numberDropDown;

	@FindBy(xpath = "//select[contains(@id,'AP1:soc2::content')]")
	public WebElement currencyDropDown;

	@FindBy(xpath = "//select[contains(@id,'AP1:soc12::content')]")
	public WebElement timezoneDropDown;

	@FindBy(xpath = "//button[contains(@id,'APsv')]")
	public WebElement saveBtn;

	@FindBy(xpath = "//button[contains(text(),'Save')]")
	public WebElement saveBtn1;

	@FindBy(xpath = "//button[contains(@id,'APscl')]")
	public WebElement saveCloseBtn;

	@FindBy(xpath = "//button[contains(text(),'ave and Close')]")
	public WebElement saveCloseBtn1;

	@FindBy(xpath = "//button[contains(@id,'APc')]")
	public WebElement cancelBtn;

	@FindBy(xpath = "//button[contains(text(),'ancel')]")
	public WebElement cancelBtn1;

	@FindBy(xpath = "//h1[contains(text(),': Language')]")
	public WebElement languagePageTitle;
	
	@FindBy(xpath="//select[contains(@id,'AP2:soc2::content')]")
	public WebElement defaultDropDown;
	
	@FindBy(xpath="//select[contains(@id,'AP2:soc1::content')]")
	public WebElement currentDropDown;

	@FindBy(xpath = "//h1[contains(text(),': Accessibility')]")
	public WebElement accessibilityPageTitle;

//	@FindBy(id = "_FOpt1:_FOr1:0:_FOSritemNode_Tools_Preferences:0:MAnt2:1:AP1:sor1:_1") //_FOpt1:_FOr1:0:_FOSritemNode_Tools_Preferences:0:MAnt2:0:AP1:sor1:_1
//	@FindBy(xpath="//input[contains(@id,'AP1:sor1:_1')]") ////label[contains(@for,'AP1:sor1:_1')]
	@FindBy(xpath="//label[contains(@for,'AP1:sor1:_1')]")
	public WebElement screenRdrModeBtn;

//	@FindBy(id = "_FOpt1:_FOr1:0:_FOSritemNode_Tools_Preferences:0:MAnt2:1:AP1:sor1:_0")
//	@FindBy(xpath="//input[contains(@id,'AP1:sor1:_0')]")
	@FindBy(xpath="//label[contains(@for,'AP1:sor1:_0')]")
	public WebElement defaultModeBtn;
	
	@FindBy(xpath="//input[contains(@id,':nPwIp::content')]")
	public WebElement newPassword1;
	
	@FindBy(xpath="//input[contains(@id,'nPwIpC::content')]")
	public WebElement confirmPassword;
	
	@FindBy(xpath = "//button[@title='Save and Close']")
	public WebElement passwordSaveBtn;
	
	@FindBy(xpath="//select[contains(@id,'dynamicRegion1:0:AP1:socter::content')]")
	public WebElement adminTerritoryDD;
	
	@FindBy(xpath="//select[contains(@id,'dynamicRegion1:0:AP1:soc12::content')]")
	public WebElement adminTimeZoneDD;
	
	@FindBy(xpath = "//select[contains(@id,':AP1:soc1::content')]")
	public WebElement userTerritoryDD;
	
	@FindBy(xpath = "//select[contains(@id,':AP1:soc3::content')]")
	public WebElement userDateFormatDD;
	
	@FindBy(xpath = "//select[contains(@id,':AP1:soc23::content')]")
	public WebElement userTimeFormatDD;
	
	@FindBy(xpath = "//select[contains(@id,':AP1:soc35::content')]")
	public WebElement userNumberFormatDD;
	
	@FindBy(xpath = "//select[contains(@id,':AP1:soc2::content')]")
	public WebElement userCurrencyFormatDD;
	
	@FindBy(xpath = "//select[contains(@id,'AP1:soc12::content')]")
	public WebElement userTimeZoneDD;
	
	
}
