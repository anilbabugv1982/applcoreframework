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


public class CSMRestAbort { 	
	static Map<String, List<Object>> postObject;
	Map<String, String> responseObject;
	String response;
	static String RestUrl;
	static String SourceRestUrl;
	static String CsSetIdForAbort="0";
	static String importStatus,applyStatus,abortStatus,restoreStatus;
	static String uniqueID;
	static Integer count=0;
	static String ExportStatus;
	
	public String getURL(String keyDescription) {
		return RestUrl+postObject.get(keyDescription).get(1).toString();
	}
	
	public String getSourceURL(String keyDescription) {
		return SourceRestUrl+postObject.get(keyDescription).get(1).toString();
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
			postObject = RestCommonUtils.parseUSBJson("CSMUSB","./src/main/java/ConfigurationResources/REST/CSMExpImp.json");
			String targetenv=GetDriverInstance.Environment2Name;
			String sourceenv=GetDriverInstance.EnvironmentName;
			Integer index=targetenv.indexOf("/fscmUI/");
			SourceRestUrl=sourceenv.substring(0,index);
			RestUrl=targetenv.substring(0,index);
			System.out.println(RestUrl);
	        uniqueID = CommonUtils.uniqueId();
	    
			
		}
	
	//Export Migration Set
	@Test(description="Export Migration Set to test Abort",priority=433,enabled=true)
	public void createExportMigrationSet() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
	{		
		Response usbresponse;
		Log.info("Post Method for Creating Migration Set ");
		String URL=getSourceURL("CreateMigrationSet");
		String parameters=getParameters("CreateMigrationSet");
		usbresponse = RestCommonUtils.postRequest(URL,parameters,"app_impl_consultant");
		Log.info("Migration Set Creation Response from  Server");
		System.out.println("getStatusCode for createMigrationSetExport:  "+usbresponse.getStatusCode());
		System.out.println("createMigrationSetExport Response:"+usbresponse.getBody().asString());
		Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Request is successful");
		
		//Get Migration Set ID
		if(usbresponse.getStatusCode() == 200) {
			JsonPath jsonPathEvaluator = usbresponse.jsonPath();
			CsSetIdForAbort=jsonPathEvaluator.get("csID");
			System.out.println("Export CS Set id is :"+CsSetIdForAbort);
			System.out.println("Export Status is :"+jsonPathEvaluator.get("Status"));
			 if(CsSetIdForAbort == null) {
					System.out.println("Export not started, didnot get the CsSetIdForAbort, hence quitting:");
					Assert.fail("CsSet id is null -"+jsonPathEvaluator.get("Status"));
					return;
				}
			 updateConfigurationUI(CsSetIdForAbort);
		}	
	}
	
	
    public void updateConfigurationUI(String CsSetIdForAbort) {
    	
    }
	
	
	
	//Get Export Status
	@Test(description="Get Export Status",priority=434,enabled=true)
	public void getExportStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
			{	
		        //One more status -  "TargetPushStatus" : "FAILED"
		        //CsSetIdForAbort = "1074775186311856"; //to be deleted
		        Response  export_response;
		        CommonUtils.hold(30);//Sleep for 20 seconds to check the status
				Log.info("Get Method for getting export status");
				 if(CsSetIdForAbort == null) {
						System.out.println("Export not started, didnot get the CsSetIdForAbort, hence quitting:");
						Assert.fail("CsSet id is null, may be Export did not start");
						return;
					}
				while(true) {
					    System.out.println(count++);
						export_response=getCSMExportStatus(CsSetIdForAbort);
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
						else if(ExportStatus.equalsIgnoreCase("CREATED") )
						{
							System.out.println("Exported Successfully");
							System.out.println("Export Status is  "+ExportStatus);
							Assert.assertTrue(true);
							return;
						}
						else if(ExportStatus.equalsIgnoreCase("IN_PROGRESS") || ExportStatus.equalsIgnoreCase("SUCCESS")) {
							System.out.println("Export Status is  "+ExportStatus);
							CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
						}							
				}
			}

	
	private Response getCSMExportStatus(String CsSetIdForAbort) {
		Response getusbresponse;
		Log.info("Get Method for export status");
		String URL=getSourceURL("GetExportStatus");
		String replacedURL=URL.replace("id",CsSetIdForAbort);
		
		System.out.println(replacedURL);
		getusbresponse = RestCommonUtils.getUSBRequest(replacedURL);
		Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
		System.out.println("GetExport Status Code:" +getusbresponse.getStatusCode());		
		System.out.println("GetExport Body:" +getusbresponse.getBody().asString());			
		return getusbresponse;
	}
	
