package pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import UtilClasses.UI.CommonUtils.ExplicitWaitCalls;

public class ManageUserRoleMigration extends ManageUserRoleMigrationPage {

	public ManageUserRoleMigration(WebDriver driver) {
		super(driver);
	}
	
//***************************************************************************************************************************************************************
	
	public void validateNoUserPresentInUserYetToSyncTab(WebDriver driver) throws Exception {
		SaaSPaaSCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.hold(3);
		CommonUtils.explicitWait(yetToSyncTab, ExplicitWaitCalls.Click.toString(), "", driver);
		yetToSyncTab.click();
		SaaSPaaSCommonUtils.waitTillUILoad(15, driver);
		//CommonUtils.waitForPageLoad(driver);
		//CommonUtils.hold(5);
		
		if(!yetToSyncTab.getAttribute("class").contains("p_AFSelected")) {
			CommonUtils.explicitWait(yetToSyncTab, ExplicitWaitCalls.Click.toString(), "", driver);
			yetToSyncTab.click();
			CommonUtils.hold(6);
		}
		
		List<WebElement> userNames =driver.findElements(By.xpath("//span[contains(@id,'AT3:_ATp:ATt3') and contains(@id,'ot5')]"));
		System.out.println("Users present in Users yet to Synchronize tab: "+userNames.size());
		
		if(userNames.size()==0) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false, "Users are still present in Users yet to Synchronize tab.");
		}
	
	}

//***********************************************************************************************************************************************************	
	public void validateUserInUserYetToSyncTab(String userName, WebDriver driver) throws Exception {
		SaaSPaaSCommonUtils.waitTillUILoad(15, driver);
		CommonUtils.hold(3);
		CommonUtils.explicitWait(yetToSyncTab, ExplicitWaitCalls.Click.toString(), "", driver);
		yetToSyncTab.click();
		SaaSPaaSCommonUtils.waitTillUILoad(15, driver);
		//CommonUtils.waitForPageLoad(driver);
		//CommonUtils.hold(5);
		/*
		try {
			System.out.println("in try");
			if(yetToSyncTabSelected.isDisplayed()) {
				Log.info("User is in Users yet to Synchronize tab.");
			}
		} catch (NoSuchElementException e) {
			System.out.println("in catch");
			CommonUtils.explicitWait(yetToSyncTab, ExplicitWaitCalls.Click.toString(), "", driver);
			yetToSyncTab.click();
			CommonUtils.waitForInvisibilityOfElement(waitCursor, driver, 10);
			CommonUtils.waitForPageLoad(driver);
			CommonUtils.hold(5);
		}
		*/
		if(!yetToSyncTab.getAttribute("class").contains("p_AFSelected")) {
			CommonUtils.explicitWait(yetToSyncTab, ExplicitWaitCalls.Click.toString(), "", driver);
			yetToSyncTab.click();
			CommonUtils.hold(6);
		}
		
		List<WebElement> userNames =driver.findElements(By.xpath("//span[contains(@id,'AT3:_ATp:ATt3') and contains(@id,'ot5')]"));
		System.out.println("Users present in Users yet to Synchronize tab: "+userNames.size());
		
		if(userNames.size()==0) {
			Assert.assertFalse(true, "No Users present in Users yet to Synchronize tab.");
		}
		
		boolean status = false;
		for(WebElement ee:userNames) {
			if (ee.getText().trim().equalsIgnoreCase(userName)) {
				status = true;
				break;
			}
		}
		
		if (!status) {
			Assert.assertFalse(true, userName+" user not present in Users yet to Synchronized tab.");
		}
		
			
	}
//***********************************************************************************************************************************************************
	
	public void validateUsersUpdatedEmailInUserYetToSyncTab(String userName, String updatedEmail, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(yetToSyncTab, ExplicitWaitCalls.Click.toString(), "", driver);
		yetToSyncTab.click();
		SaaSPaaSCommonUtils.waitTillUILoad(20, driver);
		//CommonUtils.waitForPageLoad(driver);
		//CommonUtils.hold(5);
		
		
		List<WebElement> userNames =driver.findElements(By.xpath("//span[contains(@id,'AT3:_ATp:ATt3') and contains(@id,'ot5')]"));
		System.out.println("Users present in Users yet to Synchronize tab: "+userNames.size());
		
		if(userNames.size()==0) {
			Assert.assertFalse(true, "No Users present in Users yet to Synchronize tab.");
		}
		
		boolean status = false;
		int itr = 0;
		for(WebElement ee:userNames) {
			itr++;
			if (ee.getText().trim().equalsIgnoreCase(userName)) {
				status = true;
				WebElement email = driver.findElement(By.xpath("(//span[contains(@id,'AT3:_ATp:ATt3') and contains(@id,'ot2')])["+itr+"]"));
				Assert.assertEquals(email.getText(), updatedEmail, "Updated Email is not reflected in Users yet to Synchronized tab.");
				break;
			}
		}
		
		if (!status) {
			Assert.assertFalse(true, userName+" user not present in Users yet to Synchronized tab.");
		}
		
			
	}
		
		
