package pageDefinitions.UI.oracle.applcore.qa.trees.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class ProgressCheck {
    private WebDriver pageDriver;
    private TreesUtils treesUtils;

    public ProgressCheck(WebDriver driver) {
        this.pageDriver = driver;
        this.treesUtils = new TreesUtils(pageDriver);
        PageFactory.initElements(pageDriver, this);
    }

    @FindBy(xpath = "//div[text()='Confirmation']/ancestor::div[1]/descendant::button[text()='OK']")
    public WebElement buttonOK;

    public void waitForProcessCheckCompletion() {
        treesUtils.waitForElementToBeVisible(buttonOK);
        buttonOK.click();
        treesUtils.waitForElementNotVisible("//div[text()='Confirmation']/ancestor::div[1]/descendant::button[text()='OK']");
    }
}