			//Abort Migration Set
		@Test(description="abort Migration Set",priority=435,enabled=true) //dependsOnMethods="importMigrationSet" to be included
		public void abortMigrationSet() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
						{		
					        
							Response usbresponse;
							Response import_response;
							import_response=getCSMStatus(CsSetIdForAbort);		
							if(getStatusCode(import_response) == 200) {
									if(getStatus(import_response).equalsIgnoreCase("IMPORT_SUCCESS") || getStatus(import_response).equalsIgnoreCase("LOADED")) //If imported then we can abort the CSM Set
									{ 
											Log.info("Post Method for aborting Migration Set ");
											String URL=getURL("AbortImportCSMSet");
											String replacedURL=URL.replace("id",CsSetIdForAbort);
											System.out.println(replacedURL);
											usbresponse = RestCommonUtils.postRequest(replacedURL,"NULL","app_impl_consultant"); //No Payload, so passing NULL
											Log.info("abort Response from  Server");
											System.out.println("abortMigrationSet Status code :  "+usbresponse.getStatusCode());
											System.out.println("abortMigrationSet Response :"+usbresponse.getBody().asString());
											Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Response code is"+usbresponse.getStatusCode());
											
											//Check the error message
											if(usbresponse.getStatusCode() == 200) {
												JsonPath jsonPathEvaluator = usbresponse.jsonPath();
												usbresponse.prettyPrint();
												System.out.println("abort Status  "+jsonPathEvaluator.get("status"));
												System.out.println("Message is ::   "+jsonPathEvaluator.get("Message"));
												if (jsonPathEvaluator.get("Message").toString().contains(("Unexpected error")))
														Assert.fail("Unexpected Error while aborting Migration set id.."+CsSetIdForAbort);
												else if (jsonPathEvaluator.get("status").toString().equals(("abort started")))
							                            assertTrue(true);
											}
									}

							}
							
						}
		
		
		//Get abort Status
		@Test(description="Get Abort Status",priority=436,enabled=true)
		public void getAbortStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
						{		
			
					        Response  abort_response;
							Log.info("Get Method for getting abort status");
							while(count<50) {
								    System.out.println(count++);
									abort_response=getCSMStatus(CsSetIdForAbort);
									if(abort_response.getStatusCode() == 200) {
										JsonPath jsonPathEvaluator = abort_response.jsonPath();
										abortStatus=jsonPathEvaluator.get("Status");	
										System.out.println("abort Status is :"+abortStatus);		
										
										if(abortStatus.equalsIgnoreCase("Error"))
										{   
											System.out.println("Cannot be aborted");
											System.out.println("Getabort Body:" +abort_response.getBody().asString());			
											System.out.println("abort Status is  "+abortStatus);
											Assert.fail("Cannot be aborted"+ abort_response.getBody().asString());
											return;
										}
										else if(abortStatus.equalsIgnoreCase("ABORTED") || abortStatus.equalsIgnoreCase("SUCCESS"))
										{
											System.out.println("aborted Successfully");
											System.out.println("abort Status is  "+abortStatus);
											Assert.assertTrue(true);
											return;
										}
										else if(abortStatus.equalsIgnoreCase("IN_PROGRESS") || abortStatus.equalsIgnoreCase("BACKUP_DONE") || abortStatus.equalsIgnoreCase("ABORT_IN_PROGRESS"))
										{
											CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
										}
											
									}
									else 
									{
										Assert.fail("Status code is not 200"+abort_response.getStatusCode());
									}
									System.out.println("abort Status is  "+abortStatus);
									
							}
						}

		private Response getCSMStatus(String CsSetIdForAbort) {
					Response getusbresponse;
					String URL=getURL("GetImportStatus");
					String replacedURL=URL.replace("id",CsSetIdForAbort);
					System.out.println(replacedURL);
					getusbresponse = RestCommonUtils.getUSBRequest(replacedURL);
					Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Response code is  "+getusbresponse.getStatusCode());
					System.out.println("Getimport Status Code:" +getusbresponse.getStatusCode());		
					System.out.println("Getimport Body:" +getusbresponse.getBody().asString());			
					return getusbresponse;
				}
		
				
		private String getStatus(Response response) {
			
			return response.jsonPath().get("Status");
		
		}
		
       private Integer getStatusCode(Response response) {
			
			return response.getStatusCode();
		
		}
		
	}
