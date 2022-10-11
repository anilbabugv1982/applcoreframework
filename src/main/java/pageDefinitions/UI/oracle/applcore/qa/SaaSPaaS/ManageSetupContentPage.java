package pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageSetupContentPage {

	public ManageSetupContentPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//img[contains(@id,'sdi10::icon')]")
	public WebElement panelDrawer;
	
	@FindBy(xpath="//a[normalize-space(text())='Manage Setup Content']")
	public WebElement manageSetupContentLink;
	
	@FindBy(xpath="//img[contains(@id,'AP1:AT1:_ATp:create::icon')]")
	public WebElement createAppIntegrationIcon;
	
	@FindBy(xpath="//input[contains(@id,'AP1:it8::content')]")
	public WebElement applicationNameField;
	
	@FindBy(xpath="//input[contains(@id,':it3::content')]")
	public WebElement fullURLField;
	
	@FindBy(xpath="//input[contains(@id,'AP1:it2::content')]")
	public WebElement partnerNameField;
	
	@FindBy(xpath="//select[contains(@id,'AP1:soc1::content')]")
	public WebElement securityPolicyField;
	
	@FindBy(xpath="//input[contains(@id,'AP1:i1:0:it15::content')]")
	public WebElement userNameField;
	
	@FindBy(xpath="//input[contains(@id,'AP1:i1:1:it15::content')]")
	public WebElement passwordField;
	
	@FindBy(xpath="//button[contains(@id,'AP1:gb2')]")
	public WebElement applyBtn;
	
	@FindBy(xpath="//*[contains(@id,'AP1:APscl')]")
	public WebElement saveAndCloseBtn;
	
	@FindBy(xpath="//span[contains(@id,'it7::content')]")
	public WebElement contextRootDetails;

}
