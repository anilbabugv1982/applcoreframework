package UI.tests.GlobalSearch;


import static org.testng.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import junit.framework.Assert;
import pageDefinitions.UI.oracle.applcore.qa.GlobalSearch.gs;



public class GlobalSearch   extends GetDriverInstance{
	WebDriver globalsearchinstance;
	private ApplicationLogin aLoginInstance=null;
	private NavigationTaskFlows ntFInstance=null;
	private NavigatorMenuElements nMEInstance=null;
	private GlobalPageTemplate glbpageInstance=null;
	private CommonUtils CUtilInstance=null;
	private gs gsinstance;
	

	
	
	

//**********************************Login into the Application*************************************************************************************************	
	@Parameters({ "user", "pwd" })
	@BeforeClass(alwaysRun = true)
    public void startUP(String username, String password) throws Exception {

		globalsearchinstance =  getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(globalsearchinstance);
		ntFInstance = new NavigationTaskFlows(globalsearchinstance);
		nMEInstance = new NavigatorMenuElements(globalsearchinstance);
		glbpageInstance=new GlobalPageTemplate(globalsearchinstance);
		gsinstance=new gs(globalsearchinstance);
    aLoginInstance.login("cvbuyer01", "Welcome1",globalsearchinstance);       
	Log.info("Logging into the Application");
	CommonUtils.hold(30);
    }
	
//***************************************************************************************************************************************************************	
	
	@Test(description="Validation of Global Search",priority=1,enabled=true)
	public void testcase01() {
		
		//assertEquals(true, gs.globalsearch.isDisplayed());
		Log.info("Validation of Global Search is displayed");
		gsinstance.globalsearch.click();
		Log.info("Click on the Global Search");
		CommonUtils.hold(10);
		System.out.println(gsinstance.personalizesearch.isDisplayed());
		gsinstance.personalizesearch.click();	
		Log.info("Click on the Personalize Search");
		CommonUtils.hold(30);
		gsinstance.autosuggest.click();
		Log.info("Click on the AutoSuggest Search");
		//CommonUtils.waitForPageLoad(5);
		CommonUtils.hold(10);
		
		//gsinstance.searchactivegroups();
	}
//***************************************************************************************************************************************************************
 
	@Test(description="Validation of Last Search",priority=2,enabled=true)
	public void testcase02() {
		gsinstance.globalsearch.sendKeys("Supplier");
		gsinstance.globalsearch.sendKeys(Keys.ENTER);
		CommonUtils.hold(20);
		Log.info("Search Results are displayed");
		WebElement ele1=gsinstance.searchclose;
		CommonUtils.waitForElement(ele1,globalsearchinstance);
		while(gsinstance.searchclose.isDisplayed()==true) {
		CommonUtils.waitForElementToPresent(gsinstance.searchclose);
		CommonUtils.hold(10);	
		gsinstance.searchclose.click();
		Log.info("Close Search Window");
		break;
		}
		
		gsinstance.globalsearch.clear();
		
		CommonUtils.hold(10);
		gsinstance.globalsearch.click();
		//CommonUtils.waitForElement(gsinstance.searchterms);
	 /*   if(gs.searchterms.isDisplayed()==true) {
			assertEquals(true, gs.searchterms.isDisplayed());
		}
		*/
	}

//***************************************************************************************************************************************************************	
	@Test(description="Validation of Recent Items",priority=3,enabled=true)
	public void testcase03() throws Exception {
		CommonUtils.hold(5);
		
		ntFInstance.navigateToTask(glbpageInstance.auditreports,globalsearchinstance);
		CUtilInstance.waitForElementToPresent(glbpageInstance.auditreports);
		Log.info("Navigate to Audit Reports");
		CommonUtils.hold(10);
		glbpageInstance.homeIcon.click();
		Log.info("Navigate to FuseWelcome Page");
		CommonUtils.hold(10);
		gsinstance.globalsearch.click();              //Click on the global search
		CommonUtils.hold(5);
	    List<WebElement> listBox = globalsearchinstance.findElements(By.xpath("//ul[@id='pt1:_UISGSr:0:_GSFsf::_fndSuggestPopup']//li"));
        int listBoxSize = listBox.size();
        System.out.println("The size of the listbox is:"+listBoxSize);
        ArrayList<String> listBoxItems = new ArrayList<String>();
       for (WebElement option : listBox) {
System.out.println(option.getText());
if(option.getText().equals("RECENT ITEMS") || option.getText().equals("Audit Reports")) {
	
	  Assert.assertTrue(true);
	 }
       }
  
	
	}
//***************************************************************************************************************************************************************
	
	@Test(description = "Navigation Item", priority = 4, enabled = true)
	public void testcase04() throws Exception {
		gsinstance.globalsearch.sendKeys("File Import and Export");
		CUtilInstance.hold(5);

		List<WebElement> listBox = globalsearchinstance
				.findElements(By.xpath("//ul[@id='pt1:_UISGSr:0:_GSFsf::_fndSuggestPopup']//li"));
		int listBoxSize = listBox.size();
		System.out.println("The size of the listbox is:" + listBoxSize);
		ArrayList<String> listBoxItems = new ArrayList<String>();
		// for(WebElement option : listBox){
		for (int i = 0; i < listBox.size(); i++) {

			System.out.println("----->" + listBox.get(i).getText());
			if (listBox.get(i).getText().equals("NAVIGATOR")) {
				listBox.get(i + 1).click();
				CommonUtils.waitForElementToPresent(globalsearchinstance.findElement(By.xpath("//input[contains(@name,'FOSritemNode_tools_file_import_and_export:0:_FOTsr1:0:pt1:AP1:it5')]")));
				Assert.assertEquals("File Import and Export - File Import and Export - Oracle Applications",globalsearchinstance.getTitle());
				break;

			}

		}

	}
//***************************************************************************************************************************************************************
	@Test(description = "Navigation Item", priority = 5, enabled = true)
	public void testcase05() throws Exception {
		
		CommonUtils.waitForElementToPresent((glbpageInstance.homeIcon));
		glbpageInstance.favoritesAndRecentItemsIcon.click();
		//GlobalPageTemplate.favorites.click();
		CUtilInstance.waitForElement(globalsearchinstance.findElement(By.id("pt1:_UISfpr1:0:_UISfavLink::disAcr")),globalsearchinstance);
		globalsearchinstance.findElement(By.id("pt1:_UISfpr1:0:_UISfavLink::disAcr")).click();
		CommonUtils.hold(4);
		int r1_count=globalsearchinstance.findElements(By.xpath("//div[@class='AFStretchWidth af_showDetailItem']/div[1]/div/div/a/span")).size();
		System.out.println(r1_count);
		for(int k=0;k<r1_count-2;k++) {
			System.out.println(globalsearchinstance.findElement(By.xpath("//a[@id='pt1:_UISfpr1:0:itr1_RI:"+k+":cl3_RI']/span")).getText());
		}
			
		
	}
	
	
	
	
	
//***************************************************************************************************************************************************************	
	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {

		try {
			aLoginInstance.logout(globalsearchinstance);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(globalsearchinstance);
			CUtilInstance.hold(10);
			aLoginInstance.logout(globalsearchinstance);
		}
		finally {
			try {
				
				globalsearchinstance.quit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}

	}
	}	
//***************************************************************************************************************************************************************	
}

