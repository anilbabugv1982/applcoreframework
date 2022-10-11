/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;

/**
 * @author kkoti
 *
 */
public class NewAndExistingPages {

	private static ArrayList<String> pages= new ArrayList<String>();
	private StructurePages spInstance;

	public NewAndExistingPages(WebDriver driver) {
		PageFactory.initElements(driver, this);
		spInstance = new StructurePages(driver);
	}
	
	@FindBy(xpath = "//div[contains(@id , '_FOTr0:0:sp1:msg1')]/descendant::td[contains(text() , 'You must be in an active sandbox to make changes')]")
	public WebElement PageIntegrationWizard_PreviewMode;
	
	@FindBy(xpath = "//span[contains(text(),'ew Page')]")
	public WebElement newpage;
	
	@FindBy(xpath = "//span[contains(text(),'Category')]")
	public WebElement rename_category;

	@FindBy(xpath = "//img[contains(@title,'Page Integration Wizard: New Pages')]")
	public WebElement tab_NewPages;

	@FindBy(xpath = "//*[local-name() = 'svg' and @aria-label = 'Page Integration Wizard: Existing Pages']")
	public WebElement tab_ExistinPages;
	
	@FindBy(id="_FOpt1:_FOr1:0:_FOSritemNode_tools_new_pages:0:_FOTr0:0:sp1:t1:0:cl1")
	public WebElement custom_page;
	
	@FindBy(xpath="//span[contains(text(),'Add ')]")
	public WebElement addTabToExistingPage;
	
	@FindBy(xpath="//span[contains(text(),'Collections')]")
	public WebElement collectionPage;
	
	@FindBy(xpath="//div[contains(@id , 'sp1:tt1::scroller')]")
//	@FindBy(xpath="//div[contains(@id , 'sp1:tt1') and @class = 'AFStretchWidth af_treeTable']")
	public WebElement existingPagesScroller;
	
