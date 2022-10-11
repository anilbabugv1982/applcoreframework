/**
 * 
 */
package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import UtilClasses.UI.SetupAndMaintenance;
import UtilClasses.UI.ReportGenerator;
import pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox.*;
/**
 * @author klalam
 *
 */
public class SandboxDetailsUI extends SandboxUI{
	
	private static final Map<String, String> ConfirmationMessages;
	private static final Map<String, String> SandboxStatusMessages;
	
static {
		
		ConfirmationMessages = new HashMap<String, String>();
		
		ConfirmationMessages.put("Refresh", "The sandbox will be refreshed and during the refresh process");
		ConfirmationMessages.put("Publish", "You are going to publish this sandbox");
		ConfirmationMessages.put("ConflictAccept", "You will accept all the changes from mainline and merge into this sandbox");
		ConfirmationMessages.put("ConflictReject", "If you reject this merge, you will be brought back to the time point before you ran refresh");
		ConfirmationMessages.put("Delete", "This sandbox will be deleted and all changes in this sandbox will be lost");
		
		SandboxStatusMessages = new HashMap<String, String>();
		
		SandboxStatusMessages.put("Publish_Successful", "COMMITTED");
		SandboxStatusMessages.put("Publish_Ongoing", "PENDING_PUBLISH");
		SandboxStatusMessages.put("Refresh_Successful", "ACTIVE");
		SandboxStatusMessages.put("Refresh_Ongoing", "PENDING_REFRESH_BEGIN");
		SandboxStatusMessages.put("Conflict_Refresh_pending", "PENDING_REFRESH_RESUME");
		SandboxStatusMessages.put("Errored", "INACTIVE");
		
	}

	private NavigationTaskFlows ntfInstance;
	private NavigatorMenuElements nMEInstanceRef;

	
	public SandboxDetailsUI(WebDriver driver) {
		super(driver);
		System.out.println("Driver Instance -> "+driver);
		PageFactory.initElements(driver, this);
		
		ntfInstance = new NavigationTaskFlows(driver);
		nMEInstanceRef = new NavigatorMenuElements(driver);
	}
	
	static boolean PublishFromBanner = false;
	static boolean RefreshFromBanner = false;
	static boolean PublishFromActiveSandbox = false;
	public static String flexNotDeployedWithinSandboxMsg;
	
	

	@FindBy(xpath = "//table[@class = 'af_panelHeader_title-table0']/descendant::h1")
	public  WebElement SandboxDetailAssert;

	@FindBy(xpath = "//button[contains(@id , '_sdcb2')]")
	public  WebElement ActivateSandbox;

	/*@FindBy(xpath = "//button[contains(@id , '_sdcb2')]/ancestor::tr[1]/descendant::button[contains(@id , '_sdcb1')]")
	public  WebElement PublishSandbox;*/
	
	@FindBy(xpath = "//td[contains(@id , '_sdctb1::popArea')]")
	public  WebElement ActionsMenu;
	
	@FindBy(xpath = "//td[text() = 'Leave Sandbox']")
	public  WebElement ExitSandboxActiveSession;
	
	@FindBy(xpath = "//tr[contains(@id , '_sdcmi2')]/td[2]")
	public  WebElement DeleteSandbox;
	
	@FindBy(xpath = "//button[contains(@id , '_sdd2::yes')]")
	public  WebElement confirmSandboxDelete;
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb3')]")
	public  WebElement ExitSBDetailsUI;
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb7')]")
	public  WebElement RefreshSandbox;
	
	@FindBy(xpath = "//button[text() = 'Retry Refresh']")
	public  WebElement RetryRefreshSandbox;
	
	@FindBy(xpath = "//button[text() = 'Download Errors']")
	public  WebElement SandboxActionFailed;
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb8')]")
	public  WebElement AcceptConflicts;
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb9')]")
	public WebElement RejectConflicts;
	
	@FindBy(xpath = "//button[contains(@id , '_sdd14::ok')]")
	public  WebElement PublishNoChangesWarningOK;
	
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb2')]/ancestor::tr[1]/descendant::button[contains(@id , '_sdcb1')]")
	public  WebElement initiatePublish;
	
	@FindBy(xpath = "//button[contains(@id , '_sdd3::yes')]")
	public  WebElement confirmPublish;
	
	@FindBy(xpath = "//button[contains(@id , '_sdd3::no')]")
	public  WebElement discardPublish;
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb10')]")
	public  WebElement RefreshConfirm;
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb12')]")
	public  WebElement RefreshDiscard;
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb7')]")
	public  WebElement initiateRefresh;
	
	@FindBy(xpath = "//button[contains(text() , 'Download Errors')]")
	public WebElement RefreshFailure;
	
	@FindBy(xpath = "//span[text() = 'Refresh needed']")
	public WebElement SandboxRefreshNeeded;
	
	@FindBy(xpath = "//span[text() = 'Refreshing']")
	public  WebElement SandboxRefreshing;
	
	@FindBy(xpath = "//span[text() = 'Up to date']")
	public WebElement SuccesfullyRefreshed;
	
	@FindBy(xpath = "//span[text() = 'Published']")
	public WebElement SuccesfullyPublished;
	
	@FindBy(xpath = "//span[text() = 'Publishing']")
	public WebElement SandboxPublishing;
	
