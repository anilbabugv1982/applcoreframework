package pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResponsiveFusePlusPage {

	public ResponsiveFusePlusPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@title = 'My Team']")
	public WebElement myTeam;

	@FindBy(xpath = "//a/span[text() ='Show Filters']")
	public WebElement showFilter;

	@FindBy(xpath = "//td[@title ='Worker Type']/following-sibling::td//a[(text() ='Clear')]")
	public WebElement clear;

	@FindBy(xpath = "//label[(text() = 'Contingent worker')]")
	public WebElement contingentWorker;

	@FindBy(xpath = "//label[(text() = 'Employee')]")
	public WebElement employee;

	@FindBy(xpath = "//label[(text() = 'Nonworker')]")
	public WebElement nonworker;

	@FindBy(xpath = "//label[(text() = 'Pending worker')]")
	public WebElement pendingWorker;

	@FindBy(xpath = "//a[@title = 'Personalize Filters']")
	public WebElement personalizefilter;

	@FindBy(xpath = "//td[text() = 'Configure for Self']")
	public WebElement configureforself;

	@FindBy(xpath = "//td/span/label[text() ='Manager Type']/../../ancestor::td[2]//label[@class ='af_selectBooleanCheckbox_item-text']")
	public WebElement managertype;

	@FindBy(xpath = "//input[@placeholder = 'Search Person']")
	public WebElement seachperson;

	@FindBy(xpath = "//a[contains(@id,'hf03Cil')]")
	public WebElement search;

	@FindBy(xpath = "//a[contains(@id, 'showmore_groupNode')]")
	public WebElement showmore;

	@FindBy(xpath = "//input[contains(@id,'it3::content')]")
	public WebElement profileValue;

	@FindBy(xpath = "//button[text()='ave and Close']")
	public WebElement savendclosebtn;

	@FindBy(xpath = "//div[contains(@title,'Tasks')]/a")
	public WebElement Tasks;

	@FindBy(xpath = "//a[text()='Layout Sets']")
	public WebElement layoutSets;

	@FindBy(xpath = "//input[contains(@id,'qryId2:value00::content')]")
	public WebElement layoutSetInputName;

	@FindBy(xpath = "//button[contains(.,'Search')]")
	public WebElement layoutSetSearch;

	@FindBy(xpath = "//td//span[text() = 'Payroll Layout Set']/../../following-sibling::td")
	public WebElement payrollLayoutSetRow;

	@FindBy(xpath = "//div[contains(@title,'Duplicate')]/a")
	public WebElement duplicate;

	@FindBy(xpath = "//label[text() ='Responsive UI']")
	public WebElement labelResposiveUI;

	@FindBy(xpath = "//div[contains(@title,'Generate')]/a")
	public WebElement generate;

	@FindBy(xpath = "//td//label[text() ='Name']/../following-sibling::td//input")
	public WebElement payrollLayouSetName;

	@FindBy(xpath = "//span[text()='ave and Close']")
	public WebElement payrollLayoutSetSaveandClose;

	@FindBy(xpath = "//button[text()='OK']")
	public WebElement payrollLayoutSetOk;

	@FindBy(xpath = "//a[contains(@id,'AP1:SPdonei')]")
	public WebElement backButton;

	@FindBy(xpath = "//a[text()='Worker Time Entry Profiles']")
	public WebElement workerTimeEntryProfile;

	@FindBy(xpath = "//input[contains(@id,'qryId4:value00::content')]")
	public WebElement workerTimeEntryProfileName;

	@FindBy(xpath = "//button[contains(@id,'qryId4::search')]")
	public WebElement workerTimeEntryProfileSearch;

	@FindBy(xpath = "//td//a[text() = 'Payroll Time Entry Profile']/../following-sibling::td")
	public WebElement payrollTimeEntryProfileRow;

	@FindBy(xpath = "//input[contains(@id,'it1::content')]")
	public WebElement payrollTimeEntryProfileName;

	@FindBy(xpath = "//label[text() = 'Layout Set']/following-sibling::span/a")
	public WebElement layoutSetDropDown;

	@FindBy(xpath = "//a[contains(@id,'dropdownPopup::popupsearch')]")
	public WebElement popupSearch;

	@FindBy(xpath = "//input[contains(@id,'afrLovInternalQueryId:value00::content')]")
	public WebElement popUpLayoutSetName;

	@FindBy(xpath = "//button[contains(text(),'Search')]")
	public WebElement popLayoutSearch;

	@FindBy(xpath = "//button[contains(@id,'lovDialogId::ok')]")
	public WebElement popupOk;

	@FindBy(xpath = "//div[contains(@title,'Next')]/a")
	public WebElement next;

	@FindBy(xpath = "//div[contains(@id,'AP5:APscl')]/a")
	public WebElement payrollTimeEntryProfileSaveandClose;

	@FindBy(xpath = "//button[text() = 'Troubleshoot']")
	public WebElement trobleshoot;

	@FindBy(xpath = "//input[contains(@id,'qryId1:value20::content')]")
	public WebElement employeeName;

	@FindBy(xpath = "//td/a[contains(text(),'Payroll Time Entry')]/../following-sibling::td")
	public WebElement payRollTimeEntryRow;

	@FindBy(xpath = "//button[text() = 'Delete Override']")
	public WebElement deleteOverride;

	@FindBy(xpath = "//button[contains(@id,'d6::yes')]")
	public WebElement deleteOverrideYes;

	@FindBy(xpath = "//button[text() = 'Assign Profile to Person']")
	public WebElement assignProfileToPerson;

	@FindBy(xpath = "//input[contains(@id,'setupProfileNameId::content')]")
	public WebElement profileName;

	@FindBy(xpath = "//button[contains(@id,'d1::ok')]")
	public WebElement profileOk;

}
