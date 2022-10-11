package pageDefinitions.UI.oracle.applcore.qa.trees.treeStructures;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

public class TreeStructureSummary extends GetDriverInstance {

    private WebDriver pageDriver;
    private TreesUtils treeUtils;

    public TreeStructureSummary(WebDriver pageDriver) {
        this.pageDriver = pageDriver;
        PageFactory.initElements(pageDriver, this);
        treeUtils = new TreesUtils(pageDriver);
    }

    @FindBy(xpath = "//img[contains(@id,'ATp:btnCreate::icon')]")
    public WebElement img_Create;

    @FindBy(xpath = "//img[contains(@id,'ATp:btnDuplicate::icon')]")
    public WebElement img_Duplicate;

    @FindBy(xpath = "//img[contains(@id,'ATp:btnUpdate::icon')]")
    public WebElement img_Edit;

    @FindBy(xpath = "//img[contains(@id,'ATp:btnDelete::icon')]")
    public WebElement img_Remove;

    @FindBy(xpath = "//a[text()='Actions']")
    public WebElement actions;

    @FindBy(xpath = "//td[text()='Delete']")
    public WebElement delete;

    @FindBy(xpath = "//div[contains(@id,'dupDiag') and @class='af_dialog']")
    public WebElement duplicatePopup;

    @FindBy(xpath = "//label[text()='Name']/ancestor::td[1]/following-sibling::td[1]//input[contains(@id,'dupName::content')]")
    public WebElement duplicateTSName;

    @FindBy(xpath = "//label[text()='Code']/ancestor::td[1]/following-sibling::td[1]//input[contains(@id,'dupCode::content')]")
    public WebElement duplicateTSCode;

    @FindBy(xpath = "//button[text()='Save and Close']")
    public WebElement button_SaveAndClose;

    //    @FindBy(xpath = "//img[@alt='Create']")
//    public WebElement img_Create;
//
    @FindBy(xpath = "//input[contains(@id,'fndTreeStructureSummaryVCQueryId:value00::content')]")
    public WebElement input_TreeStructureCode;
//
    @FindBy(xpath = "//button[contains(@id,'fndTreeStructureSummaryVCQueryId::search')]")
    public WebElement button_Search;
//
//    @FindBy(xpath = "//img[contains(@id,'btnDelete::icon')]/ancestor::a")
//    public WebElement image_delete;
//
//    @FindBys({@FindBy(xpath = "//span[contains(text(),'AutoTree')]")})
//    public List<WebElement> matchedTreeStructures;
//
    @FindBy(xpath = "//button[contains(@id,'delDiag::yes')]")
    public WebElement button_Yes;

    @FindBy(xpath = "//a[text()='Actions']")
    public WebElement action_menu;

    @FindBy(xpath = "//td[text()='Quick Edit']")
    public WebElement action_QuickEdit;

    @FindBy(xpath = "//input[contains(@id,'qNodeSrch::content')]")
    public WebElement input_searchViewObject;

    @FindBy(xpath = "//div[contains(@id,'AP1:tsAT:delDiag') and @class='af_dialog']")
    public WebElement waringDialog;

    @FindBy(xpath = "//button[contains(@id,':pt1:AP1:APb')]")
    public WebElement button_AuditDone;

    @FindBy(xpath = "//td[text()='Active']")
    public WebElement action_SetStatus_Active;


    public WebElement selectAction(String action) {
        actions.click();
        WebElement element;
        String locator = "//td[text()='" + action + "']";
        element = pageDriver.findElement(By.xpath(locator));
        treeUtils.waitForElementToBeClickable(element);
        return element;
    }

    public void verifyDuplicateTSPopup() {
        treeUtils.waitForElementToBeVisible(duplicatePopup);
        treeUtils.waitForElementToBeVisible(duplicateTSCode);
        treeUtils.waitForElementToBeVisible(duplicateTSName);
        treeUtils.waitForElementToBeClickable(button_SaveAndClose);
    }

    public void acceptWarning() {
        treeUtils.waitForElementToBeVisible(waringDialog);
        treeUtils.waitForElementToBeClickable(button_Yes);
        button_Yes.click();
        treeUtils.waitForElementNotVisible("//button[contains(@id,'delDiag::yes')]");
        treeUtils.waitForElementNotVisible("//div[contains(@id,'AP1:tsAT:delDiag') and @class='af_dialog']");
        CommonUtils.hold(3);
    }


}
