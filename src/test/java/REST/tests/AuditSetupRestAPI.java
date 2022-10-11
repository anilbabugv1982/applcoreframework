package REST.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import TestBase.UI.GetDriverInstance;
import io.restassured.response.Response;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.AttachCatUtils;
import pageDefinitions.UI.oracle.applcore.qa.Aolcore.ProfilesUtils;
import pageDefinitions.UI.oracle.applcore.qa.Audit.CommonAuditSetup;
import pageDefinitions.UI.oracle.applcore.qa.Audit.HcmPersonUtils;
import pageDefinitions.UI.oracle.applcore.qa.Kff.ManageKFFSetup;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.ApplicationLogin;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.GlobalPageTemplate;
import UtilClasses.UI.Log;
import UtilClasses.UI.NavigationTaskFlows;
import UtilClasses.UI.NavigatorMenuElements;

public class AuditSetupRestAPI extends GetDriverInstance {
    String pid;
    WebDriver AuditDrvInstance;
    private ApplicationLogin aLoginInstance;
    private NavigationTaskFlows ntFInstance;
    private NavigatorMenuElements nMEInstance;
    private GlobalPageTemplate glbpageInstance;
    private CommonUtils CUtilInstance;
    private CommonAuditSetup AuditSetupinstance;
    private ProfilesUtils ProfilsInstance;
    private ManageKFFSetup manageKFFSetupInstance;
    private AttachCatUtils AttachCatUtilInstance;
    private HcmPersonUtils HcmPersonUtilInstance;
    public static String timestamp = "";
    Map<String, Map<String, Object>> postObject;
    Map<String, String> responseObject;
    String response;
    public String RestUrl;
    public String[] words;
    public String auditLevelOPSS;

