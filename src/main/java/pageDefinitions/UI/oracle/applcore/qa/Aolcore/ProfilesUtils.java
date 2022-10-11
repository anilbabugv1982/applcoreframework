package pageDefinitions.UI.oracle.applcore.qa.Aolcore;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ProfilePages;

public class ProfilesUtils extends ProfilePages {

	public ProfilesUtils(WebDriver driver) {
		super(driver);
	}

	/*
	 * Method for create Profile Options
	 * 
	 */

	public String profileoptionname = "PO" + CommonUtils.uniqueId();

	public void createProfileOptions(String pName, WebDriver driver) {

		try {

			CommonUtils.hold(2);
			CommonUtils.explicitWait(profileOptAddBtn, "Click", "", driver);
			profileOptAddBtn.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(profileOptCode, "Visible", "", driver);
			CommonUtils.hold(8);
			profileOptCode.sendKeys(pName);
			CommonUtils.explicitWait(profileDspName, "Visible", "", driver);
			profileDspName.sendKeys(pName);
			CommonUtils.selectDropDownElement(profileApplication, "Oracle Middleware Extensions for Applications");
			profileModule.sendKeys("Oracle Middleware Extensions for Applications");
			profileDescription.sendKeys(pName);
			profileStartDate.click();
			CommonUtils.hold(2);
			startDateIcon.click();
			CommonUtils.hold(3);
			startDateYearIncr.click();
			CommonUtils.hold(3);
			startDateNumber.click();
			CommonUtils.hold(2);
			saveAndCloseBtn.click();
			CommonUtils.hold(5);
			siteEnableCheckBox.click();
			siteUpdatableCheckBox.click();
			CommonUtils.hold(2);
			userEnabledCheckBox.click();
			userUpdatableCheckBox.click();
			CommonUtils.hold(2);
			saveProfileOption.click();
			CommonUtils.hold(4);

		} catch (Exception createProfileOptions) {
			Log.info("Exception in CreateProfileOptionPage(). " + createProfileOptions.getMessage());
		}

	}// End of CreateProfileCode()

	public void verifyCreateProfileOptions(String pName, WebDriver driver) {

		try {
			CommonUtils.hold(2);
			Log.info("Entered Create Verify Operation");
			createdProfileOptionValue.clear();
			createdProfileOptionValue.sendKeys(pName);
			CommonUtils.hold(2);
			profileOptSearch.click();

			// Verification of created Profile Option Value from Create
			String profileVaue = profileOptionFieldSavedValue.getText();

			if (profileVaue != null && !profileVaue.isEmpty()) {
				Log.info("1st column: " + profileVaue);
				Assert.assertEquals(pName, profileVaue);
			}

		} catch (Exception e) {
			Log.info("Exception in searchProfileOptionPage...()");
			e.printStackTrace();
		}

	}// End of Verify Create Profile Option

	public void searchProfileOption(String profileOption) throws Exception {
		createdProfileOptionValue.clear();
		createdProfileOptionValue.sendKeys(profileOption);
		profileOptSearch.click();
		CommonUtils.hold(4);

	}

