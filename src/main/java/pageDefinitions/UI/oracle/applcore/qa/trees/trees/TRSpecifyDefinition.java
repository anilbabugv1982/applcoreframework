package pageDefinitions.UI.oracle.applcore.qa.trees.trees;

import UtilClasses.UI.CommonUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

public class TRSpecifyDefinition {
    private WebDriver pageDriver;
    private TreesUtils treesUtils;

    public TRSpecifyDefinition(WebDriver driver) {
        pageDriver = driver;
        PageFactory.initElements(driver, this);
        treesUtils = new TreesUtils(pageDriver);
    }

    @FindBy(xpath = "//label[text()='Name']/ancestor::tr[1]/descendant::input")
    public WebElement input_Name;

    @FindBy(xpath = "//label[text()='Code']/ancestor::tr[1]/descendant::input")
    public WebElement input_Code;

    @FindBy(xpath = "//label[text()='Tree Structure']/ancestor::tr[1]/descendant::input")
    public WebElement input_TreeStructure;

    @FindBy(xpath = "//label[text()='Tree Structure']/ancestor::tr[1]/descendant::span[2]")
    public WebElement readOnly_TreeStructure;

    @FindBy(xpath = "//button[contains(@id,'tbb1:1:cni2')]")
    public WebElement button_Next;

    @FindBy(xpath = "//input[contains(@id,'treeTable1:3:it1')]")
    public WebElement input_detailValueSetCode;

    @FindBy(xpath = "//input[contains(@id,'treeTable1:2:it1')]")
    public WebElement input_detailValueSetCode1;

    @FindBy(xpath = "//input[contains(@id,'treeTable1:6:it1')]")
    public WebElement input_parentValueSetCode;

    @FindBy(xpath = "//span[text()='Save']")
    public WebElement button_Save;

    @FindBy(xpath = "//a[contains(@id,'treeTable1:0::di')]")
    public WebElement expand_DetailValueSet;

    @FindBy(xpath = "//a[contains(@id,'treeTable1:1::di')]")
    public WebElement expand_ParentValueSet;


    public void verifyTRSpecifyDefinitioPage() {
        treesUtils.waitForElementToBeVisible(input_Name);
        //  treesUtils.waitForElementToBeVisible(input_Code);
        //  treesUtils.waitForElementToBeVisible(input_TreeStructure);
        treesUtils.waitForElementToBeClickable(button_Next);
    }

    public void enterDetails(String treeName, String mode) {
        verifyTRSpecifyDefinitioPage();
        input_Name.click();
        if ("create".equalsIgnoreCase(mode)) {
            input_Name.sendKeys(treeName);
            input_Code.sendKeys(treeName);
        } else {
            input_Name.clear();
            input_Name.sendKeys("UpdatedTreeName");
        }
        //  Assert.assertEquals(readOnly_TreeStructure.getText(),"HCM Geographic Hierarchy Tree Structure");
        CommonUtils.hold(3);
        button_Next.click();

    }

}
