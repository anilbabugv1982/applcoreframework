package UI.tests.FusePlus;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.FusePlus.PageTemplateFusePlus;
import pageDefinitions.UI.oracle.applcore.qa.FusePlus.PurchaseUtil;

public class PurchaseTest extends GetDriverInstance {
	String pid;
	WebDriver PurchaseTestDriver;
	PurchaseUtil purchaseutil;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
/*public PurchaseTest() throws WebDriverException {
		
	
		}*/

	@BeforeClass(alwaysRun = true)
	public void signIn() throws Exception {
		
		PurchaseTestDriver= getDriverInstanceObject();
		purchaseutil=new PurchaseUtil(PurchaseTestDriver);
		aLoginInstance = new ApplicationLogin(PurchaseTestDriver);
		ntFInstance = new NavigationTaskFlows(PurchaseTestDriver);
		nMEInstance = new NavigatorMenuElements(PurchaseTestDriver);
        Thread.sleep(1000);
             
        pid=CommonUtils.uniqueId();
        
        aLoginInstance.login("cvrqst01", "Welcome1",PurchaseTestDriver);
	}
	
	@Test(priority=1,description="PurchaseRequisitions test1")
	public void testcase01() throws Exception{		
		System.out.println("############################PurchaseRequisistions#############################3");
	    CommonUtils.hold(6);
	    System.out.println("Step1: Click on navigation");
	    purchaseutil.waitAndClick(PageTemplateFusePlus.navigatorFusePlusButton,PurchaseTestDriver);
		CommonUtils.hold(5);
		System.out.println("Step2: Click on Purchase Requisitions");
		purchaseutil.waitAndClick(PageTemplateFusePlus.expandProcureButton,PurchaseTestDriver);
		CommonUtils.hold(5);
		purchaseutil.waitAndClick(PageTemplateFusePlus.purchaseRequisitions,PurchaseTestDriver);
		//CommonUtils.hold(10);
		System.out.println("Step3: Set Laptop on search filed and press ENTER key");
		purchaseutil.waitAndSendKeys(PageTemplateFusePlus.searchInputField, "Laptop",PurchaseTestDriver);
		//CommonUtils.hold(2);
		PageTemplateFusePlus.searchInputField.sendKeys(Keys.ENTER);
		CommonUtils.hold(5);
		System.out.println("Step4: Click on ListView button and get the table rowcount");
		purchaseutil.waitAndClick(PageTemplateFusePlus.listViewButton,PurchaseTestDriver);
		CommonUtils.hold(5);
		int rowCount = Integer.parseInt(PageTemplateFusePlus.listViewTable.getAttribute("_rowcount"));
		System.out.println("Step5: Click on Card view and verify");
		PageTemplateFusePlus.cardViewButton.click();
		CommonUtils.hold(5);
		CommonUtils.explicitWait(PageTemplateFusePlus.f1, "Visible", "",PurchaseTestDriver);
		System.out.println("Step6: Validating Item Count per page and previous button");
		Assert.assertEquals(PageTemplateFusePlus.f1.getText(), "1");
		Assert.assertEquals(PageTemplateFusePlus.fPrevious.getAttribute("aria-disabled"), "true");
		int itemCountPerPage = Integer.parseInt(PageTemplateFusePlus.f2.getText());
		Assert.assertTrue(itemCountPerPage<=rowCount);
		int itemCount = itemCountPerPage;
		System.out.println("Step7: click on next button and validate the item count");
		while(itemCount != rowCount){
			PageTemplateFusePlus.fNext.click();
			CommonUtils.hold(10);
			Assert.assertEquals(Integer.parseInt(PageTemplateFusePlus.f1.getText()), itemCount+1);
			itemCount = Integer.min(itemCount+itemCountPerPage, rowCount);
			Assert.assertEquals(Integer.parseInt(PageTemplateFusePlus.f2.getText()), itemCount);
		}
		System.out.println("Step8: Validating next button");
		Assert.assertEquals(PageTemplateFusePlus.fNext.getAttribute("aria-disabled"), "true");
		System.out.println("Step9: click on previous button and validate the item count");
		int f1Value = Integer.parseInt(PageTemplateFusePlus.f1.getText());
        int f2Value = rowCount;
		while(f1Value != 1){
			PageTemplateFusePlus.fPrevious.click();
			CommonUtils.hold(10);
			f1Value = f1Value - itemCountPerPage;
			f2Value = f1Value + itemCountPerPage - 1;
			Assert.assertEquals(Integer.parseInt(PageTemplateFusePlus.f1.getText()), f1Value);
			Assert.assertEquals(Integer.parseInt(PageTemplateFusePlus.f2.getText()), f2Value);
		}
		CommonUtils.hold(4);
		System.out.println("Verified successfully");
	}
	
	
	@AfterClass
	public void logOut() throws InterruptedException {
		try {
			aLoginInstance.logout(PurchaseTestDriver);

		} catch (Exception ex) {
			aLoginInstance.launchURL(PurchaseTestDriver);
			CommonUtils.hold(5);
			aLoginInstance.logout(PurchaseTestDriver);
		}finally{
			PurchaseTestDriver.quit();
		}
	}
}
