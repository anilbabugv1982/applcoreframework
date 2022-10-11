package UI.tests.Flex.KFF;

import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Kff.KFFRuntimeJournals;
import pageDefinitions.UI.oracle.applcore.qa.Kff.KFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Kff.ManageKFFSetup;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class KFFRuntimeGL extends GetDriverInstance {
	private WebDriver kffRuntimeTestsDriver;
	public static String timestamp = "";
	String defaultAccountValue = "05-114-1160-1728-000";
	static String dynamicAccountValue;
	public static int totalSegmentCount;

	private ApplicationLogin loginInstance;
	private NavigationTaskFlows navigationTaskFlowsInstance;
	private NavigatorMenuElements navigatorMenuElementsInstance;
	private ManageKFFSetup manageKFFSetupInstance;
	private KFFRuntimeJournals kffRuntimeJournalsInstance;

	@Parameters({ "user_runtime", "pwd" })
	@BeforeClass
	public void signIn(String user, String pwd) throws Exception {
		kffRuntimeTestsDriver = getDriverInstanceObject();
		timestamp = KeyFlexfieldTests.timestamp;
		loginInstance = new ApplicationLogin(kffRuntimeTestsDriver);
		kffRuntimeJournalsInstance = new KFFRuntimeJournals(kffRuntimeTestsDriver);
		navigationTaskFlowsInstance = new NavigationTaskFlows(kffRuntimeTestsDriver);
		loginInstance.login(user, pwd, kffRuntimeTestsDriver);
		CommonUtils.hold(10);
	}

	@Test(priority = 1, description = "NavigateToJournals")
	public void testcase01() throws Exception {
		System.out.println("Navigate to Journals");
		navigationTaskFlowsInstance.navigateToTask(kffRuntimeJournalsInstance.journalsIcon,kffRuntimeTestsDriver);
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		String existingDAS = kffRuntimeJournalsInstance.existingDataAccessSet.getText();
		System.out.println("Existing DAS is :" + existingDAS);
		if (!existingDAS.equals("Vision Operations (USA)")) {
			kffRuntimeJournalsInstance.changeDataAccessSet.click();
			CommonUtils.hold(5);
			Select mydrpdwn = new Select(kffRuntimeJournalsInstance.selectDAS);
			mydrpdwn.selectByVisibleText("Vision Operations (USA)");
			CommonUtils.hold(5);
			kffRuntimeJournalsInstance.OKDASButton.click();
		}
//		CommonUtils.waitForPageLoad(driverInstance);
//		CommonUtils.hold(5);

		// wait.until(ExpectedConditions.elementToBeClickable(kffRuntimeJournalsInstance.tasksButton));

		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.tasksButton, kffRuntimeTestsDriver);
		Assert.assertTrue("Tasks not loaded", kffRuntimeJournalsInstance.tasksButton.isDisplayed());
	}

	@Test(priority = 2, description = "CreateJournals", dependsOnMethods = { "testcase01" })
	public void testcase02() throws Exception {
		System.out.println("CreateJournals");
		kffRuntimeJournalsInstance.tasksButton.click();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		// wait.until(ExpectedConditions.elementToBeClickable(kffRuntimeJournalsInstance.createJournal));
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.createJournal, kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		kffRuntimeJournalsInstance.createJournal.click();
		CommonUtils.hold(10);
		kffRuntimeJournalsInstance.waitForJournalPageTobeLoaded();
		kffRuntimeJournalsInstance.journalName.click();
		kffRuntimeJournalsInstance.journalName.sendKeys("journal" + timestamp);
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(5);
		kffRuntimeJournalsInstance.categorySelect.click();
		kffRuntimeJournalsInstance.categorySelect.sendKeys("Addition");
		CommonUtils.hold(5);
		kffRuntimeJournalsInstance.categorySelect.sendKeys(Keys.TAB);
		CommonUtils.hold(5);
	}

	@Test(priority = 3, description = "Delete Default Journal lines", dependsOnMethods = { "testcase02" })
	public void testcase03() throws Exception {
		CommonUtils.scrollToElement(kffRuntimeJournalsInstance.journalLinesHeader,kffRuntimeTestsDriver);
		kffRuntimeJournalsInstance.journalLinesHeader.click();
		CommonUtils.hold(5);
		System.out.println("Deleteing Journal lines");
		kffRuntimeJournalsInstance.deleteJournalLine.click();
		kffRuntimeJournalsInstance.waitForConfirmationPopupTobePresent();
		CommonUtils.hold(5);
		KFFUtils.waitForElementToBeVisible(kffRuntimeJournalsInstance.confirmationPopup, kffRuntimeTestsDriver);
		KFFUtils.waitForElementToBeVisible(kffRuntimeJournalsInstance.confirmDeleteJournalLine,kffRuntimeTestsDriver);
		if (kffRuntimeJournalsInstance.confirmationPopup.isDisplayed()) {
			kffRuntimeJournalsInstance.confirmDeleteJournalLine.click();
		}
		CommonUtils.hold(10);
		System.out.println("Deleted Journal line 1");
		kffRuntimeJournalsInstance.verifyPopupConfirmation();
		CommonUtils.hold(5);
		kffRuntimeJournalsInstance.deleteJournalLine.click();
		kffRuntimeJournalsInstance.waitForConfirmationPopupTobePresent();
		CommonUtils.hold(10);
		if (kffRuntimeJournalsInstance.confirmationPopup.isDisplayed()) {
			kffRuntimeJournalsInstance.confirmDeleteJournalLine.click();
		}
		CommonUtils.hold(10);
		System.out.println("Deleted Journal line 2");
		kffRuntimeJournalsInstance.verifyPopupConfirmation();
		CommonUtils.hold(5);
		kffRuntimeJournalsInstance.saveButton.click();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		System.out.println("Deleted Default Journal lines");
	}

	@Test(priority = 4, description = "Create new journal line and verify Created segment presence", dependsOnMethods = {
			"testcase03" })
	public void testcase04() throws InterruptedException {
		System.out.println("CreateJournalLine");
		System.out.println("segment name is: " + "segmentPrompt" + timestamp);
		CommonUtils.scrollToElement(kffRuntimeJournalsInstance.createJournalLine, kffRuntimeTestsDriver);
		kffRuntimeJournalsInstance.createJournalLine.click();
		CommonUtils.hold(10);
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.kffIcon, kffRuntimeTestsDriver);
		System.out.println("Clicking on KFF icon");
		kffRuntimeJournalsInstance.kffIcon.click();
		KFFUtils.waitForElementToBeVisible(kffRuntimeJournalsInstance.accountPopup, kffRuntimeTestsDriver);
		CommonUtils.hold(8);
		System.out.println("Verifying account popup opened");
		Assert.assertTrue("Account popup is not present",
				CommonUtils.isElementPresent(kffRuntimeJournalsInstance.accountPopup));
		CommonUtils.hold(5);
		System.out.println("Verifying created segment presence");
		CommonUtils.scrollToElement(kffRuntimeJournalsInstance.verifyCreatedSegmentPrompt(timestamp), kffRuntimeTestsDriver);
		System.out.println("Created segment present: "
				+ CommonUtils.isElementPresent(kffRuntimeJournalsInstance.verifyCreatedSegmentPrompt(timestamp)));
		kffRuntimeJournalsInstance.cancelButton.click();
		KFFUtils.waitForElementNotVisible("//td/div[contains(text(),'Account')]", kffRuntimeTestsDriver);
		CommonUtils.hold(5);
		kffRuntimeJournalsInstance.journalLinesHeader.click();
		CommonUtils.hold(5);
		System.out.println("Deleteing Journal line");
		kffRuntimeJournalsInstance.deleteJournalLine.click();
		kffRuntimeJournalsInstance.waitForConfirmationPopupTobePresent();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		if (kffRuntimeJournalsInstance.confirmationPopup.isDisplayed())
			kffRuntimeJournalsInstance.confirmDeleteJournalLine.click();
		CommonUtils.hold(10);
		kffRuntimeJournalsInstance.verifyPopupConfirmation();
		System.out.println("Deleted Journal line 1");
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(5);
	}

	@Test(priority = 5, description = "Provide values to all mandatory segments", dependsOnMethods = { "testcase04" })
	public void testcase05() throws Exception {
		System.out.println("CreateJournalLine");
		CommonUtils.explicitWait(kffRuntimeJournalsInstance.createJournalLine,
				CommonUtils.ExplicitWaitCalls.Click.toString(), "", kffRuntimeTestsDriver);
		kffRuntimeJournalsInstance.createJournalLine.click();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(5);
		CommonUtils.explicitWait(kffRuntimeJournalsInstance.kffIcon, CommonUtils.ExplicitWaitCalls.Click.toString(), "",
				kffRuntimeTestsDriver);
		System.out.println("Clicking on KFF icon");
		kffRuntimeJournalsInstance.kffIcon.click();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(5);
		System.out.println("Verifying account popup opened");
		KFFUtils.waitForElementToBeVisible(kffRuntimeJournalsInstance.accountPopup, kffRuntimeTestsDriver);
		Assert.assertTrue("Account popup is not present",
				CommonUtils.isElementPresent(kffRuntimeJournalsInstance.accountPopup));
		CommonUtils.explicitWait(kffRuntimeJournalsInstance.resetButtonLine1,
				CommonUtils.ExplicitWaitCalls.Click.toString(), "", kffRuntimeTestsDriver);
		kffRuntimeJournalsInstance.resetButtonLine1.click();
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.okResetButtonL1, kffRuntimeTestsDriver);
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		Assert.assertTrue("Warning dialog is not opening",
				CommonUtils.isElementPresent(kffRuntimeJournalsInstance.warningDialogBox));
		kffRuntimeJournalsInstance.okResetButtonL1.click();
		KFFUtils.waitForElementNotVisible("//button[@id='_FOpt1:_FOr1:0:_FOSritemNode_general_accounting_journals:0:MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:account_confirmResetDialog_ok']", kffRuntimeTestsDriver);
		KFFUtils.waitForElementToBeVisible(kffRuntimeJournalsInstance.companySegmentInput, kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		kffRuntimeJournalsInstance.companySegmentInput.click();
		kffRuntimeJournalsInstance.companySegmentInput.sendKeys("05");
		kffRuntimeJournalsInstance.companySegmentInput.sendKeys(Keys.TAB);
		CommonUtils.hold(5);
		kffRuntimeJournalsInstance.departmentSegmentInput.sendKeys("112");
		kffRuntimeJournalsInstance.departmentSegmentInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffRuntimeJournalsInstance.accountSegmentInput.sendKeys("1140");
		kffRuntimeJournalsInstance.accountSegmentInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffRuntimeJournalsInstance.subaccountSegmentInput.sendKeys("1200");
		kffRuntimeJournalsInstance.subaccountSegmentInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffRuntimeJournalsInstance.productSegmentInput.sendKeys("103");
		kffRuntimeJournalsInstance.productSegmentInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		CommonUtils.scrollToElement(kffRuntimeJournalsInstance.okButton, kffRuntimeTestsDriver);
		CommonUtils.hold(3);
		kffRuntimeJournalsInstance.okButton.click();
		CommonUtils.hold(10);
		KFFUtils.waitForElementNotVisible(
				"_FOpt1:_FOr1:0:_FOSritemNode_general_accounting_journals:0:MAnt2:1:pt1:ap1:jeLineAppTable:_ATp:t3:0:accountSEl",
				kffRuntimeTestsDriver);
		KFFUtils.waitForElementNotVisible("//td/div[contains(text(),'Account')]", kffRuntimeTestsDriver);

//		WebDriverWait wait = new WebDriverWait(DriverInstance.getDriverInstance(), 20);
//		wait.until(ExpectedConditions.invisibilityOf(kffRuntimeJournalsInstance.okButton));
		kffRuntimeJournalsInstance.debitAmount.sendKeys("50");
		kffRuntimeJournalsInstance.debitAmount.sendKeys(Keys.TAB);
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(8);
		System.out.println("Journal line 1 created providing values to default mandatory values");
	}

	@Test(priority = 6, description = "SearchAndSelectvalue", dependsOnMethods = { "testcase05" })
	public void testcase06() throws Exception {
		System.out.println("Search and Select account value Validation");
		WebDriverWait wait = new WebDriverWait(kffRuntimeTestsDriver, 60);
		KFFUtils.waitForElementToBeVisible(kffRuntimeJournalsInstance.createJournalLine, kffRuntimeTestsDriver);
		kffRuntimeJournalsInstance.createJournalLine.click();
		KFFUtils.waitForElementToBeVisible(kffRuntimeJournalsInstance.accountLineTwo, kffRuntimeTestsDriver);
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(5);
		kffRuntimeJournalsInstance.accountLineTwo.click();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.kffIcon, kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		kffRuntimeJournalsInstance.kffIcon.click();
		System.out.println("Verifying account popup opened");
		KFFUtils.waitForElementToBeVisible(kffRuntimeJournalsInstance.accountPopup, kffRuntimeTestsDriver);
		CommonUtils.hold(5);
		Assert.assertTrue("Account popup is not present",
				CommonUtils.isElementPresent(kffRuntimeJournalsInstance.accountPopup));
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.resetButtonLine2, kffRuntimeTestsDriver);
		kffRuntimeJournalsInstance.resetButtonLine2.click();
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.warningDialogBox, kffRuntimeTestsDriver);
		CommonUtils.hold(5);
		Assert.assertTrue("Warning dialog is not opening",
				CommonUtils.isElementPresent(kffRuntimeJournalsInstance.warningDialogBox));
		kffRuntimeJournalsInstance.okResetButtonL2.click();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.searchButtonAccountPopupL2, kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		
		//wait.until(ExpectedConditions.elementToBeClickable(kffRuntimeJournalsInstance.searchButtonAccountPopupL2));
		kffRuntimeJournalsInstance.searchButtonAccountPopupL2.click();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(5);
		CommonUtils.scrollToElement(kffRuntimeJournalsInstance.selectFirstRowL2, kffRuntimeTestsDriver);
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.selectFirstRowL2, kffRuntimeTestsDriver);
		kffRuntimeJournalsInstance.selectFirstRowL2.click();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		kffRuntimeJournalsInstance.okButtonAccountPopupL2.click();
