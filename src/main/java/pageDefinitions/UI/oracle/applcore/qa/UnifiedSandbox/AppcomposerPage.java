/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import UtilClasses.UI.CommonUtils;

/**
 * @author sdubey
 * @author vivevenk
 *
 */
public class AppcomposerPage {

	public  AppcomposerPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
//	WebDriverWait wait= new WebDriverWait(driver, 20);
	
	public static final String textname = "Text"+CommonUtils.uniqueId();
	public static final String numbername = "Number"+CommonUtils.uniqueId();
	public static final String choicelistname = "ChoicListFixed"+CommonUtils.uniqueId();
	
	public static final String StdObj = "Account";
	public static final String custobhname = "CustomObj"+CommonUtils.uniqueId();
	
	@FindBy(xpath = "//h1[text() = 'Application Composer']")
	public  WebElement appComposerPageTitle;
	   
	@ FindBy(linkText ="Standard Objects")
	public WebElement standardobject;
	
	@ FindBy(linkText ="Account")
	public WebElement account;
	
	@ FindBy(linkText ="Fields")
	public WebElement field;
	
	@ FindBy(xpath ="//*[contains(@id, 'AP1:r1:0:pc3:m3')]")
	public WebElement action;
	
	//@ FindBy(xpath ="//*[contains(@id, 'AP1:r1:0:pc1:m1')]")
	  @ FindBy(linkText ="Action")	
	public WebElement customaction;
	
	//@ FindBy(xpath ="//*[contains(@id, 'AP1:r1:0:pc3:cmi4')]")
	@ FindBy(xpath = "//td[text() = 'Create']")
	public WebElement create;
	
	@ FindBy(xpath ="//*[contains(@id, 'sbr1::content')]")
	public WebElement text;
	
	//_FOpt1:_FOr1:0:_FOSritemNode_tools_application_composer:0:_FOTsr1:0:ft1:AP2:r2:1:AP1:r1:0:i1
	@ FindBy(xpath ="//img[contains(@id, 'FOTsr1:0:ft1:AP2:r2:1:AP1:r1:0:i1')]/ancestor::td[1]/descendant::input")
	public WebElement Choosetext;
	
	@ FindBy(xpath ="//img[contains(@src, 'qual_dropdown_32.png')]/ancestor::td[1]/descendant::input")
	public WebElement FCL_field;
	
	@ FindBy(xpath ="//img[contains(@src, 'characternumber_32')]/ancestor::td[1]/descendant::input")
	public WebElement Number_field;
	
	@ FindBy(xpath ="//*[contains(@id, 'AP1:r1:0:cb4')]")
	public WebElement ok;
	
	@ FindBy(xpath ="//*[contains(@id, 'r1:0:it6::content')]")
	public WebElement displayname;
	
	@ FindBy(xpath ="//*[contains(@id, 'r1:0:it1::content')]")
	public WebElement name;
	
	
	@ FindBy(xpath ="//*[contains(@id, 'SaveAndClose')]")
	public WebElement saveandclose;
	
	@ FindBy(xpath ="//*[contains(@id, ':0:outputText3')]")
	public WebElement editlink;
	
	@ FindBy(xpath ="//*[contains(@id, 'pt1:USma:0:MAnt1:1:AP1:r1:0:sbr1j_id_3::content')]")
	public WebElement number;
	
	@ FindBy(id ="pt1:USma:0:MAnt2:0:AP1:r1:0:sbr1j_id_2::content")
	public WebElement choicelistfixed;
	
	@ FindBy(xpath ="//*[contains(@id, 'r1:0:cil2::icon')]")
	public WebElement selectandsearch;
	
	@ FindBy(id ="r1:0:qryId1:value10::content")
	public WebElement LookupValue;
	
	@ FindBy(id ="r1:0:qryId1::search")
	public WebElement search;

	@ FindBy(linkText="Custom Objects")
	public WebElement customObjects;
	
	
	@ FindBy(xpath="//a[contains(@id , 'AP1:cl001') and text() = 'Custom Objects']")
	public WebElement customObject;
	
	@ FindBy(xpath = "//a[text() = 'Custom Objects']/ancestor::div[1]/descendant::a[@title = 'Expand']")
	public WebElement CustomObjectsExpand;
	
	@ FindBy(xpath = "//a[text() = 'Standard Objects']/ancestor::div[1]/descendant::a[@title = 'Expand']")
	public WebElement StandardObjectsExpand;
	
	
	
	//	@ FindBy(linkText="pt1:USma:0:MAnt1:1:AP1:r1:0:pc1:m1")
//	public static WebElement customAction; 
	
  // @ FindBy(id="pt1:USma:0:MAnt1:1:AP1:r1:0:pc1:cmi1")
	@ FindBy(xpath = "//td[text() = 'Create']")
    public WebElement createCustom;

	//@ FindBy(id="pt1:USma:0:MAnt1:1:AP1:r1:0:r1:1:itDisplayLabel::content")
	@ FindBy(xpath = "//input[contains(@id , 'itDisplayLabel::content')]")
	public WebElement customDisplay;

