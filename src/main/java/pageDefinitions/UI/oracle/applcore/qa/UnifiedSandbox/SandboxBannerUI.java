/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.util.HashMap;

import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigatorMenuElements;
import UtilClasses.UI.ReportGenerator;
import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;


/**
 * @author klalam
 *
 */


public class SandboxBannerUI extends SandboxUtils{
	private static final Map<String, String> NavigationMessages;
	static {
		
		NavigationMessages = new HashMap<String, String>();
		NavigationMessages.put("Publish", "You will be navigated to sandbox details page there you can publish this sandbox");
		NavigationMessages.put("ExitSandbox", "Current page will be reloaded when you exit the sandbox");
		NavigationMessages.put("AddTools", "You will be navigated to sandbox details page there you can add tools to this sandbox");
		NavigationMessages.put("EditMode", "Current page will be reloaded when you change to Edit mode");
		NavigationMessages.put("PreviewMode", "Current page will be reloaded when you change to Preview mode");
		NavigationMessages.put("ExitSandboxNonHomePage", "You will exit this sandbox and brought back to home page");
	}
	
	CommonUtilsDefs cUtilDefsInstance;  
	public SandboxBannerUI (WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		cUtilDefsInstance = new CommonUtilsDefs(driver);
	}
	
	@FindBy(xpath = "//div[contains(@id , 'pt1:_UISsbb:0:r1:0:_sbpgl1')]")
	public  WebElement SandboxBanner;
	
	@FindBy(xpath = "//div[contains(@id , 'pt1:_UISsbb:0:r1:0:_sbm1')]/descendant::a[contains(text() , 'Sandbox Mode')]")
	public  WebElement SandboxMode;
	
	@FindBy(xpath = "//div[contains(@id , 'pt1:_UISsbb:0:r1:0:_sbm1')]/descendant::td[contains(text() , 'Edit')]")
	public  WebElement SandboxMode_Edit;
	
	@FindBy(xpath = "//div[contains(@id , 'pt1:_UISsbb:0:r1:0:_sbm1')]/descendant::td[contains(text() , 'Preview')]")
	public  WebElement SandboxMode_Preview;
	
	@FindBy(xpath = "//a[contains(@id , 'pt1:_UISsbb:0:r1:0')]/descendant::span[text() = 'Refresh needed']")
	public  WebElement RefreshNeededLink;
	
	@FindBy(xpath = "//a[text() = 'Go to Sandbox Detail Page']")
	public  WebElement RefreshNeeded_SandboxDetailLink;
	
	@FindBy(xpath = "//div[contains(@id , 'UISsbb:0:r1:0:_sbm3')]/descendant::a[text() = 'Tools']")
	public  WebElement ToolsMenu;
	
	@FindBy(xpath = "//div[contains(@id , 'UISsbb:0:r1:0:_sbm3')]/descendant::div[@class = 'af_menu_bar-item-open-icon-style']")
	public  WebElement ToolsMenuIcon;

	@FindBy(xpath = "//div[contains(@id , 'pt1:_UISsbb:0:r1:0:_sbm3')]/descendant::td[contains(text(), 'Add More')]")
	public  WebElement ToolsMenu_AddMore;
	
	@FindBy(xpath = "//a[contains(@id , 'pt1:_UISsbb:0:r1:0')]/descendant::img[@title = 'Notifications']")
	public  WebElement SandboxNotification;
	
	@FindBy(xpath = "//a[contains(@id , 'pt1:_UISsbb:0:r1:0')]/descendant::img[@title = 'Notifications']/ancestor::tr[1]/descendant::span[@class = 'HeaderBadge']")
	public  WebElement SandboxNotificationCount;
	
	@FindBy(xpath = "//div[@id = '_FOpt1:_UISsbb:0:r1:0:_sblv1::db']/descendant::div[@class = 'af_listItem'][last()]/descendant::a")
	public  WebElement SandboxNameInNotificationlist;
	
	//@FindBy(xpath = "//button[contains(@id , 'UISsbb:0:r1:0') and text() = 'Cancel']")
	@FindBy(xpath = "//button[contains(@id , '_sbd2::no')]")
	public  WebElement SandboxBannerNavigationDiscard;
	
	@FindBy(xpath = "//button[contains(@id , '_sbd2::yes')]")
	public  WebElement SandboxBannerNavigationConfirm;
	
	@FindBy(xpath = "//button[contains(@id , '_sbcb1')]")
	public  WebElement SandboxBannerPublishNavigationConfirm;
	
	@FindBy(xpath = "//button[contains(@id , '_sbob1')]")
	public  WebElement SandboxBannerPreviewNavigationConfirm;
	
	@FindBy(xpath = "//button[contains(@id , '_sbcb8')]")
	public  WebElement SandboxBannerPublishNavigationDiscard;
	
	@FindBy(xpath = "//span[contains(text() , 'Activate a sandbox with the desired context and try again')]")
	public  WebElement PageComposerNavigationWithCCContextSB;
	
