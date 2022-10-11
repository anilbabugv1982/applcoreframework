package UI.tests.Flex.dff;

import UtilClasses.UI.DbResource;
import TestBase.UI.GetDriverInstance;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import org.openqa.selenium.JavascriptExecutor;
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

public class DFFTableInline extends GetDriverInstance {

    private EssJobUsingCSVLoader csvLoader = null;
    private LoaderWrapper loader = null;
    private String id;
    private JavascriptExecutor js;
    private WebDriver clovDriver = null;
    private DFFUtils dfUtils = null;
    private ApplicationLogin login = null;
    private String user1 = "";
    private String password = "";
    private Statement st;
    private ResultSet rt;

    @Parameters({"person_user", "pwd"})
    @BeforeClass
    public void setUp(String user, String passWord) throws Exception {
        user1 = user;
        password = passWord;
        try {
            id = CommonUtils.uniqueId();
            clovDriver = getDriverInstanceObject();
            js = (JavascriptExecutor) clovDriver;
            dfUtils = new DFFUtils(clovDriver);
            login = new ApplicationLogin(clovDriver);
            csvLoader = new EssJobUsingCSVLoader(clovDriver);
            loader = new LoaderWrapper(clovDriver);
            login.login(user, passWord, clovDriver);
            CommonUtils.waitForInvisibilityOfElement(login.username, clovDriver, 10);
            DbResource.sqlStatement.executeUpdate(loader.gatherStats());
        } catch (Exception exp) {
            System.out.println("ERROR : Exception in Setup() Block : " + exp.getMessage());
            exp.printStackTrace();
        }
    }

    private void uploadTestData() {
        try {
            clovDriver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_file_import_and_export"));
            loader.uploadAttachments("dffinlinetable.txt", clovDriver);
            clovDriver.navigate().to(GetDriverInstance.EnvironmentName.replace("FuseWelcome", "FuseOverview?fndGlobalItemNodeId=itemNode_tools_scheduled_processes_fuse_plus"));
            CommonUtils.waitForInvisibilityOfElement(login.username, clovDriver, 4);
            csvLoader.runCsvESSJob("ESS process for Applcore csv file upload", "crm/appointment/import", "dffinlinetable.txt", "DFF_SEGMENT", clovDriver);
            CommonUtils.hold(5);

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    @Test(priority = 1, description = "Deploy Flex")
    public void deployPerPhoneFlex() throws SQLException {

        try {

            uploadTestData();
            String sqlStatement="select * from fnd_df_segments_b where descriptive_flexfield_code='PER_PHONES_DFF' and segment_code like 'NAT_DPK%'";
            st= DbResource.sqlConnectionSession.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rt=st.executeQuery(sqlStatement);
            rt.last();
            int totalRow=rt.getRow();
            rt.beforeFirst();

            System.out.println("Total Number Of Segment Count  :"+totalRow);

            if(totalRow==15){
                Assert.assertTrue(true);
            }else{
                Assert.fail("Total Number Of Segment Count Does Not Match value : 15");
            }

            dfUtils.deployFlex("PER_PHONES_DFF",clovDriver);

//            clovDriver.get(GetDriverInstance.EnvironmentName.replace("FuseWelcome", "FuseTaskListManagerTop"));
//            CommonUtils.hold(3);
//            dfUtils.goToContextSegmentDetails("PER_PHONES_DFF", "PHONE_EXTRAINFO", clovDriver);
//            dfUtils.getContextSegmentDetails("NAT_DPK", clovDriver, 30);
//            dfUtils.navigateToDFFtf(clovDriver);
//            dfUtils.searchDFFCode("PER_PHONES_DFF", clovDriver);
//            CommonUtils.explicitWait(dfUtils.editSaveCloseBtn, CommonUtils.ExplicitWaitCalls.Click.toString(), "", clovDriver);
//            CommonUtils.hold(3);
//            dfUtils.editSaveCloseBtn.click();
//            CommonUtils.hold(3);
//            CommonUtils.explicitWait(dfUtils.deployFlexBtn, "Click", "", clovDriver);
//            dfUtils.deployFlexBtn.click();
//            CommonUtils.explicitWait(dfUtils.confirmOKBtn, "Click", "", clovDriver);
//            CommonUtils.hold(5);
//
//            while (!dfUtils.confirmOKBtn.isEnabled()) {
//                CommonUtils.hold(3);
//            }//
//            dfUtils.confirmOKBtn.click();

            CommonUtils.hold(5);
            login.logout(clovDriver);
            CommonUtils.waitForInvisibilityOfElement(login.logOut,clovDriver,7);
            login.login(user1, password, clovDriver);
            CommonUtils.waitForInvisibilityOfElement(login.username,clovDriver,8);

        } catch (Exception exp) {
            System.out.println("ERROR : Exception In Setup Method " + exp.getMessage());
            exp.printStackTrace();
            Assert.fail();
        } finally {
            rt.close();
            st.close();
        }

    }

    @Test(priority = 2, dependsOnMethods = "deployPerPhoneFlex", description = "Validate global segment default values")
    public void checkGSDefaultValue() {
        try {
            dfUtils.navigateToHireAnEmployee("Vision Corp - Canada", "EMP" + id, clovDriver);
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils.nextBtn), clovDriver);
            dfUtils.nextBtn.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils.lastNameInputField, clovDriver, 10);
            CommonUtils.waitforElementtoClick(40, dfUtils.ph_2PageTitle, clovDriver);
//            CommonUtils.waitExplicitly(8,4, ExpectedConditions.elementToBeClickable(dfUtils.ph_2PageTitle),clovDriver);
            CommonUtils.explicitWait(dfUtils.ph_3CreateIcon, "Click", "", clovDriver);
            dfUtils.ph_3CreateIcon.click();
            CommonUtils.waitExplicitly(8, 3, ExpectedConditions.elementToBeClickable(dfUtils.ph_5TyepDD), clovDriver);
            dfUtils.ph_5TyepDD.click();
            CommonUtils.waitExplicitly(8, 3, ExpectedConditions.elementToBeClickable(dfUtils.ph_5TypeValue), clovDriver);
            dfUtils.ph_5TypeValue.click();
            CommonUtils.hold(2);
            dfUtils.ph_6CountryCode.clear();
            dfUtils.ph_6CountryCode.sendKeys("India");
            CommonUtils.hold(2);
            CommonUtils.waitExplicitly(6, 4, ExpectedConditions.elementToBeClickable(dfUtils.ph_65Result), clovDriver);
            dfUtils.ph_65Result.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils.ph_65Result, clovDriver, 4);
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils.ph_7Number), clovDriver);
            dfUtils.ph_7Number.clear();
            dfUtils.ph_7Number.sendKeys("9740067190");
            js.executeScript("arguments[0].click();", dfUtils.ph_4expandIcon);

            boolean status = false;
            int iteration = 0;

            while (!status && (iteration <= 10)) {
                try {
                    dfUtils.waitForElement(dfUtils._segment1, clovDriver);
                    status = dfUtils._segment1.isDisplayed();
                } catch (Exception exp) {
                    clovDriver.navigate().refresh();
                    CommonUtils.hold(2);
                    if (dfUtils.ph_4expandIcon.getAttribute("title").contains("Expand")) {
                        js.executeScript("arguments[0].click();", dfUtils.ph_4expandIcon);
                        System.out.println("INFO : Clicked Expand Icon");
                    }
                }
                iteration++;
            }

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment1), clovDriver);
//            CommonUtils.explicitWait(dfUtils._segment1, "Click", "", clovDriver);
//            CommonUtils.hold(3);

            System.out.println(dfUtils._segment1.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment1.getAttribute("value"), "India");

            System.out.println(dfUtils._segment2.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment2.getAttribute("value"), "Bangalore");

            System.out.println(dfUtils._segment3.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment3.getAttribute("value"), "Bangalore");

            System.out.println(dfUtils._segment4.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment4.getAttribute("value"), "US");

            System.out.println(dfUtils._segment5.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment5.getAttribute("value"), "New York");

            System.out.println(dfUtils._segment6.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment6.getAttribute("value"), "New York");

            CommonUtils.hold(5);

        } catch (Exception exp) {
            System.out.println("ERROR : Unable To Locate Segments Under Detail Stamp. " + exp.getMessage());
            Assert.fail("Could Not Locate Segments");
        }

    }

    @Test(priority = 3, dependsOnMethods = {"deployPerPhoneFlex"}, description = "Validate GS Values On Changing Context Segment")
    public void checkGSValueOnContextChange() {
        try {
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._contextCode), clovDriver);
            dfUtils._contextCode.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._phContextSegmentCode), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//li[contains(text(),'PHONE_EXTRAINFO')]")), "Click", "", clovDriver);
