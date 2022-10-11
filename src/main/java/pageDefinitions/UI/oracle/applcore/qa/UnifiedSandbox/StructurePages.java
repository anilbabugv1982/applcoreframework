/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;

/**
 * @author ssundriy
 *
 */
public class StructurePages {

	public StructurePages(WebDriver driver){
		PageFactory.initElements(driver,this);
}

	@FindBy(id="pt1:nv_ATK_HOMEPAGE_FUSE_STRUCTURE")
	public WebElement structureTool;
	
	@FindBy(xpath="//a[contains(@id,'FOSsdiATK_HOMEPAGE_FUSE_STRUCTURE')]")
	public WebElement structureIcon;
	
	@FindBy(xpath="//div[contains(@id , 'pt1:tt2::scroller')]")
	public WebElement structurePaneScroller;
	
	@FindBy(xpath="//div[contains(@id , 'pt1:main-s1::t')]")
	public WebElement structurePaneMainDiv;
	
	@FindBy(xpath="//svg[contains(@id , '_FOTsdiATK_STRUCTURE::icon')]")
	public WebElement navigationConfiguration;

	@FindBy(xpath="//td[contains(@id , 'pt1:ap1:cb9::popArea')]")
	public WebElement createButton;

	@FindBy(xpath="//tr[contains(@id , 'pt1:ap1:cmi4')]")
	public WebElement createGroup;
	
	@FindBy(xpath="//input[contains(@id , 'it1::content')]")
	public WebElement categoryNameElement;

	@FindBy(xpath="//img[contains(@id , 'cil5::icon')]")
	public WebElement iconSearchImg;

	@FindBy(xpath="//img[contains(@id , 'cil7j_id_2::icon')]")
	public WebElement iconImg;

	@FindBy(xpath="//button[contains(@id ,'d1::ok')]")
	public WebElement okButton;
	
	@FindBy(xpath="//select[contains(@id ,'soc6::content')]")
	public WebElement selectNavigator;
	
	@FindBy(xpath="//button[contains(@id ,'pt1:ap1:cb9')]")
	public WebElement saveAndClose;
	
	@FindBy(xpath="//tr[contains(@id ,'pt1:ap1:cmi5')]")
	public WebElement createPageEntry;
	
	@FindBy(xpath="//input[contains(@id ,'it1::content')]")
	public WebElement pageEntryName;
	
	@FindBy(xpath="//input[contains(@id ,'it12::content')]")
	public WebElement existingPageEntryName;
	
	@FindBy(xpath="//input[contains(@id ,'it12::content')]/ancestor::table[1]")
	public WebElement existingPageEntryNameParent;
	
	@FindBy(xpath="//img[contains(@id , 'cil7j_id_15::icon')]")
	public WebElement dynamicPageEntryIcon;
	
	@FindBy(xpath="//img[contains(@id , 'cil7j_id_23::icon')]")
	public WebElement staticPageEntryIcon;
	
	@FindBy(xpath="//select[contains(@id , 'soc1::content')]")
	public WebElement selectCategory;
	
	@FindBy(xpath="//select[contains(@id , 'soc2::content')]")
	public WebElement linkType;
	
	@FindBy(xpath="//input[contains(@id , 'it5::content')]")
	public WebElement focusViewID;
	
	@FindBy(xpath="//input[contains(@id , 'wAppLOV::content')]")
	public WebElement webApplicationElement;
	
	@FindBy(xpath="//input[contains(@id , 'it8::content')]")
	public WebElement destination;
	
	@FindBy(xpath="//input[contains(@id , 'it6::content')]")
	public WebElement webAppDestinationElement;
	
	@FindBy(xpath="//span[contains(@id , 'pt1:ot111')]")
	public WebElement previewTextElement;
	
	@FindBy(xpath="//img[@alt = 'Search']")
	public WebElement menuImgElement;
	
	@FindBy(xpath="//img[contains(@id , 'id_1::icon')]")
	public WebElement timeMenu1stImg;
	
	@FindBy(xpath="//img[contains(@id , 'id_2::icon')]")
	public WebElement timeMenu2ndImg;
	
