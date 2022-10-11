package pageDefinitions.UI.oracle.applcore.qa.trees.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WarningDialog {

    private WebDriver pageDriver;
    private TreesUtils treesUtils;

    public WarningDialog(WebDriver pageDriver) {
        this.pageDriver = pageDriver;
        treesUtils = new TreesUtils(pageDriver);
    }

    @FindBy(xpath = "//button[contains(@id,'delDiag::yes')]")
    public WebElement button_Yes;

    @FindBy(xpath = "//div[contains(@id,'tsAT:delDiag') and @class='af_dialog']")
    public WebElement waringDialog;

    public void acceptWarning() {
        treesUtils.waitForElementToBeVisible(waringDialog);
        treesUtils.waitForElementToBeClickable(button_Yes);
        button_Yes.click();
        treesUtils.waitForElementNotVisible("//button[contains(@id,'delDiag::yes')]");
        treesUtils.waitForElementNotVisible("//div[contains(@id,'tsAT:delDiag') and @class='af_dialog']");
    }
}
