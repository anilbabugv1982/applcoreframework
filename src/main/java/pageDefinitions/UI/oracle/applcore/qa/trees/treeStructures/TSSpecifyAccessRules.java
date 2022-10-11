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

public class TSSpecifyAccessRules {
    public TSSpecifyAccessRules(WebDriver driver) {

        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(@id,'AP1:APsb2')]")
    public WebElement submit;

    @FindBy(xpath = "//button[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:4:pt1:AP1:cb3:0:_afrButtonStopNavItem']")
    public WebElement back;

    @FindBy(xpath = "//button[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:4:pt1:AP1:APc']")
    public WebElement cancel;

    public void verifySpecifyAccessRulesPage(WebDriver driver) {
        try {
            CommonUtils.waitForPageLoad(driver);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(submit));
            wait.until(ExpectedConditions.elementToBeClickable(back));
            Assert.assertTrue(submit.isDisplayed() && back.isDisplayed(), "tsSpecifyAccessRules page not loaded");
        } catch (Exception e) {
            System.out.println("Error in verifySpecifyAccessRulesPage");
            Log.info("Error in verifySpecifyAccessRulesPage");
            Log.info(e.getMessage());
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }
}