//            clovDriver.findElement(By.xpath("//li[contains(text(),'PHONE_EXTRAINFO')]")).click();
            dfUtils._phContextSegmentCode.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._phContextSegmentCode, clovDriver, 10);
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment7), clovDriver);
//            CommonUtils.explicitWait(dfUtils._segment7, "Click", "", clovDriver);
//            CommonUtils.hold(5);
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._contextCode), clovDriver);
            dfUtils._contextCode.click();
            CommonUtils.waitExplicitly(10, 6, ExpectedConditions.elementToBeClickable(dfUtils._phContextSegmentCode1), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//li[contains(text(),'PH_NAME1')]")), "Click", "", clovDriver);
//            clovDriver.findElement(By.xpath("//li[contains(text(),'PH_NAME1')]")).click();
            dfUtils._phContextSegmentCode1.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._phContextSegmentCode1, clovDriver, 8);
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment7, clovDriver, 8);
//            CommonUtils.hold(10);
            CommonUtils.waitExplicitly(8, 5, ExpectedConditions.elementToBeClickable(dfUtils._contextCode), clovDriver);
            dfUtils._contextCode.click();
            CommonUtils.waitExplicitly(8, 5, ExpectedConditions.elementToBeClickable(dfUtils._phContextSegmentCode), clovDriver);
            dfUtils._phContextSegmentCode.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._phContextSegmentCode, clovDriver, 13);
            CommonUtils.waitExplicitly(8, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment7), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//li[contains(text(),'PHONE_EXTRAINFO')]")), "Click", "", clovDriver);
