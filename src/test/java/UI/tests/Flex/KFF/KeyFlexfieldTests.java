package UI.tests.Flex.KFF;

import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Kff.ManageKFFSetup;

import java.sql.SQLException;
import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class KeyFlexfieldTests extends GetDriverInstance {
	private WebDriver kffSetupTestsDriver;
	public static String taskFlow = "Manage Key Flexfields";
	public static String timestamp = "";
	public static String numberOfexistingSegments;
	public static String flexfieldCode = "CAT#";
	public static String segmentName;
	public int segmentCount;
	public static boolean deployStatus = true;
	Boolean result = false;

	private ApplicationLogin loginInstance;
	private NavigationTaskFlows navigationTaskFlowsInstance;
	private NavigatorMenuElements navigatorMenuElementsInstance;
	private ManageKFFSetup manageKFFSetupInstance;

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void signIn(String uname, String pwd) throws Exception {
		// KFFUtils.deleteExistingDataFromDB();
		kffSetupTestsDriver = getDriverInstanceObject();
		timestamp = CommonUtils.uniqueId();
		segmentName = "segmentPrompt" + timestamp;
		loginInstance = new ApplicationLogin(kffSetupTestsDriver);
		navigationTaskFlowsInstance = new NavigationTaskFlows(kffSetupTestsDriver);
		navigatorMenuElementsInstance = new NavigatorMenuElements(kffSetupTestsDriver);
		manageKFFSetupInstance = new ManageKFFSetup(kffSetupTestsDriver);
		loginInstance.login(uname, pwd, kffSetupTestsDriver);
		CommonUtils.hold(10);
	}

	@Test(priority = 1, description = "NavigateToManageKFFTask")
	public void testcase01() throws Exception {
		System.out.println("Navigating to Manage Key Flexfields task");
		navigationTaskFlowsInstance.navigateToTask(navigatorMenuElementsInstance.SetupAndMaintenance,kffSetupTestsDriver);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(5);
		navigationTaskFlowsInstance.navigateToAOLTaskFlows(taskFlow,kffSetupTestsDriver);
		manageKFFSetupInstance.waitForManageKFFSetupPageLoaded();
		System.out.println("*****NavigateToManageKFF done*****");
	}

	@Test(priority = 2, description = "NavigateToManageKFFPage", dependsOnMethods = { "testcase01" })
	public void testcase02() throws Exception {
		System.out.println("Navigating to Manage Key Flexfields page");
		CommonUtils.hold(3);
		manageKFFSetupInstance.keyFlexfieldCode.click();
		manageKFFSetupInstance.keyFlexfieldCode.sendKeys(flexfieldCode);
		manageKFFSetupInstance.searchFlexfieldButton.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.waitForCATFlexFieldToDisplay();
		CommonUtils.hold(8);
		System.out.println("*****NavigateToManageKFFPage done*****");
	}

	@Test(priority = 3, description = "CreateLabel for CAT#", dependsOnMethods = { "testcase02" })
	public void testcase03() {
		System.out.println("Creating a new label for CAT#");
		WebDriverWait wait = new WebDriverWait(kffSetupTestsDriver, 30);
		manageKFFSetupInstance.actionsButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.manageSegmentlabels));
		CommonUtils.hold(2);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.manageSegmentlabels.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(10);
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.createIcon));
		manageKFFSetupInstance.columnFilter_SegmentLabelCode.click();
		manageKFFSetupInstance.createIcon.click();
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.segmentLabelCode));
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.labelName));
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.description));
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.segmentLabelCode));
		manageKFFSetupInstance.segmentLabelCode.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.segmentLabelCode.sendKeys("labelcode" + timestamp);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.labelName.sendKeys("labelName" + timestamp);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.description.sendKeys("labeldesc" + timestamp);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.biObjectName.sendKeys("labelBIName" + timestamp);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.saveAndCloseButton.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.button_Done));
		Assert.assertTrue(manageKFFSetupInstance.button_Done.isDisplayed());
		System.out.println("*****Label for CAT# Created successfully*****");

	}

	@Test(priority = 4, description = "SearchStructure for CAT#", dependsOnMethods = { "testcase03" })
	public void testcase04() {
		System.out.println("Searching structure 'CATEGORY_FLEXFIELD' ");
		WebDriverWait wait = new WebDriverWait(kffSetupTestsDriver, 30);
		manageKFFSetupInstance.button_ManageStructures.click();
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.searchStructureButton));
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.structureCodeField));
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.structureCodeField.sendKeys("CATEGORY_FLEXFIELD");
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.searchStructureButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.editIcon));
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.editIcon.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.createIcon));
		CommonUtils.hold(5);
		numberOfexistingSegments = manageKFFSetupInstance.countSegments.getAttribute("_rowcount");
		System.out.println("***********Number of existing segments*************: " + numberOfexistingSegments);
		segmentCount = Integer.parseInt(numberOfexistingSegments) + 1;
		manageKFFSetupInstance.createIcon.click();
		manageKFFSetupInstance.waitForSegmentPageToLoad();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(5);
		System.out.println("*****Searched structure successfully*****");
	}

	@Test(priority = 5, description = "CreateKFFSegment for CAT#", dependsOnMethods = { "testcase04" })
	public void testcase05() {
		System.out.println("Creating a new segment for CAT#");
		manageKFFSetupInstance.segmentCode.click();
		manageKFFSetupInstance.segmentCode.sendKeys("segmentCode" + timestamp);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.segmentName.sendKeys("segmentName" + timestamp);
		manageKFFSetupInstance.segmentDesc.sendKeys("segmentDesc" + timestamp);
		manageKFFSetupInstance.sequenceNumber.sendKeys(Integer.toString(segmentCount));
		manageKFFSetupInstance.prompt.sendKeys("segmentPrompt" + timestamp);
		manageKFFSetupInstance.shortPrompt.sendKeys("shortPrompt" + timestamp);
		manageKFFSetupInstance.displayWidth.sendKeys("10");
		CommonUtils.hold(10);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.columnNameField.sendKeys("SEGMENT" + segmentCount);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.valueSetCode.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.valueSetCode.sendKeys("Operations Company");
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.findLabel(timestamp).click();
		CommonUtils.hold(5);
		manageKFFSetupInstance.moveLabelRight.click();
		WebDriverWait wait = new WebDriverWait(kffSetupTestsDriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.saveAndCloseButton));
		manageKFFSetupInstance.saveAndCloseButton.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.waitForEditKeyFlexFieldStructurePage();
		CommonUtils.hold(5);
		// manageKFFSetupInstance.editKFFStructurePage.click();
		manageKFFSetupInstance.input_structureName.click();
		manageKFFSetupInstance.saveAndCloseButton.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.doneButtonKFFSTR));
		CommonUtils.hold(5);
		manageKFFSetupInstance.doneButtonKFFSTR.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.waitForManageKFFSetupPageLoaded();
		manageKFFSetupInstance.waitForCATFlexFieldToDisplay();
		System.out.println("*****Created segment successfully*****");
	}

	@Test(priority = 6, description = "EnableAlias for CAT#", dependsOnMethods = { "testcase05" })
	public void testcase06() {
		System.out.println("Going to enable alias checkbox for CAT#... ");
		CommonUtils.hold(5);
		manageKFFSetupInstance.button_ManageStructureInstances.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		WebDriverWait wait = new WebDriverWait(kffSetupTestsDriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.searchKFFSTRINS));
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.nameKFFSTRINS));
		CommonUtils.hold(5);
		manageKFFSetupInstance.nameKFFSTRINS.sendKeys("Vision Category Flexfield");
		CommonUtils.hold(5);
		manageKFFSetupInstance.searchKFFSTRINS.click();
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.editIcon));
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.editIcon.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.shorthandAliasEnabledCheckbox2));
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.saveAndCloseButton));
		CommonUtils.hold(5);
		if (manageKFFSetupInstance.shorthandAliasEnabledCheckbox.getAttribute("checked") == null) {
			System.out.println("Enabling alias checkbox... ");
			manageKFFSetupInstance.shorthandAliasEnabledCheckbox2.click();
		} else {
			System.out.println("The alias checkbox is already enabled");
		}
		CommonUtils.hold(2);
		manageKFFSetupInstance.saveAndCloseButton.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.doneButtonKFFSTRINS.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		System.out.println("Alias checkbox enabling done");
		manageKFFSetupInstance.waitForCATFlexFieldToDisplay();
	}

	@Test(priority = 7, description = "DeployFlexfield CAT#", dependsOnMethods = { "testcase06" })
	public void testcase07() {
		System.out.println("Preparing to deploy CAT# KFF flexfield");
		manageKFFSetupInstance.button_DeployFlexField.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(5);
		if (CommonUtils.isElementPresent(manageKFFSetupInstance.VerifyDeploymentInProgress)) {
			System.out.println("Key Flexfield " + flexfieldCode + " Deployment is in Progress!");
		}
		CommonUtils.hold(3);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(kffSetupTestsDriver)
				.withTimeout(Duration.ofMinutes(40)).pollingEvery(Duration.ofMillis(35000))
				.ignoring(StaleElementReferenceException.class);

		Boolean element = wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver t) {
				JavascriptExecutor js = (JavascriptExecutor) kffSetupTestsDriver;
				manageKFFSetupInstance.deploymentLog.click();
				CommonUtils.hold(3);
				return manageKFFSetupInstance.VerifyOKButtonEnable.isEnabled();
			}

		});

		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		if (CommonUtils.isElementPresent(manageKFFSetupInstance.VerifyDeploymentStatusSuccess))
			System.out.println("Key Flexfield " + flexfieldCode + " Deployed Successfully!");
		else if (CommonUtils.isElementPresent(manageKFFSetupInstance.VerifyDeploymentStatusError)) {
			System.out.println("Key Flexfield " + flexfieldCode + " failed due to error in Deployment!");
			deployStatus = false;
		}
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(10);
		if (manageKFFSetupInstance.VerifyOKButtonEnable.isDisplayed())
			manageKFFSetupInstance.VerifyOKButtonEnable.click();
		Assert.assertTrue(deployStatus);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
	}

	
	//GL#
	@Test(priority = 8, description = "NavigateToManageKFFTask")
	public void testcase08() throws Exception {
		System.out.println("Navigating to Manage Key Flexfields task");
		navigationTaskFlowsInstance.navigateToTask(navigatorMenuElementsInstance.SetupAndMaintenance,kffSetupTestsDriver);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(5);
		navigationTaskFlowsInstance.navigateToAOLTaskFlows(taskFlow,kffSetupTestsDriver);
		manageKFFSetupInstance.waitForManageKFFSetupPageLoaded();
		System.out.println("*****NavigateToManageKFF done*****");
	}
	
	@Test(priority = 9, description = "NavigateToManageKFFPage", dependsOnMethods = { "testcase08" })
	public void testcase09() throws Exception {
		flexfieldCode="GL#";
		System.out.println("Navigating to Manage Key Flexfields page");
		CommonUtils.hold(3);
		manageKFFSetupInstance.keyFlexfieldCode.click();
		manageKFFSetupInstance.keyFlexfieldCode.sendKeys(flexfieldCode);
		manageKFFSetupInstance.searchFlexfieldButton.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.waitForFlexFieldToDisplay();
		CommonUtils.hold(8);
		System.out.println("*****NavigateToManageKFFPage done*****");
	}

	@Test(priority = 10, description = "CreateLabel for GL#", dependsOnMethods = { "testcase09" })
	public void testcase10() {
		System.out.println("Creating a new label for GL#");
		WebDriverWait wait = new WebDriverWait(kffSetupTestsDriver, 30);
		manageKFFSetupInstance.actionsButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.manageSegmentlabels));
		CommonUtils.hold(2);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.manageSegmentlabels.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(10);
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.createIcon));
		manageKFFSetupInstance.columnFilter_SegmentLabelCode.click();
		manageKFFSetupInstance.createIcon.click();
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.segmentLabelCode));
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.labelName));
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.description));
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.segmentLabelCode));
		manageKFFSetupInstance.segmentLabelCode.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.segmentLabelCode.sendKeys("labelcode" + timestamp);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.labelName.sendKeys("labelName" + timestamp);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.description.sendKeys("labeldesc" + timestamp);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.biObjectName.sendKeys("labelBIName" + timestamp);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.saveAndCloseButton.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.button_Done));
		Assert.assertTrue(manageKFFSetupInstance.button_Done.isDisplayed());
		System.out.println("*****Label for GL# Created successfully*****");

	}
	
	@Test(priority = 11, description = "EnableAlias for GL#", dependsOnMethods = { "testcase10" })
	public void testcase11() {
		System.out.println("Going to enable alias checkbox for GL#... ");
		CommonUtils.hold(5);
		manageKFFSetupInstance.button_ManageStructureInstances.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		WebDriverWait wait = new WebDriverWait(kffSetupTestsDriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.searchKFFSTRINS));
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.nameKFFSTRINS));
		CommonUtils.hold(5);
		manageKFFSetupInstance.nameKFFSTRINS.sendKeys("Operations Accounting Flex");
		CommonUtils.hold(5);
		manageKFFSetupInstance.searchKFFSTRINS.click();
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.editIcon));
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		manageKFFSetupInstance.editIcon.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		wait.until(ExpectedConditions.visibilityOf(manageKFFSetupInstance.shorthandAliasEnabledCheckbox2));
		wait.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.saveAndCloseButton));
		CommonUtils.hold(5);
		if (manageKFFSetupInstance.shorthandAliasEnabledCheckbox.getAttribute("checked") == null) {
			System.out.println("Enabling alias checkbox... ");
			manageKFFSetupInstance.shorthandAliasEnabledCheckbox2.click();
		} else {
			System.out.println("The alias checkbox is already enabled");
		}
		CommonUtils.hold(2);
		manageKFFSetupInstance.saveAndCloseButton.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.doneButtonKFFSTRINS.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		System.out.println("Alias checkbox enabling done");
		manageKFFSetupInstance.waitForFlexFieldToDisplay();
	}
	
	@Test(priority = 12, description = "DeployFlexfield GL#", dependsOnMethods = { "testcase11" })
	public void testcase12() {
		System.out.println("Preparing to deploy GL# KFF flexfield");
		manageKFFSetupInstance.button_DeployFlexField.click();
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(5);
		if (CommonUtils.isElementPresent(manageKFFSetupInstance.VerifyDeploymentInProgress)) {
			System.out.println("Key Flexfield " + flexfieldCode + " Deployment is in Progress!");
		}
		CommonUtils.hold(3);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(kffSetupTestsDriver)
				.withTimeout(Duration.ofMinutes(40)).pollingEvery(Duration.ofMillis(35000))
				.ignoring(StaleElementReferenceException.class);

		Boolean element = wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver t) {
				JavascriptExecutor js = (JavascriptExecutor) kffSetupTestsDriver;
				manageKFFSetupInstance.deploymentLog.click();
				CommonUtils.hold(3);
				return manageKFFSetupInstance.VerifyOKButtonEnable.isEnabled();
			}

		});

		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		if (CommonUtils.isElementPresent(manageKFFSetupInstance.VerifyDeploymentStatusSuccess))
			System.out.println("Key Flexfield " + flexfieldCode + " Deployed Successfully!");
		else if (CommonUtils.isElementPresent(manageKFFSetupInstance.VerifyDeploymentStatusError)) {
			System.out.println("Key Flexfield " + flexfieldCode + " failed due to error in Deployment!");
			deployStatus = false;
		}
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
		CommonUtils.hold(10);
		if (manageKFFSetupInstance.VerifyOKButtonEnable.isDisplayed())
			manageKFFSetupInstance.VerifyOKButtonEnable.click();
		Assert.assertTrue(deployStatus);
		CommonUtils.waitForPageLoad(kffSetupTestsDriver);
	}

	
	
	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException, SQLException {

		try {
			loginInstance.logout(kffSetupTestsDriver);
			

		} catch (Exception ex) {
			loginInstance.launchURL(kffSetupTestsDriver);
			CommonUtils.hold(2);
			loginInstance.logout(kffSetupTestsDriver);
		}finally {
			kffSetupTestsDriver.quit();
			
		}
	}

}