	@FindBy(xpath = "//span[contains(text() , 'Activate a sandbox with the desired context and try again')]/ancestor::tbody[1]/descendant::button[text() = 'OK']")
	public  WebElement PageComposerNavigationWithCCContextSB_Ok;
	
	/*
	 * SandboxMenu(String SandboxName) returns the sandbox name of the current active sandbox from sandox banner
	 */
	public WebElement sandboxBannerName(String SandboxName, WebDriver driver) {
		
		WebElement SandboxNameElement = driver.findElement(By.xpath("//div[contains(@id, 'pt1:_UISsbb:0:r1:0:_sbm2')]/descendant::a[text() = '"+SandboxName+"']"));
		
		return SandboxNameElement;
	}
	
	/* Sandbox Name Menu items list on Sandbox Banner -> Sandbox Detail,Publish and Leave Sandbox
	 * 
	 */
	public WebElement SandboxMenuList(String MenuItemName, WebDriver driver) {
		
		WebElement MenuItemElement = driver.findElement(By.xpath("//div[contains(@id, 'pt1:_UISsbb:0:r1:0:_sbm2')]/descendant::td[text() = '"+MenuItemName+"']"));
		
		return MenuItemElement;
	}
	
	/*
	 * SandboxBannerNavigationActions(String TypeOfAction,String ActionConfirmation) performs navitation action initiated from sandbox banner
	 * OK -> Confirm navigation action
	 * Cancel -> Discard navigation action
	 */
	public WebElement SandboxBannerNavigationActions(String TypeOfAction,String ActionConfirmation,WebDriver driver) {
		
		WebElement ActionPopupElement = driver.findElement(By.xpath("//span[contains(text(), '"+NavigationMessages.get(TypeOfAction).toString()+"')]/ancestor::tbody[1]/descendant::button[text()='"+ActionConfirmation+"']"));
		
		return ActionPopupElement;
	}
	
	/*
	 * GetRepositoryElement(String RepositoryName) returns the Tools from sandbox banner that are activated in the current active sandbox 
	 */
	public WebElement GetRepositoryElement(String RepositoryName, WebDriver driver) {
	
		WebElement RepositoryElement = driver.findElement(By.xpath("//div[contains(@id , 'pt1:_UISsbb:0:r1:0:_sbm3')]/descendant::td[text() = '"+RepositoryName+"']"));
		return RepositoryElement;
	}
	
	/*
	 * GetNotifcationSandboxesCount() returns the count of notifications appear on the current active sandbox.
	 */
	public int GetNotifcationSandboxesCount(WebDriver driver) {
		
		WebElement GetLastElementIndex = driver.findElement(By.xpath("//div[@id = '_FOpt1:_UISsbb:0:r1:0:_sblv1::db']/descendant::div[@class = 'af_listItem'][last()]"));
		
		return Integer.parseInt(GetLastElementIndex.getAttribute("data-afrrk"));
	}
	
	/*
	 * NavigateFromNotification(String SandboxName) returns WebElement from Notifications ui in sandbox banner
	 */
	public WebElement NavigateFromNotification(String SandboxName, WebDriver driver) {
		
		WebElement GetSandboxElement = driver.findElement(By.xpath("//h1[text() = 'Sandbox Detail: "+SandboxName+"']/ancestor::div[contains(@id ,'tools_sandboxes')]/descendant::span[text() = 'Published']"));
		return GetSandboxElement;
	}
	/*
	 * NavigationPopUpLoad(String TypeOfNavigation) returns PopUp Element loaded while initiating navigation action from sandbox banner
	 */
	private WebElement NavigationPopUpLoad(String TypeOfNavigation, WebDriver driver) {
		
		WebElement NavigationPopupElement = driver.findElement(By.xpath("//span[contains(text(), '"+NavigationMessages.get(TypeOfNavigation).toString()+"')]"));
		
		return NavigationPopupElement;
	}
	
