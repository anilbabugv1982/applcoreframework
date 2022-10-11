package UtilClasses.UI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import pageDefinitions.UI.oracle.applcore.qa.UtilsDefinitions.CommonUtilsDefs;



public class SetupAndMaintenance{
	
	private CommonUtilsDefs cudInstance_sAM;
	public SetupAndMaintenance(WebDriver driver) {
		cudInstance_sAM = new CommonUtilsDefs(driver);
	}

	private String taskName;
	
	public void clickTask(WebDriver driver) {
		CommonUtils.waitforElementtoClick(50,driver.findElement(By.xpath("//a[text()='"+taskName+"']")),driver);
//		WebDriverWait wait = new WebDriverWait(driver,30);
//		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[text()='"+taskName+"']"))));
		driver.findElement(By.xpath("//a[text()='"+taskName+"']")).click();
	}

	public void setTaskName(String tfTaskName) {
		taskName = tfTaskName;
	}


	public  void verifySetupAndMaintenancePage(WebDriver driver) {
		CommonUtils.waitForPageLoad(driver);
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(cudInstance_sAM.panelDrawer));
		wait.until(ExpectedConditions.elementToBeClickable(cudInstance_sAM.img_SearchTasks));
		Assert.assertTrue(cudInstance_sAM.panelDrawer.isDisplayed() && cudInstance_sAM.img_SearchTasks.isDisplayed(), "Setup and Maintenance  Not loaded");
	}
	
	public CommonUtilsDefs getCommonUtilsDefsInstance() {
		return cudInstance_sAM;
	}
	
}
