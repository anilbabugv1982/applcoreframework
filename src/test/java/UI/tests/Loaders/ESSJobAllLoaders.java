package UI.tests.Loaders;


import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.NavigationTaskFlows;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.*;
import pageDefinitions.UI.oracle.applcore.qa.Attachments.ServiceRequestPage;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Dff.ManaageValueSetsUtil;
import pageDefinitions.UI.oracle.applcore.qa.Loader.EssJobUsingCSVLoader;
import pageDefinitions.UI.oracle.applcore.qa.Loader.LoaderWrapper;
import pageDefinitions.UI.oracle.applcore.qa.Loader.ScheduleProcessObject;
import pageDefinitions.UI.oracle.applcore.qa.ds.DataSecurityWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;


public class ESSJobAllLoaders extends GetDriverInstance {

    private WebDriver essDriver = null;
    private ApplicationLogin appLogin = null;
    private JavascriptExecutor js = null;
    private ScheduleProcessObject essPage = null;
    private EssJobUsingCSVLoader csvLoader = null;
    private List<Integer> testData = null;
    private LoaderWrapper loader = null;
    private NavigationTaskFlows navTF = null;
    private ServiceRequestPage servicePage = null;
    private ManaageValueSetsUtil vsUtils = null;
    private LookupsWrapper lookupsUtils = null;
    private ManageMessagesUtils msgUtils = null;
    private ProfilesUtils prfCateUtil = null;
    private DFFUtils dffSql = null;
    private DataSecurityWrapper dsUtil = null;
    private DocSeqUtil docUtils = null;
    private ManageDocumentSequence docPage = null;
    private String expImpURL = "";
    private String essURL = "";
    private String taskListURL = "";


