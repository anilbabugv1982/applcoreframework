package pageDefinitions.UI.oracle.applcore.qa.trees.treeStructures;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

public class TSSpecifyDataSource {
    public TSSpecifyDataSource(WebDriver driver) {

        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[contains(@id,'AP1:labelingSchemeChoice::content')]")
    public static WebElement labelingScheme;

    @FindBy(xpath = "//*[contains(@id,'_ATTp:VOAddBtn::icon')]")
    public static WebElement addViewObjects;

    @FindBy(xpath = "//label[contains(text(),'Allow Multiple Root Nodes')]")
    public static WebElement allowMultipleRootNodes;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:_ATTp:VOAddBtn::icon')]")
    public static WebElement create;

    //Add data source page
    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:viewObjectNameInputText::content')]")
    public static WebElement viewObjectTextField;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:itnm::content')]")
    public static WebElement nameField;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:maxDepth::content')]")
    public static WebElement maximumDepth;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:it1::content')]")
    public static WebElement labelDataSource;

    @FindBy(xpath = "//label[contains(text(),'Allow duplicates')]")
    public static WebElement allowDuplicate2;

    @FindBy(xpath = "//div[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:addButton')]")
    public static WebElement addDataSourceParameter;


    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:it2::content')]")
    public static WebElement parameter1;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:soc1::content')]")
    public static WebElement parameterType1;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:it3::content')]")
    public static WebElement value1;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:it2::content')]")
    public static WebElement parameter2;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:soc1::content')]")
    public static WebElement parameterType2;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:it3::content')]")
    public static WebElement value2;


    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:it2::content')]")
    public static WebElement parameter3;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:soc1::content')]")
    public static WebElement parameterType3;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:it3::content')]")
    public static WebElement value3;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:0:sbc4::Label0')]")
    public static WebElement Mandatorylabel1;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:1:sbc4::Label0')]")
    public static WebElement Mandatorylabel2;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:paramAT:_ATp:parameterTable:2:sbc4::Label0')]")
    public static WebElement Mandatorylabel3;

    @FindBy(xpath = "//td[contains(@id,'AP1:pc0:eCatPgD0::_fcc')]//button[contains(text(),'O')]")
    public static WebElement ok;

    @FindBy(xpath = "//button[contains(text(),'Ne')]")
    public static WebElement next;

    @FindBy(xpath = "//select[contains(@id,'labelingSchemeChoice::content')]")
    public static WebElement labelSchemeDropDown;

    @FindBy(xpath = "//label[contains(text(),'Use non defined primary key columns')]")
    public static WebElement primaryKeyColumn;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:pk1ColumnChoice::content')]")
    public static WebElement pkColumn1;

    @FindBy(xpath = "//*[contains(@id,'AP1:pc0:r1:0:pk2ColumnChoice::content')]")
    public static WebElement pkColumn2;

    public void verifySpecifyDataSourcePage(WebDriver driver) {
        try {
            CommonUtils.waitForPageLoad(driver);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(addViewObjects));
            wait.until(ExpectedConditions.elementToBeClickable(labelingScheme));
            Assert.assertTrue(labelingScheme.isDisplayed() && addViewObjects.isDisplayed(), "TSSpecifyDataSource page not loaded");
        } catch (Exception e) {
            System.out.println("Error in verifySpecifyDataSourcePage");
            Log.info("Error in verifySpecifyDataSourcePage");
            Log.info(e.getMessage());
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    public void fillInformation(String labelScheme, String object, String depth, String labelDataSourcevalue, WebDriver driver) {

        System.out.println("Start of fill info for TSSpecifyDataSource page");
        Log.info("Start of fill info for TSSpecifyDataSource page");

        WebDriverWait wait = new WebDriverWait(driver, 30);
        verifySpecifyDataSourcePage(driver);
        CommonUtils.hold(5);
        //labelingScheme.sendKeys(labelScheme);
        Select se = new Select(labelSchemeDropDown);
        se.selectByVisibleText(labelScheme);
        ////
        CommonUtils.hold(5);
        allowMultipleRootNodes.click();
        create.click();
        wait.until(ExpectedConditions.elementToBeClickable(viewObjectTextField));
        CommonUtils.hold(4);
        viewObjectTextField.sendKeys(object);
        nameField.sendKeys(object);
        CommonUtils.hold(2);
        wait.until(ExpectedConditions.elementToBeClickable(maximumDepth));
        maximumDepth.sendKeys(depth);
        CommonUtils.hold(5);
        labelDataSource.sendKeys(labelDataSourcevalue);
        allowDuplicate2.click();
        CommonUtils.hold(2);

        primaryKeyColumn.click();
        CommonUtils.hold(6);
        labelDataSource.sendKeys(labelDataSourcevalue);
        Select sel = new Select(pkColumn1);
        sel.selectByVisibleText("Value");
        CommonUtils.hold(2);
//	sel.selectByValue("41");	CommonUtils.hold(2);
        Select sel2 = new Select(pkColumn2);
        sel2.selectByVisibleText("ValueSetCode");


        addDataSourceParameter.click();
        CommonUtils.hold(3);
        parameter1.sendKeys("VIEW_CRITERIA_NAME");
        CommonUtils.hold(3);
        sel = new Select(parameterType1);
        sel.selectByVisibleText("View Criteria");
        //parameterType1.sendKeys("View Criteria");
        CommonUtils.hold(3);
        value1.sendKeys("HierarchicalValueSetCriteria");
        CommonUtils.hold(3);
        addDataSourceParameter.click();
        CommonUtils.hold(6);


        parameter2.sendKeys("Bind_ValueSetCode");
        CommonUtils.hold(3);
//	parameterType2.sendKeys("Bound Value");
        sel = new Select(parameterType2);
        sel.selectByVisibleText("Bound Value");
        CommonUtils.hold(3);
        addDataSourceParameter.click();
        CommonUtils.hold(6);

        parameter3.sendKeys("Bind_SummaryFlag");
        CommonUtils.hold(3);
        sel = new Select(parameterType3);
        sel.selectByVisibleText("Bound Value");
//	parameter3.sendKeys("Bind_SummaryFlag");
//	parameterType3.sendKeys("Bound Value");
        CommonUtils.hold(3);
        value3.sendKeys("Y");

        CommonUtils.hold(6);
        Mandatorylabel1.click();
        CommonUtils.hold(3);
        Mandatorylabel2.click();
        CommonUtils.hold(3);
        Mandatorylabel3.click();
        CommonUtils.hold(2);
        ok.click();
        CommonUtils.hold(5);
        next.click();
        CommonUtils.hold(3);
        Log.info("End of fill info for TSSpecifyDataSource page");

        System.out.println("End of fill info for TSSpecifyDataSource page");

    }
}