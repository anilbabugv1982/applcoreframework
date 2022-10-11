package UI.tests.Flex.dff;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;

public class DffDynamicContextFetch extends GetDriverInstance {

	JavascriptExecutor js;
	WebDriver dffDriver = null;
	NavigationTaskFlows navTF = null;
	DFFUtils dfUtils = null;
	ApplicationLogin login = null;

	@Parameters({ "person_user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		dffDriver = getDriverInstanceObject();
		navTF = new NavigationTaskFlows(dffDriver);
		dfUtils = new DFFUtils(dffDriver);
		login = new ApplicationLogin(dffDriver);
		js = (JavascriptExecutor) dffDriver;
		login.login(user, passWord, dffDriver);
		CommonUtils.hold(8);
	}

	@Test(description = "SetContextCode dynamically for Birth Certificate")
	public void testcase01() throws Exception {

		try {
			CommonUtils.explicitWait(dfUtils.meLink, "Click", "", dffDriver);
			dfUtils.meLink.click();
			CommonUtils.explicitWait(dfUtils.personalInfoLink, "Click", "", dffDriver);
			CommonUtils.hold(3);
			dfUtils.personalInfoLink.click();
			CommonUtils.hold(10);
			CommonUtils.explicitWait(dfUtils.documentRecordsLink, "Click", "", dffDriver);
			CommonUtils.hold(3);
			dfUtils.documentRecordsLink.click();
			CommonUtils.explicitWait(dfUtils.addButton, "Click", "", dffDriver);
			CommonUtils.hold(3);
			dfUtils.addButton.click();
			CommonUtils.explicitWait(dfUtils.searchInputField, "Click", "", dffDriver);
			CommonUtils.hold(3);
			dfUtils.searchInputField.clear();
			dfUtils.searchInputField.sendKeys("Birth");
			CommonUtils.hold(10);
			CommonUtils.explicitWait(dfUtils.birthRecord, "Click", "", dffDriver);
			CommonUtils.hold(5);
			dfUtils.birthRecord.click();
			CommonUtils.hold(5);
			CommonUtils.explicitWait(dfUtils.contextBirthCode, "Click", "", dffDriver);
			CommonUtils.hold(3);

			Assert.assertTrue(dfUtils.contextBirthCode.isDisplayed() && dfUtils.birthLabel.isDisplayed());

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	@Test(description = "SetContextCode dynamically for Drivers License Certificate")
	public void testcase02() throws Exception {

		try {
			CommonUtils.explicitWait(dfUtils.searchInputField, "Click", "", dffDriver);
			dfUtils.searchInputField.clear();
			dfUtils.searchInputField.sendKeys("Drivers License");
			CommonUtils.hold(10);
			CommonUtils.explicitWait(dfUtils.licenseRecord, "Click", "", dffDriver);
			CommonUtils.hold(3);
			dfUtils.licenseRecord.click();
			CommonUtils.hold(3);
			CommonUtils.explicitWait(dfUtils.contextLicenseCode, "Click", "", dffDriver);
			CommonUtils.hold(3);
			Assert.assertTrue(dfUtils.contextLicenseCode.isDisplayed() && dfUtils.licenseLabel.isDisplayed());

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	@AfterClass(alwaysRun = true)
	public void quit() {
		dffDriver.quit();
	}

}
