package Mobile.tests.PRC;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;

import pageDefinitions.mobile.oracle.applcoreqa.qa.prcFlows.*;


public class PRCflows extends GetDriverInstance {
	
	WebDriver PRCappDriver;
	private ApplicationLogin aLoginInstance;
	private PRCFlowsPage prcpages;
	private PRCFlowsUtils prcutils;
	

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void signIn(String username, String password) throws Exception {

		PRCappDriver = getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(PRCappDriver);
		aLoginInstance.login(username, password, PRCappDriver);
		prcpages = new PRCFlowsPage(PRCappDriver);
		prcutils = new PRCFlowsUtils(PRCappDriver);
		

	}

	
	@Test(priority = 1)
	public void verifycards() throws Exception {
		CommonUtils.explicitWait(prcpages.searchtab, "Click", "", PRCappDriver);
		List<WebElement> cards =  PRCappDriver.findElements(By.xpath("//li[contains(@class, 'oj-listview-card')]"));
		if (cards.size() >0) {
			System.out.println("cards present");
		}
	}	

	@Test(priority = 2)
	public void verifyseemore() throws Exception {
		List<WebElement> cards =  PRCappDriver.findElements(By.xpath("//li[contains(@class, 'oj-listview-card')]"));
		int intialcards = cards.size();
		prcutils.scrollintoview(PRCappDriver, prcpages.seeMore);
		prcpages.seeMore.click();
		 cards =  PRCappDriver.findElements(By.xpath("//li[contains(@class, 'oj-listview-card')]"));
		int finalcards = cards.size();
		if (finalcards > intialcards) {
			System.out.println("Clicking on See more cards are getting loaded");
		}
	}	
	
	@Test(priority = 3)
	public void verifyfeaturedcategory() throws Exception {
		CommonUtils.explicitWait(prcpages.searchtab, "Click", "", PRCappDriver);
		prcutils.scrollintoview(PRCappDriver, prcpages.category);
		List<WebElement> categories =  PRCappDriver.findElements(By.xpath("//li[contains(@id, 'featuredCategoriesList')]"));
		if (categories.size() > 0) {
			System.out.println("featured categories present");
		}
	}
	
	@Test(priority = 4)
	public void verifysearch() throws Exception {
		prcutils.search(PRCappDriver, "laptop");
		CommonUtils.explicitWait(prcpages.searchresult, "Click", "", PRCappDriver);
		List<WebElement> searchresults = PRCappDriver.findElements(By.xpath("//div[contains(@id, 'ssfs1_numOfResults')]"));
		if (searchresults.size() > 0) {
			
			System.out.println("search results present");
		} 
	}	
	
	@Test(priority = 5)
	public void verifysearchcount() throws Exception {
		
		String count = prcpages.count.getText();
		System.out.println(count);
		prcutils.scrollintoview(PRCappDriver, prcpages.enditem);
		List<WebElement> actualsearchresults = PRCappDriver.findElements(By.xpath("//ul/li[@class = 'oj-listview-focused-element oj-listview-item-element oj-listview-item']"));
		System.out.println(actualsearchresults.size());
		if (actualsearchresults.size() == Integer.parseInt(count)) {
			
			System.out.println("Search results count and search results are same");
		}
		
			}
	
	
	
	
	@Test(priority = 6)
	public void landonitemspage() throws Exception {
		prcpages.hometab.click();
		PRCappDriver.navigate().refresh();
		CommonUtils.explicitWait(prcpages.hometab, "Click", "", PRCappDriver);
		prcutils.scrollintoview(PRCappDriver, prcpages.category);
		prcutils.searchcategoryitem(PRCappDriver, "MNT Spares", "MNT Spares ManualPO");
		prcpages.backbutton.click();
		CommonUtils.explicitWait(prcpages.searchtab, "Click", "", PRCappDriver);
		
	
	}
	
	@Test(priority = 7)
	public void addtocartviasearch() throws Exception {
		prcpages.hometab.click();
		PRCappDriver.navigate().refresh();
		CommonUtils.explicitWait(prcpages.hometab, "Click", "", PRCappDriver);
		prcutils.search(PRCappDriver, "mnt");
		CommonUtils.hold(10);
		prcutils.addtocartcorrespondingitem(PRCappDriver, "MNT Spares ManualPO");
		CommonUtils.explicitWait(prcpages.hometab, "Click", "", PRCappDriver);
	}
	
