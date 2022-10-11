package pageDefinitions.UI.oracle.applcore.qa.SaaSPaaS;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.CommonUtils.ExplicitWaitCalls;

public class ManageAdminProfileValues extends ManageAdminProfileValuesPage{

	public ManageAdminProfileValues(WebDriver driver) {
		super(driver);
	}
	
	public void searchAdminProfile(String adminProfile, WebDriver driver) {
		profileoptioncode.click();
		profileoptioncode.clear();
		profileoptioncode.sendKeys(adminProfile);
		search.click();
		CommonUtils.hold(6);
		SaaSPaaSCommonUtils.waitTillUILoad(15, driver);
	}
	
	public void addProfileOptionValues(String adminProfile, String profileLevel, String userName, String profileValue, WebDriver driver) throws Exception {
		create.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(profilelevelDropdown, "Click", "", driver);
		CommonUtils.selectDropDownElement(profilelevelDropdown, profileLevel);
		CommonUtils.hold(3);
		if(!userName.equalsIgnoreCase("")) {
			adminUserNameInputField.sendKeys(userName);
			CommonUtils.selectDropDownElement(adminUserNameProfileValueDD, profileValue);
		}
		else {
			try {
				CommonUtils.explicitWait(profilevalue,"Visible", "", driver);
				profilevalue.clear();
				profilevalue.sendKeys(profileValue);
			}catch(Exception e) {
				System.out.println("in catch.");
				CommonUtils.selectDropDownElement(adminUserNameProfileValueDD, profileValue);
			}
		}
		saveAdminBtn.click();
		SaaSPaaSCommonUtils.waitTillUILoad(15, driver);
		//CommonUtils.hold(3);
		CommonUtils.explicitWait(saveAdminBtn,"Click", "", driver);
	}
	 
	
	public void setProfileValue(String adminProfile, String profileLevel, String userName, String profileValue, WebDriver driver) throws Exception {
		CommonUtils.hold(3);
		searchAdminProfile(adminProfile, driver);
		boolean isPresent = verifyProfileLevelIsPresent(profileLevel, driver);
		if (isPresent) {
			try {
				CommonUtils.explicitWait(profilevalue,"Visible", "", driver);
				profilevalue.clear();
				profilevalue.sendKeys(profileValue);
			}catch(Exception e) {
				CommonUtils.selectDropDownElement(adminUserNameProfileValueDD, profileValue);
			}
			saveAdminBtn.click();
			SaaSPaaSCommonUtils.waitTillUILoad(15, driver);
			//CommonUtils.hold(3);
			CommonUtils.explicitWait(saveAdminBtn,"Click", "", driver);
		}
		else {
			addProfileOptionValues(adminProfile, profileLevel, userName, profileValue, driver);
			System.out.println(profileLevel+" Profile Level has been created.");
			
		}
		
	}
	
	public boolean verifyProfileLevelIsPresent(String profileLevel, WebDriver driver) {
		boolean status = false;
		try {
			if(profilelevel.getText().equalsIgnoreCase(profileLevel)) {
				status = true;
			}
		}catch (Exception e) {
			System.out.println("in catch block::"+profileLevel+" is not present.");
			
		}
		return status;
	}
	
	public void deleteAdminProfile(String adminProfile, String profileLevel, WebDriver driver) throws Exception {
		searchAdminProfile(adminProfile, driver);
		qbeSearch.clear();
		qbeSearch1.clear();
		
		if(profileLevel.equalsIgnoreCase("site")){
			qbeSearch1.sendKeys(profileLevel);
			qbeSearch1.sendKeys(Keys.ENTER);
		}
		else{		
			qbeSearch.sendKeys(profileLevel);
			qbeSearch.sendKeys(Keys.ENTER);
		}
		SaaSPaaSCommonUtils.waitTillUILoad(10, driver);
		CommonUtils.hold(3);
		try {
			if(profileValuesNoData.isDisplayed()) {
				System.out.println(profileLevel+" is not present.");
			}
		} catch(NoSuchElementException e) {
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + profileLevel + "']")),"Click", "", driver);
			driver.findElement(By.xpath("//span[text()='" + profileLevel + "']")).click();
			delete.click();
			CommonUtils.hold(5);
		}
		
		saveAdminBtn.click();
		CommonUtils.hold(5);

		try {

			CommonUtils.explicitWait(profileValuesNoData,
					CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
			Assert.assertTrue(
					profileValuesNoData.isDisplayed(),
					"Deleted AdminProfile value " + profileLevel);
		} catch (Exception e) {
			e.getMessage();
			Assert.assertFalse(true, "AdminProfile delete failed " + profileLevel);

		}

	}


}