	@FindBy(xpath = "//span[contains(@id , '_sdot6')]")
	public WebElement SandboxCurrentStatusField;
	
	@FindBy(xpath = "//span[contains(text() , 'Your last refresh is cancelled due to failures')]")
	public WebElement SandboxRefreshFailureMsg;
	
	@FindBy(xpath = "//span[contains(text() , 'Your last publish is cancelled due to failures')]")
	public WebElement SandboxPublishFailureMsg;
	
	@FindBy(xpath = "//td[text() = 'You will accept all the changes from mainline and merge into this sandbox. Do you want to continue?']")
	public WebElement ConflictAcceptConfirmMsg;
	
	@FindBy(xpath = "//span[contains(text() , 'Your last refresh is cancelled due to unresolvable conflicts, the conflicts are listed in the merge log')]")
	public  WebElement SandboxRefreshUnresolvableConflictMsg;
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb13')]")
	public WebElement conflictAcceptConfirm;
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb14')]")
	public WebElement conflictAcceptDiscard;
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb15')]")
	public WebElement conflictRejectConfirm;
	
	@FindBy(xpath = "//button[contains(@id , '_sdcb16')]")
	public WebElement conflictRejectDiscard;
	
	@FindBy(xpath = "//div[contains(@id , '_sdd14') and @class = 'af_dialog']")
	public WebElement flexNotDeployedWarningDialog;
	
	@FindBy(xpath = "//td[contains(@id, '_sdd14::contentContainer')]/span")
	public WebElement flexNotDeployedMessageEle;
		
	@FindBy(xpath = "//button[contains(@id , '_sdcb17')]")
	public WebElement flexNotDeployedWarningOK;
	
	@FindBy(xpath = "//div[text() = 'This sandbox cannot be refreshed while you are in other sandbox.']")
	public WebElement RefreshSandboxMsg;

	@FindBy(xpath = "//h1[contains(text(), 'Sandbox Detail')]")
	public WebElement SandboxDetailUIAssert;
	
	@FindBy(xpath = "//button[text() = 'Download All']")
	public WebElement DownloadSandboxContent;
	
	@FindBy(xpath = "//div[contains(@id , '_sdtab1::ti')]/descendant::a[contains(@id , '_sdtab1::disAcr')]")
	public WebElement SandboxChangesUI;
	
	@FindBy(xpath = "//label[text() = 'Search']/ancestor::tr[1]/descendant::input[@type= 'text']']")
	public WebElement SandboxChangesTextInput;
	
	@FindBy(xpath = "//label[text() = 'Search']/ancestor::tr[2]/descendant::a")
	public WebElement SandboxChangesSearchInitiate;
	
	@FindBy(xpath = "//div[contains(@id , '_sdtab2::ti')]/descendant::a[contains(@id , '_sdtab2::disAcr')]")
	public WebElement MergeLogUI;
	
	@FindBy(xpath = "//img[@title = 'Auto Merged']/ancestor::tr[1]/descendant::span[1]")
	public WebElement AutoMergeConflictsCount;
	
	@FindBy(xpath = "//img[@title = 'Auto Merged']/ancestor::tr[1]/descendant::span[2]")
	public WebElement ResolvableConflictsCount;
	
	@FindBy(xpath = "//img[@title = 'Auto Merged']/ancestor::tr[1]/descendant::span[3]")
	public WebElement UnResolvableConflictsCount;
	
	@FindBy(xpath = "//button[contains(@id , '_FOpt1:_FOr1:0:_FOSritemNode_tools_sandboxes:0:MAnt2:1:r1:0:_sdd3') and text() = 'No']")
	public WebElement PublishDiscard;
	
	/* Adding tools to an existing sandbox
	 * 
	 */
	
	@FindBy(xpath = "//img[contains(@id , 'sdcil1::icon')]")
	public WebElement AddToolIcon;
	
	@FindBy(xpath = "//div[text() = 'All Tools']")
	public WebElement AllToolsPopup;
	
	@FindBy(xpath = "//div[text() = 'All Tools']/ancestor::table[2]/descendant::button[text() = 'OK']")
	public WebElement AllToolsPopup_Ok;
	
	/* Refresh Confirmation Msg 			-> The sandbox will be refreshed and during the refresh process 								->  "Yes/No"
	 * Conflict Refresh Accept Confirmation -> You will accept all the changes from mainline and merge into this sandbox					->  "Yes/No"
	 * Conflict Refresh Reject Confirmation	-> If you reject this merge, you will be brought back to the time point before you ran refresh	->  "Yes/No"
	 * Publish Confirmation					-> You are going to publish this sandbox														->	"Continue to Publish/No"
	 * Delete Confirmation Msg 				-> This sandbox will be deleted and all changes in this sandbox will be lost					->  "Yes/No"
	 * 
	 */
	
	public void SandboxOperationConfirmation(String SandboxOperation, String ConfirmOrDiscard, WebDriver driver) {
		WebElement ConfirmationElement;
		try {
			ConfirmationElement = driver.findElement(By.xpath("//span[contains(text() , '"+ConfirmationMessages.get(SandboxOperation).toString()+"')]/ancestor::tbody/descendant::button[text() = '"+ConfirmOrDiscard+"']"));
		}catch(Exception SOCE) {
			ConfirmationElement = driver.findElement(By.xpath("//td[contains(text() , '"+ConfirmationMessages.get(SandboxOperation).toString()+"')]/ancestor::tbody/descendant::button[text() = '"+ConfirmOrDiscard+"']"));
		}
		CommonUtils.hold(10);
		ConfirmationElement.click();
	}
	
