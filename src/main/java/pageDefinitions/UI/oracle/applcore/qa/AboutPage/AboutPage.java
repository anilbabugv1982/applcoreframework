package pageDefinitions.UI.oracle.applcore.qa.AboutPage;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AboutPage {

	public AboutPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = ".//*[@id='pt1:_UIScmil1u::icon']")
	public static WebElement dropdownIcon;

	@FindBy(xpath=".//a[text()='About This Application']")
	public static WebElement  aboutThisApplicationLink;

	@FindBy(xpath = ".//tr/td/label[text()='Oracle Application Development Framework']/../../td/span[@id='pt1:_UISatpr4:0:i1:0:ott22']")
	public static WebElement adfVersion;

	@FindBy(xpath = ".//tr/td/label[text()='Oracle Middleware Extensions for Applications']/../../td/span[@id='pt1:_UISatpr4:0:i1:1:ott22']")
	public static WebElement atgVersion;

	@FindBy(xpath = ".//tr/td/label[text()='Database Compatibility']/../../td/span[@id='pt1:_UISatpr4:0:i1:1:ott23']")
	public static WebElement dbVersion;

	@FindBy(xpath = ".//tr/td/a[text()='Copyright Information']")
	public static WebElement copyrightInformation;

	@FindBy(xpath = ".//a[@id='pt1:_dialog::close']")
	public static WebElement closeIcon;

	@FindBy(xpath = ".//tr/td/div[@id='pt1:_dialog::_ttxt']")
	public static WebElement popupTitle;

}
