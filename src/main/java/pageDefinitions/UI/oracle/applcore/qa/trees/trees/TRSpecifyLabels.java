package pageDefinitions.UI.oracle.applcore.qa.trees.trees;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

public class TRSpecifyLabels {

    private WebDriver pageDriver;
    private TreesUtils treesUtils;


    TRSpecifyLabels(WebDriver driver) {

        pageDriver = driver;
        PageFactory.initElements(driver, this);
        treesUtils = new TreesUtils(pageDriver);
    }

    @FindBy(xpath = "//label[text()='Name']/ancestor::tr[1]/descendant::input")
    public WebElement input_Name;

    @FindBy(xpath = "//label[text()='Code']/ancestor::tr[1]/descendant::input")
    public WebElement input_Code;

    @FindBy(xpath = "//div[text()='Confirmation']/ancestor::div[1]/descendant::button")
    public WebElement button_OK;

    @FindBy(xpath = "//button[contains(text(),'N')]")
    public WebElement button_Next;

    @FindBy(xpath = "//img[contains(@id,'addButton::icon')]/ancestor::a[1]")
    public WebElement img_add;

    @FindBy(xpath = "//label[text()='Labeling Scheme']/ancestor::tr[1]/descendant::span[2]")
    public WebElement text_LabelingScheme;

    @FindBy(xpath = "//a[@title='Move all items to: Selected Labels']")
    public WebElement img_ShuttleAll;

    @FindBy(xpath = "//button[contains(@id,'appTbl:eCatPgD0::ok')]")
    public WebElement labelPopup_buttonOK;

    public void verifySpecifyLabelsPage() {
        treesUtils.waitForElementToBeVisible(text_LabelingScheme);
        String text = text_LabelingScheme.getText();
        if (!("None".equalsIgnoreCase(text_LabelingScheme.getText()))) {
            treesUtils.waitForElementToBeClickable(img_add);
        }
        treesUtils.waitForElementToBeClickable(button_Next);
    }


}