//            clovDriver.findElement(By.xpath("//li[contains(text(),'PHONE_EXTRAINFO')]")).click();
//            CommonUtils.explicitWait(dfUtils._segment7, "Click", "", clovDriver);
//            CommonUtils.hold(5);


            System.out.println(dfUtils._segment1.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment1.getAttribute("value"), "India");

            System.out.println(dfUtils._segment2.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment2.getAttribute("value"), "Bangalore");

            System.out.println(dfUtils._segment3.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment3.getAttribute("value"), "Bangalore");

            System.out.println(dfUtils._segment4.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment4.getAttribute("value"), "US");

            System.out.println(dfUtils._segment5.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment5.getAttribute("value"), "New York");

            System.out.println(dfUtils._segment6.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment6.getAttribute("value"), "New York");

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test checkGSValueOnContextChange");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 4, dependsOnMethods = {"deployPerPhoneFlex", "checkGSDefaultValue"}, description = "Validate Ind And Dep Global Segment Values ")
    public void checkGlobalMandateValue() {
        try {
            CommonUtils.waitExplicitly(8, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment1Lov), clovDriver);
            dfUtils._segment1Lov.click();
            CommonUtils.waitExplicitly(8, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment1LovUSValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//ul[contains(@id,'DPK1')]/li[contains(text(),'US')]")), "Click", "", clovDriver);
//            CommonUtils.hold(4);
//            clovDriver.findElement(By.xpath("//ul[contains(@id,'DPK1')]/li[contains(text(),'US')]")).click();
            dfUtils._segment1LovUSValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment1LovUSValue, clovDriver, 10);


            try {
                CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils.errorOKBtn), clovDriver);
                dfUtils.errorOKBtn.click();
                System.out.println("INFO : Error Pop Displayed On Selecting Value From Seg1 Lov : US");
                Assert.fail("INFO : Error Pop Displayed On Selecting Value From Seg1 Lov : US");
            } catch (Exception popExp) {
                popExp.printStackTrace();
            }


            String val1 = dfUtils._segment2.getAttribute("value");
            String val2 = dfUtils._segment3.getAttribute("value");

            if (val1 == null || val1.isEmpty()) {
                System.out.println("INFO : Segment2 Empty Value On Changing Global Segment1: " + val1);
                Assert.assertTrue(true);
            } else {
                System.out.println("ERROR :Segment2 Not Empty value On Changing Global Segment1: " + val1);
                Assert.fail("ERROR :Segment2 Not Empty value On Changing Global Segment1: " + val1);
            }

            if (val2 == null || val2.isEmpty()) {
                System.out.println("INFO : Segment3 Empty Value On Changing Global Segment1: " + val2);
                Assert.assertTrue(true);
            } else {
                System.out.println("ERROR :Segment3 Not Empty value On Changing Global Segment1: " + val2);
                Assert.fail("ERROR :Segment3 Not Empty value On Changing Global Segment1: " + val2);
            }



            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils.saveBtn), clovDriver);
            dfUtils.saveBtn.click();
            CommonUtils.hold(2);
            dfUtils._segment2.click();
            CommonUtils.waitExplicitly(8, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment2ErrorMessage), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//div[contains(text(),'You must enter a value.')]")), "Click", "", clovDriver);
            Assert.assertTrue(dfUtils._segment2ErrorMessage.isDisplayed());

            CommonUtils.waitExplicitly(8, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment1Lov), clovDriver);
            dfUtils._segment1Lov.click();
            CommonUtils.waitExplicitly(8, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment1LovIndiaValue), clovDriver);
            dfUtils._segment1LovIndiaValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment1LovIndiaValue,clovDriver,8);

            try {
                CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils.errorOKBtn), clovDriver);
                dfUtils.errorOKBtn.click();
                CommonUtils.waitForInvisibilityOfElement(dfUtils.errorOKBtn,clovDriver,4);
                System.out.println("INFO : Error Pop Displayed On Selecting Value From Seg1 Lov : India and Is An Issue Currently. Fix It In Future");
                Assert.assertTrue(true);
            } catch (Exception popExp) {
                Assert.fail("ERROR : Error Pop Up Not Displayed " + popExp.getMessage());
                popExp.printStackTrace();
            }



        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test checkGlobalMandateValue()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 5, dependsOnMethods = {"deployPerPhoneFlex"}, description = "Validate Ind And Dep Global Segment Values ")
    public void checkGSIndependentDependentValue() {
        try {
            CommonUtils.waitExplicitly(8,4,ExpectedConditions.elementToBeClickable(dfUtils._segment1Lov),clovDriver);
            dfUtils._segment1Lov.click();
            CommonUtils.waitExplicitly(8,5,ExpectedConditions.elementToBeClickable(dfUtils._segment1LovUSValue),clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//ul[contains(@id,'DPK1')]/li[contains(text(),'US')]")), "Click", "", clovDriver);
//            CommonUtils.hold(2);
            dfUtils._segment1LovUSValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment1LovUSValue, clovDriver, 10);

            try {
                CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils.errorOKBtn), clovDriver);
                dfUtils.errorOKBtn.click();
                CommonUtils.waitForInvisibilityOfElement(dfUtils.errorOKBtn,clovDriver,4);
//                System.out.println("INFO : Error Pop Displayed On Selecting Value From Seg1 Lov : India");
            } catch (Exception popExp) {

            }

//            clovDriver.findElement(By.xpath("//ul[contains(@id,'DPK1')]/li[contains(text(),'US')]")).click();
//            CommonUtils.hold(9);
//            String val = dfUtils._segment2.getAttribute("value");
//            String val2 = dfUtils._segment3.getAttribute("value");
//
//            if (val == null || val.isEmpty()) {
//                System.out.println("INFO : Empty value on changing global segment2: " + val);
//                Assert.assertTrue(true);
//            } else {
//                System.out.println("ERROR : Not Empty value on changing global segment2: " + val);
//                Assert.fail("ERROR : Not Empty value on changing global segment2: " + val);
//            }
//
//            if (val2 == null || val2.isEmpty()) {
//                System.out.println("INFO : Empty Value On Changing Global Segment3: " + val2);
//                Assert.assertTrue(true);
//            } else {
//                System.out.println("ERROR : Not Empty value on changing global segment3: " + val2);
//                Assert.fail("ERROR : Not Empty value on changing global segment: " + val2);
//            }

