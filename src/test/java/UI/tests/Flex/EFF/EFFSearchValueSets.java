package UI.tests.Flex.EFF;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import TestBase.UI.GetDriverInstance;

import static org.testng.Assert.assertTrue;

import java.sql.Timestamp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;
import pageDefinitions.UI.oracle.applcore.qa.Dff.ManaageValueSetsUtil;
import pageDefinitions.UI.oracle.applcore.qa.Eff.Deployment;
import pageDefinitions.UI.oracle.applcore.qa.Eff.EFFUtils;
import pageDefinitions.UI.oracle.applcore.qa.Eff.RuntimeValidation;
import TestBase.UI.GetDriverInstance;

public class EFFSearchValueSets extends GetDriverInstance {

    public WebDriver effSearchVSTestdriver;
    private EFFUtils effUtils;
    private Deployment dep;
    private RuntimeValidation pimRunTime;
    private ApplicationLogin aLoginInstance;
    private NavigationTaskFlows ntFInstance;
    private NavigatorMenuElements nMEInstance;
    private ManaageValueSetsUtil vsUtil;

    @BeforeClass
    public void startUP() throws Exception {
        effSearchVSTestdriver = getDriverInstanceObject();
        dep = new Deployment(effSearchVSTestdriver);
        pimRunTime = new RuntimeValidation(effSearchVSTestdriver);
        aLoginInstance = new ApplicationLogin(effSearchVSTestdriver);
        ntFInstance = new NavigationTaskFlows(effSearchVSTestdriver);
        nMEInstance = new NavigatorMenuElements(effSearchVSTestdriver);
        effUtils = new EFFUtils(effSearchVSTestdriver);
        vsUtil = new ManaageValueSetsUtil(effSearchVSTestdriver);
        aLoginInstance.login("APP_IMPL_CONSULTANT", "Welcome1", effSearchVSTestdriver);
        CommonUtils.hold(5);
    }

    @Test(priority = 1, description = "Navigate to Manage ValueSets taskflow")
    public void testcase001() throws Exception {
        ntFInstance.navigateToTask(aLoginInstance.getGloablePageTemplateInstance().setupandmaintenance, effSearchVSTestdriver);
        CommonUtils.hold(10);
        ntFInstance.navigateToAOLTaskFlows("Manage Value Sets", effSearchVSTestdriver);
    }

    /*
        Search for value set using code.
        This is a seeded value set.
     */
    @Test(priority = 2, description = "Create a Valueset with code = Priority")
    public void testcase002() throws Exception {

        if (!dep.searchVSCode("Priority")) {
            System.out.println("Value Set - Priority does not exist. Creating a new one.....");
        } else {
            System.out.println("Value Set - Priority exists");
        }
    }


