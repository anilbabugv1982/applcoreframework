package REST.tests.rra;

import TestBase.UI.GetDriverInstance;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.Log;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RraUserTrainingTest {
    public String[] words;
    Map<String, Map<String, Object>> dataObject;

    @BeforeClass(alwaysRun = true)
    public void loadJsonFile() throws IOException, ParseException {
        dataObject = RestCommonUtils.parseJson("RraTrainings", System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/rra/RraTraining.json");
        String str = GetDriverInstance.EnvironmentName;
        words = str.split("fscmUI");
        Log.info("Reading data from RraOrder.json file");
    }

    @Test(description = "to get all the trainings details",priority = 1,enabled = true)
    public void getRraUserTrainings()
    {
        Response trainingResp=null;
        String url=words[0]+"fscmRestApi/resources/11.13.18.05/rraUserTrainings";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        trainingResp = RestCommonUtils.getRequest(url, headers, "helpadmin", "Welcome1");
        System.out.println(trainingResp.asString());
        int statusCode = trainingResp.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        Assert.assertTrue(trainingResp.jsonPath().getList("items").size()>0,"items list size should be greater than zero");
    }

    @Test(description = "to update Completion date of a user training",priority = 2,enabled = true)
    public void updateTrainingCompletionDate()
    {
        Response trainingResp=null;
        String url = words[0] + dataObject.get("UpdateUserTraining").get("RequestURL");
        Object param1 = dataObject.get("UpdateUserTraining").get("Payloads");
        String parameters = param1.toString();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        trainingResp = RestCommonUtils.patchRequest(url, parameters.substring(1, parameters.length() - 1), "helpadmin", "Welcome1");
        System.out.println(trainingResp.asString());
        int statusCode = trainingResp.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(description = "to update user training task status of a task",priority = 3,enabled = true)
    public void updateUserTrainingTaskStatus() throws Exception {
        Response trainingResp=null;
        String url = words[0] + dataObject.get("UpdateUserTrainingTaskStatus").get("RequestURL");
        Object param1 = dataObject.get("UpdateUserTrainingTaskStatus").get("Payloads");
        String parameters = param1.toString();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        trainingResp = RestCommonUtils.patchRequest(url, parameters.substring(1, parameters.length() - 1), "helpadmin", "Welcome1");
        System.out.println(trainingResp.asString());
        int statusCode = trainingResp.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        String status=RestCommonUtils.parseResponsetemp(trainingResp,"UserTrainingTaskStatus");
        Assert.assertEquals(status,"Completed");
    }
}
