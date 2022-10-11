package UI.tests.Preferences;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import pageDefinitions.UI.oracle.applcore.qa.Preferences.PreferencesUtils;

public class PreferencesTest extends GetDriverInstance {

	private WebDriver prefTestDriver = null;
	private ApplicationLogin appLogin = null;
	private PreferencesUtils pUtils = null;

	public String defaultTerritory = "United States";
	public String defaultTimezone = "(UTC+00:00) Coordinated Universal Time (UTC)";

	public String newTerritory = "United Kingdom";
	public String newTimeZone = "(UTC+00:00) London - Greenwich Mean Time (GMT)";
	public String newNumberFormat = "-1,234.567";
	public String newTimeFormat = "HH:mm";
	public String newDateFormat = "dd/MM/yy";

	private String adminnewTerritory = "Switzerland";
	private String adminnewTimeZone = "(UTC-07:00) Denver - Mountain Time (MT)";
//	public String adminnewNumberFormat = "-1'234.567";
//	public String adminnewTimeFormat = "HH:mm";
//	public String adminnewDateFormat = "dd.MM.yy";
//	public String adminnewCurrency = "Swiss Franc";

	@BeforeClass
	public void setUp() throws Exception {
		try {
			prefTestDriver = getDriverInstanceObject();
			pUtils = new PreferencesUtils(prefTestDriver);
			appLogin = new ApplicationLogin(prefTestDriver);
			appLogin.login("mcooper", "Welcome1", prefTestDriver);
			CommonUtils.hold(10);
		} catch (Exception e) {
			System.out.println("Exception in initializing objects in PreferencesTest class:  " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Test(description = "Set regional preference for an user")
	public void testcase01() throws Exception {
		pUtils.setRegionalPreference(newTerritory, newTimeZone,prefTestDriver);
		pUtils.verfyRegionalPreference("mcooper", prefTestDriver);
		pUtils.setRegionalPreference(defaultTerritory, defaultTimezone,prefTestDriver);
	}

	@Test(description = "Set language preference - Curresnt session")
	public void testcase02() throws Exception {
		pUtils.setLanguage("Korean", "current",prefTestDriver);
		pUtils.verifyLanguageSettings("current", "mcooper");
	}

//	// @Test(description = "Set language preference - Default session")
//	public void testcase03() throws Exception {
//		pUtils.setLanguage("Korean", "default");
//		pUtils.verifyLanguageSettings("default", "mcooper");
//	}

	@Test(description = "Enable screen reader-mode for an user")
	public void testcase04() throws Exception {
		pUtils.setScreenReaderMode(prefTestDriver);
		pUtils.verifyScreenReaderMode("mcooper");
	}

	@Test(description = "Change password for an user")
	public void testcase05() throws Exception {
		pUtils.resetPassword("Welcome1", "Welcome2",prefTestDriver);
		pUtils.verifyResetPassword("mcooper", "Welcome2");
		pUtils.resetPassword("Welcome2", "Welcome1",prefTestDriver);
	}

	@Test(description = "Set user global general preference")
	public void testcase06() throws Exception {
		pUtils.setAdminPreference(adminnewTerritory, adminnewTimeZone,prefTestDriver);
	}

	@Test(description = "Verify user global general preference", dataProvider = "Users", dependsOnMethods = "testcase06")
	public void testcase07(String user) throws Exception {
		pUtils.verifyAdminPreference(user,prefTestDriver);
	}

	@DataProvider(name = "Users")
	public Object[][] testData() throws Exception {

		return new Object[][] { { "finuser1" }, { "application_developer" }, { "hcm_user10" }, { "hr_spec_all" },
				{ "app_impl_consultant" } };

	}

	@Test(description = "Revert user global general preference", dependsOnMethods = "testcase07")
	public void testcase08() throws Exception {
		pUtils.setAdminPreference(defaultTerritory, defaultTimezone,prefTestDriver);
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException {
		prefTestDriver.quit();
	}
}
