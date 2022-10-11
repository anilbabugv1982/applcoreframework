package pageDefinitions.UI.oracle.applcore.qa.trees.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Dialogs {

    private WebDriver pageDriver;

    private TreesUtils treesUtils;

    @FindBy(xpath = "//div[text()='Confirmation' and @class='af_dialog_title']")
    public WebElement confirmationDialog;

    @FindBy(xpath = "//button[contains(@id,'d1::msgDlg::cancel')]")
    public WebElement accept_Confirmation;

    @FindBy(xpath = "//button[contains(@id,'delDiag::yes')]")
    public WebElement button_Yes;

    @FindBy(xpath = "//div[contains(@id,'tsAT:delDiag') and @class='af_dialog']")
    public WebElement waringDialog;


    public Dialogs(WebDriver pageDriver) {
        this.pageDriver = pageDriver;
        treesUtils = new TreesUtils(this.pageDriver);
    }

    public void acceptConfirmation() {
        treesUtils.waitForElementToBeVisible(confirmationDialog);
        treesUtils.waitForElementToBeClickable(accept_Confirmation);
        accept_Confirmation.click();
        treesUtils.waitForElementNotVisible("//div[text()='Confirmation' and @class='af_dialog_title']");
        treesUtils.waitForElementNotVisible("//button[contains(@id,'d1::msgDlg::cancel')]");
    }

    public void acceptWarning() {
        treesUtils.waitForElementToBeVisible(waringDialog);
        treesUtils.waitForElementToBeClickable(button_Yes);
        button_Yes.click();
        treesUtils.waitForElementNotVisible("//button[contains(@id,'delDiag::yes')]");
        treesUtils.waitForElementNotVisible("//div[contains(@id,'tsAT:delDiag') and @class='af_dialog']");
    }
}
