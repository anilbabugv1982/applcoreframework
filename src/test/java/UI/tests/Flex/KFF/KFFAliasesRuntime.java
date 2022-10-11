package UI.tests.Flex.KFF;

import org.junit.Assert;


import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import UtilClasses.UI.SetupAndMaintenance;
import pageDefinitions.UI.oracle.applcore.qa.Kff.KFFAliases;
import pageDefinitions.UI.oracle.applcore.qa.Kff.KFFRuntimeJournals;
import pageDefinitions.UI.oracle.applcore.qa.Kff.KFFUtils;


public class KFFAliasesRuntime extends GetDriverInstance{
	private WebDriver kffAliasRuntimeDriver;
	private ApplicationLogin loginInstance1;
	private NavigationTaskFlows navigationTaskFlowsInstance1;
	private NavigatorMenuElements navigatorMenuElementsInstance1;
	private SetupAndMaintenance setupAndMaintenanceInstance1;
	private KFFAliases kffAliasInstance1;
	private KFFRuntimeJournals kffRuntimeJournalsInstance1;
	public static String timestamp;
	//String defaultAccountValue="05-114-1160-1728-000";
	static String alias1ValuePopulated, alias1ValueExpected="03-114-1140-1728-102";
	static String alias2ValuePopulated, alias2ValueExpected="05-113-1150-1729-103";
	public static int totalSegmentCount;

	@Parameters({ "user_runtime", "pwd" })
	@BeforeClass
	public void signIn(String user, String pwd) throws Exception {
		kffAliasRuntimeDriver = getDriverInstanceObject();
		loginInstance1 = new ApplicationLogin(kffAliasRuntimeDriver);
		navigationTaskFlowsInstance1 = new NavigationTaskFlows(kffAliasRuntimeDriver);
		navigatorMenuElementsInstance1 = new NavigatorMenuElements(kffAliasRuntimeDriver);
		setupAndMaintenanceInstance1 = new SetupAndMaintenance(kffAliasRuntimeDriver);
		kffRuntimeJournalsInstance1 = new KFFRuntimeJournals(kffAliasRuntimeDriver);
		kffAliasInstance1 = new KFFAliases(kffAliasRuntimeDriver);
		timestamp =KFFAliasesSetup.timestamp;
		loginInstance1.login(user, pwd,kffAliasRuntimeDriver);
		CommonUtils.hold(10);
	}
	
