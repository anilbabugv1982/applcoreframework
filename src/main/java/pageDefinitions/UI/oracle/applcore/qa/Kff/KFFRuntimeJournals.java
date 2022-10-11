package pageDefinitions.UI.oracle.applcore.qa.Kff;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class KFFRuntimeJournals {

	private WebDriver driver;
	
	public KFFRuntimeJournals(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
//	public  WebDriverWait wait = new WebDriverWait(driver, 50);
	@FindBy(id="pt1:_UISmmLink::icon")
	public  WebElement navigatorButton;

	@FindBy(id="pt1:_UISnvr:0:nv_itemNode_general_accounting_journals")
	public  WebElement journalsIcon;
	
	@FindBy(xpath="//span[contains(text(),'Data Access Set')]/../../td[5]")
	public  WebElement existingDataAccessSet;
	
	@FindBy(xpath="//a[contains(text(),'Change')]")
	public  WebElement changeDataAccessSet;
	
	@FindBy(xpath="//label[contains(text(),'Data Access Set')]/../..//td[2]/select")
	public  WebElement selectDAS;
	
	@FindBy(xpath="//button[contains(text(),'OK')]")
	public  WebElement OKDASButton;
	
	@FindBy(xpath="//img[contains(@title,'Tasks')]")
	public  WebElement tasksButton;

	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:RAtl2")
	public  WebElement createJournal;

	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:RAtl1")
	public  WebElement manageJournal;


	@FindBy(xpath="//input[contains(@id, 'MAnt2:1:pt1:ap1:showLessJournalName::content')]")
	public  WebElement journalName;

	@FindBy(xpath="//label[text()='Category']/ancestor::tr[1]/descendant::input")
	public  WebElement categorySelect;


	@FindBy(xpath="//tr[contains(@id, 'MAnt2:1:pt1:ap1:sis3:userJeCategoryNameInputSearch1::item0')]")
	public  WebElement categorySelectAddition;


	@FindBy(xpath="//img[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:delete::icon')]")
	public  WebElement deleteJournalLine;
	
	@FindBy(xpath="//div[text()='Confirmation']")
	public  WebElement confirmationPopup;
	
	@FindBy(xpath="//a/span[contains(text(),'Save')]")
	public  WebElement saveButton;
	
	@FindBy(xpath="//a[@title='Save']")
	public  WebElement dropdown_Save;
	
	@FindBy(xpath="//td[contains(text(),'ave and Close')]")
	public  WebElement saveAndClose;

	@FindBy(xpath="//button[contains(@id, 'MAnt2:1:pt1:ap1:yesLineDelete')]")
	public  WebElement confirmDeleteJournalLine;

	@FindBy(xpath="//img[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:create::icon')]")
	public  WebElement createJournalLine;
	
	@FindBy(xpath="//h1[contains(text(),'Journal Lines')]")
	public  WebElement journalLinesHeader;
	
	@FindBy(xpath="//img[contains(@title,'Select: Account')]")
	public  WebElement kffIcon;

	@FindBy(xpath="//td/div[contains(text(),'Account')]")
	public  WebElement accountPopup;
	
/*	@FindBy(xpath="//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_general_accounting_journals:0:MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountSPOP_query:value00::lovIconId']")
	public  WebElement companyDropdown;
	
	@FindBy(xpath="//a[@id='_FOpt1:_FOr1:0:_FOSritemNode_general_accounting_journals:0:MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountSPOP_query:value00::dropdownPopup::popupsearch']")
	public  WebElement search;*/
	
	@FindBy(xpath="//button[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountRSBT')]")
	public  WebElement resetButtonLine1;
	
	@FindBy(xpath="//button[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:1:accountRSBT')]")
	public  WebElement resetButtonLine2;
	                             
	@FindBy(xpath="//button[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:account_confirmResetDialog_ok')]")
	public  WebElement okResetButtonL1;
	
	@FindBy(xpath="//button[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:1:account_confirmResetDialog_ok')]")
	public  WebElement okResetButtonL2;
	
	
	@FindBy(xpath="//div[contains(text(),'Warning')]")
	public  WebElement warningDialogBox;
	
	public  WebElement verifyCreatedSegmentPrompt(String timestamp){
		 
		WebElement element = driver.findElement(By.xpath("//span/label[contains(text(),'segmentPrompt"+timestamp+"')]/../input"));
	    return element;	 
	}

	public  WebElement CreatedSegmentPrompt;
	
	@FindBy(xpath="//button[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountCNL')]")
	public  WebElement cancelButton;

	@FindBy(xpath="//input[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountSPOP_query:value00::content')]")
	public  WebElement companySegmentInput;
	
	@FindBy(xpath="//input[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountSPOP_query:value10::content')]")
	public  WebElement departmentSegmentInput;
	
	@FindBy(xpath="//input[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountSPOP_query:value20::content')]")
	public  WebElement accountSegmentInput;
	
	@FindBy(xpath="//input[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountSPOP_query:value30::content')]")
	public  WebElement subaccountSegmentInput;
	
	@FindBy(xpath="//input[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountSPOP_query:value40::content')]")
	public  WebElement productSegmentInput;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountSEl")
	public  WebElement okButton;
	
	@FindBy(xpath="//button[contains(@id, 'MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:1:accountQRBT')]")
	public  WebElement searchButtonAccountPopupL2;
	
	@FindBy(xpath="//div[contains(@id,'account_kffSearchDialog')]/descendant::div[contains(@id,'accountSPOP_kffResultTable::db')]/descendant::tr[2]/td[1]")
	public  WebElement selectFirstRowL2;
	
	@FindBy(xpath="//span[contains(text(),'Company')]/../../../../../colgroup")
	public  WebElement getSegmentCount;
	
	@FindBy(xpath="//button[contains(@id,'accountSEl')]")
	public  WebElement okButtonAccountPopupL2;
	
	@FindBy(xpath="//label[contains(text(),'Entered Debit')]/../input")
	public  WebElement debitAmount;
	
	@FindBy(xpath="//label[contains(text(),'Entered Credit')]/../input")
	public  WebElement creditAmount;
	
	/*@FindBy(id="_FOpt1:_FOr1:0:_FOSritemNode_general_accounting_journals:0:MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:1:account")
	public  WebElement selectLineTwo;*/
	
	@FindBy(xpath="//td/span/span[contains(text(),'2')]")
	public  WebElement accountLineTwo;
	
	@FindBy(xpath="//td/span/span[contains(text(),'3')]")
	public  WebElement accountLineThree;
	
	@FindBy(xpath="//label[contains(text(),'Account')]/../input")
	public  WebElement accountInput;
//	
	@FindBy(xpath="//a/span[contains(text(),'ancel')]")
	public  WebElement cancelJButton1;
	
	@FindBy(xpath="//button[contains(@id,'accountCNL')]")
	public  WebElement cancelJButton;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:pt1:ap1:queryP:value00::content")
	public  WebElement journalInput;
	
	@FindBy(xpath="//button[contains(text(),'Search')]")
	public  WebElement searchJournal;
	
	public  WebElement verifyCreatedJournal(String timestamp){
		 
		WebElement element = driver.findElement(By.xpath("//td/a[contains(text(),'journal"+timestamp+"')]"));
	    return element;	 
	}
	
	@FindBy(xpath="//h1[contains(text(),'Journals')]")
	public  WebElement journalPage;
	
	public  void waitForJournalPageTobeLoaded() {
		KFFUtils.waitForElementToBeVisible(journalName,driver);
		KFFUtils.waitForElementToBeVisible(categorySelect,driver);
		KFFUtils.waitForElementToBeClickable(createJournalLine,driver);
	}
	
	public  void waitForConfirmationPopupTobePresent() {
		KFFUtils.waitForElementToBeVisible(confirmationPopup,driver);
		KFFUtils.waitForElementToBeClickable(confirmDeleteJournalLine,driver);
	}
	
	public  void verifyPopupConfirmation() {
		KFFUtils.waitForElementNotVisible("//div[text()='Confirmation']",driver);
		KFFUtils.waitForElementNotVisible("//button[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:pt1:ap1:yesLineDelete']",driver);
	}
}