//***********************************************************************************************************************************************************
	public void addRoleinMigrateRolesPage(String role, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(roleRefresh, ExplicitWaitCalls.Click.toString(), "", driver);
		try {
			roleRefresh.click();
		} catch(Exception e) {
			SaaSPaaSCommonUtils.waitTillUILoad(5, driver);
			driver.findElement(By.xpath("//img[contains(@id,'pc1:refresh::icon')]")).click();
		}
		
		SaaSPaaSCommonUtils.waitTillUILoad(15, driver);
		
		try {
			WebElement addedRole = driver.findElement(By.xpath("//span[normalize-space(text())='DisplayName']/following::span[normalize-space(text())='"+role+"']"));
			if (addedRole.isDisplayed()) {
				System.out.println("Role is already added in the list");
				Log.info("Role is already added in the list");
			}
		}catch (NoSuchElementException e) {
			
			CommonUtils.explicitWait(addRoleButton, ExplicitWaitCalls.Click.toString(), "", driver);
			addRoleButton.click();
			CommonUtils.hold(5);
			
			CommonUtils.explicitWait(displayName, ExplicitWaitCalls.Visible.toString(), "", driver);
			displayName.sendKeys(role);
			roleSearchButton.click();
			CommonUtils.hold(4);
			
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[normalize-space(text())='"+role+"']")), ExplicitWaitCalls.Visible.toString(), "", driver);
			driver.findElement(By.xpath("//span[normalize-space(text())='"+role+"']")).click();
			roleAddButton.click();
			CommonUtils.hold(3);
			
			//roleRefresh.click();
			CommonUtils.hold(4);
			WebElement addedRole1 = driver.findElement(By.xpath("//span[normalize-space(text())='DisplayName']/following::span[normalize-space(text())='"+role+"']"));		
			Assert.assertTrue(addedRole1.isDisplayed(), "Role has not been addded");
			Log.info("Role has been added.");
			
		}
		
	}
		
		
//***********************************************************************************************************************************************************
	
	public boolean isRolePresentInMigrateRolesPage(String role, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(roleRefresh, ExplicitWaitCalls.Click.toString(), "", driver);
		try {
			roleRefresh.click();
		} catch(Exception e) {
			driver.findElement(By.xpath("//img[contains(@id,'pc1:refresh::icon')]")).click();
		}
		CommonUtils.hold(6);
		
		boolean status = false;
		
		try {
			WebElement addedRole = driver.findElement(By.xpath("//span[normalize-space(text())='DisplayName']/following::span[normalize-space(text())='"+role+"']"));
			if (addedRole.isDisplayed()) {
				System.out.println(role+ " Role is already added in the list");
				Log.info(role+ " Role is already added in the list");
				status = true; 
			}
		}catch (NoSuchElementException e) {
			System.out.println(role+ " Role is not present in the list");
			Log.info(role+ " Role is not present in the list");
					
		}
		
		return status;
		
	}

//***********************************************************************************************************************************************************
	
	public void deleteRoleInMigrateRolesPage(String role, WebDriver driver) throws Exception {
		CommonUtils.explicitWait(roleRefresh, ExplicitWaitCalls.Click.toString(), "", driver);
		try {
			roleRefresh.click();
		} catch(Exception e) {
			driver.findElement(By.xpath("//img[contains(@id,'pc1:refresh::icon')]")).click();
		}
		CommonUtils.hold(6);
		SaaSPaaSCommonUtils.waitTillUILoad(20, driver);
		
		try {
			WebElement addedRole = driver.findElement(By.xpath("//span[normalize-space(text())='DisplayName']/following::span[normalize-space(text())='"+role+"']"));
			if (addedRole.isDisplayed()) {
				addedRole.click();
				SaaSPaaSCommonUtils.waitTillUILoad(20, driver);
				roleDeleteButton.click();
				CommonUtils.explicitWait(warningYesButton, ExplicitWaitCalls.Click.toString(), "", driver);
				warningYesButton.click();
				SaaSPaaSCommonUtils.waitTillUILoad(20, driver);
				
			}
		}catch (NoSuchElementException e) {
			Log.info(role+ " Role is not present in the list");
			Assert.fail(role+ " Role is not present in the list");
					
		}
		
		
	}

//************************************************************************************************************************************************************	
}
