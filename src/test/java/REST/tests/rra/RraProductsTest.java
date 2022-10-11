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

public class RraProductsTest {

    public String[] words;

    @BeforeClass(alwaysRun = true)
    public void loadJsonFile() throws IOException, ParseException {
        String str = GetDriverInstance.EnvironmentName;
        words = str.split("fscmUI");
    }


    @Test(description = "to get all the product details",priority = 1,enabled = true)
    public void getProductDetails()
    {
        Response productResp=null;
        String url=words[0]+"fscmRestApi/resources/11.13.18.05/rraProducts";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        productResp = RestCommonUtils.getRequest(url, headers, "helpadmin", "Welcome1");
        System.out.println(productResp.asString());
        int statusCode = productResp.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        Assert.assertTrue(productResp.jsonPath().getList("items").size()>0,"products should be fetched successfully");
    }
}
