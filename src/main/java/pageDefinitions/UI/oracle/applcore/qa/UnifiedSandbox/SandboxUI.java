package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.util.ArrayList;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import UtilClasses.UI.ReportGenerator;


public class SandboxUI extends SandboxBannerUI{

	private NavigatorMenuElements nMenuEleInstance;
	private NavigationTaskFlows ntfInstance;
		
	public SandboxUI(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		nMenuEleInstance = new NavigatorMenuElements(driver);
		ntfInstance = new NavigationTaskFlows(driver);
	}

	static String SandboxPublishStatus = "";

	@FindBy(xpath = "//img[@alt = 'My Home']")
	public  WebElement HomePageIcon;

	@FindBy(xpath = "//h1[text() = 'Sandboxes']")
	public  WebElement SandboxUIAssert;
	
	//@FindBy(xpath = "//button[text()='Create Sandbox']")
	@FindBy(xpath = "//button[contains(@id , '_stcb2')]")
	public  WebElement InitiateSandboxCreate;
	
	/*@FindBy(xpath = "//label[text() = 'Find']/ancestor::tr[1]/descendant::input[@type = 'text']")
	public  WebElement SandboxSearchableElement;
	
	@FindBy(xpath = "//label[text() = 'Find']/ancestor::tr[2]/descendant::img[@title = 'Find']")
	public  WebElement InitiateSandboxSearch;*/
	
	@FindBy(xpath = "//input[contains(@id , '_stit2::content')]")
	public  WebElement sandboxFilterValue;
	
	@FindBy(xpath = "//img[contains(@id , '_stb1::icon')]")
	public  WebElement InitiateSandboxSearch;
	
	@FindBy(xpath = "//img[contains(//img[contains(@id , 'MainlineActivities::icon')]")
	public  WebElement MainlineActivities;
	
	//@FindBy(xpath = "//a[text() = 'Available Sandboxes']")
	@FindBy(xpath = "//a[contains(@id , 'sttab1::disAcr') and not(contains(@id , 'sttab1::disAcrCnvr'))]")
	public  WebElement AvailableSandboxesLabel;
	
	/*@FindBy(xpath = "//div[@_afrptkey = '_FOpt1:_FOr1:0:_FOSritemNode_tools_sandboxes:0:_FOTsr1:0:r1:0:_sttab1']//*[text()='Available Sandboxes']")
	public  WebElement AvailableSandboxesTab;*/
	
	//@FindBy(xpath = "//a[text() = 'Published Sandboxes']")
	@FindBy(xpath = "//a[contains(@id , 'sttab3::disAcr') and not(contains(@id , 'sttab3::disAcrCnvr'))]")
	public  WebElement PublishedSandboxesLabel;
	
	@FindBy(xpath = "//div[@id = '_FOpt1:_FOr1:0:_FOSritemNode_tools_sandboxes:0:_FOTsr1:0:r1:0:_stt1']")
	public  WebElement PublishedSandboxesTabAssert;
	
	@FindBy(xpath = "//a[contains(@id , 'sttab1::disAcr') and not(contains(@id , 'sttab1::disAcrCnvr'))]")
	public  WebElement AvailableSandboxListTable;
	
	@FindBy(xpath = "//a[contains (@id , '_stcil1') and contains(@class, 'AFDisabled')]")
	public  WebElement SandboxEnterIconDisable;
	
	@FindBy(xpath = "//*[local-name() = 'svg' and contains(@id , 'FOSsdiitemNode_tools_application_composer::icon')]")
	public  WebElement AppComposerIcon;
	
	/*@FindBy(xpath = "//div[@id = '_FOpt1:_FOr1:0:_FOSritemNode_tools_sandboxes:0:_FOTsr1:0:r1:0:_stt4::scroller']")
	public  WebElement SandboxtableDivElement;*/
	
	@FindBy(xpath = "//button[contains(@id , '_stcb9')]")
	public  WebElement refreshToLatestConfiguration;
	
	@FindBy(xpath = "//button[contains(@id , '_std10::yes')]")
	public  WebElement refreshToLatestConfigurationConfirm;
	
	@FindBy(xpath = "//button[contains(@id , '_std10::no')]")
	public  WebElement refreshToLatestConfigurationDiscard;
	
