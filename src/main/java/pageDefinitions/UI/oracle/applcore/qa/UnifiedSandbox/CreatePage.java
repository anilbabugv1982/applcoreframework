/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;

/**
 * @author kkoti
 *
 */
public class CreatePage {

	private static ArrayList<String> tabs = new ArrayList<String>();

	private NewAndExistingPages naepCpInstance;
	public CreatePage(WebDriver cpDriver) {
		PageFactory.initElements(cpDriver, this);
		naepCpInstance = new NewAndExistingPages(cpDriver);
	}

	//@FindBy(xpath = "//label[text()='Name']/ancestor::tr[1]/descendant::input")
	@FindBy(xpath = "//input[contains(@id , 'sp1:it1::content')]")
	private WebElement name;

	//@FindBy(xpath = "//label[text()='Category Name']/ancestor::tr[1]/descendant::input")
	@FindBy(xpath = "//input[contains(@id , 'sp1:it2::content')]")
	private WebElement categoryName;

	//@FindBy(xpath = "//label[text()='Icon']/ancestor::tr[1]/descendant::a")
	@FindBy(xpath = "//img[contains(@id , 'sp1:cil1::icon')]")
	private WebElement icon;

	//@FindBy(xpath = "//label[text()='Application Role']/ancestor::tr[1]/descendant::select")
	@FindBy(xpath = "//select[contains(@id , 'sp1:soc1::content')]")
	private WebElement applicationRole;

	//@FindBy(xpath = "//label[text()='Web Page']/ancestor::tr[1]/descendant::input")
	@FindBy(xpath = "//input[contains(@id , 'sp1:it3::content')]")
	public WebElement webPage;

	//@FindBy(xpath = "//span[contains(text(),'ave and Close')]")
	@FindBy(xpath = "//div[contains(@id, 'sp1:APscl2')]/descendant::a")
	public WebElement saveAndClose;

	@FindBy(xpath = "//span[contains(text(),'ancel')]")
	private WebElement Cancel;

	@FindBy(xpath = "//button[contains(@id , 'sp1:dg1::ok')]")
	private WebElement OK;

	@FindBy(xpath = "//img[contains(@src, 'grid_matte_airplane.png')]")
	private WebElement icon1;

	@FindBy(xpath = "//img[contains(@src , 'grid_matte_bankcycle_rtl.png')]")
	private WebElement icon2;

	@FindBy(xpath = "//img[contains(@src , 'grid_matte_badgestar.png')]")
	private WebElement icon3;

	@FindBy(xpath = "//button[text() = 'dd Tab']")
	public WebElement addTabCPage;

	@FindBy(xpath = "//span[contains(text(),'elete Page')]")
	public WebElement cPdelete;

	@FindBy(id = "_FOpt1:_FOr1:0:_FOSritemNode_tools_new_pages:0:MAnt2:1:sp1:cb2")
	public WebElement confirmationDialog_CONFIRM;

	@FindBy(id = "_FOpt1:_FOr1:0:_FOSritemNode_tools_new_pages:0:MAnt2:1:sp1:SPb")
	public WebElement done;
	
	@FindBy(id= "_FOpt1:_FOr1:0:_FONSr2:0:_FOTr0:0:headerText::_afrTtxt")
	public WebElement customPage_converter;
	
	@FindBy(xpath="//img[@title='G2_tb2']")
	public WebElement customPage_tab;
	
	@FindBy(xpath="//span[contains(@id , 'sp1:wrnot1')]")
	public WebElement piwPreviewModeDialog;

	public void selectApplicationRole(String value) {
		CommonUtils.selectDropDownElement(applicationRole, value);
	}

	public void createPagedef(String pageName, String applicationRole) {
		CommonUtils.hold(5);
		name.sendKeys(pageName);
		naepCpInstance.addPage(pageName);
		if (CommonUtils.isElementPresent(categoryName)) {
			categoryName.sendKeys("ApplcoreQATest_Category");
		} else {
			System.out.println("Category Field not available");
		}
		CommonUtils.hold(3);
		icon.click();
		CommonUtils.hold(8);
		icon1.click();
		CommonUtils.hold(5);
		OK.click();
		CommonUtils.hold(5);
		selectApplicationRole(applicationRole);
		CommonUtils.hold(2);
		webPage.sendKeys("Test");
		CommonUtils.hold(5);
		saveAndClose.click();
	}

	public WebElement selectTabByName(String tab, WebDriver driver) {
		WebElement tabName = driver.findElement(By.xpath("//a[contains(@title,'"+tab+"')]"));
		return tabName;
	}

	public void addtabDef(String tabName) {
		tabs.add(tabName);
	}
}//End Of CreatePage Class