	@ FindBy(xpath ="//*[contains(@id, 'r1:1:cbSave')]")
	public WebElement customOK;
	
	@ FindBy(id="pt1:USma:0:MAnt1:1:AP1:r1:0:pc1:tbObjList:0:ot2")
	public WebElement createdCustomObject;
	
	@ FindBy(id="pt1:USra:0:RAp1:0:ATRr1:27:commandLink1")
	public WebElement createdNewCustomObject;
	
	@ FindBy(xpath ="//*[contains(@id, 'r1:0:cb3::icon')]")
	public WebElement editCustomObject;
	
	@ FindBy(xpath ="//*[contains(@id, 'r1:1:itDisplayLabel::content')]")
	public WebElement editDisplay;
	
	@ FindBy(id="r1:0:ctb4")
	public WebElement editOK;
	
	@ FindBy(xpath ="//*[contains(@id, 'r1:0:ctb9')]")
	public WebElement editSave;
	
	@ FindBy(linkText ="Standard")
	public WebElement standard;
	
	@ FindBy(linkText ="Address Line 1")
	public WebElement adressline;
	
	@ FindBy(xpath ="//div[text() = 'Creating Custom Object']")
	public WebElement WaittillCustomObjectCreate;
	
	/*@ FindBy(xpath ="//span[contains(text() , 'Sandbox is currently in preview mode. To perform configurations in this page, switch to edit mode and enter this page')]")
	public static WebElement AppComposerPreviewMode;
	*/
	
	@ FindBy(xpath ="//span[contains(@id , 'ft1:AP2:r2:0:ot1')]")
	public WebElement AppComposerPreviewMode;
	
	
	@ FindBy(xpath="//*[contains(@id,'FOTsr1:0:ft1:AP2:r1:0:ATRr1:3:cil1::icon::_afrtt')]")
	public WebElement createPlusIcon;
	
	
	
	public WebElement StadardObjectEle(String StdobjName,WebDriver driver) {
		WebElement objlink =driver.findElement(By.linkText(StdobjName));
		return objlink;
	}
	
	public WebElement getlinktext(String fName,WebDriver driver)
	{
		WebElement editlink =driver.findElement(By.linkText(fName));
		return editlink;
		
	}
	
	public WebElement getlinknumber(String fName,WebDriver driver)
	{
		WebElement editlink =driver.findElement(By.linkText(fName));
		return editlink;
		
	}
	
	public WebElement getlinkchoicelist(String fName,WebDriver driver)
	{
		WebElement editlink =driver.findElement(By.linkText(fName));
		return editlink;
		
	}
	
	public WebElement getFieldupdate(String ufName,WebDriver driver)
	{
		WebElement editlink =driver.findElement(By.linkText(ufName));
		return editlink;
		
	}
	
	public WebElement getlinknumberupdate(String ufName,WebDriver driver)
	{
		WebElement editlink =driver.findElement(By.linkText(ufName));
		return editlink;
		
	}
	
	public WebElement getlinkchoicelistupdate(String ufName,WebDriver driver)
	{
		WebElement editlink =driver.findElement(By.linkText(ufName));
		return editlink;
		
	}
	
	public WebElement standardupdate(WebDriver driver)
	{
		WebElement editlink =driver.findElement(By.linkText("Address Line 1"+"_Update"));
		return editlink;
		
	}
	
	
	public WebElement chooseStandardObject(String standardObjName,WebDriver driver)
	{
		WebElement standardObj =driver.findElement(By.linkText(standardObjName));
		return standardObj;
		
	}
	
	public WebElement Getcustomobject(String objName,WebDriver driver)
	{
		WebElement editcustObj =driver.findElement(By.linkText(objName));
		return editcustObj;
		
	}
	
	public WebElement customobjectCreateWait(String objName,WebDriver driver)
	{
		WebElement editcustObj = driver.findElement(By.xpath("//h1[contains(text(),'"+objName+"')]"));
		return editcustObj;
		
	}
	/*public static WebElement Getupdatedcustomobject(String objName, String updatedText)
	{
		WebElement editcustObj =driver.findElement(By.linkText(objName+updatedText));
		return editcustObj;
		
	}*/
	
	public void ExpandObject(String objName,WebDriver driver) {
		
		WebElement ListedcustObj =driver.findElement(By.xpath("//a[text() = '"+objName+"']"));
		try {
			CommonUtils.explicitWait(ListedcustObj, "Visible", "",driver);
		} catch (Exception EOE) {
			System.out.println("Excpetion in ExpandObject() while waiting for AvailableCustomObject");
			EOE.printStackTrace();
		}
		WebElement ExpandListedcustObj =driver.findElement(By.xpath("//a[text() = '"+objName+"']/ancestor::div[1]/descendant::a[@title = 'Expand']"));
		ExpandListedcustObj.click();
	}
	

}
