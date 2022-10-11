package UtilClasses.UI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ConnectionLeakLoginPage {

	private WebDriver driver;

	public ConnectionLeakLoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "j_username")
	public WebElement dmsSpy_Username;

	@FindBy(id = "j_password")
	public WebElement dmsSpy_Password;

	@FindBy(xpath = "//input[@type='submit']")
	public WebElement dmsSpy_Submit;

	public void waitForPageToLoad() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(dmsSpy_Username));
		wait.until(ExpectedConditions.visibilityOf(dmsSpy_Password));
		wait.until(ExpectedConditions.elementToBeClickable(dmsSpy_Submit));
		Assert.assertTrue(dmsSpy_Username.isDisplayed() && dmsSpy_Password.isDisplayed() && dmsSpy_Submit.isDisplayed(),
				"DMS Spy Page Not loaded");
		Thread.sleep(5000);
	}

	public void loginToDMSSpy() throws InterruptedException {
		dmsSpy_Username.clear();
		dmsSpy_Password.clear();
		dmsSpy_Username.sendKeys("faadmin");
		dmsSpy_Password.sendKeys("Fusionapps1");
		dmsSpy_Submit.click();
		Thread.sleep(5000);
	}

}
