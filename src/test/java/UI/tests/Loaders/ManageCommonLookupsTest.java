package UI.tests.Loaders;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.NavigationTaskFlows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ServiceRequestPage;
import pageDefinitions.UI.oracle.applcore.qa.Loader.EssJobUsingCSVLoader;
import pageDefinitions.UI.oracle.applcore.qa.Loader.LoaderWrapper;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ManageCommonLookupsTest extends GetDriverInstance {

    static private String pid;
    private String srName;
    private LoaderWrapper loader;
    private ApplicationLogin aLoginInstance;
    private WebDriver loaderDriver;
    private NavigationTaskFlows ntFInstance;
    private ServiceRequestPage sr;
    private String expImpURL;
    private String taskListURL;
    private List<Integer> cmnLkp;
    private EssJobUsingCSVLoader essLoader;

    @Parameters({"user", "pwd"})
    @BeforeClass
    public void setUp(String user, String passWord) throws Exception {
        loaderDriver = getDriverInstanceObject();
        setURLS();
        pid = CommonUtils.uniqueId();
        aLoginInstance = new ApplicationLogin(loaderDriver);
        aLoginInstance.login(user, passWord, loaderDriver);
        CommonUtils.waitForInvisibilityOfElement(aLoginInstance.username, loaderDriver, 5);
        ntFInstance = new NavigationTaskFlows(loaderDriver);
        loader = new LoaderWrapper(loaderDriver);
        sr = new ServiceRequestPage(loaderDriver);
        cleanUp();
        cmnLkp = new ArrayList<Integer>();
        essLoader = new EssJobUsingCSVLoader(loaderDriver);
    }

    public void cleanUp() {
        try {
            System.out.println("INFO : Deleting Common Lookup data ");
            DbResource.sqlStatement.executeUpdate(loader.cleanUpData("REL12%"));
        } catch (Exception ex) {
            System.out.println("ERROR : While Trying To Cleanup Common LookUp lookupData " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Test(priority = 2, description = "Import CommonLookup CSV File1")
    public void ImportDataFile1() {
        try {
            cmnLkp.clear();
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(0);

            String newFileName = "ManageCommonLookupTest01_";
            CommonUtils.hold(4);
            loaderDriver.navigate().to(GetDriverInstance.EnvironmentName.replaceAll("FuseWelcome", "FuseTaskListManagerTop"));
            CommonUtils.hold(3);
            ntFInstance.navigateToAOLTaskFlows("Manage Common Lookups", loaderDriver);
            CommonUtils.explicitWait(loaderDriver.findElement(By.xpath("//h1[contains(text(),'Manage Common Lookups')]")), "Click", "", loaderDriver);
            String codeFile = "LookupCodes_S1.txt";
            String typeFile = "LookupTypes_S1.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("CMNLOOKUPS", cmnLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP9_L01", 2, "type");
        } catch (Exception exp) {
            System.out.println("Error: CommonLookup Loader Test ImportDataFile1() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }

    }


    @Test(priority = 3, description = "Import CommonLookup CSV File2")
    public void ImportDataFile2() {

        try {
            cmnLkp.clear();
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(0);

            String newFileName = "ManageCommonLookupTest02_";
            String codeFile = "LookupCodes_S2.txt";
            String typeFile = "LookupTypes_S2.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("CMNLOOKUPS", cmnLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L02", 1, "type");
        } catch (Exception exp) {
            System.out.println("Error: CommonLookup Loader Test ImportDataFile2() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 4, description = "Import CommonLookup CSV File3")
    public void ImportDataFile3() {
        try {
            cmnLkp.clear();
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(0);

            String newFileName = "ManageCommonLookupTest03_";

            String codeFile = "LookupCodes_S3.txt";
            String typeFile = "LookupTypes_S3.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            System.out.println("Details in " + newFileName + " file are : ");
            Assert.assertTrue(essLoader.validateLoaderLog("CMNLOOKUPS", cmnLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L03", 2, "type");
        } catch (Exception exp) {
            System.out.println("Error: CommonLookup Loader Test ImportDataFile3() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }

    }

    @Test(priority = 5, description = "Import CommonLookup CSV File4")
    public void ImportDataFile4() {
        try {
            cmnLkp.clear();
            cmnLkp.add(0);
            cmnLkp.add(0);
            cmnLkp.add(0);
            cmnLkp.add(0);
            cmnLkp.add(0);
            cmnLkp.add(0);
            cmnLkp.add(0);
            cmnLkp.add(0);

            String newFileName = "ManageCommonLookupTest04_";
            String codeFile = "LookupCodes_S4.txt";
            String typeFile = "LookupTypes_S4.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("CMNLOOKUPS", cmnLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
        } catch (Exception exp) {
            System.out.println("Error: CommonLookup Loader Test ImportDataFile4() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 6, description = "Import CommonLookup CSV File5")
    public void ImportDataFile5() {

        try {
            cmnLkp.clear();
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(0);

            String newFileName = "ManageCommonLookupTest05_";
            String codeFile = "LookupCodes_S5.txt";
            String typeFile = "LookupTypes_S5.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("CMNLOOKUPS", cmnLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L01", 2, "type");
        } catch (Exception exp) {
            System.out.println("Error: CommonLookup Loader Test ImportDataFile5() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 7, description = "Import CommonLookup CSV File6")
    public void ImportDataFile6() {

        try {
            cmnLkp.clear();
            cmnLkp.add(100);
            cmnLkp.add(0);
            cmnLkp.add(100);
            cmnLkp.add(0);
            cmnLkp.add(100);
            cmnLkp.add(0);
            cmnLkp.add(100);
            cmnLkp.add(0);

            String newFileName = "ManageCommonLookupTest06_";
            String codeFile = "LookupCodes_S6.txt";
            String typeFile = "LookupTypes_S6.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("CMNLOOKUPS", cmnLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L22", 2, "type");
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L44", 2, "type");
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L66", 2, "type");
        } catch (Exception exp) {
            System.out.println("Error: CommonLookup Loader Test ImportDataFile5() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }

    }

    @Test(priority = 8, description = "Import CommonLookup CSV File7")
    public void ImportDataFile7() {

        try {
            cmnLkp.clear();
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(0);
            cmnLkp.add(1);
            cmnLkp.add(1);
            cmnLkp.add(0);
            cmnLkp.add(0);
            cmnLkp.add(1);

            String newFileName = "ManageCommonLookupTest07_";
            String codeFile = "LookupCodes_S7.txt";
            String typeFile = "LookupTypes_S7.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("CMNLOOKUPS", cmnLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L01", 2, "type1234");
            CommonUtils.hold(5);
        } catch (Exception exp) {
            System.out.println("Error: CommonLookup Loader Test ImportDataFile6() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
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
