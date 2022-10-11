package UtilClasses.UI;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;

public class GlobalPageTemplate {

	public GlobalPageTemplate(WebDriver driver) {
		System.out.println("Driver Instance in global page template -> "+driver);
		PageFactory.initElements(driver, this);
		
	}

	@FindBy(xpath="//a[contains(@id,'pt1:_UISmmLink')]")
	public  WebElement navigatorButton;

	@FindBy(id="pt1:nvi_itemNode_contract_management_contracts::icon")
	public  WebElement contracts;
	
	
	//@FindBy(id="pt1:nvi_itemNode_tools_setup_and_maintenance::icon")
	/*@FindBy(id="pt1:_UISnvr:0:nv_itemNode_tools_setup_and_maintenance")*/
	@FindBy(xpath="//a[contains(@id,'_setup_and_maintenance')]")
	public WebElement setupandmaintenance;

    @FindBy(xpath = "//a[contains(@id,'pt1:_UIShome')]")
	public WebElement homeIcon;
    
    @FindBy(xpath="//a[@id = 'pt1:atkfr1:0:grid:0:cil1']")
	public WebElement fuseWelcomeSpringBoard;
    
    @FindBy(xpath="//div[@id = 'pt1:_UISGSr']")
	public WebElement globalTemplateArea;
    
    @FindBy(xpath="//a[@id='pt1:nv_itemNode_workforce_management_workforce_structures' or contains(@id,'_itemNode_workforce_management_workforce_structures')]")
    public WebElement workForceStructures;
    
    @FindBy(xpath="//a[contains(@id,'_itemNode_product_management_items')]")
    public WebElement productInformationManagement;
    
    @FindBy(xpath="//a[contains(@id,'_ASE_FUSE_SECURITY_CONSOLE')]")
    public WebElement securityConsole;

	//@FindBy(id ="_FOpt1:_UIShome")
	//public static WebElement homeIcon;

	@FindBy(id ="pt1:_UIScmt3")
	public WebElement globalpagetemplate;
	
	@FindBy(xpath ="//a[contains(@id,'pt1:nv_itemNode_Tools_Preferences')]")
	public WebElement setPreferences;
	
	@FindBy(id ="pt1:_UISwlLink::icon")
	public WebElement watchlisticon;
	
	@FindBy(id ="pt1:_UIScmi1")
	public WebElement preferences;
	
	@FindBy(xpath ="//a[contains(@id , 'pt1:_UIScmi1')]")
	public WebElement preferenceslink;
	
	
	
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmi3')]")
	public  WebElement pagedit;
	
	
	@FindBy(id ="pt1:nv_itemNode_sales_quotas")
	public WebElement quotas;
	
	@FindBy(xpath="//a[contains(@id,'itemNode_tools_audit_reports')]")
	public WebElement auditreports;
	
	@FindBy(id ="pt1:nv_MOO_OPPTYMGMTOPPORTUNITIES_CRM_CARD")
	public WebElement oppurtunitiesLink;
	
	@FindBy(xpath="//a[contains(@id,'pt1:_UISfavIconu')]")
	public WebElement favoritesAndRecentItemsIcon;
	
	@FindBy(xpath="//div[contains(@id,'UISriLink::ti')]/descendant::a")
	public WebElement recentItemslink;
	
	//@FindBy(id ="pt1:_UISwlLink")
	@FindBy(xpath="//a[contains(@id,'pt1:_UISwlLink')]")
	public WebElement watchListIcon;
	
	//@FindBy(id ="pt1:_UISatr:0:cil1")
	@FindBy(xpath="//a[contains(@id,'pt1:_UISatr:0:cil1')]")
	public WebElement notificationsIcon;
	
	//@FindBy(id ="pt1:_UIScmil2u::icon")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmil2u::icon')]")
	public WebElement userIcon;
	
	//@FindBy(id ="pt1:_UIScmil1u::icon")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmil1u')]")
	public WebElement userDropDownIcon;
	
	//"Settings And Actions" menu items under UserName
	//@FindBy(id ="pt1:_UISac2")
	@FindBy(xpath="//a[contains(@id,'pt1:_UISac2')]")
	public WebElement personalization_AccessAccessibilitySettings;
	
	//@FindBy(id ="pt1:_UIScmi1")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmi1')]")
	public WebElement personalization_SetPrefrences;
	
	//@FindBy(id ="pt1:_UIScmi3")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmi3')]")
	public WebElement administration_EditPages;
	
	//@FindBy(id ="pt1:_UIScmt31")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmt3')]")
	public WebElement administration_EditGlobalPageTemplate;
	
	//@FindBy(id ="pt1:_UIScmi16")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmi16')]")
	public WebElement administration_ManageConfigurations;
	
	//@FindBy(id ="pt1:_UIScmi4")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmi4')]")
	public WebElement administration_SetupAndMaintenance;
	
	//@FindBy(id ="pt1:_UIScmi17")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmi17')]")
	public WebElement administration_HighlightFlexFields;
	
	//@FindBy(id ="pt1:_UIScmi7")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmi7')]")
	public WebElement troubleShooting_RunDiagnosticTests;
	
