package pageDefinitions.UI.oracle.applcore.qa.Loader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ScheduleProcessObject {

    public ScheduleProcessObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[text()='Tools']")
    public WebElement toolsImageLink;

    @FindBy(xpath = "//a[text()='Scheduled Processes']")
    public WebElement processImageLink;

    @FindBy(xpath = "//span[contains(text(),'Schedule New Process')]")
    public WebElement scheduleprocess;

    @FindBy(xpath = "//input[contains(@id,'pt1:selectOneChoice2::content')]")
    public WebElement _nameInput;

    @FindBy(xpath = "//span[contains(text(),'Applcore csv upload')]")
    public WebElement descriptionText;

    @FindBy(xpath = "//*[contains(@title,'Search: Name')]")
    public WebElement searchIcon;

    @FindBy(xpath = "//a[contains(text(),'Search...')]")
    public WebElement search;

    @FindBy(xpath = "//input[contains(@id,'_afrLovInternalQueryId:value00::content')]")
    public WebElement nameInput;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    public WebElement search2;

    @FindBy(xpath = "//button[contains(@id,'lovDialogId::ok')]")
    public WebElement popupOK2;

    @FindBy(xpath = "//button[contains(@id,'pt1:snpokbtnid')]")
    public WebElement popupOK;

    @FindBy(xpath = "//input[contains(@id,'paramDynForm_Attribute1_ATTRIBUTE1::content')]")
    public WebElement fileName;

    @FindBy(xpath = "//input[contains(@id,'paramDynForm_Attribute2_ATTRIBUTE2::content')]")
    public WebElement account;

    @FindBy(xpath = "//select[contains(@id,'paramDynForm_Attribute3_ATTRIBUTE3::content')]")
    public WebElement fileType;

    @FindBy(xpath = "//div[contains(@id,'requestBtns:submitButton')]")
    public WebElement submit;

    @FindBy(xpath = "//div[contains(text(),'Confirmation')]")
    public WebElement confirm;

    @FindBy(xpath = "//button[contains(@id,'confirmationPopup:confirmSubmitDialog::ok')]")
    public WebElement jobOk;

    By jobOk_ = By.xpath("//button[contains(@id,'confirmationPopup:confirmSubmitDialog::ok')]");

    @FindBy(xpath = "//img[contains(@title,'Refresh')]")
    public WebElement refresh;

    @FindBy(xpath = "//span[contains(@id,'requestBtns:confirmationPopup:pt_ol1')]/label")
    public WebElement processIdLabel;

    @FindBy(xpath = "//a[contains(@id,'pt1:srRssdfl::_afrDscl')]")
    public WebElement expCollapseSearch;

    @FindBy(xpath = "//input[contains(@id,'pt1:srRssdfl:value10::content')]")
    public WebElement inputSearchField;

    @FindBy(xpath = "//button[contains(@id,'pt1:srRssdfl::search')]")
    public WebElement searchButton;

    @FindBy(xpath = "//span[text()='ESS process for Applcore csv file upload']")
    public WebElement firstRow;


}
