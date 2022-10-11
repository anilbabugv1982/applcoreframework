package pageDefinitions.UI.oracle.applcore.qa.Loader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageStandardLookup {
	
	public ManageStandardLookup(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//input[contains(@id,':value00::content')]")
	public  WebElement lookupType;
	
	@FindBy(xpath="//button[contains(text(),'Search')]")
	public  WebElement search;
}
