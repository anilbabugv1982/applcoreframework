package UI.tests.Loaders;


import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.NavigationTaskFlows;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ServiceRequestPage;
import pageDefinitions.UI.oracle.applcore.qa.Dff.ManaageValueSetsUtil;
import pageDefinitions.UI.oracle.applcore.qa.Loader.EssJobUsingCSVLoader;
import pageDefinitions.UI.oracle.applcore.qa.Loader.LoaderWrapper;
import pageDefinitions.UI.oracle.applcore.qa.Loader.ManageValueSet;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ManageRelatedValueSetTest extends GetDriverInstance {
    private static String pid;
    private String srName;
    private List<Integer> testData = null;
    private LoaderWrapper loader;
    private ApplicationLogin aLoginInstance;
    private WebDriver loaderDriver;
    private NavigationTaskFlows ntFInstance;
    private ServiceRequestPage sr;
    private ManageValueSet manageValueSet;
    private ManaageValueSetsUtil vsUtils = null;
    private String expImpURL;
    private String taskListURL;
    private EssJobUsingCSVLoader essLoaderUtil;
    private Map<String, String> valueSet;

    @Parameters({"user", "pwd"})
    @BeforeClass
    public void setUp(String user, String passWord) throws Exception {
        try {
            loaderDriver = getDriverInstanceObject();
            setURLS();
            pid = CommonUtils.uniqueId();
            testData = new ArrayList<>();
            vsUtils = new ManaageValueSetsUtil(loaderDriver);
            aLoginInstance = new ApplicationLogin(loaderDriver);
            ntFInstance = new NavigationTaskFlows(loaderDriver);
            loader = new LoaderWrapper(loaderDriver);
            sr = new ServiceRequestPage(loaderDriver);
            manageValueSet = new ManageValueSet(loaderDriver);
            essLoaderUtil = new EssJobUsingCSVLoader(loaderDriver);
            valueSet = new HashMap<>();
            DbResource.sqlStatement.executeUpdate(loader.gatherStats());
            DbResource.sqlStatement.executeUpdate(loader.cleanUprelatedValueSetData());
            aLoginInstance.login(user, passWord, loaderDriver);
            CommonUtils.waitForInvisibilityOfElement(aLoginInstance.username, loaderDriver, 8);
        } catch (Exception ex) {
            System.out.println("Error : Exception in ManageRelatedValueSetTest -> Setup() :" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Test(priority = 1, description = "Upload and Import Lookup file to file export Import ")
    public void ImportValueSetFiles() {
        try {
            loaderDriver.get(GetDriverInstance.EnvironmentName.replaceAll("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_file_import_and_export"));
            CommonUtils.hold(3);
            loader.uploadAttachments("VS1_VALUES.txt", loaderDriver);
            loader.uploadAttachments("RELATED_VS_VALUES.txt", loaderDriver);
            loader.uploadAttachments("RELATED_VS_VALUES_UPDATE.txt", loaderDriver);
            loader.uploadAttachments("VSSet_Relation.txt", loaderDriver);
            loader.uploadAttachments("VSSet_Relation_interchange_headers.txt", loaderDriver);
        } catch (Exception exp) {
            System.out.println("ERROR -> Exception While Running Test -> ImportValueSetFiles() : " + exp.getMessage());
            exp.printStackTrace();
        }

    }

    @Test(priority = 2, description = "Import value set code files ")
    public void ImportDataFile1() throws Exception {
        CommonUtils.hold(5);
        vsUtils.navigateToManageValueSettf(loaderDriver);
        String newFileName = "ManageRelatedValueSetTest01_" + pid + ".txt";
        loader.importValueSet("VS1_VALUES.txt", "", loaderDriver, "import");
        loader.renameFile("vsimport_logfile.txt", newFileName);
    }

    @Test(priority = 3, description = "Import RVS Values : Insert")
    public void ImportDataFile2() {
        try {
            testData.clear();
            testData.add(4);
            testData.add(0);
            testData.add(0);
            testData.add(4);
            testData.add(0);
            testData.add(145);
            testData.add(0);
            testData.add(0);
            testData.add(145);
            testData.add(0);

            CommonUtils.hold(2);
            String newFileName = "ManageRelatedValueSetTest02_";
            String valueFile = "RELATED_VS_VALUES.txt";
            String valueSetFile2 = "VSSet_Relation.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importValueSet(valueSetFile2, valueFile, loaderDriver, "importvalueset");
            loader.renameFile("import_logfile.txt", newFileName);
            essLoaderUtil.validateLoaderLog("RELATEDVALUESET", testData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName));
            loader.validateRelatedValueSetOnUI(loaderDriver, "VV1", "VV2", "VV3", 120, 0);
            loader.validateRelatedValueSetOnUI(loaderDriver, "VV2", "VV1", "VV4", 120, 0);
            loader.validateRelatedValueSetOnUI(loaderDriver, "VV3", "VV1", "VV4", 0, 25);
            loader.validateRelatedValueSetOnUI(loaderDriver, "VV4", "VV2", "VV3", 0, 25);
        } catch (Exception exp) {
            System.out.println("ERROR -> Exception While Running Test -> ImportDataFile2() : " + exp.getMessage());
            exp.printStackTrace();
        }


    }

    @Test(priority = 4, description = "Import RVS Values : Update")
    public void ImportDataFile3() {
        try {
            testData.clear();
            testData.add(2);
            testData.add(0);
            testData.add(0);
            testData.add(0);
            testData.add(2);
            testData.add(155);
            testData.add(0);
            testData.add(128);
            testData.add(10);
            testData.add(17);

            String newFileName = "ManageRelatedValueSetTest03_";
            CommonUtils.hold(2);
            String valueFile = "RELATED_VS_VALUES_UPDATE.txt";
            String valueSetFile2 = "VSSet_Relation_interchange_headers.txt";
            newFileName = newFileName + pid + ".txt";
            loader.importValueSet(valueSetFile2, valueFile, loaderDriver, "importvalueset");
            loader.renameFile("import_logfile.txt", newFileName);
            essLoaderUtil.validateLoaderLog("RELATEDVALUESET", testData, new File(GetDriverInstance.fsmExpImpFile + System.getProperty("file.separator") + newFileName));

            valueSet.put("VSCODE", "VV1");
            valueSet.put("RVS1", "VV2");
            valueSet.put("RVS3", "VV3");
            valueSet.put("FIRSTROW", "UPDATE");
            valueSet.put("SECONDROW", "CREATE");
            valueSet.put("RVS12XBOX", "checked");
            valueSet.put("RVS13XBOX", "unchecked");
            valueSet.put("RVS12COUNT", "12");
            valueSet.put("RVS13COUNT", "5");
            loader.validateRelatedValueSetOnUIForUpdate(loaderDriver, valueSet);

            valueSet.put("VSCODE", "VV2");
            valueSet.put("RVS1", "VV1");
            valueSet.put("RVS3", "VV4");
            valueSet.put("FIRSTROW", "UPDATE");
            valueSet.put("SECONDROW", "CREATE");
            valueSet.put("RVS12XBOX", "checked");
            valueSet.put("RVS13XBOX", "checked");
            valueSet.put("RVS12COUNT", "12");
            valueSet.put("RVS13COUNT", "5");
            loader.validateRelatedValueSetOnUIForUpdate(loaderDriver, valueSet);

            valueSet.put("VSCODE", "VV3");
            valueSet.put("RVS1", "VV1");
            valueSet.put("RVS3", "VV4");
            valueSet.put("FIRSTROW", "CREATE");
            valueSet.put("SECONDROW", "UPDATE");
            valueSet.put("RVS12XBOX", "unchecked");
            valueSet.put("RVS13XBOX", "checked");
            valueSet.put("RVS12COUNT", "5");
            valueSet.put("RVS13COUNT", "5");
            loader.validateRelatedValueSetOnUIForUpdate(loaderDriver, valueSet);

            valueSet.put("VSCODE", "VV4");
            valueSet.put("RVS1", "VV2");
            valueSet.put("RVS3", "VV3");
            valueSet.put("FIRSTROW", "CREATE");
            valueSet.put("SECONDROW", "UPDATE");
            valueSet.put("RVS12XBOX", "checked");
            valueSet.put("RVS13XBOX", "checked");
            valueSet.put("RVS12COUNT", "5");
            valueSet.put("RVS13COUNT", "5");
            loader.validateRelatedValueSetOnUIForUpdate(loaderDriver, valueSet);
        } catch (Exception exp) {
            System.out.println("ERROR -> Exception While Running Test -> ImportDataFile3() : " + exp.getMessage());
            exp.printStackTrace();
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