	WebElement SandboxOperationConfirmationElement(String SandboxOperation, WebDriver driver) {
		WebElement OperationConfirmationElement = driver.findElement(By.xpath("//span[contains(text() , '"+ConfirmationMessages.get(SandboxOperation).toString()+"')]"));
	return OperationConfirmationElement;
	}
	
	/*
	 * FilterSandboxChanges(String FilterCriteria,String SearchableObject) will filter the sandbox changes appeared within the sandbox
	 */
	
	public void FilterSandboxChanges(String FilterCriteria,String SearchableObject, WebDriver driver) {
		Select FilteCriteriaElement = new Select(driver.findElement(By.xpath("//label[text() = 'Search']/ancestor::tr[1]/descendant::select")));
		FilteCriteriaElement.selectByVisibleText(FilterCriteria);
		//<<<<<<<<<<<--------------WAIT FUNCTION--------->
		SandboxChangesTextInput.sendKeys(SearchableObject);
		SandboxChangesSearchInitiate.click();
	}
	
	/*
	 * InvokeSandboxPublish() will trigger sandbox publish operation
	 */
	String InvokeSandboxPublish(String PublishableSandboxName,WebDriver driver) throws Exception {
		String publishStatus = null;
		flexNotDeployedWithinSandboxMsg = null;
		/*
		  * Check if the lock is acquired any sandbox prior to initiate current sandbox publish
		  */
		 
		 if(SandboxUtils.verifySandboxRepositoryLock() == true) {
			 while(SandboxUtils.verifySandboxRepositoryLock() == true) {
				 System.out.println("Acquired Locks are not released yet");
				 CommonUtils.hold(20);
			 }
			 
			 System.out.println("Acquired locks are released");
		 }//End of sandbox lock check
		 
		 CommonUtils.hold(5);
		 System.out.println("Sandbox "+PublishableSandboxName+" may went to Refreh state, if another sandbox pulished to mainline prior to this. Check and refresh sandbox before initiating publish.");
		 //Sandbox to be refresh after acquired locks are released before initiating publish
		 refreshPublishableSandbox(PublishableSandboxName,driver);
		 
		 System.out.println("Proceed with Sandbox "+PublishableSandboxName+" Publish");
		 
		 CommonUtils.hold(15);
		 
		 System.out.println("Invoke Sandbox "+PublishableSandboxName+" Publish");
		
		initiatePublish.click();
			System.out.println("Sandbox "+PublishableSandboxName+" Publish Initiated");
			CommonUtils.hold(5);
		try {
			CommonUtils.explicitWait(confirmPublish, "Click", "",driver);
			confirmPublish.click();
			System.out.println("Sandbox "+PublishableSandboxName+" Publish Confirmed and Initiated");
			CommonUtils.hold(10);
						
			}catch(Exception pBE) {
				System.out.println("Sandbox "+PublishableSandboxName+" publish not invoking"+pBE.getMessage());
				System.out.println("Checking if flex repo activated and updated flexfields not deployed within sandbox session");
				try {
					Assert.assertTrue(flexNotDeployedWarningDialog.isDisplayed());
					flexNotDeployedWithinSandboxMsg = flexNotDeployedMessageEle.getText();
					System.out.println("Publish not invoked due to : "+flexNotDeployedWithinSandboxMsg);
					flexNotDeployedWarningOK.click();
				}catch(Exception pbe1) {
						System.out.println("Sandbox publish invocation failure not due to Flexfields");
						System.out.println("Check if no changes publish warning popped up");
					try {
						 CommonUtils.explicitWait(PublishNoChangesWarningOK, "Click","",driver);
					    PublishNoChangesWarningOK.click();
						}
						catch(Exception sdk) {
							System.out.println("NoChanges Publish warning didnot popup"+sdk.getMessage());
							sdk.printStackTrace();
							pBE.printStackTrace();
						}
				}
			}
		
			publishStatus = GetSandboxStatus(PublishableSandboxName,"COMMITTED","PENDING_PUBLISH",driver);
		
			System.out.println("Final Status of Sandbox "+PublishableSandboxName+" Publish : "+publishStatus);
		
			CommonUtils.hold(15);
			
		try {
			if(flexNotDeployedWithinSandboxMsg!= null) {
				if(ActivateSandbox.isEnabled()) {
					ActivateSandbox.click();
					CommonUtils.explicitWait(sandboxBannerName(PublishableSandboxName,driver), "Visible", "", driver);
				}
			CommonUtils.hold(5);
			}else {
				ExitSBDetailsUI.click();
				 CommonUtils.explicitWait(InitiateSandboxCreate, "Visible", "",driver);
			}
		}catch(Exception ExitSB) {
				try {
					CommonUtils.explicitWait(InitiateSandboxCreate, "Visible", "",driver);
				} catch (Exception e) {
					e.printStackTrace();
				}
			;
		}
		
	return publishStatus;		
	}//End of InvokeSandboxPublish()
	
