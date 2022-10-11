package UI.tests.Loaders;

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ServiceRequestPage;
import pageDefinitions.UI.oracle.applcore.qa.Loader.LoaderWrapper;
import pageDefinitions.UI.oracle.applcore.qa.Loader.ManageStandardLookup;

public class LoadFiles extends GetDriverInstance {
	static String pid;
	String srName;
	LoaderWrapper loader;
	private ApplicationLogin aLoginInstance;
	WebDriver LoaderDriver;
	private NavigationTaskFlows ntFInstance;
	ServiceRequestPage sr;
	ManageStandardLookup standardLookup;

	@Parameters({ "user", "pwd" })
	@BeforeClass

	public void setUp(String user, String passWord) throws Exception {
		LoaderDriver = getDriverInstanceObject();
		pid = CommonUtils.uniqueId();
		aLoginInstance = new ApplicationLogin(LoaderDriver);
		aLoginInstance.login(user, passWord, LoaderDriver);
		CommonUtils.hold(3);
		standardLookup = new ManageStandardLookup(LoaderDriver);
		ntFInstance = new NavigationTaskFlows(LoaderDriver);
		loader = new LoaderWrapper(LoaderDriver);
		sr = new ServiceRequestPage(LoaderDriver);

	}

	@Test(priority = 1, description = "Upload and Import Lookup file to file export Import ")
	public void ImportLookupFiles() throws Exception {

//		ntFInstance.navigateToTask(ServiceRequestPage.fileImportExport, LoaderDriver);

		LoaderDriver.get(GetDriverInstance.EnvironmentName.replaceAll("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_file_import_and_export"));
		CommonUtils.hold(2);

		String s = "LookupCodes_S";
		for (int i = 1; i <= 7; i++) {
			s = s + i + ".txt";
			loader.uploadAttachments(s, LoaderDriver);
			s = "LookupCodes_S";
		}
		String s1 = "LookupTypes_S";
		for (int i = 1; i <= 7; i++) {
			s1 = s1 + i + ".txt";
			loader.uploadAttachments(s1, LoaderDriver);
			s1 = "LookupTypes_S";
		}

	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException, SQLException {
		LoaderDriver.quit();
	}

}