	@FindBy(xpath = "//div[contains(@id , '_stt4::scroller')]")
	public  WebElement sandboxUiScroller;
	
	@FindBy(xpath = "//a[contains (@id , '_stcb6') and not(contains(@class, 'AFDisabled'))]")
	public  WebElement paginationNextPageEnabledIcon;
	
	@FindBy(xpath = "//a[contains (@id , '_stcb6') and contains(@class, 'AFDisabled')]")
	public  WebElement paginationNextPageDisabledIcon;
	
	@FindBy(xpath = "//a[contains (@id , '_stcb7') and not(contains(@class, 'AFDisabled'))]")
	public  WebElement paginationPreviousPageEnabledIcon;
	
	@FindBy(xpath = "//a[contains (@id , '_stcb7') and contains(@class, 'AFDisabled')]")
	public  WebElement paginationPreviousPageDisabledIcon;
	
	@FindBy(xpath="//a[@title='Help']")
	public WebElement SandboxHelpIcon;
	
	@FindBy(xpath="//td[contains(@class,'instruction-text')]")
	public WebElement SandboxHeadertxt;
	
	@FindBy(xpath="//a[@id='_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:0:r1:0:_stdc01:_AUpw200::close']")
	public WebElement sandboxhelpiconClose;
	
	
	