	public void InitiateSandboxPublishFromBanner(String SandboxName, WebDriver driver) {
		try {
			sandboxBannerName(SandboxName,driver).click();
				System.out.println("SandboxName "+SandboxName+" Menulist loaded and retrieving publish element from banner");
				CommonUtils.hold(5);
				WebElement	publishFromBannerElement = SandboxMenuList("Publish",driver);
			CommonUtils.explicitWait(publishFromBannerElement, "Visible", "",driver);
				System.out.println("Publish element from banner found and initiating "+SandboxName+" publish from banner");
			publishFromBannerElement.click();
				System.out.println("Sandboxpublish "+SandboxName+" from banner initiated");
			CommonUtils.explicitWait(SandboxBannerPublishNavigationConfirm, "Visible", "",driver);
			CommonUtils.hold(5);
			SandboxBannerPublishNavigationConfirm.click();
				System.out.println("Sandboxpublish "+SandboxName+" from banner confirmed");
			
			PublishFromBanner=true;
			
			//CommonUtils.explicitWait(initiatePublish, "Click", "",driver);
			CommonUtils.explicitWait(SandboxChangesUI, "Click", "",driver);
			CommonUtils.hold(2);
			SandboxChangesUI.click();
			CommonUtils.hold(5);
			SandboxPublish(SandboxName,driver); //Invoke SandboxPublish()
		}catch(Exception ISPFE) {
			System.out.println("Exception in InitiateSandboxPublishFromBanner()");
			ISPFE.printStackTrace();
			Assert.fail();
		}
		
	}//End of InitiateSandboxPublishFromBanner()
	
	/*
	 * SandboxPublish(String PublishableSandboxName, WebDriver driver) initiates sandbox publish
	 */
	
