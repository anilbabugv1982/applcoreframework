package pageDefinitions.UI.oracle.applcore.qa.trees.trees;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

public class TRSpecifyAccessRules {

    private WebDriver pageDriver;
    private TreesUtils treeUtils;

    TRSpecifyAccessRules(WebDriver driver) {
        pageDriver = driver;
        treeUtils = new TreesUtils(pageDriver);
        PageFactory.initElements(pageDriver, this);
    }

    @FindBy(xpath = "//span[contains(text(),'Submit')]/ancestor::a[1]")
    public WebElement menu_Submit;

    @FindBy(xpath = "//button[text()='Submit']")
    public WebElement button_Submit;

    public WebElement buttonSubmit() {
        WebElement submitButton = treeUtils.isElementPresent(By.xpath("//button[text()='Submit']")) == true ? button_Submit : menu_Submit;
        return submitButton;
    }

    public void verifyPageLoaded() {

    }
}
