package pageDefinitions.UI.oracle.applcore.qa.Loader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageCommonLookup {
private WebDriver driver;
	
	public ManageCommonLookup(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//input[contains(@id,'AP1:qryId2:value00::content')]")
	public  WebElement lookupType;
	
	@FindBy(xpath="//button[contains(text(),'Search')]")
	public  WebElement search;
	
	@FindBy(xpath="//img[contains(@id,':AT1:_ATp:create::icon')]")
	public WebElement lkpTypeAddBtn;
	}
