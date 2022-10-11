package UI.tests.Flex.KFF;

import java.sql.SQLException;


import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import UtilClasses.UI.SetupAndMaintenance;
import pageDefinitions.UI.oracle.applcore.qa.Kff.KFFAliases;
import pageDefinitions.UI.oracle.applcore.qa.Kff.KFFUtils;


public class KFFAliasesSetup extends GetDriverInstance{
	
	private WebDriver kffAliasSetupDriver;
		
	public static String taskFlow = "Manage Shorthand Aliases";
	public static String timestamp = "";
	public static String  rowcount;
	
	private ApplicationLogin loginInstance;
	private NavigationTaskFlows navigationTaskFlowsInstance;
	private NavigatorMenuElements navigatorMenuElementsInstance;
	private SetupAndMaintenance setupAndMaintenanceInstance;
	private KFFAliases kffAliasInstance;
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void signIn(String uname, String pwd) throws Exception {
		kffAliasSetupDriver= getDriverInstanceObject();
		timestamp = CommonUtils.uniqueId();
		loginInstance = new ApplicationLogin(kffAliasSetupDriver);
		navigationTaskFlowsInstance = new NavigationTaskFlows(kffAliasSetupDriver);
		navigatorMenuElementsInstance = new NavigatorMenuElements(kffAliasSetupDriver);
		setupAndMaintenanceInstance = new SetupAndMaintenance(kffAliasSetupDriver);
		kffAliasInstance = new KFFAliases(kffAliasSetupDriver);
		loginInstance.login(uname, pwd,kffAliasSetupDriver);
		CommonUtils.hold(10);
	}

	@Test(priority = 21, description = "NavigateToManageShorthandAliases")
	public void testcase01() throws Exception {
		System.out.println("Navigating to Manage Shorthand Aliases task");
		navigationTaskFlowsInstance.navigateToTask(navigatorMenuElementsInstance.SetupAndMaintenance,kffAliasSetupDriver);
		CommonUtils.waitForPageLoad(kffAliasSetupDriver);
		CommonUtils.hold(5);
		navigationTaskFlowsInstance.navigateToAOLTaskFlows(taskFlow,kffAliasSetupDriver);
		CommonUtils.waitForPageLoad(kffAliasSetupDriver);
		CommonUtils.hold(5);
		Assert.assertTrue(CommonUtils.isElementPresent(kffAliasInstance.manageShorthandAliasesPageHeading), "Not in Manage Shorthand Aliases page");
		System.out.println("*****NavigateToManageShorthandAliases done*****");
	}
	

	@Test(priority = 22, description = "CreateAliases" , dependsOnMethods = {"testcase01"})
	public void testcase02() throws Exception {
		System.out.println("Creating Aliases");
		Select mydrpdwn = new Select(kffAliasInstance.selectChartOfAccounts);
		mydrpdwn.selectByVisibleText("Operations Accounting Flex");
		KFFUtils.waitForElementToBeVisible(kffAliasInstance.chartOfAccounts,kffAliasSetupDriver);
		CommonUtils.hold(5);
		KFFUtils.waitForElementToBeClickable(kffAliasInstance.searchAliasesButton,kffAliasSetupDriver);
		kffAliasInstance.searchAliasesButton.click();
        CommonUtils.waitForPageLoad(kffAliasSetupDriver);
		CommonUtils.hold(15);
		kffAliasInstance.chartOfAccountsLabel.click();
		CommonUtils.waitForPageLoad(kffAliasSetupDriver);
		CommonUtils.hold(5);
		kffAliasInstance.addRow.click();
		CommonUtils.waitForPageLoad(kffAliasSetupDriver);
		kffAliasInstance.waitForEditableRowToPresent();
		CommonUtils.hold(15);
		System.out.println("Creating Alias 1");
		kffAliasInstance.aliasName.click();
		kffAliasInstance.aliasName.sendKeys("Alias1"+timestamp);
		kffAliasInstance.aliasName.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffAliasInstance.companyInput.sendKeys("03");
		kffAliasInstance.companyInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffAliasInstance.departmentInput.sendKeys("114");
		kffAliasInstance.departmentInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffAliasInstance.accountInput.sendKeys("1140");
		kffAliasInstance.accountInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffAliasInstance.subAccountInput.sendKeys("1728");
		kffAliasInstance.subAccountInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffAliasInstance.productInput.sendKeys("102");
		CommonUtils.waitForPageLoad(kffAliasSetupDriver);
		CommonUtils.hold(5);
		kffAliasInstance.saveButton.click();
		WebDriverWait element = new WebDriverWait(kffAliasSetupDriver,30);
		CommonUtils.waitForPageLoad(kffAliasSetupDriver);
		kffAliasInstance.waitForRowToBeSaved("Alias1"+timestamp);
		//element.until(ExpectedConditions.elementToBeClickable(KFFAliases.saveButton));
		CommonUtils.hold(10);
		System.out.println("Alias 1 created");
		kffAliasInstance.addRow.click();
		CommonUtils.waitForPageLoad(kffAliasSetupDriver);
		kffAliasInstance.waitForEditableRowToPresent();
		CommonUtils.hold(15);
		System.out.println("Creating Alias 2");
		kffAliasInstance.aliasName.click();
		kffAliasInstance.aliasName.sendKeys("Alias2"+timestamp);
		kffAliasInstance.aliasName.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffAliasInstance.companyInput.sendKeys("05");
		kffAliasInstance.companyInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffAliasInstance.departmentInput.sendKeys("113");
		kffAliasInstance.departmentInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffAliasInstance.accountInput.sendKeys("1150");
		kffAliasInstance.accountInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffAliasInstance.subAccountInput.sendKeys("1729");
		kffAliasInstance.subAccountInput.sendKeys(Keys.TAB);
		CommonUtils.hold(3);
		kffAliasInstance.productInput.sendKeys("103");
		CommonUtils.waitForPageLoad(kffAliasSetupDriver);
		CommonUtils.hold(5);
		System.out.println("Alias 2 created");
		kffAliasInstance.saveButton.click();
		//element.until(ExpectedConditions.elementToBeClickable(KFFAliases.saveButton));
		kffAliasInstance.waitForRowToBeSaved("Alias2"+timestamp);
		CommonUtils.waitForPageLoad(kffAliasSetupDriver);
		CommonUtils.hold(10);
		kffAliasInstance.cancelButton.click();
		CommonUtils.waitForPageLoad(kffAliasSetupDriver);
		element.until(ExpectedConditions.elementToBeClickable(kffAliasInstance.taskFlow_ManageShorthandAliases));
		CommonUtils.hold(5);
	}
	
	@AfterClass(alwaysRun = true)
	public void logOut() throws Exception {
		try {
			loginInstance.logout(kffAliasSetupDriver);
			

		} catch (Exception ex) {
			loginInstance.launchURL(kffAliasSetupDriver);
			CommonUtils.hold(2);
			loginInstance.logout(kffAliasSetupDriver);
		}finally {
			kffAliasSetupDriver.quit();
		}

	}
}
