package pageDefinitions.UI.oracle.applcore.qa.Taxonamy;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import UtilClasses.UI.CommonUtils;



public class TaxonamyPage {
	
	private WebDriver driver;

	public TaxonamyPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	public static String taxid = CommonUtils.uniqueId();
	public static String familyname = "TaxFly_"+taxid;
	public static String applicationname = "TaxApp_"+taxid;
	public static String lbaname = "TaxLBA_"+taxid;
	
	@FindBy(xpath = "//a[@title='Expand']")
	public  static WebElement rootnodelink;
	
	@FindBy(xpath = "//div[text()='Oracle Fusion']")
	public  static WebElement fusionlink;
	
	@FindBy(xpath = "//div[text()='Oracle Fusion']//a")
	public  static WebElement fusionexpand;
	
	@FindBy(xpath = "//img[@title='Create Child Module']")
	public  static WebElement childnode;
	
	@FindBy(xpath = "//select[contains(@id,'taxCHPT:AP1:inputText31::content')]")
	public  static WebElement module;
	
	@FindBy(xpath = "//input[contains(@id,'taxCHPT:AP1:inputText41::content')]")
	public  static WebElement modulename;
	
	@FindBy(xpath = "//input[contains(@id,'taxCHPT:AP1:inputText51::content')]")
	public  static WebElement modulekey;
	
	@FindBy(xpath = "//input[contains(@id,'taxCHPT:AP1:inputText71::content')]")
	public  static WebElement alternativeid;
	
	@FindBy(xpath = "//input[contains(@id,'taxCHPT:AP1:inputText61::content')]")
	public  static WebElement productcode;
	
	@FindBy(xpath = "//input[contains(@id,'taxCHPT:AP1:inputText101::content')]")
	public  static WebElement usermodulename;
	
	@FindBy(xpath = "//input[contains(@id,'taxCHPT:AP1:inputText111::content')]")
	public  static WebElement description;
	
	@FindBy(xpath = "//select[contains(@id,'taxCHPT:AP1:inputText121::content')]")
	public  static WebElement seeddata;
	
	@FindBy(xpath = "//select[contains(@id,'taxCHPT:AP1:soc1::content')]")
	public  static WebElement domain;	
	
	@FindBy(xpath = "//select[contains(@id,'taxCHPT:AP1:soc2::content')]")
	public  static WebElement application;
	
	@FindBy(xpath = "//button[text()='Save']")
	public  static WebElement save;
	
	@FindBy(xpath = "//button[@id ='d1::msgDlg::cancel']")
	public  static WebElement ok;
	
	public static WebElement family(WebDriver driver)	
	
	{  
		
		WebElement familynameobj  = driver.findElement(By.xpath("//tr//div[text() = '"+familyname+"']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
	       
	     js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", familynameobj);
		return familynameobj;
		
	}
	
	
public static WebElement familyexpand(WebDriver driver)	
	
	{  
		
		WebElement familynameexpandobj  =driver.findElement(By.xpath("//tr//div[text() = '"+familyname+"']//a"));
		
		return familynameexpandobj;
		
	}

public static WebElement application(WebDriver driver)	

{  
	
	WebElement applicationnameobj  =driver.findElement(By.xpath("//tr//div[text() = '"+applicationname+"']"));
	JavascriptExecutor js = (JavascriptExecutor) driver;
       
     js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", applicationnameobj);
	return applicationnameobj;
	
}
	
	

	public static void selectmodule(WebElement element, String selectvalue) {
		
		Select profilevalue = new Select(element);
		profilevalue.selectByVisibleText(selectvalue);

		

	}

	
}
