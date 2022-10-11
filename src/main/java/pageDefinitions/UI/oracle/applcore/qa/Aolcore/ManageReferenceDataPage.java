package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageReferenceDataPage {
	
	public ManageReferenceDataPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//button[contains(@id,'APsv')]")
	public WebElement saveBtn;
	
	@FindBy(xpath="//button[contains(@id,'qryId2::search')]")
	public WebElement searchBtn;
	
	@FindBy(xpath="//input[contains(@id,'AP1:qryId2:value00::content')]")
	public WebElement setCodeSearchField;
	
	@FindBy(xpath="//img[contains(@id,'_ATp:create::icon')]")
	public WebElement addBtn;
	
	@FindBy(xpath="//img[contains(@id,'_ATp:delete::icon')]")
	public WebElement deleteBtn;
	
	@FindBy(xpath="//label[contains(text(),'Set Code')]/preceding-sibling::input[contains(@id,'it1::content')]")
	public WebElement setCodeInput;
	
	@FindBy(xpath="//input[contains(@id,'it2::content')]")
	public WebElement setNameInput;
	
	@FindBy(xpath="//input[contains(@id,'it3::content')]")
	public WebElement setDescInput;
	
	@FindBy(xpath="//input[contains(@id,'qryId2:value20::content')]")
	public WebElement refSetNameSearch;
	
	@FindBy(xpath="//select[contains(@id,'soc4::content')]")
	public WebElement groupNameDD;
	
	@FindBy(xpath="//select[contains(@id,'soc1::content')]")
	public WebElement determinantNameDD;
	
	@FindBy(xpath="//input[contains(@id,'setNameId::content')]")
	public WebElement refSetNameInput;
	
	
}
