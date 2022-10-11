package pageDefinitions.UI.oracle.applcore.qa.ds;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import UtilClasses.UI.CommonUtils.ExplicitWaitCalls;
import pageDefinitions.UI.oracle.applcore.qa.Audit.CommonAuditSetup;
import pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS.SaaSPaaSCommonUtils;

/**
 * @author Ashish Pareek
 *
 */

public class CreateUserRoleMap extends CreateUserRoleMapPage{
	JavascriptExecutor js;
	private CommonAuditSetup AuditSetupinstance;
	
	public CreateUserRoleMap(WebDriver driver) {
		super(driver);
		js = ((JavascriptExecutor) driver);
		AuditSetupinstance = new CommonAuditSetup(driver);
	}

//***********************************************************************************************************************************************************	
	public void manageSecurityConsoleRolesWarningBox(WebDriver driver) throws Exception {
		//CommonUtils.explicitWait(rolesTab, ExplicitWaitCalls.Visible.toString(), "", driver);
		CommonUtils.waitForJStoLoad(driver, 50);
		try {
			if (securityConsoleRolesWarningBox.isDisplayed()) {
				System.out.println("in warning box");
				securityConsoleRolesOkButton.click();
				//CommonUtils.hold(5);
				CommonUtils.waitForInvisibilityOfElement(securityConsoleRolesOkButton, driver, 5);
			}
		} catch (NoSuchElementException e) {
			Log.info("Security Console: Warning Alert dialog box is not displayed.");
		}
		
	}
	
	
//***********************************************************************************************************************************************************	
	public String createUser(WebDriver driver, String firstName) throws Exception {
		
		String uniqueID = CommonUtils.uniqueId();
		
		CommonUtils.explicitWait(usersTab, ExplicitWaitCalls.Click.toString(), "", driver);
		usersTab.click();
		CommonUtils.hold(3);
		
		CommonUtils.explicitWait(addUserAccountButton, ExplicitWaitCalls.Click.toString(), "", driver);
		addUserAccountButton.click();
		
		CommonUtils.explicitWait(userFirstName, ExplicitWaitCalls.Visible.toString(), "", driver);
		userFirstName.sendKeys(firstName);
		
		String lastName = "LastName_"+uniqueID;
		userLastName.sendKeys(lastName);
		
		String email = firstName+"@oracle.com";
		userEmail.sendKeys(email);
		
		userPassword.sendKeys("Welcome1");
		userConfirmPassword.sendKeys("Welcome1");
		
		String userName = firstName;
		userUserName.clear();
		CommonUtils.hold(8);
		userUserName.clear();
		userUserName.sendKeys(userName);
		
		SaveAndCloseBtn.click();
		CommonUtils.hold(20);
		
		return userName;
		
	}
		
//***********************************************************************************************************************************************************	