	@Test(priority = 8)
	public void verifyresearch() throws Exception {
		prcpages.hometab.click();
		PRCappDriver.navigate().refresh();
		CommonUtils.explicitWait(prcpages.searchtab, "Click", "", PRCappDriver);
		prcpages.searchtab.click();
		prcutils.search(PRCappDriver, "mnt");
		CommonUtils.explicitWait(prcpages.remove, "Click", "", PRCappDriver);
		prcpages.remove.click();
		CommonUtils.explicitWait(prcpages.searchtab, "Click", "", PRCappDriver);
		prcutils.search(PRCappDriver, "laptop");
		CommonUtils.explicitWait(prcpages.hometab, "Click", "", PRCappDriver);
	}
	
	
	
	@Test(priority = 9)
	public void addtocartviacategory() throws Exception {
		prcpages.hometab.click();
		prcutils.scrollintoview(PRCappDriver, prcpages.category);
		prcutils.searchcategoryitem(PRCappDriver, "MNT Spares", "MNT Spares ManualPO");
				
	}
	
	
	@Test(priority = 13)
	public void submitcart() throws Exception {
		prcutils.scrollintoview(PRCappDriver, prcpages.submit);
		CommonUtils.explicitWait(prcpages.submit, "Click", "", PRCappDriver);
		prcpages.submit.click();
	}
	
	@Test(priority = 10)
	public void updateDate() throws Exception {
		
		prcpages.carttab.click();
		CommonUtils.explicitWait(prcpages.edit, "Click", "", PRCappDriver);
		prcpages.edit.click();
		CommonUtils.explicitWait(prcpages.date, "Click", "", PRCappDriver);           
		prcpages.date.click();
		prcutils.choosedate(PRCappDriver, "25");
		
		
		
		
	}
	
	@Test(priority = 11)
	public void updateAddress() throws Exception {
		prcpages.enteraddress.click();
		prcutils.updateaddress(PRCappDriver, "123 main st", "Aaron", "46202");		
		
		
		
	}
	
	@Test(priority = 12)
	public void updatechargeaccount() throws Exception {
		
		prcpages.enterchargeaccount.click();
		CommonUtils.explicitWait(prcpages.chargeaccount, "Click", "", PRCappDriver);
		prcpages.chargeaccount.clear();
		prcpages.chargeaccount.sendKeys("01-113-2440-1400-103");
		CommonUtils.explicitWait(prcpages.update, "Click", "", PRCappDriver);           
		prcpages.update.click();
		
		
	}
	
	@Test(priority = 14)
	public void verifysearchdialog() throws Exception {
		CommonUtils.explicitWait(prcpages.hometab, "Click", "", PRCappDriver); 
		prcpages.hometab.click();
		PRCappDriver.navigate().refresh();
		CommonUtils.explicitWait(prcpages.searchtab, "Click", "", PRCappDriver); 
		prcpages.searchtab.click();
		List<WebElement> searchdialog = PRCappDriver.findElements(By.xpath("//input[@aria-label = 'Search']"));
		if(searchdialog.size() > 0)
		{
			System.out.println("Search dialog present");
		}
	}
		
		@Test(priority = 15)
		public void verifysearchresultsretained() throws Exception {
			prcutils.search(PRCappDriver, "laptop");
			CommonUtils.explicitWait(prcpages.carttab, "Click", "", PRCappDriver); 
			prcpages.carttab.click();
			CommonUtils.explicitWait(prcpages.searchtab, "Click", "", PRCappDriver); 
			prcpages.searchtab.click();
			
		
	}
		
		@Test(priority = 16)
		public void verifysearchtwostrings() throws Exception {
			prcpages.hometab.click();
			PRCappDriver.navigate().refresh();
			prcutils.scrollintoview(PRCappDriver, prcpages.category);
			PRCappDriver.findElement(By.xpath("//div[text() = 'MNT Spares']")).click();
			prcutils.search(PRCappDriver, "mnt");
			prcutils.search(PRCappDriver, "Auto");
			
			
		
	}
	
}

     