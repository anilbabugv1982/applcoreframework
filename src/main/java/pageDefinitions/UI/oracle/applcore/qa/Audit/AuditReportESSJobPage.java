package pageDefinitions.UI.oracle.applcore.qa.Audit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuditReportESSJobPage {

	public AuditReportESSJobPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[contains(@id,'paramDynForm_Attribute1_ATTRIBUTE1::glyph')]")
	public static WebElement fromDateCalendar;
	
	@FindBy(xpath = "//a[contains(@id,'paramDynForm_Attribute2_ATTRIBUTE2::glyph')]")
	public static WebElement toDateCalendar;
	
	@FindBy(xpath = "//tr[contains(@id,'paramDynForm_Attribute1_ATTRIBUTE1')]/following::td[@class='af_chooseDate_today p_AFSelected']")
	public static WebElement fromDateCurrentDate;
	
	@FindBy(xpath = "//tr[contains(@id,'paramDynForm_Attribute2_ATTRIBUTE2')]/following::td[@class='af_chooseDate_today p_AFSelected']")
	public static WebElement toDateCurrentDate;
	
	@FindBy(xpath = "//select[contains(@id,'paramDynForm_Attribute4_ATTRIBUTE4')]")
	public static WebElement auditProduct;
	
	@FindBy(xpath = "//body[contains(@style,'cursor: wait;')]")
	public static WebElement waitCursor;
	
	@FindBy(xpath = "//select[contains(@id,'paramDynForm_Attribute6_ATTRIBUTE6')]")
	public static WebElement auditBusinessObjectDD;
	
	@FindBy(xpath = "//select[contains(@id,'paramDynForm_Attribute8_ATTRIBUTE8')]")
	public static WebElement includeChildObjectDD;
	
	@FindBy(xpath = "//select[contains(@id,'paramDynForm_Attribute11_ATTRIBUTE11')]")
	public static WebElement outputFileFormatDD;
	
	@FindBy(xpath = "//a[contains(@id,'attachmentMoreLink')]")
	public static WebElement attachmentMoreLink;
	
	@FindBy(xpath = "//div[contains(@id,'addAttachmentDialog::_ttxt')]")
	public static WebElement attachmentDiagBoxHeader;
	
	@FindBy(xpath = "//button[contains(@id,'attachment1:dc_cb1')]")
	public static WebElement attachmentDiagBox_OkBtn;

}
