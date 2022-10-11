package REST.tests.UnifiedSandbox.CSM;

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


public class CSMRestExport {
	public static String CsSetId="0";
	static Map<String, List<Object>> postObject;
	Map<String, String> responseObject;
	String response;
	static String RestUrl;
	static String uniqueID;
	static String ExportStatus;
	static Integer count=0;
	
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
	@BeforeClass(alwaysRun = true,description="Prequisite: Parse CSM REST Json")
	public void parseJson() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException{
		postObject = RestCommonUtils.parseUSBJson("CSMUSB","./src/main/java/ConfigurationResources/REST/CSMExpImp.json");
		String env=GetDriverInstance.EnvironmentName;
		Integer index=env.indexOf("/fscmUI/");
		RestUrl=env.substring(0,index);
		System.out.println(RestUrl);
        uniqueID = CommonUtils.uniqueId();
        
		
	}
	
	//Get Delta CSM Set list 
	@Test(description="Get List of Delta Migration Sets",priority=400,enabled=true)
	public void getListOfDeltaMigrationSet() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		Response getusbresponse;
		Log.info("Get Method for getting list of delta migration sets");
		String URL=getURL("GetListOfDeltaMigrationSets");
		System.out.println(URL);
		getusbresponse = RestCommonUtils.getUSBRequest(URL);
		Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
		System.out.println("GetListOfDeltaMigrationSets Status Code:" +getusbresponse.getStatusCode());		
		System.out.println("GetListOfDeltaMigrationSets Body:" +getusbresponse.getBody().asString());		
	}
	
	//Get List of Optional Modules
		@Test(description="Get List of Optional Modules",priority=401,enabled=true)
		public void getListOfOptionalModules() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response getusbresponse;
			Log.info("Get Method List of Optional Modules");
			String URL=getURL("GetListOfOptionalModules");
			System.out.println(URL);
			getusbresponse = RestCommonUtils.getUSBRequest(URL);
			Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
			System.out.println("GetListOfOptionalModules Status Code:" +getusbresponse.getStatusCode());		
			System.out.println("GetListOfOptionalModules Body:" +getusbresponse.getBody().asString());		
		}
	
		//Get Migration mode
		@Test(description="Get Migration Mode",priority=402,enabled=true)
		public void getMigrationMode() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
					Response getusbresponse;
					Log.info("Get Method for getting Migration mode");
					String URL=getURL("GetMigrationMode");
					System.out.println(URL);
					getusbresponse = RestCommonUtils.getUSBRequest(URL);
					Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
					System.out.println("GetMigrationMode Status Code:" +getusbresponse.getStatusCode());		
					System.out.println("GetMigrationMode Body:" +getusbresponse.getBody().asString());		
		}	
		
		//Export Migration Set
		@Test(description="Export Migration Set",priority=403,enabled=true)
		public void createExportMigrationSet() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
		{		
			Response usbresponse;
			Log.info("Post Method for Creating Migration Set ");
			String URL=getURL("CreateMigrationSet");
			String parameters=getParameters("CreateMigrationSet");
			usbresponse = RestCommonUtils.postRequest(URL,parameters,"app_impl_consultant");
			Log.info("Migration Set Creation Response from  Server");
			System.out.println("getStatusCode for createMigrationSetExport:  "+usbresponse.getStatusCode());
			System.out.println("createMigrationSetExport Response:"+usbresponse.getBody().asString());
			Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Request is successful");
			
			//Get Migration Set ID
			if(usbresponse.getStatusCode() == 200) {
				JsonPath jsonPathEvaluator = usbresponse.jsonPath();
				CsSetId=jsonPathEvaluator.get("csID");
				System.out.println("Export CS Set id is :"+CsSetId);
				System.out.println("Export Status is :"+jsonPathEvaluator.get("Status"));
				 if(CsSetId == null) {
						System.out.println("Export not started, didnot get the csSetId, hence quitting:");
						Assert.fail("CsSet id is null -"+jsonPathEvaluator.get("Status"));
						return;
					}
			}	
		}
		
		
	    
		
		
		//Get Export Status
		@Test(description="Get Export Status",priority=404,enabled=true)
		public void getExportStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
				{	
			        //One more status -  "TargetPushStatus" : "FAILED"
			        //CsSetId = "1074775186311856"; //to be deleted
			        Response  export_response;
			        CommonUtils.hold(30);//Sleep for 20 seconds to check the status
					Log.info("Get Method for getting export status");
					 if(CsSetId == null) {
							System.out.println("Export not started, didnot get the csSetId, hence quitting:");
							Assert.fail("CsSet id is null, may be Export did not start");
							return;
						}
					while(true) {
						    System.out.println(count++);
							export_response=getCSMExportStatus(CsSetId);
							if(export_response.getStatusCode() == 200) {
								JsonPath jsonPathEvaluator = export_response.jsonPath();
								ExportStatus=jsonPathEvaluator.get("Status");	
								System.out.println("Export Status is :"+ExportStatus);
							}
							if(ExportStatus.equals("This operation is not permitted as some other CSM operation is in progress")) {	
								System.out.println("Export Status is  "+ExportStatus);
								Assert.fail(ExportStatus);
								return;
							}
							if(ExportStatus.equalsIgnoreCase("Error") || ExportStatus.equalsIgnoreCase("ERRORED"))
							{   
								System.out.println("Cannot be exported");
								System.out.println("GetExport Body:" +export_response.getBody().asString());			
								System.out.println("Export Status is  "+ExportStatus);
								Assert.fail("Cannot be exported"+ export_response.getBody().asString());
								return;
							}
							else if(ExportStatus.equalsIgnoreCase("CREATED"))
							{
								System.out.println("Migration Set Created Successfully");
								System.out.println("Export Status is  "+ExportStatus);
								Assert.assertTrue(true);
								return;
							}
							else if(ExportStatus.equalsIgnoreCase("SUCCESS"))
							{
								System.out.println("Exported Successfully, trying to upload to UCM so lets wait for it to change to CREATED");
								System.out.println("Export Status is  "+ExportStatus);
								CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
							}
							else if(ExportStatus.equalsIgnoreCase("IN_PROGRESS")) {
								System.out.println("Export Status is  "+ExportStatus);
								CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
							}							
					}
				}

		private Response getCSMExportStatus(String CsSetId) {
			Response getusbresponse;
			Log.info("Get Method for export status");
			String URL=getURL("GetExportStatus");
			String replacedURL=URL.replace("id",CsSetId);
			
			System.out.println(replacedURL);
			getusbresponse = RestCommonUtils.getUSBRequest(replacedURL);
			Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
			System.out.println("GetExport Status Code:" +getusbresponse.getStatusCode());		
			System.out.println("GetExport Body:" +getusbresponse.getBody().asString());			
			return getusbresponse;
		}
		
	}
