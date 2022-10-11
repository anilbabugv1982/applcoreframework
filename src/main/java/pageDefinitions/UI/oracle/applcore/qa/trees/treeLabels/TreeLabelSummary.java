package pageDefinitions.UI.oracle.applcore.qa.trees.treeLabels;

import UtilClasses.UI.CommonUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class TreeLabelSummary {

    private WebDriver pageDriver;

    public TreeLabelSummary(WebDriver driver) {
        this.pageDriver = driver;
        PageFactory.initElements(pageDriver, this);
    }

    @FindBy(xpath = "//img[@title='Create']")
    public WebElement create;

    @FindBy(xpath = "//input[contains(@id,'fndLabelSummaryVCQueryId:value00::content')]")
    public WebElement treeStructureCodeField;

    @FindBy(xpath = "//a[@title='Search and Select: Tree Structure Code']")
    public WebElement searchDropDown;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    public WebElement search1;

    @FindBy(xpath = "//a[contains(text(),'Search')]")
    public WebElement search;

    @FindBy(xpath = "//input[contains(@id,\"AP1:treeStructureCodeId::content\")]")
    public WebElement treeStructureCode;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    public WebElement search2;

    @FindBy(xpath = "//button[contains(text(),'OK')]")
    public WebElement ok;

    @FindBy(xpath = "//label[contains(text(),'Short Name')]//..//following-sibling::td//input")
    public WebElement shortName;

    @FindBy(xpath = "//label[text()='Name']//..//following-sibling::td//input")
    public WebElement name;

    @FindBy(xpath = "//label[text()='Description']//..//following-sibling::td//textarea")
    public WebElement description;

    @FindBy(xpath = "//a[contains(@id,'inputDate1::glyph')]")
    public WebElement effectiveStartDate;

    @FindBy(xpath = "//td[@tabindex='0']")
    public WebElement todayDate;

    @FindBy(xpath = "//button[contains(text(),'s')]")
    public WebElement saveAndClose;

    @FindBy(xpath = "//button[@id='d1::msgDlg::cancel']")
    public WebElement okPopUp;

    @FindBy(xpath = "//button[contains(@id,'pt1:AP1:APb')]")
    public WebElement done;

    @FindBy(xpath = "//button[contains(@id,'AP1:treeStructureCodeId::lovDialogId::ok')][contains(text(),'OK')]")
    public WebElement searchOk;


    public void verifyManageTreeLabelsPage(WebDriver driver) {
        CommonUtils.waitForPageLoad(driver);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(create));
        wait.until(ExpectedConditions.elementToBeClickable(search2));
        Assert.assertTrue(create.isDisplayed() && search2.isDisplayed(), "ManageTreeStructures page not loaded");

    }
}
