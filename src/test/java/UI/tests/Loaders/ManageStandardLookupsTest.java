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
import pageDefinitions.UI.oracle.applcore.qa.Loader.ManageStandardLookup;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageStandardLookupsTest extends GetDriverInstance {

    private static String pid;
    private String srName;
    private LoaderWrapper loader;
    private ApplicationLogin logObj;
    private WebDriver loaderDriver;
    private NavigationTaskFlows ntFInstance;
    private ServiceRequestPage sr;
    private ManageStandardLookup standardLookup;
    private List<Integer> stdSet;
    private EssJobUsingCSVLoader essLoader;

    @Parameters({"user", "pwd"})
    @BeforeClass
    public void setUp(String user, String psw) throws Exception {

        loaderDriver = getDriverInstanceObject();
        logObj = new ApplicationLogin(loaderDriver);
        standardLookup = new ManageStandardLookup(loaderDriver);
        ntFInstance = new NavigationTaskFlows(loaderDriver);
        loader = new LoaderWrapper(loaderDriver);
        sr = new ServiceRequestPage(loaderDriver);
        logObj.login(user, psw, loaderDriver);
        CommonUtils.waitForInvisibilityOfElement(logObj.username, loaderDriver, 5);
        cleanUp();
        stdSet = new ArrayList<Integer>();
        essLoader = new EssJobUsingCSVLoader(loaderDriver);
        pid = CommonUtils.uniqueId();
    }

    public void cleanUp() {
        try {
            System.out.println("INFO : Deleting Standard Lookup Data ");
            DbResource.sqlStatement.executeUpdate(loader.cleanUpData("REL12%"));
            DbResource.sqlStatement.executeUpdate(loader.gatherStats());
        } catch (Exception ex) {
            System.out.println("ERROR : While trying to cleanup Standard Lookup Data " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Test(priority = 2, description = "Import file and find required data ")
    public void ImportDataFile1() {
        try {
            stdSet.clear();
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(0);

            String newFileName = "ManageStandardLookupTest01_";
            loaderDriver.navigate().to(GetDriverInstance.EnvironmentName.replaceAll("FuseWelcome", "FuseTaskListManagerTop"));
            CommonUtils.hold(3);
            ntFInstance.navigateToAOLTaskFlows("Manage Standard Lookups", loaderDriver);
            CommonUtils.explicitWait(loaderDriver.findElement(By.xpath("//h1[contains(text(),'Manage Standard Lookups')]")), "Click", "", loaderDriver);
            String codeFile = "LookupCodes_S1.txt";
            String typeFile = "LookupTypes_S1.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("STDLOOKUPS", stdSet, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP9_L01", 2, "type");
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running ImportDataFile1() Test " + exp.getMessage());
            Assert.fail(exp.getMessage());
        }
    }

    @Test(priority = 3, description = "Import file and find required data ")
    public void ImportDataFile2() {

        try {
            stdSet.clear();
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(0);

            String newFileName = "ManageStandardLookupTest02_";
            String codeFile = "LookupCodes_S2.txt";
            String typeFile = "LookupTypes_S2.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("STDLOOKUPS", stdSet, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L02", 1, "type");
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running ImportDataFile2() Test " + exp.getMessage());
            Assert.fail(exp.getMessage());
        }
    }

    @Test(priority = 4, description = "Import file and find required data ")
    public void ImportDataFile3() {
        try {
            stdSet.clear();
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(0);

            String newFileName = "ManageStandardLookupTest03_";
            String codeFile = "LookupCodes_S3.txt";
            String typeFile = "LookupTypes_S3.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("STDLOOKUPS", stdSet, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L03", 2, "type");
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running ImportDataFile3() Test " + exp.getMessage());
            Assert.fail(exp.getMessage());
        }

    }

    @Test(priority = 5, description = "Import file and find required data ")
    public void ImportDataFile4() {

        try {
            stdSet.clear();
            stdSet.add(0);
            stdSet.add(0);
            stdSet.add(0);
            stdSet.add(0);
            stdSet.add(0);
            stdSet.add(0);
            stdSet.add(0);
            stdSet.add(0);

            String newFileName = "ManageStandardLookupTest04_";
            String codeFile = "LookupCodes_S4.txt";
            String typeFile = "LookupTypes_S4.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            System.out.println("Details in " + newFileName + " file are : ");
            Assert.assertTrue(essLoader.validateLoaderLog("STDLOOKUPS", stdSet, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running ImportDataFile3() Test " + exp.getMessage());
            Assert.fail(exp.getMessage());
        }
    }

    @Test(priority = 6, description = "Import file and find required data ")
    public void ImportDataFile5() {
        try {
            stdSet.clear();
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(0);

            String newFileName = "ManageStandardLookupTest05_";
            String codeFile = "LookupCodes_S5.txt";
            String typeFile = "LookupTypes_S5.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("STDLOOKUPS", stdSet, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L01", 2, "type");
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running ImportDataFile4() Test " + exp.getMessage());
            Assert.fail(exp.getMessage());
        }


    }

    @Test(priority = 7, description = "Import file and find required data ")
    public void ImportDataFile6() {

        try {
            stdSet.clear();
            stdSet.add(100);
            stdSet.add(0);
            stdSet.add(100);
            stdSet.add(0);
            stdSet.add(100);
            stdSet.add(0);
            stdSet.add(100);
            stdSet.add(0);

            String newFileName = "ManageStandardLookupTest06_";
            String codeFile = "LookupCodes_S6.txt";
            String typeFile = "LookupTypes_S6.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("STDLOOKUPS", stdSet, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L22", 2, "type");
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L44", 2, "type");
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L66", 2, "type");
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running ImportDataFile5() Test " + exp.getMessage());
            Assert.fail(exp.getMessage());
        }

    }

    @Test(priority = 8, description = "Import file and find required data ")
    public void ImportDataFile7() {

        try {
            stdSet.clear();
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(0);
            stdSet.add(1);
            stdSet.add(1);
            stdSet.add(0);
            stdSet.add(0);
            stdSet.add(1);

            String newFileName = "ManageStandardLookupTest07_";
            String codeFile = "LookupCodes_S7.txt";
            String typeFile = "LookupTypes_S7.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("STDLOOKUPS", stdSet, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6_L01", 2, "type1234");
            CommonUtils.hold(5);
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running ImportDataFile7() Test " + exp.getMessage());
            Assert.fail(exp.getMessage());
        }

    }

    @AfterClass(alwaysRun = true)
    public void logOut() throws InterruptedException, SQLException {
        loaderDriver.quit();
    }
}
