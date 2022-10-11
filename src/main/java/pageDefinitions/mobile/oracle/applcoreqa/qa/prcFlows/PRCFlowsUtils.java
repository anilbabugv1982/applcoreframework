package pageDefinitions.mobile.oracle.applcoreqa.qa.prcFlows;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import UtilClasses.UI.CommonUtils;

public class PRCFlowsUtils extends PRCFlowsPage {

	public PRCFlowsUtils(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	public void search(WebDriver driver, String item) throws Exception {
		searchtab.click();
		search.sendKeys(item);
		CommonUtils.hold(5);
		search.sendKeys(Keys.ENTER);
		CommonUtils.hold(10);
		
	}
	
	public void scrollintoview(WebDriver driver, WebElement element) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		js.executeScript("arguments[0].scrollIntoView();", element);
		CommonUtils.hold(3);
		js.executeScript("window.scrollBy(0,-200)");
		CommonUtils.hold(3);
			
	}
	
	public void searchcategoryitem(WebDriver driver, String Category, String item) throws Exception {
		driver.findElement(By.xpath("//div[text() = '"+Category+"']")).click();
		CommonUtils.hold(10);
		driver.findElement(By.xpath("//div[text() = '"+item+"']")).click();
		
	}
	
	public void addtocartcorrespondingitem(WebDriver driver, String item) throws Exception {
		driver.findElement(By.xpath("//div[text() = '"+item+"']/../../../../following-sibling::oj-list-item-layout//a[(text() ='Add to cart')]")).click();
		
	}
	
	public void choosedate(WebDriver driver, String date) throws Exception {
		driver.findElement(By.xpath("//a[text()= '"+date+"']")).click();
		
	}
	
	
	public void updateaddress(WebDriver driver, String address, String City, String PostalCode) throws Exception {
		address1.sendKeys(address);
		citydropdown.click();
		CommonUtils.hold(5);
		driver.findElement(By.xpath("//li//span[text()= '"+City+"']")).click();
		CommonUtils.hold(5);
		postalcode.click();
		CommonUtils.hold(5);
		driver.findElement(By.xpath("//li//span[text()= '"+PostalCode+"']")).click();

		
	}
	
}
