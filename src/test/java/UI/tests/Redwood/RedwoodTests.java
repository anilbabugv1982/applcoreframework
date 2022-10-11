package UI.tests.Redwood;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.Redwood.RedwoodPage;
import pageDefinitions.UI.oracle.applcore.qa.Redwood.RedwoodUtil;
import pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus.ResponsiveFusePlusPage;
import pageDefinitions.UI.oracle.applcore.qa.ResponsiveFusePlus.ResponsiveFusePlusUtil;

public class RedwoodTests  extends GetDriverInstance {

	WebDriver RedwoodDriver;
	private ApplicationLogin aLoginInstance;
	private RedwoodPage redwoodpages;
	private RedwoodUtil redwoodutil;
	
	@Parameters({ "user", "pwd" })
	@BeforeClass()
	//public void signIn(String username, String password) throws Exception {
	public void signIn() throws Exception {

		RedwoodDriver = getDriverInstanceObject();

		aLoginInstance = new ApplicationLogin(RedwoodDriver);
		redwoodpages = new RedwoodPage(RedwoodDriver);
		redwoodutil = new RedwoodUtil(RedwoodDriver);
		aLoginInstance.login("finuser1", "Welcome1", RedwoodDriver);

	}
	
	@Test(priority = 1)
	public void checkHorTileBarColour() throws Exception {
		redwoodutil.navigateToInvoice();
		redwoodutil.checkHorBarColour();
	}
	
	@Test(priority = 2)
	public void checkVerTileBarColour() throws Exception {
		redwoodutil.navigateToReceivable();
		redwoodutil.checkVerBarColour();
		
	}
	
	@Test(priority = 3)
	public void validateRedwoodHeaderFont() throws Exception {
		redwoodutil.navigateToInvoice();
		redwoodutil.checkRedwoodHeaderFont();
		
	}
	@Test(priority = 4)
	public void validateRedwoodHeaderLinkColor() throws Exception {
		//redwoodutil.navigateToInvoice();
		redwoodutil.checkRedwoodHeaderLinkColor();
		
	}

	@Test(priority = 5)
	public void validateDynamicTab() throws Exception {
		redwoodutil.navigateToPersonManagement();
		redwoodutil.checkDynamicTabBarColour();
		
	}
	
	@Test(priority = 6)
	public void checkCommandLinkColour() throws Exception {
		redwoodutil.checkCommandLinkColour(RedwoodPage.showMore);
		aLoginInstance.logout(RedwoodDriver);
		CommonUtils.hold(5);
		RedwoodDriver.quit();
		
	}
}