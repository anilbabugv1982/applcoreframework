/**
 * 
 *//*
package oracle.applcore.qa.sandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import oracle.applcore.qa.setup.DriverInstance;



*//**
 * @author ssundriy
 *
 *//*
public class StructurePages {

	static {
		PageFactory.initElements(DriverInstance.getDriverInstance(),StructurePages.class);
}
	
	@FindBy(xpath="//a[contains(@id,'pt1:nvi_ATK_HOMEPAGE_FUSE_STRUCTURE')]")
	public static WebElement structureTool;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:_FOTsdiATK_STRUCTURE::icon")
	public static WebElement navigationConfiguration;

	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:_FOTr1:0:pt1:ap1:cb9::popEl")
	public static WebElement createButton;

	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:_FOTr1:0:pt1:ap1:cmi4")
	public static WebElement createCategory;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:it1::content")
	public static WebElement categoryName;

	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:cil5::icon")
	public static WebElement iconSearchImg;

	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:cil7j_id_2::icon")
	public static WebElement iconImg;

	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:d1::ok")
	public static WebElement okButton;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:soc6::content")
	public static WebElement selectNavigator;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:ap1:cb9")
	public static WebElement saveAndClose;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:_FOTr1:0:pt1:ap1:cmi5")
	public static WebElement createPageEntry;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:it1::content")
	public static WebElement pageEntryName;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:cil7j_id_36::icon")
	public static WebElement dynamicPageEntryIcon;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:cil7j_id_23::icon")
	public static WebElement staticPageEntryIcon;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:soc1::content")
	public static WebElement selectCategory;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:soc2::content")
	public static WebElement linkType;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:it5::content")
	public static WebElement focusViewID; 
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:wAppLOV::content")
	public static WebElement webApplication;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:it8::content")
	public static WebElement destination;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:MAnt2:1:pt1:r1:0:it6::content")
	public static WebElement webAppDestination;
	
	
	public static WebElement verifyCreatedCategoryText(String timestamp){
		 
		WebElement element = DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(text(),'Category"+timestamp+"')]"));
	    return element;	 
	 }
	
	public static WebElement verifyCreatedStaticPageN(String timestamp){
		 
		WebElement element = DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(text(),'pageEntryStaticN"+timestamp+"')]"));
	    return element;	 
	 }
	
	public static WebElement verifyCreatedStaticPageEX(String timestamp){
		 
		WebElement element = DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(text(),'pageEntryStaticEX"+timestamp+"')]"));
	    return element;	 
	 }
	
	public static WebElement verifyCreatedStaticPageInExpand(String timestamp){
		 
		WebElement element = DriverInstance.getDriverInstance().findElement(By.xpath("//span[contains(text(),'pageEntryStaticEX"+timestamp+"')]"));
	    return element;	 
	 }
	
	public static WebElement verifyCreatedDynamicPage(String timestamp){
		 
		WebElement element = DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(text(),'pageEntryDynamic"+timestamp+"')]"));
	    return element;	 
	 }
	
	public static WebElement verifyPageUnderExistingCategory(String timestamp){
		 
		WebElement element = DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(text(),'pageEntryStatic"+timestamp+"')]"));
	    return element;	 
	 }
	
	public static WebElement verifyModifiedPageUnderExistingCategory(String timestamp){
		 
		WebElement element = DriverInstance.getDriverInstance().findElement(By.xpath("//a[contains(text(),'pageEntryStatic'"+timestamp+"'_append'')]"));
	    return element;	 
	 }
	
	@FindBy(xpath = "//a[contains(text(),'Sales')]")
	public static WebElement verifyExistingCategoryPage;
	
	@FindBy(id="app-navigation")
	public static WebElement appNavigation;
	
	@FindBy(id="logo")
	public static WebElement oracleAriaLogo;
	
	@FindBy(xpath = "//h1[contains(text(),'Setup: Compensation Management')]")
	public static WebElement dynamicPageHeading;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSrATK_HOMEPAGE_FUSE_STRUCTURE:0:_FOTr1:0:pt1:tt2:7::di")
	public static WebElement expandSalesCategory;
}//End of StructurePages class
*/