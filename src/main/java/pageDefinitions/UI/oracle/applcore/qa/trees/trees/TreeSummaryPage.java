package pageDefinitions.UI.oracle.applcore.qa.trees.trees;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;
import org.testng.Assert;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

import java.util.List;

public class TreeSummaryPage {

	private TreesUtils treeUtils;
	private WebDriver pageDriver;

	public TreeSummaryPage(WebDriver driver) {
		pageDriver = driver;
		PageFactory.initElements(driver, this);
		treeUtils = new TreesUtils(driver);
	}

	@FindBy(xpath = "//img[@alt='Create Tree']")
	public WebElement createTree;

	@FindBy(xpath = "//span[text()='Submit']")
	public WebElement submit;

	@FindBy(xpath = "//label[text()='Tree Code']/ancestor::tr[1]/descendant::input")
	public WebElement input_TreeCode;

	@FindBy(xpath = "//label[text()='Duplicate Tree Code']/ancestor::td[1]/following-sibling::td[1]//input")
	public WebElement duplicateTreeCode;

	@FindBy(xpath = "//label[text()='Duplicate Tree Name']/ancestor::td[1]/following-sibling::td[1]//input")
	public WebElement duplicateTreeName;

	@FindBy(xpath = "//button[text()='Save and Close']")
	public WebElement saveAndClose;

	@FindBy(xpath = "//span[contains(text(),'Submit')]/ancestor::a[1]")
	public WebElement button;

	@FindBy(xpath = "//a[text()='Actions']")
	public WebElement actions;

	@FindBy(xpath = "//td[text()='Delete']")
	public WebElement delete;

	@FindBy(xpath = "//button[contains(@id,'delDiag::yes')]")
	public WebElement button_Yes;

	@FindBy(xpath = "//td[text()='Create Tree']/ancestor::tr[1]/following-sibling::tr[1]/td[2]")
	public WebElement create2;

	@FindBy(xpath = "//div[contains(@id,'fndTreeSearchVCQueryResultId::db')]/descendant::td[1]")
	public WebElement selectTree;

	@FindBy(xpath="//div[contains(@id,'fndTreeSearchVCQueryResultId::db')]/descendant::tr[contains(@class,'p_AFSelected')]")
	public WebElement treeSelected;

	@FindBy(xpath = "//div[contains(@id,'dupTRDiag') and @class='af_dialog']")
	public WebElement duplicatePopup;

	@FindBy(xpath = "//div[text()='Confirmation' and @class='af_dialog_title']")
	public WebElement confirmationDialog;

	@FindBy(xpath = "//button[contains(@id,'d1::msgDlg::cancel')]")
	public WebElement accept_Confirmation;

	@FindBy(xpath = "//button[contains(@id,'okdlgp1')]")
	public WebElement accept_Confirmation1;

	@FindBy(xpath = "//div[contains(@id,'AP1:ATT2:delDiag') and @class='af_dialog']")
	public WebElement waringDialog;

	@FindBy(xpath = "//tr[contains(@id,'ATTp:commandMenuItem')]/descendant::td[text()='Create Tree Version']")
	public WebElement action_CreateTreeVersion;

	@FindBy(xpath = "//td[text()='Active']")
	public WebElement action_SetStatus_Active;

	@FindBy(xpath = "//td[text()='Row Flattening']")
	public WebElement action_Flatten_RowFlatterning;

	@FindBy(xpath = "//td[text()='Column Flattening']")
	public WebElement action_Flatten_ColumnFlatterning;

	@FindBy(xpath = "//a[text()='atg_version']/ancestor::td[1]/following-sibling::td[1]")
	public WebElement selectVersion;

	@FindBy(xpath = "//a[contains(@id,'p:fndTreeSearchVCQueryResultId') and (@title='Expand' or @title='Collapse')]")
	public WebElement button_ExpandOrCollapse;

	@FindBy(xpath = "//input[contains(@id,'dupTVName::content')]")
	public WebElement duplicateTreeVersionName;

	@FindBy(xpath = "//div[@title='Manage Department Trees']")
	public static WebElement title_Page;