	public String createRole(WebDriver driver, String addFunctionSecurityPolicyName, String userName) throws Exception {
		
		CommonUtils.hold(5);
		CommonUtils.explicitWait(rolesTab, ExplicitWaitCalls.Click.toString(), "", driver);
		rolesTab.click();
		CommonUtils.explicitWait(createRoleButton, ExplicitWaitCalls.Click.toString(), "", driver);
		createRoleButton.click();
		CommonUtils.hold(5);
		
		CommonUtils.explicitWait(roleName, ExplicitWaitCalls.Visible.toString(), "", driver);
		String uniqueID = CommonUtils.uniqueId();
		String role_Name = "Role_"+uniqueID;
		roleName.sendKeys(role_Name);
		
		String role_Code = "Role_"+uniqueID;
		roleCode.sendKeys(role_Code);
		
		String role_Category = "Common - Job Roles";
		roleCategory.sendKeys(role_Category);
		CommonUtils.hold(3);
		
		NextButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(8);
		
		CommonUtils.explicitWait(addFunctionSecurityPolicyButton, ExplicitWaitCalls.Click.toString(), "", driver);
		if (!addFunctionSecurityPolicyName.equalsIgnoreCase("")) {
			CommonUtils.explicitWait(addFunctionSecurityPolicyButton, ExplicitWaitCalls.Click.toString(), "", driver);
			addFunctionSecurityPolicyButton.click();
			
			CommonUtils.explicitWait(addFunctionSecurityPolicy_Search, ExplicitWaitCalls.Visible.toString(), "", driver);
			addFunctionSecurityPolicy_Search.sendKeys(addFunctionSecurityPolicyName);
			
			CommonUtils.hold(10);
			CommonUtils.explicitWait(addFunctionSecurityPolicy_SearchResult, ExplicitWaitCalls.Visible.toString(), "", driver);
			addFunctionSecurityPolicy_SearchResult.click();
			
			CommonUtils.explicitWait(addFunctionSecurityPolicy_SearchResult_NameContent, ExplicitWaitCalls.Visible.toString(), "", driver);
			addFunctionSecurityPolicy_SearchResult_NameContent.click();
			
			addSelectedPrivilegesButton.click();
			CommonUtils.hold(5);
			
			addFunctionSecurityPolicy_CancelButton.click();
			CommonUtils.hold(10);
		}
		
		NextButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		
		NextButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		
		NextButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		
		CommonUtils.explicitWait(addUserButton, ExplicitWaitCalls.Click.toString(), "", driver);
		if(!userName.equalsIgnoreCase("")) {
			CommonUtils.explicitWait(addUserButton, ExplicitWaitCalls.Click.toString(), "", driver);
			addUserButton.click();
			
			CommonUtils.explicitWait(addUser_Search, ExplicitWaitCalls.Visible.toString(), "", driver);
			addUser_Search.sendKeys(userName);
			
			try {
				//CommonUtils.explicitWait(addUser_SearchResultFull, ExplicitWaitCalls.Visible.toString(), "", driver);
				CommonUtils.waitExplicitly(10,2,ExpectedConditions.elementToBeClickable(addUser_SearchResultFull),driver);
				addUser_SearchResultFull.click();
			} catch (Exception e) {
				System.out.println("in catch block.....");
				//CommonUtils.explicitWait(addUser_SearchResultPart, ExplicitWaitCalls.Visible.toString(), "", driver);
				CommonUtils.waitExplicitly(10,2,ExpectedConditions.elementToBeClickable(addUser_SearchResultPart),driver);
				addUser_SearchResultPart.click();
			}
			
			CommonUtils.explicitWait(addUser_SearchResult_UserLoginContent, ExplicitWaitCalls.Visible.toString(), "", driver);
			addUser_SearchResult_UserLoginContent.click();
			
			addUserToRoleButton.click();
			CommonUtils.hold(5);
			
			addUser_CancelButton.click();
			CommonUtils.hold(10);
			
		}
		
		NextButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		
		SaveAndCloseBtn.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		
		try {
			if(role_ConfirmationPopUp.isDisplayed()) {
				role_ConfirmationPopUpOKButton.click();
			}
		} catch (Exception e){
			CommonUtils.hold(30);
			try {
				if(role_ConfirmationPopUp.isDisplayed()) {
					role_ConfirmationPopUpOKButton.click();
				}
			} catch (NoSuchElementException e1) {
				Log.info("Role Confirmation popuop is not displayed.");
			}
			
		}
		System.out.println(role_Code+ " role has been created.");
		return role_Code;
	}
	
//***********************************************************************************************************************************************************
	
	public void searchUserandClick(String userName, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(usersTab, ExplicitWaitCalls.Click.toString(), "", driver);
		usersTab.click();
		CommonUtils.hold(5);
		
		CommonUtils.explicitWait(user_SearchBox, ExplicitWaitCalls.Click.toString(), "", driver);
		CommonUtils.hold(5);
		user_SearchBox.clear();
		user_SearchBox.sendKeys(userName);
		user_SearchBox.sendKeys(Keys.ENTER);
		//user_SearchButton.click();
		//CommonUtils.hold(8);
		String userNameXpath = "//a[contains(@id,'cl2')and normalize-space(text())='"+userName+"']";
		try {
			AuditSetupinstance.waitTillXpathVisible(userNameXpath, 20, 2, driver);
			//CommonUtils.explicitWait(driver.findElement(By.xpath("//a[contains(@id,'cl2')and normalize-space(text())='"+userName+"']")), ExplicitWaitCalls.Click.toString(), "", driver);
		} catch (AssertionError e) {
			user_SearchBox.sendKeys(Keys.ENTER);
			user_SearchButton.click();
			//CommonUtils.hold(8);
			//CommonUtils.explicitWait(driver.findElement(By.xpath("//a[contains(@id,'cl2')and normalize-space(text())='"+userName+"']")), ExplicitWaitCalls.Click.toString(), "", driver);
			AuditSetupinstance.waitTillXpathVisible(userNameXpath, 20, 2, driver);
		}
		
		WebElement ele = driver.findElement(By.xpath(userNameXpath));
		try {
			ele.click();
		} catch (ElementNotInteractableException e) {
			js.executeScript("arguments[0].scrollIntoView(true);",ele);
			Log.info("----after Verticale scroll into view----");
			CommonUtils.explicitWait(ele, ExplicitWaitCalls.Click.toString(), "", driver);
			ele.click();
			
		}
		//CommonUtils.hold(5);
		CommonUtils.waitForInvisibilityOfElement(ele, driver, 8);
		
	}

//*************************************************************************************************************************************************************
	