	public void SandboxPublish(String PublishableSandboxName, WebDriver driver) {
		 try {
			 System.out.println("Sandbox "+PublishableSandboxName+" Publish Begin"); 
			
			 //Check if publish initiated from banner and execute IF condition if publish doesn't initiated from banner 
			 if(!(PublishFromBanner)) {
				 System.out.println("Sandbox "+PublishableSandboxName+" publish not initiated from sandbox banner");
				 	CommonUtils.hold(5);
				 	/*gTlInstance.userDropDownIcon.click();
					CommonUtils.explicitWait(gTlInstance.administration_SetupAndMaintenance,"Visible","");
					gTlInstance.administration_SetupAndMaintenance.click();
					CommonUtils.explicitWait(SetupAndMaintenance.panelDrawer, "Click", ""); */
				 	
				 	try {
				 		accessFromRecentItems("Manage Sandboxes",driver).click();
				 		}catch(Exception eI) {
				 			System.out.println("In EnterSandboxSession() <-> Exeption while accessing sandbox ui from Favorite and RecentItems. Navigating to Sandoxes tf from Navigator Menu");
				 			eI.printStackTrace();
				 			ntfInstance.navigateToTask(nMEInstanceRef.Sandboxes,driver);
				 	}
						 CommonUtils.explicitWait(getSandboxNameElement(PublishableSandboxName,driver), "TextPresent", PublishableSandboxName,driver);
						 NavigateToSandboxDetailsUI(PublishableSandboxName,driver); // Navigate to SandboxDetailsUI
			 }
			CommonUtils.hold(10);
			
			//Check and perform refresh on sandbox to be refreshed prior to publish
			refreshPublishableSandbox(PublishableSandboxName,driver);
				 
			 
			 try {
				  if(ToolsMenu.isDisplayed()) {
					  System.out.println("Sandbox "+PublishableSandboxName+" is in active state");
					  PublishFromActiveSandbox = true;
					  System.out.println("Active Sandbox Status - "+PublishFromActiveSandbox);
				  }
					  
			 }catch(Exception Internal) {
				 PublishFromActiveSandbox = false;
			 }
			 
			 /*
			  * Check if the lock is acquired any sandbox prior to initiate current sandbox publish
			  
			 
			 if(SandboxUtils.verifySandboxRepositoryLock() == true) {
				 while(SandboxUtils.verifySandboxRepositoryLock() == true) {
					 System.out.println("Acquired Locks are not released yet");
					 CommonUtils.hold(20);
				 }
				 
				 System.out.println("Acquired locks are released");
				 
				 //If the sandbox to be refresh after acquired locks are released before initiating publish
				 refreshPublishableSandbox(PublishableSandboxName,driver);
				 
				 CommonUtils.hold(15);
			 }//End of sandbox lock check
			 
			 System.out.println("Invoke Sandbox Publish");*/
			 
			 System.out.println(PublishableSandboxName+" PublishFromBanner Status - "+PublishFromBanner);
			 System.out.println(PublishableSandboxName+" Sandbox Active Status - "+PublishFromActiveSandbox);
			 
			 	//Initiate Sandbox Publish
			 String publishStatus =	InvokeSandboxPublish(PublishableSandboxName,driver);
			 
			 			 
			/* while(publishStatus.contains("COMMITTED")) {
				 if(publishStatus.contains("COMMITTED") || publishStatus.contains("INACTIVE"))
					 break;
				 CommonUtils.hold(30);
			 }*/
			/* CommonUtils.hold(15);
			try {
				 ExitSBDetailsUI.click();
				 CommonUtils.explicitWait(InitiateSandboxCreate, "Visible", "",driver);
			}catch(Exception ExitSB) {
				CommonUtils.explicitWait(InitiateSandboxCreate, "Visible", "",driver);
				;
			}*/
			PublishFromBanner = false;
			//Verify state of the sandbox after publish
			CommonUtils.hold(5);
			/*if(publishStatus.contains("COMMITTED")) {
				System.out.println("Verify sandbox publish operation begins");
					verfiySandboxPublish(PublishableSandboxName,driver);
					CommonUtils.hold(5);
			System.out.println("Verify sandbox publish operation ends"); 
			}else if(publishStatus.contains("ACTIVE")) {
				System.out.println("Sandbox Publish "+PublishableSandboxName+" didn't went due to another sandbox publish running at same time. (i.e.) 2 sandboxes publish operations confirmed at same time");
				
			}*/
			
			if(publishStatus.equalsIgnoreCase("COMMITTED")) {
				System.out.println("Verify sandbox "+PublishableSandboxName+" publish operation begins");
					verfiySandboxPublish(PublishableSandboxName,driver);
					CommonUtils.hold(5);
				System.out.println("Verify sandbox "+PublishableSandboxName+" publish operation ends"); 
			}else if (publishStatus.equalsIgnoreCase("ACTIVE")) {
				if(flexNotDeployedWithinSandboxMsg!=null) {
					System.out.println("Sandbox publish halted due to updated flex fields not deployed within sandbox sesson. Please deploy flexfields and retry publish again");
					
				}else {
					System.out.println("Sandbox Publish "+PublishableSandboxName+" didn't went due to another sandbox publish running at same time. (i.e.) 2 sandboxes publish operations confirmed at same time");
					CommonUtils.hold(5);
					try {
						System.out.println("Sandbox "+PublishableSandboxName+" publish reattempt. Chekcing whether request in sandbox details ui or not");
						 if (ExitSBDetailsUI.isDisplayed())
							 System.out.println("Sandbox "+PublishableSandboxName+" request is in details ui.");
					}catch(Exception rSbPub) {
							System.out.println("Sandbox "+PublishableSandboxName+" request not in details ui. Navigating to sandbox details ui");
							NavigateToSandboxDetailsUI(PublishableSandboxName,driver); 
							System.out.println("In Sandbox "+PublishableSandboxName+" details ui");
							CommonUtils.hold(5);
					}
					String status = InvokeSandboxPublish(PublishableSandboxName,driver);
					CommonUtils.hold(5);
					if (status.equalsIgnoreCase("COMMITTED")) {
						System.out.println("Verify sandbox "+PublishableSandboxName+" publish operation begins");
						verfiySandboxPublish(PublishableSandboxName,driver);
						CommonUtils.hold(5);
					System.out.println("Verify sandbox "+PublishableSandboxName+" publish operation ends"); 
					}
				}
			}else if(publishStatus.equalsIgnoreCase("INACTIVE")) {
				System.out.println("Staus of "+PublishableSandboxName+" is "+publishStatus);
				System.out.println("Sandbox "+PublishableSandboxName+" publish failed. Please review the logs");
				 Assert.fail();
			}
			System.out.println("Sandbox "+PublishableSandboxName+" Publish End"); 
		 }catch(Exception PSE) {
			 System.out.println("Exception in Sandbox "+PublishableSandboxName+" Publish()");
			 PSE.printStackTrace();
			 Assert.fail();
		 }
	 }//End of SandboxPublish()
	
	/*
	 * InvokeSandboxRefresh() invokes sandbox refresh
	 */
	void InvokeSandboxRefresh(WebDriver driver) {
		CommonUtils.hold(5);
		RefreshSandbox.click(); //Initiate Refresh.
			System.out.println("Sandbox refresh initiated");
			try {
				CommonUtils.explicitWait(RefreshConfirm, "Click", "", driver);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail();
			}
		CommonUtils.hold(3);
		RefreshConfirm.click();
			System.out.println("Sandbox refresh confirmed and refresh in progress");
		CommonUtils.hold(10);
	}//End of InvokeSandboxRefresh()
	
