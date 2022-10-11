package UI.tests.Flex.KFF;

import java.time.Duration;


import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
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
import pageDefinitions.UI.oracle.applcore.qa.FsmExportImport.FSMExpImpWrapper;
import pageDefinitions.UI.oracle.applcore.qa.Kff.ManageKFFSetup;


public class KFFExportImport extends GetDriverInstance{
	
	private WebDriver kffExportImportDriver;
	private ApplicationLogin loginInstance;
	private NavigationTaskFlows navigationTaskFlowsInstance;
	private NavigatorMenuElements navigatorMenuElementsInstance;
	private ManageKFFSetup manageKFFSetupInstance;
	private FSMExpImpWrapper fsmExpImpWrapperInstance;
	
	String timestamp;
	String ipName;
	public static String numberOfexistingSegments;
	public static String flexfieldCode = "CAT#";
	public int segmentCount;
	public static boolean deployStatus = true;
	public static String initialSegmentPrompt, segmentPrompt;
	

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String password) throws Exception {
		kffExportImportDriver = getDriverInstanceObject();
		loginInstance = new ApplicationLogin(kffExportImportDriver);
		navigationTaskFlowsInstance = new NavigationTaskFlows(kffExportImportDriver);
		navigatorMenuElementsInstance = new NavigatorMenuElements(kffExportImportDriver);
		manageKFFSetupInstance = new ManageKFFSetup(kffExportImportDriver);
		fsmExpImpWrapperInstance = new FSMExpImpWrapper(kffExportImportDriver);
		timestamp = CommonUtils.uniqueId();
		initialSegmentPrompt="segmentPrompt"+timestamp;
		ipName = "KFF" + timestamp;
		loginInstance.login(user, password,kffExportImportDriver);
		CommonUtils.hold(5);
		navigationTaskFlowsInstance.navigateToTask(navigatorMenuElementsInstance.SetupAndMaintenance,kffExportImportDriver);
		CommonUtils.hold(5);
	}

	@Test(priority = 41, description = "CreationOfKFFSegmentAndDeployment")
	public void testcase01() throws Exception {
		System.out.println("Creation Of KFF Segment And Deployment");
		System.out.println("Navigating to Manage Key Flexfields page");
		navigationTaskFlowsInstance.navigateToAOLTaskFlows("Manage Key Flexfields", kffExportImportDriver);
		manageKFFSetupInstance.keyFlexfieldCode.sendKeys(flexfieldCode);
		manageKFFSetupInstance.searchFlexfieldButton.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		System.out.println("Searching structure 'CATEGORY_FLEXFIELD' ");
		manageKFFSetupInstance.button_ManageStructures.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.structureCodeField.sendKeys("CATEGORY_FLEXFIELD");
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.searchStrButtonCAT.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		manageKFFSetupInstance.editIcon.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		numberOfexistingSegments = manageKFFSetupInstance.countSegments.getAttribute("_rowcount");
		System.out.println("***********Number of existing segments*************: " + numberOfexistingSegments);
		segmentCount = Integer.parseInt(numberOfexistingSegments) + 1;
		manageKFFSetupInstance.createIcon.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		System.out.println("*****Searched structure successfully*****");
		System.out.println("Creating a new segment");
		manageKFFSetupInstance.segmentCode.sendKeys("segmentCode" + timestamp);
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		manageKFFSetupInstance.segmentName.sendKeys("segmentName" + timestamp);
		manageKFFSetupInstance.segmentDesc.sendKeys("segmentDesc" + timestamp);
		manageKFFSetupInstance.sequenceNumber.sendKeys(Integer.toString(segmentCount));
		manageKFFSetupInstance.prompt.sendKeys("segmentPrompt" + timestamp);
		manageKFFSetupInstance.shortPrompt.sendKeys("shortPrompt" + timestamp);
		manageKFFSetupInstance.displayWidth.sendKeys("10");
		CommonUtils.hold(10);
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		manageKFFSetupInstance.columnNameField.sendKeys("SEGMENT" + segmentCount);
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		manageKFFSetupInstance.valueSetCode.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		manageKFFSetupInstance.valueSetCode.sendKeys("10 Characters");
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		manageKFFSetupInstance.saveAndCloseButton.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.editKFFStructurePageCAT.click();
		manageKFFSetupInstance.saveAndCloseButton.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.doneButtonKFFSTR.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		System.out.println("*****Created segment successfully*****");
		CommonUtils.hold(5);
		System.out.println("Preparing to deploy KFF flexfield");
		manageKFFSetupInstance.button_DeployFlexField.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		if (CommonUtils.isElementPresent(manageKFFSetupInstance.VerifyDeploymentInProgress)) {
			System.out.println("Key Flexfield " + flexfieldCode + " Deployment is in Progress!");
		}

		new FluentWait<WebDriver>(kffExportImportDriver).withTimeout(Duration.ofSeconds(2400))
				.pollingEvery(Duration.ofMillis(1000)).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.elementToBeClickable(manageKFFSetupInstance.VerifyOKButtonEnable));
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		if (CommonUtils.isElementPresent(manageKFFSetupInstance.VerifyDeploymentStatusSuccess))
			System.out.println("Key Flexfield " + flexfieldCode + " Deployed Successfully!");
		else if (CommonUtils.isElementPresent(manageKFFSetupInstance.VerifyDeploymentStatusError)) {
			System.out.println("Key Flexfield " + flexfieldCode + " failed due to error in Deployment!");
			deployStatus = false;
		}
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(10);
		if(manageKFFSetupInstance.VerifyOKButtonEnable.isDisplayed())
			manageKFFSetupInstance.VerifyOKButtonEnable.click();
		Assert.assertTrue(deployStatus);
		CommonUtils.waitForPageLoad(kffExportImportDriver);
	}
	
	@Test(priority = 42, description = "ExportProcessAndDownloadingConfPackage")
	public void testcase02() throws Exception {
		System.out.println("Starting Export process and downloading configuration package");
		fsmExpImpWrapperInstance.createImplementationProject(ipName, "Manage Key Flexfields", kffExportImportDriver);
		fsmExpImpWrapperInstance.createConfigurationProject(ipName, kffExportImportDriver);
		fsmExpImpWrapperInstance.downloadPackage(kffExportImportDriver);
		CommonUtils.hold(3);
	}
	
	@Test(priority = 43, description = "EditingTheSegmentDetails")
	public void testcase03() throws Exception {
		System.out.println("Editing the segment details!");
		navigationTaskFlowsInstance.navigateToTask(navigatorMenuElementsInstance.SetupAndMaintenance, kffExportImportDriver);
		CommonUtils.hold(5);
		navigationTaskFlowsInstance.navigateToAOLTaskFlows("Manage Key Flexfields", kffExportImportDriver);
		manageKFFSetupInstance.keyFlexfieldCode.sendKeys(flexfieldCode);
		manageKFFSetupInstance.searchFlexfieldButton.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		System.out.println("Searching structure 'CATEGORY_FLEXFIELD' ");
		manageKFFSetupInstance.button_ManageStructures.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.structureCodeField.sendKeys("CATEGORY_FLEXFIELD");
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.searchStrButtonCAT.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		manageKFFSetupInstance.editIcon.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		System.out.println("finding created segment to edit...");
		manageKFFSetupInstance.findcreatedSegment(timestamp).click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.editKFFSegment.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		System.out.println("Editing segment details");
		manageKFFSetupInstance.segmentName.clear();
		CommonUtils.hold(2);
		manageKFFSetupInstance.segmentName.sendKeys("segmentNameEdited" + timestamp);
		CommonUtils.hold(2);
		manageKFFSetupInstance.segmentDesc.clear();
		CommonUtils.hold(2);
		manageKFFSetupInstance.segmentDesc.sendKeys("segmentDescEdited" + timestamp);
		CommonUtils.hold(2);
		manageKFFSetupInstance.prompt.clear();
		CommonUtils.hold(2);
		manageKFFSetupInstance.prompt.sendKeys("segmentPromptEdited" + timestamp);
		CommonUtils.hold(2);
		manageKFFSetupInstance.shortPrompt.clear();
		CommonUtils.hold(2);
		manageKFFSetupInstance.shortPrompt.sendKeys("shortPromptEdited" + timestamp);
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.saveAndCloseButton.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.editKFFStructurePageCAT.click();
		manageKFFSetupInstance.saveAndCloseButton.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.doneButtonKFFSTR.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		System.out.println("Edited segment details successfully!");
	}
	@Test(priority = 44, description = "UploadingConfSetAndStartingImport")
	public void testcase04() throws Exception {
		System.out.println("Uploading Configuration Set And Starting Import process");
		fsmExpImpWrapperInstance.uploadConfigurationPackage(ipName, kffExportImportDriver);
		fsmExpImpWrapperInstance.importSetupData(ipName, kffExportImportDriver);
	}
	
	@Test(priority = 45, description = "ValidatingTheSegmentDetailsAfterImport")
	public void testcase05() throws Exception {
		System.out.println("Validating the segment details after Import");
		navigationTaskFlowsInstance.navigateToTask(navigatorMenuElementsInstance.SetupAndMaintenance, kffExportImportDriver);
		CommonUtils.hold(5);
		navigationTaskFlowsInstance.navigateToAOLTaskFlows("Manage Key Flexfields", kffExportImportDriver);
		manageKFFSetupInstance.keyFlexfieldCode.sendKeys(flexfieldCode);
		manageKFFSetupInstance.searchFlexfieldButton.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		System.out.println("Searching structure 'CATEGORY_FLEXFIELD' ");
		manageKFFSetupInstance.button_ManageStructures.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.structureCodeField.sendKeys("CATEGORY_FLEXFIELD");
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		manageKFFSetupInstance.searchStrButtonCAT.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		manageKFFSetupInstance.editIcon.click();
		CommonUtils.waitForPageLoad(kffExportImportDriver);
		CommonUtils.hold(5);
		System.out.println("Validating segment details after Import");
		segmentPrompt=manageKFFSetupInstance.findcreatedSegment(timestamp).getText();
		System.out.println("After Import segment propmt is:"+segmentPrompt);
		Assert.assertEquals(segmentPrompt, initialSegmentPrompt, "After Import segment prompt is not equal to initial segment prompt");
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		try {
			loginInstance.logout(kffExportImportDriver);
			

		} catch (Exception ex) {
			loginInstance.launchURL(kffExportImportDriver);
			CommonUtils.hold(2);
			loginInstance.logout(kffExportImportDriver);
		}finally {
			kffExportImportDriver.quit();
		}

	}
}