	public void clickOnEditUserButton(String userName, WebDriver driver) throws Exception {
		try {
			CommonUtils.explicitWait(editUserButton, ExplicitWaitCalls.Click.toString(), "", driver);
			editUserButton.click();
		} catch (ElementNotInteractableException e) {
			System.out.println("in catch");
			CommonUtils.hold(10);
			CommonUtils.explicitWait(editUserButton, "Click", "", driver);
			editUserButton.click();
		}
		//CommonUtils.hold(5);
		CommonUtils.waitForInvisibilityOfElement(editUserButton, driver, 5);
	}
		
//***********************************************************************************************************************************************************	

	public void editUserEmail(String userName, String email, WebDriver driver) throws Exception {
		searchUserandClick(userName, driver);
		clickOnEditUserButton(userName, driver);
		
		CommonUtils.explicitWait(userEmail, ExplicitWaitCalls.Visible.toString(), "", driver);
		CommonUtils.hold(3);
		userEmail.clear();
		userEmail.sendKeys(email);
		userEmail.sendKeys(Keys.TAB);
		CommonUtils.hold(8);
		userInformation.click();
		//CommonUtils.hold(8);
		AuditSetupinstance.isButtonEnabled(SaveAndCloseBtn, 12);
		
		CommonUtils.explicitWait(SaveAndCloseBtn, ExplicitWaitCalls.Click.toString(), "", driver);
		SaveAndCloseBtn.click();
		//CommonUtils.hold(10);
		CommonUtils.waitForInvisibilityOfElement(SaveAndCloseBtn, driver, 20);
		//CommonUtils.waitforElementtoVisible(40, DoneButton, driver);
		AuditSetupinstance.waitTillElementVisible(DoneButton, 40, 2, driver);
		CommonUtils.explicitWait(DoneButton, "Click", "", driver);
		DoneButton.click();
		CommonUtils.waitForInvisibilityOfElement(DoneButton, driver, 20);
		//CommonUtils.waitforElementtoVisible(120, user_SearchBox, driver);
		AuditSetupinstance.waitTillElementVisible(user_SearchBox, 120, 10, driver);
		Log.info("User Email has been updated with "+email);
		
		
	}
	
//***********************************************************************************************************************************************************

	public void DeactivateFAUser(String userName, WebDriver driver) throws Exception {
		searchUserandClick(userName, driver);
		clickOnEditUserButton(userName, driver);
		
		if(activeCheckbox.isSelected() ) {
			System.out.println("in if:");
			activeCheckboxLabel.click();
			CommonUtils.hold(8);
			System.out.println("deacivated the user.");
		}
		else {
			System.out.println("in else");
			//unchecking and checking the checkbox to enable Save and Close button.
			activeCheckboxLabel.click();
			CommonUtils.hold(8);
			
			activeCheckboxLabel.click();
			CommonUtils.hold(8);
		}
		
		CommonUtils.explicitWait(SaveAndCloseBtn, ExplicitWaitCalls.Click.toString(), "", driver);
		SaveAndCloseBtn.click();
		CommonUtils.hold(10);
		CommonUtils.waitforElementtoVisible(120, DoneButton, driver);
		
	}
	
//***********************************************************************************************************************************************************
	public void ActivateFAUser(String userName, WebDriver driver) throws Exception {
		searchUserandClick(userName, driver);
		clickOnEditUserButton(userName, driver);
		
		if(!activeCheckbox.isSelected() ) {
			System.out.println("in if:");
			activeCheckboxLabel.click();
			CommonUtils.hold(8);
			System.out.println("Activated the user.");
		}
		else {
			System.out.println("in else");
			//unchecking and checking the checkbox to enable Save and Close button.
			activeCheckboxLabel.click();
			CommonUtils.hold(8);
			
			activeCheckboxLabel.click();
			CommonUtils.hold(8);
		}
		
		CommonUtils.explicitWait(SaveAndCloseBtn, ExplicitWaitCalls.Click.toString(), "", driver);
		SaveAndCloseBtn.click();
		CommonUtils.hold(10);
		CommonUtils.waitforElementtoVisible(120, DoneButton, driver);
		
	}
		
		
//***********************************************************************************************************************************************************