	@FindBy(xpath = "//button[text()='Search']")
	public static WebElement button_Search;

	@FindBy(xpath = "//a[text()='Actions']")
	public static WebElement menu_Actions;

	@FindBy(xpath = "//a[text()='Fusion HCM Development']/ancestor::div[contains(@id,'fndTreeSearchVCQueryResultId::db')]")
	public static WebElement select_Record;

	@FindBy(xpath = "//td[text()='Flatten']")
	public static WebElement action_Flatten;

	@FindBy(xpath = "//td[text()='Column Flattening']")
	public static WebElement action_ColumnFlatten;

	@FindBy(xpath = "//td[text()='Row Flattening']")
	public static WebElement action_RowFlatten;

	@FindBy(xpath = "//td[text()='Audit']")
	public static WebElement action_Audit;

	@FindBy(xpath = "//span[contains(@id,'requestBtns:confirmationPopup')]/descendant::label")
	public static WebElement jobnumber;

	@FindBy(xpath = "//div[contains(@id,'requestBtns:submitButton')]")
	public static WebElement button_Submit_ScheduleFlattening;

	@FindBy(xpath = "//td[@class='af_commandMenuItem_menu-item-text'][contains(text(),'Edit')]")
	public static WebElement action_Edit;

	@FindBy(xpath = "//td[@class='af_commandMenuItem_menu-item-text'][contains(text(),'Delete')]")
	public static WebElement action_Delete;

	@FindBy(xpath = "//button[contains(@id,'msgDlg::cancel')]")
	public WebElement accept_Confirmation_1;

	@FindBy(xpath = "//button[contains(@id,'delDiag::yes')]")
	public static WebElement accept_WarningPopup;

	@FindBy(xpath = "//button[contains(@id,'delDiag::no')]")
	public static WebElement dismiss_WarningPopup;

	@FindBy(xpath = "//div[@title='Schedule Flattening']/descendant::a[1]")
	public static WebElement button_ScheduleFlatterning;

	@FindBy(xpath = "//div[@title='Schedule Flattening']/descendant::a[2]")
	public static WebElement dropDown_ScheduleFlatterning;

	@FindBy(xpath = "//td[text()='Schedule Force Flattening']")
	public static WebElement button_ScheduleForceFlatterning;

	@FindBy(xpath = "//div[contains(@id,'submitButton')]")
	public static WebElement popup_Submit;

	@FindBy(xpath = "//button[contains(@id,'confirmationPopup:confirmSubmitDialog::ok')]")
	public static WebElement confirmationPopup_OK;

	@FindBy(xpath = "//div[contains(@id,'scheduleRequesttaskflow1:requestBtns:confirmationPopup')]/descendant::label")
	public static WebElement message_ConfirmationPopup;

	@FindBy(xpath = "//button[contains(@id,'pt1:AP1:ATT2:cb2')]")
	public static WebElement editOk;

	@FindBy(xpath = "//span[text()='Schedule Audit']/parent::a")
	public static WebElement button_ScheduleAudit;

	@FindBy(xpath = "//a[text()='Fusion HCM Development']/..//..//..//following-sibling::td//span[contains(text(),'FHD')]")
	public static WebElement select_Record2;

	@FindBys({ @FindBy(xpath = "//label[contains(@id,'dupTVTable')]") })
	public List<WebElement> duplicatePopup_treeVersionList;

	@FindBy(xpath = "//div[contains(@id,'dupTVTable::db')]/descendant::input[2]")
	public WebElement duplicatePopup_Input_TreeVersion;

	@FindBy(xpath = "//label[contains(@id,'dupTVTable')]")
	public WebElement duplicatePopup_checkbox_TreeVersion;

	public void verifyPageLoaded() {

	}

	public WebElement treeVersion(String treeVersionName) {
		return pageDriver.findElement(
				By.xpath("//a[text()='" + treeVersionName + "']/ancestor::td[1]/following-sibling::td[1]"));
	}

