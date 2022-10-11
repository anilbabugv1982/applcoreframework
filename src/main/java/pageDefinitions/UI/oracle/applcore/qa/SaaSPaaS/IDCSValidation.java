package pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.CommonUtils;

public class IDCSValidation extends IDCSPage{

	public IDCSValidation(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	private Actions action;
    private JavascriptExecutor js;
	
	public void loginToIDCS(String user, String pwd, WebDriver driver) throws Exception {
		driver.get(GetDriverInstance.idcsURL);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.hold(4);
		if(driver.getCurrentUrl().contains("/signin")) {
			CommonUtils.explicitWait(loginuserName, "Click", "", driver);
			loginuserName.clear();
			loginuserName.sendKeys(user);
			
			CommonUtils.explicitWait(password, "Click", "", driver);
			password.clear();
			password.sendKeys(pwd);
			
			CommonUtils.explicitWait(signinButton, "Click", "", driver);
			signinButton.click();
			CommonUtils.hold(10);
			CommonUtils.waitForPageLoad(driver);
		}
		else {
			System.out.println("User is already signed in.");
		}
		
		
	}
	
	public void resetIDCSPassword(String user, String tempPwd, String pwd, WebDriver driver) throws Exception {
		loginToIDCS(user, tempPwd, driver);
		CommonUtils.hold(2);
		//CommonUtils.explicitWait(oldPasswordField, "Click", "", driver);
		oldPasswordField.clear();
		oldPasswordField.sendKeys(tempPwd);
		
		CommonUtils.explicitWait(newPasswordField, "Click", "", driver);
		newPasswordField.clear();
		newPasswordField.sendKeys(pwd);
		
		CommonUtils.explicitWait(confirmNewPasswordField, "Click", "", driver);
		confirmNewPasswordField.clear();
		confirmNewPasswordField.sendKeys(pwd);
		
		CommonUtils.hold(3);
		CommonUtils.explicitWait(confirmNewPasswordField, "Click", "", driver);
		resetPasswordButton.click();
		CommonUtils.waitForPageLoad(driver);
		
	}
	
	public String[] createATGQAClientApplication(String applicationName, WebDriver driver) throws Exception {
		waitTillUILoad(10, driver);
		CommonUtils.explicitWait(goToAppsIcon, "Click", "", driver);
		goToAppsIcon.click();
		CommonUtils.waitForInvisibilityOfElement(goToAppsIcon, driver, 10);
		waitTillUILoad(10, driver);
		
		try {
			if(driver.findElement(By.xpath("//div[text()='"+applicationName+"']")).isDisplayed()) {
				System.out.println(applicationName+" application already created in IDCS.");
				driver.findElement(By.xpath("//div[text()='"+applicationName+"']")).click();
			}
		} catch (NoSuchElementException e) {
			System.out.println("Creating New ATGQAClient application");
			CommonUtils.explicitWait(addApplicationBtn, "Click", "", driver);
			addApplicationBtn.click();
			
			CommonUtils.explicitWait(enterpriseApplication, "Click", "", driver);
			enterpriseApplication.click();
			CommonUtils.waitForInvisibilityOfElement(enterpriseApplication, driver, 15);
			waitTillUILoad(10, driver);
			
			
			CommonUtils.explicitWait(appNameField, "Click", "", driver);
			appNameField.clear();
			appNameField.sendKeys(applicationName);
			
			CommonUtils.explicitWait(appDescField, "Click", "", driver);
			appDescField.clear();
			appDescField.sendKeys("Application for User Role Migration");
			
			CommonUtils.explicitWait(appURLField, "Click", "", driver);
			appURLField.clear();
			appURLField.sendKeys(GetDriverInstance.EnvironmentName);
			
			CommonUtils.explicitWait(addAppNextBtn, "Click", "", driver);
			CommonUtils.hold(3);
			addAppNextBtn.click();
			waitTillUILoad(15, driver);
			
			CommonUtils.explicitWait(clientConfiguration, "Click", "", driver);
			if(clientConfiguration.getAttribute("aria-expanded").equalsIgnoreCase("false")) {
				clientConfiguration.click();
				//CommonUtils.hold(3);
				waitTillUILoad(15, driver);
			}
			
			CommonUtils.explicitWait(configAsClientRadioBtn, "Click", "", driver);
			configAsClientRadioBtn.click();
			
			CommonUtils.explicitWait(clientCredentialsCheckbox, "Click", "", driver);
			clientCredentialsCheckbox.click();
			
			CommonUtils.explicitWait(AddGrantClientAccessIcon, "Click", "", driver);
			CommonUtils.scrollToElement(AddGrantClientAccessIcon, driver);
			AddGrantClientAccessIcon.click();
			
			CommonUtils.explicitWait(IdentityDomainAdminCheckbox, "Click", "", driver);
			CommonUtils.scrollToElement(IdentityDomainAdminCheckbox, driver);
			IdentityDomainAdminCheckbox.click();
			
			CommonUtils.explicitWait(UserAdminCheckbox, "Click", "", driver);
			CommonUtils.scrollToElement(UserAdminCheckbox, driver);
			UserAdminCheckbox.click();
			
			CommonUtils.explicitWait(addAppRole_OkBtn, "Click", "", driver);
			addAppRole_OkBtn.click();
			
			Assert.assertTrue(IdentityDomainAdminRole.isDisplayed(), "Identity Domain Administrator app Role is not granted the client access.");
			Assert.assertTrue(UserAdminRole.isDisplayed(), "User Administrator app Role is not granted the client access.");
			
			CommonUtils.scrollToElement(addAppFinishBtn, driver);
			addAppFinishBtn.click();
			waitTillUILoad(10, driver);
			
			CommonUtils.explicitWait(ActivateBtn, "Click", "", driver);
			ActivateBtn.click();
			
			CommonUtils.explicitWait(activateApp_OkBtn, "Click", "", driver);
			activateApp_OkBtn.click();
			
		}
		
		waitTillUILoad(10, driver);
		CommonUtils.explicitWait(OAuthConfigurationHeader, "Click", "", driver);
		OAuthConfigurationHeader.click();
		
		CommonUtils.explicitWait(clientIDValue, "Visible", "", driver);
		String clientID = clientIDValue.getText().trim();
		
		CommonUtils.explicitWait(showSecretBtn, "Click", "", driver);
		showSecretBtn.click();
		
		CommonUtils.explicitWait(clientSecretValue, "Visible", "", driver);
		String clientSecret = clientSecretValue.getText().trim();
		
		CommonUtils.explicitWait(clientSecretCloseBtn, "Visible", "", driver);
		clientSecretCloseBtn.click();
		
		return new String[] {clientID,clientSecret};
		
	}
	
	public void clickOnUsersTab(WebDriver driver) throws Exception {
		try {
			CommonUtils.explicitWait(navigator, "Click", "", driver);
			navigator.click();
			CommonUtils.explicitWait(usersTab, "Click", "", driver);
			usersTab.click();
			CommonUtils.explicitWait(idcsPageHeader, "Click", "", driver);
			Assert.assertTrue(idcsPageHeader.getText().equalsIgnoreCase("Users"), "Users page not displayed after clicking on Users tab.");
			
		}catch (Exception e) {
			CommonUtils.explicitWait(navigator, "Click", "", driver);
			navigator.click();
			CommonUtils.explicitWait(usersTab, "Click", "", driver);
			usersTab.click();
			CommonUtils.explicitWait(idcsPageHeader, "Click", "", driver);
			Assert.assertTrue(idcsPageHeader.getText().equalsIgnoreCase("Users"), "Users page not displayed after clicking on Users tab.");
		}
		CommonUtils.waitForPageLoad(driver);
	}
	
	public void clickOnRolesTab(WebDriver driver) throws Exception {
		try {
			CommonUtils.explicitWait(navigator, "Click", "", driver);
			navigator.click();
			CommonUtils.explicitWait(groupsTab, "Click", "", driver);
			groupsTab.click();
			CommonUtils.explicitWait(idcsPageHeader, "Click", "", driver);
			Assert.assertTrue(idcsPageHeader.getText().equalsIgnoreCase("Groups"), "Groups page not displayed after clicking on Groups tab.");
		}catch (Exception e) {
			CommonUtils.explicitWait(navigator, "Click", "", driver);
			navigator.click();
			CommonUtils.explicitWait(groupsTab, "Click", "", driver);
			groupsTab.click();
			CommonUtils.explicitWait(idcsPageHeader, "Click", "", driver);
			Assert.assertTrue(idcsPageHeader.getText().equalsIgnoreCase("Groups"), "Groups page not displayed after clicking on Groups tab.");
		}
		CommonUtils.waitForPageLoad(driver);
	}
	
	public void searchUser(String userName, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(usersSearch, "Click", "", driver);
		usersSearch.clear();
		usersSearch.sendKeys(userName);
		CommonUtils.hold(2);
		usersSearch.sendKeys(Keys.ENTER);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@class,'user-list-view-item-user-name') and text()='"+userName+"']")), "Click", "", driver);
		
	}
	
	public void searchAndClickUser(String userName, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(usersSearch, "Click", "", driver);
		usersSearch.clear();
		usersSearch.sendKeys(userName);
		CommonUtils.hold(2);
		usersSearch.sendKeys(Keys.ENTER);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@class,'user-list-view-item-user-name') and text()='"+userName+"']")), "Click", "", driver);
		WebElement user=driver.findElement(By.xpath("//div[contains(@class,'user-list-view-item-user-name') and text()='"+userName+"']"));
		
		try {
			action = new Actions(driver);
			action.moveToElement(user).click().build().perform();
			CommonUtils.waitForPageLoad(driver);
		}catch (Exception e){
			js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", user);
			
		}
		
		CommonUtils.waitForInvisibilityOfElement(user, driver, 30);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//h1[@class='idcs-page-header-title' and contains(text(),'"+userName+"')]")), "Click", "", driver);
		//CommonUtils.waitForInvisibilityOfElement(spinningWheel, driver, 30);
		
	}
	
	public void verifyUserEmailInIDCS(String userName, String email, WebDriver driver) throws Exception {
		clickOnUsersTab(driver);
		driver.navigate().refresh();
		
		searchUser(userName, driver);
		
		WebElement userEmail = driver.findElement(By.xpath("//div[contains(@class,'user-list-view-item-user-name') and text()='"+userName+"']/following::span[contains(@aria-labelledby,'email')]"));
		System.out.println("Email is : "+userEmail.getText());
		Assert.assertEquals(userEmail.getText(), email, "Email is not correct in IDCS.");
		
	}
	
	public void verifyUserIsDeactivated(String userName, WebDriver driver) throws Exception {
		clickOnUsersTab(driver);
		driver.navigate().refresh();
		
		searchUser(userName, driver);
		
		WebElement activeStatus = driver.findElement(By.xpath("//div[contains(@class,'user-list-view-item-user-name') and text()='"+userName+"']/following::span[contains(@class,'idcs-text-color')]"));
		System.out.println("User is : "+activeStatus.getAttribute("title"));
		Assert.assertEquals(activeStatus.getAttribute("title"), "Inactive", "User is not Inactive in IDCS.");
		
	}
	
	public void verifyUserIsActivated(String userName, WebDriver driver) throws Exception {
		clickOnUsersTab(driver);
		driver.navigate().refresh();
		
		searchUser(userName, driver);
		
		WebElement activeStatus = driver.findElement(By.xpath("//div[contains(@class,'user-list-view-item-user-name') and text()='"+userName+"']/following::span[contains(@class,'idcs-text-color')]"));
		System.out.println("User is : "+activeStatus.getAttribute("title"));
		Assert.assertEquals(activeStatus.getAttribute("title"), "Active", "User is not Active in IDCS.");
		
	}
	
	public void verifyFederated(String userName, String value, WebDriver driver) throws Exception {
		clickOnUsersTab(driver);
		driver.navigate().refresh();
		
		searchAndClickUser(userName, driver);
		
		//WebElement federatedStatus = driver.findElement(By.xpath("//label[text()='Federated']/following::div[@class='oj-switch-thumb']"));
		System.out.println("User Federation status is : "+federatedStatus.getAttribute("aria-checked"));
		Assert.assertEquals(federatedStatus.getAttribute("aria-checked"), value, "Federated status is incorrect.");
		
	}
	
	public void verifyUserWithRole(String userName, String roleName, WebDriver driver) throws Exception {
		clickOnUsersTab(driver);
		driver.navigate().refresh();
		
		searchAndClickUser(userName, driver);
		CommonUtils.explicitWait(groupsSection_UsersTab, "Click", "", driver);
		CommonUtils.hold(2);
		Actions action = new Actions(driver);
		action.moveToElement(groupsSection_UsersTab).click().build().perform();	
			
		//CommonUtils.explicitWait(groupsSection_UsersTab, "Click", "", driver);
		//groupsSection_UsersTab.click();
		
		CommonUtils.explicitWait(searchGroup_UsersTab, "Click", "", driver);
		searchGroup_UsersTab.clear();
		searchGroup_UsersTab.sendKeys(roleName);
		searchGroup_UsersTab.sendKeys(Keys.ENTER);
		CommonUtils.hold(5);
		
		CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@class,'groups-name-list-view') and text()='"+roleName+"']")), "Visible", "", driver);
		WebElement role = driver.findElement(By.xpath("//div[contains(@class,'groups-name-list-view') and text()='"+roleName+"']"));
		Assert.assertTrue(role.isDisplayed(), roleName+" is not associated with  "+userName+" user");
		
		
	}
	
	public void searchRole(String roleName, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(groupsSearch, "Click", "", driver);
		groupsSearch.clear();
		groupsSearch.sendKeys(roleName);
		CommonUtils.hold(2);
		groupsSearch.sendKeys(Keys.ENTER);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(driver);
		//boolean isDisplayed = false;
		CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@class,'groups-name-list-view') and text()='"+roleName+"']")), "Click", "", driver);
		//WebElement role = driver.findElement(By.xpath("//div[contains(@class,'groups-name-list-view') and text()='"+roleName+"']"));
		//isDisplayed = role.isDisplayed();
		//return isDisplayed;
		
	}
	
	public void searchAndClickRole(String roleName, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(groupsSearch, "Click", "", driver);
		groupsSearch.clear();
		groupsSearch.sendKeys(roleName);
		CommonUtils.hold(2);
		groupsSearch.sendKeys(Keys.ENTER);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(driver);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@class,'groups-name-list-view') and text()='"+roleName+"']")), "Click", "", driver);
		WebElement role=driver.findElement(By.xpath("//div[contains(@class,'groups-name-list-view') and text()='"+roleName+"']"));
		
		try {
			action = new Actions(driver);
			action.moveToElement(role).click().build().perform();
			CommonUtils.waitForPageLoad(driver);
		}catch (Exception e){
			js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", role);
			
		}
		
		CommonUtils.waitForInvisibilityOfElement(role, driver, 30);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//h1[@class='main-heading idcs-single-line-ellipsis' and contains(text(),'"+roleName+"')]")), "Click", "", driver);
		CommonUtils.waitForInvisibilityOfElement(spinningWheel, driver, 30);
		
	}
	
	
	public void verifyRole(String roleName, WebDriver driver) throws Exception {
		clickOnRolesTab(driver);
		driver.navigate().refresh();
		
		searchRole(roleName, driver);
		
	}
	
	public void verifyRoleWithUser(String userName, String roleName, WebDriver driver) throws Exception {
		clickOnRolesTab(driver);
		driver.navigate().refresh();
		
		searchAndClickRole(roleName, driver);
		CommonUtils.explicitWait(usersSection_GroupsTab, "Click", "", driver);
		Actions action = new Actions(driver);
		action.moveToElement(usersSection_GroupsTab).click().build().perform();	
			
		//CommonUtils.explicitWait(groupsSection_UsersTab, "Click", "", driver);
		//groupsSection_UsersTab.click();
		
		CommonUtils.explicitWait(searchUser_GroupsTab, "Click", "", driver);
		searchUser_GroupsTab.clear();
		searchUser_GroupsTab.sendKeys(userName);
		searchUser_GroupsTab.sendKeys(Keys.ENTER);
		CommonUtils.hold(4);
		
		try {
			CommonUtils.explicitWait(driver.findElement(By.xpath("//div[@aria-label='User Name' and text()='"+userName+"']")), "Visible", "", driver);
			WebElement role = driver.findElement(By.xpath("//div[@aria-label='User Name' and text()='"+userName+"']"));
			Assert.assertTrue(role.isDisplayed(), userName+" is not associated with  "+roleName+" role.");
		} catch (Exception e) {
			Assert.assertFalse(true, userName+" is not associated with "+roleName+" role.");
		}
		
		
		
	}
	
	public void verifyRoleDescription(String roleName, String roleDescription, WebDriver driver) throws Exception {
		clickOnRolesTab(driver);
		driver.navigate().refresh();
		
		searchRole(roleName, driver);
		
		WebElement desc = driver.findElement(By.xpath("//div[contains(@class,'groups-name-list-view') and text()='"+roleName+"']/following-sibling::div[contains(@class,'groups-desc-list-view')]"));
		Assert.assertEquals(desc.getText(), roleDescription, "Role description is mismatching.");
		
	}
	
	public void verifyRoleIsNotPresent(String roleName, WebDriver driver) throws Exception {
		clickOnRolesTab(driver);
		driver.navigate().refresh();
		
		CommonUtils.explicitWait(groupsSearch, "Click", "", driver);
		groupsSearch.clear();
		groupsSearch.sendKeys(roleName);
		CommonUtils.hold(2);
		groupsSearch.sendKeys(Keys.ENTER);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(driver);
		//boolean isDisplayed = false;
		try {
			CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(@class,'groups-name-list-view') and text()='"+roleName+"']")), "Click", "", driver);
			WebElement role = driver.findElement(By.xpath("//div[contains(@class,'groups-name-list-view') and text()='"+roleName+"']"));
			//isDisplayed = role.isDisplayed();
			Assert.assertFalse(true, "Role is displayed. It should not be displayed.");
		} catch (NoSuchElementException e) {
			System.out.println("Role is not displayed.");
			Assert.assertTrue(true);
		}
		
	}
	
	
	public static void waitTillUILoad(int timeInSeconds, WebDriver driver) {
		CommonUtils.hold(3);
		final int TIMEOUT = timeInSeconds*1000;  
		long targetTime = System.currentTimeMillis() + TIMEOUT;
		while((System.currentTimeMillis() < targetTime)) {
			CommonUtils.hold(1);
		    if (driver.findElement(By.xpath("//div[@class='idcs-spinning-wheel-background']")).getAttribute("style").contains("display: none;")) {
		    	break;
		    }
		}
		//CommonUtils.hold(2);
	}
}
