package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.ReportGenerator;

public class CreateSandboxUI extends SandboxUI{

	public CreateSandboxUI(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	ArrayList<String> SandboxContextLevels = new ArrayList<String>(Arrays.asList("External Or Internal","Job Role","HcmCountry","HcmOrganization","HcmTimeCardLayout"));
	String contextLevel;
	String contextLevelValue;
	
	@FindBy(xpath = "//h1[text() = 'Create Sandbox']")
	public  WebElement CreateSandboxUIAssert;
	
	//@FindBy(xpath = "//label[text() = 'Name']/ancestor::tr[1]/descendant::input[@type = 'text']")
	@FindBy(xpath = "//label[contains(@for , 'csit1::content')]/ancestor::tr[1]/descendant::input[@type = 'text']")
	public  WebElement SandboxName;
	
	//@FindBy(xpath = "//label[text() = 'Description']/ancestor::tr[1]/descendant::textarea")
	@FindBy(xpath = "//label[contains(@for , 'csit2::content')]/ancestor::tr[1]/descendant::textarea")
	public  WebElement SandboxDescription;
	
	@FindBy(xpath = "//button[contains(@id , '_csb1')]")
	public  WebElement CreateSandbox;
	
	@FindBy(xpath = "//button[contains(@id , '_csb2')]")
	public  WebElement CreateAndEnterSandbox;
	
	@FindBy(xpath = "//button[contains(@id , '_csb3')]")
	public  WebElement CancelSandboxCreate;
	
	@FindBy(xpath = "//span[text() = 'Page Composer']/ancestor::tr[2]/descendant::a")
	public  WebElement LaunchSandboxContextUI;
	
	@FindBy(xpath = "//label[contains(@for, '_cssor1:_1')]/ancestor::span/descendant::div")
	public  WebElement nonPublishableSandbox;
	
	@FindBy(xpath = "//table[contains(@id , '_cspgl7')]")
	public  WebElement nonPublishableSandboxConfirmMessage;
	
	//XPaths for updating Sandbox Context
	
	@FindBy(xpath = "//div[contains(@id , '_csd4::_ttxt')]")
	public  WebElement sandboxContextUIAssert;
	
	@FindBy(xpath = "//button[contains(@id , '_csd4::ok')]")
	public  WebElement confirmSandboxContextUpdate;
	
	@FindBy(xpath = "//button[contains(@id , '_csd4::cancel')]")
	public  WebElement discardSandboxContextUpdate;
	
	@FindBy(xpath = "//label[contains(@for , '_cssoc1::content')]/ancestor::tr[1]/descendant::select")
	public  WebElement categorySelectionField;
		
	
	public void UpdateSandboxContext(String contextCategory,String contextLevelValues,WebDriver driver) {
			
			 List<String> sandboxContext = getContextLevelValues(contextLevelValues);
			 Iterator contextIterator = sandboxContext.iterator();
			 
			Select category = new Select(categorySelectionField);
			category.selectByVisibleText(contextCategory);
		
			CommonUtils.hold(5);
			
			while(contextIterator.hasNext()) {
				contextLevel = contextIterator.next().toString();
				contextLevelValue = contextIterator.next().toString();
				System.out.println("Context Level -> "+contextLevel);
				System.out.println("Context Level Value -> "+contextLevelValue);
				WebElement ContextLevelElement = driver.findElement(By.xpath("//span[text() = '"+contextLevel+"']/ancestor::tr[2]/descendant::input[@type = 'radio']"));
				ContextLevelElement.click();
				CommonUtils.hold(5);
				Select ContextLevelValueElement = new Select(driver.findElement(By.xpath("//span[text() = '"+contextLevel+"']/ancestor::tr[2]/descendant::select")));
				ContextLevelValueElement.selectByVisibleText(contextLevelValue);
				CommonUtils.hold(5);
		}
			
			confirmSandboxContextUpdate.click();
	}//End of UpdateSandboxContext() 

	public void ActivateSandboxRepository(String repositoryName,WebDriver driver) {
		WebElement activateTool;
		System.out.println("Activating "+repositoryName+" Repository");
			CommonUtils.hold(10);
			/*try {
					System.out.println("Wait for repository element");
					driver.findElement(By.xpath("//span[text() = '"+RepositoryName+"']/ancestor::tr[@_afrrk]/descendant::label")).click();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			CommonUtils.hold(10);
			if(verifyActivatedToolStatus(RepositoryName,driver)) {
				System.out.println("Activated "+RepositoryName+" Repository to the sandbox");
					System.out.println("Added < "+RepositoryName+" > Repository to the sandbox");
			}else {
				System.out.println("Repository "+RepositoryName+" not activated within the sandbox");
				System.out.println("Repository "+RepositoryName+" not activated within the sandbox");
			}*/
		
		try {
			System.out.println("Wait for repository element");
			driver.findElement(By.xpath("//span[text() = '"+repositoryName+"']/ancestor::tr[@_afrrk]/descendant::label")).click();
		} catch (Exception e) {
			try {
				driver.findElement(By.xpath("//td[text() = '"+repositoryName+"']/ancestor::tr[@_afrrk]/descendant::label")).click();
			}catch(Exception rE) {
				System.out.println("Exception in ActivateSandboxRepository()");
				rE.printStackTrace();
			}
		}
		CommonUtils.hold(10);
		if(verifyActivatedToolStatus(repositoryName,driver)) 
			System.out.println("Activated "+repositoryName+" Repository to the sandbox");
		else
			System.out.println("Repository "+repositoryName+" not activated within the sandbox");
	
	}//End of ActivateSandboxTool()
	
	/*
	 * verifyActivatedToolStatus(String RepositoryName) will check for repository activation
	 */
	public boolean verifyActivatedToolStatus(String RepositoryName,WebDriver driver) {
		boolean toolactivationstatus = true;
		try {
			//DriverInstance.getDriverInstance().findElement(By.xpath("//td[text() = '"+RepositoryName+"']/ancestor::tr[2]/descendant::input[@type = 'checkbox']")).click();
			driver.findElement(By.xpath("//span[text() = '"+RepositoryName+"']/ancestor::tr[@_afrrk]/descendant::input[@type = 'checkbox' and @checked]")).isDisplayed();
		}catch(Exception E) {
			try {
				System.out.println("Verifying for tool activation");
				driver.findElement(By.xpath("//td[text() = '"+RepositoryName+"']/ancestor::tr[@_afrrk]/descendant::input[@type = 'checkbox' and @checked]")).isDisplayed();
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			toolactivationstatus = false;
			e.printStackTrace();
			}
		}
		return toolactivationstatus;
	}// End of verifyActivatedToolStatus(String RepositoryName)
	
	/*
	 * Method will create sandbox with the details provided as parameters
	 * @sandboxTitle -> Title of the sandbox
	 * @description -> Sandbox description
	 * @sandboxRepositories -> Repositories to active within sandbox context
	 * @activateSandbox -> Activate = CreateAndEnter operation else Create operation
	 * @isPublishableSandbox -> YES - Publishable Sandbox
	 * 							No - Non Publishable Sandbox (i.e.) demo sandbox
	 * @sandboxContext -> CRM / HCM
	 * @contextValues -> list of context level and context level values pair seperated by ',' (ex: ExternalOrInternal,Internal,JobRole,ApplicationImplementaionConsultant)
	 */
	public String CreateSandbox(String sandboxTitle,String description,String sandboxRepositories,String sandboxContext,String contextValues,String activateSandbox,String isPublishableSandbox,WebDriver driver) {
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	  		System.out.println("Identifying list of repositories to be activated within sandbox");
	  				List<String> activatableRepositories = getListOfRepositories(sandboxRepositories);
	  				Iterator repositoryIterator = activatableRepositories.iterator();
	  		System.out.println("Identified list of repositories to be activated within sandbox");
	  
	  				sandboxTitle = sandboxTitle + timeStamp;
	  
	  		System.out.println("Sandbox < "+sandboxTitle+" > Creation Starts");
	  
	  				InitiateSandboxCreate.click();
	  
	  			CommonUtils.explicitWait(CreateSandbox, "Visible", "",driver);
	  		System.out.println("Create Sandbox UI loaded");
	  		System.out.println("SandboxName - "+sandboxTitle);
	  		System.out.println("SandboxDescription - "+description);
	 
	  				SandboxName.sendKeys(sandboxTitle);
	  				SandboxDescription.sendKeys(description);
	  	  
	  				CommonUtils.hold(5);
	  			SandboxDescription.click();
	  			CommonUtils.hold(5);
	  		System.out.println("Publishable Sandbox -> "+isPublishableSandbox);
		 
		 /*
		  * If @isPublishableSandbox = NO then condition will execute to create NonPublishable / Demo sandbox
		  */
		 if(isPublishableSandbox.equalsIgnoreCase("NO")) {
			 System.out.println("Non Publishable Sandbox Selection Starts");
			 try {
				((JavascriptExecutor)driver).executeScript("arguments[0].click()", nonPublishableSandbox);
				 CommonUtils.explicitWait(nonPublishableSandboxConfirmMessage, "Visible", "",driver);
				 CommonUtils.hold(5);
			 }catch(Exception nPSE) {
				 System.out.println("Exception while selecting NO option in create sandbox ui");
				 nPSE.printStackTrace();
			 }
			
			 System.out.println("Non Publishable Sandbox Selection Ends");
		 }
	
		// Loop to iterate all repositories to be active and Activate the repositories within the sandbox
		 while(repositoryIterator.hasNext())
			 ActivateSandboxRepository(repositoryIterator.next().toString(),driver);
		 CommonUtils.hold(15);			
			 
			//condition to check sandbox context and activate respective context for the sandbox if sandbox context is not empty
			  if(!(sandboxContext.isEmpty())) {											
				 LaunchSandboxContextUI.click();
				 CommonUtils.explicitWait(sandboxContextUIAssert, "Visible", "",driver);
				 UpdateSandboxContext(sandboxContext,contextValues,driver);
				 CommonUtils.hold(5);
				 WebElement SandboxContextElement = sandboxContext(driver);
				 CommonUtils.explicitWait(SandboxContextElement, "Visible", "",driver);
				 System.out.println("Sandbox Context Set with the user choice context");
			 }
			  
			  /*
				  * if @activateSandbox = Activate then 'CreateAndEnter'
				  * 	else 'Create'
				  */
				  if (activateSandbox.equalsIgnoreCase("Activate")) {
					  	CreateAndEnterSandbox.click();	
					  	CommonUtils.hold(20);      //Performance issue in CreateAndEnter Sandbox, till the issue resolved have to had wait time
					  System.out.println("Sandbox < "+sandboxTitle+" > creation and activation initiated");
				  }else
				  	{
					  	CreateSandbox.click();								
					  System.out.println("Sandbox < "+sandboxTitle+" > creation initiated");
					 }
					
				  CommonUtils.waitForPageLoad(driver);
				  CommonUtils.hold(15);
		  }catch(Exception CSE) {
			  System.out.println("Exception in CreateSandbox() - ");
			  CSE.printStackTrace();
		  }
		return sandboxTitle;
		
	}//End of CreateSandbox()

