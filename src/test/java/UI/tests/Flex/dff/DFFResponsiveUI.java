package UI.tests.Flex.dff;


import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageDefinitions.UI.oracle.applcore.qa.Dff.DFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Loader.EssJobUsingCSVLoader;
import pageDefinitions.UI.oracle.applcore.qa.Loader.LoaderWrapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DFFResponsiveUI extends GetDriverInstance {

    private EssJobUsingCSVLoader csvLoader = null;
    private LoaderWrapper loader = null;
    private String id;
    private WebDriver clovDriver = null;
    private DFFUtils dfUtils = null;
    private ApplicationLogin login = null;
    private String user1 = "";
    private String password = "";
    private Statement st;
    private ResultSet rt;

    @Parameters({"irc_user", "pwd"})
    @BeforeClass
    public void setUp(String user, String passWord) throws Exception {
        user1 = user;
        password = passWord;
        try {
            id = CommonUtils.uniqueId();
            clovDriver = getDriverInstanceObject();
            dfUtils = new DFFUtils(clovDriver);
            login = new ApplicationLogin(clovDriver);
            csvLoader = new EssJobUsingCSVLoader(clovDriver);
            loader = new LoaderWrapper(clovDriver);
            login.login(user, passWord, clovDriver);
            CommonUtils.waitForInvisibilityOfElement(login.username, clovDriver, 10);
            DbResource.sqlStatement.executeUpdate(loader.gatherStats());
        } catch (Exception inlineSearch) {
            System.out.println("Exception in method setUp() : " + inlineSearch.getMessage());
            inlineSearch.printStackTrace();
            Assert.fail();
        }
    }

    private void uploadTestData() {
        try {
            clovDriver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_file_import_and_export"));
            CommonUtils.hold(3);
            loader.uploadAttachments("VS_CODE2.txt", clovDriver);
            loader.uploadAttachments("VS1_VALUES.txt", clovDriver);
            loader.uploadAttachments("dffcontext.txt", clovDriver);
            loader.uploadAttachments("dffgseg.txt", clovDriver);

            CommonUtils.hold(3);

            clovDriver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_scheduled_processes_fuse_plus"));
            CommonUtils.hold(3);
            csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "VS_CODE2.txt", "VALUE_SET", clovDriver);

            clovDriver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_scheduled_processes_fuse_plus"));
            CommonUtils.hold(3);
            csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "VS1_VALUES.txt", "VALUESET_VALUES", clovDriver);

            clovDriver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_scheduled_processes_fuse_plus"));
            CommonUtils.hold(3);
            csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "dffcontext.txt", "DFF_CONTEXT", clovDriver);

            clovDriver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_scheduled_processes_fuse_plus"));
            CommonUtils.hold(3);
            csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "dffgseg.txt", "DFF_SEGMENT", clovDriver);
            CommonUtils.hold(3);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Test(priority = 1, description = "Deploy Flex")
    public void deployIRCFlex() throws SQLException {

        try {
            uploadTestData();
            String sqlStatement = "select * from fnd_df_segments_b where descriptive_flexfield_code='IRC_REQUISITIONS_DFF' and segment_code like 'IRC_DPK%'";
            st = DbResource.sqlConnectionSession.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rt = st.executeQuery(sqlStatement);
            rt.last();
            int totalRow = rt.getRow();
            rt.beforeFirst();

            System.out.println("Total Number Of Segments For IRC_REQUISITIONS_DFF :" + totalRow);

            if (totalRow == 19) {
                Assert.assertTrue(true);
            } else {
                Assert.fail("Total Number Of Segments Count Does Not Match Value IRC_REQUISITIONS_DFF : 19");
            }

            dfUtils.enableContextSegmentField(clovDriver, "IRC_REQUISITIONS_DFF");

            dfUtils.deployFlex("IRC_REQUISITIONS_DFF", clovDriver);
            CommonUtils.hold(5);
            login.logout(clovDriver);
            CommonUtils.hold(2);
            login.login(user1, password, clovDriver);
            CommonUtils.hold(5);

        } catch (Exception exp) {
            System.out.println("ERROR : Exception in deployIRCFlex() : " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        } finally {
            rt.close();
            st.close();
        }

    }


    @Test(priority = 2, dependsOnMethods = "deployIRCFlex", description = "Validate Global Segments default values")
    public void checkSegmentsDefaultValue() {
        try {
            dfUtils.navigateToIRCPage(clovDriver, id);
            Assert.assertEquals(dfUtils._segment1.getAttribute("value"), "70", "Check constant default value");
            Assert.assertEquals(dfUtils._segment2.getAttribute("value"), "IRC_REQUISITIONS_DFF", "Check SQL default value");
            Assert.assertEquals(dfUtils._segment3.getAttribute("value"), "ZFRCE1101_Jacob_ZBEN", "Check Groovy default value");
            Assert.assertEquals(dfUtils._segment4.getAttribute("value"), "India");
            Assert.assertEquals(dfUtils._segment5.getAttribute("value"), "Bangalore");
            Assert.assertEquals(dfUtils._segment6.getAttribute("value"), "India");
            Assert.assertEquals(dfUtils._segment7.getAttribute("value"), "Bangalore");
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Validating checkSegmentsDefaultValue() " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }
    }


    @Test(priority = 3, dependsOnMethods = "deployIRCFlex", description = "Validate Global Segment Values On Changing Context Segment")
    public void checkSegmentsDefaultValue1() {
        try {

            dfUtils.waitForElementToAppear(clovDriver, dfUtils._contextCode);
            dfUtils._contextCode.click();dfUtils.waitForElementToAppear(clovDriver, dfUtils._contextSegmentCode);
            dfUtils._contextSegmentCode.click();dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment8);

            Assert.assertEquals(dfUtils._segment4.getAttribute("value"), "India");
            Assert.assertEquals(dfUtils._segment5.getAttribute("value"), "Bangalore");
            Assert.assertEquals(dfUtils._segment6.getAttribute("value"), "India");
            Assert.assertEquals(dfUtils._segment7.getAttribute("value"), "Bangalore");

            dfUtils._contextCode.click();
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._contextSegmentCode1);
            dfUtils._contextSegmentCode1.click();
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment4);

            Assert.assertEquals(dfUtils._segment4.getAttribute("value"), "India");
            Assert.assertEquals(dfUtils._segment5.getAttribute("value"), "Bangalore");
            Assert.assertEquals(dfUtils._segment6.getAttribute("value"), "India");
            Assert.assertEquals(dfUtils._segment7.getAttribute("value"), "Bangalore");

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Validating Test checkSegmentsDefaultValue1(). " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 4, dependsOnMethods = "deployIRCFlex", description = "Validate Global Segment Ind and Dep Segment Values ")
    public void checkIndependentDependentValue() {
        try {
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment4Lov), clovDriver);
            dfUtils._segment4Lov.click();
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment4USValue);
            dfUtils._segment4USValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment4USValue, clovDriver, 10);
            System.out.println("Why Error");

            String val = "";

            if (dfUtils.checkEmptyValue(clovDriver, dfUtils._segment5)) {
                Assert.assertTrue(true);
            } else {
                val = dfUtils._segment5.getAttribute("value");
                System.out.println("ERROR : No Empty Value For Dependent Segment On Changing Ind Segment: IRC_DPK5" + val);
                Assert.fail();
            }

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment5Lov), clovDriver);
            dfUtils._segment5Lov.click();
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment5LovNYValue);
            Assert.assertTrue(dfUtils._segment5LovNYValue.isDisplayed());
            dfUtils._segment5LovNYValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment5Lov, clovDriver, 25);
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test checkIndependentDependentValue() " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 5, dependsOnMethods = "deployIRCFlex", description = "Validate Ind and Dep Segment Values Inline ")
    public void checkIndependentDependentValue1() {
        try {
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment6), clovDriver);
            dfUtils._segment6.clear();
            dfUtils._segment6.sendKeys("US");
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment6USValue);
            dfUtils._segment6USValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment6USValue, clovDriver, 10);

            String val = "";
            if (dfUtils.checkEmptyValue(clovDriver, dfUtils._segment7)) {
                Assert.assertTrue(true);
            } else {
                val = dfUtils._segment7.getAttribute("value");
                System.out.println("ERROR : No Empty Value For Dependent Segment On Changing Ind Segment: IRC_DPK7 " + val);
                Assert.fail();
            }

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment7), clovDriver);
            dfUtils._segment7.click();
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment7NYValue);
            dfUtils._segment7NYValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment7NYValue, clovDriver, 8);
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Executing Test checkIndependentDependentValue1(): " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }
    }


    @Test(priority = 6, dependsOnMethods = "deployIRCFlex", description = "Validate Sensitive Segment Default Values")
    public void checkSensitiveSegmentDefaultValues() {
        try {
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._contextCode), clovDriver);
            dfUtils._contextCode.click();
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._contextSegmentCode);
            dfUtils._contextSegmentCode.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._contextSegmentCode, clovDriver, 10);
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment8);

            Assert.assertEquals(dfUtils._segment8.getAttribute("value"), "India");
            Assert.assertEquals(dfUtils._segment9.getAttribute("value"), "Bangalore");
            Assert.assertEquals(dfUtils._segment10.getAttribute("value"), "India");
            Assert.assertEquals(dfUtils._segment11.getAttribute("value"), "Bangalore");
            Assert.assertEquals(dfUtils._segment12.getAttribute("value"), "Bangalore");
            Assert.assertEquals(dfUtils._segment14.getAttribute("value"), "Bangalore");
            Assert.assertEquals(dfUtils._segment15.getAttribute("value"), "Bangalore");

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Executing Test checkSensitiveSegmentDefaultValues() :" + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 7, dependsOnMethods = "deployIRCFlex", description = "Check SS Dependent Values Clears Out")
    public void checkSensitiveSegmentValues() {
        try {
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment8), clovDriver);
            dfUtils._segment8.click();
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment8InValue);
            dfUtils._segment8InValue.click();

            dfUtils.waitForElementToAppear(clovDriver, dfUtils.genericError);

            try {
                if (dfUtils.genericError.isDisplayed()) {
                    System.out.println("INFO : Error PopUp Displayed");
                    dfUtils.genericErrorOKButton.click();
                }

            } catch (Exception exp) {
                Assert.fail();
            }

            String val1 = dfUtils._segment9.getAttribute("value");
            String val2 = dfUtils._segment14.getAttribute("value");
            String val3 = dfUtils._segment12.getAttribute("value");

            System.out.println("VAL1 " + val1 + " VAL2 " + val2 + " VAL3 " + val3);

            if (val1 == null || val1.isEmpty()) {
                Assert.assertTrue(true);
            } else {
                System.out.println("ERROR : Segment9 Field Is Not Empty");
                Assert.fail();
            }

            if (val2 == null || val2.isEmpty()) {
                Assert.assertTrue(true);
            } else {
                System.out.println("ERROR : Segment14 Field Is Not Empty");
                Assert.fail();
            }

            if (val3 == null || val3.isEmpty()) {
                Assert.assertTrue(true);
            } else {
                System.out.println("ERROR : Segment12 Field Is Not Empty");
                Assert.fail("Segment 12 Of Type POP Field Is Not Cleared Out And Is An Issue Currently");
            }


        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Executing Test checkSensitiveSegmentValues()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 8, dependsOnMethods = "deployIRCFlex", description = "Validate Mandate Field")
    public void CheckManDateFieldValidation() {
        try {
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils.resSaveAndCloseBtn), clovDriver);
            dfUtils.resSaveAndCloseBtn.click();
            CommonUtils.hold(3);
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment9), clovDriver);
            dfUtils._segment9.click();
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.visibilityOf(dfUtils._segment9ErrorMessage), clovDriver);
            Assert.assertTrue(dfUtils._segment9ErrorMessage.isDisplayed());

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Executing Test CheckManDateFieldValidation()");
            Assert.fail(exp.getMessage());
            exp.printStackTrace();
        }

    }

    @Test(priority = 9, dependsOnMethods = "deployIRCFlex", description = "Validate SS dependent values")
    public void validateSSDependentValues() {
        try {
            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment9Lov), clovDriver);
            dfUtils._segment9Lov.click();