	public void addExistingRoleToUser(String userName, String roleName, WebDriver driver) throws Exception {
		searchUserandClick(userName, driver);
		clickOnEditUserButton(userName, driver);
		
		addRoleButton.click();
		SaaSPaaSCommonUtils.waitTillUILoad(12, driver);
		CommonUtils.explicitWait(addRoleMembershipPopUp, ExplicitWaitCalls.Visible.toString(), "", driver);
		CommonUtils.explicitWait(Role_SearchBox, ExplicitWaitCalls.Visible.toString(), "", driver);
		CommonUtils.hold(3);
		Role_SearchBox.sendKeys(roleName);
		//Role_SearchBox.sendKeys(Keys.ENTER);
		SaaSPaaSCommonUtils.waitTillUILoad(10, driver);
		CommonUtils.hold(3);
		
		//span[@class='AppsBoldFont' and normalize-space(text())='"+roleName+"']
		CommonUtils.explicitWait(driver.findElement(By.xpath("//span[@class='FndSearchSuggestItemHilight' and normalize-space(text())='"+roleName+"']")), ExplicitWaitCalls.Click.toString(), "", driver);
		driver.findElement(By.xpath("//span[@class='FndSearchSuggestItemHilight' and normalize-space(text())='"+roleName+"']")).click();
		
		CommonUtils.explicitWait(addRoleMembershipButton, ExplicitWaitCalls.Click.toString(), "", driver);
		addRoleMembershipButton.click();
		CommonUtils.explicitWait(addRoleMembershipDoneButton, ExplicitWaitCalls.Click.toString(), "", driver);
		addRoleMembershipDoneButton.click();
		
		CommonUtils.hold(5);
		CommonUtils.explicitWait(SaveAndCloseBtn, ExplicitWaitCalls.Click.toString(), "", driver);
		SaveAndCloseBtn.click();
		CommonUtils.hold(10);
		CommonUtils.waitforElementtoVisible(120, DoneButton, driver);
		
	}
	
//***********************************************************************************************************************************************************
	public void searchRole(String role, WebDriver driver) throws Exception {
		CommonUtils.hold(5);
		CommonUtils.explicitWait(rolesTab, ExplicitWaitCalls.Click.toString(), "", driver);
		rolesTab.click();
		SaaSPaaSCommonUtils.waitTillUILoad(15, driver);

		CommonUtils.explicitWait(role_SearchBox, ExplicitWaitCalls.Click.toString(), "", driver);
		CommonUtils.hold(3);
		role_SearchBox.clear();
		role_SearchBox.sendKeys(role);
		role_SearchBox.sendKeys(Keys.ENTER);
		CommonUtils.hold(6);
		SaaSPaaSCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//span[normalize-space(text())='"+role+"']")), "Visible", "", driver);
		driver.findElement(By.xpath("//span[normalize-space(text())='"+role+"']")).click();
		SaaSPaaSCommonUtils.waitTillUILoad(20, driver);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//h2[normalize-space(text())='"+role+"']")), "Visible", "", driver);

	}
	
//***********************************************************************************************************************************************************
	public void clickOnEditRole(String role, WebDriver driver) throws Exception {
		WebElement roleActionDropdown = driver.findElement(By.xpath("//span[normalize-space(text())='"+role+"']/following::button[@title='Actions']"));
		CommonUtils.explicitWait(roleActionDropdown, ExplicitWaitCalls.Click.toString(), "", driver);
		roleActionDropdown.click();
		CommonUtils.explicitWait(editRoleOption, ExplicitWaitCalls.Click.toString(), "", driver);
		editRoleOption.click();
		CommonUtils.hold(3);
		
	}

//***********************************************************************************************************************************************************	