    @Parameters({"user1", "pwd1"})
    @BeforeClass
    public void setUp(String user, String passWord) throws Exception {
        try {
            essDriver = getDriverInstanceObject();
            setURLS();
            js = (JavascriptExecutor) essDriver;
            testData = new ArrayList<Integer>();
            loader = new LoaderWrapper(essDriver);
            appLogin = new ApplicationLogin(essDriver);
            dsUtil = new DataSecurityWrapper(essDriver);
            docUtils = new DocSeqUtil(essDriver);
            js = (JavascriptExecutor) essDriver;
            essPage = new ScheduleProcessObject(essDriver);
            appLogin.login(user, passWord, essDriver);
            servicePage = new ServiceRequestPage(essDriver);
            csvLoader = new EssJobUsingCSVLoader(essDriver);
            navTF = new NavigationTaskFlows(essDriver);
            vsUtils = new ManaageValueSetsUtil(essDriver);
            lookupsUtils = new LookupsWrapper(essDriver);
            msgUtils = new ManageMessagesUtils(essDriver);
            prfCateUtil = new ProfilesUtils(essDriver);
            docPage = new ManageDocumentSequence(essDriver);
            dffSql = new DFFUtils(essDriver);
            CommonUtils.hold(4);
            csvLoader.deleteLookUpData("REL12BP%", "REL12BP%");
            csvLoader.deleteMessageData("DPK%", "DPK%");
            csvLoader.deleteProfileOptions("DPK%");
            csvLoader.deleteProfileCategory("PCCODE%");
            DbResource.sqlStatement.executeUpdate(loader.gatherStats());
            DbResource.sqlStatement.executeUpdate(loader.cleanProfileData(user));
            DbResource.sqlStatement.executeUpdate(dffSql.deleteSegmentLabel("PER_ASG_DF", "800", user));
            DbResource.sqlStatement.executeUpdate(dffSql.deleteGlobalSegmentSQL("IRC_REQUISITIONS_DFF", "821", user));
            DbResource.sqlStatement.executeUpdate(dffSql.deleteContextCode("IRC_REQUISITIONS_DFF", "821", user));
            DbResource.sqlStatement.executeUpdate(dffSql.deleteContextCode("PER_PHONES_DFF", "800", user));
            DbResource.sqlStatement.executeUpdate(loader.cleanUpDSdata());
            loader.cleanUpDocSeq();

        } catch (Exception e) {
            System.out.println("Exception in initializing objects in ESSJobAllLoaders class : " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void setURLS() {
        String actualURL = GetDriverInstance.EnvironmentName;
        expImpURL = actualURL.replaceAll("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_file_import_and_export");
        essURL = actualURL.replaceAll("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_scheduled_processes_fuse_plus");
        taskListURL = actualURL.replaceAll("FuseWelcome", "FuseTaskListManagerTop");
    }

    public void cleanUpData() {
        try {
            DbResource.sqlStatement.executeUpdate(loader.cleanUpValueSetAllinOne());
        } catch (Exception cleanData) {
            System.out.println("ERROR : Error while trying delete VSCODE : " + cleanData.getMessage());
            cleanData.printStackTrace();
        }

    }

    @Test(description = "Upload VALUE_SET loader csv files", priority = 1)
    public void testCase01() {
        cleanUpData();
        essDriver.get(expImpURL);
        loader.uploadAttachments("VS_CODE2.txt", essDriver);
        loader.uploadAttachments("valueSet_code.txt", essDriver);
        loader.uploadAttachments("ValueSet5.txt", essDriver);
    }

    @Test(description = "Validate loader:VALUE_SET", dependsOnMethods = "testCase01", priority = 2)
    public void testCase02() {

        try {
            testData.clear();

            testData.add(26);
            testData.add(8);
            testData.add(18);
            testData.add(0);

            essDriver.get(essURL);
            File essJobId = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "VS_CODE2.txt", "VALUE_SET", essDriver);

            if (essJobId == null) {
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("VALUE_SET", testData, essJobId));
                vsUtils.navigateToManageValueSettf(essDriver);
                Assert.assertTrue(vsUtils.searchValueSet("VSCODE_SOAR_CHAR_FORMAT", essDriver));
                Assert.assertTrue(vsUtils.searchValueSet("VSCODE_SOAR_DEP_CHAR", essDriver));
            }

            testData.clear();

            testData.add(28);
            testData.add(0);
            testData.add(28);
            testData.add(0);

            essDriver.get(essURL);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "valueSet_code.txt", "VALUE_SET", essDriver);

            if (essJobId2 == null) {
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("VALUE_SET", testData, essJobId2));
            }

        } catch (Exception ex) {
            System.out.println("Exception while running test02 :" + ex.getMessage());
            ex.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    @Test(description = "Upload STD TYPE/CODE loader csv files", priority = 3)
    public void testCase03() {
        try {
//			navTF.navigateToTask(servicePage.fileImportExport, essDriver);
            essDriver.get(expImpURL);
            CommonUtils.hold(4);
            loader.uploadAttachments("LookupTypes_S1.txt", essDriver);
            loader.uploadAttachments("LookupCodes_S1.txt", essDriver);
            loader.uploadAttachments("LookupTypes_Set1.txt", essDriver);
            loader.uploadAttachments("LookupCodes_Set1.txt", essDriver);

        } catch (Exception uploadFile) {
            System.out.println("Error while try to upload Lookups CSV file to UCM : " + uploadFile.getMessage());
            uploadFile.printStackTrace();  Assert.assertTrue(false);
        }
        testData.clear();
    }

    @Test(description = "Validate loader:STD TYPE/CODE", dependsOnMethods = "testCase03", priority = 4)
    public void testCase04() {

        try {
            testData.clear();

            testData.add(1);
            testData.add(0);
            testData.add(1);
            testData.add(0);

//			csvLoader.navigateToESSPage(essDriver);
            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId1 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "LookupTypes_S1.txt", "STANDARD_TYPE", essDriver);
//			csvLoader.navigateToESSPage(essDriver);
            essDriver.get(essURL);
            CommonUtils.hold(4);
//			CommonUtils.hold(5);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "LookupCodes_S1.txt", "STANDARD_CODE", essDriver);

            if (essJobId1 == null && essJobId2 == null) {
                Assert.assertTrue(false, "essJobID1 and essJobID2 are null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("STANDARD_TYPE", testData, essJobId1) && csvLoader.ValidateEssLogs("STANDARD_CODE", testData, essJobId2));

//				if(ApplicationLogin.newsFeedEnabled) {
//					navTF.navigateToTask(navTF.getGlobalPageInstance().setupandmaintenance, essDriver);
//				}else {
//					essDriver.get(GetDriverInstance.EnvironmentName);
//					CommonUtils.hold(3);
//					CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", essDriver);
//					js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//					docPage.setupAndMaintananceElement.click();
//					CommonUtils.hold(3);
//				}
                essDriver.get(taskListURL);
                CommonUtils.hold(4);

                navTF.navigateToAOLTaskFlows("Manage Standard Lookups", essDriver);
                CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(essDriver.findElement(By.xpath("//h1[contains(text(),'Manage Standard Lookups')]"))), essDriver);
//				CommonUtils.explicitWait(essDriver.findElement(By.xpath("//h1[contains(text(),'Manage Standard Lookups')]")),"Click", "",essDriver);
                lookupsUtils.verifyStatus("REL12BP9_L01", "REL12BP9_L01", "REL12BP9_L01", "REL12BP9_L01_Desc", essDriver);
            }
        } catch (Exception ex) {
            System.out.println("Exception while running test02 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }

    }

    @Test(description = "Validate loader:CMN TYPE/CODE", dependsOnMethods = "testCase03", priority = 5)
    public void testCase05() {

        try {
            testData.clear();
            testData.add(1);
            testData.add(0);
            testData.add(1);
            testData.add(0);

//			csvLoader.navigateToESSPage(essDriver);
            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId1 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "LookupTypes_S1.txt", "COMMON_TYPE", essDriver);
            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "LookupCodes_S1.txt", "COMMON_CODE", essDriver);

            if (essJobId1 == null && essJobId2 == null) {
                Assert.assertTrue(false, "essJobID1 and essJobID2 are null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("COMMON_TYPE", testData, essJobId1) && csvLoader.ValidateEssLogs("COMMON_CODE", testData, essJobId2));
//				if(ApplicationLogin.newsFeedEnabled) {
//					navTF.navigateToTask(navTF.getGlobalPageInstance().setupandmaintenance, essDriver);
//				}else {
//					essDriver.get(GetDriverInstance.EnvironmentName);
//					CommonUtils.hold(3);
//					CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", essDriver);
//					js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//					docPage.setupAndMaintananceElement.click();
//					CommonUtils.hold(3);
//				}
                essDriver.get(taskListURL);
                CommonUtils.hold(4);
                navTF.navigateToAOLTaskFlows("Manage Common Lookups", essDriver);
                CommonUtils.explicitWait(essDriver.findElement(By.xpath("//h1[contains(text(),'Manage Common Lookups')]")), "Click", "", essDriver);
                lookupsUtils.verifyStatus("REL12BP9_L01", "REL12BP9_L01", "REL12BP9_L01", "REL12BP9_L01_Desc", essDriver);
            }
        } catch (Exception ex) {
            System.out.println("Exception while running test02 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }
        testData.clear();
    }

    @Test(description = "Validate loader:SETID TYPE/CODE", dependsOnMethods = "testCase03", priority = 6)
    public void testCase06() {

        try {
            testData.clear();
            testData.add(1);
            testData.add(0);
            testData.add(1);
            testData.add(0);

//			csvLoader.navigateToESSPage(essDriver);
            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId1 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "LookupTypes_Set1.txt", "SETID_TYPE", essDriver);

//			csvLoader.navigateToESSPage(essDriver);
            essDriver.get(essURL);
            CommonUtils.hold(5);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "LookupCodes_Set1.txt", "SETID_CODE", essDriver);

            if (essJobId1 == null && essJobId2 == null) {
                Assert.assertTrue(false, "essJobID1 and essJobID2 are null");
                System.out.println("Job ID's returned are NULL");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("SETID_TYPE", testData, essJobId1) && csvLoader.ValidateEssLogs("SETID_CODE", testData, essJobId2));

//				if(ApplicationLogin.newsFeedEnabled) {
//					navTF.navigateToTask(navTF.getGlobalPageInstance().setupandmaintenance, essDriver);
//				}else {
//					essDriver.get(GetDriverInstance.EnvironmentName);
//					CommonUtils.hold(3);
//					CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", essDriver);
//					js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//					docPage.setupAndMaintananceElement.click();
//					CommonUtils.hold(3);
//				}
                essDriver.get(taskListURL);
                CommonUtils.hold(4);
                navTF.navigateToAOLTaskFlows("Manage Set Enabled Lookups", essDriver);
                CommonUtils.explicitWait(essDriver.findElement(By.xpath("//h1[contains(text(),'Manage Set Enabled Lookups')]")), "Click", "", essDriver);
                lookupsUtils.verifyStatus("REL12BP6SET_L01", "REL12BP6SET_L01", "REL12BP6SET_L01", "REL12BP6SET_L01_Desc", essDriver);
            }
        } catch (Exception ex) {
            System.out.println("Exception while running test02 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }
        testData.clear();
    }

    @Test(description = "Upload MESSAGE and TOKEN loader csv files", priority = 7)
    public void testCase07() {
        try {
//			navTF.navigateToTask(servicePage.fileImportExport, essDriver);
            essDriver.get(expImpURL);
            CommonUtils.hold(4);
            loader.uploadAttachments("messages.txt", essDriver);
            loader.uploadAttachments("messagetokens.txt", essDriver);

        } catch (Exception uploadFile) {
            System.out.println("Error while try to upload Lookups CSV file to UCM : " + uploadFile.getMessage());
            uploadFile.printStackTrace();
           Assert.assertTrue(false);
        }
        testData.clear();
    }

    @Test(description = "Validate loader:MESSAGES",dependsOnMethods = "testCase07",  priority = 8)
    public void testCase08() {

        try {
            testData.clear();
            testData.add(16);
            testData.add(6);
            testData.add(5);
            testData.add(5);

//			csvLoader.navigateToESSPage(essDriver);
            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId1 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "messages.txt", "MESSAGES", essDriver);

            if (essJobId1 == null) {
                Assert.assertTrue(false, "essJobID1 is null");
                System.out.println("Job ID's returned are NULL");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("MESSAGES", testData, essJobId1));
//				if(ApplicationLogin.newsFeedEnabled) {
//					navTF.navigateToTask(navTF.getGlobalPageInstance().setupandmaintenance, essDriver);
//				}else {
//					essDriver.get(GetDriverInstance.EnvironmentName);
//					CommonUtils.hold(3);
//					CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", essDriver);
//					js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//					docPage.setupAndMaintananceElement.click();
//					CommonUtils.hold(3);
//				}
                essDriver.get(taskListURL);
                CommonUtils.hold(3);
                navTF.navigateToAOLTaskFlows("Manage Messages", essDriver);
                msgUtils.verifyStatus("DPK1", null, null, null, essDriver);

            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase08 :" + ex.getMessage());
            ex.printStackTrace();           Assert.assertTrue(false);

        }
        testData.clear();
    }

    @Test(description = "Validate loader:MESSAGES_TOKENS", priority = 9)
    public void testCase09() throws Exception {

        try {
            testData.clear();
            testData.add(14);
            testData.add(6);
            testData.add(5);
            testData.add(3);

//			csvLoader.navigateToESSPage(essDriver);
            essDriver.get(essURL);
            CommonUtils.hold(3);
            File essJobId1 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "messagetokens.txt", "MESSAGE_TOKENS", essDriver);

            if (essJobId1 == null) {
                Assert.assertTrue(false, "essJobID1 is null");
                System.out.println("Job ID's returned are NULL");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("MESSAGE_TOKENS", testData, essJobId1));

//				if(ApplicationLogin.newsFeedEnabled) {
//					navTF.navigateToTask(navTF.getGlobalPageInstance().setupandmaintenance, essDriver);
//				}else {
//					essDriver.get(GetDriverInstance.EnvironmentName);
//					CommonUtils.hold(3);
//					CommonUtils.explicitWait(docPage.setupAndMaintananceElement, "Click", "", essDriver);
//					js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
//					docPage.setupAndMaintananceElement.click();
//					CommonUtils.hold(3);
//				}
//				CommonUtils.hold(2);
                essDriver.get(taskListURL);
                CommonUtils.hold(3);
                navTF.navigateToAOLTaskFlows("Manage Messages", essDriver);
                msgUtils.loaderVerifytoltalMessages("DPK", essDriver);
                msgUtils.loaderVerifyUpdate("DPK1", essDriver, "Text", "checkBox", "TOKEN1", null, "Description November update");
                msgUtils.loaderVerifyUpdate("DPK2", essDriver, "UI String", null, "TOKEN2", null, "Description November122");
                msgUtils.loaderVerifyUpdate("DPK3", essDriver, null, null, "TOKEN3", "UPD", null);

            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase09 :" + ex.getMessage());
            ex.printStackTrace();           Assert.assertTrue(false);

        }
        testData.clear();
    }

