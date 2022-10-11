package pageDefinitions.UI.oracle.applcore.qa.Attachments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContentServerPage {

	public ContentServerPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[contains(@class,'yuimenubaritemlabel') and text()='New Check-In']")
	public static WebElement newCheckInLink;
	
	@FindBy(xpath = "//h1[contains(text(),'Content Check-In Form')]")
	public static WebElement newCheckInPageHeading;
	
	@FindBy(xpath = "//td[contains(@class,'idcCheckinUpdateEntry')]/input[contains(@id,'dDocTitle')]")
	public static WebElement newCheckInTitleField;
	
	@FindBy(xpath = "//td[contains(@class,'idcCheckinUpdateEntry')]/textarea[contains(@id,'xComments')]")
	public static WebElement newCheckInCommentsField;
	
	@FindBy(xpath = "//td[contains(@class,'idcCheckinUpdateEntry')]/span/select[contains(@id,'dSecurityGroup')]")
	public static WebElement newCheckInSecurityGroup;
	
	//input[contains(@id,'xCollectionID')]
	@FindBy(xpath = "//input[contains(@value,'Browse...')]")
	public static WebElement newCheckInBrowse;
	
	@FindBy(xpath = "//input[contains(@id,'primaryFile')]")
	public static WebElement newCheckInPrimaryFileBrowse;
	
	@FindBy(xpath = "//input[@value='Check In']")
	public static WebElement CheckInButton;
	
	@FindBy(xpath = "//span[@class='infoLabel' and normalize-space(text())='Content ID:']/following-sibling::span[@class='tableEntry']")
	public static WebElement contentID;
	
	@FindBy(xpath = "//span[@class='infoLabel' and normalize-space(text())='Title:']/following-sibling::span[@class='tableEntry']")
	public static WebElement contentTitle;
	
	@FindBy(xpath = "//a[contains(@class,'yuimenubaritemlabel') and text()='Search']")
	public static WebElement searchLink;
	
	@FindBy(xpath = "//tr[@class='idcRowClass-dDocName idcRequiredEntry']/descendant::input[contains(@id,'dDocName')]")
	public static WebElement searchContentIDField;
	
	@FindBy(xpath = "//input[@name='submitQueryForm' and contains(@value,'Search')]")
	public static WebElement search_SearchBtn;
	
	@FindBy(xpath = "//span[contains(@id,'dDocTitle_label')]")
	public static WebElement contentInfo_Title;
	
	@FindBy(xpath = "//span[contains(@id,'xComments_label')]")
	public static WebElement contentInfo_Comments;
	
	@FindBy(xpath = "//h1[contains(text(),'Content Information')]")
	public static WebElement contentInformationPageHeading;
	

}
