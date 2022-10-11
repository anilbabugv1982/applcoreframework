package REST.tests;

import static org.testng.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import UtilClasses.REST.RestCommonUtils;
import UtilClasses.UI.CommonUtils;
import UtilClasses.UI.DbResource;
import UtilClasses.UI.Log;

import TestBase.UI.GetDriverInstance;
import org.testng.annotations.BeforeClass;


public class KFFRESTTest {
	
	static Map<String, List<Object>> postObject;
	Map<String, String> responseObject;
	Map<String, String> headers;
	String response;
	static String RestUrl;
	static String uniqueID;
	static String depStatus;
	static Integer count=0;
	static String deploymentStatus;
	static Map<String, Map<String,String>> queryResult;
	
	@DataProvider (name = "KFF_code_provider")
	 public Object[][] dpMethod(){
	 return new Object[][] {
	//	 {"/101/KFF/GL#"}, 
	//	 {"/140/KFF/CAT#"},
	//	 {"/140/KFF/KEY#"},
	//	 {"/140/KFF/LOC#"}, 
    	 {"/401/KFF/MDSP"},
		 {"/401/KFF/MTLL"},
		 {"/707/KFF/CONS"},
		 {"/707/KFF/VALU"},
		 {"/800/KFF/PPG"},
		 {"/801/KFF/COST"},
		 {"/10011/KFF/MCAT"}, 
		 {"/10052/KFF/XCC"},
		 {"/10455/KFF/VRM"} 
	 };
	 }
	
	public String getURL(String keyDescription) {
		return RestUrl+postObject.get(keyDescription).get(1).toString();
	}
	
	public String getURL(String keyDescription,String KFFCode) throws UnsupportedEncodingException {
		StringBuilder URL=new StringBuilder(RestUrl+postObject.get(keyDescription).get(1).toString());
		/*if(KFFCode.contains("#"))
		{
			String[] sp=KFFCode.split("/");
			URL.append("/"+sp[1]+"/"+sp[2]+"/");
			return URL.append(encodeValue(sp[3])).toString();
			
		}*/
		return URL.append(KFFCode).toString();
	}
	
	private String encodeValue(String value) throws UnsupportedEncodingException {
	    return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
	    
	}
	
	public String getParameters(String keyDescription) {
		return postObject.get(keyDescription).get(2).toString();
	}
	
	//Get Json Object and URL
	@BeforeClass(alwaysRun = true,description="Prequisite: Parse KFF REST Json")
	public void parseJson() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException{
		postObject = RestCommonUtils.parseUSBJson("KFF","./src/main/java/ConfigurationResources/REST/KFFRest.json");
		String env=GetDriverInstance.EnvironmentName;
		Integer index=env.indexOf("/fscmUI/");
		RestUrl=env.substring(0,index);
		System.out.println(RestUrl);
        uniqueID = CommonUtils.uniqueId();
       
	}
	