//            CommonUtils.hold(4);
//            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment9LovNYValue), clovDriver);
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment9LovNYValue);
            Assert.assertTrue(dfUtils._segment9LovNYValue.isDisplayed());

            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment9LovUSValue), clovDriver);
            System.out.println("INFO : Segment9 value displayed properly : San Francisco");
            Assert.assertTrue(dfUtils._segment9LovUSValue.isDisplayed());
            dfUtils._segment9LovNYValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment9LovNYValue, clovDriver, 10);

            dfUtils._segment14.clear();
            dfUtils._segment14.click();

//            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment14LovNYValue), clovDriver);
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment14LovNYValue);
            Assert.assertTrue(dfUtils._segment14LovNYValue.isDisplayed());
            System.out.println("INFO : Segment14 value displayed : New York");

//            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment14LovSFValue), clovDriver);
            Assert.assertTrue(dfUtils._segment14LovSFValue.isDisplayed());
            System.out.println("INFO : Segment14 value displayed : San Francisco");
            dfUtils._segment14LovNYValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment14LovNYValue, clovDriver, 20);

            dfUtils._segment12.clear();
//            CommonUtils.waitExplicitly(4, 1, ExpectedConditions.elementToBeClickable(dfUtils._segment12Lov), clovDriver);
            dfUtils._segment12Lov.click();
