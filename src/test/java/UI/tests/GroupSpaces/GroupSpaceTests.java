package UI.tests.GroupSpaces;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.GroupSpaces.GroupSapcePage;

public class GroupSpaceTests extends GetDriverInstance{

	static String uniqueID;

	private WebDriver GroupSpaceWebDriver =null;
	private ApplicationLogin aLoginInstance;
	private NavigationTaskFlows ntFInstance;
	private NavigatorMenuElements nMEInstance;
	private GroupSapcePage groupSpacePage;
	public GroupSpaceTests() throws WebDriverException {
		
	}

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		GroupSpaceWebDriver =  getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(GroupSpaceWebDriver);
		ntFInstance = new NavigationTaskFlows(GroupSpaceWebDriver);
		nMEInstance = new NavigatorMenuElements(GroupSpaceWebDriver);
		groupSpacePage=new GroupSapcePage(GroupSpaceWebDriver);
		//ApplicationLogin.login(user, passWord, driver);
		aLoginInstance.login(user, passWord, GroupSpaceWebDriver);
		CommonUtils.hold(3);
		
		
		ntFInstance.navigateToTask(GroupSapcePage.groupspace,GroupSpaceWebDriver);
			
		}
	
	@Test(priority = 1)
	public void createportal() throws Exception {
		GroupSpaceWebDriver.switchTo().frame("pt1:inlineFrame1::f");
		CommonUtils.hold(2);
		GroupSapcePage.createlink.click();
		//CommonUtils.hold(3);
		//GroupSapcePage.usethis.click();
		//CommonUtils.hold(2);
		//GroupSapcePage.createbutton.click();
		GroupSpaceWebDriver.switchTo().defaultContent();
	}
	
    @AfterClass(alwaysRun=true)
    public void logOut() throws InterruptedException {
 
        CommonUtils.hold(2);
        aLoginInstance.logout(GroupSpaceWebDriver);
        CommonUtils.hold(2);
 
    }
	

}
