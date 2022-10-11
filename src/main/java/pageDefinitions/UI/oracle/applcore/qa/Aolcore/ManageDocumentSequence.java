package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageDocumentSequence {	
	
	public ManageDocumentSequence(WebDriver docSeqPageDriver) {
		 PageFactory.initElements(docSeqPageDriver, this);
	}
	
	// Manage Document Sequence Category page webelements
	
	@FindBy(linkText="Manage Document Sequence Categories")
	public WebElement categoryTFLink;
	
	@FindBy(xpath="//input[contains(@id,'pt1:AP2:qryId1:value20')]")
	public WebElement categorySearch;
	
	@FindBy(xpath="//button[contains(@id,':APsv')]")
	public WebElement saveBtn;
	
	@FindBy(xpath="//button[contains(@id,'qryId1::search')]")
	public WebElement searchBtn;
	
	@FindBy(xpath="//button[contains(@id,':qryId1::reset')]")
	public WebElement resetBtn;
	
	@FindBy(xpath="//img[contains(@id,'_ATp:create::icon')]")
	public WebElement createBtn;
	
	@FindBy(xpath="//img[contains(@id,'_ATp:delete::icon')]")
	public WebElement deleteBtn;
	
	@FindBy(xpath="//select[contains(@id,'soc2::content')]")
	public WebElement applicationDD;
	
	@FindBy(xpath="//input[contains(@id,'it4::content')]")
	public WebElement categoryNameField;
	
	@FindBy(xpath="//input[contains(@id,'userModuleNameId::content')]")
	public WebElement moduleField;
	
	@FindBy(xpath="//input[contains(@id,'it3::content')]")
	public WebElement tableNameField;
	
	@FindBy(xpath="//label[text()='Description']/preceding-sibling::input[contains(@id,'it1::content')]")
	public WebElement descField;	
	
	@FindBy(xpath="//input[contains(@id,'it2::content')]")
	public WebElement categoryCodeField;
	
	
	// Manage Document Sequence page webelements	
	
	@FindBy(linkText="Manage Document Sequences")
	public WebElement docTFLink;
	
	@FindBy(xpath="//input[contains(@id,'qryId1:value00::content')]")
	public WebElement docNameSearch;

//	@FindBy(xpath="//input[contains(@id,'it1::content')]")
	@FindBy(xpath="//label[contains(text(),'Document Sequence Name')]//preceding-sibling::input[contains(@id,'it1::content')]")
	public WebElement docSeqNameField;
	
	@FindBy(xpath="//select[contains(@id,'soc3::content')]")
	public WebElement typeDD;
	
	@FindBy(xpath="//select[contains(@id,'soc5::content')]")
	public WebElement detTypeDD;
	
	@FindBy(xpath="//a[contains(@id,'id2::glyph')]")
	public WebElement startDate;
	
	@FindBy(xpath="//td[@tabindex='0']")
	public WebElement currentDate;	
	
	@FindBy(xpath="//img[contains(@id,'pt1:AP1:AT2:_ATp:create::icon')]")
	public WebElement assignCreateBtn;
	
	@FindBy(xpath="//img[contains(@id,'pt1:AP1:AT2:_ATp:delete::icon')]")
	public WebElement assignDeleteBtn;
	
	@FindBy(xpath="//input[contains(@id,'nameId::content')]")
	public WebElement assignmentNameField;
	
	@FindBy(xpath="//a[contains(@id,'id3::glyph')]")
	public WebElement assignstartDate1;

	@FindBy(xpath="//a[text()='Setup and Maintenance']")
	public WebElement setupAndMaintananceElement;
	
	

}
