package pageDefinitions.UI.oracle.applcore.qa.trees.common;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import UtilClasses.UI.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageDefinitions.UI.oracle.applcore.qa.trees.ess.ScheduledProcesses;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.AddTreeNodePopup;
import pageDefinitions.UI.oracle.applcore.qa.trees.trees.TreesAuditResult;


public class TreesUtils {

    private WebDriver treesUtilDriver;
    private AddTreeNodePopup addTreeNodesPopUp;
    private WebDriverWait wait;
    private Actions actions;
    private ApplicationLogin aLoginInstance;
    private GlobalPageTemplate globalPageTemplate;
    private NavigationTaskFlows navigationTaskFlows;

    public TreesUtils(WebDriver driver) {

        treesUtilDriver = driver;
        PageFactory.initElements(driver, this);
        addTreeNodesPopUp = new AddTreeNodePopup(driver);
        wait = new WebDriverWait(treesUtilDriver, 60);
        globalPageTemplate = new GlobalPageTemplate(treesUtilDriver);
        navigationTaskFlows = new NavigationTaskFlows(treesUtilDriver);
        actions = new Actions(treesUtilDriver);
    }

    public void waitForElementToBeClickable(WebElement element) {
        CommonUtils.waitForPageLoad(treesUtilDriver);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToBeVisible(WebElement element) {
        CommonUtils.waitForPageLoad(treesUtilDriver);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementNotVisible(String elementLocator) {
        CommonUtils.waitForPageLoad(treesUtilDriver);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elementLocator)));
    }

    public void addMoreNodes() {
        actions.keyDown(Keys.LEFT_CONTROL).click(addTreeNodesPopUp.selectNode(AddTreeNodePopup.availableNodes.get(0)))
                .click(addTreeNodesPopUp.selectNode(AddTreeNodePopup.availableNodes.get(1)))
                .click(addTreeNodesPopUp.selectNode(AddTreeNodePopup.availableNodes.get(2))).keyUp(Keys.LEFT_CONTROL)
                .build().perform();

    }
    
    public void addMoreNodes1() {
        actions.keyDown(Keys.LEFT_CONTROL).click(addTreeNodesPopUp.selectNode(AddTreeNodePopup.availableNodes.get(2)))
                .click(addTreeNodesPopUp.selectNode(AddTreeNodePopup.availableNodes.get(3)))
                .click(addTreeNodesPopUp.selectNode(AddTreeNodePopup.availableNodes.get(4))).keyUp(Keys.LEFT_CONTROL)
                .build().perform();

    }
    
    public void addMoreNodes2() {
        actions.keyDown(Keys.LEFT_CONTROL).click(addTreeNodesPopUp.selectNode(AddTreeNodePopup.availableNodes.get(5)))
                .click(addTreeNodesPopUp.selectNode(AddTreeNodePopup.availableNodes.get(6)))
                .click(addTreeNodesPopUp.selectNode(AddTreeNodePopup.availableNodes.get(7))).keyUp(Keys.LEFT_CONTROL)
                .build().perform();

    }

    public void addMoreNodesWithoutScrolling() {
        actions.keyDown(Keys.LEFT_CONTROL).click(addTreeNodesPopUp.selectNode(AddTreeNodePopup.availableNodes.get(0)))
                .click(addTreeNodesPopUp.selectNodeWithoutScrolling(AddTreeNodePopup.availableNodes.get(1)))
                .click(addTreeNodesPopUp.selectNodeWithoutScrolling(AddTreeNodePopup.availableNodes.get(2))).keyUp(Keys.LEFT_CONTROL)
                .build().perform();

    }

    public void pollForButton_OK() {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(treesUtilDriver)
                .withTimeout(Duration.ofMinutes(40)).pollingEvery(Duration.ofMillis(3000))
                .ignoring(StaleElementReferenceException.class);

        Boolean element = wait.until(new Function<WebDriver, Boolean>() {

            @Override
            public Boolean apply(WebDriver t) {
                JavascriptExecutor js = (JavascriptExecutor) treesUtilDriver;
                ScheduledProcesses.img_Refresh.click();
                CommonUtils.hold(3);
                return TreesAuditResult.button_OK.isEnabled();
            }
        });
    }

    public void secureLogout() {
        aLoginInstance = new ApplicationLogin(treesUtilDriver);
        try {
            aLoginInstance.logout(treesUtilDriver);
            treesUtilDriver.quit();

        } catch (Exception ex) {
            aLoginInstance.launchURL(treesUtilDriver);
            CommonUtils.hold(2);
            aLoginInstance.logout(treesUtilDriver);
            treesUtilDriver.quit();
        }
    }

    public boolean isElementPresent(By by) {
        List<WebElement> elements = treesUtilDriver.findElements(by);
        return elements.size() > 0 ? true : false;
    }

    public boolean isElementPresent(WebElement element) {
        Optional<String> tagName = Optional.of(element.getTagName());
        return tagName.get() != null ? true : false;
    }

    public void navigateToTreesTaskflow(String taskflowName) {

        waitForElementToBeClickable(globalPageTemplate.navigatorButton);
        waitForElementToBeClickable(globalPageTemplate.userDropDownIcon);
        globalPageTemplate.userDropDownIcon.click();
        CommonUtils.hold(3);
        treesUtilDriver.findElement(By.xpath("//a[text()='Setup and Maintenance']")).click();
        CommonUtils.hold(3);
        navigationTaskFlows.navigateToAOLTaskFlows(taskflowName, treesUtilDriver);


    }

}