//            CommonUtils.waitExplicitly(15, 2, ExpectedConditions.elementToBeClickable(dfUtils._segment12SearchUI), clovDriver);
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment12SearchUI);
            dfUtils._segment12SearchUI.click();
            CommonUtils.waitExplicitly(15, 1, ExpectedConditions.elementToBeClickable(dfUtils._segment12SearchUINYValue), clovDriver);
            Assert.assertTrue(dfUtils._segment12SearchUINYValue.isDisplayed());
            System.out.println("INFO : Segment9 value displayed properly : New York");


            Assert.assertTrue(dfUtils._segment12SearchUISFValue.isDisplayed());
            System.out.println("INFO : Segment9 value displayed properly : San Francisco");
            dfUtils._segment12SearchUINYValue.click();
            CommonUtils.hold(3);
            dfUtils._segment12OK.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment12OK, clovDriver, 20);
        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Executing Test validateSSDependentValues()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 10, dependsOnMethods = "deployIRCFlex", description = "Validate SS Inline dependent values")
    public void validateSSValuesInline() {
        try {

            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment10), clovDriver);
            dfUtils._segment10.clear();
            CommonUtils.hold(2);
            dfUtils._segment10.click();
//            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment10LovUSValueIrc), clovDriver);
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment10LovUSValueIrc);
            dfUtils._segment10LovUSValueIrc.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment10LovUSValueIrc, clovDriver, 10);

            try {
                CommonUtils.waitExplicitly(15, 7, ExpectedConditions.visibilityOf(dfUtils._segment10ErrorMessage), clovDriver);
                Assert.assertTrue(dfUtils._segment10ErrorMessage.isDisplayed(), "Error pops up for segment 11 which is mandate");

                if (dfUtils.errorOKBtn.isDisplayed()) {
                    System.out.println("========================> Error OK Button Clicked");
                    CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils.errorOKBtn), clovDriver);
                    dfUtils.errorOKBtn.click();
                    CommonUtils.waitForInvisibilityOfElement(dfUtils.errorOKBtn, clovDriver, 10);
                } else
                    System.out.println("========================> Error OK Button not clicked");

            } catch (Exception error) {
                System.out.println("ERROR : Required Error Pop Up Is Not Displayed. " + error.getMessage());
                error.printStackTrace();
            }

            String val1 = dfUtils._segment11.getAttribute("value");
            String val2 = dfUtils._segment15.getAttribute("value");

            if (val1 == null || val1.isEmpty()) {
                Assert.assertTrue(true);
                System.out.println("INFO : Empty Value Found For Segment11");
            } else {
                System.out.println("ERROR : Segment11 Field Is Not Empty");
                Assert.fail();
            }

            if (val2 == null || val2.isEmpty()) {
                Assert.assertTrue(true);
                System.out.println("INFO : Empty Value Found For Segment15");
            } else {
                System.out.println("ERROR : Segment15 Field Is Not Empty");
                Assert.fail();
            }

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Executing Test validateSSValuesInline()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 11, dependsOnMethods = "deployIRCFlex", description = "Validate SS Inline dependent values")
    public void validateSSDependentValuesInline() {
        try {

            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment11), clovDriver);
            dfUtils._segment11.clear();
            CommonUtils.hold(2);
            dfUtils._segment11.click();

