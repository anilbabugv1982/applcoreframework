package pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManageUserRoleMigrationPage {

	public ManageUserRoleMigrationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//button[normalize-space(text())='Done']")
	public static WebElement Done;
	
	@FindBy(xpath = "//div[contains(@class,'panelTab')]/a[contains(@id,'USR_NextRun::disAcr')]")
	public static WebElement yetToSyncTab;
	
	@FindBy(xpath = "//div[contains(@class,'af_panelTab')]/a[contains(@id,'USR_NextRun::disAcr') and @class='af_panelTabbed_tab-text-link p_AFSelected']")
	public static WebElement yetToSyncTabSelected;
	
	@FindBy(xpath = "//img[@title='Add Role']")
	public static WebElement addRoleButton;
	
	@FindBy(xpath = "//input[contains(@id,'it20::content')]")
	public static WebElement displayName;
	
	@FindBy(xpath = "//button[normalize-space(text())='Search']")
	public static WebElement roleSearchButton;
	
	@FindBy(xpath = "//button[normalize-space(text())='Add']")
	public static WebElement roleAddButton;
	
	@FindBy(xpath = "//img[contains(@id,'pc1:refresh::icon')]")
	public static WebElement roleRefresh;
	
	@FindBy(xpath = "//body[@style='cursor: wait;']")
	public static WebElement waitCursor;
	
	@FindBy(xpath = "//img[contains(@id,'delete::icon')]")
	public static WebElement roleDeleteButton;
	
	@FindBy(xpath = "//button[normalize-space(text())='Yes']")
	public static WebElement warningYesButton;
	
	

}
