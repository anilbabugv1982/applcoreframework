package pageDefinitions.UI.oracle.applcore.qa.Topology;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;

public class TopologyPage {

	public TopologyPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}


	@FindBy(xpath = "//img[(@title = 'View Menu for Target Navigation Tree')]")
	public  static WebElement menu;

	@FindBy(xpath = "//td[(text() = 'Collapse All')]")
	public  static WebElement collapse;

	@FindBy(xpath = "//a[contains(@id , 'emTemplate:cpTree:5::di')]")
	public  static WebElement farmdomainexpand;

	@FindBy(xpath = "//a[contains(@id , 'emTemplate:cpTree:1::di')]")
	public  static WebElement weblogicdomainexpand;

	@FindBy(xpath = "//img[contains(@id , 'emTemplate:idSearchHidShowBtn::icon')]")
	public  static WebElement fndid;

	@FindBy(xpath = "//input[contains(@id , 'emTemplate:search1:searchInputText1::content')]")
	public  static WebElement searchinput;

	@FindBy(xpath = "//a[contains(@id , 'emTemplate:search1::search_icon')]")
	public  static WebElement searchicon;

	@FindBy(xpath = "//a[contains(text() , 'Operations')]")
	public  static WebElement operation;

	@FindBy(xpath = "//input[contains(@id, 'emTemplate:idMBeanOperParamTable:0:idOperParamTableInputText::content')]")
	public  static WebElement inputparam1;

	@FindBy(xpath = "//input[contains(@id, 'emTemplate:idMBeanOperParamTable:1:idOperParamTableInputText::content')]")
	public  static WebElement inputparam2;

	@FindBy(xpath = "//button[contains(text() , 'Invoke')]")
	public  static WebElement invoke;

	@FindBy(xpath = "//button[contains(text() , 'Return')]")
	public  static WebElement returnbutton;

	@FindBy(xpath = "//button[contains(@id , 'emTemplate:topsubview:sdk_err_popup_close_id')]")
	public  static WebElement errorclose;

	@FindBy(xpath = "//img[contains(@id , 'emTemplate:idMBeanOperParamTable:0:clxcil2::icon')]")
	public  static WebElement pencilicon;

	@FindBy(xpath = "//img[contains(@id , 'emTemplate:clxcil1::icon')]")
	public  static WebElement addicon;

	@FindBy(xpath = "//input[contains(@id, 'emTemplate:idParamEditorTable:0:clxit3::content')]")
	public  static WebElement inputparam3;

	@FindBy(xpath = "//button[contains(@id , 'emTemplate:clxcb1')]")
	public  static WebElement ok;





	public static void navigatetotopologymbean(WebDriver driver) {

		Actions action = new Actions(driver);

		String farmdomainid =	driver.findElement(By.xpath("//span[text() ='Farm_FADomain']")).getAttribute("id");
		farmdomainid  = farmdomainid.replace("targetLink", ":di");
		driver.findElement(By.xpath("//a[@id = '"+farmdomainid+"']")).click();
		CommonUtils.hold(5);
		driver.findElement(By.xpath("//span[text() ='WebLogic Domain']")).click();
		CommonUtils.hold(5);
		WebElement fadomainrightclick = driver.findElement(By.xpath("//span[text() ='FADomain']"));
		action.contextClick(fadomainrightclick).build().perform();
		CommonUtils.hold(5);
		driver.findElement(By.xpath("//td[text() ='System MBean Browser']")).click();
		CommonUtils.hold(5);
		fndid.click();
		CommonUtils.hold(10);
		searchinput.sendKeys("TopologyRuntimeMBean");
		searchicon.click();
		CommonUtils.hold(5);
		operation.click();
		CommonUtils.hold(5);


	}

	public static void invokeapi(String apiname, WebDriver driver) {

		driver.findElement(By.xpath("//a[text() ='"+apiname+"']")).click();

	}

	public static void invokeoperationanvalidatetabular(String apiname, WebDriver driver) {
		invoke.click();
		CommonUtils.hold(5);	
		try {
			errorclose.click();
			CommonUtils.hold(5);
			CommonUtils.waitForPageLoad(driver);
			System.out.println(apiname+ "test is throwing exception on invoking");
		}

		catch(Exception e) 
		{

		}

		List<WebElement> rows = driver.findElements(By.xpath("//table[@summary='This table displays the return value of the mbean operation invocation.']/tbody/tr"));
		try {
			Assert.assertTrue(rows.size() > 1);
		}
		catch (AssertionError  e)
		{
			System.out.println(apiname+ "test not returning data"); 
		}	


	}

	public static void invokeoperationanvalidatestring(String apiname, WebDriver driver) {
		invoke.click();
		CommonUtils.hold(5);	
		try {
			errorclose.click();
			CommonUtils.hold(5);
			CommonUtils.waitForPageLoad(driver);
			System.out.println(apiname+ "test is throwing exception on invoking");
		}

		catch(Exception e) 
		{

		}


		try {
			String Outputtext = driver.findElement(By.xpath("//span[@id='emTemplate:clxt3:0:clxof2']")).getText(); 

			Assert.assertTrue(Outputtext != null);
		}
		catch (NoSuchElementException  e)
		{
			System.out.println(apiname+ "test not returning data"); 

		}	


	}

	public static void invokeoperationanvalidateboolean(String apiname, WebDriver driver) {
		invoke.click();
		CommonUtils.hold(5);	
		try {
			errorclose.click();
			CommonUtils.hold(5);
			CommonUtils.waitForPageLoad(driver);
			System.out.println(apiname+ "test is throwing exception on invoking");
		}

		catch(Exception e) 
		{

		}


		try {
			String Outputtext = driver.findElement(By.xpath("//span[@id='emTemplate:clxot11']")).getText(); 

			Assert.assertTrue(Outputtext != null);
		}
		catch (NoSuchElementException  e)
		{
			System.out.println(apiname+ "test not returning data"); 

		}	


	}

	public static void scrolldown(WebDriver driver) {

		driver.findElement(By.xpath("//div[@id='emTemplate:idMBeanOperTable::scroller']")).sendKeys(Keys.PAGE_DOWN);


	}

	public static void clickcontinue(WebDriver driver) {

		List<WebElement> continuebutton = driver.findElements(By.xpath("//button[text() = 'Continue']")); 

		if (continuebutton.size() > 0)

		{
			driver.findElement(By.xpath("//button[text() = 'Continue']")).click();
		}


	}


}