//            try{
////                CommonUtils.waitforElementtoClick(10,dfUtils._segment5ErrorMessage,clovDriver);
//                dfUtils._segment2.click();
//                CommonUtils.waitExplicitly(8,5,ExpectedConditions.elementToBeClickable(dfUtils._segment5ErrorMessage),clovDriver);
//
//            }catch(Exception exp){
//                    System.out.println("Waste Life");
//                    exp.printStackTrace();
//            }


            dfUtils._segment2Lov.click();
            CommonUtils.waitExplicitly(8, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment2LovNYValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[contains(text(),'New York')]")), "Click", "", clovDriver);
//            CommonUtils.hold(2);
            Assert.assertTrue(dfUtils._segment2LovNYValue.isDisplayed());
            dfUtils._segment2LovNYValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment2LovNYValue, clovDriver, 10);
//            CommonUtils.hold(7);

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment3), clovDriver);
            dfUtils._segment3.click();
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment3NYValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'New York')]")), "Click", "", clovDriver);
            Assert.assertTrue(dfUtils._segment3NYValue.isDisplayed());
            dfUtils._segment3NYValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment3NYValue, clovDriver, 10);
//            CommonUtils.hold(2);
//            clovDriver.findElement(By.xpath("//td[contains(text(),'New York')]")).click();
//            CommonUtils.hold(7);


            dfUtils.saveBtn.click();
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils.ph_9ConfirmationOKBtn), clovDriver);
//            CommonUtils.explicitWait(dfUtils.ph_9ConfirmationOKBtn, "Click", "", clovDriver);
//            CommonUtils.hold(2);
            dfUtils.ph_9ConfirmationOKBtn.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils.ph_9ConfirmationOKBtn, clovDriver, 8);
//            CommonUtils.explicitWait(dfUtils._segment1, "Click", "", clovDriver);
//            CommonUtils.hold(2);

            System.out.println(dfUtils._segment1.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment1.getAttribute("value"), "US");

            System.out.println(dfUtils._segment3.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment3.getAttribute("value"), "New York");

            System.out.println(dfUtils._segment2.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment2.getAttribute("value"), "New York");


        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test checkGSIndependentDependentValue()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 6, description = "Validate Ind And Dep Global Segment Values ")
    public void checkGSIndependentDependentClientValue() {
        try {
            dfUtils._segment4.clear();
            dfUtils._segment4.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment4IndValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'India')]")), "Click", "", clovDriver);
//            CommonUtils.hold(2);
//            clovDriver.findElement(By.xpath("//td[contains(text(),'India')]")).click();
//            CommonUtils.hold(8);
            dfUtils._segment4IndValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment4IndValue, clovDriver, 8);
            String val = dfUtils._segment5.getAttribute("value");
            String val2 = dfUtils._segment6.getAttribute("value");

            if (val == null || val.isEmpty()) {
                System.out.println("INFO : Segment5 Empty Value On Changing Global Segment4: " + val);
                Assert.assertTrue(true);
            } else {
                System.out.println("ERROR : Segment5 Not Empty Value On Changing Global Segment4: " + val);
                Assert.fail("ERROR : Segment5 Not Empty Value On Changing Global Segment4:" + val);
            }

            if (val2 == null || val2.isEmpty()) {
                System.out.println("INFO : Segment6 Empty Value On Changing Global Segment4: " + val2);
                Assert.assertTrue(true);
            } else {
                System.out.println("ERROR :Segment6 Not Empty Value On Changing Global Segment4 And Currently Is An Issue : " + val2);
                Assert.fail("ERROR :Segment6 Not Empty Value On Changing Global Segment6 And Currently Is An Issue :" + val2);
            }

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test checkGSIndependentDependentClientValue()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 7, description = "Error PopUp Shown On Changing Client LOV Segments")
    public void checkIndependentDependentClientValue1() {
        try {
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment5), clovDriver);
            dfUtils._segment5.click();
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.visibilityOf(dfUtils._segment5ErrorMessage), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//div[contains(text(),'is a required field')]")), "Click", "", clovDriver);
            System.out.println("ERROR : Error PopUp Shown On Changing Client LOV Segments And Currently Is An Issue. Fix It In Future");
//            CommonUtils.hold(4);
            Assert.assertTrue(dfUtils._segment5ErrorMessage.isDisplayed());
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//div[contains(text(),'is a required field')]")).isDisplayed());

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test checkIndependentDependentClientValue1()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 8, dependsOnMethods = {"deployPerPhoneFlex"}, description = "Validate Ind And Dep Global Segment ClientLOV Values ")
    public void checkIndependentDependentClientValue2() {
        try {
            dfUtils._segment5.click();
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment5BlrValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'Bangalore')]")), "Click", "", clovDriver);
//            CommonUtils.hold(4);
            dfUtils._segment5BlrValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment5BlrValue, clovDriver, 10);
//            clovDriver.findElement(By.xpath("//td[contains(text(),'Bangalore')]")).click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment6), clovDriver);
//            CommonUtils.explicitWait(dfUtils._segment6, "Click", "", clovDriver);
//            CommonUtils.hold(2);

            dfUtils._segment6.clear();
            dfUtils._segment6Lov.click();
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment12SearchUI), clovDriver);
//            CommonUtils.explicitWait(dfUtils._segment12SearchUI, "Click", "", clovDriver);
//            CommonUtils.hold(4);
            dfUtils._segment12SearchUI.click();
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment12SearchUIBlrValue), clovDriver);

