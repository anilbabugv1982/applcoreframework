package pageDefinitions.UI.oracle.applcore.qa.trees.trees;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pageDefinitions.UI.oracle.applcore.qa.trees.ess.ScheduledProcesses;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

public class TreeAuditResult {
    private WebDriver driver;
    private TreesUtils treeUtils;

    public TreeAuditResult(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        treeUtils = new TreesUtils(driver);
    }

    @FindBy(xpath = "//div[contains(@id,'_ATp:cmdi4')]/descendant::a[1]")
    public WebElement button_OnlineAudit;

    @FindBy(xpath = "//div[@title='Schedule Audit']/descendant::a")
    public WebElement button_ScheduleAudit;

    @FindBy(xpath = "//button[contains(@id,'pt1:AP1:APb')]")
    public WebElement button_Done;

    @FindBy(xpath = "//div[@title='Refresh']/descendant::a")
    public WebElement button_Refresh;

    @FindBy(xpath = "//div[contains(@id,'_ATp:cmdi4')]/descendant::a[2]")
    private WebElement dropDownButton;

    @FindBy(xpath = "//div[contains(@id,'_ATp:mp1::ScrollBox')]/descendant::td[text()='Offline Audit']")
    private WebElement button_OfflineAudit;

    @FindBy(xpath = "//div[contains(@id,'auditJobTable::db')]/descendant::img")
    public WebElement auditStatus;

    @FindBy(xpath = "//button[contains(@id,'okdlgp')]")
    public WebElement accept_Confirmation;

    @FindBy(xpath = "//div[@class='af_messages_detail']")
    public WebElement message_ConfirmationPopup;

    @FindBy(xpath = "//button[@id='d1::msgDlg::cancel']")
    public WebElement offlineAction_Confirmation;

    public WebElement button_OfflineAudit() {
        dropDownButton.click();
        return button_OfflineAudit;
    }


    public String getProcessID() {
        String message = message_ConfirmationPopup.getText();
        System.out.println(message);
        String splitmessage[] = message.split(" ");
        System.out.println(splitmessage);
        ScheduledProcesses.processID = splitmessage[splitmessage.length - 1];
        return splitmessage[splitmessage.length - 1];
    }
}