//            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment11LovNYValue), clovDriver);
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment11LovNYValue);
            Assert.assertTrue(dfUtils._segment11LovNYValue.isDisplayed(), "Segment14 value displayed properly : New York");
            System.out.println("INFO : Segment14 value displayed properly : New York");

            Assert.assertTrue(dfUtils._segment11LovSFValue.isDisplayed());
            System.out.println("INFO : Segment14 value displayed properly : San Francisco");
            dfUtils._segment11LovNYValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment11LovNYValue, clovDriver, 10);

            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment15Lov), clovDriver);
            dfUtils._segment15Lov.click();

//            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment15LovNYValue), clovDriver);
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment15LovNYValue);
            Assert.assertTrue(dfUtils._segment15LovNYValue.isDisplayed());
            System.out.println("INFO : Segment15 value displayed properly : New York");

//            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment15LovSFValue), clovDriver);
            Assert.assertTrue(dfUtils._segment15LovSFValue.isDisplayed());
            System.out.println("INFO : Segment15 value displayed properly : San Francisco");
            dfUtils._segment15LovNYValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment15LovNYValue, clovDriver, 10);

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Executing Test validateSSDependentValuesInline()");
            exp.printStackTrace();
            Assert.fail();
        }
    }


    @Test(priority = 12, dependsOnMethods = "deployIRCFlex", description = "Validate SS Inline Table Values")
    public void validateSSTableInline() {
        try {
            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment13), clovDriver);
            dfUtils._segment13.clear();
            CommonUtils.hold(2);
            dfUtils._segment13.click();