//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[contains(text(),'Bangalore')]")), "Click", "", clovDriver);
//            CommonUtils.hold(3);
            Assert.assertTrue(dfUtils._segment12SearchUIBlrValue.isDisplayed());
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//span[contains(text(),'Bangalore')]")).isDisplayed());
            System.out.println("INFO : Segment6 value displayed properly : Bangalore");

            CommonUtils.waitExplicitly(4, 2, ExpectedConditions.elementToBeClickable(dfUtils._segment12SearchUIDelValue), clovDriver);

//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[contains(text(),'Delhi')]")), "Click", "", clovDriver);
            System.out.println("INFO : Segment9 value displayed properly : Delhi");
            Assert.assertTrue(dfUtils._segment12SearchUIDelValue.isDisplayed());
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//span[contains(text(),'Delhi')]")).isDisplayed());
//            clovDriver.findElement(By.xpath("//span[contains(text(),'Bangalore')]")).click();
            dfUtils._segment12SearchUIBlrValue.click();
            CommonUtils.hold(3);
//            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment12SearchUIBlrValue,clovDriver,10);

//            CommonUtils.hold(3);
            dfUtils._segment12OK.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment12OK, clovDriver, 10);

            CommonUtils.waitExplicitly(4, 2, ExpectedConditions.elementToBeClickable(dfUtils.saveBtn), clovDriver);
//            CommonUtils.explicitWait(dfUtils.saveBtn, "Click", "", clovDriver);
//            CommonUtils.hold(3);
            dfUtils.saveBtn.click();

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils.ph_9ConfirmationOKBtn), clovDriver);
//            CommonUtils.explicitWait(dfUtils.ph_9ConfirmationOKBtn, "Click", "", clovDriver);
            CommonUtils.hold(2);
            dfUtils.ph_9ConfirmationOKBtn.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils.ph_9ConfirmationOKBtn, clovDriver, 8);
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment1), clovDriver);
//            CommonUtils.explicitWait(dfUtils._segment1, "Click", "", clovDriver);
//            CommonUtils.hold(2);

            System.out.println(dfUtils._segment4.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment4.getAttribute("value"), "India");

            System.out.println(dfUtils._segment5.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment5.getAttribute("value"), "Bangalore");

            System.out.println(dfUtils._segment6.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment6.getAttribute("value"), "Bangalore");

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test checkIndependentDependentClientValue2()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 9, dependsOnMethods = {"deployPerPhoneFlex"}, description = "Validate Sensitive Segment default values")
    public void checkSensitiveSegmentDefaultValues() {
        try {
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._contextCode), clovDriver);
            dfUtils._contextCode.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._phContextSegmentCode), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//li[contains(text(),'PHONE_EXTRAINFO')]")), "Click", "", clovDriver);
//            clovDriver.findElement(By.xpath("//li[contains(text(),'PHONE_EXTRAINFO')]")).click();
            dfUtils._phContextSegmentCode.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._phContextSegmentCode, clovDriver, 10);
//            CommonUtils.explicitWait(dfUtils._segment7, "Click", "", clovDriver);
//            CommonUtils.hold(8);

            System.out.println("INFO : Segment7 Default Value : " + dfUtils._segment7.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment7.getAttribute("value"), "India");

            System.out.println("INFO : Segment8 Default Value : " + dfUtils._segment8.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment8.getAttribute("value"), "Bangalore");

            System.out.println("INFO : Segment9 Default Value : " + dfUtils._segment9.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment9.getAttribute("value"), "Delhi");

            System.out.println("INFO : Segment10 Default Value : " + dfUtils._segment10.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment10.getAttribute("value"), "Delhi");

            System.out.println("INFO : Segment11 Default Value : " + dfUtils._segment11.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment11.getAttribute("value"), "US");

            System.out.println("INFO : Segment12 Default Value : " + dfUtils._segment12.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment12.getAttribute("value"), "New York");

            System.out.println("INFO : Segment13 Default Value : " + dfUtils._segment13.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment13.getAttribute("value"), "New York");

            System.out.println("INFO : Segment14 Default Value : " + dfUtils._segment14.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment14.getAttribute("value"), "New York");

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test checkIndependentDependentClientValue2()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 10, dependsOnMethods = {"deployPerPhoneFlex"}, description = "Check SS dependent values clears out")
    public void checkSensitiveSegmentValues() {
        try {
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment7), clovDriver);
            dfUtils._segment7.click();
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment7USValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//ul[contains(@id,'DPK7')]/li[contains(text(),'US')]")), "Click", "", clovDriver);
//            CommonUtils.hold(3);
            dfUtils._segment7USValue.click();
