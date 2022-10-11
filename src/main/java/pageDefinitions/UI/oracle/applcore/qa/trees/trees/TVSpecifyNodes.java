package pageDefinitions.UI.oracle.applcore.qa.trees.trees;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

public class TVSpecifyNodes {

    private WebDriver pageDriver;
    private TreesUtils treeUtil;
    private TreeSummaryPage treeSummaryPage;

    public TVSpecifyNodes(WebDriver driver) {
        this.pageDriver = driver;
        PageFactory.initElements(this.pageDriver, this);
        treeUtil = new TreesUtils(this.pageDriver);
        treeSummaryPage = new TreeSummaryPage(this.pageDriver);
    }

    @FindBy(xpath = "//img[@title='Add']")
    public static WebElement img_add;

    @FindBy(xpath = "//div[contains(@id,'AP1:AH1:hierCol:_tbx')]/descendant::img[@title='Remove']")
    public static WebElement img_remove;

    @FindBy(xpath = "//img[@title='Export Nodes']")
    public static WebElement img_ExportNodes;

    @FindBys({@FindBy(xpath = "//tr[@class='af_table_data-row']")})
    public static List<WebElement> allNodes;

    @FindBy(xpath = "//button[text()='Add Tree Node']")
    public static WebElement button_AddTreeNod;

    @FindBy(xpath = "//button[contains(text(),'Sub')]")
    public static WebElement button_Submit;

    //@FindBy(xpath = "//span[text()='000-No Department-01 2003-01-01']/ancestor::div[contains(@id,'hierCol:tt1::db')]")
    //@FindBy(xpath = "//span[text()='0000-Balance Sheet-100 2000-01-01']/ancestor::div[contains(@id,'hierCol:tt1::db')]")
    @FindBy(xpath = "//div[contains(@id,'hierCol:tt1::db')]")
    public static WebElement selectNode;
    
    @FindBy(xpath = "//span[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:AH1:hierCol:tt1:0:ot2']")
    public static WebElement childNode;
    
    @FindBy(xpath = "//span[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:2:pt1:AP1:AH1:hierCol:tt1:1:ot2']")
    public static WebElement grandchildNode;

    @FindBy(xpath = "//a[contains(@id,'hierCol:tt1:0::di') and (@title='Expand' or @title='Collapse')]")
    public static WebElement button_ExpandOrCollapse;

    @FindBy(xpath = "//a[contains(@id,'hierCol:tt1:1::di') and (@title='Expand' or @title='Collapse')]")
    public static WebElement button_ExpandOrCollapse1;

    @FindBy(xpath = "//a[contains(@id,'hierCol:tt1:2::di') and (@title='Expand' or @title='Collapse')]")
    public static WebElement button_ExpandOrCollapse2;

    @FindBy(xpath = "//button[contains(text(),'OK')][contains(@id,'AP1:AH1:remView:remDiag::ok')]")
    public static WebElement deleteOk;

    @FindBy(xpath = "//span[text()='Creation of tree export data is complete. Download file to view contents.']")
    public static WebElement text_ConfirmationText;

    @FindBy(xpath = "//button[text()='Download']")
    public static WebElement button_Download;

    @FindBy(xpath = "//button[contains(@id,'hierCol:downloadokdlgp6')]")
    public static WebElement button_OK;

    @FindBy(xpath = "//button[contains(@id,'remView:remDiag::ok')]")
    public static WebElement deleteWaring_Confirmation;

    @FindBy(xpath = "//input[contains(@id,'remView:cascadeDelete')]")
    public static WebElement input_RemoveChildNodes;

    @FindBy(xpath = "//a[text()='View']")
    public static WebElement dropDown_View;


    public void verifySpecifyNodes(WebDriver driver) {
        CommonUtils.waitForPageLoad(driver);
        treeUtil.waitForElementToBeClickable(img_add);
        treeUtil.waitForElementToBeClickable(img_remove);
        Assert.assertTrue(img_add.isDisplayed() && img_remove.isDisplayed(), "Specify Nodes Page not loaded");
    }

    public WebElement selectNode(String nodeName, WebDriver driver) {
        System.out.println(nodeName);
        String locator = "//span[contains(text(),'" + nodeName + "')]/ancestor::div[contains(@id,'hierCol:tt1::db')]";
        //  String locator ="//span[contains(text(),'\" + nodeName + \"')]";
        return driver.findElement(By.xpath(locator));
    }

    public WebElement selectNode(String nodeName) {
        System.out.println(nodeName);
        String locator = "//span[contains(text(),'" + nodeName + "')]";
        return pageDriver.findElement(By.xpath(locator));
    }

    public WebElement selectTreeNode(String nodeName) {
        String[] nodeNameArray = nodeName.split("-");
        String locator = "//span[contains(text(),'" + nodeNameArray[1].trim() + "')]";
        return pageDriver.findElement(By.xpath(locator));
    }

    public WebElement selectView(String viewType) {
        dropDown_View.click();
        WebElement element;
        String locator = "//td[text()='" + viewType + "']";
        element = pageDriver.findElement(By.xpath(locator));
        treeUtil.waitForElementToBeClickable(element);
        return element;
    }

    public void acceptConfirmation() {
        treeSummaryPage.acceptConfirmation();
    }


    public WebElement selectParentNode(String nodeName){
        String locator = "//span[contains(text(),'" + nodeName + "')]";
        return pageDriver.findElement(By.xpath(locator));
    }


}
