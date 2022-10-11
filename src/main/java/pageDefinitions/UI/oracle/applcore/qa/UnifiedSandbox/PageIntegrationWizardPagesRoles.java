package pageDefinitions.UI.oracle.applcore.qa.UnifiedSandbox;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import UtilClasses.UI.CommonUtils;

public class PageIntegrationWizardPagesRoles {
	
	public PageIntegrationWizardPagesRoles(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	/*
	 *Xpaths to search for existence of role to be created 
	 */
	//@FindBy(xpath = "//input[contains(@id , 'pt1:srchBox::content')]")
	@FindBy(xpath = "//input[contains(@id , 'sp1:srchBox::content')]")
	public  WebElement roleToBeSearch;
	
	//@FindBy(xpath = "//img[contains(@id , 'pt1:cil1::icon')]")
	@FindBy(xpath = "//img[contains(@id , 'sp1:cil1::icon')]")
	public  WebElement roleSearchIcon;
	
	@FindBy(xpath = "//button[contains(@id , 'cmiCrte')]")
	public   WebElement initiateNewRoleCreate;
	
	/*
	 * XPaths to create new role
	 */
	@FindBy(xpath = "//input[contains(@id , 'bIRNam::content')]")
	public   WebElement roleName;
	
	@FindBy(xpath = "//input[contains(@id , 'bIRCod::content')]")
	public   WebElement roleCode;
	
	@FindBy(xpath = "//input[contains(@id , 'bIRCat::content')]")
	public   WebElement roleCategory;
	
	@FindBy(xpath = "//textarea[contains(@id , 'bIRDes::content')]")
	public   WebElement roleDescription;
	
	@FindBy(xpath = "//div[contains(@id , 'rmTrain:5:_afrStopNavItem')]")
	public   WebElement navigateToUsersTab;
	
	@FindBy(xpath = "//button[contains(@id , 'cil3')]")
	public   WebElement addUsersInitiation;
	
	@FindBy(xpath = "//div[contains(@id , 'd1::_ttxt')]")
	public   WebElement addUsersPopupDiv;
	
	@FindBy(xpath = "//input[contains(@id , 'usSrcBx::content')]")
	public   WebElement userToBeAdd;
	
	@FindBy(xpath = "//img[contains(@id , 'cil1::icon')]")
	public   WebElement searchUser;
	
	@FindBy(xpath = "//button[contains(@id , 'usAddUs')]")
	public   WebElement addUserToRole;
	
	@FindBy(xpath = "//button[contains(@id , 'usSrCan')]")
	public   WebElement rejectUserToTRoleAdd;
	
	@FindBy(xpath = "//a[contains(@id , 'd1::close')]")
	public   WebElement exitAddUsersDiv;
	
	@FindBy(xpath = "//span[text() = 'x']//ancestor::button")
	public   WebElement nextTab;
	
	@FindBy(xpath = "//span[text() = 'S']//ancestor::button")
	public   WebElement commitUserToRoleAdd;
	
		
	/*
	 * isRoleExisted(String roleName) check for whether the role to be created is existed in the environment or not
	 * @param -> RoleName to be create.
	 * @return -> 	true : if role is existed in the environment
	 * 				false : if role doesn't existed in the environment
	 */
	public  boolean isRoleExisted(String roleName,WebDriver driver) {
		boolean roleExisted = false;
		try {
			CommonUtils.hold(3);
			roleToBeSearch.clear();
			CommonUtils.hold(2);
			roleToBeSearch.sendKeys(roleName);
			CommonUtils.hold(3);
			roleSearchIcon.click();
			CommonUtils.explicitWait(driver.findElement(By.xpath("//h1[text() = '"+roleName+"']")), "Visible", "", driver);
			CommonUtils.hold(3);
			if(driver.findElement(By.xpath("//h1[text() = '"+roleName+"']")).isDisplayed()) {
				System.out.println("Role <"+roleName+"> is existed in the environment");
				roleExisted = true;
			}
		}catch(Exception iREE) {
			System.out.println("Role <"+roleName+"> doesn't existed in the environment");
		}
		return roleExisted;
	}//End Of isRoleExisted()
	
	
	/*
	 * selectUserToAdd(String userName) will select user row of the user passes as an argument to the method 
	 */
	public  void selectUserToAdd(String userName,WebDriver driver) {
		CommonUtils.hold(3);
		try {
			driver.findElement(By.xpath("//span[text() = '"+userName+"']/ancestor::div[contains(@id , 'li1')]")).click();
			System.out.println("User <"+userName+"> found and Selected");
			CommonUtils.hold(5);
		}catch(Exception sUTAE) {
			System.out.println("User <"+userName+"> not found, scrolling down to select user");
			try {
				CommonUtils.scrollToElement(driver.findElement(By.xpath("//span[text() = '"+userName+"']/ancestor::div[contains(@id , 'li1')]")),driver);
				CommonUtils.hold(5);
				System.out.println("Scrolled to user row");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Scroll to Element exception. Scrolling down further");
				WebElement scrollToUserElement = driver.findElement(By.xpath("//span[contains(@id , 'usgh1')]/ancestor::div[contains(@id , 'pgl7')]"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", scrollToUserElement);
				System.out.println("Scrolled to End");
			}
			CommonUtils.hold(5);
			System.out.println("Scrolled to user row");
			driver.findElement(By.xpath("//span[text() = '"+userName+"']/ancestor::div[contains(@id , 'li1')]")).click();
			System.out.println("User <"+userName+"> found and Selected");
			CommonUtils.hold(5);
		}
	}//End Of selectUserToAdd()
	
	
	/*
	 * createRole(String newRoleName, String userName) will create new role and assign user to the new role
	 * @params : newRoleName -> role to be create
	 * 			 userName	 -> Assigning user to the role
	 */
	public  void createRole(String newRoleName, String userName,WebDriver driver) {
		try {
			CommonUtils.hold(5);
			System.out.println("Initiating role <"+newRoleName+"> creation");
			initiateNewRoleCreate.click();
			CommonUtils.explicitWait(roleName, "Visible","",driver);
			CommonUtils.hold(5);
			System.out.println("Role Creation Initiated");
			System.out.println("Role Name -> "+newRoleName);
			roleName.sendKeys(newRoleName);
			CommonUtils.hold(2);
			System.out.println("Role Code -> "+newRoleName);
			roleCode.sendKeys(newRoleName);
			CommonUtils.hold(2);
			System.out.println("Role Category -> Common - Job Roles");
			roleCategory.sendKeys("Common - Job Roles");
			CommonUtils.hold(2);
			roleDescription.sendKeys("Creating new role "+CommonUtils.uniqueId());
			CommonUtils.hold(3);
			System.out.println("Navigating to 'Users' tab to add <"+userName+"> to role <"+newRoleName+"> ");
			navigateToUsersTab.click();
			CommonUtils.explicitWait(addUsersInitiation, "Click", "",driver);
			CommonUtils.hold(5);
			System.out.println("Initiating User <"+userName+"> to role <"+newRoleName+"> Add");
			addUsersInitiation.click();
			CommonUtils.explicitWait(addUsersPopupDiv, "Visible", "",driver);
			CommonUtils.hold(3);
			System.out.println("Add Users PopUp Loaded");
			System.out.println("Initiaing User Search");
			userToBeAdd.sendKeys(userName);
			CommonUtils.hold(2);
			searchUser.click();
			System.out.println("User Search Initiated");
			CommonUtils.hold(10);
			addUsersPopupDiv.click();
			CommonUtils.hold(3);
			selectUserToAdd(userName,driver);
			CommonUtils.hold(5);
			addUserToRole.click();
			System.out.println("User <"+userName+"> added to Role <"+newRoleName+">");
			CommonUtils.hold(10);
			System.out.println("Exiting from Add User popup");
			exitAddUsersDiv.click();
			CommonUtils.hold(15);
			System.out.println("Exited from Add User popup");
			System.out.println("Navigated to preview tab");
			nextTab.click();
			CommonUtils.explicitWait(commitUserToRoleAdd, "Click", "",driver);
			CommonUtils.hold(5);
			commitUserToRoleAdd.click();
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(10);
			System.out.println("role <"+newRoleName+"> successfully created and user <"+userName+"> is assigend to this role");
		}catch(Exception cRE) {
			System.out.println("Exception in createRole() ");
			cRE.printStackTrace();
		}
	}//End of createRole()
	

}
