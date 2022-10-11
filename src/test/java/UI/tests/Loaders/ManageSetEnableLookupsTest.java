package UI.tests.Loaders;

import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.NavigationTaskFlows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import java.util.ArrayList;
import java.util.List;


public class ManageSetEnableLookupsTest extends GetDriverInstance {

    private String pid;
    private LoaderWrapper loader;
    private ApplicationLogin aLoginInstance;
    private WebDriver loaderDriver;
    private NavigationTaskFlows ntFInstance;
    private ServiceRequestPage sr;
    private ManageStandardLookup standardLookup;
    private List<Integer> setLkp;
    private EssJobUsingCSVLoader essLoader;

    @Parameters({"user", "pwd"})
    @BeforeClass
    public void setUp(String user, String passWord) throws Exception {
        loaderDriver = getDriverInstanceObject();
        pid = CommonUtils.uniqueId();
        aLoginInstance = new ApplicationLogin(loaderDriver);
        standardLookup = new ManageStandardLookup(loaderDriver);
        ntFInstance = new NavigationTaskFlows(loaderDriver);
        loader = new LoaderWrapper(loaderDriver);
        sr = new ServiceRequestPage(loaderDriver);
        aLoginInstance.login(user, passWord, loaderDriver);
        CommonUtils.waitForInvisibilityOfElement(aLoginInstance.username, loaderDriver, 5);
        cleanUp();
        setLkp = new ArrayList<Integer>();
        essLoader = new EssJobUsingCSVLoader(loaderDriver);
    }

