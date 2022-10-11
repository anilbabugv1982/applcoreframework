package pageDefinitions.UI.oracle.applcore.qa.Redwood;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RedwoodPage {
	public RedwoodPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@title = 'My Team']")
	public WebElement myTeam;

	@FindBy(xpath = "//a/span[text() ='Show Filters']")
	public WebElement showFilter;

	@FindBy(xpath = "//td[@title ='Worker Type']/following-sibling::td//a[(text() ='Clear')]")
	public WebElement clear;

	@FindBy(xpath = "//label[(text() = 'Contingent worker')]")
	public WebElement contingentWorker;

	@FindBy(xpath = "//label[(text() = 'Employee')]")
	public WebElement employee;
	
	@FindBy(id="pt1:_UISnvr:0:nvgpgl1_groupNode_payables")
	public static WebElement expandPayablesButton;
	
	@FindBy(xpath = "//a[@title = 'Invoices']")
	public static WebElement invoices;
	
   @FindBy(xpath = "//a[contains(@id,'UISnvr:0:nvcl1')]")
	public  static WebElement showMore;

	
	@FindBy(id="pt1:_UISnvr:0:nvgpgl1_groupNode_receivables")
	public static WebElement expandReceivablesButton;
	
	@FindBy(id="pt1:_UISnvr:0:nvgpgl1_groupNode_workforce_management")
	public static WebElement expandMyClientGroup;
	
	@FindBy(xpath = "//a[@title = 'Accounts Receivable']")
	public static WebElement accountsReceivable;
	
	@FindBy(xpath = "//a[@title = 'Person Management']")
	public static WebElement personManagement;
	
	//@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:AP1:RWAF1:0:ITPdc1j_id_2:ITtile")
	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:pm1:r1:0:r1:0:ITPdc2j_id_1:ITtileSel")
	public static WebElement horizontalBar;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:AP1:RWAF1:0:ITPdc1j_id_1:ITtileSel")
	public static WebElement VerticalBar;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:MainAreaTab1::ti")
	public static WebElement dynamicTab;
	
	@FindBy(xpath ="//a[@title = 'Navigator']")
	public static WebElement navigatorButton;
	
	@FindBy(xpath = "//a[@title = 'Accounts Receivable']")
	public static WebElement infoTile;
	
	@FindBy(id="FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:pm1:r1:0:r1:0:ITPdtl:1:AT2:_ATp:ATt2:0:cl2")
	public static WebElement invoiceNumber;
	
	@FindBy(xpath = "//div[@title='Invoices']/h1")
	public WebElement headerFont;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:pm1:r1:0:r1:0:cl1")
	public static WebElement headerLink;
}
