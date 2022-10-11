package pageDefinitions.UI.oracle.applcore.qa.trees.treeStructures;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class TSSpecifyPerformanceOptions {
    public TSSpecifyPerformanceOptions(WebDriver driver) {

        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[contains(@id,'AP1:rowftbl::lovIconId')]")
    public static WebElement rowFlattendedTableDropDown;

    @FindBy(xpath = "//a[contains(@id,'AP1:colftbl::lovIconId')]")
    public static WebElement columnFlattendedTableDropDown;

    @FindBy(xpath = "//a[contains(text(),'Search')]")
    public static WebElement search;

    @FindBy(xpath = "//input[contains(@id,'AP1:rowftbl::_afrLovInternalQueryId:value00::content')]")
    public static WebElement rowFlattendedSearch;

    @FindBy(xpath = "//span[contains(@id,'AP1:rowftbl_afrLovInternalTableId:1:_afrColChild0')]")
    public static WebElement rowFlattendedValue;

    @FindBy(xpath = "//button[contains(text(),'OK')][contains(@id,'AP1:rowftbl::lovDialogId::ok')]")
    public static WebElement ok;

    @FindBy(xpath = "//button[contains(text(),'OK')][contains(@id,'AP1:colftbl::lovDialogId::ok')]")
    public static WebElement colOk;

    @FindBy(xpath = "//input[contains(@id,'AP1:colftbl::_afrLovInternalQueryId:value00::content')]")
    public static WebElement columnFlattendedSearch;

    @FindBy(xpath = "//span[contains(@id,'AP1:colftbl_afrLovInternalTableId:1:_afrColChild0')]")
    public static WebElement columnFlattendedValue;

    @FindBy(xpath = "//td[contains(@id,'AP1:colftbl::lovDialogId::_fcc')]//button[contains(text(),'OK')]")
    public static WebElement ok2;

    @FindBy(xpath = "//input[contains(@id,'AP1:colEO::content')]")
    public static WebElement columnFlattenedEntityObject;

    @FindBy(xpath = "//input[contains(@id,'AP1:biVO::content')]")
    public static WebElement biViewObject;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    public static WebElement search2;

    @FindBy(xpath = "//button[contains(@id,'AP1:tbb1:1:cni2')]")
    public static WebElement next;

    public void verifySpecifyPerformanceOptionsPage(WebDriver driver) {
        try {
            CommonUtils.waitForPageLoad(driver);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(rowFlattendedTableDropDown));
            wait.until(ExpectedConditions.elementToBeClickable(columnFlattendedTableDropDown));
            Assert.assertTrue(rowFlattendedTableDropDown.isDisplayed() && columnFlattendedTableDropDown.isDisplayed(), "TSSpecifyPerformanceOptions page not loaded");
        } catch (Exception e) {
            System.out.println("Error in verifySpecifyPerformanceOptionsPage");
            Log.info("Error in verifySpecifyPerformanceOptionsPage");
            Log.info(e.getMessage());
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    public void fillInformation(String rowFlattendTable, String columnFlattendtable, String entityObject, String biViwObj, WebDriver driver) throws Exception {
        System.out.println("Start of fill info for TSSpecifyPerformanceOptions page");
        Log.info("Start of fill info for TSSpecifyPerformanceOptions page");
        verifySpecifyPerformanceOptionsPage(driver);
        CommonUtils.explicitWait(rowFlattendedTableDropDown, "Click", "", driver);
        rowFlattendedTableDropDown.click();
        CommonUtils.hold(5);
        CommonUtils.explicitWait(search, "Click", "", driver);
        search.click();
        CommonUtils.hold(3);
        rowFlattendedSearch.clear();
        rowFlattendedSearch.sendKeys(rowFlattendTable);
        search2.click();
        CommonUtils.hold(3);
        CommonUtils.explicitWait(rowFlattendedValue, "Click", "", driver);
        rowFlattendedValue.click();
        ok.click();

        CommonUtils.hold(3);
        CommonUtils.explicitWait(columnFlattendedTableDropDown, "Click", "", driver);
        columnFlattendedTableDropDown.click();
        CommonUtils.hold(5);
        CommonUtils.explicitWait(search, "Click", "", driver);
        search.click();
        CommonUtils.hold(3);
        columnFlattendedSearch.clear();
        columnFlattendedSearch.sendKeys(columnFlattendtable);
        search2.click();
        CommonUtils.hold(2);
        CommonUtils.explicitWait(columnFlattendedValue, "Click", "", driver);
        columnFlattendedValue.click();
        colOk.click();
        CommonUtils.hold(3);
        columnFlattenedEntityObject.clear();
        columnFlattenedEntityObject.sendKeys(entityObject);
        CommonUtils.hold(3);
        biViewObject.clear();
        biViewObject.sendKeys(biViwObj);
        CommonUtils.hold(3);
        next.click();
        CommonUtils.hold(5);
        Log.info("End of fill info for TSSpecifyPerformanceOptions page");

        System.out.println("End of fill info for TSSpecifyPerformanceOptions page");

    }
}
