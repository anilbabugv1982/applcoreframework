package pageDefinitions.UI.oracle.applcore.qa.trees.trees;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TreesAuditResult {

    public TreesAuditResult(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(@id,'okdlgp')]")
    public static WebElement button_OK;

    @FindBy(xpath = "//span[text()='Online Audit']")
    public static WebElement button_OnlineAudit;

    @FindBy(xpath = "//span[text()='Schedule Audit']")
    public static WebElement button_ScheduleAudit;

}