	public void verifyStatus(String profileName, String value, WebDriver driver) throws Exception {

		searchProfileOption(profileName);
		if (!value.isEmpty()) {
			try {
				System.out.println("Not empty. ......");
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + value + "']")),
						CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + value + "']")).isDisplayed());
			} catch (Exception e) {
				Assert.fail();
			}
		} else {
			try {
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + profileName + "']")),
						CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + profileName + "']")).isDisplayed());
			} catch (Exception e) {
				Assert.fail();
			}
		}

	}

	/*
	 * Method for Update Profile Options
	 * 
	 */
	public void updateProfileOptions(String pName, String value) {

		try {
			searchProfileOption(pName);
			Log.info("Entered the Update Operation");
			editProfileOption.click();
			CommonUtils.hold(5);
			profileDspName.clear();
			profileDspName.sendKeys(value);
			Log.info("Entered update");
			saveAndCloseEditProfileOption.click();
			CommonUtils.hold(4);
			Log.info("Save done");

			// verifyUpdatedProfileOptions(pName);

		} catch (Exception e) {
			Log.info("Exception in updateProfileOptionPage...()");
			e.printStackTrace();
		}

	}// End of Update Profile Option

	/*
	 * Method to verify Update Profile Options
	 * 
	 */
	public void verifyUpdatedProfileOptions(String pName) {

		try {
			CommonUtils.hold(2);
			Log.info("Entered update verify Operation");
			CommonUtils.hold(2);
			createdProfileOptionValue.clear();
			createdProfileOptionValue.sendKeys(pName);
			CommonUtils.hold(2);
			profileOptSearch.click();

			// Verification of updated Profile Option Value from Update
			// Operation
			String profileDispValue = profileDispNameSavedValue.getText();

			if (profileDispValue != null && !profileDispValue.isEmpty()) {
				Log.info("2nd column: " + profileDispValue);
				Assert.assertEquals(pName + "_Updated", profileDispValue);
			}

		} catch (Exception e) {
			Log.info("Exception in searchProfileOptionPage...()");
			e.printStackTrace();
		}

	}

	/*
	 * Method to create Profile Category
	 * 
	 */
	public void createProfileCategory(String pCategoryCode, WebDriver driver) {
		try {
			CommonUtils.hold(2);
			Log.info("===Click on the add button===");
			CommonUtils.explicitWait(addButton, "Click", "", driver);
			addButton.click();
			CommonUtils.hold(8);
			Log.info("===Clear CategoryCode text and fill value===");
			CommonUtils.explicitWait(categoryCodeTextField, "Visible", "", driver);
			CommonUtils.hold(8);
			categoryCodeTextField.clear();
			categoryCodeTextField.sendKeys(pCategoryCode + "_" + pCategoryCode);
			Log.info("===Enter Category Name===");
			categoryNameTextField.sendKeys(pCategoryCode);
			Log.info("===Enter Application value===");
			CommonUtils.selectDropDownElement(applicationTextField, "Oracle Middleware Extensions for Applications");
			Log.info("===Enter Module Value===");
			moduleTextField.sendKeys("Oracle Middleware Extensions for Applications");
			// ProfilePage.descriptionTextField.click();
			saveAndCloseBtn1.click();
			CommonUtils.hold(5);
			Log.info("===Addition of profile Category successful===");

		} catch (Exception e) {
			Log.info("===Exception in addProfileCategory...()===");
			e.printStackTrace();

		}

	}

	public void searchProfileCategory(String profileCateCode, WebDriver driver) throws Exception {
		Log.info("===Search for the desired category===");
		CommonUtils.explicitWait(categoryTextField, "Visible", "", driver);
		categoryNameSearchField.clear();
		categoryNameSearchField.sendKeys(profileCateCode);
		searchButton.click();
		CommonUtils.hold(8);
	}

	public void verifyStatus1(String profileName, String value, WebDriver driver) throws Exception {

		searchProfileCategory(profileName, driver);
		if (!value.isEmpty()) {
			try {
				System.out.println("Not empty. ......");
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + value + "']")),
						CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + value + "']")).isDisplayed());
			} catch (Exception e) {
				Assert.fail();
			}
		} else {
			try {
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + profileName + "']")),
						CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + profileName + "']")).isDisplayed());
			} catch (Exception e) {
				Assert.fail();
			}
		}

	}

	public void updateCategory(String pCode, String value, WebDriver driver) throws Exception {
		searchProfileCategory(pCode, driver);
		editCategoryButton.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(categoryDescField, CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
		categoryDescField.clear();
		categoryDescField.sendKeys(value);
		saveAndCloseBtn1.click();
		CommonUtils.hold(5);

	}

	/*
	 * Method to create Profile option
	 * 
	 */

	public void addProfileOption(String pCategoryCode, String pDisplaySequence, String pProfileName, WebDriver driver) {
		try {
			searchProfileCategory(pCategoryCode, driver);
			Log.info("===Click on create profile Option button===");
			CommonUtils.explicitWait(addProfileOptionButton, "Click", "", driver);
			CommonUtils.hold(8);
			addProfileOptionButton.click();
			Log.info("===Fill Display sequence field===");
			CommonUtils.hold(5);
			CommonUtils.explicitWait(displaySequenceTextField, "Visible", "", driver);
			CommonUtils.hold(3);
			displaySequenceTextField.sendKeys(pDisplaySequence);
			Log.info("===Enter profile name value===");
			CommonUtils.explicitWait(catProfileNameInputField, "Visible", "", driver);
			// ProfilePage.catProfileNameInputField.sendKeys(pProfileName);
			// ProfilePage.saveProfileOption.click();
			// CommonUtils.hold(5);
			profileName.click();
			CommonUtils.hold(5);
			profileSearch.click();
			CommonUtils.hold(5);
			profileDisplayNameTextField.sendKeys(pProfileName);
			searchOptoinButton.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//*[contains(text(),'" + pProfileName + "')]")),
		//	CommonUtils.explicitWait(driver.findElement(By.xpath("//span[contains(text(),'" + pProfileName + "')]")),
					CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
			CommonUtils.hold(5);
			driver.findElement(By.xpath("//*[contains(text(),'" + pProfileName + "')]")).click();

		//	driver.findElement(By.xpath("//span[contains(text(),'" + pProfileName + "')]")).click();
			CommonUtils.hold(5);
			// ProfilePage.profileNameDropdown.sendKeys(pProfileName);
			okButton.click();
			CommonUtils.hold(5);
			saveprofileButton.click();
			CommonUtils.hold(5);
			// CommonUtils.ExplicitWait(ProfilePage.saveprofileButton, "Click",
			// "");
			// WebElement ele = driver
			// .findElement(By.xpath("//*[@id='pt1:r1:0:rt:1:r2:0:dynamicRegion1:0:pt1:AP1:APscl']"));
			// JavascriptExecutor executor = (JavascriptExecutor) driver;
			// executor.executeScript("arguments[0].click();", ele);
			// Log.info("===Adding profile options successful===");
		} catch (Exception e) {
			Log.info("===Exception in addProfileOption...()===");
			e.printStackTrace();
		}

	}

	/*
	 * Method to update Profile category
	 * 
	 */

	public String updateProfileCategories(String pCategoryCode, String pCategoryNameUpdate, WebDriver driver) {
		try {
			CommonUtils.hold(2);
			Log.info("===search for the desired category===");
			CommonUtils.explicitWait(categoryTextField, "Visible", "", driver);
			categoryTextField.clear();
			Log.info("===Enter the category code===");
			categoryTextField.sendKeys(pCategoryCode);
			Log.info("===click on the search button===");
			searchButton.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(editCategoryButton, "Click", "", driver);
			Log.info("===Click on edit button===");
			editCategoryButton.click();
			CommonUtils.explicitWait(categoryNameTextField, "Visible", "", driver);
			categoryNameTextField.clear();
			categoryNameTextField.sendKeys(pCategoryNameUpdate);
			Log.info("===Updated category field name===");
			CommonUtils.hold(2);
			Log.info("===save profile option===");
			updatedSaveButton.click();
			Log.info("===updation of profile option successful===");
		} catch (Exception e) {
			Log.info("===Exception in updateProfileCategories...()===");
			e.printStackTrace();
		}
		return pCategoryNameUpdate;

	}
	/*
	 * Method to verify category updation
	 * 
	 */

	public void verifyCategoryUpdation(String pCategoryCode, String pCategoryName, WebDriver driver) {
		try {
			CommonUtils.hold(2);
			Log.info("===Search for the desired category===");
			CommonUtils.explicitWait(categoryTextField, "Visible", "", driver);
			categoryTextField.clear();
			categoryTextField.sendKeys(pCategoryCode);
			searchButton.click();
			CommonUtils.hold(5);
			Log.info("===Click on the edit button===");
			CommonUtils.explicitWait(editCategoryButton, "Click", "", driver);
			editCategoryButton.click();
			CommonUtils.hold(2);
			Log.info("===Verify the updated value of category name===");
			CommonUtils.explicitWait(categoryNameTextField, "Visible", "", driver);
			String categoryProfileName = categoryNameTextField.getAttribute("value");
			Log.info("===Category Name received is" + categoryProfileName + "===");
			Assert.assertEquals(pCategoryName, categoryProfileName);
			Log.info("===Category Name value Verification successful===");
		} catch (Exception e) {
			Log.info("===Exception in verifyCategoryUpdation...()===");
			e.printStackTrace();
		}

	}

	/*
	 * Method to read category profile values
	 * 
	 */
	public void readCategoryProfileValues(String pCategoryCode, WebDriver driver) {
		try {

			Log.info("===Search for the desired category===");
			CommonUtils.explicitWait(categoryTextField, "Visible", "", driver);
			categoryTextField.clear();
			categoryTextField.sendKeys(pCategoryCode);
			searchButton.click();
			CommonUtils.hold(5);
			String profile_category = profileCategoryTextField.getText();
			if ((profile_category != null) && (!profile_category.isEmpty())) {
				Log.info("===Profile Category value received is" + profile_category + "===");
				System.out.println("Profile Category value received is" + profile_category);
				Log.info("Reading of profile category value successful");
			} else {
				Log.info("Could not fetch profile_category value or the value fetched is empty");
			}

			String profile_displaySequence = profileDisplaySequenceTextField.getAttribute("value");
			if ((profile_displaySequence != null) && (!profile_displaySequence.isEmpty())) {
				Log.info("display sequence value received is" + profile_displaySequence);
				System.out.println("display sequence value received is" + profile_displaySequence);
				Log.info("Reading of profile category value successful");
			} else {
				Log.info("Could not fetch profile_displaySequence value or the value fetched is empty");
			}

		} catch (Exception e) {
			Log.info("===Exception in readCategoryProfileValues...()===");
			e.printStackTrace();
		}

	}
	/*
	 * Method to delete profile options
	 * 
	 */

	public void deleteProfileOptions(String pCategoryCode, String profileOption, WebDriver driver) {
		try {
			Log.info("===Search for the desired category===");
			searchProfileCategory(pCategoryCode, driver);
			CommonUtils.explicitWait(driver.findElement(By.xpath("//span[contains(text(),'" + profileOption + "')]")),
					CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
			driver.findElement(By.xpath("//span[contains(text(),'" + profileOption + "')]")).click();
			CommonUtils.hold(4);
			profileOptionDeleteButton.click();
			CommonUtils.hold(5);
			saveprofileButton.click();
			CommonUtils.hold(5);
			searchProfileCategory(pCategoryCode, driver);

			try {

				CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(text(),'No data to display.')]")),
						CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
				Assert.assertTrue(
						driver.findElement(By.xpath("//div[contains(text(),'No data to display.')]")).isDisplayed(),
						"Deleted profile category option " + profileOption);
			} catch (Exception e) {
				e.getMessage();
				Assert.assertFalse(false, "profile category option delete faield " + profileOption);

			}
		} catch (Exception e) {
			Log.info("===Exception in deleteProfileOptions...()===");
			e.printStackTrace();
		}

	}

	public void searchAdminProfile(String adminProfile, WebDriver driver) throws Exception {
		profileoptioncode.click();
		profileoptioncode.clear();
		profileoptioncode.sendKeys(adminProfile);
		search.click();
		CommonUtils.hold(5);
	}

	public void addProfileOptionValues(String adminProfile, String level, String userName, String userValue,WebDriver driver) throws Exception {
		searchAdminProfile(adminProfile, driver);
		create.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(profilelevel, "Click", "", driver);
		CommonUtils.selectDropDownElement(profilelevel, level);
		CommonUtils.hold(3);
		if(!userName.equalsIgnoreCase("")) {
			adminUserNameInputField.sendKeys(userName);
			CommonUtils.selectDropDownElement(adminUserNameInputFieldDD, userValue);			
		}
		saveAdminBtn.click();
		CommonUtils.hold(5);
	}

	public void deleteAdminProfile(String adminProfile, String value, WebDriver driver) throws Exception {
		searchAdminProfile(adminProfile, driver);
		qbeSearch.clear();
		qbeSearch1.clear();
		
		if(value.equalsIgnoreCase("site")){
			qbeSearch1.sendKeys(value);
			qbeSearch1.sendKeys(Keys.ENTER);
		}
		else{		
			qbeSearch.sendKeys(value);
			qbeSearch.sendKeys(Keys.ENTER);
		}
		CommonUtils.hold(3);
		CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + value + "']")),"Click", "", driver);
		driver.findElement(By.xpath("//span[text()='" + value + "']")).click();
		delete.click();
		CommonUtils.hold(5);
		saveAdminBtn.click();
		CommonUtils.hold(5);

		try {

			CommonUtils.explicitWait(driver.findElement(By.xpath("//div[contains(text(),'No data to display.')]")),
					CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
			Assert.assertTrue(
					driver.findElement(By.xpath("//div[contains(text(),'No data to display.')]")).isDisplayed(),
					"Deleted AdminProfile value " + value);
		} catch (Exception e) {
			e.getMessage();
			Assert.assertFalse(false, "AdminProfile delete faield " + value);

		}

	}

	public void verifyStatus3(String adminProfile, String value, WebDriver driver) throws Exception {
		searchAdminProfile(adminProfile, driver);

		if (!value.isEmpty()) {
			try {
				System.out.println("Not empty. ......");
				CommonUtils.explicitWait(qbeSearch, CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
				qbeSearch.clear();
				qbeSearch.sendKeys(value);
				qbeSearch.sendKeys(Keys.ENTER);
				CommonUtils.hold(3);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + value + "']")),"Click", "", driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + value + "']")).isDisplayed());
				qbeSearch.clear();
				qbeSearch.sendKeys(Keys.ENTER);
			} catch (Exception e) {
				Assert.fail();
			}
		} else {
			try {
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + adminProfile + "']")),
						CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + adminProfile + "']")).isDisplayed());
			} catch (Exception e) {
				Assert.fail();
			}
		}

	}
	
	public void verifyStatus4(String adminProfile, String profileLevel, WebDriver driver) throws Exception {
		searchAdminProfile(adminProfile, driver);

		if (!profileLevel.isEmpty()) {
			try {
				System.out.println("Not empty. ......");
				CommonUtils.explicitWait(qbeSearch, CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
				qbeSearch1.clear();
				qbeSearch1.sendKeys(profileLevel);
				qbeSearch1.sendKeys(Keys.ENTER);
				CommonUtils.hold(3);
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + profileLevel + "']")),"Click", "", driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + profileLevel + "']")).isDisplayed());
				qbeSearch1.clear();
				qbeSearch1.sendKeys(Keys.ENTER);
			} catch (Exception e) {
				Assert.fail();
			}
		} else {
			try {
				CommonUtils.explicitWait(driver.findElement(By.xpath("//span[text()='" + adminProfile + "']")),
						CommonUtils.ExplicitWaitCalls.Visible.toString(), "", driver);
				Assert.assertTrue(driver.findElement(By.xpath("//span[text()='" + adminProfile + "']")).isDisplayed());
			} catch (Exception e) {
				Assert.fail();
			}
		}

	}

}
