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


public class CSMRestRestore { 	
	static Map<String, List<Object>> postObject;
	Map<String, String> responseObject;
	String response;
	static String RestUrl;
	static String SourceRestUrl;
	public static String CsSetId="0";
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
			String env=GetDriverInstance.Environment2Name;
			String sourceenv=GetDriverInstance.EnvironmentName;
			Integer index=env.indexOf("/fscmUI/");
			SourceRestUrl=sourceenv.substring(0,index);
			RestUrl=env.substring(0,index);
			System.out.println(RestUrl);
	        uniqueID = CommonUtils.uniqueId();
	        CsSetId = CSMRestExport.CsSetId;
			
		}
	
	
		
		private Response getCSMExportStatus(String CsSetId) {
			Response getusbresponse;
			Log.info("Get Method for export status");
			String URL=getSourceURL("GetExportStatus");
			String replacedURL=URL.replace("id",CsSetId);
			
			System.out.println(replacedURL);
			getusbresponse = RestCommonUtils.getUSBRequest(replacedURL);
			Assert.assertEquals(getusbresponse.getStatusCode() == 200, true, "Server Request is successful");
			System.out.println("GetExport Status Code:" +getusbresponse.getStatusCode());		
			System.out.println("GetExport Body:" +getusbresponse.getBody().asString());			
			return getusbresponse;
		}

	//Restore Migration Set
		@Test(description="restore Migration Set",priority=424,enabled=true) //dependsOnMethods="importMigrationSet" to be included
		public void restoreMigrationSet() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
				{				        
					Response usbresponse;
					Response import_response;
					import_response=getCSMStatus(CsSetId);
					//Status - RESTORED, ERROR 
					if(getStatusCode(import_response) == 200) {
							if(getStatus(import_response).equalsIgnoreCase("APPLY_SUCCESS") || getStatus(import_response).equalsIgnoreCase("APPLY_ERRORED")) //If applied then start to Restore the CSM Set
							{ 
									Log.info("Post Method for restoreing Migration Set ");
									String URL=getURL("RestoreCSMSet");
									String replacedURL=URL.replace("id",CsSetId);
									System.out.println(replacedURL);
									usbresponse = RestCommonUtils.postRequest(replacedURL,"NULL","app_impl_consultant"); //No Payload, so passing NULL
									Log.info("restore Response from  Server");
									System.out.println("restoreMigrationSet Status code :  "+usbresponse.getStatusCode());
									System.out.println("restoreMigrationSet Response :"+usbresponse.getBody().asString());
									Assert.assertEquals(usbresponse.getStatusCode() == 200, true, "Response code is"+usbresponse.getStatusCode());
									
									//Check the error message
									if(usbresponse.getStatusCode() == 200) {
										JsonPath jsonPathEvaluator = usbresponse.jsonPath();
										usbresponse.prettyPrint();
										System.out.println("restore Status  "+jsonPathEvaluator.get("status"));
										System.out.println("Message is ::   "+jsonPathEvaluator.get("Message"));
										if (jsonPathEvaluator.get("Message").toString().contains(("Unexpected error")))
												Assert.fail("Unexpected Error while restoreing Migration set id.."+CsSetId);
										else if (jsonPathEvaluator.get("status").toString().equals(("restore started")))
					                            assertTrue(true);
									}
							}

					}
							
					
				}
	
		
		@Test(description="Get Restore Status",priority=425,enabled=true)
		public void getRestoreStatus() throws FileNotFoundException, IOException, ParseException, ReflectiveOperationException
						{		
			
			 //restore Status - LOADED,restore STARTED,IN_PROGRESS,BACKUP_DONE,
					        Response  restore_response;
							Log.info("Get Method for getting restore status");
							while(true) {
								    System.out.println(count++);
									restore_response=getCSMStatus(CsSetId);
									if(restore_response.getStatusCode() == 200) {
										JsonPath jsonPathEvaluator = restore_response.jsonPath();
										restoreStatus=jsonPathEvaluator.get("Status");	
										System.out.println("restore Status is :"+restoreStatus);		
										
										if(restoreStatus.equalsIgnoreCase("RESTORE_ERRORED"))
										{   
											System.out.println("Cannot be restoreed");
											System.out.println("Getrestore Body:" +restore_response.getBody().asString());			
											System.out.println("restore Status is  "+restoreStatus);
											Assert.fail("Cannot be restoreed"+ restore_response.getBody().asString());
											return;
										}
										else if(restoreStatus.equalsIgnoreCase("RESTORE_SUCCESS"))
										{
											System.out.println("restoreed Successfully");
											System.out.println("restore Status is  "+restoreStatus);
											Assert.assertTrue(true);
											return;
										}
										else if(restoreStatus.equalsIgnoreCase("RESTORE_IN_PROGRESS"))
										{
											CommonUtils.hold(20);//Sleep for 20 seconds to check the status again
										}
											
									}
									else 
									{
										Assert.fail("Status code is not 200"+restore_response.getStatusCode());
										return;
									}
									System.out.println("restore Status is  "+restoreStatus);
									
							}
						}
		


		private Response getCSMStatus(String CsSetId) {
					Response getusbresponse;
					String URL=getURL("GetImportStatus");
					String replacedURL=URL.replace("id",CsSetId);
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
