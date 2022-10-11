/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import UtilClasses.UI.ReportGenerator;
import UtilClasses.UI.CommonUtils;


/**
 * @author klalam
 *
 */
public class StructureTestMethods extends StructurePages{
	
	public String navigatorValue ="Yes";
	public static String categoryName;
	/*public String pageEntryStaticN ="pageEntryStaticN";
	public String pageEntryStaticEX ="pageEntryStaticEX";*/
	public String staticURL ="Static URL";
	public String staticDestination ="https://people.us.oracle.com/";
	public String pageEntryDynamic ="pageEntryDynamic";
	public String dynamicURL ="Dynamic URL";
	public String webApplication ="ORA_FSCM_UI";
	public String webAppDestination ="faces/FuseTaskListManagerTop";
	public String existingCategory ="Sales";
	public String appendText ="_appended";
	public String UniqueId;
	
	public StructureTestMethods(WebDriver driver) {
		super(driver);
	}
	
	public void createCategory(String categoryNameVal,WebDriver driver) throws InterruptedException {
	try {
		//	structurePaneMainDiv.click();
		categoryName = categoryNameVal;
			CommonUtils.hold(2);
		createButton.click();
		CommonUtils.hold(5);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", createGroup);
		//StructurePages.createGroup.click();
		CommonUtils.explicitWait(categoryNameElement, "Visible", "",driver);
		categoryNameElement.sendKeys(categoryName);
		CommonUtils.hold(2);
		iconSearchImg.click();
		CommonUtils.explicitWait(iconImg, "Click", "",driver);
		iconImg.click();
		CommonUtils.explicitWait(okButton, "Click", "",driver);
		okButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.selectDropDownElement(selectNavigator,navigatorValue);
		CommonUtils.scrollToElement(saveAndClose,driver);
		CommonUtils.waitForPageLoad(driver);
		saveAndClose.click();
		CommonUtils.hold(5);
		System.out.println("Category Created Successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createPageEntryStaticURL(String spgName,WebDriver driver) throws InterruptedException {
	try {
	//	structurePaneMainDiv.click();
		CommonUtils.hold(2);
		createButton.click();
		CommonUtils.explicitWait(createPageEntry, "Click", "",driver);
		CommonUtils.hold(3);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", createPageEntry);
		//StructurePages.createPageEntry.click();
		CommonUtils.explicitWait(pageEntryName, "Visible", "",driver);
		pageEntryName.sendKeys(spgName);
		CommonUtils.hold(2);
		iconSearchImg.click();
		CommonUtils.explicitWait(staticPageEntryIcon, "Click", "",driver);
		staticPageEntryIcon.click();
		CommonUtils.explicitWait(okButton, "Click", "",driver);
		okButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(selectCategory,categoryName);
		CommonUtils.hold(3);
		CommonUtils.selectDropDownElement(linkType,staticURL);
		CommonUtils.hold(2);
		destination.sendKeys(staticDestination);
		CommonUtils.hold(2);
		CommonUtils.scrollToElement(saveAndClose,driver);
		CommonUtils.waitForPageLoad(driver);
		saveAndClose.click();
		CommonUtils.hold(5);
		System.out.println("Static Page Entry Created Successfully");
		}catch(Exception cPSUE) {
			cPSUE.printStackTrace();
		}
	}

	public void createPageEntryDynamicURL(String dpgName,WebDriver driver) throws InterruptedException {
		try {
		//	structurePaneMainDiv.click();
			CommonUtils.hold(2);
		createButton.click();
		CommonUtils.explicitWait(createPageEntry, "Click", "",driver);
		CommonUtils.hold(3);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", createPageEntry);
		//StructurePages.createPageEntry.click();
		CommonUtils.explicitWait(pageEntryName, "Visible", "",driver);
		pageEntryName.sendKeys(dpgName);
		iconSearchImg.click();
		CommonUtils.explicitWait(dynamicPageEntryIcon, "Click", "",driver);
		dynamicPageEntryIcon.click();
		CommonUtils.waitForPageLoad(driver);
		okButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(selectCategory,categoryName);
		CommonUtils.selectDropDownElement(linkType,dynamicURL);
		webApplicationElement.sendKeys(webApplication);
		webAppDestinationElement.sendKeys(webAppDestination);
		CommonUtils.scrollToElement(saveAndClose,driver);
		CommonUtils.waitForPageLoad(driver);
		saveAndClose.click();
		CommonUtils.hold(5);
		System.out.println("Dynamic Page Entry Created Successfully");
		}catch(Exception cPEDUE) {
			cPEDUE.printStackTrace();
		}
	} 
	
	public void createPageEntryUnderExistingCategory(String pgName,WebDriver driver) throws InterruptedException {
	try {
			System.out.println("Before structurePaneMainDiv click");
			structurePaneMainDiv.click();
			System.out.println("After structurePaneMainDiv click");
			CommonUtils.hold(5);
			
		createButton.click();
		System.out.println("After createbutton click");
		CommonUtils.explicitWait(createPageEntry, "Click", "",driver);
		CommonUtils.hold(3);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", createPageEntry);
		CommonUtils.hold(5);
		CommonUtils.explicitWait(pageEntryName, "Visible", "",driver);
		pageEntryName.sendKeys(pgName);
		iconSearchImg.click();
		CommonUtils.explicitWait(staticPageEntryIcon, "Click", "",driver);
		staticPageEntryIcon.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		okButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(selectCategory,"Sales");
		CommonUtils.hold(5);
		CommonUtils.selectDropDownElement(linkType,staticURL);
		destination.sendKeys(staticDestination);
		CommonUtils.scrollToElement(saveAndClose,driver);
		CommonUtils.waitForPageLoad(driver);
		saveAndClose.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text() = '"+pgName+"']")), "Visible", "",driver);
		System.out.println("Static Page Entry Created Under Existing <Sales> Category Successfully");
		}catch (Exception cPEUECE) {
			cPEUECE.printStackTrace();
		}
	} 
	
	
	/*public void verifyCategory(WebDriver driver) throws InterruptedException {
		System.out.println("Verifying category");
		CommonUtils.scrollToElement(verifyCreatedCategoryText(UniqueId,driver),driver);
		System.out.println("Created categogy present: " +CommonUtils.checkForElementPresence(verifyCreatedCategoryText(UniqueId,driver), structurePaneScroller,driver));
	} 
	
	
	public void verifyStaticPage(WebDriver driver) throws Exception {
		verifyCreatedCategoryText(UniqueId,driver).click();
		System.out.println("Created static page present: "+CommonUtils.checkForElementPresence(verifyCreatedStaticPageN(UniqueId,driver), structurePaneScroller,driver));	
		String winHandleBefore=driver.getWindowHandle();
		verifyCreatedStaticPageN(UniqueId,driver).click();
		CommonUtils.waitForPageLoad(driver);
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
		System.out.println("Navigating to static page URL, oracleAriaLogo verified: "+CommonUtils.checkForElementPresence(oracleAriaLogo, structurePaneScroller,driver));
		
		driver.switchTo().defaultContent();
	} 

	
	public void verifyDynamicPage(WebDriver driver) {
		verifyCreatedCategoryText(UniqueId,driver).click();
		System.out.println("Created dynamic page present: "+CommonUtils.checkForElementPresence(verifyCreatedDynamicPage(UniqueId,driver), structurePaneScroller,driver));
		String winHandleBefore=driver.getWindowHandle();
		verifyCreatedDynamicPage(UniqueId,driver).click();
		CommonUtils.waitForPageLoad(driver);
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
		System.out.println("Navigating to dynamic page URL, dynamicPageHeading verified: "+CommonUtils.checkForElementPresence(dynamicPageHeading, structurePaneScroller,driver));
	}  
	
	 
	public void verifyExistingCategoryStaticPage(WebDriver driver) throws InterruptedException {
		CommonUtils.scrollToElement(verifyExistingCategoryPage,driver);
		verifyExistingCategoryPage.click();
		System.out.println("Created static page present: "+CommonUtils.checkForElementPresence(verifyCreatedStaticPageEX(UniqueId,driver), structurePaneScroller,driver));
		String winHandleBefore=driver.getWindowHandle();
		verifyCreatedStaticPageEX(UniqueId,driver).click();
		CommonUtils.waitForPageLoad(driver);
		for(String winHandle : driver.getWindowHandles()){
			driver.switchTo().window(winHandle);
		}
		System.out.println("Navigating to existing category static page URL, oracleAriaLogo verified: "+CommonUtils.checkForElementPresence(oracleAriaLogo, structurePaneScroller,driver));
		CommonUtils.hold(5);
	}
	*/
		
	public void modifyExistingCategoryPage(String parentMenu, String updatableMenu,WebElement updatedElement1,WebElement updatedElement2,WebDriver driver) throws Exception {
		try {
			System.out.println("Parent Menu Item Found: "+CommonUtils.checkForElementPresence(getExistingMenuItem(parentMenu,driver), structurePaneScroller,driver));	
			CommonUtils.hold(3);
			try {
				WebElement expandableIcon = getMenuExpandIcon("Me",driver);
				if(expandableIcon.isDisplayed()) {
					System.out.println("Element not in expand state");
					expandableIcon.click();
					System.out.println("Element Expanded");
				}
			}catch(Exception exe) {
				System.out.println("Element Expanded check failed. Checking for Collapsed state");
				try {
					WebElement collapsedIcon = getMenuCollapsedIcon("Me",driver);
					if(collapsedIcon.isDisplayed()){
						System.out.println("Element in collapsed state. Expand operation not needed");
						collapsedIcon.click();
						System.out.println("Menu Item is collapsed");
						CommonUtils.hold(5);
						System.out.println("Element in collapsed state. Expanding Menu Item");
						getMenuExpandIcon("Me",driver).click();
						CommonUtils.hold(3);
						System.out.println("Element Expanded");
					}
				}catch(Exception ce) {
					System.out.println("Element doesn't exit. Exit from method");
					return;
				}
			}
			
			CommonUtils.hold(5);
			System.out.println("after expandicon check");
			WebElement chldElement = getExistingMenuItem(updatableMenu,driver);
			CommonUtils.hold(3);
			System.out.println("Child Element -> "+chldElement);
			CommonUtils.checkForElementPresence(chldElement, structurePaneScroller,driver);
			CommonUtils.hold(5);
			//CommonUtils.explicitWait(chldElement, "ElementVisible", "",driver);
			CommonUtils.explicitWait(chldElement, "Visible", "",driver);
			CommonUtils.hold(3);
			chldElement.click();
			System.out.println("MenuElement found and clicked");
			CommonUtils.explicitWait(existingPageEntryName, "Visible", "",driver);
			CommonUtils.hold(5);
			existingPageEntryNameParent.click();
			CommonUtils.hold(5);
				//Get Image icon name of existing Image Icon of the updatableMenu 
			String imgSrcValue = getTagAttibuteValue(menuImageField,"src",driver);
			imgSrcValue = getImageString(imgSrcValue);
			CommonUtils.hold(2);
			System.out.println("Existing Image Icon Src value -> "+imgSrcValue);
			CommonUtils.hold(2);
			menuImgElement.click();
			CommonUtils.explicitWait(updatedElement1, "Visible", "",driver);
			CommonUtils.hold(2);
			String updatedElementSrcValue = getTagAttibuteValue(updatedElement1,"src",driver);
			updatedElementSrcValue = getImageString(updatedElementSrcValue);
			CommonUtils.hold(3);
			if(updatedElementSrcValue.equalsIgnoreCase(imgSrcValue)) {
				System.out.println("Existing Image Icon "+imgSrcValue+" same as updated Image Icon "+updatedElementSrcValue);
				updatedElement2.click();
			}else {
				System.out.println("Existing Image Icon "+imgSrcValue+" different than updated Image Icon "+updatedElementSrcValue);
				updatedElement1.click();
			}
			CommonUtils.hold(10);
			okButton.click();
			CommonUtils.hold(10);
			System.out.println("Toggle Mobile Enalbed Value");
			String valueOfMobileEnabledAttr = getTagAttibuteValue(mobileEnabledValue,"value",driver);
			if(valueOfMobileEnabledAttr.equalsIgnoreCase("Yes"))
			{
				System.out.println("Value of MobileEnable field is : Yes. So setting value to No ");
					mobileEnabledValue.sendKeys("No");
			}
			else if(valueOfMobileEnabledAttr.equalsIgnoreCase("No"))
			{
				System.out.println("Value of MobileEnable field is : No. So setting value to Yes ");
					mobileEnabledValue.sendKeys("Yes");
			}
			CommonUtils.hold(5);
			saveAndClose.click();
			CommonUtils.explicitWait(createButton, "Click", "",driver);
			CommonUtils.hold(5);
			System.out.println("Modified page entry with existing category "+getExistingCategoryName(driver)  +" Successfully");
		}catch(Exception general) {
			System.out.println("Exception in modifyExistingCategoryPage()");
			general.printStackTrace();
		}
	}
	
	/*
	 * scrollTopOfStructureMenu(WebDriver driver) will scroll to top of structure menu
	 */
	public void scrollTopOfStructureMenu(WebDriver driver) {
		try {
			System.out.println("Scrolling to Top of Structure Menu");
			//((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", structurePaneScroller);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollBottom = arguments[0].scrollHeight", structurePaneScroller);
			//((JavascriptExecutor) driver).executeScript("arguments[0].scrollBottom = arguments[0].scrollIntoView();", getExistingMenuItem(MenuName,driver));
			CommonUtils.hold(3);
			System.out.println("Scrolled to Top of Structure Menu");
		} catch (Exception e) {
			System.out.println("Exception while scrolling to top of strucuture menu");
			e.printStackTrace();
		}
	}
	 
	/*public void verifyModifiedPage(WebDriver driver) throws InterruptedException {
		CommonUtils.scrollToElement(verifyExistingCategoryPage,driver);
		verifyExistingCategoryPage.click();
		String modifiedPageName=verifyCreatedStaticPageEX(UniqueId,driver).getText();
		System.out.println("Modified page name is : "+modifiedPageName);
		Assert.assertEquals("pageEntryStaticEX_appended", modifiedPageName, "Name not matching");
		System.out.println("Verified Page with existing Category "+getExistingCategoryName(driver)  +" Successfully");
	}*/
	
	public String getImageString(String imgName) {
		
		String imageString;
		 imageString = imgName.substring(imgName.lastIndexOf("/")+1, imgName.length());
		 System.out.println("Image Name -> "+imageString);
	return imageString;
	}
}
