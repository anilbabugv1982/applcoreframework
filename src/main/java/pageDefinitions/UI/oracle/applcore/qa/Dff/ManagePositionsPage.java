package pageDefinitions.UI.oracle.applcore.qa.Dff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Ashish Pareek
 *
 */

public class ManagePositionsPage {

	public ManagePositionsPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[contains(@id,'AP2:q1:value00::content')]")
	public static WebElement positionName;
	
	@FindBy(xpath = "//button[contains(@id,'AP2:q1::search')]")
	public static WebElement searchButton;
	
	@FindBy(xpath = "//div[contains(@id,'AP3:commandToolbarButton2') and @title='Edit']")
	public static WebElement editPositionDetails;
	
	@FindBy(xpath = "//tr[contains(@id,'AP3:UpdateBtn')]/td[text()='Update']")
	public static WebElement updateButton;
	
	@FindBy(xpath = "//button[contains(@id,'AP3:UpdDlg::ok')]")
	public static WebElement updateOkButton;
	
	@FindBy(xpath = "//button[contains(@id,'AP3:dialog3::ok')]")
	public static WebElement updateWarningOkButton;
	
	@FindBy(xpath = "//input[contains(@id,'ATT1_UK_001_SIMPLE__FLEX_EMPTY::content')]")
	public static WebElement att1_UK_001_Simple;
	
	@FindBy(xpath = "//input[contains(@id,'ATT2_UK_001_SIMPLE__FLEX_EMPTY::content')]")
	public static WebElement att2_UK_001_Simple;
	
	@FindBy(xpath = "//a[contains(@id,'ATT2_UK_001_SIMPLE__FLEX_EMPTY::glyph')]")
	public static WebElement att2_UK_001_SimpleCal;
	
	@FindBy(xpath = "//td[contains(@class,'af_chooseDate_today')]")
	public static WebElement att2_UK_001_Simple_CurrentDate;
	
	@FindBy(xpath = "//input[contains(@id,'POS_GLB__FLEX_EMPTY::content')]")
	public static WebElement POS_GLB;
	
	@FindBy(xpath = "//input[contains(@id,'posglobal1__FLEX_EMPTY::content')]")
	public static WebElement posglobal1;
	
	@FindBy(xpath = "//div[contains(@id,'tt1:save') and @title='Save']")
	public static WebElement saveButton;
	
	@FindBy(xpath = "//div[contains(@id,'tt1:review')]")
	public static WebElement reviewButton;
	
	@FindBy(xpath = "//button[contains(@id,'tt1:okConfirmationDialog')]")
	public static WebElement saveOkConfirmationButton;
	
	@FindBy(xpath = "//div[contains(@id,'tt1:submit') and @class='af_commandToolbarButton p_AFTextOnly']")
	public static WebElement submitButton;
	
	//button[contains(@id,'tt1:okWarningDialog')] - id won't work because on revisiting same position different warning popup comes after clicking on Submit button
	@FindBy(xpath = "//button[text()='es']")
	public static WebElement yesWarningButton;
	
	//button[contains(@id,'tt1:okConfirmationDialog')] - id won't work because on revisiting same position different warning popup comes after clicking on Submit button
	@FindBy(xpath = "//button[text()='O']/span[text()='K']")
	public static WebElement okConfirmationButton;
	
	@FindBy(xpath = "//a[text()='Delete the Saved Transaction and Start a New One']")
	public static WebElement deleteStartNew;
	
	@FindBy(xpath = "//div[contains(@class,'p_AFDisabled p_AFBusy')]")
	public static WebElement busyButton;

}