//            clovDriver.findElement(By.xpath("//ul[contains(@id,'DPK7')]/li[contains(text(),'US')]")).click();
            CommonUtils.hold(8);
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment7USValue, clovDriver, 10);

            String val1 = dfUtils._segment8.getAttribute("value");
            String val2 = dfUtils._segment9.getAttribute("value");
            String val3 = dfUtils._segment10.getAttribute("value");

            System.out.println("VAL1 " + val1 + " VAL2 " + val2 + " VAL3 " + val3);

            if (val1 == null || val1.isEmpty()) {
                Assert.assertTrue(true);
                System.out.println("INFO : Empty Value Found For Segment8");
            } else {
                System.out.println("ERROR : Segment8 Field Is Not Empty");
                Assert.fail();
            }

            if (val2 == null || val2.isEmpty()) {
                Assert.assertTrue(true);
                System.out.println("INFO :Segment9 Empty Value On Changing Segment8");
            } else {
                System.out.println("ERROR : Segment9 Field Is Not Empty On Changing Segment8 And Currently Is An Issue : " + val2);
                Assert.fail();
            }

            if (val3 == null || val3.isEmpty()) {
                Assert.assertTrue(true);
                System.out.println("INFO :Segment10 Empty On Changing Segment8");
            } else {
                System.out.println("ERROR : Segment10 Field Is Not Empty On Changing Segment8" + val3);
                Assert.fail("Segment10 Field Is Not Empty On Changing Segment8");
            }


        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test checkSensitiveSegmentValues()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 11, description = "Validate Mandate Field")
    public void CheckManDateFieldValidation() {
        try {
            CommonUtils.waitExplicitly(8, 3, ExpectedConditions.elementToBeClickable(dfUtils.saveBtn), clovDriver);
            dfUtils.saveBtn.click();
            CommonUtils.hold(2);
            dfUtils._segment8.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment8ErrorMessage), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//div[contains(text(),'You must enter a value.')]")), "Click", "", clovDriver);
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//div[contains(text(),'You must enter a value.')]")).isDisplayed());
//            CommonUtils.hold(2);
            Assert.assertTrue(dfUtils._segment8ErrorMessage.isDisplayed());
//			CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'value is required')]")), "Click", "", clovDriver);
//			CommonUtils.hold(3);
//			Assert.assertTrue(clovDriver.findElement(By.xpath("//td[contains(text(),'value is required')]")).isDisplayed());

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test CheckManDateFieldValidation()");
            Assert.fail(exp.getMessage());
            exp.printStackTrace();
        }

    }

    @Test(priority = 12, description = "Validate SS dependent values")
    public void validateSSDependentValues() {
        try {
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment8Lov), clovDriver);
            dfUtils._segment8Lov.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment8LovNYValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[contains(text(),'New York')]")), "Click", "", clovDriver);
//            CommonUtils.hold(2);
            Assert.assertTrue(dfUtils._segment8LovNYValue.isDisplayed());
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//span[contains(text(),'New York')]")).isDisplayed());
            System.out.println("INFO 629: Segment8 value displayed properly : New York");

            CommonUtils.waitExplicitly(6, 3, ExpectedConditions.elementToBeClickable(dfUtils._segment8LovSFValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[contains(text(),'San Francisco')]")), "Click", "", clovDriver);
            System.out.println("INFO 634: Segment8 value displayed properly : San Francisco");
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//span[contains(text(),'San Francisco')]")).isDisplayed());
            Assert.assertTrue(dfUtils._segment8LovSFValue.isDisplayed());

            dfUtils._segment8LovNYValue.click();

//            clovDriver.findElement(By.xpath("//span[contains(text(),'New York')]")).click();
//            CommonUtils.explicitWait(dfUtils._segment9, "Click", "", clovDriver);
//            CommonUtils.hold(3);
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment8LovNYValue, clovDriver, 10);
            CommonUtils.waitExplicitly(6, 3, ExpectedConditions.elementToBeClickable(dfUtils._segment9Lov), clovDriver);

            dfUtils._segment9.clear();
            dfUtils._segment9Lov.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment12SearchUI), clovDriver);
//            CommonUtils.explicitWait(dfUtils._segment12SearchUI, "Click", "", clovDriver);
//            CommonUtils.hold(3);
            dfUtils._segment12SearchUI.click();
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment12SearchUINYValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[contains(text(),'New York')]")), "Click", "", clovDriver);
//            CommonUtils.hold(3);
            Assert.assertTrue(dfUtils._segment12SearchUINYValue.isDisplayed());
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//span[contains(text(),'New York')]")).isDisplayed());
            System.out.println("INFO 656: Segment9 value displayed properly : New York");

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment12SearchUISFValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//span[contains(text(),'San Francisco')]")), "Click", "", clovDriver);
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//span[contains(text(),'San Francisco')]")).isDisplayed());
            Assert.assertTrue(dfUtils._segment12SearchUISFValue.isDisplayed());
            System.out.println("INFO 662: Segment9 value displayed properly : San Francisco");

            dfUtils._segment12SearchUINYValue.click();
            CommonUtils.hold(5);
//            clovDriver.findElement(By.xpath("//span[contains(text(),'New York')]")).click();
//            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment12SearchUINYValue,clovDriver,10);
//            CommonUtils.hold(3);
            dfUtils._segment12OK.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment12OK, clovDriver, 10);

            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment10), clovDriver);
//            CommonUtils.explicitWait(dfUtils._segment10, "Click", "", clovDriver);
//            CommonUtils.hold(3);
            dfUtils._segment10.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment10LovNyValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'New York')]")), "Click", "", clovDriver);
//            CommonUtils.hold(2);
            Assert.assertTrue(dfUtils._segment10LovNyValue.isDisplayed());
            System.out.println("INFO 680: Segment10 value displayed properly : New York");

            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment10LovSfValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'San Francisco')]")), "Click", "", clovDriver);
            Assert.assertTrue(dfUtils._segment10LovSfValue.isDisplayed());
            System.out.println("INFO : Segment10 value displayed properly : San Francisco");
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//td[contains(text(),'San Francisco')]")).isDisplayed());

            dfUtils._segment10LovNyValue.click();
