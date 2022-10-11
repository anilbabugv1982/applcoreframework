package pageDefinitions.UI.oracle.applcore.qa.FusePlus;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.FusePlus.PageTemplateFusePlus;

public class PurchaseUtil extends PageTemplateFusePlus {
	
	public PurchaseUtil(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void waitAndClick(WebElement elem,WebDriver driver) throws Exception{
		CommonUtils.explicitWait(elem, "Click", "",driver);
		CommonUtils.hold(2);
		elem.click();
		CommonUtils.hold(3);
	}

	public void waitAndSendKeys(WebElement elem, String keys,WebDriver driver) throws Exception{
		CommonUtils.explicitWait(elem, "Visible", "",driver);
		CommonUtils.hold(3);
		elem.sendKeys(keys);
		CommonUtils.hold(2);
	}
}