//            CommonUtils.waitExplicitly(15, 7, ExpectedConditions.elementToBeClickable(dfUtils._segment13LovValue), clovDriver);
            dfUtils.waitForElementToAppear(clovDriver, dfUtils._segment13LovValue);
            Assert.assertTrue(dfUtils._segment13LovValue.isDisplayed());
            System.out.println("INFO : Segment13 value displayed properly : Reporting name");
            Assert.assertTrue(dfUtils._segment13LovValue1.isDisplayed());
            System.out.println("INFO : Segment13 value displayed properly : State code");
            dfUtils._segment13LovValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment13LovValue, clovDriver, 10);

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Executing Test validateSSTableInline()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 13, dependsOnMethods = "deployIRCFlex", description = "Ind and Table : Number Data Type Global Segments")
    public void validateGlobalSegNumberTypeInline() {
        try {
            CommonUtils.waitExplicitly(3, 1, ExpectedConditions.elementToBeClickable(dfUtils._segment16), clovDriver);
            dfUtils._segment16.clear();
            dfUtils._segment16.click();
            CommonUtils.hold(5);

            int size = dfUtils._segment16LovAllValues.size();

            System.out.println("Size " + size);

            if (size > 0) {
                Assert.assertTrue(true, "Number Based Table Segment Showing Value In Inline Search");
            } else {
                Assert.fail("Number Based Table Segment Not Showing Value In Inline Search");
            }

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment16LovSingaleValue), clovDriver);

            System.out.println("Get Text from : " + dfUtils._segment16LovSingaleValue.getText());

            dfUtils._segment16LovSingaleValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment16LovSingaleValue, clovDriver, 8);

            dfUtils._segment18.clear();
            dfUtils._segment18.click();
            CommonUtils.hold(5);

            size = clovDriver.findElements(By.xpath("//table[contains(@id,'DPK18')]/tr")).size();

            if (size > 0) {
                Assert.assertTrue(true, "Number Based Independent Segment Showing Value In Inline Search");
            } else {
                Assert.fail("Number Based Independent Segment Not Showing Value In Inline Search");
            }

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment18SingleValue), clovDriver);
            dfUtils._segment18SingleValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment18SingleValue, clovDriver, 8);


        } catch (Exception exp) {
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 14, dependsOnMethods = "deployIRCFlex", description = "Ind and Table : Number Data Type Context Segments")
    public void validateSSNumberTypeInline() {
        try {

            dfUtils._segment17.clear();
            dfUtils._segment17.click();
            CommonUtils.hold(3);

            int size = clovDriver.findElements(By.xpath("//table[contains(@id,'DPK17')]/tr")).size();

            if (size > 0) {
                Assert.assertTrue(true, "Number Based Table Segment Showing Value In Inline Search");
            } else {
                Assert.fail("Number Based Table Segment Not Showing Value In Inline Search");
            }

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment17SingleValue), clovDriver);

            System.out.println("Get Text from : " + dfUtils._segment17SingleValue.getText());

            dfUtils._segment17SingleValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment17SingleValue, clovDriver, 10);

            dfUtils._segment19.clear();
            dfUtils._segment19.click();
            CommonUtils.hold(3);

            size = clovDriver.findElements(By.xpath("//table[contains(@id,'DPK19')]/tr")).size();

            if (size > 0) {
                Assert.assertTrue(true, "Number Based Independent Segment Showing Value In Inline Search");
            } else {
                Assert.fail("Number Based Independent Segment Not Showing Value In Inline Search");
            }

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment19SingleValue), clovDriver);
            dfUtils._segment19SingleValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment19SingleValue, clovDriver, 8);

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils.resSaveAndCloseBtn), clovDriver);
            dfUtils.resSaveAndCloseBtn.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils.resSaveAndCloseBtn, clovDriver, 10);

            try {
                CommonUtils.waitExplicitly(40, 20, ExpectedConditions.elementToBeClickable(dfUtils.confirMation), clovDriver);
            } catch (Exception exp) {
                System.out.println("INFO : No Confirmation Pop Up Displayed.");
            }


        } catch (Exception exp) {
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        clovDriver.quit();
    }

}
