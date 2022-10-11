package UI.tests.Flex.EFF;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Eff.Deployment;
import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Eff.RuntimeValidation;

public class EFFPIMItemProfileUpdate extends GetDriverInstance {
	WebDriver effPIMProfileUpdatedriver;
	private EFFUtils effUtils;
	private Deployment dep;
	private RuntimeValidation pimRunTime;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private static String itemClass="Root Item Class";
	private String itemName;
	
	@BeforeClass
	public void startUP() throws Exception {
		
		try{
		        effPIMProfileUpdatedriver = getDriverInstanceObject();
				dep = new Deployment(effPIMProfileUpdatedriver);
				pimRunTime = new RuntimeValidation(effPIMProfileUpdatedriver);
				aLoginInstance = new ApplicationLogin(effPIMProfileUpdatedriver);
			    ntFInstance = new NavigationTaskFlows(effPIMProfileUpdatedriver);
			    nMEInstance = new NavigatorMenuElements(effPIMProfileUpdatedriver);
			    effUtils = new EFFUtils(effPIMProfileUpdatedriver);
			   /*dbConnection = new DbResource(effPIMTest);*/
			    CommonUtils.hold(5);
		}catch(Exception ex){
			System.out.println("EFF Error message = ===== : "+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	
	@Test(description = "Check whether Updateable Item Profile Value is set to NO or not")
	public void CheckItemProfile() throws Exception {
		aLoginInstance.login("app_impl_consultant", "Welcome1",effPIMProfileUpdatedriver);
		CommonUtils.hold(15);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effPIMProfileUpdatedriver);
		CommonUtils.hold(15);
		ntFInstance.navigateToAOLTaskFlows("Manage Administrator Profile Values", effPIMProfileUpdatedriver);
		dep.setItemProfileValue();
		Assert.assertTrue(dep.getItemProfileValue(), "Updateable Item Profile Value is set to NO");
		aLoginInstance.logout(effPIMProfileUpdatedriver);
	}
	
	
    @Test(dependsOnMethods = {"CheckItemProfile"},description = "Ensure Item is created and saved properly with Item name disabled after saving it")
	public void createItemToTestProfile() throws Exception {
    	itemName = "v_"+itemClass+CommonUtils.uniqueId();
    	System.out.println("Item name to be created  is -"+itemName);
        aLoginInstance.login("pimqa", "Welcome1",effPIMProfileUpdatedriver);
        CommonUtils.hold(5);
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().productInformationManagement,effPIMProfileUpdatedriver);
        CommonUtils.hold(5);
        Assert.assertTrue(pimRunTime.createItem(itemName, "Item", itemClass, effPIMProfileUpdatedriver), "Validation successful for Item creation");
	}
    
    @AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(effPIMProfileUpdatedriver);			

		} catch (Exception ex) {
			aLoginInstance.launchURL(effPIMProfileUpdatedriver);
			CommonUtils.hold(2);
			aLoginInstance.logout(effPIMProfileUpdatedriver);
		}finally {
			try {
				effPIMProfileUpdatedriver.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}
}