	/*
	 * SandboxRefresh(String RefershableSandboxName,, String ConflictAction) initiates Sandbox Refresh
	 * ConflictAction -> Accept or Reject conflicts
	 */
	public void SandboxRefresh(String RefershableSandboxName, String ConflictAction, WebDriver driver) {
		String CheckForConflictRefresh;
		try {
			System.out.println("Sandbox "+RefershableSandboxName+" Refresh Begin");
				AvailableSandboxesLabel.click();
			
			//If Refresh doesn't initiated from Sandbox banner
			if(!(RefreshFromBanner))  
				NavigateToSandboxDetailsUI(RefershableSandboxName,driver); // Navigate to SandboxDetailsUI
			
			/*if(PublishSandbox.isEnabled()) {
				System.out.println("Publish is enabled");
				return;
			}*/
			CommonUtils.explicitWait(RefreshSandbox, "Click", "",driver);
			InvokeSandboxRefresh(driver);
			CommonUtils.explicitWait(InitiateSandboxCreate, "Click", "",driver);
			
			CheckForConflictRefresh = GetSandboxStatus(RefershableSandboxName,"PENDING_REFRESH_RESUME","PENDING_REFRESH_BEGIN",driver);
				System.out.println("Sandbox "+RefershableSandboxName+" refresh state <"+CheckForConflictRefresh+">");
			
			if(CheckForConflictRefresh.equalsIgnoreCase("PENDING_REFRESH_RESUME")) {
					System.out.println("Initiate conflict refresh on sandbox"+RefershableSandboxName);
				SandboxConflictRefresh(RefershableSandboxName,ConflictAction,driver);
			}else {
				try {
					System.out.println("Request is in Sandbox "+RefershableSandboxName+" details Page");
					ExitSBDetailsUI.click();
					CommonUtils.explicitWait(InitiateSandboxCreate, "Click", "",driver);
				}catch(Exception ruE) {
					System.out.println("Request is in Sandbox "+RefershableSandboxName+" overview Page");
					CommonUtils.explicitWait(InitiateSandboxCreate, "Click", "",driver);
				}
			}
			CommonUtils.hold(5);
			//Verify state of the sandbox after refresh
		/*		System.out.println("Verify sandbox refresh operation begins");
			VerifySandboxRefresh(RefershableSandboxName);
				System.out.println("Verify sandbox refresh operation ends"); 
		*/
		}catch(Exception RSNE){
			System.out.println("Exception in Sandbox "+RefershableSandboxName+" Refresh ");
			//ReportGenerator.testReporter.log(Status.FAIL,ExceptionUtils.getStackTrace(RSNE));
			RSNE.printStackTrace();
			Assert.fail();
		}
	}//End of SandboxRefresh()
	
	/*
	 * initiateRefreshfromBanner() method initiate sandbox refresh from sandbox banner
	 */
	public void initiateRefreshfromBanner(WebDriver driver) {
		try {
			RefreshNeededLink.click();
				System.out.println("Sandbox refresh from banner initiated");
			CommonUtils.hold(5);
			RefreshNeeded_SandboxDetailLink.click();
			CommonUtils.explicitWait(RefreshSandbox, "Visible", "",driver);
				System.out.println("Navigated to sandbox detailsui to start refresh");
			RefreshFromBanner = true;
		}catch(Exception iRfBE) {
			iRfBE.printStackTrace();
			System.out.println("Exception in initiateRefreshfromBanner()");
		}
	}//End of initiateRefreshfromBanner()
	
	/*
	 * VerifySandboxRefresh(String RefreshedSandboxName) verifies Refreshed sandbox state
	 */
	public boolean VerifySandboxRefresh(String RefreshedSandboxName, WebDriver driver) {
		try {
			System.out.println("VerifySandbox "+RefreshedSandboxName+" Refresh Begin");
			CommonUtils.hold(20);
			accessFromRecentItems("Manage Sandboxes",driver).click();
			CommonUtils.explicitWait(InitiateSandboxCreate, "Click", "",driver);
			NavigateToSandboxDetailsUI(RefreshedSandboxName,driver);
			
			String RefreshStatus = GetSandboxStatus(RefreshedSandboxName,"ACTIVE","PENDING_REFRESH_BEGIN",driver);
				System.out.println("Sandbox "+RefreshedSandboxName+" status after successful refresh <"+RefreshStatus+">");
			/*
			 * Condition to check for conflict refresh failed after "Accept"
			*/
				System.out.println("Check for <INACTIVE> status");	
			if(RefreshStatus.equalsIgnoreCase("INACTIVE")) {
				if(SandboxActionFailed.isDisplayed()) {
						System.out.println("Sandbox "+RefreshedSandboxName+" Refresh failed With an Error");
					return false;
				}
			}
			
			System.out.println("Check for <INACTIVE> status done");
			
			/*
			 * Condition to check during conflict refresh if "EnterSandbox" and "Publish" buttons are enabled
			 */
			System.out.println("Check for <ACTIVE> status");
			if(RefreshStatus.equalsIgnoreCase("ACTIVE")) {
				try {
				if(RetryRefreshSandbox.isDisplayed() && SandboxRefreshUnresolvableConflictMsg.isDisplayed())
				{
						System.out.println("Sandbox "+RefreshedSandboxName+" refresh is failed due to unresolvable conflicts. Please review the logs");
					return false;
					}
				}catch(Exception e) {
					try {
						if(RefreshSandbox.isDisplayed() && SandboxRefreshFailureMsg.isDisplayed()) {
							System.out.println("Sandbox "+RefreshedSandboxName+" refresh is failed. Please review the logs");
						return false;
						}
					}catch(Exception rSE) {
						Assert.assertTrue(true);
					}
				}
			}
			System.out.println("Check for <ACTIVE> status done");
						
			CommonUtils.explicitWait(ExitSBDetailsUI, "Click", "",driver);
			ExitSBDetailsUI.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(InitiateSandboxCreate, "Click", "",driver);
			CommonUtils.hold(10);
			CommonUtils.explicitWait(getSandboxNameElement(RefreshedSandboxName,driver), "Visible", "",driver);
			CommonUtils.hold(5);
		}catch(Exception VSRE) {
			System.out.println("Exception in VerifySandbox "+RefreshedSandboxName+" Refresh");
			VSRE.printStackTrace();
			Assert.fail();
		}
	return true;
	}//End of VerifySandboxRefresh()
	