	//@FindBy(id ="pt1:_UISatpLink1")
	@FindBy(xpath="//a[contains(@id,'pt1:_UISatpLink1')]")
	public WebElement troubleShooting_RecordIssues;
	
	//@FindBy(id ="pt1:_UISprint")
	@FindBy(xpath="//a[contains(@id,'pt1:_UISprint')]")
	public WebElement printMe;
	
	//@FindBy(id ="pt1:_UIShtr:0:AtkUATg1")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIShtr:0:AtkUATg1')]")
	public WebElement showHelpIcons;
	
	//@FindBy(id ="pt1:nv_itemNode_workforce_management_new_person")
	//@FindBy(xpath="//a[contains(@id,'pt1:nv_itemNode_workforce_management_new_person')]")
	@FindBy(id ="pt1:_UISnvr:0:nv_itemNode_manager_resources_new_person::icon")
	public WebElement newPerson;
	
	//@FindBy(id ="pt1:_UIScmi5")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmi5')]")
	public WebElement applicationsHelp;
	
	//@FindBy(id ="pt1:_UIScmi99")
	@FindBy(xpath="//a[contains(@id,'pt1:_UIScmi99')]")
	public WebElement aboutThisApplication;
	
	@FindBy(xpath="//a[text() = 'View']")
	public static WebElement composerViewLink;
	
	@FindBy(xpath="//div[ contains(@id,'_groupNode_tools')]/descendant::a[@title='Scheduled Processes']")
	public  WebElement scheduledProcesses;
	
	//a[@id='pt1:_UISnvr:0:nvcl1']
	@FindBy(xpath="//a[contains(@id,'showmore_groupNode_manager_resources')]")
	public WebElement redWoodMyTeamShowMoreLink;
	
	@FindBy(xpath="//a[contains(@class,'flat-show-less-link')]")
	public WebElement redWoodShowLessLink;
	
	/*@FindBy(xpath = "//a[contains(@id,'nv_itemNode_tools_setup_and_maintenance')]")
	public static WebElement clickLink;

	@FindBy(xpath = "//*[local-name() = 'svg' and contains(@aria-label , 'Home')]")
	public static WebElement homeIcon_usingSVGIcon;
	
	@FindBy(xpath = "//*[local-name() = 'svg' and contains(@aria-label , 'Favorites and Recent Items')]")
	public static WebElement FaVRecentItemsIcon_usingSVGIcon;
	
	@FindBy(xpath = "//*[local-name() = 'svg' and contains(@id , 'pt1:_UISwlLink::icon')]")
	public static WebElement watchlisticon_usingSVGIcon;
	
	@FindBy(xpath = "//*[local-name() = 'svg' and contains(@id , 'pt1:_UISfavIconu::icon')]")
	public static WebElement recentitemsicon;
	
	@FindBy(xpath = "//a[@id = 'pt1:_UISfpr1:0:_UISriLink::disAcr']")
	public static WebElement RecentItemsTab;*/
		
	public void navigatequotas(WebDriver driver) {
	WebElement navigatequotas=driver.findElement(By.id("pt1:nv_itemNode_sales_quotas"));
    JavascriptExecutor executor = (JavascriptExecutor)driver;
    executor.executeScript("arguments[0].click();", navigatequotas);
	}
	
	public void verifyGlobalPageTemplate(WebDriver driver){
		WebDriverWait wait = new WebDriverWait(driver,60);
		wait.until(ExpectedConditions.elementToBeClickable(navigatorButton));
		wait.until(ExpectedConditions.elementToBeClickable(homeIcon));
		wait.until(ExpectedConditions.elementToBeClickable(favoritesAndRecentItemsIcon));
		Assert.assertTrue(navigatorButton.isDisplayed() && homeIcon.isDisplayed() && favoritesAndRecentItemsIcon.isDisplayed(), "Global Icons not loaded");
	}
	
	public WebElement accessFromRecentItems(String taskflowName, WebDriver driver) {
		WebElement taskflowElement = null;
		try {
			favoritesAndRecentItemsIcon.click();
			CommonUtils.explicitWait(recentItemslink, "Click", "",driver);
			recentItemslink.click();
			CommonUtils.hold(10);
			taskflowElement = driver.findElement(By.xpath("//div[contains(@id, 'UISfpr1:0:pgl1_RIL')]/descendant::a[@title = '"+taskflowName+"']"));
		}catch(Exception aFRIE) {
			System.out.println("Exception while accessing taskflow from recent items");
			aFRIE.printStackTrace();
		}
	return taskflowElement;
	}
	
	public WebElement GetFuseIcon(String IconName,WebDriver driver) {
		WebElement IconElement = driver.findElement(By.xpath("//a[text() = '"+IconName+"']"));
		return IconElement;
		
	}//End Of GetFuseIcon()
	
	public void Adminlinks(String Str,WebDriver driver) {
		WebElement Pagetemplate=driver.findElement(By.xpath("//a[contains(text(),'"+Str+"')]"));
		//DriverInstance.waitForElement(Pagetemplate);
		CommonUtils.hold(10);
			//Assert.assertEquals(true, Pagetemplate.isDisplayed());

		Pagetemplate.click();

	}//End Of Adminlinks()

}
