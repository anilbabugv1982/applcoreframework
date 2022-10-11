package pageDefinitions.UI.oracle.applcore.qa.trees.trees;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;


public class TVSpecifyDefinition {
    TreesUtils treeUtil;

    public TVSpecifyDefinition(WebDriver treesExportImportDriver) {
        PageFactory.initElements(treesExportImportDriver, this);
        treeUtil = new TreesUtils(treesExportImportDriver);
    }

    @FindBy(xpath = "//label[text()='Name']/ancestor::tr[1]/descendant::input")
    public static WebElement treeVersion_Name;

    @FindBy(xpath = "//label[text()='Description']/ancestor::tr[1]/descendant::textarea")
    public static WebElement description;

    @FindBy(xpath = "//label[text()='Note']/ancestor::tr[1]/descendant::textarea")
    public static WebElement note;

    @FindBy(xpath = "//label[text()='Code']/ancestor::tr[1]/descendant::input")
    public static WebElement treeVersion_Code;

    @FindBy(xpath = "//label[text()='Tree Structure']/ancestor::tr[1]/descendant::input")
    public static WebElement selectTreeStructure;

    @FindBy(xpath = "//input[contains(@id,'treeTable1:3:it1::content')]")
    public static WebElement parent_ValueSetCode;

    @FindBy(xpath = "//input[contains(@id,'treeTable1:6:it1::content')]")
    public static WebElement detail_ValueSetCode;

    @FindBy(xpath = "//span[text()='Save']")
    public static WebElement button_Save;

    @FindBy(xpath = "//button[contains(text(),'Ne')]")
    public static WebElement button_Next;

    @FindBy(xpath = "//a[contains(@id,'inputStartDate::glyph')]")
    public static WebElement startDate;

    @FindBy(xpath = "//td[@tabindex='0']")
    public static WebElement selectCurrentDate;

    @FindBy(xpath = "//button[@id='d1::msgDlg::cancel']")
    public static WebElement button_OK1;


    public static void specifyDefinition(String treeCode, String treeStructure) {
        treeVersion_Name.sendKeys(treeCode);
        treeVersion_Code.sendKeys(treeCode);
        selectTreeStructure.sendKeys(treeStructure);
    }


    public void verifySpecifyDefinition(WebDriver driver) {
        CommonUtils.waitForPageLoad(driver);
        treeUtil.waitForElementToBeVisible(treeVersion_Name);
        treeUtil.waitForElementToBeClickable(startDate);
        treeUtil.waitForElementToBeClickable(button_Next);
        Assert.assertTrue(treeVersion_Name.isDisplayed() && startDate.isDisplayed() && button_Next.isDisplayed(), "Specify Definition Page not loaded");
    }

    public void enterDetails(String treeVersionCode) {
        treeVersion_Name.click();
        treeVersion_Name.sendKeys(treeVersionCode);
        startDate.click();
        treeUtil.waitForElementToBeClickable(selectCurrentDate);
        selectCurrentDate.click();
        treeUtil.waitForElementNotVisible("//td[@tabindex='0']");
        button_Next.click();
    }
}