	/*
	 * Method will create sandbox with the details provided as parameters
	 * @sandboxTitle -> Title of the sandbox
	 * @description -> Sandbox description
	 * @sandboxRepositories -> Repositories to active within sandbox context
	 * @activateSandbox -> Activate = CreateAndEnter operation else Create operation
	 * @isPublishableSandbox -> YES - Publishable Sandbox
	 * 							No - Non Publishable Sandbox (i.e.) demo sandbox
	 */
	public String CreateSandbox(String sandboxTitle,String description,String sandboxRepositories,String activateSandbox,String isPublishableSandbox, WebDriver driver) {
		try {
			  String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			  		System.out.println("Identifying list of repositories to be activated within sandbox");
			  List<String> activatableRepositories = getListOfRepositories(sandboxRepositories);
			  Iterator repositoryIterator = activatableRepositories.iterator();
			  		System.out.println("Identified list of repositories to be activated within sandbox");
			  
			  sandboxTitle = sandboxTitle + timeStamp;
			  
			  System.out.println("Sandbox < "+sandboxTitle+" > Creation Starts");
			  
			  		InitiateSandboxCreate.click();
			  
			  		CommonUtils.explicitWait(CreateSandbox, "Visible", "",driver);
			  System.out.println("Create Sandbox UI loaded");
			  System.out.println("SandboxName - "+sandboxTitle);
			  System.out.println("SandboxDescription - "+description);
			 
			  		SandboxName.sendKeys(sandboxTitle);
			  		SandboxDescription.sendKeys(description);
			  	  
			  	CommonUtils.hold(5);
			  		SandboxDescription.click();
				CommonUtils.hold(5);
			System.out.println("Publishable Sandbox -> "+isPublishableSandbox);
				 
				 /*
				  * If @isPublishableSandbox = NO then condition will execute to create NonPublishable / Demo sandbox
				  */
				 if(isPublishableSandbox.equalsIgnoreCase("NO")) {
					 System.out.println("Non Publishable Sandbox Selection Starts");
					 try {
						((JavascriptExecutor)driver).executeScript("arguments[0].click()", nonPublishableSandbox);
						 CommonUtils.explicitWait(nonPublishableSandboxConfirmMessage, "Visible", "",driver);
						 CommonUtils.hold(5);
					 }catch(Exception nPSE) {
						 System.out.println("Exception while selecting NO option in create sandbox ui");
						 nPSE.printStackTrace();
					 }
					
					 System.out.println("Non Publishable Sandbox Selection Ends");
				 }
			
				// Loop to iterate all repositories to be active and Activate the repositories within the sandbox
			 while(repositoryIterator.hasNext())
			 	ActivateSandboxRepository(repositoryIterator.next().toString(),driver);
			 CommonUtils.hold(15);
	 		  
			 /*
			  * if @activateSandbox = Activate then 'CreateAndEnter'
			  * 	else 'Create'
			  */
			  if (activateSandbox.equalsIgnoreCase("Activate")) {
				  	CreateAndEnterSandbox.click();	
				  	CommonUtils.hold(20);      //Performance issue in CreateAndEnter Sandbox, till the issue resolved have to had wait time
				  System.out.println("Sandbox < "+sandboxTitle+" > creation and activation initiated");
			  }else
			  	{
				  	CreateSandbox.click();								
				  System.out.println("Sandbox < "+sandboxTitle+" > creation initiated");
				 }
				
			  CommonUtils.waitForPageLoad(driver);
			  CommonUtils.hold(15);
			}catch(Exception CSE) {
			  System.out.println("Exception in CreateSandbox() - ");
			  CSE.printStackTrace();
		  }
		return sandboxTitle;
		
	}//End of CreateSandbox()
	
