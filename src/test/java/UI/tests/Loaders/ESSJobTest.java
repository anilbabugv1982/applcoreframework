package UI.tests.Loaders;

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.NavigationTaskFlows;
import pageDefinitions.UI.oracle.applcore.qa.Loader.LoaderWrapper;

public class ESSJobTest extends GetDriverInstance {

	LoaderWrapper loader;
	private ApplicationLogin aLoginInstance;
	WebDriver LoaderDriver;
	private NavigationTaskFlows ntFInstance;

	@Parameters({ "user", "pwd" })
	@BeforeClass
	public void setUp(String user, String passWord) throws Exception {
		LoaderDriver = getDriverInstanceObject();

		aLoginInstance = new ApplicationLogin(LoaderDriver);
		aLoginInstance.login(user, passWord, LoaderDriver);
		CommonUtils.waitForInvisibilityOfElement(aLoginInstance.username,LoaderDriver,15);
		ntFInstance = new NavigationTaskFlows(LoaderDriver);
		loader = new LoaderWrapper(LoaderDriver);
		DbResource.sqlStatement.executeUpdate(loader.cleanUpValueSetAllinOne());
	}

	@Test(priority = 1, description = "Upload and Import Lookup file to file export Import ")
	public void ImportValueSetFiles(){
		try{
			String processname = "ESS process for Applcore csv file upload";
			String fileName = "valueSet_code.txt";
			LoaderDriver.get(GetDriverInstance.EnvironmentName.replaceAll("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_file_import_and_export"));
			CommonUtils.hold(2);
			loader.uploadAttachments(fileName, LoaderDriver);
			LoaderDriver.get(GetDriverInstance.EnvironmentName.replaceAll("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_scheduled_processes_fuse_plus"));
			CommonUtils.hold(2);
			String account = "crm/appointment/import";
			String fileType = "VALUE_SET";
			loader.createEssJobForLoadingValueSet(processname, fileName, account, fileType, LoaderDriver);
		}catch (Exception exp){
			System.out.println("ERROR : Exception While Uploading VSCode File to UCM "+exp.getMessage());
			Assert.fail(exp.getMessage());
		}


	}

	@AfterClass(alwaysRun = true)
	public void logOut() throws InterruptedException, SQLException {
		LoaderDriver.quit();

	}
}