	public WebElement selectAction(String action) {
		actions.click();
		WebElement element;
		if (!("Create Tree Version".equalsIgnoreCase(action))) {
			String locator = "//td[text()='" + action + "']";
			element = pageDriver.findElement(By.xpath(locator));
		} else {
			element = action_CreateTreeVersion;
		}

		treeUtils.waitForElementToBeClickable(element);
		return element;
	}

	public void verifyDuplicateTreePopup() {
		treeUtils.waitForElementToBeVisible(duplicatePopup);
		treeUtils.waitForElementToBeVisible(duplicateTreeCode);
		treeUtils.waitForElementToBeVisible(duplicateTreeName);
		treeUtils.waitForElementToBeClickable(saveAndClose);
	}

	public void acceptConfirmation() {
		treeUtils.waitForElementToBeVisible(confirmationDialog);
		treeUtils.waitForElementToBeClickable(accept_Confirmation);
		accept_Confirmation.click();
		treeUtils.waitForElementNotVisible("//div[text()='Confirmation' and @class='af_dialog_title']");
		treeUtils.waitForElementNotVisible("//button[contains(@id,'d1::msgDlg::cancel')]");
	}

	public void tvAcceptConfirmation() {
		treeUtils.waitForElementToBeVisible(confirmationDialog);
		treeUtils.waitForElementToBeClickable(accept_Confirmation1);
		accept_Confirmation1.click();
		treeUtils.waitForElementNotVisible("//div[text()='Confirmation' and @class='af_dialog_title']");
	}

	public void acceptWarning() {
		treeUtils.waitForElementToBeVisible(waringDialog);
		treeUtils.waitForElementToBeClickable(button_Yes);
		button_Yes.click();
		treeUtils.waitForElementNotVisible("//button[contains(@id,'delDiag::yes')]");
		treeUtils.waitForElementNotVisible("//div[contains(@id,'AP1:ATT2:delDiag') and @class='af_dialog']");
		CommonUtils.hold(3);
	}

	public void selectTreeVersion(String versionName) {
		String title = button_ExpandOrCollapse.getAttribute("title");
		if ("Expand".equals(title)) {
			button_ExpandOrCollapse.click();
			CommonUtils.hold(5);
		}
		String locator = "//a[text()='" + versionName + "']/ancestor::td[1]/following-sibling::td[1]";
		By element = By.xpath(locator);
		WebElement elementRef = pageDriver.findElement(element);
		treeUtils.waitForElementToBeVisible(elementRef);
	//	CommonUtils.hold(3);
		elementRef.click();
		CommonUtils.hold(3);

	}

	public void expandTree() {
		String title = button_ExpandOrCollapse.getAttribute("title");
		if ("Expand".equals(title)) {
			button_ExpandOrCollapse.click();
			CommonUtils.hold(5);
		}
	}

	public void verifyManageAccountHierarchiesPage() {
		CommonUtils.waitForPageLoad(pageDriver);
		// treeUtils.waitForElementToBeVisible(title_Page);
		treeUtils.waitForElementToBeClickable(button_Search);
		treeUtils.waitForElementToBeVisible(input_TreeCode);
		// Assert.assertTrue(title_Page.isDisplayed() && button_Search.isDisplayed() &&
		// input_TreeCode.isDisplayed(),"GL tree page not loaded");
	}

	public void verifyManageDepartmentTreePage() {
		CommonUtils.waitForPageLoad(pageDriver);
		treeUtils.waitForElementToBeVisible(title_Page);
		treeUtils.waitForElementToBeClickable(button_Search);
		treeUtils.waitForElementToBeVisible(input_TreeCode);
		Assert.assertTrue(title_Page.isDisplayed() && button_Search.isDisplayed() && input_TreeCode.isDisplayed(),
				"Manage department Tree not loade");
	}

	public void verifyManageOrganizationTreesPage() {
		CommonUtils.waitForPageLoad(pageDriver);
		// treeUtils.waitForElementToBeVisible(title_Page);
		treeUtils.waitForElementToBeClickable(button_Search);
		treeUtils.waitForElementToBeVisible(input_TreeCode);
//        Assert.assertTrue(title_Page.isDisplayed() && button_Search.isDisplayed() && input_TreeCode.isDisplayed(),
//                "Manage Organization trees not loaded");
	}
}