	//Get Deployment Status
	@Test(description="Get Deployment Status",priority=700,enabled=true)
	public void firsttime_getDeploymentStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		Response getresponse;
		Log.info("Get Deployment Status ");
		String URL=getURL("getDeploymentStatus","/801/KFF/CAT#");
		System.out.println(URL);
		headers=new HashMap<String,String>();
		getresponse = RestCommonUtils.getRequest(URL,headers,"app_impl_consultant","Welcome1");
		Assert.assertEquals(getresponse.getStatusCode() == 200, true, "Server Request is successful");
		System.out.println(" Status Code:" +getresponse.getStatusCode());		
		System.out.println(" Body:" +getresponse.getBody().asString());		
	}
	
	//Deploy KFF
	@Test(dataProvider = "KFF_code_provider",description="Deploy KFF",priority=701,enabled=true)
	public void deployKFF(String kffcode) throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
			Response response;
			Log.info("Post Method for Deploying Key Flex Field ");
			String URL=getURL("deployFlexRest",kffcode);
			String parameters=getParameters("deployFlexRest");
			response = RestCommonUtils.postRequest(URL,parameters,"app_impl_consultant");
			Log.info("Deploy Flex Response");
			System.out.println("Response StatusCode :  "+response.getStatusCode());
			System.out.println("Response Body :"+response.getBody().asString());
			Assert.assertEquals(response.getStatusCode() == 200, true, "Request is unsuccessful, got .."+response.getStatusCode());
			
			//Get Deployment Status
			if(response.getStatusCode() == 200) {
				getDeploymentStatus(kffcode);
			}
	}
		
	@Test(description="Update the status for some of the KFF",priority=702,enabled=true)
	public void update_statusKFF() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
			Log.info("Update KFF Status");
			System.out.println("Update KFF Status");
			try {
			updateKFFStatus("GL#","READY");
			updateKFFStatus("CONS","READY_INC");
			updateKFFStatus("COST","EDITED");
			updateKFFStatus("KEY#","EDITED_READY");
			updateKFFStatus("MDSP","ERROR"); 
			updateKFFStatus("LOC#","SANDBOXED"); 
			updateKFFStatus("MCAT","READY_INC");
			updateKFFStatus("CAT#","READY_INC");
			}
			catch(Exception e) {
				System.out.println("Exception while updating KFF statuses");
				e.printStackTrace();
			}
			
	}
		
	//Deploy KFF
	@Test(dataProvider = "KFF_code_provider",description="Deploy KFF",priority=703,enabled=true)
	public void deployKFFAfterStatusUpdate(String kffcode) throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
					Response response;
					Log.info("Post Method for Deploying Key Flex Field ");
					String URL=getURL("deployFlexRest",kffcode);
					String parameters=getParameters("deployFlexRest");
					response = RestCommonUtils.postRequest(URL,parameters,"app_impl_consultant");
					Log.info("Deploy Flex Response");
					System.out.println("Response StatusCode :  "+response.getStatusCode());
					System.out.println("Response Body :"+response.getBody().asString());
					Assert.assertEquals(response.getStatusCode() == 200, true, "Request is unsuccessful, got .."+response.getStatusCode());
					
					//Get Deployment Status
					if(response.getStatusCode() == 200) {
						getDeploymentStatus(kffcode);
					}
	}
		
		
		
	public void updateKFFStatus(String kffCode, String status) {
			int result_row;
			try {
				result_row = DbResource.updateDB("update FND_KF_FLEXFIELDS_VL set deployment_status = '"+status+"' where key_flexfield_code = '"+kffCode+"'");
				if (result_row>0) {
					Log.info("KFF Status updated successfully");
					System.out.println("Code -"+kffCode+" Updated status is :"+status);
				}
				else
					System.out.println("Code -"+kffCode+" Not Updated as :"+status);
			} catch (SQLException e) {
				System.out.println("Problem with DB connection");
				e.printStackTrace();
			}
		}
		
		
		//Get KFF Deployment Status
		public void getDeploymentStatus(String KFFCode) throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
				{		        
			        Response  kffDeploy_response;
			        CommonUtils.hold(30);//Sleep for 30 seconds to check the status
					Log.info("Get Method for getting deployment Status");
					while(true) {
						    System.out.println(count++);
							kffDeploy_response=getKFFDeployStatus(KFFCode);
							if(kffDeploy_response.getStatusCode() == 200) {
								JsonPath jsonPathEvaluator = kffDeploy_response.jsonPath();
								depStatus=jsonPathEvaluator.get("jobStatus");	
								System.out.println("Status is :"+depStatus);
								if(depStatus.equalsIgnoreCase("ERROR")) {
									String depErrorMsg=jsonPathEvaluator.get("deploymentErrorMessage");
									if(!depErrorMsg.contains("is not Submitted"))
									{
										System.out.println("Deployment didnot go well as it errored out  "+depStatus);
										Assert.fail(depStatus+jsonPathEvaluator.get("deploymentErrorMessage"));
										return;
									}
									else
										CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
								}
								else if(depStatus.equalsIgnoreCase("DEPLOYED"))
								{
									System.out.println(KFFCode + " has deployed Successfully");
									Assert.assertTrue(true);
									return;
								}
								else if(depStatus.equalsIgnoreCase("DEPLOYING")) {
									System.out.println("Still.. "+depStatus);
									CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
								}							
							}
							else {
								Assert.fail("Status code is not successful::"+kffDeploy_response.getStatusCode()); 
								return;
							}
								
					}
	}

	private Response getKFFDeployStatus(String KFFCode) throws UnsupportedEncodingException {
			Response getresponse;
			Log.info("Checking Deployment status");
			String URL=getURL("getDeploymentStatus",KFFCode);
			getresponse = RestCommonUtils.getUSBRequest(URL);
			Assert.assertEquals(getresponse.getStatusCode() == 200, true, "Server Request is successful");
			System.out.println("Response Status Code:" +getresponse.getStatusCode());		
			System.out.println("Response Body:" +getresponse.getBody().asString());			
			return getresponse;
	}
		
	
	@AfterClass(alwaysRun = true)
	public void allDone_updateStatusBack() throws InterruptedException, SQLException {

		Log.info("Update KFF Status");
		System.out.println("Update KFF Status");
		try {
			updateKFFStatus("LOC#","EDITED_READY"); 
		
		}
		catch(Exception e) {
			System.out.println("Exception while updating KFF statuses");
			e.printStackTrace();
		}
	}

}
