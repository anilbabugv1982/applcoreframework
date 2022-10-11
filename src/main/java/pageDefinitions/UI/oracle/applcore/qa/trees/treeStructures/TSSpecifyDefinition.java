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


public class TSSpecifyDefinition {

    public TSSpecifyDefinition(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[contains(@id,'AP1:DefCodeCreate::content')]")
    public static WebElement codeTextField;

    @FindBy(xpath = "//*[contains(@id,'AP1:DefNameCreate::content')]")
    public static WebElement nameTextField;

    @FindBy(xpath = "//*[contains(@id,'AP1:DefAppCreate::content')]")
    public static WebElement applicationField;

    @FindBy(xpath = "//*[contains(@id,'AP1:DefDescriptionCreate::content')]")
    public static WebElement descriptionField;

    @FindBy(xpath = "//*[contains(@id,'AP1:DefCreationModeCreate::content')]")
    public static WebElement creationModeField;

//	@FindBy(xpath="//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:1:pt1:AP1:DefAllowMulTVCreate::Label0']")
//	public static WebElement allowMultipleActiveTreeVersionField3;

    @FindBy(xpath = "//label[contains(text(),'Allow multiple active tree versions')]")
    public static WebElement allowMultipleActiveTreeVersionField;

//	@FindBy(xpath="//*[contains(@id,'AP1:tbb1:1:cni2')]")
//	public static WebElement next;

    @FindBy(xpath = "//*[contains(text(),'Ne')][contains(@id,'AP1:tbb1:1:cni2')]")
    public static WebElement next;

    public void verifySpecifyDefinitionPage(WebDriver driver) {
        try {
            CommonUtils.waitForPageLoad(driver);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(codeTextField));
            wait.until(ExpectedConditions.elementToBeClickable(descriptionField));
            Assert.assertTrue(codeTextField.isDisplayed() && descriptionField.isDisplayed(), "SpecifyDefinnitation page not loaded");
        } catch (Exception e) {
            System.out.println("Error in verifySpecifyDefinitionPage");
            Log.info("Error in verifySpecifyDefinitionPage");
            Log.info(e.getMessage());
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    public void fillInformation(String ipName, String appName, String mode, WebDriver driver) {

        System.out.println("Start of fill info for TVSpecifyDefinition page");
        Log.info("Start of fill info for TVSpecifyDefinition page");
        verifySpecifyDefinitionPage(driver);
        TSSpecifyDefinition.codeTextField.sendKeys(ipName);
        TSSpecifyDefinition.nameTextField.sendKeys(ipName);
        CommonUtils.hold(2);
        TSSpecifyDefinition.applicationField.sendKeys(appName);
        TSSpecifyDefinition.descriptionField.sendKeys(ipName);
        CommonUtils.hold(2);
        TSSpecifyDefinition.creationModeField.sendKeys(mode);
        CommonUtils.hold(4);
        TSSpecifyDefinition.allowMultipleActiveTreeVersionField.click();
        CommonUtils.hold(3);
        TSSpecifyDefinition.next.click();
        CommonUtils.hold(3);
        Log.info("End of fill info for TVSpecifyDefinition page");
        System.out.println("End of fill info for TVSpecifyDefinition page");
    }

    public void fillInInformation(String name, String appName){
        System.out.println("Start of fill info for TVSpecifyDefinition page");
        Log.info("Start of fill info for TVSpecifyDefinition page");
        TSSpecifyDefinition.codeTextField.sendKeys(name);
        TSSpecifyDefinition.nameTextField.sendKeys(name);
        CommonUtils.hold(2);
        TSSpecifyDefinition.applicationField.sendKeys(appName);
        TSSpecifyDefinition.descriptionField.sendKeys(name);
        CommonUtils.hold(2);
        TSSpecifyDefinition.allowMultipleActiveTreeVersionField.click();
        CommonUtils.hold(3);
        TSSpecifyDefinition.next.click();
        CommonUtils.hold(3);
        Log.info("End of fill info for TVSpecifyDefinition page");
        System.out.println("End of fill info for TVSpecifyDefinition page");
    }


}
