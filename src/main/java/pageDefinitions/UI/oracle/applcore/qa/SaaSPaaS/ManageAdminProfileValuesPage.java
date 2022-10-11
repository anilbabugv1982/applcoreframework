package pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageAdminProfileValuesPage {

	public ManageAdminProfileValuesPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//button[contains(@id,'APsv')]")
	public  WebElement saveAdminBtn;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP2:qryId1:value00::content')]")
	public WebElement profileoptioncode;
	
	@FindBy(xpath = "//button[contains(@id , 'search')]")
	public  WebElement search;
	
	@FindBy(xpath = "//select[contains(@id , ':soc3::content')]")
	public  WebElement profilelevelDropdown;
	
	@FindBy(xpath = "//span[contains(@id,'AP2:AT4:_ATp:ATt4') and contains(@id,'soc3::content')]")
	public  WebElement profilelevel;
	
	@FindBy(xpath = "//input[contains(@id,'AP2:AT4:_ATp:ATt4') and contains(@id,'it3::content')]")
	public  WebElement profilevalue;
	
	@FindBy(xpath = "//img[contains(@id , 'create')]")
	public  WebElement create;
	
	@FindBy(xpath="//input[contains(@id,'userLevelValueId::content')]")
	public  WebElement adminUserNameInputField;
	
	@FindBy(xpath="//select[contains(@id,'soc2::content')]")
	public  WebElement adminUserNameProfileValueDD;
	
	@FindBy(xpath="//input[contains(@id,'ATp_afr_ATt4_afr_c5::content')]")
	public  WebElement qbeSearch;
	
	@FindBy(xpath="//input[contains(@id,'c2::content')]")
	public  WebElement qbeSearch1;
	
	@FindBy(xpath = "//img[contains(@id , 'delete')]")
	public  WebElement delete;
	
	@FindBy(xpath = "//div[contains(text(),'No data to display.')]")
	public  WebElement profileValuesNoData;
	
	@FindBy(xpath = "//body[@style='cursor: wait;']")
	public static WebElement waitCursor;

}