	public void EnterSandboxSession(String SandboxName,WebDriver driver) {
		try {
		
			//globalTemplateArea.click();
			CommonUtils.hold(3);
			try {
				accessFromRecentItems("Manage Sandboxes",driver).click();
			}catch(Exception eI) {
				System.out.println("In EnterSandboxSession() <-> Exeption while accessing sandbox ui from Favorite and RecentItems");
				eI.printStackTrace();
				ntfInstance.navigateToTask(nMenuEleInstance.Sandboxes,driver);
			}
			
			CommonUtils.explicitWait(InitiateSandboxCreate, "Click", "",driver);
			Assert.assertTrue(InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
			CommonUtils.hold(10);
			WebElement sandboxToActivate =  getEnterSandboxElement(SandboxName,driver);
			if(sandboxToActivate.isEnabled())
				sandboxToActivate.click();
			else {
				System.out.println("Sandbox Can't be acitvated due to either sandbox is in refresh needed state or the sandbox is already activated");
				return;
				}
			
			CommonUtils.explicitWait(SandboxBanner, "Visible", "",driver);
			CommonUtils.hold(10);	
			}catch (Exception ESSE) {
				System.out.println("Excpetion in EnterIntoSandboxSession()");
				ESSE.printStackTrace();
			}
		
	}//End of EnterSandboxSession()
	
	/*
	 * NavigateToSandboxDetailsUI(String SandboxName) will navigate to sandbox details UI of the respective sandbox.
	 */
	public void NavigateToSandboxDetailsUI(String SandboxName,WebDriver driver) {
			
		CommonUtils.hold(5);
		getSandboxNameElement(SandboxName,driver).click();
			System.out.println("Navigating to <"+SandboxName+"> details ui initiated");
		try {
				CommonUtils.explicitWait(driver.findElement(By.xpath("//button[contains(@id , '_sdcb3')]")), "Click", "",driver);
				System.out.println("Navigated to <"+SandboxName+"> details ui");
		} catch (Exception NTSDUE) {
			System.out.println("Exception in waiting for Done button in sandbox details UI");
			NTSDUE.printStackTrace();
		}
		System.out.println("Sandbox Name -"+SandboxName);
		Assert.assertTrue(driver.findElement(By.xpath("//table[@class = 'af_panelHeader_title-table0']/descendant::h1")).getText().contains(SandboxName));
	}//End of NavigateToSandboxDetailsUI()
	
	//Filter sandbox's based on Tools
	public List<String> SandboxFilter(String FilterableRepository,WebDriver driver){
		int CountofFilteredSandboxes;
		ArrayList<String> FileteredSandboxNameList = new ArrayList<String>();
		
		if(!(FileteredSandboxNameList.isEmpty()))
			FileteredSandboxNameList.clear();
		
		WebElement FileterableRepositoryElement = driver.findElement(By.xpath("//h1[text() = 'Tools']/ancestor::div[1]/descendant::span[contains(text(), '"+FilterableRepository+"')]/ancestor::tr[1]/descendant::input[@type = 'checkbox']"));
		FileterableRepositoryElement.click();
		//-----------------ExplicitWait ---------------------
		
		//Get Count of number of Sandboxes Filtered
		List<WebElement> FileteredSandboxesCount = (List<WebElement>) driver.findElement(By.xpath("//table[@summary = 'Editable Sandboxes']/tbody/tr"));
		CountofFilteredSandboxes = FileteredSandboxesCount.size();
		
		for(int Loop = 0; Loop < CountofFilteredSandboxes; Loop++)
			FileteredSandboxNameList.add(driver.findElement(By.xpath("//table[@summary = 'Editable Sandboxes']/tbody/tr["+Loop+"]/descendant::img[contains(@title, '"+FilterableRepository+"')]/ancestor::tr[2]/descendant::a")).getText());
			
		return FileteredSandboxNameList;
		
	}
	
	//Filter(or)Search sandbox's based on SandboxName/Status/Context/LastModifiedBy criteria
	public void SandboxSearch(String SearchCriteria, String SearchableValue,WebDriver driver){
		/*Select SearchCriteriaElement = new Select(driver.findElement(By.xpath("//label[text() = 'Find']/ancestor::tr[1]/descendant::select[1]")));
		SearchCriteriaElement.selectByVisibleText(SearchCriteria);
		
		//<<<<<<<<<<<<<<<------------------Explicit Wait on  Name/Context/Last Modified By Valuefields "//label[text() = 'Find']/ancestor::tr[1]/descendant::input[@type = 'text']"-------------->
		
		if(SearchCriteria.equalsIgnoreCase("Status")) {
			Select SearchwithStatusElement = new Select(driver.findElement(By.xpath("//label[text() = 'Find']/ancestor::tr[1]/descendant::select[2]")));
			SearchwithStatusElement.selectByVisibleText(SearchableValue);
		}else
			SandboxSearchableElement.sendKeys(SearchableValue);
		
		InitiateSandboxSearch.click();*/
		
		try {
			System.out.println("Searching for SandboxSerach FilterCriteria Element ");
			getsandboxFilterElement(SearchCriteria,driver).click();
			System.out.println("SandboxSerach FilterCriteria Element Found and Clicked");
			CommonUtils.hold(5);
			if(SearchCriteria.equalsIgnoreCase("Name") || SearchCriteria.equalsIgnoreCase("Context")) {
				System.out.println("SandboxSerach FilterCriteria Element is "+SearchCriteria);
				CommonUtils.explicitWait(sandboxFilterValue,"Visible","",driver);
				CommonUtils.hold(2);
				sandboxFilterValue.click();
				CommonUtils.hold(1);
				sandboxFilterValue.sendKeys(SearchableValue);
				System.out.println("SandboxSerach FilterValue Entered ");
				CommonUtils.hold(3);
			}
			System.out.println("Initiating sandboxFilter process");
			InitiateSandboxSearch.click();
			System.out.println("SandboxFilter Process Initiated");	
		}catch(Exception sSE) {
			System.out.println("Exception in SandboxSearch()");
			sSE.printStackTrace();
		}
			
	}
	
	//Sandbox Mainline Activities
	public WebElement MainlineActivities(String SandboxName, String SandboxOperation, String UserName,WebDriver driver) {
		WebElement SandboxElement = driver.findElement(By.xpath("//label[text() = 'Sort']/ancestor::div[contains(@id , 'MainlineActivities')]/descendant::a[text() = '"+SandboxName+"']/ancestor::td[1]/descendant::span[contains(text() , '"+SandboxOperation+" by "+UserName+"')]/ancestor::tr[1]/descendant::img[@title = '"+SandboxOperation+"']"));
		
		return SandboxElement;
	}
	
	/*
	 * getSandboxNameElement(String sandboxName,WebDriver driver) return element of the sandboxname which was passed in argument
	 * @sandboxName -> name of the sandbox to get element 
	 */
	public WebElement getSandboxNameElement(String sandboxName,WebDriver driver) {
		WebElement sandboxNameElement = null;
		boolean isEelementFound = false;
		try {
			sandboxNameElement = driver.findElement(By.xpath("//a[text() = '"+sandboxName+"' and @id]"));
			System.out.println("Sandbox is visibled on the ui");
		}catch(Exception gSNEE) {
			System.out.println("Checking whether Pagination existed on sandbox overview page");
			try {
				if (paginationPreviousPageDisabledIcon.isDisplayed() || paginationNextPageDisabledIcon.isDisplayed() || paginationNextPageEnabledIcon.isDisplayed()) {
					System.out.println("Pagination existed in Sandbox OverView Page");
					while(!(isEelementFound)) {
						try {
							sandboxNameElement = driver.findElement(By.xpath("//a[text() = '"+sandboxName+"' and @id]"));
							System.out.println("SandboxName Element Found");
							isEelementFound = true;
						}catch(Exception pME) {
							System.out.println("Sandbox not existed in the current page. Heading to next page to get sandbox element");
							try {
								if(paginationNextPageEnabledIcon.isEnabled()) {
									System.out.println("Heading to next page");
									paginationNextPageEnabledIcon.click();
									CommonUtils.hold(10);
									System.out.println("Headed to next page");
								}
							}catch(Exception sENFE) {
								System.out.println("Sandbox doesn't exist in Sandbox overview page table. So exiting from While loop");
								break;
							}
							
						}
					}//End of While loop
				}//End of IF loop
			}catch(Exception pNE) {
				System.out.println("Pagination doesn't existed in Sandbox OverView Page");
				WebElement elementscrollPopUp = driver.findElement(By.xpath("//div[contains(@id , '_stt4::scroller')]"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollIntoView", elementscrollPopUp);
				
				System.out.println("Overview table scrolled to TOP position");
				CommonUtils.hold(10);
				sandboxNameElement = driver.findElement(By.xpath("//a[text() = '"+sandboxName+"' and @id]"));
			}
		}
		
	return sandboxNameElement;
	}
	
	public WebElement getEnterSandboxElement(String SandboxName,WebDriver driver) {
		String sandboxNameElement = getSandboxNameElement(SandboxName,driver).toString();
		System.out.println(" SandboxNameElement -> "+sandboxNameElement);
		sandboxNameElement = sandboxNameElement.substring(sandboxNameElement.indexOf("//"), sandboxNameElement.lastIndexOf(']'));
		System.out.println(" Post replace SandboxNameElement -> "+sandboxNameElement);
		WebElement EnterSandboxIconElement = driver.findElement(By.xpath(sandboxNameElement+"/ancestor::tr[1]/descendant::a[contains (@id , '_stcil1') and not(contains(@class, 'AFDisabled'))]"));
		return EnterSandboxIconElement;
	}
	
	public void getSandboxRow(String sandboxName,WebDriver driver) {
		
		WebElement SandboxRowElement = driver.findElement(By.xpath("//a[text() = '"+sandboxName+"' and @id]/ancestor::tr[1]"));
		
		SandboxRowElement.click();
		
	}
	
	/*
	 * Method to check final status of the published sandbox
	 */
	public void verfiySandboxPublish(String publishedSandboxName,WebDriver driver) {
		System.out.println("VerifySandboxPublish() Begin"); 
		try {
			/*SandboxPublishStatus = getSandboxFinalStatus(publishedSandboxName);
				System.out.println("Sandbox < "+publishedSandboxName+" > publish status < "+SandboxPublishStatus+" >");
				*/
				CommonUtils.hold(15);
				homeIcon.click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(10);
				try {
					CommonUtils.hold(10);
					accessFromRecentItems("Manage Sandboxes",driver).click();
				}catch(Exception eI) {
					System.out.println("Exeption while accessing sandbox ui from Favorite and RecentItems");
					eI.printStackTrace();
					ntfInstance.navigateToTask(nMenuEleInstance.Sandboxes,driver);
				}
							
				CommonUtils.explicitWait(InitiateSandboxCreate, "Click", "",driver);
				Assert.assertTrue(InitiateSandboxCreate.isDisplayed());
				System.out.println("Sandboxes UI Loaded");
				CommonUtils.hold(10);
				
				PublishedSandboxesLabel.click();
				CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@id , '_stt1::db')]")), "Visible", "", driver);
				CommonUtils.hold(5);
				Assert.assertTrue(getPublishedSandboxName(publishedSandboxName,driver).isDisplayed(), "Sandbox Successfully Published");
				System.out.println("VerifySandboxPublish() End");
				System.out.println("<"+publishedSandboxName+"> successfully published");
				
				/*if(SandboxPublishStatus.equalsIgnoreCase("COMMITTED")) {
					PublishedSandboxesLabel.click();
					CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@id , '_stt1::db')]")), "Visible", "", driver);
					CommonUtils.hold(5);
					Assert.assertTrue(getPublishedSandboxName(publishedSandboxName,driver).isDisplayed(), "Sandbox Successfully Published");
					System.out.println("VerifySandboxPublish() End");
					System.out.println("<"+publishedSandboxName+"> successfully published");
				}else if(SandboxPublishStatus.equalsIgnoreCase("INACTIVE")){
					if(driver.findElement(By.xpath("//span[contains(text() , 'Your last sandbox publish process failed because of an internal error.')]")).isDisplayed() && driver.findElement(By.xpath("//div[text() = 'You can't refresh this sandbox because you haven't yet activated it. Enter this sandbox and try refreshing it again.']")).isDisplayed()) {
						System.out.println("Sandbox refresh is failed. Please review the logs");
						Assert.fail("Sandbox refresh is failed. Please review the logs");
					}
				}else {
					System.out.println("Sandbox publish failed with status <"+SandboxPublishStatus+">");
					Assert.fail();
				}*/
		} catch (Exception VSPE) {
			System.out.println("Exception in VerifySandboxPublish");
			VSPE.printStackTrace();
			Assert.fail();
		}
	}//End of VerfiySandboxPublish()
	
	/*
	 * getPublishedSandboxName(String Publishedsandbox) returns publishedsandbox element from publishedsnadboxes tab
	 */
	public WebElement getPublishedSandboxName(String Publishedsandbox,WebDriver driver) {
		
		WebElement PublishedSandboxElement = driver.findElement(By.xpath("//a[text() = '"+Publishedsandbox+"']"));
		return PublishedSandboxElement;
	}
	
	/*
	 * getsandboxFilterElement(string filterCriteria) will return webelement to filter available sandboxes based on filter criteria
	 * Filter criteria's -> All, Name, Context
	 */
	public WebElement getsandboxFilterElement(String filterCriteria, WebDriver driver) {
		WebElement sandboxFilterElement = null;
			sandboxFilterElement = driver.findElement(By.xpath("//table[contains(@id , '_stsoc1')]/descendant::select[contains(@id , '_stsoc1::content')]/option[text() = '"+filterCriteria+"']"));
		return sandboxFilterElement;
	}//End of getsandboxFilterElement();
	
	/*
	   * refreshToLatestConfiguration() method will refresh the user session to latest MDS configuration in label read enabled environment
	   */
	  public void refreshToLatestConfiguration(WebDriver driver) {
		  try {
			  CommonUtils.explicitWait(refreshToLatestConfiguration, "Click", "",driver);
				System.out.println("Refresh To Last Configuration is enabled");
				refreshToLatestConfiguration.click();
				CommonUtils.explicitWait(refreshToLatestConfigurationConfirm, "Click", "", driver);
				CommonUtils.hold(3);
				System.out.println("Confirm Refresh To Last Configuration");
				refreshToLatestConfigurationConfirm.click();
				CommonUtils.waitForPageLoad(driver);
				CommonUtils.hold(10);
				System.out.println("Refresh To Last Configuration Successfully Completed");
		  }catch(Exception rtlcE) {
			  System.out.println("Exception in refreshToLatestConfiguration()");
			  rtlcE.printStackTrace();
		  }
	  }//End Of refreshToLatestConfiguration()
	
	  
	  public void clickOnSandboxHelpIcon(WebDriver driver) {
		  SandboxHelpIcon.click();
		  CommonUtils.hold(10);
		  System.out.println(SandboxHeadertxt.isDisplayed());
		  Assert.assertTrue(SandboxHeadertxt.isDisplayed());
          CommonUtils.waitForElement(sandboxhelpiconClose, 20, 5, driver);
		  sandboxhelpiconClose.click();
	  }
	
	
}