	@Test(priority = 31, description = "NavigateToJournals")
	public void testcase01() throws Exception {
		System.out.println("Navigate to Journals");
		navigationTaskFlowsInstance1.navigateToTask(kffRuntimeJournalsInstance1.journalsIcon,kffAliasRuntimeDriver);
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance1.tasksButton,kffAliasRuntimeDriver);
		String existingDAS = kffRuntimeJournalsInstance1.existingDataAccessSet.getText();
		System.out.println("Existing DAS is :"+existingDAS);
		if (!existingDAS.equals("Vision Operations (USA)")) {
			kffRuntimeJournalsInstance1.changeDataAccessSet.click();
			CommonUtils.hold(5);
			Select mydrpdwn = new Select(kffRuntimeJournalsInstance1.selectDAS);
			mydrpdwn.selectByVisibleText("Vision Operations (USA)");
			CommonUtils.hold(5);
			kffRuntimeJournalsInstance1.OKDASButton.click();
		}
		CommonUtils.hold(5);
	}

	@Test(priority = 32, description = "CreateJournals" , dependsOnMethods = {"testcase01"})
	public void testcase02() throws Exception {
		System.out.println("CreateJournals");
		kffRuntimeJournalsInstance1.tasksButton.click();
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		KFFUtils.waitForElementToBeClickable(kffRuntimeJournalsInstance1.createJournal,kffAliasRuntimeDriver);
		CommonUtils.hold(8);
		kffRuntimeJournalsInstance1.createJournal.click();
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(5);
		kffRuntimeJournalsInstance1.journalName.click();
		kffRuntimeJournalsInstance1.journalName.sendKeys("journal" + timestamp);
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(5);
		kffRuntimeJournalsInstance1.categorySelect.click();
	    System.out.println("Clicked catagory");
		CommonUtils.hold(10);
		kffRuntimeJournalsInstance1.categorySelectAddition.click();
		System.out.println("Selected catagory value");
		CommonUtils.hold(15);
	}
	
	@Test(priority = 33, description = "VerifyAliasDropdownAndInputField" , dependsOnMethods = {"testcase02"}) 
	public void testcase03() throws Exception {
		System.out.println("Verify Alias Dropdown And Input Field");
		System.out.println("Clicking on KFF icon");
		kffRuntimeJournalsInstance1.kffIcon.click();
		CommonUtils.explicitWait(kffAliasInstance1.aliasInput, "Click", "",kffAliasRuntimeDriver);
		CommonUtils.hold(2);
		System.out.println("Verifying account popup opened");
		Assert.assertTrue("Account popup is not present",
				CommonUtils.isElementPresent(kffRuntimeJournalsInstance1.accountPopup));
		CommonUtils.hold(2);
		System.out.println("Verifying alias is enabled");
		Assert.assertTrue("Alias label is not present on account popup",
				CommonUtils.isElementPresent(kffAliasInstance1.aliaslabel));
		CommonUtils.hold(2);
		System.out.println("Verifying alias input present");
		Assert.assertTrue("Alias input is not present on account popup",
				CommonUtils.isElementPresent(kffAliasInstance1.aliasInput));
		CommonUtils.hold(2);
		kffAliasInstance1.searchButton.click();
		CommonUtils.hold(5);
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(10);
		totalSegmentCount=Integer.parseInt(kffRuntimeJournalsInstance1.getSegmentCount.getAttribute("span"));
		System.out.println("total segment identified are: "+totalSegmentCount);
		for (int i = 1; i <= totalSegmentCount - 5; i++) {
			alias1ValueExpected = alias1ValueExpected.concat("-");
			alias2ValueExpected = alias2ValueExpected.concat("-");
		}
		System.out.println("alias1 Value Expected is: " + alias1ValueExpected);
		System.out.println("alias2 Value Expected is: " + alias2ValueExpected);
		kffRuntimeJournalsInstance1.cancelButton.click();
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(10);
	}
	
	@Test(priority = 34, description = "SelectAliasFromDropdownAndVerifyPopulatingValue" , dependsOnMethods = {"testcase03"}) 
	public void testcase04() throws InterruptedException {
		System.out.println("Select Alias From available values And Verify Populating Value");
		System.out.println("Clicking on KFF icon");
		kffRuntimeJournalsInstance1.kffIcon.click();
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(5);
		System.out.println("Verifying account popup opened");
		KFFUtils.waitForElementToBeVisible(kffAliasInstance1.aliasInput,kffAliasRuntimeDriver);
		WebDriverWait wait = new WebDriverWait(kffAliasRuntimeDriver, 30);
		//wait.until(ExpectedConditions.visibilityOf(KFFAliases.aliasInput));
		Assert.assertTrue("Account popup is not present",
				CommonUtils.isElementPresent(kffRuntimeJournalsInstance1.accountPopup));
		CommonUtils.hold(2);
		kffAliasInstance1.aliasInput.click();
		kffAliasInstance1.aliasInput.sendKeys("Alias1"+timestamp);
		kffAliasInstance1.aliasInput.sendKeys(Keys.ENTER);
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(5);
		CommonUtils.scrollToElement(kffRuntimeJournalsInstance1.okButton,kffAliasRuntimeDriver);
		CommonUtils.hold(2);
		kffRuntimeJournalsInstance1.okButton.click();
		CommonUtils.hold(8);
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		KFFUtils.waitForElementNotVisible("//button[contains(@id,'accountSEl')]",kffAliasRuntimeDriver);
		alias1ValuePopulated=kffRuntimeJournalsInstance1.accountInput.getAttribute("value");
		System.out.println("Account value populated is : "+kffRuntimeJournalsInstance1.accountInput.getAttribute("value"));
		System.out.println("Verifying account value populated");
		Assert.assertTrue(alias1ValuePopulated.equals(alias1ValueExpected));
		kffRuntimeJournalsInstance1.debitAmount.sendKeys("100");
		kffRuntimeJournalsInstance1.debitAmount.sendKeys(Keys.TAB);
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(5);
		System.out.println("Journal line 1 created by selecting alias value from alias dropdown");
	}
	
	@Test(priority = 35, description = "EnterAliasNameDirectlyIntoAccountFieldAndVerifyPopulatingValue" , dependsOnMethods = {"testcase04"}) 
	public void testcase05() throws InterruptedException {
		System.out.println("Enter #alias name Directly Into Account Field And Verify Populating Value");
		String alias = "#Alias2"+timestamp;
		CommonUtils.scrollToElement(kffRuntimeJournalsInstance1.accountLineTwo,kffAliasRuntimeDriver);
		kffRuntimeJournalsInstance1.accountLineTwo.click();
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(10);
		kffRuntimeJournalsInstance1.accountInput.click();
		kffRuntimeJournalsInstance1.accountInput.sendKeys("#Alias2"+timestamp);
		kffRuntimeJournalsInstance1.accountInput.sendKeys(Keys.TAB);
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(10);
		kffRuntimeJournalsInstance1.creditAmount.click();
		kffRuntimeJournalsInstance1.creditAmount.sendKeys("100");
		kffRuntimeJournalsInstance1.creditAmount.sendKeys(Keys.TAB);
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(5);
		System.out.println("Journal line 2 created by providing #alias name directly into account field");
		kffRuntimeJournalsInstance1.saveButton.click();
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(8);
		System.out.println("Journal created and saved with name: "+kffRuntimeJournalsInstance1.journalName.getAttribute("value"));
		kffRuntimeJournalsInstance1.cancelJButton1.click();
		CommonUtils.waitForPageLoad(kffAliasRuntimeDriver);
		CommonUtils.hold(10);
		if(kffRuntimeJournalsInstance1.journalPage.isDisplayed())
			System.out.println("Journal saved successfully");
		else
			System.out.println("Journal not saved properly");
	}
	
	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {
		try {
			loginInstance1.logout(kffAliasRuntimeDriver);
			

		} catch (Exception ex) {
			loginInstance1.launchURL(kffAliasRuntimeDriver);
			CommonUtils.hold(2);
			loginInstance1.logout(kffAliasRuntimeDriver);
		}finally {
			kffAliasRuntimeDriver.quit();
		}

	}

}
