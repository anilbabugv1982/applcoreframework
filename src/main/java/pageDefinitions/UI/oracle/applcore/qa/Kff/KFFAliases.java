package pageDefinitions.UI.oracle.applcore.qa.Kff;

import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import UtilClasses.UI.CommonUtils;


public class KFFAliases {
	
	private WebDriver driver;
	
	public KFFAliases(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	//private WebDriverWait aliasSetupWait = new WebDriverWait(DriverInstance.getDriverInstance(), 250);

	@FindBy(xpath = "//h1[contains(text(),'Manage Shorthand Aliases')]")
	public WebElement manageShorthandAliasesPageHeading;

	@FindBy(xpath = "//label[@class='af_query_criterion::label-text']")
	public WebElement chartOfAccountsLabel;

	@FindBy(xpath = "//select[@title='Operations Accounting Flex']")
	public WebElement chartOfAccounts;

	@FindBy(id = "pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:qryId1:value00::content")
	public WebElement selectChartOfAccounts;

	@FindBy(xpath = "//select[@title='Operations Accounting Flex']")
	public WebElement selectChartOfAccounts_Value;

	@FindBy(id = "pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:qryId1::search")
	public WebElement searchAliasesButton;

	@FindBy(xpath = "//table[@summary='Manage Shorthand Aliases']")
	public WebElement searchResultTable;

	@FindBy(xpath = "//img[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:AT3:_ATp:create::icon']")
	public WebElement addRow;

	@FindBy(xpath = "//label[contains(text(),'Name')]/../input")
	public WebElement aliasName;

	@FindBy(xpath = "//label[contains(text(),'Company')]/../input")
	public WebElement companyInput;

	@FindBy(xpath = "//label[contains(text(),'Department')]/../input")
	public WebElement departmentInput;

	@FindBy(xpath = "//label[contains(text(),'Account')]/../input")
	public WebElement accountInput;

	@FindBy(xpath = "//label[contains(text(),'Sub-Account')]/../input")
	public WebElement subAccountInput;

	@FindBy(xpath = "//label[contains(text(),'Product')]/../input")
	public WebElement productInput;

	@FindBy(xpath = "//a/span[contains(text(),'Save')]")
	public WebElement saveButton;

	@FindBy(xpath = "//a/span[contains(text(),'ancel')]")
	public WebElement cancelButton;

	@FindBy(xpath = "//a/span[contains(text(),'ancel')]")
	public WebElement cancelJButton1;

	@FindBy(xpath = "//label[contains(text(),'Alias')]")
	public WebElement aliaslabel;

    @FindBy(xpath = "//input[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:account_kffSearchSha::content']")
	public WebElement aliasInput;

	// @FindBy(xpath="//td/span/span[contains(text(),'1')]")
	// public WebElement accountLineOne;

    @FindBy(xpath = "//button[@id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountQRBT']")
	public WebElement searchButton;

	@FindBy(xpath = "//a[text()='Manage Shorthand Aliases']")
	public WebElement taskFlow_ManageShorthandAliases;

	@FindBy(xpath = "//a[@title='Search: Company']")
	public WebElement dropDown_Company;

	@FindBy(xpath = "//a[@title='Search: Product']")
	public WebElement dropDown_Product;

	@FindBy(xpath = "//a[@title='Search: Department']")
	public WebElement dropDown_Department;

	@FindBy(xpath = "//label[text()='Start Date']/following-sibling::a")
	public WebElement startDate;

	@FindBy(xpath = "//label[text()='End Date']/following-sibling::a")
	public WebElement endDate;

	@FindBy(xpath = "//img[@title='Query By Example']")
	public WebElement queryByExample;

	@FindBy(xpath = "//input[contains(@id,'afr_c2::content')]")
	public WebElement input_Filter;

	@FindBy(xpath = "//div[contains(@id,'AP1:APscl2')]")
	public WebElement button_SaveAndClose;

	@FindBy(xpath = "//a[@title='Clear All']")
	public WebElement img_ClearAll;

	public WebElement savedAliasName(String aliasname) {
		WebElement element = driver.findElement(By.xpath("//span[text()='" + aliasname + "']"));
		return element;

	}

	public void waitForEditableRowToPresent() {

		KFFUtils.waitForElementToBeVisible(aliasName,driver);
		KFFUtils.waitForElementToBeClickable(dropDown_Company,driver);
		KFFUtils.waitForElementToBeClickable(dropDown_Department,driver);
		KFFUtils.waitForElementToBeClickable(dropDown_Product,driver);

	}

	public void waitForRowToBeSaved(String aliasname) {
		CommonUtils.waitForPageLoad(driver);
		KFFUtils.waitForElementToBeClickable(saveButton,driver);
		KFFUtils.waitForElementNotVisible("//a[@title='Search: Department']", driver);
		verifyCreatedAlias(aliasname);
		img_ClearAll.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(3);
		queryByExample.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		KFFUtils.waitForElementNotVisible("//input[contains(@id,'afr_c2::content')]", driver);
	}

	public void verifyCreatedAlias(String aliasname) {
		KFFUtils.waitForElementToBeClickable(queryByExample, driver);
		queryByExample.click();
		CommonUtils.hold(3);
		CommonUtils.waitForPageLoad(driver);
		KFFUtils.waitForElementToBeVisible(input_Filter, driver);
		input_Filter.sendKeys(aliasname);
		input_Filter.sendKeys(Keys.ENTER);
		KFFUtils.waitForElementToBeVisible(savedAliasName(aliasname), driver);
		CommonUtils.hold(5);
	}

}