	public void addUserToRole(String role, String userName, WebDriver driver) throws Exception {
		
		searchRole(role,driver);
		clickOnEditRole(role,driver);
		
		CommonUtils.explicitWait(usersStep, ExplicitWaitCalls.Click.toString(), "", driver);
		usersStep.click();
		CommonUtils.hold(6);
		
		CommonUtils.explicitWait(addUserButton, ExplicitWaitCalls.Click.toString(), "", driver);
		if(!userName.equalsIgnoreCase("")) {
			CommonUtils.explicitWait(addUserButton, ExplicitWaitCalls.Click.toString(), "", driver);
			addUserButton.click();
			
			CommonUtils.explicitWait(addUser_Search, ExplicitWaitCalls.Visible.toString(), "", driver);
			addUser_Search.sendKeys(userName);
			
			try {
				CommonUtils.explicitWait(addUser_SearchResultFull, ExplicitWaitCalls.Visible.toString(), "", driver);
				addUser_SearchResultFull.click();
			} catch (NoSuchElementException e) {
				CommonUtils.explicitWait(addUser_SearchResultPart, ExplicitWaitCalls.Visible.toString(), "", driver);
				addUser_SearchResultPart.click();
			}
			
			CommonUtils.explicitWait(addUser_SearchResult_UserLoginContent, ExplicitWaitCalls.Visible.toString(), "", driver);
			addUser_SearchResult_UserLoginContent.click();
			
			addUserToRoleButton.click();
			CommonUtils.hold(5);
			
			addUser_CancelButton.click();
			CommonUtils.hold(10);
			
		}
		CommonUtils.waitForInvisibilityOfElement(waitCursor, driver, 15);
		CommonUtils.explicitWait(NextButton, ExplicitWaitCalls.Click.toString(), "", driver);
		NextButton.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(5);
		
		CommonUtils.explicitWait(SaveAndCloseBtn, ExplicitWaitCalls.Click.toString(), "", driver);
		SaveAndCloseBtn.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(8);
		
		try {
			if(role_ConfirmationPopUp.isDisplayed()) {
				role_ConfirmationPopUpOKButton.click();
			}
		} catch (Exception e){
			CommonUtils.hold(30);
			try {
				if(role_ConfirmationPopUp.isDisplayed()) {
					role_ConfirmationPopUpOKButton.click();
				}
			} catch (NoSuchElementException e1) {
				Log.info("Role Confirmation popuop is not displayed.");
			}
			
		}
		
	}
		
//***********************************************************************************************************************************************************	

	public void updateRoleDescription(String role, String roleDescription, WebDriver driver) throws Exception {
		
		searchRole(role,driver);
		clickOnEditRole(role,driver);
		
		CommonUtils.explicitWait(roleDescriptionArea, ExplicitWaitCalls.Visible.toString(), "", driver);
		roleDescriptionArea.click();
		roleDescriptionArea.clear();
		roleDescriptionArea.sendKeys(roleDescription);
		
		summaryStep.click();
		CommonUtils.hold(5);
		
		CommonUtils.explicitWait(SaveAndCloseBtn, ExplicitWaitCalls.Click.toString(), "", driver);
		SaveAndCloseBtn.click();
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(8);
		
		try {
			if(role_ConfirmationPopUp.isDisplayed()) {
				role_ConfirmationPopUpOKButton.click();
			}
		} catch (Exception e){
			CommonUtils.hold(30);
			try {
				if(role_ConfirmationPopUp.isDisplayed()) {
					role_ConfirmationPopUpOKButton.click();
				}
			} catch (NoSuchElementException e1) {
				Log.info("Role Confirmation popuop is not displayed.");
			}
			
		}
		
	}
		
//***********************************************************************************************************************************************************	

	public String validateUserAlreadyAddedToRole(String role, String[] userList, WebDriver driver) throws Exception {
		searchRole(role,driver);
		SaaSPaaSCommonUtils.waitTillUILoad(10, driver);
		CommonUtils.explicitWait(expandTowardDropdown, ExplicitWaitCalls.Click.toString(), "", driver);
		expandTowardDropdown.click();
		CommonUtils.explicitWait(expandTowardAsUsers, ExplicitWaitCalls.Click.toString(), "", driver);
		expandTowardAsUsers.click();
		
		CommonUtils.waitForInvisibilityOfElement(waitCursor, driver, 10);
		CommonUtils.explicitWait(showDropdown, ExplicitWaitCalls.Click.toString(), "", driver);
		showDropdown.click();
		CommonUtils.explicitWait(showAsUsers, ExplicitWaitCalls.Click.toString(), "", driver);
		showAsUsers.click();
		
		boolean userFound = false;
		String userName="";
		for(String user : userList) {
			System.out.println("User is: "+user);
			CommonUtils.explicitWait(userLoginFilter, ExplicitWaitCalls.Click.toString(), "", driver);
			userLoginFilter.clear();
			userLoginFilter.sendKeys(user);
			userLoginFilter.sendKeys(Keys.ENTER);
			try {
				CommonUtils.waitforElementtoVisible(10, noDataToDisplay, driver);
				System.out.println(user+" user is not added to the role.");
				userName = user;
				userFound = true;
				break;
			}catch (Exception e) {
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[normalize-space(text())='"+role+"']")), ExplicitWaitCalls.Visible.toString(), "", driver);
				System.out.println(user+" user is already added to the role.");
			}
		}
		
		return userName;
		
	}
		
		

		
}