//		wait.until(ExpectedConditions.invisibilityOf(kffRuntimeJournalsInstance.okButtonAccountPopupL2));
		KFFUtils.waitForElementNotVisible("//button[contains(@id,'accountSEl')]", kffRuntimeTestsDriver);
		KFFUtils.waitForElementNotVisible("//td/div[contains(text(),'Account')]", kffRuntimeTestsDriver);
		CommonUtils.hold(15);
		CommonUtils.scrollToElement(kffRuntimeJournalsInstance.creditAmount, kffRuntimeTestsDriver);
		kffRuntimeJournalsInstance.creditAmount.sendKeys("100");
		kffRuntimeJournalsInstance.creditAmount.sendKeys(Keys.TAB);
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		System.out.println("Journal line 2 created by search and select values");
	}

	@Test(priority = 7, description = "KFFDynamicInsertion", dependsOnMethods = { "testcase06" })
	public void testcase07() throws Exception {
		System.out.println("Dynamically inserting concatnated value into the account field");
		for (int i = 1; i <= totalSegmentCount - 5; i++) {
			defaultAccountValue = defaultAccountValue.concat("-");
		}
		dynamicAccountValue = defaultAccountValue;
		System.out.println("dynamicAccountValue is: " + dynamicAccountValue);
		CommonUtils.scrollToElement(kffRuntimeJournalsInstance.createJournalLine, kffRuntimeTestsDriver);
		kffRuntimeJournalsInstance.createJournalLine.click();
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.accountLineThree, kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		WebDriverWait wait = new WebDriverWait(kffRuntimeTestsDriver, 20);
		kffRuntimeJournalsInstance.accountLineThree.click();
		KFFUtils.waitForElementToBeVisible(kffRuntimeJournalsInstance.accountInput, kffRuntimeTestsDriver);
		CommonUtils.hold(8);
		CommonUtils.scrollToElement(kffRuntimeJournalsInstance.accountInput, kffRuntimeTestsDriver);
		kffRuntimeJournalsInstance.accountInput.sendKeys(dynamicAccountValue);
		kffRuntimeJournalsInstance.accountInput.sendKeys(Keys.TAB);
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(8);
		kffRuntimeJournalsInstance.okButtonAccountPopupL2.click();
		KFFUtils.waitForElementNotVisible("//td/div[contains(text(),'Account')]", kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		kffRuntimeJournalsInstance.debitAmount.sendKeys("50");
		kffRuntimeJournalsInstance.debitAmount.sendKeys(Keys.TAB);
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(15);
		System.out.println("Dynamic value successfully inserted into the account field");
		System.out.println("Journal line 3 created successfully");
		CommonUtils.scrollToElement(kffRuntimeJournalsInstance.saveButton, kffRuntimeTestsDriver);
//		kffRuntimeJournalsInstance.dropdown_Save.click();
//		CommonUtils.hold(5);
//		kffRuntimeJournalsInstance.saveAndClose.click();
//		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.tasksButton);
		kffRuntimeJournalsInstance.saveButton.click();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		System.out.println(
				"Journal created and saved with name: " + kffRuntimeJournalsInstance.journalName.getAttribute("value"));
		kffRuntimeJournalsInstance.cancelJButton1.click();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance.tasksButton, kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		if (kffRuntimeJournalsInstance.journalPage.isDisplayed())
			System.out.println("Journal saved successfully");
		else
			System.out.println("Journal not saved properly");
	}

	/*
	 * @Test(priority = 8, description = "verify created journal") public void
	 * testcase08() throws Exception {
	 * System.out.println("Created journal verification");
	 * CommonUtils.waitForPageLoad(driverInstance); CommonUtils.hold(2);
	 * kffRuntimeJournalsInstance.tasksButton.click();
	 * CommonUtils.waitForPageLoad(driverInstance); CommonUtils.hold(8);
	 * kffRuntimeJournalsInstance.manageJournal.click(); CommonUtils.hold(5);
	 * CommonUtils.waitForPageLoad(driverInstance);
	 * kffRuntimeJournalsInstance.journalInput.sendKeys("journal" + timestamp);
	 * CommonUtils.waitForPageLoad(driverInstance); CommonUtils.hold(2);
	 * kffRuntimeJournalsInstance.searchJournal.click();
	 * CommonUtils.waitForPageLoad(driverInstance); CommonUtils.hold(5);
	 * Assert.assertTrue("Created journal is not present",
	 * CommonUtils.isElementPresent(kffRuntimeJournalsInstance.verifyCreatedJournal(
	 * timestamp))); System.out.println("Created journal present: "
	 * +CommonUtils.isElementPresent(kffRuntimeJournalsInstance.verifyCreatedJournal
	 * (timestamp))); }
	 * 
	 * @Test(priority = 9, description = "Hover Over Validation") public void
	 * testcase09() throws Exception { System.out.println("HoverOverValidation");
	 * Actions builder = new Actions (DriverInstance.getDriverInstance());
	 * builder.clickAndHold().moveToElement(kffRuntimeJournalsInstance.kffIcon);
	 * builder.moveToElement(kffRuntimeJournalsInstance.kffIcon).build().perform();
	 * CommonUtils.waitForPageLoad(driverInstance);
	 * 
	 * }
	 */

	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {

		try {
			loginInstance.logout(kffRuntimeTestsDriver);

		} catch (Exception ex) {
			loginInstance.launchURL(kffRuntimeTestsDriver);
			CommonUtils.hold(2);
			loginInstance.logout(kffRuntimeTestsDriver);
		} finally {
			kffRuntimeTestsDriver.quit();

		}

	}
}