	/*
	 * getListOfRepositories(String SandboxRepositories) method will return list of repositories to be activated within the sandbox
	 */
	public List<String> getListOfRepositories(String SandboxRepositories){
		List<String> ActivatableRepositories;
		
		if(!(SandboxRepositories.isEmpty())) {
			ActivatableRepositories = new ArrayList<String>();
			String[] RepositoryList = SandboxRepositories.split(",");
		
			for(String RL : RepositoryList) {
				ActivatableRepositories.add(RL);
				System.out.println("Repository "+RL+" Added to List");
			}
		}else
			ActivatableRepositories = Collections.EMPTY_LIST;
		
		return ActivatableRepositories;
	}//End of getListOfRepositories()
	
	/*
	 * getContextLevelValues(String ContextLevelvalue) method return list of contextlevelvalues for the sandbox to be created
	 */
	public List<String> getContextLevelValues(String ContextLevelvalue){
		List<String> ContextValues = new ArrayList<String>();
		
		String[] CValues = ContextLevelvalue.split(",");
		
		for(String CLV : CValues)
			ContextValues.add(CLV);
		
		return ContextValues;
	}//End of getContextLevelValues()
	
	/*
	 * sandboxContext(String ContextLevel,String ContextLevelValue) returns sandboxContext Webelement
	 */
	public WebElement sandboxContext(WebDriver driver) {
		
		String ContextValue = contextLevel+" - "+contextLevelValue;
		System.out.println("Sandbox Context : "+ContextValue);
		WebElement ContextElement = driver.findElement(By.xpath("//span[text() = '"+ContextValue+"']"));
		return ContextElement;
	}//End of sandboxContext()
	
}//End of CreateSandboxUI class
