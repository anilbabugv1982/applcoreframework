package REST.tests;

import TestBase.UI.GetDriverInstance;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.Log;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuditReportHistoryRestAPI {
    Map<String, Map<String, Object>> dataObject;
    public String[] words;
    String cookie = "OAMAuthnHintCookie";

    @BeforeClass(alwaysRun = true)
    public void loadJsonFile() throws IOException, ParseException {
        dataObject = RestCommonUtils.parseJson("AuditReport", System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/AuditReportHistory.json");
        String str = GetDriverInstance.EnvironmentName;
        words = str.split("fscmUI");
        Log.info("Reading data from AuditReportHistory.json file");
    }

    @Test(description = "This testcase is used to get audit history for Profile Option", priority = 1, enabled = true)
    public void getAuditHistory_ProfileOption() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("ProfileOption").get("RequestURL");
        Object param1 = dataObject.get("ProfileOption").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = RestCommonUtils.parseResponsetemp(updateAuditResp, "status");
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(description = "This testcase is used to get audit history for Document Categories", priority = 1, enabled = true)
    public void getAuditHistory_DocumentCategories() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("DocumentCategories").get("RequestURL");
        Object param1 = dataObject.get("DocumentCategories").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = RestCommonUtils.parseResponsetemp(updateAuditResp, "status");
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(description = "This testcase is used to get audit history for Key flex fields", priority = 1, enabled = true)
    public void getAuditHistory_KFF() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("KeyFlexField").get("RequestURL");
        Object param1 = dataObject.get("KeyFlexField").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = RestCommonUtils.parseResponsetemp(updateAuditResp, "status");
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(description = "This testcase is used to get audit history for Key flex fields with includeImpersonator=false", priority = 1, enabled = true)
    public void getAuditHistory_KFF_FalseImpersonator() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("KeyFlexField_FalseImpersonator").get("RequestURL");
        Object param1 = dataObject.get("KeyFlexField_FalseImpersonator").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = RestCommonUtils.parseResponsetemp(updateAuditResp, "status");
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(description = "This testcase is used to get audit history for OPSS", priority = 1, enabled = true)
    public void getAuditHistory_OPSS() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("KeyFlexField_FalseImpersonator").get("RequestURL");
        Object param1 = dataObject.get("KeyFlexField_FalseImpersonator").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = RestCommonUtils.parseResponsetemp(updateAuditResp, "status");
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(description = "This testcase is used to get audit history for HCM", priority = 1, enabled = true)
    public void getAuditHistory_HCM() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("HCMAuditHistory").get("RequestURL");
        Object param1 = dataObject.get("HCMAuditHistory").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = RestCommonUtils.parseResponsetemp(updateAuditResp, "status");
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(description = "This testcase is used to get audit history for Profile Option with includeAttributes=false", priority = 2, enabled = true)
    public void getAuditHistory_FalseAttr() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("ProfileOption_FalseAttr").get("RequestURL");
        Object param1 = dataObject.get("ProfileOption_FalseAttr").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = RestCommonUtils.parseResponsetemp(updateAuditResp, "status");
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(description = "This testcase is used to get audit history for Profile Option with date range > 30 days", priority = 3, enabled = true)
    public void getAuditHistory_DateRangeCheck() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("ProfileOption_DateRange").get("RequestURL");
        Object param1 = dataObject.get("ProfileOption_DateRange").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 400);
            String status = updateAuditResp.jsonPath().getString("status");
            Assert.assertEquals(status, "FAIL");
            int pageNum = updateAuditResp.jsonPath().getInt("pageNumber");
            int pageSize = updateAuditResp.jsonPath().getInt("pageSize");
            Assert.assertEquals(pageNum, 0);
            Assert.assertEquals(pageSize, 0);
            String errMsg = updateAuditResp.jsonPath().getString("error.errorDetail[0].detail");
            String title = updateAuditResp.jsonPath().getString("error.errorDetail[0].title");
            Assert.assertEquals(errMsg, "Time Span is more than 30 days");
            Assert.assertEquals(title, "Incorrect Time Span");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(description = "This testcase is used to get audit history for Profile Option without includeAttributes in request", priority = 2, enabled = true)
    public void getAuditHistory_RemoveAttr() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("ProfileOption_RemoveAttr").get("RequestURL");
        Object param1 = dataObject.get("ProfileOption_RemoveAttr").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = RestCommonUtils.parseResponsetemp(updateAuditResp, "status");
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(description = "This testcase is used to get audit history for Profile Option with timeZone in request", priority = 2, enabled = true)
    public void getAuditHistory_timeZone() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("ProfileOption_Timezone").get("RequestURL");
        Object param1 = dataObject.get("ProfileOption_Timezone").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = RestCommonUtils.parseResponsetemp(updateAuditResp, "status");
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(description = "This testcase is used to get audit history for Profile Option with invalid product name", priority = 3, enabled = true)
    public void getAuditHistory_InvalidProduct() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("ProfileOption_InvalidProduct").get("RequestURL");
        Object param1 = dataObject.get("ProfileOption_InvalidProduct").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = updateAuditResp.jsonPath().getString("status");
            Assert.assertEquals(status, "SUCCESS");
            int pageSize = updateAuditResp.jsonPath().getInt("pageSize");
            Assert.assertEquals(pageSize, 0);
            Assert.assertTrue(updateAuditResp.jsonPath().getList("auditData").size()==0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Test(description = "This testcase is used to get audit history for Profile Option with invalid business object", priority = 3, enabled = true)
    public void getAuditHistory_InvalidBO() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("ProfileOption_InvalidBO").get("RequestURL");
        Object param1 = dataObject.get("ProfileOption_InvalidBO").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = updateAuditResp.jsonPath().getString("status");
            Assert.assertEquals(status, "SUCCESS");
            int pageSize = updateAuditResp.jsonPath().getInt("pageSize");
            Assert.assertEquals(pageSize, 0);
            Assert.assertTrue(updateAuditResp.jsonPath().getList("auditData").size()==0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test(description = "This testcase is used to get audit history for Profile Option with attributeDetailMode=false", priority = 2, enabled = true)
    public void getAuditHistory_FalseMode() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response updateAuditResp;

        String url = words[0] + dataObject.get("ProfileOption_FalseMode").get("RequestURL");
        Object param1 = dataObject.get("ProfileOption_FalseMode").get("Payloads");
        String parameters = param1.toString();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        updateAuditResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "internal_auditor_m1", "Welcome1", cookie);
        int statusCode = updateAuditResp.getStatusCode();

        try {
            Assert.assertEquals(statusCode, 200);
            String status = RestCommonUtils.parseResponsetemp(updateAuditResp, "status");
            Assert.assertEquals(status, "SUCCESS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
