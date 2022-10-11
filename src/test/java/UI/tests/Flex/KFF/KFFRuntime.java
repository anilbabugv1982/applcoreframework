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
import pageDefinitions.UI.oracle.applcore.qa.Kff.KFFRuntimeAssets;
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

public class KFFRuntime extends GetDriverInstance {
	private WebDriver kffRuntimeTestsDriver;
	public static String timestamp = "";
	String defaultAccountValue = "AUTO-TRUCK";
	static String dynamicAccountValue;
	public static int totalSegmentCount;

	private ApplicationLogin loginInstance;
	private NavigationTaskFlows navigationTaskFlowsInstance;
	private NavigatorMenuElements navigatorMenuElementsInstance;
	private ManageKFFSetup manageKFFSetupInstance;
	private KFFRuntimeAssets kffRuntimeAssetsInstance;

	@Parameters({ "user_runtime", "pwd" })
	@BeforeClass
	public void signIn(String user, String pwd) throws Exception {
		kffRuntimeTestsDriver = getDriverInstanceObject();
		timestamp = KeyFlexfieldTests.timestamp;
		loginInstance = new ApplicationLogin(kffRuntimeTestsDriver);
		kffRuntimeAssetsInstance = new KFFRuntimeAssets(kffRuntimeTestsDriver);
		navigationTaskFlowsInstance = new NavigationTaskFlows(kffRuntimeTestsDriver);
		loginInstance.login(user, pwd, kffRuntimeTestsDriver);
		CommonUtils.hold(10);
	}

	@Test(priority = 11, description = "NavigateToAssets")
	public void testcase01() throws Exception {
		System.out.println("Navigate to Assets");
		navigationTaskFlowsInstance.navigateToTask(kffRuntimeAssetsInstance.assetsIcon,kffRuntimeTestsDriver);
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		kffRuntimeAssetsInstance.waitForAssetPageTobeLoaded();
		KFFUtils.waitForElementToBeClickable(kffRuntimeAssetsInstance.tasksButton, kffRuntimeTestsDriver);
		Assert.assertTrue("Asset not loaded", kffRuntimeAssetsInstance.tasksButton.isDisplayed());
	}

	@Test(priority = 12, description = "Click on AddAsset", dependsOnMethods = { "testcase01" })
	public void testcase02() throws Exception {
		System.out.println("AddAssets");
		kffRuntimeAssetsInstance.tasksButton.click();
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		// wait.until(ExpectedConditions.elementToBeClickable(kffRuntimeAssetsInstance.createasset));
		KFFUtils.waitForElementToBeClickable(kffRuntimeAssetsInstance.addAssetLink, kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		kffRuntimeAssetsInstance.addAssetLink.click();
		CommonUtils.hold(10);
		kffRuntimeAssetsInstance.waitForAssetPageTobeLoaded();
		CommonUtils.hold(5);
	}

	
	@Test(priority = 13, description = "Verify Created segment presence for CAT#", dependsOnMethods = {"testcase02" })
	public void testcase03() throws InterruptedException {
		System.out.println("Verify created segment");
		System.out.println("segment name is: " + "segmentPrompt" + timestamp);
		CommonUtils.hold(10);
		KFFUtils.waitForElementToBeClickable(kffRuntimeAssetsInstance.categoryKFFIcon, kffRuntimeTestsDriver);
		System.out.println("Clicking on KFF icon");
		kffRuntimeAssetsInstance.categoryKFFIcon.click();
		KFFUtils.waitForElementToBeVisible(kffRuntimeAssetsInstance.categortyPopup, kffRuntimeTestsDriver);
		CommonUtils.hold(8);
		System.out.println("Verifying account popup opened");
		Assert.assertTrue("Category popup is not present",
				CommonUtils.isElementPresent(kffRuntimeAssetsInstance.categortyPopup));
		CommonUtils.hold(5);
		System.out.println("Verifying created segment presence");
		CommonUtils.scrollToElement(kffRuntimeAssetsInstance.verifyCreatedSegmentPrompt(timestamp), kffRuntimeTestsDriver);
		System.out.println("Created segment present: "
				+ CommonUtils.isElementPresent(kffRuntimeAssetsInstance.verifyCreatedSegmentPrompt(timestamp)));
		kffRuntimeAssetsInstance.cancelButton.click();
		KFFUtils.waitForElementNotVisible("//td/div[contains(text(),'Category')]", kffRuntimeTestsDriver);
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		CommonUtils.hold(5);
	}

	
	@Test(priority = 14, description = "KFFDynamicInsertion", dependsOnMethods = { "testcase03" })
	public void testcase04() throws Exception {
		System.out.println("Dynamically inserting concatnated value into the category field");
		KFFUtils.waitForElementToBeVisible(kffRuntimeAssetsInstance.addAssetPopup, kffRuntimeTestsDriver);
		kffRuntimeAssetsInstance.categoryInputText.sendKeys("AUTO-TRUCK");
		kffRuntimeAssetsInstance.categoryInputText.sendKeys(Keys.TAB);
		CommonUtils.waitForPageLoad(kffRuntimeTestsDriver);
		KFFUtils.waitForElementToBeVisible(kffRuntimeAssetsInstance.categortyPopup, kffRuntimeTestsDriver);
		CommonUtils.hold(10);
		if(kffRuntimeAssetsInstance.majorInputText.getAttribute("value").equalsIgnoreCase("AUTO") && kffRuntimeAssetsInstance.minorInputText.getAttribute("value").equalsIgnoreCase("TRUCK"))
			System.out.println("Tab out opens the category popup and shows the value correctly");
		else
			System.out.println("Tab out opens the category popup and but doesnt show the value correctly");
		kffRuntimeAssetsInstance.cancelButton.click();
	}

	

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
