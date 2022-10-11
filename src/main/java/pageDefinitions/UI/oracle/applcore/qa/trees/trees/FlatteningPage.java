package pageDefinitions.UI.oracle.applcore.qa.trees.trees;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import pageDefinitions.UI.oracle.applcore.qa.trees.ess.ScheduledProcesses;
import pageDefinitions.UI.oracle.applcore.qa.trees.common.TreesUtils;

public class FlatteningPage {

    private WebDriver pageDriver;
    private TreesUtils treeUtils;

    public FlatteningPage(WebDriver driver) {
        this.pageDriver = driver;
        PageFactory.initElements(driver, this);
        treeUtils = new TreesUtils(driver);
    }

    @FindBy(xpath = "//span[text()='Online Flattening']/ancestor::a[1]")
    public WebElement button_OnlineFlattening;

    @FindBy(xpath = "//td[text()='Force Flattening']")
    public WebElement button_OnlineForceFlattening;

    @FindBy(xpath = "//span[text()='Schedule Flattening']/ancestor::a[1]")
    public WebElement button_ScheduleFlattening;

    @FindBy(xpath = "//td[text()='Schedule Force Flattening']")
    public WebElement button_ScheduleForceFlattening;

    @FindBy(xpath = "//a[@class='af_commandToolbarButton_dropdown-icon-style' and @title='Online Flattening']")
    public WebElement dropDown_OnlineFlattening;

    @FindBy(xpath = "//a[@class='af_commandToolbarButton_dropdown-icon-style' and @title='Schedule Flattening']")
    public WebElement dropDown_ScheduleFlattening;

    @FindBy(xpath = "//td[text()='Offline Flattening']")
    public WebElement button_OfflineFlattening;

    @FindBy(xpath = "//td[text()='Offline Force Flattening']")
    public WebElement button_OfflineForceFlattening;

    @FindBy(xpath = "//button[contains(@id,'pt1:AP1:APb')]")
    public WebElement button_Done;

    @FindBy(xpath = "//div[contains(@id,'requestBtns:submitButton')]")
    public static WebElement button_Submit_ScheduleFlattening;

    @FindBy(xpath = "//button[contains(@id,'confirmationPopup:confirmSubmitDialog::ok')]")
    public static WebElement confirmationPopup_OK;

//    @FindBy(xpath="//div[contains(@id,'scheduleRequesttaskflow1:requestBtns:confirmationPopup')]/descendant::label")
//    public static WebElement message_ConfirmationPopup;

    @FindBy(xpath = "//div[contains(@id,'requestBtns:confirmationPopup')]/descendant::label")
    public static WebElement message_ConfirmationPopup;

    @FindBy(xpath = "//div[@class='af_messages_detail']")
    public static WebElement offlineAction_messageConfirmationPopup;

    @FindBy(xpath = "//button[@id='d1::msgDlg::cancel']")
    public static WebElement offlineAction_acceptConfirmationPopup;


    public String getProcessID() {

        String message = message_ConfirmationPopup.getText();
        System.out.println(message);
        String splitmessage[] = message.split(" ");
        System.out.println(splitmessage);
        ScheduledProcesses.processID = splitmessage[1];
        return "splitmessage[1]";
    }

    public String getOfflineProcessID() {
        String message = offlineAction_messageConfirmationPopup.getText();
        System.out.println(message);
        String splitmessage[] = message.split(" ");
        System.out.println(splitmessage);
        ScheduledProcesses.processID = splitmessage[splitmessage.length - 1];
        return splitmessage[splitmessage.length - 1];
    }

}