    /*
        ValueSet with Validation type = Independent , Value Data type = Date
        Search for value set using code.Create value set with given code if the value set not already exist.
        Original - FDATE_1903110446
     */
    @Test(priority = 3, description = "Create a Valueset with code = FDATE_1903110446")
    public void testcase003() throws Exception {
        // Original - FDATE_1903110446
        if (!dep.searchVSCode("FDATE_1903110446")) {
            System.out.println("Value Set - FDATE_1903110446 does not exist. Need to create a new one.......");
            vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "FDATE_1903110446", "ValueSetDescription", "FDATE_1903110446",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "FormatOnly", "ValueType", "Date");
        } else {
            System.out.println("Value Set - FDATE_1903110446 exists");
        }
    }

    /*
       ValueSet with Validation type = Format Only , Value Data type = Character type
       Search for value set using code.Create value set with given code if the value set not already exist.
    */
    @Test(priority = 4, description = "Create a Valueset with code = 1char_frm_vs")
    public void testcase004() throws Exception {
        if (!dep.searchVSCode("1char_frm_vs")) {
            System.out.println("Value Set - '1char_frm_vs' does not exist. Need to create a new one.......");

            vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "1char_frm_vs", "ValueSetDescription",
                    "1char_frm_vs", "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "FormatOnly",
                    "ValueType", "Character", "ValueSubType", "Text", "MaxLength", "10", "UpperCase", "No");
        } else {
            System.out.println("Value Set - '1char_frm_vs' exists");
        }
    }

    /*
        ValueSet with Validation type = Table , Value Data type = Character type
        Search for value set using code.Create valueset with given code if the valueset not already exist.
   */
    @Test(priority = 5, description = "Create a Valueset with code = tabvs")
    public void testcase005() throws Exception {
        if (!dep.searchVSCode("tabvs")) {
            System.out.println("Value Set - 'tabvs' does not exist. Need to create a new one.....");

            vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "tabvs", "ValueSetDescription", "tabvs",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Table", "ValueType", "Character", "FromClause",
                    "AR_LOOKUPS", "ValueColumnName", "LOOKUP_CODE", "WHEREClause",
                    "LOOKUP_TYPE='ACCOUNT_STATUS'", "DescriptionColumnName",
                    "DESCRIPTION");
        } else {
            System.out.println("Value Set - 'tabvs' exists");
        }
    }

    /*
        ValueSet with Validation type = Format Only , Value Data type = Number type
        Search for value set using code.Create valueset with given code if the valueset not already exist.
    */
    @Test(priority = 6, description = "Create a Valueset with code = num_frm_vs")
    public void testcase006() throws Exception {
        if (!dep.searchVSCode("num_frm_vs")) {
            System.out.println("Value Set - 'num_frm_vs' does not exist. Need to create a new one.....");

            vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "num_frm_vs", "ValueSetDescription",
                    "num_frm_vs", "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "FormatOnly",
                    "ValueType", "Number", "Precision", "6", "Scale", "2");
        } else {
            System.out.println("Value Set - 'num_frm_vs' exists");
        }
    }

    /*
         ValueSet with Validation type = Independent , Value Data type = Character
         Search for value set using code.Create valueset with given code if the valueset not already exist.
    */
    /*@Test(priority = 7, description = "Create a Valueset with code = MyIndVS")
    public void testcase007() throws Exception {
        if (!dep.searchVSCode("MyIndVS")) {
            System.out.println("Value Set - 'MyIndVS' does not exist. Need to create a new one......");

            vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "MyIndVS", "ValueSetDescription", "MyIndVS",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Independent", "ValueType", "Character",
                    "ValueSubType", "Text", "MaxLength", "20", "ManageValues", "5", "Value1", "ACCOUNT_CLASSES", "Value2", "ACCOUNT_SET_TYPES", "Value3", "ACCOUNT_STATUS", "Value4", "APR", "Value5", "ABBR_MONTH", "Desc1", "ACCOUNT_CLASSES", "Desc2", "ACCOUNT_SET_TYPES", "Desc3", "ACCOUNT_STATUS", "Desc4", "APR", "Desc5", "ABBR_MONTH");
        } else {
            System.out.println("Value Set - 'MyIndVS' exists");
        }
    }*/

    /*
        ValueSet with Validation type = Independent , Value Data type = Character
        Search for value set using code.Create valueset with given code if the valueset not already exist.
    */
    @Test(priority = 8, description = "Create a Valueset with code = ADS Job Function")
    public void testcase008() throws Exception {
        /*if (!dep.searchVSCode("charvs")) {
            System.out.println("Value Set - 'charvs' does not exist. Need to create a new one......");

            vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "charvs", "ValueSetDescription", "charvs",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Independent", "ValueType", "Character",
                    "ValueSubType", "Text", "MaxLength", "20", "ManageValues", "2", "Value1", "a1", "Value2", "a2", "Desc1", "a1", "Desc2", "a2");
        } else {
            System.out.println("Value Set - 'charvs' exists");
        }*/
        if (!dep.searchVSCode("ADS Job Function")) {
            System.out.println("Value Set - 'ADS Job Function' does not exist. Need to create a new one......");

            /*vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "charvs", "ValueSetDescription", "charvs",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Independent", "ValueType", "Character",
                    "ValueSubType", "Text", "MaxLength", "20", "ManageValues", "2", "Value1", "a1", "Value2", "a2", "Desc1", "a1", "Desc2", "a2");*/
        } else {
            System.out.println("Value Set - 'ADS Job Function' exists");
        }
    }

    /*
        ValueSet with Validation type = Dependent , Value Data type = Character
        Search for value set using code.Create valueset with given code if the valueset not already exist.
    */
    @Test(priority = 9, description = "Create a Valueset with code = ADS Job Family", dependsOnMethods = {"testcase008"})
    public void testcase009() throws Exception {
        /*if (!dep.searchVSCode("chardep")) {
            System.out.println("Value Set - 'chardep' does not exist. Need to create a new one......");
            vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "chardep", "ValueSetDescription", "chardep",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Dependent", "ValueType", "Character", "ValueSubType", "Text", "MaxLength", "10",
                    "IndValueSetCode", "charvs", "10", "charvs", "ManageValues", "4", "Value1", "a11", "IndValue1", "a1", "Value2", "a12", "IndValue2",
                    "a1", "Value3", "a21", "IndValue3", "a2", "Value4", "a22", "IndValue4", "a2", "Desc1", "a11", "Desc2",
                    "a12", "Desc3", "a21", "Desc4", "a22");
        } else {
            System.out.println("Value Set - 'chardep' exists");
        }*/
        if (!dep.searchVSCode("ADS Job Family")) {
            System.out.println("Value Set - 'ADS Job Family' does not exist. Need to create a new one......");
            /*vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "chardep", "ValueSetDescription", "chardep",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Dependent", "ValueType", "Character", "ValueSubType", "Text", "MaxLength", "10",
                    "IndValueSetCode", "charvs", "10", "charvs", "ManageValues", "4", "Value1", "a11", "IndValue1", "a1", "Value2", "a12", "IndValue2",
                    "a1", "Value3", "a21", "IndValue3", "a2", "Value4", "a22", "IndValue4", "a2", "Desc1", "a11", "Desc2",
                    "a12", "Desc3", "a21", "Desc4", "a22");*/
        } else {
            System.out.println("Value Set - 'ADS Job Family' exists");
        }
    }

    /*
        ValueSet with Validation type = Independent , Value Data type = Number
        Search for value set using code.Create valueset with given code if the valueset not already exist.
        original - numIndVS
    */
    @Test(priority = 10, description = "Create a Valueset with code = Number_10_Key")
    public void testcase010() throws Exception {
        /*if (!dep.searchVSCode("numIndVS")) {
            System.out.println("Value Set - 'numIndVS' does not exist. Need to create a new one......");
            vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "numIndVS", "ValueSetDescription", "numIndVS",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Independent", "ValueType", "Number", "ManageValues",
                    "3", "Value1", "10", "Value2", "100", "Value3", "1000", "Desc1", "10", "Desc2", "100", "Desc3", "1000");

        } else {
            System.out.println("Value Set - 'numIndVS' exists");
        }*/
        if (!dep.searchVSCode("Number_10_Key")) {
            System.out.println("Value Set - 'Number_10_Key' does not exist. Need to create a new one......");
            /*vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "numIndVS", "ValueSetDescription", "numIndVS",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Independent", "ValueType", "Number", "ManageValues",
                    "3", "Value1", "10", "Value2", "100", "Value3", "1000", "Desc1", "10", "Desc2", "100", "Desc3", "1000");*/

        } else {
            System.out.println("Value Set - 'Number_10_Key' exists");
        }
    }

    /*
        ValueSet with Validation type = Dependent , Value Data type = Number
        Search for value set using code.Create value set with given code if the value set not already exist.
    */
    /*@Test(priority = 11, description = "Create a Valueset with code = numDepVS")
    public void testcase011() throws Exception {
        //original - numDepVS
        if (!dep.searchVSCode("numDepVS")) {
            vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "numDepVS", "ValueSetDescription", "numDepVS",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Dependent", "ValueType", "Number", "IndValueSetCode",
                    "numIndVS", "ManageValues", "9", "Value1", "11", "IndValue1", "10", "Value2", "12", "IndValue2",
                    "10", "Value3", "13", "IndValue3", "10", "Value4", "111", "IndValue4", "100", "Value5", "112", "IndValue5", "100", "Value6", "113",
                    "IndValue6", "100", "Value7", "1001", "IndValue7", "1000", "Value8", "1002", "IndValue8", "1000", "Value9", "1003",
                    "IndValue9", "1000", "Desc1", "11", "Desc2", "Desc4", "Two Hundred",
                    "12", "Desc3", "13", "Desc4", "111", "Desc5", "112", "Desc6", "113", "Desc7", "1001", "Desc8", "1002", "Desc9", "1003");
        	vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "numDepVS", "ValueSetDescription", "numDepVS",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Dependent", "ValueType", "Number", "IndValueSetCode",
                    "numIndVS", "ManageValues", "3", "Value1", "11", "IndValue1", "10", "Value2", "113", "IndValue2",
                    "100", "Value3", "1001", "IndValue3", "1000", "Desc1", "11", "Desc2", "113", "Desc3", "1001");
        } else {
            System.out.println("Value Set - 'numDepVS' exists");
        }
    }*/

    /*
        ValueSet with Validation type = Table , Value Data type = Number
        Search for value set using code.Create value set with given code if the value set not already exist.
    */
    @Test(priority = 12, description = "Create a Valueset with code = numtabvs")
    public void testcase012() throws Exception {
        if (!dep.searchVSCode("numtabvs")) {
            vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "numtabvs", "ValueSetDescription", "numtabvs",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Table", "ValueType", "Number", "FromClause",
                    "FND_DF_SEGMENTS_B", "ValueColumnName", "APPLICATION_ID", "WHEREClause",
                    "SEGMENT_CODE='NH_CB_Character' or SEGMENT_CODE='HRT_CI_GlobalSegment1' or SEGMENT_CODE='VOLUNTARY_SICKNESS_INSURANCE' or SEGMENT_CODE='SFSS_FLAG'");
        } else {
            System.out.println("Value Set - 'numtabvs' exists");
        }
    }
    
    @Test(priority = 13, description = "Create a Valueset with code = MyIndVS")
    public void testcase0013() throws Exception {
        if (!dep.searchVSCode("MyIndVS")) {
            System.out.println("Value Set - 'MyIndVS' does not exist. Need to create a new one......");

            vsUtil.createValueSet(effSearchVSTestdriver, "ValueSetCode", "MyIndVS", "ValueSetDescription", "MyIndVS",
                    "ValueSetModule", "Oracle Middleware Extensions for Applications", "ValidationType", "Independent", "ValueType", "Character",
                    "ValueSubType", "Text", "MaxLength", "20", "ManageValues", "5", "Value1", "ACCOUNT_CLASSES", "Value2", "ACCOUNT_SET_TYPES", "Value3", "ACCOUNT_STATUS", "Value4", "APR", "Value5", "ABBR_MONTH", "Desc1", "ACCOUNT_CLASSES", "Desc2", "ACCOUNT_SET_TYPES", "Desc3", "ACCOUNT_STATUS", "Desc4", "APR", "Desc5", "ABBR_MONTH");
        } else {
            System.out.println("Value Set - 'MyIndVS' exists");
        }
    }

    @AfterClass(alwaysRun = true)
    public void Logout() throws InterruptedException {
        try {
            aLoginInstance.logout(effSearchVSTestdriver);


        } catch (Exception ex) {
            aLoginInstance.launchURL(effSearchVSTestdriver);
            CommonUtils.hold(2);
            aLoginInstance.logout(effSearchVSTestdriver);
        } finally {
            try {
                effSearchVSTestdriver.quit();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("Exception while closing db connection ");
            }

        }

    }
}