	public WebElement  selectPageByName(String page, WebDriver driver) throws Exception
	{
		WebElement pageName = null;
		try {
				CommonUtils.explicitWait(driver.findElement(By.xpath("//a[text() = '"+page+"']")),"Click", "",driver);
				 pageName = driver.findElement(By.xpath("//a[text() = '"+page+"']"));
		}catch(Exception e) {
			WebElement elementscrollPopUp = driver.findElement(By.xpath("//div[contains(@id, 'sp1:t1::scroller')]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", elementscrollPopUp);
			CommonUtils.hold(5);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//a[text() = '"+page+"']")),"Click", "",driver);
			pageName = driver.findElement(By.xpath("//a[text() = '"+page+"']"));
		}
		System.out.println("pageName - "+pageName.toString());
		return pageName;
	}
	
	public void addPage(String pageName)
	{
		pages.add(pageName);
	}
	
	public WebElement selectPageByIndex(int i, WebDriver driver)
	{
		WebElement pageName = driver.findElement(By.xpath("//a[contains(@title,'"+pages.get(i)+"')]"));
		return pageName;
	}
	
	public WebElement selectFusePage(String page, WebDriver driver)
	{
		WebElement pageName = driver.findElement(By.xpath("//span[contains(text(),'"+page+"')]"));
		return pageName;
	}
	
	/*
	 * GetCreatedPage(String pagename) returns the page that has been created and published to mainline 
	 */
	public WebElement GetCreatedPage(String pagename, WebDriver driver)
	{
		WebElement CreatedpageName = driver.findElement(By.xpath("//div[text() = '"+pagename+"']"));
		return CreatedpageName;
	}
	
	/*
	 * GetAddedTabToFusePage(String tabname) returns the tab that has been added to existing fuse page and published to mainline 
	 */
	public WebElement GetAddedTabToFusePage(String tabname, WebDriver driver)
	{
		WebElement GetTabName = driver.findElement(By.xpath("//div[@title = '"+tabname+"']"));
		return GetTabName;
	}
	
	/*
	 * GetFusePage(String CategoryName, String FusePage) returns fusepage under category to add tab 
	 */
	public WebElement GetFusePage(String parentMenu, String childMenu, WebDriver driver) {
		WebElement chldFuseElement = null;
		/*try {
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text() = '"+CategoryName+"']/ancestor::div[1]/descendant::a[@title = 'Expand']")), "ElementVisible", "",driver);
			driver.findElement(By.xpath("//span[text() = 'My Team']/ancestor::div[1]/descendant::a[contains(@id , 'tt1:1::di')]")).click();
		}catch(Exception categoryException) {
			categoryException.printStackTrace();
			 if CATEGROYNAME is in EXPAND state then search for childpage and return 
			System.out.println("CategoryName is already in EXPAND state. Collapse the CategoryName");
			CommonUtils.hold(3);
			try {
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text() = '"+FusePage+"']")), "ElementVisible", "",driver);
				GetChildFusePageEle = getChildCategoryName(FusePage,driver);
			}catch(Exception childFPageE) {
				childFPageE.printStackTrace();
				//CommonUtils.checkForElementPresence(getChildCategoryName(FusePage), existingPagesScroller);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", existingPagesScroller);
				CommonUtils.hold(3);
				GetChildFusePageEle = getChildCategoryName(FusePage,driver);
			}
			return GetChildFusePageEle;
		}
		
		CommonUtils.hold(3);
		try {
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text() = '"+FusePage+"']")), "ElementVisible", "",driver);
			GetChildFusePageEle = getChildCategoryName(FusePage,driver);
		}catch(Exception childFPageE) {
			childFPageE.printStackTrace();
			//CommonUtils.checkForElementPresence(getChildCategoryName(FusePage), existingPagesScroller);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", existingPagesScroller);
			CommonUtils.hold(3);
			GetChildFusePageEle = getChildCategoryName(FusePage,driver);
		}*/
		try {
			if(CommonUtils.checkForElementPresence(driver.findElement(By.xpath("//span[text() = '"+parentMenu+"']")), existingPagesScroller,driver)) {
					System.out.println(parentMenu+" Found. Checking whether "+parentMenu+" is in expand or collapsed state");
				try {
					WebElement expandableIcon = spInstance.getMenuExpandIcon(parentMenu,driver);
					System.out.println(parentMenu+" is in Collapsed State");
					CommonUtils.hold(2);
						System.out.println("Expanding "+parentMenu+" Item");
					expandableIcon.click();
					CommonUtils.hold(5);
							System.out.println(parentMenu+" is Expanded");
				}catch(Exception peI) {
					System.out.println(parentMenu +"not in Collapsed state. Checking whether its in Collapsed state or not");
						try {
							WebElement collapsableIcon = spInstance.getMenuCollapsedIcon(parentMenu, driver);
							CommonUtils.hold(3);
								if(collapsableIcon.isDisplayed())
									System.out.println(parentMenu+" is in Expanded State");
						}catch(Exception pcI) {
							System.out.println(parentMenu +"not in expanded or collapsed state. (i.e.) no childe menu existed for this menu");
							chldFuseElement = driver.findElement(By.xpath("//span[text() = '"+parentMenu+"']"));
							return chldFuseElement;
						}
				}
				 chldFuseElement = driver.findElement(By.xpath("//span[text() = '"+childMenu+"']"));
				 System.out.println("Childe Menu Element -> "+chldFuseElement);
			}
		}catch(Exception cnf) {
			System.out.println("parent element "+parentMenu+" not found");
			cnf.printStackTrace();
		}
			
		return chldFuseElement;
	}
	
	public WebElement getChildCategoryName(String childCategory, WebDriver driver) {
		WebElement GetChildFusePageEle = driver.findElement(By.xpath("//span[text() = '"+childCategory+"']"));
		return GetChildFusePageEle;
	}
}//End Of NewAndExistingPages Class