//            clovDriver.findElement(By.xpath("//td[contains(text(),'New York')]")).click();
//            CommonUtils.hold(4);
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment10LovNyValue, clovDriver, 8);

            dfUtils.saveBtn.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils.ph_9ConfirmationOKBtn), clovDriver);
//            CommonUtils.explicitWait(dfUtils.ph_9ConfirmationOKBtn, "Click", "", clovDriver);
//            CommonUtils.hold(2);
            dfUtils.ph_9ConfirmationOKBtn.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils.ph_9ConfirmationOKBtn, clovDriver, 5);
            CommonUtils.waitExplicitly(3, 6, ExpectedConditions.elementToBeClickable(dfUtils._segment1), clovDriver);
//            CommonUtils.explicitWait(dfUtils._segment1, "Click", "", clovDriver);
//            CommonUtils.hold(2);

            System.out.println(dfUtils._segment7.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment7.getAttribute("value"), "US");

            System.out.println(dfUtils._segment8.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment8.getAttribute("value"), "New York");

            System.out.println(dfUtils._segment9.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment9.getAttribute("value"), "New York");

            System.out.println(dfUtils._segment10.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment10.getAttribute("value"), "New York");

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test validateSSDependentValues() ");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 13, description = "Validate SS Inline dependent values")
    public void validateSSValuesInline() {
        try {
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment11), clovDriver);
            dfUtils._segment11.clear();
            dfUtils._segment11.click();
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment11LovIndValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'India')]")), "Click", "", clovDriver);
//            CommonUtils.hold(2);
//            clovDriver.findElement(By.xpath("//td[contains(text(),'India')]")).click();
            dfUtils._segment11LovIndValue.click();
            CommonUtils.hold(3);

            try {
                CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment11ErrorMessage), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//div[contains(text(),'is a required field.')]")), "Click", "", clovDriver);
//            CommonUtils.hold(3);

//            Assert.assertTrue(clovDriver.findElement(By.xpath("//div[contains(text(),'is a required field.')]")).isDisplayed(), "Error PopUp For Segment12 Displayed And Currently Is An Issue");
                Assert.assertTrue(dfUtils._segment11ErrorMessage.isDisplayed());

                CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils.errorOKBtn), clovDriver);

                if (dfUtils.errorOKBtn.isDisplayed()) {
                    System.out.println("========================> Error OK Button Clicked");
                    dfUtils.errorOKBtn.click();
                    CommonUtils.waitForInvisibilityOfElement(dfUtils.errorOKBtn, clovDriver, 8);
//                CommonUtils.hold(3);
                } else
                    System.out.println("========================> Error OK Button not clicked");
            } catch (Exception popExp) {
                System.out.println("ERROR : No Error Pop Up Displayed On Changing Value From Segment 11");
                popExp.printStackTrace();
//                Assert.fail();
            }


            String val1 = dfUtils._segment12.getAttribute("value");
            String val2 = dfUtils._segment13.getAttribute("value");
            String val3 = dfUtils._segment14.getAttribute("value");

            if (val1 == null || val1.isEmpty()) {
                Assert.assertTrue(true);
                System.out.println("INFO : Segment12 Empty Value On Changing Segment11");
            } else {
                System.out.println("ERROR : Segment12 Field Is Not Empty On Changing Segment11 And Currently Is An Issue : " + val1);
                Assert.fail();
            }

            if (val2 == null || val2.isEmpty()) {
                Assert.assertTrue(true);
                System.out.println("INFO : Segment13 Empty Value On Changing Segment11");
            } else {
                System.out.println("ERROR : Segment13 Field Is Not Empty On Changing Segment11. " + val2);
                Assert.fail();
            }

            if (val3 == null || val3.isEmpty()) {
                Assert.assertTrue(true);
                System.out.println("INFO : Segment14 Empty Value On Changing Segment11");
            } else {
                System.out.println("ERROR : Segment14 Field Is Not Empty On Changing Segment11 : " + val3);
                Assert.fail();
            }

        } catch (Exception exp) {
            System.out.println("ERROR : Exception While Running Test validateSSValuesInline()");
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 14, description = "Validate SS Inline dependent values")
    public void validateSSDependentValuesInline() {
        try {
            dfUtils._segment12.clear();
            dfUtils._segment12.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment12BlrValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'Bangalore')]")), "Click", "", clovDriver);
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//td[contains(text(),'Bangalore')]")).isDisplayed());
            Assert.assertTrue(dfUtils._segment12BlrValue.isDisplayed());
            System.out.println("INFO 791: Segment12 value displayed properly : Bangalore");

//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'Delhi')]")), "Click", "", clovDriver);
            Assert.assertTrue(dfUtils._segment12DelValue.isDisplayed());
            System.out.println("INFO 795: Segment12 value displayed properly : Delhi");
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//td[contains(text(),'Delhi')]")).isDisplayed());

//            clovDriver.findElement(By.xpath("//td[contains(text(),'Bangalore')]")).click();
            dfUtils._segment12BlrValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment12BlrValue, clovDriver, 6);
//            CommonUtils.hold(3);

            dfUtils._segment13Lov.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment13LovPhBlrValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//ul[contains(@id,'DPK13')]/li[contains(text(),'Bangalore')]")), "Click", "", clovDriver);
//            CommonUtils.hold(2);
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//ul[contains(@id,'DPK13')]/li[contains(text(),'Bangalore')]")).isDisplayed());
            Assert.assertTrue(dfUtils._segment13LovPhBlrValue.isDisplayed());
            System.out.println("INFO 809: Segment13 value displayed properly : Bangalore");