	/*
	 * SandboxConflictRefresh(String ConflictRefreshableSandboxName,String SandboxConflictAction) to perform conflict refresh on the sandbox if conflicts are reported during sandbox refresh
	 * SandboxConflictAction -> Accpet/Reject
	 */
	public void SandboxConflictRefresh(String ConflictRefreshableSandboxName,String SandboxConflictAction, WebDriver driver) {
		try {
			System.out.println("Sandbox "+ConflictRefreshableSandboxName+" ConflictRefresh Begin"); 
			try {
				System.out.println("Check and Navigating to Sandbox "+ConflictRefreshableSandboxName+" Details Page");
					NavigateToSandboxDetailsUI(ConflictRefreshableSandboxName,driver);
						CommonUtils.explicitWait(ExitSBDetailsUI, "Click", "",driver);
				if(ActivateSandbox.isEnabled() ) {
					System.out.println("Enter sandbox "+ConflictRefreshableSandboxName+" should be disabled during sandbox conflict refresh state");
				return;
				}
				
				if(SandboxConflictAction.equalsIgnoreCase("Accept")) {
					if(!(AcceptConflicts.isDisplayed())) {
							System.out.println("Accept button doesn't exist in sandbox ui to accept sandbox "+ConflictRefreshableSandboxName+" conflicts");
						return;
					}
					AcceptConflicts.click();
						System.out.println("Conflicts Accepted");
					CommonUtils.hold(10);
					//SandboxOperationConfirmation("ConflictAccept", "Yes",driver)
					conflictAcceptConfirm.click();
					CommonUtils.hold(5);
						System.out.println(ConflictRefreshableSandboxName+" Conflict refresh is inprogress");
				}else {
					if(!(RejectConflicts.isDisplayed())) {
							System.out.println("Reject button doesn't exist in sandbox "+ConflictRefreshableSandboxName+" ui to Reject sandbox conflicts");
						return;
					}
					RejectConflicts.click();
						System.out.println("Conflicts Rejected");
					CommonUtils.hold(10);
						//SandboxOperationConfirmation("ConflictReject", "Yes",driver);
					conflictRejectConfirm.click();
					CommonUtils.hold(5);
					System.out.println("Conflicts Rejected");
				}
			}catch(Exception cRSBE) {
					System.out.println("Request scope is on refreshable sandbox <"+ConflictRefreshableSandboxName+"> details page");
					if(SandboxConflictAction.equalsIgnoreCase("Accept")) {
						if(!(AcceptConflicts.isDisplayed())) {
								System.out.println("Accept button doesn't exist in sandbox "+ConflictRefreshableSandboxName+" ui to accept sandbox conflicts");
							return;
						}
						AcceptConflicts.click();
							System.out.println("Conflicts Accepted");
						CommonUtils.hold(5);
						//SandboxOperationConfirmation("ConflictAccept", "Yes",driver);
						conflictAcceptConfirm.click();
							CommonUtils.hold(5);
							System.out.println("Conflict refresh is inprogress");
					}else {
						if(!(RejectConflicts.isDisplayed())) {
							System.out.println("Reject button doesn't exist in sandbox "+ConflictRefreshableSandboxName+" ui to Reject sandbox conflicts");
							return;
						}
						RejectConflicts.click();
							System.out.println("Conflicts Rejected");
						CommonUtils.hold(5);
						//SandboxOperationConfirmation("ConflictReject", "Yes",driver);
						conflictRejectConfirm.click();
						CommonUtils.hold(5);
						System.out.println("Conflicts Rejected");
					}
				}
				
				String getConflictedRefreshStatus = GetSandboxStatus(ConflictRefreshableSandboxName,"ACTIVE","PENDING_PUBLISH",driver);
				CommonUtils.hold(5);
				getConflictedRefreshStatus = getSandboxFinalStatus(ConflictRefreshableSandboxName);
				while(!(getConflictedRefreshStatus.contains("ACTIVE"))) {
					 if(getConflictedRefreshStatus.contains("ACTIVE") || getConflictedRefreshStatus.contains("INACTIVE"))
						 break;
					 CommonUtils.hold(30);
					 getConflictedRefreshStatus = getSandboxFinalStatus(ConflictRefreshableSandboxName);
					 System.out.println("Conflict refresh is in-progress -> "+getConflictedRefreshStatus);
				 }
				
				if(getConflictedRefreshStatus.equalsIgnoreCase("ACTIVE"))
					System.out.println("Sandbox "+ConflictRefreshableSandboxName+" conflict refresh is successful");
				else
					System.out.println("Sandbox "+ConflictRefreshableSandboxName+" conflict refresh is unsuccessful");
				CommonUtils.hold(5);
				ExitSBDetailsUI.click();
				//<<<Add logic to check for merge logs and exit from sandbox details ui after conflicts>>>
					System.out.println("Sandbox "+ConflictRefreshableSandboxName+" conflict refresh succesfully completed");
				System.out.println("SandboxConflictRefresh() End");
					
		}catch(Exception SCRE) {
			System.out.println("Exception in Sandbox "+ConflictRefreshableSandboxName+" ConflictRefresh()");
				SCRE.printStackTrace();
			Assert.fail();
		}
		
	}//End of SandboxConflictRefresh()
	
	
	
