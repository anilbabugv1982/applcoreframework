package pageDefinitions.UI.oracle.applcore.qa.trees.ess;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import UtilClasses.UI.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

public class ScheduledProcesses {

    private WebDriver processDriver;
    private TreesUtils treesUtil;
    private ApplicationLogin aLoginInstance;
    private NavigationTaskFlows ntFInstance;
    private NavigatorMenuElements nMEInstance;

    public ScheduledProcesses(WebDriver driver) {
        PageFactory.initElements(driver, this);
        processDriver = driver;
        treesUtil = new TreesUtils(driver);
    }

    public static String processID;

    @FindBy(xpath = "//td[@class='af_column_data-cell']/ancestor::tr[1]")
    public static WebElement scheduledProcessRow;

    @FindBys({
            @FindBy(xpath = "//td[@class='af_column_data-cell']/ancestor::tr[1]/descendant::span[contains(@id,'panel:result')]")})
    public static List<WebElement> selectedRowValues;

    @FindBy(xpath = "//img[@title='Refresh']")
    public static WebElement img_Refresh;

    @FindBy(xpath = "//div[contains(@id,'panel:scheduleProcess')]/descendant::a")
    public static WebElement button_NewScheduledProcess;

    @FindBy(xpath = "//a[@title='Expand Search']")
    public static WebElement button_Expand;

    @FindBy(xpath = "//a[@title='Collapse Search']")
    public static WebElement button_Collapse;

    @FindBy(xpath = "//input[contains(@id,'srRssdfl:value10')]")
    public static WebElement input_ProcessID;

    @FindBy(xpath = "//button[text()='Search']")
    public static WebElement button_Search;

    @FindBy(xpath = "//a[@title='Succeeded']")
    public static WebElement status_Succeeded;

    public void verifyScheduleProcessPage() {
        CommonUtils.waitForPageLoad(processDriver);
        CommonUtils.hold(5);
        treesUtil.waitForElementToBeClickable(img_Refresh);
        treesUtil.waitForElementToBeClickable(button_NewScheduledProcess);
    }

    public void pollForStatusSuccess() {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(processDriver)
                .withTimeout(Duration.ofMinutes(40)).pollingEvery(Duration.ofMillis(3000))
                .ignoring(StaleElementReferenceException.class);

        Boolean element = wait.until(new Function<WebDriver, Boolean>() {

            @Override
            public Boolean apply(WebDriver t) {
                JavascriptExecutor js = (JavascriptExecutor) processDriver;
                CommonUtils.hold(5);
                ScheduledProcesses.img_Refresh.click();
                CommonUtils.hold(10);
                return ScheduledProcesses.status_Succeeded.isDisplayed();
            }
        });
    }

    public void verifyScheduledJobStatus() throws Exception {
        ntFInstance = new NavigationTaskFlows(processDriver);
        aLoginInstance = new ApplicationLogin(processDriver);
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().scheduledProcesses, processDriver);
        verifyScheduleProcessPage();
        if (ScheduledProcesses.button_Expand.isDisplayed() == true) {
            ScheduledProcesses.button_Expand.click();
            treesUtil.waitForElementToBeVisible(ScheduledProcesses.input_ProcessID);
            CommonUtils.hold(3);
        }
        ScheduledProcesses.input_ProcessID.clear();
        ScheduledProcesses.input_ProcessID.sendKeys(ScheduledProcesses.processID);
        ScheduledProcesses.button_Search.click();
        CommonUtils.hold(3);
        ScheduledProcesses.button_Collapse.click();
        pollForStatusSuccess();
        CommonUtils.hold(5);
    }

}
