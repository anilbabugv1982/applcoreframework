package UI.tests.Flex.EFF;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import TestBase.UI.*;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Eff.Deployment;

public class EFFFlatFileImport extends GetDriverInstance {
	private WebDriver effPIMImportDriver = null;
	private Deployment dep = null;
	private ApplicationLogin aLoginInstance = null;
	private NavigationTaskFlows ntFInstance = null;
	private NavigatorMenuElements nMEInstance = null;
	
	@BeforeClass
	public void setup() throws Exception {
		effPIMImportDriver = getDriverInstanceObject();
		dep = new Deployment(effPIMImportDriver);
		aLoginInstance = new ApplicationLogin(effPIMImportDriver);
		ntFInstance = new NavigationTaskFlows(effPIMImportDriver);
		nMEInstance = new NavigatorMenuElements(effPIMImportDriver);
	}
	
	@Test()
	public void navigateToImport() throws Exception {
		aLoginInstance.login("APP_IMPL_CONSULTANT", "Welcome1",effPIMImportDriver);
		CommonUtils.hold(5);
		ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance,effPIMImportDriver);
		ntFInstance.navigateToAOLTaskFlows("Manage Item Classes",effPIMImportDriver);
		CommonUtils.hold(5);
	}
	
	@AfterClass(alwaysRun = true)
	public void Logout() throws InterruptedException{
		try {
			aLoginInstance.logout(effPIMImportDriver);
			

		} catch (Exception ex) {
			aLoginInstance.launchURL(effPIMImportDriver);
			CommonUtils.hold(2);
			aLoginInstance.logout(effPIMImportDriver);
		}finally {
			try {
				effPIMImportDriver.quit();
				} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception while closing db connection ");
			}
			
		}
	
	}

}
