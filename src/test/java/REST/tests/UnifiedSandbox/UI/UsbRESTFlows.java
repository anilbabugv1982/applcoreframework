package REST.tests.UnifiedSandbox.UI;

import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.Log;

import TestBase.UI.GetDriverInstance;
import org.testng.annotations.BeforeClass;


public class UsbRESTFlows {
	static Map<String, List<Object>> postObject;
	Map<String, String> responseObject;
	Map<String, String> headers;
	String response;
	static String RestUrl;
	static String publishableSandboxId;
	static String refreshableSandboxId;
	static String uniqueID;
	static final String uname="app_impl_consultant";
	static final String password="Welcome1";
	
	public String getURL(String keyDescription) {
		return RestUrl+postObject.get(keyDescription).get(1).toString();
	}
	
	public String getParameters(String keyDescription) {
		String parameter=postObject.get(keyDescription).get(2).toString();
		if(parameter.contains("_Date")) {
			String temp=parameter.replace("_Date", uniqueID);
			return temp;
		}  
		return parameter;
	}
	
	//Get Json Object and URL
	@BeforeClass(alwaysRun = true)
	public void parseJson() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException{
		postObject = RestCommonUtils.parseUSBJson("USB","./src/main/java/ConfigurationResources/REST/UsbRest.json");
		String env=GetDriverInstance.EnvironmentName;
		Integer index=env.indexOf("/fscmUI/");
		RestUrl=env.substring(0,index);
		System.out.println(RestUrl);
        uniqueID = CommonUtils.uniqueId();
		//publishableSandboxId="fa7f5430c1ae49ec9af8b3c37641c01a";
		//refreshableSandboxId="e9b935543a754fc49d993954a11a8200";
	}
	