    @BeforeClass(alwaysRun = true)
    public void loadJsonFile() throws IOException, ParseException {
        postObject = RestCommonUtils.parseJson("AuditSetup", System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/Audit.json");
        String str = GetDriverInstance.EnvironmentName;
        words = str.split("fscmUI");
        Log.info("Reading data from Audit.json file");
    }

    // ****************************************************************************************************************************************************************************************************************
    @Test(description = "This testcase is used to update audit status for Profile Option", priority = 1, enabled = true)
    public void updateAuditStatus_ProfileOption() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + postObject.get("SetupProfileOption").get("RequestURL");
        Object param1 = postObject.get("SetupProfileOption").get("Payloads");
        String parameters = param1.toString();
        String enableValue=RestCommonUtils.fetchAnyKeyDataFromPayload(parameters.substring(1,parameters.length()-1),"auditData","enable");

        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), "app_impl_consultant");
        int statusCode = updateAuditResp.getStatusCode();
        Log.info("getStatusCode for indexcreationresponse:" + statusCode);
        Log.info("indexcreationresponse:" + updateAuditResp.getBody().asString());

        try {
            String status = RestCommonUtils.parseResponsetemp(updateAuditResp, "status");
            boolean enabled = updateAuditResp.jsonPath().get("auditData[0].objectDetails[0].enable");
            Assert.assertEquals(statusCode, 200);
            Assert.assertEquals(status, "SUCCESS");
            Assert.assertEquals(enabled,Boolean.parseBoolean(enableValue));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//****************************************************************************************************************************************************************************************************************	

    @Test(description = "This testcase is used to update audit status for KFF", priority = 2, enabled = true)
    public void updateAuditStatus_KFF() throws FileNotFoundException, IOException, ParseException {
        Response updateKFFResp;
        String url = words[0] + postObject.get("SetupKFFAUdit").get("RequestURL");
        Object param1 = postObject.get("SetupKFFAUdit").get("Payloads");
        String parameters = param1.toString();
        String enableValue=RestCommonUtils.fetchAnyKeyDataFromPayload(parameters.substring(1,parameters.length()-1),"auditData","enable");
        updateKFFResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), "app_impl_consultant");
        int statusCode = updateKFFResp.getStatusCode();
        Log.info("getStatusCode for indexcreationresponse:" + updateKFFResp.getStatusCode());
        Log.info("indexcreationresponse:" + updateKFFResp.getBody().asString());
        Assert.assertEquals(statusCode, 200);

        try {
            String status = RestCommonUtils.parseResponsetemp(updateKFFResp, "status");
            Assert.assertEquals(status, "SUCCESS");
            boolean enabled = updateKFFResp.jsonPath().get("auditData[0].objectDetails[0].enable");
            Assert.assertEquals(enabled, Boolean.parseBoolean(enableValue));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //****************************************************************************************************************************************************************************************************************
    @Test(description = "This testcase is used for update audit for Manage Attachment Categories", priority = 3, enabled = true)
    public void updateAuditStatus_AttachCategory() throws FileNotFoundException, IOException, ParseException {
        Response updateAuditCatResp;
        String url = words[0] + postObject.get("AuditAttachCate").get("RequestURL");
        Object param1 = postObject.get("AuditAttachCate").get("Payloads");
        String parameters = param1.toString();
        String enableValue=RestCommonUtils.fetchAnyKeyDataFromPayload(parameters.substring(1,parameters.length()-1),"auditData","enable");
        updateAuditCatResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), "app_impl_consultant");
        int statusCode = updateAuditCatResp.getStatusCode();
        Log.info("getStatusCode for indexcreationresponse:" + statusCode);
        Log.info("indexcreationresponse:" + updateAuditCatResp.getBody().asString());

        try {
            String status = RestCommonUtils.parseResponsetemp(updateAuditCatResp, "status");
            Assert.assertEquals(statusCode, 200);
            Assert.assertEquals(status, "SUCCESS");
            boolean enabled = updateAuditCatResp.jsonPath().get("auditData[0].objectDetails[0].enable");
            Assert.assertEquals(enabled, Boolean.parseBoolean(enableValue));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //****************************************************************************************************************************************************************************************************************
    @Test(description = "This testcase is used to set audit level OPSS Data", priority = 4, enabled = true)
    public void setAuditLevelOPSS() throws FileNotFoundException, IOException, ParseException {
        Response setAuditOPSSResp;

        String url = words[0] + postObject.get("SetAuditOPSSData").get("RequestURL");
        Object param1 = postObject.get("SetAuditOPSSData").get("Payloads");
        String parameters = param1.toString();

		setAuditOPSSResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), "app_impl_consultant");
        int statusCode=setAuditOPSSResp.getStatusCode();
        Log.info("getStatusCode for indexcreationresponse:" + setAuditOPSSResp.getStatusCode());
        Log.info("indexcreationresponse:" + setAuditOPSSResp.getBody().asString());

        try {
            String status = RestCommonUtils.parseResponsetemp(setAuditOPSSResp, "status");
            Assert.assertEquals(statusCode,200);
            Assert.assertEquals(status, "SUCCESS");
            auditLevelOPSS=setAuditOPSSResp.jsonPath().get("auditData[0].auditLevel");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //****************************************************************************************************************************************************************************************************************
    @Test(description = "This testcase is used to update audit status for Person HCM", priority = 5, enabled = true)
    public void updateAuditStatus_PersonHCM() throws FileNotFoundException, IOException, ParseException {
        Response updateAuditHCMResp;
        String url = words[0] + postObject.get("AuditHCMPerson").get("RequestURL");
        Object param1 = postObject.get("AuditHCMPerson").get("Payloads");
        String parameters = param1.toString();
        String enableValue=RestCommonUtils.fetchAnyKeyDataFromPayload(parameters.substring(1,parameters.length()-1),"auditData","enable");
		updateAuditHCMResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), "app_impl_consultant");
		int statusCode=updateAuditHCMResp.getStatusCode();
        Log.info("getStatusCode for indexcreationresponse:" + statusCode);
        Log.info("indexcreationresponse:" + updateAuditHCMResp.getBody().asString());

        try {
            String status = RestCommonUtils.parseResponsetemp(updateAuditHCMResp, "status");
            Assert.assertEquals(statusCode,200);
            Assert.assertEquals(status, "SUCCESS");
			boolean enabled = updateAuditHCMResp.jsonPath().get("auditData[0].objectDetails[0].enable");
			Assert.assertEquals(enabled, Boolean.parseBoolean(enableValue));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //*****************************************************************************************************************************
    @Test(priority = 6, description = "This testcase is used to update audit status for Auditing EFF Item", enabled = true)
    public void updateAuditStatus_EFFItem() throws Exception {
        Response auditEFFItemFlex;
        String url = words[0] + postObject.get("AuditItemEFF").get("RequestURL");
        Object param1 = postObject.get("AuditItemEFF").get("Payloads");
        String parameters = param1.toString();
		auditEFFItemFlex = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), "app_impl_consultant");
		int statusCode=auditEFFItemFlex.getStatusCode();
        Log.info("getStatusCode for indexcreationresponse:" + auditEFFItemFlex.getStatusCode());
        Log.info("indexcreationresponse:" + auditEFFItemFlex.getBody().asString());

        try {
            String status = RestCommonUtils.parseResponsetemp(auditEFFItemFlex, "status");
            Assert.assertEquals(statusCode,200);
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(priority = 7, description = "This testcase is used to get audit status for OPSS", enabled = true,dependsOnMethods = {"setAuditLevelOPSS"})
    public void getAuditDataOPSS() throws Exception {
        Response getAuditOPSSResp;
        String url = words[0] + postObject.get("GetAuditOPSS").get("RequestURL");
        Object param1 = postObject.get("GetAuditOPSS").get("Payloads");
        String parameters = param1.toString();
        getAuditOPSSResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), "app_impl_consultant");
        int statusCode=getAuditOPSSResp.getStatusCode();
        Log.info("getStatusCode for indexcreationresponse:" + getAuditOPSSResp.getStatusCode());
        Log.info("indexcreationresponse:" + getAuditOPSSResp.getBody().asString());

        try {
            String status = RestCommonUtils.parseResponsetemp(getAuditOPSSResp, "status");
            Assert.assertEquals(statusCode,200);
            Assert.assertEquals(status, "SUCCESS");
            String actAuditLevel=getAuditOPSSResp.jsonPath().get("auditData[0].auditLevel");
            Assert.assertEquals(actAuditLevel,auditLevelOPSS);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(priority = 8, description = "This testcase is used to check audit status for Profile option", enabled = true)
    public void checkAuditStatus_ProfileOption() throws Exception {
        Response getAuditOPSSResp;
        String url = words[0] + postObject.get("CheckAuditStatus_ProfileOption").get("RequestURL");
        Object param1 = postObject.get("CheckAuditStatus_ProfileOption").get("Payloads");
        String parameters = param1.toString();
        getAuditOPSSResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), "app_impl_consultant");
        int statusCode=getAuditOPSSResp.getStatusCode();
        Log.info("getStatusCode for indexcreationresponse:" + getAuditOPSSResp.getStatusCode());
        Log.info("indexcreationresponse:" + getAuditOPSSResp.getBody().asString());

        try {
            String status = RestCommonUtils.parseResponsetemp(getAuditOPSSResp, "status");
            Assert.assertEquals(statusCode,200);
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(priority = 9, description = "This testcase is used to get audit set up", enabled = true)
    public void getAuditSetup() throws Exception {
        Response getAuditOPSSResp;
        String url = words[0] + postObject.get("GetAuditSetup").get("RequestURL");
        Object param1 = postObject.get("GetAuditSetup").get("Payloads");
        String parameters = param1.toString();
        getAuditOPSSResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), "app_impl_consultant");
        int statusCode=getAuditOPSSResp.getStatusCode();
        Log.info("getStatusCode for indexcreationresponse:" + getAuditOPSSResp.getStatusCode());
        Log.info("indexcreationresponse:" + getAuditOPSSResp.getBody().asString());

        try {
            String status = RestCommonUtils.parseResponsetemp(getAuditOPSSResp, "status");
            Assert.assertEquals(statusCode,200);
            Assert.assertEquals(status, "SUCCESS");
            int size=getAuditOPSSResp.jsonPath().getList("auditData[0].objectDetails").size();
            Assert.assertTrue(size>0,"Validating object details length should be greater than zero");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


//**********************************************************************************************************************************************************************
	/*@Parameters({ "user", "pwd" })
	@BeforeClass(alwaysRun = true)
    public void startUP(String username, String password) throws Exception {
		AuditDrvInstance =  getDriverInstanceObject();
		aLoginInstance = new ApplicationLogin(AuditDrvInstance);
		ntFInstance = new NavigationTaskFlows(AuditDrvInstance);
		nMEInstance = new NavigatorMenuElements(AuditDrvInstance);
		glbpageInstance=new GlobalPageTemplate(AuditDrvInstance);
		AuditSetupinstance=new CommonAuditSetup(AuditDrvInstance);
		ProfilsInstance=new ProfilesUtils(AuditDrvInstance);
    aLoginInstance.login("app_impl_consultant", "Welcome1",AuditDrvInstance);       
	Log.info("Logging into the Application");
	CommonUtils.hold(30);
    }*/

//*****************************************************************************************************************************************************************	

    @Test(priority = 10, description = "This testcase is used Creating Profile Option", enabled = true)
    public void CreateProfileOption() throws Exception {
        AuditDrvInstance = getDriverInstanceObject();
        aLoginInstance = new ApplicationLogin(AuditDrvInstance);
        ntFInstance = new NavigationTaskFlows(AuditDrvInstance);
        nMEInstance = new NavigatorMenuElements(AuditDrvInstance);
        glbpageInstance = new GlobalPageTemplate(AuditDrvInstance);
        AuditSetupinstance = new CommonAuditSetup(AuditDrvInstance);
        ProfilsInstance = new ProfilesUtils(AuditDrvInstance);
        aLoginInstance.login("app_impl_consultant", "Welcome1", AuditDrvInstance);
        Log.info("Logging into the Application");
        CommonUtils.hold(30);
        ntFInstance.navigateToTask(glbpageInstance.setupandmaintenance, AuditDrvInstance);
        Log.info("Navigate to Setup and Maintenance");
        ntFInstance.navigateToAOLTaskFlows("Manage Profile Options", AuditDrvInstance);
        Log.info("Navigate to Manage Profile Option task flow");
        CommonUtils.hold(10);
        ProfilsInstance.createProfileOptions("PO" + CommonUtils.uniqueId(), AuditDrvInstance);
        Log.info("Create Profile Option");
        CommonUtils.hold(5);
        glbpageInstance.homeIcon.click();
        Log.info("Click on Home Icon");
    }


    //*****************************************************************************************************************************************************************

    @Test(priority = 11, description = "This testcase is used Creating Attachment Category", enabled = true)
    public void CreateAttachmentCategory() throws Exception {
        AttachCatUtilInstance = new AttachCatUtils(AuditDrvInstance);
        CommonUtils.hold(30);
        ntFInstance.navigateToTask(glbpageInstance.setupandmaintenance, AuditDrvInstance);
        Log.info("Click on Setup and Maintenance");
        ntFInstance.navigateToAOLTaskFlows("Manage Attachment Entities", AuditDrvInstance);
        Log.info("Click on Manage Attachment Entities");
        AttachCatUtilInstance.attachmententity(AuditDrvInstance, false);
        Log.info("Create Attachment Entity");
        ntFInstance.navigateToTask(glbpageInstance.setupandmaintenance, AuditDrvInstance);
        Log.info("Click on Setup and Maintenance");
        ntFInstance.navigateToAOLTaskFlows("Manage Attachment Categories", AuditDrvInstance);
        Log.info("Click on Manage Attachment Categories");
        AttachCatUtilInstance.AttachCateg(AuditDrvInstance);
        Log.info("Create Attachment Category");
        CommonUtils.hold(5);
        glbpageInstance.homeIcon.click();
        Log.info("Click on Home Icon");
    }

//**********************************************************************************************************************************************************************

    @Test(priority = 12, description = "This testcase is used Creating KFF", enabled = true)
    public void CreateKFF() throws Exception {

        manageKFFSetupInstance = new ManageKFFSetup(AuditDrvInstance);
        CommonUtils.hold(10);
        ntFInstance.navigateToTask(glbpageInstance.setupandmaintenance, AuditDrvInstance);
        Log.info("Click on Setup and Maintenance");
        CommonUtils.hold(10);
        ntFInstance.navigateToAOLTfSearchPage("Manage Key Flexfields", AuditDrvInstance);
        Log.info("Search for KFF");
        CommonUtils.hold(10);
        glbpageInstance.homeIcon.click();
        Log.info("Click on Home Icon");
        aLoginInstance.logout(AuditDrvInstance);
        Log.info("Logout of the Environment");

    }


    //**********************************************************************************************************************************************************************
    @Test(priority = 13, description = "This testcase is used Creating Person in HCM", enabled = true)
    public void CreatePerson() throws Exception {
        HcmPersonUtilInstance = new HcmPersonUtils(AuditDrvInstance);
        aLoginInstance.login("hcm_user1", "Welcome1", AuditDrvInstance);
        Log.info("Login into hcm_user1 user");
        CommonUtils.hold(20);
        ntFInstance.navigateToTask(glbpageInstance.newPerson, AuditDrvInstance);
        Log.info("Click on New Person");
        HcmPersonUtilInstance.createperson(AuditDrvInstance);
        Log.info("Create a Person");


    }

    //**********************************************************************************************************************************************************************
    @Test(priority = 11, description = "This testcase is used Creating User/Role in SecurityConsole", enabled = false)
    public void Createuser() throws Exception {


    }

    //**********************************************************************************************************************************************************************
    @Test(priority = 12, description = "This testcase is used Creating Item in EFF", enabled = false)
    public void CreateItem() throws Exception {


    }

    //**********************************************************************************************************************************************************************
    @Test(priority = 13, description = "Audit Report for Profile Option", enabled = false)
    public void ProfileOPtionAuditReport() throws Exception {


    }

    //**********************************************************************************************************************************************************************
    @Test(priority = 14, description = "Audit Report for AttachmentCategory", enabled = false)
    public void AttachEntAuditReport() throws Exception {


    }

    //**********************************************************************************************************************************************************************
    @Test(priority = 15, description = "Audit Report for KFF", enabled = false)
    public void KFFAuditReport() throws Exception {


    }
    //**********************************************************************************************************************************************************************

}
