/**
 * 
 *//*
package oracle.applcore.qa.stringeditor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import oracle.applcore.qa.setup.DriverInstance;


*//**
 * @author klalam
 *
 *//*
public class ApplicationTextUI {
	
static {
		
		PageFactory.initElements(DriverInstance.getDriverInstance(), ApplicationTextUI.class);
	}

	@FindBy(xpath = "//h2[text() = 'Customize Application Text']")
	public  static WebElement CustomizeUITextAssert;
	
	@FindBy(xpath = "//button[@id = 'pt1:USma:0:MAnt1:0:pt1:rg1:0:cb1']")
	public  static WebElement SearchAndReplace_Enabled;
	
	@FindBy(xpath = "//button[(@id = 'pt1:USma:0:MAnt1:0:pt1:rg1:0:cb1') and (contains(@class , 'p_AFDisabled'))]")
	public  static WebElement SearchAndReplace_Disabled;
	
	@FindBy(xpath = "//span[contains(text() , 'You must be in a properly configured Sandbox to customize text')]")
	public  static WebElement UITPreviewMode;
		
	//Xpaths for "TextReplacement History" table
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']")
	public  static WebElement TextReplacementTable;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Created By']/ancestor::td[1]/following-sibling::td/descendant::span")
	public  static WebElement ReplacedHistory_UserName;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Singular Search']/ancestor::td[1]/following-sibling::td/descendant::span")
	public  static WebElement ReplacedHistory_SingularSearch;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Plural Search']/ancestor::td[1]/following-sibling::td/descendant::span")
	public  static WebElement ReplacedHistory_PluralSearch;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Updated Count']/ancestor::td[1]/following-sibling::td/descendant::span")
	public  static WebElement ReplacedHistory_Count;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Singular Replace']/ancestor::td[1]/following-sibling::td/descendant::span")
	public  static WebElement ReplacedHistory_SingularReplace;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Plural Replace']/ancestor::td[1]/following-sibling::td/descendant::span")
	public  static WebElement ReplacedHistory_PluralReplace;
	
	@FindBy(xpath = "//div[@class = 'AFStretchWidth af_listView']/descendant::div[@data-afrrk = 0]//descendant::label[text() = 'Language']/ancestor::td[1]/following-sibling::td/descendant::span")
	public  static WebElement ReplacedHistory_Language;
	
}//End of ApplicationTextUI class
*/