	/*public static void LeaveSandbox() {
		
	}*/
	
	public void addTool(String RepositoryName, WebDriver driver) {
		try {
			System.out.println("Adding Tool initiated");
			System.out.println("Activating <"+RepositoryName+"> to the sandbox initiated");
			AddToolIcon.click();
			CommonUtils.explicitWait(AllToolsPopup_Ok, "Click", "",driver);
			try {
				System.out.println("Wait for repository element");
				driver.findElement(By.xpath("//span[text() = '"+RepositoryName+"']/ancestor::tr[@_afrrk]/descendant::label")).click();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CommonUtils.hold(20);
			AllToolsPopup_Ok.click();
			CommonUtils.explicitWait(VerifyAddedTool(RepositoryName,driver), "Visible", "",driver);
			System.out.println("<"+RepositoryName+"> has been activated to the sandbox");
			System.out.println("Tool "+RepositoryName+" has been added");
		}catch(Exception aTE) {
			System.out.println("Exception in addTool()");
			aTE.printStackTrace();
		}
	}//End of addTool()
	
	public WebElement VerifyAddedTool(String RepositoryName, WebDriver driver) {
		WebElement GetAddedToolEle = driver.findElement(By.xpath("//img[contains(@src , 'addtool')]/ancestor::div[2]/descendant::span[text() = '"+RepositoryName+"']"));
		return GetAddedToolEle;
	}
	
	/*
	 * refreshPublishableSandbox() will perform refresh on the sandbox that to be published if the sandbox required refresh prior to publish
	 */
	public void refreshPublishableSandbox(String sandboxName,WebDriver driver) {
		 try {
				if(RefreshSandbox.isDisplayed())
				 {
					 System.out.println("Sandbox "+sandboxName+" doesn't sync with Mainline, sandbox needs to be refreshed before initiating sandbox publish");
					 	InvokeSandboxRefresh(driver);
					 		CommonUtils.hold(3);
					 System.out.println("Sandbox "+sandboxName+" Refresh Completed");
					 		CommonUtils.explicitWait(InitiateSandboxCreate, "Click", "",driver);
					 		CommonUtils.hold(5);
					 	NavigateToSandboxDetailsUI(sandboxName,driver);
					 System.out.println("Check status of sandbox "+sandboxName+" refresh operation ");
						String publishedRefreshStatus = GetSandboxStatus(sandboxName,"ACTIVE","PENDING_REFRESH_BEGIN",driver);
							CommonUtils.hold(10);
						if(publishedRefreshStatus.equalsIgnoreCase("PENDING_REFRESH_RESUME")) {
								System.out.println("Sandbox "+sandboxName+" had conflicts to resolve. Resolve the conflicts before proceeding to Sandbox publish");
								CommonUtils.hold(3);
								if(AcceptConflicts.isDisplayed()) {
									System.out.println("Conflicted objects are detected during sandbox "+sandboxName+" refresh. Perform Sandbox conflict refresh");
										AcceptConflicts.click();
									System.out.println("Conflicts Accepted");
										CommonUtils.hold(5);
											//SandboxOperationConfirmation("ConflictAccept", "Yes",driver);
										conflictAcceptConfirm.click();
										CommonUtils.hold(5);
								System.out.println(sandboxName+" Conflict refresh is inprogress");
									String getConflictedRefreshStatus = GetSandboxStatus(sandboxName,"ACTIVE","PENDING_PUBLISH",driver);
								CommonUtils.explicitWait(initiatePublish, "Click", "",driver);
								//----- Add logic for conflict refresh failed
								}
						}else if(publishedRefreshStatus.equalsIgnoreCase("INACTIVE") && RefreshSandbox.isDisplayed()) {
								System.out.println("Sandbox "+sandboxName+" Refresh failed. Please review the logs for the failure");
							Assert.fail();
							return;
						}
				 }
			}catch (Exception NoSuchElementException) {
				System.out.println("Sandbox is not in refresh needed state");
			}
	}//End Of refreshPublishableSandbox()
	
	/*
	 * deleteSandbox() method will delete the sandbox from Sandboxes ui
	 */
	public void deleteSandbox(WebDriver driver) {
		try {
			CommonUtils.explicitWait(ActionsMenu, "Click", "",driver);
			CommonUtils.hold(5);
			System.out.println("Initiating Sandbox Delete");
			ActionsMenu.click();
			CommonUtils.explicitWait(DeleteSandbox, "Click", "",driver);
			CommonUtils.hold(2);
			DeleteSandbox.click();
			CommonUtils.explicitWait(confirmSandboxDelete, "Click", "",driver);
			CommonUtils.hold(2);
			confirmSandboxDelete.click();
			CommonUtils.waitForPageLoad(driver);
			System.out.println("Sandbox successfully Deleted");
		}catch(Exception dSE) {
			System.out.println("Exception in deleteSandbox()");
			dSE.printStackTrace();
		}
	}//End of deleteSandbox()
	
	/*public String getListofFlexfieldsToDeployInSandbox() {
		
	}
	*/
}//End of SandboxDetailsUI class