	//Get Sandbox 
	@Test(description="This testcase is to get all existing Sandboxes",priority=100,enabled=true)
	public void getSandboxes() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		Response getusbresponse;
		Log.info("Get Method for getting all sandboxes");
		String URL=getURL("getAllSandboxes");
		System.out.println(URL);
		getusbresponse = RestCommonUtils.getUSBRequest(URL);
		Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
		System.out.println("getusbresponse Status Code:" +getusbresponse.getStatusCode());		
		System.out.println("getusbresponse Body:" +getusbresponse.getBody().asString());	
			
	}
	
	//Get Sandbox 
	@Test(description="This testcase is to get all existing Sandboxes that matches the condition",priority=102,enabled=true)
	public void getSandboxOnName() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		Response getusbresponse;
		Log.info("Get Method for getting all sandboxes based on a name condition");
		String URL=getURL("getAllSandboxeswithname");
		System.out.println(URL);
		getusbresponse = RestCommonUtils.getUSBRequest(URL);
		Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
		System.out.println("getusbresponse Status Code:" +getusbresponse.getStatusCode());		
		System.out.println("getusbresponse Body:" +getusbresponse.getBody().asString());		
	}
	
	//Get Sandbox 
	@Test(description="This testcase is to get all existing Sandboxes that matches the condition",priority=103,enabled=true)
	public void getSandboxOnCondition() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		Response getusbresponse;
		Log.info("Get Method for getting all sandboxes based on a condition");
		String URL=getURL("getAllSandboxesWithCondition");
		System.out.println(URL);
		getusbresponse = RestCommonUtils.getUSBRequest(URL);
		Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
		System.out.println("getusbresponse Status Code:" +getusbresponse.getStatusCode());		
		System.out.println("getusbresponse Body:" +getusbresponse.getBody().asString());		
	}
	
	//Get Sandbox Feature
	@Test(description="This testcase is to get all existing Features - Repo names",priority=104,enabled=true)
	public void getSandboxFeatures() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		Response getusbresponse;
		Log.info("Get Method for getting all sandboxes features");
		String URL=getURL("getAllSandboxFeatures");
		System.out.println(URL);
		getusbresponse = RestCommonUtils.getUSBRequest(URL);
		Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
		System.out.println("getusbresponse Status Code:" +getusbresponse.getStatusCode());		
		System.out.println("getusbresponse Body:" +getusbresponse.getBody().asString());		
	}
	
	//Create Sandbox
	@Test(description="This testcase is to create a Sandbox with Appcomposer and String Editor Tools",priority=105,enabled=true)
	public void createPublishableSandbox() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		Response usbresponse;
		System.out.println("Inside USB Create Sandbox");
		Log.info("Post Method for Creating USB ");
		String URL=getURL("createPublishableSandbox");
		String parameters=getParameters("createPublishableSandbox");
		usbresponse = RestCommonUtils.postRequest(URL,parameters,"app_impl_consultant");
		Log.info("USB Creation Response from  Server");
		System.out.println("getStatusCode for usbcreationresponse:  "+usbresponse.getStatusCode());
		System.out.println("usbcreationresponse:"+usbresponse.getBody().asString());
		Assert.assertEquals(usbresponse.getStatusCode() == 201, true, "Request is successful");
		
		//Get Sandbox ID 
		if(usbresponse.getStatusCode() == 201) {
			JsonPath jsonPathEvaluator = usbresponse.jsonPath();
			publishableSandboxId=jsonPathEvaluator.get("id");
			System.out.println("Sandbox is created and its id is :"+publishableSandboxId);
		}	
	}
	
	
	//createCustomObjectSandbox
	@Test(description="This testcase is to create a Custom Object for Publishable Sandbox",priority=106,enabled=true)
	public void createCustomObjectForPublishableSandbox() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		Response usbresponse;
		Log.info("Post Method for Creating Custom Object for a USB ");
		String URL=getURL("createCustomObjectSandbox");
		String parameters=getParameters("createCustomObjectSandbox");
		String contentType="application/vnd.oracle.adf.resourceitem+json";
		usbresponse = RestCommonUtils.postUSBRequest(URL,parameters,contentType,publishableSandboxId);
		System.out.println("Response Status Code:  "+usbresponse.getStatusCode());
		System.out.println("Response Body:"+usbresponse.getBody().asString());
		Assert.assertEquals(usbresponse.getStatusCode() == 201, true, "Request is successful");
	}

	//Create Sandbox2
		@Test(description="This testcase is to create a refreshable Sandbox with Appcomposer and String Editor Tools",priority=107,enabled=true)
		public void createRefreshableSandbox() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response usbresponse;
			Log.info("Post Method for Creating USB ");
			String URL=getURL("createRefreshableSandbox");
			String parameters=getParameters("createRefreshableSandbox");
			usbresponse = RestCommonUtils.postRequest(URL,parameters,"app_impl_consultant");
			System.out.println("getStatusCode for usbcreationresponse:  "+usbresponse.getStatusCode());
			System.out.println("usbcreationresponse:"+usbresponse.getBody().asString());
			Assert.assertEquals(usbresponse.getStatusCode() == 201, true, "Request is successful");
			
			//Get Sandbox ID 
			if(usbresponse.getStatusCode() == 201) {
				JsonPath jsonPathEvaluator = usbresponse.jsonPath();
				refreshableSandboxId=jsonPathEvaluator.get("id");
				System.out.println("Sandbox is created and its id is :"+refreshableSandboxId);
			}	
		}
		
		
		//createCustomObjectSandbox
		@Test(description="This testcase is to create a Custom Object for Refreshable Sandbox",priority=108,enabled=true)
		public void createCustomObjectForRefreshableSandbox() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response usbresponse;
			Log.info("Post Method for Creating Custom Object for a USB ");
			String URL=getURL("createCustomObjectSandbox2");
			String parameters=getParameters("createCustomObjectSandbox2");
			String contentType="application/vnd.oracle.adf.resourceitem+json";
			usbresponse = RestCommonUtils.postUSBRequest(URL,parameters,contentType,refreshableSandboxId);
			System.out.println("Response Status Code:  "+usbresponse.getStatusCode());
			System.out.println("Response Body:"+usbresponse.getBody().asString());
			Assert.assertEquals(usbresponse.getStatusCode() == 201, true, "Request is successful");
		}

	
		@Test(description="This testcase is to Publish a Sandbox",priority=109,enabled=true)
		public void publishSandbox() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response usbresponse;
			Log.info("PUT Method for Publishing USB ");
			String URL=getURL("usbPublish");
			String parameters="NULL";
			String replacedURL=URL.replace("id", publishableSandboxId);
			System.out.println(replacedURL);
			headers=new HashMap<String,String>();
			usbresponse = RestCommonUtils.putRequest(replacedURL,parameters,headers,uname,password);
			System.out.println("Response Status Code:  "+usbresponse.getStatusCode());
			System.out.println("Response Body:"+usbresponse.getBody().asString());
			Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Request is successful");
			
			//Check the Sandbox status
			if(usbresponse.getStatusCode() == 200) {
				JsonPath jsonPathEvaluator = usbresponse.jsonPath();
				if(jsonPathEvaluator.get("state")=="PENDING_PUBLISH")
					System.out.println("Sandbox status is PENDING_PUBLISH");
			}	
			
		}
		
		//Get Sandbox Condition
		@Test(description="This testcase is to get the condition",priority=110,enabled=true)
		public void getSandboxPublishCondition() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			
			Log.info("Get Method for getting USB publish condition");
			Map<String,String> status_condition=new HashMap<>();
			status_condition=getUSBStatus(publishableSandboxId);
			for(String key:status_condition.keySet())
				System.out.println("USB " + key+" is  :"+status_condition.get(key));
			while(status_condition.containsValue("pending"))
			{
				CommonUtils.hold(20); //Sleep for 20 seconds to check the status again
				status_condition=getUSBStatus(publishableSandboxId);
				for(String key:status_condition.keySet())
					System.out.println("USB "+key+"Value is"+status_condition.get(key));
			}
			
			Log.info("Done with getting Condition, now proceed to refresh");
			
		}
		
		public Map<String,String> getUSBStatus(String sandboxid) {
			Map<String,String> status=new HashMap<>();
			Response getusbresponse;
			Log.info("Get Method for getting all sandboxes status and condition");
			String URL=getURL("getSandboxCondition");
			String replacedURL=URL.replace("id", sandboxid);
			System.out.println(replacedURL);
			getusbresponse = RestCommonUtils.getUSBRequest(replacedURL);
			System.out.println("getusbresponse:" +getusbresponse.getStatusCode());
			
			//Check the Sandbox published condition
			if(getusbresponse.getStatusCode() == 200) {
				JsonPath jsonPathEvaluator = getusbresponse.jsonPath();
				status.put("condition",jsonPathEvaluator.get("condition").toString());
				status.put("state",jsonPathEvaluator.get("state").toString());	
			}
			return status;
		}
		
		@Test(description="This testcase is to Refresh a Sandbox",priority=111,enabled=true)
		public void refreshSandbox() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response usbresponse;
			Log.info("PUT Method for Refreshing USB ");
			String URL=getURL("usbRefresh");
			String parameters="NULL";
			String replacedURL=URL.replace("id", refreshableSandboxId);
			System.out.println(replacedURL);
			headers=new HashMap<String,String>();
			usbresponse = RestCommonUtils.putRequest(replacedURL,parameters,headers,uname,password);
			System.out.println("Response Status Code:  "+usbresponse.getStatusCode());
			System.out.println("Response Body:"+usbresponse.getBody().asString());
			Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Request is successful");
			
			//Get the condition of USB Refresh
			Map<String,String> status_condition=new HashMap<>();
			status_condition=getUSBStatus(refreshableSandboxId);
			for(String key:status_condition.keySet())
				System.out.println("Key is"+key+"Value is"+status_condition.get(key));
		
		}
		
		
		
		//Get Sandbox Condition
		@Test(description="This testcase is to get the condition",priority=112,enabled=true)
		public void getSandboxRefreshCondition() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
					
			Log.info("Get Method for getting USB refresh condition");
			Map<String,String> status_condition=new HashMap<>();
			status_condition=getUSBStatus(refreshableSandboxId);
			for(String key:status_condition.keySet())
				System.out.println("USB " + key+" is  :"+status_condition.get(key));
			while(status_condition.containsValue("pending") && status_condition.containsValue("PENDING_REFRESH_BEGIN"))
			{
				CommonUtils.hold(20); //Sleep for 20 seconds to check the status again
				status_condition=getUSBStatus(refreshableSandboxId);
				for(String key:status_condition.keySet())
					System.out.println("USB "+key+" is  :"+status_condition.get(key));
			}
			Log.info("Done with getting Condition, now proceed to accept");
		}
		
		@Test(description="This testcase is to Get Refresh Logs",priority=113,enabled=true)
		public void getRefreshLogs() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response getusbresponse;
			Log.info("Get Method for getting refresh logs");
			String URL=getURL("usbRefreshLogs");
			String replacedURL=URL.replace("id", refreshableSandboxId);
			System.out.println(replacedURL);
			getusbresponse = RestCommonUtils.getUSBRequest(replacedURL);
			System.out.println("getusbresponse status code :" +getusbresponse.getStatusCode());
			System.out.println("getusbresponse body :" +getusbresponse.body().asString());
			
		}
		
		@Test(description="This testcase is to Accept a Sandbox",priority=114,enabled=true)
		public void refreshAcceptSandbox() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response usbresponse;
			Log.info("PUT Method for Accepting Changes in refresh USB ");
			String URL=getURL("usbRefreshAccept");
			String parameters=getParameters("usbRefreshAccept");
			String replacedURL=URL.replace("id", refreshableSandboxId);
			System.out.println(replacedURL);
			Map<String,String> status_condition=new HashMap<>();
			status_condition=getUSBStatus(refreshableSandboxId);
			headers=new HashMap<String,String>();
			if(status_condition.containsValue("PENDING_REFRESH_RESUME")) //Proceed to Accept all changes		
			{
			usbresponse = RestCommonUtils.putRequest(replacedURL,parameters,headers,uname,password);
			System.out.println("Response Status Code:  "+usbresponse.getStatusCode());
			System.out.println("Response Body:"+usbresponse.getBody().asString());
			Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Request is successful");
			status_condition=getUSBStatus(refreshableSandboxId);
			
			}
			//Get Refresh Status
			
		}
		
		//Get Sandbox Condition
		@Test(description="This testcase is to get the condition",priority=115,enabled=true)
		public void getRefreshAcceptAllCondition() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
							
			Log.info("Get Method for getting USB refresh accept condition");
			Map<String,String> status_condition=new HashMap<>();
			status_condition=getUSBStatus(refreshableSandboxId);
			for(String key:status_condition.keySet())
				System.out.println("USB " + key+" is  :"+status_condition.get(key));
			while(status_condition.containsValue("pending"))
			{
				CommonUtils.hold(20); //Sleep for 20 seconds to check the status again
				status_condition=getUSBStatus(refreshableSandboxId);
				for(String key:status_condition.keySet())
					System.out.println("USB "+key+" is  :"+status_condition.get(key));
			}
			if(status_condition.containsValue("ACTIVE"))
					System.out.println("Refresh Completed - Accepted all changes");
							
		}
	
		
		//Get Lookups
		@Test(description="This testcase is to get Lookups",priority=116,enabled=true)
		public void getLookups() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response getusbresponse;
			Log.info("Get Method for getting Lookups");
			String URL=getURL("usbLookups");
			System.out.println(URL);
			getusbresponse = RestCommonUtils.getUSBRequest(URL);
			Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
			System.out.println("getusbresponse Status Code:" +getusbresponse.getStatusCode());		
			System.out.println("getusbresponse Body:" +getusbresponse.getBody().asString());	
		}
		
		//Get Sandbox Content
		@Test(description="This testcase is to get all contents",priority=117,enabled=true)
		public void getSandboxContents() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response getusbresponse;
			Log.info("Get Method for getting all sandboxes contents");
			String URL=getURL("usbGetContents");
			String replacedURL=URL.replace("id", refreshableSandboxId);
			System.out.println(replacedURL);
			getusbresponse = RestCommonUtils.getUSBRequest(replacedURL);	
			Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
			System.out.println("getusbresponse Status Code:" +getusbresponse.getStatusCode());		
			System.out.println("getusbresponse Body:" +getusbresponse.getBody().asString());		
		}
		
		//Get About USB 
		@Test(description="This testcase is to get all existing Features - Repo names",priority=118,enabled=true)
		public void getAbout() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response getusbresponse;
			Log.info("Get Method for getting about USB");
			String URL=getURL("usbAbout");
			System.out.println(URL);
			getusbresponse = RestCommonUtils.getUSBRequest(URL);
			Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
			System.out.println("getusbresponse Status Code:" +getusbresponse.getStatusCode());		
			System.out.println("getusbresponse Body:" +getusbresponse.getBody().asString());		
		}		
		
		
		//Delete an USB
		@Test(description="This testcase is to create a Custom Object for Publishable Sandbox",priority=119,enabled=true)
		public void deleteSandbox() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response usbresponse;
			Log.info("Post Method for Creating Custom Object for a USB ");
			String URL=getURL("usbDelete");
			String parameters="NULL";
			String replacedURL=URL.replace("id", refreshableSandboxId);
			System.out.println(replacedURL);
			usbresponse = RestCommonUtils.deleteUSBRequest(replacedURL);
			System.out.println("Response Status Code:  "+usbresponse.getStatusCode());
			System.out.println("Response Body:"+usbresponse.getBody().asString());
			Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Request is successful");
		}	
		
		
		
}