    @Test(description = "Upload PROFILE_OPTIONS loader csv files", priority = 10)
    public void testCase10() {
        try {
//			navTF.navigateToTask(servicePage.fileImportExport, essDriver);
            essDriver.get(expImpURL);
            CommonUtils.hold(4);
            loader.uploadAttachments("profileoptions.txt", essDriver);

        } catch (Exception uploadFile) {
            System.out.println("Error while try to upload Profile Options CSV file to UCM : " + uploadFile.getMessage());
            uploadFile.printStackTrace();  Assert.assertTrue(false);
        }
        testData.clear();
    }

    @Test(description = "Validate loader:PROFILE_OPTIONS", dependsOnMethods = "testCase10", priority = 11)
    public void testCase11() {

        try {
            testData.clear();
            testData.add(8);
            testData.add(3);
            testData.add(3);
            testData.add(2);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId1 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "profileoptions.txt", "PROFILE_OPTIONS", essDriver);

            if (essJobId1 == null) {
                Assert.assertTrue(false, "essJobID1 is null");
                System.out.println("Job ID's returned are NULL");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("PROFILE_OPTIONS", testData, essJobId1));

                essDriver.get(taskListURL);
                CommonUtils.hold(4);
                navTF.navigateToAOLTaskFlows("Manage Profile Options", essDriver);
                CommonUtils.hold(5);
                prfCateUtil.searchProfileOption("DPK_CODE");
                Assert.assertTrue(essDriver.findElements(By.xpath("//span[contains(text(),'DPK_CODE')]")).size() == 3);
                Assert.assertTrue(essDriver.findElement(By.xpath("//span[contains(text(),'Oracle Municipal Code Officer')]")).isDisplayed());
                Assert.assertTrue(essDriver.findElement(By.xpath("//span[contains(text(),'UPDATE')]")).isDisplayed());

                prfCateUtil.verifyStatus("DPK_CODE1", "", essDriver);
            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase11 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }
        testData.clear();
    }

    @Test(description = "Upload PROFILE_CATEGORY/PROFILE_CATEGORY_OPTIONS loader csv files", priority = 12)
    public void testCase12() {
        try {
            essDriver.get(expImpURL);
            CommonUtils.hold(4);
            loader.uploadAttachments("po_category.txt", essDriver);
            loader.uploadAttachments("po_category_option.txt", essDriver);

        } catch (Exception uploadFile) {
            System.out.println("Error while try to upload Profile category/Options CSV file to UCM : " + uploadFile.getMessage());
            uploadFile.printStackTrace();  Assert.assertTrue(false);
        }
        testData.clear();
    }

    @Test(description = "Validate loader:PROFILE_CATEGORIES/PROFILE_CATEGORY_OPTIONS", dependsOnMethods = "testCase12", priority = 13)
    public void testCase13() {

        try {
            testData.clear();
            testData.add(6);
            testData.add(3);
            testData.add(2);
            testData.add(1);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId1 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "po_category.txt", "PROFILE_CATEGORIES", essDriver);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "po_category_option.txt", "PROFILE_CATEGORY_OPTIONS", essDriver);

            if (essJobId1 == null && essJobId2 == null) {
                Assert.assertTrue(false, "essJobID1 and essJobId2 are null");
                System.out.println("Job ID's returned are NULL");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("PROFILE_CATEGORIES", testData, essJobId1) && csvLoader.ValidateEssLogs("PROFILE_CATEGORY_OPTIONS", testData, essJobId2));

                essDriver.get(taskListURL);
                CommonUtils.hold(4);
                navTF.navigateToAOLTaskFlows("Manage Profile Categories", essDriver);
                CommonUtils.hold(5);
                prfCateUtil.searchProfileCategory("PCNAME", essDriver);
                Assert.assertTrue(essDriver.findElements(By.xpath("//span[contains(text(),'PCCODE')]")).size() == 2, "Total number of check PC : 2");
                Assert.assertTrue(essDriver.findElements(By.xpath("//span[contains(text(),'UPDATE')]")).size() == 2, "Total number of UPDATES : 2");
                Assert.assertTrue(essDriver.findElements(By.xpath("//img[@title='checked']")).size() == 2, "Total number of check box : 2");
                Assert.assertTrue(essDriver.findElement(By.xpath("//span[contains(text(),'Oracle Municipal Code Officer')]")).isDisplayed(), "Total number of check box : 2");

                prfCateUtil.verifyStatus1("PCNAME UPDATE", "Enable Artifacts Preloading", essDriver);
                Assert.assertTrue(essDriver.findElement(By.xpath("//input[@value='3']")).isDisplayed(), "DisplaySeq value : 3");
                prfCateUtil.verifyStatus1("PCNAME2", "Artifacts Preloading File Location", essDriver);
                Assert.assertTrue(essDriver.findElement(By.xpath("//input[@value='2']")).isDisplayed(), "DisplaySeq value : 2");
            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase13 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }
        testData.clear();
    }


    @Test(description = "Validate loader:VALUESET_VALUES", dependsOnMethods = "testCase01", priority = 14)
    public void testCase14() {

        try {
            testData.clear();
            testData.add(16);
            testData.add(16);
            testData.add(0);
            testData.add(0);
            testData.add(0);
            testData.add(0);
            testData.add(16);
            testData.add(0);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "ValueSet5.txt", "VALUESET_VALUES", essDriver);

            if (essJobId2 == null) {
                System.out.println("INFO : Job ID returned NULL. Hence not processing");
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("VALUESET_VALUES", testData, essJobId2));
                vsUtils.navigateToManageValueSettf(essDriver);
                assertEquals(loader.verifyUploadDataOnValueSetUI(essDriver, "ValueSet_WS_CHAR_DEP_TTEXT_ML20"), 2);
                assertEquals(loader.verifyUploadDataOnValueSetUI(essDriver, "ValueSet_WS_CHAR_IND_TTEXT_ML20"), 2);
            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase14 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }

        testData.clear();

    }

    @Test(description = "Upload DFF loader csv files", priority = 15)
    public void testCase15() {
        try {
            essDriver.get(expImpURL);
            CommonUtils.hold(2);
            loader.uploadAttachments("dffcontext.txt", essDriver);
            loader.uploadAttachments("dfflabelcreate.txt", essDriver);
            loader.uploadAttachments("dffgseg.txt", essDriver);

        } catch (Exception uploadFile) {
            System.out.println("Error while try to upload DFF CSV file to UCM : " + uploadFile.getMessage());
            uploadFile.printStackTrace();  Assert.assertTrue(false);
        }
        testData.clear();
    }

    @Test(description = "Validate loader:DFF_LABELED_SEGMENT", dependsOnMethods = "testCase15", priority = 16)
    public void testCase16() {

        try {
            testData.clear();
            testData.add(3);
            testData.add(1);
            testData.add(2);
            testData.add(0);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "dfflabelcreate.txt", "DFF_LABELED_SEGMENT", essDriver);

            if (essJobId2 == null) {
                System.out.println("INFO : Job ID returned NULL. Hence not processing");
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("DFF_LABELED_SEGMENT", testData, essJobId2));
                dffSql.goToSegmentLabel("PER_ASG_DF", essDriver);
                dffSql.getSegmentLabel("LabelCode", essDriver, 2);
            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase16 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }

        testData.clear();

    }


    @Test(description = "Validate loader:DFF_CONTEXT", dependsOnMethods = "testCase15", priority = 17)
    public void testCase17() {

        try {
            testData.clear();
            testData.add(12);
            testData.add(7);
            testData.add(3);
            testData.add(2);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "dffcontext.txt", "DFF_CONTEXT", essDriver);

            if (essJobId2 == null) {
                System.out.println("ERROR : Job ID returned NULL. Hence not processing");
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("DFF_CONTEXT", testData, essJobId2));
                dffSql.goToContextPage("IRC_REQUISITIONS_DFF", essDriver);
                dffSql.getContextCode("CC_NAME", essDriver, 2);

            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase17 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }

        testData.clear();

    }

    //	@Test(description = "Validate loader:DFF_CONTEXT_SEGMENT", dependsOnMethods= "testCase15",priority=18)
    public void testCase18() {

        try {
            testData.clear();
            testData.add(3);
            testData.add(1);
            testData.add(2);
            testData.add(0);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "dffconseg.txt", "DFF_SEGMENT", essDriver);

            if (essJobId2 == null) {
                System.out.println("ERROR : Job ID returned NULL. Hence not processing");
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("DFF_SEGMENT", testData, essJobId2));
                dffSql.goToContextSegmentDetails("PER_ASG_DF", "CC_NAME1", essDriver);
                dffSql.getContextSegmentDetails("DPK", essDriver, 2);
            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase18 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }

        testData.clear();

    }

    @Test(description = "Validate loader:DFF_GLOBAL_SEGMENT", dependsOnMethods = "testCase15", priority = 19)
    public void testCase19() {

        try {
            testData.clear();
            testData.add(31);
            testData.add(9);
            testData.add(19);
            testData.add(3);


            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "dffgseg.txt", "DFF_SEGMENT", essDriver);

            if (essJobId2 == null) {
                System.out.println("ERROR : Job ID returned NULL. Hence not processing");
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("DFF_SEGMENT", testData, essJobId2));
//				dffSql.goToGlobalegmentDetails("IRC_REQUISITIONS_DFF", "SOAR", essDriver);
                dffSql.goToContextSegmentDetails("IRC_REQUISITIONS_DFF", "CC_NAME1", essDriver);
                dffSql.getContextSegmentDetails("IRC_DPK", essDriver, 38);
            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase19 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }

        testData.clear();

    }

    @Test(description = "Validate USER_PROFILE_VALUES", priority = 20)
    public void testCase20() {

        try {

            essDriver.get(expImpURL);
            CommonUtils.hold(4);
            loader.uploadAttachments("PROFILE1.txt", essDriver);
        } catch (Exception uploadFile) {
            System.out.println("Error while try to upload profile values CSV file to UCM : " + uploadFile.getMessage());
            uploadFile.printStackTrace();  Assert.assertTrue(false);
        }
        testData.clear();
    }


    @Test(description = "Validate loader:USER_PROFILE_VALUES", dependsOnMethods = "testCase20", priority = 21)
    public void testCase21() {

        try {
            testData.clear();
            testData.add(5);
            testData.add(1);
            testData.add(4);
            testData.add(0);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "PROFILE1.txt", "USER_PROFILE_VALUES", essDriver);

            if (essJobId2 == null) {
                System.out.println("ERROR : Job ID returned NULL. Hence not processing");
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("USER_PROFILE_VALUES", testData, essJobId2));
                essDriver.get(taskListURL);
                CommonUtils.hold(4);
                navTF.navigateToAOLTaskFlows("Manage Administrator Profile Values", essDriver);
                CommonUtils.explicitWait(essDriver.findElement(By.xpath("//h1[contains(text(),'Manage Administrator Profile Values')]")), "Click", "", essDriver);
                CommonUtils.hold(4);
                loader.verifyUploadDataOnProfileUI(essDriver, "AFLOG_LEVEL", "MCOOPER", "Finest");
            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase19 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }

        testData.clear();

    }


    @Test(description = "Enable DS for Value SetCode", priority = 22)
    public void testCase22() {
        try {
            loader.searchVSTableCode("ABCUN_ITEM_NUMBER", "DS_SOAR1", essDriver, vsUtils);

        } catch (Exception vsExp) {
            System.out.println("ERROR : While trying to enable DS on a VS code : " + vsExp.getMessage());
            vsExp.printStackTrace();  Assert.assertTrue(false);

        }
    }

    @Test(description = "Upload CSV File : DS CONDITIONS/GRANTS", priority = 23, dependsOnMethods = "testCase22")
    public void testCase23() {

        try {
            essDriver.get(expImpURL);
            CommonUtils.hold(2);
            loader.uploadAttachments("DS_INSTANCE.txt", essDriver);
            loader.uploadAttachments("DS_GRANTS.txt", essDriver);
        } catch (Exception uploadFile) {
            System.out.println("Error while try to upload DS Condition/Grants CSV file to UCM : " + uploadFile.getMessage());
            uploadFile.printStackTrace();  Assert.assertTrue(false);
        }
        testData.clear();
    }


    @Test(description = "Validate loader:DS_CONDITIONS", dependsOnMethods = {"testCase22", "testCase23"}, priority = 24)
    public void testCase24() {

        try {
            testData.clear();
            testData.add(7);
            testData.add(2);
            testData.add(5);
            testData.add(0);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "DS_INSTANCE.txt", "DS_CONDITIONS", essDriver);

            if (essJobId2 == null) {
                System.out.println("ERROR : Job ID returned NULL. Hence not processing");
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("DS_CONDITIONS", testData, essJobId2));
                essDriver.get(GetDriverInstance.EnvironmentName);
                CommonUtils.hold(4);
                Assert.assertTrue((loader.checkDSCount(essDriver, dsUtil, "CONDITION", "DS_SOAR1", "SOARCODE") == 1));
                essDriver.get(GetDriverInstance.EnvironmentName);
                CommonUtils.hold(4);
                Assert.assertTrue((loader.checkDSCount(essDriver, dsUtil, "CONDITION", "FND_TREE_STRUCTURE", "SOARCODE") == 2));

            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase22 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }

        testData.clear();

    }

    @Test(description = "Validate loader:DS_GRANTS", priority = 25, dependsOnMethods = {"testCase24"})
    public void testCase25() {

        try {
            testData.clear();
            testData.add(6);
            testData.add(3);
            testData.add(3);
            testData.add(0);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "DS_GRANTS.txt", "DS_GRANTS", essDriver);

            if (essJobId2 == null) {
                System.out.println("ERROR : Job ID returned NULL. Hence not processing");
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("DS_GRANTS", testData, essJobId2));
                essDriver.get(GetDriverInstance.EnvironmentName);
                CommonUtils.hold(4);
                Assert.assertTrue((loader.checkDSCount(essDriver, dsUtil, "GRANTS", "DS_SOAR1", "SOAR GRANTS") == 1));
                essDriver.get(GetDriverInstance.EnvironmentName);
                CommonUtils.hold(4);
                Assert.assertTrue((loader.checkDSCount(essDriver, dsUtil, "GRANTS", "FND_TREE", "SOAR GRANTS") == 2));

            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase22 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }

        testData.clear();

    }


    @Test(description = "Upload CSV File : DOC_CATE/SEQ/ASSIGN", priority = 26)
    public void testCase26() {

        try {
            essDriver.get(expImpURL);
            CommonUtils.hold(4);
            loader.uploadAttachments("DOC_CATEGORY.txt", essDriver);
            loader.uploadAttachments("DOC_SEQ.txt", essDriver);
        } catch (Exception uploadFile) {
            System.out.println("Error while try to upload DocSeq CSV file to UCM : " + uploadFile.getMessage());
            uploadFile.printStackTrace();  Assert.assertTrue(false);
        }
        testData.clear();
    }

    @Test(description = "Validate loader:DOCUMENT_SEQUENCE_CATEGORY", priority = 27, dependsOnMethods = {"testCase26"})
    public void testCase27() {

        try {
            testData.clear();
            testData.add(10);
            testData.add(4);
            testData.add(4);
            testData.add(2);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "DOC_CATEGORY.txt", "DOCUMENT_SEQUENCE_CATEGORY", essDriver);

            if (essJobId2 == null) {
                System.out.println("ERROR : Job ID returned NULL. Hence not processing");
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("DOCUMENT_SEQUENCE_CATEGORY", testData, essJobId2));
                essDriver.get(taskListURL);
                CommonUtils.hold(4);
                navTF.navigateToAOLTaskFlows("Manage Document Sequence Categories", essDriver);
                CommonUtils.hold(4);
                docUtils.searchDocCategory("SOAR%", essDriver);
                Assert.assertTrue(essDriver.findElements(By.xpath("//span[contains(text(),'SOAR_CATE')]")).size() == 4, "Total number of records inserted is : 4");
                Assert.assertTrue(essDriver.findElements(By.xpath("//input[@value='Oracle Municipal Code Officer']")).size() == 2, "Total number of records inserted is : 2");
                Assert.assertTrue(essDriver.findElements(By.xpath("//input[@value='SOAR_DESC2_UPDATES']")).size() == 1, "Total number of records inserted is : 1");
            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase27 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }

        testData.clear();

    }


    @Test(description = "Validate loader:DOCUMENT_SEQUENCE", priority = 28, dependsOnMethods = {"testCase26"})
    public void testCase28() {
        try {
            testData.clear();

            testData.add(11);
            testData.add(5);
            testData.add(3);
            testData.add(3);

            essDriver.get(essURL);
            CommonUtils.hold(4);
            File essJobId2 = csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "DOC_SEQ.txt", "DOCUMENT_SEQUENCE", essDriver);

            if (essJobId2 == null) {
                System.out.println("ERROR : Job ID returned NULL. Hence not processing");
                Assert.assertTrue(false, "essJobID is null");
            } else {
                Assert.assertTrue(csvLoader.ValidateEssLogs("DOCUMENT_SEQUENCE", testData, essJobId2));
                essDriver.get(taskListURL);
                CommonUtils.hold(4);
                navTF.navigateToAOLTaskFlows("Manage Document Sequences", essDriver);
                CommonUtils.hold(4);
                docUtils.searchDocSeq("DPK%");
                Assert.assertTrue(essDriver.findElements(By.xpath("//span[contains(text(),'DPK')]")).size() == 3, "Total number of records inserted is : 3");
            }
        } catch (Exception ex) {
            System.out.println("Exception while running testCase27 :" + ex.getMessage());
            ex.printStackTrace();  Assert.assertTrue(false);
        }

        testData.clear();

    }

    @AfterClass
    public void close() {
        essDriver.quit();
    }

}
