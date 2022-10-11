package UI.tests.datasecurity;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.ds.DataSecurityWrapper;

public class DataSecurityTest extends GetDriverInstance{

	private String dsunique = "";
	private WebDriver isoDriver = null;
	private ApplicationLogin appLogin = null;
	private DataSecurityWrapper dsUtils = null;
	
	
	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void startUP(String username, String password) throws Exception {

		try {
			isoDriver =  getDriverInstanceObject();
			appLogin = new ApplicationLogin(isoDriver);
			dsUtils = new DataSecurityWrapper(isoDriver);
			appLogin.login(username, password, isoDriver);
			CommonUtils.hold(5);
			dsunique = CommonUtils.uniqueId();
			dsUtils.navigateToDStaskFlow();
			CommonUtils.hold(10);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in DataSecurityTest class:  " + e.getMessage());
			e.printStackTrace();
		}

	}

	@Test(description="Add new DataBase object")
	public void testcase01() throws Exception {
		dsUtils.createDataBaseObject("DS" + dsunique);
		Assert.assertTrue(dsUtils.searchDSObject("DS" + dsunique));
	}

	@Test(dependsOnMethods = { "testcase01" },description="Add new DataBase condition")
	public void testcase02() throws Exception {
		dsUtils.createCondition("DS" + dsunique, "DSCON_" + dsunique);

	}

	@Test(dependsOnMethods = { "testcase02" }, description="Add new DataBase action")
	public void testcase03() throws Exception {
		dsUtils.createAcion("DS" + dsunique, "DSACT" + dsunique);
	}

	@Test(dependsOnMethods = { "testcase03" }, description="Add new DataBase policy")
	public void testcase04() throws Exception {
		dsUtils.createDataBasePolicy("DS" + dsunique, "DSPOL_" + dsunique);
	}

	@Test(dependsOnMethods = { "testcase04" }, description="Update new DS object")
	public void testcase05() throws Exception {
		dsUtils.updateObject("DS" + dsunique, "condition", "DSCON_" + dsunique, "UPDATE_UNIQUE");
	}

	@Test(dependsOnMethods = { "testcase05" }, description="Update new DS object action")
	public void testcase06() throws Exception {
		dsUtils.updateObject("DS" + dsunique, "action", "DSACT" + dsunique, "UPDATE_UNIQUE");
	}

	@Test(dependsOnMethods = { "testcase06" }, description="Update new DS object policy")
	public void testcase07() throws Exception {
		dsUtils.updateObject("DS" + dsunique, "policy", "DSPOL_" + dsunique, "UPDATE_UNIQUE1234");
	}

	@Test(dependsOnMethods = { "testcase07" }, description="Delete DS object policy")
	public void testcase08() throws Exception {
		dsUtils.deleteResource("DS" + dsunique, "DSPOL_" + dsunique, "policy");
	}

	@Test(dependsOnMethods = { "testcase08" }, description="Delete DS object Condition")
	public void testcase09() throws Exception {
		dsUtils.deleteResource("DS" + dsunique, "DSCON_" + dsunique, "condition");
	}

	@Test(dependsOnMethods = { "testcase09" }, description="Delete DS object Action")
	public void testcase10() throws Exception {
		dsUtils.deleteResource("DS" + dsunique, "DSACT" + dsunique, "action");
	}

	@Test(dependsOnMethods = { "testcase10" }, description="Delete DS object")
	public void testcase11() throws Exception {
		dsUtils.deleteResource("DS" + dsunique, "", "");
	}

	@AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {        
        isoDriver.quit();
    }
}
