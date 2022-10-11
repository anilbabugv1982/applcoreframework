/**
 * 
 *//*
package oracle.applcore.qa.sandbox;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import oracle.applcore.qa.common.CommonUtils;
import oracle.applcore.qa.common.GlobalPageTemplate;
import oracle.applcore.qa.setup.DriverInstance;



*//**
 * @author klalam
 *
 *//*
public class StructureTestMethods {
	
	public static String uniqueID;
	public static  String navigatorValue ="Yes";
	public static String categoryName ="Category";
	public static String pageEntryStaticN ="pageEntryStaticN";
	public static String pageEntryStaticEX ="pageEntryStaticEX";
	public static String staticURL ="Static URL";
	public static String staticDestination ="https://people.us.oracle.com/";
	public static String pageEntryDynamic ="pageEntryDynamic";
	public static String dynamicURL ="Dynamic URL";
	public static String webApplication ="ORA_FSCM_UI";
	public static String webAppDestination ="faces/FuseTaskListManagerTop";
	public static String existingCategory ="Sales";
	public static String appendText ="_appended";
	public static String catname=null;
	
	
	public static String getUniqueId() {
		uniqueID = CommonUtils.uniqueId();
		return uniqueID;
	}
	public static void createCategory() throws Exception {
		//DriverInstance.getDriverInstance().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//HomePage.launchURL();
		//HomePage.navigatorButton.click();
		//StructurePages.structureTool.click();
		CommonUtils.hold(3);
		//DriverInstance.getDriverInstance().findElement(By.id("pt1:_UISmmLink::icon")).click();
		//DriverInstance.getDriverInstance().findElement(By.id("pt1:nv_ATK_HOMEPAGE_FUSE_STRUCTURE")).click();
		//CommonUtils.navigateToTask(StructurePages.structureTool);
		//StructurePages.structureTool.click();
		CommonUtils.hold(5);
		//StructurePages.navigationConfiguration.click();
		StructurePages.createButton.click();
		StructurePages.createCategory.click();
		CommonUtils.hold(10);
		StructurePages.categoryName.sendKeys(categoryName+getUniqueId());
		catname=StructurePages.categoryName.getAttribute("value");
		System.out.print(catname);
		//StructurePages.iconSearchImg.click();
		//StructurePages.iconImg.click();
		//CommonUtils.waitForPageLoad();
		//StructurePages.okButton.click();
		CommonUtils.waitForPageLoad();
		CommonUtils.selectDropDownElement(StructurePages.selectNavigator,navigatorValue);
		CommonUtils.scrollToElement(StructurePages.saveAndClose);
		CommonUtils.waitForPageLoad();
		StructurePages.saveAndClose.click();
		CommonUtils.hold(5);
		System.out.println("Category Created Successfully");	
	}
	
	public static void createPageEntryStaticURL() throws InterruptedException {
		//DriverInstance.getDriverInstance().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//HomePage.launchURL();
		//HomePage.navigatorButton.click();
		//StructurePages.structureTool.click();
		//StructurePages.navigationConfiguration.click();
		//CommonUtils.waitForPageLoad();
		StructurePages.createButton.click();
		StructurePages.createPageEntry.click();
		StructurePages.pageEntryName.sendKeys(pageEntryStaticN+getUniqueId());
		StructurePages.iconSearchImg.click();
		CommonUtils.waitForPageLoad();
		//StructurePages.staticPageEntryIcon.click();
		CommonUtils.hold(10);
		StructurePages.staticPageEntryIcon.sendKeys(Keys.TAB);
		StructurePages.staticPageEntryIcon.sendKeys(Keys.ENTER);
		CommonUtils.hold(5);
		StructurePages.okButton.click();
		CommonUtils.waitForPageLoad();
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(StructurePages.selectCategory,catname);
		//Select dropdown = new Select(DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:soc1::content']")));
		//dropdown.selectByVisibleText(catname);
		WebElement elem01=DriverInstance.getDriverInstance().findElement(By.xpath("//input[@id='_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:soc1::content']"));
		elem01.click();
		DriverInstance.getDriverInstance().findElement(By.xpath("//li[@class='af_selectOneChoice_item'][contains(text(),'"+categoryName+getUniqueId()+"')]")).click();
		
		CommonUtils.hold(5);
		
		WebElement elem02=DriverInstance.getDriverInstance().findElement(By.id("_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:soc2::content"));
		elem02.click();
		DriverInstance.getDriverInstance().findElement(By.xpath("//li[@class='af_selectOneChoice_item'][contains(text(),'"+staticURL+"')]")).click();
		
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(StructurePages.linkType,staticURL);
		CommonUtils.hold(5);
		StructurePages.destination.sendKeys(staticDestination);
		CommonUtils.hold(3);
		CommonUtils.scrollToElement(StructurePages.saveAndClose);
		CommonUtils.waitForPageLoad();
		StructurePages.saveAndClose.click();
		CommonUtils.hold(5);
		System.out.println("Static Page Entry Created Successfully");
	}

	public static void createPageEntryDynamicURL() throws InterruptedException {
		//DriverInstance.getDriverInstance().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//HomePage.launchURL();
		//HomePage.navigatorButton.click();
		//StructurePages.structureTool.click();
		//StructurePages.navigationConfiguration.click();
		StructurePages.createButton.click();
		StructurePages.createPageEntry.click();
		StructurePages.pageEntryName.sendKeys(pageEntryDynamic+getUniqueId());
		StructurePages.iconSearchImg.click();
		CommonUtils.hold(10);
		StructurePages.dynamicPageEntryIcon.sendKeys(Keys.TAB);
		StructurePages.dynamicPageEntryIcon.sendKeys(Keys.ENTER);
		CommonUtils.hold(5);
		//StructurePages.dynamicPageEntryIcon.click();
		CommonUtils.waitForPageLoad();
		StructurePages.okButton.click();
		CommonUtils.waitForPageLoad();
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(StructurePages.selectCategory,catname);
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(StructurePages.linkType,dynamicURL);
		CommonUtils.hold(5);
		StructurePages.webApplication.sendKeys(webApplication);
		CommonUtils.hold(5);
		StructurePages.webAppDestination.sendKeys(webAppDestination);
		CommonUtils.scrollToElement(StructurePages.saveAndClose);
		CommonUtils.waitForPageLoad();
		StructurePages.saveAndClose.click();
		CommonUtils.hold(5);
		System.out.println("Dynamic Page Entry Created Successfully");
	} 
	
	public static void createPageEntryUnderExistingCategory() throws InterruptedException {
		//DriverInstance.getDriverInstance().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//HomePage.launchURL();
		//HomePage.navigatorButton.click();
		//StructurePages.structureTool.click();
		//StructurePages.navigationConfiguration.click();
		StructurePages.createButton.click();
		StructurePages.createPageEntry.click();
		CommonUtils.hold(2);
		StructurePages.pageEntryName.sendKeys(pageEntryStaticEX+getUniqueId());
		StructurePages.iconSearchImg.click();
		CommonUtils.hold(10);
		StructurePages.staticPageEntryIcon.sendKeys(Keys.TAB);
		StructurePages.staticPageEntryIcon.sendKeys(Keys.ENTER);
		//StructurePages.staticPageEntryIcon.click();
		CommonUtils.waitForPageLoad();
		CommonUtils.hold(5);
		StructurePages.okButton.click();
		CommonUtils.waitForPageLoad();
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(StructurePages.selectCategory,existingCategory);
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(StructurePages.linkType,staticURL);
		CommonUtils.hold(10);
		StructurePages.destination.sendKeys(staticDestination);
		CommonUtils.scrollToElement(StructurePages.saveAndClose);
		CommonUtils.waitForPageLoad();;
		StructurePages.saveAndClose.click();
		CommonUtils.hold(5);
		System.out.println("Static Page Entry Created Under Existing "+existingCategory +" Category Successfully");
	} 
	
	
	public static void verifyCategory() throws InterruptedException {
		//DriverInstance.getDriverInstance().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//HomePage.launchURL();
		System.out.println("Verifying category");
		CommonUtils.hold(10);
		CommonUtils.scrollToElement(StructurePages.verifyCreatedCategoryText(catname));
		System.out.println("Created categogy present: " +CommonUtils.isElementPresent(StructurePages.verifyCreatedCategoryText(catname)));
	} 
	
	
	public static void verifyStaticPage() throws Exception {
	//	DriverInstance.getDriverInstance().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	//	HomePage.launchURL();
		StructurePages.verifyCreatedCategoryText(catname).click();
		System.out.println("Created static page present: "+CommonUtils.isElementPresent(StructurePages.verifyCreatedStaticPageN(getUniqueId())));	
		String winHandleBefore=DriverInstance.getDriverInstance().getWindowHandle();
		StructurePages.verifyCreatedStaticPageN(uniqueID).click();
		CommonUtils.waitForPageLoad();
		for(String winHandle : DriverInstance.getDriverInstance().getWindowHandles()){
			DriverInstance.getDriverInstance().switchTo().window(winHandle);
		}
		System.out.println("Navigating to static page URL, oracleAriaLogo verified: "+CommonUtils.isElementPresent(StructurePages.oracleAriaLogo));
		
		DriverInstance.getDriverInstance().switchTo().defaultContent();
	} 

	
	public static void verifyDynamicPage() {
	//	DriverInstance.getDriverInstance().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	//	HomePage.launchURL();
		StructurePages.verifyCreatedCategoryText(uniqueID).click();
		System.out.println("Created dynamic page present: "+CommonUtils.isElementPresent(StructurePages.verifyCreatedDynamicPage(getUniqueId())));
		String winHandleBefore=DriverInstance.getDriverInstance().getWindowHandle();
		StructurePages.verifyCreatedDynamicPage(uniqueID).click();
		CommonUtils.waitForPageLoad();
		for(String winHandle : DriverInstance.getDriverInstance().getWindowHandles()){
			DriverInstance.getDriverInstance().switchTo().window(winHandle);
		}
		System.out.println("Navigating to dynamic page URL, dynamicPageHeading verified: "+CommonUtils.isElementPresent(StructurePages.dynamicPageHeading));
	}  
	
	 
	public static void verifyExistingCategoryStaticPage() throws InterruptedException {
	//	DriverInstance.getDriverInstance().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	//	HomePage.launchURL();
		CommonUtils.scrollToElement(StructurePages.verifyExistingCategoryPage);
		StructurePages.verifyExistingCategoryPage.click();
		System.out.println("Created static page present: "+CommonUtils.isElementPresent(StructurePages.verifyCreatedStaticPageEX(getUniqueId())));
		String winHandleBefore=DriverInstance.getDriverInstance().getWindowHandle();
		StructurePages.verifyCreatedStaticPageEX(getUniqueId()).click();
		CommonUtils.waitForPageLoad();
		for(String winHandle : DriverInstance.getDriverInstance().getWindowHandles()){
			DriverInstance.getDriverInstance().switchTo().window(winHandle);
		}
		System.out.println("Navigating to existing category static page URL, oracleAriaLogo verified: "+CommonUtils.isElementPresent(StructurePages.oracleAriaLogo));
		CommonUtils.hold(5);
	}
	
		
	public static void modifyExistingCategoryPage(String UpdatedText) throws Exception {
	//	DriverInstance.getDriverInstance().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	//	HomePage.launchURL();
	//	HomePage.navigatorButton.click();
	//	StructurePages.structureTool.click();
	//	CommonUtils.waitForPageLoad();
		try {
			System.out.println("expand icon found: "+CommonUtils.isElementPresent(StructurePages.expandSalesCategory));	
			StructurePages.expandSalesCategory.click();
		}catch(Exception general) {
			CommonUtils.waitForPageLoad();
			CommonUtils.scrollToElement(StructurePages.verifyCreatedStaticPageInExpand(getUniqueId()));
			StructurePages.verifyCreatedStaticPageInExpand(getUniqueId()).click();
			StructurePages.pageEntryName.clear();
			StructurePages.pageEntryName.sendKeys(pageEntryStaticEX+getUniqueId()+UpdatedText);
			System.out.println("Modified category name is: "+pageEntryStaticEX+getUniqueId()+appendText);
			StructurePages.saveAndClose.click();
			CommonUtils.hold(5);
			System.out.println("Modified page entry with existing category "+existingCategory  +" Successfully");
		}
	}
	
	 
	public static void verifyModifiedPage() throws InterruptedException {
	//	DriverInstance.getDriverInstance().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	//	HomePage.launchURL();
		CommonUtils.scrollToElement(StructurePages.verifyExistingCategoryPage);
		StructurePages.verifyExistingCategoryPage.click();
		String modifiedPageName=StructurePages.verifyCreatedStaticPageEX(getUniqueId()).getText();
		System.out.println("Modified page name is : "+modifiedPageName);
		Assert.assertEquals("pageEntryStaticEX"+uniqueID+"_appended", modifiedPageName, "Name not matching");
		System.out.println("Verified Page with existing Category "+existingCategory  +" Successfully");
	}
	

}
*/