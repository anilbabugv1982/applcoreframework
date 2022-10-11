package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageTradingPartnerItemsPage {

	public ManageTradingPartnerItemsPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[contains(@id,'FOTsdiItemRegionalArea::ti')]//a")
	public static WebElement pimPanelDrawer;
	
	@FindBy(xpath="//a[contains(@id,'0:_FOTRaT:0:RAtl7') and text()='Manage Trading Partner Items']")
	public static WebElement manageTradingPartnerItemsLink;
	
	@FindBy(xpath="//a[contains(@id,'ls1:_LSSFB') and normalize-space(text())='Show Filters']")
	public static WebElement showFiltersLink;
	
	@FindBy(xpath="//a[contains(@id,'ls1:_LSSFB') and normalize-space(text())='Hide Filters']")
	public static WebElement hideFiltersLink;
	
	@FindBy(xpath="//input[contains(@id,'ls1:_LSQry:value10::content')]")
	public static WebElement tradingPartnerField;
	
	@FindBy(xpath="//button[contains(@id,'ls1:_LSQry::search')]")
	public static WebElement filterSearchButton;
	
	@FindBy(xpath="//img[contains(@id,'ls1:call1:CLL_ctb2::icon')]")
	public static WebElement listViewIcon;
	
	@FindBy(xpath="//span[contains(@id,'ot5') and normalize-space(text())='ICTech1']")
	public static WebElement ICTech1Column;
	
	@FindBy(xpath="//a[contains(@id,'sdi3::_afrDscl')]")
	public static WebElement attachmentsArrowIcon;
	
	@FindBy(xpath="//a[contains(@id,'dciTable:tblAttach:0:clTitle')]")
	public static WebElement attachmentsTitle;
	
	@FindBy(xpath="//span[contains(@id,'dciTable:tblAttach:0:otAgtDesc')]")
	public static WebElement attachmentsDescription;
	
	@FindBy(xpath="//a[contains(@id,'dciTable:tblAttach:0:glDwn')]")
	public static WebElement attachmentsDownloadIcon;
	

}
