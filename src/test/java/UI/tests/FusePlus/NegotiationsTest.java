package UI.tests.FusePlus;

import static org.testng.Assert.assertEquals;

import pageDefinitions.UI.oracle.applcore.qa.FusePlus.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import TestBase.UI.GetDriverInstance;

public class NegotiationsTest extends GetDriverInstance {
	
	PageTemplateFusePlusUtil pageTempFuseUtil;
	WebDriver ManageNegotiationsDriver;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
		
	@BeforeClass
public void signIn() throws Exception {
		
		ManageNegotiationsDriver = getDriverInstanceObject();
		pageTempFuseUtil=new PageTemplateFusePlusUtil(ManageNegotiationsDriver);
		aLoginInstance = new ApplicationLogin(ManageNegotiationsDriver);
		ntFInstance = new NavigationTaskFlows(ManageNegotiationsDriver);
		nMEInstance = new NavigatorMenuElements(ManageNegotiationsDriver);
		Thread.sleep(1000);
		aLoginInstance.login("cvbuyer01", "Welcome1",ManageNegotiationsDriver);
           pageTempFuseUtil.navigateToManagePrograms(ManageNegotiationsDriver);
	}




@Test(priority=1,description="This testcase is to create program")
public void testcase01() throws Exception{	
	System.out.println("############################Starting create Program ############################");

	pageTempFuseUtil.createProgram("test",ManageNegotiationsDriver);
			
 }

@Test(priority=2,description="This testcase is to create program" )
public void testcase02() throws Exception {	
	System.out.println("############################Starting testcase2 ############################");

	pageTempFuseUtil.createProgram("latest",ManageNegotiationsDriver);

   }

@Test(priority=3,description="This testcase is to create program")
public void testcase03() throws Exception {	
	System.out.println("############################Starting testcase3 ############################");

	pageTempFuseUtil.createProgram("update",ManageNegotiationsDriver);

}
@Test(priority=4,description="Negotiations test1" )
public void testcase04() throws Exception {	
	System.out.println("############################Starting testcase4 ############################");

	pageTempFuseUtil.createProgram("updateNew",ManageNegotiationsDriver);

 }
@Test(priority=5,description="This testcase is to search the program, testSavedSearch" )
public void testcase05() throws Exception {	
	System.out.println("############################Starting testcase5 ############################");
	CommonUtils.hold(2);
	CommonUtils.explicitWait(PageTemplateFusePlus.showFilters, "Click", "", ManageNegotiationsDriver);
	PageTemplateFusePlus.showFilters.click();
     CommonUtils.hold(2);
	CommonUtils.explicitWait(PageTemplateFusePlus.title, "Click", "", ManageNegotiationsDriver);
     PageTemplateFusePlus.title.click();
         CommonUtils.hold(2);
     pageTempFuseUtil.saveSearch("test", "testSavedSearch",ManageNegotiationsDriver);
 	System.out.println("Verified Successfully");
 	PageTemplateFusePlus.showFilters.click();
    
 }
@Test(priority=6,description="This testcase is to search the program, updateSavedSearch" )
public void testcase06() throws Exception {	
	System.out.println("############################Starting testcase6 ############################");
	CommonUtils.hold(2);
	CommonUtils.explicitWait(PageTemplateFusePlus.showFilters, "Click", "", ManageNegotiationsDriver);
	PageTemplateFusePlus.showFilters.click();
     CommonUtils.hold(5);
	CommonUtils.explicitWait(PageTemplateFusePlus.title, "Click", "", ManageNegotiationsDriver);
     PageTemplateFusePlus.title.click();
          CommonUtils.hold(2);
     pageTempFuseUtil.saveSearch("update", "updateSavedSearch",ManageNegotiationsDriver);
 	System.out.println("Verified Successfully");
 	PageTemplateFusePlus.showFilters.click();
    
 }
@Test(priority=7,description="This testcase is to search the program, latestSavedSeach" )
public void testcase07() throws Exception {	
	System.out.println("############################Starting testcase7 ############################");
	CommonUtils.hold(2);
	CommonUtils.explicitWait(PageTemplateFusePlus.showFilters, "Click", "", ManageNegotiationsDriver);
	PageTemplateFusePlus.showFilters.click();
     CommonUtils.hold(2);
	CommonUtils.explicitWait(PageTemplateFusePlus.title, "Click", "", ManageNegotiationsDriver);
     PageTemplateFusePlus.title.click();
       pageTempFuseUtil.saveSearch("latest", "latestSavedSearch",ManageNegotiationsDriver);
 	System.out.println("Verified Successfully");
 	PageTemplateFusePlus.showFilters.click();
   
 }

@Test(priority=8,description="This testcase is to search the program, testSavedSearch" , dependsOnMethods = {"testcase05"})
public void testcase08() throws Exception {
	System.out.println("############################Starting testcase8 ############################");
	CommonUtils.hold(2);
	CommonUtils.explicitWait(PageTemplateFusePlus.showFilters, "Click", "", ManageNegotiationsDriver);
	PageTemplateFusePlus.showFilters.click();
     CommonUtils.hold(2);
	 pageTempFuseUtil.verifySavedSearch("testSavedSearch",ManageNegotiationsDriver);
    System.out.println("Verified Successfully");
    PageTemplateFusePlus.showFilters.click();
  
 	
 }
@Test(priority=9,description="This testcase is to search the program, updateSavedSearch" , dependsOnMethods = {"testcase06"})
public void testcase09() throws Exception {	
	System.out.println("############################Starting testcase9 ############################");
	CommonUtils.hold(2);
	CommonUtils.explicitWait(PageTemplateFusePlus.showFilters, "Click", "", ManageNegotiationsDriver);
	PageTemplateFusePlus.showFilters.click();
	 CommonUtils.hold(2);
	 pageTempFuseUtil.verifySavedSearch("updateSavedSearch",ManageNegotiationsDriver);
	 CommonUtils.hold(2);
 	System.out.println("Verified Successfully");
 	PageTemplateFusePlus.showFilters.click();
  
 }
@Test(priority=10,description="This testcase is to search the program, latestSavedSeach" , dependsOnMethods = {"testcase07"})
public void testcase10() throws Exception {	
	System.out.println("############################Starting testcase10 ############################");
	CommonUtils.hold(2);
	CommonUtils.explicitWait(PageTemplateFusePlus.showFilters, "Click", "", ManageNegotiationsDriver);
	PageTemplateFusePlus.showFilters.click();
	CommonUtils.hold(2);
	 pageTempFuseUtil.verifySavedSearch("latestSavedSearch",ManageNegotiationsDriver);
	 CommonUtils.hold(2);
 	System.out.println("Verified Successfully");
 	PageTemplateFusePlus.showFilters.click();
   
 }
@Test(priority=11,description="Delete saved seaches" )
public void testcase11() throws Exception {	
	System.out.println("############################Starting testcase11 ############################");

	 CommonUtils.hold(2);
	 PageTemplateFusePlus.showFilters.click();
     CommonUtils.hold(10);
	 Select dropdown = new Select(ManageNegotiationsDriver.findElement(By.id("_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSQrySS::content")));
	 dropdown.selectByVisibleText("Manage...");
	 CommonUtils.hold(10);
	 int rows = Integer.parseInt(PageTemplateFusePlus.managedSavedSearchTable.getAttribute("_rowcount"));
	 for(int i = 0; i < rows-1; i++) {
		 PageTemplateFusePlus.deleteSavedSearchButton.click();
		 CommonUtils.hold(5);
	 }
	 PageTemplateFusePlus.managedSavedSeachApplyButton.click();
	 CommonUtils.hold(5);
	 PageTemplateFusePlus.managedSavedSeachOkButton.click();
	 CommonUtils.hold(5);
 	  dropdown = new Select(ManageNegotiationsDriver.findElement(By.id("_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSQrySS::content")));
	 dropdown.selectByVisibleText("Manage...");
	 CommonUtils.hold(10);
	  rows = Integer.parseInt(PageTemplateFusePlus.managedSavedSearchTable.getAttribute("_rowcount"));
	  System.out.println(rows);
		assertEquals(rows,1,"All Saved filter are not deleted " );
		System.out.println("Verified Successfully");
 }

@AfterClass
public void logOut() throws InterruptedException {
		try {
			aLoginInstance.logout(ManageNegotiationsDriver);

		} catch (Exception ex) {
			aLoginInstance.launchURL(ManageNegotiationsDriver);
			CommonUtils.hold(5);
			aLoginInstance.logout(ManageNegotiationsDriver);
		}finally{
			ManageNegotiationsDriver.quit();
	}
	}

}

