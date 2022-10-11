/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/**
 * @author klalam
 *
 */
public class ApplicationTextUI {
	
	public ApplicationTextUI(WebDriver driver){
		
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h2[text() = 'Customize Application Text']")
	public  WebElement CustomizeUITextAssert;
	
	@FindBy(xpath = "//button[contains(@id , 'pt1:rg1:0:cb1')]")
	public   WebElement SearchAndReplace_Enabled;
	
	@FindBy(xpath = "//button[contains(@id , 'pt1:rg1:0:cb1') and (contains(@class , 'p_AFDisabled'))]")
	public   WebElement SearchAndReplace_Disabled;
	
	@FindBy(xpath = "//span[contains(text() , 'You must be in a properly configured Sandbox to customize text')]")
	public   WebElement UITPreviewMode;
		
	//Xpaths for "TextReplacement History" table
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']")
	public   WebElement TextReplacementTable;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Created By']/ancestor::td[1]/following-sibling::td/descendant::span")
	public   WebElement ReplacedHistory_UserName;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Singular Search']/ancestor::td[1]/following-sibling::td/descendant::span")
	public   WebElement ReplacedHistory_SingularSearch;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Plural Search']/ancestor::td[1]/following-sibling::td/descendant::span")
	public   WebElement ReplacedHistory_PluralSearch;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Updated Count']/ancestor::td[1]/following-sibling::td/descendant::span")
	public   WebElement ReplacedHistory_Count;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Singular Replace']/ancestor::td[1]/following-sibling::td/descendant::span")
	public   WebElement ReplacedHistory_SingularReplace;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Plural Replace']/ancestor::td[1]/following-sibling::td/descendant::span")
	public   WebElement ReplacedHistory_PluralReplace;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Language']/ancestor::td[1]/following-sibling::td/descendant::span")
	public   WebElement ReplacedHistory_Language;
	
}//End of ApplicationTextUI class