    public void cleanUp() {
        try {
            System.out.println("INFO : Deleting Set-Enabled Lookup Data");
            DbResource.sqlStatement.executeUpdate(loader.cleanUpData("REL12%"));
        } catch (Exception ex) {
            System.out.println("ERROR : Exception While trying to Deleting Set-Enabled Lookup Data" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Test(priority = 1, description = "Upload Set-Enabled CSV Files To UCM")
    public void ImportsetEnabledFiles() throws Exception {

        CommonUtils.hold(2);
        loaderDriver.get(GetDriverInstance.EnvironmentName.replaceAll("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_file_import_and_export"));
        CommonUtils.hold(2);

        String s = "LookupCodes_Set";
        for (int i = 1; i <= 7; i++) {
            s = s + i + ".txt";
            loader.uploadAttachments(s, loaderDriver);
            s = "LookupCodes_Set";
        }
        String s1 = "LookupTypes_Set";
        for (int i = 1; i <= 7; i++) {
            s1 = s1 + i + ".txt";
            loader.uploadAttachments(s1, loaderDriver);
            s1 = "LookupTypes_Set";
        }

    }

    @Test(priority = 2, description = "Import CSV File1", dependsOnMethods = {"ImportsetEnabledFiles"})
    public void ImportDataFile1() {

        try {
            setLkp.clear();
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(0);

            String newFileName = "ManagesetEnableLookupTest01_";
            loaderDriver.navigate().to(GetDriverInstance.EnvironmentName.replaceAll("FuseWelcome", "FuseTaskListManagerTop"));
            ntFInstance.navigateToAOLTaskFlows("Manage Set Enabled Lookups", loaderDriver);
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.visibilityOf(loaderDriver.findElement(By.xpath("//h1[contains(text(),'Manage Set Enabled Lookups')]"))), loaderDriver);
            String codeFile = "LookupCodes_Set1.txt";
            String typeFile = "LookupTypes_Set1.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("SETLOOKUPS", setLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6SET_L01", 2, "type");
        } catch (Exception exp) {
            System.out.println("Error: SetLookup Loader Test ImportDataFile1() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }

    }

    @Test(priority = 3, description = "Import SetLkp CSV File2", dependsOnMethods = {"ImportsetEnabledFiles"})
    public void ImportDataFile2() {
        try {
            setLkp.clear();
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(0);

            String newFileName = "ManagesetEnableLookupTest02_";
            String codeFile = "LookupCodes_Set2.txt";
            String typeFile = "LookupTypes_Set2.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("SETLOOKUPS", setLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6SET_L02", 1, "type");
        } catch (Exception exp) {
            System.out.println("Error: SetLookup Loader Test ImportDataFile2() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }

    }

    @Test(priority = 4, description = "Import SetLkp CSV File3", dependsOnMethods = {"ImportsetEnabledFiles"})
    public void ImportDataFile3() {

        try {
            setLkp.clear();
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(0);

            String newFileName = "ManagesetEnableLookupTest03_";

            String codeFile = "LookupCodes_Set3.txt";
            String typeFile = "LookupTypes_Set3.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("SETLOOKUPS", setLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6SET_L03", 2, "type");
        } catch (Exception exp) {
            System.out.println("Error: SetLookup Loader Test ImportDataFile3() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }

    }

    @Test(priority = 5, description = "Import SetLkp CSV File4", dependsOnMethods = {"ImportsetEnabledFiles"})
    public void ImportDataFile4() {

        try {
            setLkp.clear();
            setLkp.add(0);
            setLkp.add(0);
            setLkp.add(0);
            setLkp.add(0);
            setLkp.add(0);
            setLkp.add(0);
            setLkp.add(0);
            setLkp.add(0);

            String newFileName = "ManagesetEnableLookupTest04_";
            String codeFile = "LookupCodes_Set4.txt";
            String typeFile = "LookupTypes_Set4.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            System.out.println("Details in " + newFileName + " file are : ");
            Assert.assertTrue(essLoader.validateLoaderLog("SETLOOKUPS", setLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));

        } catch (Exception exp) {
            System.out.println("Error: SetLookup Loader Test ImportDataFile4() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 6, description = "Import SetLkp CSV File5")
    public void ImportDataFile5() {

        try {
            setLkp.clear();
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(0);

            String newFileName = "ManagesetEnableLookupTest05_";
            String codeFile = "LookupCodes_Set5.txt";
            String typeFile = "LookupTypes_Set5.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("SETLOOKUPS", setLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6SET_L05", 2, "type");
        } catch (Exception exp) {
            System.out.println("Error: SetLookup Loader Test ImportDataFile5() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }

    }

    @Test(priority = 7, description = "Import SetLkp CSV File6", dependsOnMethods = {"ImportsetEnabledFiles"})
    public void ImportDataFile6() {

        try {
            setLkp.clear();
            setLkp.add(100);
            setLkp.add(0);
            setLkp.add(100);
            setLkp.add(0);
            setLkp.add(100);
            setLkp.add(0);
            setLkp.add(100);
            setLkp.add(0);

            String newFileName = "ManagesetEnableLookupTest06_";
            String codeFile = "LookupCodes_Set6.txt";
            String typeFile = "LookupTypes_Set6.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("SETLOOKUPS", setLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6SET_L77", 2, "type");
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6SET_L66", 2, "type");
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6SET_L88", 2, "type");
        } catch (Exception exp) {
            System.out.println("Error: SetLookup Loader Test ImportDataFile6() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();

        }
    }

    @Test(priority = 8, description = "Import SetLkp CSV File7", dependsOnMethods = {"ImportsetEnabledFiles"})
    public void ImportDataFile7() {

        try {
            setLkp.clear();
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(0);
            setLkp.add(1);
            setLkp.add(1);
            setLkp.add(0);
            setLkp.add(0);
            setLkp.add(1);

            String newFileName = "ManagesetEnableLookupTest07_";
            String codeFile = "LookupCodes_Set7.txt";
            String typeFile = "LookupTypes_Set7.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importLoader(codeFile, typeFile, loaderDriver);
            loader.renameFile("lookups_import_logfile.txt", newFileName);
            Assert.assertTrue(essLoader.validateLoaderLog("SETLOOKUPS", setLkp, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName)));
            loader.verifyUploadDataOnStandardLookupUI(loaderDriver, "REL12BP6SET_L05", 2, "type1234");
        } catch (Exception exp) {
            System.out.println("Error: SetLookup Loader Test ImportDataFile7() Failed Due to : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();

        }

    }

    @AfterClass(alwaysRun = true)
    public void logOut() {
        loaderDriver.quit();

    }
}
