/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;

/**
 * @author kkoti
 *
 */
public class CreateTab extends CreatePage{
	
	public CreateTab(WebDriver cTDriver) {
		super(cTDriver);
		PageFactory.initElements(cTDriver, this);
	}
	
	@FindBy(xpath = "//label[text()='Name']/ancestor::tr[1]/descendant::input")
	private WebElement name;


	@FindBy(xpath = "//label[text()='Icon']/ancestor::tr[1]/descendant::a")
	private WebElement icon;

	/*@FindBy(xpath = "//label[text()='Application Role']/ancestor::tr[1]/descendant::select")
	private WebElement applicationRole;*/

	@FindBy(xpath = "//label[text()='Web Page']/ancestor::tr[1]/descendant::input")
	public WebElement webPage;

	@FindBy(xpath = "//span[contains(text(),'ancel')]")
	private WebElement Cancel;

	@FindBy(xpath = "//button[contains(@id , 'sp1:dg1::ok')]")
	private WebElement OK;

	@FindBy(xpath = "//img[contains(@src, 'grid_matte_bankcycle.png')]")
	private WebElement FusePageTabIcon;

	@FindBy(xpath = "//img[contains(@src, 'grid_matte_airplane.png')]")
	private WebElement NewPageTabIcon;

	@FindBy(id = "_FOpt1:_FOr1:0:_FOSritemNode_tools_new_pages:0:MAnt2:1:sp1:cil3j_id_2::icon")
	private WebElement icon3;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSritemNode_tools_new_pages:0:MAnt2:1:sp1:cb1")
	private WebElement addTab;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSritemNode_tools_new_pages:0:MAnt2:1:sp1:ctb1")
	public WebElement ctDelete;
	
	@FindBy(xpath="//img[@title = 'Simplified Page']")
	public WebElement tABpAGEiMGiCON;

	@FindBy(xpath="//input[contains(@id , 'sp1:soc1::content')]")
	public WebElement hCMtABaPPLICATIONrOLE;

	/*public void selectApplicationRole(String value) {
		CommonUtils.selectDropDownElement(applicationRole, value);
	}*/
	
	public void createTab(String tabName, String applicationRole, String webFieldValue, WebDriver driver) {
	
		name.sendKeys(tabName);
		addtabDef(tabName);
		CommonUtils.hold(3);
		//tABpAGEiMGiCON.click();
		name.click();
		CommonUtils.hold(5);
		icon.click();
		try {
			CommonUtils.explicitWait(FusePageTabIcon, "Click", "",driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FusePageTabIcon.click();
		CommonUtils.hold(5);
		OK.click();
		CommonUtils.hold(3);
		try {
			System.out.println("Adding Application Role to newly created tab");
				selectApplicationRole(applicationRole);
			System.out.println("Added Application Role tab");
		}catch(Exception arte) {
			System.out.println("Application Role WebElement doesn't exit. Adding Application role to an input element");
				hCMtABaPPLICATIONrOLE.sendKeys(applicationRole);
			System.out.println("Added Application Role tab having input element");
		}
		CommonUtils.hold(3);
		webPage.sendKeys(webFieldValue);
		CommonUtils.hold(5);
		saveAndClose.click();
	}

}//End of CreateTab Class