	/*
	 * navigatetoToolPage(String ToolName) navigate to tools (Structure/PageIntegrationWizard/AppComposer/PageComposer/User InterfaceText) page from sandbox banner 
	 */
	public void navigatetoToolPage(String ToolName, WebDriver driver) {
		WebElement NavigationElement = driver.findElement(By.xpath("//div[contains(@id , 'pt1:_UISsbb:0:r1:0:_sbm3')]/descendant::td[text() = '"+ToolName+"']"));
		try {
			ToolsMenuIcon.click();
			CommonUtils.explicitWait(NavigationElement, "Click", "",driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NavigationElement.click();
	}
	
	/*
	 * checkAndExitActiveSandboxSession() will check if there is active sandbox in logged in user session. If it has then exit from the active sandbox session
	 */
	public void checkAndExitActiveSandboxSession(WebDriver driver) {
		System.out.println("Initiating Active Sandbox Checking");
		try {
			if(driver.findElement(By.xpath("//div[contains(@id , 'pt1:_UISsbb:0:r1:0:_sbpgl1')]")).isDisplayed()) {
				CommonUtils.hold(3);
				String sandboxName = driver.findElement(By.xpath("//div[contains(@id , 'pt1:_UISsbb:0:r1:0:_sbm2')]/descendant::a")).getText();
				CommonUtils.hold(3);
				System.out.println("Sandbox < "+sandboxName+" > has been activated in the user session.");
				System.out.println("Exiting from the sandbox session");
				
				driver.findElement(By.xpath("//a[@class = 'af_menu_bar-item-text' and text() = '"+sandboxName+"']")).click();
				
				CommonUtils.hold(3);

				System.out.println("Initiating < "+sandboxName+" >sandbox session exit");
				
				driver.findElement(By.xpath("//tr[contains(@id,'pt1:_UISsbb:0:r1:0:_sbcmi5')]/td[2]")).click();
			
				CommonUtils.hold(3);
				System.out.println("Exit < "+sandboxName+" > Sandbox session confirm");
				
				//driver.findElement(By.xpath("//button[contains(@id,'pt1:_UISsbb:0:r1:0:_sbd2::ok')]")).click();
				driver.findElement(By.xpath("//button[contains(@id,'pt1:_UISsbb:0:r1:0:_sbd2::yes')]")).click();
				
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(3);
				
				System.out.println("Sandbox < "+sandboxName+" > exited from its active session");
			}
		}catch(Exception aSBE) {
			System.out.println("No Active Sandbox's in this user seesion");
		}
	}//End of checkAndExitActiveSandboxSession()
	
	/*
	 * SetSandboxPreviewMode() sets the sandbox into PREVIEW mode
	 */
	public String SetSandboxPreviewMode(WebDriver driver) {
		
		System.out.println("SetSandboxPreviewMode() Begins");
		
		try {
			CommonUtils.hold(5);
			SandboxBanner.click();
			CommonUtils.hold(3);
				SandboxMode.click();
				CommonUtils.hold(3);
				SandboxMode_Preview.click();
					System.out.println("Flipping sandbox preview mode initiated");
			
				/*CommonUtils.explicitWait(NavigationPopUpLoad("PreviewMode",driver), "Visible", "",driver);
				CommonUtils.hold(3);*/
					
				CommonUtils.explicitWait(SandboxBannerPreviewNavigationConfirm, "Click", "", driver);
				CommonUtils.hold(3);
				SandboxBannerPreviewNavigationConfirm.click();
					System.out.println("Flipping sandbox preview mode confirmed");
				CommonUtils.explicitWait(SandboxMode, "TextPresent", "Sandbox Mode: Preview",driver);
			
			} catch (Exception SSPME) {
				System.out.println("Exception In SetSandboxPreviewMode()");
				SSPME.printStackTrace();
				Assert.fail();
			}
		return SandboxMode.getText();
	}//End of SetSandboxPreviewMode()
	
	public boolean ExitSandboxSession(String SandboxName, WebDriver driver) {
		boolean ExitSandboxSessionStatus = false;
		SandboxBanner.click();
			System.out.println("Loading <"+SandboxName+"> menu on sandbox banner");
			sandboxBannerName(SandboxName,driver).click();
		try {
			CommonUtils.explicitWait(SandboxMenuList("Leave Sandbox",driver), "Visible", "",driver);
		
				
			SandboxMenuList("Leave Sandbox",driver).click();
				System.out.println("Exit sandbox session from sandbox banner initiated");
			
			CommonUtils.explicitWait(SandboxBannerNavigationConfirm, "Visible", "",driver);
			CommonUtils.hold(3);
			SandboxBannerNavigationConfirm.click();
				System.out.println("Exit sandbox session from sandbox banner confirmed");
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(3);
			System.out.println("Verify waited");
			try {
				System.out.println("Verify after Exit");
				Assert.assertTrue(SandboxBanner.isDisplayed());
				System.out.println("Verified");
			}catch(Exception ESSE) {
					System.out.println("Successfully exited from <"+SandboxName+"> active sandbox session");
					System.out.println("News Feed PO "+ApplicationLogin.newsFeedEnabled);
					System.out.println("News Feed ele -> "+cUtilDefsInstance.newsFeed);
				if(ApplicationLogin.newsFeedEnabled && cUtilDefsInstance.newsFeed.isEnabled()) {
					System.out.println("News Feed Enabled on HomePage");
					ExitSandboxSessionStatus = true;
				}else if(fuseWelcomeSpringBoard.isDisplayed()) {
					System.out.println("News Feed not Enabled on HomePage");
					ExitSandboxSessionStatus = true;
				}
			}
		} catch (Exception ESSE) {
			System.out.println("Exception while cheking for LeaveSandbox link in sandbox Banner in ExitSandboxSession()");
			ESSE.printStackTrace();
		}
	return ExitSandboxSessionStatus;
		
	}//End of ExitSandboxSession()
	
	
}//End of SandboxBannerUI class