	@FindBy(xpath="//input[contains(@id , 'soc12::content')]")
	public static WebElement mobileEnabledValue;
	
	@FindBy(xpath="//img[contains(@id , 'cil2')]")
	public static WebElement menuImageField;
	
	
	public WebElement verifyCreatedCategoryText(String timestamp,WebDriver driver){
		 
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Category"+timestamp+"')]"));
	    return element;	 
	 }
	
	public WebElement verifyCreatedStaticPageN(String timestamp,WebDriver driver){
		 
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'pageEntryStaticN"+timestamp+"')]"));
	    return element;	 
	 }
	
	public  WebElement verifyCreatedStaticPageEX(String timestamp,WebDriver driver){
		 
		WebElement element = driver.findElement(By.xpath("//span[contains(text(),'pageEntryStaticEX"+timestamp+"')]"));
	    return element;	 
	 }
	
	public WebElement verifyCreatedStaticPageInExpand(String timestamp,WebDriver driver){
		 
		WebElement element = driver.findElement(By.xpath("//span[contains(text(),'pageEntryStaticEX"+timestamp+"')]"));
	    return element;	 
	 }
	
	public WebElement verifyCreatedDynamicPage(String timestamp,WebDriver driver){
		 
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'pageEntryDynamic"+timestamp+"')]"));
	    return element;	 
	 }
	
	public WebElement verifyPageUnderExistingCategory(String timestamp,WebDriver driver){
		 
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'pageEntryStatic"+timestamp+"')]"));
	    return element;	 
	 }
	
	public  WebElement verifyModifiedPageUnderExistingCategory(String timestamp,WebDriver driver){
		 
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'pageEntryStatic'"+timestamp+"'_append'')]"));
	    return element;	 
	 }
	
	//getExistingCategoryName() method will return "sales" category name as the imgid corresponds to sales category
	public String getExistingCategoryName(WebDriver driver) {
		
		String categroyName = driver.findElement(By.xpath("//img[contains(@id , 'pt1:tt2:12:cil1::icon')]/ancestor::a/descendant::span")).getText();
		
		return categroyName;
	}
	
	public WebElement getExistingMenuItem(String MenuName,WebDriver driver) {
		
		WebElement menuItemElement = driver.findElement(By.xpath("//span[text() = '"+MenuName+"']"));
		
		return menuItemElement;
	}
	
	public WebElement getMenuExpandCollapseIcon(int menuIndex,WebDriver driver) {
		WebElement expandCollapseIcon = null;
		try {
			expandCollapseIcon = driver.findElement(By.xpath("//img[contains(@id , 'pt1:tt2:"+menuIndex+":cil1::icon')]/ancestor::div[1]/descendant::a[contains(@id, 'pt1:tt2:"+menuIndex+"::di')]"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return expandCollapseIcon;
	}
	
	public WebElement getMenuExpandIcon(String menuName,WebDriver driver) {
		WebElement expandIcon = null;
		try {
			expandIcon = driver.findElement(By.xpath("//span[text() = '"+menuName+"']/ancestor::div[1]/descendant::a[@title = 'Expand']"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return expandIcon;
	}
	
	public WebElement getMenuCollapsedIcon(String menuName,WebDriver driver) {
		WebElement collapsedIcon = null;
		try {
			collapsedIcon = driver.findElement(By.xpath("//span[text() = '"+menuName+"']/ancestor::div[1]/descendant::a[@title = 'Collapse']"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return collapsedIcon;
	}
	
	/*
	 * getTagAttibuteValue() will return value of "elementattribute" prameter of "fieldElement" parameter
	 * If fieldElement = img tag and elementattribute = src then 
	 * 		will return value of "src" attribute of "img" tag
	 */
	public String getTagAttibuteValue(WebElement fieldElement, String elementattribute,WebDriver driver) {
		String attributeValue = null;
		try {
			CommonUtils.hold(2);
			attributeValue = fieldElement.getAttribute(elementattribute);
			CommonUtils.hold(2);
		}catch(Exception gITVE) {
			System.out.println("Exception in getTagAttibuteValue()");
			gITVE.printStackTrace();
		}
	return attributeValue;
	}
	

}//End of StructurePages class
