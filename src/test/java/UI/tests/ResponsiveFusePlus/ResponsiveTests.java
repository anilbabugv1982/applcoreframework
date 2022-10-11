package UI.tests.ResponsiveFusePlus;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus.ResponsiveFusePlusPage;
import pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus.ResponsiveFusePlusUtil;

public class ResponsiveTests extends GetDriverInstance {

	WebDriver ResponisveFusePlusDriver;
	private ApplicationLogin aLoginInstance;
	private ResponsiveFusePlusPage responsivepages;
	private ResponsiveFusePlusUtil responsiveutil;

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void signIn(String username, String password) throws Exception {

		ResponisveFusePlusDriver = getDriverInstanceObject();

		aLoginInstance = new ApplicationLogin(ResponisveFusePlusDriver);
		responsivepages = new ResponsiveFusePlusPage(ResponisveFusePlusDriver);
		responsiveutil = new ResponsiveFusePlusUtil(ResponisveFusePlusDriver);
		aLoginInstance.login(username, password, ResponisveFusePlusDriver);

	}

	@Test(priority = 1)
	public void testSearchByWorkerType() throws Exception {
		responsiveutil.navigateto(ResponisveFusePlusDriver, "My Team", "Transfer", "yes");
		responsiveutil.navigateto(ResponisveFusePlusDriver, "My Team", "My Team", "No");
		responsivepages.showFilter.click();
		CommonUtils.explicitWait(responsivepages.clear, "Click", "", ResponisveFusePlusDriver);
		responsivepages.clear.click();
		CommonUtils.explicitWait(responsivepages.contingentWorker, "Click", "", ResponisveFusePlusDriver);
		responsivepages.contingentWorker.click();
		CommonUtils.explicitWait(responsivepages.clear, "Click", "", ResponisveFusePlusDriver);
		responsivepages.clear.click();
		CommonUtils.explicitWait(responsivepages.employee, "Click", "", ResponisveFusePlusDriver);
		responsivepages.employee.click();
		CommonUtils.explicitWait(responsivepages.clear, "Click", "", ResponisveFusePlusDriver);
		responsivepages.clear.click();
		CommonUtils.explicitWait(responsivepages.nonworker, "Click", "", ResponisveFusePlusDriver);
		responsivepages.nonworker.click();
		CommonUtils.explicitWait(responsivepages.clear, "Click", "", ResponisveFusePlusDriver);
		responsivepages.clear.click();
		CommonUtils.explicitWait(responsivepages.pendingWorker, "Click", "", ResponisveFusePlusDriver);
		responsivepages.pendingWorker.click();
		CommonUtils.explicitWait(responsivepages.clear, "Click", "", ResponisveFusePlusDriver);
		responsivepages.clear.click();

	}

	@Test(priority = 2)
	public void testRemoveManagerType() throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor) ResponisveFusePlusDriver;

		responsivepages.personalizefilter.click();
		CommonUtils.explicitWait(responsivepages.configureforself, "Click", "", ResponisveFusePlusDriver);
		responsivepages.configureforself.click();
		CommonUtils.explicitWait(responsivepages.managertype, "Click", "", ResponisveFusePlusDriver);
		responsivepages.managertype.click();
		CommonUtils.hold(5);
		WebElement ok = ResponisveFusePlusDriver.findElement(By.xpath("//button[@title = 'OK']"));
		executor.executeScript("arguments[0].click();", ok);
	}

	@Test(priority = 3)
	public void testSearchEmployee() throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor) ResponisveFusePlusDriver;

		responsivepages.seachperson.sendKeys("AUTO_");
		CommonUtils.explicitWait(responsivepages.search, "Click", "", ResponisveFusePlusDriver);
		responsivepages.search.click();

	}

}
