package UI.tests.Loaders;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.NavigationTaskFlows;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ServiceRequestPage;
import pageDefinitions.UI.oracle.applcore.qa.Loader.EssJobUsingCSVLoader;
import pageDefinitions.UI.oracle.applcore.qa.Loader.LoaderWrapper;
import pageDefinitions.UI.oracle.applcore.qa.Loader.ManageStandardLookup;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageAdministratorProfileTest extends GetDriverInstance {

	static String pid;
	String srName;
	LoaderWrapper loader;
	private ApplicationLogin aLoginInstance;
	WebDriver loaderDriver;
	private NavigationTaskFlows ntFInstance;
	ServiceRequestPage sr;
	ManageStandardLookup standardLookup;
	private String expImpURL;
	private String taskListURL;
	private EssJobUsingCSVLoader essLoaderUtil;
	private List<Integer> profileData;

	@Parameters({"user1", "pwd1"})
	@BeforeClass
	public void setUp(String user1, String passWord) throws Exception {
		loaderDriver = getDriverInstanceObject();
		pid = CommonUtils.uniqueId();
		aLoginInstance = new ApplicationLogin(loaderDriver);
		standardLookup = new ManageStandardLookup(loaderDriver);
		ntFInstance = new NavigationTaskFlows(loaderDriver);
		loader = new LoaderWrapper(loaderDriver);
		sr = new ServiceRequestPage(loaderDriver);
		essLoaderUtil = new EssJobUsingCSVLoader(loaderDriver);
		aLoginInstance.login(user1, passWord, loaderDriver);
		CommonUtils.waitForInvisibilityOfElement(aLoginInstance.username, loaderDriver, 10);
		DbResource.sqlStatement.executeUpdate(loader.cleanProfileData(user1));
		DbResource.sqlStatement.executeUpdate(loader.gatherStats());
		profileData = new ArrayList<Integer>();
		setURLS();
	}


	@Test(priority = 1, description = "Import file ")
	public void ImportDataFile1() {

		loaderDriver.get(expImpURL);
		CommonUtils.hold(2);

		String s = "PROFILE";
		String s1 = ".txt";
		String s2 = "";
		for (int i = 1; i <= 6; i++) {
			s2 = s + i + s1;
			loader.uploadAttachments(s2, loaderDriver);
			s2 = "";
		}

	}

	@Test(priority = 2, description = "Import ProfileOptions Values1")
	public void ImportProfile1() {
		try {
			profileData.clear();
			profileData.add(5);
			profileData.add(1);
			profileData.add(4);
			profileData.add(0);

			loaderDriver.get(taskListURL);
			CommonUtils.hold(4);
			ntFInstance.navigateToAOLTaskFlows("Manage Administrator Profile Values", loaderDriver);
			String newFileName = "ManageAdministratorProfileTest01_";
			String profile1 = "PROFILE1.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importProfile(profile1, loaderDriver);
			loader.renameFile("user_profiles_import_logfile.txt", newFileName);
			Assert.assertTrue(essLoaderUtil.ValidateEssLogs("USER_PROFILE_VALUES", profileData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
			//loader.verifyUploadDataOnProfileUI(loaderDriver, "FND_FONT_SIZE", "ABRAHAM.MASON", "Medium");
			loader.verifyUploadDataOnProfileUI(loaderDriver, "AFLOG_LEVEL", "MCOOPER", "Finest");

			profileData.clear();
		} catch (Exception exp) {
			System.out.println("Error: Exception while executing test ImportProfile1() : " + exp.getMessage());
			Assert.fail();
		}
	}

	@Test(priority = 3, description = "Import ProfileOptions Values2")
	public void ImportProfile2() {
		try {
			profileData.clear();
			profileData.add(5);
			profileData.add(4);
			profileData.add(0);
			profileData.add(1);

			CommonUtils.hold(2);
			String newFileName = "ManageAdministratorProfileTest02_";
			String profile1 = "PROFILE2.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importProfile(profile1, loaderDriver);
			loader.renameFile("user_profiles_import_logfile.txt", newFileName);
			Assert.assertTrue(essLoaderUtil.ValidateEssLogs("USER_PROFILE_VALUES", profileData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
			loader.verifyUploadDataOnProfileUI(loaderDriver, "FND_COLOR_CONTRAST", "Daniel.Langer", "Standard");
			profileData.clear();
		} catch (Exception exp) {
			System.out.println("Error: Exception while executing test ImportProfile2() : " + exp.getMessage());
			Assert.fail();
		}
	}

	@Test(priority = 4, description = "Import ProfileOptions Values3")
	public void ImportProfile3() {
		try {
			profileData.clear();
			profileData.add(4);
			profileData.add(3);
			profileData.add(1);
			profileData.add(0);

			CommonUtils.hold(2);
			String newFileName = "ManageAdministratorProfileTest03_";
			String profile1 = "PROFILE3.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importProfile(profile1, loaderDriver);
			loader.renameFile("user_profiles_import_logfile.txt", newFileName);
			Assert.assertTrue(essLoaderUtil.ValidateEssLogs("USER_PROFILE_VALUES", profileData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
			loader.verifyUploadDataOnProfileUI(loaderDriver, "AFLOG_FILENAME", "ABRAHAM.MASON", "@#$5^&i/<>,.?uyriyiiy()+=~");
			CommonUtils.hold(2);
			profileData.clear();
		} catch (Exception exp) {
			System.out.println("Error: Exception while executing test ImportProfile3() : " + exp.getMessage());
			Assert.fail();
		}
	}

	@Test(priority = 5, description = "Import ProfileOptions Values4")
	public void ImportProfile4() {
		try {
			profileData.clear();
			profileData.add(24);
			profileData.add(14);
			profileData.add(8);
			profileData.add(2);

			CommonUtils.hold(3);
			String newFileName = "ManageAdministratorProfileTest04_";
			String profile1 = "PROFILE4.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importProfile(profile1, loaderDriver);
			loader.renameFile("user_profiles_import_logfile.txt", newFileName);
			Assert.assertTrue(essLoaderUtil.ValidateEssLogs("USER_PROFILE_VALUES", profileData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
			loader.verifyUploadDataOnProfileUI(loaderDriver, "AFLOG_FILENAME", "APPLICATION_DEVELOPER", "filename_test.txt");
			loader.verifyUploadDataOnProfileUI(loaderDriver, "AFLOG_FILENAME", "INTEGRATION_SPECIALIST", "filename_test12345.txt");
		} catch (Exception exp) {
			System.out.println("Error: Exception while executing test ImportProfile4() : " + exp.getMessage());
			Assert.fail();
		}
	}

	@Test(priority = 6, description = "Import ProfileOptions Values5")
	public void ImportProfile5() {
		try {
			profileData.clear();
			profileData.add(2);
			profileData.add(2);
			profileData.add(0);
			profileData.add(0);

			CommonUtils.hold(3);
			String newFileName = "ManageAdministratorProfileTest05_";
			String profile1 = "PROFILE5.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importProfile(profile1, loaderDriver);
			loader.renameFile("user_profiles_import_logfile.txt", newFileName);
			Assert.assertTrue(essLoaderUtil.ValidateEssLogs("USER_PROFILE_VALUES", profileData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
			loader.verifyUploadDataOnProfileUI(loaderDriver, "AFLOG_FILENAME", "INTEGRATION_SPECIALIST", "filename_test12345.txt");
			profileData.clear();
		} catch (Exception exp) {
			System.out.println("Error: Exception while executing test ImportProfile5() : " + exp.getMessage());
			Assert.fail();
		}
	}

	@Test(priority = 7, description = "Import ProfileOptions Values6")
	public void ImportProfile6() {
		try {
			profileData.clear();
			profileData.add(2);
			profileData.add(0);
			profileData.add(2);
			profileData.add(0);

			CommonUtils.hold(3);
			String newFileName = "ManageAdministratorProfileTest06_";
			String profile1 = "PROFILE6.txt";
			newFileName = newFileName + pid + ".txt";
			loader.importProfile(profile1, loaderDriver);
			loader.renameFile("user_profiles_import_logfile.txt", newFileName);
			Assert.assertTrue(essLoaderUtil.ValidateEssLogs("USER_PROFILE_VALUES", profileData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
			loader.verifyUploadDataOnProfileUI(loaderDriver, "AFLOG_FILENAME", "Site", "filename_test.log");
			profileData.clear();
		} catch (Exception exp) {
			System.out.println("Error: Exception while executing test ImportProfile6() : " + exp.getMessage());
			Assert.fail();
		}
	}


	public void setURLS() {
		String actualURL = GetDriverInstance.EnvironmentName;
		expImpURL = actualURL.replaceAll("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_file_import_and_export");
		taskListURL = actualURL.replaceAll("FuseWelcome", "FuseTaskListManagerTop");
	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException, SQLException {
		loaderDriver.quit();

	}
}
