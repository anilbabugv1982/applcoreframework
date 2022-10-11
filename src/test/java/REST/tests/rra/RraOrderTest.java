package REST.tests.rra;

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

public class RraOrderTest {
    Map<String, Map<String, Object>> dataObject;
    public String[] words;
    public String orderIdForUpdate="";

    @BeforeClass(alwaysRun = true)
    public void loadJsonFile() throws IOException, ParseException {
        dataObject = RestCommonUtils.parseJson("OrderRra", System.getProperty("user.dir") + "/src/main/java/ConfigurationResources/REST/rra/RraOrder.json");
        String str = GetDriverInstance.EnvironmentName;
        words = str.split("fscmUI");
        Log.info("Reading data from RraOrder.json file");
    }

    @Test(description = "This testcase is used to create order", priority = 1, enabled = true)
    public void createOrder_RRA() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response orderResp;
        String url = words[0] + dataObject.get("CreateOrder").get("RequestURL");
        Object param1 = dataObject.get("CreateOrder").get("Payloads");
        String parameters = param1.toString();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        orderResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "helpadmin", "Welcome1");
        int statusCode = orderResp.getStatusCode();
        try {
            Assert.assertEquals(statusCode, 201);
            String status = RestCommonUtils.parseResponsetemp(orderResp, "OrderStatus");
            String orderId = RestCommonUtils.parseResponsetemp(orderResp, "OrderId");
            orderIdForUpdate=orderId;
            Assert.assertEquals(status, "PROCESSING");
            Assert.assertTrue(orderId!=null,"OrderId should not be null");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(description = "to get all the order details",priority = 2,enabled = true)
    public void getOrderDetails()
    {
        Response orderResp=null;
        String url=words[0]+"fscmRestApi/resources/11.13.18.05/rraOrders";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        orderResp = RestCommonUtils.getRequest(url, headers, "helpadmin", "Welcome1");
        System.out.println(orderResp.asString());
        int statusCode = orderResp.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(description = "to get all the order details when invalid authorisation details sent in header",priority = 3,enabled = true)
    public void getOrderDetails_InvalidAuth()
    {
        Response orderResp=null;
        String url=words[0]+"fscmRestApi/resources/11.13.18.05/rraOrders";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        orderResp = RestCommonUtils.getRequest(url, headers, "abc", "Welcome1");
        int statusCode = orderResp.getStatusCode();
        Assert.assertEquals(statusCode, 401);
    }

    @Test(description = "update an order details",priority = 4,enabled = true , dependsOnMethods = { "createOrder_RRA" })
    public void updateOrder_Rra() throws Exception {
        Response orderResp;
        String url = words[0] + dataObject.get("UpdateOrder").get("RequestURL")+"/"+orderIdForUpdate;
        Object param1 = dataObject.get("UpdateOrder").get("Payloads");
        String parameters = param1.toString();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "*/*");
        orderResp = RestCommonUtils.patchRequest(url, parameters.substring(1, parameters.length() - 1), "helpadmin", "Welcome1");
        System.out.println(orderResp.asString());
        int statusCode=orderResp.getStatusCode();
        Assert.assertEquals(statusCode,200);
        String status = RestCommonUtils.parseResponsetemp(orderResp, "OrderStatus");
        Assert.assertEquals(status, "SUCCESS");
        String orderId=RestCommonUtils.parseResponsetemp(orderResp, "OrderId");
        Assert.assertEquals(orderId, orderIdForUpdate);
    }

    @Test(description = "This testcase is used to update order lines", priority = 5, enabled = true)
    public void updateOrderLines_RRA() throws FileNotFoundException, IOException, ParseException, Exception, Exception {
        Response orderResp;
        String url = words[0] + dataObject.get("UpdateOrderLines").get("RequestURL");
        Object param1 = dataObject.get("UpdateOrderLines").get("Payloads");
        String parameters = param1.toString();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/vnd.oracle.adf.batch+json");
        headers.put("Accept", "*/*");
        orderResp = RestCommonUtils.postRequest(url, parameters.substring(1, parameters.length() - 1), headers, "helpadmin", "Welcome1");
        int statusCode = orderResp.getStatusCode();
        try {
            Assert.assertEquals(statusCode, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
