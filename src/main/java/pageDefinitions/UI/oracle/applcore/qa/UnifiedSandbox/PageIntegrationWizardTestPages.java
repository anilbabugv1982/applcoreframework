/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;

/**
 * @author klalam
 *
 */
public class PageIntegrationWizardTestPages extends CreateTab{
	
	public String page1 ="ApplcoreQAPage1";
	public String tab1 ="ApplcoreQATab1";
	public String page2 ="ApplcoreQAPage2";
	public String tab2 ="ApplcoreQATab2";
	public String tab3 ="ApplcoreQATab3";
	public String tab4 ="ApplcoreQATab4";
	public String applicationRole="FND_EXTENSIONS_TEST_ROLE_USB_PIW_FSCM_AIC";
	private GlobalPageTemplate pitpGptInstance;
	private SandboxBannerUI pitpSbuiInstance;
	private NewAndExistingPages naepInstance;
	
	public PageIntegrationWizardTestPages(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		pitpGptInstance = new GlobalPageTemplate(driver);
		pitpSbuiInstance = new SandboxBannerUI(driver);
		naepInstance = new NewAndExistingPages(driver);
	}

	public void createPage(String PageName, String ApplicationRole) {
		CommonUtils.hold(5);
		naepInstance.newpage.click();
		CommonUtils.hold(5);
		createPagedef(PageName, ApplicationRole);
	}

	
	public void addTabToPage(String PageName, String TabName, String ApplicationRole,String webFieldVal,WebDriver driver) {
		try {
			CommonUtils.hold(5);
			naepInstance.selectPageByName(PageName,driver).click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(3);
			addTabCPage.click();
			createTab(TabName, ApplicationRole,webFieldVal,driver);
			CommonUtils.hold(5);
			saveAndClose.click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void updateTab(String PageName, String TabName, String ContentUpdate,WebDriver driver) {
		try {
			CommonUtils.hold(5);
		naepInstance.selectPageByName(PageName,driver).click();
		selectTabByName(TabName,driver).click();
		CommonUtils.hold(3);
		webPage.clear();
		webPage.sendKeys(ContentUpdate);
		saveAndClose.click();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updatePage(String Pagename, String ContentUpdate,WebDriver driver) {
		try {
			CommonUtils.hold(5);
		naepInstance.selectPageByName(Pagename,driver).click();
		webPage.clear();
		webPage.sendKeys(ContentUpdate);
		saveAndClose.click();
			}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}


	public void deleteTab(String PageName, String TabName,WebDriver driver) {
		try {
			CommonUtils.hold(5);
		naepInstance.selectPageByName(PageName,driver).click();
		selectTabByName(TabName,driver).click();
		CommonUtils.hold(3);
		ctDelete.click();
		CommonUtils.hold(3);
		confirmationDialog_CONFIRM.click();
		CommonUtils.hold(5);
		saveAndClose.click();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void deletePage(String PageName,WebDriver driver) {
		try {
			CommonUtils.hold(5);
			naepInstance.selectPageByName(PageName,driver).click();
		cPdelete.click();
		confirmationDialog_CONFIRM.click();
	}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	
	public void addTabToExistingPage(String CategoryName,String FusePageName,String TabName, String ApplicationRole,String webPageValue,WebDriver driver) {
		WebElement pageToAddTab;
		naepInstance.tab_ExistinPages.click();
		/*CommonUtils.hold(3);
		try {
			pitpGptInstance.globalTemplateArea.click();
		}catch(Exception gTemplate) {
			pitpSbuiInstance.SandboxBanner.click();
		}*/
		CommonUtils.hold(5);
		naepInstance.addTabToExistingPage.click();
		//NewAndExistingPages.collectionPage.click();
		CommonUtils.hold(10);
		
		/*if(CategoryName == null)
			naepInstance.selectFusePage(FusePageName,driver).click();
		else {*/
			pageToAddTab = naepInstance.GetFusePage(CategoryName, FusePageName, driver);
			if(pageToAddTab!=null) {
				System.out.println(FusePageName+" Found. Navigating to the page");
				pageToAddTab.click();
			}
				
		//}
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		createTab(TabName, ApplicationRole,webPageValue,driver);
		try {
			CommonUtils.explicitWait(naepInstance.addTabToExistingPage, "Click", "",driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void addSecondTabToExistingPage(String FusePageName,String tabName,String applicationRole,String webPageVal,WebDriver driver) {
		try {
			CommonUtils.hold(5);
			naepInstance.tab_ExistinPages.click();
			CommonUtils.hold(5);
		naepInstance.selectPageByName(FusePageName,driver).click();
		CommonUtils.hold(5);
		addTabCPage.click();
		CommonUtils.hold(5);
		createTab(tabName,applicationRole,webPageVal,driver);
		CommonUtils.hold(5);
		done.click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void updateTabinExistingPage(String FusePageName, String TabName, String ContentUpdate,WebDriver driver) {
		try {
			naepInstance.selectPageByName(FusePageName,driver).click();
		CommonUtils.hold(5);
		selectTabByName(TabName,driver).click();
		CommonUtils.hold(5);
		webPage.clear();
		webPage.sendKeys(ContentUpdate);
		CommonUtils.hold(5);
		saveAndClose.click();
		CommonUtils.hold(5);
		done.click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void deleteTabinExistingPage(String FusePageName, String TabName,WebDriver driver) {
		try {
			CommonUtils.hold(5);
			naepInstance.selectPageByName(FusePageName,driver).click();
		selectTabByName(TabName,driver).click();
		ctDelete.click();
		confirmationDialog_CONFIRM.click();
		CommonUtils.hold(5);
		done.click();
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}

}