//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//ul[contains(@id,'DPK13')]/li[contains(text(),'Delhi')]")), "Click", "", clovDriver);
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment13LovPhDelValue), clovDriver);
            Assert.assertTrue(dfUtils._segment13LovPhDelValue.isDisplayed());
            System.out.println("INFO 815: Segment13 value displayed properly : Delhi");
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//ul[contains(@id,'DPK13')]/li[contains(text(),'Delhi')]")).isDisplayed());

//            clovDriver.findElement(By.xpath("//ul[contains(@id,'DPK13')]/li[contains(text(),'Bangalore')]")).click();
//            CommonUtils.hold(3);

            dfUtils._segment13LovPhBlrValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment13LovPhBlrValue, clovDriver, 8);

            dfUtils._segment14.click();
            CommonUtils.waitExplicitly(10, 6, ExpectedConditions.elementToBeClickable(dfUtils._segment14LovBlrValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'Bangalore')]")), "Click", "", clovDriver);
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//td[contains(text(),'Bangalore')]")).isDisplayed());
            Assert.assertTrue(dfUtils._segment14LovBlrValue.isDisplayed());
            System.out.println("INFO 829: Segment14 value displayed properly : Bangalore");

//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'Delhi')]")), "Click", "", clovDriver);
            Assert.assertTrue(dfUtils._segment14LovDelValue.isDisplayed());
            System.out.println("INFO 833: Segment14 value displayed properly : Delhi");
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//td[contains(text(),'Delhi')]")).isDisplayed());

//            clovDriver.findElement(By.xpath("//td[contains(text(),'Bangalore')]")).click();
//            CommonUtils.hold(3);

            dfUtils._segment14LovBlrValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment14LovBlrValue, clovDriver, 5);

            CommonUtils.waitExplicitly(4, 2, ExpectedConditions.elementToBeClickable(dfUtils.saveBtn), clovDriver);
//            CommonUtils.explicitWait(dfUtils.saveBtn, "Click", "", clovDriver);
            dfUtils.saveBtn.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils.ph_9ConfirmationOKBtn), clovDriver);
//            CommonUtils.explicitWait(dfUtils.ph_9ConfirmationOKBtn, "Click", "", clovDriver);
//            CommonUtils.hold(2);
            dfUtils.ph_9ConfirmationOKBtn.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils.ph_9ConfirmationOKBtn, clovDriver, 5);
            CommonUtils.explicitWait(dfUtils._segment1, "Click", "", clovDriver);
            CommonUtils.hold(2);

            System.out.println(dfUtils._segment11.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment11.getAttribute("value"), "India");

            System.out.println(dfUtils._segment12.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment12.getAttribute("value"), "Bangalore");

            System.out.println(dfUtils._segment13.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment13.getAttribute("value"), "Bangalore");

            System.out.println(dfUtils._segment14.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment14.getAttribute("value"), "Bangalore");

        } catch (Exception exp) {
            exp.printStackTrace();
            Assert.fail();
        }
    }

    @Test(priority = 15, description = "Validate SS Inline Table Values")
    public void validateSSTableInline() {
        try {
            CommonUtils.waitExplicitly(10, 5, ExpectedConditions.elementToBeClickable(dfUtils._segment15), clovDriver);
            dfUtils._segment15.clear();
            dfUtils._segment15.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment13LovValue), clovDriver);
//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'Reporting name')]")), "Click", "", clovDriver);
//            CommonUtils.hold(3);

//            Assert.assertTrue(clovDriver.findElement(By.xpath("//td[contains(text(),'Reporting name')]")).isDisplayed());
            Assert.assertTrue(dfUtils._segment13LovValue.isDisplayed());
            System.out.println("INFO : Segment15 value displayed properly : Reporting name");

//            CommonUtils.explicitWait(clovDriver.findElement(By.xpath("//td[contains(text(),'State code')]")), "Click", "", clovDriver);
            Assert.assertTrue(dfUtils._segment13LovValue1.isDisplayed());
            System.out.println("INFO : Segment15 value displayed properly : State code");
//            Assert.assertTrue(clovDriver.findElement(By.xpath("//td[contains(text(),'State code')]")).isDisplayed());

//            clovDriver.findElement(By.xpath("//td[contains(text(),'Reporting name')]")).click();
//            CommonUtils.hold(3);

            dfUtils._segment13LovValue.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils._segment13LovValue, clovDriver, 5);

            CommonUtils.waitExplicitly(4, 2, ExpectedConditions.elementToBeClickable(dfUtils.saveBtn), clovDriver);
//            CommonUtils.explicitWait(dfUtils.saveBtn, "Click", "", clovDriver);
            dfUtils.saveBtn.click();
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils.ph_9ConfirmationOKBtn), clovDriver);
//            CommonUtils.explicitWait(dfUtils.ph_9ConfirmationOKBtn, "Click", "", clovDriver);
//            CommonUtils.hold(2);
            dfUtils.ph_9ConfirmationOKBtn.click();
            CommonUtils.waitForInvisibilityOfElement(dfUtils.ph_9ConfirmationOKBtn, clovDriver, 5);
            CommonUtils.waitExplicitly(8, 4, ExpectedConditions.elementToBeClickable(dfUtils._segment1), clovDriver);
//            CommonUtils.explicitWait(dfUtils._segment1, "Click", "", clovDriver);
            CommonUtils.hold(10);

            System.out.println(dfUtils._segment15.getAttribute("value"));
            Assert.assertEquals(dfUtils._segment15.getAttribute("value"), "Reporting name");


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
