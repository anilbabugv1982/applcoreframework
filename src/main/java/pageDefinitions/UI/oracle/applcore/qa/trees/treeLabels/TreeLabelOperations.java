package pageDefinitions.UI.oracle.applcore.qa.trees.treeLabels;

import UtilClasses.UI.CommonUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

import static org.testng.Assert.assertTrue;

public class TreeLabelOperations {

    private WebDriver webDriverRef;
    private TreeLabelSummary treeLabelSummaryPage;
    private TreesUtils treesUtils;

    public TreeLabelOperations(WebDriver driver) {
        this.webDriverRef = driver;
        treeLabelSummaryPage = new TreeLabelSummary(this.webDriverRef);
        treesUtils = new TreesUtils(webDriverRef);
    }

    public void searchTreeLabel(String treeLabelCode){

        treeLabelSummaryPage.okPopUp.click();CommonUtils.hold(3);
        treeLabelSummaryPage.treeStructureCodeField.sendKeys(treeLabelCode);
        treeLabelSummaryPage.search2.click();
        CommonUtils.hold(6);
        WebElement element=webDriverRef.findElement(By.xpath("//span[contains(text(),'"+treeLabelCode+"')]"));
        assertTrue(element.isDisplayed());
        System.out.println("label Verified sucessfully");

        treeLabelSummaryPage.done.click();
        CommonUtils.hold(6);
        System.out.println("End of creating tree label");
    }

    public void createTreeLabel(String treeLabelCode,String treeStructureCode){
        System.out.println("start of creating tree label");
        treeLabelSummaryPage.create.click();
        treesUtils.waitForElementToBeClickable(treeLabelSummaryPage.searchDropDown);
        CommonUtils.hold(3);
        treeLabelSummaryPage.treeStructureCode.click();
        treeLabelSummaryPage.treeStructureCode.sendKeys(treeStructureCode);
        treeLabelSummaryPage.treeStructureCode.sendKeys(Keys.TAB);
        CommonUtils.hold(3);
        treeLabelSummaryPage.shortName.click();
        CommonUtils.hold(3);
        treeLabelSummaryPage.shortName.sendKeys(treeLabelCode);
        treeLabelSummaryPage.name.sendKeys(treeLabelCode);
        treeLabelSummaryPage.description.sendKeys(treeLabelCode);
        treeLabelSummaryPage.effectiveStartDate.click();
        CommonUtils.hold(6);
        treeLabelSummaryPage.todayDate.click();
        treeLabelSummaryPage.saveAndClose.click();
        CommonUtils.hold(7);
    }
}
