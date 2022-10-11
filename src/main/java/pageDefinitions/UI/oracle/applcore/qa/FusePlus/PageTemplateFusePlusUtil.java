package pageDefinitions.UI.oracle.applcore.qa.FusePlus;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.FusePlus.PageTemplateFusePlus;

public class PageTemplateFusePlusUtil extends PageTemplateFusePlus{
	String  pid;
	HashMap<String, Integer> seachRowCountMaps = new HashMap<String, Integer>();
	
	public PageTemplateFusePlusUtil(WebDriver driver) {
		super(driver);
		 pid=CommonUtils.uniqueId();
	
	}
	public  void  verifySavedSearch(String savedSeachName,WebDriver driver) throws Exception {
		System.out.println("############################Starting method verifysavedsearch ############################");

		 savedSeachName = savedSeachName + pid;
		Select dropdown = new Select(driver.findElement(By.id("_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:ls1:_LSQrySS::content")));
		CommonUtils.hold(10);
		dropdown.selectByVisibleText(savedSeachName);
		//DriverInstance.getDriverInstance().findElement(By.xpath("//option[@value='"+savedSeachName + pid+"']"));
		//PageTemplateFusePlus.savedSeachDropDown.sendKeys(savedSeachName + pid);
		CommonUtils.explicitWait(PageTemplateFusePlus.resultTable, "Click", "", driver);
		CommonUtils.hold(2);
		System.out.println("########################## "+PageTemplateFusePlus.resultTable.getAttribute("_rowcount"));
		System.out.println("##########################::: "+seachRowCountMaps.get(savedSeachName));
		Assert.assertEquals(seachRowCountMaps.get(savedSeachName), new Integer(Integer.parseInt(PageTemplateFusePlus.resultTable.getAttribute("_rowcount"))));
	}
	public  void saveSearch(String searchKey, String saveSearchName,WebDriver driver) {
		System.out.println("############################Starting savesearch menthod ############################");

		saveSearchName = saveSearchName + pid;
		PageTemplateFusePlus.title.clear();
		PageTemplateFusePlus.title.sendKeys(searchKey);
	     CommonUtils.hold(5);
	     PageTemplateFusePlus.title.sendKeys(Keys.ENTER);
	     CommonUtils.hold(5);
	     PageTemplateFusePlus.save.click();
	     CommonUtils.hold(8);
	     PageTemplateFusePlus.savedSearchName.clear();
	     CommonUtils.hold(3);
	     PageTemplateFusePlus.savedSearchName.sendKeys(saveSearchName);
	     CommonUtils.hold(5);
	     PageTemplateFusePlus.ok.click();
	     CommonUtils.hold(10);
	     seachRowCountMaps.put(saveSearchName,Integer.parseInt(PageTemplateFusePlus.resultTable.getAttribute("_rowcount")));
	}
	public  void VerifySavedProgram(String testName ,WebDriver driver) {
		
		System.out.println("Inside verify create programe");
		PageTemplateFusePlus.searchSavedProgram.clear();
		PageTemplateFusePlus.searchSavedProgram.sendKeys(testName);
		PageTemplateFusePlus.manageProgramsSearchButton.click();
		 CommonUtils.hold(10);
		assertTrue(driver.findElement(By.xpath("//a[contains(text(),'"+testName+"')]")).isDisplayed());
		
		System.out.println("Verified Successfully");
	}
	public  void createProgram(String testName,WebDriver driver ) {
		
		System.out.println("Step6: Click on create button");
		PageTemplateFusePlus.createMP.click();
		CommonUtils.hold(15);
		String pt1 = testName + pid;
		System.out.println("Step7: Set Program Title: " + pt1);
	    PageTemplateFusePlus.programTitleMP.sendKeys(pt1);
        CommonUtils.hold(5);
 	    PageTemplateFusePlus.dateMP.sendKeys(CommonUtils.currentDate("M/d/YY"));
	    CommonUtils.hold(1);
 	    PageTemplateFusePlus.saveAndCloseMP.click();
 	    CommonUtils.hold(8);
 	    VerifySavedProgram(pt1,driver);
		
  }
	public  void navigateToManagePrograms(WebDriver driver) throws Exception {
	System.out.println("############################Starting navigating to manage Program ############################");
    System.out.println("Step1: Click on navigation");
    CommonUtils.hold(4);
    PageTemplateFusePlus.navigatorFusePlusButton.click();
    System.out.println("Step2: Click Negotiations");
    CommonUtils.hold(8);
    PageTemplateFusePlus.expandProcureButton.click();
	CommonUtils.hold(5);
	PageTemplateFusePlus.negotiations.click();
	//CommonUtils.hold(12);
	CommonUtils.explicitWait(PageTemplateFusePlus.rightTasksMenu,"Click" ," ", driver);
	System.out.println("Step3: Click on Tasks Menu");
	PageTemplateFusePlus.rightTasksMenu.click();
	//CommonUtils.hold(8);
	CommonUtils.explicitWait(PageTemplateFusePlus.managePrograms,"Click" ," ",driver);
	System.out.println("Step5: Click on Manage Programs link");
    PageTemplateFusePlus.managePrograms.click();
	CommonUtils.hold(12);
	}
	
}
